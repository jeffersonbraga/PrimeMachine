package br.com.opsocial.ejb.dao.generic;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

import br.com.opsocial.ejb.das.MaintenanceOpLogRemote;
import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.application.OpLog;
import br.com.opsocial.ejb.entity.facebook.FacebookPost;
import br.com.opsocial.ejb.entity.generic.AbstractEntityImpl;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.google.GooglePlusPost;
import br.com.opsocial.ejb.entity.instagram.InstagramPost;
import br.com.opsocial.ejb.entity.twitter.TwitterPost;
import br.com.opsocial.ejb.entity.youtube.YoutubePost;

public class AbstractDAOImpl<T extends AbstractEntityImpl> implements AbstractDAO<T> {
	
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
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
	public List<T> getSome(int[] interval) {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(getDomainClass()));
		Query q = em.createQuery(cq);
		q.setMaxResults(interval[1] - interval[0]);
		q.setFirstResult(interval[0]);

		return q.getResultList();
	}
	
	@Override
	public T getById(Object id) {
		return (T) em.find(getDomainClass(), id);
	}

	@Override
	public T getById(Long id) {
		return (T) em.find(getDomainClass(), id);
	}
	
	@Override
	public T getById(String id) {
		return (T) em.find(getDomainClass(), id);
	}

	@Override
	public T getByActiveId(Long id) {
		T t = (T) em.find(getDomainClass(), id);

		return t != null && t.isActive() ? t : null;
	}
	
	@Override
	public T merge(T object) throws Exception {
		T objTmp = getById(object.getId());
		try {
			validateVersion(objTmp, object);
		} catch (IllegalStateException ex) {
			throw new OptimisticLockException();
		}
	
		em.merge(object);
		object = getById(object.getId());
		
		
		this.flush();
		
		return object;
	}

	@Override
	public T save(T object) throws Exception {
		
		try {
			
			if (object.isPersistent()) {
				T objTmp = getById(object.getId());
				try {
					validateVersion(objTmp, object);
				} catch (IllegalStateException ex) {
					throw new OptimisticLockException();
				}
			
				em.merge(object);
				
				this.flush();
				
				this.saveLog(object, "update");
			
			} else {
				
				em.persist(object);

				this.flush();
				
				this.saveLog(object, "insert");
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		return object;
	}

	@Override
	public T backup(T object) throws Exception {
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

	public void validateVersion(T actual, T fresh) throws IllegalStateException {
		if (actual == null) {
			throw getRemovalException(fresh.getId());
		}
		if (!validateVersion(actual.getVersion(), fresh.getVersion())) {
			throw getUpdateException(fresh.getId());
		}
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
	public void delete(T object) throws IllegalArgumentException,
	IllegalStateException, Exception {
		try {
			T objTmp = getById(object.getId());
			try {
				validateVersion(objTmp, object);
			} catch (IllegalStateException ex) {
				throw new Exception(ex.getMessage(), ex);
			}
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
	
	public void delete(T object, boolean validateVersion) throws IllegalArgumentException,
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
	public List<T> getBySQL(String sql) {
		String where = "";
		if (sql != null && !sql.isEmpty()) {
			where = " where " + sql;
		}
		return (List<T>) em.createQuery(
				"select c from " + getDomainClass().getSimpleName() + " c "
						+ where).getResultList();
	}

	@Override
	public List<T> getByFullSQL(String sql) {
		return em.createQuery(sql).getResultList();
	}

	@Override
	public Collection<T> save(Collection<T> t) throws Exception {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Collection<T> backup(Collection<T> t) throws Exception {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void close() {
		em.close();
	}
	@Override
	
	public List<T> getAllActive() {
		sql = "select c  from " + getDomainClass().getSimpleName() + "  c where c.active = 'T' ";
		return (List<T>) em.createQuery(sql, getDomainClass())
				.getResultList();
	}

	@Override
	public List<T> getAll( ) {
		sql = "select c  from " + getDomainClass().getSimpleName() + "  c ";		
		return (List<T>) em.createQuery(sql, getDomainClass())
				.getResultList();
	}

	@Override
	public EntityTransaction getTransaction() {
		return em.getTransaction();
	}
	
	public void saveLog(T object, String operation){
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