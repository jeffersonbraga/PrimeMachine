package br.com.primum.modelo.viasoft.CARRINHOINFO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARRINHOINFO")
public class Carrinhoinfo  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCARRINHO;

	@Column
	private Integer SEQITEM;

	@Column
	private Integer IDINFOVENDA;

	@Column
	private String VALORINFO;


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


	public Integer getIdinfovenda(){
		return IDINFOVENDA;
	}


	public void setIdinfovenda(Integer IDINFOVENDA){
		this.IDINFOVENDA = IDINFOVENDA;
	}


	public String getValorinfo(){
		return VALORINFO;
	}


	public void setValorinfo(String VALORINFO){
		this.VALORINFO = VALORINFO;
	}

}