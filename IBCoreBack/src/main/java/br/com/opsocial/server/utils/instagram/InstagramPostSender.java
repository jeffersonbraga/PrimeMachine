package br.com.opsocial.server.utils.instagram;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.api.client.http.HttpStatusCodes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenanceInstagramDeviceRemote;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.ProxyRegion;
import br.com.opsocial.ejb.entity.instagram.InstagramDevice;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.ejb.entity.sets.PostAttachment;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.aws.S3Integration;
import br.com.opsocial.server.utils.instagram.entities.FollowerFeed;
import br.com.opsocial.server.utils.instagram.entities.FriendshipStatus;

public class InstagramPostSender {
	
	private InstagramProxyClient instagramProxyClient;
	
	private String loginResponse;
	private String sendImageResponse;
	private String configImageResponse;
	private String sendVideoMessage;
	private String sendChunkVideoMessage;
	private String configVideoResponse;
	private String configAlbumResponse;
	private String followersResponse;
	private String followResponse;
	
	private Boolean isAccountLocked;
	private Boolean isAccountAuthenticated;
	private Integer statusCodeOfLastRequest;
	
	private String jsonResponseError;
	private Integer statusCode;
	
	private String rankToken;
	
	private String cookies;		
	private Long lastLoginTime;
	private ProxyRegion proxyRegion;
	private Integer port;
	private InstagramDevice instagramDevice;
	private String deviceUserAgent;
	
	public String uploadedPostId;
			
	public static final String PHOTO = "photo";		
	public static final String VIDEO = "video";
	
	public static final String API_URL = "https://i.instagram.com/api/v1/";
	
	public static final String LOGIN_URL = "https://i.instagram.com/api/v1/accounts/login/";
	public static final String GET_CSRF_TOKEN_URL = "https://i.instagram.com/api/v1/si/fetch_headers/";
	public static final String SYNC_FEATURES_URL = "https://i.instagram.com/api/v1/qe/sync/"; 
	public static final String AUTO_COMPLETE_USER_LIST_URL = "https://i.instagram.com/api/v1/friendships/autocomplete_user_list/";
	public static final String TIMELINE_FEED_URL = "https://i.instagram.com/api/v1/feed/timeline/";
	public static final String RANKED_RECIPIENTS_URL = "https://i.instagram.com/api/v1/direct_v2/ranked_recipients/";
	public static final String RECENT_RECIPIENTS_URL = "https://i.instagram.com/api/v1/direct_share/recent_recipients/";
	public static final String MEGAPHONE_LOG_URL = "https://i.instagram.com/api/v1/megaphone/log/";
	public static final String DIRECT_V2_INBOX_URL = "https://i.instagram.com/api/v1/direct_v2/inbox/";
	public static final String NEWS_INBOX_URL = "https://i.instagram.com/api/v1/news/inbox/";
	public static final String FEED_REELS_TRAY_URL = "https://i.instagram.com/api/v1/feed/reels_tray/";
	public static final String DISCOVER_EXPLORE_URL = "https://i.instagram.com/api/v1/discover/explore/";
	
	public static final String UPLOAD_PHOTO_URL = "https://i.instagram.com/api/v1/upload/photo/";
	public static final String TIMELINE_CONFIGURE_IMAGE_URL = "https://i.instagram.com/api/v1/media/configure/";
	public static final String STORY_CONFIGURE_IMAGE_URL = "https://i.instagram.com/api/v1/media/configure_to_story/";
	
	public static final String UPLOAD_VIDEO_URL = "https://i.instagram.com/api/v1/upload/video/";
	public static final String TIMELINE_CONFIGURE_VIDEO_URL = "https://i.instagram.com/api/v1/media/configure/?video=1";
	public static final String STORY_CONFIGURE_VIDEO_URL = "https://i.instagram.com/api/v1/media/configure_to_story/?video=1";
	
	public static final String TIMELINE_CONFIGURE_ALBUM_URL = "https://i.instagram.com/api/v1/media/configure_sidecar/";
	
	public static final String BUSINESS_INSIGHTS_URL = "https://i.instagram.com/api/v1/insights/account_organic_insights/";//https://i.instagram.com/api/v1/insights/account_organic_insights?page_type=AUDIENCE&timeframe=ONE_WEEK&timezone_name=Etc/Greenwich
	public static final String BUSINESS_INSIGHTS_DETAIL_URL = "https://i.instagram.com/api/v1/insights/account_organic_insights?page_type=AUDIENCE&timeframe=ONE_WEEK&timezone_name=Etc/Greenwich";
	public static final String BUSINESS_INSIGHTS_MEDIA_URL = "https://i.instagram.com/api/v1/insights/media_organic_insights/{$mediaId}/";	
	
	public static final String CHECKPOINT_REQUIRED = "checkpoint_required";
	
	public static final Integer POST_STUCK_TIME = 900; // 15 minutes in seconds.
	public static final Integer MAX_POSTING_ATTEMPTS = 3; // 3 attempts to post on Instagram.
	
	public static final String X_IG_CAPABILITIES = "3ToAAA==";
	public static final String X_IG_CONNECTION_TYPE = "WIFI";
	public static final String X_FB_HTTP_ENGINE = "Liger";
	
	public static final String LOGIN_EXPERIMENTS = "ig_android_background_phone_confirmation_v2,ig_android_confirmation_code_registration,ig_android_non_fb_sso,ig_android_sms_consent_in_reg,ig_android_analytics_data_loss,ig_fbns_blocked,ig_android_gmail_oauth_in_reg,ig_android_show_password_in_reg_universe,ig_android_reg_inline_errors,ig_fbns_push,ig_android_gmail_oauth_in_access,ig_android_reg_whiteout_redesign_v2,ig_android_phoneid_sync_interval,ig_android_access_redesign,ig_android_link_to_access_if_email_taken_in_reg";
	public static final String EXPERIMENTS = "ig_android_ad_holdout_16m5_universe,ig_creation_growth_holdout,ig_android_business_conversion_social_context,ig_android_shopping,ig_android_ad_always_send_ad_attribution_id_universe,ig_android_direct_links,ig_android_insta_video_abr_resize,ig_android_special_brush,ig_android_stories_private_likes,ig_android_universe_video_production,ig_android_http_stack_experiment_2017,ig_android_enable_main_feed_reel_tray_preloading,ig_android_ad_cta_redesign_universe,ig_fbns_push,ig_android_live_special_codec_size_list,ig_android_direct_send_auto_retry,ig_android_preview_capture,ig_android_stories_book_universe,android_instagram_prefetch_suggestions_universe,ig_android_direct_video_autoplay_scroll,ig_android_offline_location_feed,ig_android_histogram_reporter,ig_android_exoplayer_4142,ig_android_draw_button_new_tool_animation,ig_android_disable_comment,ig_android_offline_likes_v2,ig_android_high_res_upload_2,ig_android_direct_address_links,ig_android_chaining_teaser_animation,ig_android_2fac,ig_android_react_native_lazy_modules_killswitch,ig_android_checkbox_instead_of_button_as_follow_affordance_universe,ig_android_insta_video_audio_encoder,ig_android_grid_video_icon,ig_android_image_disk_cache_max_entry_count,ig_android_mark_reel_seen_on_Swipe_forward,ig_android_disable_chroma_subsampling,ig_android_share_spinner,ig_android_reel_viewer_data_buffer_size,ig_android_video_reuse_surface,ig_android_offline_reel_feed,ig_android_offline_follows,ig_android_instavideo_periodic_notif,ig_android_snippets_haptic_feedback,ig_request_cache_layer,ig_android_promote_loading_screen_image,ig_android_unified_inbox,ig_android_stories_teach_gallery_location,ig_android_top_live_titles_universe,ig_android_insta_video_reconnect_viewers,ig_android_new_block_flow,ig_android_remove_followers_universe,ig_android_direct_link_style,ig_android_boomerang_feed_attribution,ig_android_text_background,ig_fbns_shared,ig_android_react_native_universe,ig_android_live_see_fewer_videos_like_this_universe,ig_android_snippets_profile_nux,ig_android_video_loopcount_int,ig_android_profile_photo_as_media,ig_android_direct_sqlite_universe,ig_android_camera_universe,ig_video_max_duration_qe_preuniverse,ig_android_ad_show_mai_cta_loading_state_universe,ig_android_swipe_navigation_x_angle_universe,ig_android_profile,ig_android_empty_feed_redesign,ig_android_direct_blue_tab,ig_android_video_single_surface,ig_android_enable_share_to_messenger,ig_android_mqtt_skywalker,ig_ranking_following,ig_family_bridges_holdout_universe,ig_android_stories_weblink_creation,ig_android_ad_fix_missing_viewed_time_events_universe,ig_android_insta_video_broadcaster_infra_perf,ig_android_sms_consent_in_edit_profile,ig_android_full_user_detail_endpoint,ig_android_mead,ig_android_profile_share_username,ig_android_asset_button_new_content_animation,ig_android_verified_comments_universe,ig_android_business_conversion_value_prop_v2,ig_android_http_stack_kz_debug,ig_android_sticker_tap_affordance_more_prominent,ig_android_etag_layer,ig_android_su_in_feed_unit_redesign,ig_android_keep_http_cache_on_user_switch,ig_android_retry_story_seen_state,ig_android_live_analytics,ig_android_su_activity_feed,ig_android_offline_commenting,ig_android_immersive_viewer,ig_android_video_keep_screen_on,ig_android_links_receivers,ig_android_family_bridge_discover,ig_android_save_collections,ig_android_gl_drawing_marks_after_undo_backing,ig_android_business_action,ig_save_insights,ig_android_channels_home,ig_android_mark_seen_state_on_viewed_impression,ig_android_non_square_first,ig_android_log_mediacodec_info,ig_android_react_native_universe_kill_switch,ig_android_stories_weblink_consumption,ig_android_insta_video_drawing,ig_android_facebook_twitter_profile_photos,ig_android_view_pager_paging_check_fix,ig_android_should_sample_ppr,ig_android_infinite_scrolling_launch,ig_android_swipeable_filters_blacklist,android_ig_fbns_kill_switch,ig_android_direct_video_autoplay,ig_android_share_to_whatsapp,ig_android_direct_mutually_exclusive_experiment_universe,ig_android_progressive_jpeg,ig_android_redirect_to_low_latency_universe,ig_android_stories_tray_etag_layer,ig_android_swipeablefilters_universe,ig_android_insta_video_sound_always_on,ig_android_share_profile_photo_to_feed_universe,ig_android_capture_slowmo_mode,ig_android_light_status_bar,ig_android_instavideo_remove_nux_comments,ig_android_instavideo_audio_only_mode,ig_fbns_blocked,ig_fbns_preload_default,ig_promotions_unit_in_insights_landing_page,ig_android_post_auto_retry_v7_21,ig_android_direct_typing_indicator,ig_android_direct_phone_number_links,ig_android_network_cancellation,ig_android_anrwatchdog,ig_android_search_client_matching,ig_android_user_detail_endpoint,ig_android_os_version_blocking,ig_android_add_follow_button_to_direct_thread_details,ig_android_sfplt,ig_android_show_sidecar_insights,ig_android_ad_watchbrowse_universe,ig_android_insta_video_consumption_titles,ig_android_family_bridge_bookmarks,ig_android_offline_main_feed,ig_android_fb_topsearch_sgp_fork_request,ig_android_sidecar,ig_android_memory_improve_universe,ig_android_media_favorites,ig_android_live_enhanced_end_screen_universe,ig_android_save_all,ig_android_insta_video_universe,ig_android_promote_from_profile_button,ig_android_stories_device_tilt,ig_android_universe_reel_video_production,ig_android_ontact_invite_universe,ig_android_offline_hashtag_feed,ig_android_disk_cache_has_sanity_check,ig_android_camera_cover_perf_improvement,ig_explore_netego,ig_android_samsung_app_badging,ig_android_disk_usage,ig_android_business_promotion,ig_android_live_save_to_camera_roll,ig_android_comment_tweaks_universe,ig_android_suggest_password_reset_on_oneclick_login,ig_android_disk_cache_match_journal_size_to_cache_max_count,ig_android_react_native_usertag,ig_android_offline_story_stickers,ig_android_feedvideoplayer_consistent_volume,ig_fbns_dump_ids,ig_android_feed_header_profile_ring_universe,ig_android_live_join_comment_ui_change,ig_in_feed_commenting,ig_android_direct_inbox_count,ig_android_rendering_controls,ig_android_live_stop_broadcast_on_404,ig_formats_and_feedbacks_holdout_universe,ig_video_copyright_whitelist,ig_android_insta_video_consumption_infra,ig_android_disable_comment_public_test,ig_android_offline_mode_holdout,ig_android_insta_video_consumption,ig_android_render_iframe_interval,ig_android_cache_layer_timeout,ig_android_pending_request_search_bar,ig_android_ad_rn_preload_universe,ig_android_toplive_verified_badges_universe,ig_android_discover_page_find_people_buttons,ig_android_explore_story_sfslt_universe,ig_android_search,ig_android_boomerang_entry,ig_android_fb_invite_in_followers_list,ig_android_insta_video_titles_universe,ig_android_send_direct_typing_indicator,ig_android_business_conversion_value_prop_navigate,ig_android_live_like_button_position_test,ig_video_use_sve_universe,ig_android_explore_verified_badges_stories_universe,ig_android_live_skin_smooth,ig_android_marauder_update_frequency,ig_android_family_bridge_share,ig_android_exoplayer_holdout,ig_android_offline_reel_profile,ig_android_add_to_last_post,ig_android_offline_freshness_toast_10_8,ig_android_offline_explore_10_8,ig_android_react_native_restart_after_error_universe,ig_android_video_captions_universe,ig_android_last_edits,ig_android_video_download_logging,ig_android_ad_carousel_redesign_universe,ig_android_comment_deep_linking_v1,ig_android_snippets_feed_tooltip,ig_android_async_network_tweak_universe,ig_android_use_software_layer_for_kc_drawing_universe,ig_android_react_native_ota,ig_android_following_follower_social_context";
	
