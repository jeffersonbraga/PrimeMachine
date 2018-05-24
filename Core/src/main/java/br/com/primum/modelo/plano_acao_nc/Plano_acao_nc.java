package br.com.primum.modelo.plano_acao_nc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.plano_acao.Plano_acao;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="plano_acao_nc")
public class Plano_acao_nc implements Serializable {
	
	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//5520134598376388301L;

	@Id
	@Column
	private Integer cd_plano_acao_nc;

	@Column
	private Integer cd_plano_acao_origem;
	
	@Column
	private Integer cd_plano_acao_executar;

	
	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.plano_acao.Plano_acao",persistir=false, campoBuscar="cd_plano_acao", campoComparar="cd_plano_acao_executar")
	private Plano_acao plano_acao_nc_executar;

	public Plano_acao getPlano_acao_nc_executar() {
		return plano_acao_nc_executar;
	}

	public void setPlano_acao_nc_executar(Plano_acao plano_acao_nc_executar) {
		this.plano_acao_nc_executar = plano_acao_nc_executar;
	}

	public Integer getCd_plano_acao_nc() {
		return cd_plano_acao_nc;
	}

	public void setCd_plano_acao_nc(Integer cd_plano_acao_nc) {
		this.cd_plano_acao_nc = cd_plano_acao_nc;
	}

	public Integer getCd_plano_acao_origem() {
		return cd_plano_acao_origem;
	}

	public void setCd_plano_acao_origem(Integer cd_plano_acao_origem) {
		this.cd_plano_acao_origem = cd_plano_acao_origem;
	}

	public Integer getCd_plano_acao_executar() {
		return cd_plano_acao_executar;
	}

	public void setCd_plano_acao_executar(Integer cd_plano_acao_executar) {
		this.cd_plano_acao_executar = cd_plano_acao_executar;
	}
}
