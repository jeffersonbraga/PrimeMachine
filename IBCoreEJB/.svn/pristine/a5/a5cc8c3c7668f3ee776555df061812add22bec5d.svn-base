package br.com.opsocial.ejb.dao;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.report.TwitterStatusReport;

@Local
public interface TwitterStatusReportDAO extends AbstractDAO<TwitterStatusReport>{

	TwitterStatusReport getEntityByProperty(String where);
	Long getEntityValueByProperty(String idProfile, String idStatus, String property);
	public TwitterStatusReport getEntityByProperty(String idProfile, String idStatus, String property);

}
