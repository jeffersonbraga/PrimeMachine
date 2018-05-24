package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.report.ReportGenerate;

@Local
public interface ReportGenerateDAO extends AbstractDAO<ReportGenerate> {
	
	public boolean hasEntity(String networkId, Character networkType);
	public List<ReportGenerate> getEntities(Character state);
	public ReportGenerate getEntity(String networkId, Character networkType);

}
