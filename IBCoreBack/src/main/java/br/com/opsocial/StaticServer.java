package br.com.opsocial;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneRules;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.message.BasicNameValuePair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.opsocial.ejb.das.MaintenanceTimezoneRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.Timezone;
import br.com.opsocial.server.utils.RecoverMaintenance;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import facebook4j.internal.org.json.XML;

public class StaticServer {

	public static void main(String args[]) throws ParseException {
/*
		Calendar cExpire = Calendar.getInstance();
		cExpire.add(Calendar.YEAR, 1);
		System.out.println(cExpire.get(Calendar.YEAR));



		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><retorno><num_pedido>3531535704</num_pedido><status_pagamento>7</status_pagamento><mensagem_transacao>(99) Transação não autorizada</mensagem_transacao><operadora_mensagem>Transação bloqueada: </operadora_mensagem><metodo>mastercard</metodo><redirect>false</redirect></retorno>";

        try {
			JSONObject resultJson = XML.toJSONObject(xml);
			JSONObject r = resultJson.getJSONObject("retorno");
			String num_pedido = r.getString("num_pedido");
			String status_pagamento = r.getString("status_pagamento");
			String mensagem_transacao = r.getString("mensagem_transacao");
			String operadora_mensagem = r.getString("operadora_mensagem");
			String metodo = r.getString("metodo");
			String redirect = r.getString("redirect");
			System.out.println(mensagem_transacao);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		*/
		
		/*JDate d = new Date(1498100400);1498359600
		Date d = new Date(1498100400000L);
		Date d2 = new Date(1498359600000L);
		//----- System.out.println(d.toString());
		//----- System.out.println(d2.toString());*/
		String encryptedPassword = new BCryptPasswordEncoder().encode("123456");
		
		//----- System.out.println(encryptedPassword);

		Date d = new Date();
		//----- System.out.println(d.getTime());
		//----- System.out.println(d.toGMTString());
		
		GregorianCalendar gc = new GregorianCalendar();
		////----- System.out.println(gc.getTimeInMillis());
		////----- System.out.println(gc.get(GregorianCalendar.HOUR_OF_DAY));
		////----- System.out.println(gc.get(GregorianCalendar.HOUR));
		
		//gc.set(GregorianCalendar.ZONE_OFFSET, -7200000);
		//----- System.out.println(TimeZone.getAvailableIDs().toString());

		Object o = TimeZone.getAvailableIDs();
		//gc.setTimeZone(Zone);
		////----- System.out.println(gc.get(GregorianCalendar.HOUR_OF_DAY));
		////----- System.out.println(gc.get(GregorianCalendar.HOUR));

		DateFormat df = new SimpleDateFormat("M/d/yyyy H:m:s");
		
		
		ZoneRules rules = ZoneId.of("America/New_York").getRules();

		// if it has DST, isFixed is false
		boolean isFixed = rules.isFixedOffset();

		// if lists are empty, offset never varies, so there's no DST
		List<ZoneOffsetTransitionRule> transitionRules = rules.getTransitionRules();
		List<ZoneOffsetTransition> transitions = rules.getTransitions();

		// check a specific date (using java.time.Instant)
		System.out.println(rules.isDaylightSavings(Instant.now()));
		// check a specific date (using java.util.Date)
		Date date = new Date();
		System.out.println(rules.isDaylightSavings(date.toInstant()));
		
		
		
		TimeZone t = TimeZone.getTimeZone("Asia/Macau");		
		System.out.println(t.getDisplayName()); 		
		
		t = TimeZone.getTimeZone("America/Los_Angeles");
		System.out.println(t.getDisplayName());
		
		t = TimeZone.getTimeZone("America/Phoenix");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Denver");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Chicago");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/New_York");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Indiana/Indianapolis");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Pacific/Pago_Pago");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Pacific/Honolulu");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Anchorage");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Tijuana");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Chihuahua");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Guatemala");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Mexico_City");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Regina");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Bogota");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Caracas");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Halifax");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Cuiaba");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/La_Paz");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Santiago");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/St_Johns");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Sao_Paulo");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Argentina/Buenos_Aires");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Cayenne");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Godthab");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("America/Montevideo");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Atlantic/South_Georgia");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Atlantic/Azores");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Atlantic/Cape_Verde");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Africa/Casablanca");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Etc/UTC");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Europe/London");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Atlantic/Reykjavik");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Europe/Berlin");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Europe/Prague");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Europe/Brussels");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Europe/Warsaw");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Africa/Algiers");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Amman");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Europe/Athens");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Beirut");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Africa/Cairo");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Damascus");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Africa/Harare");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Europe/Helsinki");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Jerusalem");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Europe/Minsk");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Africa/Windhoek");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Baghdad");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Europe/Istanbul");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Kuwait");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Europe/Moscow");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Africa/Nairobi");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Tehran");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Muscat");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Baku");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Indian/Mauritius");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Tbilisi");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Yerevan");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Kabul");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Yekaterinburg");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Karachi");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Tashkent");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Kolkata");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Colombo");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Kathmandu");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Almaty");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Dhaka");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Novosibirsk");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Rangoon");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Bangkok");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Krasnoyarsk");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Hong_Kong");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Irkutsk");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Australia/Perth");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Taipei");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Ulaanbaatar");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Tokyo");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Seoul");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Yakutsk");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Australia/Adelaide");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Australia/Darwin");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Australia/Brisbane");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Pacific/Guam");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Australia/Hobart");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Australia/Sydney");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Asia/Magadan");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Pacific/Auckland");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Pacific/Fiji");
		System.out.println(t.getDisplayName()); 
		
		t = TimeZone.getTimeZone("Pacific/Tongatapu");
		System.out.println(t.getDisplayName()); 
		/*
		Asia/Macau
		Pacific/Funafuti
		UTC
		Europe/London

		America/Cuiaba
		Asia/Jerusalem
		
		/1000 /60 /60
		*/

		//df.setTimeZone(t);
		df.setLenient (false); 
		//Date dt = df.parse ("25/09/2017 0:30:00 GMT+01:00");
		//Date dt = df.parse ("09/25/2017 20:30:00 GMT-03:00");
		int i = (((t.getRawOffset() / 1000) / 60) / 60);
		Date dt = df.parse ("03/09/2018 15:00:00");
		//System.out.println(dt.getTime());
		System.out.println(dt.toGMTString());

		DateFormat df2 = new SimpleDateFormat("M/d/yyyy H:m:s, z");
		//df2.setTimeZone(TimeZone.getTimeZone("America/Indiana/Indianapolis"));
		df2.setLenient (false); 
		Date dt2 = df2.parse ("03/09/2018 17:20:00, GMT-03:00");
		//System.out.println(dt2.getTime());
		System.out.println(dt2.toGMTString());

		DateFormat df3 = new SimpleDateFormat("M/d/yyyy H:m:s, z");
		//df3.setTimeZone(TimeZone.getTimeZone("America/Cuiaba"));
		df3.setLenient (false); 
		Date dt3 = df3.parse ("03/09/2018 17:20:00, GMT-03:00");
		//System.out.println(dt2.getTime());
		System.out.println(dt3.toGMTString());

		DateFormat df4 = new SimpleDateFormat("M/d/yyyy H:m:s, z");
		//df4.setTimeZone(TimeZone.getTimeZone("Australia/Brisbane"));
		df4.setLenient (false); 
		Date dt4 = df4.parse ("03/09/2018 17:20:00, GMT-03:00");
		//System.out.println(dt2.getTime());
		System.out.println(dt4.toGMTString());
		
		
		
		

		List<Object> l = new ArrayList<Object>();
		MaintenanceTimezoneRemote setRemote = (MaintenanceTimezoneRemote) RecoverMaintenance.recoverMaintenance("Timezone");
		List<Timezone> timezones = setRemote.listTimezones();
		l.add(timezones);

		for (Timezone itTimeZone : timezones) {

			if (itTimeZone.getName() != null && !itTimeZone.getName().equals("")) {
				TimeZone tt = TimeZone.getTimeZone(itTimeZone.getName());
				Float valueF = new Float( new Float(( tt.getRawOffset()/1000 ) /60) /60);
				Object valueAux = null;
				itTimeZone.setValue(valueF);

				if ((valueF - Math.floor(valueF)) > 0) {
					valueAux = Math.round((30 * (valueF - Math.floor(valueF))) / 0.50);
				} else {
					valueAux = "00";
				}

				String compHour = Math.round(valueF) + "";
				if (Math.round(valueF) < 10 && Math.round(valueF) > -10) {
					if (Math.round(valueF) > 0){
						compHour = "+0" + Math.round(valueF);
					} else {
						compHour = "-0" + Math.round(valueF * -1);
					}
				}

				itTimeZone.setDescription( "(GMT" + compHour + ":" + valueAux + ") - " + itTimeZone.getDescription() );
			}
		}
	}
}
