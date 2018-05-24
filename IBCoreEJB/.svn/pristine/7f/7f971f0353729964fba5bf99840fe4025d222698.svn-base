package br.com.opsocial.ejb.entity.news;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="newsposts", schema="opsocial")
public class NewsPost extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idnewspost")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "newsposts_idnewspost_seq")
	private Long idNewsPost;
	
	@Column(name = "url")
	private String url;

	@Column(name = "title")
	private String title;

	@Column(name = "imageurl")
	private String imageUrl;

	@Column(name = "imagewidth")
	private Integer imageWidth;

	@Column(name = "imageheight")
	private Integer imageHeight;

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
	
	public NewsPost() {
	}
	
	public Long getIdNewsPost() {
		return idNewsPost;
	}

	public void setIdNewsPost(Long idNewsPost) {
		this.idNewsPost = idNewsPost;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
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
