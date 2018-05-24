package br.com.primum.modelo.jhi_persistent_audit_evt_data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="jhi_persistent_audit_evt_data")
public class Jhi_persistent_audit_evt_data  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer event_id;

	@Column
	private String name;

	@Column
	private String value;


	public Integer getEvent_id(){
		return event_id;
	}


	public void setEvent_id(Integer event_id){
		this.event_id = event_id;
	}


	public String getName(){
		return name;
	}


	public void setName(String name){
		this.name = name;
	}


	public String getValue(){
		return value;
	}


	public void setValue(String value){
		this.value = value;
	}

}