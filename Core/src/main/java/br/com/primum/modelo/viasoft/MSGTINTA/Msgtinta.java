package br.com.primum.modelo.viasoft.MSGTINTA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="MSGTINTA")
public class Msgtinta  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDMSGTINTA;

	@Column
	private String DESCRICAO;


	public Integer getIdmsgtinta(){
		return IDMSGTINTA;
	}


	public void setIdmsgtinta(Integer IDMSGTINTA){
		this.IDMSGTINTA = IDMSGTINTA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}