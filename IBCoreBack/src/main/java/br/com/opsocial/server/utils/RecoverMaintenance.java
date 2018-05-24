package br.com.opsocial.server.utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceAnniversarieRemote;
import br.com.opsocial.ejb.das.MaintenanceBlogPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceBlogPostRemote;
import br.com.opsocial.ejb.das.MaintenanceCardTransactionRemote;
import br.com.opsocial.ejb.das.MaintenanceColorTagRemote;
import br.com.opsocial.ejb.das.MaintenanceConversationRemote;
import br.com.opsocial.ejb.das.MaintenanceExceptionRemote;
import br.com.opsocial.ejb.das.MaintenanceFacePostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceFaceReportLocationRemote;
import br.com.opsocial.ejb.das.MaintenanceFaceReportPostCommentRemote;
import br.com.opsocial.ejb.das.MaintenanceFaceReportPostRemote;
import br.com.opsocial.ejb.das.MaintenanceFacebookInfluentialUserRemote;
import br.com.opsocial.ejb.das.MaintenanceFacebookPostInsightRemote;
import br.com.opsocial.ejb.das.MaintenanceFacebookPostRemote;
import br.com.opsocial.ejb.das.MaintenanceGenericRemote;
import br.com.opsocial.ejb.das.MaintenanceGooglePagingRemote;
import br.com.opsocial.ejb.das.MaintenanceGooglePlusPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceGooglePlusPostRemote;
import br.com.opsocial.ejb.das.MaintenanceGroupRemote;
import br.com.opsocial.ejb.das.MaintenanceGroupsProfilesRemote;
import br.com.opsocial.ejb.das.MaintenanceGroupsRssfeedsRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramDeviceRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramFollowerRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostCommentRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostLikeRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostTagRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramProfileDeviceRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramReportPostRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramReportRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramUserRemote;
import br.com.opsocial.ejb.das.MaintenanceInternalAnalyticRemote;
import br.com.opsocial.ejb.das.MaintenanceLinkedinPostRemote;
import br.com.opsocial.ejb.das.MaintenanceMessageRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringGroupReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringHashTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringInfluentialUserRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLinkRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLocationRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLocationReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLogReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringPostLocationRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringPostTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringQualificationReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringReportControlRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringSearchRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTermReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringUserRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringUserReportRemote;
import br.com.opsocial.ejb.das.MaintenanceNewsPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceNewsPostRemote;
import br.com.opsocial.ejb.das.MaintenanceNotificationRemote;
import br.com.opsocial.ejb.das.MaintenanceObjectAttachmentRemote;
import br.com.opsocial.ejb.das.MaintenanceOpLogRemote;
import br.com.opsocial.ejb.das.MaintenancePostRemote;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceProfileUserDashboardRemote;
import br.com.opsocial.ejb.das.MaintenanceProxyRegionRemote;
import br.com.opsocial.ejb.das.MaintenanceRateLimitStatusRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostRemote;
import br.com.opsocial.ejb.das.MaintenanceReportControlRemote;
import br.com.opsocial.ejb.das.MaintenanceReportDetailedRemote;
import br.com.opsocial.ejb.das.MaintenanceReportGenerateRemote;
import br.com.opsocial.ejb.das.MaintenanceReportLogoRemote;
import br.com.opsocial.ejb.das.MaintenanceRssFeedRemote;
import br.com.opsocial.ejb.das.MaintenanceSamplePostRemote;
import br.com.opsocial.ejb.das.MaintenanceSampleRemote;
import br.com.opsocial.ejb.das.MaintenanceSampleReportRemote;
import br.com.opsocial.ejb.das.MaintenanceSetRemote;
import br.com.opsocial.ejb.das.MaintenanceStreamRemote;
import br.com.opsocial.ejb.das.MaintenanceSuggestionRemote;
import br.com.opsocial.ejb.das.MaintenanceTabRemote;
import br.com.opsocial.ejb.das.MaintenanceTimezoneRemote;
import br.com.opsocial.ejb.das.MaintenanceTrendingTopicRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterFollowerControlRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterFollowerRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterFollowerReportRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPagingRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostURLRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterReportRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterStatusRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterStatusReportRemote;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.das.MaintenanceYoutubePostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceYoutubePostRemote;
import br.com.opsocial.ejb.das.generic.AbstractDAS;

