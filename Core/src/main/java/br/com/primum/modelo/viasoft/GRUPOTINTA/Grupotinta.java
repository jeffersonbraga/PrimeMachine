package br.com.primum.modelo.viasoft.GRUPOTINTA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="GRUPOTINTA")
public class Grupotinta  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCOLECAO;

	@Column
	private Integer IDGRUPOTINTA;

	@Column
	private String DESCRICAO;

	@Column
	private String SITUACAO;


	public Integer getIdcolecao(){
		return IDCOLECAO;
	}


	public void setIdcolecao(Integer IDCOLECAO){
		this.IDCOLECAO = IDCOLECAO;
	}


	public Integer getIdgrupotinta(){
		return IDGRUPOTINTA;
	}


	public void setIdgrupotinta(Integer IDGRUPOTINTA){
		this.IDGRUPOTINTA = IDGRUPOTINTA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public String getSituacao(){
		return SITUACAO;
	}


	public void setSituacao(String SITUACAO){
		this.SITUACAO = SITUACAO;
	}

}