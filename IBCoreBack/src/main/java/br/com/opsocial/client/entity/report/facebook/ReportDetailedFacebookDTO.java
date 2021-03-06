package br.com.opsocial.client.entity.report.facebook;

import java.util.List;
import java.util.Map;

import br.com.opsocial.client.entity.primitive.Date;
import br.com.opsocial.client.entity.report.ReportDetailedDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;

public class ReportDetailedFacebookDTO extends Persistent {

	private static final long serialVersionUID = 1L;

	public static final Integer FIFTEEN = 15;
	public static final Integer THIRTY = 30;
	public static final Integer FOURTY_FIVE = 45;
	public static final Integer SIXTY = 60;
	
	private Date dateFrom;
	private Date dateUntil;
	
	private Map<String, Long> sumsByProperty;
	private Map<String, Long> valuesByProperty;
	
	//Detailed of Facebook 
	private List<ReportDetailedDTO> pageNewFans;
	private List<ReportDetailedDTO> pageFanRemoves;
	private List<ReportDetailedDTO> pageImpressions;
	private List<ReportDetailedDTO> pageImpressionsUnique;
	private List<ReportDetailedDTO> pageStories;
	private List<ReportDetailedDTO> pageStorytellers;
	
	public static final String PAGE_NEW_FANS = "pageNewFans";
	public static final String PAGE_FAN_REMOVES = "pageFanRemoves";
	public static final String PAGE_VIEWS = "pageViews";

	public static final String PAGE_IMPRESSIONS = "pageImpressions";
	public static final String PAGE_IMPRESSIONS_UNIQUE = "pageImpressionsUnique";
	public static final String PAGE_IMPRESSIONS_PAID = "pageImpressionsPaid";
	public static final String PAGE_IMPRESSIONS_ORGANIC = "pageImpressionsOrganic";
	public static final String PAGE_IMPRESSIONS_VIRAL = "pageImpressionsViral";

	public static final String PAGE_IMPRESSIONS_MALE_13_AMONG_17 = "pageImpressionsMale13Among17";
	public static final String PAGE_IMPRESSIONS_MALE_18_AMONG_24 = "pageImpressionsMale18Among24";
	public static final String PAGE_IMPRESSIONS_MALE_25_AMONG_34 = "pageImpressionsMale25Among34";
	public static final String PAGE_IMPRESSIONS_MALE_35_AMONG_44 = "pageImpressionsMale35Among44";
	public static final String PAGE_IMPRESSIONS_MALE_45_AMONG_54 = "pageImpressionsMale45Among54";
	public static final String PAGE_IMPRESSIONS_MALE_55_AMONG_64 = "pageImpressionsMale55Among64";
	public static final String PAGE_IMPRESSIONS_MALE_65_PLUS = "pageImpressionsMale65Plus";

	public static final String PAGE_IMPRESSIONS_FEMALE_13_AMONG_17 = "pageImpressionsFemale13Among17";
	public static final String PAGE_IMPRESSIONS_FEMALE_18_AMONG_24 = "pageImpressionsFemale18Among24";
	public static final String PAGE_IMPRESSIONS_FEMALE_25_AMONG_34 = "pageImpressionsFemale25Among34";
	public static final String PAGE_IMPRESSIONS_FEMALE_35_AMONG_44 = "pageImpressionsFemale35Among44";
	public static final String PAGE_IMPRESSIONS_FEMALE_45_AMONG_54 = "pageImpressionsFemale45Among54";
	public static final String PAGE_IMPRESSIONS_FEMALE_55_AMONG_64 = "pageImpressionsFemale55Among64";
	public static final String PAGE_IMPRESSIONS_FEMALE_65_PLUS = "pageImpressionsFemale65Plus";

