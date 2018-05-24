package br.com.opsocial.ejb.entity.report;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class TwitterReportStatus extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private String idStatus;
	private String idProfile;
	private String text;
	private Long createdAt;
	private String statusUrl;
	private Long repliesCount;
	private Long retweetsCount;
	
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
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
	/**
	 * @return the statusUrl
	 */
	public String getStatusUrl() {
		return statusUrl;
	}
	/**
	 * @param statusUrl the statusUrl to set
	 */
	public void setStatusUrl(String statusUrl) {
		this.statusUrl = statusUrl;
	}
	/**
	 * @return the repliesCount
	 */
	public Long getRepliesCount() {
		return repliesCount;
	}
	/**
	 * @param repliesCount the repliesCount to set
	 */
	public void setRepliesCount(Long repliesCount) {
		this.repliesCount = repliesCount;
	}
	/**
	 * @return the retweetsCount
	 */
	public Long getRetweetsCount() {
		return retweetsCount;
	}
	/**
	 * @param retweetsCount the retweetsCount to set
	 */
	public void setRetweetsCount(Long retweetsCount) {
		this.retweetsCount = retweetsCount;
	}

}
