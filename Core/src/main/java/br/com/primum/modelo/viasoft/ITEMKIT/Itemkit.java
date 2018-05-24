package br.com.primum.modelo.viasoft.ITEMKIT;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMKIT")
public class Itemkit  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String IDITEMKIT;

	@Column
	private String IDITEM;

	@Column
	private Integer QTDE;

	@Column
	private Integer VLRUNIT;

	@Column
	private String ACREDESC;

	@Column
	private Integer PERCVALOR;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getIditemkit(){
		return IDITEMKIT;
	}


	public void setIditemkit(String IDITEMKIT){
		this.IDITEMKIT = IDITEMKIT;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getQtde(){
		return QTDE;
	}


	public void setQtde(Integer QTDE){
		this.QTDE = QTDE;
	}


	public Integer getVlrunit(){
		return VLRUNIT;
	}


	public void setVlrunit(Integer VLRUNIT){
		this.VLRUNIT = VLRUNIT;
	}


	public String getAcredesc(){
		return ACREDESC;
	}


	public void setAcredesc(String ACREDESC){
		this.ACREDESC = ACREDESC;
	}


	public Integer getPercvalor(){
		return PERCVALOR;
	}


	public void setPercvalor(Integer PERCVALOR){
		this.PERCVALOR = PERCVALOR;
	}

}