	private final static String VIDEO_AVI = "video/avi";
	private final static String VIDEO_ASF = "video/x-ms-asf";
	private final static String VIDEO_DV = "video/x-dv";
	private final static String VIDEO_MOD = "audio/mod";
	private final static String VIDEO_MOV = "video/quicktime";
	private final static String VIDEO_MP4 = "video/mp4";
	private final static String VIDEO_MPEG = "video/mpeg";
	private final static String VIDEO_XFLV = "video/x-flv";
	private final static String VIDEO_3GP = "video/3gpp";
	private final static String VIDEO_MKV = "video/x-matroska";
	
	private final static String TIMELINE = "timeline";
	private final static String STORY = "story";
	private final static String ALBUM = "album";
	
	private final static String PHOTO_FILE = "photoFile";
	private final static String VIDEO_FILE = "videoFile";
	
	public static final Long APP_REFRESH_INTERVAL = 10800L;
	
	public InstagramPostSender(String cookies, Long lastLoginTime, ProxyRegion proxyRegion, InstagramDevice instagramDevice, Integer port) {	
		this.cookies = cookies;
		this.lastLoginTime = lastLoginTime;
		this.proxyRegion = proxyRegion;
		this.instagramDevice = instagramDevice;
		this.deviceUserAgent = instagramDevice.getDeviceUserAgent() + " " + proxyRegion.getDeviceLanguage() + ")";
		this.port = port;
	}

