package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.instagram.InstagramProfileDevice;

@Remote
public interface MaintenanceInstagramProfileDeviceRemote extends AbstractDAS<InstagramProfileDevice> {

}
