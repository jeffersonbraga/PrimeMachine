package br.com.primum.modelo.viasoft.ITEMSIMULAPR;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMSIMULAPR")
public class Itemsimulapr  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer ESTABITEM;

	@Column
	private String IDITEM;

	@Column
	private Integer VLRUNENT;

	@Column
	private Integer FRETEENT;

	@Column
	private Integer SEGUROENT;

	@Column
	private Integer DESPESASENT;

	@Column
	private Integer TXFINANCENT;

	@Column
	private Integer ALIQICMSENT;

	@Column
	private Integer BASESTENT;

	@Column
	private Integer ALIQSTENT;

	@Column
	private Integer ALIQIPIENT;

	@Column
	private Integer ALIQPISENT;

	@Column
	private Integer ALIQCOFINSENT;

	@Column
	private Integer DESPESASOPER;

	@Column
	private Integer ALIQICMSSAI;

	@Column
	private Integer ALIQPISSAI;

	@Column
	private Integer ALIQCOFINSSAI;

	@Column
	private Integer MARGEMOFERTA;

	@Column
	private Integer MARGEMPRECO;

	@Column
	private Integer FRETECT;

	@Column
	private Integer ALIQICMSFRETECT;

	@Column
	private Integer DESCONTOENT;

	@Column
	private Integer ALIQDIFICMSENT;

	@Column
	private Integer MARGPREDIR;

	@Column
	private Integer MARGOFERDIR;

	@Column
	private String MARGDIRSOB;

	@Column
	private Integer PDESC;

	@Column
	private String PDESCDET;

	@Column
	private Integer OUTROS;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
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


	public Integer getVlrunent(){
		return VLRUNENT;
	}


	public void setVlrunent(Integer VLRUNENT){
		this.VLRUNENT = VLRUNENT;
	}


	public Integer getFreteent(){
		return FRETEENT;
	}


	public void setFreteent(Integer FRETEENT){
		this.FRETEENT = FRETEENT;
	}


	public Integer getSeguroent(){
		return SEGUROENT;
	}


	public void setSeguroent(Integer SEGUROENT){
		this.SEGUROENT = SEGUROENT;
	}


	public Integer getDespesasent(){
		return DESPESASENT;
	}


	public void setDespesasent(Integer DESPESASENT){
		this.DESPESASENT = DESPESASENT;
	}


	public Integer getTxfinancent(){
		return TXFINANCENT;
	}


	public void setTxfinancent(Integer TXFINANCENT){
		this.TXFINANCENT = TXFINANCENT;
	}


	public Integer getAliqicmsent(){
		return ALIQICMSENT;
	}


	public void setAliqicmsent(Integer ALIQICMSENT){
		this.ALIQICMSENT = ALIQICMSENT;
	}


	public Integer getBasestent(){
		return BASESTENT;
	}


	public void setBasestent(Integer BASESTENT){
		this.BASESTENT = BASESTENT;
	}


	public Integer getAliqstent(){
		return ALIQSTENT;
	}


	public void setAliqstent(Integer ALIQSTENT){
		this.ALIQSTENT = ALIQSTENT;
	}


	public Integer getAliqipient(){
		return ALIQIPIENT;
	}


	public void setAliqipient(Integer ALIQIPIENT){
		this.ALIQIPIENT = ALIQIPIENT;
	}


	public Integer getAliqpisent(){
		return ALIQPISENT;
	}


	public void setAliqpisent(Integer ALIQPISENT){
		this.ALIQPISENT = ALIQPISENT;
	}


	public Integer getAliqcofinsent(){
		return ALIQCOFINSENT;
	}


	public void setAliqcofinsent(Integer ALIQCOFINSENT){
		this.ALIQCOFINSENT = ALIQCOFINSENT;
	}


	public Integer getDespesasoper(){
		return DESPESASOPER;
	}


	public void setDespesasoper(Integer DESPESASOPER){
		this.DESPESASOPER = DESPESASOPER;
	}


	public Integer getAliqicmssai(){
		return ALIQICMSSAI;
	}


	public void setAliqicmssai(Integer ALIQICMSSAI){
		this.ALIQICMSSAI = ALIQICMSSAI;
	}


	public Integer getAliqpissai(){
		return ALIQPISSAI;
	}


	public void setAliqpissai(Integer ALIQPISSAI){
		this.ALIQPISSAI = ALIQPISSAI;
	}


	public Integer getAliqcofinssai(){
		return ALIQCOFINSSAI;
	}


	public void setAliqcofinssai(Integer ALIQCOFINSSAI){
		this.ALIQCOFINSSAI = ALIQCOFINSSAI;
	}


	public Integer getMargemoferta(){
		return MARGEMOFERTA;
	}


	public void setMargemoferta(Integer MARGEMOFERTA){
		this.MARGEMOFERTA = MARGEMOFERTA;
	}


	public Integer getMargempreco(){
		return MARGEMPRECO;
	}


	public void setMargempreco(Integer MARGEMPRECO){
		this.MARGEMPRECO = MARGEMPRECO;
	}


	public Integer getFretect(){
		return FRETECT;
	}


	public void setFretect(Integer FRETECT){
		this.FRETECT = FRETECT;
	}


	public Integer getAliqicmsfretect(){
		return ALIQICMSFRETECT;
	}


	public void setAliqicmsfretect(Integer ALIQICMSFRETECT){
		this.ALIQICMSFRETECT = ALIQICMSFRETECT;
	}


	public Integer getDescontoent(){
		return DESCONTOENT;
	}


	public void setDescontoent(Integer DESCONTOENT){
		this.DESCONTOENT = DESCONTOENT;
	}


	public Integer getAliqdificmsent(){
		return ALIQDIFICMSENT;
	}


	public void setAliqdificmsent(Integer ALIQDIFICMSENT){
		this.ALIQDIFICMSENT = ALIQDIFICMSENT;
	}


	public Integer getMargpredir(){
		return MARGPREDIR;
	}


	public void setMargpredir(Integer MARGPREDIR){
		this.MARGPREDIR = MARGPREDIR;
	}


	public Integer getMargoferdir(){
		return MARGOFERDIR;
	}


	public void setMargoferdir(Integer MARGOFERDIR){
		this.MARGOFERDIR = MARGOFERDIR;
	}


	public String getMargdirsob(){
		return MARGDIRSOB;
	}


	public void setMargdirsob(String MARGDIRSOB){
		this.MARGDIRSOB = MARGDIRSOB;
	}


	public Integer getPdesc(){
		return PDESC;
	}


	public void setPdesc(Integer PDESC){
		this.PDESC = PDESC;
	}


	public String getPdescdet(){
		return PDESCDET;
	}


	public void setPdescdet(String PDESCDET){
		this.PDESCDET = PDESCDET;
	}


	public Integer getOutros(){
		return OUTROS;
	}


	public void setOutros(Integer OUTROS){
		this.OUTROS = OUTROS;
	}

}