package br.com.primum.modelo.viasoft.WMCONFIG;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="WMCONFIG")
public class Wmconfig  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer DIASBLOQNOTSYNC;

	@Column
	private Integer IDCONCEITOPADRAOCAD;

	@Column
	private String INCCARRINHOCLINOTSYNC;

	@Column
	private String CLIDOREPRES;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getDiasbloqnotsync(){
		return DIASBLOQNOTSYNC;
	}


	public void setDiasbloqnotsync(Integer DIASBLOQNOTSYNC){
		this.DIASBLOQNOTSYNC = DIASBLOQNOTSYNC;
	}


	public Integer getIdconceitopadraocad(){
		return IDCONCEITOPADRAOCAD;
	}


	public void setIdconceitopadraocad(Integer IDCONCEITOPADRAOCAD){
		this.IDCONCEITOPADRAOCAD = IDCONCEITOPADRAOCAD;
	}


	public String getInccarrinhoclinotsync(){
		return INCCARRINHOCLINOTSYNC;
	}


	public void setInccarrinhoclinotsync(String INCCARRINHOCLINOTSYNC){
		this.INCCARRINHOCLINOTSYNC = INCCARRINHOCLINOTSYNC;
	}


	public String getClidorepres(){
		return CLIDOREPRES;
	}


	public void setClidorepres(String CLIDOREPRES){
		this.CLIDOREPRES = CLIDOREPRES;
	}

}