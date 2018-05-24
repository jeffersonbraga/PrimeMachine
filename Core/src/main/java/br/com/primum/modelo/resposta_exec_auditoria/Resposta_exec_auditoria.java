package br.com.primum.modelo.resposta_exec_auditoria;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.grupo_auditoria.Grupo_auditoria;
import br.com.primum.modelo.pergunta_auditoria.Pergunta_auditoria;
import br.com.primum.modelo.plano_acao_auditoria.Plano_acao_auditoria;
import br.com.primum.modelo.resposta_auditoria.Resposta_auditoria;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="resposta_exec_auditoria")
public class Resposta_exec_auditoria {

	@Id
	@Column
	private Integer cd_resposta_exec_auditoria;

	@Column
	private Integer cd_usuario;

	@Column
	private Integer cd_execucao_auditoria;

	@Column
	private Integer cd_pergunta_auditoria;

	@Column
	private Integer cd_resposta_auditoria;

	@Column
	private Integer cd_grupo_auditoria;

	@Column
	private String ds_justificativa;

	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.pergunta_auditoria.Pergunta_auditoria", campoBuscar="cd_pergunta_auditoria", campoComparar="cd_pergunta_auditoria")
	@Transient
	private Pergunta_auditoria pergunta_auditoria;

	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.resposta_auditoria.Resposta_auditoria", campoBuscar="cd_resposta_auditoria", campoComparar="cd_resposta_auditoria")
	@Transient
	private Resposta_auditoria resposta_auditoria;

	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.grupo_auditoria.Grupo_auditoria", campoBuscar="cd_grupo_auditoria", campoComparar="cd_grupo_auditoria")
	@Transient
	private Grupo_auditoria grupo_auditoria;
	
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.plano_acao_auditoria.Plano_acao_auditoria", campoBuscar="cd_resposta_exec_auditoria", campoComparar="cd_resposta_exec_auditoria")
	@Transient
	private Plano_acao_auditoria plano_acao_auditoria;


	public Integer getCd_resposta_exec_auditoria() {
		return cd_resposta_exec_auditoria;
	}


	public void setCd_resposta_exec_auditoria(Integer cd_resposta_exec_auditoria) {
		this.cd_resposta_exec_auditoria = cd_resposta_exec_auditoria;
	}


	public Integer getCd_usuario() {
		return cd_usuario;
	}


	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}


	public Integer getCd_execucao_auditoria() {
		return cd_execucao_auditoria;
	}


	public void setCd_execucao_auditoria(Integer cd_execucao_auditoria) {
		this.cd_execucao_auditoria = cd_execucao_auditoria;
	}


	public Integer getCd_pergunta_auditoria() {
		return cd_pergunta_auditoria;
	}


	public void setCd_pergunta_auditoria(Integer cd_pergunta_auditoria) {
		this.cd_pergunta_auditoria = cd_pergunta_auditoria;
	}


	public Integer getCd_resposta_auditoria() {
		return cd_resposta_auditoria;
	}


	public void setCd_resposta_auditoria(Integer cd_resposta_auditoria) {
		this.cd_resposta_auditoria = cd_resposta_auditoria;
	}


	public Integer getCd_grupo_auditoria() {
		return cd_grupo_auditoria;
	}


	public void setCd_grupo_auditoria(Integer cd_grupo_auditoria) {
		this.cd_grupo_auditoria = cd_grupo_auditoria;
	}


	public String getDs_justificativa() {
		return ds_justificativa;
	}


	public void setDs_justificativa(String ds_justificativa) {
		this.ds_justificativa = ds_justificativa;
	}


	public Pergunta_auditoria getPergunta_auditoria() {
		return pergunta_auditoria;
	}


	public void setPergunta_auditoria(Pergunta_auditoria pergunta_auditoria) {
		this.pergunta_auditoria = pergunta_auditoria;
	}


	public Resposta_auditoria getResposta_auditoria() {
		return resposta_auditoria;
	}


	public void setResposta_auditoria(Resposta_auditoria resposta_auditoria) {
		this.resposta_auditoria = resposta_auditoria;
	}


	public Grupo_auditoria getGrupo_auditoria() {
		return grupo_auditoria;
	}


	public void setGrupo_auditoria(Grupo_auditoria grupo_auditoria) {
		this.grupo_auditoria = grupo_auditoria;
	}


	public Plano_acao_auditoria getPlano_acao_auditoria() {
		return plano_acao_auditoria;
	}


	public void setPlano_acao_auditoria(Plano_acao_auditoria plano_acao_auditoria) {
		this.plano_acao_auditoria = plano_acao_auditoria;
	}

}