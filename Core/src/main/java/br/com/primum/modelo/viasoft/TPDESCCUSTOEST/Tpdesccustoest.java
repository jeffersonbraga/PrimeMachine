package br.com.primum.modelo.viasoft.TPDESCCUSTOEST;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="TPDESCCUSTOEST")
public class Tpdesccustoest  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDTIPODESC;

	@Column
	private String DESCRICAO;

	@Column
	private Integer PERC;


	public Integer getIdtipodesc(){
		return IDTIPODESC;
	}


	public void setIdtipodesc(Integer IDTIPODESC){
		this.IDTIPODESC = IDTIPODESC;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getPerc(){
		return PERC;
	}


	public void setPerc(Integer PERC){
		this.PERC = PERC;
	}

}