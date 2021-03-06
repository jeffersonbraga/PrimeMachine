package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.InstagramPostLikeDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramPostLike;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;

@Stateless
public class MaintenanceInstagramPostLikeBean extends AbstractDASImpl<InstagramPostLike> implements MaintenanceInstagramPostLikeRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	InstagramPostLikeDAO dao;
	
	@Override
	public void setDao(AbstractDAO<InstagramPostLike> dao) {
		this.dao = (InstagramPostLikeDAO) dao;
	}

	@Override
	public AbstractDAO<InstagramPostLike> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<InstagramPostLike> getDAO() {
		return dao;
	}
	
	@Override
	public Map<Integer, Long> getLikesPerMonthYear(String idInstagramUser, Integer year) {
		return dao.getLikesPerMonthYear(idInstagramUser, year);
	}

	@Override
	public List<InstagramReport> getLikesPerDay(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getLikesPerDay(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public Long getCountOfLikes(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getCountOfLikes(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public Object[] getLikesOrigin(String idInstagramUser) {
		return dao.getLikesOrigin(idInstagramUser);
	}

	@Override
	public Object[] getLikesOrigin(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getLikesOrigin(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public Map<Integer, Long> getLikesPerHour(String idInstagramUser) {
		return dao.getLikesPerHour(idInstagramUser);
	}

	@Override
	public Object[] getTimeWithMoreLikes(String idInstagramUser) {
		return dao.getTimeWithMoreLikes(idInstagramUser);
	}

	@Override
	public Object[] getTimeWithLessLikes(String idInstagramUser) {
		return dao.getTimeWithLessLikes(idInstagramUser);
	}

	@Override
	public Map<Integer, Long> getLikesPerHour(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getLikesPerHour(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public Object[] getTimeWithMoreLikes(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getTimeWithMoreLikes(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public Object[] getTimeWithLessLikes(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getTimeWithLessLikes(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramSimpleDataDTO> getLikesPerMonthYearSimpleData(String valueOf, int i) {
		return dao.getLikesPerMonthYearSimpleData(valueOf, i);
	}

	@Override
	public List<InstagramSimpleDataDTO> getLikesPerHourSimpleData(String valueOf, Long dateFrom, Long dateUntil) {
		return dao.getLikesPerHourSimpleData(valueOf, dateFrom, dateUntil);
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
