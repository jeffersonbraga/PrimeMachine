package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.google.ObjectAttachment;

@Remote
public interface MaintenanceObjectAttachmentRemote extends AbstractDAS<ObjectAttachment> {
	
	public List<ObjectAttachment> listByPost(String activityId);

}
