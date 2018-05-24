package br.com.opsocial.ejb.entity.instagram;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="instagramdevices", schema="opsocial")
public class InstagramDevice extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idinstagramdevice")
	private Long idInstagramDevice;
	
	@Column(name="deviceuseragent")
	private String deviceUserAgent;
	
	@Column(name="igsigkey")
	private String igSigKey;
	
	@Column(name="manufacturer")
	private String manufacturer;
	
	@Column(name="model")
	private String model;
	
	@Column(name="androidversion")
	private Integer androidVersion;
	
	@Column(name="androidrelease")
	private String androidRelease;
	
	public InstagramDevice() {
	}

	public Long getIdInstagramDevice() {
		return idInstagramDevice;
	}

	public void setIdInstagramDevice(Long idInstagramDevice) {
		this.idInstagramDevice = idInstagramDevice;
	}
	
	@Override
	public Long getId() {
		return idInstagramDevice;
	}
	
	@Override
	public void setId(Long id) {
		idInstagramDevice = id;
	}

	public String getDeviceUserAgent() {
		return deviceUserAgent;
	}

	public void setDeviceUserAgent(String deviceUserAgent) {
		this.deviceUserAgent = deviceUserAgent;
	}

	public String getIgSigKey() {
		return igSigKey;
	}

	public void setIgSigKey(String igSigKey) {
		this.igSigKey = igSigKey;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(Integer androidVersion) {
		this.androidVersion = androidVersion;
	}

	public String getAndroidRelease() {
		return androidRelease;
	}

	public void setAndroidRelease(String androidRelease) {
		this.androidRelease = androidRelease;
	}
}
