package br.com.primum.modelo.acao_corretiva_formulario;

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

@XmlRootElement(name = "Acao_corretiva_formulario")
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="acao_corretiva_formulario")
public class Acao_corretiva_formulario implements Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//5855022169417478025L;

	@Id
	@Column
	private Integer cd_acao_corretiva_formulario;
	
	@Column
	private Integer cd_acao_corretiva;
	
	@ChaveEstrangeira(nomeObjetoOrigem="cd_formulario")
	@Column
	private Integer cd_formulario;
	
	@Transient
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.acao_corretiva.Acao_corretiva", campoBuscar="cd_acao_corretiva", campoComparar="cd_acao_corretiva")
	private Acao_corretiva acao_corretiva;

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

	public Acao_corretiva getAcao_corretiva() {
		return acao_corretiva;
	}

	public void setAcao_corretiva(Acao_corretiva acao_corretiva) {
		this.acao_corretiva = acao_corretiva;
	}
}
