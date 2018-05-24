package br.com.primum.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ObjetoBusca {

	String caminhoClasseJava();
	
	String campoBuscar();
	
	String campoComparar();
	
	boolean persistir();
	
	boolean notificador();
}