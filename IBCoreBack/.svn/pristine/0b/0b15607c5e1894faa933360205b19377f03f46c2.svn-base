package br.com.opsocial.server.actions.application;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.primitive.StringList;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.GenericImageAdapt;
import br.com.opsocial.server.utils.UtilFunctions;

@RestController
@RequestMapping("woopsocial")
public class RecoverLinkResumeAction extends ServerAction {

	private Document doc;

	private List<Object> images;

	private HashMap<String, Object> img_container = new HashMap<String, Object>();

	@CrossOrigin
	@RequestMapping(value = "/recover_link_resume",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> doAction(String url) throws Exception, OptimisticLockException {

		try {

			if ((url != null) && (url.indexOf("http") == -1)) {
				url = "http://" + url;
			}

			try {
				doc = Jsoup.parse(new URL(url), 5000);
			} catch (SocketTimeoutException ste) {
				ste.printStackTrace();
			}

			images = new ArrayList<Object>();

			this.img_container = new HashMap<String, Object>();

			getOpenGraphImage();

			String imageOG = doc.getElementsByAttributeValue("property", "og:image").attr("content");
			String title = doc.getElementsByAttributeValue("property", "og:title").attr("content");
			String description = doc.getElementsByAttributeValue("property", "og:description").attr("content");

			if ( (imageOG == null) || (imageOG == "") ) {
				Elements imgElements = doc.getElementsByTag("img");
	
				for(Iterator<Element> iterImg = imgElements.iterator(); iterImg.hasNext();) {
	
					Element imgElement = (Element) iterImg.next();
	
					GenericImageAdapt genericImageAdapt  = null;
	
					Integer width = null;
	
					if(imgElement.attr("width") != null && !imgElement.attr("width").isEmpty()) {
						if(imgElement.attr("width").contains("px")) {
							width = Integer.parseInt(imgElement.attr("width").substring(0, imgElement.attr("width").indexOf("px")));
						} else {
							width = Integer.parseInt(imgElement.attr("width"));
						}
					} else {
	
						try {
							genericImageAdapt = UtilFunctions.getImageSize(url + imgElement.attr("src"));
							width = genericImageAdapt.getWidth();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					Integer height = null;
				
					if(genericImageAdapt != null) {
						height = genericImageAdapt.getHeight();
					} else {
						if(imgElement.attr("height") != null && !imgElement.attr("height").isEmpty()) {
							
							if(imgElement.attr("height").contains("px")) {
								height = Integer.parseInt(imgElement.attr("height").substring(0, imgElement.attr("height").indexOf("px")));
							} else {
								height = Integer.parseInt(imgElement.attr("height"));
							}
						}
					}
	
					if(width != null) {
	
						if(width > 100) {
	
							try {
								new URL(imgElement.attr("src"));
								//images.add(imgElement.attr("src") + "â—™" + width + "â—™" + height);
								img_container.put("image", imgElement.attr("src"));
								img_container.put("width", width);
								img_container.put("height", height);
								images.add(img_container);
								break;
							} catch (Exception e) {
							}
						}
						
					} else {
						new URL(imgElement.attr("src"));
						//images.add(imgElement.attr("src") + "â—™nullâ—™null");
						img_container.put("image", imgElement.attr("src"));
						img_container.put("width", null);
						img_container.put("height", null);
						images.add(img_container);
						break;
					}
				}
			} else {

				img_container.put("image", imageOG);
				img_container.put("width", null);
				img_container.put("height", null);
				images.add(img_container);
			}

			if (title == null || title == "") {
				title = doc.getElementsByTag("title").html();
			}

			if (description == null || description == "") {
				title = doc.getElementsByTag("title").html();
			}

			setParameters(new HashMap<String, Object>());

			getParameters().put("title", new StringUtil(title));
			getParameters().put("description", new StringUtil(description));
			getParameters().put("images", images);
			getParameters().put("link", url);

		} catch (UnknownHostException e) {

			this.setParameters(null);
		} catch (Exception e) {

			getParameters().put("title", new StringUtil(""));
			getParameters().put("description", new StringUtil(""));
			getParameters().put("images", new StringList(new ArrayList<String>()));
			getParameters().put("link", url);
			e.printStackTrace();
		}

		return new ResponseEntity<>(getParameters(), HttpStatus.OK);
	}
	
	private void getOpenGraphImage() {
		
		String image = null;
		
		Elements elements = doc.select("meta[property=og:image]");
		
		if(!elements.isEmpty()) {
			
			image = elements.get(0).attr("content");
			
			try {
				
				if(!new URI(image).isAbsolute()) {
					
					URI uriImage = new URI(doc.baseUri());
					
					image = uriImage.getScheme() + "://" + uriImage.getHost() + image;
				}
				
				new URL(image);
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
				image = null;
			} catch (URISyntaxException e) {
				e.printStackTrace();
				image = null;
			}
		}
		
		if(image != null) {
			//images.add(image + "◙null◙null");
			this.img_container.put("image", image);
			this.img_container.put("width", null);
			this.img_container.put("height", null);
			images.add(this.img_container);
		}
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		// TODO Auto-generated method stub
		
	}
}
