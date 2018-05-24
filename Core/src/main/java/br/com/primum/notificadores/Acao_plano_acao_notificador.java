package br.com.primum.notificadores;

import org.hibernate.criterion.Restrictions;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.primum.bo.plano_acao.Plano_acao_BO;
import br.com.primum.bo.relatorios.RelatorioBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.acao_plano_acao_hp.Acao_plano_acao_hp;
import br.com.primum.modelo.plano_acao_hp.Plano_acao_hp;

public class Acao_plano_acao_notificador implements Job {

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
		Integer valor = dataMap.getInt("vl_tipo_notificacao");
		
		try {
			
			
			Acao_plano_acao_hp acao = ( Acao_plano_acao_hp ) this.getDao().getSession().createCriteria(Acao_plano_acao_hp.class)
          			.add(Restrictions.eq("cd_acao_plano_acao_hp", dataMap.getInt("envio_acao_pl_hd") )).uniqueResult();

			Plano_acao_hp plano = ( Plano_acao_hp ) this.getDao().getSession().createCriteria(Plano_acao_hp.class)
          			.add(Restrictions.eq("cd_plano_acao_hp", acao.getCd_plano_acao_hp() )).uniqueResult();
			
			if(!acao.getSt_acao_plano_acao_hp().equals(2)){
				//ENVIA EMAIL PRO CARA DIZENDO QUE FALTA 2 DIAS PRA ELE REALIZAR A A��O
				//AGENDA ENVIO PARA O DIA DA A��O
				if(valor.equals(1)){
					String strChamado = "<b>Ol� "+ RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", acao.getCd_usuario_responsavel().toString() ) +"</b><br><br>"; 
					strChamado	+= "Faltam dois dias para o prazo previsto de t�rmino da a��o de descri��o: <b>"+ acao.getDs_acao_corretiva_plano_hp() +"</b>";
					strChamado += "<br><br>D�vidas, favor entrar em contato.<br><br>Obrigado.<br><br>";
				
					Helpdesk_notificador.enviaEmailParaPessoa(acao.getCd_usuario_responsavel(), "NOT. DE A��O - PLANO DE A��O ", strChamado);
					Plano_acao_BO p = new Plano_acao_BO();
					p.agendarEnvioAcao( acao , "envio_acao_pl_hd-"+acao.getCd_acao_plano_acao_hp(), 2 , acao.getDt_prazo_previsto());
					
					
				}else if(valor.equals(2)){
					
					String strChamado = "<b>Ol� "+ RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", acao.getCd_usuario_responsavel().toString() ) +"</b><br><br>"; 
					strChamado	+= "O prazo previsto de t�rmino da a��o de descri��o: <b>'"+ acao.getDs_acao_corretiva_plano_hp()+"</b>' expirou!" ;
					
					Helpdesk_notificador.enviaEmailParaPessoa(acao.getCd_usuario_responsavel(), "NOT. DE A��O - PLANO DE A��O (N�O CUMPRIDA) ", strChamado);
					
					strChamado = "<b>Ol� "+ RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", plano.getCd_usuario_abrir().toString() ) +"</b><br><br>"; 
					strChamado	+= "O usu�rio <b>"+RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", acao.getCd_usuario_responsavel().toString() ) +"</b> n�o concluiu a a��o de descri��o: <b>'"+ acao.getDs_acao_corretiva_plano_hp()+"</b>' no prazo previsto!" ;
					Helpdesk_notificador.enviaEmailParaPessoa(acao.getCd_usuario_responsavel(), "NOT. DE A��O - PLANO DE A��O (N�O CUMPRIDA) ", strChamado);
				}else{
					

					String strChamado = "<b>Ol� "+ RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", plano.getCd_usuario_abrir().toString() ) +"</b><br><br>"; 
					strChamado	+= "O usu�rio <b>"+RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", acao.getCd_usuario_responsavel().toString() ) +"</b> n�o concluiu a a��o de descri��o: <b>'"+ acao.getDs_acao_corretiva_plano_hp()+"</b>' no prazo previsto!" ;
					Helpdesk_notificador.enviaEmailParaPessoa(acao.getCd_usuario_responsavel(), "NOT. DE A��O - PLANO DE A��O (N�O CUMPRIDA) ", strChamado);
				}
				
			}
			

//          	String strJobName="envio_ind-"+config_indicadores.getCd_config_indicadores();
// 			try {
// 				
// 				i.agendarEnvio( config_indicadores.getCd_config_indicadores() , strJobName, config_indicadores.getConfig_envio_indicadores().getDt_notificar() );
// 			} catch (Exception e) {
// 				e.printStackTrace();
// 			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
