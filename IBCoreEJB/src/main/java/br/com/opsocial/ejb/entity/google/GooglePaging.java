package br.com.opsocial.ejb.entity.google;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Entity
@Table(name="googlepagings", schema="opsocial")
public class GooglePaging extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final Character GOOGLE = 'G';
	public static final Character YOUTUBE = 'Y'; 
	
	@Id
	@Column(name = "idgooglepaging")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "googlepagings_idgooglepaging_seq")
	private Long idGooglePaging;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@Column(name = "term")
	private String term;
	
	@Column(name="network")
	private Character network;
	
	@Column(name="nextpagetoken")
	private String nextPageToken;
	
	@Column(name="etag")
	private String etag;

	@Column(name="count")
	private Long count;
	
	public GooglePaging() {
	}

	public Long getIdGooglePaging() {
		return idGooglePaging;
	}

	public void setIdGooglePaging(Long idGooglePaging) {
		this.idGooglePaging = idGooglePaging;
	}
	
	@Override
	public Long getId() {
		return idGooglePaging;
	}
	
	@Override
	public void setId(Long id) {
		idGooglePaging = id;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Character getNetwork() {
		return network;
	}

	public void setNetwork(Character network) {
		this.network = network;
	}

	public String getNextPageToken() {
		return nextPageToken;
	}

	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
}
