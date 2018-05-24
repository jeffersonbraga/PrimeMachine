package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.feed.Tab;

@Remote
public interface MaintenanceTabRemote extends AbstractDAS<Tab> {

	List<Tab> getTabsByUser(Long idUser);

	Tab getInitialTab(Long idUser);

}
