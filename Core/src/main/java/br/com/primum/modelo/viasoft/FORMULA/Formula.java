package br.com.primum.modelo.viasoft.FORMULA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FORMULA")
public class Formula  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDFORMULA;

	@Column
	private Integer IDCOLECAO;

	@Column
	private Integer IDBASE;

	@Column
	private Integer IDPRODUTO;

	@Column
	private Integer IDMSGTINTA;

	@Column
	private String DESCRICAO;

	@Column
	private String CODCATALOGO;

	@Column
	private Integer IDEMBTINTA;

	@Column
	private Integer IDGRUPOTINTA;


	public Integer getIdformula(){
		return IDFORMULA;
	}


	public void setIdformula(Integer IDFORMULA){
		this.IDFORMULA = IDFORMULA;
	}


	public Integer getIdcolecao(){
		return IDCOLECAO;
	}


	public void setIdcolecao(Integer IDCOLECAO){
		this.IDCOLECAO = IDCOLECAO;
	}


	public Integer getIdbase(){
		return IDBASE;
	}


	public void setIdbase(Integer IDBASE){
		this.IDBASE = IDBASE;
	}


	public Integer getIdproduto(){
		return IDPRODUTO;
	}


	public void setIdproduto(Integer IDPRODUTO){
		this.IDPRODUTO = IDPRODUTO;
	}


	public Integer getIdmsgtinta(){
		return IDMSGTINTA;
	}


	public void setIdmsgtinta(Integer IDMSGTINTA){
		this.IDMSGTINTA = IDMSGTINTA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public String getCodcatalogo(){
		return CODCATALOGO;
	}


	public void setCodcatalogo(String CODCATALOGO){
		this.CODCATALOGO = CODCATALOGO;
	}


	public Integer getIdembtinta(){
		return IDEMBTINTA;
	}


	public void setIdembtinta(Integer IDEMBTINTA){
		this.IDEMBTINTA = IDEMBTINTA;
	}


	public Integer getIdgrupotinta(){
		return IDGRUPOTINTA;
	}


	public void setIdgrupotinta(Integer IDGRUPOTINTA){
		this.IDGRUPOTINTA = IDGRUPOTINTA;
	}

}