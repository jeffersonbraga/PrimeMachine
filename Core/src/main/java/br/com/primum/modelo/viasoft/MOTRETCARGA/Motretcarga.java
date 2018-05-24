package br.com.primum.modelo.viasoft.MOTRETCARGA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="MOTRETCARGA")
public class Motretcarga  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDMOTRETCARGA;

	@Column
	private String DESCRICAO;


	public Integer getIdmotretcarga(){
		return IDMOTRETCARGA;
	}


	public void setIdmotretcarga(Integer IDMOTRETCARGA){
		this.IDMOTRETCARGA = IDMOTRETCARGA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}