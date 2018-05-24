package br.com.primum.modelo.atividade;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;

/**
 */
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(catalog = "gestor", name = "atividade")
public class Atividade implements Serializable {
	//private static final long serialVersionUID = ContextoAplicao.SERIAL_PADRAO;// 1L;//1L;

	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//1338771836856731534L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "cd_atividade", nullable = false)
	private Integer cd_atividade;

	@Column(name = "cd_projeto", nullable = false)
	private Integer cd_projeto;

	@Column(name = "cd_usuario_desenvolvimento", nullable = false)
	private Integer cd_usuario_desenvolvimento;

	@Column(name = "cd_usuario_teste", nullable = false)
	private Integer cd_usuario_teste;

	@Column(name = "ds_atividade", columnDefinition = "TEXT")
	private String ds_atividade;

	////@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name = "dt_criacao")
	//@Temporal(value = TemporalType.DATE)
	private Calendar dt_criacao;

	@Column(name = "ds_protocolo", length = 12)
	private String ds_protocolo;

	@Column(name = "nr_tempoestimado")
	private Integer nr_tempoestimado;

	@Column(name = "st_atividade")
	private Integer st_atividade;

	public Integer getCd_atividade() {
		return cd_atividade;
	}

	public void setCd_atividade(Integer cd_atividade) {
		this.cd_atividade = cd_atividade;
	}

	public Integer getCd_projeto() {
		return cd_projeto;
	}

	public void setCd_projeto(Integer cd_projeto) {
		this.cd_projeto = cd_projeto;
	}

	public Integer getCd_usuario_desenvolvimento() {
		return cd_usuario_desenvolvimento;
	}

	public void setCd_usuario_desenvolvimento(Integer cd_usuario_desenvolvimento) {
		this.cd_usuario_desenvolvimento = cd_usuario_desenvolvimento;
	}

	public Integer getCd_usuario_teste() {
		return cd_usuario_teste;
	}

	public void setCd_usuario_teste(Integer cd_usuario_teste) {
		this.cd_usuario_teste = cd_usuario_teste;
	}

	public String getDs_atividade() {
		return ds_atividade;
	}

	public void setDs_atividade(String ds_atividade) {
		this.ds_atividade = ds_atividade;
	}

	public Calendar getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(Calendar dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public String getDs_protocolo() {
		return ds_protocolo;
	}

	public void setDs_protocolo(String ds_protocolo) {
		this.ds_protocolo = ds_protocolo;
	}

	public Integer getNr_tempoestimado() {
		return nr_tempoestimado;
	}

	public void setNr_tempoestimado(Integer nr_tempoestimado) {
		this.nr_tempoestimado = nr_tempoestimado;
	}

	public Integer getSt_atividade() {
		return st_atividade;
	}

	public void setSt_atividade(Integer st_atividade) {
		this.st_atividade = st_atividade;
	}
}
