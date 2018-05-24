package br.com.opsocial.ejb.entity.twitter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="twitterpostsurls", schema="opsocial")
public class TwitterPostURL extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idtwitterposturl")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "twitterpostsurls_idtwitterposturl_seq")
	private Long idTwitterPostURL;
	
	@JoinColumn(name = "statusid", referencedColumnName = "statusid")
	@ManyToOne()
	private TwitterPost twitterPost;
	
	@Column(name = "url")
	private String URL;
	
	@Column(name = "expandedurl")
	private String expandedURL;
	
	@Column(name = "displayurl")
	private String displayURL;
	
	@Column(name = "urlstart")
	private Integer urlStart;
	
	@Column(name = "urlend")
	private Integer urlEnd;
	
	public TwitterPostURL() {
		
	}

	public Long getIdTwitterPostURL() {
		return idTwitterPostURL;
	}

	public void setIdTwitterPostURL(Long idTwitterPostURL) {
		this.idTwitterPostURL = idTwitterPostURL;
	}
	
	@Override
	public Long getId() {
		return idTwitterPostURL;
	}
	
	@Override
	public void setId(Long id) {
		this.idTwitterPostURL = id;
	}

	public TwitterPost getTwitterPost() {
		return twitterPost;
	}

	public void setTwitterPost(TwitterPost twitterPost) {
		this.twitterPost = twitterPost;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getExpandedURL() {
		return expandedURL;
	}

	public void setExpandedURL(String expandedURL) {
		this.expandedURL = expandedURL;
	}

	public String getDisplayURL() {
		return displayURL;
	}

	public void setDisplayURL(String displayURL) {
		this.displayURL = displayURL;
	}

	public Integer getUrlStart() {
		return urlStart;
	}

	public void setUrlStart(Integer urlStart) {
		this.urlStart = urlStart;
	}

	public Integer getUrlEnd() {
		return urlEnd;
	}

	public void setUrlEnd(Integer urlEnd) {
		this.urlEnd = urlEnd;
	}

}
