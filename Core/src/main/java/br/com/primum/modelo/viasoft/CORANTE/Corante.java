package br.com.primum.modelo.viasoft.CORANTE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CORANTE")
public class Corante  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCORANTE;

	@Column
	private String SIGLA;

	@Column
	private String DESCRICAO;

	@Column
	private String COR;

	@Column
	private Integer IDCOLECAO;


	public Integer getIdcorante(){
		return IDCORANTE;
	}


	public void setIdcorante(Integer IDCORANTE){
		this.IDCORANTE = IDCORANTE;
	}


	public String getSigla(){
		return SIGLA;
	}


	public void setSigla(String SIGLA){
		this.SIGLA = SIGLA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public String getCor(){
		return COR;
	}


	public void setCor(String COR){
		this.COR = COR;
	}


	public Integer getIdcolecao(){
		return IDCOLECAO;
	}


	public void setIdcolecao(Integer IDCOLECAO){
		this.IDCOLECAO = IDCOLECAO;
	}

}