	public static final String PAGE_FANS_MALE_13_AMONG_17 = "pageFansMale13Among17";
	public static final String PAGE_FANS_MALE_18_AMONG_24 = "pageFansMale18Among24";
	public static final String PAGE_FANS_MALE_25_AMONG_34 = "pageFansMale25Among34";
	public static final String PAGE_FANS_MALE_35_AMONG_44 = "pageFansMale35Among44";
	public static final String PAGE_FANS_MALE_45_AMONG_54 = "pageFansMale45Among54";
	public static final String PAGE_FANS_MALE_55_AMONG_64 = "pageFansMale55Among64";
	public static final String PAGE_FANS_MALE_65_PLUS = "pageFansMale65Plus";

	public static final String PAGE_FANS_FEMALE_13_AMONG_17 = "pageFansFemale13Among17";
	public static final String PAGE_FANS_FEMALE_18_AMONG_24 = "pageFansFemale18Among24";
	public static final String PAGE_FANS_FEMALE_25_AMONG_34 = "pageFansFemale25Among34";
	public static final String PAGE_FANS_FEMALE_35_AMONG_44 = "pageFansFemale35Among44";
	public static final String PAGE_FANS_FEMALE_45_AMONG_54 = "pageFansFemale45Among54";
	public static final String PAGE_FANS_FEMALE_55_AMONG_64 = "pageFansFemale55Among64";
	public static final String PAGE_FANS_FEMALE_65_PLUS = "pageFansFemale65Plus";

	public static final String PAGE_CONSUMPTIONS_OTHER_CLICKS = "pageConsumptionsOtherClicks";
	public static final String PAGE_CONSUMPTIONS_LINK_CLICKS = "pageConsumptionsLinkClicks";
	public static final String PAGE_CONSUMPTIONS_PHOTO_VIEW = "pageConsumptionsPhotoView";

	public static final String PAGE_IMPRESSIONS_BY_FAN = "pageImpressionsByFan";
	public static final String PAGE_IMPRESSIONS_BY_CHECKIN = "pageImpressionsByCheckin";
	public static final String PAGE_IMPRESSIONS_BY_OTHER = "pageImpressionsByOther";
	public static final String PAGE_IMPRESSIONS_BY_USER_POST = "pageImpressionsByUserPost";
	public static final String PAGE_IMPRESSIONS_BY_PAGE_POST = "pageImpressionsByPagePost";
	public static final String PAGE_IMPRESSIONS_BY_MENTION = "pageImpressionsByMention";

	public static final String PAGE_STORIES = "pageStories";

	public static final String PAGE_STORIES_BY_FAN = "pageStoriesByFan"; 
	public static final String PAGE_STORIES_BY_CHECKIN = "pageStoriesByCheckin";
	public static final String PAGE_STORIES_BY_OTHER = "pageStoriesByOther";
	public static final String PAGE_STORIES_BY_USER_POST = "pageStoriesByUserPost";
	public static final String PAGE_STORIES_BY_PAGE_POST = "pageStoriesByPagePost";
	public static final String PAGE_STORIES_BY_MENTION = "pageStoriesByMention";
	
	public static final String PAGE_FANS_PAGE_PROFILE = "pageFansPageProfile";
	public static final String PAGE_FANS_HOVERCARD = "pageFansHovercard";
	public static final String PAGE_FANS_MOBILE = "pageFansMobile";
	public static final String PAGE_FANS_EXTERNAL_CONNECT = "pageFansExternalConnect";
	public static final String PAGE_FANS_ADS = "pageFansAds";
	public static final String PAGE_FANS_LIKE_STORY = "pageFansLikeStory";
	public static final String PAGE_FANS_PHOTO_SNOWLIFT = "pageFansPhotoSnowlift";
	public static final String PAGE_FANS_PAGE_INVITE = "pageFansPageInvite";
	public static final String PAGE_FANS_RECOMMENDED_PAGES = "pageFansRecommendedPages";
	public static final String PAGE_FANS_SEARCH = "pageFansSearch";
	