	private void syncFeatures(String instagramGUID, String networkId, Boolean preLogin) {

		try {

			if(instagramProxyClient == null) {
				instagramProxyClient = new InstagramProxyClient(cookies, proxyRegion, port);
			}

			Header[] headers = new Header[5];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			headers[2] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[3] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[4] = new BasicHeader("X-IG-Capabilities", X_IG_CAPABILITIES);
			
			String data = "";
			if(preLogin) {
				data = "{\"id\":\"" + instagramGUID + "\",\"experiments\":\"" + LOGIN_EXPERIMENTS + "\"}";
			} else {
				
				String CSRFToken = instagramProxyClient.getCSRFToken();
				
				data = "{\"_uuid\":\"" + instagramGUID + "\",\"_uid\":\"" + networkId + "\",\"_csrftoken\":\"" + CSRFToken + "\",\"id\":\"" + networkId + "\",\"experiments\":\"" + EXPERIMENTS + "\"}";
			}
			
			String sig = UtilFunctions.generateSignature(data, instagramDevice.getIgSigKey());
			data = "signed_body=" + sig + "." + URLEncoder.encode(data, "UTF-8") + "&ig_sig_key_version=4";
			
			CloseableHttpResponse response = (CloseableHttpResponse) instagramProxyClient.requestPOST(SYNC_FEATURES_URL, data, headers);

			cookies = instagramProxyClient.getCookiesAsJson();
			
			statusCode = response.getStatusLine().getStatusCode();
			extractJSONResponse(response);
			statusCodeOfLastRequest = response.getStatusLine().getStatusCode();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generateCSRFToken(String instagramGUID) {
		
		try {
			
			if(instagramProxyClient == null) {
				instagramProxyClient = new InstagramProxyClient(cookies, proxyRegion, port);
			}
	
			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(GET_CSRF_TOKEN_URL))
		            .addParameter("challenge_type", "signup")
		            .addParameter("guid", instagramGUID.replaceAll("-", ""));
		    URI uri = uriBuilder.build();
			
			CloseableHttpResponse response = (CloseableHttpResponse) instagramProxyClient.requestGET(uri, headers);
			cookies = instagramProxyClient.getCookiesAsJson();
			
			statusCode = response.getStatusLine().getStatusCode();
			extractJSONResponse(response);
			statusCodeOfLastRequest = response.getStatusLine().getStatusCode();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void timelineFeed() {
		
		try {
			
			if(instagramProxyClient == null) {
				instagramProxyClient = new InstagramProxyClient(cookies, proxyRegion, port);
			}
	
			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(TIMELINE_FEED_URL)).
					addParameter("rank_token", rankToken).
					addParameter("ranked_content", "true");
		    URI uri = uriBuilder.build();
			
			CloseableHttpResponse response = (CloseableHttpResponse) instagramProxyClient.requestGET(uri, headers);
			
			cookies = instagramProxyClient.getCookiesAsJson();
			
			statusCode = response.getStatusLine().getStatusCode();
			extractJSONResponse(response);
			statusCodeOfLastRequest = response.getStatusLine().getStatusCode();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String requestPostDefault(String jsonAddress, String postData, String agent) {
		
		String sonuc = "";
		
		try {
			
			if(instagramProxyClient == null) {
				instagramProxyClient = new InstagramProxyClient(cookies, proxyRegion, port);
			}
			
			Header[] headers = new Header[11];
			headers[0] = new BasicHeader("Host", "i.instagram.com");
			headers[1] = new BasicHeader("User-Agent", agent);
			headers[2] = new BasicHeader("Connection", "keep-alive");
			headers[3] = new BasicHeader("Accept", "*/*");
			headers[4] = new BasicHeader("Accept-Encoding", "gzip, deflate, sdch");
			headers[5] = new BasicHeader("X-IG-Capabilities", X_IG_CAPABILITIES);
			headers[6] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[7] = new BasicHeader("X-IG-Connection-Speed", String.valueOf(ThreadLocalRandom.current().nextLong(1000, 3701)) + "kbps");
			headers[8] = new BasicHeader("X-FB-HTTP-Engine", X_FB_HTTP_ENGINE);
			headers[9] = new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			headers[10] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			
			CloseableHttpResponse response = (CloseableHttpResponse) instagramProxyClient.requestPOST(jsonAddress, postData, headers);
			
			cookies = instagramProxyClient.getCookiesAsJson();
			
			statusCode = response.getStatusLine().getStatusCode();
			statusCodeOfLastRequest = response.getStatusLine().getStatusCode();
			sonuc = extractJSONResponse(response);
			
		} catch (Exception e) {
			e.printStackTrace();
			return sonuc;
		}
		
		return sonuc;
	}
	
	public Boolean authenticateUser(Profile profile, Boolean forceLogin) {
		
		try {
			
			if(instagramProxyClient == null) {
				instagramProxyClient = new InstagramProxyClient(cookies, proxyRegion, port);
			}
			
			if(!isLoggedIn() || forceLogin) {
			
				syncFeatures(profile.getInstagramGUID(), profile.getNetworkId(), true);
				generateCSRFToken(profile.getInstagramGUID());
				
				String deviceId = profile.getInstagramDeviceId();
				String phoneId = profile.getInstagramPhoneId();
				String CSRFToken = instagramProxyClient.getCSRFToken();
				String GUID = profile.getInstagramGUID();
				String loginAttemptCount = "0";
				
				String data = "{\"phone_id\":\"" + phoneId + "\",\"_csrftoken\":\"" + CSRFToken + "\",\"username\":\"" + profile.getScreenName() + "\",\"guid\":\"" + GUID + "\",\"device_id\":\"" + deviceId + "\",\"password\":\"" + profile.getInstagramPassword() + "\",\"login_attempt_count\":\"" + loginAttemptCount + "\"}";
				String sig = UtilFunctions.generateSignature(data, instagramDevice.getIgSigKey());
				data = "signed_body=" + sig + "." + URLEncoder.encode(data, "UTF-8") + "&ig_sig_key_version=4";
				
				//Login Request
				loginResponse = requestPostDefault(LOGIN_URL, data, deviceUserAgent);
				
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(loginResponse);
				JSONObject jsonObject = (JSONObject) obj;
				if (((String) jsonObject.get("status")).equals("ok")) { //Login SuccessFul
					
					isAccountAuthenticated = true;
					
					rankToken = profile.getNetworkId() + "_" + profile.getInstagramGUID();
					
					lastLoginTime = new Date().getTime();
					
					new Thread(() -> new InstagramCallsHelper(cookies, proxyRegion, port, instagramDevice, profile).requestsAfterAuthenticated()).start();
					
					return true;
				} else {
					isAccountAuthenticated = false;
					if(jsonObject.containsKey("lock")) {
						isAccountLocked = (Boolean) jsonObject.get("lock");
					}
					return false;
				}
				
			} else {
				rankToken = profile.getNetworkId() + "_" + profile.getInstagramGUID();
			}
			
			// Act like a real logged in app client refreshing its news timeline.
			// This also lets us detect if we're still logged in with a valid session.
			timelineFeed();
			
			if(statusCodeOfLastRequest != HttpStatus.SC_OK) {
				authenticateUser(profile, true);
			}
			
			// SUPER IMPORTANT:
	        //
	        // STOP trying to ask us to remove this code section!
	        //
	        // EVERY time the user presses their device's home button to leave the
	        // app and then comes back to the app, Instagram does ALL of these things
	        // to refresh its internal app state. We MUST emulate that perfectly,
	        // otherwise Instagram will silently detect you as a "fake" client
	        // after a while!
	        //
	        // You can configure the login's $appRefreshInterval in the function
	        // parameter above, but you should keep it VERY frequent (definitely
	        // NEVER longer than 6 hours), so that Instagram sees you as a real
	        // client that keeps quitting and opening their app like a REAL user!
	        //
	        // Otherwise they WILL detect you as a bot and silently BLOCK features
	        // or even ban you.
	        //
	        // You have been warned.
			if(lastLoginTime == null || ((new Date().getTime() - lastLoginTime) / 1000L) > APP_REFRESH_INTERVAL) {
				
				lastLoginTime = new Date().getTime();
				
				new Thread(() -> new InstagramCallsHelper(cookies, proxyRegion, port, instagramDevice, profile).requestWhenAlreadyAuthenticated()).start();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public Boolean uploadStoryPhoto(Profile profile, String caption, File file) {
		
		Boolean wasSend = false;
		
		try {
			
			// Verificação de novos parâmetros em nova versão do APP do Instagram.
			if(profile.getInstagramDeviceId() == null || profile.getInstagramPhoneId() == null) {
				
				profile.setInstagramDeviceId("android-" + UtilFunctions.generateMD5Hash(profile.getScreenName() + profile.getInstagramPassword()));
				profile.setInstagramPhoneId(UUID.randomUUID().toString());
				
				// Little work around to make the user connect a different device, waiting for the verification on mobile phone.
				if(profile.getInstagramDevice() == null) {
					
					MaintenanceInstagramDeviceRemote instagramDeviceRemote = (MaintenanceInstagramDeviceRemote) 
							RecoverMaintenance.recoverMaintenance("InstagramDevice");
					
					String instagramGUID;
					
					instagramGUID = UUID.randomUUID().toString();
					instagramDevice = instagramDeviceRemote.getById(instagramDeviceRemote.getInstagramDeviceLessUsedId());
					
					profile.setInstagramGUID(instagramGUID);
					profile.setInstagramDevice(instagramDevice);
				}
				
				MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				
				profileRemote.save(profile);
			}
			
			if(authenticateUser(profile, false)) {
				wasSend = uploadSinglePhoto(profile, STORY, caption, file, null, PHOTO_FILE);
			} else {
				jsonResponseError = loginResponse;
				wasSend = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			wasSend = false;
		}
		
		try {
			instagramProxyClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return wasSend;
	}
	
	public Boolean uploadStoryVideo(Profile profile, Post post, File file) {
		
		Boolean wasSend = false;
		
		try {
			
			// Verificação de novos parâmetros em nova versão do APP do Instagram.
			if(profile.getInstagramDeviceId() == null || profile.getInstagramPhoneId() == null) {
				
				profile.setInstagramDeviceId("android-" + UtilFunctions.generateMD5Hash(profile.getScreenName() + profile.getInstagramPassword()));
				profile.setInstagramPhoneId(UUID.randomUUID().toString());
				
				// Little work around to make the user connect a different device, waiting for the verification on mobile phone.
				if(profile.getInstagramDevice() == null) {
					
					MaintenanceInstagramDeviceRemote instagramDeviceRemote = (MaintenanceInstagramDeviceRemote) 
							RecoverMaintenance.recoverMaintenance("InstagramDevice");
					
					String instagramGUID;
					
					instagramGUID = UUID.randomUUID().toString();
					instagramDevice = instagramDeviceRemote.getById(instagramDeviceRemote.getInstagramDeviceLessUsedId());
					
					profile.setInstagramGUID(instagramGUID);
					profile.setInstagramDevice(instagramDevice);
				}
				
				MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				
				profileRemote.save(profile);
			}
			
			if(authenticateUser(profile, false)) {
				
				JSONObject jsonObject = requestVideoUploadURL(profile, STORY, post, post.getPostsAttachments().get(0)); 
				
				if(jsonObject != null) {
					
					wasSend = uploadVideoData(file, jsonObject, post, jsonObject.get("uploadId").toString(), profile, STORY, post.getPostsAttachments().get(0));
					
					uploadSinglePhoto(profile, STORY, post.getText(), file, jsonObject.get("uploadId").toString(), VIDEO_FILE);
					
					configureSingleVideo(profile, post, file, jsonObject.get("uploadId").toString(), STORY);
					
				} else {
					wasSend = false;
				}
				
			} else {
				jsonResponseError = loginResponse;
				wasSend = false;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			wasSend = false;
		}
		
		try {
			instagramProxyClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return wasSend;
	}
	
	public Boolean uploadTimelineVideo(Profile profile, Post post, File file) {
		
		Boolean wasSend = false;
		
		try {
			
			// Verificação de novos parâmetros em nova versão do APP do Instagram.
			if(profile.getInstagramDeviceId() == null || profile.getInstagramPhoneId() == null) {
				
				profile.setInstagramDeviceId("android-" + UtilFunctions.generateMD5Hash(profile.getScreenName() + profile.getInstagramPassword()));
				profile.setInstagramPhoneId(UUID.randomUUID().toString());
				
				// Little work around to make the user connect a different device, waiting for the verification on mobile phone.
				if(profile.getInstagramDevice() == null) {
					
					MaintenanceInstagramDeviceRemote instagramDeviceRemote = (MaintenanceInstagramDeviceRemote) 
							RecoverMaintenance.recoverMaintenance("InstagramDevice");
					
					String instagramGUID;
					
					instagramGUID = UUID.randomUUID().toString();
					instagramDevice = instagramDeviceRemote.getById(instagramDeviceRemote.getInstagramDeviceLessUsedId());
					
					profile.setInstagramGUID(instagramGUID);
					profile.setInstagramDevice(instagramDevice);
				}
				
				MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				
				profileRemote.save(profile);
			}
			
			if(authenticateUser(profile, false)) {
				
				JSONObject jsonObject = requestVideoUploadURL(profile, TIMELINE, post, post.getPostsAttachments().get(0)); 
				
				if(jsonObject != null) {
					
					wasSend = uploadVideoData(file, jsonObject, post, jsonObject.get("uploadId").toString(), profile, TIMELINE, post.getPostsAttachments().get(0));
					
					uploadSinglePhoto(profile, TIMELINE, post.getText(), file, jsonObject.get("uploadId").toString(), VIDEO_FILE);
					
					configureSingleVideo(profile, post, file, jsonObject.get("uploadId").toString(), TIMELINE);
					
				} else {
					wasSend = false;
				}
			} else {
				jsonResponseError = loginResponse;
				wasSend = false;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			wasSend = false;
		}
		
		try {
			instagramProxyClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return wasSend;
	}
	
	private JSONObject requestVideoUploadURL(Profile profile, String targetFeed, Post post, PostAttachment postAttachment) throws Exception { 
		
		// Starting the upload video process.
		String videoPostsPath = OpSocialBackApplication.UPLOAD_DIR_VIDEO_POSTS;
		String uploadId = generateUploadId();
		Double videoDuration;
		Integer videoHeight;
		Integer videoWidth;
		
		if(targetFeed.equals(ALBUM)) {
			videoDuration = postAttachment.getDuration();
			videoHeight = postAttachment.getHeight();
			videoWidth = postAttachment.getWidth();
		} else {
			videoDuration = postAttachment.getDuration();
			videoHeight = Integer.valueOf(postAttachment.getHeight());
			videoWidth = Integer.valueOf(postAttachment.getWidth());
		}

		if (videoDuration == null) {
			videoDuration = UtilFunctions.getSecondsFromVideo(new File(videoPostsPath, postAttachment.getFileName()));
			postAttachment.setDuration(videoDuration);
		}

		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		multipartEntityBuilder.addPart("upload_id", new StringBody(uploadId, ContentType.TEXT_PLAIN));
		multipartEntityBuilder.addPart("_csrftoken", new StringBody(instagramProxyClient.getCSRFToken(), ContentType.TEXT_PLAIN));
		multipartEntityBuilder.addPart("_uuid", new StringBody(profile.getInstagramGUID(), ContentType.TEXT_PLAIN));

		if(targetFeed.equals(ALBUM)) {

			multipartEntityBuilder.addPart("is_sidecar", new StringBody("1", ContentType.TEXT_PLAIN));

		} else {

			multipartEntityBuilder.addPart("media_type", new StringBody("2", ContentType.TEXT_PLAIN));
			multipartEntityBuilder.addPart("upload_media_duration_ms", 
					new StringBody(Integer.valueOf(Double.valueOf(Math.ceil(videoDuration * 1000)).intValue()).toString(), ContentType.TEXT_PLAIN));
			multipartEntityBuilder.addPart("upload_media_width", new StringBody(videoWidth.toString(), ContentType.TEXT_PLAIN));
			multipartEntityBuilder.addPart("upload_media_height", new StringBody(videoHeight.toString(), ContentType.TEXT_PLAIN));
		}

		multipartEntityBuilder.setBoundary(profile.getInstagramGUID());
		multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));

		HttpEntity httpEntity = multipartEntityBuilder.build();
		
		Header[] headers = new Header[5];
		headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
		headers[1] = new BasicHeader("Connection", "keep-alive");
		headers[2] = new BasicHeader("Accept", "*/*");
		headers[3] = new BasicHeader("Content-type", "multipart/form-data; boundary=" + profile.getInstagramGUID());
		headers[4] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
		
		CloseableHttpResponse response = (CloseableHttpResponse) 
				instagramProxyClient.requestPOST(UPLOAD_VIDEO_URL, httpEntity, headers);
		
		sendVideoMessage = extractJSONResponse(response);

		JSONParser parser = new JSONParser();
		Object uploadResponse = parser.parse(sendVideoMessage);
		JSONObject uploadResponseObject = (JSONObject) uploadResponse;
		if (((String) uploadResponseObject.get("status")).equals("ok")) { //Upload Video Initiation SuccessFul
			
			JSONObject jsonObject = getVideoUploadUrl(uploadResponseObject);
			jsonObject.put("uploadId", uploadId);
			
			return jsonObject;
		} else {
			jsonResponseError = sendVideoMessage;
			return null;
		}
	}
	
	private Boolean uploadVideoData(File file, JSONObject videoUploadURL, Post post, String uploadId, Profile profile, String targetFeed, PostAttachment postAttachment) throws Exception {
		
		Long videoSize;
		String videoExtension;
		
		if(targetFeed.equals(ALBUM)) {
			videoSize = postAttachment.getSize();
			videoExtension = postAttachment.getExtension();
		} else {
			videoSize = postAttachment.getSize();
			videoExtension = postAttachment.getExtension();
		}
		
		// To support video uploads to albums, we MUST fake-inject the
        // "sessionid" cookie from "i.instagram" into our "upload.instagram"
        // request, otherwise the server will reply with a "StagedUpload not
        // found" error when the final chunk has been uploaded.
		String sessionIDCookie = null;
		if(targetFeed.equals(ALBUM)) {
			
			for(Cookie cookie : instagramProxyClient.getCookies()) {
				if(cookie.getName().equals("sessionid") && cookie.getDomain().equals("i.instagram.com")) {
					sessionIDCookie = cookie.getValue();
					break;
				}
			}
		}
		
		String uploadUrl = videoUploadURL.get("url").toString();
		String job = videoUploadURL.get("job").toString();
		
		Integer requestSize = Double.valueOf(Math.floor(videoSize.doubleValue())).intValue() / 4;
		
		Integer lastRequestExtra = videoSize.intValue() - (requestSize * 4);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));
		for (int i = 0; i <= 3; i++) {
			
			Integer rangeStart = i * requestSize;
			Integer rangeEnd = (i + 1) * requestSize + (i == 3 ? lastRequestExtra : 0);
			int length = Math.min(requestSize, videoSize.intValue() - i + 1) + (i == 3 ? lastRequestExtra : 0);
			
			Header[] headersVideoUpload = new Header[11];
			headersVideoUpload[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headersVideoUpload[1] = new BasicHeader("Connection", "keep-alive");
			headersVideoUpload[2] = new BasicHeader("Accept", "*/*");
			headersVideoUpload[3] = new BasicHeader("Cookie2", "$Version=1");
			headersVideoUpload[4] = new BasicHeader("Accept-Encoding", "gzip, deflate");
			headersVideoUpload[5] = new BasicHeader("Content-type", "application/octet-stream");
			headersVideoUpload[6] = new BasicHeader("Session-ID", uploadId);
			headersVideoUpload[7] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headersVideoUpload[8] = new BasicHeader("Content-Disposition", "attachment; filename=\"video." + getVideoExtension(videoExtension) + "\"");
			headersVideoUpload[9] = new BasicHeader("Content-Range", "bytes " + rangeStart + "-" + String.valueOf(rangeEnd - 1) + "/" + videoSize);
			headersVideoUpload[10] = new BasicHeader("job", job);
			
			// When uploading videos to albums, we must fake-inject the
            // "sessionid" cookie (the official app fake-injects it too).
			if(targetFeed.equals(ALBUM) && sessionIDCookie != null) {
				
				// We'll add it with the default options ("single use") so
                // that the fake cookie is only added to THIS request.
				BasicClientCookie cookie = new BasicClientCookie("sessionid", sessionIDCookie);
				cookie.setDomain("upload.instagram.com");
				cookie.setPath("/api/v1/stage/video/");
				cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "true");
				
				instagramProxyClient.addCookie(cookie);
			}	
			
			byte[] bytes = new byte[length];
		    bis.read(bytes);
		    
			ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bytes); 
			
			CloseableHttpResponse responseVideoUpload = (CloseableHttpResponse) 
					instagramProxyClient.requestPOST(uploadUrl, byteArrayEntity, headersVideoUpload);
			
			sendChunkVideoMessage = extractJSONResponse(responseVideoUpload);
		}
		
		JSONParser parser = new JSONParser();
		Object uploadChunkResponse = parser.parse(sendChunkVideoMessage);
		JSONObject uploadChunkResponseObject = (JSONObject) uploadChunkResponse;
		if (((String) uploadChunkResponseObject.get("status")).equals("ok")) { //Upload Video SuccessFul
			return true;
		} else {
			jsonResponseError = sendChunkVideoMessage;
			return false;
		}
	}
	
	public Boolean uploadTimelineAlbum(Profile profile, Post post) {
		
		Boolean wasSend = false;
		String imagePostsPath = OpSocialBackApplication.UPLOAD_DIR_IMAGES_POSTS;
		String videoPostsPath = OpSocialBackApplication.UPLOAD_DIR_VIDEO_POSTS;

		try {

			// Verificação de novos parâmetros em nova versão do APP do Instagram.
			if(profile.getInstagramDeviceId() == null || profile.getInstagramPhoneId() == null) {
				
				profile.setInstagramDeviceId("android-" + UtilFunctions.generateMD5Hash(profile.getScreenName() + profile.getInstagramPassword()));
				profile.setInstagramPhoneId(UUID.randomUUID().toString());
				
				// Little work around to make the user connect a different device, waiting for the verification on mobile phone.
				if(profile.getInstagramDevice() == null) {
					
					MaintenanceInstagramDeviceRemote instagramDeviceRemote = (MaintenanceInstagramDeviceRemote) 
							RecoverMaintenance.recoverMaintenance("InstagramDevice");
					
					String instagramGUID;
					
					instagramGUID = UUID.randomUUID().toString();
					instagramDevice = instagramDeviceRemote.getById(instagramDeviceRemote.getInstagramDeviceLessUsedId());
					
					profile.setInstagramGUID(instagramGUID);
					profile.setInstagramDevice(instagramDevice);
				}
				
				MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				
				profileRemote.save(profile);
			}
			
			if(authenticateUser(profile, false)) {

				// Perform all media file uploads.
				for(PostAttachment postAttachment : post.getPostsAttachments()) {

					if(postAttachment.getFileType().equals(PostAttachment.PHOTO)) {
						
						new S3Integration().downloadS3Object(S3Integration.WOOPSOCIAL_BUCKET_NAME, 
								S3Integration.WOOPSOCIAL_IMAGES_POSTS_FOLDER, postAttachment.getFileName(), imagePostsPath);
						
						String uploadId = uploadPhotoData(profile, ALBUM, new File(imagePostsPath, postAttachment.getFileName()), null, PHOTO_FILE);
						
						postAttachment.setUploadId(uploadId);
						
					} else if(postAttachment.getFileType().equals(PostAttachment.VIDEO)) {
						
						new S3Integration().downloadS3Object(S3Integration.WOOPSOCIAL_BUCKET_NAME, 
								S3Integration.WOOPSOCIAL_VIDEOS_POSTS_FOLDER, postAttachment.getFileName(), videoPostsPath);
						
						File file = new File(videoPostsPath, postAttachment.getFileName());
						
						JSONObject jsonObject = requestVideoUploadURL(profile, ALBUM, post, postAttachment); 
						postAttachment.setUploadId(jsonObject.get("uploadId").toString());
						
						if(jsonObject != null) {
							
							if(uploadVideoData(file, jsonObject, post, jsonObject.get("uploadId").toString(), profile, ALBUM, postAttachment)) {
								uploadPhotoData(profile, ALBUM, createVideoIcon(file), jsonObject.get("uploadId").toString(), VIDEO_FILE);
							} else {
								wasSend = false;
							}	
						}
					}
				}
				
				// Configure the uploaded album and attach it to our timeline.
				wasSend = configureTimelineAlbum(profile, post);
				
			} else {
				jsonResponseError = loginResponse;
				wasSend = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			wasSend = false;
		}
		
		try {
			instagramProxyClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return wasSend;
	}

	private Boolean configureSingleVideo(Profile profile, Post post, File file, String uploadId, String targetFeed) throws Exception {
		
		String data;
		String sig;
		String endpoint;
		
		if(targetFeed.equals(TIMELINE)) {
			endpoint = TIMELINE_CONFIGURE_VIDEO_URL;
		} else {
			endpoint = STORY_CONFIGURE_VIDEO_URL;
		}
		
		String caption = post.getText() == null ? "" : post.getText();
		
		JsonObject mainObject = new JsonObject();
		mainObject.addProperty("video_result", "deprecated");
		mainObject.addProperty("upload_id", uploadId);
		mainObject.addProperty("poster_frame_index", 0);
		mainObject.addProperty("length", new BigDecimal(post.getPostsAttachments().get(0).getDuration()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
		mainObject.addProperty("audio_muted", false);
		mainObject.addProperty("filter_type", 0);
		mainObject.addProperty("source_type", 4);
		
		JsonObject deviceObject = new JsonObject();
		deviceObject.addProperty("manufacturer", instagramDevice.getManufacturer());
		deviceObject.addProperty("model", instagramDevice.getModel());
		deviceObject.addProperty("android_version", instagramDevice.getAndroidVersion().toString());
		deviceObject.addProperty("android_release", instagramDevice.getAndroidRelease());
		
		JsonObject extraObject = new JsonObject();
		extraObject.addProperty("source_width", post.getPostsAttachments().get(0).getWidth());
		extraObject.addProperty("source_height", post.getPostsAttachments().get(0).getHeight());
		
		mainObject.add("device", deviceObject);
		mainObject.add("extra", extraObject);
		
		mainObject.addProperty("_csrftoken", instagramProxyClient.getCSRFToken());
		mainObject.addProperty("_uid", instagramProxyClient.getUserId());
		mainObject.addProperty("_uuid", profile.getInstagramGUID());
		
		if(targetFeed.equals(STORY)) {
			mainObject.addProperty("configure_mode", 1); // 1 - REEL_SHARE, 2 - DIRECT_STORY_SHARE
			mainObject.addProperty("story_media_creation_date", Long.valueOf(new Date().getTime() / 1000L) - ThreadLocalRandom.current().nextLong(10, 21));
			mainObject.addProperty("client_shared_at", Long.valueOf(new Date().getTime() / 1000L) - ThreadLocalRandom.current().nextLong(3, 11));
			mainObject.addProperty("client_timestamp", new Date().getTime() / 1000L); 
		}
		
		if(targetFeed.equals(STORY)) {
			mainObject.addProperty("story_media_creation_date", new Date().getTime() / 1000L);
		}
		
		mainObject.addProperty("caption", caption);	
		
		String jsonToSend = mainObject.toString();
		
		sig = UtilFunctions.generateSignature(jsonToSend, instagramDevice.getIgSigKey());

		data = "ig_sig_key_version=4&signed_body=" + sig + "." + URLEncoder.encode(jsonToSend, "UTF-8");
		
		//Configure Photo
		configVideoResponse = requestPostDefault(endpoint, data, deviceUserAgent);
		
		JSONParser parser = new JSONParser();
		Object configResponse = parser.parse(configVideoResponse);
		JSONObject configResponseObject = (JSONObject) configResponse;
		if (((String) configResponseObject.get("status")).equals("ok")) { //Login SuccessFul
			return true;
		} else {
			jsonResponseError = configVideoResponse;
			return false;
		}	
	}
	
	public Boolean configureTimelineAlbum(Profile profile, Post post) throws Exception {
		
		String data;
		String sig;
		
		String caption = post.getText() == null ? "" : post.getText();
		
		JsonArray childrenMetadataArray = new JsonArray();
		
		String date = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(new Date());
		
		// Build the album's per-children metadata.
		for(PostAttachment postAttachment : post.getPostsAttachments()) {
			
			if(postAttachment.getFileType().equals(PostAttachment.PHOTO)) {
				
				// Build this item's configuration.
				JsonObject photoConfigObject = new JsonObject();
				photoConfigObject.addProperty("date_time_original", date);
				photoConfigObject.addProperty("scene_type", 1);
				photoConfigObject.addProperty("disable_comments", false);
				photoConfigObject.addProperty("upload_id", postAttachment.getUploadId()); 
				photoConfigObject.addProperty("source_type", 0);
				photoConfigObject.addProperty("scene_capture_type", "standard");
				photoConfigObject.addProperty("date_time_digitized", date);
				photoConfigObject.addProperty("geotag_enabled", false);
				photoConfigObject.addProperty("camera_position", "back");
				
				JsonObject editsObject = new JsonObject();
				editsObject.addProperty("filter_strength", 1);
				editsObject.addProperty("filter_name", "IGNormalFilter");
				
				photoConfigObject.addProperty("0", "edits");
				photoConfigObject.add("1", editsObject);
				
				childrenMetadataArray.add(photoConfigObject);
				
			} else if(postAttachment.getFileType().equals(PostAttachment.VIDEO)) {
				
				// Build this item's configuration.
				JsonObject videoConfigObject = new JsonObject();
				videoConfigObject.addProperty("length", new BigDecimal(postAttachment.getDuration()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
				videoConfigObject.addProperty("date_time_original", date);
				videoConfigObject.addProperty("scene_type", 1);
				videoConfigObject.addProperty("poster_frame_index", 0);
				videoConfigObject.addProperty("trim_type", 0);
				videoConfigObject.addProperty("disable_comments", false);
				videoConfigObject.addProperty("upload_id", postAttachment.getUploadId());
				videoConfigObject.addProperty("source_type", "library");
				videoConfigObject.addProperty("geotag_enabled", false);
				
				JsonObject editsObject = new JsonObject();
				editsObject.addProperty("length", 0);
				editsObject.addProperty("cinema", "unsupported");
				editsObject.addProperty("original_length", 0);
				editsObject.addProperty("source_type", "library");
				editsObject.addProperty("start_time", 0);
				editsObject.addProperty("camera_position", "unknown");
				editsObject.addProperty("trim_type", 0);
				
				videoConfigObject.addProperty("0", "edits");
				videoConfigObject.add("1", editsObject);
				
				childrenMetadataArray.add(videoConfigObject);
			}
		}
		
		JsonObject mainObject = new JsonObject();
		mainObject.addProperty("_csrftoken", instagramProxyClient.getCSRFToken());
		mainObject.addProperty("_uid", instagramProxyClient.getUserId());
		mainObject.addProperty("_uuid", profile.getInstagramGUID());
		mainObject.addProperty("client_sidecar_id", generateUploadId());
		mainObject.addProperty("caption", caption);
		mainObject.add("children_metadata", childrenMetadataArray);
		
		String jsonToSend = mainObject.toString();
		
		sig = UtilFunctions.generateSignature(jsonToSend, instagramDevice.getIgSigKey());

		data = "ig_sig_key_version=4&signed_body=" + sig + "." + URLEncoder.encode(jsonToSend, "UTF-8");
		
		configAlbumResponse = requestPostDefault(TIMELINE_CONFIGURE_ALBUM_URL, data, deviceUserAgent);
		
		JSONParser parser = new JSONParser();
		Object configResponse = parser.parse(configAlbumResponse);
		JSONObject configResponseObject = (JSONObject) configResponse;
		if (((String) configResponseObject.get("status")).equals("ok")) {
			return true;
		} else {
			jsonResponseError = configAlbumResponse;
			return false;
		}
	}

	public Boolean uploadTimelinePhoto(Profile profile, String caption, File file) {
		
		Boolean wasSend = false;
		
		try {
			
			// Verificação de novos parâmetros em nova versão do APP do Instagram.
			if(profile.getInstagramDeviceId() == null || profile.getInstagramPhoneId() == null) {
				
				profile.setInstagramDeviceId("android-" + UtilFunctions.generateMD5Hash(profile.getScreenName() + profile.getInstagramPassword()));
				profile.setInstagramPhoneId(UUID.randomUUID().toString());
				
				// Little work around to make the user connect a different device, waiting for the verification on mobile phone.
				if(profile.getInstagramDevice() == null) {
					
					MaintenanceInstagramDeviceRemote instagramDeviceRemote = (MaintenanceInstagramDeviceRemote) 
							RecoverMaintenance.recoverMaintenance("InstagramDevice");
					
					String instagramGUID;
					
					instagramGUID = UUID.randomUUID().toString();
					instagramDevice = instagramDeviceRemote.getById(instagramDeviceRemote.getInstagramDeviceLessUsedId());
					
					profile.setInstagramGUID(instagramGUID);
					profile.setInstagramDevice(instagramDevice);
				}
				
				MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				
				profileRemote.save(profile);
			}
			
			if(authenticateUser(profile, false)) {
				wasSend = uploadSinglePhoto(profile, TIMELINE, caption, file, null, PHOTO_FILE);
			} else {
				jsonResponseError = loginResponse;
				wasSend = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			wasSend = false;
		}
		
		try {
			instagramProxyClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return wasSend;
	}
	
	private String uploadPhotoData(Profile profile, String targetFeed, File file, String uploadId, String fileType) throws Exception {
		
		if(uploadId == null) {
			uploadId = generateUploadId();
		}
		
		byte[] imageByteArray;
		if(file.getPath().endsWith(".png")) {
			imageByteArray = Files.readAllBytes(convertPNGToJPEG(file).toPath());
		} else {
			imageByteArray = Files.readAllBytes(file.toPath());
		}
		
		Long dateInMilliseconds = new Date().getTime();
		String fileName = "pending_media_" + dateInMilliseconds.toString() +  ".jpg";
		
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		
		multipartEntityBuilder.addPart("upload_id", new StringBody(uploadId, ContentType.TEXT_PLAIN));
		multipartEntityBuilder.addPart("_uuid", new StringBody(profile.getInstagramGUID(), ContentType.TEXT_PLAIN));
		multipartEntityBuilder.addPart("_csrftoken", new StringBody(instagramProxyClient.getCSRFToken(), ContentType.TEXT_PLAIN));
		
		multipartEntityBuilder.addPart("image_compression", 
				new StringBody("{\"lib_name\":\"jt\",\"lib_version\":\"1.3.0\",\"quality\":\"87\"}", ContentType.APPLICATION_JSON));
		
		multipartEntityBuilder.addBinaryBody("photo", imageByteArray, ContentType.APPLICATION_OCTET_STREAM, fileName);
		
		if(targetFeed.equals(ALBUM)) {
			
			multipartEntityBuilder.addPart("is_sidecar", new StringBody("1", ContentType.TEXT_PLAIN));
			
			if(fileType.equals(VIDEO_FILE)) {
				multipartEntityBuilder.addPart("media_type", new StringBody("2", ContentType.TEXT_PLAIN));
			}
		}
		
		multipartEntityBuilder.setBoundary(profile.getInstagramGUID());
		multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));
		
		HttpEntity httpEntity = multipartEntityBuilder.build();
		
		Header[] headers = new Header[10];
		headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
		headers[1] = new BasicHeader("Connection", "keep-alive");
		headers[2] = new BasicHeader("Accept", "*/*");
		headers[3] = new BasicHeader("Accept-Encoding", "gzip, deflate");
		headers[4] = new BasicHeader("X-IG-Capabilities", X_IG_CAPABILITIES);
		headers[5] = new BasicHeader("X-IG-Connection-Type", X_IG_CONNECTION_TYPE);
		headers[6] = new BasicHeader("X-IG-Connection-Speed", String.valueOf(ThreadLocalRandom.current().nextLong(1000, 3701)) + "kbps");
		headers[7] = new BasicHeader("X-FB-HTTP-Engine", X_FB_HTTP_ENGINE);
		headers[8] = new BasicHeader("Content-type", "multipart/form-data; boundary=" + profile.getInstagramGUID());
		headers[9] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
		
		CloseableHttpResponse response = (CloseableHttpResponse) 
				instagramProxyClient.requestPOST(UPLOAD_PHOTO_URL, httpEntity, headers);
		
		sendImageResponse = extractJSONResponse(response);
		
		JSONParser parser = new JSONParser();
		Object uploadResponse = parser.parse(sendImageResponse);
		JSONObject uploadResponseObject = (JSONObject) uploadResponse;
		if (((String) uploadResponseObject.get("status")).equals("ok")) {
			return (String) uploadResponseObject.get("upload_id");
		} else {
			jsonResponseError = sendImageResponse;
			return null;
		}
	}
	
	private Boolean uploadSinglePhoto(Profile profile, String targetFeed, String caption, File file, String uploadId, String fileType) throws Exception {
		
		if(fileType.equals(VIDEO_FILE)) {
			file = createVideoIcon(file);
		}

		uploadId = uploadPhotoData(profile, targetFeed, file, uploadId, fileType);

		if(uploadId != null) {
			return configurePhoto(profile, targetFeed, caption, file, uploadId);
		} else {
			return false;
		}
	}
	
	private Boolean configurePhoto(Profile profile, String targetFeed, String caption, File file, String uploadId) throws Exception {

		String data;
		String sig;
		String endpoint;
		
		if(targetFeed.equals(TIMELINE)) {
			endpoint = TIMELINE_CONFIGURE_IMAGE_URL;
		} else {
			endpoint = STORY_CONFIGURE_IMAGE_URL;  
		}
		
		caption = caption == null ? "" : caption;
		
		BufferedImage bimg = ImageIO.read(file);
		Integer photoWidth = bimg.getWidth();
		Integer photoHeight = bimg.getHeight();
		
		JsonObject mainObject = new JsonObject();
		mainObject.addProperty("_csrftoken", instagramProxyClient.getCSRFToken());
		
		mainObject.addProperty("_uid", instagramProxyClient.getUserId());
		mainObject.addProperty("_uuid", profile.getInstagramGUID());
		
		if(targetFeed.equals(TIMELINE)) {
			
			mainObject.addProperty("caption", caption);
			mainObject.addProperty("source_type", 4);
			mainObject.addProperty("media_folder", "Camera");
			mainObject.addProperty("upload_id", uploadId);
			
		} else if(targetFeed.equals(STORY)) {
			
			mainObject.addProperty("client_shared_at", new Date().getTime() / 1000L);
			mainObject.addProperty("source_type", 3);
			mainObject.addProperty("configure_mode", 1);
			mainObject.addProperty("client_timestamp", new Date().getTime() / 1000L);
			mainObject.addProperty("upload_id", uploadId);
		}
		
		JsonObject deviceObject = new JsonObject();
		deviceObject.addProperty("manufacturer", instagramDevice.getManufacturer());
		deviceObject.addProperty("model", instagramDevice.getModel());
		deviceObject.addProperty("android_version", instagramDevice.getAndroidVersion().toString());
		deviceObject.addProperty("android_release", instagramDevice.getAndroidRelease());
		
		JsonArray cropOriginalSize = new JsonArray();
		cropOriginalSize.add(new JsonPrimitive(photoWidth));
		cropOriginalSize.add(new JsonPrimitive(photoHeight));
		
		JsonArray cropCenter = new JsonArray();
		cropCenter.add(new JsonPrimitive(0.0F));
		cropCenter.add(new JsonPrimitive(-0.0F));
		
		JsonObject editObject = new JsonObject();
		editObject.add("crop_original_size", cropOriginalSize);
		editObject.add("crop_center", cropCenter);
		editObject.addProperty("crop_zoom", 1);
		
		JsonObject extraObject = new JsonObject();
		extraObject.addProperty("source_width", photoWidth);
		extraObject.addProperty("source_height", photoHeight);
		
		mainObject.add("device", deviceObject);
		mainObject.add("edits", editObject);
		mainObject.add("extra", extraObject);
		
		String jsonToSend = mainObject.toString();
		
		sig = UtilFunctions.generateSignature(jsonToSend, instagramDevice.getIgSigKey());

		data = "ig_sig_key_version=4&signed_body=" + sig + "." + URLEncoder.encode(jsonToSend, "UTF-8");
		
		//Configure Photo
		configImageResponse = requestPostDefault(endpoint, data, deviceUserAgent);
		
		JSONParser parser = new JSONParser();
		Object configResponse = parser.parse(configImageResponse);
		JSONObject configResponseObject = (JSONObject) configResponse;

		if (((String) configResponseObject.get("status")).equals("ok")) { //Login SuccessFul
			
			this.uploadedPostId = ((Long) ((JSONObject) configResponseObject.get("media")).get("pk")).toString();
			
			return true;
		} else {
			jsonResponseError = configImageResponse;
			return false;
		}	
	}
	
	private File convertPNGToJPEG(File filePNG) throws Exception {
		
		StringBuilder fileJPGName = new StringBuilder(filePNG.getPath());
		fileJPGName.replace(filePNG.getPath().lastIndexOf(".png"), filePNG.getPath().lastIndexOf(".png") + 4, ".jpg");
		
		File fileJPEG = new File(fileJPGName.toString());
		
		//read image file
		BufferedImage bufferedImage = ImageIO.read(filePNG);

		// create a blank, RGB, same width and height, and a black background
		BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
		bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.BLACK, null);

		// write to jpeg file
		ImageIO.write(newBufferedImage, "jpg", fileJPEG);
		
		return fileJPEG;
	}
	
	public String extractSendImageErrorMessage() {
		
		String errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.instagram.sendimageerror");
		
		try {
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(jsonResponseError);
			JSONObject jsonObject = (JSONObject) obj;
			if (((String) jsonObject.get("status")).equals("fail")) {
				errorMessage = translateSendImageErrorMessage((String) jsonObject.get("message")); 
			}
			
		} catch (Exception e) {
			if(statusCodeOfLastRequest != HttpStatusCodes.STATUS_CODE_OK) {
				errorMessage = getSendImageErrorMessageHTTPCode();
			}
			e.printStackTrace();
		}
		
		return errorMessage;
	}
	
	public String extractLoginErrorMessage() {
		
		String errorMessage = "";
		
		try {
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(loginResponse);
			JSONObject jsonObject = (JSONObject) obj;
			if (((String) jsonObject.get("status")).equals("fail")) {
				errorMessage = translateErrorMessage((String) jsonObject.get("message")); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return errorMessage;
	}
	
	public String getSendImageErrorMessageHTTPCode() {
		
		String errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.instagram.sendimageerror");
		
		if(statusCodeOfLastRequest.equals(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY)) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.instagram.badgatewayerror");
		} else if(statusCodeOfLastRequest.equals(HttpStatusCodes.STATUS_CODE_SERVER_ERROR)) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.instagram.internalservererror");
		} else if(statusCodeOfLastRequest.equals(HttpStatusCodes.STATUS_CODE_SERVICE_UNAVAILABLE)) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.instagram.serviceunavailableerror");
		}
		
		return errorMessage;
	}
	
	private String translateSendImageErrorMessage(String message) {
		
		String errorMessage = "";
		
		if(message.equals(CHECKPOINT_REQUIRED)) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.checkpointrequired");
		} else if(message.equals(OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.passwordincorrect", Locale.US)) 
				|| message.equals(OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.passwordincorrectaltversion", Locale.US))) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.passwordincorrect");
		} else if(message.equals(OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.usernametologin", Locale.US))) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.usernametologin");
		} else if(message.equals(OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.usernamenotexist", Locale.US))) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.usernamenotexist");
		} else if(message.equals(OpSocialBackApplication.messageByLocaleService.getMessage("post.instagram.notallowedaspectratio", Locale.US))) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.instagram.notallowedaspectratio");
		} else if(message.equals(OpSocialBackApplication.messageByLocaleService.getMessage("post.instagram.notrightformat", Locale.US))) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.instagram.notrightformat");
		} else {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.instagram.sendimageerror");
		}
		
		return errorMessage;
		
	}
	
	private String translateErrorMessage(String message) {
		
		String errorMessage = "";
		
		if(message.equals(CHECKPOINT_REQUIRED)) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.checkpointrequired");
		} else if(message.equals(OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.passwordincorrect", Locale.US)) 
				|| message.equals(OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.passwordincorrectaltversion", Locale.US)) 
				|| message.equals(OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.passwordincorrect", new Locale("pt", "BR")))) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.passwordincorrect");
		} else if(message.equals(OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.usernametologin", Locale.US))) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.usernametologin");
		} else if(message.equals(OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.usernamenotexist", Locale.US))) {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.usernamenotexist");
		} else {
			errorMessage = OpSocialBackApplication.messageByLocaleService.getMessage("profile.intagram.reportscheduleerror");
		}
		
		return errorMessage;
	}
	
	public void deactivateProfileScheduling(Long idProfile) {
		
		try {
			
			if(!isAccountAuthenticated()) {	
				
				MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				Profile profile = profileRemote.getById(idProfile);
				
				profile.setIsSchedulingAuthenticated(Profile.INACTIVE);
				profile.setReconnectProfileCount(profile.getReconnectProfileCount() + 1L);
				
				if(extractSendImageErrorMessage().equals(OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.checkpointrequired"))) {
					profile.setAuthenticationProfileCount(profile.getAuthenticationProfileCount() + 1L); 
				}
				
				profileRemote.save(profile);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean tryToPostAgain() {
		
		Boolean tryAgain = false;
		
		if(statusCode != null) {
			tryAgain = !statusCode.equals(HttpStatus.SC_OK) ? true : false; 
		}
		
		return tryAgain;
	}
	
	private String extractJSONResponse(CloseableHttpResponse response) throws Exception {
		
		String jsonResponse = "";
		
		if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
			
		}

		HttpEntity entity2 = response.getEntity();

		BufferedReader br = new BufferedReader(new InputStreamReader(entity2.getContent(), Charset.forName("UTF-8")));

		String line;
		while ((line = br.readLine())!= null) {
			jsonResponse += line;
		}
		
		EntityUtils.consume(entity2);

		response.close();
		
		return jsonResponse;
	}
	
	private JSONObject getVideoUploadUrl(JSONObject jsonObject) {
		
		JSONArray videoUploadUrls = (JSONArray) jsonObject.get("video_upload_urls");
		
		JSONObject videoUploadUrl = (JSONObject)videoUploadUrls.get(3);
		
		return videoUploadUrl;
	}
	
	private File createVideoIcon(File videoFile) throws Exception {
		
		String uploadDirThumbnails = OpSocialBackApplication.UPLOAD_DIR_THUMBNAILS;
		String previewVideo = uploadDirThumbnails + RandomStringUtils.randomAlphanumeric(12) + ".jpg";
		String previewVideoThumbnail = RandomStringUtils.randomAlphanumeric(12) + "_thumb.jpg";

		//capture video preview
		String command = "ffmpeg -i " + videoFile.toString() + " -f mjpeg -ss 00:00:01 -vframes 1 " + previewVideo;
		Process p = Runtime.getRuntime().exec(command);
		p.waitFor();
		p.getInputStream().close();

		BufferedImage imgThumbnail = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		imgThumbnail.createGraphics().drawImage(ImageIO.read(new File(previewVideo)).getScaledInstance(100, 100, Image.SCALE_SMOOTH),0,0,null);
		ImageIO.write(imgThumbnail, "jpg", new File(uploadDirThumbnails, previewVideoThumbnail));
		
		new S3Integration().getAmazonS3Client().putObject(
				  S3Integration.WOOPSOCIAL_BUCKET_NAME, 
				  S3Integration.WOOPSOCIAL_IMAGES_THUMBNAILS + previewVideoThumbnail, 
				  new File(uploadDirThumbnails, previewVideoThumbnail));

		return new File(uploadDirThumbnails, previewVideoThumbnail);
	}
	
	private Boolean isLoggedIn() {
		
		boolean isLoggedIn = false;
		
		if(instagramProxyClient.hasCookies()) {
			for(Cookie cookie : instagramProxyClient.getCookies()) {
				if(cookie.getName().equals("csrftoken") 
						&& cookie.getDomain().equals("i.instagram.com")
		                && cookie.getExpiryDate().getTime() > new Date().getTime()) {
					isLoggedIn = true;
					break;
				}
			}
		}
		
		return isLoggedIn;
	}
	
	public String generateUploadId() {
        return Long.toString((new Date()).getTime());
    }

	public String getLoginResponse() {
		return loginResponse;
	}

	public void setLoginResponse(String loginResponse) {
		this.loginResponse = loginResponse;
	}

	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	public Boolean isAccountAuthenticated() {
		return isAccountAuthenticated;
	}

	public void setIsAccountAuthenticated(Boolean isAccountAuthenticated) {
		this.isAccountAuthenticated = isAccountAuthenticated;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	private String getVideoExtension(String contentType) {
		
		String videoExtension = "";
		
		if(contentType.equals(VIDEO_AVI)) {
			videoExtension = "avi";
		} else if(contentType.equals(VIDEO_ASF)) {
			videoExtension = "asf";
		} else if(contentType.equals(VIDEO_DV)) {
			videoExtension = "dv";
		} else if(contentType.equals(VIDEO_MOD)) {
			videoExtension = "mod";
		} else if(contentType.equals(VIDEO_MOV)) {
			videoExtension = "mov";
		} else if(contentType.equals(VIDEO_MP4)) {
			videoExtension = "mp4";
		} else if(contentType.equals(VIDEO_MPEG)) {
			videoExtension = "mpeg";
		} else if(contentType.equals(VIDEO_XFLV)) {
			videoExtension = "flv";
		} else if(contentType.equals(VIDEO_3GP)) {
			videoExtension = "3gp";
		} else if(contentType.equals(VIDEO_MKV)) {
			videoExtension = "mkv";
		}
		
		return videoExtension;
	}
	
	public String getBusinessInformation() {
		
		String sonuc = "";
		
		try {
			
			if(instagramProxyClient == null) {
				instagramProxyClient = new InstagramProxyClient(cookies, proxyRegion, port);
			}
	
			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(BUSINESS_INSIGHTS_URL)).
					addParameter("show_promotions_in_landing_page", "true");
		    URI uri = uriBuilder.build();
			
			CloseableHttpResponse response = (CloseableHttpResponse) instagramProxyClient.requestGET(uri, headers);
			
			statusCode = response.getStatusLine().getStatusCode();
			sonuc = extractJSONResponse(response);
			statusCodeOfLastRequest = response.getStatusLine().getStatusCode();
			
		} catch (Exception e) {
			e.printStackTrace();
			return sonuc;
		}
		
		return sonuc;
	}
	
	public String getBusinessID() {
		
		String sonuc = "";
		
		try {
			
			if(instagramProxyClient == null) {
				instagramProxyClient = new InstagramProxyClient(cookies, proxyRegion, port);
			}
	
			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(BUSINESS_INSIGHTS_URL)).
					addParameter("show_promotions_in_landing_page", "true");
		    URI uri = uriBuilder.build();
			
			CloseableHttpResponse response = (CloseableHttpResponse) instagramProxyClient.requestGET(uri, headers);
			
			statusCode = response.getStatusLine().getStatusCode();
			sonuc = extractJSONResponse(response);
			statusCodeOfLastRequest = response.getStatusLine().getStatusCode();
			
		} catch (Exception e) {
			e.printStackTrace();
			return sonuc;
		}
		
		return sonuc;
	}
	
	public FriendshipStatus unfollow(Profile profile, String userId) {
		
		FriendshipStatus friendshipStatus = new FriendshipStatus();
		
		try {
			
			// Verificação de novos parâmetros em nova versão do APP do Instagram.
			if(profile.getInstagramDeviceId() == null || profile.getInstagramPhoneId() == null) {
				
				profile.setInstagramDeviceId("android-" + UtilFunctions.generateMD5Hash(profile.getScreenName() + profile.getInstagramPassword()));
				profile.setInstagramPhoneId(UUID.randomUUID().toString());
				
				// Little work around to make the user connect a different device, waiting for the verification on mobile phone.
				if(profile.getInstagramDevice() == null) {
					
					MaintenanceInstagramDeviceRemote instagramDeviceRemote = (MaintenanceInstagramDeviceRemote) 
							RecoverMaintenance.recoverMaintenance("InstagramDevice");
					
					String instagramGUID;
					
					instagramGUID = UUID.randomUUID().toString();
					instagramDevice = instagramDeviceRemote.getById(instagramDeviceRemote.getInstagramDeviceLessUsedId());
					
					profile.setInstagramGUID(instagramGUID);
					profile.setInstagramDevice(instagramDevice);
				}
				
				MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				
				profileRemote.save(profile);
			}
			
			if(authenticateUser(profile, false)) {
				
				JsonObject mainObject = new JsonObject();
				mainObject.addProperty("_uuid", profile.getInstagramGUID());
				mainObject.addProperty("_uid", instagramProxyClient.getUserId());
				mainObject.addProperty("_csrftoken", instagramProxyClient.getCSRFToken());
				mainObject.addProperty("user_id", userId);
				mainObject.addProperty("radio_type", "wifi-none");
				
				String jsonToSend = mainObject.toString();
				
				String sig = UtilFunctions.generateSignature(jsonToSend, instagramDevice.getIgSigKey());

				String data = "ig_sig_key_version=4&signed_body=" + sig + "." + URLEncoder.encode(jsonToSend, "UTF-8");
				
				followResponse = requestPostDefault(API_URL + "friendships/destroy/" + userId + "/", data, deviceUserAgent);
				
				JSONParser parser = new JSONParser();
				Object uploadResponse = parser.parse(followResponse);
				JSONObject uploadResponseObject = (JSONObject) uploadResponse;
				if (((String) uploadResponseObject.get("status")).equals("ok")) { //Get Followers succesful
					
					Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
					friendshipStatus = gson.fromJson(uploadResponseObject.get("friendship_status").toString(), FriendshipStatus.class);
					
				} else {
					jsonResponseError = followResponse;
				}
				
			} else {
				jsonResponseError = loginResponse;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			instagramProxyClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return friendshipStatus;
	}
	
	public FriendshipStatus follow(Profile profile, String userId) {
		
		FriendshipStatus friendshipStatus = new FriendshipStatus();
		
		try {
			
			// Verificação de novos parâmetros em nova versão do APP do Instagram.
			if(profile.getInstagramDeviceId() == null || profile.getInstagramPhoneId() == null) {
				
				profile.setInstagramDeviceId("android-" + UtilFunctions.generateMD5Hash(profile.getScreenName() + profile.getInstagramPassword()));
				profile.setInstagramPhoneId(UUID.randomUUID().toString());
				
				// Little work around to make the user connect a different device, waiting for the verification on mobile phone.
				if(profile.getInstagramDevice() == null) {
					
					MaintenanceInstagramDeviceRemote instagramDeviceRemote = (MaintenanceInstagramDeviceRemote) 
							RecoverMaintenance.recoverMaintenance("InstagramDevice");
					
					String instagramGUID;
					
					instagramGUID = UUID.randomUUID().toString();
					instagramDevice = instagramDeviceRemote.getById(instagramDeviceRemote.getInstagramDeviceLessUsedId());
					
					profile.setInstagramGUID(instagramGUID);
					profile.setInstagramDevice(instagramDevice);
				}
				
				MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				
				profileRemote.save(profile);
			}
			
			if(authenticateUser(profile, false)) {
				
				JsonObject mainObject = new JsonObject();
				mainObject.addProperty("_uuid", profile.getInstagramGUID());
				mainObject.addProperty("_uid", instagramProxyClient.getUserId());
				mainObject.addProperty("_csrftoken", instagramProxyClient.getCSRFToken());
				mainObject.addProperty("user_id", userId);
				mainObject.addProperty("radio_type", "wifi-none");
				
				String jsonToSend = mainObject.toString();
				
				String sig = UtilFunctions.generateSignature(jsonToSend, instagramDevice.getIgSigKey());

				String data = "ig_sig_key_version=4&signed_body=" + sig + "." + URLEncoder.encode(jsonToSend, "UTF-8");
				
				followResponse = requestPostDefault(API_URL + "friendships/create/" + userId + "/", data, deviceUserAgent);
				
				JSONParser parser = new JSONParser();
				Object uploadResponse = parser.parse(followResponse);
				JSONObject uploadResponseObject = (JSONObject) uploadResponse;
				if (((String) uploadResponseObject.get("status")).equals("ok")) { //Get Followers succesful
					
					Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
					friendshipStatus = gson.fromJson(uploadResponseObject.get("friendship_status").toString(), FriendshipStatus.class);
					
				} else {
					jsonResponseError = followResponse;
				}
				
			} else {
				jsonResponseError = loginResponse;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			instagramProxyClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return friendshipStatus;
	}
	
	public FollowerFeed getFollowers(Profile profile, String maxId) {
		
		FollowerFeed followerFeed = new FollowerFeed();
		
		try {
			
			// Verificação de novos parâmetros em nova versão do APP do Instagram.
			if(profile.getInstagramDeviceId() == null || profile.getInstagramPhoneId() == null) {
				
				profile.setInstagramDeviceId("android-" + UtilFunctions.generateMD5Hash(profile.getScreenName() + profile.getInstagramPassword()));
				profile.setInstagramPhoneId(UUID.randomUUID().toString());
				
				// Little work around to make the user connect a different device, waiting for the verification on mobile phone.
				if(profile.getInstagramDevice() == null) {
					
					MaintenanceInstagramDeviceRemote instagramDeviceRemote = (MaintenanceInstagramDeviceRemote) 
							RecoverMaintenance.recoverMaintenance("InstagramDevice");
					
					String instagramGUID;
					
					instagramGUID = UUID.randomUUID().toString();
					instagramDevice = instagramDeviceRemote.getById(instagramDeviceRemote.getInstagramDeviceLessUsedId());
					
					profile.setInstagramGUID(instagramGUID);
					profile.setInstagramDevice(instagramDevice);
				}
				
				MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				
				profileRemote.save(profile);
			}
			
			if(authenticateUser(profile, false)) {
				
				Header[] headers = new Header[4];
				headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
				headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
				headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
				headers[3] = new BasicHeader("X-IG-Capabilities", X_IG_CAPABILITIES);
				
				URIBuilder uriBuilder = new URIBuilder(new URI(API_URL + "friendships/" + profile.getNetworkId() + "/followers/")).
						addParameter("rank_token", rankToken);
				
				if(maxId != null) {
					uriBuilder.addParameter("max_id", maxId);
				}
				
			    URI uri = uriBuilder.build();
				
				CloseableHttpResponse response = (CloseableHttpResponse) instagramProxyClient.requestGET(uri, headers);
				
				statusCode = response.getStatusLine().getStatusCode();
				followersResponse = extractJSONResponse(response);
				statusCodeOfLastRequest = response.getStatusLine().getStatusCode();

				JSONParser parser = new JSONParser();
				Object uploadResponse = parser.parse(followersResponse);
				JSONObject uploadResponseObject = (JSONObject) uploadResponse;
				if (((String) uploadResponseObject.get("status")).equals("ok")) { //Get Followers succesful
					
					Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
					followerFeed = gson.fromJson(followersResponse, FollowerFeed.class);
					
				} else {
					jsonResponseError = followersResponse;
				}
				
			} else {
				jsonResponseError = loginResponse;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			instagramProxyClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return followerFeed;
	}
	
	public String getBusinessDetailInformation() {
		
		String sonuc = "";
		
		try {
			
			if(instagramProxyClient == null) {
				instagramProxyClient = new InstagramProxyClient(cookies, proxyRegion, port);
			}
	
			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(BUSINESS_INSIGHTS_DETAIL_URL)).
					addParameter("show_promotions_in_landing_page", "true");
		    URI uri = uriBuilder.build();
			
			CloseableHttpResponse response = (CloseableHttpResponse) instagramProxyClient.requestGET(uri, headers);
			
			statusCode = response.getStatusLine().getStatusCode();
			sonuc = extractJSONResponse(response);
			statusCodeOfLastRequest = response.getStatusLine().getStatusCode();
			
		} catch (Exception e) {
			e.printStackTrace();
			return sonuc;
		}		
		
		return sonuc;
	}
	
	public String getBusinessMediaInformation() {
		
		String sonuc = "";
		
		try {
			
			if(instagramProxyClient == null) {
				instagramProxyClient = new InstagramProxyClient(cookies, proxyRegion, port);
			}
	
			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(BUSINESS_INSIGHTS_MEDIA_URL)).
					addParameter("ig_sig_key_version", "4");
		    URI uri = uriBuilder.build();
			
			CloseableHttpResponse response = (CloseableHttpResponse) instagramProxyClient.requestGET(uri, headers);
			
			statusCode = response.getStatusLine().getStatusCode();
			sonuc = extractJSONResponse(response);
			statusCodeOfLastRequest = response.getStatusLine().getStatusCode();
			
		} catch (Exception e) {
			e.printStackTrace();
			return sonuc;
		}
		
		return sonuc;
	}

	public Boolean uploadStoryPhoto(Profile profile, String caption, Post post) {

		Boolean wasSend = false;
		String imagePostsPath = OpSocialBackApplication.UPLOAD_DIR_IMAGES_POSTS;
		
		try {

			// Verificação de novos parâmetros em nova versão do APP do Instagram.
			if(profile.getInstagramDeviceId() == null || profile.getInstagramPhoneId() == null) {
				
				profile.setInstagramDeviceId("android-" + UtilFunctions.generateMD5Hash(profile.getScreenName() + profile.getInstagramPassword()));
				profile.setInstagramPhoneId(UUID.randomUUID().toString());
				
				// Little work around to make the user connect a different device, waiting for the verification on mobile phone.
				if(profile.getInstagramDevice() == null) {
					
					MaintenanceInstagramDeviceRemote instagramDeviceRemote = (MaintenanceInstagramDeviceRemote) 
							RecoverMaintenance.recoverMaintenance("InstagramDevice");
					
					String instagramGUID;
					
					instagramGUID = UUID.randomUUID().toString();
					instagramDevice = instagramDeviceRemote.getById(instagramDeviceRemote.getInstagramDeviceLessUsedId());
					
					profile.setInstagramGUID(instagramGUID);
					profile.setInstagramDevice(instagramDevice);
				}
				
				MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				
				profileRemote.save(profile);
			}

			if(authenticateUser(profile, false)) {

				for(PostAttachment postAttachment : post.getPostsAttachments()) {
					
					new S3Integration().downloadS3Object(S3Integration.WOOPSOCIAL_BUCKET_NAME, 
							S3Integration.WOOPSOCIAL_IMAGES_POSTS_FOLDER, postAttachment.getFileName(), imagePostsPath);
					
					wasSend = uploadSinglePhoto(profile, STORY, caption, new File(imagePostsPath, postAttachment.getFileName()), null, PHOTO_FILE);					
				}

			} else {
				jsonResponseError = loginResponse;
				wasSend = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			wasSend = false;
		}
		
		try {
			instagramProxyClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return wasSend;
	}
}