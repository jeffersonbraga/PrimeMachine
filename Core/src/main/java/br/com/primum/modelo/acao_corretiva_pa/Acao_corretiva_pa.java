package br.com.primum.modelo.acao_corretiva_pa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.acao_corretiva.Acao_corretiva;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="acao_corretiva_pa")
public class Acao_corretiva_pa implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//1583746331891861281L;

	@Id
	@Column
	private Integer cd_acao_corretiva_pa;
	
	@Column
	private Integer cd_acao_corretiva;
	
	@Column
	private Integer cd_plano_acao;

	@Transient
	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.acao_corretiva.Acao_corretiva", campoBuscar="cd_acao_corretiva", campoComparar="cd_acao_corretiva", persistir=false, notificador=false)
	private Acao_corretiva acao_corretiva;
	

	public Acao_corretiva getAcao_corretiva() {
		return acao_corretiva;
	}

	public void setAcao_corretiva(Acao_corretiva acao_corretiva) {
		this.acao_corretiva = acao_corretiva;
	}

	public Integer getCd_acao_corretiva_pa() {
		return cd_acao_corretiva_pa;
	}

	public void setCd_acao_corretiva_pa(Integer cd_acao_corretiva_pa) {
		this.cd_acao_corretiva_pa = cd_acao_corretiva_pa;
	}

	public Integer getCd_acao_corretiva() {
		return cd_acao_corretiva;
	}

	public void setCd_acao_corretiva(Integer cd_acao_corretiva) {
		this.cd_acao_corretiva = cd_acao_corretiva;
	}

	public Integer getCd_plano_acao() {
		return cd_plano_acao;
	}

	public void setCd_plano_acao(Integer cd_plano_acao) {
		this.cd_plano_acao = cd_plano_acao;
	}

	
}
