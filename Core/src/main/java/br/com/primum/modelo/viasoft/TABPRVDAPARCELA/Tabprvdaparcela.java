package br.com.primum.modelo.viasoft.TABPRVDAPARCELA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="TABPRVDAPARCELA")
public class Tabprvdaparcela  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDTABPRVDA;

	@Column
	private String FORMACART;

	@Column
	private String FORMADUP;

	@Column
	private String FORMACHE;

	@Column
	private String FORMAOUT;


	public Integer getIdtabprvda(){
		return IDTABPRVDA;
	}


	public void setIdtabprvda(Integer IDTABPRVDA){
		this.IDTABPRVDA = IDTABPRVDA;
	}


	public String getFormacart(){
		return FORMACART;
	}


	public void setFormacart(String FORMACART){
		this.FORMACART = FORMACART;
	}


	public String getFormadup(){
		return FORMADUP;
	}


	public void setFormadup(String FORMADUP){
		this.FORMADUP = FORMADUP;
	}


	public String getFormache(){
		return FORMACHE;
	}


	public void setFormache(String FORMACHE){
		this.FORMACHE = FORMACHE;
	}


	public String getFormaout(){
		return FORMAOUT;
	}


	public void setFormaout(String FORMAOUT){
		this.FORMAOUT = FORMAOUT;
	}

}