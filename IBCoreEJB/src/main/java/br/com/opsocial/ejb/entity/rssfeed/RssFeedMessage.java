package br.com.opsocial.ejb.entity.rssfeed;

public class RssFeedMessage {
	
	private Long idRssfeedMsg;
	private RssFeed rssFeed;
    private String messageTitle;
    private String messageDescription;
    private String messageContent;
    private String messageLink;
    private String messageAuthor;
    private String messageGuid;
    private String messagePubDate;
    private String messageImgURL;
    private String groupName;

	public String getMessageImgURL() {
		return messageImgURL;
	}

	public void setMessageImgURL(String messageImgURL) {
		this.messageImgURL = messageImgURL;
	}

	public String getMessagePubDate() {
		return messagePubDate;
	}

	public void setMessagePubDate(String messagePubDate) {
		this.messagePubDate = messagePubDate;
	}

	public Long getIdRssfeedMsg() {
		return idRssfeedMsg;
	}

	public void setIdRssfeedMsg(Long idRssfeedMsg) {
		this.idRssfeedMsg = idRssfeedMsg;
	}

	public RssFeed getRssFeed() {
		return rssFeed;
	}

	public void setRssFeed(RssFeed rssFeed) {
		this.rssFeed = rssFeed;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageDescription() {
		return messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}

	public String getMessageLink() {
		return messageLink;
	}

	public void setMessageLink(String messageLink) {
		this.messageLink = messageLink;
	}

	public String getMessageAuthor() {
		return messageAuthor;
	}

	public void setMessageAuthor(String messageAuthor) {
		this.messageAuthor = messageAuthor;
	}

	public String getMessageGuid() {
		return messageGuid;
	}

	public void setMessageGuid(String messageGuid) {
		this.messageGuid = messageGuid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
}
