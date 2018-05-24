package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.report.TwitterFollowerReport;

@Stateless
public class TwitterFollowerReportDAOImpl extends AbstractDAOImpl<TwitterFollowerReport> implements TwitterFollowerReportDAO {

	@Override
	public TwitterFollowerReport getEntity(Long idProfile, Long followerId, String property, Long date) {
		
		sql = "SELECT t FROM TwitterFollowerReport t WHERE t.idProfile = :idProfile " +
				"AND t.followerId = :followerId " +
				"AND t.property = :property " +
				"AND t.date = :date";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("followerId", followerId);
		query.setParameter("property", property);
		query.setParameter("date", date);

		try {
			return (TwitterFollowerReport) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Long getFollowersEngagedCount(Long idProfile, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT COUNT(DISTINCT(followerid)) FROM opsocial.twitterfollowersreports " +
				"WHERE idProfile = " + idProfile + " " +
				"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")";
		
		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getFollowersEngagement(Long idProfile, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT SUM(tfr.value) FROM TwitterFollowerReport tfr " +
				"WHERE tfr.idProfile = :idProfile " +
				"AND (tfr.date >= :dateFrom AND tfr.date <= :dateUntil)";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);

		try {
			return query.getSingleResult() != null ? (Long) query.getSingleResult() : 0L;
		} catch (NoResultException e) {
			return 0L;
		}
	}
	
}
