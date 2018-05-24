package br.com.primum.modelo.viasoft.TABPRVDAITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="TABPRVDAITEM")
public class Tabprvdaitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDTABPRVDA;

	@Column
	private Integer SEQUENCIA;

	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private Integer IDEMBALAGEM;

	@Column
	private Integer PRECO;

	@Column
	private Integer PERCENTUAL;

	@Column
	private String IDITEMKIT;


	public Integer getIdtabprvda(){
		return IDTABPRVDA;
	}


	public void setIdtabprvda(Integer IDTABPRVDA){
		this.IDTABPRVDA = IDTABPRVDA;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getIdembalagem(){
		return IDEMBALAGEM;
	}


	public void setIdembalagem(Integer IDEMBALAGEM){
		this.IDEMBALAGEM = IDEMBALAGEM;
	}


	public Integer getPreco(){
		return PRECO;
	}


	public void setPreco(Integer PRECO){
		this.PRECO = PRECO;
	}


	public Integer getPercentual(){
		return PERCENTUAL;
	}


	public void setPercentual(Integer PERCENTUAL){
		this.PERCENTUAL = PERCENTUAL;
	}


	public String getIditemkit(){
		return IDITEMKIT;
	}


	public void setIditemkit(String IDITEMKIT){
		this.IDITEMKIT = IDITEMKIT;
	}

}