package br.com.primum.modelo.jhi_user_authority;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="jhi_user_authority")
public class Jhi_user_authority  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer user_id;

	@Column
	private String authority_name;


	public Integer getUser_id(){
		return user_id;
	}


	public void setUser_id(Integer user_id){
		this.user_id = user_id;
	}


	public String getAuthority_name(){
		return authority_name;
	}


	public void setAuthority_name(String authority_name){
		this.authority_name = authority_name;
	}

}