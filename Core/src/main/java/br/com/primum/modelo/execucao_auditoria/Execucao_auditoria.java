package br.com.primum.modelo.execucao_auditoria;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.planos_cronograma_auditoria.Planos_cronograma_auditoria;
import br.com.primum.modelo.resposta_exec_auditoria.Resposta_exec_auditoria;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="execucao_auditoria")
public class Execucao_auditoria {

	@Id
	@Column
	private Integer cd_execucao_auditoria;

	@Column
	private Integer cd_planos_cronograma_auditoria;

	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.resposta_exec_auditoria.Resposta_exec_auditoria", persistir=true, campoBuscar="cd_execucao_auditoria")
	@Transient
	private List<Resposta_exec_auditoria> listaRespostas;
	
//	@Transient
//	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.planos_cronograma_auditoria.Planos_cronograma_auditoria", campoBuscar="cd_planos_cronograma_auditoria", campoComparar="cd_planos_cronograma_auditoria", persistir=false)
//	private Planos_cronograma_auditoria planos_cronograma_auditoria;

	public Integer getCd_execucao_auditoria() {
		return cd_execucao_auditoria;
	}

	public void setCd_execucao_auditoria(Integer cd_execucao_auditoria) {
		this.cd_execucao_auditoria = cd_execucao_auditoria;
	}

	public Integer getCd_planos_cronograma_auditoria() {
		return cd_planos_cronograma_auditoria;
	}

	public void setCd_planos_cronograma_auditoria(
			Integer cd_planos_cronograma_auditoria) {
		this.cd_planos_cronograma_auditoria = cd_planos_cronograma_auditoria;
	}

	public List<Resposta_exec_auditoria> getListaRespostas() {
		return listaRespostas;
	}

	public void setListaRespostas(List<Resposta_exec_auditoria> listaRespostas) {
		this.listaRespostas = listaRespostas;
	}
}