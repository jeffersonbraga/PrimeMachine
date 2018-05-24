package br.com.opsocial.client.entity.socialnetworks.facebook;

import br.com.opsocial.ejb.entity.generic.Persistent;
import facebook4j.Paging.Cursors;

public class FacebookPaging extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private int limit;
	private int offset;
	private String since;
	private String until;
	private Cursors cursors;
	private String next;
	private String previous;
	
	public FacebookPaging() {
		
	}
	
	public FacebookPaging(int limit) {
		this.limit = limit;
	}
	
	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	/**
	 * @return the since
	 */
	public String getSince() {
		return since;
	}
	/**
	 * @param since the since to set
	 */
	public void setSince(String since) {
		this.since = since;
	}
	/**
	 * @return the until
	 */
	public String getUntil() {
		return until;
	}
	/**
	 * @param until the until to set
	 */
	public void setUntil(String until) {
		this.until = until;
	}

	public Cursors getCursors() {
		return cursors;
	}

	public void setCursors(Cursors cursors) {
		this.cursors = cursors;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}
}