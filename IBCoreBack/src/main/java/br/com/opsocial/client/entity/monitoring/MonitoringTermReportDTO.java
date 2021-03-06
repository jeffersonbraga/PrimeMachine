package br.com.opsocial.client.entity.monitoring;


import java.util.Map;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class MonitoringTermReportDTO extends Persistent {

	private static final long serialVersionUID = 1L;

	private String term;
	private Long totalPosts;
	private Map<Character, Long> sentimentAnalysis;
	private Map<Character, Integer> sentimentAnalysisPerc;
	
	public MonitoringTermReportDTO() {
		
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Long getTotalPosts() {
		return totalPosts;
	}

	public void setTotalPosts(Long totalPosts) {
		this.totalPosts = totalPosts;
	}

	public Map<Character, Long> getSentimentAnalysis() {
		return sentimentAnalysis;
	}

	public void setSentimentAnalysis(Map<Character, Long> sentimentAnalysis) {
		this.sentimentAnalysis = sentimentAnalysis;
	}

	public Map<Character, Integer> getSentimentAnalysisPerc() {
		return sentimentAnalysisPerc;
	}

	public void setSentimentAnalysisPerc(
			Map<Character, Integer> sentimentAnalysisPerc) {
		this.sentimentAnalysisPerc = sentimentAnalysisPerc;
	}
	
}
