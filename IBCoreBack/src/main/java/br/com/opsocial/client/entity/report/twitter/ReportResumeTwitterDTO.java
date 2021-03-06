package br.com.opsocial.client.entity.report.twitter;

import java.util.List;
import java.util.Map;

import br.com.opsocial.client.entity.primitive.Date;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;

public class ReportResumeTwitterDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Long> sumsByProperty;

	private List<TwitterReportDTO> retweetsOfMeByDay;
	
	private Long followersMalePerc;
	private Long followersFemalePerc;
	
	private Date dateFrom;
	private Date dateUntil;
	
	private List<Profile> twitterProfilesDTO;
	
	public Map<String, Long> getSumsByProperty() {
		return sumsByProperty;
	}
	
	public void setSumsByProperty(Map<String, Long> sumsByProperty) {
		this.sumsByProperty = sumsByProperty;
	}
	
	public List<TwitterReportDTO> getRetweetsOfMeByDay() {
		return retweetsOfMeByDay;
	}

	public void setRetweetsOfMeByDay(List<TwitterReportDTO> retweetsOfMeByDay) {
		this.retweetsOfMeByDay = retweetsOfMeByDay;
	}

	public Long getFollowersMalePerc() {
		return followersMalePerc;
	}
	
	public void setFollowersMalePerc(Long followersMalePerc) {
		this.followersMalePerc = followersMalePerc;
	}
	
	public Long getFollowersFemalePerc() {
		return followersFemalePerc;
	}
	
	public void setFollowersFemalePerc(Long followersFemalePerc) {
		this.followersFemalePerc = followersFemalePerc;
	}
	
	public Date getDateFrom() {
		return dateFrom;
	}
	
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateUntil() {
		return dateUntil;
	}

	public void setDateUntil(Date dateUntil) {
		this.dateUntil = dateUntil;
	}
	
	public List<Profile> getTwitterProfilesDTO() {
		return twitterProfilesDTO;
	}
	
	public void setTwitterProfilesDTO(List<Profile> twitterProfilesDTO) {
		this.twitterProfilesDTO = twitterProfilesDTO;
	}
}
