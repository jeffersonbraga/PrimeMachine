package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.report.TwitterFollowerControl;

@Remote
public interface MaintenanceTwitterFollowerControlRemote extends AbstractDAS<TwitterFollowerControl> {
	
}
