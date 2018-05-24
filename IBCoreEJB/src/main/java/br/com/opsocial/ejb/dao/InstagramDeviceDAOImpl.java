package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramDevice;

@Stateless

public class InstagramDeviceDAOImpl extends AbstractDAOImpl<InstagramDevice> implements InstagramDeviceDAO {

	@Override
	public Long getInstagramDeviceLessUsedId() {
		
		sql = "SELECT idv.idinstagramdevice, count(*) FROM opsocial.instagramdevices AS idv " +
				"LEFT JOIN opsocial.profiles AS p ON " +
				"p.idinstagramdevice = idv.idinstagramdevice " +
				"GROUP BY idv.idinstagramdevice " +
				"ORDER BY count(*) ASC " +
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);
		
		Object[] result = (Object[]) query.getSingleResult();
		
		return (Long) result[0];
	}
	
}
