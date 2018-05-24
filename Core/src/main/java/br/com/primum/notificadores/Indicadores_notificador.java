package br.com.primum.notificadores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import br.com.primum.bo.indicadores.IndicadoresBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.config_envio_indicadores.Config_envio_indicadores;
import br.com.primum.modelo.config_indicadores.Config_indicadores;
import br.com.primum.modelo.escala_tempo.Escala_tempo;
import br.com.primum.modelo.notificacao_automatica.Notificacao_automatica;
import br.com.primum.modelo.periodicidade.Periodicidade;

public class Indicadores_notificador implements Job {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		this.setDao(new GenericDAO());
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		IndicadoresBO i = new IndicadoresBO();
		List<Integer> listaConfJaEnviados = new ArrayList<>(); 
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		try {
			
			Config_indicadores config_indicadores = ( Config_indicadores ) this.getDao().getSession().createCriteria(Config_indicadores.class)
          			.add(Restrictions.eq("cd_config_indicadores", dataMap.getInt("envio_ind") )).uniqueResult();
			
			
			pesquisa.buscarObjetoCompleto( config_indicadores );

			List<Config_envio_indicadores> listaConfigEnvioAux = ( List<Config_envio_indicadores> ) this.getDao().getSession().createCriteria(Config_envio_indicadores.class)
	         		.add(Restrictions.eq("dt_notificar", config_indicadores.getConfig_envio_indicadores().getDt_notificar()))
	         		.add(Restrictions.eq("vl_mesmo_horario", 1))
	         		.list();
			
			
			if(listaConfigEnvioAux!=null && listaConfigEnvioAux.size()>0){
				String strEmail = "";
				
				List<Integer> listaCodigosUsuariosReceber = new ArrayList<Integer>();
        		List<String> listaEmailCadaUsuarios = new ArrayList<String>();
        		
				
				for( Config_envio_indicadores confAux : listaConfigEnvioAux){
					
					if(new Date().after(confAux.getDt_notificar())){
					
						Config_indicadores config_indicadoresPai = ( Config_indicadores ) this.getDao().getSession().createCriteria(Config_indicadores.class)
	             			.add(Restrictions.eq("cd_config_indicadores", confAux.getCd_config_indicadores())).uniqueResult();
					
							pesquisa.buscarObjetoCompleto( config_indicadoresPai );
							//MODIFICA DATA DA PROXIMA
				         	Periodicidade periodicidade = (Periodicidade) this.getDao().getSession().createCriteria(Periodicidade.class).add(Restrictions.eq("cd_periodicidade", config_indicadoresPai.getCd_periodicidade() )).uniqueResult();
							Escala_tempo escala_tempo = (Escala_tempo) this.getDao().getSession().createCriteria(Escala_tempo.class).add(Restrictions.eq("cd_escala_tempo", periodicidade.getCd_escala_tempo() )).uniqueResult();
							Integer minutos = escala_tempo.getVl_equivalencia_minuto() * periodicidade.getVl_tempo_periodicidade();
							GregorianCalendar dataExecutar = (GregorianCalendar) new GregorianCalendar();
							
							strEmail += "<br><br>" + i.chamaIndicador( config_indicadoresPai.getCd_menu_indicadores() , config_indicadoresPai.getCd_usuario(), config_indicadoresPai.getCd_unidade(), config_indicadoresPai.getConfig_envio_indicadores().getDt_notificar(), Math.round(minutos/1440), true);
							
							if( listaCodigosUsuariosReceber.indexOf( config_indicadoresPai.getCd_usuario() ) == -1 ){
		        				
		        				listaCodigosUsuariosReceber.add( config_indicadoresPai.getCd_usuario() );
		        				listaEmailCadaUsuarios.add( "<br><br>" + i.chamaIndicador( config_indicadores.getCd_menu_indicadores() , config_indicadores.getCd_usuario(), config_indicadores.getCd_unidade(), confAux.getDt_notificar(), (Integer) Math.round(minutos/1440), true ) );
		        				
		        			}else{
		        				
		        				String s = listaEmailCadaUsuarios.get( listaCodigosUsuariosReceber.indexOf(config_indicadoresPai.getCd_usuario()) ) + "<br><br>" + i.chamaIndicador( config_indicadores.getCd_menu_indicadores() , config_indicadores.getCd_usuario(), config_indicadores.getCd_unidade(), confAux.getDt_notificar(), (Integer) Math.round(minutos/1440), true );
		        				listaEmailCadaUsuarios.set( listaCodigosUsuariosReceber.indexOf( config_indicadores.getCd_usuario() ), s);
		        					
		        			}
							
							dataExecutar.setTime( config_indicadoresPai.getConfig_envio_indicadores().getDt_notificar() );
							dataExecutar.set(Calendar.MINUTE, dataExecutar.get(Calendar.MINUTE) + minutos);
							config_indicadoresPai.getConfig_envio_indicadores().setDt_notificar( dataExecutar.getTime() );
							
							try {
				         		CrudGenericBO crud = new CrudGenericBO();
				         		crud.atualizar( config_indicadoresPai.getConfig_envio_indicadores() );
				         	} catch (Exception e) {
								// TODO: handle exception
							}
						
						
						listaConfJaEnviados.add( confAux.getCd_config_envio_indicadores() );
					}
				}
				
				for(Integer p = 0 ; p < listaCodigosUsuariosReceber.size() ; p++){
					
        			Helpdesk_notificador.enviaEmailParaPessoa( listaCodigosUsuariosReceber.get(p) , "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy").format(new Date() ), "<html>" +
	        				"<head></head><body>" + listaEmailCadaUsuarios.get(p)  +"</body></html>");
        		}
			}	
			
