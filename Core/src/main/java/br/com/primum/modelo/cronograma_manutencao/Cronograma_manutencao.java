package br.com.primum.modelo.cronograma_manutencao;

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
import br.com.primum.modelo.atividades_cronograma_manutencao.Atividades_cronograma_manutencao;
import br.com.primum.modelo.manutencao_preventiva.Manutencao_preventiva;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="cronograma_manutencao")
public class Cronograma_manutencao {
	
	@Id
	@Column
	private Integer cd_cronograma_manutencao; 
	
	@Column
	private Integer cd_manutencao_preventiva;
	
	@Column
	private Integer cd_usuario;
	
	@Column
	private Integer cd_responsavel_verificacao;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_previsao_inicio;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date hr_previsao_inicio;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_execucao;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date hr_execucao;
	
	@Column
	private Integer st_cronograma_manutencao;
	
	@Column
	private Integer cd_unidade;
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.atividades_cronograma_manutencao.Atividades_cronograma_manutencao", persistir=true, campoBuscar="cd_cronograma_manutencao")
	@Transient
	private List<Atividades_cronograma_manutencao> listaAtividadesExecucao;
	
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.manutencao_preventiva.Manutencao_preventiva", campoBuscar="cd_manutencao_preventiva", campoComparar="cd_manutencao_preventiva")
	@Transient
	private Manutencao_preventiva manutencao_preventiva;


	public Integer getCd_responsavel_verificacao() {
		return cd_responsavel_verificacao;
	}


	public void setCd_responsavel_verificacao(Integer cd_responsavel_verificacao) {
		this.cd_responsavel_verificacao = cd_responsavel_verificacao;
	}


	public Integer getCd_cronograma_manutencao() {
		return cd_cronograma_manutencao;
	}


	public void setCd_cronograma_manutencao(Integer cd_cronograma_manutencao) {
		this.cd_cronograma_manutencao = cd_cronograma_manutencao;
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


	public Integer getSt_cronograma_manutencao() {
		return st_cronograma_manutencao;
	}


	public void setSt_cronograma_manutencao(Integer st_cronograma_manutencao) {
		this.st_cronograma_manutencao = st_cronograma_manutencao;
	}


	public List<Atividades_cronograma_manutencao> getListaAtividadesExecucao() {
		return listaAtividadesExecucao;
	}


	public void setListaAtividadesExecucao(
			List<Atividades_cronograma_manutencao> listaAtividadesExecucao) {
		this.listaAtividadesExecucao = listaAtividadesExecucao;
	}


	public Manutencao_preventiva getManutencao_preventiva() {
		return manutencao_preventiva;
	}


	public void setManutencao_preventiva(Manutencao_preventiva manutencao_preventiva) {
		this.manutencao_preventiva = manutencao_preventiva;
	}


	public Integer getCd_unidade() {
		return cd_unidade;
	}


	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}	
}
