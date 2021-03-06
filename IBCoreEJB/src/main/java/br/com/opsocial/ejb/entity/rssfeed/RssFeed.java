package br.com.opsocial.ejb.entity.rssfeed;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="rssfeeds", schema="opsocial")
public class RssFeed extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idrssfeed")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "rssfeeds_idrssfeed_seq")
	private Long idRssfeed;

	@Column(name = "url")
    private String url;

	@Column(name = "title")
    private String title;
	
	@Column(name = "link")
    private String link;
	
	@Column(name = "description")
    private String description;
	
	@Column(name = "language")
    private String language;
	
	@Column(name = "copyright")
    private String copyright;
	
	@Column(name = "pubdate")
    private String pubDate;
	
	@Transient
	private List<RssFeedMessage> rssFeedMessage;

	public List<RssFeedMessage> getRssFeedMessage() {
		return rssFeedMessage;
	}

	public void setRssFeedMessage(List<RssFeedMessage> rssFeedMessage) {
		this.rssFeedMessage = rssFeedMessage;
	}

	public Long getIdRssfeed() {
		return idRssfeed;
	}

	public void setIdRssfeed(Long idRssfeed) {
		this.idRssfeed = idRssfeed;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	@Override
	public Long getId() {		
		return idRssfeed;
	}

	@Override
	public void setId(Long id) {
		this.idRssfeed= id;		
	}
}
