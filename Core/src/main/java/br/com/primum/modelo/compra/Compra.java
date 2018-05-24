package br.com.primum.modelo.compra;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="compra")
public class Compra  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer cd_compra;

	@Column
	private Integer cd_pessoa;

	@Column
	private String nr_nota_fiscal;

	@Column
	private Date dt_compra;

	@Column
	private Date dt_entrada_produto;


	public Integer getCd_compra(){
		return cd_compra;
	}


	public void setCd_compra(Integer cd_compra){
		this.cd_compra = cd_compra;
	}


	public Integer getCd_pessoa(){
		return cd_pessoa;
	}


	public void setCd_pessoa(Integer cd_pessoa){
		this.cd_pessoa = cd_pessoa;
	}


	public String getNr_nota_fiscal(){
		return nr_nota_fiscal;
	}


	public void setNr_nota_fiscal(String nr_nota_fiscal){
		this.nr_nota_fiscal = nr_nota_fiscal;
	}


	public Date getDt_compra(){
		return dt_compra;
	}


	public void setDt_compra(Date dt_compra){
		this.dt_compra = dt_compra;
	}


	public Date getDt_entrada_produto(){
		return dt_entrada_produto;
	}


	public void setDt_entrada_produto(Date dt_entrada_produto){
		this.dt_entrada_produto = dt_entrada_produto;
	}

}