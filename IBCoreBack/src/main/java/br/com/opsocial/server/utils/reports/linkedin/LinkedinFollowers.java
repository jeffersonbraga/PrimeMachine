package br.com.opsocial.server.utils.reports.linkedin;

public class LinkedinFollowers {

	private Long organicFollowerCount;
	private Long paidFollowerCount;
	private Long totalFollowerCount;
	private Long time;

	public Long getOrganicFollowerCount() {
		return organicFollowerCount;
	}

	public void setOrganicFollowerCount(Long organicFollowerCount) {
		this.organicFollowerCount = organicFollowerCount;
	}

	public Long getPaidFollowerCount() {
		return paidFollowerCount;
	}

	public void setPaidFollowerCount(Long paidFollowerCount) {
		this.paidFollowerCount = paidFollowerCount;
	}

	public Long getTotalFollowerCount() {
		return totalFollowerCount;
	}

	public void setTotalFollowerCount(Long totalFollowerCount) {
		this.totalFollowerCount = totalFollowerCount;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public LinkedinFollowers() {}
}
