package br.com.opsocial.server.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class LanguageIdentifier {
	
	private HttpPost httpPost;
	private HttpClient httpclient;
	
	public LanguageIdentifier() {
	
		httpclient = new DefaultHttpClient();
		
		httpclient = WebClientDevWrapper.wrapClient(httpclient);
		
		httpPost = new HttpPost("https://services.open.xerox.com/RestOp/LanguageIdentifier/GetLanguageForString");
	}
	
	public String recoverLanguage(String content) {
		
        try {
        	
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("document", content));
	        
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
			HttpResponse response = httpclient.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
		      
			if (entity != null) {
				InputStream instream = entity.getContent();
				
	            try {
	            	StringWriter writer = new StringWriter();
	                IOUtils.copy(instream, writer, HTTP.UTF_8);
	                
	                return writer.toString();
	                
	          } finally {
	        	  instream.close();
	          }
	      }
			
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return null;
	}
}
