package br.com.primum.modelo.viasoft.ESTOQUESALDO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ESTOQUESALDO")
public class Estoquesaldo  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer ESTABITEM;

	@Column
	private String IDITEM;

	@Column
	private Integer IDESTOQUELOCAL;

	@Column
	private Integer IDESTOQUETIPO;

	@Column
	private Date DATA;

	@Column
	private Integer SALDOANT;

	@Column
	private Integer SALDO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
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


	public Integer getIdestoquelocal(){
		return IDESTOQUELOCAL;
	}


	public void setIdestoquelocal(Integer IDESTOQUELOCAL){
		this.IDESTOQUELOCAL = IDESTOQUELOCAL;
	}


	public Integer getIdestoquetipo(){
		return IDESTOQUETIPO;
	}


	public void setIdestoquetipo(Integer IDESTOQUETIPO){
		this.IDESTOQUETIPO = IDESTOQUETIPO;
	}


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
	}


	public Integer getSaldoant(){
		return SALDOANT;
	}


	public void setSaldoant(Integer SALDOANT){
		this.SALDOANT = SALDOANT;
	}


	public Integer getSaldo(){
		return SALDO;
	}


	public void setSaldo(Integer SALDO){
		this.SALDO = SALDO;
	}

}