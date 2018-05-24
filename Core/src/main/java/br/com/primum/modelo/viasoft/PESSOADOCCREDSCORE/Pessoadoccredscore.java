package br.com.primum.modelo.viasoft.PESSOADOCCREDSCORE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOADOCCREDSCORE")
public class Pessoadoccredscore  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Column
	private Integer RENDA;

	@Column
	private Integer TIPORESIDENCIA;

	@Column
	private Integer ESTADOCIVIL;

	@Column
	private String NOMECONJUGE;

	@Column
	private String CARGO;

	@Column
	private String OBS;

	@Column
	private Integer RENDACONJUGE;

	@Column
	private Integer OUTROSRENDIMEN;

	@Column
	private Integer DESPESAS;

	@Column
	private Integer VLRALUGUEL;

	@Column
	private String OBSFIN;

	@Column
	private String CPFCONJ;

	@Column
	private String TEMPORES;

	@Column
	private Integer NRODEPENDENTE;

	@Column
	private Integer LIMITE;

	@Column
	private Date DTPROXREAVAL;

	@Column
	private Integer LIMITEMENSAL;

	@Column
	private Integer CARENCIASDODEV;

	@Column
	private Integer LIMITESDODEV;

	@Column
	private Date DTNASCCONJ;

	@Column
	private String TELEFONECONJUGE;

	@Column
	private String TRABALHOCONJUGE;

	@Column
	private String CARGOCONJUGE;

	@Column
	private Integer CAPITALSOCIAL;

	@Column
	private Integer PATRIMONIO;

	@Column
	private Date DTFUNDACAO;

	@Column
	private Date ULTALTREFPESSOAL;

	@Column
	private Date ULTALTREFCREDITO;

	@Column
	private Date ULTALTREFCOMERCIAL;

	@Column
	private Integer VLRMEDICAMENTO;

	@Column
	private Integer RENDATOTAL;

	@Id
	@Column
	private Integer EMPRESA;

	@Column
	private Integer IDPESS;

	@Column
	private Date DTNASC;

	@Column
	private String NOMEPAI;

	@Column
	private String NOMEMAE;

	@Column
	private String TRABALHO;

	@Column
	private Date DTADMISSAO;


	public Integer getRenda(){
		return RENDA;
	}


	public void setRenda(Integer RENDA){
		this.RENDA = RENDA;
	}


	public Integer getTiporesidencia(){
		return TIPORESIDENCIA;
	}


	public void setTiporesidencia(Integer TIPORESIDENCIA){
		this.TIPORESIDENCIA = TIPORESIDENCIA;
	}


	public Integer getEstadocivil(){
		return ESTADOCIVIL;
	}


	public void setEstadocivil(Integer ESTADOCIVIL){
		this.ESTADOCIVIL = ESTADOCIVIL;
	}


	public String getNomeconjuge(){
		return NOMECONJUGE;
	}


	public void setNomeconjuge(String NOMECONJUGE){
		this.NOMECONJUGE = NOMECONJUGE;
	}


	public String getCargo(){
		return CARGO;
	}


	public void setCargo(String CARGO){
		this.CARGO = CARGO;
	}


	public String getObs(){
		return OBS;
	}


	public void setObs(String OBS){
		this.OBS = OBS;
	}


	public Integer getRendaconjuge(){
		return RENDACONJUGE;
	}


	public void setRendaconjuge(Integer RENDACONJUGE){
		this.RENDACONJUGE = RENDACONJUGE;
	}


	public Integer getOutrosrendimen(){
		return OUTROSRENDIMEN;
	}


	public void setOutrosrendimen(Integer OUTROSRENDIMEN){
		this.OUTROSRENDIMEN = OUTROSRENDIMEN;
	}


	public Integer getDespesas(){
		return DESPESAS;
	}


	public void setDespesas(Integer DESPESAS){
		this.DESPESAS = DESPESAS;
	}


	public Integer getVlraluguel(){
		return VLRALUGUEL;
	}


	public void setVlraluguel(Integer VLRALUGUEL){
		this.VLRALUGUEL = VLRALUGUEL;
	}


	public String getObsfin(){
		return OBSFIN;
	}


	public void setObsfin(String OBSFIN){
		this.OBSFIN = OBSFIN;
	}


	public String getCpfconj(){
		return CPFCONJ;
	}


	public void setCpfconj(String CPFCONJ){
		this.CPFCONJ = CPFCONJ;
	}


	public String getTempores(){
		return TEMPORES;
	}


	public void setTempores(String TEMPORES){
		this.TEMPORES = TEMPORES;
	}


	public Integer getNrodependente(){
		return NRODEPENDENTE;
	}


	public void setNrodependente(Integer NRODEPENDENTE){
		this.NRODEPENDENTE = NRODEPENDENTE;
	}


	public Integer getLimite(){
		return LIMITE;
	}


	public void setLimite(Integer LIMITE){
		this.LIMITE = LIMITE;
	}


	public Date getDtproxreaval(){
		return DTPROXREAVAL;
	}


	public void setDtproxreaval(Date DTPROXREAVAL){
		this.DTPROXREAVAL = DTPROXREAVAL;
	}


	public Integer getLimitemensal(){
		return LIMITEMENSAL;
	}


	public void setLimitemensal(Integer LIMITEMENSAL){
		this.LIMITEMENSAL = LIMITEMENSAL;
	}


	public Integer getCarenciasdodev(){
		return CARENCIASDODEV;
	}


	public void setCarenciasdodev(Integer CARENCIASDODEV){
		this.CARENCIASDODEV = CARENCIASDODEV;
	}


	public Integer getLimitesdodev(){
		return LIMITESDODEV;
	}


	public void setLimitesdodev(Integer LIMITESDODEV){
		this.LIMITESDODEV = LIMITESDODEV;
	}


	public Date getDtnascconj(){
		return DTNASCCONJ;
	}


	public void setDtnascconj(Date DTNASCCONJ){
		this.DTNASCCONJ = DTNASCCONJ;
	}


	public String getTelefoneconjuge(){
		return TELEFONECONJUGE;
	}


	public void setTelefoneconjuge(String TELEFONECONJUGE){
		this.TELEFONECONJUGE = TELEFONECONJUGE;
	}


	public String getTrabalhoconjuge(){
		return TRABALHOCONJUGE;
	}


	public void setTrabalhoconjuge(String TRABALHOCONJUGE){
		this.TRABALHOCONJUGE = TRABALHOCONJUGE;
	}


	public String getCargoconjuge(){
		return CARGOCONJUGE;
	}


	public void setCargoconjuge(String CARGOCONJUGE){
		this.CARGOCONJUGE = CARGOCONJUGE;
	}


	public Integer getCapitalsocial(){
		return CAPITALSOCIAL;
	}


	public void setCapitalsocial(Integer CAPITALSOCIAL){
		this.CAPITALSOCIAL = CAPITALSOCIAL;
	}


	public Integer getPatrimonio(){
		return PATRIMONIO;
	}


	public void setPatrimonio(Integer PATRIMONIO){
		this.PATRIMONIO = PATRIMONIO;
	}


	public Date getDtfundacao(){
		return DTFUNDACAO;
	}


	public void setDtfundacao(Date DTFUNDACAO){
		this.DTFUNDACAO = DTFUNDACAO;
	}


	public Date getUltaltrefpessoal(){
		return ULTALTREFPESSOAL;
	}


	public void setUltaltrefpessoal(Date ULTALTREFPESSOAL){
		this.ULTALTREFPESSOAL = ULTALTREFPESSOAL;
	}


	public Date getUltaltrefcredito(){
		return ULTALTREFCREDITO;
	}


	public void setUltaltrefcredito(Date ULTALTREFCREDITO){
		this.ULTALTREFCREDITO = ULTALTREFCREDITO;
	}


	public Date getUltaltrefcomercial(){
		return ULTALTREFCOMERCIAL;
	}


	public void setUltaltrefcomercial(Date ULTALTREFCOMERCIAL){
		this.ULTALTREFCOMERCIAL = ULTALTREFCOMERCIAL;
	}


	public Integer getVlrmedicamento(){
		return VLRMEDICAMENTO;
	}


	public void setVlrmedicamento(Integer VLRMEDICAMENTO){
		this.VLRMEDICAMENTO = VLRMEDICAMENTO;
	}


	public Integer getRendatotal(){
		return RENDATOTAL;
	}


	public void setRendatotal(Integer RENDATOTAL){
		this.RENDATOTAL = RENDATOTAL;
	}


	public Integer getEmpresa(){
		return EMPRESA;
	}


	public void setEmpresa(Integer EMPRESA){
		this.EMPRESA = EMPRESA;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Date getDtnasc(){
		return DTNASC;
	}


	public void setDtnasc(Date DTNASC){
		this.DTNASC = DTNASC;
	}


	public String getNomepai(){
		return NOMEPAI;
	}


	public void setNomepai(String NOMEPAI){
		this.NOMEPAI = NOMEPAI;
	}


	public String getNomemae(){
		return NOMEMAE;
	}


	public void setNomemae(String NOMEMAE){
		this.NOMEMAE = NOMEMAE;
	}


	public String getTrabalho(){
		return TRABALHO;
	}


	public void setTrabalho(String TRABALHO){
		this.TRABALHO = TRABALHO;
	}


	public Date getDtadmissao(){
		return DTADMISSAO;
	}


	public void setDtadmissao(Date DTADMISSAO){
		this.DTADMISSAO = DTADMISSAO;
	}

}