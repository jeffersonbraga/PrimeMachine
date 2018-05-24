package br.com.primum.modelo.viasoft.CARRINHOPAGTOS;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARRINHOPAGTOS")
public class Carrinhopagtos  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCARRINHO;

	@Column
	private Integer SEQUENCIA;

	@Column
	private Integer FORMA;

	@Column
	private Integer IDPARCELA;

	@Column
	private Integer IDCARTAO;

	@Column
	private Integer VALOR;

	@Column
	private Integer VALORLIQ;

	@Column
	private Integer CARENCIA;

	@Column
	private Integer JUROS;

	@Column
	private Integer DESPESAS;

	@Column
	private Integer DESCONTO;

	@Column
	private Integer PRAZOMED;

	@Column
	private Integer IDSITUACAONOTA;

	@Column
	private Integer RESTRICAO;


	public Integer getIdcarrinho(){
		return IDCARRINHO;
	}


	public void setIdcarrinho(Integer IDCARRINHO){
		this.IDCARRINHO = IDCARRINHO;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public Integer getForma(){
		return FORMA;
	}


	public void setForma(Integer FORMA){
		this.FORMA = FORMA;
	}


	public Integer getIdparcela(){
		return IDPARCELA;
	}


	public void setIdparcela(Integer IDPARCELA){
		this.IDPARCELA = IDPARCELA;
	}


	public Integer getIdcartao(){
		return IDCARTAO;
	}


	public void setIdcartao(Integer IDCARTAO){
		this.IDCARTAO = IDCARTAO;
	}


	public Integer getValor(){
		return VALOR;
	}


	public void setValor(Integer VALOR){
		this.VALOR = VALOR;
	}


	public Integer getValorliq(){
		return VALORLIQ;
	}


	public void setValorliq(Integer VALORLIQ){
		this.VALORLIQ = VALORLIQ;
	}


	public Integer getCarencia(){
		return CARENCIA;
	}


	public void setCarencia(Integer CARENCIA){
		this.CARENCIA = CARENCIA;
	}


	public Integer getJuros(){
		return JUROS;
	}


	public void setJuros(Integer JUROS){
		this.JUROS = JUROS;
	}


	public Integer getDespesas(){
		return DESPESAS;
	}


	public void setDespesas(Integer DESPESAS){
		this.DESPESAS = DESPESAS;
	}


	public Integer getDesconto(){
		return DESCONTO;
	}


	public void setDesconto(Integer DESCONTO){
		this.DESCONTO = DESCONTO;
	}


	public Integer getPrazomed(){
		return PRAZOMED;
	}


	public void setPrazomed(Integer PRAZOMED){
		this.PRAZOMED = PRAZOMED;
	}


	public Integer getIdsituacaonota(){
		return IDSITUACAONOTA;
	}


	public void setIdsituacaonota(Integer IDSITUACAONOTA){
		this.IDSITUACAONOTA = IDSITUACAONOTA;
	}


	public Integer getRestricao(){
		return RESTRICAO;
	}


	public void setRestricao(Integer RESTRICAO){
		this.RESTRICAO = RESTRICAO;
	}

}