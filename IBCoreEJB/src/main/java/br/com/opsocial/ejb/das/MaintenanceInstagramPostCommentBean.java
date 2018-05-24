package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.InstagramPostCommentDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramPostComment;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;

@Stateless
public class MaintenanceInstagramPostCommentBean extends AbstractDASImpl<InstagramPostComment> implements MaintenanceInstagramPostCommentRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	InstagramPostCommentDAO dao;
	
	@Override
	public void setDao(AbstractDAO<InstagramPostComment> dao) {
		this.dao = (InstagramPostCommentDAO) dao;
	}

	@Override
	public AbstractDAO<InstagramPostComment> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<InstagramPostComment> getDAO() {
		return dao;
	}
	
	@Override
	public InstagramPostComment save(InstagramPostComment object) throws IllegalArgumentException, Exception {
		return super.save(object);
	}

	@Override
	public Map<Integer, Long> getCommentsPerMonthYear(String idInstagramUser, Integer year) {
		return dao.getCommentsPerMonthYear(idInstagramUser, year);
	}

	@Override
	public List<InstagramReport> getCommentsPerDay(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getCommentsPerDay(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public Long getCountOfComments(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getCountOfComments(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public Object[] getCommentsOrigin(String idInstagramUser) {
		return dao.getCommentsOrigin(idInstagramUser);
	}

	@Override
	public Object[] getCommentsOrigin(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getCommentsOrigin(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public Map<Integer, Long> getCommentsPerHour(String idInstagramUser) {
		return dao.getCommentsPerHour(idInstagramUser);
	}

	@Override
	public Object[] getTimeWithMoreComments(String idInstagramUser) {
		return dao.getTimeWithMoreComments(idInstagramUser);
	}

	@Override
	public Object[] getTimeWithLessComments(String idInstagramUser) {
		return dao.getTimeWithLessComments(idInstagramUser);
	}

	@Override
	public Map<Integer, Long> getCommentsPerHour(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getCommentsPerHour(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public Object[] getTimeWithMoreComments(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getTimeWithMoreComments(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public Object[] getTimeWithLessComments(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getTimeWithLessComments(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramSimpleDataDTO> getCommentsPerMonthYearSimpleData(String valueOf, int i) {
		return dao.getCommentsPerMonthYearSimpleData(valueOf, i);
	}

	@Override
	public List<InstagramSimpleDataDTO> getCommentsPerHourSimpleData(String valueOf, Long dateFrom, Long dateUntil) {
		return dao.getCommentsPerHourSimpleData(valueOf, dateFrom, dateUntil);
	}

	@Override
	public void setIdUserSession(Long idUserSession) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getIdUserSession() {
		// TODO Auto-generated method stub
		return null;
	}

}
