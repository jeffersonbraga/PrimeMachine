package br.com.primum.modelo.cronograma_auditoria;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.classificacao_auditoria.Classificacao_auditoria;
import br.com.primum.modelo.equipe_cronograma_auditoria.Equipe_cronograma_auditoria;
import br.com.primum.modelo.planos_cronograma_auditoria.Planos_cronograma_auditoria;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="cronograma_auditoria")
public class Cronograma_auditoria {
	
	@Id
	@Column
	private Integer cd_cronograma_auditoria;

	@Column
	private Integer cd_classificacao_auditoria;

	@Column
	private Integer cd_usuario;

	@Column
	private Integer cd_pessoa_juridica;

	@Column
	private String nm_cronograma_auditoria;

	@Column
	private String nm_responsavel_tecnico;

	@Column
	private String nm_responsavel_acompanhamento;

	////@Temporal(value=TemporalType.DATE)
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_cronograma_auditoria_inicio;

	////@Temporal(value=TemporalType.DATE)
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_cronograma_auditoria_fim;

	@Column
	private Integer st_cronograma_auditoria;

	@Column
	private String ds_cronograma_auditoria;

	@Column
	private Integer tp_cronograma_auditoria;
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.classificacao_auditoria.Classificacao_auditoria", campoBuscar="cd_classificacao_auditoria", campoComparar="cd_classificacao_auditoria")
	@Transient
	private Classificacao_auditoria classificacao_auditoria;
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.planos_cronograma_auditoria.Planos_cronograma_auditoria", persistir=true, campoBuscar="cd_cronograma_auditoria")
	@Transient
	private List<Planos_cronograma_auditoria> listaPlanoAuditoria;
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.equipe_cronograma_auditoria.Equipe_cronograma_auditoria", persistir=true, campoBuscar="cd_cronograma_auditoria")
	@Transient
	private List<Equipe_cronograma_auditoria> listaEquipeAuditoria;

	public Integer getCd_cronograma_auditoria() {
		return cd_cronograma_auditoria;
	}

	public void setCd_cronograma_auditoria(Integer cd_cronograma_auditoria) {
		this.cd_cronograma_auditoria = cd_cronograma_auditoria;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public String getNm_cronograma_auditoria() {
		return nm_cronograma_auditoria;
	}

	public void setNm_cronograma_auditoria(String nm_cronograma_auditoria) {
		this.nm_cronograma_auditoria = nm_cronograma_auditoria;
	}

	public Date getDt_cronograma_auditoria_inicio() {
		return dt_cronograma_auditoria_inicio;
	}

	public void setDt_cronograma_auditoria_inicio(
			Date dt_cronograma_auditoria_inicio) {
		this.dt_cronograma_auditoria_inicio = dt_cronograma_auditoria_inicio;
	}

	public Date getDt_cronograma_auditoria_fim() {
		return dt_cronograma_auditoria_fim;
	}

	public void setDt_cronograma_auditoria_fim(Date dt_cronograma_auditoria_fim) {
		this.dt_cronograma_auditoria_fim = dt_cronograma_auditoria_fim;
	}

	public Integer getSt_cronograma_auditoria() {
		return st_cronograma_auditoria;
	}

	public void setSt_cronograma_auditoria(Integer st_cronograma_auditoria) {
		this.st_cronograma_auditoria = st_cronograma_auditoria;
	}

	public String getDs_cronograma_auditoria() {
		return ds_cronograma_auditoria;
	}

	public void setDs_cronograma_auditoria(String ds_cronograma_auditoria) {
		this.ds_cronograma_auditoria = ds_cronograma_auditoria;
	}

	public List<Planos_cronograma_auditoria> getListaPlanoAuditoria() {
		return listaPlanoAuditoria;
	}

	public void setListaPlanoAuditoria(
			List<Planos_cronograma_auditoria> listaPlanoAuditoria) {
		this.listaPlanoAuditoria = listaPlanoAuditoria;
	}

	public List<Equipe_cronograma_auditoria> getListaEquipeAuditoria() {
		return listaEquipeAuditoria;
	}

	public void setListaEquipeAuditoria(
			List<Equipe_cronograma_auditoria> listaEquipeAuditoria) {
		this.listaEquipeAuditoria = listaEquipeAuditoria;
	}

	public Integer getTp_cronograma_auditoria() {
		return tp_cronograma_auditoria;
	}

	public void setTp_cronograma_auditoria(Integer tp_cronograma_auditoria) {
		this.tp_cronograma_auditoria = tp_cronograma_auditoria;
	}

	public Integer getCd_pessoa_juridica() {
		return cd_pessoa_juridica;
	}

	public void setCd_pessoa_juridica(Integer cd_pessoa_juridica) {
		this.cd_pessoa_juridica = cd_pessoa_juridica;
	}

	public String getNm_responsavel_tecnico() {
		return nm_responsavel_tecnico;
	}

	public void setNm_responsavel_tecnico(String nm_responsavel_tecnico) {
		this.nm_responsavel_tecnico = nm_responsavel_tecnico;
	}

	public String getNm_responsavel_acompanhamento() {
		return nm_responsavel_acompanhamento;
	}

	public void setNm_responsavel_acompanhamento(
			String nm_responsavel_acompanhamento) {
		this.nm_responsavel_acompanhamento = nm_responsavel_acompanhamento;
	}

	public Integer getCd_classificacao_auditoria() {
		return cd_classificacao_auditoria;
	}

	public void setCd_classificacao_auditoria(Integer cd_classificacao_auditoria) {
		this.cd_classificacao_auditoria = cd_classificacao_auditoria;
	}

	public Classificacao_auditoria getClassificacao_auditoria() {
		return classificacao_auditoria;
	}

	public void setClassificacao_auditoria(
			Classificacao_auditoria classificacao_auditoria) {
		this.classificacao_auditoria = classificacao_auditoria;
	}
}