package br.com.primum.modelo.viasoft.USERFILIAL;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="USERFILIAL")
public class Userfilial  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String USERID;

	@Column
	private Integer IDDESCCONF;

	@Column
	private Integer IDVENDEDOR;

	@Column
	private Integer PDVECF;

	@Column
	private Integer IDCONFEMAIL;

	@Column
	private String SENHAVEND;

	@Column
	private Integer PERCDESCCX;

	@Column
	private String RECNOTIFICA;

	@Column
	private Integer TEMPONOTIFICA;

	@Column
	private String NOTIFICASOM;

	@Column
	private String DIRETORIOSOM;

	@Column
	private Integer DIASMAXBOMPARA;

	@Column
	private Integer PERCLUCRATGERAL;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public Integer getIddescconf(){
		return IDDESCCONF;
	}


	public void setIddescconf(Integer IDDESCCONF){
		this.IDDESCCONF = IDDESCCONF;
	}


	public Integer getIdvendedor(){
		return IDVENDEDOR;
	}


	public void setIdvendedor(Integer IDVENDEDOR){
		this.IDVENDEDOR = IDVENDEDOR;
	}


	public Integer getPdvecf(){
		return PDVECF;
	}


	public void setPdvecf(Integer PDVECF){
		this.PDVECF = PDVECF;
	}


	public Integer getIdconfemail(){
		return IDCONFEMAIL;
	}


	public void setIdconfemail(Integer IDCONFEMAIL){
		this.IDCONFEMAIL = IDCONFEMAIL;
	}


	public String getSenhavend(){
		return SENHAVEND;
	}


	public void setSenhavend(String SENHAVEND){
		this.SENHAVEND = SENHAVEND;
	}


	public Integer getPercdesccx(){
		return PERCDESCCX;
	}


	public void setPercdesccx(Integer PERCDESCCX){
		this.PERCDESCCX = PERCDESCCX;
	}


	public String getRecnotifica(){
		return RECNOTIFICA;
	}


	public void setRecnotifica(String RECNOTIFICA){
		this.RECNOTIFICA = RECNOTIFICA;
	}


	public Integer getTemponotifica(){
		return TEMPONOTIFICA;
	}


	public void setTemponotifica(Integer TEMPONOTIFICA){
		this.TEMPONOTIFICA = TEMPONOTIFICA;
	}


	public String getNotificasom(){
		return NOTIFICASOM;
	}


	public void setNotificasom(String NOTIFICASOM){
		this.NOTIFICASOM = NOTIFICASOM;
	}


	public String getDiretoriosom(){
		return DIRETORIOSOM;
	}


	public void setDiretoriosom(String DIRETORIOSOM){
		this.DIRETORIOSOM = DIRETORIOSOM;
	}


	public Integer getDiasmaxbompara(){
		return DIASMAXBOMPARA;
	}


	public void setDiasmaxbompara(Integer DIASMAXBOMPARA){
		this.DIASMAXBOMPARA = DIASMAXBOMPARA;
	}


	public Integer getPerclucratgeral(){
		return PERCLUCRATGERAL;
	}


	public void setPerclucratgeral(Integer PERCLUCRATGERAL){
		this.PERCLUCRATGERAL = PERCLUCRATGERAL;
	}

}