package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="monitoringstags", schema="opsocial")
public class MonitoringTag extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idtag")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringstags_idtag_seq")
	private Long idTag;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring", nullable = true)
	@ManyToOne(optional = true)
	private Monitoring monitoring;

	@JoinColumn(name = "idaccount", referencedColumnName = "idaccount")
	@ManyToOne()
	private Account account;
	
	@JoinColumn(name = "idcolortag", referencedColumnName = "idcolortag")
	@ManyToOne()
	private ColorTag colorTag;
	
	@Column(name="name")
	private String name;
	
	public MonitoringTag() {
	}
	
	@Override
	public Long getId() {
		return idTag;
	}
	
	@Override
	public void setId(Long id) {
		idTag = id;
	}

	public Long getIdTag() {
		return idTag;
	}

	public void setIdTag(Long idTag) {
		this.idTag = idTag;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ColorTag getColorTag() {
		return colorTag;
	}

	public void setColorTag(ColorTag colorTag) {
		this.colorTag = colorTag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
