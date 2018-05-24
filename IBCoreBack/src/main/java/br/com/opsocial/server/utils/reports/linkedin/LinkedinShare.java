package br.com.opsocial.server.utils.reports.linkedin;

public class LinkedinShare {
	
//	private LinkedinVisibility visibility;
	private String comment;
	private String id;
//	private LinkedinSource source;
	private LinkedinShareContent content;
	private Long timestamp;
	
	public LinkedinShare() {}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LinkedinShareContent getContent() {
		return content;
	}
	public void setContent(LinkedinShareContent content) {
		this.content = content;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}
