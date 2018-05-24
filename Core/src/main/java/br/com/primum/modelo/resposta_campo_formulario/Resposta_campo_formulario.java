package br.com.primum.modelo.resposta_campo_formulario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ChaveEstrangeira;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="resposta_campo_formulario")
public class Resposta_campo_formulario implements Serializable {

	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//2226543118876790444L;

	@Id
	@Column
	private Integer cd_resposta_campo_formulario;

	@ChaveEstrangeira(nomeObjetoOrigem="cd_campo_formulario")
	@Column
	private Integer cd_campo_formulario;

	@Column
	private Integer tp_resposta_campo_formulario;

	@Column
	private String ds_resposta_campo_formulario;

	@Column
	private Integer vl_resposta_campo_formulario;

	@Column
	private Integer cd_acao_corretiva;

	@Column
	private Integer cd_acao_preventiva;

	@Column
	private Integer cd_escala_tempo;

	@Column
	private Integer cd_campo_inicio;

	@Column
	private Integer cd_campo_final;

	public Integer getCd_resposta_campo_formulario() {
		return cd_resposta_campo_formulario;
	}

	public void setCd_resposta_campo_formulario(Integer cd_resposta_campo_formulario) {
		this.cd_resposta_campo_formulario = cd_resposta_campo_formulario;
	}

	public Integer getCd_campo_formulario() {
		return cd_campo_formulario;
	}

	public void setCd_campo_formulario(Integer cd_campo_formulario) {
		this.cd_campo_formulario = cd_campo_formulario;
	}

	public Integer getTp_resposta_campo_formulario() {
		return tp_resposta_campo_formulario;
	}

	public void setTp_resposta_campo_formulario(Integer tp_resposta_campo_formulario) {
		this.tp_resposta_campo_formulario = tp_resposta_campo_formulario;
	}

	public String getDs_resposta_campo_formulario() {
		return ds_resposta_campo_formulario;
	}

	public void setDs_resposta_campo_formulario(String ds_resposta_campo_formulario) {
		this.ds_resposta_campo_formulario = ds_resposta_campo_formulario;
	}

	public Integer getCd_acao_corretiva() {
		return cd_acao_corretiva;
	}

	public void setCd_acao_corretiva(Integer cd_acao_corretiva) {
		this.cd_acao_corretiva = cd_acao_corretiva;
	}

	public Integer getCd_escala_tempo() {
		return cd_escala_tempo;
	}

	public void setCd_escala_tempo(Integer cd_escala_tempo) {
		this.cd_escala_tempo = cd_escala_tempo;
	}

	public Integer getCd_campo_inicio() {
		return cd_campo_inicio;
	}

	public void setCd_campo_inicio(Integer cd_campo_inicio) {
		this.cd_campo_inicio = cd_campo_inicio;
	}

	public Integer getCd_campo_final() {
		return cd_campo_final;
	}

	public void setCd_campo_final(Integer cd_campo_final) {
		this.cd_campo_final = cd_campo_final;
	}

	public Integer getVl_resposta_campo_formulario() {
		return vl_resposta_campo_formulario;
	}

	public void setVl_resposta_campo_formulario(Integer vl_resposta_campo_formulario) {
		this.vl_resposta_campo_formulario = vl_resposta_campo_formulario;
	}

	public Integer getCd_acao_preventiva() {
		return cd_acao_preventiva;
	}

	public void setCd_acao_preventiva(Integer cd_acao_preventiva) {
		this.cd_acao_preventiva = cd_acao_preventiva;
	}

	@Override
	public String toString() {
		return this.getCd_resposta_campo_formulario() + " - " + this.getDs_resposta_campo_formulario();
	}
}