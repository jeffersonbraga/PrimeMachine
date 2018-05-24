package br.com.primum.modelo.jhi_persistent_audit_event;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="jhi_persistent_audit_event")
public class Jhi_persistent_audit_event  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer event_id;

	@Column
	private String principal;

	@Column
	private String event_type;


	public Integer getEvent_id(){
		return event_id;
	}


	public void setEvent_id(Integer event_id){
		this.event_id = event_id;
	}


	public String getPrincipal(){
		return principal;
	}


	public void setPrincipal(String principal){
		this.principal = principal;
	}


	public String getEvent_type(){
		return event_type;
	}


	public void setEvent_type(String event_type){
		this.event_type = event_type;
	}

}