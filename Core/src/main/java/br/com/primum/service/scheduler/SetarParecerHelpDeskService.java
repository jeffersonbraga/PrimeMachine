package br.com.primum.service.scheduler;

import java.util.Date;
import java.util.regex.Pattern;

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
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.modelo.notificacao_automatica.Notificacao_automatica;

public class SetarParecerHelpDeskService implements Job{
	
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
			if( !dataMap.getBoolean("primeiraExecucao") ){
				setaParecerHD( dataMap.getInt("hd_ocorrencia") );
			}else{
				dataMap.put("primeiraExecucao", false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setaParecerHD(Integer cod_helpdesk){
		
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		CrudGenericBO crud = new CrudGenericBO();
		
		Helpdesk_ocorrencia hd = ( Helpdesk_ocorrencia ) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.eq("cd_helpdesk_ocorrencia", cod_helpdesk)).uniqueResult();
		
		if(hd!=null){
			if(hd.getTp_parecer_reclamante() == null){
				
				deletaServico(hd);
				pesquisa.buscarObjetoCompleto(hd);
				hd.setTp_parecer_reclamante(0);
				hd.setDs_observacoes("Corrigido automaticamente");
				
				try {
					crud.atualizar(hd);
					
				} catch (Exception e) {
					//LogPrimum.mensagem(e.getMessage());
				}
			}
		}
	}
	
	private void deletaServico(Helpdesk_ocorrencia hd){
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
		    		
		    		//if( jobName.split(Pattern.quote("*")).length > 0){
		    		if( jobName.split("-").length > 0){
		    			if(  jobName.split("-")[0].equals("hd_ocorrencia") && !jobName.split("-")[1].isEmpty()){
			    			if( hd.getCd_helpdesk_ocorrencia().equals( Integer.parseInt(jobName.split("-")[1] ) ) ){
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
		    }
		    
		} catch (SchedulerException e) {
			e.printStackTrace();
		}*/
	}
	
	public Boolean verificaseExisteServicoRodando( Helpdesk_ocorrencia hd){
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
		    		if( jobName.split("-").length > 0){
			    		if( jobName.split("-")[0].equals("hd_ocorrencia") && !jobName.split("-")[1].isEmpty()){
			    			if( hd.getCd_helpdesk_ocorrencia().equals( Integer.parseInt(jobName.split("-")[1] ) ) ){
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
