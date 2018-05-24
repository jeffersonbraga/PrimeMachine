package br.com.primum.modelo.viasoft.APARTIRDE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="APARTIRDE")
public class Apartirde  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTACONF;

	@Column
	private Integer IDNOTACONFORIG;


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


	public Integer getIdnotaconforig(){
		return IDNOTACONFORIG;
	}


	public void setIdnotaconforig(Integer IDNOTACONFORIG){
		this.IDNOTACONFORIG = IDNOTACONFORIG;
	}

}