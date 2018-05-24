package br.com.primum.modelo.viasoft.COTACAOITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="COTACAOITEM")
public class Cotacaoitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDPEDIDO;

	@Column
	private Integer IDPESS;

	@Column
	private Integer SEQITEM;

	@Column
	private Integer VUNITARIO;

	@Column
	private Integer BICMS;

	@Column
	private Integer AICMS;

	@Column
	private Integer VICMS;

	@Column
	private Integer MVA;

	@Column
	private Integer AST;

	@Column
	private Integer VST;

	@Column
	private Integer BIPI;

	@Column
	private Integer AIPI;

	@Column
	private Integer VIPI;

	@Column
	private Integer FRETE;

	@Column
	private Integer DESCONTO;

	@Column
	private Integer DESCITEM;

	@Column
	private Integer TOTAL;

	@Column
	private Integer BPIS;

	@Column
	private Integer APIS;

	@Column
	private Integer VPIS;

	@Column
	private Integer BCOFINS;

	@Column
	private Integer ACOFINS;

	@Column
	private Integer VCOFINS;

	@Column
	private Integer CUSTO;

	@Column
	private Integer IDPEDCOT;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdpedido(){
		return IDPEDIDO;
	}


	public void setIdpedido(Integer IDPEDIDO){
		this.IDPEDIDO = IDPEDIDO;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getSeqitem(){
		return SEQITEM;
	}


	public void setSeqitem(Integer SEQITEM){
		this.SEQITEM = SEQITEM;
	}


	public Integer getVunitario(){
		return VUNITARIO;
	}


	public void setVunitario(Integer VUNITARIO){
		this.VUNITARIO = VUNITARIO;
	}


	public Integer getBicms(){
		return BICMS;
	}


	public void setBicms(Integer BICMS){
		this.BICMS = BICMS;
	}


	public Integer getAicms(){
		return AICMS;
	}


	public void setAicms(Integer AICMS){
		this.AICMS = AICMS;
	}


	public Integer getVicms(){
		return VICMS;
	}


	public void setVicms(Integer VICMS){
		this.VICMS = VICMS;
	}


	public Integer getMva(){
		return MVA;
	}


	public void setMva(Integer MVA){
		this.MVA = MVA;
	}


	public Integer getAst(){
		return AST;
	}


	public void setAst(Integer AST){
		this.AST = AST;
	}


	public Integer getVst(){
		return VST;
	}


	public void setVst(Integer VST){
		this.VST = VST;
	}


	public Integer getBipi(){
		return BIPI;
	}


	public void setBipi(Integer BIPI){
		this.BIPI = BIPI;
	}


	public Integer getAipi(){
		return AIPI;
	}


	public void setAipi(Integer AIPI){
		this.AIPI = AIPI;
	}


	public Integer getVipi(){
		return VIPI;
	}


	public void setVipi(Integer VIPI){
		this.VIPI = VIPI;
	}


	public Integer getFrete(){
		return FRETE;
	}


	public void setFrete(Integer FRETE){
		this.FRETE = FRETE;
	}


	public Integer getDesconto(){
		return DESCONTO;
	}


	public void setDesconto(Integer DESCONTO){
		this.DESCONTO = DESCONTO;
	}


	public Integer getDescitem(){
		return DESCITEM;
	}


	public void setDescitem(Integer DESCITEM){
		this.DESCITEM = DESCITEM;
	}


	public Integer getTotal(){
		return TOTAL;
	}


	public void setTotal(Integer TOTAL){
		this.TOTAL = TOTAL;
	}


	public Integer getBpis(){
		return BPIS;
	}


	public void setBpis(Integer BPIS){
		this.BPIS = BPIS;
	}


	public Integer getApis(){
		return APIS;
	}


	public void setApis(Integer APIS){
		this.APIS = APIS;
	}


	public Integer getVpis(){
		return VPIS;
	}


	public void setVpis(Integer VPIS){
		this.VPIS = VPIS;
	}


	public Integer getBcofins(){
		return BCOFINS;
	}


	public void setBcofins(Integer BCOFINS){
		this.BCOFINS = BCOFINS;
	}


	public Integer getAcofins(){
		return ACOFINS;
	}


	public void setAcofins(Integer ACOFINS){
		this.ACOFINS = ACOFINS;
	}


	public Integer getVcofins(){
		return VCOFINS;
	}


	public void setVcofins(Integer VCOFINS){
		this.VCOFINS = VCOFINS;
	}


	public Integer getCusto(){
		return CUSTO;
	}


	public void setCusto(Integer CUSTO){
		this.CUSTO = CUSTO;
	}


	public Integer getIdpedcot(){
		return IDPEDCOT;
	}


	public void setIdpedcot(Integer IDPEDCOT){
		this.IDPEDCOT = IDPEDCOT;
	}

}