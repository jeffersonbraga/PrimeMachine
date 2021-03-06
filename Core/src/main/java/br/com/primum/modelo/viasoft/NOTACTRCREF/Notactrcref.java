package br.com.primum.modelo.viasoft.NOTACTRCREF;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NOTACTRCREF")
public class Notactrcref  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer IDNOTAREF;

	@Column
	private Integer ESTABNOTAREF;

	@Column
	private Integer VALOR;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
	}


	public Integer getIdnotaref(){
		return IDNOTAREF;
	}


	public void setIdnotaref(Integer IDNOTAREF){
		this.IDNOTAREF = IDNOTAREF;
	}


	public Integer getEstabnotaref(){
		return ESTABNOTAREF;
	}


	public void setEstabnotaref(Integer ESTABNOTAREF){
		this.ESTABNOTAREF = ESTABNOTAREF;
	}


	public Integer getValor(){
		return VALOR;
	}


	public void setValor(Integer VALOR){
		this.VALOR = VALOR;
	}

}