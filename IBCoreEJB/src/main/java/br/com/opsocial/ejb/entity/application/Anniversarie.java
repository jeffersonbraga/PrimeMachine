package br.com.opsocial.ejb.entity.application;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="anniversaries", schema="opsocial")
public class Anniversarie extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idanniversarie")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "anniversaries_idanniversarie_seq")
	private Long idAnniversarie;
	
	@Column(name="name")
	private String name;
	
    @Column(name="date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date date;
    
    @Column(name="isdatevariable")
    private Boolean isDateVariable;

	public Anniversarie() {
	}
	
	public Long getIdAnniversarie() {
		return idAnniversarie;
	}

	public void setIdAnniversarie(Long idAnniversarie) {
		this.idAnniversarie = idAnniversarie;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Boolean getIsDateVariable() {
		return isDateVariable;
	}

	public void setIsDateVariable(Boolean isDateVariable) {
		this.isDateVariable = isDateVariable;
	}

	@Override
	public void setId(Long id) {
		this.idAnniversarie = id;
	}
	
	@Override
	public Long getId() {
		return idAnniversarie;
	}
}
