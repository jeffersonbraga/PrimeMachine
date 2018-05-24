package br.com.primum.modelo.viasoft.PESSOADOCCLIENTE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOADOCCLIENTE")
public class Pessoadoccliente  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDPESS;

	@Column
	private Integer IDPESSOADOCCLIENTE;

	@Column
	private Integer IDPESSCOBRADOR;

	@Column
	private Integer OVERPRICE;

	@Column
	private Integer DESCONTO;

	@Column
	private Integer IDDESCPESSOA;

	@Column
	private Integer IDPESSCOMISSIONADO;

	@Column
	private Integer IDREGIAO;


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getIdpessoadoccliente(){
		return IDPESSOADOCCLIENTE;
	}


	public void setIdpessoadoccliente(Integer IDPESSOADOCCLIENTE){
		this.IDPESSOADOCCLIENTE = IDPESSOADOCCLIENTE;
	}


	public Integer getIdpesscobrador(){
		return IDPESSCOBRADOR;
	}


	public void setIdpesscobrador(Integer IDPESSCOBRADOR){
		this.IDPESSCOBRADOR = IDPESSCOBRADOR;
	}


	public Integer getOverprice(){
		return OVERPRICE;
	}


	public void setOverprice(Integer OVERPRICE){
		this.OVERPRICE = OVERPRICE;
	}


	public Integer getDesconto(){
		return DESCONTO;
	}


	public void setDesconto(Integer DESCONTO){
		this.DESCONTO = DESCONTO;
	}


	public Integer getIddescpessoa(){
		return IDDESCPESSOA;
	}


	public void setIddescpessoa(Integer IDDESCPESSOA){
		this.IDDESCPESSOA = IDDESCPESSOA;
	}


	public Integer getIdpesscomissionado(){
		return IDPESSCOMISSIONADO;
	}


	public void setIdpesscomissionado(Integer IDPESSCOMISSIONADO){
		this.IDPESSCOMISSIONADO = IDPESSCOMISSIONADO;
	}


	public Integer getIdregiao(){
		return IDREGIAO;
	}


	public void setIdregiao(Integer IDREGIAO){
		this.IDREGIAO = IDREGIAO;
	}

}