package br.com.primum.modelo.config_campo_formulario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ChaveEstrangeira;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="config_campo_formulario")
public class Config_campo_formulario implements Serializable {

	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//1860775494792837506L;

	@Id
	@Column
	private Integer cd_config_campo_formulario;

	@ChaveEstrangeira(nomeObjetoOrigem="cd_campo_formulario")
	@Column
	private Integer cd_campo_formulario;

	@Column
	private Integer cd_condicao;
	
	@Column
	private Integer cd_dbsearch;
	
	@Column
	private Integer cd_acao_corretiva;
	
	@Column
	private Integer cd_acao_preventiva;
	
	@Column
	private String vl_config_campo_formulario;

	public Integer getCd_config_campo_formulario() {
		return cd_config_campo_formulario;
	}

	public void setCd_config_campo_formulario(Integer cd_config_campo_formulario) {
		this.cd_config_campo_formulario = cd_config_campo_formulario;
	}

	public Integer getCd_campo_formulario() {
		return cd_campo_formulario;
	}

	public void setCd_campo_formulario(Integer cd_campo_formulario) {
		this.cd_campo_formulario = cd_campo_formulario;
	}

	public Integer getCd_condicao() {
		return cd_condicao;
	}

	public void setCd_condicao(Integer cd_condicao) {
		this.cd_condicao = cd_condicao;
	}

	public String getVl_config_campo_formulario() {
		return vl_config_campo_formulario;
	}

	public void setVl_config_campo_formulario(String vl_config_campo_formulario) {
		this.vl_config_campo_formulario = vl_config_campo_formulario;
	}

	public Integer getCd_acao_corretiva() {
		return cd_acao_corretiva;
	}

	public void setCd_acao_corretiva(Integer cd_acao_corretiva) {
		this.cd_acao_corretiva = cd_acao_corretiva;
	}

	public Integer getCd_acao_preventiva() {
		return cd_acao_preventiva;
	}

	public void setCd_acao_preventiva(Integer cd_acao_preventiva) {
		this.cd_acao_preventiva = cd_acao_preventiva;
	}

	public Integer getCd_dbsearch() {
		return cd_dbsearch;
	}

	public void setCd_dbsearch(Integer cd_dbsearch) {
		this.cd_dbsearch = cd_dbsearch;
	}
}