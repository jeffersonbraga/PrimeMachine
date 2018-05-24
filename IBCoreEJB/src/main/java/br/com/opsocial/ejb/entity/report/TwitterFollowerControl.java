package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="twitterfollowerscontrol", schema="opsocial")
public class TwitterFollowerControl extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idprofile")
	private Long idProfile;
	
	@Column(name="nextcursor")
	private Long nextCursor;
	
	@Column(name="state")
	private Character state;
	
	@Column(name="date")
	private Long date;
	
	public TwitterFollowerControl() {
	}

	public Long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}
	
	@Override
	public Long getId() {
		return idProfile;
	}
	
	@Override
	public void setId(Long id) {
		this.idProfile = id;
	}

	public Long getNextCursor() {
		return nextCursor;
	}

	public void setNextCursor(Long nextCursor) {
		this.nextCursor = nextCursor;
	}

	public Character getState() {
		return state;
	}

	public void setState(Character state) {
		this.state = state;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
}
