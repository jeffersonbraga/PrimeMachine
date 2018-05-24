package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.report.FaceReportLocation;
import br.com.opsocial.ejb.entity.report.ReportFacebookLocation;
import br.com.opsocial.ejb.utils.reports.UtilReports;

@Stateless
public class FaceReportLocationDAOImpl extends AbstractDAOImpl<FaceReportLocation> implements FaceReportLocationDAO {

	@Override
	public FaceReportLocation getEntity(String pageId, String location, String fromGroup) {
		
		sql = "SELECT frl FROM FaceReportLocation frl WHERE frl.pageId = :pageId " +
				"and frl.location = :location " +
				"and frl.fromGroup = :fromGroup";
		
		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("location", location);
		query.setParameter("fromGroup", fromGroup);
		
		try {
			return (FaceReportLocation) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public List<ReportFacebookLocation> getReportFacebookCountryImp(String pageId, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
	
		List<ReportFacebookLocation> reportFacebookCountries = new ArrayList<ReportFacebookLocation>();
		
		sql = "SELECT frl.location, sum(frl.value) as sumvalue FROM FaceReportLocation frl WHERE frl.pageId = :pageId " +
				"AND frl.fromGroup = :fromGroup AND (frl.date >= :dateFrom AND frl.date <= :dateUntil) " +
				"GROUP BY frl.location ORDER BY sumvalue DESC";
		
		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("fromGroup", UtilReports.GROUP_IMP_COUNTRY);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		List<Object[]> results = query.getResultList();
		
		for (Object[] result : results) {
			
			ReportFacebookLocation reportFacebookCountry = new ReportFacebookLocation();
			reportFacebookCountry.setLocation((String) result[0]);
			reportFacebookCountry.setValue(((Number) result[1]).longValue());
		    
			reportFacebookCountries.add(reportFacebookCountry);
		}
		
		return reportFacebookCountries;
	}

	@Override
	public List<ReportFacebookLocation> getReportFacebookCityImp(String pageId, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
	
		List<ReportFacebookLocation> reportFacebookCities = new ArrayList<ReportFacebookLocation>();
		
		sql = "SELECT frl.location, sum(frl.value) as sumvalue FROM FaceReportLocation frl WHERE frl.pageId = :pageId " +
				"AND frl.fromGroup = :fromGroup AND (frl.date >= :dateFrom AND frl.date <= :dateUntil) " +
				"GROUP BY frl.location ORDER BY sumvalue DESC";
		
		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("fromGroup", UtilReports.GROUP_IMP_CITY);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		List<Object[]> results = query.getResultList();
		
		for (Object[] result : results) {
			
			ReportFacebookLocation reportFacebookCity = new ReportFacebookLocation();
			reportFacebookCity.setLocation((String) result[0]);
			reportFacebookCity.setValue(((Number) result[1]).longValue());
		    
			reportFacebookCities.add(reportFacebookCity);
		}
		
		return reportFacebookCities;
	}
	
	@Override
	public List<ReportFacebookLocation> getReportFacebookCountryFan(String pageId, Integer offset, Integer limit) {
		
		List<ReportFacebookLocation> reportFacebookCountries = new ArrayList<ReportFacebookLocation>();
		
		sql = "SELECT frl.location, frl.value FROM FaceReportLocation frl WHERE frl.pageId = :pageId " +
				"AND frl.fromGroup = :fromGroup " +
				"GROUP BY frl.location, frl.value ORDER BY frl.value DESC";
		
		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("fromGroup", UtilReports.GROUP_FAN_COUNTRY);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		List<Object[]> results = query.getResultList();
		
		for (Object[] result : results) {
			
			ReportFacebookLocation reportFacebookCountry = new ReportFacebookLocation();
			reportFacebookCountry.setLocation((String) result[0]);
			reportFacebookCountry.setValue(((Number) result[1]).longValue());
		    
			reportFacebookCountries.add(reportFacebookCountry);
		}
		
		return reportFacebookCountries;
	}

	@Override
	public List<ReportFacebookLocation> getReportFacebookCityFan(String pageId, Integer offset, Integer limit) {
		
		List<ReportFacebookLocation> reportFacebookCities = new ArrayList<ReportFacebookLocation>();
		
		sql = "SELECT frl.location, frl.value FROM FaceReportLocation frl WHERE frl.pageId = :pageId " +
				"AND frl.fromGroup = :fromGroup " + 
				"GROUP BY frl.location, frl.value " + 
				"ORDER BY frl.value DESC, frl.location ASC";
		
		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("fromGroup", UtilReports.GROUP_FAN_CITY);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		List<Object[]> results = query.getResultList();
		
		for (Object[] result : results) {
			
			ReportFacebookLocation reportFacebookCity = new ReportFacebookLocation();
			reportFacebookCity.setLocation((String) result[0]);
			reportFacebookCity.setValue(((Number) result[1]).longValue());
		    
			reportFacebookCities.add(reportFacebookCity);
		}
		
		return reportFacebookCities;
	}

	@Override
	public List<ReportFacebookLocation> getReportFacebookCityImp(String pageId, Integer offset, Integer limit) {
		
		List<ReportFacebookLocation> reportFacebookCities = new ArrayList<ReportFacebookLocation>();
		
		sql = "SELECT frl.location, sum(frl.value) AS sumvalue FROM FaceReportLocation frl WHERE frl.pageId = :pageId " +
				"AND frl.fromGroup = :fromGroup " +
				"GROUP BY frl.location " + 
				"ORDER BY sumvalue DESC, frl.location ASC";
		
		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("fromGroup", UtilReports.GROUP_IMP_CITY);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		List<Object[]> results = query.getResultList();
		
		for (Object[] result : results) {
			
			ReportFacebookLocation reportFacebookCity = new ReportFacebookLocation();
			reportFacebookCity.setLocation((String) result[0]);
			reportFacebookCity.setValue(((Number) result[1]).longValue());
		    
			reportFacebookCities.add(reportFacebookCity);
		}
		
		return reportFacebookCities;
	}
	
	@Override
	public List<ReportFacebookLocation> getReportFacebookCountryImp(String pageId, Integer offset, Integer limit) {
		
		List<ReportFacebookLocation> reportFacebookCountries = new ArrayList<ReportFacebookLocation>();
		
		sql = "SELECT frl.location, sum(frl.value) AS sumvalue FROM FaceReportLocation frl WHERE frl.pageId = :pageId " +
				"AND frl.fromGroup = :fromGroup " +
				"GROUP BY frl.location " + 
				"ORDER BY sumvalue DESC, frl.location ASC";
		
		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("fromGroup", UtilReports.GROUP_IMP_COUNTRY);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		List<Object[]> results = query.getResultList();
		
		for (Object[] result : results) {
			
			ReportFacebookLocation reportFacebookCountry = new ReportFacebookLocation();
			reportFacebookCountry.setLocation((String) result[0]);
			reportFacebookCountry.setValue(((Number) result[1]).longValue());
		    
			reportFacebookCountries.add(reportFacebookCountry);
		}
		
		return reportFacebookCountries;
	}

	@Override
	public List<ReportFacebookLocation> getExternalReferences(String pageId, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		
		sql = "SELECT frl.location, sum(frl.value) AS sumvalue FROM FaceReportLocation frl " + 
				"WHERE frl.pageId = :pageId AND frl.fromGroup = :fromGroup AND (frl.date >= :dateFrom AND frl.date <= :dateUntil) " +
				"GROUP BY frl.location " +
				"ORDER BY sumvalue DESC, frl.location ASC";
		
		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("fromGroup", UtilReports.GROUP_VIEWS_EXTERNAL);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		query.setFirstResult(offset);
		if(limit != null) {
			query.setMaxResults(limit);
		}
		
		List<Object[]> result = query.getResultList();
		
		List<ReportFacebookLocation> externalReferences = new ArrayList<ReportFacebookLocation>();
		
		for(Object[] object : result) {
			
			ReportFacebookLocation externalReference = new ReportFacebookLocation();
			externalReference.setLocation((String) object[0]);
			externalReference.setValue((Long) object[1]);
		    
			externalReferences.add(externalReference);
		}
		
		return externalReferences;
	}

	@Override
	public boolean deleteFaceReportsLocations(String pageId) {
		
		sql = "DELETE FROM FaceReportLocation frl WHERE frl.pageId = :pageId";
			
		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
	
		try {
			
			query.executeUpdate();
			return true;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
	}

}
