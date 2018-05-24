package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.application.AudienceContact;

@Remote
public interface MaintenanceAudienceContactRemote extends AbstractDAS<AudienceContact> {

}
