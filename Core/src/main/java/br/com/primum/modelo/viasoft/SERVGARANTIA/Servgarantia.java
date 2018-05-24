package br.com.primum.modelo.viasoft.SERVGARANTIA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="SERVGARANTIA")
public class Servgarantia  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDSERVGARANTIA;

	@Column
	private String DESCRICAO;

	@Column
	private Integer PRAZOFORN;

	@Column
	private Integer PRAZOCOMP;

	@Column
	private Integer TIPOREPARO;


	public Integer getIdservgarantia(){
		return IDSERVGARANTIA;
	}


	public void setIdservgarantia(Integer IDSERVGARANTIA){
		this.IDSERVGARANTIA = IDSERVGARANTIA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getPrazoforn(){
		return PRAZOFORN;
	}


	public void setPrazoforn(Integer PRAZOFORN){
		this.PRAZOFORN = PRAZOFORN;
	}


	public Integer getPrazocomp(){
		return PRAZOCOMP;
	}


	public void setPrazocomp(Integer PRAZOCOMP){
		this.PRAZOCOMP = PRAZOCOMP;
	}


	public Integer getTiporeparo(){
		return TIPOREPARO;
	}


	public void setTiporeparo(Integer TIPOREPARO){
		this.TIPOREPARO = TIPOREPARO;
	}

}