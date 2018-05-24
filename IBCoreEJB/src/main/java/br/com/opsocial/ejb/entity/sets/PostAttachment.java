package br.com.opsocial.ejb.entity.sets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="postsattachments", schema="opsocial")
public class PostAttachment extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	public final static Character PHOTO = 'P';
	public final static Character VIDEO = 'V';
	
	@Id
	@Column(name = "idpostattachment")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "postsattachments_idpostattachment_seq")
	private Long idPostAttachment;
	
	@JsonIgnore
	@JoinColumn(name = "idpost", referencedColumnName = "idpost")
	private Post post;
	
	@Column(name = "filename")
	private String fileName;
	
	@Column(name = "filetype")
	private Character fileType; 
	
	@Column(name = "height")
	private Integer height;

	@Column(name = "width")
	private Integer width;
	
	@Column(name = "size")
	private Long size;
	
	@Column(name = "extension")
	private String extension;
	
	@Column(name = "duration")
	private Double duration;
	
	@Column(name = "videothumbnail")
	private String videoThumbnail;
	
	@Column(name = "index")
	private Integer index;
	
	@Transient
	private String uploadId;
	
	@Version
    @Column(nullable = false)
    private Long version;
	    
    @Override
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
	
	public PostAttachment() {
	}

	public Long getIdPostAttachment() {
		return idPostAttachment;
	}

	public void setIdPostAttachment(Long idPostAttachment) {
		this.idPostAttachment = idPostAttachment;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Character getFileType() {
		return fileType;
	}

	public void setFileType(Character fileType) {
		this.fileType = fileType;
	}
	

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}
	
	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	@Override
	public Long getId() {
		return idPostAttachment;
	}
	
	@Override
	public void setId(Long id) {
		idPostAttachment = id;
	}

	public static Character getPhoto() {
		return PHOTO;
	}

	public static Character getVideo() {
		return VIDEO;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getVideoThumbnail() {
		return videoThumbnail;
	}

	public void setVideoThumbnail(String videoThumbnail) {
		this.videoThumbnail = videoThumbnail;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
