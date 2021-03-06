package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramPostTag;
import br.com.opsocial.ejb.entity.instagram.InstagramTopPostTag;

@Stateless
public class InstagramPostTagDAOImpl extends AbstractDAOImpl<InstagramPostTag> implements InstagramPostTagDAO {

	@Override
	public List<InstagramTopPostTag> getInstagramTopPostTags(String idInstagramUser, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT ipt.tag, SUM(irp.likes) AS sumlikes, SUM(irp.comments) AS sumcomments, " + 
				"SUM(irp.likes) + SUM(irp.comments) AS suminteractions FROM opsocial.instagrampoststags AS ipt " + 
				"INNER JOIN opsocial.instagramreportsposts AS irp ON " +
				"ipt.idmedia = irp.idmedia AND irp.idinstagramuser = '" + idInstagramUser + "' " +
				"AND (CAST(irp.createtime AS bigint) >= " + dateFrom + " AND CAST(irp.createtime AS bigint) < (" + dateUntil + " + 86400)) " +
				"GROUP BY ipt.tag " +
				"ORDER BY SUM(irp.likes) + SUM(irp.comments) DESC, ipt.tag ASC " +
				"OFFSET 0 LIMIT 10";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<InstagramTopPostTag> instagramTopPostTags = new ArrayList<InstagramTopPostTag>();
		for (Object[] object : result) {
			
			InstagramTopPostTag instagramTopPostTag = new InstagramTopPostTag();
			instagramTopPostTag.setTag((String) object[0]);
			instagramTopPostTag.setLikes((Long) object[1]);
			instagramTopPostTag.setComments((Long) object[2]);
			instagramTopPostTag.setInteractions((Long) object[3]);
			
			instagramTopPostTags.add(instagramTopPostTag);
		}

		return instagramTopPostTags;
	}

	@Override
	public List<InstagramTopPostTag> getInstagramTopPostTags(String idInstagramUser) {
		
		sql = "SELECT ipt.tag, SUM(irp.likes) AS sumlikes, SUM(irp.comments) AS sumcomments, " + 
				"SUM(irp.likes) + SUM(irp.comments) AS suminteractions FROM opsocial.instagrampoststags AS ipt " + 
				"INNER JOIN opsocial.instagramreportsposts AS irp ON " +
				"ipt.idmedia = irp.idmedia AND irp.idinstagramuser = '" + idInstagramUser + "' " +
				"GROUP BY ipt.tag " +
				"ORDER BY SUM(irp.likes) + SUM(irp.comments) DESC, ipt.tag ASC " +
				"OFFSET 0 LIMIT 10";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<InstagramTopPostTag> instagramTopPostTags = new ArrayList<InstagramTopPostTag>();
		for (Object[] object : result) {
			
			InstagramTopPostTag instagramTopPostTag = new InstagramTopPostTag();
			instagramTopPostTag.setTag((String) object[0]);
			instagramTopPostTag.setLikes((Long) object[1]);
			instagramTopPostTag.setComments((Long) object[2]);
			instagramTopPostTag.setInteractions((Long) object[3]);
			
			instagramTopPostTags.add(instagramTopPostTag);
		}

		return instagramTopPostTags;
	}

}
