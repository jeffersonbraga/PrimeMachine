package br.com.primum.modelo.acao_plano_acao_hp;

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
import br.com.primum.modelo.acao_corretiva.Acao_corretiva;
import br.com.primum.modelo.atividade_acao_pl_hp.Atividade_acao_pl_hp;

/**
 * @author Usuario
 *
 */
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="acao_plano_acao_hp")
public class Acao_plano_acao_hp {

	
	@Id
	@Column
	private Integer cd_acao_plano_acao_hp;
	
	@Column
	private Integer cd_plano_acao_hp;
	
	@Column
	private String ds_acao_corretiva_plano_hp;
	
	@Column
	private String ds_comentario;
	
	@Column
	private String ds_caminho_evidencia;
	
	@Column
	private Integer cd_acao_corretiva;
	
	@Column
	private Integer vl_prioridade;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_prazo_previsto;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_realizado;
	
	@Column
	private Integer cd_usuario_responsavel;
	
	@Column
	private Integer st_acao_plano_acao_hp;
	
	@Transient
	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.acao_corretiva.Acao_corretiva", campoBuscar="cd_acao_corretiva", campoComparar="cd_acao_corretiva", persistir=false, notificador=false)
	private Acao_corretiva acao_corretiva;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.atividade_acao_pl_hp.Atividade_acao_pl_hp", campoBuscar="cd_acao_plano_acao_hp", persistir=true)
	private List<Atividade_acao_pl_hp> listaAtividades;
	
	
	public String getDs_caminho_evidencia() {
		return ds_caminho_evidencia;
	}
	public void setDs_caminho_evidencia(String ds_caminho_evidencia) {
		this.ds_caminho_evidencia = ds_caminho_evidencia;
	}
	public String getDs_comentario() {
		return ds_comentario;
	}
	public void setDs_comentario(String ds_comentario) {
		this.ds_comentario = ds_comentario;
	}
	public Integer getCd_acao_plano_acao_hp() {
		return cd_acao_plano_acao_hp;
	}
	public void setCd_acao_plano_acao_hp(Integer cd_acao_plano_acao_hp) {
		this.cd_acao_plano_acao_hp = cd_acao_plano_acao_hp;
	}
	public Integer getCd_plano_acao_hp() {
		return cd_plano_acao_hp;
	}
	public void setCd_plano_acao_hp(Integer cd_plano_acao_hp) {
		this.cd_plano_acao_hp = cd_plano_acao_hp;
	}
	public Integer getCd_acao_corretiva() {
		return cd_acao_corretiva;
	}
	public void setCd_acao_corretiva(Integer cd_acao_corretiva) {
		this.cd_acao_corretiva = cd_acao_corretiva;
	}
	public Integer getVl_prioridade() {
		return vl_prioridade;
	}
	public void setVl_prioridade(Integer vl_prioridade) {
		this.vl_prioridade = vl_prioridade;
	}
	public Date getDt_prazo_previsto() {
		return dt_prazo_previsto;
	}
	public void setDt_prazo_previsto(Date dt_prazo_previsto) {
		this.dt_prazo_previsto = dt_prazo_previsto;
	}
	public Date getDt_realizado() {
		return dt_realizado;
	}
	public void setDt_realizado(Date dt_realizado) {
		this.dt_realizado = dt_realizado;
	}
	public Integer getCd_usuario_responsavel() {
		return cd_usuario_responsavel;
	}
	public void setCd_usuario_responsavel(Integer cd_usuario_responsavel) {
		this.cd_usuario_responsavel = cd_usuario_responsavel;
	}
	public Integer getSt_acao_plano_acao_hp() {
		return st_acao_plano_acao_hp;
	}
	public void setSt_acao_plano_acao_hp(Integer st_acao_plano_acao_hp) {
		this.st_acao_plano_acao_hp = st_acao_plano_acao_hp;
	}
	public List<Atividade_acao_pl_hp> getListaAtividades() {
		return listaAtividades;
	}
	public void setListaAtividades(List<Atividade_acao_pl_hp> listaAtividades) {
		this.listaAtividades = listaAtividades;
	}
	public Acao_corretiva getAcao_corretiva() {
		return acao_corretiva;
	}
	public void setAcao_corretiva(Acao_corretiva acao_corretiva) {
		this.acao_corretiva = acao_corretiva;
	}
	public String getDs_acao_corretiva_plano_hp() {
		return ds_acao_corretiva_plano_hp;
	}
	public void setDs_acao_corretiva_plano_hp(String ds_acao_corretiva_plano_hp) {
		this.ds_acao_corretiva_plano_hp = ds_acao_corretiva_plano_hp;
	}
	
}
