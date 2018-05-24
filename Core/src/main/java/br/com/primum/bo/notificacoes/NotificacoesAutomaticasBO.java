package br.com.primum.bo.notificacoes;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import br.com.primum.bo.crud.CrudGenericBO;
//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.chat.service.ChatService;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.categoria_ocorrencia.Categoria_ocorrencia;
import br.com.primum.modelo.causa_efeito.Causa_efeito;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.modelo.notificacao.Notificacao;
import br.com.primum.modelo.notificacao_automatica.Notificacao_automatica;
import br.com.primum.modelo.notificacao_usuario.Notificacao_usuario;
import br.com.primum.modelo.periodicidade.Periodicidade;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.notificadores.Helpdesk_notificador;
import br.com.primum.service.execucao_tarefa.ExecucaoTarefaService;
import br.com.primum.service.garbage_collector.GarbageCollector;
import br.com.primum.service.scheduler.SetarParecerHelpDeskService;

public class NotificacoesAutomaticasBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	
	public Boolean onSalvarCRUD(Notificacao_automatica not, Integer tp_operacao, Integer cd_codigo_parametro ) {
		
		ChatService cs = new ChatService();		
		
		Notificacao notificacaoUsuariosDeslogados = new Notificacao();
		List<Notificacao_usuario> listAux = new ArrayList<>();
		
		//notificacaoUsuariosDeslogados.setCd_tipo_notificacao( 2 );
		notificacaoUsuariosDeslogados.setTp_operacao(tp_operacao);
		notificacaoUsuariosDeslogados.setCd_tabela( not.getCd_tabela() );
		
		for(Notificacao_usuario notifica: not.getListaNotificacaoUsuario()){
			
			//Boolean usuarioLogado = false;
			for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
				if( usuariosLogados.getCd_usuario().equals( notifica.getCd_usuario() ) ){
					
					if(tp_operacao.equals(1)){
						cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoAutomaticaSaveCRUD" );
					}else if(tp_operacao.equals(2)){
						cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoAutomaticaEditCRUD" );
					}else if(tp_operacao.equals(3)){
						cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoAutomaticaDeleteCRUD" );
					}
					
					//break;
				}
			}
			
			
			Notificacao_usuario nu = new Notificacao_usuario();
			nu.setCd_usuario( notifica.getCd_usuario() );
			nu.setCd_notificacao_automatica( not.getCd_notificacao_automatica() );
			nu.setCd_codigo_parametro( cd_codigo_parametro );
			listAux.add( nu );
			
		}
		
		notificacaoUsuariosDeslogados.setLista_notificacao_grupo_usuario(null);
		notificacaoUsuariosDeslogados.setLista_notificacao_usuario(listAux);
		
		if(notificacaoUsuariosDeslogados.getLista_notificacao_usuario().size()>0){
			CrudGenericBO crud = new CrudGenericBO();
			crud.salvarObjetoCompleto(notificacaoUsuariosDeslogados);
		}

		return true;
	}
	
	
	public Boolean onSalvar(Notificacao_automatica not) {
		
		if (not.getCd_tipo_notificacao() == 3) {
			this.setDao(new GenericDAO());
			
			GregorianCalendar dataAtual = new GregorianCalendar();  
			dataAtual.setTime( new Date() );
			GregorianCalendar dataInicio = new GregorianCalendar();  
			
			Periodicidade periodo = (Periodicidade) this.getDao().getSession().createCriteria(Periodicidade.class).add(Restrictions.eq("cd_periodicidade", not.getCd_periodicidade())).uniqueResult();
			PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
			pesquisa.buscarObjetoCompleto(periodo);
			
			//EM MINUTOS
			Integer tempoTotal = periodo.getEscala_tempo().getVl_equivalencia_minuto() * periodo.getVl_tempo_periodicidade();
			
			//EM SEGUNDOS
			tempoTotal = tempoTotal * 60000;
			
			
			
			if((not.getDt_inicio()!=null) && (not.getDt_fim()!=null)){
				
				dataInicio.setTime( not.getDt_inicio() );
				
				if( dataInicio.equals(dataAtual)){
					
//					ChatService cs = new ChatService();
//				
//					for(Notificacao_usuario notifica: not.getListaNotificacaoUsuario()){
//						for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
//							if( usuariosLogados.getCd_usuario().equals( notifica.getCd_usuario() ) ){
//								cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoAutomatica" );
//								break;
//							}
//						}
//					}
					
				}
			
			}
			
			
			String strJobName="not_aut-"+not.getCd_notificacao_automatica();
			try {
				agendarTarefa( not.getCd_notificacao_automatica() , strJobName, tempoTotal, not.getDt_inicio() );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}
	
	public Boolean onEditar(Notificacao_automatica not){
		
//		List<Notificacao_usuario> listaNot_usu= (List<Notificacao_usuario>) this.getDao().getSession().createCriteria(Notificacao_usuario.class)
//				.add(Restrictions.eq("cd_notificacao_automatica", not.getCd_notificacao_automatica()))
//				.list();
//		
//		
//		 Scheduler scheduler;
//		try {
//			scheduler = new StdSchedulerFactory().getScheduler();
//		
//		    for (String groupName : scheduler.getJobGroupNames()) {
//		 
//		    	for (String jobName : scheduler.getJobNames(groupName)) {
//		 
//		    		Trigger[] triggers = scheduler.getTriggersOfJob(jobName,groupName);
//		    		Date nextFireTime = triggers[0].getNextFireTime();
//		 
//		    		if(not.getCd_notificacao_automatica().equals( Integer.parseInt(jobName.split("")[ jobName.split("").length - 1] ) ) ){
//		    			for(Notificacao_usuario not_usu : listaNot_usu){
//		    				if(not_usu.)
//		    			}
//		    		}
//		    		
//		    		//LogPrimum.mensagem("[jobName] : " + jobName + " [groupName] : "
//					+ groupName + " - nextFireTime: "+nextFireTime);
//		    		//scheduler.unscheduleJob(triggerName, triggerGroup);
//		    	}
//		 
//		    }
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}    
		//this.setDao(new GenericDAO());
		
		return true;	
	}
	
	public Boolean onDelete(Notificacao_automatica not){
		/*
		Boolean existeServicoRodando = false;
		String nomeJob = "";
		String nomeGrupo = "";
		
		Scheduler scheduler;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
		
		    for (String groupName : scheduler.getJobGroupNames()) {
		 
		    	for (String jobName : scheduler.getJobNames(groupName)) {
		 
		    		Trigger[] triggers = scheduler.getTriggersOfJob(jobName,groupName);
		    		Date nextFireTime = triggers[0].getNextFireTime();
		    		
		    		if(jobName.split("-").length>0){
			    		if(not.getCd_notificacao_automatica().equals( Integer.parseInt(jobName.split("-")[ 1 ] ) ) ){
			    			existeServicoRodando=true;
			    			nomeGrupo=groupName;
			    			nomeJob= jobName;
			    			break;
			    		}
		    		}
		    		//LogPrimum.mensagem("[jobName] : " + jobName + " [groupName] : "
					//+ groupName + " - nextFireTime: " +nextFireTime);
		 
		    	}
		    	
		    	if(existeServicoRodando)
		    		break;
		 
		    }
		    
		    if(existeServicoRodando){
		    	scheduler.deleteJob( nomeJob, nomeGrupo);
		    }
		    
		} catch (SchedulerException e) {
			e.printStackTrace();
		} 
		*/
		return true;	
	}
	
	
	public void agendarTarefa(Integer cd_notificacao, String jobName, Integer tempo, Date dataInicial) throws Exception{
		/*
		JobDetail job = new JobDetail();
		job.setName(jobName);
		job.setJobClass(ExecucaoTarefaService.class);
		
		job.getJobDataMap().put("not_aut", cd_notificacao);
		
		 //configure the scheduler time
		SimpleTrigger trigger = new SimpleTrigger();
		trigger.setName("trigger_primum_"+ jobName);
		
		trigger.setStartTime( dataInicial );
		//trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		
		trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		trigger.setRepeatInterval(tempo);
				//schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
	 	scheduler.scheduleJob(job, trigger);*/
	}
	
	
	public void criaGarbageCollector(String jobName) throws Exception{
		/*
		JobDetail job = new JobDetail();
		job.setName(jobName);
		job.setJobClass(GarbageCollector.class);
		
		 //configure the scheduler time
		SimpleTrigger trigger = new SimpleTrigger();
		trigger.setName("trigger_primum_"+ jobName);
		
		trigger.setStartTime( new Date() );
		//trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		
		trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		trigger.setRepeatInterval(24L * 60L * 60L * 1000L);
				//schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
	 	scheduler.scheduleJob(job, trigger);*/
	 }
	
	@SuppressWarnings("unchecked")
	public List<Notificacao_automatica> getListaNotificacoesAuto(Integer cd_usuario){
		
		this.setDao(new GenericDAO());
		List<Notificacao_automatica> listaAux = new ArrayList<>();
		
		List<Notificacao> listaNot= (List<Notificacao>) this.getDao().getSession().createCriteria(Notificacao.class)
				.add(Restrictions.isNull("cd_tipo_notificacao"))
				.add(Restrictions.isNull("st_exibir"))
				.add(Restrictions.isNull("tp_operacao"))
				.add(Restrictions.isNull("cd_codigo_parametro"))
				.list();
		
		for(Notificacao not: listaNot){
			
			List<Notificacao_usuario> listaUsu= (List<Notificacao_usuario>) this.getDao().getSession().createCriteria(Notificacao_usuario.class)
					.add(Restrictions.eq("cd_usuario", cd_usuario))
					.add(Restrictions.eq("cd_notificacao", not.getCd_notificacao()))
					.list();
			
			for(Notificacao_usuario nu: listaUsu){

				
				Notificacao_automatica notAut= (Notificacao_automatica) this.getDao().getSession().createCriteria(Notificacao_automatica.class)
						.add(Restrictions.eq("cd_notificacao_automatica", nu.getCd_notificacao_automatica()))
						.uniqueResult();
				
				if(notAut!=null){
					
					Notificacao_automatica notAux = new Notificacao_automatica();
					notAux.setCd_notificacao_automatica(notAut.getCd_notificacao_automatica());
					notAux.setCd_tabela(notAut.getCd_tabela());
					notAux.setDt_inicio(notAut.getDt_inicio());
					notAux.setDt_fim(notAut.getDt_fim());
					notAux.setDt_ultimo_envio(notAut.getDt_ultimo_envio());
					notAux.setDs_assunto(notAut.getDs_assunto());
					notAux.setDs_conteudo(notAut.getDs_conteudo());
					notAux.setCd_notificacao_usuario(nu.getCd_notificacao_usuario());
					
					listaAux.add(notAux);
				}
				
			}
		}
		
		return listaAux;
	}
	
	@SuppressWarnings("unchecked")
	public List<Notificacao_automatica> getListaNotificacoesAutoCRUD(Integer cd_usuario){
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Notificacao_automatica> listaAux = new ArrayList<>();
		
		List<Notificacao> listaNot= (List<Notificacao>) this.getDao().getSession().createCriteria(Notificacao.class)
				.add(Restrictions.isNotNull("tp_operacao"))
				.add(Restrictions.isNull("cd_tipo_notificacao"))
				.add(Restrictions.isNull("st_exibir"))
				.add(Restrictions.isNull("cd_codigo_parametro"))
				.list();
		
		for(Notificacao not: listaNot){
			
			List<Notificacao_usuario> listaUsu= (List<Notificacao_usuario>) this.getDao().getSession().createCriteria(Notificacao_usuario.class)
					.add(Restrictions.eq("cd_usuario", cd_usuario))
					.add(Restrictions.eq("cd_notificacao", not.getCd_notificacao()))
					.list();
			
			for(Notificacao_usuario nu: listaUsu){

				
				Notificacao_automatica notAut= (Notificacao_automatica) this.getDao().getSession().createCriteria(Notificacao_automatica.class)
						.add(Restrictions.eq("cd_notificacao_automatica", nu.getCd_notificacao_automatica()))
						.uniqueResult();
				
				if(notAut!=null){
					
					Notificacao_automatica notAux = new Notificacao_automatica();
					notAux.setCd_operacao_notificacao(not.getTp_operacao());
					notAux.setCd_notificacao_automatica(notAut.getCd_notificacao_automatica());
					notAux.setCd_codigo_parametro(nu.getCd_codigo_parametro());
					notAux.setCd_tabela(notAut.getCd_tabela());
					notAux.setCd_notificacao_usuario(nu.getCd_notificacao_usuario());
					
					listaAux.add(notAux);
				}
				
			}
		}
		
		return listaAux;
	}
	
	
	@SuppressWarnings("unchecked")
	public Boolean setNotificacoesVisualizada(Integer cd_notificacao_usuario){
		
		Boolean resultado=true;
		this.setDao(new GenericDAO());
		
		Notificacao_usuario notUsu= (Notificacao_usuario) this.getDao().getSession().createCriteria(Notificacao_usuario.class)
				.add(Restrictions.eq("cd_notificacao_usuario", cd_notificacao_usuario))
				.uniqueResult();
		
		CrudGenericBO crud = new CrudGenericBO();
		if(notUsu!=null && notUsu.getCd_notificacao_usuario()!=null){

			
			try {
				crud.deletar(notUsu);
			} catch (Exception e) {
				resultado=false;
				e.printStackTrace();
			}
		}
		
		
		if(resultado){
			
			List<Notificacao_usuario> listNotUsu= (List<Notificacao_usuario>) this.getDao().getSession().createCriteria(Notificacao_usuario.class)
				.add(Restrictions.eq("cd_notificacao", notUsu.getCd_notificacao()))
				.list();
			
			if(listNotUsu!=null && listNotUsu.size()==0){
				
				Notificacao  not = (Notificacao) this.getDao().getSession().createCriteria(Notificacao.class)
						.add(Restrictions.eq("cd_notificacao", notUsu.getCd_notificacao()))
						.uniqueResult();
				
				try {
					crud.deletar(not);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return resultado;
	}


	public Boolean setNotificacoesCRUDVisualizada(Integer cd_notificacao_usuario){
		return this.setNotificacoesVisualizada(cd_notificacao_usuario);
	}
	
	
	
	public void efetuarNotificacao(Object documento){
		
	}
	
	@SuppressWarnings("unchecked")
	public Boolean verificaseExisteServicoRodando(){
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		List<Notificacao_automatica> listaNotAut = ( List<Notificacao_automatica> ) this.getDao().getSession().createCriteria(Notificacao_automatica.class)
				.add(Restrictions.isNull("cd_operacao_notificacao"))
				.list();
		
		ExecucaoTarefaService eTS = new ExecucaoTarefaService();
		for( Notificacao_automatica na : listaNotAut){
			if(!eTS.verificaseExisteServicoRodando(na)){
				onSalvar(na);
			}
		}
		
		/************** SCHEDULER GARBAGE COLLECTOR *******************/ 
		GarbageCollector gc = new GarbageCollector();
		if(!gc.verificaSeExisteServicoRodando()){
			try {
				criaGarbageCollector("garbage_collector");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/************** SCHEDULER PARECER HELPDESK *******************/
		/*
		List<Helpdesk_ocorrencia> listaHelpdeskAux = new ArrayList<>();
		List<Helpdesk_ocorrencia> listaHelpdeskEnviados =  (List<Helpdesk_ocorrencia>) getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.ge("st_helpdesk_ocorrencia", 4))
				.add(Restrictions.isNull("tp_parecer_reclamante"))
				.list();
		
		for(Helpdesk_ocorrencia hd : listaHelpdeskEnviados){
			pesquisa.buscarObjetoCompleto(hd);
			
			if(hd.getSt_helpdesk_ocorrencia().equals(4)){
				listaHelpdeskAux.add(hd);
			}else{

				Integer qtdePlanos = 0;
				Integer planosFinalizados = 0;
				
				if(hd.getListaCategoria_ocorrencia().size()>0){
					for(Categoria_ocorrencia cat: hd.getListaCategoria_ocorrencia()){
						if(cat.getListaCausas().size()>0){
							for(Causa_efeito causa: cat.getListaCausas()){
								if(causa.getSt_causa_efeito().equals(1) || causa.getSt_causa_efeito().equals(2)){
									if(causa.getPlano_acao()!=null){
										qtdePlanos+=1;
										if(causa.getPlano_acao().getSt_plano_acao().equals(2) && causa.getPlano_acao().getCd_usuario_validacao()!=null){
											planosFinalizados+=1;
										}
									}
								}
							}
						}
					}
				}
				if(qtdePlanos>0 && qtdePlanos == planosFinalizados){
					listaHelpdeskAux.add(hd);
				}
			}
		}
		if(listaHelpdeskAux.size()>0){
			SetarParecerHelpDeskService set = new SetarParecerHelpDeskService();
			for( Helpdesk_ocorrencia hd : listaHelpdeskAux){
				if(!set.verificaseExisteServicoRodando(hd)){
					Helpdesk_notificador.criaScheduler(hd.getCd_helpdesk_ocorrencia());
				}
			}
		}
		*/
		return true;
	}
}
