package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.groups.Groups;
import br.com.opsocial.ejb.entity.rssfeed.RssFeed;
import br.com.opsocial.ejb.entity.rssfeed.RssFeedMessage;

@Stateless
public class RssFeedDAOImpl extends AbstractDAOImpl<RssFeed> implements RssFeedDAO {
	
	@Override
	public RssFeed getAllOnGroupByDate(Long idGroup) {
		
		try {
		
			sql = "SELECT m.idrssfeedmsg, m.messagetitle, m.messagedescription, m.messagelink," +
				  "CAST(m.messagepubdate as TIMESTAMP) AS orderdate" +
				  "FROM opsocial.rssfeedmsgs m " +
				  "INNER JOIN opsocial.rssfeeds r ON (m.idrssfeed = r.idrssfeed)" +
				  "INNER JOIN opsocial.groupsrssfeeds gr ON (m.idrssfeed = gr.idrssfeed)" +
				  "INNER JOIN opsocial.groups g ON (gr.idgroup = g.idgroup) AND (g.idgroup = " + idGroup + ")" +
				  "ORDER BY orderdate DESC"; 
			
			query = em.createNativeQuery(sql);
	
			List<Object[]> result = query.getResultList();
			
			RssFeed rssFeed = new RssFeed();
			List<RssFeedMessage> rssFeedMessages = new ArrayList<RssFeedMessage>();
			
			for (Object[] object : result) {
				
				RssFeedMessage rssFeedMessage = new RssFeedMessage();
				
				rssFeedMessage.setIdRssfeedMsg((Long) object[0]);
				rssFeedMessage.setMessageTitle((String) object[1]);
				rssFeedMessage.setMessageDescription((String) object[2]);
				rssFeedMessage.setMessageLink((String) object[3]);
				rssFeedMessage.setMessagePubDate((String) object[4]);
				
				rssFeedMessages.add(rssFeedMessage);
			}
			
			rssFeed.setRssFeedMessage(rssFeedMessages);
			
			return rssFeed;
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RssFeed();
		}
	}

	@Override
	public void deleteByTitle(String title) {

		sql = "DELETE FROM RssFeed rss WHERE rss.title = :title";
		
		query = em.createQuery(sql);
		query.setParameter("title", title);
		
		query.executeUpdate();
	}
}
