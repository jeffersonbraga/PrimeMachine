package br.com.primum.modelo.viasoft.NCMST;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NCMST")
public class Ncmst  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private String NCM;

	@Column
	private String UFDEST;

	@Column
	private String UFORI;

	@Column
	private Integer PERCMAJ;

	@Column
	private Integer PAUTA;

	@Column
	private Integer MVACONSFINAL;

	@Column
	private String MVAAJUSTADO;

	@Column
	private Integer ALIQICMSST;

	@Column
	private String EXTST;

	@Column
	private Integer SEQNCM;


	public String getNcm(){
		return NCM;
	}


	public void setNcm(String NCM){
		this.NCM = NCM;
	}


	public String getUfdest(){
		return UFDEST;
	}


	public void setUfdest(String UFDEST){
		this.UFDEST = UFDEST;
	}


	public String getUfori(){
		return UFORI;
	}


	public void setUfori(String UFORI){
		this.UFORI = UFORI;
	}


	public Integer getPercmaj(){
		return PERCMAJ;
	}


	public void setPercmaj(Integer PERCMAJ){
		this.PERCMAJ = PERCMAJ;
	}


	public Integer getPauta(){
		return PAUTA;
	}


	public void setPauta(Integer PAUTA){
		this.PAUTA = PAUTA;
	}


	public Integer getMvaconsfinal(){
		return MVACONSFINAL;
	}


	public void setMvaconsfinal(Integer MVACONSFINAL){
		this.MVACONSFINAL = MVACONSFINAL;
	}


	public String getMvaajustado(){
		return MVAAJUSTADO;
	}


	public void setMvaajustado(String MVAAJUSTADO){
		this.MVAAJUSTADO = MVAAJUSTADO;
	}


	public Integer getAliqicmsst(){
		return ALIQICMSST;
	}


	public void setAliqicmsst(Integer ALIQICMSST){
		this.ALIQICMSST = ALIQICMSST;
	}


	public String getExtst(){
		return EXTST;
	}


	public void setExtst(String EXTST){
		this.EXTST = EXTST;
	}


	public Integer getSeqncm(){
		return SEQNCM;
	}


	public void setSeqncm(Integer SEQNCM){
		this.SEQNCM = SEQNCM;
	}

}