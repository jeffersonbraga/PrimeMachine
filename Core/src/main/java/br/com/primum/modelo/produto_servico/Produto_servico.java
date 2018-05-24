package br.com.primum.modelo.produto_servico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="produto_servico")
public class Produto_servico  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer cd_produto_servico;

	@Column
	private Integer cd_produto;

	@Column
	private Integer cd_servico;


	public Integer getCd_produto_servico(){
		return cd_produto_servico;
	}


	public void setCd_produto_servico(Integer cd_produto_servico){
		this.cd_produto_servico = cd_produto_servico;
	}


	public Integer getCd_produto(){
		return cd_produto;
	}


	public void setCd_produto(Integer cd_produto){
		this.cd_produto = cd_produto;
	}


	public Integer getCd_servico(){
		return cd_servico;
	}


	public void setCd_servico(Integer cd_servico){
		this.cd_servico = cd_servico;
	}

}