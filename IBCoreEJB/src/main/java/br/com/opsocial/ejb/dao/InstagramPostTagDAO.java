package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.instagram.InstagramPostTag;
import br.com.opsocial.ejb.entity.instagram.InstagramTopPostTag;

@Local
public interface InstagramPostTagDAO extends AbstractDAO<InstagramPostTag> {
	
	public List<InstagramTopPostTag> getInstagramTopPostTags(String idInstagramUser, Long dateFrom, Long dateUntil);
	public List<InstagramTopPostTag> getInstagramTopPostTags(String idInstagramUser);

}
