package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.twitter.TwitterPost;

@Local
public interface TwitterPostDAO extends AbstractDAO<TwitterPost> {

	public List<TwitterPost> getTwitterPostsForUpdate(Long idAccount);

}
