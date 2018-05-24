package br.com.primum.modelo.viasoft.CARRINHOITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARRINHOITEM")
public class Carrinhoitem  implements Serializable {

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
	private String IDITEM;

	@Column
	private Integer QUANTIDADE;

	@Column
	private Integer VALORUNIT;

	@Column
	private Integer IDVENDEDOR;

	@Column
	private String IDITEMKIT;

	@Column
	private Integer DESCITEM;

	@Column
	private Integer DESCONTO;

	@Column
	private String INVALIDO;

	@Column
	private String USERSOLIC;

	@Column
	private String USERLIB;

	@Column
	private String MOTIVO;

	@Column
	private Integer IDEMBALAGEM;

	@Column
	private String ENTREGAR;

	@Column
	private Integer QTDEENTREGA;

	@Column
	private String PROMOCAO;

	@Column
	private Integer IDLOCALRETIRADA;

	@Column
	private Integer ESTABBX;

	@Column
	private String COMPL;

	@Column
	private Integer DESCDESONER;

	@Column
	private Integer CUSTOAQUIS;

	@Column
	private String MD5DAV;

	@Column
	private String APLICACAO;

	@Column
	private Integer DESCDESONERST;

	@Column
	private Integer IDVENDEDORCART;

	@Column
	private Integer VALORC;

	@Column
	private String SERVICO;

	@Column
	private String ADDAGREGADO;

	@Column
	private String PEDCOMPRACLIENTE;

	@Column
	private Integer SEQITEMPEDCOMPRACLI;

	@Column
	private Integer DESCMAX;

	@Column
	private Integer VALORMINIMO;

	@Column
	private Integer GORDURA;

	@Column
	private Integer SEQITEMPRINC;

	@Column
	private Integer PRECONORMALORI;

	@Column
	private Integer PRECOPROMOORI;

	@Column
	private Integer PRECOTABELAORI;

	@Column
	private Integer MAXPERCDESCINI;

	@Column
	private Integer VALORMINIMOINI;

	@Column
	private Integer QTDEVOL;

	@Column
	private Integer TOTQTDEVOL;

	@Column
	private String ICMSCST;

	@Column
	private Integer ICMSBASE;

	@Column
	private Integer ICMSALIQ;

	@Column
	private Integer ICMSVAL;

	@Column
	private String ICMSINC;

	@Column
	private Integer ICMSPBASE;

	@Column
	private Integer ICMSPDIFBC;

	@Column
	private String ICMSDEDUZ;

	@Column
	private Integer ICMSMOTDESONER;

	@Column
	private Integer ICMSDESONER;

	@Column
	private Integer STBASE;

	@Column
	private Integer STALIQ;

	@Column
	private Integer STVAL;

	@Column
	private Integer STPMVA;

	@Column
	private String STMODBC;

	@Column
	private Integer STPREDBC;

	@Column
	private String STMVAAJUST;

	@Column
	private Integer IPIBASE;

	@Column
	private Integer IPIALIQ;

	@Column
	private Integer IPIVAL;

	@Column
	private Integer PISBASE;

	@Column
	private Integer PISALIQ;

	@Column
	private Integer PISVAL;

	@Column
	private Integer COFINSBASE;

	@Column
	private Integer COFINSALIQ;

	@Column
	private Integer COFINSVAL;

	@Column
	private String PISSOMAFRET;

	@Column
	private String PISSOMAIPI;

	@Column
	private String PISSOMAST;

	@Column
	private String COFINSSOMAFRET;

	@Column
	private String COFINSSOMAIPI;

	@Column
	private String COFINSSOMAST;

	@Column
	private String ICMSSOMARIPI;

	@Column
	private Integer PESOLIQUIDO;

	@Column
	private Integer PESOBRUTO;

	@Column
	private Integer TOTPESOLIQUIDO;

	@Column
	private Integer TOTPESOBRUTO;

	@Column
	private String KITALT;

	@Column
	private Integer DESPITEM;

	@Column
	private Integer DESCCAIXA;


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


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getQuantidade(){
		return QUANTIDADE;
	}


	public void setQuantidade(Integer QUANTIDADE){
		this.QUANTIDADE = QUANTIDADE;
	}


	public Integer getValorunit(){
		return VALORUNIT;
	}


	public void setValorunit(Integer VALORUNIT){
		this.VALORUNIT = VALORUNIT;
	}


