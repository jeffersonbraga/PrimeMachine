package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.groups.Groups;

@Local
public interface GroupDAO extends AbstractDAO<Groups> {

	List<Groups> listGroupsByAccount(Long idAccount, Character groupType);
}
