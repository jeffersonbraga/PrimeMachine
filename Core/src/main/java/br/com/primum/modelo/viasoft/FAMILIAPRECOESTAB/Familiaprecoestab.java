package br.com.primum.modelo.viasoft.FAMILIAPRECOESTAB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FAMILIAPRECOESTAB")
public class Familiaprecoestab  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDBANDEIRA;

	@Column
	private Integer IDFAMILIAPRECO;

	@Column
	private Integer ESTAB;

	@Column
	private Integer PRECO;


	public Integer getIdbandeira(){
		return IDBANDEIRA;
	}


	public void setIdbandeira(Integer IDBANDEIRA){
		this.IDBANDEIRA = IDBANDEIRA;
	}


	public Integer getIdfamiliapreco(){
		return IDFAMILIAPRECO;
	}


	public void setIdfamiliapreco(Integer IDFAMILIAPRECO){
		this.IDFAMILIAPRECO = IDFAMILIAPRECO;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getPreco(){
		return PRECO;
	}


	public void setPreco(Integer PRECO){
		this.PRECO = PRECO;
	}

}