package br.com.primum.chat.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.primum.flexclient.PrimumFlexClient;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.singleton.SingletonFlexSession;
import flex.messaging.FlexContext;
import flex.messaging.MessageBroker;
import flex.messaging.client.FlexClientManager;
import flex.messaging.messages.AsyncMessage;

public class ChatService {

	public List<Usuario> buscarContatosAtivos() {

		List<Usuario> listaUsuarios = new ArrayList<>();

		//http://192.168.20.16:8080/Builder/messagebroker/amf
//		URL url;
//		try {
//			url = new URL("http://192.168.20.16:8080/Builder/messagebroker/amf/MessageBrokerServlet");
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			
//			InputStream response = connection.getInputStream();
//			System.out.println("InputStream response");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        /*  
        connection.setDoInput(true);  
        connection.setDoOutput(true);  
        connection.setUseCaches(false);  
        connection.setDefaultUseCaches (false);  
        connection.setRequestProperty("Content-Type", "application/octet-stream");  
                  

        ObjectOutputStream out = new ObjectOutputStream(  
                connection.getOutputStream());  
        JasperPrint jasperPrint = new JasperPrint(); //só para testar o servlet, quando funcionar passarei a referencia correta  
        out.writeObject(jasperPrint);             
        out.close(); 
        
		*/
		MessageBroker contexto = null;
		FlexClientManager fcm = null;
		if ( (FlexContext.getFlexSession() == null) || (FlexContext.getMessageBroker() == null) ) {
			contexto = 	SingletonFlexSession.getMessageBroker();
		} else {
			contexto = 	FlexContext.getMessageBroker();
		}
		
		if (contexto != null) {
			fcm = 		contexto.getFlexClientManager();
		}
		
		if(fcm!=null){		
			for ( String str  : fcm.getClientIds() ) {
	
				PrimumFlexClient pfc = (PrimumFlexClient) fcm.getFlexClient(str).getUserPrincipal();
				if (pfc != null) {
					pfc.getUsuario().setClientId(str);
					listaUsuarios.add(pfc.getUsuario());
				}	
			}
		}
		
		return listaUsuarios;
	}
	
//	public Boolean substituiContatoAtivo(List<Usuario> listaUsuariosRepetidos){
//		
//		MessageBroker contexto = null;
//		FlexClientManager fcm = null;
//		if ( (FlexContext.getFlexSession() == null) || (FlexContext.getMessageBroker() == null) ) {
//			contexto = 	SingletonFlexSession.getMessageBroker();
//		} else {
//			contexto = 	FlexContext.getMessageBroker();
//		}
//		
//		if (contexto != null) {
//			fcm = 		contexto.getFlexClientManager();
//		}
//		
//		for ( String str  : fcm.getClientIds() ) {
//
//			for(Usuario u : listaUsuariosRepetidos){
//				if(u.getClientId().equals(str)){
//					
//					PrimumFlexClient pfc = (PrimumFlexClient) fcm.getFlexClient(str).getUserPrincipal();
//					if (pfc != null) {
//						pfc.getUsuario().setCd_usuario(0);
//					}
//					break;
//				}
//			}
//			
////			if(str.equals(clientId)){
////				PrimumFlexClient pfc = (PrimumFlexClient) fcm.getFlexClient(str).getUserPrincipal();
////				if (pfc != null) {
////					pfc.getUsuario().setCd_usuario(0);
////					
////				}
////			}
//		}
//		
//		return true;
//	}
	
	public void notificarRefreshLista() {
		AsyncMessage msg = new AsyncMessage();

		msg.setClientId("Java-Based-Producer-For-Messaging");
		msg.setTimestamp(new Date().getTime());
		msg.setMessageId("ATUALIZAR_LISTA_CHAT_PRIMUM");
		msg.setDestination("chat");
		msg.setBody(buscarContatosAtivos());
		msg.setHeader("sender", "From the server");
		MessageBroker.getMessageBroker(null).routeMessageToService(msg, null);
	}

