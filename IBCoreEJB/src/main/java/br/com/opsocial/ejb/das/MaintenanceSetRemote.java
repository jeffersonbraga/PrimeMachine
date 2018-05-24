package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.sets.Sets;

@Remote
public interface MaintenanceSetRemote extends AbstractDAS<Sets>{
	
	public List<Sets> getByStartDate(Long startDate);
	
	public List<Sets> getByEndDate(Long endDate);
	
	public List<Sets> getByProfile(Long idProfile, Long idAccount);
	
	public List<Sets> listSchedulings(Long idAccount);
	
	public Long getAmount(Long idUser);
	
	public List<Sets> listActives();
	
	public List<Sets> listByAccount(Long idAccount);
	
	public List<Sets> listSchedulings(Long idProfile, Long idAccount, Character status, Integer offset, Integer limit);
	
}
