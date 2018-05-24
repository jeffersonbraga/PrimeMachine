package br.com.primum.modelo.viasoft.NOTAITEMCP;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NOTAITEMCP")
public class Notaitemcp  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Column
	private String CODIFICACAO;

	@Column
	private Integer GARANTIATOT;

	@Column
	private Integer IDREMGARANTIA;

	@Column
	private Integer VALSERVICO;

	@Column
	private Integer DESCFIN;

	@Column
	private String EHSERVICO;

	@Column
	private Integer DESCMAX;

	@Column
	private Integer VALORMINIMO;

	@Column
	private Integer GORDURA;

	@Column
	private Integer SEQITEMPRINC;

	@Column
	private String ADDCAIXA;

	@Column
	private Integer DESCCAIXA;

	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer SEQITEM;

	@Column
	private String IDITEMKIT;

	@Column
	private Integer JUROS;

	@Column
	private String ENTREGAR;

	@Column
	private Integer QTDEENTREGA;

	@Column
	private Integer IDLOCALRETIRADA;

	@Column
	private Integer ESTABBX;

	@Column
	private String RATEIAOUT;

	@Column
	private String COMPL;

	@Column
	private String ROMACONF;

	@Column
	private Integer QTDEROMA;

	@Column
	private String APLICACAO;

	@Column
	private String PROMOCAO;

	@Column
	private String SOMAFIN;

	@Column
	private String SOMAST;

	@Column
	private String COMPLEXP;

	@Column
	private Integer IDFAIXAGARANTIA;

	@Column
	private Integer VALORGAR;


	public String getCodificacao(){
		return CODIFICACAO;
	}


	public void setCodificacao(String CODIFICACAO){
		this.CODIFICACAO = CODIFICACAO;
	}


	public Integer getGarantiatot(){
		return GARANTIATOT;
	}


	public void setGarantiatot(Integer GARANTIATOT){
		this.GARANTIATOT = GARANTIATOT;
	}


	public Integer getIdremgarantia(){
		return IDREMGARANTIA;
	}


	public void setIdremgarantia(Integer IDREMGARANTIA){
		this.IDREMGARANTIA = IDREMGARANTIA;
	}


	public Integer getValservico(){
		return VALSERVICO;
	}


	public void setValservico(Integer VALSERVICO){
		this.VALSERVICO = VALSERVICO;
	}


	public Integer getDescfin(){
		return DESCFIN;
	}


	public void setDescfin(Integer DESCFIN){
		this.DESCFIN = DESCFIN;
	}


	public String getEhservico(){
		return EHSERVICO;
	}


	public void setEhservico(String EHSERVICO){
		this.EHSERVICO = EHSERVICO;
	}


	public Integer getDescmax(){
		return DESCMAX;
	}


	public void setDescmax(Integer DESCMAX){
		this.DESCMAX = DESCMAX;
	}


	public Integer getValorminimo(){
		return VALORMINIMO;
	}


	public void setValorminimo(Integer VALORMINIMO){
		this.VALORMINIMO = VALORMINIMO;
	}


	public Integer getGordura(){
		return GORDURA;
	}


	public void setGordura(Integer GORDURA){
		this.GORDURA = GORDURA;
	}


	public Integer getSeqitemprinc(){
		return SEQITEMPRINC;
	}


	public void setSeqitemprinc(Integer SEQITEMPRINC){
		this.SEQITEMPRINC = SEQITEMPRINC;
	}


	public String getAddcaixa(){
		return ADDCAIXA;
	}


	public void setAddcaixa(String ADDCAIXA){
		this.ADDCAIXA = ADDCAIXA;
	}


	public Integer getDesccaixa(){
		return DESCCAIXA;
	}


	public void setDesccaixa(Integer DESCCAIXA){
		this.DESCCAIXA = DESCCAIXA;
	}


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


	public String getIditemkit(){
		return IDITEMKIT;
	}


	public void setIditemkit(String IDITEMKIT){
		this.IDITEMKIT = IDITEMKIT;
	}


	public Integer getJuros(){
		return JUROS;
	}


	public void setJuros(Integer JUROS){
		this.JUROS = JUROS;
	}


	public String getEntregar(){
		return ENTREGAR;
	}


	public void setEntregar(String ENTREGAR){
		this.ENTREGAR = ENTREGAR;
	}


	public Integer getQtdeentrega(){
		return QTDEENTREGA;
	}


	public void setQtdeentrega(Integer QTDEENTREGA){
		this.QTDEENTREGA = QTDEENTREGA;
	}


	public Integer getIdlocalretirada(){
		return IDLOCALRETIRADA;
	}


	public void setIdlocalretirada(Integer IDLOCALRETIRADA){
		this.IDLOCALRETIRADA = IDLOCALRETIRADA;
	}


	public Integer getEstabbx(){
		return ESTABBX;
	}


	public void setEstabbx(Integer ESTABBX){
		this.ESTABBX = ESTABBX;
	}


	public String getRateiaout(){
		return RATEIAOUT;
	}


	public void setRateiaout(String RATEIAOUT){
		this.RATEIAOUT = RATEIAOUT;
	}


	public String getCompl(){
		return COMPL;
	}


	public void setCompl(String COMPL){
		this.COMPL = COMPL;
	}


	public String getRomaconf(){
		return ROMACONF;
	}


	public void setRomaconf(String ROMACONF){
		this.ROMACONF = ROMACONF;
	}


	public Integer getQtderoma(){
		return QTDEROMA;
	}


	public void setQtderoma(Integer QTDEROMA){
		this.QTDEROMA = QTDEROMA;
	}


	public String getAplicacao(){
		return APLICACAO;
	}


	public void setAplicacao(String APLICACAO){
		this.APLICACAO = APLICACAO;
	}


	public String getPromocao(){
		return PROMOCAO;
	}


	public void setPromocao(String PROMOCAO){
		this.PROMOCAO = PROMOCAO;
	}


	public String getSomafin(){
		return SOMAFIN;
	}


	public void setSomafin(String SOMAFIN){
		this.SOMAFIN = SOMAFIN;
	}


	public String getSomast(){
		return SOMAST;
	}


	public void setSomast(String SOMAST){
		this.SOMAST = SOMAST;
	}


	public String getComplexp(){
		return COMPLEXP;
	}


	public void setComplexp(String COMPLEXP){
		this.COMPLEXP = COMPLEXP;
	}


	public Integer getIdfaixagarantia(){
		return IDFAIXAGARANTIA;
	}


	public void setIdfaixagarantia(Integer IDFAIXAGARANTIA){
		this.IDFAIXAGARANTIA = IDFAIXAGARANTIA;
	}


	public Integer getValorgar(){
		return VALORGAR;
	}


	public void setValorgar(Integer VALORGAR){
		this.VALORGAR = VALORGAR;
	}

}