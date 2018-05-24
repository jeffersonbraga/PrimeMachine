package br.com.primum.modelo.acao_corretiva_helpdesk;

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
import br.com.primum.modelo.atividades_acao_imediata.Atividades_acao_imediata;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="acao_corretiva_helpdesk")
public class Acao_corretiva_helpdesk {

	@Id
	@Column
	private Integer cd_acao_corretiva_helpdesk;

	@Column
	private String nm_acao_corretiva_helpdesk;
	
	@Column
	private Integer cd_acao_corretiva;
	
	@Column
	private Integer cd_helpdesk_ocorrencia;
	
	@Column
	private Integer cd_responsavel_acao;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_prazo_previsto;
	
	@Column
	private Integer st_status;

	@Column
	private String ds_acao_corretiva_helpdesk;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_conclusao;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.atividades_acao_imediata.Atividades_acao_imediata", campoBuscar="cd_acao_corretiva_helpdesk", persistir=true)
	private List<Atividades_acao_imediata> listaAtividades;

	

	public Integer getCd_acao_corretiva_helpdesk() {
		return cd_acao_corretiva_helpdesk;
	}

	public void setCd_acao_corretiva_helpdesk(Integer cd_acao_corretiva_helpdesk) {
		this.cd_acao_corretiva_helpdesk = cd_acao_corretiva_helpdesk;
	}

	public String getNm_acao_corretiva_helpdesk() {
		return nm_acao_corretiva_helpdesk;
	}

	public void setNm_acao_corretiva_helpdesk(String nm_acao_corretiva_helpdesk) {
		this.nm_acao_corretiva_helpdesk = nm_acao_corretiva_helpdesk;
	}

	public Integer getCd_helpdesk_ocorrencia() {
		return cd_helpdesk_ocorrencia;
	}

	public void setCd_helpdesk_ocorrencia(Integer cd_helpdesk_ocorrencia) {
		this.cd_helpdesk_ocorrencia = cd_helpdesk_ocorrencia;
	}

	public Integer getCd_acao_corretiva() {
		return cd_acao_corretiva;
	}

	public void setCd_acao_corretiva(Integer cd_acao_corretiva) {
		this.cd_acao_corretiva = cd_acao_corretiva;
	}

	public Integer getCd_responsavel_acao() {
		return cd_responsavel_acao;
	}

	public void setCd_responsavel_acao(Integer cd_responsavel_acao) {
		this.cd_responsavel_acao = cd_responsavel_acao;
	}

	public Date getDt_prazo_previsto() {
		return dt_prazo_previsto;
	}

	public void setDt_prazo_previsto(Date dt_prazo_previsto) {
		this.dt_prazo_previsto = dt_prazo_previsto;
	}

	
	public Integer getSt_status() {
		return st_status;
	}

	public void setSt_status(Integer st_status) {
		this.st_status = st_status;
	}

	public List<Atividades_acao_imediata> getListaAtividades() {
		return listaAtividades;
	}

	public void setListaAtividades(List<Atividades_acao_imediata> listaAtividades) {
		this.listaAtividades = listaAtividades;
	}

	public String getDs_acao_corretiva_helpdesk() {
		return ds_acao_corretiva_helpdesk;
	}

	public void setDs_acao_corretiva_helpdesk(String ds_acao_corretiva_helpdesk) {
		this.ds_acao_corretiva_helpdesk = ds_acao_corretiva_helpdesk;
	}

	public Date getDt_conclusao() {
		return dt_conclusao;
	}

	public void setDt_conclusao(Date dt_conclusao) {
		this.dt_conclusao = dt_conclusao;
	}
	
	
	
}
