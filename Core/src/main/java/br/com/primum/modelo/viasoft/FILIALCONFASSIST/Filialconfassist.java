package br.com.primum.modelo.viasoft.FILIALCONFASSIST;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FILIALCONFASSIST")
public class Filialconfassist  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTACONF;

	@Column
	private String BLOQFINCARGA;

	@Column
	private String BLOQINSDOC;

	@Column
	private String LISTCADASSIST;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdnotaconf(){
		return IDNOTACONF;
	}


	public void setIdnotaconf(Integer IDNOTACONF){
		this.IDNOTACONF = IDNOTACONF;
	}


	public String getBloqfincarga(){
		return BLOQFINCARGA;
	}


	public void setBloqfincarga(String BLOQFINCARGA){
		this.BLOQFINCARGA = BLOQFINCARGA;
	}


	public String getBloqinsdoc(){
		return BLOQINSDOC;
	}


	public void setBloqinsdoc(String BLOQINSDOC){
		this.BLOQINSDOC = BLOQINSDOC;
	}


	public String getListcadassist(){
		return LISTCADASSIST;
	}


	public void setListcadassist(String LISTCADASSIST){
		this.LISTCADASSIST = LISTCADASSIST;
	}

}