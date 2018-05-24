package br.com.primum.notificadores;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.bo.email.EmailBO;
import br.com.primum.bo.helpdesk.HelpdeskBO;
import br.com.primum.chat.service.ChatService;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.acao_corretiva_helpdesk.Acao_corretiva_helpdesk;
import br.com.primum.modelo.acao_plano_acao_hp.Acao_plano_acao_hp;
import br.com.primum.modelo.atividade_acao_pl_hp.Atividade_acao_pl_hp;
import br.com.primum.modelo.atividades_acao_imediata.Atividades_acao_imediata;
import br.com.primum.modelo.campo.Campo;
import br.com.primum.modelo.categoria_ocorrencia.Categoria_ocorrencia;
import br.com.primum.modelo.causa_efeito.Causa_efeito;
import br.com.primum.modelo.classificacao_helpdesk.Classificacao_helpdesk;
import br.com.primum.modelo.codigosfixos.CodigosFixos;
import br.com.primum.modelo.configuracao_helpdesk.Configuracao_helpdesk;
import br.com.primum.modelo.diagrama_helpdesk.Diagrama_helpdesk;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.modelo.pessoa.Pessoa;
import br.com.primum.modelo.plano_acao_hp.Plano_acao_hp;
import br.com.primum.modelo.tabela.Tabela;
import br.com.primum.modelo.usuario.Usuario;

public class Helpdesk_notificador {

	private static GenericDAO dao;

	public static GenericDAO getDao() {
		return dao;
	}

