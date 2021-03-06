package br.com.opsocial.ejb.entity.report;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class PropertyValue extends Persistent {

	private static final long serialVersionUID = -8214040931856085244L;
	
	private String property;
	private Long value;
	
	public PropertyValue() {
	
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
	@JsonIgnore
	public Long getPropertyAsLong() {
		return new Long(property);
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}
