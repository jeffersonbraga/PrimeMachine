package br.com.primum.modelo.viasoft.TABPRVDAESTAB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="TABPRVDAESTAB")
public class Tabprvdaestab  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDBANDEIRA;

	@Column
	private Integer IDTABPRVDA;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdbandeira(){
		return IDBANDEIRA;
	}


	public void setIdbandeira(Integer IDBANDEIRA){
		this.IDBANDEIRA = IDBANDEIRA;
	}


	public Integer getIdtabprvda(){
		return IDTABPRVDA;
	}


	public void setIdtabprvda(Integer IDTABPRVDA){
		this.IDTABPRVDA = IDTABPRVDA;
	}

}