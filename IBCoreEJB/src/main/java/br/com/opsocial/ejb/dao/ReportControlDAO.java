package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.report.ReportControl;

@Local
public interface ReportControlDAO extends AbstractDAO<ReportControl> {

	public ReportControl getEntityByNetwork(String networkId, Character networkType, Long date);
	
	public ReportControl getEntityByNetwork(String networkId, Character networkType, String type, Long date);
	
	public boolean hasEntity(String networkId, Character networkType, String type, Long date);
	
	public boolean hasReportTwitterForProfile(String networkId, Character networkType);
	
	public Long getDate(String networkId, Character networkType, String type);
	
	public List<Long> getDates(String networkId, Character networkType, String type);
	
	public ReportControl getEntity(String networkId, Character networkType, String type);
	
	public Long getMaxDate(String networkId, Character networkType);

	public Long getMinDate(String networkId, Character networkType);

	public Long getMaxDate(String networkId, Character networkType, String type);

	public Long getMinDate(String networkId, Character networkType, String type);
	
	public Long getEntityCount(String networkId, Character networkType);
	
	public Long getFirstDate(String networkId, Character networkType);
}
