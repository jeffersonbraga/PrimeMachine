package br.com.opsocial.server.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.SyncFailedException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.persistence.OptimisticLockException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonObject;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.monitoring.FacePostMonitoringDTO;
import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.client.entity.monitoring.TwitterPostMonitoringDTO;
import br.com.opsocial.client.entity.socialnetworks.googleplus.EmbedDTO;
import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceBlogPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceExceptionRemote;
import br.com.opsocial.ejb.das.MaintenanceFacePostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceFacebookPostRemote;
import br.com.opsocial.ejb.das.MaintenanceGooglePlusPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramDeviceRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramProfileDeviceRemote;
import br.com.opsocial.ejb.das.MaintenanceInternalAnalyticRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringGroupReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringHashTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringInfluentialUserRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLinkRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLocationReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLogReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringPostLocationRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringQualificationReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringReportControlRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTermReportRemote;
import br.com.opsocial.ejb.das.MaintenanceNewsPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceNotificationRemote;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceProxyRegionRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostURLRemote;
import br.com.opsocial.ejb.das.MaintenanceYoutubePostMonitoringRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.InternalAnalytic;
import br.com.opsocial.ejb.entity.application.Notification;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.ProxyRegion;
import br.com.opsocial.ejb.entity.application.idclass.BlogPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.FacePostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.GooglePlusPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.InstagramPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.InstagramProfileDeviceId;
import br.com.opsocial.ejb.entity.application.idclass.MonitoringLocationId;
import br.com.opsocial.ejb.entity.application.idclass.MonitoringPostLocationId;
import br.com.opsocial.ejb.entity.application.idclass.NewsPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.ReclameAquiPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.TwitterPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.YoutubePostMonitoringId;
import br.com.opsocial.ejb.entity.facebook.FacePostMonitoring;
import br.com.opsocial.ejb.entity.google.GooglePlusPostMonitoring;
import br.com.opsocial.ejb.entity.google.ObjectAttachment;
import br.com.opsocial.ejb.entity.instagram.InstagramPostMonitoring;
import br.com.opsocial.ejb.entity.instagram.InstagramProfileDevice;
import br.com.opsocial.ejb.entity.monitoring.MonitoringGroupReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringHashTag;
import br.com.opsocial.ejb.entity.monitoring.MonitoringInfluentialUser;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLink;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLocationReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLogReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostLocation;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostTag;
import br.com.opsocial.ejb.entity.monitoring.MonitoringQualificationReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReportControl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTagReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTermReport;
import br.com.opsocial.ejb.entity.report.AgeGroup;
import br.com.opsocial.ejb.entity.socialnetworks.facebook.FacebookPicture;
import br.com.opsocial.ejb.entity.socialnetworks.twitter.Hashtag;
import br.com.opsocial.ejb.entity.socialnetworks.twitter.Url;
import br.com.opsocial.ejb.entity.socialnetworks.twitter.UserMention;
import br.com.opsocial.ejb.entity.twitter.TwitterPostMonitoring;
import br.com.opsocial.ejb.entity.twitter.TwitterPostURL;
import br.com.opsocial.ejb.entity.youtube.YoutubePostMonitoring;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;
import br.com.opsocial.server.utils.networksintegrations.TwitterIntegration;
import br.com.opsocial.server.utils.reports.ReportIcons;
import facebook4j.Facebook;
import facebook4j.PictureSize;
import facebook4j.auth.AccessToken;
import twitter4j.HashtagEntity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

public abstract class UtilFunctions {

	public final static String TWITTER_URL = "https://twitter.com/";
	public final static String FACEBOOK_URL = "https://www.facebook.com/";
	public final static String INSTAGRAM_URL = "https://instagram.com/";
	public final static String GOOGLE_PLUS_URL = "https://plus.google.com/";
	public final static String TWITTER_SEARCH_URL = "/search/?q=%23term&src=hash";
	
	public static final String PAG_SEGURO_TOKEN = "2837E2026BD9481B96D9EAC00003124E";
	
	public static final String HTTP_PROTOCOL = "http";
	public static final String HTTPS_PROTOCOL = "https";
	
	private final static String IMG_JPEG = "image/jpeg";
	private final static String IMG_PNG = "image/png";

	private static HashMap<String, Object> img_container = new HashMap<String, Object>();
	private static Document doc;
	private static List<Object> images;
	
	public static String getNetworkAvatar(Profile profile) throws TwitterException, facebook4j.FacebookException {

		String avatar = null;

		if(profile.getType().equals(Profile.TWITTER)) {

			TwitterIntegration twitterIntegration = new TwitterIntegration();
			Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());
			
			User user = null;
			
			try {
				user = twitter.showUser(twitter.getId());	
			} catch (Exception e) {
				return "";
			}

			return user.getProfileImageURLHttps();

		} else if(profile.getType().equals(Profile.FACEBOOK)) {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);
			Facebook facebook = new FacebookIntegration().getFacebook(accessToken);
			
