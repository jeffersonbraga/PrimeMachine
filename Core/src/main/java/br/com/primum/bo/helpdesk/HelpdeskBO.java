package br.com.primum.bo.helpdesk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.crud.CrudGenericBO;
//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.bo.email.EmailBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.bo.relatorios.RelatorioBO;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.acao_corretiva_helpdesk.Acao_corretiva_helpdesk;
import br.com.primum.modelo.acao_plano_acao_hp.Acao_plano_acao_hp;
import br.com.primum.modelo.atividade_acao_pl_hp.Atividade_acao_pl_hp;
import br.com.primum.modelo.atividades_acao_imediata.Atividades_acao_imediata;
import br.com.primum.modelo.categoria_ocorrencia.Categoria_ocorrencia;
import br.com.primum.modelo.causa_efeito.Causa_efeito;
import br.com.primum.modelo.configuracao_helpdesk.Configuracao_helpdesk;
import br.com.primum.modelo.dbconf.Dbconf;
import br.com.primum.modelo.diagrama_helpdesk.Diagrama_helpdesk;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.modelo.plano_acao_hp.Plano_acao_hp;
import br.com.primum.modelo.setor.Setor;
import br.com.primum.modelo.unidade.Unidade;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.notificadores.Helpdesk_notificador;

public class HelpdeskBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Configuracao_helpdesk> ListaConfiguracoesHelpdesk(){
	
		this.setDao(new GenericDAO());
		List<Configuracao_helpdesk> listaConfiguracoes = (List<Configuracao_helpdesk>) this.getDao().getSession().createCriteria(Configuracao_helpdesk.class).list();
		
		return listaConfiguracoes;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean setResponsavelChamado(Helpdesk_ocorrencia hd, Integer cd_usuario){
		
		this.setDao(new GenericDAO());
		//CrudGenericBO crud = new CrudGenericBO();
		try {
			
			hd.setTp_notificados(1);
			hd.setCd_resp_resolucao(cd_usuario);
			
			this.getDao().atualizar(hd);
			
			String nomeUsuario = RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", hd.getCd_resp_resolucao().toString());
			String nomeColaborador = RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", hd.getCd_responsavel_registro().toString());

			if(nomeUsuario != null){
				if( nomeUsuario.split(" ").length > 1){
					nomeUsuario = nomeUsuario.split(" ")[0];
				}
			}
			
			if(nomeColaborador != null){
				if( nomeColaborador.split(" ").length > 1){
					nomeColaborador = nomeColaborador.split(" ")[0];
				}
			}

			Helpdesk_notificador.enviaEmailParaPessoa( hd.getCd_responsavel_registro(), "NOTIFICAÇÃO DE ADOÇÃO DE OCORRÊNCIA", "Olá <b>"+ nomeColaborador + "</b>, <br><br>Sua solicitação ("+hd.getCd_helpdesk_ocorrencia()+") com descrição: <b>'"+ hd.getDs_ocorrencia() +"'.</b><br><br>Foi adotada por: <b>"+nomeUsuario+".</b>");
			//Helpdesk_notificador.enviaEmailParaPessoa( hd.getCd_responsavel_registro(), "NOTIFICAÇÃO DE ADOÇÃO DE OCORRÊNCIA", "Olá <b>"+ nomeColaborador + "</b>, <br><br>O usuário <b>"+ nomeUsuario + "</b>já adotou o chamado ("+hd.getCd_helpdesk_ocorrencia()+"): <br>de descrição: <b>'"+ hd.getDs_ocorrencia() +"'</b>.");
			
//			String nomeUsuario = RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", hd.getCd_resp_resolucao().toString());
//			
//			List<Configuracao_helpdesk> listaConfiguracoesHelpdesk =  (List<Configuracao_helpdesk>) this.getDao().getSession().createCriteria(Configuracao_helpdesk.class)
//					.add(Restrictions.eq("cd_classificacao_helpdesk", hd.getCd_classificacao_helpdesk() ))
//					.add(Restrictions.or(Restrictions.isNull("cd_natureza_ocorrencia"), Restrictions.eq("cd_natureza_ocorrencia", hd.getCd_natureza_ocorrencia())))
//					.list();
//			
//			for(Configuracao_helpdesk conf : listaConfiguracoesHelpdesk){
//				if(conf.getSt_recebe_email()!=null){
//					if(conf.getSt_recebe_email().equals(1)){
//						if( ! conf.getCd_colaborador().equals( hd.getCd_resp_resolucao() )){
//							String nomeColaborador = RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", conf.getCd_colaborador().toString());
//							Helpdesk_notificador.enviaEmailParaPessoa( conf.getCd_colaborador(), "NOTIFICAÇÃO DE ADOÇÃO DE OCORRÊNCIA", "Olá <b>"+ nomeColaborador + "</b>, <br><br>O usuário <b>"+ nomeUsuario + "</b>já adotou o chamado ("+hd.getCd_helpdesk_ocorrencia()+"): <br>de descrição: <b>'"+ hd.getDs_ocorrencia() +"'</b>.");
//						}
//					}
//				}
//			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public Boolean deletaListaConfiguracoes(Integer cd_configuracao_helpdesk){
		
		Boolean resultado = true;
		this.setDao(new GenericDAO());
		
		Configuracao_helpdesk conf = (Configuracao_helpdesk) this.getDao().getSession().createCriteria(Configuracao_helpdesk.class).add(Restrictions.eq("cd_configuracao_helpdesk", cd_configuracao_helpdesk)).uniqueResult();
		
			try {
				this.getDao().delete(conf);
			} catch (Exception e) {
				resultado = false;
				e.printStackTrace();
			}
			
		return resultado;
	}
	
	public Boolean gravaListaConfiguracoes(List<Configuracao_helpdesk> lista){
		
		Boolean resultado = true;
		this.setDao(new GenericDAO());
		
	
		for( Configuracao_helpdesk conf : lista){
			if(conf!=null){
				try {
						this.getDao().salvar(conf);
					} catch (Exception e) {
						resultado = false;
						e.printStackTrace();
					}
			}
		}
		
		return resultado;
	}
	
	public Boolean mudaSituacao(Integer cd_helpdesk_ocorrencia){
		this.setDao(new GenericDAO());
		Boolean resultado = true;
		Helpdesk_ocorrencia hd = (Helpdesk_ocorrencia) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class).add(Restrictions.eq("cd_helpdesk_ocorrencia", cd_helpdesk_ocorrencia)).uniqueResult();
		if(hd!=null){
			hd.setSt_helpdesk_ocorrencia(1);
			
			try {
				this.getDao().atualizar( hd );
			} catch (Exception e) {
				resultado=false;
				e.printStackTrace();
			}
		}
		
		return resultado;
	}
	
