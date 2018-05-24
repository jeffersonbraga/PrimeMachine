package br.com.primum.modelo.viasoft.CARGAITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARGAITEM")
public class Cargaitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCARGA;

	@Column
	private Integer IDCONTROLEEXP;

	@Column
	private Integer ORDEM;

	@Column
	private String DEVOLVIDO;


	public Integer getIdcarga(){
		return IDCARGA;
	}


	public void setIdcarga(Integer IDCARGA){
		this.IDCARGA = IDCARGA;
	}


	public Integer getIdcontroleexp(){
		return IDCONTROLEEXP;
	}


	public void setIdcontroleexp(Integer IDCONTROLEEXP){
		this.IDCONTROLEEXP = IDCONTROLEEXP;
	}


	public Integer getOrdem(){
		return ORDEM;
	}


	public void setOrdem(Integer ORDEM){
		this.ORDEM = ORDEM;
	}


	public String getDevolvido(){
		return DEVOLVIDO;
	}


	public void setDevolvido(String DEVOLVIDO){
		this.DEVOLVIDO = DEVOLVIDO;
	}

}