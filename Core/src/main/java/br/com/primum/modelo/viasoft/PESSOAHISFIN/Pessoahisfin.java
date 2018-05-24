package br.com.primum.modelo.viasoft.PESSOAHISFIN;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOAHISFIN")
public class Pessoahisfin  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDPESS;

	@Column
	private Integer QTDCOMPRA;

	@Column
	private Integer VLRCOMPRA;

	@Column
	private Integer DESCCOMPRA;

	@Column
	private Date PRICOMPRA;

	@Column
	private Date ULTCOMPRA;

	@Column
	private Integer ATRASOMEDIO;

	@Column
	private Integer QTDDUP;

	@Column
	private Integer VLRDUP;

	@Column
	private Integer DESCDUP;

	@Column
	private Integer PRAZOMEDIO;

	@Column
	private Integer ESTABPRICOMP;

	@Column
	private Integer ESTABULTCOMP;

	@Column
	private Integer VALPRICOMP;

	@Column
	private Integer VALULTCOMP;

	@Column
	private Integer ESTABMAIORCOMP;

	@Column
	private Date DTMAIORCOMP;

	@Column
	private Integer VALMAIORCOMP;


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


	public Integer getQtdcompra(){
		return QTDCOMPRA;
	}


	public void setQtdcompra(Integer QTDCOMPRA){
		this.QTDCOMPRA = QTDCOMPRA;
	}


	public Integer getVlrcompra(){
		return VLRCOMPRA;
	}


	public void setVlrcompra(Integer VLRCOMPRA){
		this.VLRCOMPRA = VLRCOMPRA;
	}


	public Integer getDesccompra(){
		return DESCCOMPRA;
	}


	public void setDesccompra(Integer DESCCOMPRA){
		this.DESCCOMPRA = DESCCOMPRA;
	}


	public Date getPricompra(){
		return PRICOMPRA;
	}


	public void setPricompra(Date PRICOMPRA){
		this.PRICOMPRA = PRICOMPRA;
	}


	public Date getUltcompra(){
		return ULTCOMPRA;
	}


	public void setUltcompra(Date ULTCOMPRA){
		this.ULTCOMPRA = ULTCOMPRA;
	}


	public Integer getAtrasomedio(){
		return ATRASOMEDIO;
	}


	public void setAtrasomedio(Integer ATRASOMEDIO){
		this.ATRASOMEDIO = ATRASOMEDIO;
	}


	public Integer getQtddup(){
		return QTDDUP;
	}


	public void setQtddup(Integer QTDDUP){
		this.QTDDUP = QTDDUP;
	}


	public Integer getVlrdup(){
		return VLRDUP;
	}


	public void setVlrdup(Integer VLRDUP){
		this.VLRDUP = VLRDUP;
	}


	public Integer getDescdup(){
		return DESCDUP;
	}


	public void setDescdup(Integer DESCDUP){
		this.DESCDUP = DESCDUP;
	}


	public Integer getPrazomedio(){
		return PRAZOMEDIO;
	}


	public void setPrazomedio(Integer PRAZOMEDIO){
		this.PRAZOMEDIO = PRAZOMEDIO;
	}


	public Integer getEstabpricomp(){
		return ESTABPRICOMP;
	}


	public void setEstabpricomp(Integer ESTABPRICOMP){
		this.ESTABPRICOMP = ESTABPRICOMP;
	}


	public Integer getEstabultcomp(){
		return ESTABULTCOMP;
	}


	public void setEstabultcomp(Integer ESTABULTCOMP){
		this.ESTABULTCOMP = ESTABULTCOMP;
	}


	public Integer getValpricomp(){
		return VALPRICOMP;
	}


	public void setValpricomp(Integer VALPRICOMP){
		this.VALPRICOMP = VALPRICOMP;
	}


	public Integer getValultcomp(){
		return VALULTCOMP;
	}


	public void setValultcomp(Integer VALULTCOMP){
		this.VALULTCOMP = VALULTCOMP;
	}


	public Integer getEstabmaiorcomp(){
		return ESTABMAIORCOMP;
	}


	public void setEstabmaiorcomp(Integer ESTABMAIORCOMP){
		this.ESTABMAIORCOMP = ESTABMAIORCOMP;
	}


	public Date getDtmaiorcomp(){
		return DTMAIORCOMP;
	}


	public void setDtmaiorcomp(Date DTMAIORCOMP){
		this.DTMAIORCOMP = DTMAIORCOMP;
	}


	public Integer getValmaiorcomp(){
		return VALMAIORCOMP;
	}


	public void setValmaiorcomp(Integer VALMAIORCOMP){
		this.VALMAIORCOMP = VALMAIORCOMP;
	}

}