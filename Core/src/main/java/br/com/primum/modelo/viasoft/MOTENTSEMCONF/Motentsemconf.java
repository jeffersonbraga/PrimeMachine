package br.com.primum.modelo.viasoft.MOTENTSEMCONF;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="MOTENTSEMCONF")
public class Motentsemconf  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDMOTCONF;

	@Column
	private String DESCRICAO;


	public Integer getIdmotconf(){
		return IDMOTCONF;
	}


	public void setIdmotconf(Integer IDMOTCONF){
		this.IDMOTCONF = IDMOTCONF;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}