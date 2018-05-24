package br.com.opsocial.client.entity.blogs;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class BlogPostDTO extends Persistent {
	
	private static final long serialVersionUID = 1L;

	private Long idBlogPost;
	private String url;
	private String title;
	private String description;
	private Long createdTime;
	private String publisher;
	private String publisherPicture;
	private String publisherUrl;
	
	public BlogPostDTO() {
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
