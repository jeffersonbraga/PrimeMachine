package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MonitoringUserId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "iduser")
	private String idUser;
	
	@Column(name = "networktype")
	private Character networkType;
	
	public MonitoringUserId() {
		
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public Character getNetworkType() {
		return networkType;
	}

	public void setNetworkType(Character networkType) {
		this.networkType = networkType;
	}

}
