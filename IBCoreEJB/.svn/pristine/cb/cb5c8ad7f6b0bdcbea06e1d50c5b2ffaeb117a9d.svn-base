package br.com.opsocial.ejb.entity.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="audiencescontacts", schema="opsocial")
public class AudienceContact extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idaudiencecontact")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "audiencescontacts_idaudiencecontact_seq")
	private Long idAudienceContact;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="agency")
	private String agency;
	
	public AudienceContact() {
	}

	public Long getIdAudienceContact() {
		return idAudienceContact;
	}

	public void setIdAudienceContact(Long idAudienceContact) {
		this.idAudienceContact = idAudienceContact;
	}
	
	@Override
	public void setId(Long id) {
		this.idAudienceContact = id;
	}
	
	@Override
	public Long getId() {
		return idAudienceContact;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}
}
