package br.com.primum.modelo.equipamento;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="equipamento")
public class Equipamento {

	@Id
	@Column
	private Integer cd_equipamento;
	
	@Column
	private Integer cd_setor;
	
	@Column
	private Integer cd_fabricante;
	
	@Column
	private Integer cd_departamento;
	
	@Column
	private String nm_equipamento;
	
	@Column
	private String ds_equipamento;
	
	@Column
	private String nr_chapa_equipamento;
	
	@Column
	private Integer nr_valor_equipamento;
	
	@Column
	private Integer st_equipamento;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_aquisicao;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_baixa;
	
	@Column
	private String ds_especificacao;
	
	@Column
	private Integer cd_unidade;	
	
	public String getNr_chapa_equipamento() {
		return nr_chapa_equipamento;
	}
	
	public void setNr_chapa_equipamento(String nr_chapa_equipamento) {
		this.nr_chapa_equipamento = nr_chapa_equipamento;
	}
	
	public Integer getCd_equipamento() {
		return cd_equipamento;
	}
	
	public void setCd_equipamento(Integer cd_equipamento) {
		this.cd_equipamento = cd_equipamento;
	}
	
	public Integer getCd_setor() {
		return cd_setor;
	}
	
	public void setCd_setor(Integer cd_setor) {
		this.cd_setor = cd_setor;
	}
	
	public Integer getCd_fabricante() {
		return cd_fabricante;
	}
	
	public void setCd_fabricante(Integer cd_fabricante) {
		this.cd_fabricante = cd_fabricante;
	}
	
	public Integer getCd_departamento() {
		return cd_departamento;
	}
	
	public void setCd_departamento(Integer cd_departamento) {
		this.cd_departamento = cd_departamento;
	}
	
	public String getNm_equipamento() {
		return nm_equipamento;
	}
	
	public void setNm_equipamento(String nm_equipamento) {
		this.nm_equipamento = nm_equipamento;
	}
	
	public String getDs_equipamento() {
		return ds_equipamento;
	}
	
	public void setDs_equipamento(String ds_equipamento) {
		this.ds_equipamento = ds_equipamento;
	}
	
	public Integer getNr_valor_equipamento() {
		return nr_valor_equipamento;
	}
	
	public void setNr_valor_equipamento(Integer nr_valor_equipamento) {
		this.nr_valor_equipamento = nr_valor_equipamento;
	}
	
	public Integer getSt_equipamento() {
		return st_equipamento;
	}
	
	public void setSt_equipamento(Integer st_equipamento) {
		this.st_equipamento = st_equipamento;
	}
	
	public Date getDt_aquisicao() {
		return dt_aquisicao;
	}
	
	public void setDt_aquisicao(Date dt_aquisicao) {
		this.dt_aquisicao = dt_aquisicao;
	}
	
	public Date getDt_baixa() {
		return dt_baixa;
	}
	
	public void setDt_baixa(Date dt_baixa) {
		this.dt_baixa = dt_baixa;
	}
	
	public String getDs_especificacao() {
		return ds_especificacao;
	}
	
	public void setDs_especificacao(String ds_especificacao) {
		this.ds_especificacao = ds_especificacao;
	}
	
	public Integer getCd_unidade() {
		return cd_unidade;
	}
	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}
}
