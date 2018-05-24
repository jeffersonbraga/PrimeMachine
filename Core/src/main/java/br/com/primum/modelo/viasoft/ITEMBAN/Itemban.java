package br.com.primum.modelo.viasoft.ITEMBAN;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMBAN")
public class Itemban  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDBANDEIRA;

	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private Integer ESTOQUEMIN;

	@Column
	private Integer ESTOQUEMAX;


	public Integer getIdbandeira(){
		return IDBANDEIRA;
	}


	public void setIdbandeira(Integer IDBANDEIRA){
		this.IDBANDEIRA = IDBANDEIRA;
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


	public Integer getEstoquemin(){
		return ESTOQUEMIN;
	}


	public void setEstoquemin(Integer ESTOQUEMIN){
		this.ESTOQUEMIN = ESTOQUEMIN;
	}


	public Integer getEstoquemax(){
		return ESTOQUEMAX;
	}


	public void setEstoquemax(Integer ESTOQUEMAX){
		this.ESTOQUEMAX = ESTOQUEMAX;
	}

}