package br.com.primum.modelo.viasoft.EMBTINTA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="EMBTINTA")
public class Embtinta  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDEMBTINTA;

	@Column
	private String DESCRICAO;

	@Column
	private Integer CAPACIDADE;

	@Column
	private Integer IDCOLECAO;


	public Integer getIdembtinta(){
		return IDEMBTINTA;
	}


	public void setIdembtinta(Integer IDEMBTINTA){
		this.IDEMBTINTA = IDEMBTINTA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getCapacidade(){
		return CAPACIDADE;
	}


	public void setCapacidade(Integer CAPACIDADE){
		this.CAPACIDADE = CAPACIDADE;
	}


	public Integer getIdcolecao(){
		return IDCOLECAO;
	}


	public void setIdcolecao(Integer IDCOLECAO){
		this.IDCOLECAO = IDCOLECAO;
	}

}