package br.com.opsocial.ejb.das;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityTransaction;

import br.com.opsocial.ejb.dao.AccountDAO;
import br.com.opsocial.ejb.dao.GenericDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.User;

@Stateless
public class MaintenanceGenericBean implements MaintenanceGenericRemote {

	private static final long serialVersionUID = 1L;		

	@EJB
	GenericDAO dao;  
	
	@Override
	public void setDao(GenericDAO dao) {
		this.dao = (GenericDAO) dao;
	}

	@Override
	public GenericDAO getDao() {
		return dao;
	}
    
	protected User user;
	
	protected Long idUserSession;

    private Class getDomainClass() {
        return (Class<Object>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void delete(Object object) throws IllegalArgumentException, IllegalStateException, Exception {
        try {
            this.getDao().delete(object);
        } catch (IllegalStateException ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }
    
    @Override
    public void delete(Object object, boolean validateVersion) throws IllegalArgumentException, IllegalStateException, Exception {
        try {
            getDao().delete(object, validateVersion);
        } catch (IllegalStateException ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    @Override
    public Object getById(Long id) {
        return (Object) getDao().getById(id);
    }
    
    @Override
    public Object getById(String id) {
        return (Object) getDao().getById(id);
    }
    
    @Override
    public Object getById(Object id) {
        return (Object) getDao().getById(id);
    }

    @Override
    public Object getByActiveId(Long id) {
        return (Object) getDao().getByActiveId(id);
    }

    @Override
    public long getCount() {
        return getDao().getCount();
    }

    @Override
    public List<Object> getSome(int[] intervalo) {
        return getDao().getSome(intervalo);
    }

    @Override
    public Object save(Object object) throws IllegalArgumentException,Exception {
    	return (Object) getDao().save(object);
    }
    
    @Override
    public Object merge(Object object) throws IllegalArgumentException,Exception {
    	return (Object) getDao().merge(object);
    }

    @Override
    public Collection<Object> save(ArrayList<Object> t) throws Exception {
        return getDao().save(t);
    }
    
    @Override
    public Object backup(Object object) throws IllegalArgumentException,Exception {
    	return (Object) getDao().backup(object);
    }

    @Override
    public Collection<Object> backup(ArrayList<Object> t) throws Exception {
        return getDao().backup(t);
    }

    @Override
    public List<Object> getBySQL(String sql) {
        return getDao().getBySQL(sql);
    }

    @Override
    public List<Object> getByFullSQL(String sql) {
        return getDao().getByFullSQL(sql);
    }

    @Override
    public void close() {
    	getDao().close();
    }

    @Override
    public List<Object> getAllActive() {
    	return getDao().getAllActive();
    }

    @Override
    public List<Object> getAll( ) {
        return getDao().getAll();
    }

    @Override
    public EntityTransaction getTransaction() {
    	return getDao().getTransaction();
    }

	@Override
	public void setIdUserSession(Long idUserSession) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getIdUserSession() {
		// TODO Auto-generated method stub
		return null;
	}
}