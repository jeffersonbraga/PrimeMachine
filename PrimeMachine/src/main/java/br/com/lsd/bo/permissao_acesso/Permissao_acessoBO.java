package br.com.lsd.bo.permissao_acesso;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.grupo_usuario.Grupo_usuario;
import br.com.primum.modelo.grupo_usuario_programa.Grupo_usuario_programa;
import br.com.primum.modelo.lista_modulos.Lista_modulos;
import br.com.primum.modelo.menu.Menu;
import br.com.primum.modelo.menu_indicadores.Menu_indicadores;
import br.com.primum.modelo.modulo.Modulo;
import br.com.primum.modelo.modulo_indicadores.Modulo_indicadores;
import br.com.primum.modelo.permissao_acesso.Permissao_acesso;
import br.com.primum.modelo.permissao_menu_indicadores.Permissao_menu_indicadores;
import br.com.primum.modelo.programa.Programa;
import br.com.primum.modelo.unidade.Unidade;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.modelo.usuario_grupo_usuario.Usuario_grupo_usuario;

public class Permissao_acessoBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	public List<?> buscarDadosPermissao_acesso() {

		this.setDao(new GenericDAO());
		List<?> listaUsuario 	= this.getDao().getSession().createCriteria(Usuario.class).list();
		PesquisaPadraoBO pesq = new PesquisaPadraoBO();

		for (Usuario u : (List<Usuario>) listaUsuario) {
			pesq.buscarObjetoCompleto(u);
			if ( (u.getListaPermissao_acesso() == null) || (u.getListaPermissao_acesso().size() == 0) ) {

				if (u.getListaPermissao_acesso() == null) {
					u.setListaPermissao_acesso( new ArrayList() );
				}
			}

			List<?> listaProgramas 	= this.getDao().getSession().createCriteria(Programa.class).list();

			for (Programa prog : (List<Programa>) listaProgramas) {

				boolean existeConfiguracao = false;
				
				for (Permissao_acesso permissao : u.getListaPermissao_acesso()) {
					if (permissao.getCd_programa().equals(prog.getCd_programa())) {
						existeConfiguracao = true;
						break;
					}
				}
				
				if (!existeConfiguracao) {
					Permissao_acesso p = new Permissao_acesso();
					p.setCd_usuario(u.getCd_usuario());
					p.setTp_salvar(0);
					p.setTp_editar(0);
					p.setTp_excluir(0);
					p.setTp_especial(0);
					p.setTp_visualizar(0);
					
					p.setCd_usuario(u.getCd_usuario());
					p.setCd_programa(prog.getCd_programa());
					
					p.setPrograma(prog);
	
					u.getListaPermissao_acesso().add(p);
				}
			}
		}

