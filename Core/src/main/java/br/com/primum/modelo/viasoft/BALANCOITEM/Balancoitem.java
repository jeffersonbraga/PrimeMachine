package br.com.primum.modelo.viasoft.BALANCOITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="BALANCOITEM")
public class Balancoitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDBALANCO;

	@Column
	private String IDITEM;

	@Column
	private Integer IDCONTAGEM;

	@Column
	private Integer QTDESTOQUE;

	@Column
	private Integer QTDCONTADA;


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


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getIdcontagem(){
		return IDCONTAGEM;
	}


	public void setIdcontagem(Integer IDCONTAGEM){
		this.IDCONTAGEM = IDCONTAGEM;
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

}