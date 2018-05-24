package br.com.opsocial.server.utils.reports;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import br.com.opsocial.ejb.das.MaintenanceFaceReportLocationRemote;
import br.com.opsocial.ejb.das.MaintenanceReportControlRemote;
import br.com.opsocial.ejb.das.MaintenanceReportDetailedRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.FaceReportLocation;
import br.com.opsocial.ejb.entity.report.ReportControl;
import br.com.opsocial.ejb.entity.report.ReportDetailed;
import br.com.opsocial.ejb.entity.socialnetworks.facebook.Insights;
import br.com.opsocial.ejb.entity.socialnetworks.facebook.PageConsumptionsType;
import br.com.opsocial.ejb.entity.socialnetworks.facebook.PageFansGenderAge;
import br.com.opsocial.ejb.entity.socialnetworks.facebook.PageFansLikeSource;
import br.com.opsocial.ejb.entity.socialnetworks.facebook.PageImpressionsGenderAge;
import br.com.opsocial.ejb.entity.socialnetworks.facebook.PageImpressionsStoryType;
import br.com.opsocial.ejb.entity.socialnetworks.facebook.PageNegativeFeedbacksType;
import br.com.opsocial.ejb.entity.socialnetworks.facebook.PagePositiveFeedbacksType;
import br.com.opsocial.ejb.entity.socialnetworks.facebook.PageStoriesStoryType;
import facebook4j.Facebook;
import facebook4j.Insight;
import facebook4j.Insight.Value;
import facebook4j.FacebookException;
import facebook4j.RawAPIResponse;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

public class ReportFacebook {

	private MaintenanceReportDetailedRemote reportDetailedRemote;
	private MaintenanceFaceReportLocationRemote faceReportLocationRemote;
	private MaintenanceReportControlRemote reportControlRemote;
	private String pageId;

	private static final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

	private String accessToken;
	
	private static final String PERIOD_DAY = "day";
	private static final String PERIOD_LIFETIME = "lifetime";
	
	public static final String PAGE_FANS = "pageFans";
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
	public static final String PAGE_FANS_ONLINE_PER_DAY = "page_fans_online_per_day";

	public static final String PAGE_NEW_FANS = "pageNewFans";
	public static final String PAGE_FAN_REMOVES = "pageFanRemoves";
	
	public static final String PAGE_VIEWS = "pageViews";
	public static final String PAGE_VIEWS_EXTERNAL_REFERRALS = "pageViewsExternalReferrals";
	
	public static final String PAGE_POSTS_IMPRESSIONS = "pagePostsImpressions";
	public static final String PAGE_POSTS_IMPRESSIONS_UNIQUE = "pagePostsImpressionsUnique";
	
	public static final String PAGE_POSTS_IMPRESSIONS_PAID = "page_posts_impressions_paid";
	public static final String PAGE_POSTS_IMPRESSIONS_PAID_UNIQUE = "page_posts_impressions_paid_unique";
	public static final String PAGE_POSTS_IMPRESSIONS_ORGANIC = "page_posts_impressions_organic";
	public static final String PAGE_POSTS_IMPRESSIONS_ORGANIC_UNIQUE = "page_posts_impressions_organic_unique";
	public static final String PAGE_POSTS_IMPRESSIONS_VIRAL = "page_posts_impressions_viral";
	public static final String PAGE_POSTS_IMPRESSIONS_VIRAL_UNIQUE = "page_posts_impressions_viral_unique";
	
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

	public static final String PAGE_CONSUMPTIONS = "page_consumptions";
	public static final String PAGE_CONSUMPTIONS_OTHER_CLICKS = "pageConsumptionsOtherClicks";
	public static final String PAGE_CONSUMPTIONS_LINK_CLICKS = "pageConsumptionsLinkClicks";
	public static final String PAGE_CONSUMPTIONS_PHOTO_VIEW = "pageConsumptionsPhotoView";
	public static final String PAGE_CONSUMPTIONS_VIDEO_PLAY = "pageConsumptionsVideoPlay";

	public static final String PAGE_IMPRESSIONS_BY_FAN = "pageImpressionsByFan";
	public static final String PAGE_IMPRESSIONS_BY_CHECKIN = "pageImpressionsByCheckin";
	public static final String PAGE_IMPRESSIONS_BY_OTHER = "pageImpressionsByOther";
	public static final String PAGE_IMPRESSIONS_BY_USER_POST = "pageImpressionsByUserPost";
	public static final String PAGE_IMPRESSIONS_BY_PAGE_POST = "pageImpressionsByPagePost";
	public static final String PAGE_IMPRESSIONS_BY_MENTION = "pageImpressionsByMention";
	public static final String PAGE_IMPRESSIONS_BY_COUPON = "pageImpressionsByCoupon";
	public static final String PAGE_IMPRESSIONS_BY_EVENT = "pageImpressionsByEvent";
	public static final String PAGE_IMPRESSIONS_BY_QUESTION = "pageImpressionsByQuestion";

	public static final String PAGE_STORIES = "pageStories";

	public static final String PAGE_POSITIVE_FEEDBACK_BY_TYPE = "page_positive_feedback_by_type";
	
	public static final String PAGE_POSITIVE_FEEDBACK_LIKE = "page_positive_feedback_like";
	public static final String PAGE_POSITIVE_FEEDBACK_COMMENT = "page_positive_feedback_comment";
	public static final String PAGE_POSITIVE_FEEDBACK_LINK = "page_positive_feedback_link";
	
	public static final String PAGE_NEGATIVE_FEEDBACK_BY_TYPE = "page_negative_feedback_by_type";
	
	public static final String PAGE_NEGATIVE_FEEDBACK_HIDE_CLICKS = "page_negative_feedback_hide_clicks";
	public static final String PAGE_NEGATIVE_FEEDBACK_HIDE_ALL_CLICKS = "page_negative_feedback_hide_all_clicks";
	public static final String PAGE_NEGATIVE_FEEDBACK_REPORT_SPAM_CLICKS = "page_negative_feedback_report_spam_clicks";
	
	public static final String PAGE_ACTIONS_POST_REACTIONS_LIKE_TOTAL = "page_actions_post_reactions_like_total";
	public static final String PAGE_ACTIONS_POST_REACTIONS_LOVE_TOTAL = "page_actions_post_reactions_love_total";
	public static final String PAGE_ACTIONS_POST_REACTIONS_WOW_TOTAL = "page_actions_post_reactions_wow_total";
	public static final String PAGE_ACTIONS_POST_REACTIONS_HAHA_TOTAL = "page_actions_post_reactions_haha_total";
	public static final String PAGE_ACTIONS_POST_REACTIONS_SORRY_TOTAL = "page_actions_post_reactions_sorry_total";
	public static final String PAGE_ACTIONS_POST_REACTIONS_ANGER_TOTAL = "page_actions_post_reactions_anger_total";
	
	public static final String PAGE_VIDEO_VIEWS = "page_video_views";
	public static final String PAGE_VIDEO_VIEWS_ORGANIC = "page_video_views_organic";
	public static final String PAGE_VIDEO_VIEWS_PAID = "page_video_views_paid";
	
	public static final String PAGE_VIDEO_VIEW_TIME = "page_video_view_time";
	public static final String PAGE_VIDEO_REPEAT_VIEWS = "page_video_repeat_views";
	
	public static final String PAGE_VIDEO_COMPLETE_VIEWS_30S = "page_video_complete_views_30s";
	public static final String PAGE_VIDEO_COMPLETE_VIEWS_30S_PAID = "page_video_complete_views_30s_paid";
	public static final String PAGE_VIDEO_COMPLETE_VIEWS_30S_ORGANIC = "page_video_complete_views_30s_organic";
	
	public static final String PAGE_VIDEO_VIEWS_10S = "page_video_views_10s";
	public static final String PAGE_VIDEO_VIEWS_10S_PAID = "page_video_views_10s_paid";
	public static final String PAGE_VIDEO_VIEWS_10S_ORGANIC = "page_video_views_10s_organic";
	
	public static final String PAGE_VIDEO_VIEWS_AUTOPLAYED = "page_video_views_autoplayed";
	public static final String PAGE_VIDEO_VIEWS_CLICK_TO_PLAY = "page_video_views_click_to_play";
	
	public static final String PAGE_STORIES_BY_FAN = "pageStoriesByFan"; 
	public static final String PAGE_STORIES_BY_CHECKIN = "pageStoriesByCheckin";
	public static final String PAGE_STORIES_BY_OTHER = "pageStoriesByOther";
	public static final String PAGE_STORIES_BY_USER_POST = "pageStoriesByUserPost";
	public static final String PAGE_STORIES_BY_PAGE_POST = "pageStoriesByPagePost";
	public static final String PAGE_STORIES_BY_MENTION = "pageStoriesByMention";
	public static final String PAGE_STORIES_BY_COUPON = "pageStoriesByCoupon";
	public static final String PAGE_STORIES_BY_EVENT = "pageStoriesByEvent";
	public static final String PAGE_STORIES_BY_QUESTION = "pageStoriesByQuestion";
	
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

	public static final String GROUP_UNIQUE = "unique";
	public static final String GROUP_IMP_GENDER_AGE = "imp_gender_age";
	public static final String GROUP_IMP_STORY_TYPE = "imp_story_type";
	public static final String GROUP_CON_TYPE = "con_type";
	public static final String GROUP_STORIES_TYPE = "stories_type";
	public static final String GROUP_FANS_LIKE_SOURCE = "fans_like_source";
	public static final String GROUP_FANS_GENDER_AGE = "fans_gender_age";
	public static final String GROUP_FANS_ONLINE = "fans_online";
	public static final String GROUP_POSITIVE_FEEDBACK = "positive_feedback";
	public static final String GROUP_NEGATIVE_FEEDBACK = "negative_feedback";

	public static final String GROUP_IMP_COUNTRY = "imp_country";
	public static final String GROUP_IMP_CITY = "imp_city";
	public static final String GROUP_FAN_CITY = "fan_city";
	public static final String GROUP_FAN_COUNTRY ="fan_country";
	public static final String GROUP_VIEWS_EXTERNAL = "views_external";
	public static final String GROUP_MAIN = "main";

	public static final String REPORT_DATE_FORMAT = "yyyy-MM-dd";

	public static final Character UNAVAILABLE = 'U';
	public static final Character GENERATING = 'G';
	public static final Character FINALIZED = 'F';
	public static final Character REFRESH_TOKEN = 'R';
	
	private Map<String, String> insightsMetricsMap;
	private Map<String, String> insightsMetricsMapInverse;

	public ReportFacebook() {
	}

	public ReportFacebook(String idPage, String accessToken) {
		
		this.pageId = idPage;
		this.accessToken = accessToken;
		
		initiateInsightsMetricsMap();
		initiateInsightsMetricsMapInverse();
	}
	
	private void initiateInsightsMetricsMap() {
		
		insightsMetricsMap = new HashMap<String, String>();
		insightsMetricsMap.put("page_fan_adds", PAGE_NEW_FANS);
		insightsMetricsMap.put("page_impressions_unique", PAGE_IMPRESSIONS_UNIQUE);
		insightsMetricsMap.put("page_views_total", PAGE_VIEWS);
		insightsMetricsMap.put("page_fan_removes", PAGE_FAN_REMOVES);
		insightsMetricsMap.put("page_impressions", PAGE_IMPRESSIONS);
		insightsMetricsMap.put("page_stories", PAGE_STORIES);
		insightsMetricsMap.put("page_impressions_organic", PAGE_IMPRESSIONS_ORGANIC);
		insightsMetricsMap.put("page_impressions_paid", PAGE_IMPRESSIONS_PAID);
		insightsMetricsMap.put("page_impressions_viral", PAGE_IMPRESSIONS_VIRAL);
		insightsMetricsMap.put("page_posts_impressions", PAGE_POSTS_IMPRESSIONS);
		insightsMetricsMap.put("page_posts_impressions_unique", PAGE_POSTS_IMPRESSIONS_UNIQUE);
		insightsMetricsMap.put(PAGE_FANS_ONLINE_PER_DAY, PAGE_FANS_ONLINE_PER_DAY);
		insightsMetricsMap.put("page_fans", PAGE_FANS);
		insightsMetricsMap.put(PAGE_CONSUMPTIONS, PAGE_CONSUMPTIONS);
		insightsMetricsMap.put(PAGE_ACTIONS_POST_REACTIONS_LIKE_TOTAL, PAGE_ACTIONS_POST_REACTIONS_LIKE_TOTAL);
		insightsMetricsMap.put(PAGE_ACTIONS_POST_REACTIONS_LOVE_TOTAL, PAGE_ACTIONS_POST_REACTIONS_LOVE_TOTAL);
		insightsMetricsMap.put(PAGE_ACTIONS_POST_REACTIONS_WOW_TOTAL, PAGE_ACTIONS_POST_REACTIONS_WOW_TOTAL);
		insightsMetricsMap.put(PAGE_ACTIONS_POST_REACTIONS_HAHA_TOTAL, PAGE_ACTIONS_POST_REACTIONS_HAHA_TOTAL);
		insightsMetricsMap.put(PAGE_ACTIONS_POST_REACTIONS_SORRY_TOTAL, PAGE_ACTIONS_POST_REACTIONS_SORRY_TOTAL);
		insightsMetricsMap.put(PAGE_ACTIONS_POST_REACTIONS_ANGER_TOTAL, PAGE_ACTIONS_POST_REACTIONS_ANGER_TOTAL);
		insightsMetricsMap.put(PAGE_POSTS_IMPRESSIONS_ORGANIC, PAGE_POSTS_IMPRESSIONS_ORGANIC);
		insightsMetricsMap.put(PAGE_POSTS_IMPRESSIONS_ORGANIC_UNIQUE, PAGE_POSTS_IMPRESSIONS_ORGANIC_UNIQUE);
		insightsMetricsMap.put(PAGE_POSTS_IMPRESSIONS_PAID, PAGE_POSTS_IMPRESSIONS_PAID);
		insightsMetricsMap.put(PAGE_POSTS_IMPRESSIONS_PAID_UNIQUE, PAGE_POSTS_IMPRESSIONS_PAID_UNIQUE);
		insightsMetricsMap.put(PAGE_POSTS_IMPRESSIONS_VIRAL, PAGE_POSTS_IMPRESSIONS_VIRAL);
		insightsMetricsMap.put(PAGE_POSTS_IMPRESSIONS_VIRAL_UNIQUE, PAGE_POSTS_IMPRESSIONS_VIRAL_UNIQUE);
		insightsMetricsMap.put(PAGE_VIDEO_VIEWS, PAGE_VIDEO_VIEWS);
		insightsMetricsMap.put(PAGE_VIDEO_VIEWS_ORGANIC, PAGE_VIDEO_VIEWS_ORGANIC);
		insightsMetricsMap.put(PAGE_VIDEO_VIEWS_PAID, PAGE_VIDEO_VIEWS_PAID);
		insightsMetricsMap.put(PAGE_VIDEO_VIEW_TIME, PAGE_VIDEO_VIEW_TIME);
		insightsMetricsMap.put(PAGE_VIDEO_REPEAT_VIEWS, PAGE_VIDEO_REPEAT_VIEWS);
		insightsMetricsMap.put(PAGE_VIDEO_COMPLETE_VIEWS_30S, PAGE_VIDEO_COMPLETE_VIEWS_30S);
		insightsMetricsMap.put(PAGE_VIDEO_COMPLETE_VIEWS_30S_PAID, PAGE_VIDEO_COMPLETE_VIEWS_30S_PAID);
		insightsMetricsMap.put(PAGE_VIDEO_COMPLETE_VIEWS_30S_ORGANIC, PAGE_VIDEO_COMPLETE_VIEWS_30S_ORGANIC);
		insightsMetricsMap.put(PAGE_VIDEO_VIEWS_10S, PAGE_VIDEO_VIEWS_10S);
		insightsMetricsMap.put(PAGE_VIDEO_VIEWS_10S_PAID, PAGE_VIDEO_VIEWS_10S_PAID);
		insightsMetricsMap.put(PAGE_VIDEO_VIEWS_10S_ORGANIC, PAGE_VIDEO_VIEWS_10S_ORGANIC);
		insightsMetricsMap.put(PAGE_VIDEO_VIEWS_AUTOPLAYED, PAGE_VIDEO_VIEWS_AUTOPLAYED);
		insightsMetricsMap.put(PAGE_VIDEO_VIEWS_CLICK_TO_PLAY, PAGE_VIDEO_VIEWS_CLICK_TO_PLAY);
		
	}
	
