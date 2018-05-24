package br.com.lsd.bo.usuario;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lsd.modelo.Authority;
import br.com.lsd.modelo.User;
import br.com.lsd.security.PersistentToken;
import br.com.primum.bo.criptografia.Criptografar;
import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.bo.relatorios.RelatorioBO;
import br.com.primum.chat.service.ChatService;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.flexclient.PrimumFlexClient;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.config_pesquisa.Config_pesquisa;
import br.com.primum.modelo.fiscal_sif.Fiscal_sif;
import br.com.primum.modelo.grupo_usuario_programa.Grupo_usuario_programa;
import br.com.primum.modelo.permissao_acesso.Permissao_acesso;
import br.com.primum.modelo.permissao_padrao.Permissao_padrao;
import br.com.primum.modelo.pessoa.Pessoa;
import br.com.primum.modelo.programa.Programa;
import br.com.primum.modelo.unidade.Unidade;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.modelo.usuario_grupo_usuario.Usuario_grupo_usuario;

@Service
@Transactional
public class UsuarioBO {

	private GenericDAO dao;
	
	public UsuarioBO(){
		
	}

	private void setaPermissoes(List<Integer> lista, List<Unidade> listaUnidades_acesso, Usuario usuario, Usuario usr){
		for (Integer cd_unidade : (List<Integer>) lista) {
			Unidade obj_unidade = (Unidade) this.getDao().getSession().createCriteria(Unidade.class).add(Restrictions.eq("cd_unidade", cd_unidade)).uniqueResult();

			if ( (obj_unidade != null) && (obj_unidade.getCd_unidade() != null) ) {
				listaUnidades_acesso.add(obj_unidade);

				List<Permissao_acesso> listaPermissao = (List<Permissao_acesso>) this.getDao().getSession().createCriteria(Permissao_acesso.class)
						.add(Restrictions.eq("cd_usuario", usr.getCd_usuario()))
						.add(Restrictions.eq("cd_unidade", cd_unidade)).list();

				if ( usr.getListaPermissao_acesso() == null ) {
					usr.setListaPermissao_acesso(new ArrayList<Permissao_acesso>());
				}

				for (Permissao_acesso permissao : listaPermissao) {

					Programa programa = (Programa) this.getDao().getSession().createCriteria(Programa.class).add(Restrictions.eq("cd_programa", permissao.getCd_programa())).uniqueResult();
					permissao.setPrograma(programa);
				}

				usr.getListaPermissao_acesso().addAll(listaPermissao);
			}
		}
	}
	
	public Usuario efetuarLoginChamado(Integer cd_usuario){
		
		Usuario usr = null;
		Usuario usrFinal = null;
		this.setDao(new GenericDAO());
		usr = (Usuario) this.getDao().getSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("cd_usuario", cd_usuario ))
		.uniqueResult();
		
