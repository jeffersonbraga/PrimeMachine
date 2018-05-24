package br.com.opsocial.ejb.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.das.MaintenanceOpLogRemote;
import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.OpLog;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.facebook.FacebookPost;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.google.GooglePlusPost;
import br.com.opsocial.ejb.entity.instagram.InstagramPost;
import br.com.opsocial.ejb.entity.twitter.TwitterPost;
import br.com.opsocial.ejb.entity.youtube.YoutubePost;

@Stateless
public class GenericDAOImpl implements GenericDAO {

	public static EntityManager emEJB;

	@PersistenceContext(unitName = "opsocial")
	public EntityManager em;
	public static EntityManagerFactory emf;
	
	protected String sql;
	protected Query query;
	
	@PostLoad
	@PostPersist
	@PostRemove
	@PostUpdate
	public void preexecute() {		
		em.getEntityManagerFactory().getCache().evictAll();
	}

	private Class getDomainClass() {
		return (Class<Object>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public Long getMin(String field, String where) {
		sql = "select min(x."+field+") from "+getDomainClass().getSimpleName()+
				" x where " + where.trim();

		try {
			return (Long) em.createQuery(sql).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Long getMax(String field, String where) {
		sql = "select max(x."+field+") from "+getDomainClass().getSimpleName()+
				" x where " + where.trim();

		try {
			return (Long) em.createQuery(sql).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Long getSum(String field, String where) {
		Long sum;
		
		sql = "select sum(x."+field+") from "+getDomainClass().getSimpleName()
					+ " x where " + where.trim();

		sum = (Long) em.createQuery(sql).getSingleResult();

		if(sum == null) {
			sum = 0L;
		}
		
		return sum;
	}

	@Override
	public long getCount() {
		sql = "select count(x) from " + getDomainClass().getSimpleName() + " x";

		try {
			return (Long) em.createQuery(sql).getSingleResult();
		} catch (Exception e) {
			return 0L;
		}
	}

	@Override
	public long getCount(String where) {
		sql = "select count(x) from " + getDomainClass().getSimpleName()
				+ " x where x." + where.trim();

		try {
			return (Long) em.createQuery(sql).getSingleResult();
		} catch (Exception e) {
			return 0L;
		}
	}

	@Override
	public List<Object> getSome(int[] interval) {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(getDomainClass()));
		Query q = em.createQuery(cq);
		q.setMaxResults(interval[1] - interval[0]);
		q.setFirstResult(interval[0]);

		return q.getResultList();
	}
	
	@Override
	public Object getById(Object id) {
		return (Object) em.find(getDomainClass(), id);
	}

	@Override
	public Object getById(Long id) {
		return (Object)em.find(getDomainClass(), id);
	}
	
	@Override
	public Object getById(String id) {
		return (Object)em.find(getDomainClass(), id);
	}

	@Override
	public Object getByActiveId(Long id) {
		Object t = (Object) em.find(getDomainClass(), id);

		return true;
	}
	
	@Override
	public Object merge(Object object) throws Exception {
		/*Object objTmp = getById(object.getId());
		try {
			validateVersion(objTmp, object);
		} catch (IllegalStateException ex) {
			throw new OptimisticLockException();
		}
	*/
		em.merge(object);
		//object = getById(object.getId());
		
		
		this.flush();
		
		return object;
	}

	@Override
	public Object save(Object object) throws Exception {
		
		try {
			
			//if (object.isPersistent()) {
				/*Object objTmp = getById(object.getId());
				try {
					validateVersion(objTmp, object);
				} catch (IllegalStateException ex) {
					throw new OptimisticLockException();
				}
			*/
				em.merge(object);
				
				this.flush();
				
				this.saveLog(object, "update");
			
			/*} else {
				
				em.persist(object);

				this.flush();
				
				this.saveLog(object, "insert");
			}*/
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		return object;
	}

	@Override
	public Object backup(Object object) throws Exception {
		try {
			em.persist(object);
			this.flush();
		} catch (Exception e) {
			throw new Exception(e);
		}
		return object;
	}

	@Override
	public void flush() {
		em.flush();				
	}
	
	@Override
	public void flush(Object post) {
		
		try {
			em.flush();			
		} catch (Exception e) {
			if(
				post instanceof FacebookPost ||
				post instanceof TwitterPost ||
				post instanceof GooglePlusPost ||
				post instanceof YoutubePost ||
				post instanceof InstagramPost) { 
			
			//----- System.out.println("Encontrão: " + post);
			}
		}
	}

	public void validateVersion(Object actual, Object fresh) throws IllegalStateException {
		/*if (actual == null) {
			throw getRemovalException(fresh.getId());
		}
		if (!validateVersion(actual.getVersion(), fresh.getVersion())) {
			throw getUpdateException(fresh.getId());
		}*/
	}

	public boolean validateVersion(Long actualVersion, Long freshVersion) {
		if (actualVersion == null || freshVersion == null) {
			return true;
		}
		return actualVersion.longValue() == freshVersion.longValue();
	}

	public IllegalStateException getRemovalException(long id) {
		return new IllegalStateException(
				"O registro com o cÃ³digo "
						+ id
						+ " foi removido da base de dados apÃ³s a ultima busca!\nFaÃ§a a busca novamente para atualizar os registros.",
						null);
	}

	public IllegalStateException getUpdateException(long id) {
		return new IllegalStateException(
				"O registro com o cÃ³digo "
						+ id
						+ " foi atualizado na base de dados desde a ultima busca!\nFaÃ§a a busca novamente para atualizar os registros.",
						null);
	}

	@Override
	public void delete(Object object) throws IllegalArgumentException,
	IllegalStateException, Exception {
		try {
			/*Object objTmp = getById(object.getId());
			try {
				validateVersion(objTmp, object);
			} catch (IllegalStateException ex) {
				throw new Exception(ex.getMessage(), ex);
			}*/
			try {
				
				object = em.merge(object);
				em.remove(object);
				
				this.saveLog(object, "delete");

				this.flush();
			} catch (IllegalStateException ex) {				
				throw new Exception(ex.getMessage(), ex);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public void delete(Object object, boolean validateVersion) throws IllegalArgumentException,
	IllegalStateException, Exception {
		try {
			try {
				
				object = em.merge(object);
				em.remove(object);
				
				this.saveLog(object, "delete");
				
				this.flush();
			} catch (IllegalStateException ex) {				
				throw new Exception(ex.getMessage(), ex);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<Object> getBySQL(String sql) {
		String where = "";
		if (sql != null && !sql.isEmpty()) {
			where = " where " + sql;
		}
		return (List<Object>) em.createQuery(
				"select c from " + getDomainClass().getSimpleName() + " c "
						+ where).getResultList();
	}

	@Override
	public List<Object> getByFullSQL(String sql) {
		return em.createQuery(sql).getResultList();
	}

	@Override
	public Collection<Object> save(Collection<Object> t) throws Exception {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Collection<Object> backup(Collection<Object> t) throws Exception {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void close() {
		em.close();
	}
	@Override
	
	public List<Object> getAllActive() {
		sql = "select c  from " + getDomainClass().getSimpleName() + "  c where c.active = 'T' ";
		return (List<Object>) em.createQuery(sql, getDomainClass())
				.getResultList();
	}

	@Override
	public List<Object> getAll( ) {
		sql = "select c  from " + getDomainClass().getSimpleName() + "  c ";		
		return (List<Object>) em.createQuery(sql, getDomainClass())
				.getResultList();
	}

	@Override
	public EntityTransaction getTransaction() {
		return em.getTransaction();
	}
	
	public void saveLog(Object object, String operation){
		if ((object.getClass().getSimpleName().equals("User") || object.getClass().getSimpleName().equals("Post") || object.getClass().getSimpleName().equals("Schedule"))){
			try {
				
				AbstractDAS remote = (AbstractDAS) new InitialContext().lookup("global/OpSocialBack/Maintenance" + object.getClass().getSimpleName() + "Bean!br.com.opsocial.ejb.das.Maintenance" + object.getClass().getSimpleName() + "Remote");
				Long idUser = remote.getIdUserSession();
				
				if(idUser != null){					
					MaintenanceOpLogRemote remoteLog = (MaintenanceOpLogRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceOpLogBean!br.com.opsocial.ejb.das.MaintenanceOpLogRemote");
					
					OpLog opLog = new OpLog();
					
					opLog.setEntity(object.getClass().getSimpleName());
					opLog.setOperation(operation);
					opLog.setIdUser(idUser);
					opLog.setDate(new Date());
					opLog.setEntityKey(((Persistent) object).getId());
					
					remoteLog.save(opLog);
				}
				
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}