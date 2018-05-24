package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.report.ReportGenerate;

@Remote
public interface MaintenanceReportGenerateRemote extends AbstractDAS<ReportGenerate> {

	public boolean hasEntity(String networkId, Character networkType);
	public List<ReportGenerate> getEntities(Character state);
	public ReportGenerate getEntity(String networkId, Character networkType);
	
}
