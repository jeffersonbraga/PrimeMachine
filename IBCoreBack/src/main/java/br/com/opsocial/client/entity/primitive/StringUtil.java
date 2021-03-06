package br.com.opsocial.client.entity.primitive;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class StringUtil extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	private java.lang.String value;
	
	public StringUtil() {
	}
	
	public StringUtil(java.lang.String value) {
		this.value = value;
	}

	public java.lang.String getValue() {
		return value;
	}

	public void setValue(java.lang.String value) {
		this.value = value;
	}
}
