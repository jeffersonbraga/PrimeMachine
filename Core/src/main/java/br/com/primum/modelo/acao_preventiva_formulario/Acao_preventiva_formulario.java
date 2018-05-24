package br.com.primum.modelo.acao_preventiva_formulario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ChaveEstrangeira;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.acao_corretiva.Acao_corretiva;
import br.com.primum.modelo.acao_preventiva.Acao_preventiva;

@XmlRootElement(name = "Acao_preventiva_formulario")
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="acao_preventiva_formulario")
public class Acao_preventiva_formulario implements Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//4707227891435577132L;

	@Id
	@Column
	private Integer cd_acao_corretiva_formulario;
	
	@Column
	private Integer cd_acao_corretiva;
	
	@ChaveEstrangeira(nomeObjetoOrigem="")
	@Column
	private Integer cd_formulario;
	
	@Transient
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.acao_preventiva.Acao_preventiva", campoBuscar="cd_acao_corretiva", campoComparar="cd_acao_corretiva")
	private Acao_preventiva acao_corretiva;

	public Integer getCd_acao_corretiva_formulario() {
		return cd_acao_corretiva_formulario;
	}

	public void setCd_acao_corretiva_formulario(Integer cd_acao_corretiva_formulario) {
		this.cd_acao_corretiva_formulario = cd_acao_corretiva_formulario;
	}

	public Integer getCd_acao_corretiva() {
		return cd_acao_corretiva;
	}

	public void setCd_acao_corretiva(Integer cd_acao_corretiva) {
		this.cd_acao_corretiva = cd_acao_corretiva;
	}

	public Integer getCd_formulario() {
		return cd_formulario;
	}

	public void setCd_formulario(Integer cd_formulario) {
		this.cd_formulario = cd_formulario;
	}

	public Acao_preventiva getAcao_corretiva() {
		return acao_corretiva;
	}

	public void setAcao_corretiva(Acao_preventiva acao_corretiva) {
		this.acao_corretiva = acao_corretiva;
	}

	
}
