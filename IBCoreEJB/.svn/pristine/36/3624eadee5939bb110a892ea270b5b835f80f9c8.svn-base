package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.twitter.TwitterPost;

@Remote
public interface MaintenanceTwitterPostRemote extends AbstractDAS<TwitterPost> {

	public List<TwitterPost> getTwitterPostsForUpdate(Long idAccount);
	
}
