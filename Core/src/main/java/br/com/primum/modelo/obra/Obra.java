package br.com.primum.modelo.obra;

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
@Table(name="obra")
public class Obra {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_obra;
	
	@Column
	private Integer cd_empresa;
	
	@Column
	private Integer cd_filial;
	
	@Column
	private Integer cd_usuario;
	
	@Column
	private Integer cd_cliente;
	
	@Column
	private String ds_descricao;
	
	////@Temporal(value=TemporalType.DATE)
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_datacadastro;
	
	@Column
	private Float vl_valorestimado;
	
	@Column
	private Float vl_valorreal;
	
	@Column
	private Integer st_situacao;
	
	////@Temporal(value=TemporalType.DATE)
	@Column
	private Date dt_inicio;
	
	////@Temporal(value=TemporalType.DATE)
	@Column
	private Date dt_previsaotermino;
	
	////@Temporal(value=TemporalType.DATE)
	@Column
	private Date dt_termino;
	
	@Column
	private String ds_numeroobra;
	
	@Column
	private String ds_informacoes;
	
	public Integer getCd_obra() {
		return cd_obra;
	}
	public void setCd_obra(Integer cd_obra) {
		this.cd_obra = cd_obra;
	}
	public Integer getCd_empresa() {
		return cd_empresa;
	}
	public void setCd_empresa(Integer cd_empresa) {
		this.cd_empresa = cd_empresa;
	}
	public Integer getCd_filial() {
		return cd_filial;
	}
	public void setCd_filial(Integer cd_filial) {
		this.cd_filial = cd_filial;
	}
	public Integer getCd_usuario() {
		return cd_usuario;
	}
	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}
	public Integer getCd_cliente() {
		return cd_cliente;
	}
	public void setCd_cliente(Integer cd_cliente) {
		this.cd_cliente = cd_cliente;
	}
	public String getDs_descricao() {
		return ds_descricao;
	}
	public void setDs_descricao(String ds_descricao) {
		this.ds_descricao = ds_descricao;
	}
	public Date getDt_datacadastro() {
		return dt_datacadastro;
	}
	public void setDt_datacadastro(Date dt_datacadastro) {
		this.dt_datacadastro = dt_datacadastro;
	}
	public Float getVl_valorestimado() {
		return vl_valorestimado;
	}
	public void setVl_valorestimado(Float vl_valorestimado) {
		this.vl_valorestimado = vl_valorestimado;
	}
	public Float getVl_valorreal() {
		return vl_valorreal;
	}
	public void setVl_valorreal(Float vl_valorreal) {
		this.vl_valorreal = vl_valorreal;
	}
	public Integer getSt_situacao() {
		return st_situacao;
	}
	public void setSt_situacao(Integer st_situacao) {
		this.st_situacao = st_situacao;
	}
	public Date getDt_inicio() {
		return dt_inicio;
	}
	public void setDt_inicio(Date dt_inicio) {
		this.dt_inicio = dt_inicio;
	}
	public Date getDt_previsaotermino() {
		return dt_previsaotermino;
	}
	public void setDt_previsaotermino(Date dt_previsaotermino) {
		this.dt_previsaotermino = dt_previsaotermino;
	}
	public Date getDt_termino() {
		return dt_termino;
	}
	public void setDt_termino(Date dt_termino) {
		this.dt_termino = dt_termino;
	}
	public String getDs_numeroobra() {
		return ds_numeroobra;
	}
	public void setDs_numeroobra(String ds_numeroobra) {
		this.ds_numeroobra = ds_numeroobra;
	}
	public String getDs_informacoes() {
		return ds_informacoes;
	}
	public void setDs_informacoes(String ds_informacoes) {
		this.ds_informacoes = ds_informacoes;
	}
}