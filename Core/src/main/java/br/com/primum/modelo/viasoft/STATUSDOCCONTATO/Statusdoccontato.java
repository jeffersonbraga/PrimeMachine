package br.com.primum.modelo.viasoft.STATUSDOCCONTATO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="STATUSDOCCONTATO")
public class Statusdoccontato  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDSTATUS;

	@Column
	private String DESCRICAO;


	public Integer getIdstatus(){
		return IDSTATUS;
	}


	public void setIdstatus(Integer IDSTATUS){
		this.IDSTATUS = IDSTATUS;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}