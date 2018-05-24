package br.com.opsocial.ejb.entity.reclameaqui;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.ReclameAquiPostMonitoringId;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Entity
@Table(name = "reclameaquipostsmonitorings", schema = "opsocial")
@IdClass(ReclameAquiPostMonitoringId.class)
public class ReclameAquiPostMonitoring extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@JoinColumn(name="cacheid")
	private ReclameAquiPost reclameAquiPost;
	
	@Id
	@ManyToOne()
	@JoinColumn(name="idmonitoring")
	private Monitoring monitoring;

	@Id
	@Column(name = "term")
	private String term;
	
	@Column(name = "qualification")
	private Character qualification;
	
	@Column(name = "retrieveddate")
	private Long retrievedDate;
	
	@Column(name="garbage")
	private Character garbage;
	
	public ReclameAquiPostMonitoring() {
	}

	public ReclameAquiPost getReclameAquiPost() {
		return reclameAquiPost;
	}

	public void setReclameAquiPost(ReclameAquiPost reclameAquiPost) {
		this.reclameAquiPost = reclameAquiPost;
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

	public Character getQualification() {
		return qualification;
	}

	public void setQualification(Character qualification) {
		this.qualification = qualification;
	}

	public Long getRetrievedDate() {
		return retrievedDate;
	}

	public void setRetrievedDate(Long retrievedDate) {
		this.retrievedDate = retrievedDate;
	}

	public Character getGarbage() {
		return garbage;
	}

	public void setGarbage(Character garbage) {
		this.garbage = garbage;
	}
}