	public Boolean mudaParecer(Integer cd_helpdesk_ocorrencia, Integer tp_parecer_reclamante, String ds_observacoes){
		this.setDao(new GenericDAO());
		Boolean resultado = true;
		Helpdesk_ocorrencia hd = (Helpdesk_ocorrencia) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class).add(Restrictions.eq("cd_helpdesk_ocorrencia", cd_helpdesk_ocorrencia)).uniqueResult();
		if(hd!=null){
			
			hd.setTp_parecer_reclamante(tp_parecer_reclamante);
			hd.setDs_observacoes(ds_observacoes);
			
			try {
				this.getDao().atualizar( hd );
			} catch (Exception e) {
				resultado=false;
				e.printStackTrace();
			}
		}
		
		return resultado;
	}
	
	public Boolean setaAcaoConcluida(Integer cd_acao_corretiva_helpdesk){
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		Boolean resultado = true;
		Acao_corretiva_helpdesk ach = (Acao_corretiva_helpdesk) this.getDao().getSession().createCriteria(Acao_corretiva_helpdesk.class)
				.add(Restrictions.eq("cd_acao_corretiva_helpdesk", cd_acao_corretiva_helpdesk)).uniqueResult();
		if(ach!=null){
			ach.setDt_conclusao(new Date());
			ach.setSt_status(2);
			try {
				this.getDao().atualizar( ach );
			} catch (Exception e) {
				resultado=false;
				e.printStackTrace();
			}
			
			if(resultado){
					
				Helpdesk_ocorrencia hd = (Helpdesk_ocorrencia) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
						.add(Restrictions.eq("cd_helpdesk_ocorrencia", ach.getCd_helpdesk_ocorrencia())).uniqueResult();
				if(hd!=null){
					pesquisa.buscarObjetoCompleto(hd);
					Boolean todasAcoesConcluidas=true;
					for(Acao_corretiva_helpdesk aC : hd.getListaAcoesImediatas()){
						if(!aC.getSt_status().equals(2)){
							todasAcoesConcluidas=false;
							break;
						}
					}
					
					if(todasAcoesConcluidas){
						try {
							hd.setSt_helpdesk_ocorrencia(3);	
							this.getDao().atualizar( hd );
						} catch (Exception e) {
							resultado=false;
							e.printStackTrace();
						}
					}
				}

			}
		}
		
		
		
		return resultado;
	}
	
	public Boolean setaAtividadeConcluida(Integer cd_atividades_acao_imediata){
		this.setDao(new GenericDAO());
		Boolean resultado = true;
		Atividades_acao_imediata aAI = (Atividades_acao_imediata) this.getDao().getSession().createCriteria(Atividades_acao_imediata.class)
				.add(Restrictions.eq("cd_atividades_acao_imediata", cd_atividades_acao_imediata)).uniqueResult();
		if(aAI!=null){
			aAI.setSt_status(2);
			try {
				this.getDao().atualizar( aAI );
			} catch (Exception e) {
				resultado=false;
				e.printStackTrace();
			}
		}
		
		if(resultado){
			Acao_corretiva_helpdesk aCHD = (Acao_corretiva_helpdesk) this.getDao().getSession().createCriteria(Acao_corretiva_helpdesk.class)
					.add(Restrictions.eq("cd_acao_corretiva_helpdesk", aAI.getCd_acao_corretiva_helpdesk())).uniqueResult();
			if(aCHD!=null){
				PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
				pesquisa.buscarObjetoCompleto(aCHD);
				Boolean todasAtividadesConcluidas=true;
				for(Atividades_acao_imediata a : aCHD.getListaAtividades()){
					if(!a.getSt_status().equals(2)){
						todasAtividadesConcluidas=false;
						break;
					}
				}
				
				if(todasAtividadesConcluidas){
					aCHD.setSt_status(2);
					try {
						this.getDao().atualizar( aCHD );
					} catch (Exception e) {
						resultado=false;
						e.printStackTrace();
					}
					if(resultado){

						Helpdesk_ocorrencia hd = (Helpdesk_ocorrencia) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
								.add(Restrictions.eq("cd_helpdesk_ocorrencia", aCHD.getCd_helpdesk_ocorrencia())).uniqueResult();
						if(hd!=null){
							pesquisa.buscarObjetoCompleto(hd);
							Boolean todasAcoesConcluidas=true;
							for(Acao_corretiva_helpdesk aC : hd.getListaAcoesImediatas()){
								if(!aC.getSt_status().equals(2)){
									todasAcoesConcluidas=false;
									break;
								}
							}
							
							if(todasAcoesConcluidas){
								try {
									hd.setSt_helpdesk_ocorrencia(3);	
									this.getDao().atualizar( hd );
								} catch (Exception e) {
									resultado=false;
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean setaAcaoPlanoAcaoIshikawaConcluida(Integer cd_acao_plano_acao_hp){
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		Boolean resultado = true;
		
		Acao_plano_acao_hp aPL_HD = (Acao_plano_acao_hp) this.getDao().getSession().createCriteria(Acao_plano_acao_hp.class)
				.add(Restrictions.eq("cd_acao_plano_acao_hp", cd_acao_plano_acao_hp)).uniqueResult();
		if(aPL_HD!=null){
			aPL_HD.setSt_acao_plano_acao_hp(2);
			aPL_HD.setDt_realizado(new Date());
			try {
				this.getDao().atualizar( aPL_HD );
			} catch (Exception e) {
				resultado=false;
				e.printStackTrace();
			}
			
			if(resultado){
				Plano_acao_hp pl_hd = (Plano_acao_hp) this.getDao().getSession().createCriteria(Plano_acao_hp.class)
						.add(Restrictions.eq("cd_plano_acao_hp", aPL_HD.getCd_plano_acao_hp())).uniqueResult();
				if(pl_hd!=null){
					pesquisa.buscarObjetoCompleto(pl_hd);
					Boolean todasAcoesConcluidas=true;
					for(Acao_plano_acao_hp aC : pl_hd.getListaAcoes()){
						if(!aC.getSt_acao_plano_acao_hp().equals(2)){
							todasAcoesConcluidas=false;
							break;
						}
					}
					
					if(todasAcoesConcluidas){
						try {
							pl_hd.setSt_plano_acao(2);	
							this.getDao().atualizar( pl_hd );
							
							/*******************************************/
							/****** NOTIFICAR GESTORES QUALIDADE *******/
							HelpdeskBO hBO = new HelpdeskBO();
							Helpdesk_ocorrencia hd = (Helpdesk_ocorrencia) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
									.add(Restrictions.eq("cd_helpdesk_ocorrencia", hBO.getCodigoHDPelaCausa(pl_hd.getCd_causa_efeito()).getCd_helpdesk_ocorrencia() )).uniqueResult();
							if(hd.getCd_classificacao_helpdesk().equals(1) || hd.getCd_classificacao_helpdesk().equals(2) ){
								
							
								List<Usuario> listaUsuarios = (List<Usuario>) this.getDao().getSession().createCriteria(Usuario.class)
										.add(Restrictions.eq("gestor_qualidade", 1)).list();
								
								for(Usuario u : listaUsuarios){
									
									RelatorioBO r = new RelatorioBO();
									String str = "Olá <b>"+ r.toText("usuario", "cd_usuario", "nm_usuario", u.getCd_usuario().toString() ) +"</b><br>"; 
			        				str		   += "Todas as ações do plano de ação ( "+ pl_hd.getCd_plano_acao_hp() + " ) foram concluídas.<br>Acesse o sistema para fazer a análise.";
									
									Helpdesk_notificador.enviaEmailParaPessoa(u.getCd_usuario(), "NOT. TÉRMINO DE AÇÕES", str);
								}
								
							}
							
							/**********/
						} catch (Exception e) {
							resultado=false;
							e.printStackTrace();
						}
					}
				}
			}
		}
		return resultado;
	}
	
	
	public Boolean setaAtividadePlanoAcaoIshikawaConcluida(Integer cd_atividade_acao_pl_hp){
		this.setDao(new GenericDAO());
		Boolean resultado = true;
		Atividade_acao_pl_hp aAPL = (Atividade_acao_pl_hp) this.getDao().getSession().createCriteria(Atividade_acao_pl_hp.class)
				.add(Restrictions.eq("cd_atividade_acao_pl_hp", cd_atividade_acao_pl_hp)).uniqueResult();
		if(aAPL!=null){
			aAPL.setSt_atividade_acao_pl_hp(2);
			try {
				this.getDao().atualizar( aAPL );
			} catch (Exception e) {
				resultado=false;
				e.printStackTrace();
			}
		}
		
		if(resultado){
			Acao_plano_acao_hp aPL_HD = (Acao_plano_acao_hp) this.getDao().getSession().createCriteria(Acao_plano_acao_hp.class)
					.add(Restrictions.eq("cd_acao_plano_acao_hp", aAPL.getCd_acao_plano_acao_hp())).uniqueResult();
			if(aPL_HD!=null){
				PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
				pesquisa.buscarObjetoCompleto(aPL_HD);
				Boolean todasAtividadesConcluidas=true;
				for(Atividade_acao_pl_hp a : aPL_HD.getListaAtividades()){
					if(!a.getSt_atividade_acao_pl_hp().equals(2)){
						todasAtividadesConcluidas=false;
						break;
					}
				}
				
				if(todasAtividadesConcluidas){
					aPL_HD.setSt_acao_plano_acao_hp(2);
					try {
						this.getDao().atualizar( aPL_HD );
					} catch (Exception e) {
						resultado=false;
						e.printStackTrace();
					}
					if(resultado){

						Plano_acao_hp pl_hd = (Plano_acao_hp) this.getDao().getSession().createCriteria(Plano_acao_hp.class)
								.add(Restrictions.eq("cd_plano_acao_hp", aPL_HD.getCd_plano_acao_hp())).uniqueResult();
						if(pl_hd!=null){
							pesquisa.buscarObjetoCompleto(pl_hd);
							Boolean todasAcoesConcluidas=true;
							for(Acao_plano_acao_hp aC : pl_hd.getListaAcoes()){
								if(!aC.getSt_acao_plano_acao_hp().equals(2)){
									todasAcoesConcluidas=false;
									break;
								}
							}
							
							if(todasAcoesConcluidas){
								try {
									pl_hd.setSt_plano_acao(2);	
									this.getDao().atualizar( pl_hd );
								} catch (Exception e) {
									resultado=false;
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		
		return resultado;
	}

	public Helpdesk_ocorrencia buscaHDAtual(Integer cd_helpdesk_ocorrencia){
		this.setDao(new GenericDAO());
		Helpdesk_ocorrencia hd = (Helpdesk_ocorrencia) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class).add(Restrictions.eq("cd_helpdesk_ocorrencia", cd_helpdesk_ocorrencia)).uniqueResult();
		if(hd!=null){
			PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
			pesquisa.buscarObjetoCompleto( hd );
			return hd;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Boolean enviaMensagemChat(Helpdesk_ocorrencia hd, Integer cd_usuario_destinatario, String msg, Integer vl_valor_abrir){
		
		this.setDao(new GenericDAO());

//		Integer cd_usuario_destinatario=null;
//		if(hd.getCd_responsavel_registro().equals(cd_usuario_de)){
//			if(hd.getCd_resp_resolucao()!=null){
//				cd_usuario_destinatario = hd.getCd_resp_resolucao();
//			}
//		}else{
//			cd_usuario_destinatario = hd.getCd_responsavel_registro();
//		}
		
		String nomeUsuario = RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", cd_usuario_destinatario.toString() );
		String mensagemFinal= "Olá <b>"+ nomeUsuario +"</b>,<br><br>Você recebeu uma mensagem a respeito do chamado ("+ hd.getCd_helpdesk_ocorrencia()+") de descrição: "+hd.getDs_ocorrencia()+"<br><br>Mensagem: "+msg;
		
		String valorURL="";
		Dbconf dbconf = null;
		try {
			String dir = ContextoAplicacao.CAMINHO_APLICACAO + "//classes//dbconf.txt";
			BufferedReader in = new BufferedReader(new FileReader(dir));

			dbconf = new Dbconf();            
            while (in.ready()) {

            	String strDbconf = null;
            	strDbconf = in.readLine();
            	String campo = strDbconf.split("&")[0];
            	String valor = strDbconf.split("&")[1];
            	if(campo.equals("urlservidor")){
            		if(vl_valor_abrir.equals(0)){
            			valorURL = valor+"?codusuario="+ cd_usuario_destinatario+"&codhelpdesk="+hd.getCd_helpdesk_ocorrencia()+"&analisar";
            		}else{
            			valorURL = valor+"?codusuario="+ cd_usuario_destinatario+"&codhelpdesk="+hd.getCd_helpdesk_ocorrencia()+"&parecer";
            		}
            	}
            }
            in.close();
	    } catch (IOException e) {
	    	//LogPrimum.mensagem("ERRO LINK EMAIL");
	    	return false;
	    } catch (Exception e) {
	    	//LogPrimum.mensagem("ERRO LINK EMAIL");
	    	return false;
		}
		
		
		Helpdesk_notificador.enviaEmailParaPessoa( cd_usuario_destinatario , "MENSAGEM CHAMADO",  mensagemFinal+"<br><br><a href='"+ valorURL +"'>Acesse o sistema para visualizar a mensagem!</a>" );
		
		return true;
	}
		
	@SuppressWarnings("unchecked")
	public Boolean enviaEmailLink(Helpdesk_ocorrencia helpdesk_ocorrencia){
		
		this.setDao(new GenericDAO());
		Dbconf dbconf = null;
		try {
			String dir = ContextoAplicacao.CAMINHO_APLICACAO + "//classes//dbconf.txt";
			BufferedReader in = new BufferedReader(new FileReader(dir));

			dbconf = new Dbconf();            
            while (in.ready()) {

            	String str = null;
            	str = in.readLine();
            	String campo = str.split("&")[0];
            	String valor = str.split("&")[1];
            	if(campo.equals("urlservidor")){
            		
            		String parametros   ="";
            		String parametroAux ="";
            		
            		List<Configuracao_helpdesk> listaConfiguracoesHelpdesk =  (List<Configuracao_helpdesk>) this.getDao().getSession().createCriteria(Configuracao_helpdesk.class)
            				.add(Restrictions.eq("cd_classificacao_helpdesk", helpdesk_ocorrencia.getCd_classificacao_helpdesk() ))
            				.add(Restrictions.or(Restrictions.isNull("cd_natureza_ocorrencia"), Restrictions.eq("cd_natureza_ocorrencia", helpdesk_ocorrencia.getCd_natureza_ocorrencia())))
            				.list();
            		
            		for(Configuracao_helpdesk conf : listaConfiguracoesHelpdesk){
            			String descricaoClassificacao =  RelatorioBO.toText("classificacao_helpdesk", "cd_classificacao_helpdesk", "ds_classificacao_helpdesk", helpdesk_ocorrencia.getCd_classificacao_helpdesk().toString() );
            			String descricaoNatureza =  	 RelatorioBO.toText("natureza_ocorrencia", "cd_natureza_ocorrencia", "ds_natureza_ocorrencia", helpdesk_ocorrencia.getCd_natureza_ocorrencia().toString() );
            			if(conf.getSt_recebe_email()!=null){
            				if(conf.getSt_recebe_email().equals(1)){
            				
            					String descricaoChamado = " - ";  
            					if( helpdesk_ocorrencia.getDs_ocorrencia().length() > 50 ){
            						descricaoChamado += helpdesk_ocorrencia.getDs_ocorrencia().substring(0, 50)+"...";
            					}else{
            						descricaoChamado += helpdesk_ocorrencia.getDs_ocorrencia() + "...";
            					}
            					
            					String nome_usuario = RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", conf.getCd_colaborador().toString() );
            					if( nome_usuario.split(" ").length > 1){
            						nome_usuario = nome_usuario.split(" ")[0];
            					}
            					
            					
            					String strChamado = "<b>Olá "+ nome_usuario +"</b><br><br>"; 
            					strChamado	+= "Foi aberto um chamado de classificação: <b>"+ descricaoClassificacao + "</b> e natureza: <b>"+ descricaoNatureza+"</b><br> pelo solicitante: <b>"+ RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario",  helpdesk_ocorrencia.getCd_responsavel_registro().toString() )+"</b>"
            					+" no dia <b>"+new SimpleDateFormat("dd/MM/yyyy").format(new Date())  
                						+"</b> às <b>"+new SimpleDateFormat("HH:mm").format(new Date()) + "</b><br>Descrição da ocorrência: " + helpdesk_ocorrencia.getDs_ocorrencia();
            					strChamado += "<br><br>Dúvidas, favor entrar em contato.<br><br>Obrigado.<br><br>";
                				
            					parametros="";
            					parametros= valor + "?codusuario="+conf.getCd_colaborador()+"&codhelpdesk="+helpdesk_ocorrencia.getCd_helpdesk_ocorrencia()+"&analisar";
            					parametroAux = valor + "?codusuario="+conf.getCd_colaborador()+"&codhelpdesk="+helpdesk_ocorrencia.getCd_helpdesk_ocorrencia()+"&adotar";
            					Helpdesk_notificador.enviaEmailParaPessoa( conf.getCd_colaborador(), "NOT. DE ABERTURA DE CHAMADO" + descricaoChamado, strChamado +"<br><br><a href='"+ parametros +"'>Acesse o sistema para analizar o chamado!<br><br></a><br><br>" +
            							"<a href='"+ parametroAux +"'>Clique aqui </a>para apenas adotar chamado!" );
            				}
            			}
            		}
            		
            	}
            }
            in.close();
	    } catch (IOException e) {
	    	//LogPrimum.mensagem("ERRO LINK EMAIL");
	    	return false;
	    } catch (Exception e) {
	    	//LogPrimum.mensagem("ERRO LINK EMAIL");
	    	return false;
		}
		
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Acao_corretiva_helpdesk> listaNotificacoesAcoesAtividades(Integer cd_usuario){
		this.setDao(new GenericDAO());
		
		
		List<Acao_corretiva_helpdesk> listaAux = new ArrayList<>();
		listaAux = (List<Acao_corretiva_helpdesk>) this.getDao().getSession().createCriteria(Acao_corretiva_helpdesk.class)
				.add(Restrictions.eq("cd_responsavel_acao", cd_usuario))
				.addOrder(Order.desc("cd_acao_corretiva_helpdesk"))
				.list();
 
		//******************** PARTE DO HELP DESK ANTIGO -- SE FOR VOLTAR, VOLTA PRA ESSA ************/
//		List<Atividades_acao_imediata> listaAtividadeAux = new ArrayList<>();
//		
//		List<Acao_corretiva_helpdesk> listaAcoesCorretivas = (List<Acao_corretiva_helpdesk>) this.getDao().getSession().createCriteria(Acao_corretiva_helpdesk.class)
//				.list();
//		
//		for(Acao_corretiva_helpdesk ach : listaAcoesCorretivas){
//			pesquisa.buscarObjetoCompleto( ach );
//			
//			Boolean existe = false; 
//			listaAtividadeAux = new ArrayList<>();
//			for(Atividades_acao_imediata ativ : ach.getListaAtividades()){
//				if(cd_usuario.equals( ativ.getCd_responsavel() ) && !ativ.getSt_status().equals(2) ){
//					existe = true;
//					listaAtividadeAux.add(ativ);
//				}
//			}
//			
//			if(existe){
//				ach.setListaAtividades(listaAtividadeAux);
//				listaAux.add(ach);
//			}else{
//				if(ach.getCd_responsavel_acao().equals(cd_usuario) && !ach.getSt_status().equals(2)){
//					ach.setListaAtividades(new ArrayList<Atividades_acao_imediata>());
//					listaAux.add(ach);
//				}
//			}
//		}
		
		return listaAux;
	}
	
	@SuppressWarnings("unchecked")
	public List<Acao_plano_acao_hp> listaNotificacoesAcoesAtividadesPA_ishi(Integer cd_usuario){
		this.setDao(new GenericDAO());
		
		List<Acao_plano_acao_hp> listaAcoesCorretivas = (List<Acao_plano_acao_hp>) this.getDao().getSession().createCriteria(Acao_plano_acao_hp.class)
				.add(Restrictions.eq("cd_usuario_responsavel", cd_usuario))
				.add(Restrictions.eq("st_acao_plano_acao_hp", 0))
				.addOrder(Order.desc("cd_plano_acao_hp"))
				.list();
		
		
		
		return listaAcoesCorretivas;
	}
	
//	@SuppressWarnings("unchecked")
//	public List<Plano_acao_hp> listaNotificacoesAcoesAtividadesPA_ishi(Integer cd_usuario){
//		this.setDao(new GenericDAO());
//		
//		List<Plano_acao_hp> listaPlanosAux = new ArrayList<>();
//		List<Acao_plano_acao_hp> listaAux = new ArrayList<>();
//		List<Atividade_acao_pl_hp> listaAtividadeAux = new ArrayList<>();
//		
//		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
//		List<Plano_acao_hp> listaPlanos = (List<Plano_acao_hp>) this.getDao().getSession().createCriteria(Plano_acao_hp.class)
//				.add(Restrictions.isNull("cd_usuario_validacao"))
//				.list();
//		
//		try {
//			
//			for(Plano_acao_hp plano : listaPlanos){
//				pesquisa.buscarObjetoCompleto( plano );
//				
//				if(plano.getCd_usuario_abrir()!=null){
//					
//					Helpdesk_ocorrencia hdAux = getCodigoHDPelaCausa( plano.getCd_causa_efeito() );
//
//					plano.setCd_resp_analise( hdAux.getCd_resp_analise() );
//					plano.setCd_helpdesk_ocorrencia( hdAux.getCd_helpdesk_ocorrencia() );
//
//					if(plano.getCd_usuario_abrir().equals(cd_usuario)){
//						if( !hdAux.getCd_classificacao_helpdesk().equals(1) && !hdAux.getCd_classificacao_helpdesk().equals(2) ){
//							listaPlanosAux.add(plano);
//						}
//					}else{
//					
//						listaAux = new ArrayList<>();
//						for(Acao_plano_acao_hp ach : plano.getListaAcoes()){
//							
//							pesquisa.buscarObjetoCompleto( ach );
//							
//							if(ach.getCd_usuario_responsavel().equals(cd_usuario)){
//								listaAux.add(ach);
//							}	
//							
//						}
//						
//						if(listaAux.size()>0){
//							plano.setListaAcoes(listaAux);
//							if(plano.getSt_plano_acao()!=null){
//								if(plano.getSt_plano_acao().equals(2)){
//									
//
//									if(hdAux!=null){
//										//Integer cd_usuario_resp_classificacao = Helpdesk_notificador.getCd_UsuarioResponsavelParaReceberNotificacao( hd.getCd_classificacao_helpdesk() );
//										if(hdAux.getCd_resp_resolucao().equals(cd_usuario)){
//											listaPlanosAux.add(plano);
//										}
//									}
//								}else{
//									listaPlanosAux.add(plano);
//								}
//							}
//						}
//						
//						if( hdAux.getCd_classificacao_helpdesk().equals(1) || hdAux.getCd_classificacao_helpdesk().equals(2) ){
//							
//							List<Configuracao_helpdesk> listaConf = ( List<Configuracao_helpdesk> ) this.getDao().getSession().createCriteria(Configuracao_helpdesk.class)
//									.add(Restrictions.eq("cd_classificacao_helpdesk", hdAux.getCd_classificacao_helpdesk())).list();
//							
//							if( listaConf!=null ){
//								for( Configuracao_helpdesk c : listaConf){
//									if( c.getCd_colaborador().equals(cd_usuario) ){
//										listaPlanosAux.add(plano);
//									}
//								}
//							}	
//						}
//					}
//				}
//			}
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return listaPlanosAux;
//	}
	
	public Helpdesk_ocorrencia getCodigoHDPelaCausa(Integer cd_causa_efeito){
		
		if( this.getDao() == null ){
			this.setDao(new GenericDAO());
		}
		
		Causa_efeito causa = (Causa_efeito) this.getDao().getSession().createCriteria(Causa_efeito.class).add(Restrictions.eq("cd_causa_efeito", cd_causa_efeito )).uniqueResult();
		if(causa!=null){
				Categoria_ocorrencia cat = (Categoria_ocorrencia) this.getDao().getSession().createCriteria(Categoria_ocorrencia.class)
						.add(Restrictions.eq("cd_categoria_ocorrencia", causa.getCd_categoria_ocorrencia() )).uniqueResult();
				
				if(cat!=null){
					
					Diagrama_helpdesk diagrama = getHDRecursivo( cat.getCd_diagrama_helpdesk() );
					
					if(diagrama!=null){
						
						if(diagrama.getCd_helpdesk_ocorrencia()!=null){
						
							Helpdesk_ocorrencia hd = (Helpdesk_ocorrencia) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
									.add(Restrictions.eq("cd_helpdesk_ocorrencia", diagrama.getCd_helpdesk_ocorrencia() )).uniqueResult();
							
							if(hd!=null){
								PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
								pesquisa.buscarObjetoCompleto( hd );
								return hd;
							}
						}else{
							
						}
					}
				}
		}
		
		return null;
	}
	
	public Diagrama_helpdesk getHDRecursivo(Integer cd_diagrama_helpdesk){
		Diagrama_helpdesk diagrama = (Diagrama_helpdesk) this.getDao().getSession().createCriteria(Diagrama_helpdesk.class)
				.add(Restrictions.eq("cd_diagrama_helpdesk", cd_diagrama_helpdesk )).uniqueResult();
		
		if( diagrama !=null){
			if(diagrama.getCd_diagrama_origem()!=null){
				return getHDRecursivo( diagrama.getCd_diagrama_origem() );
			}else{
				return diagrama;
			}
		}else{
			return null;
		}
	}
	
	
	public String getHistoricoDiagramas(Integer cd_diagrama_helpdesk){
		
		String strHistorico = "";
		this.setDao(new GenericDAO());
		Diagrama_helpdesk diagrama = (Diagrama_helpdesk) this.getDao().getSession().createCriteria(Diagrama_helpdesk.class)
				.add(Restrictions.eq("cd_diagrama_helpdesk", cd_diagrama_helpdesk )).uniqueResult();
		
		if( diagrama !=null){
			PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
			pesquisa.buscarObjetoCompleto(diagrama);
			
			return getHistoricoGeralDiagramas(diagrama);
		}else{
			return "";
		}
		
	}
	
	@SuppressWarnings("static-access")
	public String getHistoricoGeralDiagramas(Diagrama_helpdesk diagrama ){
		
		this.setDao(new GenericDAO());
		String strHistorico = "";
		RelatorioBO r = new RelatorioBO();
		
		
		
		if(diagrama.getListaCategoria_ocorrencia().size() > 0 ){
			strHistorico += "<div><pre>Diagrama de código ("+diagrama.getCd_diagrama_helpdesk()+") foi elaborado por: <b>'"+ r.toText("usuario", "cd_usuario", "nm_usuario", diagrama.getCd_usuario_diagrama().toString()) +"'</b> e teve as seguintes causas: <br><br>";
			for(Categoria_ocorrencia cat: diagrama.getListaCategoria_ocorrencia()){
				for(Causa_efeito causa: cat.getListaCausas()){
					strHistorico += "-----> "+ causa.getNm_causa_efeito() + " ("+ ((causa.getSt_causa_efeito().equals(0))?"Descartada":((causa.getSt_causa_efeito().equals(1))?"<b><font color='0x0000ff'>Causa raíz</font></b>":((causa.getSt_causa_efeito().equals(2))?"Causa secundária com plano de ação":"Causa secundária sem plano de ação")));
					strHistorico += ")<br>";
					if(causa.getPlano_acao() !=null){
						
						strHistorico += "<br>----------> <b>Plano ação</b> ("+ causa.getPlano_acao().getCd_plano_acao_hp()+")  ---  Data criação: "+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(causa.getPlano_acao().getDt_criacao() );
						strHistorico += ((causa.getPlano_acao().getListaAcoes().size() > 0)?" teve as seguintes ações: <br>":"<br>");
						
						
						for(Acao_plano_acao_hp acao: causa.getPlano_acao().getListaAcoes()){
							
							strHistorico += "<br>---------------> "+ acao.getDs_acao_corretiva_plano_hp() +" <br>";
							
							strHistorico += "<br>--------------------> Prazo previsto...: "+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(acao.getDt_prazo_previsto());
							strHistorico += "<br>--------------------> Status...........: "+ ((acao.getSt_acao_plano_acao_hp().equals(0))?"Aberta":"<b><font color='0x287F46'>Concluída</font></b>");
							strHistorico += "<br>--------------------> Responsável ação.: <b>"+ r.toText("usuario", "cd_usuario", "nm_usuario", acao.getCd_usuario_responsavel().toString()) + "</b>";
							strHistorico += ((acao.getSt_acao_plano_acao_hp().equals(2))? "<br>--------------------> Data realização: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(acao.getDt_realizado()) :"");
							strHistorico += "<br>--------------------> Comentário.......: "+ acao.getDs_comentario() + "<br>";
						}
					}else{
						
					}
				}
			}
		}else{
			strHistorico = "Diagrama de código ("+diagrama.getCd_diagrama_helpdesk()+")";
		}
		
		if( diagrama.getSt_diagrama_helpdesk()!= null && diagrama.getSt_diagrama_helpdesk() > 0){
			strHistorico += "<br>Foi análisada como '"+ (( diagrama.getSt_diagrama_helpdesk().equals(1))?"<b><font color='0xff0000'>Não eficaz</font></b>": "<b><font color='0x00ff00'>Eficaz</font></b>" );
			strHistorico += "' por: <b>"+ r.toText("usuario", "cd_usuario", "nm_usuario", diagrama.getCd_usuario_analise().toString()); 
			strHistorico += "</b> na data "+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(diagrama.getDt_analise_diagrama());
			strHistorico += "<br>Comentário: "+ diagrama.getDs_comentario();
		}
		
		if(diagrama.getCd_helpdesk_ocorrencia()!=null){
			 
			Helpdesk_ocorrencia hd = (Helpdesk_ocorrencia) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.eq("cd_helpdesk_ocorrencia", diagrama.getCd_helpdesk_ocorrencia() )).uniqueResult();
			
			strHistorico += "<br><br><br>********** <b>OCORRÊNCIA DE ORIGEM</b> *********<br>";
			
			strHistorico += "<br>----> Código da ocorrência..: ("+ hd.getCd_helpdesk_ocorrencia()+" )";
			strHistorico += "<br>----> Descrição.............:" + hd.getDs_ocorrencia() ;
			strHistorico += "<br>----> Data criação..........:" + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(hd.getDt_criacao()) ;
			strHistorico += "<br>----> Classificação.........:" + r.toText("classificacao_helpdesk", "cd_classificacao_helpdesk", "ds_classificacao_helpdesk", hd.getCd_classificacao_helpdesk().toString());
			strHistorico += "<br>----> Natureza..............:" + r.toText("natureza_ocorrencia", "cd_natureza_ocorrencia", "ds_natureza_ocorrencia", hd.getCd_natureza_ocorrencia().toString());
			strHistorico += "<br>----> Unidade de registro...:" + r.toText("unidade", "cd_unidade", "ds_unidade", hd.getCd_unidade_registro().toString()); 
			strHistorico += "<br>----> Responsável registro..:<b>" + r.toText("usuario", "cd_usuario", "nm_usuario", hd.getCd_responsavel_registro().toString()) +"</b>";
			
		}
		
		if(diagrama.getDiagrama_origem() != null){
			strHistorico += "<br><br>que foi gerado a partir do: <br><br>" + getHistoricoGeralDiagramas(diagrama.getDiagrama_origem());
		}
		
		return strHistorico+"</pre></div>";

	}
	
	@SuppressWarnings("unchecked")
	public List<Diagrama_helpdesk> getDiagramasAnalise(Integer cd_usuario){
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		Usuario u  = ( Usuario ) this.getDao().getSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("cd_usuario", cd_usuario)).uniqueResult();
		
		
		List<Diagrama_helpdesk> listaDiagramaResultado = new ArrayList<>();
		List<Diagrama_helpdesk> listaDiagrama = (List<Diagrama_helpdesk>) this.getDao().getSession().createCriteria(Diagrama_helpdesk.class)
				//.add(Restrictions.eq("cd_usuario_diagrama", cd_usuario))
				.add(Restrictions.le("st_diagrama_helpdesk", 1))
				.addOrder(Order.desc("cd_diagrama_helpdesk"))
				.list();
		
		Boolean diagramaProntoPraAnalise = true;
		
				
		
		for(Diagrama_helpdesk d : listaDiagrama){
			
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
				
				Helpdesk_ocorrencia hd = ( Helpdesk_ocorrencia ) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
						.add(Restrictions.eq("cd_helpdesk_ocorrencia", d.getCd_helpdesk_ocorrencia())).uniqueResult();
				
				if( hd!=null ){
					d.setDs_ocorrencia_helpdesk( hd.getDs_ocorrencia() );
				}
				
				if(d.getSt_diagrama_helpdesk().equals(0)){
					if(u.getGestor_qualidade() != null && u.getGestor_qualidade().equals(1)){
						if(u.getCd_usuario().equals(cd_usuario)){
								listaDiagramaResultado.add( d );
						}
					}
				}else{
					
					if(d.getCd_usuario_diagrama().equals(cd_usuario)){
						if(d.getSt_diagrama_helpdesk() == null){
							listaDiagramaResultado.add( d );
						}
						
					}
					
//					Helpdesk_ocorrencia hd = null;
//					if(d.getCd_helpdesk_ocorrencia() != null){
//						hd = ( Helpdesk_ocorrencia ) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
//								.add(Restrictions.eq("cd_helpdesk_ocorrencia", d.getCd_helpdesk_ocorrencia())).uniqueResult();
//					}else{
//						hd = ( Helpdesk_ocorrencia ) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
//								.add(Restrictions.eq("cd_helpdesk_ocorrencia", getHDRecursivo(d.getCd_diagrama_helpdesk()).getCd_helpdesk_ocorrencia())).uniqueResult();
//					}
//					
//					if( hd != null){
//						if( hd.getCd_classificacao_helpdesk().equals(1) || hd.getCd_classificacao_helpdesk().equals(2) ){
//							if(hd.getCd_resp_resolucao().equals(cd_usuario)){
//								listaDiagramaResultado.add( d );
//							}
//						}else{
//							if(d.getCd_usuario_diagrama().equals(cd_usuario)){
//								listaDiagramaResultado.add( d );
//							}
//						}
//					}
				}
			}
		}
		return listaDiagramaResultado;
	
		
	}
	
	public Helpdesk_ocorrencia getHelpDeskCompletoPelaAcao(Integer cd_acao_corretiva){
		
		this.setDao(new GenericDAO());
		
		Acao_corretiva_helpdesk acaoCorretiva = ( Acao_corretiva_helpdesk ) this.getDao().getSession().createCriteria(Acao_corretiva_helpdesk.class)
					.add(Restrictions.eq("cd_acao_corretiva_helpdesk", cd_acao_corretiva ))
					.uniqueResult();
			
		if( acaoCorretiva!=null){
			
			Helpdesk_ocorrencia hd = ( Helpdesk_ocorrencia ) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.eq("cd_helpdesk_ocorrencia", acaoCorretiva.getCd_helpdesk_ocorrencia() ))
					.uniqueResult();
			
			if(hd != null ){
				PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
				pesquisa.buscarObjetoCompleto(hd);
				return hd;
				
			}			
		}
		return null;
		
	}
	
	public Integer retornaResponsavel(Helpdesk_ocorrencia h){

		this.setDao(new GenericDAO());
		if( h.getCd_setor() != null ){
			Setor s = (Setor) getDao().getSession().createCriteria(Setor.class).add(Restrictions.eq("cd_setor", h.getCd_setor())).uniqueResult();
			return s.getCd_responsavel_setor();
			
		}else if( h.getCd_unidade() != null){
			Unidade u = (Unidade) getDao().getSession().createCriteria(Unidade.class).add(Restrictions.eq("cd_unidade", h.getCd_unidade())).uniqueResult();
			return u.getCd_responsavel_unidade();
		}else{
			return null;
		}
	}

}
