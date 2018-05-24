package br.com.primum.modelo.viasoft.PESSOADOCVEND;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOADOCVEND")
public class Pessoadocvend  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDPESS;

	@Column
	private Integer IDCOMISCONF;

	@Column
	private String TPPAGCOMIS;

	@Column
	private String CREDBXDUPCHE;

	@Column
	private Integer IDCOMISCONFC;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getIdcomisconf(){
		return IDCOMISCONF;
	}


	public void setIdcomisconf(Integer IDCOMISCONF){
		this.IDCOMISCONF = IDCOMISCONF;
	}


	public String getTppagcomis(){
		return TPPAGCOMIS;
	}


	public void setTppagcomis(String TPPAGCOMIS){
		this.TPPAGCOMIS = TPPAGCOMIS;
	}


	public String getCredbxdupche(){
		return CREDBXDUPCHE;
	}


	public void setCredbxdupche(String CREDBXDUPCHE){
		this.CREDBXDUPCHE = CREDBXDUPCHE;
	}


	public Integer getIdcomisconfc(){
		return IDCOMISCONFC;
	}


	public void setIdcomisconfc(Integer IDCOMISCONFC){
		this.IDCOMISCONFC = IDCOMISCONFC;
	}

}