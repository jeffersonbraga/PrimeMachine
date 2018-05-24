package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.twitter.TwitterPostURL;

@Remote
public interface MaintenanceTwitterPostURLRemote extends AbstractDAS<TwitterPostURL> {

	public List<TwitterPostURL> listByStatusId(Long statusId);
	
}
