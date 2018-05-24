package br.com.primum.modelo.viasoft.BASE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="BASE")
public class Base  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDBASE;

	@Column
	private Integer IDEMBTINTA;

	@Column
	private Integer IDPRODUTO;

	@Column
	private String DESCRICAO;

	@Column
	private Integer IDCOLECAO;


	public Integer getIdbase(){
		return IDBASE;
	}


	public void setIdbase(Integer IDBASE){
		this.IDBASE = IDBASE;
	}


	public Integer getIdembtinta(){
		return IDEMBTINTA;
	}


	public void setIdembtinta(Integer IDEMBTINTA){
		this.IDEMBTINTA = IDEMBTINTA;
	}


	public Integer getIdproduto(){
		return IDPRODUTO;
	}


	public void setIdproduto(Integer IDPRODUTO){
		this.IDPRODUTO = IDPRODUTO;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getIdcolecao(){
		return IDCOLECAO;
	}


	public void setIdcolecao(Integer IDCOLECAO){
		this.IDCOLECAO = IDCOLECAO;
	}

}