package br.com.opsocial.server.actions.application;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.OptimisticLockException;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenancePostRemote;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.Timezone;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.ejb.entity.sets.PostAttachment;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.Reflection;
import br.com.opsocial.server.utils.UtilFunctions;

@RestController
@RequestMapping("woopsocial")
public class ComposeAction extends ServerAction {
	
	@Autowired 
	PostAction postAction;
	
	@Override
	public void doAction() throws Exception, OptimisticLockException {
		// TODO Auto-generated method stub
		
	}

	@CrossOrigin
	@RequestMapping(value = "/persist_post",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> persistCompose(@Valid @RequestBody ArrayList<Object> objPersistir) {
	//public ResponseEntity<Object> persistCompose(@Valid @RequestBody Post postdata, @Valid @RequestBody List<Profile> profilelist) {
	//public ResponseEntity<Object> persistCompose(@Valid @RequestBody List<Profile> profilelist) {
	HashMap<Boolean, String> mapPostsSent = new HashMap<Boolean, String>();
	HashMap<Boolean, String> mapPostsSchedule = new HashMap<Boolean, String>();

	try {

			Gson gsonDeserializer = new Gson();
			Post post = new Post();
			Timezone timezone = new Timezone();

			Reflection.montarObjetoAbstrato(post, (LinkedHashMap<String, Object>) objPersistir.get(0));
			ArrayList<LinkedHashMap<String, Object>> listProfiles = (ArrayList<LinkedHashMap<String, Object>>) objPersistir.get(1);
			Reflection.montarObjetoAbstrato(timezone, (LinkedHashMap<String, Object>) objPersistir.get(2));

			MaintenancePostRemote remote 			= (MaintenancePostRemote) RecoverMaintenance.recoverMaintenance("Post");
			MaintenanceProfileRemote profileRemote 	= (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");

			List<Profile> profilesToPost = new ArrayList<>();

			Boolean isSchedule = true;
			DateFormat df2 = new SimpleDateFormat("M/d/yyyy H:m:s z");
			df2.setLenient (false); 

			Boolean addProfileOnEdit 	= false;
			Boolean switchProfileOnEdit = true;
			List<Profile> listProfilesAux = new ArrayList<>();

			Post postAux = null;
			if(post.getIdPost() != null) {

				postAux = remote.getById(post.getIdPost());
			}

			for ( LinkedHashMap<String, Object> itemProfile : listProfiles ) {

				Profile profile = new Profile();
				//Reflection.montarObjetoAbstrato(profile, itemProfile);
				Reflection.setChavePrimariaFromList(profile, itemProfile);
				profile = profileRemote.getById(profile.getId());
				listProfilesAux.add(profile);

				if (postAux != null && profile.getId().equals(postAux.getProfile().getId()) ) {
					switchProfileOnEdit = false;
				} else if (postAux != null && !profile.getId().equals(postAux.getProfile().getId()) ) {
					addProfileOnEdit 	= true;
				}
			}
			ThreadGroup groupThreadThumbnailGenerator = new ThreadGroup("groupThreadPost" + Math.random());

			for ( Profile itemProfile : listProfilesAux ) {
				Post post_item = SerializationUtils.clone(post);

				if(post_item.getIdPost() != null) {

					List<PostAttachment> postAttachmentAux = post_item.getPostsAttachments();
					post_item = remote.getById(post_item.getIdPost());
					if (post_item.getPosted().equals(Post.NOT_POSTED)) {
						post_item.setPostsAttachments(postAttachmentAux);
						post_item.setAppendType(post.getAppendType());
						post_item.setCountPostingAttempts(post.getCountPostingAttempts());
						post_item.setDescription(post.getDescription());
						post_item.setFormattedPost(post.getFormattedPost());
						post_item.setHashtags(post.getHashtags());
						post_item.setLink(post.getLink());
						post_item.setOriginalLink(post.getOriginalLink());
						post_item.setPicture(post.getPicture());
						post_item.setPictureHeight(post.getPictureHeight());
						post_item.setPictureWidth(post.getPictureWidth());
						post_item.setPostDate(post.getPostDate());
						post_item.setScheduleDate(post.getScheduleDate());
						post_item.setTargetFeed(post.getTargetFeed());
						post_item.setText(post.getText());
						post_item.setTitle(post.getTitle());
						post_item.setVideoDuration(post.getVideoDuration());
						post_item.setVideoExtension(post.getVideoExtension());
						post_item.setVideoName(post.getVideoName());
						post_item.setVideoSize(post.getVideoSize());
					} else {

						post_item = SerializationUtils.clone(post);
						post_item.setPostsAttachments(postAttachmentAux);
						post_item.setIdPost(null);
					}

					//post_item.setPostsAttachments(postsAttachments);

					if ( !itemProfile.getId().equals(post_item.getProfile().getId()) && addProfileOnEdit && !switchProfileOnEdit) {
						post_item.setProfile(itemProfile);
						post_item.setId(null);
					} else if ( !itemProfile.getId().equals(post_item.getProfile().getId()) && !addProfileOnEdit && switchProfileOnEdit) {
						post_item.setProfile(itemProfile);						
					} else if ( !itemProfile.getId().equals(post_item.getProfile().getId()) && addProfileOnEdit && switchProfileOnEdit) {
						post_item.setProfile(itemProfile);
						post_item.setId(null);						
					}

				} else {
					post_item.setProfile(itemProfile);
				}

				post_item.setPosted(Post.NOT_POSTED);

				if (post_item.getScheduleDate() != null && post_item.getScheduleDate() != "") {
					post_item.setPostDate(df2.parse(post_item.getScheduleDate()));
					post_item.setStartDate(post_item.getPostDate().getTime());
					post_item.setStatus(post_item.SCHEDULED);
					post_item.setTimezone(timezone);
				} else {
					post_item.setStatus(post_item.POSTING);
					post_item.setTimezone(null);
				}

				if (post_item.getPostsAttachments() != null) {
					for ( PostAttachment itemAtt : post_item.getPostsAttachments() ) {
						itemAtt.setPost(post_item);
					}
				}

				Object[] messageArgs = new Object[]{ itemProfile.getScreenName(), UtilFunctions.getNetworkName(itemProfile.getType()) };
				try {

					if(post_item.getIdPost() != null) {
						remote.getDao().flush();
						remote.getDao().flush(post_item);
						post_item = remote.merge(post_item);
					} else {
						post_item = remote.save(post_item);
					}
					mapPostsSchedule.put(true, OpSocialBackApplication.messageByLocaleService.getMessage("post.schedule.ok", messageArgs));
				} catch (Exception e) {
					mapPostsSchedule.put(false, OpSocialBackApplication.messageByLocaleService.getMessage("post.schedule.notok", messageArgs));
					e.printStackTrace();
				}
				/*
				if (profile.getType().equals("I")) {
					MaintenanceInstagramDeviceRemote instagramDeviceRemote = (MaintenanceInstagramDeviceRemote) 
							RecoverMaintenance.recoverMaintenance("InstagramDevice");
	
					String instagramGUID = UUID.randomUUID().toString();
					String instagramPhoneId = UUID.randomUUID().toString();
					String instagramDeviceId = "android-" + UtilFunctions.generateMD5Hash(profile.getScreenName() + profile.getInstagramPassword());
	
					InstagramDevice instagramDevice = instagramDeviceRemote.getById(instagramDeviceRemote.getInstagramDeviceLessUsedId());
	
					profile.setInstagramGUID(instagramGUID);
					profile.setInstagramDeviceId(instagramDeviceId);
					profile.setInstagramPhoneId(instagramPhoneId);
					profile.setInstagramDevice(instagramDevice);
	
					if (profile.getInstagramDevice() != null) {
						InstagramPostHelper instagramPostHelper = new InstagramPostHelper();
						String r = instagramPostHelper.getBusinessInformation(profile.getInstagramDevice());
					}
				}*/

				profilesToPost.add(itemProfile);

				if (post_item.getScheduleDate() == null || post_item.getScheduleDate() == "") {

					HashMap<Boolean, String> mapPostExecute = new HashMap<Boolean, String>();
//					new PostAction().makeSingleProfilePost(profile, post, mapPostExecute);
					//postAction.makeSingleProfilePost(profile, post, mapPostExecute);
					this.sendPostByThread(groupThreadThumbnailGenerator, itemProfile, post_item, mapPostExecute, mapPostsSent);
					/*String msg = "";
					boolean key = (mapPostExecute.get(true) != null)? true : false;

					if (mapPostsSent.size() > 0 && (mapPostsSent.get(key) != null)) {
						msg = mapPostsSent.get(key);
					}

					msg += mapPostExecute.get(key) + "\n";

					mapPostsSent.put(key, msg);*/
				} else {
					mapPostsSent = mapPostsSchedule;
				}
			}

			if (addProfileOnEdit && switchProfileOnEdit) {
				remote.getDao().flush();
				remote.getDao().flush(postAux);
				remote.delete(postAux);
			}

			while (true) {
				if (groupThreadThumbnailGenerator.activeCount() == 0) {
					break;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<>(mapPostsSent, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getPostById",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Post> getPostById(@Valid @RequestBody Post post) throws Exception {

		MaintenancePostRemote remote = (MaintenancePostRemote) RecoverMaintenance.recoverMaintenance("Post");
		post = remote.getById(post.getIdPost());
		remote.getDao().flush();
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/deleteAttachment",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteAttachment(@Valid @RequestBody PostAttachment postAtt) throws Exception {

		MaintenancePostRemote remote = (MaintenancePostRemote) RecoverMaintenance.recoverMaintenance("Post");
		if (postAtt.getIdPostAttachment() != null) {
			remote.deleteAttachment(postAtt);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

//	@CrossOrigin(origins = "*")
//	@RequestMapping(value = "/schedule_post",
//    method = RequestMethod.POST,
//    produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Boolean> schedulePost(@Valid @RequestBody Post post) throws Exception {
//		
//		JobDetailImpl job = new JobDetailImpl();
//		job.setName( "post_schedule" + post.getIdPost() );
//		job.setJobClass(ScheduleAction.class);
//		
//		job.getJobDataMap().put("post_schedule", post.getIdPost());
//		
//		 //configure the scheduler time
//		SimpleTriggerImpl trigger = new SimpleTriggerImpl();
//		trigger.setName("trigger_schedule_post_"+ post.getIdPost());
//		
//		//trigger.setStartTime( dataInicial );
//		//trigger.setStartTime(new Date(System.currentTimeMillis() + 10000));
//		trigger.setStartTime(post.getPostDate());
//		
//		//trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
//		//trigger.setRepeatInterval(tempo);
//				//schedule it
//		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//		scheduler.start();
//	 	scheduler.scheduleJob(job, trigger);
//	 	
//		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//	}

	@RequestMapping(value = "/reload_post",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Post> validateReloadPostFeature() throws Exception {

		User loggedUser = new User();

		MaintenancePostRemote remote = (MaintenancePostRemote) RecoverMaintenance.recoverMaintenance("Post");
		return new ResponseEntity<Post>((Post) remote.getPostReloadFeature(loggedUser), HttpStatus.OK);
	}

	public void sendPostByThread(ThreadGroup groupThreadThumbnailGenerator, Profile profile, Post post, HashMap<Boolean, String> mapPostExecute, HashMap<Boolean, String> mapPostsSent) {

		new Thread(groupThreadThumbnailGenerator, ("executePost" + Math.random())) {

			@Override
			public void run() {

				postAction.makeSingleProfilePost(profile, post, mapPostExecute);
				String msg = "";
				boolean key = (mapPostExecute.get(true) != null)? true : false;
				MaintenancePostRemote remote 			= (MaintenancePostRemote) RecoverMaintenance.recoverMaintenance("Post");

				if (key) {

					post.setPosted(Post.POSTED);
					post.setStatus(Post.FINALIZED);
				} else {

					post.setPosted(Post.ERROR_POSTING);
					post.setStatus(Post.FINALIZED);
				}

				try {
					remote.save(post);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (mapPostsSent.size() > 0 && (mapPostsSent.get(key) != null)) {
					msg = mapPostsSent.get(key);
				}

				msg += mapPostExecute.get(key) + "\n";

				mapPostsSent.put(key, msg);
			}
		}.start();

	}
}