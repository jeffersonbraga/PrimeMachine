package br.com.primum.modelo.produto_compra;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="produto_compra")
public class Produto_compra  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer cd_produto_compra;

	@Column
	private Integer cd_produto;

	@Column
	private Integer cd_compra;


	public Integer getCd_produto_compra(){
		return cd_produto_compra;
	}


	public void setCd_produto_compra(Integer cd_produto_compra){
		this.cd_produto_compra = cd_produto_compra;
	}


	public Integer getCd_produto(){
		return cd_produto;
	}


	public void setCd_produto(Integer cd_produto){
		this.cd_produto = cd_produto;
	}


	public Integer getCd_compra(){
		return cd_compra;
	}


	public void setCd_compra(Integer cd_compra){
		this.cd_compra = cd_compra;
	}

}