package br.com.primum.modelo.viasoft.PESSOADOCCOLAB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOADOCCOLAB")
public class Pessoadoccolab  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDPESS;

	@Column
	private Integer IDPESSOADOCCOLAB;

	@Column
	private Integer ESTAB;

	@Column
	private Integer IDFUNCAO;


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getIdpessoadoccolab(){
		return IDPESSOADOCCOLAB;
	}


	public void setIdpessoadoccolab(Integer IDPESSOADOCCOLAB){
		this.IDPESSOADOCCOLAB = IDPESSOADOCCOLAB;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdfuncao(){
		return IDFUNCAO;
	}


	public void setIdfuncao(Integer IDFUNCAO){
		this.IDFUNCAO = IDFUNCAO;
	}

}