			while( existeServicoEntrega( config_indicadores.getCd_config_indicadores() ) ){
				deletaServico( config_indicadores.getCd_config_indicadores() );
			}
			
          	String strJobName="envio_ind-"+config_indicadores.getCd_config_indicadores();
 			try {
 				i.agendarEnvio( config_indicadores.getCd_config_indicadores() , strJobName, config_indicadores.getConfig_envio_indicadores().getDt_notificar() );
 			} catch (Exception e) {
 				e.printStackTrace();
 			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void deletaServico(Integer cd_config_indicadores){
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
			    		if( jobName.split("-")[0].equals("envio_ind") && !jobName.split("-")[1].isEmpty()){
			    			if( cd_config_indicadores.equals( Integer.parseInt(jobName.split("-")[ 1 ] ) ) ){
				    			existeServicoRodando=true;
				    			nomeGrupo=groupName;
				    			nomeJob= jobName;
				    			break;
				    		}
			    		}
		    		}
		    		System.out.println("[jobName] : " + jobName + " [groupName] : "
					+ groupName + " - nextFireTime: " +nextFireTime);
		 
		    	}
		    	
		    	if(existeServicoRodando)
		    		break;
		 
		    }
		    
		    if(existeServicoRodando){
		    	scheduler.unscheduleJob("trigger_primum_"+ nomeJob, nomeGrupo);
		    	scheduler.deleteJob( nomeJob, nomeGrupo);
		    }
		    
		} catch (SchedulerException e) {
			e.printStackTrace();
		}*/
	}
	
	private Boolean existeServicoEntrega(Integer cd_config_indicadores){
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
			    		if( jobName.split("-")[0].equals("envio_ind") && !jobName.split("-")[1].isEmpty()){
			    			if( cd_config_indicadores.equals( Integer.parseInt(jobName.split("-")[ 1 ] ) ) ){
				    			existeServicoRodando=true;
				    			nomeGrupo=groupName;
				    			nomeJob= jobName;
				    			break;
				    		}
			    		}
		    		}
		    		System.out.println("[jobName] : " + jobName + " [groupName] : "
					+ groupName + " - nextFireTime: " +nextFireTime);
		 
		    	}
		    	
		    	if(existeServicoRodando)
		    		break;
		 
		    }
		    
		    return existeServicoRodando;
		    
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		return existeServicoRodando;*/
		return null;
	}

}
