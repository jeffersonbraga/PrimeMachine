package br.com.opsocial.ejb.dao;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.application.ProfileUserDashboard;

@Local
public interface ProfileUserDashboardDAO extends AbstractDAO<ProfileUserDashboard> {

}
