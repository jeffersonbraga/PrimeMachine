package br.com.opsocial.ejb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostTag;

@Stateless
public class MonitoringPostTagDAOImpl extends AbstractDAOImpl<MonitoringPostTag> implements MonitoringPostTagDAO {

	@Override
	public List<MonitoringPostTag> listByMonitoringPost(String postId,
			Long idMonitoring, String term, Character network) {
		
		sql = "Select mpt FROM MonitoringPostTag mpt WHERE " + 
				"mpt.postId = :postId AND mpt.idMonitoring = :idMonitoring AND " + 
				"mpt.term = :term AND mpt.network = :network ORDER BY mpt.monitoringTag.name ASC";
		
		query = em.createQuery(sql);
		query.setParameter("postId", postId);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);
		query.setParameter("network", network);

		return query.getResultList();
	}
	
	@Override
	public boolean hasMonitoringTag(Long idTag, String postId, Long idMonitoring, String term, Character network) {
		
		sql = "SELECT mpt FROM MonitoringPostTag mpt " +
				"WHERE mpt.monitoringTag.idTag = :idTag AND mpt.postId = :postId " +
				"AND mpt.term = :term AND mpt.idMonitoring = :idMonitoring AND mpt.network = :network";

		query = em.createQuery(sql);
		query.setParameter("idTag", idTag);
		query.setParameter("postId", postId);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);
		query.setParameter("network", network);
		
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public boolean thereIsMonitoringTag(String postId,
			Long idMonitoring, String term, Character network, List<Long> monitoringTags) {
		
		String monitoringTagsIn = "(";
		
		if(!monitoringTags.isEmpty()) {
			monitoringTagsIn = monitoringTagsIn.concat(""+monitoringTags.get(0)+"");
			for(int i = 1; i < monitoringTags.size(); i++) {
				monitoringTagsIn = monitoringTagsIn.concat(","+monitoringTags.get(i)+"");
			}
		}
		
		monitoringTagsIn = monitoringTagsIn.concat(")");
		
		sql = "SELECT COUNT(mpt) FROM MonitoringPostTag mpt " +
					"WHERE mpt.postId = :postId AND mpt.term = :term " +
					"AND mpt.idMonitoring = :idMonitoring AND mpt.network = :network AND mpt.monitoringTag.idTag IN " + monitoringTagsIn + "";

		query = em.createQuery(sql);
		query.setParameter("postId", postId);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);
		query.setParameter("network", network);
		
		Long monitoringTagsCount = (Long) query.getSingleResult();
		if(monitoringTagsCount > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Map<Character, Long> getTaggedPostsPerNetworkForSamples(Long idMonitoring, Long idTag, HashMap<Character, String> mappedPosts) {
		
		sql = "SELECT networktagged, counttaggedposts FROM ( ";
		
		if(mappedPosts.get(Profile.FACEBOOK) != null) {
			sql += "SELECT mpt.network AS networktagged, count(*) AS counttaggedposts FROM opsocial.facepostsmonitorings AS fpm " + 
				"INNER JOIN opsocial.monitoringspoststags AS mpt ON " +
				"mpt.postid = fpm.postid AND mpt.term = fpm.term AND mpt.idmonitoring = fpm.idmonitoring " +
			"WHERE mpt.idtag = " + idTag + " AND mpt.idmonitoring = " + idMonitoring + " AND fpm.postid in " + mappedPosts.get(Profile.FACEBOOK) + 
			" GROUP BY networktagged ";
		}

		if(mappedPosts.get(Profile.TWITTER) != null) {
			sql += "UNION ALL " +
			"SELECT mpt.network AS networktagged, count(*) AS counttaggedposts FROM opsocial.twitterpostsmonitorings AS tpm " + 
				"INNER JOIN opsocial.monitoringspoststags AS mpt ON " +
				"mpt.postid = CAST(tpm.statusid AS varchar) AND mpt.term = tpm.term AND mpt.idmonitoring = tpm.idmonitoring " +
			"WHERE mpt.idtag = " + idTag + " AND mpt.idmonitoring = " + idMonitoring + " AND tpm.statusid in " + mappedPosts.get(Profile.TWITTER) +
			" GROUP BY networktagged ";
		}

		if(mappedPosts.get(Profile.YOUTUBE) != null) {
			sql += "UNION ALL " +
			"SELECT mpt.network AS networktagged, count(*) AS counttaggedposts FROM opsocial.youtubepostsmonitorings AS ypm " + 
				"INNER JOIN opsocial.monitoringspoststags AS mpt ON " +
				"mpt.postid = ypm.videoid AND mpt.term = ypm.term AND mpt.idmonitoring = ypm.idmonitoring " +
			"WHERE mpt.idtag = " + idTag + " AND mpt.idmonitoring = " + idMonitoring + " AND ypm.videoid in " + mappedPosts.get(Profile.YOUTUBE) +
			" GROUP BY networktagged ";
		}
		
		if(mappedPosts.get(Profile.GOOGLE) != null) {
			sql += "UNION ALL " +
			"SELECT mpt.network AS networktagged, count(*) AS counttaggedposts FROM opsocial.googlepluspostsmonitorings AS gpm " + 
				"INNER JOIN opsocial.monitoringspoststags AS mpt ON " +
				"mpt.postid = gpm.activityid AND mpt.term = gpm.term AND mpt.idmonitoring = gpm.idmonitoring " +
			"WHERE mpt.idtag = " + idTag + " AND mpt.idmonitoring = " + idMonitoring + " AND gpm.activityid in " + mappedPosts.get(Profile.GOOGLE) +
			" GROUP BY networktagged ";
		}

		if(mappedPosts.get(Profile.INSTAGRAM) != null) {
			sql += "UNION ALL " +
			"SELECT mpt.network AS networktagged, count(*) AS counttaggedposts FROM opsocial.instagrampostsmonitorings AS ipm " +
				"INNER JOIN opsocial.monitoringspoststags AS mpt ON " +
				"mpt.postid = CAST(ipm.idmedia AS varchar) AND mpt.term = ipm.term AND mpt.idmonitoring = ipm.idmonitoring " +
			"WHERE mpt.idtag = " + idTag + " AND mpt.idmonitoring = " + idMonitoring + " AND ipm.idmedia in " + mappedPosts.get(Profile.INSTAGRAM) +
			" GROUP BY networktagged ";
		}

		if(mappedPosts.get(Profile.RECLAMEAQUI) != null) {
			sql += "UNION ALL " +
			"SELECT mpt.network AS networktagged, count(*) AS counttaggedposts FROM opsocial.reclameaquipostsmonitorings AS rapm " +
				"INNER JOIN opsocial.monitoringspoststags AS mpt ON " +
				"mpt.postid = rapm.cacheid AND mpt.term = rapm.term AND mpt.idmonitoring = rapm.idmonitoring " +
			"WHERE mpt.idtag = " + idTag + " AND mpt.idmonitoring = " + idMonitoring + " AND rapm.cacheid in " + mappedPosts.get(Profile.RECLAMEAQUI) +
			" GROUP BY networktagged ";
		}

		if(mappedPosts.get(Profile.NEWS) != null) {
			sql += "UNION ALL " +
			"SELECT mpt.network AS networktagged, count(*) AS counttaggedposts FROM opsocial.newspostsmonitorings AS npm " +
				"INNER JOIN opsocial.monitoringspoststags AS mpt ON " +
				"mpt.postid = CAST(npm.idnewspost AS varchar) AND mpt.term = npm.term AND mpt.idmonitoring = npm.idmonitoring " +
			"WHERE mpt.idtag = " + idTag + " AND mpt.idmonitoring = " + idMonitoring + " AND npm.idnewspost in " + mappedPosts.get(Profile.NEWS) +
			" GROUP BY networktagged ";
		}

		if(mappedPosts.get(Profile.BLOGS) != null) {
			sql += "UNION ALL " +
			"SELECT mpt.network AS networktagged, count(*) AS counttaggedposts FROM opsocial.blogpostsmonitorings AS bpm " +
				"INNER JOIN opsocial.monitoringspoststags AS mpt ON " +
				"mpt.postid = CAST(bpm.idblogpost AS varchar) AND mpt.term = bpm.term AND mpt.idmonitoring = bpm.idmonitoring " +
			"WHERE mpt.idtag = " + idTag + " AND mpt.idmonitoring = " + idMonitoring + " AND bpm.idblogpost in " + mappedPosts.get(Profile.BLOGS) +
			" GROUP BY networktagged ";
		}
		
		sql += ") AS countalltaggedposts";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<Character, Long> taggedPostsPerNetwork = new HashMap<Character, Long>();
		for (Object[] object : result) {
			taggedPostsPerNetwork.put(((String) object[0]).charAt(0), (Long)object[1]);
		}
		
		return taggedPostsPerNetwork;
	}

}
