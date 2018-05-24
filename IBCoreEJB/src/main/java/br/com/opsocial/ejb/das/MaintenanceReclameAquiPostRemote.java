package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPost;

@Remote
public interface MaintenanceReclameAquiPostRemote extends AbstractDAS<ReclameAquiPost> {

}
