package br.com.primum.modelo.ocorrencia_ouvidoria;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="ocorrencia_ouvidoria")
public class Ocorrencia_ouvidoria {

	@Id
	@Column
	private Integer cd_ocorrencia_ouvidoria;
	
	@Column
	private String nm_ocorrencia_ouvidoria;
	
	@Column
	private String nm_responsavel_abrir;
	
	@Column
	private String ds_email;
	
	@Column
	private String nm_ouvidoria;
	
	@Column
	private String nr_fone;
	
	@Column
	private Integer tp_canal_comunicacao;
	
	@Column
	private String ds_ocorrencia_ouvidoria;
	
	@Column
	private Integer cd_usuario;
	
	@Column
	private String ds_lote_identificacao;
	
	@Column
	private String st_ocorrencia_ouvidoria;
	
	@Column
	private Integer cd_classificacao_ouvidoria;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_ocorrencia_ouvidoria;
	
	@Column
	private Integer cd_setor;

	@Column
	private Integer vl_gravidade;
	
	@Column
	private Integer vl_urgencia;
	
	@Column
	private Integer vl_tendencia;
	
	@Column
	private Integer vl_matrizgut;
	
	@Column
	private Integer cd_unidade;

	public Date getDt_ocorrencia_ouvidoria() {
		return dt_ocorrencia_ouvidoria;
	}

	public void setDt_ocorrencia_ouvidoria(Date dt_ocorrencia_ouvidoria) {
		this.dt_ocorrencia_ouvidoria = dt_ocorrencia_ouvidoria;
	}


	public Integer getVl_matrizgut() {
		return vl_matrizgut;
	}

	public void setVl_matrizgut(Integer vl_matrizgut) {
		this.vl_matrizgut = vl_matrizgut;
	}

	public Integer getVl_gravidade() {
		return vl_gravidade;
	}

	public void setVl_gravidade(Integer vl_gravidade) {
		this.vl_gravidade = vl_gravidade;
	}

	public Integer getVl_urgencia() {
		return vl_urgencia;
	}

	public void setVl_urgencia(Integer vl_urgencia) {
		this.vl_urgencia = vl_urgencia;
	}

	public Integer getVl_tendencia() {
		return vl_tendencia;
	}

	public void setVl_tendencia(Integer vl_tendencia) {
		this.vl_tendencia = vl_tendencia;
	}

	public Integer getCd_ocorrencia_ouvidoria() {
		return cd_ocorrencia_ouvidoria;
	}

	public void setCd_ocorrencia_ouvidoria(Integer cd_ocorrencia_ouvidoria) {
		this.cd_ocorrencia_ouvidoria = cd_ocorrencia_ouvidoria;
	}

	public String getNm_ocorrencia_ouvidoria() {
		return nm_ocorrencia_ouvidoria;
	}

	public void setNm_ocorrencia_ouvidoria(String nm_ocorrencia_ouvidoria) {
		this.nm_ocorrencia_ouvidoria = nm_ocorrencia_ouvidoria;
	}

	public String getNm_responsavel_abrir() {
		return nm_responsavel_abrir;
	}

	public void setNm_responsavel_abrir(String nm_responsavel_abrir) {
		this.nm_responsavel_abrir = nm_responsavel_abrir;
	}

	public String getDs_email() {
		return ds_email;
	}

	public void setDs_email(String ds_email) {
		this.ds_email = ds_email;
	}

	public String getNr_fone() {
		return nr_fone;
	}

	public void setNr_fone(String nr_fone) {
		this.nr_fone = nr_fone;
	}

	public Integer getTp_canal_comunicacao() {
		return tp_canal_comunicacao;
	}

	public void setTp_canal_comunicacao(Integer tp_canal_comunicacao) {
		this.tp_canal_comunicacao = tp_canal_comunicacao;
	}

	public String getDs_ocorrencia_ouvidoria() {
		return ds_ocorrencia_ouvidoria;
	}

	public void setDs_ocorrencia_ouvidoria(String ds_ocorrencia_ouvidoria) {
		this.ds_ocorrencia_ouvidoria = ds_ocorrencia_ouvidoria;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public String getDs_lote_identificacao() {
		return ds_lote_identificacao;
	}

	public void setDs_lote_identificacao(String ds_lote_identificacao) {
		this.ds_lote_identificacao = ds_lote_identificacao;
	}

	public String getSt_ocorrencia_ouvidoria() {
		return st_ocorrencia_ouvidoria;
	}

	public void setSt_ocorrencia_ouvidoria(String st_ocorrencia_ouvidoria) {
		this.st_ocorrencia_ouvidoria = st_ocorrencia_ouvidoria;
	}

	public Integer getCd_classificacao_ouvidoria() {
		return cd_classificacao_ouvidoria;
	}

	public void setCd_classificacao_ouvidoria(Integer cd_classificacao_ouvidoria) {
		this.cd_classificacao_ouvidoria = cd_classificacao_ouvidoria;
	}

	public Integer getCd_setor() {
		return cd_setor;
	}

	public void setCd_setor(Integer cd_setor) {
		this.cd_setor = cd_setor;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}

	public String getNm_ouvidoria() {
		return nm_ouvidoria;
	}

	public void setNm_ouvidoria(String nm_ouvidoria) {
		this.nm_ouvidoria = nm_ouvidoria;
	}
}
