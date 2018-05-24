package br.com.primum.modelo.viasoft.FAIXAGARANTIAITE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FAIXAGARANTIAITE")
public class Faixagarantiaite  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDFAIXAGARANTIA;

	@Column
	private Integer SEQ;

	@Column
	private String CODIFICACAO;

	@Column
	private Integer FAIXAATE;

	@Column
	private Date VALIDADE;

	@Column
	private Integer PRECOVDA;

	@Column
	private Integer CUSTO;

	@Column
	private Integer MARGEM;


	public Integer getIdfaixagarantia(){
		return IDFAIXAGARANTIA;
	}


	public void setIdfaixagarantia(Integer IDFAIXAGARANTIA){
		this.IDFAIXAGARANTIA = IDFAIXAGARANTIA;
	}


	public Integer getSeq(){
		return SEQ;
	}


	public void setSeq(Integer SEQ){
		this.SEQ = SEQ;
	}


	public String getCodificacao(){
		return CODIFICACAO;
	}


	public void setCodificacao(String CODIFICACAO){
		this.CODIFICACAO = CODIFICACAO;
	}


	public Integer getFaixaate(){
		return FAIXAATE;
	}


	public void setFaixaate(Integer FAIXAATE){
		this.FAIXAATE = FAIXAATE;
	}


	public Date getValidade(){
		return VALIDADE;
	}


	public void setValidade(Date VALIDADE){
		this.VALIDADE = VALIDADE;
	}


	public Integer getPrecovda(){
		return PRECOVDA;
	}


	public void setPrecovda(Integer PRECOVDA){
		this.PRECOVDA = PRECOVDA;
	}


	public Integer getCusto(){
		return CUSTO;
	}


	public void setCusto(Integer CUSTO){
		this.CUSTO = CUSTO;
	}


	public Integer getMargem(){
		return MARGEM;
	}


	public void setMargem(Integer MARGEM){
		this.MARGEM = MARGEM;
	}

}