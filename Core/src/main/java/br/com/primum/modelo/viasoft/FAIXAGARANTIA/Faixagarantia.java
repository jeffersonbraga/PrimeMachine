package br.com.primum.modelo.viasoft.FAIXAGARANTIA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FAIXAGARANTIA")
public class Faixagarantia  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDFAIXAGARANTIA;

	@Column
	private Integer IDTPGARANTIA;

	@Column
	private Integer IDSERVGARANTIA;

	@Column
	private String SERVICOPAD;


	public Integer getIdfaixagarantia(){
		return IDFAIXAGARANTIA;
	}


	public void setIdfaixagarantia(Integer IDFAIXAGARANTIA){
		this.IDFAIXAGARANTIA = IDFAIXAGARANTIA;
	}


	public Integer getIdtpgarantia(){
		return IDTPGARANTIA;
	}


	public void setIdtpgarantia(Integer IDTPGARANTIA){
		this.IDTPGARANTIA = IDTPGARANTIA;
	}


	public Integer getIdservgarantia(){
		return IDSERVGARANTIA;
	}


	public void setIdservgarantia(Integer IDSERVGARANTIA){
		this.IDSERVGARANTIA = IDSERVGARANTIA;
	}


	public String getServicopad(){
		return SERVICOPAD;
	}


	public void setServicopad(String SERVICOPAD){
		this.SERVICOPAD = SERVICOPAD;
	}

}