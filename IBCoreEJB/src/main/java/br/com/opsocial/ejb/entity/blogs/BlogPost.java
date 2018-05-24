package br.com.opsocial.ejb.entity.blogs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="blogposts", schema="opsocial")
public class BlogPost extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idblogpost")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "blogposts_idblogpost_seq")
	private Long idBlogPost;
	
	@Column(name = "url")
	private String url;

	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;

	@Column(name = "createdtime")
	private Long createdTime;
	
	@Column(name = "publisher")
	private String publisher;
	
	@Column(name = "publisherpicture")
	private String publisherPicture;
	
	@Column(name = "publisherurl")
	private String publisherUrl;
	
	public BlogPost() {
	}

	public Long getIdBlogPost() {
		return idBlogPost;
	}

	public void setIdBlogPost(Long idBlogPost) {
		this.idBlogPost = idBlogPost;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublisherPicture() {
		return publisherPicture;
	}

	public void setPublisherPicture(String publisherPicture) {
		this.publisherPicture = publisherPicture;
	}

	public String getPublisherUrl() {
		return publisherUrl;
	}

	public void setPublisherUrl(String publisherUrl) {
		this.publisherUrl = publisherUrl;
	}
}
