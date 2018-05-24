package br.com.primum.modelo.viasoft.FUNCAO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FUNCAO")
public class Funcao  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDFUNCAO;

	@Column
	private String DESCRICAO;

	@Column
	private String INTEROREXT;


	public Integer getIdfuncao(){
		return IDFUNCAO;
	}


	public void setIdfuncao(Integer IDFUNCAO){
		this.IDFUNCAO = IDFUNCAO;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public String getInterorext(){
		return INTEROREXT;
	}


	public void setInterorext(String INTEROREXT){
		this.INTEROREXT = INTEROREXT;
	}

}