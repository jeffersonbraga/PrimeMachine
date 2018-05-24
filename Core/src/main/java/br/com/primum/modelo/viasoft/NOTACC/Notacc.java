package br.com.primum.modelo.viasoft.NOTACC;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NOTACC")
public class Notacc  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer SEQUENCIA;

	@Column
	private Integer CENCUSCOD;

	@Column
	private String CENCUS;

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


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public Integer getCencuscod(){
		return CENCUSCOD;
	}


	public void setCencuscod(Integer CENCUSCOD){
		this.CENCUSCOD = CENCUSCOD;
	}


	public String getCencus(){
		return CENCUS;
	}


	public void setCencus(String CENCUS){
		this.CENCUS = CENCUS;
	}


	public Integer getValor(){
		return VALOR;
	}


	public void setValor(Integer VALOR){
		this.VALOR = VALOR;
	}

}