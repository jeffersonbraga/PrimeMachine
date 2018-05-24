package br.com.opsocial;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.face4j.facebook.exception.FacebookException;
import com.google.gson.Gson;

import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceSetRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.ProxyRegion;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.services.ipag.IpagService;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.instagram.InstagramBusinessBO;
//import br.com.opsocial.server.utils.instagram.InstagramBusinessBO;
import br.com.opsocial.server.utils.instagram.InstagramPostSender;

@RestController
@RequestMapping("api")
public class ServiceTeste {

	@RequestMapping(value = "/instagram_get_first",
	    method = RequestMethod.GET,
	    produces = MediaType.APPLICATION_JSON_VALUE)
	public void getInstagramBusiness() {
		
		MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		
		Profile p = remote.getById(new Long(159));
		try {
			InstagramBusinessBO instagramBO = new InstagramBusinessBO();
			//instagramBO.createConnection();
			instagramBO.recoverBusinessID(p.getNetworkId(), p);
			instagramBO.makeRequestsTest("17841400919984869", "17906990584042351", p);
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/teste_insta",
	    method = RequestMethod.GET,
	    produces = MediaType.APPLICATION_JSON_VALUE)
	public void getIDInstagramBusiness() {

		MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");

		Profile p = remote.getById(new Long(37473));
		try {

			ProxyRegion proxyRegion = UtilFunctions.getProxyRegion(p.getAccount().getCountry(), ProxyRegion.TEN_MINUTES);
			
			InstagramPostSender ip = new InstagramPostSender(null, null, 
					proxyRegion, p.getInstagramDevice(), UtilFunctions.getResidentialPort(proxyRegion));
			ip.authenticateUser(p, false);
			String r = ip.getBusinessID();
			//----- System.out.println(r);
			
			InputStream fis = new ByteArrayInputStream(r.getBytes(StandardCharsets.UTF_8));		

			//create JsonReader object
			JsonReader jsonReader = Json.createReader(fis);

			//get JsonObject from JsonReader
			JsonObject jsonObject = jsonReader.readObject();

			//we can close IO resource and JsonReader now
			jsonReader.close();

			JsonObject o1 	= (JsonObject) jsonObject.get("instagram_user");
			
			//----- System.out.println(o1);
			//InstagramBusinessBO instagramBO = new InstagramBusinessBO();
			//instagramBO.createConnection();
			//instagramBO.recoverBusinessID(p.getNetworkId(), p);
			//instagramBO.makeRequestsTest("17841400919984869", "17906990584042351", p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/pesquisar_aux",
	    method = RequestMethod.GET,
	    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> pesquisaGrid(User user) {
		
		MaintenanceSetRemote remote = (MaintenanceSetRemote) RecoverMaintenance.recoverMaintenance("Set");

		//Sets set = remote.getById(new Long(8));
		Object o = remote.listActives();
		
		return new ResponseEntity<>(o, HttpStatus.OK);
		/*
		MaintenanceMonitoringTagRemote remote = (MaintenanceMonitoringTagRemote) RecoverMaintenance.recoverMaintenance("MonitoringTag");
		//Object o = remote.listByMonitoring(new Long(1),new Long(1));
		Object o = remote.listGlobalTags(new Long(1));
		return new ResponseEntity<>("{pesquisaGrid:ServiceTeste}", HttpStatus.OK);*/
	}

    @RequestMapping(value = "/excluir",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> excluirObjeto(@Valid @RequestBody Object objPersistir) throws Exception {
		try {
	    	
			Gson gsonDeserializer = new Gson();
	    	LinkedHashMap<String, Object> mpa = (LinkedHashMap) objPersistir;
	    	Object entidade = Class.forName((String) mpa.get("classeJava")).newInstance();
			return new ResponseEntity<>(objPersistir, HttpStatus.OK);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return new ResponseEntity<>(objPersistir, HttpStatus.NOT_MODIFIED);
		}
    }

	@CrossOrigin
	@RequestMapping(value = "/instagram_business",
	    method = RequestMethod.POST,
	    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> instagram_business(@Valid @RequestBody Profile profile) {
		
		ProxyRegion proxyRegion = UtilFunctions.getProxyRegion(profile.getAccount().getCountry(), ProxyRegion.TEN_MINUTES); 
		
		InstagramPostSender instagramPostHelper = new InstagramPostSender(profile.getInstagramCookies(), profile.getInstagramLastLoginTime(),
				proxyRegion, profile.getInstagramDevice(), UtilFunctions.getResidentialPort(proxyRegion));
		instagramPostHelper.authenticateUser(profile, false);
		String r = instagramPostHelper.getBusinessInformation();
		String r2 = instagramPostHelper.getBusinessDetailInformation();

		InputStream fis = new ByteArrayInputStream(r.getBytes(StandardCharsets.UTF_8));		

		//create JsonReader object
		JsonReader jsonReader = Json.createReader(fis);

		//get JsonObject from JsonReader
		JsonObject jsonObject = jsonReader.readObject();

		//we can close IO resource and JsonReader now
		jsonReader.close();

		JsonObject o1 	= (JsonObject) jsonObject.get("instagram_user");
		JsonObject o2 	= o1.getJsonObject("instagram_insights");
		JsonArray o3 	= o2.getJsonArray("nodes");
		//JsonObject o4 = jsonObject.get("chart_data_list");
		List<InstagramReport> listaPersistir = new ArrayList<>();
		processAudiences(profile, o3, listaPersistir);
		return new ResponseEntity<>(r, HttpStatus.NOT_MODIFIED);
	}

	@CrossOrigin
	@RequestMapping(value = "/instagram_business_media",
	    method = RequestMethod.POST,
	    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getBusinessMediaInformation(@Valid @RequestBody Profile profile) {
			
			ProxyRegion proxyRegion = UtilFunctions.getProxyRegion(profile.getAccount().getCountry(), ProxyRegion.TEN_MINUTES);
		
			InstagramPostSender instagramPostHelper = new InstagramPostSender(profile.getInstagramCookies(), profile.getInstagramLastLoginTime(),
					proxyRegion, profile.getInstagramDevice(), UtilFunctions.getResidentialPort(proxyRegion));
			instagramPostHelper.authenticateUser(profile, false);
			String r = instagramPostHelper.getBusinessMediaInformation();
			return new ResponseEntity<>(r, HttpStatus.NOT_MODIFIED);
	}

	private void processAudiences(Profile profile, JsonArray o3, List<InstagramReport> listaPersistir) {

		for (Object item : o3) {

			JsonObject itAux 	= (JsonObject) item;
			JsonObject o4 		= itAux.getJsonObject("attachments");
			JsonArray o5 		= o4.getJsonArray("nodes");

			String unit_name = itAux.getString("unit_name", "");

			if (unit_name != null) {
				unit_name = unit_name.replaceAll(" ", "_");
			}

			String chart_name;
			JsonString __typename;
			String show_percentage;
			String title;
			String tab_name;
			for (Object itemNode : o5) {

				JsonObject itNodeAux 	= (JsonObject) itemNode;
				chart_name 				= itNodeAux.getString("chart_name", "");
				__typename 				= itNodeAux.getJsonString("__typename");
				show_percentage 		= itNodeAux.getString("show_percentage", "");
				title 					= itNodeAux.getString("title", "");
				tab_name 				= itNodeAux.getString("tab_name", "");
				JsonArray datapoints 	= itNodeAux.getJsonArray("datapoints");

				if (datapoints != null) {

					//----- System.out.println("----------------------------------------------------------------------------------");
					//----- System.out.println("unit_name: " 	+ unit_name);
					//----- System.out.println("chart_name: " 	+ chart_name);
					//----- System.out.println("tab_name: " 	+ tab_name);
					//----- System.out.println("title: " 		+ title);
					//----- System.out.println("----------------------------------------------------------------------------------");
					processDataPoint(profile, datapoints, unit_name + "_" + chart_name, listaPersistir, null);
				} else {

					JsonArray oAux4 		= itNodeAux.getJsonArray("chart_data_list");
					if (oAux4 != null) {

						for (Object itemData : oAux4) {
							JsonObject itDataAux 	= (JsonObject) itemData;
							JsonArray oAux5 		= itDataAux.getJsonArray("datapoints");		
							if (oAux5 != null) {

								//----- System.out.println("----------------------------------------------------------------------------------");
								//----- System.out.println("unit_name: " 	+ unit_name);
								//----- System.out.println("chart_name: " 	+ chart_name);
								//----- System.out.println("tab_name: " 	+ tab_name);
								//----- System.out.println("title: " 		+ title);
								//----- System.out.println("----------------------------------------------------------------------------------");
								
								String day_week = null;
								if ((tab_name != null) && (tab_name.equals("Horas"))) {
									day_week = itDataAux.getString("title", "");							
								}

								processDataPoint(profile, oAux5, unit_name + "_" + tab_name, listaPersistir, day_week);
							}
						}
					}
				}
			}
		}
	}

	private void processDataPoint(Profile profile, JsonArray datapoints, String chart_name, List<InstagramReport> listaPersistir, String day_week) {

		String labelAnterior 	= "";

		for (Object itemData : datapoints) {

			InstagramReport reportItem = new InstagramReport();

			JsonObject itDataAux 	= (JsonObject) itemData;
			String label = itDataAux.getString("label", "");

			if (chart_name.equals("follower_activity_Horas") && ((label == null) || (label.equals("")))) {
				labelAnterior = new Integer(labelAnterior) + 1 + "";
				label = labelAnterior + "h";
			} else if (chart_name.equals("follower_activity_Horas") && (!label.equals(""))) {
				labelAnterior = label.replaceAll("h", "");
			}

			int v = itDataAux.getInt("y");
			reportItem.setValue(new Long(v));
			reportItem.setProperty(chart_name);
			reportItem.setLabel(label);

			if ((day_week != null) && (!day_week.equals(""))) {
				reportItem.setDayWeek(day_week);
			}
			
			reportItem.setIdInstagramUser(profile.getNetworkId());
			
			listaPersistir.add(reportItem);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/request_info",
	    method = RequestMethod.POST,
	    produces = MediaType.APPLICATION_JSON_VALUE)
	public void getRequestInfo(HttpSession req) {
		
		System.out.println(req.toString());
	}
}