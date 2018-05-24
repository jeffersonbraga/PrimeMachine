package br.com.primum.modelo.viasoft.MOTIVOINAT;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="MOTIVOINAT")
public class Motivoinat  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDMOTIVOINAT;

	@Column
	private String DESCRICAO;


	public Integer getIdmotivoinat(){
		return IDMOTIVOINAT;
	}


	public void setIdmotivoinat(Integer IDMOTIVOINAT){
		this.IDMOTIVOINAT = IDMOTIVOINAT;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}