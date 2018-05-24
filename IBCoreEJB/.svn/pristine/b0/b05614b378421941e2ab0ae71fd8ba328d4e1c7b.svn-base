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
@Table(name="monitoringhashtags", schema="opsocial")
public class MonitoringHashTag extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idmonitoringhashtag")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringhashtags_idmonitoringhashtag_seq")
	private Long idMonitoringHashTag;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@Column(name = "networktype")
	private Character networkType;
	
	@Column(name = "hashtag")
	private String hashTag;
	
	@Column(name = "hashtagscount")
	private Long hashTagsCount;
	
	@Column(name = "date")
	private Long date;

	public MonitoringHashTag() {
		
	}

	public Long getIdMonitoringHashTag() {
		return idMonitoringHashTag;
	}

	public void setIdMonitoringHashTag(Long idMonitoringHashTag) {
		this.idMonitoringHashTag = idMonitoringHashTag;
	}

	@Override
	public Long getId() {
		return idMonitoringHashTag;
	}
	
	@Override
	public void setId(Long id) {
		idMonitoringHashTag = id;
	}
	
	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public Character getNetworkType() {
		return networkType;
	}

	public void setNetworkType(Character networkType) {
		this.networkType = networkType;
	}

	public String getHashTag() {
		return hashTag;
	}

	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}

	public Long getHashTagsCount() {
		return hashTagsCount;
	}

	public void setHashTagsCount(Long hashTagsCount) {
		this.hashTagsCount = hashTagsCount;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
	
}
