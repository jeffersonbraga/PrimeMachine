package br.com.primum.bo.notificacoes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.helpdesk.HelpdeskBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.bo.plano_acao.Plano_acao_BO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.acao_corretiva_helpdesk.Acao_corretiva_helpdesk;
import br.com.primum.modelo.acao_plano_acao_hp.Acao_plano_acao_hp;
import br.com.primum.modelo.atividade_acao_pl_hp.Atividade_acao_pl_hp;
import br.com.primum.modelo.atividades_acao_imediata.Atividades_acao_imediata;
import br.com.primum.modelo.categoria_ocorrencia.Categoria_ocorrencia;
import br.com.primum.modelo.causa_efeito.Causa_efeito;
import br.com.primum.modelo.checagem_documento.Checagem_documento;
import br.com.primum.modelo.configuracao_helpdesk.Configuracao_helpdesk;
import br.com.primum.modelo.diagrama_helpdesk.Diagrama_helpdesk;
import br.com.primum.modelo.documento.Documento;
import br.com.primum.modelo.escalonamento_formulario.Escalonamento_formulario;
import br.com.primum.modelo.escalonamento_usuario.Escalonamento_usuario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.modelo.notificacao.Notificacao;
import br.com.primum.modelo.notificacao_automatica.Notificacao_automatica;
import br.com.primum.modelo.notificacao_usuario.Notificacao_usuario;
import br.com.primum.modelo.obj_notificacao.Obj_notificacao;
import br.com.primum.modelo.ocorrencia_periodicidade.Ocorrencia_periodicidade;
import br.com.primum.modelo.permissao_acesso_documento.Permissao_acesso_documento;
import br.com.primum.modelo.plano_acao.Plano_acao;
import br.com.primum.modelo.usuario.Usuario;

public class NotificacoesBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	public Obj_notificacao getAllNotificacoes(Integer cd_usuario){

		this.setDao(new GenericDAO());
		Usuario usuario = (Usuario) this.getDao().getSession().createCriteria(Usuario.class).add(Restrictions.eq("cd_usuario", cd_usuario)).uniqueResult();
		
		if ( (usuario.getGestor_qualidade() == null) || (usuario.getGestor_qualidade().equals(0)) ) {
			usuario = null;
		}
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		Integer cont=0;

