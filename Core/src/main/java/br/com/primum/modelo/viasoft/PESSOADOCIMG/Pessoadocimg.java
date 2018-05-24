package br.com.primum.modelo.viasoft.PESSOADOCIMG;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOADOCIMG")
public class Pessoadocimg  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDPESS;

	@Column
	private Integer IDPESSOADOCIMG;

	@Column
	private String TIPO;

	@Column
	private String NOME;


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getIdpessoadocimg(){
		return IDPESSOADOCIMG;
	}


	public void setIdpessoadocimg(Integer IDPESSOADOCIMG){
		this.IDPESSOADOCIMG = IDPESSOADOCIMG;
	}


	public String getTipo(){
		return TIPO;
	}


	public void setTipo(String TIPO){
		this.TIPO = TIPO;
	}


	public String getNome(){
		return NOME;
	}


	public void setNome(String NOME){
		this.NOME = NOME;
	}

}