			return facebook.getPictureURL(PictureSize.small).toString();
			
		} else if(profile.getType().equals(Profile.FACEBOOK_PAGE)) {
			
			AccessToken accessToken = new AccessToken(profile.getTokenSecret(), null);
			Facebook facebook = new FacebookIntegration().getFacebook(accessToken);
			
			return facebook.getPictureURL(PictureSize.small).toString(); 
		}

		return avatar;
	}

	public static String getFomattedStringWithWBR(String stringToFormat, Integer lengthToSpace) {

		if(stringToFormat != null) {
			StringBuffer stringFormatted = new StringBuffer(stringToFormat);

			// Formatting links.
			List<Integer> startIndexLinks = new ArrayList<Integer>();
			List<Integer> endIndexLinks = new ArrayList<Integer>();
			List<Integer> countOfWordsLinks = new ArrayList<Integer>();

			Pattern pattern = Pattern.compile("<a(.*?)</a>");
			Matcher matcherLink = pattern.matcher(stringFormatted.toString());

			Integer lengthOfWbrTag = 0;
			lengthToSpace = lengthToSpace + 10;
			while (matcherLink.find()) {

				Document doc = Jsoup.parseBodyFragment(matcherLink.group());
				Element element = doc.getElementsByTag("a").first();

				if(element != null) {
					StringBuffer elementText = new StringBuffer(element.text());
					StringBuffer elementString = new StringBuffer(matcherLink.group());
					Integer elementLengthAux;
					Integer realStringLength;
					Integer elementLength = 0;
					Integer j = 0;
					if(element.text().length() > lengthToSpace) {
						elementLengthAux = element.text().length();
						elementLength = element.text().length();
						realStringLength = element.text().length()+((element.text().length()/lengthToSpace)*10);
						for(int i = lengthToSpace; i <= realStringLength; i = i + lengthToSpace) {
							elementText.insert((elementLength-elementLengthAux)+10, "<wbr>");
							elementText.insert((elementLength-elementLengthAux)+lengthToSpace, "<wbr>");
							elementLengthAux = elementLengthAux - lengthToSpace;
							j++;
						}

						elementString.replace(elementString.length()-element.text().length()-4, elementString.length()-4, elementText.toString());

						stringFormatted.replace(matcherLink.start()+lengthOfWbrTag, matcherLink.end()+lengthOfWbrTag, elementString.toString());

					}
					startIndexLinks.add(matcherLink.start()+lengthOfWbrTag);
					lengthOfWbrTag = lengthOfWbrTag + (10*j);
					endIndexLinks.add(matcherLink.end()+lengthOfWbrTag);
					countOfWordsLinks.add(matcherLink.group().split(" ").length);
				}
			}

			// Formatting spans.
			List<Integer> startIndexSpan = new ArrayList<Integer>();
			List<Integer> endIndexSpan = new ArrayList<Integer>();
			List<Integer> countOfWordsSpan = new ArrayList<Integer>();

			Pattern patternSpan = Pattern.compile("<span(.*?)</span>");
			Matcher matcherSpan = patternSpan.matcher(stringFormatted.toString());

			lengthOfWbrTag = 0;
			while (matcherSpan.find()) {

				Document doc = Jsoup.parseBodyFragment(matcherSpan.group());
				Element element = doc.getElementsByTag("span").first();

				StringBuffer elementText = new StringBuffer(element.text());
				StringBuffer elementString = new StringBuffer(matcherSpan.group());
				Integer elementLengthAux;
				Integer realStringLength;
				Integer elementLength = 0;
				Integer j = 0;
				if(element.text().length() > lengthToSpace) {
					elementLengthAux = element.text().length();
					elementLength = element.text().length();
					realStringLength = element.text().length()+((element.text().length()/lengthToSpace)*10);
					for(int i = lengthToSpace; i <= realStringLength; i = i + lengthToSpace) {
						elementText.insert((elementLength-elementLengthAux)+10, "<wbr>");
						elementText.insert((elementLength-elementLengthAux)+lengthToSpace, "<wbr>");
						elementLengthAux = elementLengthAux - lengthToSpace;
						j++;
					}

					elementString.replace(elementString.length()-element.text().length()-7, elementString.length()-7, elementText.toString());

					stringFormatted.replace(matcherSpan.start()+lengthOfWbrTag, matcherSpan.end()+lengthOfWbrTag, elementString.toString());

				}
				startIndexSpan.add(matcherSpan.start()+lengthOfWbrTag);
				lengthOfWbrTag = lengthOfWbrTag + (10*j);
				endIndexSpan.add(matcherSpan.end()+lengthOfWbrTag);
				countOfWordsSpan.add(matcherSpan.group().split(" ").length);
			}
			
			String[] strings = stringFormatted.toString().split(" ");
			StringBuffer[] stringsBuffer = new StringBuffer[strings.length];
			for(int i = 0; i < strings.length; i++) {
				stringsBuffer[i] = new StringBuffer(strings[i]);
			}

			Integer lengthAux = 0;
			Integer lengthStringAux;
			Integer realStringLength;
			int cLinks = 0;
			int cSpan = 0;
			String strBeginAux = "";
			String strEndAux = "";
			lengthOfWbrTag = 0;
			Integer k = 0;
			for(int i = 0; i < stringsBuffer.length; i++) {
				
				if(cLinks < startIndexLinks.size()) {
					if(lengthAux < (startIndexLinks.get(cLinks)+lengthOfWbrTag) && 
							(startIndexLinks.get(cLinks)+lengthOfWbrTag) <= (lengthAux+stringsBuffer[i].length())) {
						strBeginAux = stringsBuffer[i].substring(0, ((startIndexLinks.get(cLinks)+lengthOfWbrTag) - lengthAux));
						lengthAux = lengthAux + strBeginAux.length();
						k = 0;
						if(strBeginAux.length() > lengthToSpace) {
							lengthStringAux = strBeginAux.length();
							realStringLength = strBeginAux.length()+((strBeginAux.length()/lengthToSpace)*10);
							for(int j = lengthToSpace; j <= realStringLength; j = j + lengthToSpace) {
								stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
								stringFormatted.insert((lengthAux-lengthStringAux)+lengthToSpace, "<wbr>");
								lengthStringAux = lengthStringAux - lengthToSpace;
								k++;
							}
						}
						lengthAux = lengthAux + 
								((endIndexLinks.get(cLinks) + lengthOfWbrTag) - lengthAux) + 1;
						lengthAux = lengthAux + (10*k);
						lengthOfWbrTag = lengthOfWbrTag + (10*k);
						i = i + countOfWordsLinks.get(cLinks) - 1;
						cLinks++;
					} else if(startIndexLinks.get(cLinks) + lengthOfWbrTag == lengthAux) {
						lengthAux = lengthAux + 
								((endIndexLinks.get(cLinks) + lengthOfWbrTag) - lengthAux) + 1;
						i = i + countOfWordsLinks.get(cLinks) - 1;
						cLinks++;
					} else if (cSpan < startIndexSpan.size()){
						if(startIndexSpan.get(cSpan) + lengthOfWbrTag == lengthAux) {
							lengthAux = lengthAux + 
									((endIndexSpan.get(cSpan) + lengthOfWbrTag) - lengthAux) + 1;
							i = i + countOfWordsSpan.get(cSpan) - 1;
							cSpan++;
						} else if (lengthAux < (startIndexSpan.get(cSpan)+lengthOfWbrTag) && 
								(startIndexSpan.get(cSpan)+lengthOfWbrTag) <= (lengthAux+stringsBuffer[i].length())) {
							strBeginAux = stringsBuffer[i].substring(0, ((startIndexSpan.get(cSpan)+lengthOfWbrTag) - lengthAux));
							lengthAux = lengthAux + strBeginAux.length();
							k = 0;
							if(strBeginAux.length() > lengthToSpace) {
								k = 0;
								lengthStringAux = strBeginAux.length();
								realStringLength = strBeginAux.length()+((strBeginAux.length()/lengthToSpace)*10);
								for(int j = lengthToSpace; j <= realStringLength; j = j + lengthToSpace) {
									stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
									stringFormatted.insert((lengthAux-lengthStringAux)+lengthToSpace, "<wbr>");
									lengthStringAux = lengthStringAux - lengthToSpace;
									k++;
								}
							}
							lengthAux = lengthAux + 
									((endIndexSpan.get(cSpan) + lengthOfWbrTag) - lengthAux) + 1;
							lengthAux = lengthAux + (10*k);
							lengthOfWbrTag = lengthOfWbrTag + (10*k);
							i = i + countOfWordsSpan.get(cSpan) - 1;
							cSpan++;
						} else {
							lengthAux = lengthAux + stringsBuffer[i].length() + 1;
							if(stringsBuffer[i].length() > lengthToSpace) {
								k = 0;
								lengthStringAux = stringsBuffer[i].length();
								realStringLength = stringsBuffer[i].length()+((stringsBuffer[i].length()/lengthToSpace)*10);
								for(int j = lengthToSpace; j <= realStringLength; j = j + lengthToSpace) {
									stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
									stringFormatted.insert((lengthAux-lengthStringAux)+lengthToSpace, "<wbr>");
									lengthStringAux = lengthStringAux - lengthToSpace;
									k++;
								}
								lengthAux = lengthAux + (10*k);
								lengthOfWbrTag = lengthOfWbrTag + (10*k);
							}
						}
					} else {
						lengthAux = lengthAux + stringsBuffer[i].length() + 1;
						if(stringsBuffer[i].length() > lengthToSpace) {
							k = 0;
							lengthStringAux = stringsBuffer[i].length();
							realStringLength = stringsBuffer[i].length()+((stringsBuffer[i].length()/lengthToSpace)*10);
							for(int j = lengthToSpace; j <= realStringLength; j = j + lengthToSpace) {
								stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
								stringFormatted.insert((lengthAux-lengthStringAux)+lengthToSpace, "<wbr>");
								lengthStringAux = lengthStringAux - lengthToSpace;
								k++;
							}
							lengthAux = lengthAux + (10*k);
							lengthOfWbrTag = lengthOfWbrTag + (10*k);
						}
					}
				} else if (cSpan < startIndexSpan.size()) {
					if(startIndexSpan.get(cSpan) + lengthOfWbrTag == lengthAux) {
						lengthAux = lengthAux + 
								((endIndexSpan.get(cSpan) + lengthOfWbrTag) - lengthAux) + 1;
						i = i + countOfWordsSpan.get(cSpan) - 1;
						cSpan++;
					} else if (lengthAux < (startIndexSpan.get(cSpan)+lengthOfWbrTag) && 
							(startIndexSpan.get(cSpan)+lengthOfWbrTag) <= (lengthAux+stringsBuffer[i].length())) {
						strBeginAux = stringsBuffer[i].substring(0, ((startIndexSpan.get(cSpan)+lengthOfWbrTag) - lengthAux));
						lengthAux = lengthAux + strBeginAux.length();
						k = 0;
						if(strBeginAux.length() > lengthToSpace) {
							lengthStringAux = strBeginAux.length();
							realStringLength = strBeginAux.length()+((strBeginAux.length()/lengthToSpace)*10);
							for(int j = lengthToSpace; j <= realStringLength; j = j + lengthToSpace) {
								stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
								stringFormatted.insert((lengthAux-lengthStringAux)+lengthToSpace, "<wbr>");
								lengthStringAux = lengthStringAux - lengthToSpace;
								k++;
							}
						}
						lengthAux = lengthAux + 
								((endIndexSpan.get(cSpan) + lengthOfWbrTag) - lengthAux) + 1;
						lengthAux = lengthAux + (10*k);
						lengthOfWbrTag = lengthOfWbrTag + (10*k);
						i = i + countOfWordsSpan.get(cSpan) - 1;
						cSpan++;
					}
					else if (cLinks < startIndexLinks.size()){
						if(lengthAux < (startIndexLinks.get(cLinks)+lengthOfWbrTag) && 
								(startIndexLinks.get(cLinks)+lengthOfWbrTag) <= (lengthAux+stringsBuffer[i].length())) {
							strBeginAux = stringsBuffer[i].substring(0, ((startIndexLinks.get(cLinks)+lengthOfWbrTag) - lengthAux));
							lengthAux = lengthAux + strBeginAux.length();
							k = 0;
							if(strBeginAux.length() > lengthToSpace) {
								lengthStringAux = strBeginAux.length();
								realStringLength = strBeginAux.length()+((strBeginAux.length()/lengthToSpace)*10);
								for(int j = lengthToSpace; j <= realStringLength; j = j + lengthToSpace) {
									stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
									stringFormatted.insert((lengthAux-lengthStringAux)+lengthToSpace, "<wbr>");
									lengthStringAux = lengthStringAux - lengthToSpace;
									k++;
								}
							}
							lengthAux = lengthAux + 
									((endIndexLinks.get(cLinks) + lengthOfWbrTag) - lengthAux) + 1;
							lengthAux = lengthAux + (10*k);
							lengthOfWbrTag = lengthOfWbrTag + (10*k);
							i = i + countOfWordsLinks.get(cLinks) - 1;
							cLinks++;
						} else if(startIndexLinks.get(cLinks) + lengthOfWbrTag == lengthAux) {
							lengthAux = lengthAux + 
									((endIndexLinks.get(cLinks) + lengthOfWbrTag) - lengthAux) + 1;
							i = i + countOfWordsLinks.get(cLinks) - 1;
							cLinks++;
						} else {
							lengthAux = lengthAux + stringsBuffer[i].length() + 1;
							if(stringsBuffer[i].length() > lengthToSpace) {
								k = 0;
								lengthStringAux = stringsBuffer[i].length();
								realStringLength = stringsBuffer[i].length()+((stringsBuffer[i].length()/lengthToSpace)*10);
								for(int j = lengthToSpace; j <= realStringLength; j = j + lengthToSpace) {
									stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
									stringFormatted.insert((lengthAux-lengthStringAux)+lengthToSpace, "<wbr>");
									lengthStringAux = lengthStringAux - lengthToSpace;
									k++;
								}
								lengthAux = lengthAux + (10*k);
								lengthOfWbrTag = lengthOfWbrTag + (10*k);
							}
						}
					} else {
						lengthAux = lengthAux + stringsBuffer[i].length() + 1;
						if(stringsBuffer[i].length() > lengthToSpace) {
							k = 0;
							lengthStringAux = stringsBuffer[i].length();
							realStringLength = stringsBuffer[i].length()+((stringsBuffer[i].length()/lengthToSpace)*10);
							for(int j = lengthToSpace; j <= realStringLength; j = j + lengthToSpace) {
								stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
								stringFormatted.insert((lengthAux-lengthStringAux)+lengthToSpace, "<wbr>");
								lengthStringAux = lengthStringAux - lengthToSpace;
								k++;
							}
							lengthAux = lengthAux + (10*k);
							lengthOfWbrTag = lengthOfWbrTag + (10*k);
						}
					}
				} else {
					lengthAux = lengthAux + stringsBuffer[i].length() + 1;
					if(stringsBuffer[i].length() > lengthToSpace) {
						k = 0;
						lengthStringAux = stringsBuffer[i].length();
						realStringLength = stringsBuffer[i].length()+((stringsBuffer[i].length()/lengthToSpace)*10);
						for(int j = lengthToSpace; j <= realStringLength; j = j + lengthToSpace) {
							stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
							stringFormatted.insert((lengthAux-lengthStringAux)+lengthToSpace, "<wbr>");
							lengthStringAux = lengthStringAux - lengthToSpace;
							k++;
						}
						lengthAux = lengthAux + (10*k);
						lengthOfWbrTag = lengthOfWbrTag + (10*k);
					}
				}
			}
			
			return stringFormatted.toString();
		} else {
			return "";
		}
	}

	public static String markTermInStatusPost(String postContent, String term) {

		try {
			term = term.replace("\"", "");

			if(postContent != null) {
				StringBuffer stringFormatted = new StringBuffer(postContent);
				char[] contentArray = postContent.toUpperCase().toCharArray();
				char[] termArray = term.toUpperCase().toCharArray();
				boolean findTermInContent = true;

				String spanStyleBegin = "<span style=\"background-color: #c8f0ff;\">";
				String spanStyleEnd = "</span>";
				Integer beginTerm = 0;
				Integer endTerm = 0;
				Integer spansLength = 0;
				Integer iterateAux = 0;
				for(int i = 0; i < contentArray.length; i++) {
					if(contentArray[i] == termArray[0]) {
						iterateAux = i;
						beginTerm = iterateAux + spansLength;
						iterateAux++;
						if((contentArray.length - iterateAux) >= term.length()) {
							for(int j = 1; j < term.length(); j++) {
								if(contentArray[iterateAux] == termArray[j]) {
									iterateAux++;
								} else {
									findTermInContent = false;
									break;
								}
							}
							if(findTermInContent) {
								if(i > 0) {
									if(contentArray[i-1] == ' ') {
										i = iterateAux;
										endTerm = iterateAux + spansLength;

										stringFormatted.insert(beginTerm, spanStyleBegin);
										stringFormatted.insert(endTerm + spanStyleBegin.length(), spanStyleEnd);
										spansLength = spansLength + (spanStyleBegin.length() + spanStyleEnd.length());
									} else {
										i = iterateAux;
									}
								} else {
									i = iterateAux;
									endTerm = iterateAux + spansLength;

									stringFormatted.insert(beginTerm, spanStyleBegin);
									stringFormatted.insert(endTerm + spanStyleBegin.length(), spanStyleEnd);
									spansLength = spansLength + (spanStyleBegin.length() + spanStyleEnd.length());
								}
							}
						} else {
							findTermInContent = true;
							break;
						}
					} else {
						findTermInContent = true;
					}
				}

				return stringFormatted.toString();
			} else {
				return "";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return postContent;
		}
	}

	public static String putLinksInTwitterStatus(String postContent, URLEntity[] urlEntities) {

		String link = "";
		for(URLEntity urlEntity : urlEntities) {
			link = "<a href='"+urlEntity.getURL().toString()+"' target=\"_blank\">"+urlEntity.getDisplayURL()+"</a>";
			postContent = postContent.replaceAll(Pattern.quote(urlEntity.getURL().toString()), link);
		}

		return postContent;
	}
	
	public static String putLinksInTwitterStatus(String postContent, List<Url> urlEntities) {

		String link = "";
		for(Url urlEntity : urlEntities) {
			link = "<a href='"+urlEntity.getUrl()+"' target=\"_blank\">"+urlEntity.getDisplayUrl()+"</a>";
			postContent = postContent.replaceAll(Pattern.quote(urlEntity.getUrl().toString()), link);
		}

		return postContent;
	}	
	
	public static String putLinksInUserMentions(String postContent, UserMentionEntity[] userMentions) {
		
		String link = "";
		for(UserMentionEntity userMention: userMentions) {
			link = "<a href=\""+TWITTER_URL+userMention.getScreenName()+"\" target=\"_blank\">@"+userMention.getScreenName().toLowerCase()+"</a>";
			postContent = postContent.replaceAll("@(?i)"+Pattern.quote(userMention.getScreenName()), link);
		}
		
		return postContent;
	}
	
	public static String putLinksInUserMentions(String postContent, List<UserMention> userMentions) {
		
		String link = "";
		for(UserMention userMention: userMentions) {
			link = "<a href=\""+TWITTER_URL+userMention.getScreenName()+"\" target=\"_blank\">@"+userMention.getScreenName().toLowerCase()+"</a>";
			postContent = postContent.replaceAll("@(?i)"+Pattern.quote(userMention.getScreenName()), link);
		}
		
		return postContent;
	}
	
	public static String putLinksInHashTags(String postContent, HashtagEntity[] hashTags) {
		
		StringBuilder postContentBuilder = new StringBuilder(postContent);
		String link = "";
		int lengthPostContent = 0;
		for(HashtagEntity hashTag : hashTags) {
			link = "<a href=\""+TWITTER_URL+TWITTER_SEARCH_URL.replace("term", hashTag.getText())+"\" target=\"_blank\">#"+hashTag.getText()+"</a>";
			postContentBuilder.replace(hashTag.getStart()+lengthPostContent, hashTag.getEnd()+lengthPostContent, link);
			lengthPostContent = lengthPostContent + (link.length() - hashTag.getText().length() - 1);
		}
		
		return postContentBuilder.toString();
	}
	
	public static String putLinksInHashTags(String postContent, List<Hashtag> hashTags) {
		
		StringBuilder postContentBuilder = new StringBuilder(postContent);
		String link = "";
		int lengthPostContent = 0;
		for(Hashtag hashTag : hashTags) {
			link = "<a href=\""+TWITTER_URL+TWITTER_SEARCH_URL.replace("term", hashTag.getText())+"\" target=\"_blank\">#"+hashTag.getText()+"</a>";
			postContentBuilder.replace(hashTag.getIndices().get(0)+lengthPostContent, hashTag.getIndices().get(1)+lengthPostContent, link);
			lengthPostContent = lengthPostContent + (link.length() - hashTag.getText().length() - 1);
		}
		
		return postContentBuilder.toString();
	}
	
	public static String putLinksInFacebookPostMentions(String postContent) {
		
		if(postContent != null) {
			Pattern pattern = Pattern.compile("@\\[(.*?)\\]");
			Matcher matcherLink = pattern.matcher(postContent);

			String link = "";
			while (matcherLink.find()) {
				String str = matcherLink.group();
				link = "<a href=\""+FACEBOOK_URL+str.substring(2, str.indexOf(":"))
								+"\" target=\"_blank\">"+str.substring(str.lastIndexOf(":")+1, str.length()-1)+"</a>";
				postContent = postContent.replace(str, link);
			}
		}
		
		return postContent;
	}

	public static String putLinksInFacebookPost(String postContent) {

		if(postContent != null) {
			String regex = "\\(?\\b(http://|https://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(postContent);
			String link = "";
			while(m.find()) {
				String urlStr = m.group();
				if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
					urlStr = urlStr.substring(1, urlStr.length() - 1);
				} else if(urlStr.startsWith("www")) {
					urlStr = "http://" + urlStr;
				}
				link = "<a href='"+urlStr+"' target=\"_blank\">"+urlStr+"</a>";
				postContent = postContent.replace(urlStr, link);
			}
		}

		return postContent;
	}
	
	public static String applyFormattingInSetPosts(String post) {
		return getFomattedStringWithWBR(post, 20);
	}
	
	public static GenericImageAdapt getImageSize(String pictureUrl) throws IOException {
		
		GenericImageAdapt image = new GenericImageAdapt();
		
		try {
			
			BufferedImage bufferedImage = ImageIO.read(new URL(pictureUrl));
			if (bufferedImage != null) {
				image.setHeight(bufferedImage.getHeight());
				image.setWidth(bufferedImage.getWidth());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new IOException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	public static FacebookPicture adaptFacebookPicture(String pictureUrl, int maxWidth) {
		
		int newWidth = 0;
		int newHeight = 0;
		float percPicture = 0;
		
		try {
			BufferedImage bufferedImage = ImageIO.read(new URL(pictureUrl));
			
			if(bufferedImage.getWidth() > maxWidth) {
				percPicture = (float)new BigDecimal(((float)maxWidth / bufferedImage.getWidth()) * 100).setScale(0, BigDecimal.ROUND_DOWN).intValue() / 100;
				
				newWidth = new BigDecimal(bufferedImage.getWidth() * percPicture).setScale(0, BigDecimal.ROUND_DOWN).intValue();
				newHeight = new BigDecimal(bufferedImage.getHeight() * percPicture).setScale(0, BigDecimal.ROUND_DOWN).intValue();
			} else {
				newWidth = bufferedImage.getWidth();
				newHeight = bufferedImage.getHeight();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FacebookPicture facePicture = new FacebookPicture();
		facePicture.setSource(pictureUrl);
		facePicture.setHeight(newHeight);
		facePicture.setWidth(newWidth);
		
		return facePicture;
	}
	
	public static String createFilteredPicture(String filteredPictureBase64) {
		
		String img = filteredPictureBase64.substring(filteredPictureBase64.indexOf("base64,") + 7);
		String tempPath = OpSocialBackApplication.UPLOAD_DIR_IMAGES_POSTS;
		
		String pictureFiltName = org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(12);

		try {
			
			byte[] array = Base64.decodeBase64(img.getBytes());
			File file = new File(tempPath, pictureFiltName + ".jpg");
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(array);
			FileDescriptor fd = fos.getFD();
			fos.flush();
			fd.sync();
			fos.close();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SyncFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "" + pictureFiltName + ".jpg";
	}
	
	public static String convertToEmbedURL(String urlToConvert) {
		
		String urlConverted = "";

		try {
			URL convertURL = new URL(urlToConvert);

			if(convertURL.getHost().equals(EmbedDTO.YOUTUBE_HOST)) {
				urlConverted = urlToConvert.replace(convertURL.getFile(), "/embed/"+convertURL.getPath().split("/")[2]+"?wmode=transparent");
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return urlConverted;
	}
	
	public static Long getDateForFacebookReport(Calendar date) {
		
		date.setTimeZone(TimeZone.getTimeZone("America/Phoenix"));
		date.set(Calendar.AM_PM, Calendar.AM);
		date.set(Calendar.HOUR, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);

		return date.getTimeInMillis() / 1000L;
	}
	
	public static ZonedDateTime getDateForInstagramReport(LocalDateTime localDateTimeReport) {
		
		LocalDateTime localDateTimeNow = LocalDateTime.of(localDateTimeReport.getYear(), localDateTimeReport.getMonth(), 
				localDateTimeReport.getDayOfMonth(), 0, 0, 0);
		
		ZoneId zonePST = ZoneId.of("America/Los_Angeles");
		ZonedDateTime zoneDateTimePST = ZonedDateTime.of(localDateTimeNow, zonePST);
		
		return zoneDateTimePST;
	}
	
	public static Calendar getCalendarDateForFacebookReport(Calendar date) {
		
		date.setTimeZone(TimeZone.getTimeZone("America/Phoenix"));
		date.set(Calendar.AM_PM, Calendar.AM);
		date.set(Calendar.HOUR, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		
		return date;
	}
	
	public static Long getDateForTwitterReport(Calendar date) {
		
		date.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		date.set(Calendar.AM_PM, Calendar.AM);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		
		return date.getTimeInMillis() / 1000L;
	}
	
	public static Long getMidnightDateNoDaylightTime(Date date) {
		
		Calendar calendarDate = new GregorianCalendar();
		calendarDate.setTime(date);
		calendarDate.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		calendarDate.set(Calendar.AM_PM, Calendar.AM);
		calendarDate.set(Calendar.HOUR_OF_DAY, 0);
		calendarDate.set(Calendar.MINUTE, 0);
		calendarDate.set(Calendar.SECOND, 0);
		
		if(TimeZone.getTimeZone("America/Sao_Paulo").inDaylightTime(date)) {
			calendarDate.add(Calendar.HOUR_OF_DAY, 1);
		}
		
		return calendarDate.getTimeInMillis() / 1000L;
	}

	public static String getResourcePath(String resource) {
		return OpSocialBackApplication.opSocialContext.getRealPath(resource);
	}

	public static List<String> getHashTags(String text) {

		List<String> hashTags = new ArrayList<String>();

		try {

			Pattern hashTagPattern = Pattern.compile("(#[\\p{L}0-9-_]+)");
			Matcher hashTagMatcher = null;

			if (text != null) {
				hashTagMatcher = hashTagPattern.matcher(text);
			}

			if (hashTagMatcher != null) {
				while (hashTagMatcher.find()) {
					hashTags.add(hashTagMatcher.group(0).replaceFirst("#", ""));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return hashTags;
	}
	
	public static List<URL> getHiperLinks(String text) {
		
		List<URL> urls = new ArrayList<URL>();
		
        String [] words = text.split("\\s+");

        for (String word : words) {
        	
        	try {
        		
				URL url = new URL(word);
				
				if(url.getProtocol().equals(HTTP_PROTOCOL) || url.getProtocol().equals(HTTPS_PROTOCOL)) {
					urls.add(url);
				}
				
			} catch (MalformedURLException e) {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
		}
        
        return urls;
	}
	
	public static Long getRetrievedDatePost(MonitoringPostTag monitoringPostTag) {
		
		Long retrievedDate = 0L;
		
		if(monitoringPostTag.getNetwork().equals(Profile.FACEBOOK)) {
			
			MaintenanceFacePostMonitoringRemote facePostMonitoringRemote = (MaintenanceFacePostMonitoringRemote) RecoverMaintenance.recoverMaintenance("FacePostMonitoring");
			
			FacePostMonitoringId facePostMonitoringId = new FacePostMonitoringId();
			facePostMonitoringId.setFacebookPost(monitoringPostTag.getPostId());
			facePostMonitoringId.setMonitoring(monitoringPostTag.getIdMonitoring());
			facePostMonitoringId.setTerm(monitoringPostTag.getTerm());
			
			retrievedDate = facePostMonitoringRemote.getById(facePostMonitoringId).getRetrievedDate();
			
		} else if(monitoringPostTag.getNetwork().equals(Profile.TWITTER)) {
			
			MaintenanceTwitterPostMonitoringRemote twitterPostMonitoringRemote = (MaintenanceTwitterPostMonitoringRemote)
					RecoverMaintenance.recoverMaintenance("TwitterPostMonitoring");
			
			TwitterPostMonitoringId twitterPostMonitoringId = new TwitterPostMonitoringId();
			twitterPostMonitoringId.setMonitoring(monitoringPostTag.getIdMonitoring());
			twitterPostMonitoringId.setTwitterPost(Long.valueOf(monitoringPostTag.getPostId()));
			twitterPostMonitoringId.setTerm(monitoringPostTag.getTerm());
			
			retrievedDate = twitterPostMonitoringRemote.getById(twitterPostMonitoringId).getRetrievedDate();
			
		} else if(monitoringPostTag.getNetwork().equals(Profile.GOOGLE)) {
			
			MaintenanceGooglePlusPostMonitoringRemote googlePlusPostMonitoringRemote = (MaintenanceGooglePlusPostMonitoringRemote)
					RecoverMaintenance.recoverMaintenance("GooglePlusPostMonitoring");
			
			GooglePlusPostMonitoringId googlePlusPostMonitoringId = new GooglePlusPostMonitoringId();
			googlePlusPostMonitoringId.setMonitoring(monitoringPostTag.getIdMonitoring());
			googlePlusPostMonitoringId.setGooglePlusPost(monitoringPostTag.getPostId());
			googlePlusPostMonitoringId.setTerm(monitoringPostTag.getTerm());
			
			retrievedDate = googlePlusPostMonitoringRemote.getById(googlePlusPostMonitoringId).getRetrievedDate();
		
		} else if(monitoringPostTag.getNetwork().equals(Profile.YOUTUBE)) {
			
			MaintenanceYoutubePostMonitoringRemote youtubePostMonitoringRemote = (MaintenanceYoutubePostMonitoringRemote)
					RecoverMaintenance.recoverMaintenance("YoutubePostMonitoring");
			
			YoutubePostMonitoringId youtubePostMonitoringId = new YoutubePostMonitoringId();
			youtubePostMonitoringId.setMonitoring(monitoringPostTag.getIdMonitoring());
			youtubePostMonitoringId.setYoutubePost(monitoringPostTag.getPostId());
			youtubePostMonitoringId.setTerm(monitoringPostTag.getTerm());
			
			retrievedDate = youtubePostMonitoringRemote.getById(youtubePostMonitoringId).getRetrievedDate();
			
		} else if(monitoringPostTag.getNetwork().equals(Profile.INSTAGRAM)) {
			
			// TODO: Try catch implementado para tentar entender o porque de uma exceção java.lang.NullPointerException
			// estar sendo disparada na linha 912.
			try {
				
				MaintenanceInstagramPostMonitoringRemote instagramPostMonitoringRemote = (MaintenanceInstagramPostMonitoringRemote)
						RecoverMaintenance.recoverMaintenance("InstagramPostMonitoring");
				
				InstagramPostMonitoringId instagramPostMonitoringId = new InstagramPostMonitoringId();
				instagramPostMonitoringId.setMonitoring(monitoringPostTag.getIdMonitoring());
				instagramPostMonitoringId.setInstagramPost(Long.valueOf(monitoringPostTag.getPostId()));
				instagramPostMonitoringId.setTerm(monitoringPostTag.getTerm());
				
				retrievedDate = instagramPostMonitoringRemote.getById(instagramPostMonitoringId).getRetrievedDate();
				
			} catch (Exception e) {
				
				StringWriter printStackTrace = new StringWriter();
				
				e.printStackTrace(new PrintWriter(printStackTrace));
				//----- System.out.println("Monitoring..: " + monitoringPostTag.getIdMonitoring());
				//----- System.out.println("Post ID..: " + monitoringPostTag.getPostId());
				//----- System.out.println("Post Term..: " + monitoringPostTag.getTerm());
				
				MaintenanceExceptionRemote exceptionRemote = (MaintenanceExceptionRemote)
						RecoverMaintenance.recoverMaintenance("Exception");
				
				br.com.opsocial.ejb.entity.application.Exception exception = new br.com.opsocial.ejb.entity.application.Exception();
				exception.setObjectId(monitoringPostTag.getPostId());
				exception.setDate(new Date());
				exception.setException(printStackTrace.toString());
				exception.setCode(br.com.opsocial.ejb.entity.application.Exception.INSTA_TAG_MONITORING_POSTS_ERROR);
				exception.setMessage("Monitoring..: " + monitoringPostTag.getIdMonitoring() +
						"Post ID..: " + monitoringPostTag.getPostId() + 
						"Post Term..: " + monitoringPostTag.getTerm());
				
				try {
					
					exceptionRemote.save(exception);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		} else if(monitoringPostTag.getNetwork().equals(Profile.RECLAMEAQUI)) {
			
			MaintenanceReclameAquiPostMonitoringRemote reclameAquiPostMonitoringRemote = (MaintenanceReclameAquiPostMonitoringRemote)
					RecoverMaintenance.recoverMaintenance("ReclameAquiPostMonitoring");
			
			ReclameAquiPostMonitoringId reclameAquiPostMonitoringId = new ReclameAquiPostMonitoringId();
			reclameAquiPostMonitoringId.setMonitoring(monitoringPostTag.getIdMonitoring());
			reclameAquiPostMonitoringId.setReclameAquiPost(monitoringPostTag.getPostId());
			reclameAquiPostMonitoringId.setTerm(monitoringPostTag.getTerm());
			
			retrievedDate = reclameAquiPostMonitoringRemote.getById(reclameAquiPostMonitoringId).getRetrievedDate();
			
		} else if(monitoringPostTag.getNetwork().equals(Profile.NEWS)) {
			
			MaintenanceNewsPostMonitoringRemote newsPostMonitoringRemote = (MaintenanceNewsPostMonitoringRemote)
					RecoverMaintenance.recoverMaintenance("NewsPostMonitoring");
			
			NewsPostMonitoringId newsPostMonitoringId = new NewsPostMonitoringId();
			newsPostMonitoringId.setMonitoring(monitoringPostTag.getIdMonitoring());
			newsPostMonitoringId.setNewsPost(Long.valueOf(monitoringPostTag.getPostId()));
			newsPostMonitoringId.setTerm(monitoringPostTag.getTerm());
			
			retrievedDate = newsPostMonitoringRemote.getById(newsPostMonitoringId).getRetrievedDate();
			
		} else if(monitoringPostTag.getNetwork().equals(Profile.BLOGS)) {
			
			MaintenanceBlogPostMonitoringRemote blogPostMonitoringRemote = (MaintenanceBlogPostMonitoringRemote)
					RecoverMaintenance.recoverMaintenance("BlogPostMonitoring");
			
			BlogPostMonitoringId blogPostMonitoringId = new BlogPostMonitoringId();
			blogPostMonitoringId.setMonitoring(monitoringPostTag.getIdMonitoring());
			blogPostMonitoringId.setBlogPost(Long.valueOf(monitoringPostTag.getPostId()));
			blogPostMonitoringId.setTerm(monitoringPostTag.getTerm());
			
			retrievedDate = blogPostMonitoringRemote.getById(blogPostMonitoringId).getRetrievedDate();
		}
		
		return retrievedDate;
	}

	public static String getIdUserFromPostMonitoring(Object postMonitoringDTO) {

		String idUser = "";

		if(postMonitoringDTO instanceof FacePostMonitoring) {
			idUser = ((FacePostMonitoring) postMonitoringDTO).getFacebookPost().getProfileId();
		} else if(postMonitoringDTO instanceof TwitterPostMonitoring) {
			idUser = ((TwitterPostMonitoring) postMonitoringDTO).getTwitterPost().getUserId().toString();
		} else if(postMonitoringDTO instanceof GooglePlusPostMonitoring) {
			idUser = ((GooglePlusPostMonitoring) postMonitoringDTO).getGooglePlusPost().getActorId();
		} else if(postMonitoringDTO instanceof YoutubePostMonitoring) {
			idUser = ((YoutubePostMonitoring) postMonitoringDTO).getYoutubePost().getChannelId();
		} else if(postMonitoringDTO instanceof InstagramPostMonitoring) {
			idUser = ((InstagramPostMonitoring) postMonitoringDTO).getInstagramPost().getIdInstagramUser().toString();
		}

		return idUser;
	}

	public static List<String> getHashTags(Object postMonitoringDTO) {

		List<String> hashTags = new ArrayList<String>();

		if(postMonitoringDTO instanceof FacePostMonitoring) {

			FacePostMonitoring facePostMonitoring = (FacePostMonitoring) postMonitoringDTO;

			if(facePostMonitoring.getFacebookPost().getMessage() != null) {
				hashTags.addAll(UtilFunctions.getHashTags(facePostMonitoring.getFacebookPost().getMessage()));
			} 
			if(facePostMonitoring.getFacebookPost().getCaption() != null) {
				hashTags.addAll(UtilFunctions.getHashTags(facePostMonitoring.getFacebookPost().getCaption()));
			} 
			if(facePostMonitoring.getFacebookPost().getDescription() != null) {
				hashTags.addAll(UtilFunctions.getHashTags(facePostMonitoring.getFacebookPost().getDescription()));
			}

		} else if(postMonitoringDTO instanceof TwitterPostMonitoring) {

			TwitterPostMonitoring twitterPostMonitoring = (TwitterPostMonitoring) postMonitoringDTO;

			if(twitterPostMonitoring.getTwitterPost().getText() != null) {
				hashTags.addAll(UtilFunctions.getHashTags(twitterPostMonitoring.getTwitterPost().getText()));
			}

		} else if(postMonitoringDTO instanceof GooglePlusPostMonitoring) {

			GooglePlusPostMonitoring googlePlusPostMonitoring = (GooglePlusPostMonitoring) postMonitoringDTO;

			if(googlePlusPostMonitoring.getGooglePlusPost().getObjectContent() != null) {
				hashTags.addAll(UtilFunctions.getHashTags(
					Jsoup.parse(googlePlusPostMonitoring.getGooglePlusPost().getObjectContent()).text()));
			} 
			if(googlePlusPostMonitoring.getGooglePlusPost().getObjectsAttachment() != null) {
				for(ObjectAttachment attachment : googlePlusPostMonitoring.getGooglePlusPost().getObjectsAttachment()) {
					if(attachment.getContent() != null) {
						hashTags.addAll(UtilFunctions.getHashTags(Jsoup.parse(attachment.getContent()).text()));
					}
				}
			}

		} else if(postMonitoringDTO instanceof YoutubePostMonitoring) {

			YoutubePostMonitoring youtubePostMonitoring = (YoutubePostMonitoring) postMonitoringDTO;

			if(youtubePostMonitoring.getYoutubePost().getDescription() != null) {
				hashTags.addAll(UtilFunctions.getHashTags(youtubePostMonitoring.getYoutubePost().getDescription()));
			} 
			if(youtubePostMonitoring.getYoutubePost().getTitle() != null) {
				hashTags.addAll(UtilFunctions.getHashTags(youtubePostMonitoring.getYoutubePost().getTitle()));
			}

		} else if(postMonitoringDTO instanceof InstagramPostMonitoring) {

			InstagramPostMonitoring instagramPostMonitoring = (InstagramPostMonitoring) postMonitoringDTO;

			if(instagramPostMonitoring.getInstagramPost().getPictureCaption() != null) {
				hashTags.addAll(UtilFunctions.getHashTags(instagramPostMonitoring.getInstagramPost().getPictureCaption()));
			}

		}
		
		return hashTags;
	}
	
	public static List<URL> getHiperLinks(Object postMonitoringDTO) {
		
		List<URL> links = new ArrayList<URL>();
		
		if(postMonitoringDTO instanceof FacePostMonitoring) {
			
			FacePostMonitoring facePostMonitoring = (FacePostMonitoring) postMonitoringDTO;
			
			MaintenanceFacebookPostRemote facebookPostRemote = (MaintenanceFacebookPostRemote)
					RecoverMaintenance.recoverMaintenance("FacebookPost");
			
			br.com.opsocial.ejb.entity.facebook.FacebookPost facebookPost = facebookPostRemote.getById(facePostMonitoring.getFacebookPost());
			
			if(facebookPost.getMessage() != null) {
				links.addAll(UtilFunctions.getHiperLinks(facebookPost.getMessage()));
			} 
			if(facebookPost.getCaption() != null) {
				links.addAll(UtilFunctions.getHiperLinks(facebookPost.getCaption()));
			} 
			if(facebookPost.getDescription() != null) {
				links.addAll(UtilFunctions.getHiperLinks(facebookPost.getDescription()));
			}
			
		} else if(postMonitoringDTO instanceof TwitterPostMonitoring) {

			TwitterPostMonitoring twitterPostMonitoring = (TwitterPostMonitoring) postMonitoringDTO;
			
			if(twitterPostMonitoring.getTwitterPost().getText() != null) {
				
				MaintenanceTwitterPostURLRemote twitterPostURLRemote = (MaintenanceTwitterPostURLRemote)
						RecoverMaintenance.recoverMaintenance("TwitterPostURL");
				
				List<TwitterPostURL> twitterPostURLs = twitterPostURLRemote.listByStatusId(twitterPostMonitoring.getTwitterPost().getStatusId());
				
				for (TwitterPostURL twitterPostURL : twitterPostURLs) {
					
					String url;
					
					if(twitterPostURL.getExpandedURL() != null) {
						url = twitterPostURL.getExpandedURL();
					} else {
						url = twitterPostURL.getURL();
					}
					
					try {
						
						links.add(new URL(url));
						
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
			
		} else if(postMonitoringDTO instanceof GooglePlusPostMonitoring) {
			
			GooglePlusPostMonitoring googlePlusPostMonitoring = (GooglePlusPostMonitoring) postMonitoringDTO;
			
			if(googlePlusPostMonitoring.getGooglePlusPost().getObjectContent() != null) {
				links.addAll(UtilFunctions.getHiperLinks(
						Jsoup.parse(googlePlusPostMonitoring.getGooglePlusPost().getObjectContent()).text()));
			} 
			
			if(googlePlusPostMonitoring.getGooglePlusPost().getObjectsAttachment() != null) {
				for(ObjectAttachment attachment : googlePlusPostMonitoring.getGooglePlusPost().getObjectsAttachment()) {
					if(attachment.getContent() != null) {
						links.addAll(UtilFunctions.getHiperLinks(Jsoup.parse(attachment.getContent()).text()));
					}
				}
			}
			
		} else if(postMonitoringDTO instanceof YoutubePostMonitoring) {
			
			YoutubePostMonitoring youtubePostMonitoring = (YoutubePostMonitoring) postMonitoringDTO;
			
			if(youtubePostMonitoring.getYoutubePost().getDescription() != null) {
				links.addAll(UtilFunctions.getHiperLinks(youtubePostMonitoring.getYoutubePost().getDescription()));
			}
			
			if(youtubePostMonitoring.getYoutubePost().getTitle() != null) {
				links.addAll(UtilFunctions.getHiperLinks(youtubePostMonitoring.getYoutubePost().getTitle()));
			}
			
		} else if(postMonitoringDTO instanceof InstagramPostMonitoring) {
			
			InstagramPostMonitoring instagramPostMonitoring = (InstagramPostMonitoring) postMonitoringDTO;
			
			if(instagramPostMonitoring.getInstagramPost().getPictureCaption() != null) {
				links.addAll(UtilFunctions.getHiperLinks(instagramPostMonitoring.getInstagramPost().getPictureCaption()));
			}
			
		}
		
		return links;
	}
	
	public static void updateMonitoringReports(PostMonitoringDTO postMonitoring, Character garbage) {
		
		MaintenanceMonitoringLogReportRemote monitoringLogReportRemote = (MaintenanceMonitoringLogReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringLogReport");
		MaintenanceMonitoringRemote monitoringRemote = (MaintenanceMonitoringRemote) RecoverMaintenance.recoverMaintenance("Monitoring");
		
		// Update MonitoringReports.
		try {
			
			MaintenanceMonitoringReportRemote monitoringReportRemote = (MaintenanceMonitoringReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringReport");
			
			Long generateDay = UtilFunctions.getMidnightDateNoDaylightTime(new Date(postMonitoring.getRetrievedDate() * 1000L));
			
			// Per day.
			if(monitoringReportRemote.hasEntity(postMonitoring.getNetwork(), generateDay, garbage, MonitoringReport.DAY, postMonitoring.getMonitoring().getIdMonitoring())) {
				
				MonitoringReport monitoringReport = monitoringReportRemote.getEntity(postMonitoring.getNetwork(), generateDay, garbage, MonitoringReport.DAY, postMonitoring.getMonitoring().getIdMonitoring());
				
				MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
				monitoringLogReport.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
				monitoringLogReport.setReportType(MonitoringLogReport.MON_REPORT_DAY);
				monitoringLogReport.setReportId(monitoringReport.getIdMonitoringReport().toString());
				monitoringLogReport.setValueToApply(-1);
				monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
				monitoringLogReport.setLogDate(new Date().getTime() / 1000L);
				
				monitoringLogReportRemote.save(monitoringLogReport);
			}
			
			//Per Day/Hour.
			if(garbage.equals(MonitoringReport.NOT_GARBAGE)) {
				
				String hour = getHour(postMonitoring.getRetrievedDate()).toString();
				
				if(monitoringReportRemote.hasEntity(postMonitoring.getNetwork(), generateDay, MonitoringReport.HOUR, hour, 
							postMonitoring.getMonitoring().getIdMonitoring())) {
					
					MonitoringReport monitoringReport = monitoringReportRemote.getEntity(postMonitoring.getNetwork(), 
							generateDay, MonitoringReport.HOUR, hour, postMonitoring.getMonitoring().getIdMonitoring());
					
					MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
					monitoringLogReport.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
					monitoringLogReport.setReportType(MonitoringLogReport.MON_REPORT_HOUR);
					monitoringLogReport.setReportId(monitoringReport.getIdMonitoringReport().toString());
					monitoringLogReport.setValueToApply(-1);
					monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
					monitoringLogReport.setLogDate(new Date().getTime() / 1000L);
					
					monitoringLogReportRemote.save(monitoringLogReport);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Update MonitoringTermsReports.
		try {
			
			if(garbage.equals(MonitoringReport.NOT_GARBAGE)) {
				
				MaintenanceMonitoringTermReportRemote monitoringTermReportRemote = (MaintenanceMonitoringTermReportRemote)
						RecoverMaintenance.recoverMaintenance("MonitoringTermReport");
				
				Long generateDay = UtilFunctions.getMidnightDateNoDaylightTime(new Date(postMonitoring.getRetrievedDate() * 1000L));
				
				if(monitoringTermReportRemote.hasEntity(postMonitoring.getMonitoring().getIdMonitoring(), 
						postMonitoring.getTerm(), postMonitoring.getNetwork(), generateDay)) {
					
					MonitoringTermReport monitoringTermReport = monitoringTermReportRemote.getEntity(
							postMonitoring.getMonitoring().getIdMonitoring(), postMonitoring.getTerm(), 
							postMonitoring.getNetwork(), generateDay);
					
					MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
					monitoringLogReport.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
					monitoringLogReport.setReportType(MonitoringLogReport.MON_TERM_REPORT);
					monitoringLogReport.setReportId(monitoringTermReport.getIdMonitoringTermReport().toString());
					monitoringLogReport.setValueToApply(-1);
					monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
					monitoringLogReport.setLogDate(new Date().getTime() / 1000L);
					
					monitoringLogReportRemote.save(monitoringLogReport);
				}
				
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		// Update MonitoringGroupsReports
		try {

			if(postMonitoring.getNetwork().equals(Profile.FACEBOOK) && garbage.equals(MonitoringReport.NOT_GARBAGE)) {

				FacePostMonitoringDTO facePostMonitoring = (FacePostMonitoringDTO) postMonitoring;

				MaintenanceMonitoringGroupReportRemote monitoringGroupReportRemote = (MaintenanceMonitoringGroupReportRemote)
						RecoverMaintenance.recoverMaintenance("MonitoringGroupReport");

				Long generateDay = UtilFunctions.getMidnightDateNoDaylightTime(new Date(facePostMonitoring.getRetrievedDate() * 1000L));

				if(monitoringGroupReportRemote.hasEntity(facePostMonitoring.getMonitoring().getIdMonitoring(), facePostMonitoring.getIdProfile(), generateDay)) {

					MonitoringGroupReport monitoringGroupReport = monitoringGroupReportRemote.getEntity(
							facePostMonitoring.getMonitoring().getIdMonitoring(), facePostMonitoring.getIdProfile(), generateDay);

					MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
					monitoringLogReport.setMonitoring(monitoringRemote.getById(facePostMonitoring.getMonitoring().getIdMonitoring()));
					monitoringLogReport.setReportType(MonitoringLogReport.MON_GROUP_REPORT);
					monitoringLogReport.setReportId(monitoringGroupReport.getIdMonitoringGroupReport().toString());
					monitoringLogReport.setValueToApply(-1);
					monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
					monitoringLogReport.setLogDate(new Date().getTime() / 1000L);
					
					monitoringLogReportRemote.save(monitoringLogReport);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		// Update MonitoringQualificationReports.
		try {
			
			if(postMonitoring.getQualification() != null) {
				
				MaintenanceMonitoringReportControlRemote monitoringReportControlRemote = 
						(MaintenanceMonitoringReportControlRemote) RecoverMaintenance.recoverMaintenance("MonitoringReportControl");
				
				MaintenanceMonitoringQualificationReportRemote monitoringQualificationReportRemote = 
						(MaintenanceMonitoringQualificationReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringQualificationReport");
				
				Long generateDay = UtilFunctions.getMidnightDateNoDaylightTime(new Date(postMonitoring.getRetrievedDate() * 1000L));
				
				if(monitoringReportControlRemote.hasEntity(postMonitoring.getMonitoring().getIdMonitoring(), 
						MonitoringReportControl.QUALIFICATION, generateDay)) {

					MonitoringQualificationReport monitoringQualificationReport;

					if(monitoringQualificationReportRemote.hasEntity(postMonitoring.getMonitoring().getIdMonitoring(), 
							postMonitoring.getTerm(), postMonitoring.getQualification(), postMonitoring.getNetwork(), generateDay)) {

						monitoringQualificationReport = monitoringQualificationReportRemote.getEntity(postMonitoring.getMonitoring().getIdMonitoring(), 
								postMonitoring.getTerm(), postMonitoring.getQualification(), postMonitoring.getNetwork(), generateDay);

						MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
						monitoringLogReport.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
						monitoringLogReport.setReportType(MonitoringLogReport.MON_QUALIFICATION_REPORT);
						monitoringLogReport.setReportId(monitoringQualificationReport.getIdMonitoringQualificationReport().toString());
						monitoringLogReport.setValueToApply(-1);
						monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
						monitoringLogReport.setLogDate(new Date().getTime() / 1000L);

						monitoringLogReportRemote.save(monitoringLogReport);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Update MonitoringTagsReports.
		try {

			MaintenanceMonitoringReportControlRemote monitoringReportControlRemote = 
					(MaintenanceMonitoringReportControlRemote) RecoverMaintenance.recoverMaintenance("MonitoringReportControl");

			MaintenanceMonitoringTagReportRemote monitoringTagReportRemote = 
					(MaintenanceMonitoringTagReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringTagReport");

			Long generateDay = UtilFunctions.getMidnightDateNoDaylightTime(new Date(postMonitoring.getRetrievedDate() * 1000L));

			for(MonitoringPostTag monitoringPostTag : postMonitoring.getMonitoringPostTags()) {
				
				try {
					
					if(monitoringReportControlRemote.hasEntity(monitoringPostTag.getIdMonitoring(), 
							MonitoringReportControl.TAG, generateDay)) {

						MonitoringTagReport monitoringTagReport;

						if(monitoringTagReportRemote.hasEntity(monitoringPostTag.getIdMonitoring(), 
								monitoringPostTag.getMonitoringTag().getIdTag(), monitoringPostTag.getNetwork(), generateDay)) {

							monitoringTagReport = monitoringTagReportRemote.getEntity(monitoringPostTag.getIdMonitoring(), 
									monitoringPostTag.getMonitoringTag().getIdTag(), monitoringPostTag.getNetwork(), generateDay);

							MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
							monitoringLogReport.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
							monitoringLogReport.setReportType(MonitoringLogReport.MON_TAG_REPORT);
							monitoringLogReport.setReportId(monitoringTagReport.getIdMonitoringTagReport().toString());
							monitoringLogReport.setValueToApply(-1);
							monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
							monitoringLogReport.setLogDate(new Date().getTime() / 1000L);

							monitoringLogReportRemote.save(monitoringLogReport);
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Update MonitoringPostsLocations.
		try {
			
			if(postMonitoring.getNetwork().equals(Profile.TWITTER)) {

				TwitterPostMonitoringDTO twitterPostMonitoring = (TwitterPostMonitoringDTO) postMonitoring;
				
				if(twitterPostMonitoring.getIdLocation() != null) {
					
					MaintenanceMonitoringPostLocationRemote monitoringPostLocationRemote = (MaintenanceMonitoringPostLocationRemote)
							RecoverMaintenance.recoverMaintenance("MonitoringPostLocation");
					
					MonitoringLocationId monitoringLocationId = new MonitoringLocationId();
					monitoringLocationId.setIdLocation(twitterPostMonitoring.getIdLocation());
					monitoringLocationId.setNetworkType(Profile.TWITTER);
					
					MonitoringPostLocationId monitoringPostLocationId = new MonitoringPostLocationId();
					monitoringPostLocationId.setPostId(twitterPostMonitoring.getPostId());
					monitoringPostLocationId.setMonitoring(twitterPostMonitoring.getMonitoring().getIdMonitoring());
					monitoringPostLocationId.setMonitoringLocation(monitoringLocationId);
					
					MonitoringPostLocation monitoringPostLocation = monitoringPostLocationRemote.getById(monitoringPostLocationId);
					
					if(monitoringPostLocation != null) {
						
						monitoringPostLocationRemote.delete(monitoringPostLocation);

						MaintenanceMonitoringReportControlRemote monitoringReportControlRemote = 
								(MaintenanceMonitoringReportControlRemote) RecoverMaintenance.recoverMaintenance("MonitoringReportControl");

						MaintenanceMonitoringLocationReportRemote monitoringLocationReportRemote = 
								(MaintenanceMonitoringLocationReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringLocationReport");

						// Update MonitoringLocationReport.
						if(monitoringReportControlRemote.hasEntity(monitoringPostLocation.getMonitoring().getIdMonitoring(), 
								MonitoringReportControl.LOCATION, monitoringPostLocation.getDate())) {

							if(monitoringLocationReportRemote.hasEntity(monitoringPostLocation.getMonitoring().getIdMonitoring(), 
									monitoringPostLocation.getMonitoringLocation().getFullName(), monitoringPostLocation.getDate())) {

								MonitoringLocationReport monitoringLocationReport = monitoringLocationReportRemote.getEntity(
										monitoringPostLocation.getMonitoring().getIdMonitoring(), 
										monitoringPostLocation.getMonitoringLocation().getFullName(), monitoringPostLocation.getDate());
								
								MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
								monitoringLogReport.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
								monitoringLogReport.setReportType(MonitoringLogReport.MON_LOCATION_REPORT);
								monitoringLogReport.setReportId(monitoringLocationReport.getIdMonitoringLocationReport().toString());
								monitoringLogReport.setValueToApply(-1);
								monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
								monitoringLogReport.setLogDate(new Date().getTime() / 1000L);

								monitoringLogReportRemote.save(monitoringLogReport);
							}
						}

					}
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Update MonitoringInfluentialsUsers.
		try {
			
			MaintenanceMonitoringInfluentialUserRemote monitoringInfluentialUserRemote = 
					(MaintenanceMonitoringInfluentialUserRemote) RecoverMaintenance.recoverMaintenance("MonitoringInfluentialUser");

			Long generateDay = UtilFunctions.getMidnightDateNoDaylightTime(new Date(postMonitoring.getRetrievedDate() * 1000L));

			String idUser = UtilFunctions.getIdUserFromPostMonitoring(postMonitoring);
			
			if(monitoringInfluentialUserRemote.hasEntity(idUser, 
					postMonitoring.getMonitoring().getIdMonitoring(), postMonitoring.getNetwork(), generateDay)) {
				
				MonitoringInfluentialUser monitoringInfluentialUser = monitoringInfluentialUserRemote.getEntity(idUser, 
						postMonitoring.getMonitoring().getIdMonitoring(), postMonitoring.getNetwork(), generateDay);
				
				MonitoringLogReport monLogReportInfUser = new MonitoringLogReport();
				monLogReportInfUser.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
				monLogReportInfUser.setReportType(MonitoringLogReport.MON_INFLUENTIAL_USER);
				monLogReportInfUser.setReportId(monitoringInfluentialUser.getIdMonitoringInfluentialUser().toString());
				monLogReportInfUser.setValueToApply(-1);
				monLogReportInfUser.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
				monLogReportInfUser.setLogDate(new Date().getTime() / 1000L);

				monitoringLogReportRemote.save(monLogReportInfUser);
				
				if((postMonitoring instanceof FacePostMonitoringDTO || postMonitoring instanceof TwitterPostMonitoringDTO) && postMonitoring.getQualification() != null) {
					if(postMonitoring.getQualification().equals(PostMonitoringDTO.POSITIVE)) {
						
						MonitoringLogReport monLogReportInfUserPos = new MonitoringLogReport();
						monLogReportInfUserPos.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
						monLogReportInfUserPos.setReportType(MonitoringLogReport.MON_INFLUENTIAL_USER_POS);
						monLogReportInfUserPos.setReportId(monitoringInfluentialUser.getIdMonitoringInfluentialUser().toString());
						monLogReportInfUserPos.setValueToApply(-1);
						monLogReportInfUserPos.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
						monLogReportInfUserPos.setLogDate(new Date().getTime() / 1000L);

						monitoringLogReportRemote.save(monLogReportInfUserPos);
						
					} else if(postMonitoring.getQualification().equals(PostMonitoringDTO.NEGATIVE)) {
						
						MonitoringLogReport monLogReportInfUserNeg = new MonitoringLogReport();
						monLogReportInfUserNeg.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
						monLogReportInfUserNeg.setReportType(MonitoringLogReport.MON_INFLUENTIAL_USER_NEG);
						monLogReportInfUserNeg.setReportId(monitoringInfluentialUser.getIdMonitoringInfluentialUser().toString());
						monLogReportInfUserNeg.setValueToApply(-1);
						monLogReportInfUserNeg.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
						monLogReportInfUserNeg.setLogDate(new Date().getTime() / 1000L);

						monitoringLogReportRemote.save(monLogReportInfUserNeg);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Update MonitoringLinks.
		try {
			
			if(postMonitoring.getHasLinks() != null && postMonitoring.getHasLinks()) {
			
				MaintenanceMonitoringLinkRemote monitoringLinkRemote = (MaintenanceMonitoringLinkRemote)
						RecoverMaintenance.recoverMaintenance("MonitoringLink");
				
				Long generateDay = UtilFunctions.getMidnightDateNoDaylightTime(new Date(postMonitoring.getRetrievedDate() * 1000L));
				
				List<URL> links = UtilFunctions.getHiperLinks(postMonitoring);
				
				for(URL link : links) {
					
					try {
						
						if(monitoringLinkRemote.hasEntity(link.toString(), postMonitoring.getMonitoring().getIdMonitoring(), 
								postMonitoring.getNetwork(), generateDay)) {
							
							MonitoringLink monitoringLink = monitoringLinkRemote.getEntity(link.toString(), 
									postMonitoring.getMonitoring().getIdMonitoring(), postMonitoring.getNetwork(), generateDay);
							
							MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
							monitoringLogReport.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
							monitoringLogReport.setReportType(MonitoringLogReport.MON_LINK_REPORT);
							monitoringLogReport.setReportId(monitoringLink.getIdMonitoringLink().toString());
							monitoringLogReport.setValueToApply(-1);
							monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
							monitoringLogReport.setLogDate(new Date().getTime() / 1000L);

							monitoringLogReportRemote.save(monitoringLogReport);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Update MonitoringHashTags.
		try {
			if(postMonitoring.getHasHashTags() != null && postMonitoring.getHasHashTags()) {
				
				MaintenanceMonitoringHashTagRemote monitoringHashTagRemote = (MaintenanceMonitoringHashTagRemote)
						RecoverMaintenance.recoverMaintenance("MonitoringHashTag");
				
				Long generateDay = UtilFunctions.getMidnightDateNoDaylightTime(new Date(postMonitoring.getRetrievedDate() * 1000L));
				
				List<String> hashTags = UtilFunctions.getHashTags(postMonitoring);
				
				for (String hashTag : hashTags) {
					
					if(monitoringHashTagRemote.hasEntity(hashTag, postMonitoring.getMonitoring().getIdMonitoring(), 
							postMonitoring.getNetwork(), generateDay)) {
						
						MonitoringHashTag monitoringHashTag = monitoringHashTagRemote.getEntity(hashTag, 
								postMonitoring.getMonitoring().getIdMonitoring(), postMonitoring.getNetwork(), generateDay);
						
						MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
						monitoringLogReport.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
						monitoringLogReport.setReportType(MonitoringLogReport.MON_HASHTAG_REPORT);
						monitoringLogReport.setReportId(monitoringHashTag.getIdMonitoringHashTag().toString());
						monitoringLogReport.setValueToApply(-1);
						monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
						monitoringLogReport.setLogDate(new Date().getTime() / 1000L);

						monitoringLogReportRemote.save(monitoringLogReport);
					}
					
				}
				
			}
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Integer getHour(Long unixTimeStamp) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		calendar.setTimeInMillis(unixTimeStamp * 1000L);

		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static String generateMD5Hash(String text) {

		String md5FinalString = "";

		try {
			MessageDigest md5First = MessageDigest.getInstance("MD5");
			md5First.update(StandardCharsets.UTF_8.encode(text));

			String md5FirstString = String.format("%032x", new BigInteger(1, md5First.digest()));

			String volatileSeed = String.valueOf(new Date().getTime() / 1000L);

			MessageDigest md5Second = MessageDigest.getInstance("MD5");
			md5Second.update(StandardCharsets.UTF_8.encode(md5FirstString + volatileSeed));

			md5FinalString = String.format("%032x", new BigInteger(1, md5Second.digest())).substring(0, 16);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return md5FinalString;
	}

	public static String getGroupStatusIcon(String status) {

		if(status.equals(Profile.GROUP_STATUS_OPEN)) {
			return ReportIcons.FACEBOOK_GROUP_PUBLIC;
		} else if(status.equals(Profile.GROUP_STATUS_CLOSED)) {
			return ReportIcons.FACEBOOK_GROUP_CLOSED;
		} else if(status.equals(Profile.GROUP_STATUS_SECRET)) {
			return ReportIcons.FACEBOOK_GROUP_SECRET;
		} else {
			return "";
		}
	}

	public static String getNetworkName(Character network) {
		
		String networkName = "";
		
		switch (network) {
		case 'F':
			networkName = "Facebook";
			break;
		case 'P':
			networkName = "Facebook";
			break;
		case 'T':
			networkName = "Twitter";
			break;
		case 'G':
			networkName = "Google +";
			break;
		case 'Y':
			networkName = "Youtube";
			break;
		case 'I':
			networkName = "Instagram";
			break;
		case 'R':
			networkName = "Reclame Aqui";
			break;
		case 'N':
			networkName = "News";
			break;
		case 'B':
			networkName = "Blogs";
			break;
		case 'L':
			networkName = "LinkedIn";
			break;
		case 'H':
			networkName = "LinkedIn";
			break;
		default:
			networkName = "Nenhuma";
			break;
		}
		
		return networkName;
	}
	
	public static Double getSecondsFromVideo(File file) {

		try {
			
			String command = "ffprobe -v error -show_entries format=duration -of default=noprint_wrappers=1:nokey=1 " + file.toString();
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();
			
			StringBuffer output = new StringBuffer();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
			
			return new BigDecimal(Double.valueOf(output.toString())).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			
		} catch (Exception e) {
			e.printStackTrace();
			return new BigDecimal(RandomUtils.nextDouble(3.000D, 60.000D)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		}	
	}
	
	public static JsonObject getInfoVideo(String file) {
		
		String jsonResponse = "";
		JsonObject jsonInfoVideoObject = new JsonObject();
		
		try {
			
			String command = "ffprobe -v quiet -print_format json -show_format -show_streams " + file;
			Process p = Runtime.getRuntime().exec(command);
			//p.waitFor();
			
			StringBuffer output = new StringBuffer();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
				jsonResponse += line;
			}
			
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonResponse);
			for (Object object : (JSONArray) jsonObject.get("streams")) {
				JSONObject jsonVideoStreamObject = (JSONObject) object;
				if(jsonVideoStreamObject.get("codec_type").equals("video")) {
					jsonInfoVideoObject.addProperty("codec", jsonVideoStreamObject.get("codec_name").toString());
					jsonInfoVideoObject.addProperty("width", Integer.valueOf(jsonVideoStreamObject.get("width").toString()));
					jsonInfoVideoObject.addProperty("height", Integer.valueOf(jsonVideoStreamObject.get("height").toString()));
					jsonInfoVideoObject.addProperty("duration", new BigDecimal(Double.valueOf(jsonVideoStreamObject.get("duration").toString())).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return jsonInfoVideoObject;
		}
		
		return jsonInfoVideoObject;
	}
	
	public static String formatAgeGroupDisplay(String initialAgeGroup, String endAgeGroup) {
		
		if (Integer.valueOf(initialAgeGroup) < 65) {
			return initialAgeGroup + '-' + endAgeGroup;
		} else {
			return initialAgeGroup + '+';
		}
	}
	
	public static Integer fromMilisecondsToHours(Long milisecondsTime) {
		return new BigDecimal(((milisecondsTime.doubleValue() / 1000D) / 60D) / 60D).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
	public static Integer fromMilisecondsToMinutes(Long milisecondsTime) {
		return new BigDecimal((milisecondsTime.doubleValue() / 1000D) / 60D).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
	public static Integer getDaysBetween(Long dateFrom, Long dateUntil) {
		return getDayInterval(dateFrom, dateUntil) + 1;
	}
	
	public static Integer getDayInterval(Long dateFrom, Long dateUntil) {
		return (int) (((dateUntil * 1000L) - (dateFrom * 1000L)) / (1000 * 60 * 60 * 24));
	}
	
	public static String getActivityPeriodName(String period) {
		
		if(period.equals("1 sem.")) {
			return "1 semana";
		} else if(period.equals("2 sem.")) {
			return "2 semanas";
		} else {
			return period;
		}
	}
	
	public static void addNotification(String message, Long idAccount, String color, String action) {
		
		MaintenanceAccountRemote accountRemote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
		MaintenanceNotificationRemote notificationRemote = (MaintenanceNotificationRemote) RecoverMaintenance.recoverMaintenance("Notification");

		Notification notification = new Notification();
		notification.setAccount(accountRemote.getById(idAccount));
		notification.setAction(action);
		notification.setDate(new Date());
		notification.setMessage(message);
		notification.setUnread(true);
		notification.setColor(color);

		try {
			
			notificationRemote.save(notification);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addInternalAnalytic(String module, String action, Long idAccount) {
		
		MaintenanceAccountRemote accountRemote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
		MaintenanceInternalAnalyticRemote internalAnalyticRemote = (MaintenanceInternalAnalyticRemote) RecoverMaintenance.recoverMaintenance("InternalAnalytic");
		
		InternalAnalytic internalAnalytic = new InternalAnalytic();
		internalAnalytic.setAccount(accountRemote.getById(idAccount));
		internalAnalytic.setModule(module);
		internalAnalytic.setAction(action);
		internalAnalytic.setActionDate(new Date());
		
		try {
			
			internalAnalyticRemote.save(internalAnalytic);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static InstagramProfileDevice getInstagramProfileDevice(Profile profile, Account account) {
		
		MaintenanceAccountRemote accountRemote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
		MaintenanceInstagramDeviceRemote instagramDeviceRemote = (MaintenanceInstagramDeviceRemote) RecoverMaintenance.recoverMaintenance("InstagramDevice");
		MaintenanceInstagramProfileDeviceRemote instagramProfileDeviceRemote = (MaintenanceInstagramProfileDeviceRemote) RecoverMaintenance.recoverMaintenance("InstagramProfileDevice");
		
		InstagramProfileDeviceId instagramProfileDeviceId = new InstagramProfileDeviceId();
		instagramProfileDeviceId.setAccount(account.getIdAccount());
		instagramProfileDeviceId.setInstagramUserId(profile.getNetworkId());
		
		InstagramProfileDevice instagramProfileDevice = instagramProfileDeviceRemote.getById(instagramProfileDeviceId);

		if(instagramProfileDevice == null) {
			
			try {
				
				instagramProfileDevice = new InstagramProfileDevice();
				instagramProfileDevice.setInstagramUserId(profile.getNetworkId());
				instagramProfileDevice.setAccount(accountRemote.getById(account.getIdAccount()));
				instagramProfileDevice.setInstagramDevice(instagramDeviceRemote.getById(instagramDeviceRemote.getInstagramDeviceLessUsedId()));
				instagramProfileDevice.setProxyRegion(getProxyRegion(account.getCountry(), ProxyRegion.THIRTY_MINUTES));
				instagramProfileDevice.setInstagramGUID(UUID.randomUUID().toString());
				instagramProfileDevice.setInstagramDeviceId("android-" + UtilFunctions.generateMD5Hash(profile.getScreenName() + profile.getInstagramPassword()));
				instagramProfileDevice.setInstagramPhoneId(UUID.randomUUID().toString());
				instagramProfileDevice.setPort(getResidentialPort(instagramProfileDevice.getProxyRegion()));
				
				instagramProfileDevice = instagramProfileDeviceRemote.save(instagramProfileDevice);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return instagramProfileDevice;
	}
	
	public static Integer getResidentialPort(ProxyRegion proxyRegion) {
    	return ThreadLocalRandom.current().nextInt(proxyRegion.getInitialIp(), proxyRegion.getFinalIp() + 1);
    }
	
	public static ProxyRegion getProxyRegion(String country, Integer stickIpDuration) {
		
		MaintenanceProxyRegionRemote proxyRegionRemote = (MaintenanceProxyRegionRemote) RecoverMaintenance.recoverMaintenance("ProxyRegion");
		
		ProxyRegion proxyRegion = proxyRegionRemote.getProxyRegion(country, stickIpDuration);
		
		if(proxyRegion == null) {
			proxyRegion = proxyRegionRemote.getProxyRegion(ProxyRegion.UNITED_STATES, stickIpDuration);
		}
		
		return proxyRegion;
	}
	
	public static List<AgeGroup> initiateAgeGroupList() {
		
		List<AgeGroup> ageGroups = new ArrayList<AgeGroup>();
		ageGroups.add(new AgeGroup("13", "17"));
		ageGroups.add(new AgeGroup("18", "24"));
		ageGroups.add(new AgeGroup("25", "34"));
		ageGroups.add(new AgeGroup("35", "44"));
		ageGroups.add(new AgeGroup("45", "55"));
		ageGroups.add(new AgeGroup("55", "64"));
		ageGroups.add(new AgeGroup("65", ""));
		
		return ageGroups;
	}
	
	// Método necessário para quando páginas são inseridas mas não o perfil principal, fazendo
	// com que não exista perfil para ser reconectado em nossa base.
	public static List<Profile> normalizeExpiredProfiles(List<Profile> profiles) {
		
		HashSet<String> ghostProfiles = new HashSet<String>();
		
		List<Profile> expiredProfiles = new ArrayList<Profile>();
		for(Profile profile : profiles) {
			if(!profile.getType().equals(Profile.FACEBOOK_PAGE) && !profile.getType().equals(Profile.LINKEDIN_PAGE) && !profile.getType().equals(Profile.INSTAGRAM)) {
				expiredProfiles.add(profile);
			} else if(profile.getParentProfile() == null 
					&& !ghostProfiles.contains(profile.getProfileInformation().split("♪")[0])) {
				
				MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				
				Profile ghostParent = new Profile();
				ghostParent.setIsTokenValid(Profile.INVALID_TOKEN);
				ghostParent.setActive(Profile.INACTIVE);

				ghostParent.setScreenName(profile.getProfileInformation().split("♪")[1]);
				if(profile.getType().equals(Profile.LINKEDIN_PAGE)) {
					ghostParent.setType(Profile.LINKEDIN);
				} else if(profile.getType().equals(Profile.FACEBOOK_PAGE)) {
					ghostParent.setType(Profile.FACEBOOK);
				}
				ghostParent.setNetworkId(profile.getProfileInformation().split("♪")[0]);
				ghostParent.setIdProfile(profile.getIdProfile());

				ghostParent.setChildrenProfiles(profileRemote.getEntitiesByProfileInformation(
						profile.getProfileInformation().split("♪")[0], profile.getAccount().getIdAccount()));
				
				ghostProfiles.add(ghostParent.getNetworkId());
				expiredProfiles.add(ghostParent);
			}
		}
		
		return expiredProfiles;
	}
	
	public static JsonObject recoverLinkResume(String url) throws Exception, OptimisticLockException {
		
		JsonObject jsonObject = new JsonObject();

		try {

			if ((url != null) && (url.indexOf("http") == -1)) {
				url = "http://" + url;
			}

			doc = Jsoup.parse(new URL(url), 5000);

			images = new ArrayList<Object>();

			getOpenGraphImage();

			img_container = new HashMap<String, Object>();

			String imageOG = doc.getElementsByAttributeValue("property", "og:image").attr("content");
			String title = doc.getElementsByAttributeValue("property", "og:title").attr("content");
			String description = doc.getElementsByAttributeValue("property", "og:description").attr("content");

			if ( (imageOG == null) || (imageOG == "") ) {
				Elements imgElements = doc.getElementsByTag("img");
	
				for(Iterator<Element> iterImg = imgElements.iterator(); iterImg.hasNext();) {
	
					Element imgElement = (Element) iterImg.next();
	
					GenericImageAdapt genericImageAdapt  = null;
	
					Integer width = null;
	
					if(imgElement.attr("width") != null && !imgElement.attr("width").isEmpty()) {
						if(imgElement.attr("width").contains("px")) {
							width = Integer.parseInt(imgElement.attr("width").substring(0, imgElement.attr("width").indexOf("px")));
						} else {
							width = Integer.parseInt(imgElement.attr("width"));
						}
					} else {
	
						try {
							genericImageAdapt = UtilFunctions.getImageSize(url + imgElement.attr("src"));
							width = genericImageAdapt.getWidth();
						} catch (Exception e) {
							try {
								genericImageAdapt = UtilFunctions.getImageSize(imgElement.attr("src"));
								width = genericImageAdapt.getWidth();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					}
					
					Integer height = null;
				
					if(genericImageAdapt != null) {
						height = genericImageAdapt.getHeight();
					} else {
						if(imgElement.attr("height") != null && !imgElement.attr("height").isEmpty()) {
							
							if(imgElement.attr("height").contains("px")) {
								height = Integer.parseInt(imgElement.attr("height").substring(0, imgElement.attr("height").indexOf("px")));
							} else {
								height = Integer.parseInt(imgElement.attr("height"));
							}
						}
					}
	
					if(width != null) {
	
						if(width > 100) {
	
							try {
								new URL(imgElement.attr("src"));
								img_container.put("image", imgElement.attr("src"));
								img_container.put("width", width);
								img_container.put("height", height);
								images.add(img_container);
								break;
							} catch (Exception e) {
							}
						}
						
					} else {
						new URL(imgElement.attr("src"));
						img_container.put("image", imgElement.attr("src"));
						img_container.put("width", null);
						img_container.put("height", null);
						images.add(img_container);
						break;
					}
				}
			} else {

				img_container.put("image", imageOG);
				img_container.put("width", null);
				img_container.put("height", null);
				images.add(img_container);
			}

			if (title == null || title == "") {
				title = doc.getElementsByTag("title").html();
			}

			if (description == null || description == "") {
				title = doc.getElementsByTag("title").html();
			}


			jsonObject.addProperty("title", title);
			jsonObject.addProperty("description", description);
			jsonObject.addProperty("image", imageOG);//images.get(0).toString()); //TODO if null
			jsonObject.addProperty("link", url);

		} catch (UnknownHostException e) {
			jsonObject = null;
		} catch (Exception e) {

			jsonObject.addProperty("title", "");
			jsonObject.addProperty("description", "");
			jsonObject.addProperty("image", "");
			jsonObject.addProperty("link", url);
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	private static void getOpenGraphImage() {
		
		String image = null;
		
		Elements elements = doc.select("meta[property=og:image]");
		
		if(!elements.isEmpty()) {
			
			image = elements.get(0).attr("content");
			
			try {
				
				if(!new URI(image).isAbsolute()) {
					
					URI uriImage = new URI(doc.baseUri());
					
					image = uriImage.getScheme() + "://" + uriImage.getHost() + image;
				}
				
				new URL(image);
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
				image = null;
			} catch (URISyntaxException e) {
				e.printStackTrace();
				image = null;
			}
		}
		
		if(image != null) {
			//images.add(image + "◙null◙null");
			img_container.put("image", image);
			img_container.put("width", null);
			img_container.put("height", null);
			images.add(img_container);
		}
	}
	
	public static String getImageExtension(String contentType) {

		String imageExtension = "";

		if(contentType.equals(IMG_JPEG)) {
			imageExtension = "jpg";
		} else if(contentType.equals(IMG_PNG)) {
			imageExtension = "png";
		}

		return imageExtension;
	}
	
	public static String generateSignature(String msg, String keyString) {
		
		String result = null;

		try {
			
			byte[] keyBytes = keyString.getBytes();
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(msg.getBytes(Charset.forName("UTF-8")));
			byte[] hexBytes = new Hex().encode(rawHmac);
			result = new String(hexBytes, "UTF-8");
		} catch (Exception e) {

		}
		
		return result;
	}
}