package br.com.opsocial.ejb.entity.youtube;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="youtubeposts", schema="opsocial")
public class YoutubePost extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "videoid")
	private String videoId;
	
	@Column(name="kind")
	private String kind;
	
	@Transient
	private String url;
	
	@Column(name="description")
	private String description;
	
	@Column(name="publishedat")
	private Long publishedAt;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "channelid")
	private String channelId;
	
	@Column(name = "channeltitle")
	private String channelTitle;
	
	@Transient
	private String channelUrl;
	
	@Column(name = "thumbnailurl")
	private String thumbnailUrl;
	
	@Column(name = "thumbnailheight")
	private Integer thumbnailHeight;

	@Column(name = "thumbnailwidth")
	private Integer thumbnailWidth;
	
	@Column(name = "views")
	private Long views = 0L;
	
	@Column(name = "likes")
	private Long likes = 0L;
	
	@Column(name = "dislikes")
	private Long dislikes = 0L;
	
	@Column(name = "favorites")
	private Long favorites = 0L;
	
	@Column(name = "comments")
	private Long comments = 0L;
	
	@Column(name="lastupdate")
    @Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	@Column(name="channelpicture")
	private String channelPicture;
	
	public YoutubePost() {
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
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

	public Long getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Long publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChanneltitle() {
		return channelTitle;
	}

	public void setChanneltitle(String channeltitle) {
		this.channelTitle = channeltitle;
	}

	public String getChannelUrl() {
		return channelUrl;
	}

	public void setChannelUrl(String channelUrl) {
		this.channelUrl = channelUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public Integer getThumbnailHeight() {
		return thumbnailHeight;
	}

	public void setThumbnailHeight(Integer thumbnailHeight) {
		this.thumbnailHeight = thumbnailHeight;
	}

	public Integer getThumbnailwidth() {
		return thumbnailWidth;
	}

	public void setThumbnailwidth(Integer thumbnailwidth) {
		this.thumbnailWidth = thumbnailwidth;
	}
	
	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}

	public Integer getThumbnailWidth() {
		return thumbnailWidth;
	}

	public void setThumbnailWidth(Integer thumbnailWidth) {
		this.thumbnailWidth = thumbnailWidth;
	}

	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getDislikes() {
		return dislikes;
	}

	public void setDislikes(Long dislikes) {
		this.dislikes = dislikes;
	}

	public Long getFavorites() {
		return favorites;
	}

	public void setFavorites(Long favorites) {
		this.favorites = favorites;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getChannelPicture() {
		return channelPicture;
	}

	public void setChannelPicture(String channelPicture) {
		this.channelPicture = channelPicture;
	}
}
