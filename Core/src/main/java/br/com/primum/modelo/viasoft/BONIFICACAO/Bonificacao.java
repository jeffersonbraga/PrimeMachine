package br.com.primum.modelo.viasoft.BONIFICACAO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="BONIFICACAO")
public class Bonificacao  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDBONIFICACAO;

	@Column
	private Integer IDPESS;

	@Column
	private Integer TIPOBONIF;

	@Column
	private Integer VALOR;

	@Column
	private Integer IDPEDIDO;

	@Column
	private Integer IDDUPREC;

	@Column
	private String USERID;

	@Column
	private Integer SITUACAO;

	@Column
	private Date DATAPREV;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdbonificacao(){
		return IDBONIFICACAO;
	}


	public void setIdbonificacao(Integer IDBONIFICACAO){
		this.IDBONIFICACAO = IDBONIFICACAO;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getTipobonif(){
		return TIPOBONIF;
	}


	public void setTipobonif(Integer TIPOBONIF){
		this.TIPOBONIF = TIPOBONIF;
	}


	public Integer getValor(){
		return VALOR;
	}


	public void setValor(Integer VALOR){
		this.VALOR = VALOR;
	}


	public Integer getIdpedido(){
		return IDPEDIDO;
	}


	public void setIdpedido(Integer IDPEDIDO){
		this.IDPEDIDO = IDPEDIDO;
	}


	public Integer getIdduprec(){
		return IDDUPREC;
	}


	public void setIdduprec(Integer IDDUPREC){
		this.IDDUPREC = IDDUPREC;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public Integer getSituacao(){
		return SITUACAO;
	}


	public void setSituacao(Integer SITUACAO){
		this.SITUACAO = SITUACAO;
	}


	public Date getDataprev(){
		return DATAPREV;
	}


	public void setDataprev(Date DATAPREV){
		this.DATAPREV = DATAPREV;
	}

}