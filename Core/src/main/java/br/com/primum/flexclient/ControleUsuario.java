package br.com.primum.flexclient;

import java.util.Date;

import flex.messaging.FlexSession;
import flex.messaging.FlexSessionListener;
import flex.messaging.MessageClient;
import flex.messaging.MessageClientListener;
import flex.messaging.client.FlexClient;
import flex.messaging.client.FlexClientListener;
import flex.messaging.config.ConfigMap;
import flex.messaging.services.AbstractBootstrapService;

public class ControleUsuario extends AbstractBootstrapService implements FlexSessionListener, FlexClientListener, MessageClientListener {
	
	//private UsuarioUtils utils = new UsuarioUtils();
	 
	@Override
	public void sessionCreated(FlexSession session) {
		System.out.println("Sessão criada: " + new Date().toString());
		session.addSessionDestroyedListener(this);
	}
 
	@Override
	public void sessionDestroyed(FlexSession session) {
		System.out.println("Sessão destruida: " + new Date().toString());
	}
 
	@Override
	public void clientCreated(FlexClient client) {
		client.addClientDestroyedListener(this);
        System.out.println("FlexCliente criado: " + client.getId() + " " + new Date().toString());
	}
 
	@Override
	public void clientDestroyed(FlexClient client) {
		//utils.retirarUsuarioLista((String) client.getAttribute("usuario"));
		System.out.println("FlexClient destruido: " + client.getId() + " " + new Date().toString());
	}
 
	@Override
	public void initialize(String arg0, ConfigMap arg1) {
		 System.out.println("BootstrapService is initializing... " + new Date().toString());
	     ControleUsuario listener = new ControleUsuario();
	     FlexSession.addSessionCreatedListener(listener);
	     FlexClient.addClientCreatedListener(listener);
	}
 
	@Override
	public void start() {
		System.out.println("start() " + new Date().toString());
	}
 
	@Override
	public void stop() {
		System.out.println("stop() " + new Date().toString());
	}

	@Override
	public void messageClientCreated(MessageClient arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageClientDestroyed(MessageClient arg0) {
		// TODO Auto-generated method stub
		
	}
}
