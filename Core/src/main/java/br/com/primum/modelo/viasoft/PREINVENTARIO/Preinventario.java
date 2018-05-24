package br.com.primum.modelo.viasoft.PREINVENTARIO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PREINVENTARIO")
public class Preinventario  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDINVENTARIO;

	@Column
	private Integer IDITEM;

	@Column
	private Date DTFECHA;

	@Column
	private Integer IDESTOQUELOCAL;

	@Column
	private Integer QTDESTOQUE;

	@Column
	private Integer QTDEXP;

	@Column
	private Integer CUSTOAQUIS;

	@Column
	private Integer ESTABITEM;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdinventario(){
		return IDINVENTARIO;
	}


	public void setIdinventario(Integer IDINVENTARIO){
		this.IDINVENTARIO = IDINVENTARIO;
	}


	public Integer getIditem(){
		return IDITEM;
	}


	public void setIditem(Integer IDITEM){
		this.IDITEM = IDITEM;
	}


	public Date getDtfecha(){
		return DTFECHA;
	}


	public void setDtfecha(Date DTFECHA){
		this.DTFECHA = DTFECHA;
	}


	public Integer getIdestoquelocal(){
		return IDESTOQUELOCAL;
	}


	public void setIdestoquelocal(Integer IDESTOQUELOCAL){
		this.IDESTOQUELOCAL = IDESTOQUELOCAL;
	}


	public Integer getQtdestoque(){
		return QTDESTOQUE;
	}


	public void setQtdestoque(Integer QTDESTOQUE){
		this.QTDESTOQUE = QTDESTOQUE;
	}


	public Integer getQtdexp(){
		return QTDEXP;
	}


	public void setQtdexp(Integer QTDEXP){
		this.QTDEXP = QTDEXP;
	}


	public Integer getCustoaquis(){
		return CUSTOAQUIS;
	}


	public void setCustoaquis(Integer CUSTOAQUIS){
		this.CUSTOAQUIS = CUSTOAQUIS;
	}


	public Integer getEstabitem(){
		return ESTABITEM;
	}


	public void setEstabitem(Integer ESTABITEM){
		this.ESTABITEM = ESTABITEM;
	}

}