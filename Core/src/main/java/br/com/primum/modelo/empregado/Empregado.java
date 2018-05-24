package br.com.primum.modelo.empregado;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="empregado")
public class Empregado {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_empregado;
	
	@Column
	private Integer cd_empresa;
	
	@Column
	private Integer cd_pessoa;
	
	@Column
	private Integer cd_escolaridade;
	
	@Column
	private String ds_ctps;
	
	////@Temporal(value=TemporalType.DATE)
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_contratacao;
	
	@Column
	private Integer st_empregado;
	
	@Column
	private Float nr_salario;
	
	@Column
	private Float nr_valorhora;
	
	@Column
	private Integer cd_profissao;

	public Integer getCd_empregado() {
		return cd_empregado;
	}

	public void setCd_empregado(Integer cd_empregado) {
		this.cd_empregado = cd_empregado;
	}

	public Integer getCd_empresa() {
		return cd_empresa;
	}

	public void setCd_empresa(Integer cd_empresa) {
		this.cd_empresa = cd_empresa;
	}

	public Integer getCd_pessoa() {
		return cd_pessoa;
	}

	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}

	public Integer getCd_escolaridade() {
		return cd_escolaridade;
	}

	public void setCd_escolaridade(Integer cd_escolaridade) {
		this.cd_escolaridade = cd_escolaridade;
	}

	public String getDs_ctps() {
		return ds_ctps;
	}

	public void setDs_ctps(String ds_ctps) {
		this.ds_ctps = ds_ctps;
	}

	public Date getDt_contratacao() {
		return dt_contratacao;
	}

	public void setDt_contratacao(Date dt_contratacao) {
		this.dt_contratacao = dt_contratacao;
	}

	public Integer getSt_empregado() {
		return st_empregado;
	}

	public void setSt_empregado(Integer st_empregado) {
		this.st_empregado = st_empregado;
	}

	public Float getNr_salario() {
		return nr_salario;
	}

	public void setNr_salario(Float nr_salario) {
		this.nr_salario = nr_salario;
	}

	public Float getNr_valorhora() {
		return nr_valorhora;
	}

	public void setNr_valorhora(Float nr_valorhora) {
		this.nr_valorhora = nr_valorhora;
	}

	public Integer getCd_profissao() {
		return cd_profissao;
	}

	public void setCd_profissao(Integer cd_profissao) {
		this.cd_profissao = cd_profissao;
	}
}