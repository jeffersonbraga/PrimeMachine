package br.com.primum.modelo.viasoft.INVENTARIOITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="INVENTARIOITEM")
public class Inventarioitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDINVENTARIO;

	@Column
	private Integer IDCONTAGEM;

	@Column
	private Integer SEQITEM;

	@Column
	private String IDITEM;

	@Column
	private Integer QTDESTOQUE;

	@Column
	private Integer QTDCONTADA;

	@Column
	private Integer CUSTOAQUIS;

	@Column
	private String INVENTARIADO;


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


	public Integer getIdcontagem(){
		return IDCONTAGEM;
	}


	public void setIdcontagem(Integer IDCONTAGEM){
		this.IDCONTAGEM = IDCONTAGEM;
	}


	public Integer getSeqitem(){
		return SEQITEM;
	}


	public void setSeqitem(Integer SEQITEM){
		this.SEQITEM = SEQITEM;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getQtdestoque(){
		return QTDESTOQUE;
	}


	public void setQtdestoque(Integer QTDESTOQUE){
		this.QTDESTOQUE = QTDESTOQUE;
	}


	public Integer getQtdcontada(){
		return QTDCONTADA;
	}


	public void setQtdcontada(Integer QTDCONTADA){
		this.QTDCONTADA = QTDCONTADA;
	}


	public Integer getCustoaquis(){
		return CUSTOAQUIS;
	}


	public void setCustoaquis(Integer CUSTOAQUIS){
		this.CUSTOAQUIS = CUSTOAQUIS;
	}


	public String getInventariado(){
		return INVENTARIADO;
	}


	public void setInventariado(String INVENTARIADO){
		this.INVENTARIADO = INVENTARIADO;
	}

}