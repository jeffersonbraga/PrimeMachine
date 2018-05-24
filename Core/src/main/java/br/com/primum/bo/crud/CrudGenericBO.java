package br.com.primum.bo.crud;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.exception.PrimumException;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.hibernate.HibernateSessionFactory;
import br.com.primum.interfaces.IInterceptador;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.reflection.Reflection;

public class CrudGenericBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	public CrudGenericBO() {
		
	}

	public Object salvar(Object obj) throws Exception {

		if (this.getDao() == null) {

			this.setDao(new GenericDAO());
		}

		try{

			if(Reflection.getExisteChavePrimaria(obj)){

				this.getDao().salvar(obj);
			} else {
				//Object objVerificar = this.getDao().findById(obj, (Integer) Reflection.getValorChavePrimaria(obj));

				//this.getDao().getSession().evict(obj);
				//this.getDao().getSession().evict(objVerificar);
				/*if (objVerificar == null) {
					this.getDao().salvar(obj);
				} else {
					this.atualizar(obj);
				}*/
				this.atualizar(obj);
			}

		}catch(Exception e){
			obj = null;
			e.printStackTrace();
		}

		return obj;
	}

	public void atualizar(Object obj) throws Exception {

		if (this.getDao() == null) {

			this.setDao(new GenericDAO());
		}

		try{
	
			this.getDao().atualizar(obj);

		}catch(Exception e){

			e.printStackTrace();
		}	
	}
	
	public void deletar(Object obj) throws Exception {

		if (this.getDao() == null) {

			this.setDao(new GenericDAO());
		}

		try{
			this.getDao().delete(obj);
		}catch(Exception e){
			
			e.printStackTrace();
			throw new PrimumException(e.getMessage());
		}
	}
	
	public Object buscarGenerico(Object obj, Integer id) throws Exception {

		Object objeto = null;
		this.setDao(new GenericDAO());
		try{

			objeto = this.getDao().findById(obj, id);
		}catch(Exception e){

			e.printStackTrace();
		}

		return objeto;
	}

	public List<?> buscarListaObjeto(Object obj) throws Exception {
		
		List<?> list = null;
		
		try{
			
			this.setDao(new GenericDAO());
			list = this.getDao().findAll(obj);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return list;
	}

	public void abrirTransacao() {
		try {

			if ( this.getDao() == null ) {
				this.setDao(new GenericDAO());
			}

			this.getDao().abrirTransacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fecharTransacao() {
		try {
			if ((this.getDao() != null) && (this.getDao().getSession().isConnected())) {
				this.getDao().fecharConexao();
			}
			
			//LogPrimum.mensagem("fecharTransacao");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public List<?> salvaObjetosLista(List<?> listaObjetos, Boolean persistirObjetoCompleto) {

		List<Object> listaResultado = new ArrayList<>();
		boolean resultado = true;

		if (this.getDao() == null) {
			this.setDao(new GenericDAO());
		}

		this.getDao().abrirTransacao();

		try{
			for(int i=0; i<listaObjetos.size();i++){

				if(Reflection.getExisteChavePrimaria(listaObjetos.get(i))){
					if(persistirObjetoCompleto){
						listaResultado.add( salvarObjetoCompleto(listaObjetos.get(i)) );
					}else{
						listaResultado.add( salvar(listaObjetos.get(i)) );
					}

				}else{
					this.atualizar(listaObjetos.get(i));
				}
			}
		}catch(Exception e){

			resultado = false;
			e.printStackTrace();
			this.getDao().getSession().flush();			
		}

		return listaResultado;
	}

	public Boolean excluiObjetosDaLista(List<?> listaObjetos, Boolean excluirObjetoCompleto) {

		List<Object> listaResultado = new ArrayList<>();
		boolean resultado = true;
		this.setDao(new GenericDAO());
		this.getDao().abrirTransacao();
		
		try{
			for(int i=0; i<listaObjetos.size();i++){
				if(excluirObjetoCompleto){
					excluirObjetoCompleto( listaObjetos.get(i) );
				}else{
					deletar( listaObjetos.get(i) );
				}
			}
		}catch(Exception e){

			resultado = false;
			e.printStackTrace();
			this.getDao().getSession().flush();			
		}
		
		return resultado;
	}

	public boolean salvarObjetoLista(Object obj, List<?> listaPersistir, List<?> listaExcluir) {
		
		boolean resultado = true;
		this.setDao(new GenericDAO());
		this.getDao().abrirTransacao();
		try{

			if(Reflection.getExisteChavePrimaria(obj)){

				this.getDao().salvar(obj);

				for (Object item : listaPersistir) {

					Reflection.setValorChaveEstrangeira(obj, item);
					this.getDao().salvar(item);
				}

			}else{

				this.atualizar(obj);

				for (Object item : listaPersistir) {

					if(Reflection.getExisteChavePrimaria(item)){

						this.getDao().salvar(item);
					} else {

						this.atualizar(item);
					}
				}

				for (Object item : listaExcluir) {

					this.deletar(item);
				}				
			}

		}catch(Exception e){

			resultado = false;
			e.printStackTrace();
			this.getDao().getSession().flush();			
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	public Object salvarObjetoCompleto(Object objetoPersistir) {

		boolean resultado = true;

		try {

			if (this.getDao() == null) {
				this.setDao(new GenericDAO(true));
				//this.abrirTransacao();
			}

			this.salvar(objetoPersistir);		
			Field[] fields = objetoPersistir.getClass().getDeclaredFields();
			for (Field field : fields) {

				if ( field.getType().getName().equals("java.util.List") ) {

					if ( (field.isAnnotationPresent(ListaBusca.class)) && (field.getAnnotation(ListaBusca.class).persistir()) ) {

						String caminho = field.getAnnotation(ListaBusca.class).caminhoClasseJava();
						try {

							List<?> lista = (List<?>) Reflection.getValorCampo(objetoPersistir, field.getName());
							if ( (lista != null) && (lista.size() > 0) ) {

								this.validarObjetosLista(objetoPersistir, (List<Object>) lista);

								for (Object objetoFilho : lista) {

									Reflection.setValorChaveEstrangeira(objetoPersistir, objetoFilho);
									this.getDao().getSession().evict(objetoFilho);
									

									if (Reflection.verificarExisteLista(Class.forName(caminho)) || Reflection.verificarExisteObjetoBusca(Class.forName(caminho))) {
										salvarObjetoCompleto(objetoFilho);
									} else {
										this.getDao().salvar(objetoFilho);
									}
								}
							} else {
								this.validarObjetosLista(objetoPersistir, caminho);
							}
						} catch (Exception e) {

							e.printStackTrace();
						}
					}
				} else if (field.isAnnotationPresent(ObjetoBusca.class) && field.getAnnotation(ObjetoBusca.class).persistir()) {

					try {

							Object objetoFilho = Reflection.getValorCampo(objetoPersistir, field.getName());
							if(objetoFilho != null) {
								Reflection.setValorChaveEstrangeira(objetoPersistir, objetoFilho);
								this.getDao().getSession().evict(objetoFilho);
								String caminho = field.getAnnotation(ObjetoBusca.class).caminhoClasseJava();
	
								if (Reflection.verificarExisteLista(Class.forName(caminho))) {
									salvarObjetoCompleto(objetoFilho);
								} else {
									this.getDao().salvar(objetoFilho);
								}
							}
						/*
						String caminho = field.getAnnotation(ObjetoBusca.class).caminhoClasseJava();
						Object valorBuscar = Reflection.getValorCampo(objetoBusca, field.getAnnotation(ObjetoBusca.class).campoComparar());
						
						//LogPrimum.mensagem("caminho: " + caminho);
						//LogPrimum.mensagem("field.getAnnotation(ObjetoBusca.class).campoBuscar(): " + field.getAnnotation(ObjetoBusca.class).campoBuscar());
						//LogPrimum.mensagem("valorBuscar.toString(): " + valorBuscar.toString());
						Object o = this.getDao().getSession().createCriteria(Class.forName(caminho)).add(Restrictions.eq(field.getAnnotation(ObjetoBusca.class).campoBuscar(), valorBuscar)).uniqueResult();
						
						Reflection.setValorObject(objetoBusca, field.getName(), o, Class.forName(caminho));
						
						if (o != null) {
							if ( (Reflection.verificarExisteLista(Class.forName(caminho))) || (Reflection.verificarExisteObjetoBusca(Class.forName(caminho))) ) {
		
								buscarObjetoCompleto(o);
							}
						}
						*/
					} catch (HibernateException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			//this.fecharTransacao();
		} catch (SecurityException e) {
			resultado = false;
			e.printStackTrace();
		} catch (Exception e) {
			resultado = false;
			e.printStackTrace();
		}

		//this.validarNotificacao(objetoPersistir);
		return objetoPersistir;
	}
	
	public void validarNotificacao(Object objetoPersistir) {
		
		IInterceptador interceptador = (IInterceptador) HibernateSessionFactory.getConfiguration().getInterceptor();
		//IInterceptador interceptador = (IInterceptador) this.getDao().getHibernateSessionFactiory().getConfiguration().getInterceptor();
		interceptador.enviarNotificacao(objetoPersistir);
	}

	public Object buscarObjetoCompleto(Object objetoBuscar) throws Exception {
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		pesquisa.buscarObjetoCompleto(objetoBuscar);
		pesquisa.getDao().getSession().close();
		return objetoBuscar;
	}

	public List<?> buscarConjuntoRegistros(Integer cd_programa, List<Object> listaTabelasProcurar, Object valorBuscar) throws Exception {
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Object> lista = pesquisa.buscarConjuntoRegistrosProgramas(cd_programa, listaTabelasProcurar, valorBuscar);
		return lista;
	}

	public boolean excluirObjetoCompleto(Object objetoPersistir) throws Exception {

		boolean resultado = true;

		try {
			if (this.getDao() == null) {
				this.setDao(new GenericDAO());
			}
			//this.abrirTransacao();

			Field[] fields = objetoPersistir.getClass().getDeclaredFields();
			for (Field field : fields) {

				if ( field.getType().getName().equals("java.util.List") ) {

					if (field.isAnnotationPresent(ListaBusca.class) && (field.getAnnotation(ListaBusca.class).persistir())) {

						String caminho = field.getAnnotation(ListaBusca.class).caminhoClasseJava();
						try {

							List<?> lista = (List<?>) Reflection.getValorCampo(objetoPersistir, field.getName());
							if (lista != null) {

								for (Object objetoFilho : lista) {

									Reflection.setValorChaveEstrangeira(objetoPersistir, objetoFilho);

									if (Reflection.verificarExisteLista(Class.forName(caminho))) {

										excluirObjetoCompleto(objetoFilho);
									} else {

										this.getDao().delete(objetoFilho);
									}
								}
							} else {
								this.validarObjetosLista(objetoPersistir, caminho);
							}
						} catch (Exception e) {

							e.printStackTrace();
							throw new PrimumException(e.getMessage());
						}
					}
				} else if (field.isAnnotationPresent(ObjetoBusca.class) && (field.getAnnotation(ObjetoBusca.class).persistir())) {

					try {

						Object objetoFilho = Reflection.getValorCampo(objetoPersistir, field.getName());
						if(objetoFilho != null) {

							//Reflection.setValorChaveEstrangeira(objetoPersistir, objetoFilho);
							//this.getDao().getSession().evict(objetoFilho);
							String caminho = field.getAnnotation(ObjetoBusca.class).caminhoClasseJava();

							if (Reflection.verificarExisteLista(Class.forName(caminho))) {
								this.excluirObjetoCompleto(objetoFilho);
							} else {
								this.getDao().delete(objetoFilho);
							}
						}
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}

			this.deletar(objetoPersistir);
			//this.fecharTransacao();
		} catch (SecurityException e) {
			resultado = false;
			e.printStackTrace();
			throw new PrimumException(e.getMessage());
		} catch (Exception e) {
			resultado = false;
			e.printStackTrace();
			throw new PrimumException(e.getMessage());
		}

		return resultado;
	}
	
	/**
	 * Valida se existem itens que foram removidos da lista que ser� salva. 
	 * Caso haja item removido ent�o ir� excluir este antes de atualizar os demais.
	 * 
	 * @param objetoPai
	 * @param lista
	 */
	@SuppressWarnings("rawtypes")
	private void validarObjetosLista(Object objetoPai, List<Object> lista) {

		Integer[] valores = new Integer[lista.size()];
		Class classe = null;
		int i = 0;
		for (Object objetoFilho : lista) {

			classe = objetoFilho.getClass();

			try {
				valores[i] = (Integer) Reflection.getValorChavePrimaria(objetoFilho);
			} catch (Exception e) {

				e.printStackTrace();
			}
			i++;
		}

		if (valores.length > 0) {

			if (this.getDao() == null) {
				this.setDao(new GenericDAO());
			}

			//this.getDao().abrirTransacao();
			List<?> listaExcluir = null;

			try {

				String chaveProcura = null;

				if (objetoPai.getClass().equals(classe)) {
 
					if ( Reflection.verificarExisteChaveEstrangeira(classe) ) {
						chaveProcura = Reflection.getNomeChaveEstrangeira(classe);
					} else {
						chaveProcura = Reflection.getNomeChavePrimaria(objetoPai);
					}
				} else {
					chaveProcura = Reflection.getNomeChavePrimaria(objetoPai);
				}

				listaExcluir = this.getDao().getSession().createCriteria(classe)
				
				
				.add(Restrictions.eq(chaveProcura, Reflection.getValorCampo(objetoPai, Reflection.getNomeChavePrimaria(objetoPai))))
				
				
				.add(Restrictions.not(Restrictions.in(Reflection.getNomeChavePrimaria(classe.newInstance()), valores))).list();
				

				//listaExcluir = this.getDao().getSession().createCriteria(classe).add(Restrictions.eq(Reflection.getNomeChavePrimaria(objetoPai), Reflection.getValorChavePrimaria(objetoPai))).add(Restrictions.not(Restrictions.in(Reflection.getNomeChavePrimaria(classe.newInstance()), valores))).list();
				for (Object objeto : listaExcluir) {
					this.excluirObjetoCompleto(objeto);
					//this.deletar(objeto);
				}
			} catch (HibernateException e) {

				e.printStackTrace();
			} catch (InstantiationException e) {

				e.printStackTrace();
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * Valida se existem itens que foram removidos da lista que ser� salva. 
	 * Caso haja item removido ent�o ir� excluir este antes de atualizar os demais.
	 * 
	 * @param objetoPai
	 * @param caminhoObjeto
	 */
	@SuppressWarnings("rawtypes")
	private void validarObjetosLista(Object objetoPai, String caminhoObjeto) {

		if (this.getDao() == null) {
			this.setDao(new GenericDAO());
		}

		//this.getDao().abrirTransacao();
		List<?> listaExcluir = null;

		try {

			String chaveProcura = null;
			Class classe = Class.forName(caminhoObjeto);
			if (objetoPai.getClass().getName().equals(caminhoObjeto)) {

				if ( Reflection.verificarExisteChaveEstrangeira(classe) ) {
					chaveProcura = Reflection.getNomeChaveEstrangeira(classe);
				} else {
					chaveProcura = Reflection.getNomeChavePrimaria(objetoPai);
				}
			} else {
				chaveProcura = Reflection.getNomeChavePrimaria(objetoPai);
			}

			listaExcluir = this.getDao().getSession().createCriteria(Class.forName(caminhoObjeto)).add(Restrictions.eq(chaveProcura, Reflection.getValorChavePrimaria(objetoPai))).list();
			for (Object objeto : listaExcluir) {
				this.excluirObjetoCompleto(objeto);
				//this.deletar(objeto);
			}
		} catch (HibernateException e) {

			e.printStackTrace();
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public Boolean deletarTabela( String tabela ) {
		if (this.getDao() == null) {
			this.setDao(new GenericDAO());
		}
		
		Boolean resultado = true;
		try {
			this.getDao().getSession().createSQLQuery("DELETE FROM "+tabela+";").executeUpdate();
		} catch (Exception e) {
			resultado=false;
		}
		
		return resultado;
	}
	
	public Integer getValorChavePrimaria(Object bean, String tabela, String nomeCampo) {
		this.setDao(new GenericDAO());
		List<?> lista = (List<?>) this.getDao().getSession().createCriteria(bean.getClass()).addOrder(Order.asc(nomeCampo)).list();
		//this.getDao().getSession().createSQLQuery("SELECT * FROM "+tabela+";").setResultTransformer(Transformers.aliasToBean(bean.getClass())).list();
		if(lista!= null && lista.size() > 0){
			Integer resultado = null;
			try {
				resultado = (Integer) Reflection.getValorCampo(lista.get(lista.size()-1), nomeCampo);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			return resultado+1;
		}else{
			return 1;	
		}
	}
	
}