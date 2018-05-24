package br.com.primum.modelo.viasoft.CARRINHOGARANTIA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARRINHOGARANTIA")
public class Carrinhogarantia  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCARRINHO;

	@Column
	private Integer SEQITEM;

	@Column
	private Integer IDFAIXAGARANTIA;

	@Column
	private Integer VALORGAR;

	@Column
	private String CODIFICACAO;

	@Column
	private Integer GARANTIATOT;


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


	public Integer getIdfaixagarantia(){
		return IDFAIXAGARANTIA;
	}


	public void setIdfaixagarantia(Integer IDFAIXAGARANTIA){
		this.IDFAIXAGARANTIA = IDFAIXAGARANTIA;
	}


	public Integer getValorgar(){
		return VALORGAR;
	}


	public void setValorgar(Integer VALORGAR){
		this.VALORGAR = VALORGAR;
	}


	public String getCodificacao(){
		return CODIFICACAO;
	}


	public void setCodificacao(String CODIFICACAO){
		this.CODIFICACAO = CODIFICACAO;
	}


	public Integer getGarantiatot(){
		return GARANTIATOT;
	}


	public void setGarantiatot(Integer GARANTIATOT){
		this.GARANTIATOT = GARANTIATOT;
	}

}