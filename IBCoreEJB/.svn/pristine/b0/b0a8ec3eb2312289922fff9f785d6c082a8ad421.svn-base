package br.com.opsocial.ejb.dao.generic;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityTransaction;

import br.com.opsocial.ejb.entity.generic.AbstractEntityImpl;

public interface AbstractDAO<T extends AbstractEntityImpl> {

	Long getMax(String field, String where);
	
	Long getMin(String field, String where);
	
	Long getSum(String field, String where);
	
    long getCount();

    long getCount(String where);

    List<T> getSome(int[] intervalo); 

    T getById(Object id);
    
    T getById(Long id);

    T getByActiveId(Long id);

    T save(T object) throws Exception;
    
    T merge(T object) throws Exception;

    Collection<T> save(Collection<T> t) throws Exception;

    void delete(T object) throws IllegalArgumentException, IllegalStateException, Exception;
    
    void delete(T object, boolean validateVersion) throws IllegalArgumentException, IllegalStateException, Exception;

    List<T> getBySQL(String sql);

    public List<T> getByFullSQL(String sql);

    public void flush();
    
    T backup(T object) throws Exception;

    Collection<T> backup(Collection<T> t) throws Exception;
    
    void close();
    
    EntityTransaction getTransaction();
    
    List<T> getAll( );
     
    List<T> getAllActive();

	T getById(String id);

	void flush(Object post);	
}
