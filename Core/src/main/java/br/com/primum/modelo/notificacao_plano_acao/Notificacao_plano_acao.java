package br.com.primum.modelo.notificacao_plano_acao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.plano_acao.Plano_acao;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="notificacao_plano_acao")
public class Notificacao_plano_acao {

	@Id
	@Column
	private Integer cd_notificacao_plano_acao;
	
	@Column
	private Integer cd_plano_acao;
	
	@Column
	private Integer st_exibir;
	
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.plano_acao.Plano_acao",campoBuscar="cd_plano_acao",campoComparar="cd_plano_acao", persistir=false)
	@Transient
	private Plano_acao plano;

	
	public Integer getCd_notificacao_plano_acao() {
		return cd_notificacao_plano_acao;
	}

	public void setCd_notificacao_plano_acao(Integer cd_notificacao_plano_acao) {
		this.cd_notificacao_plano_acao = cd_notificacao_plano_acao;
	}

	public Integer getCd_plano_acao() {
		return cd_plano_acao;
	}

	public void setCd_plano_acao(Integer cd_plano_acao) {
		this.cd_plano_acao = cd_plano_acao;
	}

	public Integer getSt_exibir() {
		return st_exibir;
	}

	public void setSt_exibir(Integer st_exibir) {
		this.st_exibir = st_exibir;
	}

	public Plano_acao getPlano() {
		return plano;
	}

	public void setPlano(Plano_acao plano) {
		this.plano = plano;
	}
	
	
}
