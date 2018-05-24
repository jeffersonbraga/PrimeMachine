package br.com.primum.modelo.viasoft.ITEMPESSBAN;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMPESSBAN")
public class Itempessban  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDITEMPESSOA;

	@Column
	private Integer IDBANDEIRA;

	@Column
	private Integer ESTABITEM;

	@Column
	private String IDITEM;

	@Column
	private Integer IDPESS;

	@Column
	private String IDITEMFORN;

	@Column
	private Integer IDEMBALAGEM;

	@Column
	private Integer QTDMINCOMP;

	@Column
	private String DESCFORN;

	@Column
	private Integer PRECOPARTIDA;

	@Column
	private Integer CUSTOFINAL;

	@Column
	private String PADCOMP;

	@Column
	private String NUMLAUDO;

	@Column
	private Date DTEMISSAOLAUDO;

	@Column
	private Date DTVENCTOLAUDO;

	@Column
	private Integer ESTABULTCOMP;

	@Column
	private Integer IDNOTAULTCOMP;

	@Column
	private Integer SEQITEMULTCOMP;


	public Integer getIditempessoa(){
		return IDITEMPESSOA;
	}


	public void setIditempessoa(Integer IDITEMPESSOA){
		this.IDITEMPESSOA = IDITEMPESSOA;
	}


	public Integer getIdbandeira(){
		return IDBANDEIRA;
	}


	public void setIdbandeira(Integer IDBANDEIRA){
		this.IDBANDEIRA = IDBANDEIRA;
	}


	public Integer getEstabitem(){
		return ESTABITEM;
	}


	public void setEstabitem(Integer ESTABITEM){
		this.ESTABITEM = ESTABITEM;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public String getIditemforn(){
		return IDITEMFORN;
	}


	public void setIditemforn(String IDITEMFORN){
		this.IDITEMFORN = IDITEMFORN;
	}


	public Integer getIdembalagem(){
		return IDEMBALAGEM;
	}


	public void setIdembalagem(Integer IDEMBALAGEM){
		this.IDEMBALAGEM = IDEMBALAGEM;
	}


	public Integer getQtdmincomp(){
		return QTDMINCOMP;
	}


	public void setQtdmincomp(Integer QTDMINCOMP){
		this.QTDMINCOMP = QTDMINCOMP;
	}


	public String getDescforn(){
		return DESCFORN;
	}


	public void setDescforn(String DESCFORN){
		this.DESCFORN = DESCFORN;
	}


	public Integer getPrecopartida(){
		return PRECOPARTIDA;
	}


	public void setPrecopartida(Integer PRECOPARTIDA){
		this.PRECOPARTIDA = PRECOPARTIDA;
	}


	public Integer getCustofinal(){
		return CUSTOFINAL;
	}


	public void setCustofinal(Integer CUSTOFINAL){
		this.CUSTOFINAL = CUSTOFINAL;
	}


	public String getPadcomp(){
		return PADCOMP;
	}


	public void setPadcomp(String PADCOMP){
		this.PADCOMP = PADCOMP;
	}


	public String getNumlaudo(){
		return NUMLAUDO;
	}


	public void setNumlaudo(String NUMLAUDO){
		this.NUMLAUDO = NUMLAUDO;
	}


	public Date getDtemissaolaudo(){
		return DTEMISSAOLAUDO;
	}


	public void setDtemissaolaudo(Date DTEMISSAOLAUDO){
		this.DTEMISSAOLAUDO = DTEMISSAOLAUDO;
	}


	public Date getDtvenctolaudo(){
		return DTVENCTOLAUDO;
	}


	public void setDtvenctolaudo(Date DTVENCTOLAUDO){
		this.DTVENCTOLAUDO = DTVENCTOLAUDO;
	}


	public Integer getEstabultcomp(){
		return ESTABULTCOMP;
	}


	public void setEstabultcomp(Integer ESTABULTCOMP){
		this.ESTABULTCOMP = ESTABULTCOMP;
	}


	public Integer getIdnotaultcomp(){
		return IDNOTAULTCOMP;
	}


	public void setIdnotaultcomp(Integer IDNOTAULTCOMP){
		this.IDNOTAULTCOMP = IDNOTAULTCOMP;
	}


	public Integer getSeqitemultcomp(){
		return SEQITEMULTCOMP;
	}


	public void setSeqitemultcomp(Integer SEQITEMULTCOMP){
		this.SEQITEMULTCOMP = SEQITEMULTCOMP;
	}

}