package br.com.primum.modelo.notificacao_automatica;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.notificacao_usuario.Notificacao_usuario;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="notificacao_automatica")
public class Notificacao_automatica {

	@Id
	@Column
	private Integer cd_notificacao_automatica;

	@Column
	private Integer cd_tipo_notificacao;
	
	@Column
	private Integer cd_tabela;
	
	@Column
	private Integer cd_codigo_parametro;
	
	@Column
	private Integer cd_periodicidade;
	
	@Column
	private Integer cd_operacao_notificacao;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_inicio;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_fim;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_ultimo_envio;
	
	@Column
	private String ds_assunto;
	
	@Column
	private String ds_conteudo;
	
	@Column
	private String ds_remetente;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.notificacao_usuario.Notificacao_usuario", campoBuscar="cd_notificacao_automatica", persistir=true)
	private List<Notificacao_usuario> listaNotificacaoUsuario;
	
	@Transient
	private Integer cd_notificacao_usuario;

	
	

	public Integer getCd_notificacao_usuario() {
		return cd_notificacao_usuario;
	}

	public void setCd_notificacao_usuario(Integer cd_notificacao_usuario) {
		this.cd_notificacao_usuario = cd_notificacao_usuario;
	}

	public Integer getCd_notificacao_automatica() {
		return cd_notificacao_automatica;
	}

	public void setCd_notificacao_automatica(Integer cd_notificacao_automatica) {
		this.cd_notificacao_automatica = cd_notificacao_automatica;
	}

	public Integer getCd_tipo_notificacao() {
		return cd_tipo_notificacao;
	}

	public void setCd_tipo_notificacao(Integer cd_tipo_notificacao) {
		this.cd_tipo_notificacao = cd_tipo_notificacao;
	}

	public Integer getCd_tabela() {
		return cd_tabela;
	}

	public void setCd_tabela(Integer cd_tabela) {
		this.cd_tabela = cd_tabela;
	}

	public Integer getCd_codigo_parametro() {
		return cd_codigo_parametro;
	}

	public void setCd_codigo_parametro(Integer cd_codigo_parametro) {
		this.cd_codigo_parametro = cd_codigo_parametro;
	}

	public Integer getCd_periodicidade() {
		return cd_periodicidade;
	}

	public void setCd_periodicidade(Integer cd_periodicidade) {
		this.cd_periodicidade = cd_periodicidade;
	}

	public Date getDt_inicio() {
		return dt_inicio;
	}

	public void setDt_inicio(Date dt_inicio) {
		this.dt_inicio = dt_inicio;
	}

	public Date getDt_fim() {
		return dt_fim;
	}

	public void setDt_fim(Date dt_fim) {
		this.dt_fim = dt_fim;
	}

	public Date getDt_ultimo_envio() {
		return dt_ultimo_envio;
	}

	public void setDt_ultimo_envio(Date dt_ultimo_envio) {
		this.dt_ultimo_envio = dt_ultimo_envio;
	}

	public String getDs_assunto() {
		return ds_assunto;
	}

	public void setDs_assunto(String ds_assunto) {
		this.ds_assunto = ds_assunto;
	}

	public String getDs_conteudo() {
		return ds_conteudo;
	}

	public void setDs_conteudo(String ds_conteudo) {
		this.ds_conteudo = ds_conteudo;
	}

	public String getDs_remetente() {
		return ds_remetente;
	}

	public void setDs_remetente(String ds_remetente) {
		this.ds_remetente = ds_remetente;
	}

	public List<Notificacao_usuario> getListaNotificacaoUsuario() {
		return listaNotificacaoUsuario;
	}

	public void setListaNotificacaoUsuario(
			List<Notificacao_usuario> listaNotificacaoUsuario) {
		this.listaNotificacaoUsuario = listaNotificacaoUsuario;
	}

	public Integer getCd_operacao_notificacao() {
		return cd_operacao_notificacao;
	}

	public void setCd_operacao_notificacao(Integer cd_operacao_notificacao) {
		this.cd_operacao_notificacao = cd_operacao_notificacao;
	}
	
}
