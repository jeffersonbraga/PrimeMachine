package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.MonitoringUserId;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="monitoringusers", schema="opsocial")
@IdClass(MonitoringUserId.class)
public class MonitoringUser extends Persistent {

	private static final long serialVersionUID = 1L;

	public static final String FEMALE = "female";
	public static final String MALE = "male";
	
	@Id
	@Column(name = "iduser")
	private String idUser;
	
	@Id
	@Column(name = "networktype")
	private Character networkType;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "profileurl")
	private String profileUrl;
	
	@Column(name = "profileimageurl")
	private String profileImageUrl;
	
	@Column(name = "sex")
	private String sex;
	
	public MonitoringUser() {
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
