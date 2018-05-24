package br.com.primum.modelo.viasoft.WMVERSAO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="WMVERSAO")
public class Wmversao  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private String VERSAO;

	@Column
	private Integer SEQUENCIA;

	@Column
	private String COMANDO;

	@Column
	private String ATVERSAO;

	@Column
	private Date DATAEXEC;


	public String getVersao(){
		return VERSAO;
	}


	public void setVersao(String VERSAO){
		this.VERSAO = VERSAO;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public String getComando(){
		return COMANDO;
	}


	public void setComando(String COMANDO){
		this.COMANDO = COMANDO;
	}


	public String getAtversao(){
		return ATVERSAO;
	}


	public void setAtversao(String ATVERSAO){
		this.ATVERSAO = ATVERSAO;
	}


	public Date getDataexec(){
		return DATAEXEC;
	}


	public void setDataexec(Date DATAEXEC){
		this.DATAEXEC = DATAEXEC;
	}

}