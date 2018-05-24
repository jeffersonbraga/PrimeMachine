package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.report.FaceReportPostComment;

@Stateless
public class FaceReportPostCommentDAOImpl extends AbstractDAOImpl<FaceReportPostComment> implements FaceReportPostCommentDAO {

	@Override
	public FaceReportPostComment save(FaceReportPostComment faceReportPostComment) throws Exception {

		try {

			FaceReportPostComment objTmp = getById(faceReportPostComment.getCommentId());

			if(objTmp != null) {
				
				faceReportPostComment.setCommentId(objTmp.getCommentId());
				faceReportPostComment.setVersion(objTmp.getVersion());
				
				em.merge(faceReportPostComment);

				this.flush(faceReportPostComment);

			} else {

				em.persist(faceReportPostComment);

				this.flush(faceReportPostComment);		
			}

		} catch (Exception e) {
			throw new Exception(e);
		}

		return faceReportPostComment;
	}

	@Override
	public List<FaceReportPostComment> getComments(String postId) {
		
		sql = "SELECT frpc FROM FaceReportPostComment frpc WHERE frpc.faceReportPost.postId = :postId ORDER BY frpc.createdTime DESC";
		
		query = em.createQuery(sql);
		query.setParameter("postId", postId);
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<FaceReportPostComment>();
		}
	}

}
