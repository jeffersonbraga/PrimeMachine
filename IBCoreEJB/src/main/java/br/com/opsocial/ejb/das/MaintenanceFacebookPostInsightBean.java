package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.FacebookPostInsightDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.report.AgeGroup;
import br.com.opsocial.ejb.entity.report.FacebookPostInsight;
import br.com.opsocial.ejb.entity.report.PropertyValue;
import br.com.opsocial.ejb.entity.report.SumByDate;

@Stateless
public class MaintenanceFacebookPostInsightBean extends AbstractDASImpl<FacebookPostInsight> implements MaintenanceFacebookPostInsightRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	FacebookPostInsightDAO dao;
	
	@Override
	public void setDao(AbstractDAO<FacebookPostInsight> dao) {
		this.dao = (FacebookPostInsightDAO) dao;
	}
	
	@Override
	public FacebookPostInsight save(FacebookPostInsight object) throws IllegalArgumentException, Exception {
		return super.save(object);
	}
	
	@Override
	public Long getAveragePostsReach(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAveragePostsReach(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public SumByDate getMajorPostsReachDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getMajorPostsReachDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getAveragePostsInteractions(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAveragePostsInteractions(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public SumByDate getMajorPostsInteractionsDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getMajorPostsInteractionsDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getPostsStoriesLikeCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsStoriesLikeCount(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getPostsStoriesCommentCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsStoriesCommentCount(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getPostsStoriesShareCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsStoriesShareCount(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getPostsInteractionsCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsInteractionsCount(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<SumByDate> getPostsInteractionsByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsInteractionsByDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<SumByDate> getPostsReachByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsReachByDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<SumByDate> getPostsReachPaid(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsReachPaid(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<SumByDate> getPostsReachOrganic(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsReachOrganic(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<SumByDate> getPostsReachViral(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsReachViral(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getPostsReachOrganicCount(String pageId, Long dateFrom,Long dateUntil) {
		return dao.getPostsReachOrganicCount(pageId, dateFrom, dateUntil);
	}

	@Override
	public Long getPostsReachViralCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsReachViralCount(pageId, dateFrom, dateUntil);
	}

	@Override
	public Long getPostsReachPaidCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsReachPaidCount(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getPostsReachCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsReachCount(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getAverageReachOfPostsPerDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAverageReachOfPostsPerDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<SumByDate> getPostsImpressionsByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsImpressionsByDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<SumByDate> getPostsImpressionsPaid(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsImpressionsPaid(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<SumByDate> getPostsImpressionsOrganic(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsImpressionsOrganic(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<SumByDate> getPostsImpressionsViral(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsImpressionsViral(pageId, dateFrom, dateUntil);
	}

	@Override
	public Long getPostsImpressionsOrganicCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsImpressionsOrganicCount(pageId, dateFrom, dateUntil);
	}

	@Override
	public Long getPostsImpressionsViralCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsImpressionsViralCount(pageId, dateFrom, dateUntil);
	}

	@Override
	public Long getPostsImpressionsPaidCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsImpressionsPaidCount(pageId, dateFrom, dateUntil);
	}

	@Override
	public Long getPostsImpressionsCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsImpressionsCount(pageId, dateFrom, dateUntil);
	}

	@Override
	public Long getAverageImpressionsOfPostsPerDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAverageImpressionsOfPostsPerDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<SumByDate> getPostsStoriesLikeByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsStoriesLikeByDay(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<SumByDate> getPostsStoriesCommentByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsStoriesCommentByDay(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<SumByDate> getPostsStoriesShareByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsStoriesShareByDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getPostsStoriesCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsStoriesCount(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Integer getPostsStoriesLikePercentage(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsStoriesLikePercentage(pageId, dateFrom, dateUntil);
	}

	@Override
	public Integer getPostsStoriesCommentPercentage(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsStoriesCommentPercentage(pageId, dateFrom, dateUntil);
	}

	@Override
	public Integer getPostsStoriesSharePercentage(String pageId, Long dateFrom,Long dateUntil) {
		return dao.getPostsStoriesSharePercentage(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Map<String, Long> getPostsStoriesLikeByStatusType(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsStoriesLikeByStatusType(pageId, dateFrom, dateUntil);
	}

	@Override
	public Map<String, Long> getPostsStoriesCommentByStatusType(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsStoriesCommentByStatusType(pageId, dateFrom, dateUntil);
	}

	@Override
	public Map<String, Long> getPostsStoriesShareByStatusType(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsStoriesShareByStatusType(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<SumByDate> getPostsConsumptionsByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsConsumptionsByDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<SumByDate> getPostsConsumptionsPhotoViewByDay(String pageId,Long dateFrom, Long dateUntil) {
		return dao.getPostsConsumptionsPhotoViewByDay(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<SumByDate> getPostsConsumptionsLinkClicksByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsConsumptionsLinkClicksByDay(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<SumByDate> getPostsConsumptionsVideoPlaysByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsConsumptionsVideoPlaysByDay(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<SumByDate> getPostsConsumptionsOtherClicksByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsConsumptionsOtherClicksByDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getPostsConsumptionsCount(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsConsumptionsCount(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Integer getPostsConsumptionsPhotoViewPercentage(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsConsumptionsPhotoViewPercentage(pageId, dateFrom, dateUntil);
	}

	@Override
	public Integer getPostsConsumptionsLinkClicksPercentage(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsConsumptionsLinkClicksPercentage(pageId, dateFrom, dateUntil);
	}

	@Override
	public Integer getPostsConsumptionsVideoPlaysPercentage(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsConsumptionsVideoPlaysPercentage(pageId, dateFrom, dateUntil);
	}

	@Override
	public Integer getPostsConsumptionsOtherClicksPercentage(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsConsumptionsOtherClicksPercentage(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getAverageConsumptionsPhotoViewPerPost(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAverageConsumptionsPhotoViewPerPost(pageId, dateFrom, dateUntil);
	}

	@Override
	public Long getAverageConsumptionsLinkClicksPerPost(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAverageConsumptionsLinkClicksPerPost(pageId, dateFrom, dateUntil);
	}

	@Override
	public Long getAverageConsumptionsVideoPlaysPerPost(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAverageConsumptionsVideoPlaysPerPost(pageId, dateFrom, dateUntil);
	}

	@Override
	public Long getAverageConsumptionsOtherClicksPerPost(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAverageConsumptionsOtherClicksPerPost(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Map<String, Long> getAveragePostReachByPostType(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAveragePostReachByPostType(pageId, dateFrom, dateUntil);
	}

	@Override
	public Map<String, Long> getAveragePostInteractionsByPostType(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAveragePostInteractionsByPostType(pageId, dateFrom, dateUntil);
	}

	@Override
	public Map<String, Integer> getEngagementPercentageByPostType(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getEngagementPercentageByPostType(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Map<String, Long> getPostInsights(String postId) {
		return dao.getPostInsights(postId);
	}
	
	@Override
	public List<SumByDate> getInsightsByMonth(String pageId, String property) {
		return dao.getInsightsByMonth(pageId, property);
	}
	
	@Override
	public Map<String, Long> getPostInsightsDashboard(String postId) {
		return dao.getPostInsightsDashboard(postId);
	}
	
	@Override
	public Map<String, Long> getPostInsightsHistorical(String postId) {
		return dao.getPostInsightsHistorical(postId);
	}
	
	@Override
	public Integer getPostsInteractionPercentage(String pageId, String property) {
		return dao.getPostsInteractionPercentage(pageId, property);
	}
	
	@Override
	public SumByDate getMostEngagedMonth(String pageId) {
		return dao.getMostEngagedMonth(pageId);
	}

	@Override
	public AbstractDAO<FacebookPostInsight> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO getDAO() {
		return dao;
	}

	@Override
	public List<AgeGroup> getVideoTotalTimeByAgeGender(String pageId, Long dateFrom, Long dateUntil, Character gender) {
		return dao.getVideoTotalTimeByAgeGender(pageId, dateFrom, dateUntil, gender);
	}
	
	@Override
	public List<PropertyValue> getVideoRetention(String postId, String type) {
		return dao.getVideoRetention(postId, type);
	}

	@Override
	public Map<String, Long> getPostVideoInsights(String postId) {
		return dao.getPostVideoInsights(postId);
	}
	
	@Override
	public List<AgeGroup> getVideoTotalTimeByAgeGender(String postId, Character gender) {
		return dao.getVideoTotalTimeByAgeGender(postId, gender);
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
