package br.com.primum.modelo.viasoft.RELIMP;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="RELIMP")
public class Relimp  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDRELIMP;

	@Column
	private Integer IDREL;

	@Column
	private String CHAVE;

	@Column
	private String USERID;

	@Column
	private String AUTORIZADO;


	public Integer getIdrelimp(){
		return IDRELIMP;
	}


	public void setIdrelimp(Integer IDRELIMP){
		this.IDRELIMP = IDRELIMP;
	}


	public Integer getIdrel(){
		return IDREL;
	}


	public void setIdrel(Integer IDREL){
		this.IDREL = IDREL;
	}


	public String getChave(){
		return CHAVE;
	}


	public void setChave(String CHAVE){
		this.CHAVE = CHAVE;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public String getAutorizado(){
		return AUTORIZADO;
	}


	public void setAutorizado(String AUTORIZADO){
		this.AUTORIZADO = AUTORIZADO;
	}

}