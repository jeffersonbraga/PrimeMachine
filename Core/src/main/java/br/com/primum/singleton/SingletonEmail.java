package br.com.primum.singleton;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.reflection.Reflection;


public class SingletonEmail {

	private static SingletonEmail instance = null;

	private String smtp;

	private Integer porta_smtp;

	private String email_enviar;

	private String email_senha;

	private String cabecalho;
	
	private String caminho;
	
	private String ssl;

	protected SingletonEmail() {

		try {
			String dir = ContextoAplicacao.CAMINHO_APLICACAO + "//classes//email.txt";

			BufferedReader in = new BufferedReader(new FileReader(dir));

            while (in.ready()) {

            	String str = null;
            	str = in.readLine();

            	String campo = "";
            	String valor = "";
            	
            	if ((str.split("&") != null) && (str.split("&").length < 2) ) {
            		campo = str.split("&")[0];
            	} else if ((str.split("&") != null) && (str.split("&").length > 1) ) {
            		campo = str.split("&")[0];
            		valor = str.split("&")[1];
            	}

        		try {
					Reflection.setBeanConfiguracao(this, campo, valor);
				} catch (NoSuchMethodException e) {
					
					//LogPrimum.mensagem("ERRO CONVERS홒 TIPO AO LER VALORES. NOVA TENTATIVA COM INTEIRO");
					Reflection.setValorObject(this, campo, Integer.parseInt(valor), Integer.class);
					e.printStackTrace();
				}
            }
            in.close();
	    } catch (IOException e) {

	    	//LogPrimum.mensagem("ATEN플O CHECAR O CONTEXTO DA APLICA플O: " + ContextoAplicacao.CAMINHO_APLICACAO);
	    	e.printStackTrace();
	    	//LogPrimum.mensagem("ATEN플O CHECAR O CONTEXTO DA APLICA플O: " + ContextoAplicacao.CAMINHO_APLICACAO);
	    } catch (Exception e) {

	    	//LogPrimum.mensagem("ATEN플O CHECAR O CONTEXTO DA APLICA플O: " + ContextoAplicacao.CAMINHO_APLICACAO);
			e.printStackTrace();
	    	//LogPrimum.mensagem("ATEN플O CHECAR O CONTEXTO DA APLICA플O: " + ContextoAplicacao.CAMINHO_APLICACAO);
		}
		
		System.out.println("Boot class path: " + System.getProperty("sun.boot.class.path"));  
		System.out.println("Extension class path: " + System.getProperty("java.ext.dirs"));  
		System.out.println("AppClassPath: " + System.getProperty("java.class.path"));  
		  
		//Info i=new Info(); 
		Usuario i = new Usuario();
		System.out.println("\nBoot CL: " + java.lang.Object.class.getClassLoader());  
		System.out.println("App ClassLoader: " + i.getClass().getClassLoader());
	}

	public static boolean isInstance() {

		boolean valor = false;
		if(instance != null) {
			valor = true;
		}

		return valor;
	}

	public static SingletonEmail getInstance() {
		if(instance == null) {
			instance = new SingletonEmail();
		}
		return instance;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public Integer getPorta_smtp() {
		return porta_smtp;
	}

	public void setPorta_smtp(Integer porta_smtp) {
		this.porta_smtp = porta_smtp;
	}

	public String getEmail_enviar() {
		return email_enviar;
	}

	public void setEmail_enviar(String email_enviar) {
		this.email_enviar = email_enviar;
	}

	public String getEmail_senha() {
		return email_senha;
	}

	public void setEmail_senha(String email_senha) {
		this.email_senha = email_senha;
	}

	public String getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(String cabecalho) {
		this.cabecalho = cabecalho;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getSsl() {
		return ssl;
	}

	public void setSsl(String sSL) {
		ssl = sSL;
	}
	
	
}