	@SuppressWarnings("static-access")
	public void enviarMensagem(String destinatario, String mensagem) {

		MessageBroker contexto = null;
		FlexClientManager fcm = null;
		if ( (FlexContext.getFlexSession() == null) || (FlexContext.getMessageBroker() == null) ) {
			contexto = 	SingletonFlexSession.getMessageBroker();
		} else {
			contexto = 	FlexContext.getMessageBroker();
		}

		if (contexto != null) {
			fcm = 		contexto.getFlexClientManager();
		}

		for ( String str  : fcm.getClientIds() ) {

			if ( str.toLowerCase().equals( destinatario.toLowerCase() ) ) {

				AsyncMessage msg = new AsyncMessage();

				msg.setMessageId("MENSAGEM_CHAT_PRIMUM");
				msg.setClientId(destinatario);
				msg.setTimestamp(new Date().getTime());
				msg.setDestination("chatMensagem");
				msg.setBody(mensagem);
				//msg.setHeader(msg.SUBTOPIC_HEADER_NAME, FlexContext.getFlexClient().getId() );
				msg.setHeader(msg.SUBTOPIC_HEADER_NAME, destinatario );
				//msg.setHeader("cd_usuario", cd_usuario_receber);
				
				if (FlexContext.getFlexClient() != null) {
					
					System.out.println("FlexClientID: " + FlexContext.getFlexClient().getId());
					msg.setHeader("clientId", FlexContext.getFlexClient().getId() );
				}

				MessageBroker.getMessageBroker(null).routeMessageToService(msg, null);
			}
		}

		System.out.println("++++++++++++++" + mensagem + "*************************");
	}
	
	
	@SuppressWarnings("static-access")
	public void enviarMensagemNotificacaoAutomatica(String destinatario, String mensagem) {

		MessageBroker contexto = null;
		FlexClientManager fcm = null;
		if ( (FlexContext.getFlexSession() == null) || (FlexContext.getMessageBroker() == null) ) {
			contexto = 	SingletonFlexSession.getMessageBroker();
		} else {
			contexto = 	FlexContext.getMessageBroker();
		}

		if (contexto != null) {
			fcm = 		contexto.getFlexClientManager();
		}

		for ( String str  : fcm.getClientIds() ) {

			if ( str.toLowerCase().equals( destinatario.toLowerCase() ) ) {

				AsyncMessage msg = new AsyncMessage();

				msg.setMessageId("MENSAGEM_NOT_AUT");
				msg.setClientId(destinatario);
				msg.setTimestamp(new Date().getTime());
				msg.setDestination("chatMensagem");
				msg.setBody(mensagem);
				//msg.setHeader(msg.SUBTOPIC_HEADER_NAME, FlexContext.getFlexClient().getId() );
				msg.setHeader(msg.SUBTOPIC_HEADER_NAME, destinatario );
				//msg.setHeader("cd_usuario", cd_usuario_receber);
				
				if (FlexContext.getFlexClient() != null) {
					
					System.out.println("FlexClientID: " + FlexContext.getFlexClient().getId());
					msg.setHeader("clientId", FlexContext.getFlexClient().getId() );
				}

				MessageBroker.getMessageBroker(null).routeMessageToService(msg, null);
			}
		}

		System.out.println("++++++++++++++" + mensagem + "*************************");
	}
	
	
	@SuppressWarnings("static-access")
	public void enviarMensagemTodos( String mensagem) {

		MessageBroker contexto = null;
		FlexClientManager fcm = null;
		if ( (FlexContext.getFlexSession() == null) || (FlexContext.getMessageBroker() == null) ) {
			contexto = 	SingletonFlexSession.getMessageBroker();
		} else {
			contexto = 	FlexContext.getMessageBroker();
		}

		if (contexto != null) {
			fcm = 		contexto.getFlexClientManager();
		}

		for ( String str  : fcm.getClientIds() ) {

			AsyncMessage msg = new AsyncMessage();

			msg.setMessageId("MENSAGEM_TODOS");
			msg.setClientId(str);
			msg.setTimestamp(new Date().getTime());
			msg.setDestination("chatMensagem");
			msg.setBody(mensagem);
			msg.setHeader(msg.SUBTOPIC_HEADER_NAME, str );
			
			if (FlexContext.getFlexClient() != null) {
				msg.setHeader("clientId", FlexContext.getFlexClient().getId() );
			}

			MessageBroker.getMessageBroker(null).routeMessageToService(msg, null);
			
		}
	}
	
}