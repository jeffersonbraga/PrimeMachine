package br.com.opsocial.server.utils.monitorings.json;

public class MonitoringReportJSON {
	
	private Long idMonitoring;
	private Character networkType;
	private Long date;
	private String periodType;
	private String period;
	private Long value;
	private Character garbage;
	
	public MonitoringReportJSON() {
		
	}

	public Long getIdMonitoring() {
		return idMonitoring;
	}

	public void setIdMonitoring(Long idMonitoring) {
		this.idMonitoring = idMonitoring;
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

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Character getGarbage() {
		return garbage;
	}

	public void setGarbage(Character garbage) {
		this.garbage = garbage;
	}
}
