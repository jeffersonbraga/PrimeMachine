package br.com.primum.modelo.viasoft.PROMOCAO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PROMOCAO")
public class Promocao  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDPROMO;

	@Column
	private Integer ESTAB;

	@Column
	private String DESCRICAO;


	public Integer getIdpromo(){
		return IDPROMO;
	}


	public void setIdpromo(Integer IDPROMO){
		this.IDPROMO = IDPROMO;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}