package br.com.primum.modelo.executar_acao_preventiva;

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
import br.com.primum.modelo.atividades_execucao_manutencao.Atividades_execucao_manutencao;
import br.com.primum.modelo.manutencao_preventiva.Manutencao_preventiva;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="executar_acao_preventiva")
public class Executar_acao_preventiva {
	
	@Id
	@Column
	private Integer cd_executar_acao_preventiva; 
	
	@Column
	private Integer cd_manutencao_preventiva;
	
	@Column
	private Integer cd_usuario;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_previsao_inicio;
	
	@Column
	private Date hr_previsao_inicio;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_execucao;
	
	@Column
	private Date hr_execucao;
	
	@Column
	private Integer st_executar_acao_preventiva;

	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.atividades_execucao_manutencao.Atividades_execucao_manutencao", persistir=true, campoBuscar="cd_executar_acao_preventiva")
	@Transient
	private List<Atividades_execucao_manutencao> listaAtividadesExecucao;
	
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.manutencao_preventiva.Manutencao_preventiva", campoBuscar="cd_manutencao_preventiva", campoComparar="cd_manutencao_preventiva")
	@Transient
	private Manutencao_preventiva manutencao_preventiva;
	
	
	public Manutencao_preventiva getManutencao_preventiva() {
		return manutencao_preventiva;
	}

	public void setManutencao_preventiva(Manutencao_preventiva manutencao_preventiva) {
		this.manutencao_preventiva = manutencao_preventiva;
	}

	public List<Atividades_execucao_manutencao> getListaAtividadesExecucao() {
		return listaAtividadesExecucao;
	}

	public void setListaAtividadesExecucao(
			List<Atividades_execucao_manutencao> listaAtividadesExecucao) {
		this.listaAtividadesExecucao = listaAtividadesExecucao;
	}

	public Integer getCd_executar_acao_preventiva() {
		return cd_executar_acao_preventiva;
	}

	public void setCd_executar_acao_preventiva(Integer cd_executar_acao_preventiva) {
		this.cd_executar_acao_preventiva = cd_executar_acao_preventiva;
	}

	public Integer getCd_manutencao_preventiva() {
		return cd_manutencao_preventiva;
	}

	public void setCd_manutencao_preventiva(Integer cd_manutencao_preventiva) {
		this.cd_manutencao_preventiva = cd_manutencao_preventiva;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Date getDt_previsao_inicio() {
		return dt_previsao_inicio;
	}

	public void setDt_previsao_inicio(Date dt_previsao_inicio) {
		this.dt_previsao_inicio = dt_previsao_inicio;
	}

	public Date getHr_previsao_inicio() {
		return hr_previsao_inicio;
	}

	public void setHr_previsao_inicio(Date hr_previsao_inicio) {
		this.hr_previsao_inicio = hr_previsao_inicio;
	}

	public Date getDt_execucao() {
		return dt_execucao;
	}

	public void setDt_execucao(Date dt_execucao) {
		this.dt_execucao = dt_execucao;
	}

	public Date getHr_execucao() {
		return hr_execucao;
	}

	public void setHr_execucao(Date hr_execucao) {
		this.hr_execucao = hr_execucao;
	}

	public Integer getSt_executar_acao_preventiva() {
		return st_executar_acao_preventiva;
	}

	public void setSt_executar_acao_preventiva(Integer st_executar_acao_preventiva) {
		this.st_executar_acao_preventiva = st_executar_acao_preventiva;
	}
	
}
