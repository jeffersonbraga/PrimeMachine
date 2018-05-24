package br.com.primum.modelo.viasoft.PESSOADOCAUTORIZ;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOADOCAUTORIZ")
public class Pessoadocautoriz  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDPESS;

	@Column
	private Integer IDPESSOADOCAUTORIZ;

	@Column
	private String DOCUMENTO;

	@Column
	private String NOME;

	@Column
	private Date DTLIMITE;

	@Column
	private Integer PARENTESCO;

	@Column
	private String MAE;

	@Column
	private Date DTNASCIMENTO;

	@Column
	private Integer ESTADOCIVIL;

	@Column
	private String SEXO;

	@Column
	private String CPF;

	@Column
	private String CTPS;

	@Column
	private Date CTPSEMISSAO;

	@Column
	private String UFRG;

	@Column
	private String SIC;

	@Column
	private String NIS;

	@Column
	private String CIDNATURAL;

	@Column
	private String NROCERTIDAO;

	@Column
	private String LIVROCERTIDAO;

	@Column
	private String FOLHACERTIDAO;

	@Column
	private Integer IDINSTRUCAO;

	@Column
	private Integer IDOCUPACAO;

	@Column
	private String TRABALHO;

	@Column
	private String SERVPUB;

	@Column
	private String MATSERVPUB;

	@Column
	private Integer RENDA;


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getIdpessoadocautoriz(){
		return IDPESSOADOCAUTORIZ;
	}


	public void setIdpessoadocautoriz(Integer IDPESSOADOCAUTORIZ){
		this.IDPESSOADOCAUTORIZ = IDPESSOADOCAUTORIZ;
	}


	public String getDocumento(){
		return DOCUMENTO;
	}


	public void setDocumento(String DOCUMENTO){
		this.DOCUMENTO = DOCUMENTO;
	}


	public String getNome(){
		return NOME;
	}


	public void setNome(String NOME){
		this.NOME = NOME;
	}


	public Date getDtlimite(){
		return DTLIMITE;
	}


	public void setDtlimite(Date DTLIMITE){
		this.DTLIMITE = DTLIMITE;
	}


	public Integer getParentesco(){
		return PARENTESCO;
	}


	public void setParentesco(Integer PARENTESCO){
		this.PARENTESCO = PARENTESCO;
	}


	public String getMae(){
		return MAE;
	}


	public void setMae(String MAE){
		this.MAE = MAE;
	}


	public Date getDtnascimento(){
		return DTNASCIMENTO;
	}


	public void setDtnascimento(Date DTNASCIMENTO){
		this.DTNASCIMENTO = DTNASCIMENTO;
	}


	public Integer getEstadocivil(){
		return ESTADOCIVIL;
	}


	public void setEstadocivil(Integer ESTADOCIVIL){
		this.ESTADOCIVIL = ESTADOCIVIL;
	}


	public String getSexo(){
		return SEXO;
	}


	public void setSexo(String SEXO){
		this.SEXO = SEXO;
	}


	public String getCpf(){
		return CPF;
	}


	public void setCpf(String CPF){
		this.CPF = CPF;
	}


	public String getCtps(){
		return CTPS;
	}


	public void setCtps(String CTPS){
		this.CTPS = CTPS;
	}


	public Date getCtpsemissao(){
		return CTPSEMISSAO;
	}


	public void setCtpsemissao(Date CTPSEMISSAO){
		this.CTPSEMISSAO = CTPSEMISSAO;
	}


	public String getUfrg(){
		return UFRG;
	}


	public void setUfrg(String UFRG){
		this.UFRG = UFRG;
	}


	public String getSic(){
		return SIC;
	}


	public void setSic(String SIC){
		this.SIC = SIC;
	}


	public String getNis(){
		return NIS;
	}


	public void setNis(String NIS){
		this.NIS = NIS;
	}


	public String getCidnatural(){
		return CIDNATURAL;
	}


	public void setCidnatural(String CIDNATURAL){
		this.CIDNATURAL = CIDNATURAL;
	}


	public String getNrocertidao(){
		return NROCERTIDAO;
	}


	public void setNrocertidao(String NROCERTIDAO){
		this.NROCERTIDAO = NROCERTIDAO;
	}


	public String getLivrocertidao(){
		return LIVROCERTIDAO;
	}


	public void setLivrocertidao(String LIVROCERTIDAO){
		this.LIVROCERTIDAO = LIVROCERTIDAO;
	}


	public String getFolhacertidao(){
		return FOLHACERTIDAO;
	}


	public void setFolhacertidao(String FOLHACERTIDAO){
		this.FOLHACERTIDAO = FOLHACERTIDAO;
	}


	public Integer getIdinstrucao(){
		return IDINSTRUCAO;
	}


	public void setIdinstrucao(Integer IDINSTRUCAO){
		this.IDINSTRUCAO = IDINSTRUCAO;
	}


	public Integer getIdocupacao(){
		return IDOCUPACAO;
	}


	public void setIdocupacao(Integer IDOCUPACAO){
		this.IDOCUPACAO = IDOCUPACAO;
	}


	public String getTrabalho(){
		return TRABALHO;
	}


	public void setTrabalho(String TRABALHO){
		this.TRABALHO = TRABALHO;
	}


	public String getServpub(){
		return SERVPUB;
	}


	public void setServpub(String SERVPUB){
		this.SERVPUB = SERVPUB;
	}


	public String getMatservpub(){
		return MATSERVPUB;
	}


	public void setMatservpub(String MATSERVPUB){
		this.MATSERVPUB = MATSERVPUB;
	}


	public Integer getRenda(){
		return RENDA;
	}


	public void setRenda(Integer RENDA){
		this.RENDA = RENDA;
	}

}