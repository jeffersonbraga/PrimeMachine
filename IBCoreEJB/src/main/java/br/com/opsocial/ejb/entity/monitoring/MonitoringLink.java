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
@Table(name="monitoringlinks", schema="opsocial")
public class MonitoringLink extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idmonitoringlink")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringlinks_idmonitoringlink_seq")
	private Long idMonitoringLink;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@Column(name = "networktype")
	private Character networkType;
	
	@Column(name = "link")
	private String link;
	
	@Column(name = "linkscount")
	private Long linksCount;
	
	@Column(name = "date")
	private Long date;
	
	public MonitoringLink() {
		
	}

	public Long getIdMonitoringLink() {
		return idMonitoringLink;
	}

	public void setIdMonitoringLink(Long idMonitoringLink) {
		this.idMonitoringLink = idMonitoringLink;
	}
	
	@Override
	public Long getId() {
		return idMonitoringLink;
	}
	
	@Override
	public void setId(Long id) {
		idMonitoringLink = id;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Long getLinksCount() {
		return linksCount;
	}

	public void setLinksCount(Long linksCount) {
		this.linksCount = linksCount;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

}