		return listaUsuario;
	}

	@SuppressWarnings("unchecked")
	public List<?> buscarDadosPermissao_acessoUnidade(Integer cd_unidade) {

		this.setDao(new GenericDAO());
		List<?> listaUsuario 	= this.getDao().getSession().createCriteria(Usuario.class)
				.addOrder(Order.asc("nm_usuario"))
				.list();

		for (Usuario u : (List<Usuario>) listaUsuario) {

			List<?> listPermissaoUsuario = this.getDao().getSession().createCriteria(Permissao_acesso.class).add(Restrictions.eq("cd_usuario", u.getCd_usuario())).add(Restrictions.eq("cd_unidade", cd_unidade)).list();  	
			u.setListaPermissao_acesso( (List<Permissao_acesso>) listPermissaoUsuario);
			if ( (u.getListaPermissao_acesso() == null) || (u.getListaPermissao_acesso().size() == 0) ) {

				if (u.getListaPermissao_acesso() == null) {
					u.setListaPermissao_acesso( new ArrayList() );
				}
			}

			//List<?> listaProgramas 	= this.getDao().getSession().createCriteria(Programa.class).list();
			
			List<Lista_modulos> listaModulos = (List<Lista_modulos>) this.getDao().getSession().createCriteria(Lista_modulos.class).list();
			List<Programa> listaProgramas  = new ArrayList<>();
			for(Lista_modulos l : listaModulos){
				List<Menu> listaMenus = (List<Menu>) this.getDao().getSession().createCriteria(Menu.class)
						.add(Restrictions.eq("cd_modulo", l.getCd_modulo()))
						.add(Restrictions.isNotNull("cd_programa"))
						.add(Restrictions.isNotNull("cd_menu_item"))
						.list();
				
				for(Menu m : listaMenus){
					Programa p = (Programa) this.getDao().getSession().createCriteria(Programa.class)
							.add(Restrictions.eq("cd_programa", m.getCd_programa())).uniqueResult();
					if(p!=null){
						p.setCd_modulo( l.getCd_modulo() );
						listaProgramas.add( p );
					}
				}

			}

			for (Programa prog : (List<Programa>) listaProgramas) {
				
				boolean existeConfiguracao = false;
				
				for (Permissao_acesso permissao : u.getListaPermissao_acesso()) {
					if (permissao.getCd_programa().equals(prog.getCd_programa())) {
						existeConfiguracao = true;
						permissao.setCd_modulo(prog.getCd_modulo());
						break;
					}
				}
				
				if (!existeConfiguracao) {
					Permissao_acesso p = new Permissao_acesso();
					p.setCd_usuario(u.getCd_usuario());
					p.setTp_salvar(0);
					p.setTp_editar(0);
					p.setTp_excluir(0);
					p.setTp_especial(0);
					p.setTp_visualizar(0);
					p.setCd_modulo( prog.getCd_modulo() );
					p.setCd_usuario(u.getCd_usuario());
					p.setCd_programa(prog.getCd_programa());
					
					p.setPrograma(prog);
					
					p.setCd_unidade(cd_unidade);
	
					u.getListaPermissao_acesso().add(p);
				}
			}
		}
		this.getDao().getSession().close();
		return listaUsuario;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> buscarDadosPermissao_menu_indicadorUnidade(Integer cd_unidade) {

		this.setDao(new GenericDAO());
		List<?> listaUsuario 	= this.getDao().getSession().createCriteria(Usuario.class)
				.addOrder(Order.asc("nm_usuario"))
				.list();

		for (Usuario u : (List<Usuario>) listaUsuario) {

			List<?> listPermissaoUsuario = this.getDao().getSession().createCriteria(Permissao_menu_indicadores.class).add(Restrictions.eq("cd_usuario", u.getCd_usuario())).add(Restrictions.eq("cd_unidade", cd_unidade)).list();  	
			u.setListaPermissao_menu_indicadores( (List<Permissao_menu_indicadores>) listPermissaoUsuario );
			if ( (u.getListaPermissao_menu_indicadores() == null) || (u.getListaPermissao_menu_indicadores().size() == 0) ) {

				if (u.getListaPermissao_menu_indicadores() == null) {
					u.setListaPermissao_menu_indicadores( new ArrayList() );
				}
			}

			List<Modulo_indicadores> listaModulos = (List<Modulo_indicadores>) this.getDao().getSession().createCriteria(Modulo_indicadores.class).list();
			
			for(Modulo_indicadores l : listaModulos){
				List<Menu_indicadores> listaMenus = (List<Menu_indicadores>) this.getDao().getSession().createCriteria(Menu_indicadores.class)
						.add(Restrictions.eq("cd_modulo_indicadores", l.getCd_modulo_indicadores()))
						.list();
				
				List<Menu_indicadores> listaSubMenus = (List<Menu_indicadores>) this.getDao().getSession().createCriteria(Menu_indicadores.class)
						.add(Restrictions.eq("cd_menu_origem", l.getCd_modulo_indicadores()))
						.add(Restrictions.isNull("cd_modulo_indicadores"))
						.list();
				
				listaMenus.addAll( listaSubMenus );
				for(Menu_indicadores m : listaMenus){
					boolean existeConfiguracao = false;
					
					for (Permissao_menu_indicadores permissao : u.getListaPermissao_menu_indicadores()) {
						if (permissao.getCd_menu_indicadores().equals(m.getCd_menu_indicadores())) {
							existeConfiguracao = true;
							break;
						}
					}
					
					if (!existeConfiguracao) {
						Permissao_menu_indicadores p = new Permissao_menu_indicadores();
						
						p.setCd_usuario(u.getCd_usuario());
						p.setTp_visualizar(0);
						p.setCd_menu_indicadores(m.getCd_menu_indicadores());
						p.setCd_unidade(cd_unidade);
						p.setCd_modulo_indicadores( l.getCd_modulo_indicadores() );
		
						u.getListaPermissao_menu_indicadores().add(p);
					}
				}

			}
		}

		return listaUsuario;
	}
	
	public List<?> buscarUnidades() {

		this.setDao(new GenericDAO());
		List<?> lista = this.getDao().getSession().createCriteria(Unidade.class).addOrder(Order.asc("ds_unidade")).list();
		return lista;
	}

	public void salvarDadosPermissao_acesso(List<?> lista) {
		
		try {
			
			this.setDao(new GenericDAO());
			//this.getDao().abrirTransacao();
			for (Usuario usuario : (List<Usuario>) lista) {
				System.out.println(usuario.getCd_usuario().toString());
				for (Permissao_acesso permissao : usuario.getListaPermissao_acesso()) {
					this.getDao().salvar(permissao);
				}
			}
			
			//this.getDao().commitTransacao();
			this.getDao().getSession().close();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			///this.getDao().rollbackTransacao();
			//this.getDao().fecharConexao();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvarDadosPermissao_menu_indicadores(List<?> lista) {
		//TODO Anota��o para informar uso de crud fora do genericdao 
		try {
			
			Boolean atualizacao = false;
			this.setDao(new GenericDAO());
			this.getDao().abrirTransacao();
			for (Usuario usuario : (List<Usuario>) lista) {

				for (Permissao_menu_indicadores permissao : usuario.getListaPermissao_menu_indicadores()) {

					if (permissao.getCd_permissao_menu_indicadores() == null) {
						this.getDao().setValorChavePrimaria(permissao);
						this.getDao().getSession().save(permissao);
					} else {
						try {
							this.getDao().atualizar(permissao);
							atualizacao = true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			if( !atualizacao ){
				this.getDao().commitTransacao();
				this.getDao().fecharConexao();
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			this.getDao().rollbackTransacao();
			this.getDao().fecharConexao();
			e.printStackTrace();
		}
	}
	
	public List<Permissao_acesso> verificaConflitoPermissoes( Grupo_usuario grupo ){
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Permissao_acesso> listaConflitante = new ArrayList<>();
		for(Grupo_usuario_programa grupoPermissao : grupo.getListaPermissoes()){
			List<Permissao_acesso> listaPermissoes = ( List<Permissao_acesso> ) this.getDao().getSession().createCriteria( Permissao_acesso.class )
					.add(Restrictions.eq("cd_programa", grupoPermissao.getCd_programa()))
					.list();
			
			for( Permissao_acesso perm : listaPermissoes){
				for(Usuario_grupo_usuario usuarioGrupo : grupo.getListaUsuarios() ){
					if( usuarioGrupo.getCd_usuario().equals(perm.getCd_usuario()) &&
							usuarioGrupo.getCd_unidade().equals(perm.getCd_unidade() )){
						
						if( !grupoPermissao.getTp_salvar().equals(0) || !grupoPermissao.getTp_excluir().equals(0) || !grupoPermissao.getTp_visualizar().equals(0) ){
							if( !perm.getTp_salvar().equals(0) || !perm.getTp_excluir().equals(0) || !perm.getTp_visualizar().equals(0) ){
								if(!grupoPermissao.getTp_salvar().equals(perm.getTp_salvar()) ||
									!grupoPermissao.getTp_excluir().equals(perm.getTp_excluir()) ||	
									!grupoPermissao.getTp_visualizar().equals(perm.getTp_visualizar()) ){
								
									Usuario u = (Usuario) this.getDao().getSession().createCriteria( Usuario.class )
										.add(Restrictions.eq("cd_usuario", perm.getCd_usuario())).uniqueResult();
								
									pesquisa.buscarObjetoCompleto( perm );
									perm.setNm_usuario( u.getNm_usuario() );
									listaConflitante.add( perm );
									//CONFLITO
								}
							}
						}
					}
				}
			}
		}
		return listaConflitante;
	}
	
	public List<Permissao_acesso> verificaConflitoPermissoesGrupo( Permissao_acesso p ){
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Permissao_acesso> listaConflitante = new ArrayList<>();
		
		List<Grupo_usuario_programa> listaPermissoes = ( List<Grupo_usuario_programa> ) this.getDao().getSession().createCriteria( Grupo_usuario_programa.class )
				.add(Restrictions.eq("cd_programa", p.getCd_programa()))
				.list();
		
		for(Grupo_usuario_programa grupoPermissao : listaPermissoes){
			
			List<Usuario_grupo_usuario> listaUsuarios = ( List<Usuario_grupo_usuario> ) this.getDao().getSession().createCriteria( Usuario_grupo_usuario.class )
					.add(Restrictions.eq("cd_grupo_usuario", grupoPermissao.getCd_grupo_usuario()))
					.list();
			
				for(Usuario_grupo_usuario usuarioGrupo : listaUsuarios ){
					if( usuarioGrupo.getCd_usuario().equals(p.getCd_usuario()) &&
							usuarioGrupo.getCd_unidade().equals(p.getCd_unidade() )){
						if( !grupoPermissao.getTp_salvar().equals(0) || !grupoPermissao.getTp_excluir().equals(0) || !grupoPermissao.getTp_visualizar().equals(0) ){
							if( !p.getTp_salvar().equals(0) || !p.getTp_excluir().equals(0) || !p.getTp_visualizar().equals(0) ){
								if(!grupoPermissao.getTp_salvar().equals(p.getTp_salvar()) ||
									!grupoPermissao.getTp_excluir().equals(p.getTp_excluir()) ||	
									!grupoPermissao.getTp_visualizar().equals(p.getTp_visualizar()) ){
								
									Usuario u = (Usuario) this.getDao().getSession().createCriteria( Usuario.class )
										.add(Restrictions.eq("cd_usuario", p.getCd_usuario())).uniqueResult();
								
									pesquisa.buscarObjetoCompleto( p );
									p.setNm_usuario( u.getNm_usuario() );
									listaConflitante.add( p );
									//CONFLITO
								}
							}
						}
					}
				}
			
		}
		return listaConflitante;
	}
}