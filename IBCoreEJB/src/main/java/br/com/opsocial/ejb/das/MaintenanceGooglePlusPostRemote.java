package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.google.GooglePlusPost;

@Remote
public interface MaintenanceGooglePlusPostRemote extends AbstractDAS<GooglePlusPost> {

}
