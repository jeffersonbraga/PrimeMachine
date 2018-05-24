package br.com.opsocial.ejb.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.idclass.MonitoringLocationId;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLocation;

@Stateless
public class MonitoringLocationDAOImpl extends AbstractDAOImpl<MonitoringLocation> implements MonitoringLocationDAO {

	@Override
	public MonitoringLocation save(MonitoringLocation monitoringLocation) throws Exception {

		try {

			MonitoringLocationId monitoringLocationId = new MonitoringLocationId();
			monitoringLocationId.setIdLocation(monitoringLocation.getIdLocation());
			monitoringLocationId.setNetworkType(monitoringLocation.getNetworkType());
			
			MonitoringLocation objTmp = getById(monitoringLocationId); 

			if(objTmp != null) {

				monitoringLocation.setVersion(objTmp.getVersion());

				em.merge(monitoringLocation);

				this.flush();

			} else {

				em.persist(monitoringLocation);

				this.flush();
			}

		} catch (Exception e) {
			throw new Exception(e);
		}

		return monitoringLocation;
	}

	@Override
	public Map<String, Long> getPostsCitiesCount(Long idMonitoring, Character networkType, 
			Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		
		sql = "SELECT ml.idlocation, ml.fullname, COUNT(*) FROM opsocial.monitoringpostslocations AS mpl " +
				"INNER JOIN opsocial.monitoringlocations AS ml ON " +
					"mpl.idlocation = ml.idlocation AND mpl.networktype = ml.networktype " +
				"WHERE mpl.idmonitoring = " + idMonitoring + " AND mpl.networktype = '" + networkType + "' " +
					"AND (mpl.date >= " + dateFrom + " AND mpl.date <= " + dateUntil + ") " +
					"AND ml.placetype = 'city' " +
				"GROUP BY ml.idlocation, ml.fullname " +
				"ORDER BY COUNT(*) DESC, ml.fullname ASC " + 
				"LIMIT " + limit + " OFFSET " + offset;

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> postsCitiesCount = new LinkedHashMap<String, Long>();
		for(Object[] object : result) {
			postsCitiesCount.put((String) object[1], (Long) object[2]);
		}

		return postsCitiesCount;
	}

	@Override
	public List<Object[]> getPostsCitiesCountByDay(Long idMonitoring, Long date) {
		
		sql = "SELECT ml.idlocation, ml.fullname, COUNT(*) FROM opsocial.monitoringpostslocations AS mpl " + 
				"INNER JOIN opsocial.monitoringlocations AS ml ON " +
					"mpl.idlocation = ml.idlocation AND mpl.networktype = ml.networktype " +
				"WHERE mpl.idmonitoring = " + idMonitoring + " AND mpl.date = " + date + " AND ml.placetype = 'city' " +
				"GROUP BY ml.idlocation, ml.fullname";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		return result;
	}

}
