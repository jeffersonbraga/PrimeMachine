package br.com.primum.modelo.viasoft.AVARIACAO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="AVARIACAO")
public class Avariacao  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDAVARIACAO;

	@Column
	private String DESCRICAO;


	public Integer getIdavariacao(){
		return IDAVARIACAO;
	}


	public void setIdavariacao(Integer IDAVARIACAO){
		this.IDAVARIACAO = IDAVARIACAO;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}