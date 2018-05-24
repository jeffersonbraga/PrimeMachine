package br.com.opsocial.client.entity.primitive;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class Long extends Persistent { 
	
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long value;
	
	public Long() {
	}
	
	public Long(java.lang.Long value) {
		this.value = value;
	}

	public java.lang.Long getValue() {
		return value;
	}

	public void setValue(java.lang.Long value) {
		this.value = value;
	}
}