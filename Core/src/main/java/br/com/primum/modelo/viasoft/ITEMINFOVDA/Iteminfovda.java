package br.com.primum.modelo.viasoft.ITEMINFOVDA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMINFOVDA")
public class Iteminfovda  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer EMPRESA;

	@Column
	private Integer ESTABITEM;

	@Column
	private String IDITEM;

	@Column
	private Integer IDINFOVENDA;


	public Integer getEmpresa(){
		return EMPRESA;
	}


	public void setEmpresa(Integer EMPRESA){
		this.EMPRESA = EMPRESA;
	}


	public Integer getEstabitem(){
		return ESTABITEM;
	}


	public void setEstabitem(Integer ESTABITEM){
		this.ESTABITEM = ESTABITEM;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getIdinfovenda(){
		return IDINFOVENDA;
	}


	public void setIdinfovenda(Integer IDINFOVENDA){
		this.IDINFOVENDA = IDINFOVENDA;
	}

}