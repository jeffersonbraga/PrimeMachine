package br.com.primum.modelo.viasoft.CARRINHOPAGTOSDET;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARRINHOPAGTOSDET")
public class Carrinhopagtosdet  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCARRINHO;

	@Column
	private Integer SEQPAGTO;

	@Column
	private String FORMA;

	@Column
	private Integer SEQ;

	@Column
	private Date DATA;

	@Column
	private Integer VALOR;

	@Column
	private Date DATAORIG;

	@Column
	private Integer VALORORIG;


	public Integer getIdcarrinho(){
		return IDCARRINHO;
	}


	public void setIdcarrinho(Integer IDCARRINHO){
		this.IDCARRINHO = IDCARRINHO;
	}


	public Integer getSeqpagto(){
		return SEQPAGTO;
	}


	public void setSeqpagto(Integer SEQPAGTO){
		this.SEQPAGTO = SEQPAGTO;
	}


	public String getForma(){
		return FORMA;
	}


	public void setForma(String FORMA){
		this.FORMA = FORMA;
	}


	public Integer getSeq(){
		return SEQ;
	}


	public void setSeq(Integer SEQ){
		this.SEQ = SEQ;
	}


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
	}


	public Integer getValor(){
		return VALOR;
	}


	public void setValor(Integer VALOR){
		this.VALOR = VALOR;
	}


	public Date getDataorig(){
		return DATAORIG;
	}


	public void setDataorig(Date DATAORIG){
		this.DATAORIG = DATAORIG;
	}


	public Integer getValororig(){
		return VALORORIG;
	}


	public void setValororig(Integer VALORORIG){
		this.VALORORIG = VALORORIG;
	}

}