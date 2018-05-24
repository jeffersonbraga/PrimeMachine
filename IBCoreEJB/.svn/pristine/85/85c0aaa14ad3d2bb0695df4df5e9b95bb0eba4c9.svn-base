package br.com.opsocial.ejb.entity.google;

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
@Table(name="objectattachments", schema="opsocial")
public class ObjectAttachment extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idobjectattachment")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "objectattachments_idobjectattachment_seq")
	private Long idObjectAttachment;
	
	@JoinColumn(name = "activityid", referencedColumnName = "activityid")
	@ManyToOne()
	private GooglePlusPost googlePlusPost;
	
	@Column(name="objecttype")
	private String objectType;
	
	@Column(name="displayname")
	private String displayName;
	
	@Column(name="content")
	private String content;

	@Column(name="url")
	private String url;
	
	@Column(name="imageurl")
	private String imageUrl;
	
	@Column(name="imagetype")
	private String imageType;
	
	@Column(name="imageheight")
	private Integer imageHeight;
	
	@Column(name="imagewidth")
	private Integer imageWidth;
	
	@Column(name="embedurl")
	private String embedUrl;
	
	@Column(name="embedtype")
	private String embedType;
	
	public ObjectAttachment() {
		
	}
	
	public Long getIdObjectAttachment() {
		return idObjectAttachment;
	}

	public void setIdObjectAttachment(Long idObjectAttachment) {
		this.idObjectAttachment = idObjectAttachment;
	}

	public GooglePlusPost getGooglePlusPost() {
		return googlePlusPost;
	}

	public void setGooglePlusPost(GooglePlusPost googlePlusPost) {
		this.googlePlusPost = googlePlusPost;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

	public Integer getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getEmbedUrl() {
		return embedUrl;
	}

	public void setEmbedUrl(String embedUrl) {
		this.embedUrl = embedUrl;
	}

	public String getEmbedType() {
		return embedType;
	}

	public void setEmbedType(String embedType) {
		this.embedType = embedType;
	}
}
