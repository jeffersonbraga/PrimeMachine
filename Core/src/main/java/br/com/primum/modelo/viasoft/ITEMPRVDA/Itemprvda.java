package br.com.primum.modelo.viasoft.ITEMPRVDA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMPRVDA")
public class Itemprvda  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDBANDEIRA;

	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private Integer PRECO;

	@Column
	private Integer PRECOOFF;

	@Column
	private Integer OFERTA;

	@Column
	private Date OFERTADTINI;

	@Column
	private Date OFERTADTFIN;

	@Column
	private Integer OFERTAQTDMIN;

	@Column
	private Integer OFERTAQTDMAX;

	@Column
	private Integer OFERTAOFF;

	@Column
	private Integer IDFAMILIAPRECO;

	@Column
	private Integer MARGEMVENDA;

	@Column
	private Integer MARGEMOFERTA;

	@Column
	private Integer DESPESASOPER;

	@Column
	private Integer PERCICMS;

	@Column
	private Integer PERCPIS;

	@Column
	private Integer PERCCOFINS;

	@Column
	private Integer MARGEMVENDAANT;

	@Column
	private Integer MARGEMOFERTAANT;

	@Column
	private Integer RESTRICAO;


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


	public Integer getPreco(){
		return PRECO;
	}


	public void setPreco(Integer PRECO){
		this.PRECO = PRECO;
	}


	public Integer getPrecooff(){
		return PRECOOFF;
	}


	public void setPrecooff(Integer PRECOOFF){
		this.PRECOOFF = PRECOOFF;
	}


	public Integer getOferta(){
		return OFERTA;
	}


	public void setOferta(Integer OFERTA){
		this.OFERTA = OFERTA;
	}


	public Date getOfertadtini(){
		return OFERTADTINI;
	}


	public void setOfertadtini(Date OFERTADTINI){
		this.OFERTADTINI = OFERTADTINI;
	}


	public Date getOfertadtfin(){
		return OFERTADTFIN;
	}


	public void setOfertadtfin(Date OFERTADTFIN){
		this.OFERTADTFIN = OFERTADTFIN;
	}


	public Integer getOfertaqtdmin(){
		return OFERTAQTDMIN;
	}


	public void setOfertaqtdmin(Integer OFERTAQTDMIN){
		this.OFERTAQTDMIN = OFERTAQTDMIN;
	}


	public Integer getOfertaqtdmax(){
		return OFERTAQTDMAX;
	}


	public void setOfertaqtdmax(Integer OFERTAQTDMAX){
		this.OFERTAQTDMAX = OFERTAQTDMAX;
	}


	public Integer getOfertaoff(){
		return OFERTAOFF;
	}


	public void setOfertaoff(Integer OFERTAOFF){
		this.OFERTAOFF = OFERTAOFF;
	}


	public Integer getIdfamiliapreco(){
		return IDFAMILIAPRECO;
	}


	public void setIdfamiliapreco(Integer IDFAMILIAPRECO){
		this.IDFAMILIAPRECO = IDFAMILIAPRECO;
	}


	public Integer getMargemvenda(){
		return MARGEMVENDA;
	}


	public void setMargemvenda(Integer MARGEMVENDA){
		this.MARGEMVENDA = MARGEMVENDA;
	}


	public Integer getMargemoferta(){
		return MARGEMOFERTA;
	}


	public void setMargemoferta(Integer MARGEMOFERTA){
		this.MARGEMOFERTA = MARGEMOFERTA;
	}


	public Integer getDespesasoper(){
		return DESPESASOPER;
	}


	public void setDespesasoper(Integer DESPESASOPER){
		this.DESPESASOPER = DESPESASOPER;
	}


	public Integer getPercicms(){
		return PERCICMS;
	}


	public void setPercicms(Integer PERCICMS){
		this.PERCICMS = PERCICMS;
	}


	public Integer getPercpis(){
		return PERCPIS;
	}


	public void setPercpis(Integer PERCPIS){
		this.PERCPIS = PERCPIS;
	}


	public Integer getPerccofins(){
		return PERCCOFINS;
	}


	public void setPerccofins(Integer PERCCOFINS){
		this.PERCCOFINS = PERCCOFINS;
	}


	public Integer getMargemvendaant(){
		return MARGEMVENDAANT;
	}


	public void setMargemvendaant(Integer MARGEMVENDAANT){
		this.MARGEMVENDAANT = MARGEMVENDAANT;
	}


	public Integer getMargemofertaant(){
		return MARGEMOFERTAANT;
	}


	public void setMargemofertaant(Integer MARGEMOFERTAANT){
		this.MARGEMOFERTAANT = MARGEMOFERTAANT;
	}


	public Integer getRestricao(){
		return RESTRICAO;
	}


	public void setRestricao(Integer RESTRICAO){
		this.RESTRICAO = RESTRICAO;
	}

}