package br.com.primum.service.execucao_tarefa;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import br.com.primum.bo.crud.CrudGenericBO;
//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.chat.service.ChatService;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.notificacao.Notificacao;
import br.com.primum.modelo.notificacao_automatica.Notificacao_automatica;
import br.com.primum.modelo.notificacao_usuario.Notificacao_usuario;
import br.com.primum.modelo.usuario.Usuario;

public class ExecucaoTarefaService implements Job{

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		try {
			enviaNotificacaoParaUsuariosLogados( dataMap.getInt("not_aut") );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//context.getTrigger().getJobDataMap().ge
	}
	
	@SuppressWarnings("unchecked")
	private void enviaNotificacaoParaUsuariosLogados(Integer cod_not_aut){
		
		ChatService cs = new ChatService();
		CrudGenericBO crud = new CrudGenericBO();
		this.setDao(new GenericDAO());
		
		Notificacao notificacaoUsuariosDeslogados = new Notificacao();
		List<Notificacao_usuario> listAux = new ArrayList<>();
		
		GregorianCalendar dataAtual = new GregorianCalendar();  
		dataAtual.setTime( new Date() );
	
		Notificacao_automatica not_automatica= ( Notificacao_automatica ) this.getDao().getSession().createCriteria(Notificacao_automatica.class).add(Restrictions.eq("cd_notificacao_automatica", cod_not_aut)).uniqueResult();
		List<Notificacao_usuario> lista = (List<Notificacao_usuario> ) this.getDao().getSession().createCriteria(Notificacao_usuario.class)
				.add(Restrictions.eq("cd_notificacao_automatica", cod_not_aut))
				.add(Restrictions.isNull("cd_notificacao"))
				.list();
		
		if(not_automatica!=null){
			notificacaoUsuariosDeslogados.setCd_tabela( not_automatica.getCd_tabela() );

			//VERIFICAR SE A DATA ATUAL É IGUAL A DATA DE FIM PARA CANCELAR JOB E EXCLUIR NOTIFICACAO
			if(not_automatica.getDt_fim() != null){
				if(dataAtual.equals(not_automatica.getDt_fim()) || dataAtual.after(not_automatica.getDt_fim()) ){
					deletaServico( not_automatica );
				}
			}else{
				
				if(lista!=null && lista.size()>0){
				
					for(Notificacao_usuario not: lista){
						Boolean usuarioLogado=false;
						for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
							if( usuariosLogados.getCd_usuario().equals( not.getCd_usuario() ) ){
								usuarioLogado=true;
								cs.enviarMensagemNotificacaoAutomatica( usuariosLogados.getClientId() , not_automatica.getDs_assunto()+"_"+not_automatica.getDs_conteudo() );
								//break;
							}
						}
						
						if(! usuarioLogado){
							Notificacao_usuario nu = new Notificacao_usuario();
							nu.setCd_usuario( not.getCd_usuario() );
							nu.setCd_notificacao_automatica( not.getCd_notificacao_automatica() );
							listAux.add( nu );
						}
					}
						
					
					notificacaoUsuariosDeslogados.setLista_notificacao_grupo_usuario(null);
					notificacaoUsuariosDeslogados.setLista_notificacao_usuario(listAux);
					
					if(notificacaoUsuariosDeslogados.getLista_notificacao_usuario().size()>0){
						crud.salvarObjetoCompleto(notificacaoUsuariosDeslogados);
					}
						
					//ATUALIZAR A DATA DE ULTIMO ENVIO
					not_automatica.setDt_ultimo_envio( new Date() );
					try {
						crud.atualizar(not_automatica);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			
			}
		}
	}
	
	private void deletaServico(Notificacao_automatica noticacao_automatica){
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
			    		if( jobName.split("-")[0].equals("not_aut") && !jobName.split("-")[1].isEmpty()){
			    			if( noticacao_automatica.getCd_notificacao_automatica().equals( Integer.parseInt(jobName.split("-")[ 1 ] ) ) ){
				    			existeServicoRodando=true;
				    			nomeGrupo=groupName;
				    			nomeJob= jobName;
				    			break;
				    		}
			    		}
		    		}		 
		    	}
		    	
		    	if(existeServicoRodando)
		    		break;
		 
		    }
		    
		    if(existeServicoRodando){
		    	scheduler.deleteJob( nomeJob, nomeGrupo);
		    	CrudGenericBO crud = new CrudGenericBO();
		    	try {
					crud.deletar(noticacao_automatica);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    	
		    }
		    
		} catch (SchedulerException e) {
			e.printStackTrace();
		}*/
	}
	
	public Boolean verificaseExisteServicoRodando( Notificacao_automatica noticacao_automatica ){
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
			    		if( jobName.split("-")[0].equals("not_aut") && !jobName.split("-")[1].isEmpty()){
			    			if( noticacao_automatica.getCd_notificacao_automatica().equals( Integer.parseInt( jobName.split("-")[1] ) ) ){
				    			existeServicoRodando=true;
				    			nomeGrupo=groupName;
				    			nomeJob= jobName;
				    			break;
				    		}
			    		}
		    		}
		    		
		    	}
		    	
		    	if(existeServicoRodando)
		    		break;
		 
		    }
		    
		    if(existeServicoRodando){
		    	return true;
		    }
		    
		} catch (SchedulerException e) {
			e.printStackTrace();
		}*/
		return false;
	}

}
