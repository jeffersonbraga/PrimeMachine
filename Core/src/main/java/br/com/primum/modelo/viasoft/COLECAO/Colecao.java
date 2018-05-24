package br.com.primum.modelo.viasoft.COLECAO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="COLECAO")
public class Colecao  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCOLECAO;

	@Column
	private String DESCRICAO;

	@Column
	private Integer GOTAFATOR;


	public Integer getIdcolecao(){
		return IDCOLECAO;
	}


	public void setIdcolecao(Integer IDCOLECAO){
		this.IDCOLECAO = IDCOLECAO;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getGotafator(){
		return GOTAFATOR;
	}


	public void setGotafator(Integer GOTAFATOR){
		this.GOTAFATOR = GOTAFATOR;
	}

}