package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.linkedin.LinkedinPost;
import br.com.opsocial.ejb.entity.report.SumByDate;

@Remote
public interface MaintenanceLinkedinPostRemote extends AbstractDAS<LinkedinPost> {

	Integer countLinkedinPosts(String networkId);

	List<LinkedinPost> getLinkedinPosts(String networkId);

	List<LinkedinPost> getLinkedinPostsOnPeriod(String networkId, Long dateFrom, Long dateUntil);

	List<SumByDate> getLinkedinPostsCountByDay(String networkId, Long dateFrom, Long dateUntil);
}
