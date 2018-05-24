package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="reportsgenerate", schema="opsocial")
public class ReportGenerate extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idreportgenerate")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "reportsgenerate_idreportgenerate_seq")
	private Long idReportGenerate;
	
	@Column(name="networkid")
	private String networkId;
	
	@Column(name="networktype")
	private Character networkType;
	
	@Column(name="state")
	private Character state;
	
	@Column(name="tokensecret")
	private String tokenSecret;
	
	@Column(name="isinnewinstafollowersreports")
	private Character isInNewInstaFollowersReports;
	
	public ReportGenerate() {
	}
	
	public Long getIdReportGenerate() {
		return idReportGenerate;
	}

	public void setIdReportGenerate(Long idReportGenerate) {
		this.idReportGenerate = idReportGenerate;
	}
	
	@Override
	public Long getId() {
		return idReportGenerate;
	}
	
	@Override
	public void setId(Long id) {
		this.idReportGenerate = id;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public Character getNetworkType() {
		return networkType;
	}

	public void setNetworkType(Character networkType) {
		this.networkType = networkType;
	}

	public Character getState() {
		return state;
	}

	public void setState(Character state) {
		this.state = state;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public Character getIsInNewInstaFollowersReports() {
		return isInNewInstaFollowersReports;
	}

	public void setIsInNewInstaFollowersReports(
			Character isInNewInstaFollowersReports) {
		this.isInNewInstaFollowersReports = isInNewInstaFollowersReports;
	}
}
