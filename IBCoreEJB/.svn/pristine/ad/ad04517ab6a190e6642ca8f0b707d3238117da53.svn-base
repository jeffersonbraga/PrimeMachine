package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.youtube.YoutubePost;

@Stateless
public class YoutubePostDAOImpl extends AbstractDAOImpl<YoutubePost> implements YoutubePostDAO {

	@Override
	public YoutubePost save(YoutubePost youtubePost) throws Exception {
		
		try {
			
			YoutubePost objTmp = getById(youtubePost.getVideoId());

			if(objTmp != null) {
			
				em.merge(youtubePost);
				
				this.flush(youtubePost);
			
			} else {
				
				em.persist(youtubePost);
				
				this.flush(youtubePost);
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		return youtubePost;
	}
}
