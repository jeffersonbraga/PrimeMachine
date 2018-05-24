package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.report.FaceReportLocation;
import br.com.opsocial.ejb.entity.report.ReportFacebookLocation;

@Local
public interface FaceReportLocationDAO extends AbstractDAO<FaceReportLocation> {
	
	public FaceReportLocation getEntity(String pageId, String location, String fromGroup);
	public List<ReportFacebookLocation> getReportFacebookCityImp(String pageId, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
	public List<ReportFacebookLocation> getReportFacebookCityImp(String pageId, Integer offset, Integer limit);
	public List<ReportFacebookLocation> getReportFacebookCityFan(String pageId, Integer offset, Integer limit);
	
	
	public List<ReportFacebookLocation> getReportFacebookCountryImp(String pageId, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
	public List<ReportFacebookLocation> getReportFacebookCountryImp(String pageId, Integer offset, Integer limit);
	public List<ReportFacebookLocation> getReportFacebookCountryFan(String pageId, Integer offset, Integer limit);
	
	public List<ReportFacebookLocation> getExternalReferences(String pageId, Long dateFrom, Long dateUntil, Integer offset, Integer limit);

	public boolean deleteFaceReportsLocations(String pageId);
}
