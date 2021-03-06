package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.report.AgeGroup;
import br.com.opsocial.ejb.entity.report.FacebookPostInsight;
import br.com.opsocial.ejb.entity.report.PropertyValue;
import br.com.opsocial.ejb.entity.report.SumByDate;

@Remote
public interface MaintenanceFacebookPostInsightRemote extends AbstractDAS<FacebookPostInsight> {

	public Long getAveragePostsReach(String pageId, Long dateFrom, Long dateUntil);
	public SumByDate getMajorPostsReachDay(String pageId, Long dateFrom, Long dateUntil);
	public Long getAveragePostsInteractions(String pageId, Long dateFrom, Long dateUntil);
	public SumByDate getMajorPostsInteractionsDay(String pageId, Long dateFrom, Long dateUntil);
	
	public Long getPostsStoriesLikeCount(String pageId, Long dateFrom, Long dateUntil);
	public Long getPostsStoriesCommentCount(String pageId, Long dateFrom, Long dateUntil);
	public Long getPostsStoriesShareCount(String pageId, Long dateFrom, Long dateUntil);
	
	public Long getPostsInteractionsCount(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsInteractionsByDay(String pageId, Long dateFrom, Long dateUntil);
	
	public List<SumByDate> getPostsReachByDay(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsReachPaid(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsReachOrganic(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsReachViral(String pageId, Long dateFrom, Long dateUntil);
	public Long getPostsReachOrganicCount(String pageId, Long dateFrom, Long dateUntil);
	public Long getPostsReachViralCount(String pageId, Long dateFrom, Long dateUntil);
	public Long getPostsReachPaidCount(String pageId, Long dateFrom, Long dateUntil);
	public Long getPostsReachCount(String pageId, Long dateFrom, Long dateUntil);
	public Long getAverageReachOfPostsPerDay(String pageId, Long dateFrom, Long dateUntil);
	
	public List<SumByDate> getPostsImpressionsByDay(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsImpressionsPaid(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsImpressionsOrganic(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsImpressionsViral(String pageId, Long dateFrom, Long dateUntil);
	public Long getPostsImpressionsOrganicCount(String pageId, Long dateFrom, Long dateUntil);
	public Long getPostsImpressionsViralCount(String pageId, Long dateFrom, Long dateUntil);
	public Long getPostsImpressionsPaidCount(String pageId, Long dateFrom, Long dateUntil);
	public Long getPostsImpressionsCount(String pageId, Long dateFrom, Long dateUntil);
	public Long getAverageImpressionsOfPostsPerDay(String pageId, Long dateFrom, Long dateUntil);
	
	public List<SumByDate> getPostsStoriesLikeByDay(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsStoriesCommentByDay(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsStoriesShareByDay(String pageId, Long dateFrom, Long dateUntil);
	public Long getPostsStoriesCount(String pageId, Long dateFrom, Long dateUntil);
	public Integer getPostsStoriesLikePercentage(String pageId, Long dateFrom, Long dateUntil);
	public Integer getPostsStoriesCommentPercentage(String pageId, Long dateFrom, Long dateUntil);
	public Integer getPostsStoriesSharePercentage(String pageId, Long dateFrom, Long dateUntil);
	public Map<String, Long> getPostsStoriesLikeByStatusType(String pageId, Long dateFrom, Long dateUntil);
	public Map<String, Long> getPostsStoriesCommentByStatusType(String pageId, Long dateFrom, Long dateUntil);
	public Map<String, Long> getPostsStoriesShareByStatusType(String pageId, Long dateFrom, Long dateUntil);
	
	public List<SumByDate> getPostsConsumptionsByDay(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsConsumptionsPhotoViewByDay(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsConsumptionsLinkClicksByDay(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsConsumptionsVideoPlaysByDay(String pageId, Long dateFrom, Long dateUntil);
	public List<SumByDate> getPostsConsumptionsOtherClicksByDay(String pageId, Long dateFrom, Long dateUntil);
	public Long getPostsConsumptionsCount(String pageId, Long dateFrom, Long dateUntil);
	public Integer getPostsConsumptionsPhotoViewPercentage(String pageId, Long dateFrom, Long dateUntil);
	public Integer getPostsConsumptionsLinkClicksPercentage(String pageId, Long dateFrom, Long dateUntil);
	public Integer getPostsConsumptionsVideoPlaysPercentage(String pageId, Long dateFrom, Long dateUntil);
	public Integer getPostsConsumptionsOtherClicksPercentage(String pageId, Long dateFrom, Long dateUntil);
	public Long getAverageConsumptionsPhotoViewPerPost(String pageId, Long dateFrom, Long dateUntil);
	public Long getAverageConsumptionsLinkClicksPerPost(String pageId, Long dateFrom, Long dateUntil);
	public Long getAverageConsumptionsVideoPlaysPerPost(String pageId, Long dateFrom, Long dateUntil);
	public Long getAverageConsumptionsOtherClicksPerPost(String pageId, Long dateFrom, Long dateUntil);
	
	public Map<String, Long> getAveragePostReachByPostType(String pageId, Long dateFrom, Long dateUntil);
	public Map<String, Long> getAveragePostInteractionsByPostType(String pageId, Long dateFrom, Long dateUntil);
	public Map<String, Integer> getEngagementPercentageByPostType(String pageId, Long dateFrom, Long dateUntil);
	
	public Map<String, Long> getPostInsights(String postId);
	public Map<String, Long> getPostInsightsHistorical(String postId);
	public Map<String, Long> getPostInsightsDashboard(String postId);
	
	public List<SumByDate> getInsightsByMonth(String pageId, String property);
	public Integer getPostsInteractionPercentage(String pageId, String property);
	public SumByDate getMostEngagedMonth(String pageId);
	
	public List<AgeGroup> getVideoTotalTimeByAgeGender(String pageId, Long dateFrom, Long dateUntil, Character gender);
	
	public List<PropertyValue> getVideoRetention(String postId, String type);
	public Map<String, Long> getPostVideoInsights(String postId);
	public List<AgeGroup> getVideoTotalTimeByAgeGender(String postId, Character gender);
}
