package br.com.opsocial.server.utils;

public class GenericDateInterval {
	
	private Long dateFrom;
	private Long dateUntil;
	
	public GenericDateInterval() {
	}
	
	public GenericDateInterval(Long dateFrom, Long dateUntil) {
		
		this.dateFrom = dateFrom;
		this.dateUntil = dateUntil;
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
}
