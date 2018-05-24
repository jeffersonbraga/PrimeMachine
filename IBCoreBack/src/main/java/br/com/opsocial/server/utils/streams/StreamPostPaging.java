package br.com.opsocial.server.utils.streams;

import java.io.Serializable;

public class StreamPostPaging implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer limit;
	private Integer offset;
	private String since;
	private String until;
	private String after;
	private String before;
	private Long maxId;
	private Long sinceId;
	private Integer page;
	
	public StreamPostPaging() {
		
	}
	
	public StreamPostPaging(Integer limit) {
		this.limit = limit;
	}
	
	public Integer getLimit() {
		return limit;
	}
	
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	public Integer getOffset() {
		return offset;
	}
	
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public String getSince() {
		return since;
	}
	
	public void setSince(String since) {
		this.since = since;
	}
	
	public String getUntil() {
		return until;
	}
	
	public void setUntil(String until) {
		this.until = until;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public Long getMaxId() {
		return maxId;
	}

	public void setMaxId(Long maxId) {
		this.maxId = maxId;
	}

	public Long getSinceId() {
		return sinceId;
	}

	public void setSinceId(Long sinceId) {
		this.sinceId = sinceId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
}
