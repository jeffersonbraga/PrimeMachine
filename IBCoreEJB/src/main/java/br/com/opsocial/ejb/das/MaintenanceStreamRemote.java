package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.feed.Stream;

@Remote
public interface MaintenanceStreamRemote extends AbstractDAS<Stream> {
	
	List<Stream> getStreamsByUser(Long idUser);

	Long getMaxOrderByTab(Long idTab);	
}
