package br.com.primum.modelo.planos_cronograma_auditoria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.auditoria.Auditoria;
import br.com.primum.modelo.execucao_auditoria.Execucao_auditoria;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="planos_cronograma_auditoria")
public class Planos_cronograma_auditoria {

	@Id
	@Column
	private Integer cd_planos_cronograma_auditoria;
	
	@Column
	private Integer cd_cronograma_auditoria;
	
	@Column
	private Integer cd_auditoria;
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.auditoria.Auditoria", campoBuscar="cd_auditoria", campoComparar="cd_auditoria")
	@Transient
	private Auditoria auditoria;
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.execucao_auditoria.Execucao_auditoria", campoBuscar="cd_planos_cronograma_auditoria", campoComparar="cd_planos_cronograma_auditoria")
	@Transient
	private Execucao_auditoria execucao_auditoria;

	public Integer getCd_planos_cronograma_auditoria() {
		return cd_planos_cronograma_auditoria;
	}

	public void setCd_planos_cronograma_auditoria(
			Integer cd_planos_cronograma_auditoria) {
		this.cd_planos_cronograma_auditoria = cd_planos_cronograma_auditoria;
	}

	public Integer getCd_cronograma_auditoria() {
		return cd_cronograma_auditoria;
	}

	public void setCd_cronograma_auditoria(Integer cd_cronograma_auditoria) {
		this.cd_cronograma_auditoria = cd_cronograma_auditoria;
	}

	public Integer getCd_auditoria() {
		return cd_auditoria;
	}

	public void setCd_auditoria(Integer cd_auditoria) {
		this.cd_auditoria = cd_auditoria;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public Execucao_auditoria getExecucao_auditoria() {
		return execucao_auditoria;
	}

	public void setExecucao_auditoria(Execucao_auditoria execucao_auditoria) {
		this.execucao_auditoria = execucao_auditoria;
	}
}
