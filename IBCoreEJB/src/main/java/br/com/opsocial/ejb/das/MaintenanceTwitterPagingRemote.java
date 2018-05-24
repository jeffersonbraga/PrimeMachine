package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.twitter.TwitterPaging;

@Remote
public interface MaintenanceTwitterPagingRemote extends AbstractDAS<TwitterPaging> {

	public TwitterPaging getEntity(Long idMonitoring, String term);
	
}
