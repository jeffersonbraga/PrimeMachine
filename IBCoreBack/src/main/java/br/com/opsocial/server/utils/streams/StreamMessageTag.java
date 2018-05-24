package br.com.opsocial.server.utils.streams;

import java.io.Serializable;

public class StreamMessageTag implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String type;
	private Integer offset;
	private Integer length;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		
		this.type = type;
	}
	public Integer getOffset() {
		return offset;
	}
	
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public Integer getLength() {
		return length;
	}
	
	public void setLength(Integer length) {
		this.length = length;
	}
}
