package br.com.primum.modelo.viasoft.CUSTOESTHIST;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CUSTOESTHIST")
public class Custoesthist  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Column
	private Integer IDBANDEIRA;

	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private Date DATA;

	@Column
	private Integer CUSTOEST;

	@Column
	private Integer DESCONTO;

	@Column
	private String USERID;

	@Column
	private Integer FRETE;

	@Column
	private Integer FRETECT;

	@Column
	private Integer OUTROS;

	@Column
	private Integer SEGURO;

	@Column
	private Integer DESPESAS;

	@Column
	private Integer VICMS;

	@Column
	private Integer VST;

	@Column
	private Integer VIPI;

	@Column
	private Integer VPIS;

	@Column
	private Integer VCOFINS;

	@Column
	private Integer VICMSFRE;

	@Column
	private Integer VIPIFRE;

	@Column
	private Integer VICMSFRECT;

	@Column
	private Integer PDESC;

	@Column
	private Integer CUSTOBRUTO;

	@Column
	private Integer AICMS;

	@Column
	private Integer AST;

	@Column
	private Integer AIPI;

	@Column
	private Integer APIS;

	@Column
	private Integer ACOFINS;

	@Column
	private Integer BST;

	@Id
	@Column
	private Integer IDHIST;


	public Integer getIdbandeira(){
		return IDBANDEIRA;
	}


	public void setIdbandeira(Integer IDBANDEIRA){
		this.IDBANDEIRA = IDBANDEIRA;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
	}


	public Integer getCustoest(){
		return CUSTOEST;
	}


	public void setCustoest(Integer CUSTOEST){
		this.CUSTOEST = CUSTOEST;
	}


	public Integer getDesconto(){
		return DESCONTO;
	}


	public void setDesconto(Integer DESCONTO){
		this.DESCONTO = DESCONTO;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public Integer getFrete(){
		return FRETE;
	}


	public void setFrete(Integer FRETE){
		this.FRETE = FRETE;
	}


	public Integer getFretect(){
		return FRETECT;
	}


	public void setFretect(Integer FRETECT){
		this.FRETECT = FRETECT;
	}


	public Integer getOutros(){
		return OUTROS;
	}


	public void setOutros(Integer OUTROS){
		this.OUTROS = OUTROS;
	}


	public Integer getSeguro(){
		return SEGURO;
	}


	public void setSeguro(Integer SEGURO){
		this.SEGURO = SEGURO;
	}


	public Integer getDespesas(){
		return DESPESAS;
	}


	public void setDespesas(Integer DESPESAS){
		this.DESPESAS = DESPESAS;
	}


	public Integer getVicms(){
		return VICMS;
	}


	public void setVicms(Integer VICMS){
		this.VICMS = VICMS;
	}


	public Integer getVst(){
		return VST;
	}


	public void setVst(Integer VST){
		this.VST = VST;
	}


	public Integer getVipi(){
		return VIPI;
	}


	public void setVipi(Integer VIPI){
		this.VIPI = VIPI;
	}


	public Integer getVpis(){
		return VPIS;
	}


	public void setVpis(Integer VPIS){
		this.VPIS = VPIS;
	}


	public Integer getVcofins(){
		return VCOFINS;
	}


	public void setVcofins(Integer VCOFINS){
		this.VCOFINS = VCOFINS;
	}


	public Integer getVicmsfre(){
		return VICMSFRE;
	}


	public void setVicmsfre(Integer VICMSFRE){
		this.VICMSFRE = VICMSFRE;
	}


	public Integer getVipifre(){
		return VIPIFRE;
	}


	public void setVipifre(Integer VIPIFRE){
		this.VIPIFRE = VIPIFRE;
	}


	public Integer getVicmsfrect(){
		return VICMSFRECT;
	}


	public void setVicmsfrect(Integer VICMSFRECT){
		this.VICMSFRECT = VICMSFRECT;
	}


	public Integer getPdesc(){
		return PDESC;
	}


	public void setPdesc(Integer PDESC){
		this.PDESC = PDESC;
	}


	public Integer getCustobruto(){
		return CUSTOBRUTO;
	}


	public void setCustobruto(Integer CUSTOBRUTO){
		this.CUSTOBRUTO = CUSTOBRUTO;
	}


	public Integer getAicms(){
		return AICMS;
	}


	public void setAicms(Integer AICMS){
		this.AICMS = AICMS;
	}


	public Integer getAst(){
		return AST;
	}


	public void setAst(Integer AST){
		this.AST = AST;
	}


	public Integer getAipi(){
		return AIPI;
	}


	public void setAipi(Integer AIPI){
		this.AIPI = AIPI;
	}


	public Integer getApis(){
		return APIS;
	}


	public void setApis(Integer APIS){
		this.APIS = APIS;
	}


	public Integer getAcofins(){
		return ACOFINS;
	}


	public void setAcofins(Integer ACOFINS){
		this.ACOFINS = ACOFINS;
	}


	public Integer getBst(){
		return BST;
	}


	public void setBst(Integer BST){
		this.BST = BST;
	}


	public Integer getIdhist(){
		return IDHIST;
	}


	public void setIdhist(Integer IDHIST){
		this.IDHIST = IDHIST;
	}

}