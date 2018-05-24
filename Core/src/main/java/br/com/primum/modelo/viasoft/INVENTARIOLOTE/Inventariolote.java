package br.com.primum.modelo.viasoft.INVENTARIOLOTE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="INVENTARIOLOTE")
public class Inventariolote  implements Serializable {

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
	private String LOTE;

	@Column
	private Integer QTDCONTADA;

	@Column
	private Integer QTDESTOQUE;


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


	public String getLote(){
		return LOTE;
	}


	public void setLote(String LOTE){
		this.LOTE = LOTE;
	}


	public Integer getQtdcontada(){
		return QTDCONTADA;
	}


	public void setQtdcontada(Integer QTDCONTADA){
		this.QTDCONTADA = QTDCONTADA;
	}


	public Integer getQtdestoque(){
		return QTDESTOQUE;
	}


	public void setQtdestoque(Integer QTDESTOQUE){
		this.QTDESTOQUE = QTDESTOQUE;
	}

}