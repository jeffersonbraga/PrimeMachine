package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="monitoringspoststags", schema="opsocial")
public class MonitoringPostTag extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idposttag")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringspoststags_idposttag_seq")
	private Long idPostTag;
	
	@JoinColumn(name = "idtag", referencedColumnName = "idtag")
	@ManyToOne()
	private MonitoringTag monitoringTag;

	@Column(name="postid")
	private String postId;
	
	@Column(name = "term")
	private String term;
	
	@Column(name = "idmonitoring")
	private Long idMonitoring;
	
	@Column(name = "network")
	private Character network;

	public MonitoringPostTag() {
		
	}
	
	@Override
	public Long getId() {
		return idPostTag;
	}
	
	@Override
	public void setId(Long id) {
		idPostTag = id;
	}

	public Long getIdPostTag() {
		return idPostTag;
	}

	public void setIdPostTag(Long idPostTag) {
		this.idPostTag = idPostTag;
	}

	public MonitoringTag getMonitoringTag() {
		return monitoringTag;
	}

	public void setMonitoringTag(MonitoringTag monitoringTag) {
		this.monitoringTag = monitoringTag;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Long getIdMonitoring() {
		return idMonitoring;
	}

	public void setIdMonitoring(Long idMonitoring) {
		this.idMonitoring = idMonitoring;
	}

	public Character getNetwork() {
		return network;
	}

	public void setNetwork(Character network) {
		this.network = network;
	}
}
