package br.com.primum.modelo.viasoft.FILIALCONFPROC;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FILIALCONFPROC")
public class Filialconfproc  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Column
	private Integer IDNOTACFGPADSAI;

	@Column
	private String CUSTOACOU;

	@Column
	private String CUSTOPAD;

	@Column
	private Integer IDNOTACONFTSDEP;

	@Column
	private Integer IDESTOQUETIPOTSDEP;

	@Column
	private String DEPOSITOLOJA;

	@Column
	private Integer PRODAGREGPOR;

	@Column
	private Integer IDPEDIDOCFGSOL;

	@Column
	private Integer MARGEMVALIDAKMFROTA;

	@Column
	private String AGRUPAAGRE;

	@Column
	private String VALLIMVENDASMES;

	@Column
	private Integer IDNOTACONFTRANSP;

	@Column
	private Integer IDNOTACONFTRANSD;

	@Column
	private Integer MARGEMVALIDADATAFROTA;

	@Column
	private Integer QTDFURONUMDOC;

	@Column
	private String NFTRANSPCX;

	@Column
	private String SELCONFTRANSPCX;

	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDPEDIDOCFGENT;

	@Column
	private Integer IDPEDIDOCFGSAI;

	@Column
	private String COMPRACENT;

	@Column
	private String ENTPEDSALDOENT;

	@Column
	private String ENTPEDSALDOSAI;

	@Column
	private String ATUITENSFORN;

	@Column
	private String ATPRECOOFF;

	@Column
	private String ATOFERTAOFF;

	@Column
	private String ACAOLIMITE;

	@Column
	private String ACAOSDODEV;

	@Column
	private String VALNOTA;

	@Column
	private String VALPED;

	@Column
	private String VALLIMDUP;

	@Column
	private String VALLIMDUPPEN;

	@Column
	private String VALLIMCHE;

	@Column
	private String VALLIMCHEPEN;

	@Column
	private String VALSDODUP;

	@Column
	private String VALSDODUPPEN;

	@Column
	private String VALSDOCHE;

	@Column
	private String VALSDOCHEPEN;

	@Column
	private String VALLIMDIN;

	@Column
	private Integer IDNOTACONFTE;

	@Column
	private Integer IDNOTACONFTS;

	@Column
	private String IAT;

	@Column
	private String VALSDODEVDIN;

	@Column
	private String VALCARRINHO;

	@Column
	private Integer PERCVARCUSTO;

	@Column
	private String CONTROLAENTREGA;

	@Column
	private String CONFERENFIMP;

	@Column
	private String BLOQCXEXP;

	@Column
	private String EXPEDICAOLIBERADA;

	@Column
	private String DEVOLCOMEXP;

	@Column
	private String ATUALIZANCM;

	@Column
	private Integer REFRESHEXP;

	@Column
	private String EXCPRODIMPNFE;

	@Column
	private Integer IDPEDIDOCFGCOT;

	@Column
	private String VALSDOCTAPESS;

	@Column
	private String VALLIMCTAPESS;

	@Column
	private String VALLIMCHEQDEV;

	@Column
	private String VALSDOCHEQDEV;

	@Column
	private String CONTROLEROMA;

	@Column
	private String CONSITENSNAOCONT;

	@Column
	private String EXPCLIPADRAO;

	@Column
	private String ESTNEGATIVO;

	@Column
	private String CTLMONTAGEM;

	@Column
	private String REORDENATRIB;

	@Column
	private String CONSIDERAQTDEXP;

	@Column
	private String XMLSEMPEDIDO;

	@Column
	private String EXPVENDAPENDENTE;

	@Column
	private Integer IDPEDIDOCFGBON;

	@Column
	private Integer EXPMAXDIASPREVENT;

	@Column
	private String VALLOCRET;

	@Column
	private String ALOCAKITPARCIAL;

	@Column
	private Integer LOCALCONSUMO;

	@Column
	private Integer IDNOTACFGACOUENT;

	@Column
	private Integer IDNOTACFGACOUSAI;

	@Column
	private Integer IDNOTACFGPADENT;


	public Integer getIdnotacfgpadsai(){
		return IDNOTACFGPADSAI;
	}


	public void setIdnotacfgpadsai(Integer IDNOTACFGPADSAI){
		this.IDNOTACFGPADSAI = IDNOTACFGPADSAI;
	}


	public String getCustoacou(){
		return CUSTOACOU;
	}


	public void setCustoacou(String CUSTOACOU){
		this.CUSTOACOU = CUSTOACOU;
	}


	public String getCustopad(){
		return CUSTOPAD;
	}


	public void setCustopad(String CUSTOPAD){
		this.CUSTOPAD = CUSTOPAD;
	}


	public Integer getIdnotaconftsdep(){
		return IDNOTACONFTSDEP;
	}


	public void setIdnotaconftsdep(Integer IDNOTACONFTSDEP){
		this.IDNOTACONFTSDEP = IDNOTACONFTSDEP;
	}


	public Integer getIdestoquetipotsdep(){
		return IDESTOQUETIPOTSDEP;
	}


	public void setIdestoquetipotsdep(Integer IDESTOQUETIPOTSDEP){
		this.IDESTOQUETIPOTSDEP = IDESTOQUETIPOTSDEP;
	}


	public String getDepositoloja(){
		return DEPOSITOLOJA;
	}


	public void setDepositoloja(String DEPOSITOLOJA){
		this.DEPOSITOLOJA = DEPOSITOLOJA;
	}


	public Integer getProdagregpor(){
		return PRODAGREGPOR;
	}


	public void setProdagregpor(Integer PRODAGREGPOR){
		this.PRODAGREGPOR = PRODAGREGPOR;
	}


	public Integer getIdpedidocfgsol(){
		return IDPEDIDOCFGSOL;
	}


	public void setIdpedidocfgsol(Integer IDPEDIDOCFGSOL){
		this.IDPEDIDOCFGSOL = IDPEDIDOCFGSOL;
	}


	public Integer getMargemvalidakmfrota(){
		return MARGEMVALIDAKMFROTA;
	}


	public void setMargemvalidakmfrota(Integer MARGEMVALIDAKMFROTA){
		this.MARGEMVALIDAKMFROTA = MARGEMVALIDAKMFROTA;
	}


	public String getAgrupaagre(){
		return AGRUPAAGRE;
	}


	public void setAgrupaagre(String AGRUPAAGRE){
		this.AGRUPAAGRE = AGRUPAAGRE;
	}


	public String getVallimvendasmes(){
		return VALLIMVENDASMES;
	}


	public void setVallimvendasmes(String VALLIMVENDASMES){
		this.VALLIMVENDASMES = VALLIMVENDASMES;
	}


	public Integer getIdnotaconftransp(){
		return IDNOTACONFTRANSP;
	}


	public void setIdnotaconftransp(Integer IDNOTACONFTRANSP){
		this.IDNOTACONFTRANSP = IDNOTACONFTRANSP;
	}


	public Integer getIdnotaconftransd(){
		return IDNOTACONFTRANSD;
	}


	public void setIdnotaconftransd(Integer IDNOTACONFTRANSD){
		this.IDNOTACONFTRANSD = IDNOTACONFTRANSD;
	}


	public Integer getMargemvalidadatafrota(){
		return MARGEMVALIDADATAFROTA;
	}


	public void setMargemvalidadatafrota(Integer MARGEMVALIDADATAFROTA){
		this.MARGEMVALIDADATAFROTA = MARGEMVALIDADATAFROTA;
	}


	public Integer getQtdfuronumdoc(){
		return QTDFURONUMDOC;
	}


	public void setQtdfuronumdoc(Integer QTDFURONUMDOC){
		this.QTDFURONUMDOC = QTDFURONUMDOC;
	}


	public String getNftranspcx(){
		return NFTRANSPCX;
	}


	public void setNftranspcx(String NFTRANSPCX){
		this.NFTRANSPCX = NFTRANSPCX;
	}


	public String getSelconftranspcx(){
		return SELCONFTRANSPCX;
	}


	public void setSelconftranspcx(String SELCONFTRANSPCX){
		this.SELCONFTRANSPCX = SELCONFTRANSPCX;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdpedidocfgent(){
		return IDPEDIDOCFGENT;
	}


	public void setIdpedidocfgent(Integer IDPEDIDOCFGENT){
		this.IDPEDIDOCFGENT = IDPEDIDOCFGENT;
	}


	public Integer getIdpedidocfgsai(){
		return IDPEDIDOCFGSAI;
	}


	public void setIdpedidocfgsai(Integer IDPEDIDOCFGSAI){
		this.IDPEDIDOCFGSAI = IDPEDIDOCFGSAI;
	}


	public String getCompracent(){
		return COMPRACENT;
	}


	public void setCompracent(String COMPRACENT){
		this.COMPRACENT = COMPRACENT;
	}


	public String getEntpedsaldoent(){
		return ENTPEDSALDOENT;
	}


	public void setEntpedsaldoent(String ENTPEDSALDOENT){
		this.ENTPEDSALDOENT = ENTPEDSALDOENT;
	}


	public String getEntpedsaldosai(){
		return ENTPEDSALDOSAI;
	}


	public void setEntpedsaldosai(String ENTPEDSALDOSAI){
		this.ENTPEDSALDOSAI = ENTPEDSALDOSAI;
	}


	public String getAtuitensforn(){
		return ATUITENSFORN;
	}


	public void setAtuitensforn(String ATUITENSFORN){
		this.ATUITENSFORN = ATUITENSFORN;
	}


	public String getAtprecooff(){
		return ATPRECOOFF;
	}


	public void setAtprecooff(String ATPRECOOFF){
		this.ATPRECOOFF = ATPRECOOFF;
	}


	public String getAtofertaoff(){
		return ATOFERTAOFF;
	}


	public void setAtofertaoff(String ATOFERTAOFF){
		this.ATOFERTAOFF = ATOFERTAOFF;
	}


	public String getAcaolimite(){
		return ACAOLIMITE;
	}


	public void setAcaolimite(String ACAOLIMITE){
		this.ACAOLIMITE = ACAOLIMITE;
	}


	public String getAcaosdodev(){
		return ACAOSDODEV;
	}


	public void setAcaosdodev(String ACAOSDODEV){
		this.ACAOSDODEV = ACAOSDODEV;
	}


	public String getValnota(){
		return VALNOTA;
	}


	public void setValnota(String VALNOTA){
		this.VALNOTA = VALNOTA;
	}


	public String getValped(){
		return VALPED;
	}


	public void setValped(String VALPED){
		this.VALPED = VALPED;
	}


	public String getVallimdup(){
		return VALLIMDUP;
	}


	public void setVallimdup(String VALLIMDUP){
		this.VALLIMDUP = VALLIMDUP;
	}


	public String getVallimduppen(){
		return VALLIMDUPPEN;
	}


	public void setVallimduppen(String VALLIMDUPPEN){
		this.VALLIMDUPPEN = VALLIMDUPPEN;
	}


	public String getVallimche(){
		return VALLIMCHE;
	}


	public void setVallimche(String VALLIMCHE){
		this.VALLIMCHE = VALLIMCHE;
	}


	public String getVallimchepen(){
		return VALLIMCHEPEN;
	}


	public void setVallimchepen(String VALLIMCHEPEN){
		this.VALLIMCHEPEN = VALLIMCHEPEN;
	}


	public String getValsdodup(){
		return VALSDODUP;
	}


	public void setValsdodup(String VALSDODUP){
		this.VALSDODUP = VALSDODUP;
	}


	public String getValsdoduppen(){
		return VALSDODUPPEN;
	}


	public void setValsdoduppen(String VALSDODUPPEN){
		this.VALSDODUPPEN = VALSDODUPPEN;
	}


	public String getValsdoche(){
		return VALSDOCHE;
	}


	public void setValsdoche(String VALSDOCHE){
		this.VALSDOCHE = VALSDOCHE;
	}


	public String getValsdochepen(){
		return VALSDOCHEPEN;
	}


	public void setValsdochepen(String VALSDOCHEPEN){
		this.VALSDOCHEPEN = VALSDOCHEPEN;
	}


	public String getVallimdin(){
		return VALLIMDIN;
	}


	public void setVallimdin(String VALLIMDIN){
		this.VALLIMDIN = VALLIMDIN;
	}


	public Integer getIdnotaconfte(){
		return IDNOTACONFTE;
	}


	public void setIdnotaconfte(Integer IDNOTACONFTE){
		this.IDNOTACONFTE = IDNOTACONFTE;
	}


	public Integer getIdnotaconfts(){
		return IDNOTACONFTS;
	}


	public void setIdnotaconfts(Integer IDNOTACONFTS){
		this.IDNOTACONFTS = IDNOTACONFTS;
	}


	public String getIat(){
		return IAT;
	}


	public void setIat(String IAT){
		this.IAT = IAT;
	}


	public String getValsdodevdin(){
		return VALSDODEVDIN;
	}


	public void setValsdodevdin(String VALSDODEVDIN){
		this.VALSDODEVDIN = VALSDODEVDIN;
	}


	public String getValcarrinho(){
		return VALCARRINHO;
	}


	public void setValcarrinho(String VALCARRINHO){
		this.VALCARRINHO = VALCARRINHO;
	}


	public Integer getPercvarcusto(){
		return PERCVARCUSTO;
	}


	public void setPercvarcusto(Integer PERCVARCUSTO){
		this.PERCVARCUSTO = PERCVARCUSTO;
	}


	public String getControlaentrega(){
		return CONTROLAENTREGA;
	}


	public void setControlaentrega(String CONTROLAENTREGA){
		this.CONTROLAENTREGA = CONTROLAENTREGA;
	}


	public String getConferenfimp(){
		return CONFERENFIMP;
	}


	public void setConferenfimp(String CONFERENFIMP){
		this.CONFERENFIMP = CONFERENFIMP;
	}


	public String getBloqcxexp(){
		return BLOQCXEXP;
	}


	public void setBloqcxexp(String BLOQCXEXP){
		this.BLOQCXEXP = BLOQCXEXP;
	}


	public String getExpedicaoliberada(){
		return EXPEDICAOLIBERADA;
	}


	public void setExpedicaoliberada(String EXPEDICAOLIBERADA){
		this.EXPEDICAOLIBERADA = EXPEDICAOLIBERADA;
	}


	public String getDevolcomexp(){
		return DEVOLCOMEXP;
	}


	public void setDevolcomexp(String DEVOLCOMEXP){
		this.DEVOLCOMEXP = DEVOLCOMEXP;
	}


	public String getAtualizancm(){
		return ATUALIZANCM;
	}


	public void setAtualizancm(String ATUALIZANCM){
		this.ATUALIZANCM = ATUALIZANCM;
	}


	public Integer getRefreshexp(){
		return REFRESHEXP;
	}


	public void setRefreshexp(Integer REFRESHEXP){
		this.REFRESHEXP = REFRESHEXP;
	}


	public String getExcprodimpnfe(){
		return EXCPRODIMPNFE;
	}


	public void setExcprodimpnfe(String EXCPRODIMPNFE){
		this.EXCPRODIMPNFE = EXCPRODIMPNFE;
	}


	public Integer getIdpedidocfgcot(){
		return IDPEDIDOCFGCOT;
	}


	public void setIdpedidocfgcot(Integer IDPEDIDOCFGCOT){
		this.IDPEDIDOCFGCOT = IDPEDIDOCFGCOT;
	}


	public String getValsdoctapess(){
		return VALSDOCTAPESS;
	}


	public void setValsdoctapess(String VALSDOCTAPESS){
		this.VALSDOCTAPESS = VALSDOCTAPESS;
	}


	public String getVallimctapess(){
		return VALLIMCTAPESS;
	}


	public void setVallimctapess(String VALLIMCTAPESS){
		this.VALLIMCTAPESS = VALLIMCTAPESS;
	}


	public String getVallimcheqdev(){
		return VALLIMCHEQDEV;
	}


	public void setVallimcheqdev(String VALLIMCHEQDEV){
		this.VALLIMCHEQDEV = VALLIMCHEQDEV;
	}


	public String getValsdocheqdev(){
		return VALSDOCHEQDEV;
	}


	public void setValsdocheqdev(String VALSDOCHEQDEV){
		this.VALSDOCHEQDEV = VALSDOCHEQDEV;
	}


	public String getControleroma(){
		return CONTROLEROMA;
	}


	public void setControleroma(String CONTROLEROMA){
		this.CONTROLEROMA = CONTROLEROMA;
	}


	public String getConsitensnaocont(){
		return CONSITENSNAOCONT;
	}


	public void setConsitensnaocont(String CONSITENSNAOCONT){
		this.CONSITENSNAOCONT = CONSITENSNAOCONT;
	}


	public String getExpclipadrao(){
		return EXPCLIPADRAO;
	}


	public void setExpclipadrao(String EXPCLIPADRAO){
		this.EXPCLIPADRAO = EXPCLIPADRAO;
	}


	public String getEstnegativo(){
		return ESTNEGATIVO;
	}


	public void setEstnegativo(String ESTNEGATIVO){
		this.ESTNEGATIVO = ESTNEGATIVO;
	}


	public String getCtlmontagem(){
		return CTLMONTAGEM;
	}


	public void setCtlmontagem(String CTLMONTAGEM){
		this.CTLMONTAGEM = CTLMONTAGEM;
	}


	public String getReordenatrib(){
		return REORDENATRIB;
	}


	public void setReordenatrib(String REORDENATRIB){
		this.REORDENATRIB = REORDENATRIB;
	}


	public String getConsideraqtdexp(){
		return CONSIDERAQTDEXP;
	}


	public void setConsideraqtdexp(String CONSIDERAQTDEXP){
		this.CONSIDERAQTDEXP = CONSIDERAQTDEXP;
	}


	public String getXmlsempedido(){
		return XMLSEMPEDIDO;
	}


	public void setXmlsempedido(String XMLSEMPEDIDO){
		this.XMLSEMPEDIDO = XMLSEMPEDIDO;
	}


	public String getExpvendapendente(){
		return EXPVENDAPENDENTE;
	}


	public void setExpvendapendente(String EXPVENDAPENDENTE){
		this.EXPVENDAPENDENTE = EXPVENDAPENDENTE;
	}


	public Integer getIdpedidocfgbon(){
		return IDPEDIDOCFGBON;
	}


	public void setIdpedidocfgbon(Integer IDPEDIDOCFGBON){
		this.IDPEDIDOCFGBON = IDPEDIDOCFGBON;
	}


	public Integer getExpmaxdiasprevent(){
		return EXPMAXDIASPREVENT;
	}


	public void setExpmaxdiasprevent(Integer EXPMAXDIASPREVENT){
		this.EXPMAXDIASPREVENT = EXPMAXDIASPREVENT;
	}


	public String getVallocret(){
		return VALLOCRET;
	}


	public void setVallocret(String VALLOCRET){
		this.VALLOCRET = VALLOCRET;
	}


	public String getAlocakitparcial(){
		return ALOCAKITPARCIAL;
	}


	public void setAlocakitparcial(String ALOCAKITPARCIAL){
		this.ALOCAKITPARCIAL = ALOCAKITPARCIAL;
	}


	public Integer getLocalconsumo(){
		return LOCALCONSUMO;
	}


	public void setLocalconsumo(Integer LOCALCONSUMO){
		this.LOCALCONSUMO = LOCALCONSUMO;
	}


	public Integer getIdnotacfgacouent(){
		return IDNOTACFGACOUENT;
	}


	public void setIdnotacfgacouent(Integer IDNOTACFGACOUENT){
		this.IDNOTACFGACOUENT = IDNOTACFGACOUENT;
	}


	public Integer getIdnotacfgacousai(){
		return IDNOTACFGACOUSAI;
	}


	public void setIdnotacfgacousai(Integer IDNOTACFGACOUSAI){
		this.IDNOTACFGACOUSAI = IDNOTACFGACOUSAI;
	}


	public Integer getIdnotacfgpadent(){
		return IDNOTACFGPADENT;
	}


	public void setIdnotacfgpadent(Integer IDNOTACFGPADENT){
		this.IDNOTACFGPADENT = IDNOTACFGPADENT;
	}

}