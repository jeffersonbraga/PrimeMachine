package br.com.opsocial.client.entity.report.instagram;

import java.util.List;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;

public class InstagramPostsStatsYearDTO extends Persistent {

	private static final long serialVersionUID = 1L;

	private Integer year;
	private Long postsTotal;
	private Long likesTotal;
	private Integer averageLikesPerMonth;
	private Long commentsTotal;
	private Integer averageCommentsPerMonth;
	
	private Integer postFrequencyByMonth;

	private List<InstagramSimpleDataDTO> postsPerMonthSimpleData;
	private List<InstagramSimpleDataDTO> likesPerMonthSimpleData;
	private List<InstagramSimpleDataDTO> commentsPerMonthSimpleData;
	
	public InstagramPostsStatsYearDTO() {
		
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Long getPostsTotal() {
		return postsTotal;
	}

	public void setPostsTotal(Long postsTotal) {
		this.postsTotal = postsTotal;
	}

	public Long getLikesTotal() {
		return likesTotal;
	}

	public void setLikesTotal(Long likesTotal) {
		this.likesTotal = likesTotal;
	}

	public Integer getAverageLikesPerMonth() {
		return averageLikesPerMonth;
	}

	public void setAverageLikesPerMonth(Integer averageLikesPerMonth) {
		this.averageLikesPerMonth = averageLikesPerMonth;
	}

	public Long getCommentsTotal() {
		return commentsTotal;
	}

	public void setCommentsTotal(Long commentsTotal) {
		this.commentsTotal = commentsTotal;
	}

	public Integer getAverageCommentsPerMonth() {
		return averageCommentsPerMonth;
	}

	public void setAverageCommentsPerMonth(Integer averageCommentsPerMonth) {
		this.averageCommentsPerMonth = averageCommentsPerMonth;
	}

	public List<InstagramSimpleDataDTO> getPostsPerMonthSimpleData() {
		return postsPerMonthSimpleData;
	}

	public List<InstagramSimpleDataDTO> getLikesPerMonthSimpleData() {
		return likesPerMonthSimpleData;
	}

	public List<InstagramSimpleDataDTO> getCommentsPerMonthSimpleData() {
		return commentsPerMonthSimpleData;
	}

	public void setPostsPerMonthSimpleData(List<InstagramSimpleDataDTO> postsPerMonthSimpleData) {
		this.postsPerMonthSimpleData = postsPerMonthSimpleData;
	}

	public void setLikesPerMonthSimpleData(List<InstagramSimpleDataDTO> likesPerMonthSimpleData) {
		this.likesPerMonthSimpleData = likesPerMonthSimpleData;
	}

	public void setCommentsPerMonthSimpleData(List<InstagramSimpleDataDTO> commentsPerMonthSimpleData) {
		this.commentsPerMonthSimpleData = commentsPerMonthSimpleData;
	}

	public Integer getPostFrequencyByMonth() {
		return postFrequencyByMonth;
	}

	public void setPostFrequencyByMonth(Integer postFrequencyByMonth) {
		this.postFrequencyByMonth = postFrequencyByMonth;
	}	
}