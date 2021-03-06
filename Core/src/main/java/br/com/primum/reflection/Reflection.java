package br.com.primum.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.interfaces.ChaveEstrangeira;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;

public class Reflection {

	public static boolean getExisteChavePrimaria(Object bean) throws Exception {		
		Boolean salvar = true;                  
		Field[] fields = bean.getClass().getDeclaredFields();    
		for (Field field : fields) {
			if (field.isAnnotationPresent(Id.class)) {
				field.setAccessible(true);
				if (field.get(bean) != null){
					salvar = false;
				}
			}
		}
		return salvar;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getExisteChavePrimaria(Class classe) throws Exception {

		String retorno = null;
		Field[] fields = classe.getDeclaredFields();
		for (Field field : fields) {

			if (field.isAnnotationPresent(Id.class)) {
				retorno = field.getName();
			}
		}

		return retorno;
	}
	
	public static Class<?> getTipoCampo(Object bean, String nome) throws Exception {		
		
		Field[] fields = bean.getClass().getDeclaredFields();    
		for (Field field : fields) {                    
			if (field.getName().equals(nome)) {
				return field.getType();
			}
		}
		return null;
	}

	public static String getNomeChavePrimaria(Object bean) throws Exception {		

		String nomeCampo = "";
		
		if (bean != null) {
			try {
				Field[] fields = bean.getClass().getDeclaredFields();
				for (Field field : fields) {                    
					if (field.isAnnotationPresent(Id.class)) {
						nomeCampo = field.getName();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nomeCampo;
	}

	public static void setValorChaveEstrangeira(Object pai, Object filho) throws Exception {		

		String nomeChavePrimaria = Reflection.getNomeChavePrimaria(pai);
		Field[] fields = filho.getClass().getDeclaredFields();
		boolean invocarMetodo 	= false; 

		for (Field field : fields) {

			String nomeCampo 		= null;
			//LogPrimum.mensagem("field.getName(): " + field.getName() + "\n nomeChavePrimaria: " + nomeChavePrimaria );
			if (field.isAnnotationPresent(ChaveEstrangeira.class)) {
				if (field.getAnnotation(ChaveEstrangeira.class).nomeObjetoOrigem().equals(nomeChavePrimaria)) {
					nomeCampo = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1).toLowerCase();
					invocarMetodo = true;
				}
			}

			if (invocarMetodo && (nomeCampo != null) ) {

				Method metodo = filho.getClass().getMethod( nomeCampo, field.getType() );
				Object valor = Reflection.getValorCampo(pai, nomeChavePrimaria);
				metodo.invoke(filho, valor);
				break;
			}
		}
		
		if (!invocarMetodo) {
			for (Field field : fields) {

				String nomeCampo 		= null;
				if (field.getName().equals(nomeChavePrimaria)) {

					nomeCampo = "set" + nomeChavePrimaria.substring(0, 1).toUpperCase() + nomeChavePrimaria.substring(1).toLowerCase();
					invocarMetodo = true;
				}

				if (invocarMetodo && (nomeCampo != null) ) {

					Method metodo = filho.getClass().getMethod( nomeCampo, field.getType() );
					Object valor = Reflection.getValorCampo(pai, nomeChavePrimaria);
					metodo.invoke(filho, valor);
					break;
				}
			}
		}
	}

	public static void setValorChavePrimaria(Object bean, Object valor) throws Exception {

		String nome = Reflection.getNomeChavePrimaria(bean);
		if(nome!=null && nome!=""){
			nome = "set" + nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
			Method metodo = bean.getClass().getMethod(nome, Integer.class);
			metodo.invoke( bean, valor );	
		}
	}

	public static Object getValorChavePrimaria(Object bean) throws Exception {

		String nome = Reflection.getNomeChavePrimaria(bean);
		if (nome != "") {	
			nome = "get" + nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
			Method metodo = bean.getClass().getMethod(nome);
			return metodo.invoke( bean );
		} else {
			return null;
		}
	}

	public static void setValorCampo(Object bean, Object valor) throws Exception {

		String nome = Reflection.getNomeChavePrimaria(bean.getClass());
		nome = "set" + nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
		Method metodo = bean.getClass().getMethod(nome, Integer.class);
		metodo.invoke( bean, 1 );
	}

	public static Object getValorCampo(Object bean, String nomeCampo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		//LogPrimum.mensagem("nomeCampo: "+ nomeCampo);
		nomeCampo = "get" + nomeCampo.substring(0, 1).toUpperCase() + nomeCampo.substring(1);
		//Method metodo = classe.getMethod(nome, Integer.class);
		Method metodo = bean.getClass().getMethod( nomeCampo );
		Object valor = (Object) metodo.invoke(bean);
	
		return valor;
	}

	public static void setBeanConfiguracao(Object bean, String propriedade, Object valor) throws Exception {

		propriedade = "set" + propriedade.substring(0, 1).toUpperCase() + propriedade.substring(1).toLowerCase();
		Method metodo = bean.getClass().getMethod(propriedade, String.class);
		metodo.invoke( bean, (String) valor );
	}

	public static void setListaFilhos(Object bean, String propriedade, Object valor) throws Exception {

		propriedade = "set" + propriedade.substring(0, 1).toUpperCase() + propriedade.substring(1);
		Method metodo = bean.getClass().getMethod(propriedade, List.class);
		metodo.invoke( bean, valor );
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean verificarExisteLista(Class classe) throws Exception {

		boolean retorno = false;
		Field[] fields = classe.getDeclaredFields();
		for (Field field : fields) {

			if ( field.getType().getName().equals("java.util.List") ) {

				if (field.isAnnotationPresent(ListaBusca.class)) {
					retorno = true;
				}
			}
		}

		return retorno;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean verificarExisteObjetoBusca(Class classe) throws Exception {

		boolean retorno = false;
		Field[] fields = classe.getDeclaredFields();
		for (Field field : fields) {

			if (field.isAnnotationPresent(ObjetoBusca.class)) {
				retorno = true;
			}
		}

		return retorno;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean verificarExisteChaveEstrangeira(Class classe) throws Exception {

		boolean retorno = false;
		Field[] fields = classe.getDeclaredFields();
		for (Field field : fields) {

			if (field.isAnnotationPresent(ChaveEstrangeira.class)) {
				retorno = true;
			}
		}

		return retorno;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getNomeChaveEstrangeira(Class classe) throws Exception {

		String retorno = null;
		Field[] fields = classe.getDeclaredFields();
		for (Field field : fields) {

			if (field.isAnnotationPresent(ChaveEstrangeira.class)) {
				retorno = field.getName();
			}
		}

		return retorno;
	}

	@SuppressWarnings("rawtypes")
	public static void setValorObject(Object bean, String propriedade, Object valor, Class classe) throws Exception {

		propriedade = "set" + propriedade.substring(0, 1).toUpperCase() + propriedade.substring(1).toLowerCase();
		Method metodo = bean.getClass().getMethod(propriedade, classe);
		metodo.invoke( bean, valor );
	}

	@SuppressWarnings("rawtypes")
	public static String getNomeChavePrimaria(Class classe) throws Exception {		

		String nomeCampo = "";
		try {
			Field[] fields = classe.getDeclaredFields();
			for (Field field : fields) {                    
				if (field.isAnnotationPresent(Id.class)) {
					nomeCampo = field.getName();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nomeCampo;
	}

	public static void montarObjetoAbstrato(Object objeto, LinkedHashMap<String, Object> mapa) throws Exception {		

		try {
			Field[] fields = objeto.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Column.class)) {
					Reflection.setValorObject(objeto, field.getName(), mapa.get(field.getName()), field.getType());
				}else if (field.isAnnotationPresent(Id.class)) {
					Reflection.setValorObject(objeto, field.getName(), mapa.get(field.getName()), field.getType());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
