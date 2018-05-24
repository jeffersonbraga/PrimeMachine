package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.report.TwitterStatusReport;

@Remote
public interface MaintenanceTwitterStatusReportRemote extends AbstractDAS<TwitterStatusReport> {

	public TwitterStatusReport getEntityByProperty(String idProfile, String idStatus, String property) throws IllegalArgumentException;
	
	public Long getEntityValueByProperty(String idProfile, String idStatus, String property) throws IllegalArgumentException;	
}
