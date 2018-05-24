package br.com.lsd.service.permissao_acesso;

import java.util.List;

import br.com.lsd.bo.permissao_acesso.Permissao_acessoBO;
import br.com.primum.modelo.grupo_usuario.Grupo_usuario;
import br.com.primum.modelo.permissao_acesso.Permissao_acesso;

public class Permissao_acessoService {

	private Permissao_acessoBO permBO;
	
	public Permissao_acessoBO getPermBO() {
		return permBO;
	}

	public void setPermBO(Permissao_acessoBO permBO) {
		this.permBO = permBO;
	}

	public List<?> buscarDadosPermissao_acesso() {
		this.setPermBO(new Permissao_acessoBO());
		return this.getPermBO().buscarDadosPermissao_acesso();
	}
	
	public void salvarDadosPermissao_acesso(List<?> lista) {
		this.setPermBO(new Permissao_acessoBO());
		this.getPermBO().salvarDadosPermissao_acesso(lista);
	}
	
	public void salvarDadosPermissao_menu_indicadores(List<?> lista) {
		this.setPermBO(new Permissao_acessoBO());
		this.getPermBO().salvarDadosPermissao_menu_indicadores(lista);
	}

	public List<?> buscarDadosPermissao_menu_indicadorUnidade(Integer cd_unidade) {
		this.setPermBO(new Permissao_acessoBO());
		return this.getPermBO().buscarDadosPermissao_menu_indicadorUnidade(cd_unidade);
	}

	public List<?> buscarDadosPermissao_acessoUnidade(Integer cd_unidade) {
		this.setPermBO(new Permissao_acessoBO());
		return this.getPermBO().buscarDadosPermissao_acessoUnidade(cd_unidade);
	}

	public List<?> buscarUnidades() {
		this.setPermBO(new Permissao_acessoBO());
		return this.getPermBO().buscarUnidades();
	}	
	
	public List<Permissao_acesso> verificaConflitoPermissoes( Grupo_usuario grupo ){
		this.setPermBO(new Permissao_acessoBO());
		return this.getPermBO().verificaConflitoPermissoes( grupo );
	}
	
	public List<Permissao_acesso> verificaConflitoPermissoesGrupo( Permissao_acesso p ){
		this.setPermBO(new Permissao_acessoBO());
		return this.getPermBO().verificaConflitoPermissoesGrupo( p );
	}
}