package br.com.opsocial.server.utils;

import org.springframework.stereotype.Service;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.client.entity.report.facebook.ReportDetailedFacebookDTO;
import br.com.opsocial.client.entity.report.twitter.ReportDetailedTwitterDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.monitoring.MonitoringUser;
import br.com.opsocial.server.utils.reports.ReportFacebook;

@Service
public class UtilFunctionsService {

	public UtilFunctionsService() {
		
	}
	
	public String getSentimentName(Character sentiment) {
		
		String sentimentName = "";
		
		if(sentiment != null) {
			if(sentiment.equals(PostMonitoringDTO.POSITIVE)) {
				sentimentName = OpSocialBackApplication.messageByLocaleService.getMessage("monitoring.sentiment.positive");
			} else if(sentiment.equals(PostMonitoringDTO.NEGATIVE)) {
				sentimentName = OpSocialBackApplication.messageByLocaleService.getMessage("monitoring.sentiment.negative");
			} else if(sentiment.equals(PostMonitoringDTO.NEUTER)){
				sentimentName = OpSocialBackApplication.messageByLocaleService.getMessage("monitoring.sentiment.neuter");
			}
		}
		
		return sentimentName;
	}
	
	public String getUserSex(String sex) {
		
		String userSex = "";
		
		if(sex != null) {
			if(sex.equals(MonitoringUser.FEMALE)) {
				userSex = OpSocialBackApplication.messageByLocaleService.getMessage("monitoring.user.female");
			} else if(sex.equals(MonitoringUser.MALE)) {
				userSex = OpSocialBackApplication.messageByLocaleService.getMessage("monitoring.user.male");
			} else {
				userSex = OpSocialBackApplication.messageByLocaleService.getMessage("monitoring.user.unknown");
			}
		} else {
			userSex = OpSocialBackApplication.messageByLocaleService.getMessage("monitoring.user.unknown");
		}
		
		return userSex;
	}

	public String getGroupStatusName(String status) {

		if(status.equals(Profile.GROUP_STATUS_OPEN)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.group.open");
		} else if(status.equals(Profile.GROUP_STATUS_CLOSED)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.group.closed");
		} else if(status.equals(Profile.GROUP_STATUS_SECRET)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.group.secret");
		} else {
			return "";
		}
	}
	
	public String getFormattedWeekDayShort(int weekDay) {
		
		String formattedWeekDay = "";
		
		switch (weekDay) {
		case 0:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.short.sunday");
			break;
		case 1:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.short.monday");
			break;
		case 2:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.short.tuesday");
			break;	
		case 3:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.short.wednesday");
			break;
		case 4:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.short.thursday");
			break;
		case 5:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.short.friday");
			break;
		case 6:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.short.saturday");
			break;
		default:
			break;
		}
		
		return formattedWeekDay;
	}
	
	public String getFormattedWeekDay(int weekDay) {
		
		String formattedWeekDay = "";
		
		switch (weekDay) {
		case 0:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.complete.sunday");
			break;
		case 1:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.complete.monday");
			break;
		case 2:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.complete.tuesday");
			break;	
		case 3:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.complete.wednesday");
			break;
		case 4:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.complete.thursday");
			break;
		case 5:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.complete.friday");
			break;
		case 6:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.complete.saturday");
			break;
		default:
			break;
		}
		
		return formattedWeekDay;
	}
	
	public String getTweetTypeName(String tweetType) {
		
		if(tweetType.equals(ReportDetailedTwitterDTO.MSG_SENT_WITH_TEXT_COUNT)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("twitter.post.type.text");
		} else if(tweetType.equals(ReportDetailedTwitterDTO.MSG_SENT_WITH_PHOTO_COUNT)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("twitter.post.type.photo");
		} else if(tweetType.equals(ReportDetailedTwitterDTO.MSG_SENT_WITH_LINK_COUNT)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("twitter.post.type.link");
		} else {
			return "";
		}	
	}
	
