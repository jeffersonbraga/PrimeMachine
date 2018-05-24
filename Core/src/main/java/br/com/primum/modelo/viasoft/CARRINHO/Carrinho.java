package br.com.primum.modelo.viasoft.CARRINHO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARRINHO")
public class Carrinho  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCARRINHO;

	@Column
	private Integer ESTAB;

	@Column
	private String NROCARTAO;

	@Column
	private String SITUACAO;

	@Column
	private String MOTCANC;

	@Column
	private Integer IDPESS;

	@Column
	private String NOME;

	@Column
	private String TELEFONE;

	@Column
	private Integer TAXAFRETE;

	@Column
	private String OBS;

	@Column
	private Integer IDDOCTO;

	@Column
	private String BLOQUEADO;

	@Column
	private Integer NRODAV;

	@Column
	private Integer COODAV;

	@Column
	private Integer COOCUPOM;

	@Column
	private Integer PDVDAV;

	@Column
	private Integer PDVCUPOM;

	@Column
	private Integer DESPESAS;

	@Column
	private Integer IDEND;

	@Column
	private String INVALIDO;

	@Column
	private String IAT;

	@Column
	private Integer IDMOTIVOCANC;

	@Column
	private String ENVIADO;

	@Column
	private Integer IDNOTACONF;

	@Column
	private Integer IDPESSCOMISSIONADO;

	@Column
	private Integer DESCCAIXA;

	@Column
	private Date DTVALIDADE;

	@Column
	private String MOTRETORNO;

	@Column
	private String MD5DAV;

	@Column
	private Integer JUROS;

	@Column
	private Integer DESPCAIXA;

	@Column
	private String OBSEXPEDICAO;

	@Column
	private Integer IDPESSAVALISTA;

	@Column
	private Date DTINIOBRA;

	@Column
	private String IMEI;

	@Column
	private String PEDCOMPRACLIENTE;

	@Column
	private Integer IDMOTRETORNO;

	@Column
	private String EMAIL;

	@Column
	private Integer FORMACOMPRA;

	@Column
	private Integer IDTRANSPORTADORA;

	@Column
	private String IDFRETETIPO;

	@Column
	private Integer QTDVOLUME;

	@Column
	private Integer PESOLIQUIDO;

	@Column
	private Integer PESOBRUTO;


	public Integer getIdcarrinho(){
		return IDCARRINHO;
	}


	public void setIdcarrinho(Integer IDCARRINHO){
		this.IDCARRINHO = IDCARRINHO;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getNrocartao(){
		return NROCARTAO;
	}


	public void setNrocartao(String NROCARTAO){
		this.NROCARTAO = NROCARTAO;
	}


	public String getSituacao(){
		return SITUACAO;
	}


	public void setSituacao(String SITUACAO){
		this.SITUACAO = SITUACAO;
	}


	public String getMotcanc(){
		return MOTCANC;
	}


	public void setMotcanc(String MOTCANC){
		this.MOTCANC = MOTCANC;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public String getNome(){
		return NOME;
	}


	public void setNome(String NOME){
		this.NOME = NOME;
	}


	public String getTelefone(){
		return TELEFONE;
	}


	public void setTelefone(String TELEFONE){
		this.TELEFONE = TELEFONE;
	}


	public Integer getTaxafrete(){
		return TAXAFRETE;
	}


	public void setTaxafrete(Integer TAXAFRETE){
		this.TAXAFRETE = TAXAFRETE;
	}


	public String getObs(){
		return OBS;
	}


	public void setObs(String OBS){
		this.OBS = OBS;
	}


	public Integer getIddocto(){
		return IDDOCTO;
	}


	public void setIddocto(Integer IDDOCTO){
		this.IDDOCTO = IDDOCTO;
	}


	public String getBloqueado(){
		return BLOQUEADO;
	}


	public void setBloqueado(String BLOQUEADO){
		this.BLOQUEADO = BLOQUEADO;
	}


	public Integer getNrodav(){
		return NRODAV;
	}


	public void setNrodav(Integer NRODAV){
		this.NRODAV = NRODAV;
	}


	public Integer getCoodav(){
		return COODAV;
	}


	public void setCoodav(Integer COODAV){
		this.COODAV = COODAV;
	}


	public Integer getCoocupom(){
		return COOCUPOM;
	}


	public void setCoocupom(Integer COOCUPOM){
		this.COOCUPOM = COOCUPOM;
	}


	public Integer getPdvdav(){
		return PDVDAV;
	}


	public void setPdvdav(Integer PDVDAV){
		this.PDVDAV = PDVDAV;
	}


	public Integer getPdvcupom(){
		return PDVCUPOM;
	}


	public void setPdvcupom(Integer PDVCUPOM){
		this.PDVCUPOM = PDVCUPOM;
	}


	public Integer getDespesas(){
		return DESPESAS;
	}


	public void setDespesas(Integer DESPESAS){
		this.DESPESAS = DESPESAS;
	}


	public Integer getIdend(){
		return IDEND;
	}


	public void setIdend(Integer IDEND){
		this.IDEND = IDEND;
	}


	public String getInvalido(){
		return INVALIDO;
	}


	public void setInvalido(String INVALIDO){
		this.INVALIDO = INVALIDO;
	}


	public String getIat(){
		return IAT;
	}


	public void setIat(String IAT){
		this.IAT = IAT;
	}


	public Integer getIdmotivocanc(){
		return IDMOTIVOCANC;
	}


	public void setIdmotivocanc(Integer IDMOTIVOCANC){
		this.IDMOTIVOCANC = IDMOTIVOCANC;
	}


	public String getEnviado(){
		return ENVIADO;
	}


	public void setEnviado(String ENVIADO){
		this.ENVIADO = ENVIADO;
	}


	public Integer getIdnotaconf(){
		return IDNOTACONF;
	}


	public void setIdnotaconf(Integer IDNOTACONF){
		this.IDNOTACONF = IDNOTACONF;
	}


	public Integer getIdpesscomissionado(){
		return IDPESSCOMISSIONADO;
	}


	public void setIdpesscomissionado(Integer IDPESSCOMISSIONADO){
		this.IDPESSCOMISSIONADO = IDPESSCOMISSIONADO;
	}


	public Integer getDesccaixa(){
		return DESCCAIXA;
	}


	public void setDesccaixa(Integer DESCCAIXA){
		this.DESCCAIXA = DESCCAIXA;
	}


	public Date getDtvalidade(){
		return DTVALIDADE;
	}


	public void setDtvalidade(Date DTVALIDADE){
		this.DTVALIDADE = DTVALIDADE;
	}


	public String getMotretorno(){
		return MOTRETORNO;
	}


	public void setMotretorno(String MOTRETORNO){
		this.MOTRETORNO = MOTRETORNO;
	}


	public String getMd5dav(){
		return MD5DAV;
	}


	public void setMd5dav(String MD5DAV){
		this.MD5DAV = MD5DAV;
	}


	public Integer getJuros(){
		return JUROS;
	}


	public void setJuros(Integer JUROS){
		this.JUROS = JUROS;
	}


	public Integer getDespcaixa(){
		return DESPCAIXA;
	}


	public void setDespcaixa(Integer DESPCAIXA){
		this.DESPCAIXA = DESPCAIXA;
	}


	public String getObsexpedicao(){
		return OBSEXPEDICAO;
	}


	public void setObsexpedicao(String OBSEXPEDICAO){
		this.OBSEXPEDICAO = OBSEXPEDICAO;
	}


	public Integer getIdpessavalista(){
		return IDPESSAVALISTA;
	}


	public void setIdpessavalista(Integer IDPESSAVALISTA){
		this.IDPESSAVALISTA = IDPESSAVALISTA;
	}


	public Date getDtiniobra(){
		return DTINIOBRA;
	}


	public void setDtiniobra(Date DTINIOBRA){
		this.DTINIOBRA = DTINIOBRA;
	}


	public String getImei(){
		return IMEI;
	}


	public void setImei(String IMEI){
		this.IMEI = IMEI;
	}


	public String getPedcompracliente(){
		return PEDCOMPRACLIENTE;
	}


	public void setPedcompracliente(String PEDCOMPRACLIENTE){
		this.PEDCOMPRACLIENTE = PEDCOMPRACLIENTE;
	}


	public Integer getIdmotretorno(){
		return IDMOTRETORNO;
	}


	public void setIdmotretorno(Integer IDMOTRETORNO){
		this.IDMOTRETORNO = IDMOTRETORNO;
	}


	public String getEmail(){
		return EMAIL;
	}


	public void setEmail(String EMAIL){
		this.EMAIL = EMAIL;
	}


	public Integer getFormacompra(){
		return FORMACOMPRA;
	}


	public void setFormacompra(Integer FORMACOMPRA){
		this.FORMACOMPRA = FORMACOMPRA;
	}


	public Integer getIdtransportadora(){
		return IDTRANSPORTADORA;
	}


	public void setIdtransportadora(Integer IDTRANSPORTADORA){
		this.IDTRANSPORTADORA = IDTRANSPORTADORA;
	}


	public String getIdfretetipo(){
		return IDFRETETIPO;
	}


	public void setIdfretetipo(String IDFRETETIPO){
		this.IDFRETETIPO = IDFRETETIPO;
	}


	public Integer getQtdvolume(){
		return QTDVOLUME;
	}


	public void setQtdvolume(Integer QTDVOLUME){
		this.QTDVOLUME = QTDVOLUME;
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

}