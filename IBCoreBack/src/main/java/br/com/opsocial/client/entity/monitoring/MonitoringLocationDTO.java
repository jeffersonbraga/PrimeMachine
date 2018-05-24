package br.com.opsocial.client.entity.monitoring;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class MonitoringLocationDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private String idLocation;
	private Character networkType;
	private String name;
	private String fullName;
	private String placeType;
	private String country;
	private String countryCode;
	
	public MonitoringLocationDTO() {
		
	}

	public String getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(String idLocation) {
		this.idLocation = idLocation;
	}

	public Character getNetworkType() {
		return networkType;
	}

	public void setNetworkType(Character networkType) {
		this.networkType = networkType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
}
