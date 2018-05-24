package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.ejb.entity.sets.PostAttachment;

@Local
public interface PostDAO extends AbstractDAO<Post> {

	List<Post> listSchedulings(ArrayList<Integer> idProfile, Long idAccount, Character status, Integer offset, Integer limit, String order);

	Post getPostReloadFeature(User loggedUser);

	List<Post> getByStartDate(Long startDate);

	void deleteAttachment(PostAttachment postAtt);

}
