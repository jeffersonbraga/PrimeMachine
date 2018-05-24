package br.com.opsocial.client.entity.report.facebook;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class ReportFacebookCityDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private String city;
	private Long value;
	
	public ReportFacebookCityDTO() {
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}
