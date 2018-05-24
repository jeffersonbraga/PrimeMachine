package br.com.primum.modelo.viasoft.PESSOADOCIMP;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOADOCIMP")
public class Pessoadocimp  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDPESS;

	@Column
	private Integer LININICIAL;

	@Column
	private Integer LINFINAL;

	@Column
	private String COLCODFORN;

	@Column
	private String COLDESCFORN;

	@Column
	private String COLCUSTO;

	@Column
	private String COLCODBAR;

	@Column
	private String COLNCM;


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getLininicial(){
		return LININICIAL;
	}


	public void setLininicial(Integer LININICIAL){
		this.LININICIAL = LININICIAL;
	}


	public Integer getLinfinal(){
		return LINFINAL;
	}


	public void setLinfinal(Integer LINFINAL){
		this.LINFINAL = LINFINAL;
	}


	public String getColcodforn(){
		return COLCODFORN;
	}


	public void setColcodforn(String COLCODFORN){
		this.COLCODFORN = COLCODFORN;
	}


	public String getColdescforn(){
		return COLDESCFORN;
	}


	public void setColdescforn(String COLDESCFORN){
		this.COLDESCFORN = COLDESCFORN;
	}


	public String getColcusto(){
		return COLCUSTO;
	}


	public void setColcusto(String COLCUSTO){
		this.COLCUSTO = COLCUSTO;
	}


	public String getColcodbar(){
		return COLCODBAR;
	}


	public void setColcodbar(String COLCODBAR){
		this.COLCODBAR = COLCODBAR;
	}


	public String getColncm(){
		return COLNCM;
	}


	public void setColncm(String COLNCM){
		this.COLNCM = COLNCM;
	}

}