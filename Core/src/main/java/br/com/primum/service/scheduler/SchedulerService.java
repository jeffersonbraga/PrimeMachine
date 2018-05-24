package br.com.primum.service.scheduler;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.usuario.Usuario;

public class SchedulerService implements Job {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		GenericDAO dao = new GenericDAO();
		List<?> lista = null;
		
		try {
			lista = dao.findAll(new Usuario());
		
			for (Usuario usu : (List<Usuario>) lista) {
				//LogPrimum.mensagem("Usuáro: " + usu.getNm_usuario());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
