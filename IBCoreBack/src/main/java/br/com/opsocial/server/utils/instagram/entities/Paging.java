package br.com.opsocial.server.utils.instagram.entities;

import java.io.Serializable;

public class Paging implements Serializable {

	private static final long serialVersionUID = -6554564994492402604L;
	
	private Cursors cursors;
	private String next;
	
	public Paging() {
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
	
	
}
