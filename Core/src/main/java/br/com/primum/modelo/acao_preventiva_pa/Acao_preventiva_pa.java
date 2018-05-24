package br.com.primum.modelo.acao_preventiva_pa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.acao_corretiva.Acao_corretiva;
import br.com.primum.modelo.acao_preventiva.Acao_preventiva;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="acao_preventiva_pa")
public class Acao_preventiva_pa implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-1490559786183619159L;

	@Id
	@Column
	private Integer cd_acao_preventiva_pa;
	
	@Column
	private Integer cd_acao_preventiva;
	
	@Column
	private Integer cd_plano_acao;

	@Transient
	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.acao_preventiva.Acao_preventiva", campoBuscar="cd_acao_corretiva", campoComparar="cd_acao_preventiva", persistir=false, notificador=false)
	private Acao_preventiva acao_preventiva;
	
	
	
	public Acao_preventiva getAcao_preventiva() {
		return acao_preventiva;
	}

	public void setAcao_preventiva(Acao_preventiva acao_preventiva) {
		this.acao_preventiva = acao_preventiva;
	}

	public Integer getCd_acao_preventiva_pa() {
		return cd_acao_preventiva_pa;
	}

	public void setCd_acao_preventiva_pa(Integer cd_acao_preventiva_pa) {
		this.cd_acao_preventiva_pa = cd_acao_preventiva_pa;
	}

	public Integer getCd_acao_preventiva() {
		return cd_acao_preventiva;
	}

	public void setCd_acao_preventiva(Integer cd_acao_preventiva) {
		this.cd_acao_preventiva = cd_acao_preventiva;
	}

	public Integer getCd_plano_acao() {
		return cd_plano_acao;
	}

	public void setCd_plano_acao(Integer cd_plano_acao) {
		this.cd_plano_acao = cd_plano_acao;
	}
	
}
