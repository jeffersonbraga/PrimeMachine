package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MonitoringLocationId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "idlocation")
	private String idLocation;
	
	@Column(name = "networktype")
	private Character networkType;

	public MonitoringLocationId() {
		
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
	
}
