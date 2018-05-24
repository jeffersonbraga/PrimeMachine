package br.com.primum.modelo.fiscal_sif;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="fiscal_sif")
public class Fiscal_sif {

	@Id
	@Column
	private Integer cd_fiscal_sif;

	@Column
	private Integer cd_pessoa;

	@Column
	private String ds_senha_acesso;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_cadastro;

	@Column
	private Integer st_fiscal_sif;
	
	@Column
	private String nr_identificacao;

	public Integer getCd_fiscal_sif() {
		return cd_fiscal_sif;
	}

	public void setCd_fiscal_sif(Integer cd_fiscal_sif) {
		this.cd_fiscal_sif = cd_fiscal_sif;
	}

	public Integer getCd_pessoa() {
		return cd_pessoa;
	}

	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}

	public String getDs_senha_acesso() {
		return ds_senha_acesso;
	}

	public void setDs_senha_acesso(String ds_senha_acesso) {
		this.ds_senha_acesso = ds_senha_acesso;
	}

	public Date getDt_cadastro() {
		return dt_cadastro;
	}

	public void setDt_cadastro(Date dt_cadastro) {
		this.dt_cadastro = dt_cadastro;
	}

	public Integer getSt_fiscal_sif() {
		return st_fiscal_sif;
	}

	public void setSt_fiscal_sif(Integer st_fiscal_sif) {
		this.st_fiscal_sif = st_fiscal_sif;
	}

	public String getNr_identificacao() {
		return nr_identificacao;
	}

	public void setNr_identificacao(String nr_identificacao) {
		this.nr_identificacao = nr_identificacao;
	}
}