		List<Documento> listaDoc = (List<Documento>) this.getDao().getSession().createCriteria(Documento.class).list();
		for(Documento doc:listaDoc){
			try{

				pesquisa.buscarObjetoCompleto(doc);

				if( (doc.getTp_disponibilidade().equals(0)) || ((doc.getTp_disponibilidade().equals(1)) && (doc.getListaPermissoes()!=null))){
					Boolean temPermissao=false;
					if(doc.getNotificacao()!=null){
						for(Notificacao_usuario notify : doc.getNotificacao().getLista_notificacao_usuario() ){

							if (doc.getTp_disponibilidade().equals(1)) {
								for(Permissao_acesso_documento permissao:doc.getListaPermissoes()){
									if( notify.getCd_usuario().equals(cd_usuario) 
											&& notify.getCd_usuario().equals(permissao.getCd_colaborador()) 
											&& notify.getSt_visualizado().equals(0) 
											&& notify.getSt_notificar().equals(doc.getSt_documento()) ){
										temPermissao=true;
										break;
									}
								}

								if(temPermissao){
									cont+=1;
									temPermissao=false;
								}
							}else {
								if( notify.getCd_usuario().equals(cd_usuario) 

										&& ( (notify.getSt_notificar() == null) || (notify.getSt_notificar().equals(5)))){

									if (notify.getSt_visualizado() == null || notify.getSt_visualizado().equals(0)) {
										cont+=1;
										break;	
									} else {
										if(doc.getListaChecagem_documento() != null){
											Checagem_documento checkAux = null;
								    		for (Checagem_documento check : doc.getListaChecagem_documento()) {

												if ( (check.getCd_usuario().equals(cd_usuario)) && (check.getSt_checagem_documento() == null) && (check.getTp_checagem_documento() == null) ) {
													cont+=1;
												} else if ( (check.getCd_usuario().equals(cd_usuario)) && (check.getSt_checagem_documento() == null) && (check.getTp_checagem_documento() != null) && ( (checkAux != null) && (checkAux.getSt_checagem_documento() != null) && (checkAux.getSt_checagem_documento().equals(1))) ) {
													cont+=1;
												}
												checkAux = check;
											}			
										}
									}
								}
								
								
								if( notify.getCd_usuario().equals(cd_usuario) && notify.getSt_notificar() !=null && doc.getSt_documento().equals(notify.getSt_notificar())){
									if (notify.getSt_visualizado() == null || notify.getSt_visualizado().equals(0)) {
										cont+=1;
									} 
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			boolean contar = false;			
			if (doc.getSt_documento().equals(4) && doc.getCd_responsavel_criar().equals(cd_usuario)) {
				contar = true;
			} 

			if( (doc.getSetor() != null) && (!contar) ){
				//RESPONSÁVEL DO SETOR
				if(doc.getSetor().getCd_responsavel_setor().equals(cd_usuario)){
					//SE SITUAÇÃO É REPROVADA
					if(doc.getSt_documento().equals(3)){
						contar = true;
					} else if ( doc.getSt_documento().equals(4) ) {
						//contar = true;
					} else if ( doc.getSt_documento().equals(1) &&
							(doc.getCd_resp_setor() == null) ) {
						contar = true;
					}
				}
			}

			if ( (usuario != null) && (doc.getSt_documento().equals(0)) && (!contar) ) {
				//RESPONSÁVEL DA QUALIDADE
				contar = true;
			}

			if (contar) {
				cont+=1;
			}
		}

		Obj_notificacao objRetorno = new Obj_notificacao();
		objRetorno.setNotificacoesDocumentos(cont);
		getAllNotificacaoHelpDesk(cd_usuario, objRetorno);
		getAllNotificacaoAutomatica(cd_usuario, objRetorno);
		getAllNotificacoesAcoesAtividadesHD(cd_usuario, objRetorno);
		getAllNotificacoesHelDeskEnviado(cd_usuario, objRetorno);
		getAllNotificacoesDiagrama(cd_usuario, objRetorno);
		getAllNotificacoesPlanoAcao(cd_usuario, objRetorno);
		getAllNotificacoesPeriodicidadePorSituacao(cd_usuario, objRetorno);

		return objRetorno;
	}

	public void getAllNotificacoesPlanoAcao(Integer cd_usuario, Obj_notificacao obj){
		Plano_acao_BO plano = new Plano_acao_BO();

		List<Plano_acao> listaEnviados = new ArrayList<>();
		List<Plano_acao> listaRecebidos = new ArrayList<>();

		try {
			listaEnviados = plano.buscarPlano_acao(cd_usuario, 0);
			listaRecebidos = plano.buscarPlano_acao(cd_usuario, 1);

			for(Plano_acao p: listaEnviados){
				if(p.getSt_plano_acao()!=null){
					if(p.getSt_plano_acao().equals(2)  && p.getCd_usuario_validacao() == null){
						obj.setNotificacoesEnviadas( obj.getNotificacoesEnviadas() + 1);		
					}
				}
			}

			for(Plano_acao p : listaRecebidos){
				if(p.getSt_plano_acao()!=null){
					if(!p.getSt_plano_acao().equals(2)){
						obj.setNotificacoesRecebidas( obj.getNotificacoesRecebidas() + 1); 		
					}
				}
			}

			Usuario u = (Usuario)this.getDao().getSession().createCriteria(Usuario.class).
					add(Restrictions.eq("cd_usuario", cd_usuario)).uniqueResult();

			if(u!=null){
				if(u.getCd_profissao()!=null){
					if(u.getCd_profissao().equals(1)){
						List<?> listaFeeds = new ArrayList<>();
						listaFeeds = plano.buscarPlano_acaoFeed();
						obj.setNotificacoesFeed(obj.getNotificacoesFeed() + listaFeeds.size());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void getAllNotificacoesPeriodicidadePorSituacao(Integer cd_usuario, Obj_notificacao obj){

		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Formulario> listaFormulariosSituacao = new ArrayList<>();
		List<Escalonamento_usuario> listaFormulariosUsuario = new ArrayList<>();
		List<Escalonamento_formulario> listaEscalonamentosSituacao = new ArrayList<>();

		List<Ocorrencia_periodicidade> listaPeriodicidadeSituacao = (List<Ocorrencia_periodicidade>) this.getDao().getSession().createCriteria(Ocorrencia_periodicidade.class).list();
		List<Ocorrencia_periodicidade> listaPeriodicidadeSituacaoAux = new ArrayList<>(); 
		for(Ocorrencia_periodicidade p: listaPeriodicidadeSituacao){
			if(new Date().before(p.getDt_expirar()) && new Date().after(p.getDt_ocorrencia_periodicidade())){
				listaPeriodicidadeSituacaoAux.add(p);
			}
		}
		listaPeriodicidadeSituacao = new ArrayList<>();
		listaPeriodicidadeSituacao.addAll(listaPeriodicidadeSituacaoAux);

		for(Ocorrencia_periodicidade p: listaPeriodicidadeSituacao){
			List<Formulario> listaFormulariosSituacaoAux = (List<Formulario>) this.getDao().getSession().createCriteria(Formulario.class)
					.add(Restrictions.eq("cd_periodicidade", p.getCd_periodicidade())).list();

			listaFormulariosSituacao.addAll(listaFormulariosSituacaoAux);
		}

		for(Formulario f: listaFormulariosSituacao){
				List<Escalonamento_formulario> listaEscAux = (List<Escalonamento_formulario>) this.getDao().getSession().createCriteria(Escalonamento_formulario.class).add(Restrictions.eq("cd_formulario", f.getCd_formulario())).list();
				listaEscalonamentosSituacao.addAll(listaEscAux);
		}

		for(Escalonamento_formulario esc:listaEscalonamentosSituacao){
			pesquisa.buscarObjetoCompleto(esc);
		}

		for(Escalonamento_formulario esc:listaEscalonamentosSituacao){
			for(Escalonamento_usuario esc_usuario: esc.getListaEscalonamento_usuario()){
				if(esc_usuario.getCd_usuario().equals(cd_usuario)){
					listaFormulariosUsuario.add(esc_usuario);
				}
			}
		}
		obj.setNotificacoesFeed(obj.getNotificacoesFeed() + listaFormulariosUsuario.size()); 

		/*String sql = "select distinct formulario.cd_formulario from formulario INNER JOIN escalonamento_usuario on escalonamento_usuario.cd_usuario = "+cd_usuario +" AND escalonamento_usuario.cd_escalonamento_formulario escalonamento_formulario on escalonamento_formulario.cd_formulario = formulario.cd_formulario "+
					"INNER JOIN ocorrencia_periodicidade on ocorrencia_periodicidade.cd_periodicidade = ";*/
	}

	@SuppressWarnings("unchecked")
	public void getAllNotificacoesAcoesAtividadesHD(Integer cd_usuario, Obj_notificacao obj){
		this.setDao(new GenericDAO());
		Integer numero=0;
		List<Acao_corretiva_helpdesk> listaAcoesCorretivas = (List<Acao_corretiva_helpdesk>) this.getDao().getSession()
				.createCriteria(Acao_corretiva_helpdesk.class)
				.add(Restrictions.eq("cd_responsavel_acao", cd_usuario))
				.add(Restrictions.ne("st_status", 2))
				.list();

		List<Atividades_acao_imediata> listaAtividades = (List<Atividades_acao_imediata>) this.getDao().getSession()
				.createCriteria(Atividades_acao_imediata.class)
				.add(Restrictions.eq("cd_responsavel", cd_usuario))
				.add(Restrictions.ne("st_status", 2))
				.list();

		if(listaAcoesCorretivas!=null){
			numero = numero+ listaAcoesCorretivas.size();
		} 
		if(listaAtividades!=null){
			numero = numero+ listaAtividades.size();
		} 

		getAllNotificacoesAcoesAtividadesPL_HD(cd_usuario, obj);

		obj.setNotificacoesRecebidas( obj.getNotificacoesRecebidas() + numero );
	}

	@SuppressWarnings("unchecked")
	public void getAllNotificacoesAcoesAtividadesPL_HD(Integer cd_usuario, Obj_notificacao obj){
		this.setDao(new GenericDAO());
		Integer numero=0;
		List<Acao_plano_acao_hp> listaAcoesCorretivas = (List<Acao_plano_acao_hp>) this.getDao().getSession()
				.createCriteria(Acao_plano_acao_hp.class)
				.add(Restrictions.eq("cd_usuario_responsavel", cd_usuario))
				.add(Restrictions.ne("st_acao_plano_acao_hp", 2))
				.list();

		List<Atividade_acao_pl_hp> listaAtividades = (List<Atividade_acao_pl_hp>) this.getDao().getSession()
				.createCriteria(Atividade_acao_pl_hp.class)
				.add(Restrictions.eq("cd_usuario_responsavel", cd_usuario))
				.add(Restrictions.ne("st_atividade_acao_pl_hp", 2))
				.list();

		if(listaAtividades!=null){
			numero = numero+ listaAtividades.size();
		} 

		if(listaAcoesCorretivas!=null){
			numero = numero+ listaAcoesCorretivas.size();
		} 

//		if(listaAcoesCorretivas!=null){
//			for(Acao_plano_acao_hp a : listaAcoesCorretivas){
//				Plano_acao_hp p = (Plano_acao_hp)this.getDao().getSession()
//				.createCriteria(Plano_acao_hp.class)
//				.add(Restrictions.eq("cd_plano_acao_hp", a.getCd_plano_acao_hp()))
//				.uniqueResult();
//				if(p!=null){
//					if(p.getCd_usuario_validacao() == null){
//						numero = numero + 1;
//					}
//				}
//			}
//			//numero = numero+ listaAcoesCorretivas.size();
//		} 
		
		
//		List<Plano_acao_hp> listaPlanos = (List<Plano_acao_hp>) this.getDao().getSession()
//				.createCriteria(Plano_acao_hp.class)
//				//.add(Restrictions.eq("cd_usuario_abrir", cd_usuario))
//				.add(Restrictions.eq("st_plano_acao", 2))
//				.add(Restrictions.isNull("cd_usuario_validacao"))
//				.list();
//				
//		if(listaPlanos!=null){
//			for(Plano_acao_hp pHD : listaPlanos){
//				HelpdeskBO hBO = new HelpdeskBO();
//				Helpdesk_ocorrencia hd = hBO.getCodigoHDPelaCausa( pHD.getCd_causa_efeito() );
//				if( hd.getCd_classificacao_helpdesk().equals(1) ||  hd.getCd_classificacao_helpdesk().equals(2) ){
//					
//					List<Configuracao_helpdesk> listaConfiguracaoHelpdesk =  ( List<Configuracao_helpdesk> ) getDao().getSession().createCriteria(Configuracao_helpdesk.class)
//							.add(Restrictions.eq("cd_classificacao_helpdesk", hd.getCd_classificacao_helpdesk())).list();
//					for(Configuracao_helpdesk conf : listaConfiguracaoHelpdesk ){
//						if( conf.getCd_colaborador().equals( cd_usuario )){
//							numero = numero + 1;	
//						}
//					}
//					
//				}else{
//					if(pHD.getCd_usuario_abrir().equals( cd_usuario )){
//						numero = numero + 1;	
//					}
//					
//				}
//			}
			//numero = numero+ listaPlanos.size();
//		} 
		
		obj.setNotificacoesRecebidas( obj.getNotificacoesRecebidas() + numero );
	}
	
	@SuppressWarnings("unchecked")
	public void getAllNotificacaoHelpDesk(Integer cd_usuario, Obj_notificacao obj){
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		//SE A PESSOA LOGADA É RESPONSAVEL POR ALGUMA CLASSIFICACAO
		List<Configuracao_helpdesk> listaConfiguracaoHelpdesk =  ( List<Configuracao_helpdesk> ) getDao().getSession().createCriteria(Configuracao_helpdesk.class)
				.add(Restrictions.eq("cd_colaborador", cd_usuario)).list();
		
		if(listaConfiguracaoHelpdesk!=null){
			for(Configuracao_helpdesk conf : listaConfiguracaoHelpdesk){
				List<Helpdesk_ocorrencia> listaHelpdesk = new ArrayList<>();
				
				if(!conf.getCd_classificacao_helpdesk().equals(1) && !conf.getCd_classificacao_helpdesk().equals(2)){
					if(conf.getCd_natureza_ocorrencia() == null){
							
						listaHelpdesk =  (List<Helpdesk_ocorrencia>) getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
								.add(Restrictions.eq("cd_classificacao_helpdesk", conf.getCd_classificacao_helpdesk()))
								.add(Restrictions.le("st_helpdesk_ocorrencia", 1 ))
								.list();
					
						if(listaHelpdesk.size() > 0){
							obj.setNotificacoesHelpDesk( obj.getNotificacoesHelpDesk() + listaHelpdesk.size() );
						}
					}else{
						
						listaHelpdesk =  (List<Helpdesk_ocorrencia>) getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
								.add(Restrictions.eq("cd_classificacao_helpdesk", conf.getCd_classificacao_helpdesk()))
								.add(Restrictions.eq("cd_natureza_ocorrencia", conf.getCd_natureza_ocorrencia() ))
								.add(Restrictions.le("st_helpdesk_ocorrencia", 1 ))
								.list();
					
						if(listaHelpdesk.size() > 0){
							obj.setNotificacoesHelpDesk( obj.getNotificacoesHelpDesk() + listaHelpdesk.size() );
						}
					}
				}
			}
		}
		
		/************************************************/
		/*** PARA REGISTROS DE AMBIENTAL OU QUALIDADE ***/
		
		List<Helpdesk_ocorrencia> listaHelpdesk =  ( List<Helpdesk_ocorrencia> ) getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
		.add(Restrictions.le("cd_classificacao_helpdesk", 2))
		.add(Restrictions.le("st_helpdesk_ocorrencia", 1))
		.list();
		
		if(listaHelpdesk!=null){
			for(Helpdesk_ocorrencia h : listaHelpdesk ){
				
				HelpdeskBO hBO = new HelpdeskBO();
				Integer responsavel = hBO.retornaResponsavel( h );
				if(responsavel != null){
					if( responsavel.equals( cd_usuario ) ){
						obj.setNotificacoesHelpDesk( obj.getNotificacoesHelpDesk() + 1 );
					}
					
				}
			}
		}
		
		List<Helpdesk_ocorrencia> listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.le("cd_classificacao_helpdesk", 2))
				.add(Restrictions.eq("cd_resp_resolucao", cd_usuario))
				.add(Restrictions.ge("st_helpdesk_ocorrencia", 5)).list();
		for(Helpdesk_ocorrencia h : listaHDs ){
			pesquisa.buscarObjetoCompleto( h );
			if(h.getDiagrama_helpdesk()!=null){
				if(h.getDiagrama_helpdesk().getSt_diagrama_helpdesk() ==null){
					obj.setNotificacoesRecebidas( obj.getNotificacoesRecebidas() + 1 );
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void getAllNotificacoesDiagrama(Integer cd_usuario, Obj_notificacao obj){
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		Usuario u  = ( Usuario ) this.getDao().getSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("cd_usuario", cd_usuario)).uniqueResult();
		
		List<Diagrama_helpdesk> listaDiagrama = (List<Diagrama_helpdesk>) this.getDao().getSession().createCriteria(Diagrama_helpdesk.class)
				//.add(Restrictions.eq("cd_usuario_diagrama", cd_usuario))
				.add(Restrictions.le("st_diagrama_helpdesk", 1))
				.list();
		
		Boolean diagramaProntoPraAnalise = true;
		
		for(Diagrama_helpdesk d : listaDiagrama){
			

//			Helpdesk_ocorrencia hd = null;
//			if(d.getCd_helpdesk_ocorrencia() != null){
//				hd = ( Helpdesk_ocorrencia ) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
//						.add(Restrictions.eq("cd_helpdesk_ocorrencia", d.getCd_helpdesk_ocorrencia())).uniqueResult();
//			}else{
//				HelpdeskBO hBO = new HelpdeskBO();
//				hd = ( Helpdesk_ocorrencia ) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
//						.add(Restrictions.eq("cd_helpdesk_ocorrencia", hBO.getHDRecursivo(d.getCd_diagrama_helpdesk()).getCd_helpdesk_ocorrencia())).uniqueResult();
//			}
			
			pesquisa.buscarObjetoCompleto( d );
			for(Categoria_ocorrencia cat : d.getListaCategoria_ocorrencia()){
				for(Causa_efeito causa : cat.getListaCausas()){
					
					if(causa.getSt_causa_efeito().equals(1) || causa.getSt_causa_efeito().equals(2) ){
						if( causa.getPlano_acao() != null){
							if(!causa.getPlano_acao().getSt_plano_acao().equals(2)){
								diagramaProntoPraAnalise = false;
								break;
							}
						}else{
							diagramaProntoPraAnalise = false;
							break;
						}
					}
				}
				if(!diagramaProntoPraAnalise){
					break;
				}
			}
			if(diagramaProntoPraAnalise){
				
//				Diagrama_helpdesk diagrama = ( Diagrama_helpdesk ) this.getDao().getSession().createCriteria(Diagrama_helpdesk.class)
//						.add(Restrictions.eq("cd_diagrama_origem", d.getCd_diagrama_helpdesk())).uniqueResult();
				
//				if(d != null && d.getCd_diagrama_helpdesk() == null){
					
					if(d.getSt_diagrama_helpdesk().equals(0)){
						if(u.getGestor_qualidade() != null && u.getGestor_qualidade().equals(1)){
							if(u.getCd_usuario().equals(cd_usuario)){
								obj.setNotificacoesRecebidas(obj.getNotificacoesRecebidas() + 1);
							}
						}
					}else{
						
						if(d.getCd_usuario_diagrama().equals(cd_usuario)){
							if( d.getSt_diagrama_helpdesk() == null ){
								obj.setNotificacoesRecebidas(obj.getNotificacoesRecebidas() + 1);
							}
						}
					}
				
//				}
				
//				if( hd != null){
//					if( hd.getCd_classificacao_helpdesk().equals(1) || hd.getCd_classificacao_helpdesk().equals(2) ){
//						if(hd.getCd_resp_resolucao().equals(cd_usuario)){
//							obj.setNotificacoesRecebidas(obj.getNotificacoesRecebidas() + 1);
//						}
//					}else{
//						if(d.getCd_usuario_diagrama().equals(cd_usuario)){
//							obj.setNotificacoesRecebidas(obj.getNotificacoesRecebidas() + 1);
//						}
//					}
//				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void getAllNotificacoesHelDeskEnviado(Integer cd_usuario, Obj_notificacao obj){
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		//SE A PESSOA LOGADA É RESPONSAVEL POR ALGUMA CLASSIFICACAO
//		List<Configuracao_helpdesk> listaConfiguracaoHelpdesk =  ( List<Configuracao_helpdesk> ) getDao().getSession().createCriteria(Configuracao_helpdesk.class)
//				.add(Restrictions.eq("cd_colaborador", cd_usuario)).list();
//		
//		if(listaConfiguracaoHelpdesk!=null){
//			for(Configuracao_helpdesk conf : listaConfiguracaoHelpdesk){
//				
//				List<Helpdesk_ocorrencia> listaHelpdesk =  (List<Helpdesk_ocorrencia>) getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
//						.add(Restrictions.eq("cd_classificacao_helpdesk", conf.getCd_classificacao_helpdesk()))
//						.add(Restrictions.eq("st_helpdesk_ocorrencia", 3 ))
//						.list();
//				if(listaHelpdesk.size() > 0 ){
//					obj.setNotificacoesRecebidas( obj.getNotificacoesRecebidas() + listaHelpdesk.size() );
//				}
//			}
//		}
		
		List<Helpdesk_ocorrencia> listaHelpdesk = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.eq("cd_resp_resolucao", cd_usuario))
				.add(Restrictions.eq("st_helpdesk_ocorrencia", 3 ))
				.add(Restrictions.gt("cd_classificacao_helpdesk", 2 ))
				.list();
		
		/************************************************/
		/*** PARA REGISTROS DE AMBIENTAL OU QUALIDADE ***/
		List<Helpdesk_ocorrencia> listaHelpdeskAmbientalQualidade =  ( List<Helpdesk_ocorrencia> ) getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.le("cd_classificacao_helpdesk", 2))
				.add(Restrictions.eq("st_helpdesk_ocorrencia", 3))
				.list();
				
		if(listaHelpdeskAmbientalQualidade!=null){
			for(Helpdesk_ocorrencia h : listaHelpdeskAmbientalQualidade ){
				
				List<Configuracao_helpdesk> listaConfiguracaoHelpdesk =  ( List<Configuracao_helpdesk> ) getDao().getSession().createCriteria(Configuracao_helpdesk.class)
						.add(Restrictions.eq("cd_classificacao_helpdesk", h.getCd_classificacao_helpdesk())).list();
				for(Configuracao_helpdesk conf : listaConfiguracaoHelpdesk ){
					if( conf.getCd_colaborador() != null){
						if( conf.getCd_colaborador().equals( cd_usuario )){
							obj.setNotificacoesRecebidas( obj.getNotificacoesRecebidas() + 1 );
						}
					}
				}
			}
		}
		
		if(listaHelpdesk.size() > 0 ){
			obj.setNotificacoesRecebidas( obj.getNotificacoesRecebidas() + listaHelpdesk.size() );
		}

		List<Helpdesk_ocorrencia> listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.le("cd_classificacao_helpdesk", 2))
				.add(Restrictions.gt("st_helpdesk_ocorrencia", 4))
				.add(Restrictions.eq("cd_resp_resolucao", cd_usuario)).list();

		if(listaHDs!=null){
			for(Helpdesk_ocorrencia hd : listaHDs ){
				pesquisa.buscarObjetoCompleto(hd);
				if(hd.getDiagrama_helpdesk() != null){
					if((hd.getDiagrama_helpdesk() != null) && (hd.getDiagrama_helpdesk().getListaCategoria_ocorrencia()!=null && hd.getDiagrama_helpdesk().getListaCategoria_ocorrencia().size()>0)){
						if(hd.getDiagrama_helpdesk().getSt_diagrama_helpdesk() == null ){
							obj.setNotificacoesRecebidas( obj.getNotificacoesRecebidas() + 1 );
						}
					}
				}
			}
		}

		/**************************/
		/**************************/
		//SE A PESSOA LOGADA FOI QUEM REGISTROU A OCORRÊNCIA
		List<Helpdesk_ocorrencia> listaHelpdeskEnviados =  (List<Helpdesk_ocorrencia>) getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.eq("cd_responsavel_registro", cd_usuario ))
				.add(Restrictions.ge("st_helpdesk_ocorrencia", 4))
				.add(Restrictions.ne("cd_classificacao_helpdesk", 1))
				.add(Restrictions.ne("cd_classificacao_helpdesk", 2))
				.add(Restrictions.isNull("tp_parecer_reclamante"))
				.list();
		
		for(Helpdesk_ocorrencia hd : listaHelpdeskEnviados){
			pesquisa.buscarObjetoCompleto(hd);
			
			if(hd.getSt_helpdesk_ocorrencia().equals(4)){
				obj.setNotificacoesEnviadas( obj.getNotificacoesEnviadas() + 1 );
			}else{

//				Integer qtdePlanos = 0;
//				Integer planosFinalizados = 0;
//				
//				if(hd.getListaCategoria_ocorrencia().size()>0){
//					for(Categoria_ocorrencia cat: hd.getListaCategoria_ocorrencia()){
//						if(cat.getListaCausas().size()>0){
//							for(Causa_efeito causa: cat.getListaCausas()){
//								if(causa.getSt_causa_efeito().equals(1) || causa.getSt_causa_efeito().equals(2)){
//									if(causa.getPlano_acao()!=null){
//										qtdePlanos+=1;
//										if(causa.getPlano_acao().getSt_plano_acao().equals(2) && causa.getPlano_acao().getCd_usuario_validacao()!=null){
//											planosFinalizados+=1;
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//				
//				if(qtdePlanos>0 && qtdePlanos == planosFinalizados){
//					obj.setNotificacoesEnviadas( obj.getNotificacoesEnviadas() + 1 );
//				}
			
			}

		}
	}
	
	@SuppressWarnings("unchecked")
	public Obj_notificacao getAllNotificacaoAutomatica(Integer cd_usuario, Obj_notificacao obj){
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		

		//PARTE DE NOTIFICACAO CRUD
		List<Notificacao_automatica> listaNotificacaoAuto = (List<Notificacao_automatica>) this.getDao().getSession().createCriteria(Notificacao_automatica.class).
				add(Restrictions.isNotNull("cd_operacao_notificacao")).
				list();
			
		for(Notificacao_automatica not : listaNotificacaoAuto){
			pesquisa.buscarObjetoCompleto(not);
			
			for(Notificacao_usuario not_usu : not.getListaNotificacaoUsuario()){
				if(not_usu.getCd_usuario().equals(cd_usuario) && not_usu.getCd_notificacao()!=null){
					
					if(not.getCd_operacao_notificacao().equals(1) 
							|| not.getCd_operacao_notificacao().equals(2) 
							|| not.getCd_operacao_notificacao().equals(3)){
						obj.setNotificacoesAutomaticasCRUD( obj.getNotificacoesAutomaticasCRUD() + 1 );
					}
				}
			}
			
		}
		
		/*******************************/
		
		
		//PARTE DE NOTIFICACAO DA TAREFA AGENDADA
		List<Notificacao> listaNotificacaoTarefas = ( List<Notificacao> ) this.getDao().getSession().createCriteria(Notificacao.class)
				.add(Restrictions.isNull("cd_tipo_notificacao"))
				.add(Restrictions.isNull("cd_codigo_parametro"))
				.add(Restrictions.isNull("tp_operacao"))
				.list();
			
		for(Notificacao notificacao : listaNotificacaoTarefas){
			List<Notificacao_usuario> listaNotificacaoUsuarios = (List<Notificacao_usuario>) this.getDao().getSession().createCriteria(Notificacao_usuario.class).
					add(Restrictions.eq("cd_notificacao",notificacao.getCd_notificacao())).
					add(Restrictions.eq("cd_usuario", cd_usuario )).
					list();
			
			if(listaNotificacaoUsuarios!=null)
				obj.setNotificacoesAutomaticas(obj.getNotificacoesAutomaticas() + listaNotificacaoUsuarios.size());
		}
		
		return obj;
	}
	
	
	@SuppressWarnings("unchecked")
	public Boolean setNotificacoesDocumentoVisualizadas(Integer cd_usuario){
		
		Boolean resultado = true;
		this.setDao(new GenericDAO());
		List<Documento> listaDoc = (List<Documento>) this.getDao().getSession().createCriteria(Documento.class).list();
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		CrudGenericBO crud = new CrudGenericBO();
		/**for(Documento doc:listaDoc){

			pesquisa.buscarObjetoCompleto(doc);

			if( (doc.getTp_disponibilidade().equals(0)) || ((doc.getTp_disponibilidade().equals(1)) && (doc.getListaPermissoes()!=null))){
				Boolean temPermissao=false;
				if(doc.getNotificacao()!=null){
					for(Notificacao_usuario notify : doc.getNotificacao().getLista_notificacao_usuario() ){

						if (doc.getTp_disponibilidade().equals(1)) {
							for(Permissao_acesso_documento permissao:doc.getListaPermissoes()){
								if( notify.getCd_usuario().equals(cd_usuario) 
										&& notify.getCd_usuario().equals(permissao.getCd_colaborador()) 
										&& notify.getSt_visualizado().equals(0) 
										&& notify.getSt_notificar().equals(doc.getSt_documento()) ){
										*/
		for(Documento doc:listaDoc){

			pesquisa.buscarObjetoCompleto(doc);

			if( (doc.getTp_disponibilidade().equals(0)) || ((doc.getTp_disponibilidade().equals(1)) && (doc.getListaPermissoes()!=null))){
				if(doc.getNotificacao()!=null){
					for(Notificacao_usuario notify : doc.getNotificacao().getLista_notificacao_usuario() ){
						if( doc.getTp_disponibilidade().equals(1) ){
							for(Permissao_acesso_documento permissao:doc.getListaPermissoes()){
								if( notify.getCd_usuario().equals(cd_usuario) 
									&& notify.getCd_usuario().equals(permissao.getCd_colaborador()) 
									&& notify.getSt_visualizado().equals(0) 
									&& notify.getSt_notificar().equals(doc.getSt_documento()) ){
	
									notify.setSt_visualizado(1);
	
									try {
										crud.atualizar(notify);
									} catch (Exception e) {
										resultado=false;
										e.printStackTrace();
									}
	
									break;
								}
							}
						}else{
							if( notify.getCd_usuario().equals(cd_usuario)
								&& notify.getSt_visualizado().equals(0) 
								&& notify.getSt_notificar().equals(doc.getSt_documento()) ){

								notify.setSt_visualizado(1);

								try {
									crud.atualizar(notify);
								} catch (Exception e) {
									resultado=false;
									e.printStackTrace();
								}
							}
						}
						
						for (Checagem_documento check : doc.getListaChecagem_documento()) {
							if( notify.getCd_usuario().equals(cd_usuario) 
									&& notify.getCd_usuario().equals(check.getCd_usuario()) 
									&& ((notify.getSt_notificar() == null) || (notify.getSt_notificar().equals(doc.getSt_documento())))){

								notify.setSt_visualizado(1);

								try {
									crud.atualizar(notify);
								} catch (Exception e) {
									resultado=false;
									e.printStackTrace();
								}

								break;
							}
						}
					}
				}
			}
		}
		
		return resultado;
	}
	
}