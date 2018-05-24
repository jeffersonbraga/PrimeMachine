package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.sets.Sets;

@Local
public interface SetDAO extends AbstractDAO<Sets> {
	
	public List<Sets> getByStartDate(Long startDate);
	
	public List<Sets> getByEndDate(Long endDate);;
	
	public List<Sets> getByProfile(Long idProfile, Long idAccount);
	
	public List<Sets> listSchedulings(Long idAccount);
	
	public List<Sets> listSchedulings(Long idProfile, Long idAccount, Character status, Integer offset, Integer limit);
	
	public Long getAmount(Long idAccount);
	
	public List<Sets> listActives();
	
	public List<Sets> listByAccount(Long idAccount);
}
