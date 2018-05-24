package br.com.primum.modelo.viasoft.GRUPOCTB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="GRUPOCTB")
public class Grupoctb  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDGRUPOCTB;

	@Column
	private String DESCRICAO;


	public Integer getIdgrupoctb(){
		return IDGRUPOCTB;
	}


	public void setIdgrupoctb(Integer IDGRUPOCTB){
		this.IDGRUPOCTB = IDGRUPOCTB;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}