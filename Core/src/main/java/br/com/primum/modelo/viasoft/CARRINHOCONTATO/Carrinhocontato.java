package br.com.primum.modelo.viasoft.CARRINHOCONTATO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARRINHOCONTATO")
public class Carrinhocontato  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCARRINHO;

	@Column
	private Integer SEQ;

	@Column
	private String DESCRICAO;


	public Integer getIdcarrinho(){
		return IDCARRINHO;
	}


	public void setIdcarrinho(Integer IDCARRINHO){
		this.IDCARRINHO = IDCARRINHO;
	}


	public Integer getSeq(){
		return SEQ;
	}


	public void setSeq(Integer SEQ){
		this.SEQ = SEQ;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}