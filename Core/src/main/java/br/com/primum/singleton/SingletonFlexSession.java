package br.com.primum.singleton;

import flex.messaging.MessageBroker;

public class SingletonFlexSession {

	private static SingletonFlexSession instance = null;

	private static MessageBroker messageBroker = null;

	protected SingletonFlexSession() {
		// Exists only to defeat instantiation.
	}

	public static boolean isInstance() {

		boolean valor = false;
		if(instance != null) {
			valor = true;
		}

		return valor;
	}

	public static SingletonFlexSession getInstance() {
		if(instance == null) {
			instance = new SingletonFlexSession();
		}
		return instance;
	}

	public static void setMessageBroker(MessageBroker f) {
		messageBroker = f;
	}

	public static MessageBroker getMessageBroker() {
		return messageBroker;
	}
}