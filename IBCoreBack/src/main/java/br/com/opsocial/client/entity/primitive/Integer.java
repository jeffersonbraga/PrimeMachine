package br.com.opsocial.client.entity.primitive;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class Integer extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private java.lang.Integer value;
	
	public Integer() {
	}
	
	public Integer(java.lang.Integer value) {
		this.value = value;
	}
	
	public java.lang.Integer getValue() {
		return value;
	}

	public void setValue(java.lang.Integer value) {
		this.value = value;
	}
}
