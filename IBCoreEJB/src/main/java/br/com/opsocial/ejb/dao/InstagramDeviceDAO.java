package br.com.opsocial.ejb.dao;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.instagram.InstagramDevice;

@Local
public interface InstagramDeviceDAO extends AbstractDAO<InstagramDevice> {
	
	public Long getInstagramDeviceLessUsedId();

}
