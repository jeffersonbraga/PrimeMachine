package br.com.opsocial.ejb.entity.mailbox;

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
@Table(name="messages", schema="opsocial")
public class Message extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idmessage")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "messages_idmessage_seq")
	private Long idMessage;
	
	@Column(name = "networkmessageid")
	private String networkMessageId;
	
	@Column(name = "createdtime")
	private Long createdTime;

	@Column(name = "username")
	private String userName;

	@Column(name = "userid")
	private String userId;

	@Column(name = "message")
	private String message;
	
	@JoinColumn(name = "idconversation", referencedColumnName = "idconversation")
	@ManyToOne()
	private Conversation conversation;

	public Message() {
	}

	public Long getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(Long idMessage) {
		this.idMessage = idMessage;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public String getNetworkMessageId() {
		return networkMessageId;
	}

	public void setNetworkMessageId(String networkMessageId) {
		this.networkMessageId = networkMessageId;
	}

	@Override
	public Long getId() {		
		return idMessage;
	}

	@Override
	public void setId(Long id) {
		this.idMessage = id;		
	}
}
