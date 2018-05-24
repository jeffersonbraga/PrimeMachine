package br.com.opsocial.ejb.entity.twitter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="ratelimitstatus", schema="opsocial")
public class RateLimitStatus extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idratelimitstatus")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ratelimitstatus_idratelimitstatus_seq")
	private Long idRateLimitStatus;
	
	@JoinColumn(name = "idprofile", referencedColumnName = "idprofile")
	@ManyToOne()
	private Profile profile;
	
	@Column(name = "resource")
	private String resource;
	
	@Column(name = "requestslimit")
	private Integer requestsLimit;
	
	@Column(name = "requestsremaining")
	private Integer requestsRemaining;
	
	@Column(name = "resetdate")
	private Long resetDate;
	
	public RateLimitStatus() {
	}

	public Long getIdRateLimitStatus() {
		return idRateLimitStatus;
	}

	public void setIdRateLimitStatus(Long idRateLimitStatus) {
		this.idRateLimitStatus = idRateLimitStatus;
	}
	
	@Override
	public Long getId() {
		return idRateLimitStatus;
	}
	
	@Override
	public void setId(Long id) {
		this.idRateLimitStatus = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Integer getRequestsLimit() {
		return requestsLimit;
	}

	public void setRequestsLimit(Integer requestsLimit) {
		this.requestsLimit = requestsLimit;
	}

	public Integer getRequestsRemaining() {
		return requestsRemaining;
	}

	public void setRequestsRemaining(Integer requestsRemaining) {
		this.requestsRemaining = requestsRemaining;
	}

	public Long getResetDate() {
		return resetDate;
	}

	public void setResetDate(Long resetDate) {
		this.resetDate = resetDate;
	}
}
