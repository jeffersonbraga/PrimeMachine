package br.com.primum.bo.crud;

import java.util.TimerTask;

public class LogPrimum extends TimerTask {

	public static void mensagem(Integer str) {
//		System.out.println("//LogPrimum********************************************");
//		System.out.println(str);
//		System.out.println("//LogPrimum--------------------------------------------");
	}

	public static void mensagem(String str) {
		//System.out.println("//LogPrimum********************************************");
		//System.out.println(str);
		//System.out.println("//LogPrimum--------------------------------------------");
	}

	public static void debug(String str) {
		System.out.println(str);
	}
	
	public LogPrimum() {
		System.out.println("LogPrimum ativado.");
	}

	@Override
	public void run() {
		System.out.println("run(): //LogPrimum ativado.");
	}
}
