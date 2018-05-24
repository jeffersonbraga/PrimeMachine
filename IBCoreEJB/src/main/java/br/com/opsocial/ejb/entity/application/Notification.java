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
@Table(name="notifications", schema="opsocial")
public class Notification extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final String COLOR_SUCCESS = "#48b0f7";
	public static final String COLOR_WARNING = "#f8d053";
	public static final String COLOR_ERROR = "#f55753";

	@Id
	@Column(name = "idnotification")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "notifications_idnotification_seq")
	private Long idNotification;
	
	@JoinColumn(name = "idaccount", referencedColumnName = "idaccount")
	@ManyToOne()
	private Account account;
	
	@JoinColumn(name = "idprofile", referencedColumnName = "idprofile")
	@ManyToOne()
	private Profile profile;
	
	@Column(name="message")
	private String message;
	
	@Column(name="action")
	private String action;
	
	@Column(name="date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column(name="unread")
	private Boolean unread;
	
	@Column(name="color")
	private String color;
	
	public Long getIdNotification() {
		return idNotification;
	}

	public void setIdNotification(Long idNotification) {
		this.idNotification = idNotification;
	}
	
	@Override
	public Long getId() {
		return idNotification;
	}
	
	@Override
	public void setId(Long id) {
		this.idNotification = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getUnread() {
		return unread;
	}

	public void setUnread(Boolean unread) {
		this.unread = unread;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
