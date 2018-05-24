package br.com.opsocial.ejb.entity.application;

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
@Table(name="suggestions", schema="opsocial")
public class Suggestion extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idsuggestion")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "suggestions_idsuggestion_seq")
	private Long idSuggestion;
	
	@Column(name="text")
	private String text;
	
	@Column(name="senddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;
	
	@Column(name="emailwassent")
	private Boolean emailWasSent;
	
	@JoinColumn(name = "iduser", referencedColumnName = "iduser")
	@ManyToOne()
	private User user;
	
	public Long getIdSuggestion() {
		return idSuggestion;
	}

	public void setIdSuggestion(Long idSuggestion) {
		this.idSuggestion = idSuggestion;
	}

	@Override
	public void setId(Long id) {
		this.idSuggestion = id;
	}
	
	@Override
	public Long getId() {
		return idSuggestion;
	}

	public Suggestion() {
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Boolean getEmailWasSent() {
		return emailWasSent;
	}

	public void setEmailWasSent(Boolean emailWasSent) {
		this.emailWasSent = emailWasSent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
