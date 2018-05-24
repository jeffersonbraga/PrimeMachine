package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.ProfileUserDashboard;

@Stateless
public class ProfileUserDashboardDAOImpl extends AbstractDAOImpl<ProfileUserDashboard> implements ProfileUserDashboardDAO {

//	@Override
//	public ProfileUserDashboard save(ProfileUserDashboard profileUserDashboard) throws Exception {
//		
//		try {
//			
//			if(DEBUG) {
//				if(!em.getTransaction().isActive()) {
//					em.getTransaction().begin();
//				}
//			}
//			
//			ProfileUserDashboardId profileUserDashboardId = new ProfileUserDashboardId();
//			profileUserDashboardId.setProfile(profileUserDashboard.getProfile().getIdProfile());
//			profileUserDashboardId.setUser(profileUserDashboard.getUser().getIdUser());
//			
//			ProfileUserDashboard objTmp = getById(profileUserDashboardId);
//			
//			if(objTmp != null) {
//				
//				profileUserDashboard.setVersion(objTmp.getVersion());
//			
//				em.merge(profileUserDashboard);
//				
//				if(DEBUG) {
//					em.getTransaction().commit();
//				} else {
//					this.flush();
//				}
//
//			} else {
//				
//				em.persist(profileUserDashboard);
//				
//				if(DEBUG) {
//					em.getTransaction().commit();
//				} else {
//					this.flush();
//				}
//			}
//			
//		} catch (Exception e) {
//			if(DEBUG) {
//				em.getTransaction().rollback();
//			}
//			e.printStackTrace();
//		}
//		
//		return profileUserDashboard;
//	}
	
	@Override
	public void delete(ProfileUserDashboard object) throws Exception {
		try {
			object = em.merge(object);
			em.remove(object);
			this.flush();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
