package br.com.opsocial.server.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.util.ReflectionUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import br.com.opsocial.ejb.interfaces.OpColumn;

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

			/*if (field.isAnnotationPresent(ChaveEstrangeira.class)) {
				if (field.getAnnotation(ChaveEstrangeira.class).nomeObjetoOrigem().equals(nomeChavePrimaria)) {
					nomeCampo = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1).toLowerCase();
					invocarMetodo = true;
				}
			}*/

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

	public static void setChavePrimariaFromList(Object bean, LinkedHashMap<String, Object> mapa) throws Exception {

		String nome = Reflection.getNomeChavePrimaria(bean);
		Object valueFromMap = mapa.get(nome);
		Long id = null;

		if (valueFromMap instanceof String) {
			id = new Long((String) mapa.get(nome));
		} else if (valueFromMap instanceof Integer) {
			id = new Long((Integer) mapa.get(nome));
		}

		if(nome!=null && nome!=""){
			nome = "setId";
			Method metodo = bean.getClass().getMethod(nome, Long.class);
			metodo.invoke( bean, id );	
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

				/*if (field.isAnnotationPresent(ListaBusca.class)) {
					retorno = true;
				}*/
			}
		}

		return retorno;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean verificarExisteObjetoBusca(Class classe) throws Exception {

		boolean retorno = false;
		Field[] fields = classe.getDeclaredFields();
		for (Field field : fields) {

			/*if (field.isAnnotationPresent(ObjetoBusca.class)) {
				retorno = true;
			}*/
		}

		return retorno;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean verificarExisteChaveEstrangeira(Class classe) throws Exception {

		boolean retorno = false;
		Field[] fields = classe.getDeclaredFields();
		for (Field field : fields) {

			/*if (field.isAnnotationPresent(ChaveEstrangeira.class)) {
				retorno = true;
			}*/
		}

		return retorno;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getNomeChaveEstrangeira(Class classe) throws Exception {

		String retorno = null;
		Field[] fields = classe.getDeclaredFields();
		for (Field field : fields) {

			/*if (field.isAnnotationPresent(ChaveEstrangeira.class)) {
				retorno = field.getName();
			}*/
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
				if (field.isAnnotationPresent(Column.class) && ( mapa.get(field.getName()) != null ) ) {
					if ( (field.getType() == Character.class) || (field.getType() == char.class) ) {
						ReflectionUtils.setField(field, objeto, ((String) mapa.get(field.getName())).charAt(0) );
					} else if (field.getType() == Long.class) {

						if (mapa.get(field.getName()) instanceof String) {
							ReflectionUtils.setField(field, objeto, new Long((String) mapa.get(field.getName())));
						} else if (mapa.get(field.getName()) instanceof Integer) {
							ReflectionUtils.setField(field, objeto, new Long((Integer) mapa.get(field.getName())));
						}						

					} else if (field.getType() == Date.class) {
						//----- System.out.println(new Date("2017-09-22 17:20:00").getTime());
						//----- System.out.println(new Date("2017-09-22T17:20:00.003Z").getTime());

						if (mapa.get(field.getName()) instanceof Long) {
							ReflectionUtils.setField(field, objeto, new Date((Long) mapa.get(field.getName())));
						} else if (mapa.get(field.getName()) instanceof String) {
							ReflectionUtils.setField(field, objeto, new Date((String) mapa.get(field.getName())));	
						}

					} else {
						
						if (field.getType() == Double.class) {
							if (mapa.get(field.getName()) instanceof Integer) {
								ReflectionUtils.setField(field, objeto, new Double((Integer) mapa.get(field.getName())) );
							}
						} else {
							ReflectionUtils.setField(field, objeto, mapa.get(field.getName()));
						}
					}
				} else if (field.isAnnotationPresent(OneToMany.class) && ( mapa.get(field.getName()) != null ) && (field.getType().equals(List.class)) ) {
					//ReflectionUtils.setField(field, objeto, new ArrayList((ArrayList<Object>) mapa.get(field.getName())));
					ParameterizedType listType = (ParameterizedType) field.getGenericType();
					Class<?> stringListClass = (Class<?>) listType.getActualTypeArguments()[0];
					Object o = listType.getActualTypeArguments()[0];					
					ArrayList<LinkedHashMap<String, Object>> listProfiles = (ArrayList<LinkedHashMap<String, Object>>) mapa.get(field.getName());

					List<Object> listItem = new ArrayList<>();
					for ( Object itemProfile : listProfiles ) {

						Object itemGerar = Class.forName(((Class) o).getCanonicalName()).newInstance();
						Reflection.montarObjetoAbstrato(itemGerar, (LinkedHashMap<String, Object>) itemProfile);
						listItem.add(itemGerar);
					}

					Reflection.setListaFilhos(objeto, field.getName(), listItem);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public static void populateObjectByAnnotation(Object newItem, JsonObject dataSource) throws Exception {		

		try {
			Field[] fields = newItem.getClass().getDeclaredFields();
			for (Field field : fields) {

				if (field.isAnnotationPresent(OpColumn.class)) {

					JsonPrimitive dataValue = (JsonPrimitive) dataSource.get(field.getAnnotation(OpColumn.class).jsonColumnName());
					if (dataValue != null) {
						if ( (field.getType() == Character.class) || (field.getType() == char.class) ) {
							ReflectionUtils.setField(field, newItem, dataValue.getAsCharacter() );
						} else if (field.getType() == Long.class) {

							ReflectionUtils.setField(field, newItem, new Long(dataValue.getAsString()));
							/*if (dataValue instanceof String) {
								ReflectionUtils.setField(field, newItem, new Long(dataValue.getAsString()));
							} else if (dataValue instanceof Integer) {
								ReflectionUtils.setField(field, newItem, new Long((Integer) dataValue.getAsInt()));
							}*/						
	
						} else if (field.getType() == Date.class) {
							//ReflectionUtils.setField(field, newItem, new Date((String) dataValue)));
						} else if (field.getType() == Integer.class) {
							ReflectionUtils.setField(field, newItem, Integer.parseInt(dataValue.getAsString()));
						} else {
							ReflectionUtils.setField(field, newItem, dataValue.getAsString());
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}