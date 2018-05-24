package br.com.primum.modelo.viasoft.FILIALCONFVDA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FILIALCONFVDA")
public class Filialconfvda  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Column
	private String RATDESCPMAX;

	@Column
	private String FINCARCOMSERV;

	@Column
	private Integer INATIVIDADE;

	@Column
	private String MANTPERCDESCITEM;

	@Column
	private String DADOSCONFCLI;

	@Column
	private String GORDURAPOSITIVA;

	@Column
	private String COMISCARVISTA;

	@Column
	private String VALIDAPRAZOREP;

	@Column
	private String CARSALTODLOCEST;

	@Column
	private String LOCALRETITEMKIT;

	@Column
	private String PRNORMADIANTCONTP;

	@Column
	private String RECPERDESSOBVAL;

	@Column
	private String TPCUSTOLUCRATIV;

	@Column
	private String USASERVICOAGREG;

	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String ALTPRECO;

	@Column
	private String DIFEMDESC;

	@Column
	private Integer PERCPONTAEST;

	@Column
	private Integer MEDSTULTCOMP;

	@Column
	private Integer IDPESSPADRAO;

	@Column
	private Integer TAXAFRETE;

	@Column
	private String CONTROLECART;

	@Column
	private Integer VALIDADECART;

	@Column
	private Integer IDPORTTRANSIC;

	@Column
	private String OBRIGAACERTO;

	@Column
	private String BLOQCARTFIN;

	@Column
	private String VALCNPJFSAIDA;

	@Column
	private String MSGCPNFE;

	@Column
	private String OBRIGACARTAO;

	@Column
	private String ALTCONFCX;

	@Column
	private String OBRIGACLIENTE;

	@Column
	private String DUPLIREGCART;

	@Column
	private String USACOMISSIONA;

	@Column
	private String OBRIGAFONE;

	@Column
	private String VENDFINALIZA;

	@Column
	private Integer DECVALOR;

	@Column
	private Integer VALMAXPRODVENDA;

	@Column
	private String SELEXPFINCAR;

	@Column
	private String DEVCONSFINAL;

	@Column
	private String RECALCPRECO;

	@Column
	private String OBRIGACONTATO;

	@Column
	private String ALTPRENAOUSAPRO;

	@Column
	private String GERANFCLIPADRAO;

	@Column
	private String SDOPRODLOCALRET;

	@Column
	private String OBRIGAOPERCART;

	@Column
	private String ENDPESS;

	@Column
	private String BLOQCARIMPORC;

	@Column
	private Integer IDTABPRVDA;

	@Column
	private String FORMAVISTA;

	@Column
	private String FRETEPRPARCELA;

	@Column
	private Integer TIMERECALEST;

	@Column
	private String UTILIZARMULTVDA;

	@Column
	private String SELQTDEEXP;

	@Column
	private String EXIBEINATIVO;

	@Column
	private String GERAVDAFUTCLIPADRAO;

	@Column
	private Integer IDESTOQUETIPODISP;

	@Column
	private String EXIBETABELAPR;

	@Column
	private String EXIBECONCEITO;

	@Column
	private String VENDPRODENTFUT;

	@Column
	private String VISUALIZAMDCARTAO;

	@Column
	private String PRECOORC;

	@Column
	private String CONSIDERALIMCTP;

	@Column
	private String SALDTOTPONTEST;

	@Column
	private Integer IDRETIRADAASSIST;

	@Column
	private Integer IDCARTPADRAO;

	@Column
	private String IDITEMRECCEL;

	@Column
	private String EXIBEINFOADIC;

	@Column
	private String RECALCDESCEXCITEM;

	@Column
	private String CANCELACARRINHO;

	@Column
	private Integer MOTCANCPADRAOCARR;

	@Column
	private String CORINATIVO;


	public String getRatdescpmax(){
		return RATDESCPMAX;
	}


	public void setRatdescpmax(String RATDESCPMAX){
		this.RATDESCPMAX = RATDESCPMAX;
	}


	public String getFincarcomserv(){
		return FINCARCOMSERV;
	}


	public void setFincarcomserv(String FINCARCOMSERV){
		this.FINCARCOMSERV = FINCARCOMSERV;
	}


	public Integer getInatividade(){
		return INATIVIDADE;
	}


	public void setInatividade(Integer INATIVIDADE){
		this.INATIVIDADE = INATIVIDADE;
	}


	public String getMantpercdescitem(){
		return MANTPERCDESCITEM;
	}


	public void setMantpercdescitem(String MANTPERCDESCITEM){
		this.MANTPERCDESCITEM = MANTPERCDESCITEM;
	}


	public String getDadosconfcli(){
		return DADOSCONFCLI;
	}


	public void setDadosconfcli(String DADOSCONFCLI){
		this.DADOSCONFCLI = DADOSCONFCLI;
	}


	public String getGordurapositiva(){
		return GORDURAPOSITIVA;
	}


	public void setGordurapositiva(String GORDURAPOSITIVA){
		this.GORDURAPOSITIVA = GORDURAPOSITIVA;
	}


	public String getComiscarvista(){
		return COMISCARVISTA;
	}


	public void setComiscarvista(String COMISCARVISTA){
		this.COMISCARVISTA = COMISCARVISTA;
	}


	public String getValidaprazorep(){
		return VALIDAPRAZOREP;
	}


	public void setValidaprazorep(String VALIDAPRAZOREP){
		this.VALIDAPRAZOREP = VALIDAPRAZOREP;
	}


	public String getCarsaltodlocest(){
		return CARSALTODLOCEST;
	}


	public void setCarsaltodlocest(String CARSALTODLOCEST){
		this.CARSALTODLOCEST = CARSALTODLOCEST;
	}


	public String getLocalretitemkit(){
		return LOCALRETITEMKIT;
	}


	public void setLocalretitemkit(String LOCALRETITEMKIT){
		this.LOCALRETITEMKIT = LOCALRETITEMKIT;
	}


	public String getPrnormadiantcontp(){
		return PRNORMADIANTCONTP;
	}


	public void setPrnormadiantcontp(String PRNORMADIANTCONTP){
		this.PRNORMADIANTCONTP = PRNORMADIANTCONTP;
	}


	public String getRecperdessobval(){
		return RECPERDESSOBVAL;
	}


	public void setRecperdessobval(String RECPERDESSOBVAL){
		this.RECPERDESSOBVAL = RECPERDESSOBVAL;
	}


	public String getTpcustolucrativ(){
		return TPCUSTOLUCRATIV;
	}


	public void setTpcustolucrativ(String TPCUSTOLUCRATIV){
		this.TPCUSTOLUCRATIV = TPCUSTOLUCRATIV;
	}


	public String getUsaservicoagreg(){
		return USASERVICOAGREG;
	}


	public void setUsaservicoagreg(String USASERVICOAGREG){
		this.USASERVICOAGREG = USASERVICOAGREG;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getAltpreco(){
		return ALTPRECO;
	}


	public void setAltpreco(String ALTPRECO){
		this.ALTPRECO = ALTPRECO;
	}


	public String getDifemdesc(){
		return DIFEMDESC;
	}


	public void setDifemdesc(String DIFEMDESC){
		this.DIFEMDESC = DIFEMDESC;
	}


	public Integer getPercpontaest(){
		return PERCPONTAEST;
	}


	public void setPercpontaest(Integer PERCPONTAEST){
		this.PERCPONTAEST = PERCPONTAEST;
	}


	public Integer getMedstultcomp(){
		return MEDSTULTCOMP;
	}


	public void setMedstultcomp(Integer MEDSTULTCOMP){
		this.MEDSTULTCOMP = MEDSTULTCOMP;
	}


	public Integer getIdpesspadrao(){
		return IDPESSPADRAO;
	}


	public void setIdpesspadrao(Integer IDPESSPADRAO){
		this.IDPESSPADRAO = IDPESSPADRAO;
	}


	public Integer getTaxafrete(){
		return TAXAFRETE;
	}


	public void setTaxafrete(Integer TAXAFRETE){
		this.TAXAFRETE = TAXAFRETE;
	}


	public String getControlecart(){
		return CONTROLECART;
	}


	public void setControlecart(String CONTROLECART){
		this.CONTROLECART = CONTROLECART;
	}


	public Integer getValidadecart(){
		return VALIDADECART;
	}


	public void setValidadecart(Integer VALIDADECART){
		this.VALIDADECART = VALIDADECART;
	}


	public Integer getIdporttransic(){
		return IDPORTTRANSIC;
	}


	public void setIdporttransic(Integer IDPORTTRANSIC){
		this.IDPORTTRANSIC = IDPORTTRANSIC;
	}


	public String getObrigaacerto(){
		return OBRIGAACERTO;
	}


	public void setObrigaacerto(String OBRIGAACERTO){
		this.OBRIGAACERTO = OBRIGAACERTO;
	}


	public String getBloqcartfin(){
		return BLOQCARTFIN;
	}


	public void setBloqcartfin(String BLOQCARTFIN){
		this.BLOQCARTFIN = BLOQCARTFIN;
	}


	public String getValcnpjfsaida(){
		return VALCNPJFSAIDA;
	}


	public void setValcnpjfsaida(String VALCNPJFSAIDA){
		this.VALCNPJFSAIDA = VALCNPJFSAIDA;
	}


	public String getMsgcpnfe(){
		return MSGCPNFE;
	}


	public void setMsgcpnfe(String MSGCPNFE){
		this.MSGCPNFE = MSGCPNFE;
	}


	public String getObrigacartao(){
		return OBRIGACARTAO;
	}


	public void setObrigacartao(String OBRIGACARTAO){
		this.OBRIGACARTAO = OBRIGACARTAO;
	}


	public String getAltconfcx(){
		return ALTCONFCX;
	}


	public void setAltconfcx(String ALTCONFCX){
		this.ALTCONFCX = ALTCONFCX;
	}


	public String getObrigacliente(){
		return OBRIGACLIENTE;
	}


	public void setObrigacliente(String OBRIGACLIENTE){
		this.OBRIGACLIENTE = OBRIGACLIENTE;
	}


	public String getDupliregcart(){
		return DUPLIREGCART;
	}


	public void setDupliregcart(String DUPLIREGCART){
		this.DUPLIREGCART = DUPLIREGCART;
	}


	public String getUsacomissiona(){
		return USACOMISSIONA;
	}


	public void setUsacomissiona(String USACOMISSIONA){
		this.USACOMISSIONA = USACOMISSIONA;
	}


	public String getObrigafone(){
		return OBRIGAFONE;
	}


	public void setObrigafone(String OBRIGAFONE){
		this.OBRIGAFONE = OBRIGAFONE;
	}


	public String getVendfinaliza(){
		return VENDFINALIZA;
	}


	public void setVendfinaliza(String VENDFINALIZA){
		this.VENDFINALIZA = VENDFINALIZA;
	}


	public Integer getDecvalor(){
		return DECVALOR;
	}


	public void setDecvalor(Integer DECVALOR){
		this.DECVALOR = DECVALOR;
	}


	public Integer getValmaxprodvenda(){
		return VALMAXPRODVENDA;
	}


	public void setValmaxprodvenda(Integer VALMAXPRODVENDA){
		this.VALMAXPRODVENDA = VALMAXPRODVENDA;
	}


	public String getSelexpfincar(){
		return SELEXPFINCAR;
	}


	public void setSelexpfincar(String SELEXPFINCAR){
		this.SELEXPFINCAR = SELEXPFINCAR;
	}


	public String getDevconsfinal(){
		return DEVCONSFINAL;
	}


	public void setDevconsfinal(String DEVCONSFINAL){
		this.DEVCONSFINAL = DEVCONSFINAL;
	}


	public String getRecalcpreco(){
		return RECALCPRECO;
	}


	public void setRecalcpreco(String RECALCPRECO){
		this.RECALCPRECO = RECALCPRECO;
	}


	public String getObrigacontato(){
		return OBRIGACONTATO;
	}


	public void setObrigacontato(String OBRIGACONTATO){
		this.OBRIGACONTATO = OBRIGACONTATO;
	}


	public String getAltprenaousapro(){
		return ALTPRENAOUSAPRO;
	}


	public void setAltprenaousapro(String ALTPRENAOUSAPRO){
		this.ALTPRENAOUSAPRO = ALTPRENAOUSAPRO;
	}


	public String getGeranfclipadrao(){
		return GERANFCLIPADRAO;
	}


	public void setGeranfclipadrao(String GERANFCLIPADRAO){
		this.GERANFCLIPADRAO = GERANFCLIPADRAO;
	}


	public String getSdoprodlocalret(){
		return SDOPRODLOCALRET;
	}


	public void setSdoprodlocalret(String SDOPRODLOCALRET){
		this.SDOPRODLOCALRET = SDOPRODLOCALRET;
	}


	public String getObrigaopercart(){
		return OBRIGAOPERCART;
	}


	public void setObrigaopercart(String OBRIGAOPERCART){
		this.OBRIGAOPERCART = OBRIGAOPERCART;
	}


	public String getEndpess(){
		return ENDPESS;
	}


	public void setEndpess(String ENDPESS){
		this.ENDPESS = ENDPESS;
	}


	public String getBloqcarimporc(){
		return BLOQCARIMPORC;
	}


	public void setBloqcarimporc(String BLOQCARIMPORC){
		this.BLOQCARIMPORC = BLOQCARIMPORC;
	}


	public Integer getIdtabprvda(){
		return IDTABPRVDA;
	}


	public void setIdtabprvda(Integer IDTABPRVDA){
		this.IDTABPRVDA = IDTABPRVDA;
	}


	public String getFormavista(){
		return FORMAVISTA;
	}


	public void setFormavista(String FORMAVISTA){
		this.FORMAVISTA = FORMAVISTA;
	}


	public String getFreteprparcela(){
		return FRETEPRPARCELA;
	}


	public void setFreteprparcela(String FRETEPRPARCELA){
		this.FRETEPRPARCELA = FRETEPRPARCELA;
	}


	public Integer getTimerecalest(){
		return TIMERECALEST;
	}


	public void setTimerecalest(Integer TIMERECALEST){
		this.TIMERECALEST = TIMERECALEST;
	}


	public String getUtilizarmultvda(){
		return UTILIZARMULTVDA;
	}


	public void setUtilizarmultvda(String UTILIZARMULTVDA){
		this.UTILIZARMULTVDA = UTILIZARMULTVDA;
	}


	public String getSelqtdeexp(){
		return SELQTDEEXP;
	}


	public void setSelqtdeexp(String SELQTDEEXP){
		this.SELQTDEEXP = SELQTDEEXP;
	}


	public String getExibeinativo(){
		return EXIBEINATIVO;
	}


	public void setExibeinativo(String EXIBEINATIVO){
		this.EXIBEINATIVO = EXIBEINATIVO;
	}


	public String getGeravdafutclipadrao(){
		return GERAVDAFUTCLIPADRAO;
	}


	public void setGeravdafutclipadrao(String GERAVDAFUTCLIPADRAO){
		this.GERAVDAFUTCLIPADRAO = GERAVDAFUTCLIPADRAO;
	}


	public Integer getIdestoquetipodisp(){
		return IDESTOQUETIPODISP;
	}


	public void setIdestoquetipodisp(Integer IDESTOQUETIPODISP){
		this.IDESTOQUETIPODISP = IDESTOQUETIPODISP;
	}


	public String getExibetabelapr(){
		return EXIBETABELAPR;
	}


	public void setExibetabelapr(String EXIBETABELAPR){
		this.EXIBETABELAPR = EXIBETABELAPR;
	}


	public String getExibeconceito(){
		return EXIBECONCEITO;
	}


	public void setExibeconceito(String EXIBECONCEITO){
		this.EXIBECONCEITO = EXIBECONCEITO;
	}


	public String getVendprodentfut(){
		return VENDPRODENTFUT;
	}


	public void setVendprodentfut(String VENDPRODENTFUT){
		this.VENDPRODENTFUT = VENDPRODENTFUT;
	}


	public String getVisualizamdcartao(){
		return VISUALIZAMDCARTAO;
	}


	public void setVisualizamdcartao(String VISUALIZAMDCARTAO){
		this.VISUALIZAMDCARTAO = VISUALIZAMDCARTAO;
	}


	public String getPrecoorc(){
		return PRECOORC;
	}


	public void setPrecoorc(String PRECOORC){
		this.PRECOORC = PRECOORC;
	}


	public String getConsideralimctp(){
		return CONSIDERALIMCTP;
	}


	public void setConsideralimctp(String CONSIDERALIMCTP){
		this.CONSIDERALIMCTP = CONSIDERALIMCTP;
	}


	public String getSaldtotpontest(){
		return SALDTOTPONTEST;
	}


	public void setSaldtotpontest(String SALDTOTPONTEST){
		this.SALDTOTPONTEST = SALDTOTPONTEST;
	}


	public Integer getIdretiradaassist(){
		return IDRETIRADAASSIST;
	}


	public void setIdretiradaassist(Integer IDRETIRADAASSIST){
		this.IDRETIRADAASSIST = IDRETIRADAASSIST;
	}


	public Integer getIdcartpadrao(){
		return IDCARTPADRAO;
	}


	public void setIdcartpadrao(Integer IDCARTPADRAO){
		this.IDCARTPADRAO = IDCARTPADRAO;
	}


	public String getIditemreccel(){
		return IDITEMRECCEL;
	}


	public void setIditemreccel(String IDITEMRECCEL){
		this.IDITEMRECCEL = IDITEMRECCEL;
	}


	public String getExibeinfoadic(){
		return EXIBEINFOADIC;
	}


	public void setExibeinfoadic(String EXIBEINFOADIC){
		this.EXIBEINFOADIC = EXIBEINFOADIC;
	}


	public String getRecalcdescexcitem(){
		return RECALCDESCEXCITEM;
	}


	public void setRecalcdescexcitem(String RECALCDESCEXCITEM){
		this.RECALCDESCEXCITEM = RECALCDESCEXCITEM;
	}


	public String getCancelacarrinho(){
		return CANCELACARRINHO;
	}


	public void setCancelacarrinho(String CANCELACARRINHO){
		this.CANCELACARRINHO = CANCELACARRINHO;
	}


	public Integer getMotcancpadraocarr(){
		return MOTCANCPADRAOCARR;
	}


	public void setMotcancpadraocarr(Integer MOTCANCPADRAOCARR){
		this.MOTCANCPADRAOCARR = MOTCANCPADRAOCARR;
	}


	public String getCorinativo(){
		return CORINATIVO;
	}


	public void setCorinativo(String CORINATIVO){
		this.CORINATIVO = CORINATIVO;
	}

}