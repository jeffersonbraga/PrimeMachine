package br.com.opsocial.server.actions.groups;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.rssfeed.Feed;
import br.com.opsocial.client.entity.rssfeed.FeedMessage;
import br.com.opsocial.client.entity.rssfeed.RSSFeedParser;
import br.com.opsocial.ejb.das.MaintenanceGroupRemote;
import br.com.opsocial.ejb.das.MaintenanceGroupsProfilesRemote;
import br.com.opsocial.ejb.das.MaintenanceGroupsRssfeedsRemote;
import br.com.opsocial.ejb.das.MaintenanceRssFeedRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.groups.GroupProfilesHelper;
import br.com.opsocial.ejb.entity.groups.GroupRssFeedsHelper;
import br.com.opsocial.ejb.entity.groups.Groups;
import br.com.opsocial.ejb.entity.groups.GroupsProfiles;
import br.com.opsocial.ejb.entity.groups.GroupsRssfeeds;
import br.com.opsocial.ejb.entity.rssfeed.RssFeed;
import br.com.opsocial.ejb.entity.rssfeed.RssFeedMessage;
import br.com.opsocial.server.actions.application.RecoverLinkResumeAction;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;

@RestController
@RequestMapping("woopsocial")
public class GroupsController {

	private User user;

	@CrossOrigin
	@RequestMapping(value = "/groups_delete",
    method = RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Groups>> deleteGroup(Long idGroup) {
		
		List<Groups> groups = new ArrayList<Groups>();
		Character groupTypeAux = null;
		
		try {
			
			MaintenanceGroupRemote maintenanceGroupRemote = (MaintenanceGroupRemote) RecoverMaintenance.recoverMaintenance("Groups");

			MaintenanceGroupsRssfeedsRemote maintenanceGroupsRssfeedsRemote = 
					(MaintenanceGroupsRssfeedsRemote) RecoverMaintenance.recoverMaintenance("GroupsRssfeeds");
			
			MaintenanceGroupsProfilesRemote maintenanceGroupsProfilesRemote = 
					(MaintenanceGroupsProfilesRemote) RecoverMaintenance.recoverMaintenance("GroupsProfiles");
			
			user = SecurityUtils.getCurrentUser();

			Groups group = retrieveRssfeedGroup(idGroup);
			
			if(group.getGroupType() == 'R') {
				
				groupTypeAux = 'R';
				
				MaintenanceRssFeedRemote maintenanceRssFeedRemote = 
						(MaintenanceRssFeedRemote) RecoverMaintenance.recoverMaintenance("RssFeed");
				
				for(Object object : group.getGroupsObject()) {
					
					GroupsRssfeeds groupsRssfeed = (GroupsRssfeeds) object;
					maintenanceRssFeedRemote.delete(groupsRssfeed.getRssFeed());
				}
			} else {
				groupTypeAux = 'P';
			}
			
			maintenanceGroupRemote.delete(group);
			
			if(groupTypeAux == 'R') {
				groups = maintenanceGroupRemote.listGroupsByAccount(this.user.getAccount().getIdAccount(), 'R');

				for (Groups groupAux : groups) {
					groupAux.setGroupsObject(maintenanceGroupsRssfeedsRemote.getByGroup(groupAux.getIdGroup()));
				}
			} else if (groupTypeAux == 'P') {
				groups = maintenanceGroupRemote.listGroupsByAccount(this.user.getAccount().getIdAccount(), 'P');

				for (Groups groupAux : groups) {
					groupAux.setGroupsObject(maintenanceGroupsProfilesRemote.getByGroup(groupAux.getIdGroup()));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(groups, HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(groups, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/groups/insert",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insertGroup(@Valid @RequestBody Groups group, HttpSession session) {
		
		try {
			
			MaintenanceGroupRemote remote = (MaintenanceGroupRemote) RecoverMaintenance.recoverMaintenance("Groups");
			
			user = SecurityUtils.getCurrentUser();
			
			group.setAccount(user.getAccount());

			group = remote.save(group);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(group, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/groups_update",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateGroup(@Valid @RequestBody GroupProfilesHelper groupProfilesHelper, HttpSession session) {
		
		Groups group = new Groups();
		
		try {
			
			MaintenanceGroupRemote groupsRemote = (MaintenanceGroupRemote) RecoverMaintenance.recoverMaintenance("Groups");
			
			MaintenanceGroupsProfilesRemote remote = 
					(MaintenanceGroupsProfilesRemote) RecoverMaintenance.recoverMaintenance("GroupsProfiles");
			
			String updateName = groupProfilesHelper.getGroup().getName();
			
			group = groupsRemote.getById(groupProfilesHelper.getGroup().getIdGroup());
			group.setName(updateName);
			
			if(groupProfilesHelper.getProfiles() != null) {
				
				List<Profile> listProfilesToADD = new ArrayList<Profile>(groupProfilesHelper.getProfiles());
				
				remote.deleteNotNecessaryProfiles(group.getIdGroup(), listProfilesToADD);
	
				for (Profile profile : listProfilesToADD) {
					
					GroupsProfiles groupsProfiles = new GroupsProfiles();
					groupsProfiles.setGroups(group);
					groupsProfiles.setProfile(profile);
					groupsProfiles.setVersion(1L);
					
					groupsProfiles = remote.save(groupsProfiles);
				}
				
				group = groupsRemote.save(group);
				
				group.setGroupsObject(remote.getByGroup(group.getIdGroup()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(group, HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(group, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/rssfeed_insert",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> insertRssfeedOnGroup(@Valid @RequestBody GroupRssFeedsHelper groupRssFeedsHelper, HttpSession session) {

		List<Groups> groups = new ArrayList<Groups>();
		RssFeed rssFeedUpdated = new RssFeed();
		List<Object> list = new ArrayList<Object>();
		
		try {
			
			rssFeedUpdated = processGroupObject(groupRssFeedsHelper.getGroup(), groupRssFeedsHelper.getRssFeed());
	
			groups = retrieveRssfeedGroups();
			
			list.add(groups);
			list.add(rssFeedUpdated);
			
		} catch (MalformedURLException e) {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	private RssFeed processGroupObject(Groups group, RssFeed rssFeed) throws IllegalArgumentException, Exception {

		RssFeed rssFeedAux = new RssFeed();
		
		try {
			
			MaintenanceGroupsRssfeedsRemote maintenanceGroupsRssfeedsRemote = 
					(MaintenanceGroupsRssfeedsRemote) RecoverMaintenance.recoverMaintenance("GroupsRssfeeds");
	
			GroupsRssfeeds groupsRssfeed = new GroupsRssfeeds();
			groupsRssfeed.setGroups(null);
			groupsRssfeed.setRssFeed(processRssFeedObjectNoMessages(rssFeed));
			
			rssFeed = groupsRssfeed.getRssFeed();
			
			List<Object> groupsRssfeeds = new ArrayList<Object>();
			
			if(group.getGroupsObject() != null && !group.getGroupsObject().isEmpty()) {
				groupsRssfeeds.addAll(group.getGroupsObject());
			}
			
			groupsRssfeeds.add(groupsRssfeed);
			
			group.setGroupsObject(groupsRssfeeds);
			rssFeedAux.setCopyright(rssFeed.getCopyright());
			rssFeedAux.setDescription(rssFeed.getDescription());
			rssFeedAux.setIdRssfeed(rssFeed.getIdRssfeed());
			rssFeedAux.setLanguage(rssFeed.getLanguage());
			rssFeedAux.setLink(rssFeed.getLink());
			rssFeedAux.setPubDate(rssFeed.getPubDate());
			rssFeedAux.setTitle(rssFeed.getTitle());
			rssFeedAux.setUrl(rssFeed.getUrl());
			
			MaintenanceGroupRemote maintenanceGroupRemote = (MaintenanceGroupRemote) RecoverMaintenance.recoverMaintenance("Groups");
			Groups groupAux = maintenanceGroupRemote.getById(group.getIdGroup());
			
			GroupsRssfeeds groupsRssfeedAux = new GroupsRssfeeds();
			groupsRssfeedAux.setGroups(groupAux);
			groupsRssfeedAux.setRssFeed(rssFeedAux);
			
			maintenanceGroupsRssfeedsRemote.save(groupsRssfeedAux);
			
		} catch (MalformedURLException e) {
			throw new MalformedURLException();
		} catch (RuntimeException e) {
			throw new RuntimeException();
		} catch (Exception e) {
			throw new Exception();
		}
		
		return rssFeedAux;
	}

	private RssFeed processRssFeedObjectNoMessages(RssFeed rssFeed) throws IllegalArgumentException, Exception {
		
		try {
			
			MaintenanceRssFeedRemote maintenanceRssFeedRemote = 
					(MaintenanceRssFeedRemote) RecoverMaintenance.recoverMaintenance("RssFeed");
			
			RSSFeedParser parser = new RSSFeedParser(rssFeed.getUrl());
			
		    Feed feed = parser.readFeed();
		    
		    if(rssFeed.getIdRssfeed() == null) {
		    	
			    rssFeed.setCopyright(feed.getCopyright());
			    rssFeed.setDescription(feed.getDescription());
			    rssFeed.setLanguage(feed.getLanguage());
			    rssFeed.setLink(feed.getLink());
			    rssFeed.setPubDate(feed.getPubDate());
			    rssFeed.setTitle(feed.getTitle());
			    
			    maintenanceRssFeedRemote.deleteByTitle(rssFeed.getTitle());
		    
			    rssFeed = maintenanceRssFeedRemote.save(rssFeed);
				
		    } else {
		    	rssFeed.setRssFeedMessage(new ArrayList<RssFeedMessage>());
		    }
		} catch (MalformedURLException e) {
			throw new MalformedURLException();
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
		
		return rssFeed;
	}

	private RssFeed processRssFeedObject(RssFeed rssFeed) throws IllegalArgumentException, Exception {
		
		try {
			
			MaintenanceRssFeedRemote maintenanceRssFeedRemote = 
					(MaintenanceRssFeedRemote) RecoverMaintenance.recoverMaintenance("RssFeed");
			
			RSSFeedParser parser = new RSSFeedParser(rssFeed.getUrl());
			
		    Feed feed = parser.readFeed();
		    
		    if(rssFeed.getIdRssfeed() == null) {
		    	
			    rssFeed.setCopyright(feed.getCopyright());
			    rssFeed.setDescription(feed.getDescription());
			    rssFeed.setLanguage(feed.getLanguage());
			    rssFeed.setLink(feed.getLink());
			    rssFeed.setPubDate(feed.getPubDate());
			    rssFeed.setTitle(feed.getTitle());
			    
			    maintenanceRssFeedRemote.deleteByTitle(rssFeed.getTitle());
		    
			    rssFeed = maintenanceRssFeedRemote.save(rssFeed);
				
		    } else {
		    	rssFeed.setRssFeedMessage(new ArrayList<RssFeedMessage>());
		    }
		    
		    List<RssFeedMessage> rssFeedMessages = new ArrayList<RssFeedMessage>();

    		int count = 0;
		    
		    for (FeedMessage feedMessage : feed.getMessages()) {
		    	
		    	if(feed.getMessages().size() > 20) {
		    		count++;
		    		if(count > 20) {
		    			break;
		    		}
		    	}
		    	
		    	RssFeedMessage rssFeedMessage = new RssFeedMessage();
		    	
		    	Date dateMessage = null;
		    	Date dateNow = new Date();
		    	Date dateTwoDaysAgo = new Date(dateNow.getTime() - 172800000); //TODO Verificar por timezone

		    	if(feedMessage.getPubDate() != null && feedMessage.getPubDate().length() > 0) {
		    		
			    	if(feedMessage.getPubDate().substring(3,4).equals(",")) {
			    		dateMessage = new Date(feedMessage.getPubDate());
			    	} else {
			    		TemporalAccessor creationAccessor = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(feedMessage.getPubDate());
			    		Instant instant = Instant.from(creationAccessor);
			    		dateMessage = new Date(instant.toEpochMilli());
			    	}
		    	} else {
		    		dateMessage = new Date();
		    	}
		    	
		    	if(dateMessage.after(dateTwoDaysAgo)) {
		    		
//		    		HashMap<String, Object> object = (HashMap<String, Object>) recoverLinkResumeAction.doAction(feedMessage.getLink()).getBody();
//		    		ArrayList<Object> images = (ArrayList<Object>) object.get("images");
//		    		HashMap<String, String> firstImg = (HashMap<String, String>) images.get(0);
		    		
			    	rssFeedMessage.setMessageAuthor(feedMessage.getAuthor());
			    	rssFeedMessage.setMessageDescription(feedMessage.getDescription());
			    	rssFeedMessage.setMessageContent(feedMessage.getContent());
			    	rssFeedMessage.setMessageGuid(feedMessage.getGuid());
			    	rssFeedMessage.setMessageLink(feedMessage.getLink());
			    	rssFeedMessage.setMessageTitle(feedMessage.getTitle());
			    	rssFeedMessage.setMessagePubDate(feedMessage.getPubDate());
			    	rssFeedMessage.setMessageImgURL(UtilFunctions.recoverLinkResume(feedMessage.getLink()).get("image").getAsString());
			    	
			    	Random random = new Random();
			    	rssFeedMessage.setIdRssfeedMsg(1000 + (long)(random.nextDouble()*(1000000 - 1000)));
			    	
			    	rssFeedMessages.add(rssFeedMessage);
		    	}
		    }
		    
		    rssFeed.setRssFeedMessage(rssFeedMessages);
		} catch (MalformedURLException e) {
			throw new MalformedURLException();
		} catch (Exception e) {
			throw new Exception();
		}
	    
	    return rssFeed;
	}

	@CrossOrigin
	@RequestMapping(value = "/rssfeed_delete",
    method = RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteRssfeed(Long idRssfeed, Long idGroup) {
		
		Groups group = new Groups();
		
		try {

			MaintenanceGroupsRssfeedsRemote maintenanceGroupsRssfeedsRemote = 
					(MaintenanceGroupsRssfeedsRemote) RecoverMaintenance.recoverMaintenance("GroupsRssfeeds");
			
			MaintenanceRssFeedRemote maintenanceRssFeedRemote = 
					(MaintenanceRssFeedRemote) RecoverMaintenance.recoverMaintenance("RssFeed");

			maintenanceGroupsRssfeedsRemote.deleteByRssFeedAndGroup(idGroup, idRssfeed);
			maintenanceRssFeedRemote.delete(maintenanceRssFeedRemote.getById(idRssfeed));
			
			group = retrieveRssfeedGroup(idGroup);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(group, HttpStatus.OK);
	}

	private Groups retrieveRssfeedGroup(Long idGroup) {
		
		MaintenanceGroupRemote maintenanceGroupRemote = (MaintenanceGroupRemote) RecoverMaintenance.recoverMaintenance("Groups");
		Groups group = maintenanceGroupRemote.getById(idGroup);
		
		MaintenanceGroupsRssfeedsRemote maintenanceGroupsRssfeedsRemote = 
				(MaintenanceGroupsRssfeedsRemote) RecoverMaintenance.recoverMaintenance("GroupsRssfeeds");
		
		group.setGroupsObject(maintenanceGroupsRssfeedsRemote.getByGroup(group.getIdGroup()));
		
		return group;
	}
	
	private List<Groups> retrieveRssfeedGroups() {
		
		MaintenanceGroupRemote maintenanceGroupRemote = (MaintenanceGroupRemote) RecoverMaintenance.recoverMaintenance("Groups");
		List<Groups> groups = maintenanceGroupRemote.listGroupsByAccount(SecurityUtils.getCurrentUser().getAccount().getIdAccount(), Groups.RSSFEEDGROUP);
		
		MaintenanceGroupsRssfeedsRemote maintenanceGroupsRssfeedsRemote = 
				(MaintenanceGroupsRssfeedsRemote) RecoverMaintenance.recoverMaintenance("GroupsRssfeeds");
		
		for (Groups group : groups) {
			group.setGroupsObject(maintenanceGroupsRssfeedsRemote.getByGroup(group.getIdGroup()));
		}
		
		return groups;
	}

	@CrossOrigin
	@RequestMapping(value = "/all_rssfeeds",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RssFeed>> listRssfeedsOnGroupByDate(Long idGroup) {

		List<RssFeed> rssFeeds = new ArrayList<RssFeed>();

		try { 

			MaintenanceGroupRemote maintenanceGroupRemote = (MaintenanceGroupRemote) RecoverMaintenance.recoverMaintenance("Groups");

			MaintenanceGroupsRssfeedsRemote maintenanceGroupsRssfeedsRemote = 
					(MaintenanceGroupsRssfeedsRemote) RecoverMaintenance.recoverMaintenance("GroupsRssfeeds");

			Groups group = maintenanceGroupRemote.getById(idGroup);

			group.setGroupsObject(maintenanceGroupsRssfeedsRemote.getByGroup(group.getIdGroup()));

			for (Object object : group.getGroupsObject()) {

				GroupsRssfeeds groupsRssfeeds = (GroupsRssfeeds) object;

				RssFeed rssFeed = processRssFeedObject(groupsRssfeeds.getRssFeed());

				rssFeeds.add(rssFeed);
			}

			rssFeeds = sortRssFeedMsgs(rssFeeds, group);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(rssFeeds, HttpStatus.OK);
	}

	private List<RssFeed> sortRssFeedMsgs(List<RssFeed> rssFeeds, Groups group) {

		List<RssFeed> rssFeedsAux = new ArrayList<RssFeed>();
		
		RssFeed rssFeedAux = new RssFeed();

		List<RssFeedMessage> rssFeedMessages = new ArrayList<RssFeedMessage>();
		
		for (RssFeed rssFeed : rssFeeds) {
			
			rssFeedMessages.addAll(rssFeed.getRssFeedMessage());
			rssFeed.setRssFeedMessage(null);
			
			for (RssFeedMessage rssFeedMessage : rssFeedMessages) {
				rssFeedMessage.setRssFeed(rssFeed);
				rssFeedMessage.setGroupName(group.getName());
			}
		}
		
	    Collections.sort(rssFeedMessages, new Comparator<RssFeedMessage>() {
			public int compare(RssFeedMessage o1, RssFeedMessage o2) {
		        if (o1.getMessagePubDate() == null || o2.getMessagePubDate() == null)
		        	return 0;
			    return new Date(o2.getMessagePubDate()).compareTo(new Date(o1.getMessagePubDate()));
			}
    	});
	    
	    rssFeedAux.setRssFeedMessage(rssFeedMessages);
	    rssFeedsAux.add(rssFeedAux);
	    
	    return rssFeedsAux;
	}

	@CrossOrigin
	@RequestMapping(value = "/get_rssfeedmsgs",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRssFeedMsgs(Long idRssfeed) {
		
		MaintenanceRssFeedRemote maintenanceRssFeedRemote = 
				(MaintenanceRssFeedRemote) RecoverMaintenance.recoverMaintenance("RssFeed");
		
		try {
			
			RssFeed rssFeedAux = processRssFeedObject(maintenanceRssFeedRemote.getById(idRssfeed));
			
			return new ResponseEntity<>(rssFeedAux, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/groups_profiles/list",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Groups>> listProfileGroups() throws Exception {
		
		MaintenanceGroupRemote maintenanceGroupRemote = (MaintenanceGroupRemote) RecoverMaintenance.recoverMaintenance("Groups");
		List<Groups> groups = maintenanceGroupRemote.listGroupsByAccount(SecurityUtils.getCurrentUser().getAccount().getIdAccount(), Groups.PROFILEGROUP);
		
		MaintenanceGroupsProfilesRemote maintenanceGroupsProfilesRemote = 
				(MaintenanceGroupsProfilesRemote) RecoverMaintenance.recoverMaintenance("GroupsProfiles");
		
		for (Groups group : groups) {
			group.setGroupsObject(maintenanceGroupsProfilesRemote.getByGroup(group.getIdGroup()));
		}

		return new ResponseEntity<>(groups, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/groups_feeds/list",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Groups>> listRssfeedsGroups() throws Exception {
		
		List<Groups> groups = retrieveRssfeedGroups();

		return new ResponseEntity<>(groups, HttpStatus.OK);
	}
}
