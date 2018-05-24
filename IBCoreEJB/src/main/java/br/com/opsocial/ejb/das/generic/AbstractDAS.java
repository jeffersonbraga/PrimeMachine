package br.com.opsocial.ejb.das.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityTransaction;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.generic.AbstractEntityImpl;

public interface AbstractDAS<T extends AbstractEntityImpl> {
	 
    long getCount();

    List<T> getAll(); 
    
    List<T> getSome(int[] intervalo);

    T getById(Long id);

    T getByActiveId(Long id);
    
	T getById(String id);
	
    T getById(Object id);

    T save(T object) throws IllegalArgumentException,Exception;
    
    T merge(T object) throws IllegalArgumentException,Exception;

    Collection<T> save(ArrayList<T> t) throws Exception;

    void delete(T object) throws IllegalArgumentException, IllegalStateException, Exception;
    
    void delete(T object, boolean validateVersion) throws IllegalArgumentException, IllegalStateException, Exception;

    List<T> getBySQL(String sql);

    public List<T> getByFullSQL(String sql);
    
    List<T> getAllActive();
     
    T backup(T object) throws IllegalArgumentException,Exception;

	Collection<T> backup(ArrayList<T> t) throws Exception;

	void close();
	
	EntityTransaction getTransaction();
	
	public void setDao(AbstractDAO<T> dao);
	
	public AbstractDAO<T> getDao();

	public void setIdUserSession(Long idUserSession);
	
	public Long getIdUserSession();
}