		try {
			usrFinal = efetuarLogin(usr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return usrFinal;
	}
	
	public User efetuarLogin(String login){
		
		User usr = null;
		this.setDao(new GenericDAO());
		usr = (User) this.getDao().getSession().createCriteria(User.class)
				.add(Restrictions.eq("login", login ))
		.uniqueResult();
		
		List<Authority> auth = null;
		Set<Authority> authorities = new HashSet<>();
		auth = (List<Authority>) this.getDao().getSession().createCriteria(Authority.class).list();
		authorities.addAll(auth);
		if (usr != null) {
			usr.setAuthorities(authorities);
		}
		
		return usr;
	}
	
	@SuppressWarnings("unchecked")
	public Usuario efetuarLogin(Usuario usuario) throws Exception {
		Usuario usr = null;
		try {
			this.setDao(new GenericDAO());

			usr = (Usuario) this.getDao().getSession().createCriteria(Usuario.class)
					.add(Restrictions.eq("nm_usuario", usuario.getNm_usuario()))
					.add(Restrictions.eq("ds_senha", usuario.getDs_senha()
							)).uniqueResult();

			if (usr == null) {

				usuario.setDs_senha( Criptografar.cifraMD5(usuario.getDs_senha()) );

				usr = (Usuario) this.getDao().getSession().createCriteria(Usuario.class)
						.add(Restrictions.eq("nm_usuario", usuario.getNm_usuario()))
						.add(Restrictions.eq("ds_senha", usuario.getDs_senha()
								)).uniqueResult();

				if (usr == null) {
					throw new Exception("USU�RIO INV�LIDO");
				}
			}

			if (usr != null) {

				Criterion criterioSalvar 		= Restrictions.eq("tp_salvar", 1);
				Criterion criterioExcluir		= Restrictions.eq("tp_excluir", 1);
				Criterion criterioVisualizar 	= Restrictions.eq("tp_visualizar", 1);
				Criterion criterioAuxiliar 		= Restrictions.or(criterioSalvar, criterioExcluir);
				Criterion criterioAuxiliar2		= Restrictions.or(criterioAuxiliar, criterioVisualizar);

				List<Unidade> listaUnidades_acesso = new ArrayList<>(); 
				List<?> listaUnidades = this.getDao().getSession().createCriteria(Permissao_acesso.class)
												.add(Restrictions.eq("cd_usuario", usr.getCd_usuario()))
												.add(Restrictions.and(criterioAuxiliar, criterioAuxiliar2))
												.setProjection(Projections.distinct(Projections.property("cd_unidade")))
												.list();

				List<?> listaUnidades2 = this.getDao().getSession().createCriteria(Usuario_grupo_usuario.class)
						.add(Restrictions.eq("cd_usuario", usr.getCd_usuario()))
						.setProjection(Projections.distinct(Projections.property("cd_unidade")))
						.list();

				//criteria.setProjection(Projections.distinct(Projections.property("campo")));

				if (listaUnidades != null ) {
					setaPermissoes( (List<Integer>) listaUnidades, listaUnidades_acesso, usuario, usr);
				}
				
				if(listaUnidades2!=null){
					setaPermissoes( (List<Integer>) listaUnidades2, listaUnidades_acesso, usuario, usr);
				}


				/******************/
				PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
				List<Usuario_grupo_usuario> listaUsu =  (List<Usuario_grupo_usuario>) this.getDao().getSession().createCriteria(Usuario_grupo_usuario.class)
					.add(Restrictions.eq("cd_usuario", usr.getCd_usuario())).list();
				usr.setListaGrupoPermissao(new ArrayList<Grupo_usuario_programa>());
				for(Usuario_grupo_usuario u: listaUsu){
					
					List<Grupo_usuario_programa> listaProgramas =  (List<Grupo_usuario_programa>) this.getDao().getSession().createCriteria(Grupo_usuario_programa.class)
							.add(Restrictions.eq("cd_grupo_usuario", u.getCd_grupo_usuario())).list();
					
					for(Grupo_usuario_programa grupo: listaProgramas){
						pesquisa.buscarObjetoCompleto( grupo );
					}
					
					usr.getListaGrupoPermissao().addAll( listaProgramas );
					//usr.setListaGrupoPermissao( usr.getListaGrupoPermissao() );
				}

				List<Config_pesquisa> listaConfigPesquisa = (List<Config_pesquisa>) this.getDao().getSession().createCriteria( Config_pesquisa.class ).add(Restrictions.eq("cd_usuario", usr.getCd_usuario())).list();
				for(Config_pesquisa conf : listaConfigPesquisa ){
					pesquisa.buscarObjetoCompleto( conf );
				}
				usr.setListaConfig_pesquisa( listaConfigPesquisa );

				usr.setListaUnidades_acesso(listaUnidades_acesso);
			}

			PrimumFlexClient userPrincipal = new PrimumFlexClient();

			Boolean usuarioJaLogado =false;
			ChatService cs = new ChatService();
			List<Usuario> listaUsuariosLogados = cs.buscarContatosAtivos();
			/*for(Usuario u: listaUsuariosLogados){
				LogPrimum.mensagem(""+ FlexContext.getFlexClient());
				if(u.getCd_usuario().equals(usr.getCd_usuario())){
					usuarioJaLogado=true;
//					userPrincipal.setUsuario(u);
//					userPrincipal.getUsuario().setClientId( u.getClientId() );
//					FlexContext.getFlexClient().setUserPrincipal(userPrincipal);
					//FlexContext.clearThreadLocalObjects();
					//FlexContext.createThreadLocalObjects();

					userPrincipal.setUsuario(usr);
					userPrincipal.getUsuario().setClientId( FlexContext.getFlexClient().getId() );

					FlexContext.getMessageBroker().getFlexClientManager().getFlexClient(u.getClientId()).setUserPrincipal(userPrincipal);
					break;
					
				}
			}
			
			if(!usuarioJaLogado){
				userPrincipal.setUsuario(usr);
				userPrincipal.getUsuario().setClientId( FlexContext.getFlexClient().getId() );
				FlexContext.getFlexClient().setUserPrincipal(userPrincipal);
			}
			*/
			cs.notificarRefreshLista();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.getDao().fecharConexao();
			LogPrimum.mensagem(e.getMessage());
		}
		return usr;
	}
	
	public Boolean criptografaSenha( Integer cd_usuario, String senha_atual) {
		this.setDao(new GenericDAO());
		CrudGenericBO crud = new CrudGenericBO();
		Usuario usr = (Usuario) this.getDao().getSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("cd_usuario", cd_usuario))
				.uniqueResult();
	
		if(usr!=null){
			usr.setDs_senha( Criptografar.cifraMD5( senha_atual ) );
			try {
				crud.atualizar(usr);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public Boolean validarSenhaETrocar(Usuario usuario, String senha_atual, String nova_senha) {
		
		Usuario usr = null;
		this.setDao(new GenericDAO());
		CrudGenericBO crud = new CrudGenericBO();
		usr = (Usuario) this.getDao().getSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("cd_usuario", usuario.getCd_usuario()))
				.uniqueResult();
	
		if(usr!=null){
			
			if( usr.getDs_senha().equals( Criptografar.cifraMD5( senha_atual ) ) ){
			//if(usr.getDs_senha().equals(senha_atual)){
				usr.setDs_senha( Criptografar.cifraMD5( nova_senha ) );
				usr.setVl_alterar_senha(false);
				try {
					crud.atualizar(usr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				return false;
			}
		}
		return true;
	}
	
//	private String getSQLLogin(Integer cd_usuario, String senha) throws Exception {
//		String query = "select u.cd_usuario as cd_usuario, u.nm_usuario as nm_usuario, u.ds_senha as ds_senha from Usuario u where u.cd_usuario = " + cd_usuario + " and u.ds_senha = '" + senha + "'";
//		return query;
//	}
	
	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	public List<Object> buscarUsuario() throws Exception {
		
		this.setDao(new GenericDAO());
		List<Object> lista = this.getDao().findAll(new Usuario());
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object> buscarListaColaboradorProfissao(Integer cd_profissao) throws Exception {

		this.setDao(new GenericDAO());
		List<Object> lista = this.getDao().getSession().createCriteria(Usuario.class).add(Restrictions.eq("cd_profissao", cd_profissao)).list();
		return lista;
	}
	
	
	public Pessoa buscarUsuario_Fiscal_pelaPessoa(Pessoa pessoa) {
		this.setDao(new GenericDAO());
		
		Usuario u = (Usuario) this.getDao().getSession().createCriteria(Usuario.class).add(Restrictions.eq("cd_pessoa", pessoa.getCd_pessoa() )).uniqueResult();
		Fiscal_sif fiscalSif = (Fiscal_sif) this.getDao().getSession().createCriteria(Fiscal_sif.class).add(Restrictions.eq("cd_pessoa", pessoa.getCd_pessoa() )).uniqueResult();
		
		pessoa.setUsuario( u );
		//pessoa.setFiscal_sif(fiscalSif);

		return pessoa;
	}

	public Usuario buscarUsuarioPorId(Integer cd_usuario) {
		this.setDao(new GenericDAO());
		Usuario u = (Usuario) this.getDao().getSession().createCriteria(Usuario.class).add(Restrictions.eq("cd_usuario", cd_usuario )).uniqueResult();
		/*User us = (User) this.getDao().getSession().createCriteria(User.class).add(Restrictions.eq("id", new Long(1))).uniqueResult();
		u.setUser(us);*/
		return u;
	}

	public List<Usuario_grupo_usuario> buscarUsuarioGrupoUsuario() {

		this.setDao(new GenericDAO());
		List<Usuario> lista = (List<Usuario>) this.getDao().getSession().createCriteria(Usuario.class).list();
		List<Usuario_grupo_usuario> listaAux = new ArrayList<>();

		for(Usuario u:lista){
			
			Usuario_grupo_usuario ugu = (Usuario_grupo_usuario) this.getDao().getSession().createCriteria(Usuario_grupo_usuario.class)
					.add(Restrictions.eq("cd_usuario", u.getCd_usuario()))
					.add(Restrictions.eq("cd_unidade", 1))
					.uniqueResult();
			
			if(ugu!=null){
				if(ugu.getCd_grupo_usuario()!=null){	
					ugu.setNm_usuario(u.getNm_usuario());
					ugu.setNm_grupo_pertencente( RelatorioBO.toText("grupo_usuario", "cd_grupo_usuario", "nm_grupo_usuario", ugu.getCd_grupo_usuario().toString()) );
				}
			}else{
				ugu = new Usuario_grupo_usuario();
				ugu.setCd_usuario(u.getCd_usuario());
				ugu.setNm_usuario(u.getNm_usuario());
				ugu.setCd_unidade(1);
			}
			
	
			File file = new File(ContextoAplicacao.CAMINHO_APLICACAO + "fotos_usuario/original/"+ ugu.getCd_usuario().toString() +".png");
	        FileInputStream fis;
			try {
				fis = new FileInputStream(file);
			
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        byte[] buf = new byte[1024];
	    
	            for (int readNum; (readNum = fis.read(buf)) != -1;) {
	                bos.write(buf, 0, readNum); 
	                LogPrimum.mensagem("read " + readNum + " bytes,");
	            }
	            
	        ugu.setSourceImagem( bos.toByteArray() );
	        
			} catch (IOException ex) {
	           
	        }
	 
	        
			ugu.setMarcado(false);
			listaAux.add(ugu);
		}
		return listaAux;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario_grupo_usuario> setaNm_grupoPertence(Integer cd_unidade) {
		this.setDao(new GenericDAO());
		List<Usuario_grupo_usuario> listaAux = (List<Usuario_grupo_usuario>) this.getDao().getSession().createCriteria(Usuario_grupo_usuario.class)
					.add(Restrictions.eq("cd_unidade", cd_unidade))
					.list();
			
		for(Usuario_grupo_usuario u:listaAux){
			u.setMarcado(false);
			u.setNm_grupo_pertencente( RelatorioBO.toText("grupo_usuario", "cd_grupo_usuario", "nm_grupo_usuario", u.getCd_grupo_usuario().toString()) );
		}
	
		return listaAux;
	}

	public Usuario verificaSeUsuarioCurtiu(String id_usuario){
		this.setDao(new GenericDAO());
		if(id_usuario!=null){
			Usuario u = ( Usuario ) this.getDao().getSession().createCriteria(Usuario.class).add(Restrictions.eq("cd_usuario", id_usuario)).uniqueResult();
			if(u!=null){
				if(u.getCd_usuario()!=null){
					PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
					pesquisa.buscarObjetoCompleto( u );
					return u;
				}
			}
		}
		return null;
	}

	public Usuario insereUsuarioFace(String nm_usuario, String id_usuario){

		Boolean resultado = true;

		Usuario usuario = new Usuario();
		Unidade unidade = new Unidade();
		Permissao_acesso p1 = new Permissao_acesso();
		Permissao_acesso p2 = new Permissao_acesso();
		
		usuario.setNm_usuario(nm_usuario);
		usuario.setCd_usuario(Integer.parseInt(id_usuario));
		
		unidade.setCd_responsavel_unidade(Integer.parseInt(id_usuario));
		unidade.setDs_unidade(nm_usuario);
		unidade.setSt_unidade(1);
		
		CrudGenericBO crud = new CrudGenericBO();
		Usuario usuarioPersistido = null;
		try {
			
			Pessoa pessoa = (Pessoa) crud.salvar(new Pessoa());
			usuario.setCd_pessoa(pessoa.getCd_pessoa());
			
			usuarioPersistido = (Usuario) crud.salvar(usuario);
			Unidade unidadePersistida = (Unidade) crud.salvar(unidade);
			
			p1.setCd_programa(6);
			p1.setCd_usuario(usuarioPersistido.getCd_usuario());
			p1.setTp_salvar(1);
			p1.setTp_excluir(1);
			p1.setTp_visualizar(1);
			p1.setTp_especial(1);
			p1.setTp_editar(1);
			p1.setCd_unidade(unidadePersistida.getCd_unidade());
			
			p2.setCd_programa(38);
			p2.setCd_usuario(usuarioPersistido.getCd_usuario());
			p2.setTp_salvar(1);
			p2.setTp_excluir(1);
			p2.setTp_visualizar(1);
			p2.setTp_especial(1);
			p2.setTp_editar(1);
			p2.setCd_unidade(unidadePersistida.getCd_unidade());
			
			crud.salvar( p1 );
			crud.salvar( p2 );
				
			
			if(usuarioPersistido!=null){
				if(usuarioPersistido.getCd_usuario()!=null){
					PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
					pesquisa.buscarObjetoCompleto( usuarioPersistido );
					return usuarioPersistido;
				}
			}
			
		} catch (Exception e) {
			return null;
			//resultado=false;
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean deletaConfiguracoesPesquisa(Integer cd_usuario, Integer cd_programa) {
		
		this.setDao(new GenericDAO());
		List<Config_pesquisa> listaConfigPesquisa = ( List<Config_pesquisa> ) this.getDao().getSession().createCriteria(Config_pesquisa.class)
				.add(Restrictions.eq("cd_usuario", cd_usuario))
				.add(Restrictions.eq("cd_programa", cd_programa))
				.list();
		
		for(Config_pesquisa c : listaConfigPesquisa){
			CrudGenericBO crud = new CrudGenericBO();
			try {
				crud.excluirObjetoCompleto( c );
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	public Boolean salvarPermissoesPadrao( Integer cd_usuario , Integer cd_unidade) {

		this.setDao(new GenericDAO());
		List<Permissao_padrao> listaPermissoesPadrao = (List<Permissao_padrao>) this.getDao().getSession().createCriteria(Permissao_padrao.class).list();
		for(Permissao_padrao p : listaPermissoesPadrao){
			inserePermissao( criaPermissao( p.getCd_programa(), cd_usuario, cd_unidade) );	
		}
		
//		
//		inserePermissao( criaPermissao( 6, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 11, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 12, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 13, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 14, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 15, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 16, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 26, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 27, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 28, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 29, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 91, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 31, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 33, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 34, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 38, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 39, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 40, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 42, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 43, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 44, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 49, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 50, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 51, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 53, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 54, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 57, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 59, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 60, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 64, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 65, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 66, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 68, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 69, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 101, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 106, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 109, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 110, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 111, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 105, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 113, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 114, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 115, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 19, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 93, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 20, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 102, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 98, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 92, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 18, cd_usuario, cd_unidade) );
//		inserePermissao( criaPermissao( 78, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 22, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 104, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 17, cd_usuario, cd_unidade) ); 
//		inserePermissao( criaPermissao( 116, cd_usuario, cd_unidade) );


		return true;
	}
	
	private Permissao_acesso criaPermissao(Integer cd_programa, Integer cd_usuario, Integer cd_unidade){
		Permissao_acesso p = new Permissao_acesso();
		p.setCd_programa(cd_programa);
		p.setCd_usuario(cd_usuario);
		p.setCd_unidade(cd_unidade);
		p.setTp_editar(1);
		p.setTp_especial(1);
		p.setTp_excluir(1);
		p.setTp_salvar(1);
		p.setTp_visualizar(1);
		return p;
	}
	
	private Boolean inserePermissao(Permissao_acesso p){
		try{
			
			CrudGenericBO crud = new CrudGenericBO();
			crud.salvar(p);
		
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public User buscarUsuarioJHipster(Integer cd_usuario) {
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		Usuario u = this.buscarUsuarioPorId(cd_usuario);
		if (this.getDao() == null) {
			this.setDao(new GenericDAO());
		}
		pesquisa.setDao(this.getDao());
		u = (Usuario) pesquisa.buscarObjetoCompleto(u);
		System.out.println(u.getUser().getLogin());
		
		Set<Authority> authorities = new HashSet<>();
		List<Authority> listaAux = (List<Authority>) this.getDao().getSession().createCriteria(Authority.class).list();
		authorities.addAll(listaAux);
		
		u.getUser().setAuthorities(authorities);
		return u.getUser();
	}

	public Optional<User> findOneByLogin(String lowerCase) {
		return Optional.ofNullable(new User())	  
                .map(user -> this.efetuarLogin(lowerCase));
	}

	public Optional<User> findOneByEmail(String lowerCase) {
		Integer id = new Integer(lowerCase);
		return Optional.ofNullable(new User())	  
                .map(user -> this.buscarUsuarioJHipster(id));
	}

	public Optional<User> findOneByActivationKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<User> findOneByResetKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Authority findOneAuth(String authority) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime minusDays) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<PersistentToken> findByUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object delete(String decodedSeries) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PersistentToken> findPersistTokenByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(User u) {
		// TODO Auto-generated method stub
		
	}
}