	private void initiateInsightsMetricsMapInverse() {
		
		insightsMetricsMapInverse = new HashMap<String, String>();
		insightsMetricsMapInverse.put(PAGE_NEW_FANS, "page_fan_adds");
		insightsMetricsMapInverse.put(PAGE_IMPRESSIONS_UNIQUE, "page_impressions_unique");
		insightsMetricsMapInverse.put(PAGE_VIEWS, "page_views_total");
		insightsMetricsMapInverse.put(PAGE_FAN_REMOVES, "page_fan_removes");
		insightsMetricsMapInverse.put(PAGE_IMPRESSIONS, "page_impressions");
		insightsMetricsMapInverse.put(PAGE_STORIES, "page_stories");
		insightsMetricsMapInverse.put(PAGE_IMPRESSIONS_ORGANIC, "page_impressions_organic");
		insightsMetricsMapInverse.put(PAGE_IMPRESSIONS_PAID, "page_impressions_paid");
		insightsMetricsMapInverse.put(PAGE_IMPRESSIONS_VIRAL, "page_impressions_viral");
		insightsMetricsMapInverse.put(PAGE_POSTS_IMPRESSIONS, "page_posts_impressions");
		insightsMetricsMapInverse.put(PAGE_POSTS_IMPRESSIONS_UNIQUE, "page_posts_impressions_unique");
		insightsMetricsMapInverse.put(PAGE_FANS_ONLINE_PER_DAY, PAGE_FANS_ONLINE_PER_DAY);
		insightsMetricsMapInverse.put(PAGE_FANS, "page_fans");
		insightsMetricsMapInverse.put(PAGE_CONSUMPTIONS, PAGE_CONSUMPTIONS);
		insightsMetricsMapInverse.put(PAGE_ACTIONS_POST_REACTIONS_LIKE_TOTAL, PAGE_ACTIONS_POST_REACTIONS_LIKE_TOTAL);
		insightsMetricsMapInverse.put(PAGE_ACTIONS_POST_REACTIONS_LOVE_TOTAL, PAGE_ACTIONS_POST_REACTIONS_LOVE_TOTAL);
		insightsMetricsMapInverse.put(PAGE_ACTIONS_POST_REACTIONS_WOW_TOTAL, PAGE_ACTIONS_POST_REACTIONS_WOW_TOTAL);
		insightsMetricsMapInverse.put(PAGE_ACTIONS_POST_REACTIONS_HAHA_TOTAL, PAGE_ACTIONS_POST_REACTIONS_HAHA_TOTAL);
		insightsMetricsMapInverse.put(PAGE_ACTIONS_POST_REACTIONS_SORRY_TOTAL, PAGE_ACTIONS_POST_REACTIONS_SORRY_TOTAL);
		insightsMetricsMapInverse.put(PAGE_ACTIONS_POST_REACTIONS_ANGER_TOTAL, PAGE_ACTIONS_POST_REACTIONS_ANGER_TOTAL);
		insightsMetricsMapInverse.put(PAGE_POSTS_IMPRESSIONS_ORGANIC, PAGE_POSTS_IMPRESSIONS_ORGANIC);
		insightsMetricsMapInverse.put(PAGE_POSTS_IMPRESSIONS_ORGANIC_UNIQUE, PAGE_POSTS_IMPRESSIONS_ORGANIC_UNIQUE);
		insightsMetricsMapInverse.put(PAGE_POSTS_IMPRESSIONS_PAID, PAGE_POSTS_IMPRESSIONS_PAID);
		insightsMetricsMapInverse.put(PAGE_POSTS_IMPRESSIONS_PAID_UNIQUE, PAGE_POSTS_IMPRESSIONS_PAID_UNIQUE);
		insightsMetricsMapInverse.put(PAGE_POSTS_IMPRESSIONS_VIRAL, PAGE_POSTS_IMPRESSIONS_VIRAL);
		insightsMetricsMapInverse.put(PAGE_POSTS_IMPRESSIONS_VIRAL_UNIQUE, PAGE_POSTS_IMPRESSIONS_VIRAL_UNIQUE);
		insightsMetricsMapInverse.put(PAGE_VIDEO_VIEWS, PAGE_VIDEO_VIEWS);
		insightsMetricsMapInverse.put(PAGE_VIDEO_VIEWS_ORGANIC, PAGE_VIDEO_VIEWS_ORGANIC);
		insightsMetricsMapInverse.put(PAGE_VIDEO_VIEWS_PAID, PAGE_VIDEO_VIEWS_PAID);
		insightsMetricsMapInverse.put(PAGE_VIDEO_VIEW_TIME, PAGE_VIDEO_VIEW_TIME);
		insightsMetricsMapInverse.put(PAGE_VIDEO_REPEAT_VIEWS, PAGE_VIDEO_REPEAT_VIEWS);
		insightsMetricsMapInverse.put(PAGE_VIDEO_COMPLETE_VIEWS_30S, PAGE_VIDEO_COMPLETE_VIEWS_30S);
		insightsMetricsMapInverse.put(PAGE_VIDEO_COMPLETE_VIEWS_30S_PAID, PAGE_VIDEO_COMPLETE_VIEWS_30S_PAID);
		insightsMetricsMapInverse.put(PAGE_VIDEO_COMPLETE_VIEWS_30S_ORGANIC, PAGE_VIDEO_COMPLETE_VIEWS_30S_ORGANIC);
		insightsMetricsMapInverse.put(PAGE_VIDEO_VIEWS_10S, PAGE_VIDEO_VIEWS_10S);
		insightsMetricsMapInverse.put(PAGE_VIDEO_VIEWS_10S_PAID, PAGE_VIDEO_VIEWS_10S_PAID);
		insightsMetricsMapInverse.put(PAGE_VIDEO_VIEWS_10S_ORGANIC, PAGE_VIDEO_VIEWS_10S_ORGANIC);
		insightsMetricsMapInverse.put(PAGE_VIDEO_VIEWS_AUTOPLAYED, PAGE_VIDEO_VIEWS_AUTOPLAYED);
		insightsMetricsMapInverse.put(PAGE_VIDEO_VIEWS_CLICK_TO_PLAY, PAGE_VIDEO_VIEWS_CLICK_TO_PLAY);
	}

