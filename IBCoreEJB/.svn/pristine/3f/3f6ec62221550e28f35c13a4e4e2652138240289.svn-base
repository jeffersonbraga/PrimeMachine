package br.com.opsocial.ejb.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.monitoring.MonitoringGeoPost;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostLocation;
import br.com.opsocial.ejb.entity.twitter.TwitterPost;
import br.com.opsocial.ejb.utils.monitorings.UtilMonitorings;

@Stateless
public class MonitoringPostLocationDAOImpl extends AbstractDAOImpl<MonitoringPostLocation> implements MonitoringPostLocationDAO {

	@Override
	public List<MonitoringGeoPost> getGetPosts(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {

		sql = "SELECT mpl FROM MonitoringPostLocation mpl " +
				"WHERE mpl.monitoring.idMonitoring = :idMonitoring AND " +
				"(mpl.date >= :dateFrom AND mpl.date <= :dateUntil) AND mpl.monitoringLocation.networkType = :networktype " +
				"AND (mpl.latitude <> 0 OR mpl.longitude <> 0) " +
				"ORDER BY mpl.postId DESC";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		query.setParameter("networktype", Profile.TWITTER);
		query.setFirstResult(offset);
		query.setMaxResults(limit);

		List<MonitoringPostLocation> monitoringPostLocations = query.getResultList();
		List<MonitoringGeoPost> monitoringGeoPosts = new ArrayList<MonitoringGeoPost>();

		List<String> postIds = new ArrayList<String>();

		for (MonitoringPostLocation monitoringPostLocation : monitoringPostLocations) {

			MonitoringGeoPost monitoringGeoPost = new MonitoringGeoPost();
			monitoringGeoPost.setLatitude(monitoringPostLocation.getLatitude());
			monitoringGeoPost.setLongitude(monitoringPostLocation.getLongitude());

			postIds.add(monitoringPostLocation.getPostId());

			monitoringGeoPosts.add(monitoringGeoPost);
		}
		
		if(!postIds.isEmpty()) {
		
			sql = "SELECT tp FROM TwitterPost tp WHERE tp.statusId IN :postIds ORDER BY tp.statusId DESC";
			
			query = em.createQuery(sql);
			query.setParameter("postIds", postIds);
			
			List<TwitterPost> twitterPosts = (List<TwitterPost>) query.getResultList();
			
			for (int i = 0; i < twitterPosts.size(); i++) {
				
				monitoringGeoPosts.get(i).setScreenName(twitterPosts.get(i).getScreenName());
				monitoringGeoPosts.get(i).setProfileUrl(UtilMonitorings.TWITTER_URL + twitterPosts.get(i).getScreenName());
				monitoringGeoPosts.get(i).setProfileImageUrl(twitterPosts.get(i).getProfileImageUrl());
				monitoringGeoPosts.get(i).setText(twitterPosts.get(i).getText());
				monitoringGeoPosts.get(i).setCreatedAt(new Date(twitterPosts.get(i).getCreatedAt() * 1000L));
				monitoringGeoPosts.get(i).setCreatedAtFormatted(
						new SimpleDateFormat("dd/MM/YYYY hh:mm").format(new Date(twitterPosts.get(i).getCreatedAt() * 1000L)));
				monitoringGeoPosts.get(i).setPostUrl(UtilMonitorings.TWITTER_URL + twitterPosts.get(i).getScreenName() + "/status/" + twitterPosts.get(i).getStatusId());
			}
			
		}
		
		
		return monitoringGeoPosts;
	}



}
