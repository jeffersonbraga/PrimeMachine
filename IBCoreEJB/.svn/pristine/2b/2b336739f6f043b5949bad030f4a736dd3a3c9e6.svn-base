package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.linkedin.LinkedinPost;
import br.com.opsocial.ejb.entity.report.SumByDate;

@Local
public interface LinkedinPostDAO extends AbstractDAO<LinkedinPost> {

	Integer countLinkedinPosts(String networkId);

	List<LinkedinPost> getLinkedinPosts(String networkId);

	List<LinkedinPost> getLinkedinPostsOnPeriod(String networkId, Long dateFrom, Long dateUntil);

	List<SumByDate> getLinkedinPostsCountByDay(String networkId, Long dateFrom, Long dateUntil);
}
