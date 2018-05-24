package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="monitorings", schema="opsocial")
public class Monitoring extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final Character PAUSED = 'P';
	public static final Character RUNNING = 'R';
	public static final Character DELETED = 'D';

	@Id
	@Column(name = "idmonitoring")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitorings_idmonitoring_seq")
	private Long idMonitoring;
	
	@JoinColumn(name = "idaccount", referencedColumnName = "idaccount")
	@ManyToOne()
	private Account account;

	@Column(name="name")
	private String name;
	
	@Column(name="terms")
	private String terms;

	@Column(name="ignoreterms")
	private String ignoreTerms;
	
	@Column(name="autopositiveterms")
	private String autoPositiveTerms;
	
	@Column(name="autoneuterterms")
	private String autoNeuterTerms;
	
	@Column(name="autonegativeterms")
	private String autoNegativeTerms;
	
	@Column(name="type")
	private Character type;
	
	@Column(name="createdat")
	private Long createdAt;
	
	@Column(name="language")
	private String language;

	@Column(name="isfirstsearch")
	private Character isFirstSearch = 'T';
	
	@Column(name="userip")
	private String userIp;

	@Column(name="state")
	private Character state = 'R';
	
	@Column(name="applyqualificationtosameposts")
	private Boolean applyQualificationToSamePosts = false;
	
	@Column(name="applytagstosameposts")
	private Boolean applyTagsToSamePosts = false;

	public Monitoring() {
	}
	
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
	
	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public Character getState() {
		return state;
	}

	public void setState(Character state) {
		this.state = state;
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

	@Override
	public Long getId() {
		return idMonitoring;
	}
	
	@Override
	public void setId(Long id) {
		idMonitoring = id;
	}
}
