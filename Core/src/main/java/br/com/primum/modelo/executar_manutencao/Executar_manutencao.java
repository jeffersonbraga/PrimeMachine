package br.com.primum.modelo.executar_manutencao;

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
import br.com.primum.modelo.atv_exec_manutencao.Atv_exec_manutencao;
import br.com.primum.modelo.cronograma_manutencao.Cronograma_manutencao;
import br.com.primum.modelo.equipamento.Equipamento;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="executar_manutencao")
public class Executar_manutencao {

	@Id
	@Column
	private Integer cd_executar_manutencao;
	
	@Column
	private Integer cd_cronograma_manutencao;

	@Column
	private Integer cd_equipamento;

	@Column
	private Integer nr_tempo_previsto;

	@Column
	private Integer st_executar_manutencao;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_verificacao;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_inicio_execucao;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_termino_execucao;
	
	@Column
	private Integer cd_usuario_verificacao;
	
	@Column
	private Integer tp_parecer_tecnico;
	
	@Transient
	private String nm_resp_execucao;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.atv_exec_manutencao.Atv_exec_manutencao", persistir=true, campoBuscar="cd_executar_manutencao")
	private List<Atv_exec_manutencao> listaAtividadesExtras;

//	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.atividade_execucao_manutencao.Atividade_execucao_manutencao", campoBuscar="cd_atividade_execucao_manutencao", campoComparar="cd_atividade_execucao_manutencao")
//	@Transient
//	private Atividade_execucao_manutencao atividade_execucao_manutencao;
	
	@Transient
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.equipamento.Equipamento", campoBuscar="cd_equipamento", campoComparar="cd_equipamento")
	private Equipamento equipamento;
	
	
	@Transient
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.cronograma_manutencao.Cronograma_manutencao", campoBuscar="cd_cronograma_manutencao", campoComparar="cd_cronograma_manutencao")
	private Cronograma_manutencao cronograma_manutencao;
	
	
	
	public String getNm_resp_execucao() {
		return nm_resp_execucao;
	}

	public void setNm_resp_execucao(String nm_resp_execucao) {
		this.nm_resp_execucao = nm_resp_execucao;
	}

	public Date getDt_inicio_execucao() {
		return dt_inicio_execucao;
	}

	public void setDt_inicio_execucao(Date dt_inicio_execucao) {
		this.dt_inicio_execucao = dt_inicio_execucao;
	}

	public Date getDt_termino_execucao() {
		return dt_termino_execucao;
	}

	public void setDt_termino_execucao(Date dt_termino_execucao) {
		this.dt_termino_execucao = dt_termino_execucao;
	}

	public Date getDt_verificacao() {
		return dt_verificacao;
	}

	public void setDt_verificacao(Date dt_verificacao) {
		this.dt_verificacao = dt_verificacao;
	}

	public Integer getCd_usuario_verificacao() {
		return cd_usuario_verificacao;
	}

	public void setCd_usuario_verificacao(Integer cd_usuario_verificacao) {
		this.cd_usuario_verificacao = cd_usuario_verificacao;
	}

	public Integer getTp_parecer_tecnico() {
		return tp_parecer_tecnico;
	}

	public void setTp_parecer_tecnico(Integer tp_parecer_tecnico) {
		this.tp_parecer_tecnico = tp_parecer_tecnico;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Cronograma_manutencao getCronograma_manutencao() {
		return cronograma_manutencao;
	}

	public void setCronograma_manutencao(Cronograma_manutencao cronograma_manutencao) {
		this.cronograma_manutencao = cronograma_manutencao;
	}

//	public Atividade_execucao_manutencao getAtividade_execucao_manutencao() {
//		return atividade_execucao_manutencao;
//	}
//
//	public void setAtividade_execucao_manutencao(
//			Atividade_execucao_manutencao atividade_execucao_manutencao) {
//		this.atividade_execucao_manutencao = atividade_execucao_manutencao;
//	}

	public Integer getCd_executar_manutencao() {
		return cd_executar_manutencao;
	}

	public void setCd_executar_manutencao(Integer cd_executar_manutencao) {
		this.cd_executar_manutencao = cd_executar_manutencao;
	}

	public Integer getCd_cronograma_manutencao() {
		return cd_cronograma_manutencao;
	}

	public void setCd_cronograma_manutencao(Integer cd_cronograma_manutencao) {
		this.cd_cronograma_manutencao = cd_cronograma_manutencao;
	}

	public Integer getCd_equipamento() {
		return cd_equipamento;
	}

	public void setCd_equipamento(Integer cd_equipamento) {
		this.cd_equipamento = cd_equipamento;
	}

	public Integer getNr_tempo_previsto() {
		return nr_tempo_previsto;
	}

	public void setNr_tempo_previsto(Integer nr_tempo_previsto) {
		this.nr_tempo_previsto = nr_tempo_previsto;
	}

	public Integer getSt_executar_manutencao() {
		return st_executar_manutencao;
	}

	public void setSt_executar_manutencao(Integer st_executar_manutencao) {
		this.st_executar_manutencao = st_executar_manutencao;
	}

	public List<Atv_exec_manutencao> getListaAtividadesExtras() {
		return listaAtividadesExtras;
	}

	public void setListaAtividadesExtras(
			List<Atv_exec_manutencao> listaAtividadesExtras) {
		this.listaAtividadesExtras = listaAtividadesExtras;
	}


}
