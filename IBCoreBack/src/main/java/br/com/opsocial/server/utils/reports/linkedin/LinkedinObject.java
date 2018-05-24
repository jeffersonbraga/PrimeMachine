package br.com.opsocial.server.utils.reports.linkedin;

import java.io.Serializable;

public class LinkedinObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String entryKey;
	private String entryValue;
	private Long entryValueTotal;
	
	public LinkedinObject() {}
	
	public String getEntryKey() {
		return entryKey;
	}
	public void setEntryKey(String entryKey) {
		this.entryKey = entryKey;
	}
	public String getEntryValue() {
		return entryValue;
	}
	public void setEntryValue(String entryValue) {
		this.entryValue = entryValue;
	}

	public Long getEntryValueTotal() {
		return entryValueTotal;
	}

	public void setEntryValueTotal(Long entryValueTotal) {
		this.entryValueTotal = entryValueTotal;
	}
}
