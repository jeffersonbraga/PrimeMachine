package br.com.primum.modelo.viasoft.INVENTARIOCONTAGEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="INVENTARIOCONTAGEM")
public class Inventariocontagem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDINVENTARIO;

	@Column
	private Integer IDCONTAGEM;

	@Column
	private String DESCRICAO;

	@Column
	private String RESPONSAVEL;

	@Column
	private String BLOQUEADA;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdinventario(){
		return IDINVENTARIO;
	}


	public void setIdinventario(Integer IDINVENTARIO){
		this.IDINVENTARIO = IDINVENTARIO;
	}


	public Integer getIdcontagem(){
		return IDCONTAGEM;
	}


	public void setIdcontagem(Integer IDCONTAGEM){
		this.IDCONTAGEM = IDCONTAGEM;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public String getResponsavel(){
		return RESPONSAVEL;
	}


	public void setResponsavel(String RESPONSAVEL){
		this.RESPONSAVEL = RESPONSAVEL;
	}


	public String getBloqueada(){
		return BLOQUEADA;
	}


	public void setBloqueada(String BLOQUEADA){
		this.BLOQUEADA = BLOQUEADA;
	}

}