public abstract class RecoverMaintenance {
	
	public static AbstractDAS recoverMaintenance(String entity) {
		
		if(entity.equals("User")) {
			
			MaintenanceUserRemote remote = null;
			
				try {
					remote = (MaintenanceUserRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceUserBean!br.com.opsocial.ejb.das.MaintenanceUserRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			
			return remote;
			
		} else if(entity.equals("FaceReportLocation")) {
			
			MaintenanceFaceReportLocationRemote remote = null;
			
				try {
					remote = (MaintenanceFaceReportLocationRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceFaceReportLocationBean!br.com.opsocial.ejb.das.MaintenanceFaceReportLocationRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("FaceReportPost")) {
			
			MaintenanceFaceReportPostRemote remote = null;
			
				try {
					remote = (MaintenanceFaceReportPostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceFaceReportPostBean!br.com.opsocial.ejb.das.MaintenanceFaceReportPostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("InstagramPost")) {
			
			MaintenanceInstagramPostRemote remote = null;
			
				try {
					remote = (MaintenanceInstagramPostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceInstagramPostBean!br.com.opsocial.ejb.das.MaintenanceInstagramPostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("Monitoring")) {
			
			MaintenanceMonitoringRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringBean!br.com.opsocial.ejb.das.MaintenanceMonitoringRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("Notification")) {
			
			MaintenanceNotificationRemote remote = null;
			
				try {
					remote = (MaintenanceNotificationRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceNotificationBean!br.com.opsocial.ejb.das.MaintenanceNotificationRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("Profile")) {
			
			MaintenanceProfileRemote remote = null;
			
				try {
					remote = (MaintenanceProfileRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceProfileBean!br.com.opsocial.ejb.das.MaintenanceProfileRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("ReportControl")) {
			
			MaintenanceReportControlRemote remote = null;
			
				try {
					remote = (MaintenanceReportControlRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceReportControlBean!br.com.opsocial.ejb.das.MaintenanceReportControlRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("ReportDetailed")) {
			
			MaintenanceReportDetailedRemote remote = null;
			
				try {
					remote = (MaintenanceReportDetailedRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceReportDetailedBean!br.com.opsocial.ejb.das.MaintenanceReportDetailedRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
				
			return remote;
			
		} else if(entity.equals("ReportGenerate")) {
			
			MaintenanceReportGenerateRemote remote = null;
			
				try {
					remote = (MaintenanceReportGenerateRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceReportGenerateBean!br.com.opsocial.ejb.das.MaintenanceReportGenerateRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("Set")) {
			
			MaintenanceSetRemote remote = null;
			
				try {
					remote = (MaintenanceSetRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceSetBean!br.com.opsocial.ejb.das.MaintenanceSetRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("Post")) {
			
			MaintenancePostRemote remote = null;
			
				try {
					remote = (MaintenancePostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenancePostBean!br.com.opsocial.ejb.das.MaintenancePostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("TwitterReport")) {
			
			MaintenanceTwitterReportRemote remote = null;
			
				try {
					remote = (MaintenanceTwitterReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTwitterReportBean!br.com.opsocial.ejb.das.MaintenanceTwitterReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("TwitterStatus")) {
			
			MaintenanceTwitterStatusRemote remote = null;
			
				try {
					remote = (MaintenanceTwitterStatusRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTwitterStatusBean!br.com.opsocial.ejb.das.MaintenanceTwitterStatusRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("TwitterStatusReport")) {
			
			MaintenanceTwitterStatusReportRemote remote = null;
			
				try {
					remote = (MaintenanceTwitterStatusReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTwitterStatusReportBean!br.com.opsocial.ejb.das.MaintenanceTwitterStatusReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("FacebookPost")) {
			
			MaintenanceFacebookPostRemote remote = null;
			
				try {
					remote = (MaintenanceFacebookPostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceFacebookPostBean!br.com.opsocial.ejb.das.MaintenanceFacebookPostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("FacePostMonitoring")) {
			
			MaintenanceFacePostMonitoringRemote remote = null;
			
				try {
					remote = (MaintenanceFacePostMonitoringRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceFacePostMonitoringBean!br.com.opsocial.ejb.das.MaintenanceFacePostMonitoringRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("GooglePlusPost")) {
			
			MaintenanceGooglePlusPostRemote remote = null;
			
				try {
					remote = (MaintenanceGooglePlusPostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceGooglePlusPostBean!br.com.opsocial.ejb.das.MaintenanceGooglePlusPostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("GooglePlusPostMonitoring")) {
			
			MaintenanceGooglePlusPostMonitoringRemote remote = null;
			
				try {
					remote = (MaintenanceGooglePlusPostMonitoringRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceGooglePlusPostMonitoringBean!br.com.opsocial.ejb.das.MaintenanceGooglePlusPostMonitoringRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("ObjectAttachment")) {
			
			MaintenanceObjectAttachmentRemote remote = null;
			
				try {
					remote = (MaintenanceObjectAttachmentRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceObjectAttachmentBean!br.com.opsocial.ejb.das.MaintenanceObjectAttachmentRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			
			return remote;
			
		} else if(entity.equals("RateLimitStatus")) {
			
			MaintenanceRateLimitStatusRemote remote = null;
			
				try {
					remote = (MaintenanceRateLimitStatusRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceRateLimitStatusBean!br.com.opsocial.ejb.das.MaintenanceRateLimitStatusRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
				
			return remote;
			
		} else if(entity.equals("TwitterPost")) {
			
			MaintenanceTwitterPostRemote remote = null;
			
				try {
					remote = (MaintenanceTwitterPostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTwitterPostBean!br.com.opsocial.ejb.das.MaintenanceTwitterPostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
				
			return remote;
			
		} else if(entity.equals("TwitterPostMonitoring")) {
			
			MaintenanceTwitterPostMonitoringRemote remote = null;
			
				try {
					remote = (MaintenanceTwitterPostMonitoringRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTwitterPostMonitoringBean!br.com.opsocial.ejb.das.MaintenanceTwitterPostMonitoringRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("YoutubePost")) {
			
			MaintenanceYoutubePostRemote remote = null;
			
				try {
					remote = (MaintenanceYoutubePostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceYoutubePostBean!br.com.opsocial.ejb.das.MaintenanceYoutubePostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("YoutubePostMonitoring")) {
			
			MaintenanceYoutubePostMonitoringRemote remote = null;
			
				try {
					remote = (MaintenanceYoutubePostMonitoringRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceYoutubePostMonitoringBean!br.com.opsocial.ejb.das.MaintenanceYoutubePostMonitoringRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("MonitoringReport")) {
			
			MaintenanceMonitoringReportRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringReportBean!br.com.opsocial.ejb.das.MaintenanceMonitoringReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("FacebookInfluentialUser")) {
			
			MaintenanceFacebookInfluentialUserRemote remote = null;
			
				try {
					remote = (MaintenanceFacebookInfluentialUserRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceFacebookInfluentialUserBean!br.com.opsocial.ejb.das.MaintenanceFacebookInfluentialUserRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("Suggestion")) {
			
			MaintenanceSuggestionRemote remote = null;
			
				try {
					remote = (MaintenanceSuggestionRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceSuggestionBean!br.com.opsocial.ejb.das.MaintenanceSuggestionRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("Anniversarie")) {
			
			MaintenanceAnniversarieRemote remote = null;
			
				try {
					remote = (MaintenanceAnniversarieRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceAnniversarieBean!br.com.opsocial.ejb.das.MaintenanceAnniversarieRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("Account")) {
			
			MaintenanceAccountRemote remote = null;
			
				try {
					remote = (MaintenanceAccountRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceAccountBean!br.com.opsocial.ejb.das.MaintenanceAccountRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("InstagramPostMonitoring")) {
			
			MaintenanceInstagramPostMonitoringRemote remote = null;
			
				try {
					remote = (MaintenanceInstagramPostMonitoringRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceInstagramPostMonitoringBean!br.com.opsocial.ejb.das.MaintenanceInstagramPostMonitoringRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("FacebookPostInsight")) {
			
			MaintenanceFacebookPostInsightRemote remote = null;
			
				try {
					remote = (MaintenanceFacebookPostInsightRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceFacebookPostInsightBean!br.com.opsocial.ejb.das.MaintenanceFacebookPostInsightRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("TwitterFollower")) {
			
			MaintenanceTwitterFollowerRemote remote = null;
			
				try {
					remote = (MaintenanceTwitterFollowerRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTwitterFollowerBean!br.com.opsocial.ejb.das.MaintenanceTwitterFollowerRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("TwitterFollowerControl")) {
			
			MaintenanceTwitterFollowerControlRemote remote = null;
			
				try {
					remote = (MaintenanceTwitterFollowerControlRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTwitterFollowerControlBean!br.com.opsocial.ejb.das.MaintenanceTwitterFollowerControlRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("TwitterFollowerReport")) {
			
			MaintenanceTwitterFollowerReportRemote remote = null;
			
				try {
					remote = (MaintenanceTwitterFollowerReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTwitterFollowerReportBean!br.com.opsocial.ejb.das.MaintenanceTwitterFollowerReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringUser")) {
			
			MaintenanceMonitoringUserRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringUserRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringUserBean!br.com.opsocial.ejb.das.MaintenanceMonitoringUserRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringInfluentialUser")) {
			
			MaintenanceMonitoringInfluentialUserRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringInfluentialUserRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringInfluentialUserBean!br.com.opsocial.ejb.das.MaintenanceMonitoringInfluentialUserRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("ReclameAquiPost")) {
			
			MaintenanceReclameAquiPostRemote remote = null;
			
				try {
					remote = (MaintenanceReclameAquiPostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceReclameAquiPostBean!br.com.opsocial.ejb.das.MaintenanceReclameAquiPostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("ReclameAquiPostMonitoring")) {
			
			MaintenanceReclameAquiPostMonitoringRemote remote = null;
			
				try {
					remote = (MaintenanceReclameAquiPostMonitoringRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceReclameAquiPostMonitoringBean!br.com.opsocial.ejb.das.MaintenanceReclameAquiPostMonitoringRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringPostTag")) {
			
			MaintenanceMonitoringPostTagRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringPostTagRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringPostTagBean!br.com.opsocial.ejb.das.MaintenanceMonitoringPostTagRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
				
			return remote;
		} else if(entity.equals("MonitoringTag")) {
			
			MaintenanceMonitoringTagRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringTagRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringTagBean!br.com.opsocial.ejb.das.MaintenanceMonitoringTagRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("ColorTag")) {
			
			MaintenanceColorTagRemote remote = null;
			
				try {
					remote = (MaintenanceColorTagRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceColorTagBean!br.com.opsocial.ejb.das.MaintenanceColorTagRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		}  else if(entity.equals("NewsPost")) {
			
			MaintenanceNewsPostRemote remote = null;
			
				try {
					remote = (MaintenanceNewsPostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceNewsPostBean!br.com.opsocial.ejb.das.MaintenanceNewsPostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
				
			return remote;
			
		} else if(entity.equals("NewsPostMonitoring")) {
			
			MaintenanceNewsPostMonitoringRemote remote = null;
			
				try {
					remote = (MaintenanceNewsPostMonitoringRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceNewsPostMonitoringBean!br.com.opsocial.ejb.das.MaintenanceNewsPostMonitoringRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		}  else if(entity.equals("BlogPost")) {
			
			MaintenanceBlogPostRemote remote = null;
			
				try {
					remote = (MaintenanceBlogPostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceBlogPostBean!br.com.opsocial.ejb.das.MaintenanceBlogPostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;

		} else if(entity.equals("BlogPostMonitoring")) {
			
			MaintenanceBlogPostMonitoringRemote remote = null;
			
				try {
					remote = (MaintenanceBlogPostMonitoringRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceBlogPostMonitoringBean!br.com.opsocial.ejb.das.MaintenanceBlogPostMonitoringRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("InstagramReportPost")) {
			
			MaintenanceInstagramReportPostRemote remote = null;
			
				try {
					remote = (MaintenanceInstagramReportPostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceInstagramReportPostBean!br.com.opsocial.ejb.das.MaintenanceInstagramReportPostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("InstagramPostComment")) {
			
			MaintenanceInstagramPostCommentRemote remote = null;
			
				try {
					remote = (MaintenanceInstagramPostCommentRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceInstagramPostCommentBean!br.com.opsocial.ejb.das.MaintenanceInstagramPostCommentRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("InstagramPostLike")) {
			
			MaintenanceInstagramPostLikeRemote remote = null;
			
				try {
					remote = (MaintenanceInstagramPostLikeRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceInstagramPostLikeBean!br.com.opsocial.ejb.das.MaintenanceInstagramPostLikeRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("InstagramPostTag")) {
			
			MaintenanceInstagramPostTagRemote remote = null;
			
				try {
					remote = (MaintenanceInstagramPostTagRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceInstagramPostTagBean!br.com.opsocial.ejb.das.MaintenanceInstagramPostTagRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("InstagramFollower")) {
			
			MaintenanceInstagramFollowerRemote remote = null;
			
				try {
					remote = (MaintenanceInstagramFollowerRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceInstagramFollowerBean!br.com.opsocial.ejb.das.MaintenanceInstagramFollowerRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;	
		} else if(entity.equals("ReportLogo")) {
			
			MaintenanceReportLogoRemote remote = null;
			
				try {
					remote = (MaintenanceReportLogoRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceReportLogoBean!br.com.opsocial.ejb.das.MaintenanceReportLogoRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("Conversation")) {
			
			MaintenanceConversationRemote remote = null;
			
				try {
					remote = (MaintenanceConversationRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceConversationBean!br.com.opsocial.ejb.das.MaintenanceConversationRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("Message")) {
			
			MaintenanceMessageRemote remote = null;
			
				try {
					remote = (MaintenanceMessageRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMessageBean!br.com.opsocial.ejb.das.MaintenanceMessageRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringLocation")) {
			
			MaintenanceMonitoringLocationRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringLocationRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringLocationBean!br.com.opsocial.ejb.das.MaintenanceMonitoringLocationRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				} 
			
			return remote;
		} else if(entity.equals("MonitoringPostLocation")) {
			
			MaintenanceMonitoringPostLocationRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringPostLocationRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringPostLocationBean!br.com.opsocial.ejb.das.MaintenanceMonitoringPostLocationRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringHashTag")) {
			
			MaintenanceMonitoringHashTagRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringHashTagRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringHashTagBean!br.com.opsocial.ejb.das.MaintenanceMonitoringHashTagRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringLink")) {
			
			MaintenanceMonitoringLinkRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringLinkRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringLinkRemoteBean!br.com.opsocial.ejb.das.MaintenanceMonitoringLinkRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringQualificationReport")) {
			
			MaintenanceMonitoringQualificationReportRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringQualificationReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringQualificationReportBean!br.com.opsocial.ejb.das.MaintenanceMonitoringQualificationReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringReportControl")) {
			
			MaintenanceMonitoringReportControlRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringReportControlRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringReportControlBean!br.com.opsocial.ejb.das.MaintenanceMonitoringReportControlRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringTagReport")) {
			
			MaintenanceMonitoringTagReportRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringTagReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringTagReportBean!br.com.opsocial.ejb.das.MaintenanceMonitoringTagReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("Sample")) {
			
			MaintenanceSampleRemote remote = null;
			
				try {
					remote = (MaintenanceSampleRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceSampleBean!br.com.opsocial.ejb.das.MaintenanceSampleRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("SamplePost")) {
			
			MaintenanceSamplePostRemote remote = null;
			
				try {
					remote = (MaintenanceSamplePostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceSamplePostBean!br.com.opsocial.ejb.das.MaintenanceSamplePostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
			
		} else if(entity.equals("SampleReport")) {
			
			MaintenanceSampleReportRemote remote = null;
			
				try {
					remote = (MaintenanceSampleReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceSampleReportBean!br.com.opsocial.ejb.das.MaintenanceSampleReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringTermReport")) {
			
			MaintenanceMonitoringTermReportRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringTermReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringTermReportBean!br.com.opsocial.ejb.das.MaintenanceMonitoringTermReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringUserReport")) {
			
			MaintenanceMonitoringUserReportRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringUserReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringUserReportBean!br.com.opsocial.ejb.das.MaintenanceMonitoringUserReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringLocationReport")) {
			
			MaintenanceMonitoringLocationReportRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringLocationReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringLocationReportBean!br.com.opsocial.ejb.das.MaintenanceMonitoringLocationReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("Exception")) {
			
			MaintenanceExceptionRemote remote = null;
			
				try {
					remote = (MaintenanceExceptionRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceExceptionBean!br.com.opsocial.ejb.das.MaintenanceExceptionRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("FaceReportPostComment")) {
			
			MaintenanceFaceReportPostCommentRemote remote = null;
			
				try {
					remote = (MaintenanceFaceReportPostCommentRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceFaceReportPostCommentBean!br.com.opsocial.ejb.das.MaintenanceFaceReportPostCommentRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringLogReport")) {
			
			MaintenanceMonitoringLogReportRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringLogReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringLogReportBean!br.com.opsocial.ejb.das.MaintenanceMonitoringLogReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringGroupReport")) {
			
			MaintenanceMonitoringGroupReportRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringGroupReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringGroupReportBean!br.com.opsocial.ejb.das.MaintenanceMonitoringGroupReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("TwitterPostURL")) {
			
			MaintenanceTwitterPostURLRemote remote = null;
			
				try {
					remote = (MaintenanceTwitterPostURLRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTwitterPostURLBean!br.com.opsocial.ejb.das.MaintenanceTwitterPostURLRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("InstagramUser")) {
			
			MaintenanceInstagramUserRemote remote = null;
			
				try {
					remote = (MaintenanceInstagramUserRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceInstagramUserBean!br.com.opsocial.ejb.das.MaintenanceInstagramUserRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("InstagramReport")) {
			
			MaintenanceInstagramReportRemote remote = null;
			
				try {
					remote = (MaintenanceInstagramReportRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceInstagramReportBean!br.com.opsocial.ejb.das.MaintenanceInstagramReportRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("TwitterPaging")) {
			
			MaintenanceTwitterPagingRemote remote = null;
			
				try {
					remote = (MaintenanceTwitterPagingRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTwitterPagingBean!br.com.opsocial.ejb.das.MaintenanceTwitterPagingRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("GooglePaging")) {
			
			MaintenanceGooglePagingRemote remote = null;
			
				try {
					remote = (MaintenanceGooglePagingRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceGooglePagingBean!br.com.opsocial.ejb.das.MaintenanceGooglePagingRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("ProfileUserDashboard")) {
			
			MaintenanceProfileUserDashboardRemote remote = null;
			
				try {
					remote = (MaintenanceProfileUserDashboardRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceProfileUserDashboardBean!br.com.opsocial.ejb.das.MaintenanceProfileUserDashboardRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("TrendingTopic")) {
			
			MaintenanceTrendingTopicRemote remote = null;
			
				try {
					remote = (MaintenanceTrendingTopicRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTrendingTopicBean!br.com.opsocial.ejb.das.MaintenanceTrendingTopicRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("MonitoringSearch")) {
			
			MaintenanceMonitoringSearchRemote remote = null;
			
				try {
					remote = (MaintenanceMonitoringSearchRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringSearchBean!br.com.opsocial.ejb.das.MaintenanceMonitoringSearchRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("InstagramDevice")) {
			
			MaintenanceInstagramDeviceRemote remote = null;
			
				try {
					remote = (MaintenanceInstagramDeviceRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceInstagramDeviceBean!br.com.opsocial.ejb.das.MaintenanceInstagramDeviceRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("Groups")) {
			
			MaintenanceGroupRemote remote = null;
			
				try {
					remote = (MaintenanceGroupRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceGroupBean!br.com.opsocial.ejb.das.MaintenanceGroupRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("GroupsProfiles")) {
			
			MaintenanceGroupsProfilesRemote remote = null;
			
				try {
					remote = (MaintenanceGroupsProfilesRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceGroupsProfilesBean!br.com.opsocial.ejb.das.MaintenanceGroupsProfilesRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("GroupsRssfeeds")) {
			
			MaintenanceGroupsRssfeedsRemote remote = null;
			
				try {
					remote = (MaintenanceGroupsRssfeedsRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceGroupsRssfeedsBean!br.com.opsocial.ejb.das.MaintenanceGroupsRssfeedsRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("RssFeed")) {
			
			MaintenanceRssFeedRemote remote = null;
			
				try {
					remote = (MaintenanceRssFeedRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceRssFeedBean!br.com.opsocial.ejb.das.MaintenanceRssFeedRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("LinkedinPost")) {
			
			MaintenanceLinkedinPostRemote remote = null;
			
				try {
					remote = (MaintenanceLinkedinPostRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceLinkedinPostBean!br.com.opsocial.ejb.das.MaintenanceLinkedinPostRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("Timezone")) {
			
			MaintenanceTimezoneRemote remote = null;
			
				try {
					remote = (MaintenanceTimezoneRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTimezoneBean!br.com.opsocial.ejb.das.MaintenanceTimezoneRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("OpLog")) {
			
			MaintenanceOpLogRemote remote = null;
			
				try {
					remote = (MaintenanceOpLogRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceOpLogBean!br.com.opsocial.ejb.das.MaintenanceOpLogRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("Tab")) {
			
			MaintenanceTabRemote remote = null;
			
				try {
					remote = (MaintenanceTabRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceTabBean!br.com.opsocial.ejb.das.MaintenanceTabRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("Stream")) {
			
			MaintenanceStreamRemote remote = null;
			
				try {
					remote = (MaintenanceStreamRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceStreamBean!br.com.opsocial.ejb.das.MaintenanceStreamRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("CardTransaction")) {
			
			MaintenanceCardTransactionRemote remote = null;
			
				try {
					remote = (MaintenanceCardTransactionRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceCardTransactionBean!br.com.opsocial.ejb.das.MaintenanceCardTransactionRemote");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			
			return remote;
		} else if(entity.equals("InternalAnalytic")) {
			
			MaintenanceInternalAnalyticRemote remote = null;
			
			try {
				remote = (MaintenanceInternalAnalyticRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceInternalAnalyticBean!br.com.opsocial.ejb.das.MaintenanceInternalAnalyticRemote");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		
			return remote;
		} else if(entity.equals("ProxyRegion")) {
			
			MaintenanceProxyRegionRemote remote = null;
			
			try {
				remote = (MaintenanceProxyRegionRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceProxyRegionBean!br.com.opsocial.ejb.das.MaintenanceProxyRegionRemote");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		
			return remote;
		} else if(entity.equals("InstagramProfileDevice")) {
			
			MaintenanceInstagramProfileDeviceRemote remote = null;
			
			try {
				remote = (MaintenanceInstagramProfileDeviceRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceInstagramProfileDeviceBean!br.com.opsocial.ejb.das.MaintenanceInstagramProfileDeviceRemote");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		
			return remote;
		}
		
		return null;
	}

	public static MaintenanceGenericRemote getRemoteGenericMaintenance(String entity) {
		 if(entity.equals("Generic")) {

			MaintenanceGenericRemote remote = null;
			
			try {
				remote = (MaintenanceGenericRemote) new InitialContext().lookup("global/OpSocialBack/MaintenanceGenericBean!br.com.opsocial.ejb.das.MaintenanceGenericRemote");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		
			return remote;
		 }
		 
		 return null;
	}
}