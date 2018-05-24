package br.com.primum.modelo.viasoft.MOTIVORET;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="MOTIVORET")
public class Motivoret  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDMOTIVORET;

	@Column
	private String DESCRICAO;


	public Integer getIdmotivoret(){
		return IDMOTIVORET;
	}


	public void setIdmotivoret(Integer IDMOTIVORET){
		this.IDMOTIVORET = IDMOTIVORET;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}