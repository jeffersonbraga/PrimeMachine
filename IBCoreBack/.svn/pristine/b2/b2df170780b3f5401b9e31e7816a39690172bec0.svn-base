package br.com.opsocial.server.actions.feeds.facebook;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.facebook.FacebookPostDTO;
import br.com.opsocial.client.entity.socialnetworks.facebook.FacebookPaging;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.facebook.FacebookPost;
import br.com.opsocial.ejb.entity.socialnetworks.facebook.FacebookPicture;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.facebook.FacebookAPI;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Like;
import facebook4j.PictureSize;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

@RestController
@RequestMapping("woopsocial")
public class FacebookPageHomeFeedsAction extends ServerAction {

	private Facebook facebook;
	
	private Profile profileDTO;
	private FacebookPaging facebookPaging;
	
	private String postFields;

	@Override
	@RequestMapping(value = "/facebook_page_home_feeds",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception {

		profileDTO = (Profile) getParameters().get("profile");
		
		if(getParameters().containsKey("facebookPaging")) {
			facebookPaging = (FacebookPaging) getParameters().get("facebookPaging");
		} else {
			facebookPaging = null;
		}
		
		setParameters(new HashMap<String, Object>());

		AccessToken accessToken = new AccessToken(profileDTO.getToken(), null);
		
		facebook = new FacebookIntegration().getFacebook(accessToken);
		
		ResponseList<Post> pageFeedsMe;
		
		postFields = "id,caption,created_time,description,message,link,actions,picture,source,type,name,from," +
				 "object_id,shares,likes.summary(true),comments.summary(true)";
		
		if(facebookPaging != null) {
			pageFeedsMe = facebook.getPosts(getFacebookPaging(facebookPaging));
			pageFeedsMe.remove(0);
		} else {
			Reading reading = new Reading().fields(postFields).limit(10);
			pageFeedsMe = facebook.getPosts(reading);
		}
		
		List<FacebookPost> facebookPosts = getFacebookPosts(pageFeedsMe, profileDTO.getNetworkId());
		
		List<FacebookPost> facebookPostDTOs = new ArrayList<FacebookPost>();
		
		for(FacebookPost facebookPost : facebookPosts) {
			facebookPostDTOs.add(facebookPost);
		}
		
		if(pageFeedsMe.getPaging() != null && !pageFeedsMe.isEmpty()) {
			
			String until = String.valueOf(pageFeedsMe.get(pageFeedsMe.size() -1).getCreatedTime().getTime() / 1000L);
			
			getParameters().put("facebookPaging", 
					getFacebookPagingDTO(until));
		}
		
		getParameters().put("pageFeed", facebookPostDTOs);
		
		getParameters().put("profile", profileDTO);
		
		FacebookAPI facebookAPI = new FacebookAPI();
		
		getParameters().put("pageFans", new Long(facebookAPI.recoverValueByProperty(
				profileDTO.getNetworkId(), profileDTO.getToken(), "page_fans")));
	}

	private Reading getFacebookPaging(FacebookPaging facebookPaging) {

		Reading reading = new Reading().
				fields(postFields).
				limit(facebookPaging.getLimit()).
				offset(facebookPaging.getOffset()).
				until(new Date(java.lang.Long.valueOf(facebookPaging.getUntil()) * 1000L));
		
		return reading;
	}

	private FacebookPaging getFacebookPagingDTO(String until) {
		
		FacebookPaging pagingDTO = new FacebookPaging();
		pagingDTO.setLimit(10);
		pagingDTO.setSince(null);
		pagingDTO.setUntil(until);

		return pagingDTO;
	}
	
	private List<FacebookPost> getFacebookPosts(ResponseList<Post> feeds, String idPage) throws FacebookException, NamingException {
		
		List<FacebookPost> posts = new ArrayList<FacebookPost>();
		
		for (Post post : feeds) {
			
			FacebookPost facebookPost = new FacebookPost();
			
			facebookPost.setPostId(post.getId());
			facebookPost.setProfileId(idPage);
			facebookPost.setCaption(post.getCaption());
			facebookPost.setCreatedTime(post.getCreatedTime().getTime() / 1000L);
			facebookPost.setDescription(post.getDescription());
			facebookPost.setMessage(post.getMessage());
			facebookPost.setObjectId(post.getObjectId());
			
			if(post.getLink() != null) {
				facebookPost.setLink(post.getLink().toString());
			} else {
				if(post.getActions() != null && !post.getActions().isEmpty()) {
					facebookPost.setLink(post.getActions().get(0).getLink());
				}
			}
			
			if(post.getPicture() != null && post.getType().equals(FacebookPostDTO.PHOTO)) {
				FacebookPicture facebookPicture = UtilFunctions.adaptFacebookPicture(post.getPicture().toString(), FacebookPicture.MAX_WIDTH_FEEDS); 
				
				if(facebookPicture != null) {
					facebookPost.setPictureUrl(facebookPicture.getSource());
					facebookPost.setPictureHeight(facebookPicture.getHeight());
					facebookPost.setPictureWidth(facebookPicture.getWidth());
				}				
			}
			
			facebookPost.setSource(post.getSource() != null ? post.getSource().toString() : null);
			facebookPost.setType(post.getType());
			facebookPost.setName(post.getName());
			facebookPost.setProfileNameFrom(post.getFrom().getName());
			facebookPost.setProfilePictureFrom(facebook.getPictureURL(PictureSize.small).toString());
			facebookPost.setProfileLinkFrom(facebook.getUser(idPage, new Reading().fields("link")).getLink().toString());
			
			facebookPost.setComments(post.getComments().getSummary().getTotalCount());

			if(post.getSharesCount() == null) {
				facebookPost.setShares(0);
			} else {
				facebookPost.setShares(post.getSharesCount());
			}
			
			if(post.getLikes() != null) {
				
				facebookPost.setLikes(post.getLikes().getSummary().getTotalCount());
				
				for(Like like : post.getLikes()) {
					if(like.getName().equals(profileDTO.getScreenName())) {
						facebookPost.setUserLike(true);
						break;
					}
				}
			} else {
				facebookPost.setLikes(0);
			}

			posts.add(facebookPost);
		}
		
		return posts;
	}

}
