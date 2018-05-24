package br.com.opsocial.ejb.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityTransaction;

@Local
public interface GenericDAO {

	Long getMax(String field, String where);
	
	Long getMin(String field, String where);
	
	Long getSum(String field, String where);
	
    long getCount();

    long getCount(String where);

    List<Object> getSome(int[] intervalo); 

    Object getById(Object id);
    
    Object getById(Long id);

    Object getByActiveId(Long id);

    Object save(Object object) throws Exception;
    
    Object merge(Object object) throws Exception;

    Collection<Object> save(Collection<Object> t) throws Exception;

    void delete(Object object) throws IllegalArgumentException, IllegalStateException, Exception;
    
    void delete(Object object, boolean validateVersion) throws IllegalArgumentException, IllegalStateException, Exception;

    List<Object> getBySQL(String sql);

    public List<Object> getByFullSQL(String sql);

    public void flush();
    
    Object backup(Object object) throws Exception;

    Collection<Object> backup(Collection<Object> t) throws Exception;
    
    void close();
    
    EntityTransaction getTransaction();
    
    List<Object> getAll( );
     
    List<Object> getAllActive();

    Object getById(String id);

	void flush(Object post);
}
