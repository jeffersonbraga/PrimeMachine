package br.com.opsocial.client.entity.report.linkedin;

import java.util.List;
import java.util.Map;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.linkedin.LinkedinPost;
import br.com.opsocial.ejb.entity.report.SumByDate;
import br.com.opsocial.server.utils.reports.linkedin.LinkedinCompanyImpressionsByDay;
import br.com.opsocial.server.utils.reports.linkedin.LinkedinFollowStatistics;
import br.com.opsocial.server.utils.reports.linkedin.LinkedinFollowers;
import br.com.opsocial.server.utils.reports.linkedin.LinkedinStatusUpdate;

public class ReportLinkedinOverviewDTO {
	
	private Long dateFrom;
	private Long dateUntil;
	private Profile profile;
	private List<LinkedinFollowers> linkedinFollowers;
	private List<LinkedinCompanyImpressionsByDay> impressionsByDay;
	private List<LinkedinStatusUpdate> linkedinStatusUpdate;
	private List<LinkedinPost> linkedinPosts;
	private LinkedinFollowStatistics followStatistics;
	private List<SumByDate> linkedinSharesByDay;
	private LinkedinFollowers linkedinMaxGrowthPeriod;
	private Double linkedinPostAverageByDay;
	private Map<String, Integer> linkedinFollowersByCountry;

	public LinkedinFollowStatistics getFollowStatistics() {
		return followStatistics;
	}

	public void setFollowStatistics(LinkedinFollowStatistics followStatistics) {
		this.followStatistics = followStatistics;
	}

	public List<LinkedinPost> getLinkedinPosts() {
		return linkedinPosts;
	}

	public void setLinkedinPosts(List<LinkedinPost> linkedinPosts) {
		this.linkedinPosts = linkedinPosts;
	}

	public List<LinkedinStatusUpdate> getLinkedinStatusUpdate() {
		return linkedinStatusUpdate;
	}

	public void setLinkedinStatusUpdate(List<LinkedinStatusUpdate> linkedinStatusUpdate) {
		this.linkedinStatusUpdate = linkedinStatusUpdate;
	}

	public List<LinkedinFollowers> getLinkedinFollowers() {
		return linkedinFollowers;
	}

	public void setLinkedinFollowers(List<LinkedinFollowers> linkedinFollowers) {
		this.linkedinFollowers = linkedinFollowers;
	}

	public List<LinkedinCompanyImpressionsByDay> getImpressionsByDay() {
		return impressionsByDay;
	}

	public void setImpressionsByDay(List<LinkedinCompanyImpressionsByDay> impressionsByDay) {
		this.impressionsByDay = impressionsByDay;
	}

	public ReportLinkedinOverviewDTO() {}

	public List<SumByDate> getLinkedinSharesByDay() {
		return linkedinSharesByDay;
	}

	public void setLinkedinSharesByDay(List<SumByDate> linkedinSharesByDay) {
		this.linkedinSharesByDay = linkedinSharesByDay;
	}

	public LinkedinFollowers getLinkedinMaxGrowthPeriod() {
		return linkedinMaxGrowthPeriod;
	}

	public void setLinkedinMaxGrowthPeriod(LinkedinFollowers linkedinMaxGrowthPeriod) {
		this.linkedinMaxGrowthPeriod = linkedinMaxGrowthPeriod;
	}

	public Double getLinkedinPostAverageByDay() {
		return linkedinPostAverageByDay;
	}

	public void setLinkedinPostAverageByDay(Double linkedinPostAverageByDay) {
		this.linkedinPostAverageByDay = linkedinPostAverageByDay;
	}

	public Map<String, Integer> getLinkedinFollowersByCountry() {
		return linkedinFollowersByCountry;
	}

	public void setLinkedinFollowersByCountry(Map<String, Integer> linkedinFollowersByCountry) {
		this.linkedinFollowersByCountry = linkedinFollowersByCountry;
	}

	public Long getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Long dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Long getDateUntil() {
		return dateUntil;
	}

	public void setDateUntil(Long dateUntil) {
		this.dateUntil = dateUntil;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
