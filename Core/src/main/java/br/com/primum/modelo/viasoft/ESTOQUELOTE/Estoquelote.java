package br.com.primum.modelo.viasoft.ESTOQUELOTE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ESTOQUELOTE")
public class Estoquelote  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Date DATA;

	@Column
	private String IDITEM;

	@Column
	private Integer IDESTOQUELOCAL;

	@Column
	private Integer IDESTOQUETIPO;

	@Column
	private String LOTE;

	@Column
	private Integer ENTRADAS;

	@Column
	private Integer SAIDAS;

	@Column
	private Integer SALDO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
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


	public String getLote(){
		return LOTE;
	}


	public void setLote(String LOTE){
		this.LOTE = LOTE;
	}


	public Integer getEntradas(){
		return ENTRADAS;
	}


	public void setEntradas(Integer ENTRADAS){
		this.ENTRADAS = ENTRADAS;
	}


	public Integer getSaidas(){
		return SAIDAS;
	}


	public void setSaidas(Integer SAIDAS){
		this.SAIDAS = SAIDAS;
	}


	public Integer getSaldo(){
		return SALDO;
	}


	public void setSaldo(Integer SALDO){
		this.SALDO = SALDO;
	}

}