	public static void setDao(GenericDAO daao) {
		dao = daao;
	}
	@SuppressWarnings("unchecked")
	public static Boolean notificaResponsavel(Helpdesk_ocorrencia hd){
		setDao(new GenericDAO());
		ChatService cs = new ChatService();

		
		List<Classificacao_helpdesk> listaClassificacaoHelpdesk =  (List<Classificacao_helpdesk>) getDao().getSession().createCriteria(Classificacao_helpdesk.class).list();
		for(Classificacao_helpdesk classific : listaClassificacaoHelpdesk){
			if(classific.getCd_classificacao_helpdesk().equals(hd.getCd_classificacao_helpdesk())){
				List<Configuracao_helpdesk> listaConfiguracaoHelpdesk =  ( List<Configuracao_helpdesk> ) getDao().getSession().createCriteria(Configuracao_helpdesk.class)
						.add(Restrictions.eq("cd_classificacao_helpdesk", hd.getCd_classificacao_helpdesk())).list();
				
				if(listaConfiguracaoHelpdesk!=null){
					for(Configuracao_helpdesk conf : listaConfiguracaoHelpdesk){
						
						for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
							
							if( usuariosLogados.getCd_usuario().equals( conf.getCd_colaborador() ) ){
				    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoHelpdesk" );
				    		}
							
						}
						
					}
				}
			}
		}
		//Integer cd_usuario  = getCd_UsuarioResponsavelParaReceberNotificacao(hd.getCd_classificacao_helpdesk());
		//if(cd_usuario!=null){
//			for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
//				
//				if( usuariosLogados.getCd_usuario().equals( cd_usuario ) ){
//	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoHelpdesk" );
//	    		}
//				
//			}
//		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static Pessoa achaPessoa(Integer cd_usuario){
		
	
		setDao(new GenericDAO());
		Usuario usu = (Usuario) getDao().getSession().createCriteria(Usuario.class).add(Restrictions.eq("cd_usuario", cd_usuario)).uniqueResult();
		if(usu!=null){
			Pessoa pessoa = (Pessoa) getDao().getSession().createCriteria(Pessoa.class).add(Restrictions.eq("cd_pessoa", usu.getCd_pessoa())).uniqueResult();
			if(pessoa!=null){
				if(pessoa.getDs_email()!=""){
					return pessoa;
				}
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Integer getCd_UsuarioResponsavelParaReceberNotificacao(Integer cd_classificacao){
		setDao(new GenericDAO());
		
		List<Classificacao_helpdesk> listaClassificacaoHelpdesk =  (List<Classificacao_helpdesk>) getDao().getSession().createCriteria(Classificacao_helpdesk.class).list();
		for(Classificacao_helpdesk classific : listaClassificacaoHelpdesk){
			if(classific.getCd_classificacao_helpdesk().equals(cd_classificacao)){
				Configuracao_helpdesk configuracaoHelpdesk =  ( Configuracao_helpdesk ) getDao().getSession().createCriteria(Configuracao_helpdesk.class)
						.add(Restrictions.eq("cd_classificacao_helpdesk", cd_classificacao)).uniqueResult();
				
				if(configuracaoHelpdesk!=null){
					if(configuracaoHelpdesk.getCd_colaborador()!=null)
						return configuracaoHelpdesk.getCd_colaborador();
				}
			}
		}
		
//		List<Integer> listaCd_usuarios = new ArrayList<>();
//		Classificacao_helpdesk classific=  (Classificacao_helpdesk) getDao().getSession().createCriteria(Classificacao_helpdesk.class)
//				.add(Restrictions.eq("cd_classificacao_helpdesk", cd_classificacao)).uniqueResult();
//				
//		if(classific!=null){
//			List<Configuracao_helpdesk> listaConfiguracaoHelpdesk =  ( List<Configuracao_helpdesk> ) getDao().getSession().createCriteria(Configuracao_helpdesk.class)
//					.add(Restrictions.eq("cd_classificacao_helpdesk", cd_classificacao)).list();
//			
//			for(Configuracao_helpdesk conf : listaConfiguracaoHelpdesk){
//				listaCd_usuarios.add(conf.getCd_colaborador())/
//			}
//			return listaCd_usuarios;
//		}
		
		
		return null;
	}
	
	public static void aumentaNotificacaoRecebida(Acao_corretiva_helpdesk aCH){
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( usuariosLogados.getCd_usuario().equals( aCH.getCd_responsavel_acao() ) ){
    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebida" );
    		}
		}
		
		Pessoa pessoa = achaPessoa( aCH.getCd_responsavel_acao() );
		if(pessoa!=null){
			EmailBO emailBO = new EmailBO();
			try {
				//emailBO.enviaEmailSimples( emailBO.criaEmail( pessoa.getDs_email()  , "NOTIFICAÇÃO DE AÇÃO CORRETIVA", "Descrição: "+aCH.getNm_acao_corretiva_helpdesk()) );
				//emailBO.enviaEmailSimples( emailBO.criaEmail( "vine.fm@gmail.com"  , "NOTIFICAÇÃO DE AÇÃO CORRETIVA", "Descrição: "+aCH.getNm_acao_corretiva_helpdesk()) );
				emailBO.sendEmail( pessoa.getDs_email() , pessoa.getNm_pessoa() , "NOTIFICAÇÃO DE AÇÃO CORRETIVA", "Descrição: "+aCH.getNm_acao_corretiva_helpdesk() );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void aumentaNotificacaoRecebida(Atividades_acao_imediata ativAI){
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( usuariosLogados.getCd_usuario().equals( ativAI.getCd_responsavel() ) ){
    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebida" );
    		}
		}
		
		Pessoa pessoa = achaPessoa( ativAI.getCd_responsavel() );
		if(pessoa!=null){
			EmailBO emailBO = new EmailBO();
			try {
				//emailBO.enviaEmailSimples( emailBO.criaEmail(  pessoa.getDs_email() , "NOTIFICAÇÃO DE ATIVIDADE AÇÃO IMEDIATA", "Descrição: "+ativAI.getDs_atividades()) );
				//emailBO.enviaEmailSimples( emailBO.criaEmail(  "vine.fm@gmail.com" , "NOTIFICAÇÃO DE ATIVIDADE AÇÃO IMEDIATA", "Descrição: "+ativAI.getDs_atividades()) );
				emailBO.sendEmail( pessoa.getDs_email() , pessoa.getNm_pessoa() , "NOTIFICAÇÃO DE ATIVIDADE AÇÃO IMEDIATA", "Descrição: "+ativAI.getDs_atividades() );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void aumentaNotificacaoRecebida(Acao_plano_acao_hp aCH){
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( usuariosLogados.getCd_usuario().equals( aCH.getCd_usuario_responsavel() ) ){
    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebida" );
    		}
		}
	}
	
	public static void aumentaNotificacaoRecebida(Atividade_acao_pl_hp ativAI){
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( usuariosLogados.getCd_usuario().equals( ativAI.getCd_usuario_responsavel() ) ){
    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebida" );
    		}
		}
	}
	
	public static void diminuiNotificacaoRecebida(Object entity){
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( entity instanceof Acao_corretiva_helpdesk){ 
				if( usuariosLogados.getCd_usuario().equals( ((Acao_corretiva_helpdesk) entity).getCd_responsavel_acao() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebidaFinalizada" );
	    		}
	        }
	        if( entity instanceof Atividades_acao_imediata){
	        	if( usuariosLogados.getCd_usuario().equals( ((Atividades_acao_imediata) entity).getCd_responsavel() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebidaFinalizada" );
	    		} 
	        }
	        if( entity instanceof Acao_plano_acao_hp){ 
	        	if( usuariosLogados.getCd_usuario().equals( ((Acao_plano_acao_hp) entity).getCd_usuario_responsavel() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebidaFinalizada" );
	    		}
	        }
	        if( entity instanceof Atividade_acao_pl_hp){
	        	if( usuariosLogados.getCd_usuario().equals( ((Atividade_acao_pl_hp) entity).getCd_usuario_responsavel() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebidaFinalizada" );
	    		}
	        }
	        if( entity instanceof Plano_acao_hp){ 
				if( usuariosLogados.getCd_usuario().equals( ((Plano_acao_hp) entity).getCd_usuario_abrir() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebidaFinalizada" );
	    		}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void aumentaNotificacaoRecebida(Object entity){
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( entity instanceof Helpdesk_ocorrencia){ 
				
				
				Integer cd_usuario = null;
				if( ((Helpdesk_ocorrencia) entity).getCd_resp_resolucao()!=null ){
					cd_usuario = ((Helpdesk_ocorrencia) entity).getCd_resp_resolucao();
				
					if( usuariosLogados.getCd_usuario().equals( cd_usuario ) ){
		    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebida" );
		    		}
					
					//SE FOR O PRIMEIRO A SALVAR COMO RESPONSÁVEL PELA RESOLUÇÃO
					if(((Helpdesk_ocorrencia) entity).getCd_resp_salvar()==null){
						//VERIFICA TODOS USUARIOS CONFIGURADOS PRA TAL CLASSIFICACAO E SE NAO FOR O LOGADO, RETIRA NOTIFICACAO
						setDao(new GenericDAO());
						List<Configuracao_helpdesk> listaConfiguracaoHelpdesk =  ( List<Configuracao_helpdesk> ) getDao().getSession().createCriteria(Configuracao_helpdesk.class)
								.add(Restrictions.eq("cd_classificacao_helpdesk", ((Helpdesk_ocorrencia) entity).getCd_classificacao_helpdesk())).list();
						
						if(listaConfiguracaoHelpdesk!=null){
							for(Configuracao_helpdesk conf : listaConfiguracaoHelpdesk){
								if( usuariosLogados.getCd_usuario().equals( conf.getCd_colaborador() ) ){
									//if( !usuariosLogados.getCd_usuario().equals(((Helpdesk_ocorrencia) entity).getCd_resp_resolucao() ) ){
						    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoHelpdeskIniciada" );
						    		//}
								}
							}
						}
					}
					
				}
	        }
			
			if( entity instanceof Plano_acao_hp){ 
				if( usuariosLogados.getCd_usuario().equals( ((Plano_acao_hp) entity).getCd_usuario_abrir() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebida" );
	    		}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void aumentaNotificacaoEnviada(Object entity){
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			
			//LogPrimum.mensagem("usuarios logados: " + usuariosLogados.getCd_usuario()+" > "+ usuariosLogados.getNm_usuario() );
			
			if( entity instanceof Helpdesk_ocorrencia){ 
				
				//Integer cd_usuario = getCd_UsuarioResponsavelParaReceberNotificacao(((Helpdesk_ocorrencia) entity).getCd_classificacao_helpdesk());
				if( usuariosLogados.getCd_usuario().equals( ((Helpdesk_ocorrencia) entity).getCd_responsavel_registro() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoEnviada" );
	    		}
				
				//VERIFICA TODOS USUARIOS CONFIGURADOS PRA TAL CLASSIFICACAO E SE NAO FOR O LOGADO, RETIRA NOTIFICACAO
				setDao(new GenericDAO());
				List<Configuracao_helpdesk> listaConfiguracaoHelpdesk =  ( List<Configuracao_helpdesk> ) getDao().getSession().createCriteria(Configuracao_helpdesk.class)
						.add(Restrictions.eq("cd_classificacao_helpdesk", ((Helpdesk_ocorrencia) entity).getCd_classificacao_helpdesk())).list();
				
				if(listaConfiguracaoHelpdesk!=null){
					for(Configuracao_helpdesk conf : listaConfiguracaoHelpdesk){
						if( usuariosLogados.getCd_usuario().equals( conf.getCd_colaborador() ) ){
							if( !usuariosLogados.getCd_usuario().equals(((Helpdesk_ocorrencia) entity).getCd_resp_resolucao() ) ){
				    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoHelpdeskIniciada" );
				    		}
						}
					}
				}
	        }
		}
	}
	
	public static void diminuiNotificacaoEnviada(Object entity){
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( entity instanceof Helpdesk_ocorrencia){ 
				if( usuariosLogados.getCd_usuario().equals( ((Helpdesk_ocorrencia) entity).getCd_responsavel_registro() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoEnviadaFinalizada" );
	    		}
	        }
		}
	}
	
	public static void aumentaNotificacaoEnviadaPeloPlanoFinalizado(Plano_acao_hp entity){
		
		if(entity!=null){
			
			HelpdeskBO hBO = new HelpdeskBO();
			Helpdesk_ocorrencia hd = hBO.getCodigoHDPelaCausa( entity.getCd_causa_efeito() );
			
			if( hd != null){
				
				ChatService cs = new ChatService();
				for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
					if( usuariosLogados.getCd_usuario().equals( hd.getCd_responsavel_registro() ) ){
		    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoEnviada" );
		    		}
				}
			}
			
//			Causa_efeito causa=  (Causa_efeito) getDao().getSession().createCriteria(Causa_efeito.class)
//					.add(Restrictions.eq("cd_causa_efeito", entity.getCd_causa_efeito())).uniqueResult();
//			if(causa!=null){
//				Categoria_ocorrencia cat =  (Categoria_ocorrencia) getDao().getSession().createCriteria(Categoria_ocorrencia.class)
//						.add(Restrictions.eq("cd_categoria_ocorrencia", causa.getCd_categoria_ocorrencia())).uniqueResult();
//				if(cat!=null){
//					
//					Diagrama_helpdesk diagrama = (Diagrama_helpdesk) getDao().getSession().createCriteria(Diagrama_helpdesk.class)
//							.add(Restrictions.eq("cd_diagrama_helpdesk", cat.getCd_diagrama_helpdesk() )).uniqueResult();
//					
//					if(diagrama!=null){
//						
//						Helpdesk_ocorrencia hd =  (Helpdesk_ocorrencia) getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
//								.add(Restrictions.eq("cd_helpdesk_ocorrencia", diagrama.getCd_helpdesk_ocorrencia())).uniqueResult();
//						if(hd!=null){
//							
//							ChatService cs = new ChatService();
//							for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
//								if( usuariosLogados.getCd_usuario().equals( hd.getCd_responsavel_registro() ) ){
//					    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoEnviada" );
//					    		}
//							}
//						}
//					}
//				}
//			}
		}
	}
	
	/************************************************/
	/**************** EMAAAIL ***********************/
	
	public static void enviaEmailParaPessoa(Integer cd_usuario, String assunto, String corpoMSG){
		
		EmailBO emailBO = new EmailBO();
		Pessoa pessoa = emailBO.achaPessoa(cd_usuario);
		if(pessoa!=null){
			
			try {
				//emailBO.sendEmail( pessoa.getDs_email() , pessoa.getNm_pessoa() , "NOTIFICAÇÃO DE ATIVIDADE AÇÃO IMEDIATA", "Descrição: "+ativAI.getDs_atividades() );
				emailBO.sendEmail( pessoa.getDs_email() , pessoa.getNm_pessoa() , assunto, corpoMSG);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**************** EMAAAIL ***********************/
	/************************************************/
	
	
	/************************************************/
	/**************** SCHEDULEER ********************/

	public static void criaScheduler(Integer cd_helpdesk_ocorrencia){
		String strJobName="hd_ocorrencia-"+cd_helpdesk_ocorrencia;
		try {
			agendarParecer( cd_helpdesk_ocorrencia , strJobName );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void agendarParecer(Integer cd_helpdesk_ocorrencia, String jobName) throws Exception{
		/*
		JobDetail job = new JobDetail();
		job.setName(jobName);
		job.setJobClass(br.com.primum.service.scheduler.SetarParecerHelpDeskService.class);
		
		job.getJobDataMap().put("hd_ocorrencia", cd_helpdesk_ocorrencia);
		job.getJobDataMap().put("primeiraExecucao", true);
		
		 //configure the scheduler time
		SimpleTrigger trigger = new SimpleTrigger();
		trigger.setName("trigger_parecer_"+ jobName);
		
		
		//trigger.setStartTime( new Date() );
		trigger.setStartTime(new Date(System.currentTimeMillis() ));
		trigger.setRepeatCount(2);
		//trigger.setRepeatInterval(1000);
		trigger.setRepeatInterval(5L * 24L * 60L * 60L * 1000L);
				//schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
	 	scheduler.scheduleJob(job, trigger);*/
	 }
	
	/**************** SCHEDULEER ********************/
	/************************************************/
	
	
	
}
