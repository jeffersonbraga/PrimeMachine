package br.com.opsocial.ejb.das;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.persistence.EntityTransaction;

import br.com.opsocial.ejb.dao.GenericDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.application.Account;	

@Remote
public interface MaintenanceGenericRemote {

    long getCount();

    List<Object> getAll(); 
    
    List<Object> getSome(int[] intervalo);

    Object getById(Long id);

    Object getByActiveId(Long id);
    
    Object getById(String id);
	
    Object getById(Object id);

    Object save(Object object) throws IllegalArgumentException,Exception;
    
    Object merge(Object object) throws IllegalArgumentException,Exception;

    Collection<Object> save(ArrayList<Object> t) throws Exception;

    void delete(Object object) throws IllegalArgumentException, IllegalStateException, Exception;
    
    void delete(Object object, boolean validateVersion) throws IllegalArgumentException, IllegalStateException, Exception;

    List<Object> getBySQL(String sql);

    public List<Object> getByFullSQL(String sql);
    
    List<Object> getAllActive();
     
    Object backup(Object object) throws IllegalArgumentException,Exception;

	Collection<Object> backup(ArrayList<Object> t) throws Exception;

	void close();
	
	EntityTransaction getTransaction();
	
	public void setDao(GenericDAO g);
	
	public GenericDAO getDao();

	public void setIdUserSession(Long idUserSession);
	
	public Long getIdUserSession();
}
