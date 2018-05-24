package br.com.primum.modelo.viasoft.METAEMPRESA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="METAEMPRESA")
public class Metaempresa  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer ANO;

	@Column
	private Integer MES;

	@Column
	private Integer VALORMETA;

	@Column
	private Integer DIASUTEIS;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getAno(){
		return ANO;
	}


	public void setAno(Integer ANO){
		this.ANO = ANO;
	}


	public Integer getMes(){
		return MES;
	}


	public void setMes(Integer MES){
		this.MES = MES;
	}


	public Integer getValormeta(){
		return VALORMETA;
	}


	public void setValormeta(Integer VALORMETA){
		this.VALORMETA = VALORMETA;
	}


	public Integer getDiasuteis(){
		return DIASUTEIS;
	}


	public void setDiasuteis(Integer DIASUTEIS){
		this.DIASUTEIS = DIASUTEIS;
	}

}