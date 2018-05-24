package br.com.opsocial.server.utils.monitorings.json;

public class MonitoringQualificationReportIdJSON {
	
	private Long idMonitoring;
	private String term;
	private Character qualification;
	private Character networkType;
	private Long date;
	
	public MonitoringQualificationReportIdJSON() {
		
	}

	public Long getIdMonitoring() {
		return idMonitoring;
	}

	public void setIdMonitoring(Long idMonitoring) {
		this.idMonitoring = idMonitoring;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Character getQualification() {
		return qualification;
	}

	public void setQualification(Character qualification) {
		this.qualification = qualification;
	}

	public Character getNetworkType() {
		return networkType;
	}

	public void setNetworkType(Character networkType) {
		this.networkType = networkType;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

}
