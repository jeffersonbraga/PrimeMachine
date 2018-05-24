package br.com.primum.modelo.produto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="produto")
public class Produto  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer cd_produto;

	@Column
	private Integer cd_pessoa;

	@Column
	private String nm_produto;

	@Column
	private String ds_produto;

	@Column
	private String ds_codigo;

	@Column
	private Integer st_produto;


	public Integer getCd_produto(){
		return cd_produto;
	}


	public void setCd_produto(Integer cd_produto){
		this.cd_produto = cd_produto;
	}


	public Integer getCd_pessoa(){
		return cd_pessoa;
	}


	public void setCd_pessoa(Integer cd_pessoa){
		this.cd_pessoa = cd_pessoa;
	}


	public String getNm_produto(){
		return nm_produto;
	}


	public void setNm_produto(String nm_produto){
		this.nm_produto = nm_produto;
	}


	public String getDs_produto(){
		return ds_produto;
	}


	public void setDs_produto(String ds_produto){
		this.ds_produto = ds_produto;
	}


	public String getDs_codigo(){
		return ds_codigo;
	}


	public void setDs_codigo(String ds_codigo){
		this.ds_codigo = ds_codigo;
	}


	public Integer getSt_produto(){
		return st_produto;
	}


	public void setSt_produto(Integer st_produto){
		this.st_produto = st_produto;
	}

}