	public String getFormattedMonth(String month) {
		
		String formattedMonth = "";
		
		if(month.endsWith("jan")) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.january");
		} else if(month.endsWith("feb")) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.february");
		} else if(month.endsWith("mar")) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.march");
		} else if(month.endsWith("apr")) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.april");
		} else if(month.endsWith("mai")) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.may");
		} else if(month.endsWith("jun")) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.june");
		} else if(month.endsWith("jul")) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.july");
		} else if(month.endsWith("aug")) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.august");
		} else if(month.endsWith("sep")) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.september");
		} else if(month.endsWith("oct")) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.october");
		} else if(month.endsWith("nov")) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.november");
		} else if(month.endsWith("dec")) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.december");
		}
		
		return formattedMonth;
	}
	
	public String getFormattedMonth(int month) {
		
		String formattedMonth = "";
		
		if(month == 0) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.january");
		} else if(month == 1) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.february");
		} else if(month == 2) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.march");
		} else if(month == 3) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.april");
		} else if(month == 4) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.may");
		} else if(month == 5) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.june");
		} else if(month == 6) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.july");
		} else if(month == 7) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.august");
		} else if(month == 8) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.september");
		} else if(month == 9) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.october");
		} else if(month == 10) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.november");
		} else if(month == 11) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("month.december");
		}
		
		return formattedMonth;
	}
	
	public String getLikeSourceName(String likeSource) {
		
		if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_PAGE_PROFILE)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.pageprofile");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_HOVERCARD)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.hovercard");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_PHOTO_SNOWLIFT)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.photosnowlift");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_MOBILE)){
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.mobile");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_EXTERNAL_CONNECT)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.externalconnect");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_ADS)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.ads");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_LIKE_STORY)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.likestory");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_FROM_PAGES)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.frompages");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_SEARCH)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.search");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_FROM_RECOMMENDATIONS)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.fromrecommendations");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_MOBILE_ADS)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.mobileads");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_API)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.api");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_PAGE_INVITE_ESCAPE_HATCH_FINCH)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.pageinviteescapehatchfinch");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_PAGE_SUGGESTION)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.pagesuggestion");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_FAN_CONTEXT_STORY)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.fancontextstory");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_REGISTRATION)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.registration");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_PAGE_TIMELINE)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.pagetimeline");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_WIZARD_SUGGESTION)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.wizardsuggestion");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_FROM_LIKE_SUGGESTIONS)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.fromlikesuggestions");
		} else if(likeSource.equals(ReportDetailedFacebookDTO.PAGE_FANS_SPONSORED_STORY)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.likesource.sponsoredstory");
		} else {
			return "";
		}
		
	}
	
	public String getPageNegativeFeedbackName(String pageNegativeFeedbackType) {
		
		if(pageNegativeFeedbackType.equals(ReportFacebook.PAGE_NEGATIVE_FEEDBACK_HIDE_ALL_CLICKS)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.negativefeedback.hideallclicks");
		} else if(pageNegativeFeedbackType.equals(ReportFacebook.PAGE_NEGATIVE_FEEDBACK_HIDE_CLICKS)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.negativefeedback.hideclicks");
		} else if(pageNegativeFeedbackType.equals(ReportFacebook.PAGE_NEGATIVE_FEEDBACK_REPORT_SPAM_CLICKS)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.negativefeedback.reportspamclicks");
		} else {
			return "";
		}
		
	}
	
	public String getConsumptionTypeName(String consumptionType) {
		
		if(consumptionType.equals(ReportFacebook.PAGE_CONSUMPTIONS_PHOTO_VIEW)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.consumptiontype.photoview");
		} else if(consumptionType.equals(ReportFacebook.PAGE_CONSUMPTIONS_LINK_CLICKS)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.consumptiontype.linkclicks");
		} else if(consumptionType.equals(ReportFacebook.PAGE_CONSUMPTIONS_VIDEO_PLAY)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.consumptiontype.videoplay");
		} else if(consumptionType.equals(ReportFacebook.PAGE_CONSUMPTIONS_OTHER_CLICKS)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.consumptiontype.otherclicks");
		} else {
			return "";
		}
	}
	
	public String getStoryTypeName(String storyType) {
		
		if(storyType.equals(ReportDetailedFacebookDTO.PAGE_STORIES_BY_MENTION)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.stotytype.mention");
		} else if(storyType.equals(ReportDetailedFacebookDTO.PAGE_STORIES_BY_CHECKIN)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.stotytype.checkin");
		} else if(storyType.equals(ReportDetailedFacebookDTO.PAGE_STORIES_BY_USER_POST)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("facebook.report.stotytype.userpost");
		} else {
			return "";
		}
	}
	
	public String getFormattedTinyWeekDay(int weekDay) {
		
		String formattedWeekDay = "";
		
		switch (weekDay) {
		case 0:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.tiny.sunday");
			break;
		case 1:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.tiny.monday");
			break;
		case 2:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.tiny.tuesday");
			break;	
		case 3:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.tiny.wednesday");
			break;
		case 4:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.tiny.thursday");
			break;
		case 5:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.tiny.friday");
			break;
		case 6:
			formattedWeekDay = OpSocialBackApplication.messageByLocaleService.getMessage("weekday.tiny.saturday");
			break;
		default:
			break;
		}
		
		return formattedWeekDay;
	}
	
}
