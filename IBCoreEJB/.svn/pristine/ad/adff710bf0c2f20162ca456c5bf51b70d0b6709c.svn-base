package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.Sample;

@Remote
public interface MaintenanceSampleRemote extends AbstractDAS<Sample>{
	
	public Sample getByName(String name, Long idMonitoring);

	public List<Sample> listByMonitoring(Long idMonitoring);

}
