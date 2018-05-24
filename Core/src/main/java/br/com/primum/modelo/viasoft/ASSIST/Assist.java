package br.com.primum.modelo.viasoft.ASSIST;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ASSIST")
public class Assist  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer EMPRESA;

	@Column
	private Integer IDASSIST;

	@Column
	private Integer IDPESS;

	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

	@Column
	private String IDITEM;

	@Column
	private String APARENCIA;

	@Column
	private String ACESSORIO;

	@Column
	private String DEFEITO;

	@Column
	private String OBSERVACAO;

	@Column
	private Date DATA;

	@Column
	private Date DTPREV;

	@Column
	private String TIPO;

	@Column
	private String SITUACAO;

	@Column
	private Integer IDVEND;

	@Column
	private Date DTAPROV;

	@Column
	private String CODASSIST;

	@Column
	private String RESPONSAVEL;

	@Column
	private Integer QTDE;

	@Column
	private String SEMNOTA;


	public Integer getEmpresa(){
		return EMPRESA;
	}


	public void setEmpresa(Integer EMPRESA){
		this.EMPRESA = EMPRESA;
	}


	public Integer getIdassist(){
		return IDASSIST;
	}


	public void setIdassist(Integer IDASSIST){
		this.IDASSIST = IDASSIST;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
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


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public String getAparencia(){
		return APARENCIA;
	}


	public void setAparencia(String APARENCIA){
		this.APARENCIA = APARENCIA;
	}


	public String getAcessorio(){
		return ACESSORIO;
	}


	public void setAcessorio(String ACESSORIO){
		this.ACESSORIO = ACESSORIO;
	}


	public String getDefeito(){
		return DEFEITO;
	}


	public void setDefeito(String DEFEITO){
		this.DEFEITO = DEFEITO;
	}


	public String getObservacao(){
		return OBSERVACAO;
	}


	public void setObservacao(String OBSERVACAO){
		this.OBSERVACAO = OBSERVACAO;
	}


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
	}


	public Date getDtprev(){
		return DTPREV;
	}


	public void setDtprev(Date DTPREV){
		this.DTPREV = DTPREV;
	}


	public String getTipo(){
		return TIPO;
	}


	public void setTipo(String TIPO){
		this.TIPO = TIPO;
	}


	public String getSituacao(){
		return SITUACAO;
	}


	public void setSituacao(String SITUACAO){
		this.SITUACAO = SITUACAO;
	}


	public Integer getIdvend(){
		return IDVEND;
	}


	public void setIdvend(Integer IDVEND){
		this.IDVEND = IDVEND;
	}


	public Date getDtaprov(){
		return DTAPROV;
	}


	public void setDtaprov(Date DTAPROV){
		this.DTAPROV = DTAPROV;
	}


	public String getCodassist(){
		return CODASSIST;
	}


	public void setCodassist(String CODASSIST){
		this.CODASSIST = CODASSIST;
	}


	public String getResponsavel(){
		return RESPONSAVEL;
	}


	public void setResponsavel(String RESPONSAVEL){
		this.RESPONSAVEL = RESPONSAVEL;
	}


	public Integer getQtde(){
		return QTDE;
	}


	public void setQtde(Integer QTDE){
		this.QTDE = QTDE;
	}


	public String getSemnota(){
		return SEMNOTA;
	}


	public void setSemnota(String SEMNOTA){
		this.SEMNOTA = SEMNOTA;
	}

}