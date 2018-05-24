package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.feed.Stream;

@Local
public interface StreamDAO extends AbstractDAO<Stream> {

	List<Stream> getStreamsByUser(Long idUser);

	Long getMaxOrderByTab(Long idTab);

}
