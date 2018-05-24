package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.report.FaceReportPostComment;

@Remote
public interface MaintenanceFaceReportPostCommentRemote extends AbstractDAS<FaceReportPostComment> {
	
	public List<FaceReportPostComment> getComments(String postId);

}
