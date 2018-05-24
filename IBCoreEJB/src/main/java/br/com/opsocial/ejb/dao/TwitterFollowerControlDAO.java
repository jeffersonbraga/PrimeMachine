package br.com.opsocial.ejb.dao;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.report.TwitterFollowerControl;

@Local
public interface TwitterFollowerControlDAO extends  AbstractDAO<TwitterFollowerControl> {
	
}