	public Integer getIdvendedor(){
		return IDVENDEDOR;
	}


	public void setIdvendedor(Integer IDVENDEDOR){
		this.IDVENDEDOR = IDVENDEDOR;
	}


	public String getIditemkit(){
		return IDITEMKIT;
	}


	public void setIditemkit(String IDITEMKIT){
		this.IDITEMKIT = IDITEMKIT;
	}


	public Integer getDescitem(){
		return DESCITEM;
	}


	public void setDescitem(Integer DESCITEM){
		this.DESCITEM = DESCITEM;
	}


	public Integer getDesconto(){
		return DESCONTO;
	}


	public void setDesconto(Integer DESCONTO){
		this.DESCONTO = DESCONTO;
	}


	public String getInvalido(){
		return INVALIDO;
	}


	public void setInvalido(String INVALIDO){
		this.INVALIDO = INVALIDO;
	}


	public String getUsersolic(){
		return USERSOLIC;
	}


	public void setUsersolic(String USERSOLIC){
		this.USERSOLIC = USERSOLIC;
	}


	public String getUserlib(){
		return USERLIB;
	}


	public void setUserlib(String USERLIB){
		this.USERLIB = USERLIB;
	}


	public String getMotivo(){
		return MOTIVO;
	}


	public void setMotivo(String MOTIVO){
		this.MOTIVO = MOTIVO;
	}


	public Integer getIdembalagem(){
		return IDEMBALAGEM;
	}


