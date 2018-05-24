package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.report.ReportControl;

@Remote
public interface MaintenanceReportControlRemote extends AbstractDAS<ReportControl> {

	public ReportControl getEntityByNetwork(String networkId, Character networkType, Long date);
	public ReportControl getEntityByNetwork(String networkId, Character networkType, String type, Long date);
	public Long getMaxDate(String networkId, Character networkType);
	public Long getMinDate(String networkId, Character networkType);
	public Long getMaxDate(String networkId, Character networkType, String type);
	public Long getMinDate(String networkId, Character networkType, String type);
	public boolean hasEntity(String networkId, Character networkType, String type, Long date);
	public boolean hasReportTwitterForProfile(String networkId, Character networkType);
	public Long getDate(String networkId, Character networkType, String type);
	public List<Long> getDates(String networkId, Character networkType, String type);
	public ReportControl getEntity(String networkId, Character networkType, String type);
	
	public Long getEntityCount(String networkId, Character networkType);
	public Long getFirstDate(String networkId, Character networkType);
}
