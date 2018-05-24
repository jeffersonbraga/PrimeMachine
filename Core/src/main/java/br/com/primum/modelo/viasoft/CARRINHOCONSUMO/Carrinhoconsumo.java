package br.com.primum.modelo.viasoft.CARRINHOCONSUMO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARRINHOCONSUMO")
public class Carrinhoconsumo  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDCARRINHO;

	@Column
	private Integer SEQITEM;

	@Column
	private Integer IDCORANTE;

	@Column
	private String IDITEM;

	@Column
	private Integer IDFORMULA;

	@Column
	private Integer QTDE;

	@Column
	private Integer VALORUNIT;

	@Column
	private Integer QTDENOTA;

	@Column
	private Integer FATOR;

	@Column
	private Integer IDEMBTINTA;

	@Column
	private Integer CUSTOUNIT;

	@Column
	private Integer IDCOLECAO;

	@Column
	private String FORMULAOZ;

	@Column
	private Integer CUSTOMEDIO;

	@Column
	private Integer VALORML;

	@Column
	private Integer ESTABBX;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdcarrinho(){
		return IDCARRINHO;
	}


	public void setIdcarrinho(Integer IDCARRINHO){
		this.IDCARRINHO = IDCARRINHO;
	}


	public Integer getSeqitem(){
		return SEQITEM;
	}


	public void setSeqitem(Integer SEQITEM){
		this.SEQITEM = SEQITEM;
	}


	public Integer getIdcorante(){
		return IDCORANTE;
	}


	public void setIdcorante(Integer IDCORANTE){
		this.IDCORANTE = IDCORANTE;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getIdformula(){
		return IDFORMULA;
	}


	public void setIdformula(Integer IDFORMULA){
		this.IDFORMULA = IDFORMULA;
	}


	public Integer getQtde(){
		return QTDE;
	}


	public void setQtde(Integer QTDE){
		this.QTDE = QTDE;
	}


	public Integer getValorunit(){
		return VALORUNIT;
	}


	public void setValorunit(Integer VALORUNIT){
		this.VALORUNIT = VALORUNIT;
	}


	public Integer getQtdenota(){
		return QTDENOTA;
	}


	public void setQtdenota(Integer QTDENOTA){
		this.QTDENOTA = QTDENOTA;
	}


	public Integer getFator(){
		return FATOR;
	}


	public void setFator(Integer FATOR){
		this.FATOR = FATOR;
	}


	public Integer getIdembtinta(){
		return IDEMBTINTA;
	}


	public void setIdembtinta(Integer IDEMBTINTA){
		this.IDEMBTINTA = IDEMBTINTA;
	}


	public Integer getCustounit(){
		return CUSTOUNIT;
	}


	public void setCustounit(Integer CUSTOUNIT){
		this.CUSTOUNIT = CUSTOUNIT;
	}


	public Integer getIdcolecao(){
		return IDCOLECAO;
	}


	public void setIdcolecao(Integer IDCOLECAO){
		this.IDCOLECAO = IDCOLECAO;
	}


	public String getFormulaoz(){
		return FORMULAOZ;
	}


	public void setFormulaoz(String FORMULAOZ){
		this.FORMULAOZ = FORMULAOZ;
	}


	public Integer getCustomedio(){
		return CUSTOMEDIO;
	}


	public void setCustomedio(Integer CUSTOMEDIO){
		this.CUSTOMEDIO = CUSTOMEDIO;
	}


	public Integer getValorml(){
		return VALORML;
	}


	public void setValorml(Integer VALORML){
		this.VALORML = VALORML;
	}


	public Integer getEstabbx(){
		return ESTABBX;
	}


	public void setEstabbx(Integer ESTABBX){
		this.ESTABBX = ESTABBX;
	}

}