	public static final String PAGE_FANS_PAGE_SUGGESTION = "pageFansPageSuggestion";
	public static final String PAGE_FANS_PAGE_TIMELINE = "pageFansPageTimeline";
	public static final String PAGE_FANS_MOBILE_ADS = "pageFansMobileAds";
	public static final String PAGE_FANS_REGISTRATION = "pageFansRegistration";
	public static final String PAGE_FANS_WIZARD_SUGGESTION = "pageFansWizardSuggestion";
	public static final String PAGE_FANS_PROFILE_CONNECT = "pageFansProfileConnect";
	public static final String PAGE_FANS_FAVORITES = "pageFansFavorites";
	public static final String PAGE_FANS_API = "pageFansApi";
	public static final String PAGE_FANS_PAGE_BROWSER = "pageFansPageBrowser";
	public static final String PAGE_FANS_MOBILE_PAGE_BROWSER = "pageFansMobilePageBrowser";
	public static final String PAGE_FANS_TICKER = "pageFansTicker";
	public static final String PAGE_FANS_FEED_CHAINING = "pageFansFeedChaining";
	public static final String PAGE_FANS_ALL_CATEGORY_PYML = "pageFansAllCategoryPyml";
	public static final String PAGE_FANS_PAGE_SUGGESTIONS_ON_LIKING = "pageFansPageSuggestionsOnLiking";
	public static final String PAGE_FANS_MOBILE_PAGE_SUGGESTIONS_ON_LIKING = "pageFansMobilePageSuggestionsOnLiking";
	public static final String PAGE_FANS_FAN_CONTEXT_STORY= "pageFansFanContextStory";
	public static final String PAGE_FANS_SPONSORED_STORY = "pageFansSponsoredStory";
	public static final String PAGE_FANS_PAGE_INVITE_ESCAPE_HATCH_FINCH = "pageFansPageInviteEscapeHatchFinch";
	
	public static final String PAGE_FANS_FROM_PAGES = "pageFansFromPages";
	public static final String PAGE_FANS_FROM_RECOMMENDATIONS = "pageFansFromRecommendations";
	public static final String PAGE_FANS_FROM_LIKE_SUGGESTIONS = "pageFansFromLikeSuggestions";
	
	public static final String PAGE_FANS_ONLINE = "page_fans_online";
	public static final String PAGE_FANS_ONLINE_0 = "page_fans_online_0";
	public static final String PAGE_FANS_ONLINE_1 = "page_fans_online_1";
	public static final String PAGE_FANS_ONLINE_2 = "page_fans_online_2";
	public static final String PAGE_FANS_ONLINE_3 = "page_fans_online_3";
	public static final String PAGE_FANS_ONLINE_4 = "page_fans_online_4";
	public static final String PAGE_FANS_ONLINE_5 = "page_fans_online_5";
	public static final String PAGE_FANS_ONLINE_6 = "page_fans_online_6";
	public static final String PAGE_FANS_ONLINE_7 = "page_fans_online_7";
	public static final String PAGE_FANS_ONLINE_8 = "page_fans_online_8";
	public static final String PAGE_FANS_ONLINE_9 = "page_fans_online_9";
	public static final String PAGE_FANS_ONLINE_10 = "page_fans_online_10";
	public static final String PAGE_FANS_ONLINE_11 = "page_fans_online_11";
	public static final String PAGE_FANS_ONLINE_12 = "page_fans_online_12";
	public static final String PAGE_FANS_ONLINE_13 = "page_fans_online_13";
	public static final String PAGE_FANS_ONLINE_14 = "page_fans_online_14";
	public static final String PAGE_FANS_ONLINE_15 = "page_fans_online_15";
	public static final String PAGE_FANS_ONLINE_16 = "page_fans_online_16";
	public static final String PAGE_FANS_ONLINE_17 = "page_fans_online_17";
	public static final String PAGE_FANS_ONLINE_18 = "page_fans_online_18";
	public static final String PAGE_FANS_ONLINE_19 = "page_fans_online_19";
	public static final String PAGE_FANS_ONLINE_20 = "page_fans_online_20";
	public static final String PAGE_FANS_ONLINE_21 = "page_fans_online_21";
	public static final String PAGE_FANS_ONLINE_22 = "page_fans_online_22";
	public static final String PAGE_FANS_ONLINE_23 = "page_fans_online_23";
	
