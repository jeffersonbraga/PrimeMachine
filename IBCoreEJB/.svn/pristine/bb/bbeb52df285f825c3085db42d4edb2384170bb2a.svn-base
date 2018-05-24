package br.com.opsocial.ejb.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.report.FaceReportPost;
import br.com.opsocial.ejb.entity.report.PostEngagementPerc;
import br.com.opsocial.ejb.entity.report.PostEngagementStats;
import br.com.opsocial.ejb.entity.report.SumByDate;

@Local
public interface FaceReportPostDAO extends AbstractDAO<FaceReportPost> { 

	public List<FaceReportPost> getEntitiesByPageId(String pageId, Long dateFrom, Long dateUntil);
	
	public List<FaceReportPost> getPostsMostEngaged(String pageId, Integer offset, Integer limit);
	public List<PostEngagementStats> getPostsEngagementStats(String pageId);
	public PostEngagementPerc getPostsEngagementPercs(String pageId);
	
	public SumByDate getMostEngagementMonth(String pageId);
	
	public List<PostEngagementStats> getPostsEngagementStats(String pageId, Long dateFrom, Long dateUntil);
	public Map<String, Long> getPostsEngagementTotalStats(String pageId, Long dateFrom, Long dateUntil);
	
	public List<FaceReportPost> getTopPostsMostEngaged(String pageId, Long dateFrom, Long dateUntil, Integer limit);
	
	public List<FaceReportPost> getPostsMostEngaged(String pageId, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
	
	// New reports - content.
	public List<SumByDate> getNumberOfPosts(String pageId, Long dateFrom, Long dateUntil);
	public Long getAveragePostsPerDay(String pageId, Long dateFrom, Long dateUntil);
	
	// New reports - post to post.
	public Map<String, Long> getNumberOfPostsPerType(String pageId, Long dateFrom, Long dateUntil);
	
	public List<FaceReportPost> getPostsByDate(String pageId, Long dateFrom, Long dateUntil, String order, Integer offset, Integer limit);
	
}
