package br.com.primum.modelo.viasoft.TPGARANTIA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="TPGARANTIA")
public class Tpgarantia  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDTPGARANTIA;

	@Column
	private String DESCRICAO;


	public Integer getIdtpgarantia(){
		return IDTPGARANTIA;
	}


	public void setIdtpgarantia(Integer IDTPGARANTIA){
		this.IDTPGARANTIA = IDTPGARANTIA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}