	private List<ReportFacebookCityDTO> reportFacebookCitiesImp;
	private List<ReportFacebookCityDTO> reportFacebookCitiesFan;
	
	private Profile facePageDTO;
	private List<FaceReportPostDTO> faceReportPosts;
	
	public ReportDetailedFacebookDTO() {
	}
	
	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateUntil() {
		return dateUntil;
	}
	
	public void setDateUntil(Date dateUntil) {
		this.dateUntil = dateUntil;
	}

	public Map<String, Long> getSumsByProperty() {
		return sumsByProperty;
	}
	
	public void setSumsByProperty(Map<String, Long> sumsByProperty) {
		this.sumsByProperty = sumsByProperty;
	}
	
	public Map<String, Long> getValuesByProperty() {
		return valuesByProperty;
	}
	
	public void setValuesByProperty(Map<String, Long> valuesByProperty) {
		this.valuesByProperty = valuesByProperty;
	}

	public List<ReportDetailedDTO> getPageNewFans() {
		return pageNewFans;
	}

	public void setPageNewFans(List<ReportDetailedDTO> pageNewFans) {
		this.pageNewFans = pageNewFans;
	}
	
	public List<ReportDetailedDTO> getPageFanRemoves() {
		return pageFanRemoves;
	}
	
	public void setPageFanRemoves(List<ReportDetailedDTO> pageFanRemoves) {
		this.pageFanRemoves = pageFanRemoves;
	}
	
	public List<ReportDetailedDTO> getPageImpressions() {
		return pageImpressions;
	}
	
	public void setPageImpressions(List<ReportDetailedDTO> pageImpressions) {
		this.pageImpressions = pageImpressions;
	}
	
	public List<ReportDetailedDTO> getPageImpressionsUnique() {
		return pageImpressionsUnique;
	}
	
	public void setPageImpressionsUnique(
			List<ReportDetailedDTO> pageImpressionsUnique) {
		this.pageImpressionsUnique = pageImpressionsUnique;
	}
	
	public List<ReportDetailedDTO> getPageStories() {
		return pageStories;
	}
	
	public void setPageStories(List<ReportDetailedDTO> pageStories) {
		this.pageStories = pageStories;
	}
	
	public List<ReportDetailedDTO> getPageStorytellers() {
		return pageStorytellers;
	}
	
	public void setPageStorytellers(List<ReportDetailedDTO> pageStorytellers) {
		this.pageStorytellers = pageStorytellers;
	}

	public List<ReportFacebookCityDTO> getReportFacebookCitiesImp() {
		return reportFacebookCitiesImp;
	}
	
	public void setReportFacebookCitiesImp(
			List<ReportFacebookCityDTO> reportFacebookCitiesImp) {
		this.reportFacebookCitiesImp = reportFacebookCitiesImp;
	}
	
	public List<ReportFacebookCityDTO> getReportFacebookCitiesFan() {
		return reportFacebookCitiesFan;
	}
	
	public void setReportFacebookCitiesFan(
			List<ReportFacebookCityDTO> reportFacebookCitiesFan) {
		this.reportFacebookCitiesFan = reportFacebookCitiesFan;
	}
	
	public Profile getFacePageDTO() {
		return facePageDTO;
	}

	public void setFacePageDTO(Profile facePageDTO) {
		this.facePageDTO = facePageDTO;
	}

	public List<FaceReportPostDTO> getFaceReportPosts() {
		return faceReportPosts;
	}

	public void setFaceReportPosts(List<FaceReportPostDTO> faceReportPosts) {
		this.faceReportPosts = faceReportPosts;
	}
}
