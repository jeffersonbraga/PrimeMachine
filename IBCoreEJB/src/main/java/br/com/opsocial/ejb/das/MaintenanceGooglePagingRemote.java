package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.google.GooglePaging;

@Remote
public interface MaintenanceGooglePagingRemote extends AbstractDAS<GooglePaging> {

	public GooglePaging getEntity(Long idMonitoring, String term, Character network);
	
}
