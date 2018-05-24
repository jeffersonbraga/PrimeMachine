package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.instagram.InstagramPostTag;
import br.com.opsocial.ejb.entity.instagram.InstagramTopPostTag;

@Remote
public interface MaintenanceInstagramPostTagRemote extends AbstractDAS<InstagramPostTag> {

	public List<InstagramTopPostTag> getInstagramTopPostTags(String idInstagramUser, Long dateFrom, Long dateUntil);
	public List<InstagramTopPostTag> getInstagramTopPostTags(String idInstagramUser);
	
}
