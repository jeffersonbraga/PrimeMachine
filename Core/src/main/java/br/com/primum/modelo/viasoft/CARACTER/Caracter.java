package br.com.primum.modelo.viasoft.CARACTER;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARACTER")
public class Caracter  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCARACTER;

	@Column
	private String DESCRICAO;

	@Column
	private Integer ORDEM;

	@Column
	private String OBRIGA;

	@Column
	private String ABREV;


	public Integer getIdcaracter(){
		return IDCARACTER;
	}


	public void setIdcaracter(Integer IDCARACTER){
		this.IDCARACTER = IDCARACTER;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getOrdem(){
		return ORDEM;
	}


	public void setOrdem(Integer ORDEM){
		this.ORDEM = ORDEM;
	}


	public String getObriga(){
		return OBRIGA;
	}


	public void setObriga(String OBRIGA){
		this.OBRIGA = OBRIGA;
	}


	public String getAbrev(){
		return ABREV;
	}


	public void setAbrev(String ABREV){
		this.ABREV = ABREV;
	}

}