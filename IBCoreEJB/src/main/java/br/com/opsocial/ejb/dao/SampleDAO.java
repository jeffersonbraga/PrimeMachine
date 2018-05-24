package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.Sample;

@Local
public interface SampleDAO extends AbstractDAO<Sample>{
	
	public Sample getByName(String name, Long idMonitoring);

	public List<Sample> listByMonitoring(Long idMonitoring);
}
