package br.com.opsocial.client.entity.googleplus;

import java.util.Date;

import br.com.opsocial.client.entity.socialnetworks.googleplus.ActorDTO;
import br.com.opsocial.client.entity.socialnetworks.googleplus.ObjectDTO;
import br.com.opsocial.ejb.entity.generic.Persistent;
public class GooglePlusPostDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final String VERB_POST = "post";
	public static final String VERB_SHARE = "share";
	public static final Long MAX_RESULTS_SEARCH = 20L;
	
	public static final String FIELDS_SEARCH = "nextPageToken,etag,items(id,title,published,url,verb,actor(id,displayName,url,image),object(id,content,objectType,attachments(objectType,displayName,content,url,image,embed),replies/totalItems,plusoners/totalItems,resharers/totalItems))";
	public static final String FIELDS_GET_PEOPLE = "gender,objectType,circledByCount";
	public static final String FIELDS_ACTIVITY_URL = "url";
	
	private String idActivity;
	private String title;
	private Long published;
	private String url;
	private String verb;
	private ActorDTO actor;
	private ObjectDTO object;
	private Date lastUpdate;
	
	public GooglePlusPostDTO() {
		
	}
	
	public String getIdActivity() {
		return idActivity;
	}
	
	public void setIdActivity(String idActivity) {
		this.idActivity = idActivity;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getPublished() {
		return published;
	}
	
	public void setPublished(Long published) {
		this.published = published;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVerb() {
		return verb;
	}
	
	public void setVerb(String verb) {
		this.verb = verb;
	}

	public ActorDTO getActor() {
		return actor;
	}

	public void setActor(ActorDTO actor) {
		this.actor = actor;
	}

	public ObjectDTO getObject() {
		return object;
	}

	public void setObject(ObjectDTO object) {
		this.object = object;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
