package br.com.primum.modelo.manutencao_preventiva;

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
import br.com.primum.modelo.atividades_manutencao.Atividades_manutencao;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="manutencao_preventiva")
public class Manutencao_preventiva {
	
	@Id
	@Column
	private Integer cd_manutencao_preventiva;
	
	@Column
	private String nm_manutencao_preventiva;
	
	@Column
	private Integer cd_equipamento;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_inicio;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_termino;
	
	@Column
	private Integer cd_periodicidade;
	
	@Column
	private Integer tp_aviso_previo;
	
	@Column
	private Integer tp_responsavel;
	
	@Column
	private java.math.BigDecimal nr_custo_previsto;
	
	@Column
	private Integer tp_prioridade;
	
	@Column
	private Integer nr_tempo_estimado;
	
	@Column
	private Integer cd_pessoa_juridica;
	
	@Column
	private Integer cd_unidade;
	
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.atividades_manutencao.Atividades_manutencao", persistir=true, campoBuscar="cd_manutencao_preventiva")
	@Transient
	private List<Atividades_manutencao> listaAtividadesManutencao;
	
	
	public List<Atividades_manutencao> getListaAtividadesManutencao() {
		return listaAtividadesManutencao;
	}

	public void setListaAtividadesManutencao(
			List<Atividades_manutencao> listaAtividadesManutencao) {
		this.listaAtividadesManutencao = listaAtividadesManutencao;
	}

	public Integer getCd_manutencao_preventiva() {
		return cd_manutencao_preventiva;
	}

	public void setCd_manutencao_preventiva(Integer cd_manutencao_preventiva) {
		this.cd_manutencao_preventiva = cd_manutencao_preventiva;
	}

	public String getNm_manutencao_preventiva() {
		return nm_manutencao_preventiva;
	}

	public void setNm_manutencao_preventiva(String nm_manutencao_preventiva) {
		this.nm_manutencao_preventiva = nm_manutencao_preventiva;
	}

	public Integer getCd_equipamento() {
		return cd_equipamento;
	}

	public void setCd_equipamento(Integer cd_equipamento) {
		this.cd_equipamento = cd_equipamento;
	}

	public Date getDt_inicio() {
		return dt_inicio;
	}

	public void setDt_inicio(Date dt_inicio) {
		this.dt_inicio = dt_inicio;
	}

	public Date getDt_termino() {
		return dt_termino;
	}

	public void setDt_termino(Date dt_termino) {
		this.dt_termino = dt_termino;
	}

	public Integer getCd_periodicidade() {
		return cd_periodicidade;
	}

	public void setCd_periodicidade(Integer cd_periodicidade) {
		this.cd_periodicidade = cd_periodicidade;
	}

	public Integer getTp_aviso_previo() {
		return tp_aviso_previo;
	}

	public void setTp_aviso_previo(Integer tp_aviso_previo) {
		this.tp_aviso_previo = tp_aviso_previo;
	}

	public Integer getTp_responsavel() {
		return tp_responsavel;
	}

	public void setTp_responsavel(Integer tp_responsavel) {
		this.tp_responsavel = tp_responsavel;
	}

	public java.math.BigDecimal getNr_custo_previsto() {
		return nr_custo_previsto;
	}

	public void setNr_custo_previsto(java.math.BigDecimal nr_custo_previsto) {
		this.nr_custo_previsto = nr_custo_previsto;
	}

	public Integer getTp_prioridade() {
		return tp_prioridade;
	}

	public void setTp_prioridade(Integer tp_prioridade) {
		this.tp_prioridade = tp_prioridade;
	}

	public Integer getNr_tempo_estimado() {
		return nr_tempo_estimado;
	}

	public void setNr_tempo_estimado(Integer nr_tempo_estimado) {
		this.nr_tempo_estimado = nr_tempo_estimado;
	}

	public Integer getCd_pessoa_juridica() {
		return cd_pessoa_juridica;
	}

	public void setCd_pessoa_juridica(Integer cd_pessoa_juridica) {
		this.cd_pessoa_juridica = cd_pessoa_juridica;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}	
}
