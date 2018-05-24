package br.com.primum.modelo.viasoft.MOTIVODEV;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="MOTIVODEV")
public class Motivodev  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDMOTIVODEV;

	@Column
	private String DESCRICAO;


	public Integer getIdmotivodev(){
		return IDMOTIVODEV;
	}


	public void setIdmotivodev(Integer IDMOTIVODEV){
		this.IDMOTIVODEV = IDMOTIVODEV;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}