	public void recalculateStatistics(Map<String, List<ReportDetailed>> reportsDetailed, Long date) {

		reportDetailedRemote = (MaintenanceReportDetailedRemote) RecoverMaintenance.recoverMaintenance("ReportDetailed");
								
		if(reportsDetailed.containsKey(GROUP_UNIQUE)) {
			for(ReportDetailed reportDetailed : reportsDetailed.get(GROUP_UNIQUE)) {
				retrievePropertyValue(reportDetailed);
			}
		}

		if(reportsDetailed.containsKey(GROUP_IMP_GENDER_AGE)) {
			
			try {
				
				JsonElement jsonElement = retrievePropertyValues(pageId, date, "page_impressions_by_age_gender_unique"); 
				
				retrievePageImpressionsGenderAge(reportsDetailed.get(GROUP_IMP_GENDER_AGE), 
						jsonElement);
				
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		}

		if(reportsDetailed.containsKey(GROUP_IMP_STORY_TYPE)) {
			
			try {
				
				JsonElement jsonElement = retrievePropertyValues(pageId, date, "page_impressions_by_story_type"); 
				
				retrievePageImpressionsStoryType(reportsDetailed.get(GROUP_IMP_STORY_TYPE), 
						jsonElement);
				
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		}

		if(reportsDetailed.containsKey(GROUP_CON_TYPE)) {
			
			try {
				
				JsonElement jsonElement = retrievePropertyValues(pageId, date, "page_consumptions_by_consumption_type"); 
				
				retrievePageConsumptionsType(reportsDetailed.get(GROUP_CON_TYPE), 
						jsonElement);
				
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		}

		if(reportsDetailed.containsKey(GROUP_STORIES_TYPE)) {
			
			try {
				
				JsonElement jsonElement = retrievePropertyValues(pageId, date, "page_storytellers_by_story_type");
				
				retrievePageStoriesStoryType(reportsDetailed.get(GROUP_STORIES_TYPE), jsonElement);
				
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		}
		
		if(reportsDetailed.containsKey(GROUP_FANS_LIKE_SOURCE)) {
			
			try {
				
				JsonElement jsonElement = retrievePropertyValues(pageId, date, "page_fans_by_like_source_unique"); 
				
				retrievePageFansLikeSource(reportsDetailed.get(GROUP_FANS_LIKE_SOURCE), jsonElement);
				
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		}
		
		if(reportsDetailed.containsKey(GROUP_FANS_ONLINE)) {
			
			try {
				
				JsonElement jsonElement = retrievePropertyValues(pageId, date, PAGE_FANS_ONLINE); 
				
				retrievePageFansOnline(reportsDetailed.get(GROUP_FANS_ONLINE), jsonElement);
				
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		}
		
		if(reportsDetailed.containsKey(GROUP_POSITIVE_FEEDBACK)) {
			
			try {
				
				JsonElement jsonElement = retrievePropertyValues(pageId, date, PAGE_POSITIVE_FEEDBACK_BY_TYPE); 
				
				retrievePagePositiveFeedbackByType(reportsDetailed.get(GROUP_POSITIVE_FEEDBACK), jsonElement);
				
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		}
		
		if(reportsDetailed.containsKey(GROUP_NEGATIVE_FEEDBACK)) {
			
			try {
				
				JsonElement jsonElement = retrievePropertyValues(pageId, date, PAGE_NEGATIVE_FEEDBACK_BY_TYPE); 
				
				retrievePageNegativeFeedbackByType(reportsDetailed.get(GROUP_NEGATIVE_FEEDBACK), jsonElement);
				
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void calcStatisticsImpCity(List<Long> datesToSearch) {

		faceReportLocationRemote = (MaintenanceFaceReportLocationRemote) RecoverMaintenance.recoverMaintenance("FaceReportLocation");
		
		reportControlRemote = (MaintenanceReportControlRemote) RecoverMaintenance.recoverMaintenance("ReportControl");

		for (Long date : datesToSearch) {
			
			try {
				
				JsonElement jsonElement = retrievePropertyValues(pageId, date, "page_impressions_by_city_unique");
				
				retrievePageImpressionsByCity(date, jsonElement);
				
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void calcStatisticsImpCountry(List<Long> datesToSearch) {

		faceReportLocationRemote = (MaintenanceFaceReportLocationRemote) RecoverMaintenance.recoverMaintenance("FaceReportLocation");
		
		reportControlRemote = (MaintenanceReportControlRemote) RecoverMaintenance.recoverMaintenance("ReportControl");

		for (Long date : datesToSearch) {
			
			try {
				
				JsonElement jsonElement = retrievePropertyValues(pageId, date, "page_impressions_by_country_unique");
				
				retrievePageImpressionsByCountry(date, jsonElement);
				
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void calcStatisticsViewsExternal(List<Long> datesToSearch) {
		
		faceReportLocationRemote = (MaintenanceFaceReportLocationRemote) RecoverMaintenance.recoverMaintenance("FaceReportLocation");
		
		reportControlRemote = (MaintenanceReportControlRemote) RecoverMaintenance.recoverMaintenance("ReportControl");

		for (Long date : datesToSearch) {
			
			try {
				
				JsonElement jsonElement = retrievePropertyValues(pageId, date, "page_views_external_referrals");
				
				retrievePageViewsExternalReferrals(date, jsonElement);
				
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		}		
	}

	private void retrievePropertyValue(ReportDetailed reportDetailed) {

		FacebookIntegration facebookIntegration = new FacebookIntegration();
		Facebook facebook = facebookIntegration.getFacebook(new AccessToken(accessToken));
		
		Long dateSince = reportDetailed.getDate() + 86400L;
		Long dateUntil = dateSince;
		
		try {
			
			ResponseList<Insight> insights = facebook.getInsights(reportDetailed.getPageId(), 
					insightsMetricsMapInverse.get(reportDetailed.getProperty()),
					new Reading().addParameter("period", PERIOD_DAY)
								 .since(dateSince.toString())
								 .until(dateUntil.toString()));
			
			if(!insights.isEmpty()) {
				
				Insight insight = insights.get(0);
				
				if(!insight.getValues().isEmpty()) {
					
					try {
					
						Value value = insight.getValues().get(0);
					
						reportDetailed.setValue(value.getValue().get());
					
						reportDetailedRemote.save(reportDetailed);
						
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
			
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Networkid..: " + reportDetailed.getPageId());
			System.out.println("Property..: " + reportDetailed.getProperty());
			System.out.println("Date..: " + reportDetailed.getDate());
			e.printStackTrace();
		}
	}
	
	private JsonElement retrievePropertyValues(String pageId, Long date, String metric) throws FacebookException { 

		FacebookIntegration facebookIntegration = new FacebookIntegration();
		Facebook facebook = facebookIntegration.getFacebook(new AccessToken(accessToken));

		Long dateSince = date + 86400L;
		Long dateUntil = dateSince;
		
		JsonElement valueElement = JsonNull.INSTANCE;

		try {
			
			Map<String, String> queryParameters = new HashMap<String, String>();
			queryParameters.put("metric", metric);
			queryParameters.put("period", PERIOD_DAY);
			queryParameters.put("since", dateSince.toString());
			queryParameters.put("until", dateUntil.toString());

			RawAPIResponse res = facebook.callGetAPI(pageId + "/insights", queryParameters);

			JsonParser parser = new JsonParser();			
			JsonArray jsonResponseData = parser.parse(res.asString()).getAsJsonObject().get("data").getAsJsonArray();

			Iterator<JsonElement> insightsList = jsonResponseData.iterator();
			
			while (insightsList.hasNext() ) {

				try {

					JsonObject jsonObject = ((JsonObject) insightsList.next());

					JsonObject valuesObject = jsonObject.get("values").getAsJsonArray().get(0).getAsJsonObject();

					if(!valuesObject.has("value")) {
						valuesObject.add("value", JsonNull.INSTANCE);
					}

					valueElement = valuesObject.get("value");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		
		return valueElement;
	}
	
	public void retrieveInsightsForPeriod(Integer period) {
		
		Calendar untilDate = new GregorianCalendar();
		untilDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - 3);
		Calendar sinceDate = new GregorianCalendar(untilDate.get(Calendar.YEAR),
				untilDate.get(Calendar.MONTH),
				untilDate.get(Calendar.DAY_OF_MONTH));
		sinceDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - (period - 1));
		
		Long dateSince = UtilFunctions.getDateForFacebookReport(sinceDate);
		Long dateUntil = UtilFunctions.getDateForFacebookReport(untilDate);
		
		for (Long currentDay = dateSince; currentDay <= dateUntil; currentDay += 86400) {
			retrieveInsightsForDay(currentDay);
		}
		
		retrieveInsightsForLifetime(dateUntil);
		
		ReportFacebookPosts reportFacebookPosts = new ReportFacebookPosts(accessToken, pageId);
		reportFacebookPosts.setDateFrom(dateSince);
		reportFacebookPosts.setDateUntil(dateUntil + 86400);
		reportFacebookPosts.getPosts();
	}
	
	public void retrieveAllInsightsForDay(Long day) {
		
		retrieveInsightsForDay(day);
		
		ReportFacebookPosts reportFacebookPosts = new ReportFacebookPosts(accessToken, pageId);
		reportFacebookPosts.setDateFrom(day);
		reportFacebookPosts.setDateUntil(day + 86400);
		reportFacebookPosts.getPosts();
	}
	
	public void retrieveInsightsForDay(Long day) {
		
		FacebookIntegration facebookIntegration = new FacebookIntegration();
		Facebook facebook = facebookIntegration.getFacebook(new AccessToken(accessToken));
		
		Long dateSince = day + 86400L;
		Long dateUntil = dateSince;
		
		reportDetailedRemote = (MaintenanceReportDetailedRemote) RecoverMaintenance.recoverMaintenance("ReportDetailed");
		
		faceReportLocationRemote = (MaintenanceFaceReportLocationRemote) RecoverMaintenance.recoverMaintenance("FaceReportLocation");
		
		reportControlRemote = (MaintenanceReportControlRemote) RecoverMaintenance.recoverMaintenance("ReportControl"); 
		
		if(reportControlRemote.getEntityByNetwork(pageId, Profile.FACEBOOK_PAGE, GROUP_MAIN, day) == null) {

			try {
				
				ReportControl reportControl = new ReportControl();
				reportControl.setNetworkId(pageId);
				reportControl.setNetworkType(Profile.FACEBOOK_PAGE);
				reportControl.setDate(day);
				reportControl.setType(GROUP_MAIN);
				reportControl.setStartDate(new Date().getTime() / 1000L);

				reportControl = reportControlRemote.save(reportControl);
				
				try {
					
					Map<String, String> queryParameters = new HashMap<String, String>();
					queryParameters.put("metric", prepareInsightsMetricsForDay());
					queryParameters.put("period", PERIOD_DAY);
					queryParameters.put("since", dateSince.toString());
					queryParameters.put("until", dateUntil.toString());
					
					RawAPIResponse res = facebook.callGetAPI(pageId + "/insights", queryParameters);
					
					JsonParser parser = new JsonParser();			
					JsonArray jsonResponseData = parser.parse(res.asString()).getAsJsonObject().get("data").getAsJsonArray();

					Iterator<JsonElement> insightsList = jsonResponseData.iterator();
					
					while (insightsList.hasNext() ) {
						
						try {

							JsonObject jsonObject = ((JsonObject) insightsList.next());

							Insights insights = gson.fromJson(jsonObject, Insights.class);

							JsonObject valuesObject = jsonObject.get("values").getAsJsonArray().get(0).getAsJsonObject();
							
							if(!valuesObject.has("value")) {
								valuesObject.add("value", JsonNull.INSTANCE);
							}
							
							if(insightsMetricsMap.containsKey(insights.getName())) {
								
								ReportDetailed reportDetailed = new ReportDetailed();
								reportDetailed.setPageId(pageId);
								reportDetailed.setProperty(insightsMetricsMap.get(insights.getName()));
								reportDetailed.setDate(day);
								reportDetailed.setFromGroup(GROUP_UNIQUE);
								
								if(valuesObject.get("value").isJsonPrimitive()) {
									reportDetailed.setValue(valuesObject.get("value").getAsLong());
								}
								
								try {
									
									reportDetailedRemote.save(reportDetailed);
									
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							} else {
								
								JsonElement valueElement = valuesObject.get("value");
								
								if(insights.getName().equals("page_impressions_by_age_gender_unique")) {
									retrievePageImpressionsGenderAge(day, valueElement);
								} else if(insights.getName().equals("page_impressions_by_story_type")) {
									retrievePageImpressionsStoryType(day, valueElement);
								} else if(insights.getName().equals("page_consumptions_by_consumption_type")) {
									retrievePageConsumptionsType(day, valueElement);
								} else if(insights.getName().equals("page_storytellers_by_story_type")) {
									retrievePageStoriesStoryType(day, valueElement);
								} else if(insights.getName().equals("page_fans_by_like_source_unique")) {
									retrievePageFansLikeSource(day, valueElement);
								} else if(insights.getName().equals(PAGE_FANS_ONLINE)) {
									retrievePageFansOnline(day, valueElement);
								} else if(insights.getName().equals("page_impressions_by_city_unique")) {
									retrievePageImpressionsByCity(day, valueElement);
								} else if(insights.getName().equals("page_impressions_by_country_unique")) {
									retrievePageImpressionsByCountry(day, valueElement);
								} else if(insights.getName().equals("page_views_external_referrals")) {
									retrievePageViewsExternalReferrals(day, valueElement);
								} else if(insights.getName().equals(PAGE_POSITIVE_FEEDBACK_BY_TYPE)) {
									retrievePagePositiveFeedbackByType(day, valueElement);
								} else if(insights.getName().equals(PAGE_NEGATIVE_FEEDBACK_BY_TYPE)) {
									retrievePageNegativeFeedbackByType(day, valueElement);
								} 
								
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					reportControl = reportControlRemote.getById(reportControl.getIdReportsControl());

					reportControl.setFinishDate(new Date().getTime() / 1000L);
					reportControlRemote.save(reportControl);	
					
				} catch (Exception e1) {
					
					try {
						
						reportControl = reportControlRemote.getById(reportControl.getIdReportsControl());
						reportControlRemote.delete(reportControl);
						
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					e1.printStackTrace();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void retrieveInsightsForLifetime(Long day) {
		
		FacebookIntegration facebookIntegration = new FacebookIntegration();
		Facebook facebook = facebookIntegration.getFacebook(new AccessToken(accessToken));
		
		Long dateSince = day + 86400L;
		Long dateUntil = dateSince;
		
		reportDetailedRemote = (MaintenanceReportDetailedRemote) RecoverMaintenance.recoverMaintenance("ReportDetailed");
		
		faceReportLocationRemote = (MaintenanceFaceReportLocationRemote) RecoverMaintenance.recoverMaintenance("FaceReportLocation");
		
		reportControlRemote = (MaintenanceReportControlRemote) RecoverMaintenance.recoverMaintenance("ReportControl"); 
		
		try {
			
			Map<String, String> queryParameters = new HashMap<String, String>();
			queryParameters.put("metric", prepareInsightsMetricsForLifetime());
			queryParameters.put("period", PERIOD_LIFETIME);
			queryParameters.put("since", dateSince.toString());
			queryParameters.put("until", dateUntil.toString());
			
			RawAPIResponse res = facebook.callGetAPI(pageId + "/insights", queryParameters);
			
			JsonParser parser = new JsonParser();			
			JsonArray jsonResponseData = parser.parse(res.asString()).getAsJsonObject().get("data").getAsJsonArray();

			Iterator<JsonElement> insightsList = jsonResponseData.iterator();
			
			//----- System.out.println("Insights JSON Response..: " + jsonResponseData.toString());
			
			while (insightsList.hasNext() ) {
				
				try {

					JsonObject jsonObject = ((JsonObject) insightsList.next());

					Insights insights = gson.fromJson(jsonObject, Insights.class);

					JsonObject valuesObject = jsonObject.get("values").getAsJsonArray().get(0).getAsJsonObject();

					if(valuesObject.get("value").isJsonPrimitive()) {

						try {
							
							if(insights.getName().equals("page_fans")) {
								retrieveFans(day, valuesObject.get("value").getAsLong());
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						
						JsonElement valueElement = valuesObject.get("value");
						
						if(insights.getName().equals("page_fans_city")) {
							retrievePageFansCity(day, valueElement);
						} else if(insights.getName().equals("page_fans_country")) {
							retrievePageFansCountry(day, valueElement);
						} else if(insights.getName().equals("page_fans_gender_age")) {
							retrievePageFansGenderAge(day, valueElement);
						}

					} 

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
		}
	}
	
	private String prepareInsightsMetricsForDay() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("page_fan_adds,");
		builder.append("page_impressions_unique,");
		builder.append("page_views_total,");
		builder.append("page_fan_removes,");
		builder.append("page_impressions,");
		builder.append("page_stories,");
		builder.append("page_impressions_organic,");
		builder.append("page_impressions_paid,");
		builder.append("page_impressions_viral,");
		builder.append("page_posts_impressions,");
		builder.append("page_posts_impressions_unique,");
		builder.append(PAGE_FANS_ONLINE_PER_DAY + ",");
		builder.append("page_impressions_by_age_gender_unique,");
		builder.append("page_impressions_by_story_type,");
		builder.append("page_consumptions_by_consumption_type,");
		builder.append("page_storytellers_by_story_type,");
		builder.append("page_fans_by_like_source_unique,");
		builder.append(PAGE_FANS_ONLINE + ",");
		builder.append(PAGE_CONSUMPTIONS + ",");
		builder.append("page_impressions_by_city_unique,");
		builder.append("page_impressions_by_country_unique,");
		builder.append("page_views_external_referrals,");
		builder.append(PAGE_POSITIVE_FEEDBACK_BY_TYPE + ",");
		builder.append(PAGE_NEGATIVE_FEEDBACK_BY_TYPE + ",");
		builder.append(PAGE_ACTIONS_POST_REACTIONS_LIKE_TOTAL + ",");
		builder.append(PAGE_ACTIONS_POST_REACTIONS_ANGER_TOTAL + ",");
		builder.append(PAGE_ACTIONS_POST_REACTIONS_HAHA_TOTAL + ",");
		builder.append(PAGE_ACTIONS_POST_REACTIONS_LOVE_TOTAL + ",");
		builder.append(PAGE_ACTIONS_POST_REACTIONS_SORRY_TOTAL + ",");
		builder.append(PAGE_ACTIONS_POST_REACTIONS_WOW_TOTAL + ",");
		builder.append(PAGE_POSTS_IMPRESSIONS_ORGANIC + ",");
		builder.append(PAGE_POSTS_IMPRESSIONS_ORGANIC_UNIQUE + ",");
		builder.append(PAGE_POSTS_IMPRESSIONS_PAID + ",");
		builder.append(PAGE_POSTS_IMPRESSIONS_PAID_UNIQUE + ",");
		builder.append(PAGE_POSTS_IMPRESSIONS_VIRAL + ",");
		builder.append(PAGE_POSTS_IMPRESSIONS_VIRAL_UNIQUE + ",");
		builder.append(PAGE_VIDEO_VIEWS + ",");
		builder.append(PAGE_VIDEO_VIEWS_ORGANIC + ",");
		builder.append(PAGE_VIDEO_VIEWS_PAID + ",");
		builder.append(PAGE_VIDEO_VIEW_TIME + ",");
		builder.append(PAGE_VIDEO_REPEAT_VIEWS + ",");
		builder.append(PAGE_VIDEO_COMPLETE_VIEWS_30S + ",");
		builder.append(PAGE_VIDEO_COMPLETE_VIEWS_30S_PAID + ",");
		builder.append(PAGE_VIDEO_COMPLETE_VIEWS_30S_ORGANIC + ",");
		builder.append(PAGE_VIDEO_VIEWS_10S + ",");
		builder.append(PAGE_VIDEO_VIEWS_10S_PAID + ",");
		builder.append(PAGE_VIDEO_VIEWS_10S_ORGANIC + ",");
		builder.append(PAGE_VIDEO_VIEWS_AUTOPLAYED + ",");
		builder.append(PAGE_VIDEO_VIEWS_CLICK_TO_PLAY);
		
		return builder.toString();
	}
	
	private String prepareInsightsMetricsForLifetime() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("page_fans,");
		builder.append("page_fans_city,");
		builder.append("page_fans_gender_age,");
		builder.append("page_fans_country");
		
		return builder.toString();
	}
	
	private void retrieveFans(Long day, Long value) {
		
		try {
		
			Long lastDate = reportDetailedRemote.getDateForProperty(pageId, PAGE_FANS);

			ReportDetailed reportDetailed;

			if(lastDate == null) {

				reportDetailed = new ReportDetailed();
				reportDetailed.setPageId(pageId);
				reportDetailed.setProperty(PAGE_FANS);
				reportDetailed.setDate(day);
				reportDetailed.setFromGroup(GROUP_UNIQUE);

			} else {
				reportDetailed = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS);
				reportDetailed.setDate(day);
			}

			reportDetailed.setValue(value);

			reportDetailedRemote.save(reportDetailed);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void retrievePageViewsExternalReferrals(Long currentDay, JsonElement valueElement) {

		if(!reportControlRemote.hasEntity(pageId, Profile.FACEBOOK_PAGE, GROUP_VIEWS_EXTERNAL, currentDay)) {

			try {

				ReportControl reportControl = new ReportControl();
				reportControl.setNetworkId(pageId);
				reportControl.setNetworkType(Profile.FACEBOOK_PAGE);
				reportControl.setDate(currentDay);
				reportControl.setType(GROUP_VIEWS_EXTERNAL);
				reportControl.setStartDate(new Date().getTime() / 1000L);

				reportControl = reportControlRemote.save(reportControl);
				
				if(valueElement.isJsonObject()) {
					
					JsonObject object = valueElement.getAsJsonObject();

					for (Map.Entry<String,JsonElement> entry : object.entrySet()) {

						FaceReportLocation faceReportLocation = new FaceReportLocation();
						faceReportLocation.setPageId(pageId);
						faceReportLocation.setLocation(entry.getKey());
						faceReportLocation.setValue(entry.getValue().getAsLong());
						faceReportLocation.setDate(currentDay);
						faceReportLocation.setFromGroup(GROUP_VIEWS_EXTERNAL);

						try {

							faceReportLocationRemote.save(faceReportLocation);

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}		
				}
					
				reportControl = reportControlRemote.getById(reportControl.getIdReportsControl());

				reportControl.setFinishDate(new Date().getTime() / 1000L);
				reportControlRemote.save(reportControl);
									
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void retrievePageImpressionsGenderAge(Long currentDay, JsonElement valueElement) {

		/* Male statistics*/
		ReportDetailed reportDetailedMale13Among17 = new ReportDetailed();
		reportDetailedMale13Among17.setPageId(pageId);
		reportDetailedMale13Among17.setProperty(PAGE_IMPRESSIONS_MALE_13_AMONG_17);
		reportDetailedMale13Among17.setDate(currentDay);
		reportDetailedMale13Among17.setFromGroup(GROUP_IMP_GENDER_AGE);

		ReportDetailed reportDetailedMale18Among24 = new ReportDetailed();
		reportDetailedMale18Among24.setPageId(pageId);
		reportDetailedMale18Among24.setProperty(PAGE_IMPRESSIONS_MALE_18_AMONG_24);
		reportDetailedMale18Among24.setDate(currentDay);
		reportDetailedMale18Among24.setFromGroup(GROUP_IMP_GENDER_AGE);

		ReportDetailed reportDetailedMale25Among34 = new ReportDetailed();
		reportDetailedMale25Among34.setPageId(pageId);
		reportDetailedMale25Among34.setProperty(PAGE_IMPRESSIONS_MALE_25_AMONG_34);
		reportDetailedMale25Among34.setDate(currentDay);
		reportDetailedMale25Among34.setFromGroup(GROUP_IMP_GENDER_AGE);

		ReportDetailed reportDetailedMale35Among44 = new ReportDetailed();
		reportDetailedMale35Among44.setPageId(pageId);
		reportDetailedMale35Among44.setProperty(PAGE_IMPRESSIONS_MALE_35_AMONG_44);
		reportDetailedMale35Among44.setDate(currentDay);
		reportDetailedMale35Among44.setFromGroup(GROUP_IMP_GENDER_AGE);

		ReportDetailed reportDetailedMale45Among54 = new ReportDetailed();
		reportDetailedMale45Among54.setPageId(pageId);
		reportDetailedMale45Among54.setProperty(PAGE_IMPRESSIONS_MALE_45_AMONG_54);
		reportDetailedMale45Among54.setDate(currentDay);
		reportDetailedMale45Among54.setFromGroup(GROUP_IMP_GENDER_AGE);

		ReportDetailed reportDetailedMale55Among64 = new ReportDetailed();
		reportDetailedMale55Among64.setPageId(pageId);
		reportDetailedMale55Among64.setProperty(PAGE_IMPRESSIONS_MALE_55_AMONG_64);
		reportDetailedMale55Among64.setDate(currentDay);
		reportDetailedMale55Among64.setFromGroup(GROUP_IMP_GENDER_AGE);

		ReportDetailed reportDetailedMale65Plus = new ReportDetailed();
		reportDetailedMale65Plus.setPageId(pageId);
		reportDetailedMale65Plus.setProperty(PAGE_IMPRESSIONS_MALE_65_PLUS);
		reportDetailedMale65Plus.setDate(currentDay);
		reportDetailedMale65Plus.setFromGroup(GROUP_IMP_GENDER_AGE);

		/*Female statistics*/
		ReportDetailed reportDetailedFemale13Among17 = new ReportDetailed();
		reportDetailedFemale13Among17.setPageId(pageId);
		reportDetailedFemale13Among17.setProperty(PAGE_IMPRESSIONS_FEMALE_13_AMONG_17);
		reportDetailedFemale13Among17.setDate(currentDay);
		reportDetailedFemale13Among17.setFromGroup(GROUP_IMP_GENDER_AGE);

		ReportDetailed reportDetailedFemale18Among24 = new ReportDetailed();
		reportDetailedFemale18Among24.setPageId(pageId);
		reportDetailedFemale18Among24.setProperty(PAGE_IMPRESSIONS_FEMALE_18_AMONG_24);
		reportDetailedFemale18Among24.setDate(currentDay);
		reportDetailedFemale18Among24.setFromGroup(GROUP_IMP_GENDER_AGE);

		ReportDetailed reportDetailedFemale25Among34 = new ReportDetailed();
		reportDetailedFemale25Among34.setPageId(pageId);
		reportDetailedFemale25Among34.setProperty(PAGE_IMPRESSIONS_FEMALE_25_AMONG_34);
		reportDetailedFemale25Among34.setDate(currentDay);
		reportDetailedFemale25Among34.setFromGroup(GROUP_IMP_GENDER_AGE);

		ReportDetailed reportDetailedFemale35Among44 = new ReportDetailed();
		reportDetailedFemale35Among44.setPageId(pageId);
		reportDetailedFemale35Among44.setProperty(PAGE_IMPRESSIONS_FEMALE_35_AMONG_44);
		reportDetailedFemale35Among44.setDate(currentDay);
		reportDetailedFemale35Among44.setFromGroup(GROUP_IMP_GENDER_AGE);

		ReportDetailed reportDetailedFemale45Among54 = new ReportDetailed();
		reportDetailedFemale45Among54.setPageId(pageId);
		reportDetailedFemale45Among54.setProperty(PAGE_IMPRESSIONS_FEMALE_45_AMONG_54);
		reportDetailedFemale45Among54.setDate(currentDay);
		reportDetailedFemale45Among54.setFromGroup(GROUP_IMP_GENDER_AGE);

		ReportDetailed reportDetailedFemale55Among64 = new ReportDetailed();
		reportDetailedFemale55Among64.setPageId(pageId);
		reportDetailedFemale55Among64.setProperty(PAGE_IMPRESSIONS_FEMALE_55_AMONG_64);
		reportDetailedFemale55Among64.setDate(currentDay);
		reportDetailedFemale55Among64.setFromGroup(GROUP_IMP_GENDER_AGE);

		ReportDetailed reportDetailedFemale65Plus = new ReportDetailed();
		reportDetailedFemale65Plus.setPageId(pageId);
		reportDetailedFemale65Plus.setProperty(PAGE_IMPRESSIONS_FEMALE_65_PLUS);
		reportDetailedFemale65Plus.setDate(currentDay);
		reportDetailedFemale65Plus.setFromGroup(GROUP_IMP_GENDER_AGE);

		try {
			
			String jsonResponse = valueElement.toString();

			jsonResponse = replacePageImpressionsGenderAge(jsonResponse);

			JsonParser parser = new JsonParser();
			
			JsonElement jsonElement = parser.parse(jsonResponse);
			
			if(jsonElement.isJsonObject()) {

				PageImpressionsGenderAge pageImpressionsGenderAge = gson.fromJson(jsonElement, PageImpressionsGenderAge.class);

				/* Male statistics*/
				reportDetailedMale13Among17.setValue(pageImpressionsGenderAge.getMaleThirteenAmongSeventeen().longValue());
				reportDetailedMale18Among24.setValue(pageImpressionsGenderAge.getMaleEighteenAmongTwentyFour().longValue());
				reportDetailedMale25Among34.setValue(pageImpressionsGenderAge.getMaleTwentyFiveAmongThirtyFour().longValue());
				reportDetailedMale35Among44.setValue(pageImpressionsGenderAge.getMaleThirtyFiveAmongFourtyFour().longValue());
				reportDetailedMale45Among54.setValue(pageImpressionsGenderAge.getMaleFourtyFiveAmongFiftyFour().longValue());
				reportDetailedMale55Among64.setValue(pageImpressionsGenderAge.getMaleFiftyFiveAmongSixtyFour().longValue());
				reportDetailedMale65Plus.setValue(pageImpressionsGenderAge.getMaleSixtyFivePlus().longValue());

				/*Female statistics*/
				reportDetailedFemale13Among17.setValue(pageImpressionsGenderAge.getFemaleThirteenAmongSeventeen().longValue());
				reportDetailedFemale18Among24.setValue(pageImpressionsGenderAge.getFemaleEighteenAmongTwentyFour().longValue());
				reportDetailedFemale25Among34.setValue(pageImpressionsGenderAge.getFemaleTwentyFiveAmongThirtyFour().longValue());
				reportDetailedFemale35Among44.setValue(pageImpressionsGenderAge.getFemaleThirtyFiveAmongFourtyFour().longValue());
				reportDetailedFemale45Among54.setValue(pageImpressionsGenderAge.getFemaleFourtyFiveAmongFiftyFour().longValue());
				reportDetailedFemale55Among64.setValue(pageImpressionsGenderAge.getFemaleFiftyFiveAmongSixtyFour().longValue());
				reportDetailedFemale65Plus.setValue(pageImpressionsGenderAge.getFemaleSixtyFivePlus().longValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			
			/* Male statistics*/
			reportDetailedRemote.save(reportDetailedMale13Among17);
			reportDetailedRemote.save(reportDetailedMale18Among24);
			reportDetailedRemote.save(reportDetailedMale25Among34);
			reportDetailedRemote.save(reportDetailedMale35Among44);
			reportDetailedRemote.save(reportDetailedMale45Among54);
			reportDetailedRemote.save(reportDetailedMale55Among64);
			reportDetailedRemote.save(reportDetailedMale65Plus);

			/*Female statistics*/
			reportDetailedRemote.save(reportDetailedFemale13Among17);
			reportDetailedRemote.save(reportDetailedFemale18Among24);
			reportDetailedRemote.save(reportDetailedFemale25Among34);
			reportDetailedRemote.save(reportDetailedFemale35Among44);
			reportDetailedRemote.save(reportDetailedFemale45Among54);
			reportDetailedRemote.save(reportDetailedFemale55Among64);
			reportDetailedRemote.save(reportDetailedFemale65Plus);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void retrievePageImpressionsGenderAge(List<ReportDetailed> reportsDetailed, JsonElement valueElement) {

		try {
			
			String jsonResponse = valueElement.toString();

			jsonResponse = replacePageImpressionsGenderAge(jsonResponse);

			JsonParser parser = new JsonParser();

			JsonElement jsonElement = parser.parse(jsonResponse);

			if(jsonElement.isJsonObject()) {

				PageImpressionsGenderAge pageImpressionsGenderAge = gson.fromJson(jsonElement, PageImpressionsGenderAge.class);

				for(ReportDetailed reportDetailed : reportsDetailed) {

					if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_MALE_13_AMONG_17)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getMaleThirteenAmongSeventeen().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_MALE_18_AMONG_24)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getMaleEighteenAmongTwentyFour().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_MALE_25_AMONG_34)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getMaleTwentyFiveAmongThirtyFour().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_MALE_35_AMONG_44)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getMaleThirtyFiveAmongFourtyFour().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_MALE_45_AMONG_54)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getMaleFourtyFiveAmongFiftyFour().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_MALE_55_AMONG_64)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getMaleFiftyFiveAmongSixtyFour().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_MALE_65_PLUS)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getMaleSixtyFivePlus().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_FEMALE_13_AMONG_17)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getFemaleThirteenAmongSeventeen().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_FEMALE_18_AMONG_24)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getFemaleEighteenAmongTwentyFour().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_FEMALE_25_AMONG_34)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getFemaleTwentyFiveAmongThirtyFour().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_FEMALE_35_AMONG_44)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getFemaleThirtyFiveAmongFourtyFour().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_FEMALE_45_AMONG_54)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getFemaleFourtyFiveAmongFiftyFour().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_FEMALE_55_AMONG_64)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getFemaleFiftyFiveAmongSixtyFour().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_FEMALE_65_PLUS)) {
						reportDetailed.setValue(pageImpressionsGenderAge.getFemaleSixtyFivePlus().longValue());
					}

					try {

						reportDetailedRemote.save(reportDetailed);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void retrievePageConsumptionsType(Long currentDay, JsonElement valueElement) {

		ReportDetailed reportDetailedConsumptionsOtherClicks = new ReportDetailed();
		reportDetailedConsumptionsOtherClicks.setPageId(pageId);
		reportDetailedConsumptionsOtherClicks.setProperty(PAGE_CONSUMPTIONS_OTHER_CLICKS);
		reportDetailedConsumptionsOtherClicks.setDate(currentDay);
		reportDetailedConsumptionsOtherClicks.setFromGroup(GROUP_CON_TYPE);

		ReportDetailed reportDetailedConsumptionsLinkClicks = new ReportDetailed();
		reportDetailedConsumptionsLinkClicks.setPageId(pageId);
		reportDetailedConsumptionsLinkClicks.setProperty(PAGE_CONSUMPTIONS_LINK_CLICKS);
		reportDetailedConsumptionsLinkClicks.setDate(currentDay);
		reportDetailedConsumptionsLinkClicks.setFromGroup(GROUP_CON_TYPE);

		ReportDetailed reportDetailedConsumptionsPhotoView = new ReportDetailed();
		reportDetailedConsumptionsPhotoView.setPageId(pageId);
		reportDetailedConsumptionsPhotoView.setProperty(PAGE_CONSUMPTIONS_PHOTO_VIEW);
		reportDetailedConsumptionsPhotoView.setDate(currentDay);
		reportDetailedConsumptionsPhotoView.setFromGroup(GROUP_CON_TYPE);
		
		ReportDetailed reportDetailedConsumptionsVideoPlay = new ReportDetailed();
		reportDetailedConsumptionsVideoPlay.setPageId(pageId);
		reportDetailedConsumptionsVideoPlay.setProperty(PAGE_CONSUMPTIONS_VIDEO_PLAY);
		reportDetailedConsumptionsVideoPlay.setDate(currentDay);
		reportDetailedConsumptionsVideoPlay.setFromGroup(GROUP_CON_TYPE);

		try {
			
			String jsonResponse = valueElement.toString();

			jsonResponse = replacePageConsumptionsType(jsonResponse);

			JsonParser parser = new JsonParser();
			
			JsonElement jsonElement = parser.parse(jsonResponse);
			
			if(jsonElement.isJsonObject()) {

				PageConsumptionsType pageComsumptionsType = gson.fromJson(jsonResponse, PageConsumptionsType.class);

				reportDetailedConsumptionsOtherClicks.setValue(pageComsumptionsType.getOtherClicks().longValue());
				reportDetailedConsumptionsLinkClicks.setValue(pageComsumptionsType.getLinkClicks().longValue());
				reportDetailedConsumptionsPhotoView.setValue(pageComsumptionsType.getPhotoView().longValue());
				reportDetailedConsumptionsVideoPlay.setValue(pageComsumptionsType.getVideoPlay().longValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			
			reportDetailedRemote.save(reportDetailedConsumptionsOtherClicks);
			reportDetailedRemote.save(reportDetailedConsumptionsLinkClicks);
			reportDetailedRemote.save(reportDetailedConsumptionsPhotoView);
			reportDetailedRemote.save(reportDetailedConsumptionsVideoPlay);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void retrievePageConsumptionsType(List<ReportDetailed> reportsDetailed, JsonElement valueElement) {

		try {
			
			String jsonResponse = valueElement.toString();

			jsonResponse = replacePageConsumptionsType(jsonResponse);

			JsonParser parser = new JsonParser();
			
			JsonElement jsonElement = parser.parse(jsonResponse);
			
			if(jsonElement.isJsonObject()) {

				PageConsumptionsType pageComsumptionsType = gson.fromJson(jsonElement, PageConsumptionsType.class);

				for(ReportDetailed reportDetailed : reportsDetailed) {

					if(reportDetailed.getProperty().equals(PAGE_CONSUMPTIONS_OTHER_CLICKS)) {
						reportDetailed.setValue(pageComsumptionsType.getOtherClicks().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_CONSUMPTIONS_LINK_CLICKS)) {
						reportDetailed.setValue(pageComsumptionsType.getLinkClicks().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_CONSUMPTIONS_PHOTO_VIEW)) {
						reportDetailed.setValue(pageComsumptionsType.getPhotoView().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_CONSUMPTIONS_VIDEO_PLAY)) {
						reportDetailed.setValue(pageComsumptionsType.getVideoPlay().longValue());
					}

					try {
						
						reportDetailedRemote.save(reportDetailed);
						
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void retrievePageImpressionsStoryType(Long currentDay, JsonElement valueElement) {

		ReportDetailed reportDetailedImpressionsByFan = new ReportDetailed();
		reportDetailedImpressionsByFan.setPageId(pageId);
		reportDetailedImpressionsByFan.setProperty(PAGE_IMPRESSIONS_BY_FAN);
		reportDetailedImpressionsByFan.setDate(currentDay);
		reportDetailedImpressionsByFan.setFromGroup(GROUP_IMP_STORY_TYPE);

		ReportDetailed reportDetailedImpressionsByCheckin = new ReportDetailed();
		reportDetailedImpressionsByCheckin.setPageId(pageId);
		reportDetailedImpressionsByCheckin.setProperty(PAGE_IMPRESSIONS_BY_CHECKIN);
		reportDetailedImpressionsByCheckin.setDate(currentDay);
		reportDetailedImpressionsByCheckin.setFromGroup(GROUP_IMP_STORY_TYPE);

		ReportDetailed reportDetailedImpressionsByOther = new ReportDetailed();
		reportDetailedImpressionsByOther.setPageId(pageId);
		reportDetailedImpressionsByOther.setProperty(PAGE_IMPRESSIONS_BY_OTHER);
		reportDetailedImpressionsByOther.setDate(currentDay);
		reportDetailedImpressionsByOther.setFromGroup(GROUP_IMP_STORY_TYPE);

		ReportDetailed reportDetailedImpressionsByUserPost = new ReportDetailed();
		reportDetailedImpressionsByUserPost.setPageId(pageId);
		reportDetailedImpressionsByUserPost.setProperty(PAGE_IMPRESSIONS_BY_USER_POST);
		reportDetailedImpressionsByUserPost.setDate(currentDay);
		reportDetailedImpressionsByUserPost.setFromGroup(GROUP_IMP_STORY_TYPE);

		ReportDetailed reportDetailedImpressionsByPagePost = new ReportDetailed();
		reportDetailedImpressionsByPagePost.setPageId(pageId);
		reportDetailedImpressionsByPagePost.setProperty(PAGE_IMPRESSIONS_BY_PAGE_POST);
		reportDetailedImpressionsByPagePost.setDate(currentDay);
		reportDetailedImpressionsByPagePost.setFromGroup(GROUP_IMP_STORY_TYPE);

		ReportDetailed reportDetailedImpressionsByMention = new ReportDetailed();
		reportDetailedImpressionsByMention.setPageId(pageId);
		reportDetailedImpressionsByMention.setProperty(PAGE_IMPRESSIONS_BY_MENTION);
		reportDetailedImpressionsByMention.setDate(currentDay);
		reportDetailedImpressionsByMention.setFromGroup(GROUP_IMP_STORY_TYPE);
		
		ReportDetailed reportDetailedImpressionsByCoupon = new ReportDetailed();
		reportDetailedImpressionsByCoupon.setPageId(pageId);
		reportDetailedImpressionsByCoupon.setProperty(PAGE_IMPRESSIONS_BY_COUPON);
		reportDetailedImpressionsByCoupon.setDate(currentDay);
		reportDetailedImpressionsByCoupon.setFromGroup(GROUP_IMP_STORY_TYPE);
		
		ReportDetailed reportDetailedImpressionsByEvent = new ReportDetailed();
		reportDetailedImpressionsByEvent.setPageId(pageId);
		reportDetailedImpressionsByEvent.setProperty(PAGE_IMPRESSIONS_BY_EVENT);
		reportDetailedImpressionsByEvent.setDate(currentDay);
		reportDetailedImpressionsByEvent.setFromGroup(GROUP_IMP_STORY_TYPE);
		
		ReportDetailed reportDetailedImpressionsByQuestion = new ReportDetailed();
		reportDetailedImpressionsByQuestion.setPageId(pageId);
		reportDetailedImpressionsByQuestion.setProperty(PAGE_IMPRESSIONS_BY_QUESTION);
		reportDetailedImpressionsByQuestion.setDate(currentDay);
		reportDetailedImpressionsByQuestion.setFromGroup(GROUP_IMP_STORY_TYPE);

		try {
			
			String jsonResponse = valueElement.toString();

			jsonResponse = replacePageImpressionsStoryType(jsonResponse);

			JsonParser parser = new JsonParser();
			
			JsonElement jsonElement = parser.parse(jsonResponse);
			
			if(jsonElement.isJsonObject()) {

				PageImpressionsStoryType pageImpressionsStoryType = gson.fromJson(jsonResponse, PageImpressionsStoryType.class);

				reportDetailedImpressionsByFan.setValue(pageImpressionsStoryType.getFan().longValue());
				reportDetailedImpressionsByCheckin.setValue(pageImpressionsStoryType.getCheckin().longValue());
				reportDetailedImpressionsByOther.setValue(pageImpressionsStoryType.getOther().longValue());
				reportDetailedImpressionsByUserPost.setValue(pageImpressionsStoryType.getUserPost().longValue());
				reportDetailedImpressionsByPagePost.setValue(pageImpressionsStoryType.getPagePost().longValue());
				reportDetailedImpressionsByMention.setValue(pageImpressionsStoryType.getMention().longValue());
				reportDetailedImpressionsByCoupon.setValue(pageImpressionsStoryType.getCoupon().longValue());
				reportDetailedImpressionsByEvent.setValue(pageImpressionsStoryType.getEvent().longValue());
				reportDetailedImpressionsByQuestion.setValue(pageImpressionsStoryType.getQuestion().longValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			
			reportDetailedRemote.save(reportDetailedImpressionsByFan);
			reportDetailedRemote.save(reportDetailedImpressionsByCheckin);
			reportDetailedRemote.save(reportDetailedImpressionsByOther);
			reportDetailedRemote.save(reportDetailedImpressionsByUserPost);
			reportDetailedRemote.save(reportDetailedImpressionsByPagePost);
			reportDetailedRemote.save(reportDetailedImpressionsByMention);
			reportDetailedRemote.save(reportDetailedImpressionsByCoupon);
			reportDetailedRemote.save(reportDetailedImpressionsByEvent);
			reportDetailedRemote.save(reportDetailedImpressionsByQuestion);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void retrievePageImpressionsStoryType(List<ReportDetailed> reportsDetailed, JsonElement valueElement) {

		try {
			
			String jsonResponse = valueElement.toString();

			jsonResponse = replacePageImpressionsStoryType(jsonResponse);

			JsonParser parser = new JsonParser();

			JsonElement jsonElement = parser.parse(jsonResponse);

			if(jsonElement.isJsonObject()) {

				PageImpressionsStoryType pageImpressionsStoryType = gson.fromJson(jsonElement, PageImpressionsStoryType.class);

				for(ReportDetailed reportDetailed : reportsDetailed) {

					if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_BY_FAN)) {
						reportDetailed.setValue(pageImpressionsStoryType.getFan().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_BY_CHECKIN)) {
						reportDetailed.setValue(pageImpressionsStoryType.getCheckin().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_BY_OTHER)) {
						reportDetailed.setValue(pageImpressionsStoryType.getOther().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_BY_USER_POST)) {
						reportDetailed.setValue(pageImpressionsStoryType.getUserPost().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_BY_PAGE_POST)) {
						reportDetailed.setValue(pageImpressionsStoryType.getPagePost().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_BY_MENTION)) {
						reportDetailed.setValue(pageImpressionsStoryType.getMention().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_BY_COUPON)) {
						reportDetailed.setValue(pageImpressionsStoryType.getCoupon().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_BY_EVENT)) {
						reportDetailed.setValue(pageImpressionsStoryType.getEvent().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_IMPRESSIONS_BY_QUESTION)) {
						reportDetailed.setValue(pageImpressionsStoryType.getQuestion().longValue());
					}

					try {

						reportDetailedRemote.save(reportDetailed);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void retrievePagePositiveFeedbackByType(List<ReportDetailed> reportsDetailed, JsonElement valueElement) {
		
		try {
			
			if(valueElement.isJsonObject()) {
				
				PagePositiveFeedbacksType pagePositiveFeedbacksType = gson.fromJson(valueElement, PagePositiveFeedbacksType.class);
				
				for(ReportDetailed reportDetailed : reportsDetailed) {
					
					if(reportDetailed.getProperty().equals(PAGE_POSITIVE_FEEDBACK_LIKE)) {
						reportDetailed.setValue(pagePositiveFeedbacksType.getLike().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_POSITIVE_FEEDBACK_COMMENT)) {
						reportDetailed.setValue(pagePositiveFeedbacksType.getComment().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_POSITIVE_FEEDBACK_LINK)) {
						reportDetailed.setValue(pagePositiveFeedbacksType.getLink().longValue());
					}
					
					try {
						
						reportDetailedRemote.save(reportDetailed);
						
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void retrievePageNegativeFeedbackByType(List<ReportDetailed> reportsDetailed, JsonElement valueElement) {
		
		try {
			
			if(valueElement.isJsonObject()) {
				
				PageNegativeFeedbacksType pageNegativeFeedbacksType = gson.fromJson(valueElement, PageNegativeFeedbacksType.class);
				
				for(ReportDetailed reportDetailed : reportsDetailed) {
					
					if(reportDetailed.getProperty().equals(PAGE_NEGATIVE_FEEDBACK_HIDE_CLICKS)) {
						reportDetailed.setValue(pageNegativeFeedbacksType.getHideClicks().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_NEGATIVE_FEEDBACK_HIDE_ALL_CLICKS)) {
						reportDetailed.setValue(pageNegativeFeedbacksType.getHideAllClicks().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_NEGATIVE_FEEDBACK_REPORT_SPAM_CLICKS)) {
						reportDetailed.setValue(pageNegativeFeedbacksType.getReportSpamClicks().longValue());
					}
					
					try {
						
						reportDetailedRemote.save(reportDetailed);
						
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void retrievePageFansLikeSource(List<ReportDetailed> reportsDetailed, JsonElement valueElement) {
		
		try {
			
			if(valueElement.isJsonObject()) {
				
				PageFansLikeSource pageFansLikeSource = gson.fromJson(valueElement, PageFansLikeSource.class);
				
				for(ReportDetailed reportDetailed : reportsDetailed) {
					
					if(reportDetailed.getProperty().equals(PAGE_FANS_PAGE_PROFILE)) {
						reportDetailed.setValue(pageFansLikeSource.getPageProfile().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_HOVERCARD)) {
						reportDetailed.setValue(pageFansLikeSource.getHovercard().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_MOBILE)) {
						reportDetailed.setValue(pageFansLikeSource.getMobile().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_EXTERNAL_CONNECT)) {
						reportDetailed.setValue(pageFansLikeSource.getExternalConnect().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_ADS)) {
						reportDetailed.setValue(pageFansLikeSource.getAds().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_LIKE_STORY)) {
						reportDetailed.setValue(pageFansLikeSource.getLikeStory().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_PHOTO_SNOWLIFT)) {
						reportDetailed.setValue(pageFansLikeSource.getPhotoSnowlift().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_PAGE_INVITE)) {
						reportDetailed.setValue(pageFansLikeSource.getPageInvite().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_RECOMMENDED_PAGES)) {
						reportDetailed.setValue(pageFansLikeSource.getRecommendedPages().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_SEARCH)) {
						reportDetailed.setValue(pageFansLikeSource.getSearch().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_PAGE_SUGGESTION)) {
						reportDetailed.setValue(pageFansLikeSource.getPageSuggestion().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_PAGE_TIMELINE)) {
						reportDetailed.setValue(pageFansLikeSource.getPageTimeline().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_MOBILE_ADS)) {
						reportDetailed.setValue(pageFansLikeSource.getMobileAds().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_REGISTRATION)) {
						reportDetailed.setValue(pageFansLikeSource.getRegistration().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_WIZARD_SUGGESTION)) {
						reportDetailed.setValue(pageFansLikeSource.getWizardSuggestion().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_PROFILE_CONNECT)) {
						reportDetailed.setValue(pageFansLikeSource.getProfileConnect().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_FAVORITES)) {
						reportDetailed.setValue(pageFansLikeSource.getFavorites().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_API)) {
						reportDetailed.setValue(pageFansLikeSource.getApi().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_PAGE_BROWSER)) {
						reportDetailed.setValue(pageFansLikeSource.getPageBrowser().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_MOBILE_PAGE_BROWSER)) {
						reportDetailed.setValue(pageFansLikeSource.getMobilePageBrowser().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_TICKER)) {
						reportDetailed.setValue(pageFansLikeSource.getTicker().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_FEED_CHAINING)) {
						reportDetailed.setValue(pageFansLikeSource.getFeedChaining().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_ALL_CATEGORY_PYML)) {
						reportDetailed.setValue(pageFansLikeSource.getAllCategoryPyml().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_PAGE_SUGGESTIONS_ON_LIKING)) {
						reportDetailed.setValue(pageFansLikeSource.getPageSuggestionsOnLiking().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_MOBILE_PAGE_SUGGESTIONS_ON_LIKING)) {
						reportDetailed.setValue(pageFansLikeSource.getMobilePageSuggestionsOnLiking().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_FAN_CONTEXT_STORY)) {
						reportDetailed.setValue(pageFansLikeSource.getFanContextStory().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_SPONSORED_STORY)) {
						reportDetailed.setValue(pageFansLikeSource.getSponsoredStory().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_FANS_PAGE_INVITE_ESCAPE_HATCH_FINCH)) {
						reportDetailed.setValue(pageFansLikeSource.getPageInviteEscapeHatchFinch().longValue());
					}
					
					try {
						
						reportDetailedRemote.save(reportDetailed);
						
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void retrievePageFansLikeSource(Long currentDay, JsonElement valueElement) {
		
		ReportDetailed reportDetailedPageProfile = new ReportDetailed();
		reportDetailedPageProfile.setPageId(pageId);
		reportDetailedPageProfile.setProperty(PAGE_FANS_PAGE_PROFILE);
		reportDetailedPageProfile.setDate(currentDay);
		reportDetailedPageProfile.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed reportDetailedHovercard = new ReportDetailed();
		reportDetailedHovercard.setPageId(pageId);
		reportDetailedHovercard.setProperty(PAGE_FANS_HOVERCARD);
		reportDetailedHovercard.setDate(currentDay);
		reportDetailedHovercard.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed reportDetailedMobile = new ReportDetailed();
		reportDetailedMobile.setPageId(pageId);
		reportDetailedMobile.setProperty(PAGE_FANS_MOBILE);
		reportDetailedMobile.setDate(currentDay);
		reportDetailedMobile.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed reportDetailedExternalConnect = new ReportDetailed();
		reportDetailedExternalConnect.setPageId(pageId);
		reportDetailedExternalConnect.setProperty(PAGE_FANS_EXTERNAL_CONNECT);
		reportDetailedExternalConnect.setDate(currentDay);
		reportDetailedExternalConnect.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed reportDetailedAds = new ReportDetailed();
		reportDetailedAds.setPageId(pageId);
		reportDetailedAds.setProperty(PAGE_FANS_ADS);
		reportDetailedAds.setDate(currentDay);
		reportDetailedAds.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed reportDetailedLikeStory = new ReportDetailed();
		reportDetailedLikeStory.setPageId(pageId);
		reportDetailedLikeStory.setProperty(PAGE_FANS_LIKE_STORY);
		reportDetailedLikeStory.setDate(currentDay);
		reportDetailedLikeStory.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed reportDetailedPhotoSnowlift = new ReportDetailed();
		reportDetailedPhotoSnowlift.setPageId(pageId);
		reportDetailedPhotoSnowlift.setProperty(PAGE_FANS_PHOTO_SNOWLIFT);
		reportDetailedPhotoSnowlift.setDate(currentDay);
		reportDetailedPhotoSnowlift.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed reportDetailedPageInvite = new ReportDetailed();
		reportDetailedPageInvite.setPageId(pageId);
		reportDetailedPageInvite.setProperty(PAGE_FANS_PAGE_INVITE);
		reportDetailedPageInvite.setDate(currentDay);
		reportDetailedPageInvite.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed reportDetailedRecommendedPages = new ReportDetailed();
		reportDetailedRecommendedPages.setPageId(pageId);
		reportDetailedRecommendedPages.setProperty(PAGE_FANS_RECOMMENDED_PAGES);
		reportDetailedRecommendedPages.setDate(currentDay);
		reportDetailedRecommendedPages.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed reportDetailedSearch = new ReportDetailed();
		reportDetailedSearch.setPageId(pageId);
		reportDetailedSearch.setProperty(PAGE_FANS_SEARCH);
		reportDetailedSearch.setDate(currentDay);
		reportDetailedSearch.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetPageSuggestion = new ReportDetailed();
		repDetPageSuggestion.setPageId(pageId);
		repDetPageSuggestion.setProperty(PAGE_FANS_PAGE_SUGGESTION);
		repDetPageSuggestion.setDate(currentDay);
		repDetPageSuggestion.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetPageTimeline = new ReportDetailed();
		repDetPageTimeline.setPageId(pageId);
		repDetPageTimeline.setProperty(PAGE_FANS_PAGE_TIMELINE);
		repDetPageTimeline.setDate(currentDay);
		repDetPageTimeline.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetMobileAds = new ReportDetailed();
		repDetMobileAds.setPageId(pageId);
		repDetMobileAds.setProperty(PAGE_FANS_MOBILE_ADS);
		repDetMobileAds.setDate(currentDay);
		repDetMobileAds.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetRegistration = new ReportDetailed();
		repDetRegistration.setPageId(pageId);
		repDetRegistration.setProperty(PAGE_FANS_REGISTRATION);
		repDetRegistration.setDate(currentDay);
		repDetRegistration.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetWizardSuggestion = new ReportDetailed();
		repDetWizardSuggestion.setPageId(pageId);
		repDetWizardSuggestion.setProperty(PAGE_FANS_WIZARD_SUGGESTION);
		repDetWizardSuggestion.setDate(currentDay);
		repDetWizardSuggestion.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetProfileConnect = new ReportDetailed();
		repDetProfileConnect.setPageId(pageId);
		repDetProfileConnect.setProperty(PAGE_FANS_PROFILE_CONNECT);
		repDetProfileConnect.setDate(currentDay);
		repDetProfileConnect.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetFavorites = new ReportDetailed();
		repDetFavorites.setPageId(pageId);
		repDetFavorites.setProperty(PAGE_FANS_FAVORITES);
		repDetFavorites.setDate(currentDay);
		repDetFavorites.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetApi = new ReportDetailed();
		repDetApi.setPageId(pageId);
		repDetApi.setProperty(PAGE_FANS_API);
		repDetApi.setDate(currentDay);
		repDetApi.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetPageBrowser = new ReportDetailed();
		repDetPageBrowser.setPageId(pageId);
		repDetPageBrowser.setProperty(PAGE_FANS_PAGE_BROWSER);
		repDetPageBrowser.setDate(currentDay);
		repDetPageBrowser.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetMobilePageBrowser = new ReportDetailed();
		repDetMobilePageBrowser.setPageId(pageId);
		repDetMobilePageBrowser.setProperty(PAGE_FANS_MOBILE_PAGE_BROWSER);
		repDetMobilePageBrowser.setDate(currentDay);
		repDetMobilePageBrowser.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetTicker = new ReportDetailed();
		repDetTicker.setPageId(pageId);
		repDetTicker.setProperty(PAGE_FANS_TICKER);
		repDetTicker.setDate(currentDay);
		repDetTicker.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetFeedChaining = new ReportDetailed();
		repDetFeedChaining.setPageId(pageId);
		repDetFeedChaining.setProperty(PAGE_FANS_FEED_CHAINING);
		repDetFeedChaining.setDate(currentDay);
		repDetFeedChaining.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetAllCategoryPyml = new ReportDetailed();
		repDetAllCategoryPyml.setPageId(pageId);
		repDetAllCategoryPyml.setProperty(PAGE_FANS_ALL_CATEGORY_PYML);
		repDetAllCategoryPyml.setDate(currentDay);
		repDetAllCategoryPyml.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetPageSuggestionsOnLiking = new ReportDetailed();
		repDetPageSuggestionsOnLiking.setPageId(pageId);
		repDetPageSuggestionsOnLiking.setProperty(PAGE_FANS_PAGE_SUGGESTIONS_ON_LIKING);
		repDetPageSuggestionsOnLiking.setDate(currentDay);
		repDetPageSuggestionsOnLiking.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetMobilePageSuggestionsOnLiking = new ReportDetailed();
		repDetMobilePageSuggestionsOnLiking.setPageId(pageId);
		repDetMobilePageSuggestionsOnLiking.setProperty(PAGE_FANS_MOBILE_PAGE_SUGGESTIONS_ON_LIKING);
		repDetMobilePageSuggestionsOnLiking.setDate(currentDay);
		repDetMobilePageSuggestionsOnLiking.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetFanContextStory = new ReportDetailed();
		repDetFanContextStory.setPageId(pageId);
		repDetFanContextStory.setProperty(PAGE_FANS_FAN_CONTEXT_STORY);
		repDetFanContextStory.setDate(currentDay);
		repDetFanContextStory.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetSponsoredStory = new ReportDetailed();
		repDetSponsoredStory.setPageId(pageId);
		repDetSponsoredStory.setProperty(PAGE_FANS_SPONSORED_STORY);
		repDetSponsoredStory.setDate(currentDay);
		repDetSponsoredStory.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		ReportDetailed repDetPageInviteEscapeHatchFinch = new ReportDetailed();
		repDetPageInviteEscapeHatchFinch.setPageId(pageId);
		repDetPageInviteEscapeHatchFinch.setProperty(PAGE_FANS_PAGE_INVITE_ESCAPE_HATCH_FINCH);
		repDetPageInviteEscapeHatchFinch.setDate(currentDay);
		repDetPageInviteEscapeHatchFinch.setFromGroup(GROUP_FANS_LIKE_SOURCE);
		
		try {

			if(valueElement.isJsonObject()) {
			
				PageFansLikeSource pageFansLikeSource = gson.fromJson(valueElement, PageFansLikeSource.class);
				
				reportDetailedPageProfile.setValue(pageFansLikeSource.getPageProfile().longValue());
				reportDetailedHovercard.setValue(pageFansLikeSource.getHovercard().longValue());
				reportDetailedMobile.setValue(pageFansLikeSource.getMobile().longValue());
				reportDetailedExternalConnect.setValue(pageFansLikeSource.getExternalConnect().longValue());
				reportDetailedAds.setValue(pageFansLikeSource.getAds().longValue());
				reportDetailedLikeStory.setValue(pageFansLikeSource.getLikeStory().longValue());
				reportDetailedPhotoSnowlift.setValue(pageFansLikeSource.getPhotoSnowlift().longValue());
				reportDetailedPageInvite.setValue(pageFansLikeSource.getPageInvite().longValue());
				reportDetailedRecommendedPages.setValue(pageFansLikeSource.getRecommendedPages().longValue());
				reportDetailedSearch.setValue(pageFansLikeSource.getSearch().longValue());
				
				repDetPageSuggestion.setValue(pageFansLikeSource.getPageSuggestion().longValue());
				repDetPageTimeline.setValue(pageFansLikeSource.getPageTimeline().longValue());
				repDetMobileAds.setValue(pageFansLikeSource.getMobileAds().longValue());
				repDetRegistration.setValue(pageFansLikeSource.getRegistration().longValue());
				repDetWizardSuggestion.setValue(pageFansLikeSource.getWizardSuggestion().longValue());
				repDetProfileConnect.setValue(pageFansLikeSource.getProfileConnect().longValue());
				repDetFavorites.setValue(pageFansLikeSource.getFavorites().longValue());
				repDetApi.setValue(pageFansLikeSource.getApi().longValue());
				repDetPageBrowser.setValue(pageFansLikeSource.getPageBrowser().longValue());
				repDetMobilePageBrowser.setValue(pageFansLikeSource.getMobilePageBrowser().longValue());
				repDetTicker.setValue(pageFansLikeSource.getTicker().longValue());
				repDetFeedChaining.setValue(pageFansLikeSource.getFeedChaining().longValue());
				repDetAllCategoryPyml.setValue(pageFansLikeSource.getAllCategoryPyml().longValue());
				repDetPageSuggestionsOnLiking.setValue(pageFansLikeSource.getPageSuggestionsOnLiking().longValue());
				repDetMobilePageSuggestionsOnLiking.setValue(pageFansLikeSource.getMobilePageSuggestionsOnLiking().longValue());
				repDetFanContextStory.setValue(pageFansLikeSource.getFanContextStory().longValue());
				repDetSponsoredStory.setValue(pageFansLikeSource.getSponsoredStory().longValue());
				repDetPageInviteEscapeHatchFinch.setValue(pageFansLikeSource.getPageInviteEscapeHatchFinch().longValue());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			
			reportDetailedRemote.save(reportDetailedPageProfile);
			reportDetailedRemote.save(reportDetailedHovercard);
			reportDetailedRemote.save(reportDetailedMobile);
			reportDetailedRemote.save(reportDetailedExternalConnect);
			reportDetailedRemote.save(reportDetailedAds);
			reportDetailedRemote.save(reportDetailedLikeStory);
			reportDetailedRemote.save(reportDetailedPhotoSnowlift);
			reportDetailedRemote.save(reportDetailedPageInvite);
			reportDetailedRemote.save(reportDetailedRecommendedPages);
			reportDetailedRemote.save(reportDetailedSearch);
			
			reportDetailedRemote.save(repDetPageSuggestion);
			reportDetailedRemote.save(repDetPageTimeline);
			reportDetailedRemote.save(repDetMobileAds);
			reportDetailedRemote.save(repDetRegistration);
			reportDetailedRemote.save(repDetWizardSuggestion);
			reportDetailedRemote.save(repDetProfileConnect);
			reportDetailedRemote.save(repDetFavorites);
			reportDetailedRemote.save(repDetApi);
			reportDetailedRemote.save(repDetPageBrowser);
			reportDetailedRemote.save(repDetMobilePageBrowser);
			reportDetailedRemote.save(repDetTicker);
			reportDetailedRemote.save(repDetFeedChaining);
			reportDetailedRemote.save(repDetAllCategoryPyml);
			reportDetailedRemote.save(repDetPageSuggestionsOnLiking);
			reportDetailedRemote.save(repDetMobilePageSuggestionsOnLiking);
			reportDetailedRemote.save(repDetFanContextStory);
			reportDetailedRemote.save(repDetSponsoredStory);
			reportDetailedRemote.save(repDetPageInviteEscapeHatchFinch);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void retrievePageNegativeFeedbackByType(Long currentDay, JsonElement valueElement) {
		
		ReportDetailed reportDetailedFeedbackHideCliks = new ReportDetailed();
		reportDetailedFeedbackHideCliks.setPageId(pageId);
		reportDetailedFeedbackHideCliks.setProperty(PAGE_NEGATIVE_FEEDBACK_HIDE_CLICKS);
		reportDetailedFeedbackHideCliks.setDate(currentDay);
		reportDetailedFeedbackHideCliks.setFromGroup(GROUP_NEGATIVE_FEEDBACK);
		
		ReportDetailed reportDetailedFeedbackHideAllClicks = new ReportDetailed();
		reportDetailedFeedbackHideAllClicks.setPageId(pageId);
		reportDetailedFeedbackHideAllClicks.setProperty(PAGE_NEGATIVE_FEEDBACK_HIDE_ALL_CLICKS);
		reportDetailedFeedbackHideAllClicks.setDate(currentDay);
		reportDetailedFeedbackHideAllClicks.setFromGroup(GROUP_NEGATIVE_FEEDBACK);
		
		ReportDetailed reportDetailedFeedbackReportSpamClicks = new ReportDetailed();
		reportDetailedFeedbackReportSpamClicks.setPageId(pageId);
		reportDetailedFeedbackReportSpamClicks.setProperty(PAGE_NEGATIVE_FEEDBACK_REPORT_SPAM_CLICKS);
		reportDetailedFeedbackReportSpamClicks.setDate(currentDay);
		reportDetailedFeedbackReportSpamClicks.setFromGroup(GROUP_NEGATIVE_FEEDBACK);
		
		try {
			
			String jsonResponse = valueElement.toString();
			
			JsonParser parser = new JsonParser();
			
			JsonElement jsonElement = parser.parse(jsonResponse);
			
			if(jsonElement.isJsonObject()) {

				PageNegativeFeedbacksType pageNegativeFeedbacksType = gson.fromJson(jsonResponse, PageNegativeFeedbacksType.class);

				reportDetailedFeedbackHideCliks.setValue(pageNegativeFeedbacksType.getHideClicks().longValue());
				reportDetailedFeedbackHideAllClicks.setValue(pageNegativeFeedbacksType.getHideAllClicks().longValue());
				reportDetailedFeedbackReportSpamClicks.setValue(pageNegativeFeedbacksType.getReportSpamClicks().longValue());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			
			reportDetailedRemote.save(reportDetailedFeedbackHideCliks);
			reportDetailedRemote.save(reportDetailedFeedbackHideAllClicks);
			reportDetailedRemote.save(reportDetailedFeedbackReportSpamClicks);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void retrievePagePositiveFeedbackByType(Long currentDay, JsonElement valueElement) {
		
		ReportDetailed reportDetailedFeedbackLike = new ReportDetailed();
		reportDetailedFeedbackLike.setPageId(pageId);
		reportDetailedFeedbackLike.setProperty(PAGE_POSITIVE_FEEDBACK_LIKE);
		reportDetailedFeedbackLike.setDate(currentDay);
		reportDetailedFeedbackLike.setFromGroup(GROUP_POSITIVE_FEEDBACK);
		
		ReportDetailed reportDetailedFeedbackComment = new ReportDetailed();
		reportDetailedFeedbackComment.setPageId(pageId);
		reportDetailedFeedbackComment.setProperty(PAGE_POSITIVE_FEEDBACK_COMMENT);
		reportDetailedFeedbackComment.setDate(currentDay);
		reportDetailedFeedbackComment.setFromGroup(GROUP_POSITIVE_FEEDBACK);
		
		ReportDetailed reportDetailedFeedbackLink = new ReportDetailed();
		reportDetailedFeedbackLink.setPageId(pageId);
		reportDetailedFeedbackLink.setProperty(PAGE_POSITIVE_FEEDBACK_LINK);
		reportDetailedFeedbackLink.setDate(currentDay);
		reportDetailedFeedbackLink.setFromGroup(GROUP_POSITIVE_FEEDBACK);
		
		try {
			
			String jsonResponse = valueElement.toString();

			JsonParser parser = new JsonParser();
			
			JsonElement jsonElement = parser.parse(jsonResponse);
			
			if(jsonElement.isJsonObject()) {

				PagePositiveFeedbacksType pagePositiveFeedbacksType = gson.fromJson(jsonResponse, PagePositiveFeedbacksType.class);

				reportDetailedFeedbackLike.setValue(pagePositiveFeedbacksType.getLike().longValue());
				reportDetailedFeedbackComment.setValue(pagePositiveFeedbacksType.getComment().longValue());
				reportDetailedFeedbackLink.setValue(pagePositiveFeedbacksType.getLink().longValue());
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			
			reportDetailedRemote.save(reportDetailedFeedbackLike);
			reportDetailedRemote.save(reportDetailedFeedbackComment);
			reportDetailedRemote.save(reportDetailedFeedbackLink);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void retrievePageFansOnline(Long currentDay, JsonElement valueElement) {
		
		try {
			
			if(valueElement.isJsonObject()) {

				JsonObject valueObject = valueElement.getAsJsonObject();

				for (Map.Entry<String,JsonElement> entry : valueObject.entrySet()) {
					
					ReportDetailed reportDetailed = new ReportDetailed();
					reportDetailed.setPageId(pageId);
					reportDetailed.setProperty(PAGE_FANS_ONLINE + "_" + entry.getKey());
					reportDetailed.setDate(currentDay);
					reportDetailed.setFromGroup(GROUP_FANS_ONLINE);
					reportDetailed.setValue(entry.getValue().getAsLong());
					
					try {
						
						reportDetailedRemote.save(reportDetailed);
						
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if(valueObject.entrySet().size() < 24) {
					normalizePageFansOnline(valueObject, pageId, currentDay);
				}
				
			} else {
				
				for (Integer i = 0; i < 24; i++) {

					ReportDetailed reportDetailed = new ReportDetailed();
					reportDetailed.setPageId(pageId);
					reportDetailed.setProperty(PAGE_FANS_ONLINE + "_" + i.toString());
					reportDetailed.setDate(currentDay);
					reportDetailed.setFromGroup(GROUP_FANS_ONLINE);

					try {

						reportDetailedRemote.save(reportDetailed);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void retrievePageFansOnline(List<ReportDetailed> reportsDetailed, JsonElement valueElement) {

		try {
			
			if(valueElement.isJsonObject()) {

				JsonObject valueObject = valueElement.getAsJsonObject();
				
				for (Map.Entry<String,JsonElement> entry : valueObject.entrySet()) {
					for(ReportDetailed reportDetailed : reportsDetailed) {
						
						if(reportDetailed.getProperty().equals(PAGE_FANS_ONLINE + "_" + entry.getKey())) {
							
							reportDetailed.setValue(entry.getValue().getAsLong());
							
							try {
								
								reportDetailedRemote.save(reportDetailed);
								
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							break;
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void normalizePageFansOnline(JsonObject objectValue, String pageId, Long currentDay) {
		
		for (Integer i = 0; i < 24; i++) {
			if(!objectValue.has(i.toString())) {
				
				ReportDetailed reportDetailed = new ReportDetailed();
				reportDetailed.setPageId(pageId);
				reportDetailed.setProperty(PAGE_FANS_ONLINE + "_" + i.toString());
				reportDetailed.setDate(currentDay);
				reportDetailed.setFromGroup(GROUP_FANS_ONLINE);
				reportDetailed.setValue(0L);
				
				try {
					
					reportDetailedRemote.save(reportDetailed);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void retrievePageStoriesStoryType(Long currentDay, JsonElement valueElement) {

		ReportDetailed reportDetailedStoriesByFan = new ReportDetailed();
		reportDetailedStoriesByFan.setPageId(pageId);
		reportDetailedStoriesByFan.setProperty(PAGE_STORIES_BY_FAN);
		reportDetailedStoriesByFan.setDate(currentDay);
		reportDetailedStoriesByFan.setFromGroup(GROUP_STORIES_TYPE);

		ReportDetailed reportDetailedStoriesByCheckin = new ReportDetailed();
		reportDetailedStoriesByCheckin.setPageId(pageId);
		reportDetailedStoriesByCheckin.setProperty(PAGE_STORIES_BY_CHECKIN);
		reportDetailedStoriesByCheckin.setDate(currentDay);
		reportDetailedStoriesByCheckin.setFromGroup(GROUP_STORIES_TYPE);

		ReportDetailed reportDetailedStoriesByOther = new ReportDetailed();
		reportDetailedStoriesByOther.setPageId(pageId);
		reportDetailedStoriesByOther.setProperty(PAGE_STORIES_BY_OTHER);
		reportDetailedStoriesByOther.setDate(currentDay);
		reportDetailedStoriesByOther.setFromGroup(GROUP_STORIES_TYPE);

		ReportDetailed reportDetailedStoriesByUserPost = new ReportDetailed();
		reportDetailedStoriesByUserPost.setPageId(pageId);
		reportDetailedStoriesByUserPost.setProperty(PAGE_STORIES_BY_USER_POST);
		reportDetailedStoriesByUserPost.setDate(currentDay);
		reportDetailedStoriesByUserPost.setFromGroup(GROUP_STORIES_TYPE);

		ReportDetailed reportDetailedStoriesByPagePost = new ReportDetailed();
		reportDetailedStoriesByPagePost.setPageId(pageId);
		reportDetailedStoriesByPagePost.setProperty(PAGE_STORIES_BY_PAGE_POST);
		reportDetailedStoriesByPagePost.setDate(currentDay);
		reportDetailedStoriesByPagePost.setFromGroup(GROUP_STORIES_TYPE);

		ReportDetailed reportDetailedStoriesByMention = new ReportDetailed();
		reportDetailedStoriesByMention.setPageId(pageId);
		reportDetailedStoriesByMention.setProperty(PAGE_STORIES_BY_MENTION);
		reportDetailedStoriesByMention.setDate(currentDay);
		reportDetailedStoriesByMention.setFromGroup(GROUP_STORIES_TYPE);
		
		ReportDetailed reportDetailedStoriesByCoupon = new ReportDetailed();
		reportDetailedStoriesByCoupon.setPageId(pageId);
		reportDetailedStoriesByCoupon.setProperty(PAGE_STORIES_BY_COUPON);
		reportDetailedStoriesByCoupon.setDate(currentDay);
		reportDetailedStoriesByCoupon.setFromGroup(GROUP_STORIES_TYPE);

		ReportDetailed reportDetailedStoriesByEvent = new ReportDetailed();
		reportDetailedStoriesByEvent.setPageId(pageId);
		reportDetailedStoriesByEvent.setProperty(PAGE_STORIES_BY_EVENT);
		reportDetailedStoriesByEvent.setDate(currentDay);
		reportDetailedStoriesByEvent.setFromGroup(GROUP_STORIES_TYPE);
		
		ReportDetailed reportDetailedStoriesByQuestion = new ReportDetailed();
		reportDetailedStoriesByQuestion.setPageId(pageId);
		reportDetailedStoriesByQuestion.setProperty(PAGE_STORIES_BY_QUESTION);
		reportDetailedStoriesByQuestion.setDate(currentDay);
		reportDetailedStoriesByQuestion.setFromGroup(GROUP_STORIES_TYPE);
		
		try {
			
			String jsonResponse = valueElement.toString();

			jsonResponse = replacePageStoriesStoryType(jsonResponse);

			JsonParser parser = new JsonParser();
			
			JsonElement jsonElement = parser.parse(jsonResponse);
			
			if(jsonElement.isJsonObject()) {

				PageStoriesStoryType pageStoriesStoryType = gson.fromJson(jsonResponse, PageStoriesStoryType.class);

				reportDetailedStoriesByFan.setValue(pageStoriesStoryType.getFan().longValue());
				reportDetailedStoriesByCheckin.setValue(pageStoriesStoryType.getCheckin().longValue());
				reportDetailedStoriesByOther.setValue(pageStoriesStoryType.getOther().longValue());
				reportDetailedStoriesByUserPost.setValue(pageStoriesStoryType.getUserPost().longValue());
				reportDetailedStoriesByPagePost.setValue(pageStoriesStoryType.getPagePost().longValue());
				reportDetailedStoriesByMention.setValue(pageStoriesStoryType.getMention().longValue());
				reportDetailedStoriesByCoupon.setValue(pageStoriesStoryType.getCoupon().longValue());
				reportDetailedStoriesByEvent.setValue(pageStoriesStoryType.getEvent().longValue());
				reportDetailedStoriesByQuestion.setValue(pageStoriesStoryType.getQuestion().longValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			
			reportDetailedRemote.save(reportDetailedStoriesByFan);
			reportDetailedRemote.save(reportDetailedStoriesByCheckin);
			reportDetailedRemote.save(reportDetailedStoriesByOther);
			reportDetailedRemote.save(reportDetailedStoriesByUserPost);
			reportDetailedRemote.save(reportDetailedStoriesByPagePost);
			reportDetailedRemote.save(reportDetailedStoriesByMention);
			reportDetailedRemote.save(reportDetailedStoriesByCoupon);
			reportDetailedRemote.save(reportDetailedStoriesByEvent);
			reportDetailedRemote.save(reportDetailedStoriesByQuestion);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void retrievePageStoriesStoryType(List<ReportDetailed> reportsDetailed, JsonElement valueElement) {

		try {
			
			String jsonResponse = valueElement.toString();

			jsonResponse = replacePageStoriesStoryType(jsonResponse);

			JsonParser parser = new JsonParser();
			
			JsonElement jsonElement = parser.parse(jsonResponse);
			
			if(jsonElement.isJsonObject()) {

				PageStoriesStoryType pageStoriesStoryType = gson.fromJson(jsonResponse, PageStoriesStoryType.class);

				for(ReportDetailed reportDetailed : reportsDetailed) {

					if(reportDetailed.getProperty().equals(PAGE_STORIES_BY_FAN)) {
						reportDetailed.setValue(pageStoriesStoryType.getFan().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_STORIES_BY_CHECKIN)) {
						reportDetailed.setValue(pageStoriesStoryType.getCheckin().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_STORIES_BY_OTHER)) {
						reportDetailed.setValue(pageStoriesStoryType.getOther().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_STORIES_BY_USER_POST)) {
						reportDetailed.setValue(pageStoriesStoryType.getUserPost().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_STORIES_BY_PAGE_POST)) {
						reportDetailed.setValue(pageStoriesStoryType.getPagePost().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_STORIES_BY_MENTION)) {
						reportDetailed.setValue(pageStoriesStoryType.getMention().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_STORIES_BY_COUPON)) {
						reportDetailed.setValue(pageStoriesStoryType.getCoupon().longValue());
					} else if(reportDetailed.getProperty().equals(PAGE_STORIES_BY_EVENT)) {
						reportDetailed.setValue(pageStoriesStoryType.getEvent().longValue());	
					} else if(reportDetailed.getProperty().equals(PAGE_STORIES_BY_QUESTION)) {
						reportDetailed.setValue(pageStoriesStoryType.getQuestion().longValue());
					}

					try {
						
						reportDetailedRemote.save(reportDetailed);
						
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void retrievePageImpressionsByCity(Long currentDay, JsonElement valueElement) {

		if(!reportControlRemote.hasEntity(pageId, Profile.FACEBOOK_PAGE, GROUP_IMP_CITY, currentDay)) {

			try {

				ReportControl reportControl = new ReportControl();
				reportControl.setNetworkId(pageId);
				reportControl.setNetworkType(Profile.FACEBOOK_PAGE);
				reportControl.setDate(currentDay);
				reportControl.setType(GROUP_IMP_CITY);
				reportControl.setStartDate(new Date().getTime() / 1000L);

				reportControl = reportControlRemote.save(reportControl);
					
				if(valueElement.isJsonObject()) {
					
					JsonObject object = valueElement.getAsJsonObject();
					
					for (Map.Entry<String,JsonElement> entry : object.entrySet()) {
						
						FaceReportLocation faceReportLocation = new FaceReportLocation();
						faceReportLocation.setPageId(pageId);
						faceReportLocation.setLocation(entry.getKey());
						faceReportLocation.setValue(entry.getValue().getAsLong());
						faceReportLocation.setDate(currentDay);
						faceReportLocation.setFromGroup(GROUP_IMP_CITY);

						try {
							
							faceReportLocationRemote.save(faceReportLocation);
							
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				reportControl = reportControlRemote.getById(reportControl.getIdReportsControl());

				reportControl.setFinishDate(new Date().getTime() / 1000L);
				reportControlRemote.save(reportControl);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} 
	
	private void retrievePageImpressionsByCountry(Long currentDay, JsonElement valueElement) {

		if(!reportControlRemote.hasEntity(pageId, Profile.FACEBOOK_PAGE, GROUP_IMP_COUNTRY, currentDay)) {

			try {

				ReportControl reportControl = new ReportControl();
				reportControl.setNetworkId(pageId);
				reportControl.setNetworkType(Profile.FACEBOOK_PAGE);
				reportControl.setDate(currentDay);
				reportControl.setType(GROUP_IMP_COUNTRY);
				reportControl.setStartDate(new Date().getTime() / 1000L);

				reportControl = reportControlRemote.save(reportControl);
					
				if(valueElement.isJsonObject()) {
					
					JsonObject object = valueElement.getAsJsonObject();
					
					for (Map.Entry<String,JsonElement> entry : object.entrySet()) {
						
						FaceReportLocation faceReportLocation = new FaceReportLocation();
						faceReportLocation.setPageId(pageId);
						faceReportLocation.setLocation(entry.getKey());
						faceReportLocation.setValue(entry.getValue().getAsLong());
						faceReportLocation.setDate(currentDay);
						faceReportLocation.setFromGroup(GROUP_IMP_COUNTRY);

						try {
							
							faceReportLocationRemote.save(faceReportLocation);
							
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				reportControl = reportControlRemote.getById(reportControl.getIdReportsControl());

				reportControl.setFinishDate(new Date().getTime() / 1000L);
				reportControlRemote.save(reportControl);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} 

	private void retrievePageFansCity(Long day, JsonElement valueElement) {

		Long lastDate = reportControlRemote.getDate(pageId, Profile.FACEBOOK_PAGE, GROUP_FAN_CITY);

		if (lastDate == null) {

			try {

				if(!valueElement.isJsonArray()) {

					ReportControl reportControl = new ReportControl();
					reportControl.setNetworkId(pageId);
					reportControl.setNetworkType(Profile.FACEBOOK_PAGE);
					reportControl.setDate(day);
					reportControl.setType(GROUP_FAN_CITY);

					try {

						reportControlRemote.save(reportControl);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					JsonObject object = valueElement.getAsJsonObject();

					for (Map.Entry<String,JsonElement> entry : object.entrySet()) {

						try {
						
							FaceReportLocation faceReportLocation = new FaceReportLocation();
							faceReportLocation.setPageId(pageId);
							faceReportLocation.setLocation(entry.getKey());
							faceReportLocation.setValue(entry.getValue().getAsLong());
							faceReportLocation.setDate(day);
							faceReportLocation.setFromGroup(GROUP_FAN_CITY);

							faceReportLocationRemote.save(faceReportLocation);

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {

			try {

				if(!valueElement.isJsonArray()) {

					ReportControl reportControl = reportControlRemote.getEntity(pageId, Profile.FACEBOOK_PAGE, GROUP_FAN_CITY);
					reportControl.setDate(day);

					try {
						
						reportControlRemote.save(reportControl);
						
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					JsonObject object = valueElement.getAsJsonObject();
					
					String location = "";
					for (Map.Entry<String,JsonElement> entry : object.entrySet()) {
						
						location = entry.getKey();

						FaceReportLocation faceReportLocation = faceReportLocationRemote.getEntity(pageId, location, GROUP_FAN_CITY);

						if(faceReportLocation != null) {

							faceReportLocation.setValue(entry.getValue().getAsLong());
							faceReportLocation.setDate(day);

							try {
								
								faceReportLocationRemote.save(faceReportLocation);
								
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						} else {

							faceReportLocation = new FaceReportLocation();
							faceReportLocation.setPageId(pageId);
							faceReportLocation.setLocation(location);
							faceReportLocation.setValue(entry.getValue().getAsLong());
							faceReportLocation.setDate(day);
							faceReportLocation.setFromGroup(GROUP_FAN_CITY);

							try {
								
								faceReportLocationRemote.save(faceReportLocation);
								
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void retrievePageFansCountry(Long day, JsonElement valueElement) {

		Long lastDate = reportControlRemote.getDate(pageId, Profile.FACEBOOK_PAGE, GROUP_FAN_COUNTRY);

		if (lastDate == null) {

			try {

				if(!valueElement.isJsonArray()) {

					ReportControl reportControl = new ReportControl();
					reportControl.setNetworkId(pageId);
					reportControl.setNetworkType(Profile.FACEBOOK_PAGE);
					reportControl.setDate(day);
					reportControl.setType(GROUP_FAN_COUNTRY);

					try {

						reportControlRemote.save(reportControl);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					JsonObject object = valueElement.getAsJsonObject();

					for (Map.Entry<String,JsonElement> entry : object.entrySet()) {

						try {
						
							FaceReportLocation faceReportLocation = new FaceReportLocation();
							faceReportLocation.setPageId(pageId);
							faceReportLocation.setLocation(entry.getKey());
							faceReportLocation.setValue(entry.getValue().getAsLong());
							faceReportLocation.setDate(day);
							faceReportLocation.setFromGroup(GROUP_FAN_COUNTRY);

							faceReportLocationRemote.save(faceReportLocation);

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {

			try {

				if(!valueElement.isJsonArray()) {

					ReportControl reportControl = reportControlRemote.getEntity(pageId, Profile.FACEBOOK_PAGE, GROUP_FAN_COUNTRY);
					reportControl.setDate(day);

					try {
						
						reportControlRemote.save(reportControl);
						
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					JsonObject object = valueElement.getAsJsonObject();
					
					String location = "";
					for (Map.Entry<String,JsonElement> entry : object.entrySet()) {
						
						location = entry.getKey();

						FaceReportLocation faceReportLocation = faceReportLocationRemote.getEntity(pageId, location, GROUP_FAN_COUNTRY);

						if(faceReportLocation != null) {

							faceReportLocation.setValue(entry.getValue().getAsLong());
							faceReportLocation.setDate(day);

							try {
								
								faceReportLocationRemote.save(faceReportLocation);
								
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						} else {

							faceReportLocation = new FaceReportLocation();
							faceReportLocation.setPageId(pageId);
							faceReportLocation.setLocation(location);
							faceReportLocation.setValue(entry.getValue().getAsLong());
							faceReportLocation.setDate(day);
							faceReportLocation.setFromGroup(GROUP_FAN_COUNTRY);

							try {
								
								faceReportLocationRemote.save(faceReportLocation);
								
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void retrievePageFansGenderAge(Long day, JsonElement valueElement) {

		Long lastDate = reportDetailedRemote.getDateForProperty(pageId, PAGE_FANS_MALE_13_AMONG_17); 

		if (lastDate == null) {
			
			/* Male statistics*/
			ReportDetailed reportDetailedMale13Among17 = new ReportDetailed();
			reportDetailedMale13Among17.setPageId(pageId);
			reportDetailedMale13Among17.setProperty(PAGE_FANS_MALE_13_AMONG_17);
			reportDetailedMale13Among17.setDate(day);
			reportDetailedMale13Among17.setFromGroup(GROUP_FANS_GENDER_AGE);

			ReportDetailed reportDetailedMale18Among24 = new ReportDetailed();
			reportDetailedMale18Among24.setPageId(pageId);
			reportDetailedMale18Among24.setProperty(PAGE_FANS_MALE_18_AMONG_24);
			reportDetailedMale18Among24.setDate(day);
			reportDetailedMale18Among24.setFromGroup(GROUP_FANS_GENDER_AGE);

			ReportDetailed reportDetailedMale25Among34 = new ReportDetailed();
			reportDetailedMale25Among34.setPageId(pageId);
			reportDetailedMale25Among34.setProperty(PAGE_FANS_MALE_25_AMONG_34);
			reportDetailedMale25Among34.setDate(day);
			reportDetailedMale25Among34.setFromGroup(GROUP_FANS_GENDER_AGE);

			ReportDetailed reportDetailedMale35Among44 = new ReportDetailed();
			reportDetailedMale35Among44.setPageId(pageId);
			reportDetailedMale35Among44.setProperty(PAGE_FANS_MALE_35_AMONG_44);
			reportDetailedMale35Among44.setDate(day);
			reportDetailedMale35Among44.setFromGroup(GROUP_FANS_GENDER_AGE);

			ReportDetailed reportDetailedMale45Among54 = new ReportDetailed();
			reportDetailedMale45Among54.setPageId(pageId);
			reportDetailedMale45Among54.setProperty(PAGE_FANS_MALE_45_AMONG_54);
			reportDetailedMale45Among54.setDate(day);
			reportDetailedMale45Among54.setFromGroup(GROUP_FANS_GENDER_AGE);

			ReportDetailed reportDetailedMale55Among64 = new ReportDetailed();
			reportDetailedMale55Among64.setPageId(pageId);
			reportDetailedMale55Among64.setProperty(PAGE_FANS_MALE_55_AMONG_64);
			reportDetailedMale55Among64.setDate(day);
			reportDetailedMale55Among64.setFromGroup(GROUP_FANS_GENDER_AGE);

			ReportDetailed reportDetailedMale65Plus = new ReportDetailed();
			reportDetailedMale65Plus.setPageId(pageId);
			reportDetailedMale65Plus.setProperty(PAGE_FANS_MALE_65_PLUS);
			reportDetailedMale65Plus.setDate(day);
			reportDetailedMale65Plus.setFromGroup(GROUP_FANS_GENDER_AGE);

			/*Female statistics*/
			ReportDetailed reportDetailedFemale13Among17 = new ReportDetailed();
			reportDetailedFemale13Among17.setPageId(pageId);
			reportDetailedFemale13Among17.setProperty(PAGE_FANS_FEMALE_13_AMONG_17);
			reportDetailedFemale13Among17.setDate(day);
			reportDetailedFemale13Among17.setFromGroup(GROUP_FANS_GENDER_AGE);

			ReportDetailed reportDetailedFemale18Among24 = new ReportDetailed();
			reportDetailedFemale18Among24.setPageId(pageId);
			reportDetailedFemale18Among24.setProperty(PAGE_FANS_FEMALE_18_AMONG_24);
			reportDetailedFemale18Among24.setDate(day);
			reportDetailedFemale18Among24.setFromGroup(GROUP_FANS_GENDER_AGE);

			ReportDetailed reportDetailedFemale25Among34 = new ReportDetailed();
			reportDetailedFemale25Among34.setPageId(pageId);
			reportDetailedFemale25Among34.setProperty(PAGE_FANS_FEMALE_25_AMONG_34);
			reportDetailedFemale25Among34.setDate(day);
			reportDetailedFemale25Among34.setFromGroup(GROUP_FANS_GENDER_AGE);

			ReportDetailed reportDetailedFemale35Among44 = new ReportDetailed();
			reportDetailedFemale35Among44.setPageId(pageId);
			reportDetailedFemale35Among44.setProperty(PAGE_FANS_FEMALE_35_AMONG_44);
			reportDetailedFemale35Among44.setDate(day);
			reportDetailedFemale35Among44.setFromGroup(GROUP_FANS_GENDER_AGE);

			ReportDetailed reportDetailedFemale45Among54 = new ReportDetailed();
			reportDetailedFemale45Among54.setPageId(pageId);
			reportDetailedFemale45Among54.setProperty(PAGE_FANS_FEMALE_45_AMONG_54);
			reportDetailedFemale45Among54.setDate(day);
			reportDetailedFemale45Among54.setFromGroup(GROUP_FANS_GENDER_AGE);

			ReportDetailed reportDetailedFemale55Among64 = new ReportDetailed();
			reportDetailedFemale55Among64.setPageId(pageId);
			reportDetailedFemale55Among64.setProperty(PAGE_FANS_FEMALE_55_AMONG_64);
			reportDetailedFemale55Among64.setDate(day);
			reportDetailedFemale55Among64.setFromGroup(GROUP_FANS_GENDER_AGE);

			ReportDetailed reportDetailedFemale65Plus = new ReportDetailed();
			reportDetailedFemale65Plus.setPageId(pageId);
			reportDetailedFemale65Plus.setProperty(PAGE_FANS_FEMALE_65_PLUS);
			reportDetailedFemale65Plus.setDate(day);
			reportDetailedFemale65Plus.setFromGroup(GROUP_FANS_GENDER_AGE);

			try {
				
				String jsonResponse = valueElement.toString();

				jsonResponse = replacePageFansGenderAge(jsonResponse);

				JsonParser parser = new JsonParser();
				
				JsonElement jsonElement = parser.parse(jsonResponse);
				
				if(!valueElement.isJsonArray()) {

					PageFansGenderAge pageFansGenderAge = gson.fromJson(jsonElement, PageFansGenderAge.class);

					/* Male statistics*/
					reportDetailedMale13Among17.setValue(pageFansGenderAge.getMaleThirteenAmongSeventeen().longValue());
					reportDetailedMale18Among24.setValue(pageFansGenderAge.getMaleEighteenAmongTwentyFour().longValue());
					reportDetailedMale25Among34.setValue(pageFansGenderAge.getMaleTwentyFiveAmongThirtyFour().longValue());
					reportDetailedMale35Among44.setValue(pageFansGenderAge.getMaleThirtyFiveAmongFourtyFour().longValue());
					reportDetailedMale45Among54.setValue(pageFansGenderAge.getMaleFourtyFiveAmongFiftyFour().longValue());
					reportDetailedMale55Among64.setValue(pageFansGenderAge.getMaleFiftyFiveAmongSixtyFour().longValue());
					reportDetailedMale65Plus.setValue(pageFansGenderAge.getMaleSixtyFivePlus().longValue());

					/*Female statistics*/
					reportDetailedFemale13Among17.setValue(pageFansGenderAge.getFemaleThirteenAmongSeventeen().longValue());
					reportDetailedFemale18Among24.setValue(pageFansGenderAge.getFemaleEighteenAmongTwentyFour().longValue());
					reportDetailedFemale25Among34.setValue(pageFansGenderAge.getFemaleTwentyFiveAmongThirtyFour().longValue());
					reportDetailedFemale35Among44.setValue(pageFansGenderAge.getFemaleThirtyFiveAmongFourtyFour().longValue());
					reportDetailedFemale45Among54.setValue(pageFansGenderAge.getFemaleFourtyFiveAmongFiftyFour().longValue());
					reportDetailedFemale55Among64.setValue(pageFansGenderAge.getFemaleFiftyFiveAmongSixtyFour().longValue());	
					reportDetailedFemale65Plus.setValue(pageFansGenderAge.getFemaleSixtyFivePlus().longValue());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}	

			try {

				/* Male statistics*/
				reportDetailedRemote.save(reportDetailedMale13Among17);
				reportDetailedRemote.save(reportDetailedMale18Among24);
				reportDetailedRemote.save(reportDetailedMale25Among34);
				reportDetailedRemote.save(reportDetailedMale35Among44);
				reportDetailedRemote.save(reportDetailedMale45Among54);
				reportDetailedRemote.save(reportDetailedMale55Among64);
				reportDetailedRemote.save(reportDetailedMale65Plus);

				/*Female statistics*/
				reportDetailedRemote.save(reportDetailedFemale13Among17);
				reportDetailedRemote.save(reportDetailedFemale18Among24);
				reportDetailedRemote.save(reportDetailedFemale25Among34);
				reportDetailedRemote.save(reportDetailedFemale35Among44);
				reportDetailedRemote.save(reportDetailedFemale45Among54);
				reportDetailedRemote.save(reportDetailedFemale55Among64);
				reportDetailedRemote.save(reportDetailedFemale65Plus);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			try {

				String jsonResponse = valueElement.toString();

				jsonResponse = replacePageFansGenderAge(jsonResponse);

				JsonParser parser = new JsonParser();
				
				JsonElement jsonElement = parser.parse(jsonResponse);
				
				if(!valueElement.isJsonArray()) {

					PageFansGenderAge pageFansGenderAge = gson.fromJson(jsonElement, PageFansGenderAge.class);

					/* Male statistics*/
					try {

						ReportDetailed reportDetailedMale13Among17 = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_MALE_13_AMONG_17);
						reportDetailedMale13Among17.setValue(pageFansGenderAge.getMaleThirteenAmongSeventeen().longValue());
						reportDetailedMale13Among17.setDate(day);

						reportDetailedRemote.save(reportDetailedMale13Among17);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						ReportDetailed reportDetailedMale18Among24 = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_MALE_18_AMONG_24);
						reportDetailedMale18Among24.setValue(pageFansGenderAge.getMaleEighteenAmongTwentyFour().longValue());
						reportDetailedMale18Among24.setDate(day);

						reportDetailedRemote.save(reportDetailedMale18Among24);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						ReportDetailed reportDetailedMale25Among34 = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_MALE_25_AMONG_34);
						reportDetailedMale25Among34.setValue(pageFansGenderAge.getMaleTwentyFiveAmongThirtyFour().longValue());
						reportDetailedMale25Among34.setDate(day);

						reportDetailedRemote.save(reportDetailedMale25Among34);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						ReportDetailed reportDetailedMale35Among44 = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_MALE_35_AMONG_44);
						reportDetailedMale35Among44.setValue(pageFansGenderAge.getMaleThirtyFiveAmongFourtyFour().longValue());
						reportDetailedMale35Among44.setDate(day);

						reportDetailedRemote.save(reportDetailedMale35Among44);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						ReportDetailed reportDetailedMale45Among54 = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_MALE_45_AMONG_54);
						reportDetailedMale45Among54.setValue(pageFansGenderAge.getMaleFourtyFiveAmongFiftyFour().longValue());
						reportDetailedMale45Among54.setDate(day);

						reportDetailedRemote.save(reportDetailedMale45Among54);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						ReportDetailed reportDetailedMale55Among64 = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_MALE_55_AMONG_64);
						reportDetailedMale55Among64.setValue(pageFansGenderAge.getMaleFiftyFiveAmongSixtyFour().longValue());
						reportDetailedMale55Among64.setDate(day);

						reportDetailedRemote.save(reportDetailedMale55Among64);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						ReportDetailed reportDetailedMale65Plus = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_MALE_65_PLUS);
						reportDetailedMale65Plus.setValue(pageFansGenderAge.getMaleSixtyFivePlus().longValue());
						reportDetailedMale65Plus.setDate(day);

						reportDetailedRemote.save(reportDetailedMale65Plus);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					/*Female statistics*/
					try {

						ReportDetailed reportDetailedFemale13Among17 = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_FEMALE_13_AMONG_17);
						reportDetailedFemale13Among17.setValue(pageFansGenderAge.getFemaleThirteenAmongSeventeen().longValue());
						reportDetailedFemale13Among17.setDate(day);

						reportDetailedRemote.save(reportDetailedFemale13Among17);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						ReportDetailed reportDetailedFemale18Among24 = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_FEMALE_18_AMONG_24);
						reportDetailedFemale18Among24.setValue(pageFansGenderAge.getFemaleEighteenAmongTwentyFour().longValue());
						reportDetailedFemale18Among24.setDate(day);

						reportDetailedRemote.save(reportDetailedFemale18Among24);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						ReportDetailed reportDetailedFemale25Among34 = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_FEMALE_25_AMONG_34);
						reportDetailedFemale25Among34.setValue(pageFansGenderAge.getFemaleTwentyFiveAmongThirtyFour().longValue());
						reportDetailedFemale25Among34.setDate(day);

						reportDetailedRemote.save(reportDetailedFemale25Among34);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						ReportDetailed reportDetailedFemale35Among44 = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_FEMALE_35_AMONG_44);
						reportDetailedFemale35Among44.setValue(pageFansGenderAge.getFemaleThirtyFiveAmongFourtyFour().longValue());
						reportDetailedFemale35Among44.setDate(day);

						reportDetailedRemote.save(reportDetailedFemale35Among44);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						ReportDetailed reportDetailedFemale45Among54 = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_FEMALE_45_AMONG_54);
						reportDetailedFemale45Among54.setValue(pageFansGenderAge.getFemaleFourtyFiveAmongFiftyFour().longValue());
						reportDetailedFemale45Among54.setDate(day);

						reportDetailedRemote.save(reportDetailedFemale45Among54);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						ReportDetailed reportDetailedFemale55Among64 = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_FEMALE_55_AMONG_64);
						reportDetailedFemale55Among64.setValue(pageFansGenderAge.getFemaleFiftyFiveAmongSixtyFour().longValue());
						reportDetailedFemale55Among64.setDate(day);

						reportDetailedRemote.save(reportDetailedFemale55Among64);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						ReportDetailed reportDetailedFemale65Plus = reportDetailedRemote.getEntityByPageIdProperty(pageId, PAGE_FANS_FEMALE_65_PLUS);
						reportDetailedFemale65Plus.setValue(pageFansGenderAge.getFemaleSixtyFivePlus().longValue());
						reportDetailedFemale65Plus.setDate(day);

						reportDetailedRemote.save(reportDetailedFemale65Plus);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String replacePageFansGenderAge(String responseJson) {

		String jsonReturn = responseJson;

		jsonReturn = jsonReturn.replace("value", "pageFansGenderAge");
		jsonReturn = jsonReturn.replace("M.13-17", "male_thirteen_among_seventeen");
		jsonReturn = jsonReturn.replace("M.18-24", "male_eighteen_among_twenty_four");
		jsonReturn = jsonReturn.replace("M.25-34", "male_twenty_five_among_thirty_four");
		jsonReturn = jsonReturn.replace("M.35-44", "male_thirty_five_among_fourty_four");
		jsonReturn = jsonReturn.replace("M.45-54", "male_fourty_five_among_fifty_four");
		jsonReturn = jsonReturn.replace("M.55-64", "male_fifty_five_among_sixty_four");
		jsonReturn = jsonReturn.replace("M.65+", "male_sixty_five_plus");
		jsonReturn = jsonReturn.replace("F.13-17", "female_thirteen_among_seventeen");
		jsonReturn = jsonReturn.replace("F.18-24", "female_eighteen_among_twenty_four");
		jsonReturn = jsonReturn.replace("F.25-34", "female_twenty_five_among_thirty_four");
		jsonReturn = jsonReturn.replace("F.35-44", "female_thirty_five_among_fourty_four");
		jsonReturn = jsonReturn.replace("F.45-54", "female_fourty_five_among_fifty_four");
		jsonReturn = jsonReturn.replace("F.55-64", "female_fifty_five_among_sixty_four");
		jsonReturn = jsonReturn.replace("F.65+", "female_sixty_five_plus");

		return jsonReturn;
	}

	private String replacePageImpressionsGenderAge(String responseJson) {

		String jsonReturn = responseJson;

		jsonReturn = jsonReturn.replace("M.13-17", "male_thirteen_among_seventeen");
		jsonReturn = jsonReturn.replace("M.18-24", "male_eighteen_among_twenty_four");
		jsonReturn = jsonReturn.replace("M.25-34", "male_twenty_five_among_thirty_four");
		jsonReturn = jsonReturn.replace("M.35-44", "male_thirty_five_among_fourty_four");
		jsonReturn = jsonReturn.replace("M.45-54", "male_fourty_five_among_fifty_four");
		jsonReturn = jsonReturn.replace("M.55-64", "male_fifty_five_among_sixty_four");
		jsonReturn = jsonReturn.replace("M.65+", "male_sixty_five_plus");
		jsonReturn = jsonReturn.replace("F.13-17", "female_thirteen_among_seventeen");
		jsonReturn = jsonReturn.replace("F.18-24", "female_eighteen_among_twenty_four");
		jsonReturn = jsonReturn.replace("F.25-34", "female_twenty_five_among_thirty_four");
		jsonReturn = jsonReturn.replace("F.35-44", "female_thirty_five_among_fourty_four");
		jsonReturn = jsonReturn.replace("F.45-54", "female_fourty_five_among_fifty_four");
		jsonReturn = jsonReturn.replace("F.55-64", "female_fifty_five_among_sixty_four");
		jsonReturn = jsonReturn.replace("F.65+", "female_sixty_five_plus");

		return jsonReturn;
	}

	private String replacePageConsumptionsType(String responseJson) {

		String jsonReturn = responseJson;

		jsonReturn = jsonReturn.replace("other clicks", "other_clicks");
		jsonReturn = jsonReturn.replace("link clicks", "link_clicks");
		jsonReturn = jsonReturn.replace("photo view", "photo_view");
		jsonReturn = jsonReturn.replace("video play", "video_play");

		return jsonReturn;
	}

	private String replacePageImpressionsStoryType(String responseJson) {

		String jsonReturn = responseJson;

		jsonReturn = jsonReturn.replace("user post", "user_post");
		jsonReturn = jsonReturn.replace("page post", "page_post");

		return jsonReturn;
	}

	private String replacePageStoriesStoryType(String responseJson) {

		String jsonReturn = responseJson;

		jsonReturn = jsonReturn.replace("user post", "user_post");
		jsonReturn = jsonReturn.replace("page post", "page_post");

		return jsonReturn;
	}
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}