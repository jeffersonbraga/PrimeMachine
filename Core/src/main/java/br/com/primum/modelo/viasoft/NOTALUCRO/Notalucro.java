package br.com.primum.modelo.viasoft.NOTALUCRO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NOTALUCRO")
public class Notalucro  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer SEQITEM;

	@Column
	private Integer PMZ;

	@Column
	private Integer TXFINANC;

	@Column
	private Integer LUCRO;

	@Column
	private Integer MARGEM;

	@Column
	private Integer MARKUP;

	@Column
	private Integer DESPOPER;

	@Column
	private Integer CUSTOMEDIO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
	}


	public Integer getSeqitem(){
		return SEQITEM;
	}


	public void setSeqitem(Integer SEQITEM){
		this.SEQITEM = SEQITEM;
	}


	public Integer getPmz(){
		return PMZ;
	}


	public void setPmz(Integer PMZ){
		this.PMZ = PMZ;
	}


	public Integer getTxfinanc(){
		return TXFINANC;
	}


	public void setTxfinanc(Integer TXFINANC){
		this.TXFINANC = TXFINANC;
	}


	public Integer getLucro(){
		return LUCRO;
	}


	public void setLucro(Integer LUCRO){
		this.LUCRO = LUCRO;
	}


	public Integer getMargem(){
		return MARGEM;
	}


	public void setMargem(Integer MARGEM){
		this.MARGEM = MARGEM;
	}


	public Integer getMarkup(){
		return MARKUP;
	}


	public void setMarkup(Integer MARKUP){
		this.MARKUP = MARKUP;
	}


	public Integer getDespoper(){
		return DESPOPER;
	}


	public void setDespoper(Integer DESPOPER){
		this.DESPOPER = DESPOPER;
	}


	public Integer getCustomedio(){
		return CUSTOMEDIO;
	}


	public void setCustomedio(Integer CUSTOMEDIO){
		this.CUSTOMEDIO = CUSTOMEDIO;
	}

}