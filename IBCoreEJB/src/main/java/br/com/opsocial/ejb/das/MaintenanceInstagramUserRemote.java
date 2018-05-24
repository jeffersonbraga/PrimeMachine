package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.instagram.InstagramUser;

@Remote
public interface MaintenanceInstagramUserRemote extends AbstractDAS<InstagramUser> {

}
