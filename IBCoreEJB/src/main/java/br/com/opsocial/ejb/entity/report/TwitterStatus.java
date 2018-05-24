package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="twitterstatus", schema="opsocial")
public class TwitterStatus extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idtwitterstatus")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "twitterstatus_idtwitterstatus_seq")
	private Long idTwitterStatus;

	@Column(name = "idstatus")
	private String idStatus;
	
	@Column(name = "idprofile")
	private String idProfile;
	
	@Column(name="createdat")
	private Long createdAt;
	
	@Column(name="text")
	private String text;
	
	@Column(name="mediaurl")
	private String mediaUrl;
	
	/**
	 * @return the idTwitterStatus
	 */
	public Long getIdTwitterStatus() {
		return idTwitterStatus;
	}

	/**
	 * @param idTwitterStatus the idTwitterStatus to set
	 */
	public void setIdTwitterStatus(Long idTwitterStatus) {
		this.idTwitterStatus = idTwitterStatus;
	}
	
	@Override
	public Long getId() {
		return idTwitterStatus;
	}
	
	@Override
	public void setId(Long id) {
		this.idTwitterStatus = id;
	}

	/**
	 * @return the idStatus
	 */
	public String getIdStatus() {
		return idStatus;
	}

	/**
	 * @param idStatus the idStatus to set
	 */
	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	/**
	 * @return the idProfile
	 */
	public String getIdProfile() {
		return idProfile;
	}

	/**
	 * @param idProfile the idProfile to set
	 */
	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
	}

	/**
	 * @return the createdAt
	 */
	public Long getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
}
