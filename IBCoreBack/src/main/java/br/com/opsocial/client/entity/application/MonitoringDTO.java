package br.com.opsocial.client.entity.application;

import java.util.List;

import br.com.opsocial.client.entity.monitoring.MonitoringTagDTO;
import br.com.opsocial.client.entity.monitoring.SampleDTO;
import br.com.opsocial.client.entity.report.ReportLogoDTO;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;

public class MonitoringDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final Character EXACT_TERM = 'E';
	public static final Character OPEN_TERM = 'O';
	
	public static final Character PAUSED = 'P';
	public static final Character RUNNING = 'R';
	public static final Character DELETED = 'D';
	
	private Long idMonitoring;
	private String name;
	private String terms;
	private String ignoreTerms;
	private String autoPositiveTerms;
	private String autoNeuterTerms;
	private String autoNegativeTerms;
	private Character type;
	private Long createdAt;
	private String language;
	private Account account;
	private Character isFirstSearch = 'T';
	private String userIp;
	private ReportLogoDTO reportLogo;
	private Character state = 'R';
	private List<MonitoringTagDTO> monitoringTags;
	private List<SampleDTO> samples;
	private Boolean applyQualificationToSamePosts = false;
	private Boolean applyTagsToSamePosts = false;
	private List<Profile> groups;
	
	public Long getIdMonitoring() {
		return idMonitoring;
	}
	
	public void setIdMonitoring(Long idMonitoring) {
		this.idMonitoring = idMonitoring;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTerms() {
		return terms;
	}
	
	public void setTerms(String terms) {
		this.terms = terms;
	}
	
	public String getIgnoreTerms() {
		return ignoreTerms;
	}

	public void setIgnoreTerms(String ignoreTerms) {
		this.ignoreTerms = ignoreTerms;
	}

	public String getAutoPositiveTerms() {
		return autoPositiveTerms;
	}

	public void setAutoPositiveTerms(String autoPositiveTerms) {
		this.autoPositiveTerms = autoPositiveTerms;
	}

	public String getAutoNeuterTerms() {
		return autoNeuterTerms;
	}

	public void setAutoNeuterTerms(String autoNeuterTerms) {
		this.autoNeuterTerms = autoNeuterTerms;
	}

	public String getAutoNegativeTerms() {
		return autoNegativeTerms;
	}

	public void setAutoNegativeTerms(String autoNegativeTerms) {
		this.autoNegativeTerms = autoNegativeTerms;
	}

	public Character getType() {
		return type;
	}
	
	public void setType(Character type) {
		this.type = type;
	}
	
	public Long getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Character getIsFirstSearch() {
		return isFirstSearch;
	}

	public void setIsFirstSearch(Character isFirstSearch) {
		this.isFirstSearch = isFirstSearch;
	}

	public List<MonitoringTagDTO> getMonitoringTags() {
		return monitoringTags;
	}

	public void setMonitoringTags(List<MonitoringTagDTO> monitoringTags) {
		this.monitoringTags = monitoringTags;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public ReportLogoDTO getReportLogo() {
		return reportLogo;
	}

	public void setReportLogo(ReportLogoDTO reportLogo) {
		this.reportLogo = reportLogo;
	}

	public Character getState() {
		return state;
	}

	public void setState(Character state) {
		this.state = state;
	}

	public List<SampleDTO> getSamples() {
		return samples;
	}

	public void setSamples(List<SampleDTO> samples) {
		this.samples = samples;
	}

	public Boolean getApplyQualificationToSamePosts() {
		return applyQualificationToSamePosts;
	}

	public void setApplyQualificationToSamePosts(
			Boolean applyQualificationToSamePosts) {
		this.applyQualificationToSamePosts = applyQualificationToSamePosts;
	}

	public Boolean getApplyTagsToSamePosts() {
		return applyTagsToSamePosts;
	}

	public void setApplyTagsToSamePosts(Boolean applyTagsToSamePosts) {
		this.applyTagsToSamePosts = applyTagsToSamePosts;
	}

	public List<Profile> getGroups() {
		return groups;
	}

	public void setGroups(List<Profile> groups) {
		this.groups = groups;
	}

	public void setReportLogo(Object mountReportLogo) {
		// TODO Auto-generated method stub
		
	}
}
