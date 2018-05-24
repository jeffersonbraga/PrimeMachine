package br.com.primum.modelo.viasoft.TELAS;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="TELAS")
public class Telas  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDTELA;

	@Column
	private String DESCRICAO;


	public Integer getIdtela(){
		return IDTELA;
	}


	public void setIdtela(Integer IDTELA){
		this.IDTELA = IDTELA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}