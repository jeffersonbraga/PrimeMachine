package br.com.opsocial.ejb.entity.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="proxyregions", schema="opsocial")
public class ProxyRegion extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final String UNITED_STATES = "United States";
	public static final String UNITED_KINGDOM = "United Kingdom";
	public static final String THAILAND = "Thailand";
	public static final String BRAZIL = "Brazil";
	public static final String MEXICO = "Mexico";
	public static final String ITALY = "Italy";
	public static final String SPAIN = "Spain";
	public static final String JAPAN = "Japan";
	public static final String AUSTRALIA = "Australia";
	public static final String POLAND = "Poland";
	public static final String CANADA = "Canada";
	public static final String GERMANY = "Germany";
	public static final String FRANCE = "France";
	public static final String INDIA = "India";
	
	public static final Integer ONE_MINUTE = 1;
	public static final Integer TEN_MINUTES = 10;
	public static final Integer THIRTY_MINUTES = 30;
	
	@Id
	@Column(name="idproxyregion")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "proxyregions_idproxyregion_seq")
	private Long idProxyRegion;
	
	@Column(name="country")
	private String country;
	
	@Column(name="serveraddress")
	private String serverAddress;
	
	@Column(name="initialip")
	private Integer initialIp;
	
	@Column(name="finalip")
	private Integer finalIp;
	
	@Column(name="stickipduration")
	private Integer stickIpDuration;
	
	@Column(name="acceptlanguageheader")
	private String acceptLanguageHeader;
	
	@Column(name="devicelanguage")
	private String deviceLanguage;
	
	@Column(name="serverusecount")
	private Long serverUseCount;
	
	public ProxyRegion() {
	}

	public Long getIdProxyRegion() {
		return idProxyRegion;
	}

	public void setIdProxyRegion(Long idProxyRegion) {
		this.idProxyRegion = idProxyRegion;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public Integer getInitialIp() {
		return initialIp;
	}

	public void setInitialIp(Integer initialIp) {
		this.initialIp = initialIp;
	}

	public Integer getFinalIp() {
		return finalIp;
	}

	public void setFinalIp(Integer finalIp) {
		this.finalIp = finalIp;
	}

	public Integer getStickIpDuration() {
		return stickIpDuration;
	}

	public void setStickIpDuration(Integer stickIpDuration) {
		this.stickIpDuration = stickIpDuration;
	}

	public String getAcceptLanguageHeader() {
		return acceptLanguageHeader;
	}

	public void setAcceptLanguageHeader(String acceptLanguageHeader) {
		this.acceptLanguageHeader = acceptLanguageHeader;
	}

	public String getDeviceLanguage() {
		return deviceLanguage;
	}

	public void setDeviceLanguage(String deviceLanguage) {
		this.deviceLanguage = deviceLanguage;
	}

	public Long getServerUseCount() {
		return serverUseCount;
	}

	public void setServerUseCount(Long serverUseCount) {
		this.serverUseCount = serverUseCount;
	}
	
}
