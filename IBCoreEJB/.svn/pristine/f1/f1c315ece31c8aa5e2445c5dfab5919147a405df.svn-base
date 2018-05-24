package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.groups.Groups;

@Remote
public interface MaintenanceGroupRemote extends AbstractDAS<Groups> {
	
	public List<Groups> listGroupsByAccount(Long idAccount, Character groupType);
}
