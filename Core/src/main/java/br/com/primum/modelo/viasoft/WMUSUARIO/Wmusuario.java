package br.com.primum.modelo.viasoft.WMUSUARIO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="WMUSUARIO")
public class Wmusuario  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String USERID;

	@Column
	private String NOME;

	@Column
	private String IMEI;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public String getNome(){
		return NOME;
	}


	public void setNome(String NOME){
		this.NOME = NOME;
	}


	public String getImei(){
		return IMEI;
	}


	public void setImei(String IMEI){
		this.IMEI = IMEI;
	}

}