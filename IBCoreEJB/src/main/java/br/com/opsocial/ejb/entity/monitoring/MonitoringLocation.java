package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.MonitoringLocationId;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="monitoringlocations", schema="opsocial")
@IdClass(MonitoringLocationId.class)
public class MonitoringLocation extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idlocation")
	private String idLocation;
	
	@Id
	@Column(name = "networktype")
	private Character networkType;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "fullname")
	private String fullName;
	
	@Column(name = "placetype")
	private String placeType;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "countrycode")
	private String countryCode;

	public MonitoringLocation() {
		
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
