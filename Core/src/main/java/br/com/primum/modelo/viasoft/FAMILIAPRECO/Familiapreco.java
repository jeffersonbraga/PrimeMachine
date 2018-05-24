package br.com.primum.modelo.viasoft.FAMILIAPRECO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FAMILIAPRECO")
public class Familiapreco  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDBANDEIRA;

	@Column
	private Integer IDFAMILIAPRECO;

	@Column
	private String DESCRICAO;

	@Column
	private Integer PRECO;


	public Integer getIdbandeira(){
		return IDBANDEIRA;
	}


	public void setIdbandeira(Integer IDBANDEIRA){
		this.IDBANDEIRA = IDBANDEIRA;
	}


	public Integer getIdfamiliapreco(){
		return IDFAMILIAPRECO;
	}


	public void setIdfamiliapreco(Integer IDFAMILIAPRECO){
		this.IDFAMILIAPRECO = IDFAMILIAPRECO;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getPreco(){
		return PRECO;
	}


	public void setPreco(Integer PRECO){
		this.PRECO = PRECO;
	}

}