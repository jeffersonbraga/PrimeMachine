package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.application.Anniversarie;

@Remote
public interface MaintenanceAnniversarieRemote extends AbstractDAS<Anniversarie> {
	
	public List<Anniversarie> listByDate(Date initDate, Date endDate);

}
