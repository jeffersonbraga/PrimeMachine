package br.com.primum.modelo.viasoft.PESSOACREDSCOREHIST;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOACREDSCOREHIST")
public class Pessoacredscorehist  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer EMPRESA;

	@Column
	private Integer IDPESS;

	@Column
	private String USERID;

	@Column
	private Integer RENDA;

	@Column
	private Integer RENDACONJUGE;

	@Column
	private Integer OUTROSRENDIMEN;

	@Column
	private Integer DESPESAS;

	@Column
	private Integer VLRALUGUEL;

	@Column
	private Integer LIMITE;

	@Column
	private Integer LIMITEMENSAL;

	@Column
	private Integer VLRMEDICAMENTO;

	@Column
	private Integer RENDATOTAL;


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


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public Integer getRenda(){
		return RENDA;
	}


	public void setRenda(Integer RENDA){
		this.RENDA = RENDA;
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


	public Integer getLimite(){
		return LIMITE;
	}


	public void setLimite(Integer LIMITE){
		this.LIMITE = LIMITE;
	}


	public Integer getLimitemensal(){
		return LIMITEMENSAL;
	}


	public void setLimitemensal(Integer LIMITEMENSAL){
		this.LIMITEMENSAL = LIMITEMENSAL;
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

}