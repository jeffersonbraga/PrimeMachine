package br.com.primum.modelo.viasoft.BALANCOLOTE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="BALANCOLOTE")
public class Balancolote  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDBALANCO;

	@Column
	private Integer IDCONTAGEM;

	@Column
	private String IDITEM;

	@Column
	private String LOTE;

	@Column
	private Integer QTDESTOQUE;

	@Column
	private Integer QTDCONTADA;

	@Column
	private Date VALIDADE;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdbalanco(){
		return IDBALANCO;
	}


	public void setIdbalanco(Integer IDBALANCO){
		this.IDBALANCO = IDBALANCO;
	}


	public Integer getIdcontagem(){
		return IDCONTAGEM;
	}


	public void setIdcontagem(Integer IDCONTAGEM){
		this.IDCONTAGEM = IDCONTAGEM;
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


	public Date getValidade(){
		return VALIDADE;
	}


	public void setValidade(Date VALIDADE){
		this.VALIDADE = VALIDADE;
	}

}