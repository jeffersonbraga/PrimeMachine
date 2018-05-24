package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.facebook.FacebookPost;

@Remote
public interface MaintenanceFacebookPostRemote extends AbstractDAS<FacebookPost> {

}