	public void setIdembalagem(Integer IDEMBALAGEM){
		this.IDEMBALAGEM = IDEMBALAGEM;
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


	public String getPromocao(){
		return PROMOCAO;
	}


	public void setPromocao(String PROMOCAO){
		this.PROMOCAO = PROMOCAO;
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


	public String getCompl(){
		return COMPL;
	}


	public void setCompl(String COMPL){
		this.COMPL = COMPL;
	}


	public Integer getDescdesoner(){
		return DESCDESONER;
	}


	public void setDescdesoner(Integer DESCDESONER){
		this.DESCDESONER = DESCDESONER;
	}


	public Integer getCustoaquis(){
		return CUSTOAQUIS;
	}


	public void setCustoaquis(Integer CUSTOAQUIS){
		this.CUSTOAQUIS = CUSTOAQUIS;
	}


	public String getMd5dav(){
		return MD5DAV;
	}


	public void setMd5dav(String MD5DAV){
		this.MD5DAV = MD5DAV;
	}


	public String getAplicacao(){
		return APLICACAO;
	}


	public void setAplicacao(String APLICACAO){
		this.APLICACAO = APLICACAO;
	}


	public Integer getDescdesonerst(){
		return DESCDESONERST;
	}


	public void setDescdesonerst(Integer DESCDESONERST){
		this.DESCDESONERST = DESCDESONERST;
	}


	public Integer getIdvendedorcart(){
		return IDVENDEDORCART;
	}


	public void setIdvendedorcart(Integer IDVENDEDORCART){
		this.IDVENDEDORCART = IDVENDEDORCART;
	}


	public Integer getValorc(){
		return VALORC;
	}


	public void setValorc(Integer VALORC){
		this.VALORC = VALORC;
	}


	public String getServico(){
		return SERVICO;
	}


	public void setServico(String SERVICO){
		this.SERVICO = SERVICO;
	}


	public String getAddagregado(){
		return ADDAGREGADO;
	}


	public void setAddagregado(String ADDAGREGADO){
		this.ADDAGREGADO = ADDAGREGADO;
	}


	public String getPedcompracliente(){
		return PEDCOMPRACLIENTE;
	}


	public void setPedcompracliente(String PEDCOMPRACLIENTE){
		this.PEDCOMPRACLIENTE = PEDCOMPRACLIENTE;
	}


	public Integer getSeqitempedcompracli(){
		return SEQITEMPEDCOMPRACLI;
	}


	public void setSeqitempedcompracli(Integer SEQITEMPEDCOMPRACLI){
		this.SEQITEMPEDCOMPRACLI = SEQITEMPEDCOMPRACLI;
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


	public Integer getPreconormalori(){
		return PRECONORMALORI;
	}


	public void setPreconormalori(Integer PRECONORMALORI){
		this.PRECONORMALORI = PRECONORMALORI;
	}


	public Integer getPrecopromoori(){
		return PRECOPROMOORI;
	}


	public void setPrecopromoori(Integer PRECOPROMOORI){
		this.PRECOPROMOORI = PRECOPROMOORI;
	}


	public Integer getPrecotabelaori(){
		return PRECOTABELAORI;
	}


	public void setPrecotabelaori(Integer PRECOTABELAORI){
		this.PRECOTABELAORI = PRECOTABELAORI;
	}


	public Integer getMaxpercdescini(){
		return MAXPERCDESCINI;
	}


	public void setMaxpercdescini(Integer MAXPERCDESCINI){
		this.MAXPERCDESCINI = MAXPERCDESCINI;
	}


	public Integer getValorminimoini(){
		return VALORMINIMOINI;
	}


	public void setValorminimoini(Integer VALORMINIMOINI){
		this.VALORMINIMOINI = VALORMINIMOINI;
	}


	public Integer getQtdevol(){
		return QTDEVOL;
	}


	public void setQtdevol(Integer QTDEVOL){
		this.QTDEVOL = QTDEVOL;
	}


	public Integer getTotqtdevol(){
		return TOTQTDEVOL;
	}


	public void setTotqtdevol(Integer TOTQTDEVOL){
		this.TOTQTDEVOL = TOTQTDEVOL;
	}


	public String getIcmscst(){
		return ICMSCST;
	}


	public void setIcmscst(String ICMSCST){
		this.ICMSCST = ICMSCST;
	}


	public Integer getIcmsbase(){
		return ICMSBASE;
	}


	public void setIcmsbase(Integer ICMSBASE){
		this.ICMSBASE = ICMSBASE;
	}


	public Integer getIcmsaliq(){
		return ICMSALIQ;
	}


	public void setIcmsaliq(Integer ICMSALIQ){
		this.ICMSALIQ = ICMSALIQ;
	}


	public Integer getIcmsval(){
		return ICMSVAL;
	}


	public void setIcmsval(Integer ICMSVAL){
		this.ICMSVAL = ICMSVAL;
	}


	public String getIcmsinc(){
		return ICMSINC;
	}


	public void setIcmsinc(String ICMSINC){
		this.ICMSINC = ICMSINC;
	}


	public Integer getIcmspbase(){
		return ICMSPBASE;
	}


	public void setIcmspbase(Integer ICMSPBASE){
		this.ICMSPBASE = ICMSPBASE;
	}


	public Integer getIcmspdifbc(){
		return ICMSPDIFBC;
	}


	public void setIcmspdifbc(Integer ICMSPDIFBC){
		this.ICMSPDIFBC = ICMSPDIFBC;
	}


	public String getIcmsdeduz(){
		return ICMSDEDUZ;
	}


	public void setIcmsdeduz(String ICMSDEDUZ){
		this.ICMSDEDUZ = ICMSDEDUZ;
	}


	public Integer getIcmsmotdesoner(){
		return ICMSMOTDESONER;
	}


	public void setIcmsmotdesoner(Integer ICMSMOTDESONER){
		this.ICMSMOTDESONER = ICMSMOTDESONER;
	}


	public Integer getIcmsdesoner(){
		return ICMSDESONER;
	}


	public void setIcmsdesoner(Integer ICMSDESONER){
		this.ICMSDESONER = ICMSDESONER;
	}


	public Integer getStbase(){
		return STBASE;
	}


	public void setStbase(Integer STBASE){
		this.STBASE = STBASE;
	}


	public Integer getStaliq(){
		return STALIQ;
	}


	public void setStaliq(Integer STALIQ){
		this.STALIQ = STALIQ;
	}


	public Integer getStval(){
		return STVAL;
	}


	public void setStval(Integer STVAL){
		this.STVAL = STVAL;
	}


	public Integer getStpmva(){
		return STPMVA;
	}


	public void setStpmva(Integer STPMVA){
		this.STPMVA = STPMVA;
	}


	public String getStmodbc(){
		return STMODBC;
	}


	public void setStmodbc(String STMODBC){
		this.STMODBC = STMODBC;
	}


	public Integer getStpredbc(){
		return STPREDBC;
	}


	public void setStpredbc(Integer STPREDBC){
		this.STPREDBC = STPREDBC;
	}


	public String getStmvaajust(){
		return STMVAAJUST;
	}


	public void setStmvaajust(String STMVAAJUST){
		this.STMVAAJUST = STMVAAJUST;
	}


	public Integer getIpibase(){
		return IPIBASE;
	}


	public void setIpibase(Integer IPIBASE){
		this.IPIBASE = IPIBASE;
	}


	public Integer getIpialiq(){
		return IPIALIQ;
	}


	public void setIpialiq(Integer IPIALIQ){
		this.IPIALIQ = IPIALIQ;
	}


	public Integer getIpival(){
		return IPIVAL;
	}


	public void setIpival(Integer IPIVAL){
		this.IPIVAL = IPIVAL;
	}


	public Integer getPisbase(){
		return PISBASE;
	}


	public void setPisbase(Integer PISBASE){
		this.PISBASE = PISBASE;
	}


	public Integer getPisaliq(){
		return PISALIQ;
	}


	public void setPisaliq(Integer PISALIQ){
		this.PISALIQ = PISALIQ;
	}


	public Integer getPisval(){
		return PISVAL;
	}


	public void setPisval(Integer PISVAL){
		this.PISVAL = PISVAL;
	}


	public Integer getCofinsbase(){
		return COFINSBASE;
	}


	public void setCofinsbase(Integer COFINSBASE){
		this.COFINSBASE = COFINSBASE;
	}


	public Integer getCofinsaliq(){
		return COFINSALIQ;
	}


	public void setCofinsaliq(Integer COFINSALIQ){
		this.COFINSALIQ = COFINSALIQ;
	}


	public Integer getCofinsval(){
		return COFINSVAL;
	}


	public void setCofinsval(Integer COFINSVAL){
		this.COFINSVAL = COFINSVAL;
	}


	public String getPissomafret(){
		return PISSOMAFRET;
	}


	public void setPissomafret(String PISSOMAFRET){
		this.PISSOMAFRET = PISSOMAFRET;
	}


	public String getPissomaipi(){
		return PISSOMAIPI;
	}


	public void setPissomaipi(String PISSOMAIPI){
		this.PISSOMAIPI = PISSOMAIPI;
	}


	public String getPissomast(){
		return PISSOMAST;
	}


	public void setPissomast(String PISSOMAST){
		this.PISSOMAST = PISSOMAST;
	}


	public String getCofinssomafret(){
		return COFINSSOMAFRET;
	}


	public void setCofinssomafret(String COFINSSOMAFRET){
		this.COFINSSOMAFRET = COFINSSOMAFRET;
	}


	public String getCofinssomaipi(){
		return COFINSSOMAIPI;
	}


	public void setCofinssomaipi(String COFINSSOMAIPI){
		this.COFINSSOMAIPI = COFINSSOMAIPI;
	}


	public String getCofinssomast(){
		return COFINSSOMAST;
	}


	public void setCofinssomast(String COFINSSOMAST){
		this.COFINSSOMAST = COFINSSOMAST;
	}


	public String getIcmssomaripi(){
		return ICMSSOMARIPI;
	}


	public void setIcmssomaripi(String ICMSSOMARIPI){
		this.ICMSSOMARIPI = ICMSSOMARIPI;
	}


	public Integer getPesoliquido(){
		return PESOLIQUIDO;
	}


	public void setPesoliquido(Integer PESOLIQUIDO){
		this.PESOLIQUIDO = PESOLIQUIDO;
	}


	public Integer getPesobruto(){
		return PESOBRUTO;
	}


	public void setPesobruto(Integer PESOBRUTO){
		this.PESOBRUTO = PESOBRUTO;
	}


	public Integer getTotpesoliquido(){
		return TOTPESOLIQUIDO;
	}


	public void setTotpesoliquido(Integer TOTPESOLIQUIDO){
		this.TOTPESOLIQUIDO = TOTPESOLIQUIDO;
	}


	public Integer getTotpesobruto(){
		return TOTPESOBRUTO;
	}


	public void setTotpesobruto(Integer TOTPESOBRUTO){
		this.TOTPESOBRUTO = TOTPESOBRUTO;
	}


	public String getKitalt(){
		return KITALT;
	}


	public void setKitalt(String KITALT){
		this.KITALT = KITALT;
	}


	public Integer getDespitem(){
		return DESPITEM;
	}


	public void setDespitem(Integer DESPITEM){
		this.DESPITEM = DESPITEM;
	}


	public Integer getDesccaixa(){
		return DESCCAIXA;
	}


	public void setDesccaixa(Integer DESCCAIXA){
		this.DESCCAIXA = DESCCAIXA;
	}

}