package br.com.primum.modelo.fornecedor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="fornecedor")
public class Fornecedor {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_fornecedor;
	
	@Column
	private Integer cd_pessoa;
	
	@Column
	private String ds_fornecedor;
	
	@Column
	private String ds_nomefantasia;
	
	@Column
	private String ds_cnpj;
	
	@Column
	private Integer st_situacao;

	public Integer getCd_fornecedor() {
		return cd_fornecedor;
	}

	public void setCd_fornecedor(Integer cd_fornecedor) {
		this.cd_fornecedor = cd_fornecedor;
	}

	public Integer getCd_pessoa() {
		return cd_pessoa;
	}

	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}

	public String getDs_fornecedor() {
		return ds_fornecedor;
	}

	public void setDs_fornecedor(String ds_fornecedor) {
		this.ds_fornecedor = ds_fornecedor;
	}

	public String getDs_nomefantasia() {
		return ds_nomefantasia;
	}

	public void setDs_nomefantasia(String ds_nomefantasia) {
		this.ds_nomefantasia = ds_nomefantasia;
	}

	public String getDs_cnpj() {
		return ds_cnpj;
	}

	public void setDs_cnpj(String ds_cnpj) {
		this.ds_cnpj = ds_cnpj;
	}

	public Integer getSt_situacao() {
		return st_situacao;
	}

	public void setSt_situacao(Integer st_situacao) {
		this.st_situacao = st_situacao;
	}
}
