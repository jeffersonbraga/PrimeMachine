package br.com.opsocial.ejb.das;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.ejb.entity.sets.PostAttachment;

@Remote
public interface MaintenancePostRemote extends AbstractDAS<Post> {
	
	public List<Post> listSchedulings(ArrayList<Integer> idProfile, Long idAccount, Character status, Integer offset, Integer limit, String order);

	public Post getPostReloadFeature(User loggedUser);

	public List<Post> getByStartDate(Long time);

	public void deleteAttachment(PostAttachment postAtt);

}
