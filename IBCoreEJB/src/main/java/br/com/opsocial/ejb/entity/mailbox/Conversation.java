package br.com.opsocial.ejb.entity.mailbox;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="conversations", schema="opsocial")
public class Conversation extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idconversation")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "conversations_idconversation_seq")
	private Long idConversation;
	
	@JoinColumn(name = "idprofile", referencedColumnName = "idprofile")
	@ManyToOne()
	private Profile profile;
	
	@Column(name="unread")
	private Boolean unread = false;
	
	@Column(name = "networkconversationid")
	private String networkConversationId;

	@Column(name = "snippet")
	private String snippet;

	@Column(name = "updatedtime")
	private Long updatedTime;

	@Column(name = "messagecount")
	private Integer messageCount;

	@Column(name = "username")
	private String userName;

	@Column(name = "userid")
    private String userId;
	
	@Column(name = "useravatar")
	private String userAvatar;

	@Column(name = "canreply")
    private Boolean canReply;

	@Column(name = "issubscribed")
    private Boolean isSubscribed;
	
	@Transient
	private List<Message> messages;

	public Conversation() {
		
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
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

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public Boolean getCanReply() {
		return canReply;
	}

	public void setCanReply(Boolean canReply) {
		this.canReply = canReply;
	}

	public Boolean getIsSubscribed() {
		return isSubscribed;
	}

	public void setIsSubscribed(Boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Long getIdConversation() {
		return idConversation;
	}

	public void setIdConversation(Long idConversation) {
		this.idConversation = idConversation;
	}

	public String getNetworkConversationId() {
		return networkConversationId;
	}

	public void setNetworkConversationId(String networkConversationId) {
		this.networkConversationId = networkConversationId;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Boolean getUnread() {
		return unread;
	}

	public void setUnread(Boolean unread) {
		this.unread = unread;
	}
	
	@Override
	public Long getId() {		
		return idConversation;
	}

	@Override
	public void setId(Long id) {
		this.idConversation = id;		
	}

}
