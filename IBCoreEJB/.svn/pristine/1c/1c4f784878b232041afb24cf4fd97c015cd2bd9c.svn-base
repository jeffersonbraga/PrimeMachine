package br.com.opsocial.ejb.entity.feed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="stream", schema="opsocial")
public class Stream extends Persistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idstream")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "stream_idstream_seq")
	private Long idStream;
	
	@JoinColumn(name = "idprofile", referencedColumnName = "idprofile")
	@ManyToOne()
	private Profile profile;

	@Column(name = "streamorder")
	private Long streamOrder;

	@JoinColumn(name = "idtab", referencedColumnName = "idtab")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private Tab tab;

	public Long getIdStream() {
		return idStream;
	}

	public void setIdStream(Long idStream) {
		this.idStream = idStream;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Long getStreamOrder() {
		return streamOrder;
	}

	public void setStreamOrder(Long streamOrder) {
		this.streamOrder = streamOrder;
	}

	public Tab getTab() {
		return tab;
	}

	public void setTab(Tab tab) {
		this.tab = tab;
	}

	@Override
	public Long getId() {		
		return idStream;
	}

	@Override
	public void setId(Long id) {
		this.idStream = id;	
	}
}
