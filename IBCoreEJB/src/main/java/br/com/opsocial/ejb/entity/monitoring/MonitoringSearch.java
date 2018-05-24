package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.MonitoringSearchId;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="monitoringsearches", schema="opsocial")
@IdClass(MonitoringSearchId.class)
public class MonitoringSearch extends Persistent {

private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne()
	@JoinColumn(name="idmonitoring")
	private Monitoring monitoring;

	@Id
	@Column(name = "term")
	private String term;
	
	@Id
	@Column(name = "network")
	private Character network;
	
	@Column(name = "expectedposts")
	private Integer expectedPosts;
	
	@Column(name = "returnedposts")
	private Integer returnedPosts;
	
	@Column(name="lastdatesearched")
	private Long lastDateSearched;
	
	@Column(name="lastdatesearchedwithresults")
	private Long lastDateSearchedWithResults;
	
	@Column(name="cycles")
	private Long cycles;
	
	@Column(name="searches")
	private Long searches;
	
	@Column(name="searcheswithresults")
	private Long searchesWithResults;
	
	public MonitoringSearch() {
		
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Character getNetwork() {
		return network;
	}

	public void setNetwork(Character network) {
		this.network = network;
	}

	public Integer getExpectedPosts() {
		return expectedPosts;
	}

	public void setExpectedPosts(Integer expectedPosts) {
		this.expectedPosts = expectedPosts;
	}

	public Integer getReturnedPosts() {
		return returnedPosts;
	}

	public void setReturnedPosts(Integer returnedPosts) {
		this.returnedPosts = returnedPosts;
	}

	public Long getLastDateSearched() {
		return lastDateSearched;
	}

	public void setLastDateSearched(Long lastDateSearched) {
		this.lastDateSearched = lastDateSearched;
	}

	public Long getLastDateSearchedWithResults() {
		return lastDateSearchedWithResults;
	}

	public void setLastDateSearchedWithResults(Long lastDateSearchedWithResults) {
		this.lastDateSearchedWithResults = lastDateSearchedWithResults;
	}

	public Long getCycles() {
		return cycles;
	}

	public void setCycles(Long cycles) {
		this.cycles = cycles;
	}

	public Long getSearches() {
		return searches;
	}

	public void setSearches(Long searches) {
		this.searches = searches;
	}

	public Long getSearchesWithResults() {
		return searchesWithResults;
	}

	public void setSearchesWithResults(Long searchesWithResults) {
		this.searchesWithResults = searchesWithResults;
	}
	
}
