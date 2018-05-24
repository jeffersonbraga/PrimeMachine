package br.com.primum.bo.plano_acao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.acao_plano_acao_hp.Acao_plano_acao_hp;
import br.com.primum.modelo.atividade_plano_acao.Atividade_plano_acao;
import br.com.primum.modelo.campo_formulario.Campo_formulario;
import br.com.primum.modelo.grupo_formulario.Grupo_formulario;
import br.com.primum.modelo.notificacao_plano_acao.Notificacao_plano_acao;
import br.com.primum.modelo.plano_acao.Plano_acao;
import br.com.primum.modelo.plano_acao_formulario.Plano_acao_formulario;
import br.com.primum.modelo.plano_acao_hp.Plano_acao_hp;
import br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario;
import br.com.primum.modelo.setor.Setor;
import br.com.primum.notificadores.Acao_plano_acao_notificador;

public class Plano_acao_BO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	
	public String getGrupoECampoNC( Integer cd_plano_acao ){
		
		this.setDao(new GenericDAO());
		Plano_acao_formulario p = (Plano_acao_formulario) this.getDao().getSession().createCriteria(Plano_acao_formulario.class)
				.add(Restrictions.eq("cd_plano_acao", cd_plano_acao)).uniqueResult();
		
		if( p != null && p.getCd_resposta_exec_formulario() != null){
			
			Resposta_exec_formulario r = ( Resposta_exec_formulario ) this.getDao().getSession().createCriteria(Resposta_exec_formulario.class)
						.add(Restrictions.eq("cd_resposta_exec_formulario", p.getCd_resposta_exec_formulario())).uniqueResult();
			
			if( r != null && r.getCd_campo_formulario() != null){
				
				Campo_formulario c = ( Campo_formulario ) this.getDao().getSession().createCriteria(Campo_formulario.class)
						.add(Restrictions.eq("cd_campo_formulario", r.getCd_campo_formulario() )).uniqueResult();
				
				if( c != null && c.getCd_campo_formulario() != null){
					
					Grupo_formulario g = ( Grupo_formulario ) this.getDao().getSession().createCriteria(Grupo_formulario.class)
							.add(Restrictions.eq("cd_grupo_formulario", c.getCd_grupo_formulario() )).uniqueResult();
					
			
					if( g !=null ){
						if( g.getNm_grupo_formulario() != "" ){
							
							PesquisaPadraoBO pesquisa = new PesquisaPadraoBO(); pesquisa.buscarObjetoCompleto( r );
							String strDescricaoItemNaoConforme = "";
							strDescricaoItemNaoConforme += "Grupo: "+ g.getNm_grupo_formulario() + "\n";
							strDescricaoItemNaoConforme += "Campo: "+ r.getCampo_formulario().getNm_campo_formulario() + "\n";
							return strDescricaoItemNaoConforme;
							
						}else if( g.getCd_grupo_pai() != null ){
							Grupo_formulario gPai = ( Grupo_formulario ) this.getDao().getSession().createCriteria(Grupo_formulario.class)
									.add(Restrictions.eq("cd_grupo_formulario", g.getCd_grupo_pai() )).uniqueResult();
							
							String strDescricaoItemNaoConforme = "";
							strDescricaoItemNaoConforme += "Grupo: "+ g.getNm_grupo_formulario() + "\n";
							strDescricaoItemNaoConforme += "Campo: "+ r.getCampo_formulario().getNm_campo_formulario() + "\n";
							return strDescricaoItemNaoConforme;
							
						}
					}
				}
			}
		}
		
		return "";
	}
	
	
	public Plano_acao_hp buscarPlano_acaoHD(Integer cd_plano_acao_hp) throws Exception {
		this.setDao(new GenericDAO());
		Plano_acao_hp plano = ( Plano_acao_hp ) this.getDao().getSession().createCriteria(Plano_acao_hp.class)
				.add(Restrictions.eq("cd_plano_acao_hp", cd_plano_acao_hp ))
				.uniqueResult();
		
		if(plano != null ){
			PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
			pesquisa.buscarObjetoCompleto(plano);
			return plano;
		}
		
		return null;
	}
	
	public Plano_acao_hp buscarPlano_acaoHDPelaAcao(Integer cd_acao_plano_acao_hp) throws Exception {

		this.setDao(new GenericDAO());
		Acao_plano_acao_hp acaoCorretiva = ( Acao_plano_acao_hp ) this.getDao().getSession().createCriteria(Acao_plano_acao_hp.class)
					.add(Restrictions.eq("cd_acao_plano_acao_hp", cd_acao_plano_acao_hp ))
					.uniqueResult();

		if( acaoCorretiva!=null){

			Plano_acao_hp plano = ( Plano_acao_hp ) this.getDao().getSession().createCriteria(Plano_acao_hp.class)
					.add(Restrictions.eq("cd_plano_acao_hp", acaoCorretiva.getCd_plano_acao_hp() ))
					.uniqueResult();

			if(plano != null ){
				PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
				pesquisa.buscarObjetoCompleto(plano);
				return plano;
			}			
		}
		return null;	
	}

	@SuppressWarnings("unchecked")
	public List<Plano_acao> buscarPlano_acao(Integer cd_usuario, Integer st_plano_acao) throws Exception {

		this.setDao(new GenericDAO());
		List<Plano_acao> lista = new ArrayList<>();
		if (st_plano_acao == 0) {
			lista = this.getDao().getSession().createCriteria(Plano_acao.class)
												.add(Restrictions.eq("cd_usuario_abrir", cd_usuario))
												.add(Restrictions.isNull("cd_usuario_validacao"))
												//.add(Restrictions.ne("st_plano_acao", 3))
												.addOrder(Order.desc("cd_plano_acao"))
												.list();
		} else {

			List<?> listaSetor = this.getDao().getSession().createCriteria(Setor.class)
																	.add(Restrictions.eq("cd_responsavel_setor", cd_usuario)).list();

			List<?> listaRespExecutar = this.getDao().getSession().createCriteria(Atividade_plano_acao.class)
					.add(Restrictions.eq("cd_responsavel_execucao", cd_usuario))
					.add(Restrictions.ne("st_atividade_plano_acao", 2))
					.list();

			for (Setor setor : (List<Setor>) listaSetor) {

				List<Plano_acao> listaAux = (List<Plano_acao>) this.getDao().getSession().createCriteria(Plano_acao.class)
													.add(Restrictions.eq("cd_setor_responsavel", setor.getCd_setor()))
													.add(Restrictions.ne("st_plano_acao", 3))
													.add(Restrictions.ne("st_plano_acao", 2))
													.addOrder(Order.desc("cd_plano_acao"))
													.list();

				if ( (listaAux != null) && (listaAux.size() > 0) ) {
					lista.addAll(listaAux);
				}
			}

			for (Atividade_plano_acao atividade : (List<Atividade_plano_acao>) listaRespExecutar) {

				boolean plano_acao_listado = false;
				for(Plano_acao plano_acao : lista){
					if (plano_acao.getCd_plano_acao().equals(atividade.getCd_plano_acao())) {
						plano_acao_listado = true;
						break;
					}
				}

				if (!plano_acao_listado && !atividade.getSt_atividade_plano_acao().equals(2)) {
					Plano_acao pl = (Plano_acao) this.getDao().getSession().createCriteria(Plano_acao.class).add(Restrictions.eq("cd_plano_acao", atividade.getCd_plano_acao())).uniqueResult();
					lista.add(pl);
				}
			}
		}

		for(Plano_acao plano_acao : lista){
			PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
			pesquisa.buscarObjetoCompleto(plano_acao);
			List<Atividade_plano_acao> listaAtividade_plano_acao = ( List<Atividade_plano_acao> ) this.getDao().getSession().createCriteria(Atividade_plano_acao.class).add(Restrictions.eq("cd_plano_acao", plano_acao.getCd_plano_acao())).list();
//			Setor setor = (Setor) this.getDao().getSession().createCriteria(Setor.class).add(Restrictions.eq("cd_setor", plano_acao.getCd_setor_ocorrencia())).uniqueResult();
//			
//			plano_acao.setSetor_ocorrencia(setor);
			plano_acao.setListaAtividade_plano_acao(listaAtividade_plano_acao);
		}
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> buscarPlano_acaoFeed() throws Exception {
		this.setDao(new GenericDAO());
		List<Notificacao_plano_acao> lista = new ArrayList<>();
		
		lista = this.getDao().getSession().createCriteria(Notificacao_plano_acao.class)
													.add(Restrictions.eq("st_exibir", 0))
													.addOrder(Order.desc("cd_notificacao_plano_acao"))
													.list();
		
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		for(Notificacao_plano_acao notificacao: lista){
			pesquisa.buscarObjetoCompleto(notificacao);
		}
		
		return lista;
	}
	
//	public Boolean atualizaNotificacao(Integer cd_plano_acao){
//		
//		
//		this.setDao(new GenericDAO());
//		Notificacao_plano_acao not = (Notificacao_plano_acao)this.getDao().getSession().createCriteria(Notificacao_plano_acao.class).add(Restrictions.eq("cd_plano_acao", cd_plano_acao)).uniqueResult();
//		not.setSt_exibir(0);
//		
//		CrudGenericBO crud = new CrudGenericBO();
//		
//		try {
//			crud.atualizar(not);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		
//		return true;
//	}
	
	public Boolean salvaNotificacao(Integer cd_plano_acao){
		
		this.setDao(new GenericDAO());
		Notificacao_plano_acao not = (Notificacao_plano_acao)this.getDao().getSession().createCriteria(Notificacao_plano_acao.class).add(Restrictions.eq("cd_plano_acao", cd_plano_acao)).uniqueResult();
		CrudGenericBO crud = new CrudGenericBO();
		
		try {
			if(not != null){
				if(not.getSt_exibir().equals(1))
					not.setSt_exibir(0);
				
				crud.atualizar(not);
			}else{
				not = new Notificacao_plano_acao();
				not.setCd_plano_acao(cd_plano_acao);
				not.setSt_exibir(0);
				crud.salvar(not);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public Integer qtdeFeeds(){
		this.setDao(new GenericDAO());
		List<Notificacao_plano_acao> listaNot = (List<Notificacao_plano_acao>) this.getDao().getSession().createCriteria(Notificacao_plano_acao.class).add(Restrictions.eq("st_exibir", 0)).list(); 
		if(listaNot!=null)
			return listaNot.size();
		else{
			return 0;
		}
	}
	
	@SuppressWarnings("static-access")
	public Boolean agendarEnvioAcao( Acao_plano_acao_hp acao, String jobName , Integer vl_tipo_notificacao, Date dt_notificacao){
		/*Scheduler scheduler;
		try {
		
			scheduler = new StdSchedulerFactory().getScheduler();
			JobDetail job = new JobDetail();
				
			for (String groupName : scheduler.getJobGroupNames()) {
				for (String nomeJob: scheduler.getJobNames(groupName)) {
		 
		    		Trigger[] triggers = scheduler.getTriggersOfJob(nomeJob,groupName);
		    		Date nextFireTime = triggers[0].getNextFireTime();
		    		
		    		if(nomeJob.equals("envio_acao_pl_hd-"	+acao.getCd_acao_plano_acao_hp() )){
		    			scheduler.unscheduleJob("trigger_primum_"+ jobName, groupName);
		    		}
		    		System.out.println("[jobName] : " + nomeJob + " [groupName] : " + groupName + " - nextFireTime: "+nextFireTime);
		    	}
			 }
			 
			job.setName(jobName);
			job.setJobClass(Acao_plano_acao_notificador.class);
			job.getJobDataMap().put("vl_tipo_notificacao", vl_tipo_notificacao);
			job.getJobDataMap().put("envio_acao_pl_hd", acao.getCd_acao_plano_acao_hp());
			
			SimpleTrigger trigger = new SimpleTrigger();
			trigger.setName("trigger_primum_"+ jobName);
			trigger.setStartTime( dt_notificacao );
			trigger.setRepeatCount(0);
			trigger.setRepeatInterval(1);

			scheduler.start();
			scheduler.scheduleJob(job, trigger);
				
				
		} catch (SchedulerException e) {
			e.printStackTrace();
			 return false;
		}*/
		return true;
	}
	
	@SuppressWarnings("static-access")
	public Boolean agendarEnvio( Plano_acao_hp plano_acao_hp, String jobName , Integer vl_tipo_notificacao){
		/*
		if(plano_acao_hp != null){
			
			
			Scheduler scheduler;
			try {
			
				scheduler = new StdSchedulerFactory().getScheduler();
			
				for( Acao_plano_acao_hp acao : plano_acao_hp.getListaAcoes()){
					
					if(acao.getSt_acao_plano_acao_hp().equals(2)){
						
						Boolean agendar=true;
						JobDetail job = new JobDetail();
						
						GregorianCalendar dataAtual = (GregorianCalendar) new GregorianCalendar().getInstance();
						GregorianCalendar dataNotificacao = (GregorianCalendar) new GregorianCalendar();
						dataNotificacao.setTime(acao.getDt_prazo_previsto());
						dataNotificacao.set(Calendar.DATE, dataNotificacao.get(Calendar.DATE)-2);
						
						for (String groupName : scheduler.getJobGroupNames()) {
							for (String nomeJob: scheduler.getJobNames(groupName)) {
					 
					    		Trigger[] triggers = scheduler.getTriggersOfJob(nomeJob,groupName);
					    		Date nextFireTime = triggers[0].getNextFireTime();
					    		
					    		if(nomeJob.equals("envio_acao_pl_hd-"	+acao.getCd_acao_plano_acao_hp() )){
					    			//scheduler.unscheduleJob("trigger_primum_"+ jobName, groupName);
					    			agendar=false;
					    		}
					    		System.out.println("[jobName] : " + nomeJob + " [groupName] : " + groupName + " - nextFireTime: "+nextFireTime);
					    	}
						 }
						
						if(dataNotificacao.after(dataAtual)){
							 if(agendar){
								 
								job.setName(jobName+""+acao.getCd_acao_plano_acao_hp());
								job.setJobClass(Acao_plano_acao_notificador.class);
								job.getJobDataMap().put("envio_acao_pl_hd", acao.getCd_acao_plano_acao_hp());
								job.getJobDataMap().put("vl_tipo_notificacao", vl_tipo_notificacao);
								
								SimpleTrigger trigger = new SimpleTrigger();
								trigger.setName("trigger_primum_"+ jobName);
								trigger.setStartTime( dataNotificacao.getTime() );
								trigger.setRepeatCount(0);
								trigger.setRepeatInterval(1);
			
								scheduler.start();
								scheduler.scheduleJob(job, trigger);
							}
						}else{
							if(agendar){
								dataNotificacao.setTime(acao.getDt_prazo_previsto());
								if(dataNotificacao.after(dataAtual)){
									agendarEnvioAcao(acao, jobName+""+acao.getCd_acao_plano_acao_hp(), 2, dataNotificacao.getTime() );
								}
							}
						}
					}
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
				 return false;
			}
		}
		 */
		 return true;
	 }
}