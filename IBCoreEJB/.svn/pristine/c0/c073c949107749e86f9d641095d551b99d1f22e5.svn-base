package br.com.opsocial.ejb.entity.monitoring;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="samples", schema="opsocial")
public class Sample extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idsample")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "samples_idsample_seq")
	private Long idSample;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "error")
	private Float error;
	
	@Column(name = "trustlevel")
	private Float trustLevel;
	
	@Column(name = "startdate")
    @Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Column(name = "enddate")
    @Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@Column(name = "sample")
	private Integer sample;	
	
	@Column(name = "networks")
	private String networks;
	
	@Column(name = "qualifications")
	private String qualifications;
	
	@Column(name = "tags")
	private String tags;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdate")
	private Date createDate;

	@Column(name = "population")
    private Long population;

	public Sample() {
	}

	public Long getIdSample() {
		return idSample;
	}

	public void setIdSample(Long idSample) {
		this.idSample = idSample;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getError() {
		return error;
	}

	public void setError(Float error) {
		this.error = error;
	}

	public Float getTrustLevel() {
		return trustLevel;
	}

	public void setTrustLevel(Float trustLevel) {
		this.trustLevel = trustLevel;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getSample() {
		return sample;
	}

	public void setSample(Integer sample) {
		this.sample = sample;
	}

	public String getNetworks() {
		return networks;
	}

	public void setNetworks(String networks) {
		this.networks = networks;
	}

	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getPopulation() {
		return population;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}
