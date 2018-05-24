package br.com.primum.hibernate;

import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.Transformers;

import br.com.primum.interfaces.IInterceptador;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.reflection.Reflection;
public class GenericDAO {

	Session session;
	public GenericDAO() {
		session = HibernateSessionFactory.getSession();
		try {
			//session.flush();
			session.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GenericDAO(boolean novaSession) {

		if (novaSession) {
			session = HibernateSessionFactory.getNovaSession();
		} else {
			session = HibernateSessionFactory.getSession();
		}

		try {
			//session.flush();
			session.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GenericDAO(String nomeBase) {

		session = HibernateSessionFactory.getSession();
		try {
			//session.flush();
			session.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void salvar(Object obj) {

		try {

			this.getSession().getTransaction().begin();

			//IInterceptador interceptador = (IInterceptador) HibernateSessionFactory.getConfiguration().getInterceptor();
			//interceptador.setTransactionID(this.getSession().getTransaction().toString());

			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {

				if ( (field.isAnnotationPresent(ObjetoBusca.class)) && (field.getAnnotation(ObjetoBusca.class).notificador()) ) {

					//IInterceptador interceptador = (IInterceptador) HibernateSessionFactory.getConfiguration().getInterceptor();
					//interceptador.setObjetoInterceptado(obj);
				}
			}

			if(Reflection.getExisteChavePrimaria(obj)){

				this.setValorChavePrimaria(obj);

//				if (obj instanceof Documento && ((Documento) obj).getDs_copia_controlada() != null) {
//					String nome = ((Documento) obj).getDs_copia_controlada().substring( (((Documento) obj).getDs_copia_controlada().lastIndexOf("/") + 1), (((Documento) obj).getDs_copia_controlada().length()));
//					((Documento) obj).setDs_copia_controlada(ContextoAplicacao.CAMINHO_APLICACAO + "copia_controlada/" + ((Documento) obj).getCd_documento() + "/" + nome);
//				}

				this.getSession().evict(obj);
				this.getSession().save(obj);
			} else {
//				if (obj instanceof Documento && ((Documento) obj).getDs_copia_controlada() != null) {
//					String nome = ((Documento) obj).getDs_copia_controlada().substring( (((Documento) obj).getDs_copia_controlada().lastIndexOf("/") + 1), (((Documento) obj).getDs_copia_controlada().length()));
//					((Documento) obj).setDs_copia_controlada(ContextoAplicacao.CAMINHO_APLICACAO + "copia_controlada/" + ((Documento) obj).getCd_documento() + "/" + nome);
//				}

				this.getSession().evict(obj);
				this.getSession().update(obj);
			}

			this.getSession().getTransaction().commit();
		} catch (Exception e) {	
			this.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}

	public void setValorChavePrimaria(Object obj) {
		try {

			String nome = Reflection.getNomeChavePrimaria(obj);
			Object valor = this.getSession().createCriteria(obj.getClass()).setProjection(Projections.projectionList().add(Projections.max(nome))).uniqueResult();

			if (valor == null) {
				valor = 1;
			} else {
				valor = Integer.parseInt(valor.toString()) + 1;
			}
			Reflection.setValorChavePrimaria(obj, valor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(Object persistentInstance) throws Exception {
		
		try {
			this.getSession().getTransaction().begin();
			this.getSession().delete(persistentInstance);
			this.getSession().getTransaction().commit();
			
		} catch (ConstraintViolationException ce) {
			this.getSession().getTransaction().rollback();
			this.getSession().flush();
			throw ce;
		} catch (RuntimeException re) {
			this.getSession().getTransaction().rollback();
			this.getSession().flush();
			throw re;
		}
	}

	public void atualizar(Object obj) throws Exception {

		IInterceptador interceptador = null;
		try {
			if (HibernateSessionFactory.getConfiguration().getInterceptor() != null && HibernateSessionFactory.getConfiguration().getInterceptor() instanceof IInterceptador) {
				interceptador = (IInterceptador) HibernateSessionFactory.getConfiguration().getInterceptor();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try{
			this.getSession().getTransaction().begin();

			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {

				if ( (field.isAnnotationPresent(ObjetoBusca.class)) && (field.getAnnotation(ObjetoBusca.class).notificador()) ) {

					//IInterceptador interceptador = (IInterceptador) HibernateSessionFactory.getConfiguration().getInterceptor();
					if (interceptador != null) {
						interceptador.setObjetoInterceptado(obj);
					}
				}
			}

			this.getSession().evict(obj);
			//this.getSession().merge(obj);//NÃO TRAZ OBJETO COMPLETO - TRAZ PREVIOUSSTATE
			this.getSession().update(obj); //TRAZ OBJETO COMPLETO - NÃO TRAZ PREVIOUSSTATE
			//this.getSession().save(obj);
			this.getSession().getTransaction().commit();
		}catch(RuntimeException re){
			this.getSession().flush();
			throw re;
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}

	public Object findById(Object obj, Integer id) throws Exception {

		try {

			Object instance = (Object) this.getSession().get(
					obj.getClass().getCanonicalName(), id);
			return instance;
		} catch (RuntimeException re) {
			this.getSession().flush();
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> findAll(Object obj) throws Exception{

		try {

			/*String queryString = "from " + obj.getClass().getCanonicalName();
			Query queryObject = getSession().createQuery(queryString);			
			List<Object> lst = (List<Object>) queryObject.list();*/
			this.getSession().clear();
			this.getSession().flush();
			List<Object> lst = this.getSession().createCriteria(obj.getClass()).list();			
			return lst;
		} catch (RuntimeException re) {
			this.getSession().flush();
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> findBySql(String sql, Object obj) throws Exception {
		
		try{
			
			List<Object> lst = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(obj.getClass())).list();
			return lst;
		}catch(Exception e){
			this.getSession().flush();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object findBySqlObject(String sql, Object obj) throws Exception {
		
		try{
			
			List<Object> lst = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(obj.getClass())).list();
			if(lst.size() > 0){
				
				return lst.get(0);
			}else{
				
				return null;
			}
		}catch(Exception e){
			this.getSession().flush();
			throw e;
		}
	}

	public void abrirTransacao() {
		this.getSession().beginTransaction();
	}

	public void commitTransacao() {
		this.getSession().getTransaction().commit();
	}

	public void rollbackTransacao() {
		this.getSession().getTransaction().rollback();
	}

	private void fecharTransacao() {

		try {

			if (this.getSession().getTransaction().isActive()) {

				this.commitTransacao();

				this.getSession().flush();
				this.getSession().close();				
			}
		} catch (Exception e) {

			this.rollbackTransacao();
			e.printStackTrace();
		}
	}

	public void fecharConexao() {

		if (this.getSession().isOpen()) {

			this.fecharTransacao();
		}
	}  
	
	@SuppressWarnings("unchecked")
	public List<Object> findAllFiltro(Object obj, Criterion restricao) throws Exception{
		try {

			/*String queryString = "from " + obj.getClass().getCanonicalName();
			Query queryObject = getSession().createQuery(queryString);			
			List<Object> lst = (List<Object>) queryObject.list();*/
			this.getSession().clear();
			this.getSession().flush();
			List<Object> lst = this.getSession().createCriteria(obj.getClass()).add(restricao)
					.list();			
			return lst;
		} catch (RuntimeException re) {
			this.getSession().flush();
			throw re;
		}
	}
}