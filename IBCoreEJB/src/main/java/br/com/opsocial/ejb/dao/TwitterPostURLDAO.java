package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.twitter.TwitterPostURL;

@Local
public interface TwitterPostURLDAO extends AbstractDAO<TwitterPostURL> {
	
	public List<TwitterPostURL> listByStatusId(Long statusId);

}
