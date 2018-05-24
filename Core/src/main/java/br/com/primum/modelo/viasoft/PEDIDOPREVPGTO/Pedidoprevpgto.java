package br.com.primum.modelo.viasoft.PEDIDOPREVPGTO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PEDIDOPREVPGTO")
public class Pedidoprevpgto  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDPEDIDO;

	@Column
	private Integer IDPESS;

	@Column
	private Integer SEQ;

	@Column
	private Date DTVENCTO;

	@Column
	private Integer VALOR;

	@Column
	private Integer FORMAPAGTO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdpedido(){
		return IDPEDIDO;
	}


	public void setIdpedido(Integer IDPEDIDO){
		this.IDPEDIDO = IDPEDIDO;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getSeq(){
		return SEQ;
	}


	public void setSeq(Integer SEQ){
		this.SEQ = SEQ;
	}


	public Date getDtvencto(){
		return DTVENCTO;
	}


	public void setDtvencto(Date DTVENCTO){
		this.DTVENCTO = DTVENCTO;
	}


	public Integer getValor(){
		return VALOR;
	}


	public void setValor(Integer VALOR){
		this.VALOR = VALOR;
	}


	public Integer getFormapagto(){
		return FORMAPAGTO;
	}


	public void setFormapagto(Integer FORMAPAGTO){
		this.FORMAPAGTO = FORMAPAGTO;
	}

}