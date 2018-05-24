package br.com.primum.modelo.notificacao;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.notificacao_grupo_usuario.Notificacao_grupo_usuario;
import br.com.primum.modelo.notificacao_usuario.Notificacao_usuario;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="notificacao")
public class Notificacao {

	@Id
	@Column
	private Integer cd_notificacao;

	@Column
	private Integer cd_tipo_notificacao;
	
	@Column
	private Integer st_exibir;

	@Column
	private Integer cd_tabela;

	@Column
	private Integer cd_codigo_parametro;
	
	@Column
	private Integer tp_operacao;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.notificacao_usuario.Notificacao_usuario", campoBuscar="cd_notificacao", persistir=true)
	private List<Notificacao_usuario> lista_notificacao_usuario;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.notificacao_grupo_usuario.Notificacao_grupo_usuario", campoBuscar="cd_notificacao", persistir=false)
	private List<Notificacao_grupo_usuario> lista_notificacao_grupo_usuario;
	

	public Integer getTp_operacao() {
		return tp_operacao;
	}

	public void setTp_operacao(Integer tp_operacao) {
		this.tp_operacao = tp_operacao;
	}

	public List<Notificacao_usuario> getLista_notificacao_usuario() {
		return lista_notificacao_usuario;
	}

	public void setLista_notificacao_usuario(
			List<Notificacao_usuario> lista_notificacao_usuario) {
		this.lista_notificacao_usuario = lista_notificacao_usuario;
	}

	public List<Notificacao_grupo_usuario> getLista_notificacao_grupo_usuario() {
		return lista_notificacao_grupo_usuario;
	}

	public void setLista_notificacao_grupo_usuario(
			List<Notificacao_grupo_usuario> lista_notificacao_grupo_usuario) {
		this.lista_notificacao_grupo_usuario = lista_notificacao_grupo_usuario;
	}

	public Integer getCd_notificacao() {
		return cd_notificacao;
	}

	public void setCd_notificacao(Integer cd_notificacao) {
		this.cd_notificacao = cd_notificacao;
	}

	public Integer getCd_tipo_notificacao() {
		return cd_tipo_notificacao;
	}

	public void setCd_tipo_notificacao(Integer cd_tipo_notificacao) {
		this.cd_tipo_notificacao = cd_tipo_notificacao;
	}

	public Integer getSt_exibir() {
		return st_exibir;
	}

	public void setSt_exibir(Integer st_exibir) {
		this.st_exibir = st_exibir;
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
}