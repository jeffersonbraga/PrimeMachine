package br.com.primum.modelo.usuario;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.lsd.modelo.User;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.config_pesquisa.Config_pesquisa;
import br.com.primum.modelo.exame_medico.Exame_medico;
import br.com.primum.modelo.grupo_usuario_programa.Grupo_usuario_programa;
import br.com.primum.modelo.permissao_acesso.Permissao_acesso;
import br.com.primum.modelo.permissao_menu_indicadores.Permissao_menu_indicadores;
import br.com.primum.modelo.profissao.Profissao;
import br.com.primum.modelo.treinamento_funcao.Treinamento_funcao;
import br.com.primum.modelo.unidade.Unidade;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// ContextoAplicacao.SERIAL_PADRAO;

	@Id
	private Integer cd_usuario;
	
	@Column
	private Integer cd_pessoa;
	
	@Column
	private Integer cd_profissao;
	
	@Column
	private String nm_usuario;
	
	@Column
	private String ds_senha;
	
	@Column
	private Integer st_usuario;
	
	////@Temporal(value=TemporalType.DATE)
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_cadastro;
	
	@Column
	private Boolean vl_alterar_senha;
	
	@Column
	private Integer usuario_supervisor;
	
	@Column
	private Integer gestor_qualidade;

	@Transient
	private String clientId;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.permissao_acesso.Permissao_acesso", campoBuscar="cd_usuario", persistir=false)
	private List<Permissao_acesso> listaPermissao_acesso;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.permissao_menu_indicadores.Permissao_menu_indicadores", campoBuscar="cd_usuario", persistir=false)
	private List<Permissao_menu_indicadores> listaPermissao_menu_indicadores;
	
	@Transient
	private List<Grupo_usuario_programa> listaGrupoPermissao;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.config_pesquisa.Config_pesquisa", campoBuscar="cd_usuario", persistir=false)
	private List<Config_pesquisa> listaConfig_pesquisa;
	
	
	public List<Config_pesquisa> getListaConfig_pesquisa() {
		return listaConfig_pesquisa;
	}

	public void setListaConfig_pesquisa(List<Config_pesquisa> listaConfig_pesquisa) {
		this.listaConfig_pesquisa = listaConfig_pesquisa;
	}

	public List<Grupo_usuario_programa> getListaGrupoPermissao() {
		return listaGrupoPermissao;
	}

	public void setListaGrupoPermissao(
			List<Grupo_usuario_programa> listaGrupoPermissao) {
		this.listaGrupoPermissao = listaGrupoPermissao;
	}

	@Transient
	private List<Unidade> listaUnidades_acesso;

	@Transient
	private List<Profissao> listaCargos;

	@Transient
	private List<Treinamento_funcao> listaTreinamentos;

	@Transient
	private List<Exame_medico> listaExames;

	@Transient
	private Integer cd_user;

	@Transient
	@ObjetoBusca(caminhoClasseJava="br.com.lsd.modelo.User", campoBuscar="id", campoComparar="cd_user",notificador=false, persistir=false)
	private User user;
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getGestor_qualidade() {
		return gestor_qualidade;
	}

	public void setGestor_qualidade(Integer gestor_qualidade) {
		this.gestor_qualidade = gestor_qualidade;
	}

	public Boolean getVl_alterar_senha() {
		return vl_alterar_senha;
	}

	public void setVl_alterar_senha(Boolean vl_alterar_senha) {
		this.vl_alterar_senha = vl_alterar_senha;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Integer getCd_pessoa() {
		return cd_pessoa;
	}

	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}

	public String getNm_usuario() {
		return nm_usuario;
	}

	public void setNm_usuario(String nm_usuario) {
		this.nm_usuario = nm_usuario;
	}

	public String getDs_senha() {
		return ds_senha;
	}

	public void setDs_senha(String ds_senha) {
		this.ds_senha = ds_senha;
	}

	public Integer getSt_usuario() {
		return st_usuario;
	}

	public void setSt_usuario(Integer st_usuario) {
		this.st_usuario = st_usuario;
	}

	public Date getDt_cadastro() {
		return dt_cadastro;
	}

	public void setDt_cadastro(Date dt_cadastro) {
		this.dt_cadastro = dt_cadastro;
	}

	public Integer getCd_profissao() {
		return cd_profissao;
	}

	public void setCd_profissao(Integer cd_profissao) {
		this.cd_profissao = cd_profissao;
	}

	public List<Permissao_acesso> getListaPermissao_acesso() {
		return listaPermissao_acesso;
	}

	public void setListaPermissao_acesso(
			List<Permissao_acesso> listaPermissao_acesso) {
		this.listaPermissao_acesso = listaPermissao_acesso;
	}

	public List<Permissao_menu_indicadores> getListaPermissao_menu_indicadores() {
		return listaPermissao_menu_indicadores;
	}

	public void setListaPermissao_menu_indicadores(
			List<Permissao_menu_indicadores> listaPermissao_menu_indicadores) {
		this.listaPermissao_menu_indicadores = listaPermissao_menu_indicadores;
	}

	public List<Unidade> getListaUnidades_acesso() {
		return listaUnidades_acesso;
	}

	public void setListaUnidades_acesso(List<Unidade> listaUnidades_acesso) {
		this.listaUnidades_acesso = listaUnidades_acesso;
	}

	public Integer getUsuario_supervisor() {
		return usuario_supervisor;
	}

	public void setUsuario_supervisor(Integer usuario_supervisor) {
		this.usuario_supervisor = usuario_supervisor;
	}

	public Integer getCd_user() {
		return cd_user;
	}

	public void setCd_user(Integer cd_user) {
		this.cd_user = cd_user;
	}	
}