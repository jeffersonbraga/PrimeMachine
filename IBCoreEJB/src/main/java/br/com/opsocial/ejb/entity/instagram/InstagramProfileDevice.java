package br.com.opsocial.ejb.entity.instagram;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.ProxyRegion;
import br.com.opsocial.ejb.entity.application.idclass.InstagramProfileDeviceId;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name = "instagramprofilesdevices", schema = "opsocial")
@IdClass(InstagramProfileDeviceId.class)
public class InstagramProfileDevice extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "instagramuserid")
	private String instagramUserId;
	
	@Id
	@ManyToOne()
	@JoinColumn(name="idaccount")
	private Account account;
	
	@JoinColumn(name = "idproxyregion", referencedColumnName = "idproxyregion")
	@ManyToOne
	private ProxyRegion proxyRegion;
	
	@JoinColumn(name = "idinstagramdevice", referencedColumnName = "idinstagramdevice")
	@ManyToOne
	private InstagramDevice instagramDevice;
	
	@Column(name="instagramguid")
    private String instagramGUID;
    
    @Column(name="instagramdeviceid")
    private String instagramDeviceId;
    
    @Column(name="instagramphoneid")
    private String instagramPhoneId;
    
    @Column(name = "port")
    private Integer port;
    
    public InstagramProfileDevice() {
	}

	public String getInstagramUserId() {
		return instagramUserId;
	}

	public void setInstagramUserId(String instagramUserId) {
		this.instagramUserId = instagramUserId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ProxyRegion getProxyRegion() {
		return proxyRegion;
	}

	public void setProxyRegion(ProxyRegion proxyRegion) {
		this.proxyRegion = proxyRegion;
	}

	public InstagramDevice getInstagramDevice() {
		return instagramDevice;
	}

	public void setInstagramDevice(InstagramDevice instagramDevice) {
		this.instagramDevice = instagramDevice;
	}

	public String getInstagramGUID() {
		return instagramGUID;
	}

	public void setInstagramGUID(String instagramGUID) {
		this.instagramGUID = instagramGUID;
	}

	public String getInstagramDeviceId() {
		return instagramDeviceId;
	}

	public void setInstagramDeviceId(String instagramDeviceId) {
		this.instagramDeviceId = instagramDeviceId;
	}

	public String getInstagramPhoneId() {
		return instagramPhoneId;
	}

	public void setInstagramPhoneId(String instagramPhoneId) {
		this.instagramPhoneId = instagramPhoneId;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
}
