package br.com.opsocial.server.utils.networksintegrations;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringEscapeUtils;

import br.com.opsocial.server.services.SendMail;

import com.google.gson.JsonObject;

public class LocawebSMTPIntegration {

	public static final String APP_TOKEN = "c5402f834bf26776405b38d338959db9";

	public static final String SEND_EMAIL_URL = "https://api.smtplw.com.br/v1/messages/";	

	public LocawebSMTPIntegration() {

	}

	public boolean sendEmail(String subject, String toEmail, String html) {

		try {

			JsonObject emailObject = new JsonObject();
			emailObject.addProperty("subject", subject);
			emailObject.addProperty("body", html);
			emailObject.addProperty("from", SendMail.MAIL_SMTP_USER);
			emailObject.addProperty("to", toEmail);

			JsonObject headersObject = new JsonObject();
			headersObject.addProperty("Content-Type", "text/html");

			emailObject.add("headers", headersObject);

			//create post method
			PostMethod post = new PostMethod(SEND_EMAIL_URL);
			String contentType = "application/json";
			String charset = "UTF-8";

			//set content type to json and charset to utf-8
			post.setRequestEntity(new StringRequestEntity(emailObject.toString(), contentType, charset));
			post.setRequestHeader("Content-Type", contentType);
			post.setRequestHeader("x-auth-token", APP_TOKEN);

			//create HttpClient object to sent request
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setBooleanParameter(HttpMethodParams.USE_EXPECT_CONTINUE, 
					Boolean.TRUE);
			post.getParams().setBooleanParameter(HttpMethodParams.USE_EXPECT_CONTINUE, Boolean.TRUE);
			httpClient.getParams().setContentCharset(charset);
			httpClient.getParams().setHttpElementCharset(charset);

			//call service
			int statusCode = httpClient.executeMethod(post);

			if (statusCode != HttpStatus.SC_CREATED) {
			  System.err.println("Method failed: " + post.getStatusLine());
			}
			
			byte[] responseBody = post.getResponseBody();
			  
			String response = new String(responseBody, "UTF-8");
			
			//----- System.out.println("Response..:" + response);
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
