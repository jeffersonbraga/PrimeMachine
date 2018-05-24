package br.com.primum.service.garbage_collector;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.contexto.ContextoAplicacao;

public class GarbageCollector implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			executaGarbageCollector( );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executaGarbageCollector(){
		
		File diretorio = new File(ContextoAplicacao.CAMINHO_SAIDA );
		List<String> lista = new  ArrayList<>();
		
		if(diretorio.exists()){
			for(int i = 0 ; i<diretorio.list().length;i++ ){
				lista.add((String) diretorio.list()[i] );
				//LogPrimum.mensagem("arquivo: "+ (String) diretorio.list()[i] );
			}
			
			for(int e = 0 ; e<lista.size();e++ ){
				
				String nomeArquivo = (String) lista.get(e);
				File arquivo = new File(ContextoAplicacao.CAMINHO_SAIDA+"/"+ (String) nomeArquivo);
				if( arquivo.isFile() ){
					if(arquivo.getName().endsWith(".pdf") ){
						try {
							arquivo.delete();
						} catch (Exception exe) {
							exe.printStackTrace();
						}
						
					}
				}
			}
		}
	}
	
	public Boolean verificaSeExisteServicoRodando(){
		/*Boolean existeServicoRodando = false;
		Scheduler scheduler;
		
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
		
		    for (String groupName : scheduler.getJobGroupNames()) {
		 
		    	for (String jobName : scheduler.getJobNames(groupName)) {
		 
		    		Trigger[] triggers = scheduler.getTriggersOfJob(jobName,groupName);
		    		Date nextFireTime = triggers[0].getNextFireTime();
		    				    		
		    		if(jobName!=null){
			    		if(jobName.equals("garbage_collector") ){
			    			existeServicoRodando=true;
			    			break;
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
