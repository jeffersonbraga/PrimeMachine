package br.com.primum.hibernate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.mail.EmailException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.type.Type;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;
import flex.messaging.MessageBroker;
import flex.messaging.client.FlexClientManager;
import br.com.primum.bo.criptografia.Criptografar;
import br.com.primum.bo.crud.CrudGenericBO;
//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.bo.email.EmailBO;
import br.com.primum.bo.helpdesk.HelpdeskBO;
import br.com.primum.bo.plano_acao.Plano_acao_BO;
import br.com.primum.bo.relatorios.RelatorioBO;
import br.com.primum.chat.service.ChatService;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.flexclient.PrimumFlexClient;
import br.com.primum.interfaces.IInterceptador;
import br.com.primum.interfaces.INotificador;
import br.com.primum.modelo.acao_corretiva_helpdesk.Acao_corretiva_helpdesk;
import br.com.primum.modelo.acao_plano_acao_hp.Acao_plano_acao_hp;
import br.com.primum.modelo.atividades_acao_imediata.Atividades_acao_imediata;
import br.com.primum.modelo.bug_report.Bug_report;
import br.com.primum.modelo.checagem_documento.Checagem_documento;
import br.com.primum.modelo.config_indicadores.Config_indicadores;
import br.com.primum.modelo.configuracao_helpdesk.Configuracao_helpdesk;
import br.com.primum.modelo.dbconf.Dbconf;
import br.com.primum.modelo.diagrama_helpdesk.Diagrama_helpdesk;
import br.com.primum.modelo.documento.Documento;
import br.com.primum.modelo.escala_tempo.Escala_tempo;
import br.com.primum.modelo.espelho.Espelho;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.modelo.log_documento.Log_documento;
import br.com.primum.modelo.notificacao.Notificacao;
import br.com.primum.modelo.notificacao_automatica.Notificacao_automatica;
import br.com.primum.modelo.notificacao_usuario.Notificacao_usuario;
import br.com.primum.modelo.periodicidade.Periodicidade;
import br.com.primum.modelo.plano_acao.Plano_acao;
import br.com.primum.modelo.revisao_programa_qualidade.Revisao_programa_qualidade;
import br.com.primum.modelo.setor.Setor;
import br.com.primum.modelo.tabela.Tabela;
import br.com.primum.modelo.tipo_notificacao.Tipo_notificacao;
import br.com.primum.modelo.unidade.Unidade;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.notificadores.Helpdesk_notificador;
import br.com.primum.reflection.Reflection;
import br.com.primum.service.notificacoes.NotificacoesAutomaticasService;
import br.com.primum.singleton.SingletonFlexSession;

public class Interceptador extends EmptyInterceptor implements IInterceptador {

	/**
	 * 
	 * 
	 */

	//private static final long serialVersionUID = 1L;
    private Pattern pattern;

    private String defaultSchema;

    public void SchemaReplaceInterceptor(String token, String defaultSchema) {
        pattern = Pattern.compile(token);
        this.defaultSchema = defaultSchema;
    }

    @Override
    public String onPrepareStatement(String sql) {
    	return null;
    }

    protected String recuperaNomeSchema() {
    	return "montkoya";
    }

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 8241522926019095319L;
	private int updates;
    private int creates;
    private int loads;

    private Object objetoEntity;

    private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	public void onDelete(Object entity,
                         Serializable id,
                         Object[] state,
                         String[] propertyNames,
                         Type[] types) {
    }

	@SuppressWarnings("unchecked")
	public boolean onFlushDirty(Object entity,
                                Serializable id,
                                Object[] currentState,
                                Object[] previousState,
                                String[] propertyNames,
                                Type[] types) {

		//MANDA EMAIL PARA O FISCAL
		if( entity instanceof Revisao_programa_qualidade){
			RelatorioBO r = new RelatorioBO();
			
			//SE A REVISAO ESTA COM STATUS 'AGUARDANDO APROVA��O LEGAL' - fiscal recebe 
			if(((Revisao_programa_qualidade) entity).getSt_revisao_programa_qualidade().equals(1)){
				String strProgramaQualidade = r.toText("programa_qualidade", "cd_programa_qualidade", "nm_programa_qualidade", ((Revisao_programa_qualidade) entity ).getCd_programa_qualidade().toString() );
				Helpdesk_notificador.enviaEmailParaPessoa(((Revisao_programa_qualidade) entity).getCd_fiscal_responsavel(), "NOTIFICA��O DE REVIS�O DE PROGRAMA DE QUALIDADE", "Existe uma nova revis�o para o programa de qualidade "+strProgramaQualidade+".<br><br>Entre no sistema e realize a avalia��o");
			}
			
			//APROVADO PARA UTILIZACAO
			if(((Revisao_programa_qualidade) entity).getSt_revisao_programa_qualidade().equals(2)){
				String strProgramaQualidade = r.toText("programa_qualidade", "cd_programa_qualidade", "nm_programa_qualidade", ((Revisao_programa_qualidade) entity ).getCd_programa_qualidade().toString() );
				Helpdesk_notificador.enviaEmailParaPessoa(((Revisao_programa_qualidade) entity).getCd_usuario_elaborador(), "NOTIFICA��O DE REVIS�O DE PROGRAMA DE QUALIDADE", "Existe uma nova avalia��o para o programa de qualidade <b>"+strProgramaQualidade+"</b>.<br><br>Entre no sistema para visualiz�-la.");
				Helpdesk_notificador.enviaEmailParaPessoa(((Revisao_programa_qualidade) entity).getCd_fiscal_responsavel(), "NOTIFICA��O DE REVIS�O DE PROGRAMA DE QUALIDADE", "Avalia��o do programa de qualidade <b>"+strProgramaQualidade+"</b> realizada com sucesso!");
			}
			
			//REPROVADA
			if(((Revisao_programa_qualidade) entity).getSt_revisao_programa_qualidade().equals(3)){
				String strProgramaQualidade = r.toText("programa_qualidade", "cd_programa_qualidade", "nm_programa_qualidade", ((Revisao_programa_qualidade) entity ).getCd_programa_qualidade().toString() );
				Helpdesk_notificador.enviaEmailParaPessoa(((Revisao_programa_qualidade) entity).getCd_usuario_elaborador(), "NOTIFICA��O DE REVIS�O DE PROGRAMA DE QUALIDADE", "Revis�o (<b>"+((Revisao_programa_qualidade) entity).getNm_revisao_programa_qualidade() +"</b>) do programa de qualidade <b>"+strProgramaQualidade+"</b> foi reprovada!");
			}
		}
		
		
		
    	this.setDao(new GenericDAO(true));
    	Tabela table = (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("ds_caminhoclasse", entity.getClass().getName() )).uniqueResult();
    	
    	if ( entity instanceof Notificacao ) {
    		try {
	
    			Notificacao notify = (Notificacao) entity;
    			this.setDao(new GenericDAO());
				Tipo_notificacao tipo = (Tipo_notificacao) this.getDao().getSession().createCriteria(Tipo_notificacao.class).add(Restrictions.eq("cd_tipo_notificacao", notify.getCd_tipo_notificacao() )).uniqueResult();
				if(tipo!=null){
					INotificador obj = (INotificador) Class.forName(tipo.getDs_caminho_classe()).newInstance();
					obj.efetuarNotificacao(entity);
				}

			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	if( entity instanceof Notificacao_automatica ){
    		
    		NotificacoesAutomaticasService notificacoesAutomaticasService = new NotificacoesAutomaticasService();
    		notificacoesAutomaticasService.onEditar((Notificacao_automatica) entity);
    		
    	}
    	 /*
        if ( entity instanceof Plano_acao ) {
        	
        	Plano_acao p = (Plano_acao) entity;
        	//if( p.getSt_plano_acao().equals(2) && p.getCd_usuario_validacao() == null){
        	if( p.getSt_plano_acao().equals(2) ){
        		Plano_acao_notificador.retiraNotificacaoResponsavelSetorCorrecao( (Plano_acao) entity);
        	}else{
        		Plano_acao_notificador.retiraNotificacaoResponsavelAbrir( (Plano_acao) entity);
        	}
            
        }
        
        if ( entity instanceof Atividade_plano_acao ) {

            Plano_acao_notificador.retiraNotificacaoResponsavelAtividade( (Atividade_plano_acao) entity);

        }
        
        if( entity instanceof Notificacao_plano_acao){
     	   Plano_acao_notificador.atualizaNotificacaoFeed( ( Notificacao_plano_acao) entity);
        }
    	
        
       
        if( entity instanceof Helpdesk_ocorrencia ){
        	
        	if( ((Helpdesk_ocorrencia) entity).getSt_helpdesk_ocorrencia().equals(3)){
        		Helpdesk_notificador.aumentaNotificacaoRecebida( (Helpdesk_ocorrencia) entity );
        	}else if( ((Helpdesk_ocorrencia) entity).getSt_helpdesk_ocorrencia().equals(4) ){
        		
        		if( ((Helpdesk_ocorrencia) entity).getTp_parecer_reclamante() == null ){
        			Helpdesk_notificador.aumentaNotificacaoEnviada( (Helpdesk_ocorrencia) entity);
        			
        		}else{
        			Helpdesk_notificador.diminuiNotificacaoEnviada( (Helpdesk_ocorrencia) entity);
        		}
        	}
        }
        
        if( entity instanceof Plano_acao_hp){ 
			if( ((Plano_acao_hp) entity).getSt_plano_acao().equals(2)){
				if(((Plano_acao_hp) entity).getCd_usuario_validacao()==null){
					Helpdesk_notificador.aumentaNotificacaoRecebida( (Plano_acao_hp) entity );
				}else{
					Helpdesk_notificador.diminuiNotificacaoRecebida( (Plano_acao_hp) entity );
					
					
					Helpdesk_notificador.aumentaNotificacaoEnviadaPeloPlanoFinalizado( (Plano_acao_hp) entity );
					
				}
			}
        }

        
        if( entity instanceof Acao_corretiva_helpdesk){ 
			if( ((Acao_corretiva_helpdesk) entity).getSt_status().equals(2) ){
				Helpdesk_notificador.diminuiNotificacaoRecebida( (Acao_corretiva_helpdesk) entity );	
			}
        }
       
        if( entity instanceof Acao_plano_acao_hp){ 
        	if( ((Acao_plano_acao_hp) entity).getSt_acao_plano_acao_hp().equals(2) ){
				Helpdesk_notificador.diminuiNotificacaoRecebida( (Acao_plano_acao_hp) entity );	
			}
        }
        */
        
        
    	if( entity instanceof Helpdesk_ocorrencia ){
        	
    		String valorURL="";
        	if( (((Helpdesk_ocorrencia) entity).getSt_helpdesk_ocorrencia().equals(4) || ((Helpdesk_ocorrencia) entity).getSt_helpdesk_ocorrencia().equals(0) )){
        		if( ((Helpdesk_ocorrencia) entity).getTp_parecer_reclamante() == null ){
        			if(((Helpdesk_ocorrencia) entity).getListaAcoesImediatas()!=null){
        				if(((Helpdesk_ocorrencia) entity).getSt_helpdesk_ocorrencia().equals(4)){

        					RelatorioBO r = new RelatorioBO();
	        				Integer contAcao  =0;
	        				Integer contAtiv =0;
	        				
	        				/*
	        				 Ol� Eliandro Renato Banfe.
Sua solicita��o foi analisada por: Eliandro Renato Banfe no dia: 19/08/2013 �s: 19:38.
Status: A��o finalizada.
Tendo como resposta: Foi ajustado o estoque conforme laudo tecnico repassado no email. Como a mudan�a foi feito durante o dia, n�o � poss�vel precisar a informa��o cadastrada. � necess�rio acompanhas os encerrantes e fazer um novo ajuste caso n�o tenha ficado correto.


Sua mensagem original:
Eliandro, favor imputar no sistema os encerrantes dos bicos conforme repassado por email.
 

D�vidas, favor entrar em contato.
Acesse o sistema para dar o parecer na ocorr�ncia!
Obrigado.
	        				 */
	        				
	        				
	        				
	        				String str = "<b>Ol� <font color='red'>"+ r.toText("usuario", "cd_usuario", "nm_usuario", ((Helpdesk_ocorrencia) entity ).getCd_responsavel_registro().toString() ) +"</font></b><br>"; 
	        				str		   += "<b>Sua solicita��o foi analisada por: </b>"+ r.toText("usuario", "cd_usuario", "nm_usuario", ((Helpdesk_ocorrencia) entity ).getCd_resp_resolucao().toString() ) 
	        						+"<b> no dia </b>"+new SimpleDateFormat("dd/MM/yyyy").format(new Date())  
	        						+"<b> �s </b>"+new SimpleDateFormat("HH:mm").format(new Date()) + ".<br><b>Status: </b>"+ RelatorioBO.codigoFixo("helpdesk_ocorrencia", "st_helpdesk_ocorrencia", ((Helpdesk_ocorrencia) entity ).getSt_helpdesk_ocorrencia())+"<br>";
	        				
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
	        		            		valorURL = valor+"?codusuario="+( (Helpdesk_ocorrencia) entity ).getCd_responsavel_registro()+"&codhelpdesk="+( (Helpdesk_ocorrencia) entity ).getCd_helpdesk_ocorrencia()+"&parecer";
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
	        				
	        				
	        				
	        				
	        				if(((Helpdesk_ocorrencia) entity).getListaAcoesImediatas().size()>0){
	        					str		   +="</b>";
	        					for(Acao_corretiva_helpdesk aCH : ((Helpdesk_ocorrencia) entity).getListaAcoesImediatas()){
	        						contAcao+=1;
	        						str += "<b>A��o</b> "+ contAcao +": " + aCH.getDs_acao_corretiva_helpdesk() + "<br>";
	        						contAtiv=0;
	        						for(Atividades_acao_imediata aAI : aCH.getListaAtividades() ){
	        							contAtiv+=1;
	        							if(aAI.getSt_status().equals(0)){
		        							str += " --- Atividade "+ contAcao +"."+ contAtiv +": "+ aAI.getDs_atividades() + "<br> --- Status: Aberta<br>";
		        						}else if( aAI.getSt_status().equals(1) ){
		        							str += " --- Atividade "+ contAcao +"."+ contAtiv +": "+ aAI.getDs_atividades() + "<br> --- Status: Em andamento<br>";
		        						}else{
		        							str += " --- Atividade "+ contAcao +"."+ contAtiv +": "+ aAI.getDs_atividades() + "<br> --- Status: Conclu�da<br>";
		        						}
	        							str+="<br>";
		        					}
		        					str+="<br>";
		        				}
	        					
	        					if(((Helpdesk_ocorrencia) entity).getCd_classificacao_helpdesk().equals(1) ||
	        							((Helpdesk_ocorrencia) entity).getCd_classificacao_helpdesk().equals(2) ){
	        						str += "<br><br>D�vidas, favor entrar em contato.<br><br>Obrigado.<br><br>";
	        					}else{
	        						str += "<br><br>D�vidas, favor entrar em contato.<br><br>Obrigado.<br><br><a href='"+ valorURL +"'>Acesse o sistema para dar o parecer na ocorr�ncia!</a><br>";
	        					}
	        					
		        				
		        				Helpdesk_notificador.enviaEmailParaPessoa(( (Helpdesk_ocorrencia) entity ).getCd_responsavel_registro(), "NOTIFICA��O DE T�RMINO DE OCORR�NCIA - PARECER", str);
		        			}else{
		        				
		        				str += "<b>Tendo como resposta:</b> "+ ( (Helpdesk_ocorrencia) entity ).getDs_resp_usuario() +"<br><br><br><b>Sua mensagem original:</b><br>"+ ( (Helpdesk_ocorrencia) entity ).getDs_ocorrencia();
	            				str += "<br><br>D�vidas, favor entrar em contato.<br><a href='"+ valorURL +"'>Acesse o sistema para dar o parecer na ocorr�ncia!</a><br>Obrigado.";
	            				
	            				
		        				Helpdesk_notificador.enviaEmailParaPessoa(( (Helpdesk_ocorrencia) entity ).getCd_responsavel_registro(), "NOTIFICA��O DE T�RMINO DE OCORR�NCIA - PARECER", str);	
		        			}
	        				
	        				if( ( (Helpdesk_ocorrencia) entity ).getCd_responsavel_ocorrencia() != null ){
	        					Helpdesk_notificador.enviaEmailParaPessoa(( (Helpdesk_ocorrencia) entity ).getCd_responsavel_ocorrencia(), "NOTIFICA��O DE T�RMINO DE OCORR�NCIA - PARECER", str);
	        				}
        				}
        			}
        			

//        			if( !((Helpdesk_ocorrencia) entity).getSt_helpdesk_ocorrencia().equals(0) ){
//        				
//        				if(((Helpdesk_ocorrencia) entity).getTp_notificados() == null){
//        					
//	        				String nomeUsuario = RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", ((Helpdesk_ocorrencia) entity ).getCd_resp_resolucao().toString());
//		        			List<Configuracao_helpdesk> listaConfiguracoesHelpdesk =  (List<Configuracao_helpdesk>) this.getDao().getSession().createCriteria(Configuracao_helpdesk.class)
//		        					.add(Restrictions.eq("cd_classificacao_helpdesk", ((Helpdesk_ocorrencia) entity).getCd_classificacao_helpdesk() ))
//		        					.add(Restrictions.or(Restrictions.isNull("cd_natureza_ocorrencia"), Restrictions.eq("cd_natureza_ocorrencia", ((Helpdesk_ocorrencia) entity).getCd_natureza_ocorrencia())))
//		        					.list();
//		        			
//		        			for(Configuracao_helpdesk conf : listaConfiguracoesHelpdesk){
//		        				String descricaoClassificacao =  RelatorioBO.toText("classificacao_helpdesk", "cd_classificacao_helpdesk", "ds_classificacao_helpdesk", ((Helpdesk_ocorrencia) entity).getCd_classificacao_helpdesk().toString() );
//		        				String descricaoNatureza =  	 RelatorioBO.toText("natureza_ocorrencia", "cd_natureza_ocorrencia", "ds_natureza_ocorrencia", ((Helpdesk_ocorrencia) entity).getCd_natureza_ocorrencia().toString() );
//		        				if(conf.getSt_recebe_email()!=null){
//		        					if(conf.getSt_recebe_email().equals(1)){
//		        						if( ! conf.getCd_colaborador().equals( ((Helpdesk_ocorrencia) entity ).getCd_resp_resolucao() )){
//		        							Helpdesk_notificador.enviaEmailParaPessoa( conf.getCd_colaborador(), "NOTIFICA��O DE AN�LISE DE OCORR�NCIA", "O usu�rio <b>"+ nomeUsuario + "</b> j� analisou o chamado.<br><br>Entre no sistema para visualizar ou alterar o chamado!");
//		        						}
//		        					}
//		        				}
//		        			}
//	        			
//        				}
//        			}
        			//Helpdesk_notificador.criaScheduler(( (Helpdesk_ocorrencia) entity ).getCd_helpdesk_ocorrencia());
        		}
        	} 
        }
    	
    	
//    	if( entity instanceof Diagrama_helpdesk){
//			if( ((Diagrama_helpdesk) entity).getSt_diagrama_helpdesk() != null ){
//				if( ((Diagrama_helpdesk) entity).getSt_diagrama_helpdesk().equals(1) ){
//					
//					if( ((Diagrama_helpdesk) entity).getCd_usuario_diagrama() !=null ){
//						RelatorioBO r = new RelatorioBO();
//						@SuppressWarnings("static-access")
//						String str = "Ol� <b>"+ r.toText("usuario", "cd_usuario", "nm_usuario", ((Diagrama_helpdesk) entity).getCd_usuario_diagrama().toString() )+"</b>,<br>"; 
//        				str		   += "O diagrama de c�digo ( "+ ((Diagrama_helpdesk) entity).getCd_diagrama_helpdesk() + " ) foi analisado como N�o eficaz.<br>Acesse o sistema para visualizar e/ou fazer um novo diagrama.";
//						Helpdesk_notificador.enviaEmailParaPessoa( ((Diagrama_helpdesk) entity).getCd_usuario_diagrama(), 
//								"NOT. DE N�O CONFORMIDADE DE DIAGRAMA", str);
//					}
//				}
//			}
//    	}
    	
    	if ( (table != null) && (this.getDao().getSession() != null) ) {
        	
    		if( entity instanceof Documento ){
    	    	metodoCRUD(table, 2, 2, true, (Documento) entity);
    	    	criaLogDocumento(2, (Documento) entity );
	   		}else{
	   			metodoCRUD(table, 2, 2, false, null);
	   		}
 
    	}

        /**
         * ESQUEMA PARA GERAR UM REGISTRO CRIPTOGRAFADO ESPELHO DE UMA EXECUCAO DE FORMULARIO;
        */
//        if(entity instanceof Execucao_formulario) {
//     	   
//     	   if ( (((Execucao_formulario) entity).getFormulario().getTp_formulario() != 0) || ((((Execucao_formulario) entity).getFormulario().getTp_formulario() == 0) && (((Execucao_formulario) entity).getListaRespostas_execucao_formulario() != null) && (((Execucao_formulario) entity).getListaRespostas_execucao_formulario().size() > 0))) {
// 	    	   	byte[] textoClaro = null;
//				try {
//					textoClaro = Criptografar.serializarObjeto(entity);
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
// 	    	   try {
// 	
// 	    		   byte[][] dadosCriptografados = Criptografar.cifra(textoClaro);
// 	
// 	    		   if (this.getDao() == null) {
// 	    			   this.setDao(new GenericDAO());
// 	    		   }
// 	
// 	    		   Espelho espelho = new Espelho();
// 	    		   espelho.setDs_bytedata_espelho(dadosCriptografados);
// 	    		   this.getDao().salvar(espelho);
// 	
// 				} catch (InvalidKeyException e) {
// 					// TODO Auto-generated catch block
// 					e.printStackTrace();
// 				} catch (NoSuchAlgorithmException e) {
// 					// TODO Auto-generated catch block
// 					e.printStackTrace();
// 				} catch (NoSuchPaddingException e) {
// 					// TODO Auto-generated catch block
// 					e.printStackTrace();
// 				} catch (IllegalBlockSizeException e) {
// 					// TODO Auto-generated catch block
// 					e.printStackTrace();
// 				} catch (BadPaddingException e) {
// 					// TODO Auto-generated catch block
// 					e.printStackTrace();
// 				} catch (InvalidAlgorithmParameterException e) {
// 					// TODO Auto-generated catch block
// 					e.printStackTrace();
// 				} catch (ClassNotFoundException e) {
// 					// TODO Auto-generated catch block
// 					e.printStackTrace();
// 				} catch (IOException e) {
// 					// TODO Auto-generated catch block
// 					e.printStackTrace();
// 				}
//     	   }
//        }
        
        //System.out.printf("Modificou um registro : "+ entity.getClass()  +"!!!!!");
        return false;
    }

    public boolean onLoad(Object entity,
                          Serializable id,
                          Object[] state,
                          String[] propertyNames,
                          Type[] types) {
//        if ( entity instanceof Plano_acao ) {
//            loads++;
//        }
    	
//    	System.out.printf("Loadou um registro do tipo: "+ entity.getClass()  +"!!!!!");
    	
        return false;
    }

	@SuppressWarnings({ "unchecked", "static-access" })
	public boolean onSave(Object entity,
                          Serializable id,
                          Object[] state,
                          String[] propertyNames,
                          Type[] types) {
    	
    	this.setDao(new GenericDAO(true));
    	Tabela table = (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("ds_caminhoclasse", entity.getClass().getName() )).uniqueResult();
    	
    	if ( entity instanceof Notificacao ) {
    		try {
				
    			Notificacao notify = (Notificacao) entity;
				Tipo_notificacao tipo = (Tipo_notificacao) this.getDao().getSession().createCriteria(Tipo_notificacao.class).add(Restrictions.eq("cd_tipo_notificacao", notify.getCd_tipo_notificacao() )).uniqueResult();
				if(tipo!=null){
					INotificador obj = (INotificador) Class.forName(tipo.getDs_caminho_classe()).newInstance();
					obj.efetuarNotificacao(entity);
				}
				
				
				
			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
		if( entity instanceof Notificacao_automatica ){
			   	
			NotificacoesAutomaticasService notificacoesAutomaticasService = new NotificacoesAutomaticasService();
		    notificacoesAutomaticasService.onSalvar((Notificacao_automatica) entity	);
			
		}

    	if ( entity instanceof Diagrama_helpdesk) {
    		if( ((Diagrama_helpdesk) entity	).getCd_diagrama_origem() != null ){
    			((Diagrama_helpdesk) entity	).setCd_helpdesk_ocorrencia( null );
    		}
    	}
    	 
		if ( entity instanceof Bug_report ) {
			
			EmailBO emailBO = new EmailBO();
			try {
				
				String msgErro = "Foi enviada uma nova reportagem de bug: <br><b>Programa:</b> ("+ ((Bug_report) entity	).getCd_programa() + ") " + RelatorioBO.toText("programa", "cd_programa", "ds_programa", ((Bug_report) entity ).getCd_programa().toString() ) +"<br>";
				
				msgErro += "<b>Usu�rio que registrou:</b> (" + ((Bug_report) entity	).getCd_usuario_reportador() +") "+ RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", ((Bug_report) entity ).getCd_usuario_reportador().toString() ) +"<br>";
				msgErro += "<b>Descri��o do erro:</b><br><br>" + ((Bug_report) entity	).getDs_descricao_bug() + "<br>";
				
				
				emailBO.sendEmail( "jeffersonbraga@primumsistemas.com.br" , "Jefferson Braga" , "BUG REPORT", msgErro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		if( entity instanceof Config_indicadores){
			
			Periodicidade periodicidade = (Periodicidade) this.getDao().getSession().createCriteria(Periodicidade.class).add(Restrictions.eq("cd_periodicidade", ( ( Config_indicadores) entity ).getCd_periodicidade() )).uniqueResult();
			Escala_tempo escala_tempo = (Escala_tempo) this.getDao().getSession().createCriteria(Escala_tempo.class).add(Restrictions.eq("cd_escala_tempo", periodicidade.getCd_escala_tempo() )).uniqueResult();
			
			Integer minutos = escala_tempo.getVl_equivalencia_minuto() * periodicidade.getVl_tempo_periodicidade();
			GregorianCalendar dataExecutar = (GregorianCalendar) new GregorianCalendar();
			dataExecutar.setTime(  ( ( Config_indicadores) entity ).getDt_inicio() );
			dataExecutar.set(Calendar.MINUTE, dataExecutar.get(Calendar.MINUTE) + minutos);
			
			System.out.println( new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format( ( ( Config_indicadores) entity ).getDt_inicio() ) );
			System.out.println( new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataExecutar.getTime()) );
			
			( ( Config_indicadores) entity ).getConfig_envio_indicadores().setDt_notificar( dataExecutar.getTime() );
		}
	
       /*
       if( entity instanceof Atividade_plano_acao ){
    	   Plano_acao_notificador.adicionaNotificacaoResponsavelAtividade( (Atividade_plano_acao) entity);
    	   return true;
       }
       
       if( entity instanceof Notificacao_plano_acao){
    	   Plano_acao_notificador.adicionaNotificacaoFeed();
    	   return true;
       }
		
       
       if( entity instanceof Helpdesk_ocorrencia){
    	   Helpdesk_notificador.notificaResponsavel( (Helpdesk_ocorrencia) entity );
       }
       
		if( entity instanceof Atividade_acao_pl_hp){
    	   Helpdesk_notificador.aumentaNotificacaoRecebida( (Atividade_acao_pl_hp) entity );
       }
       */
		
		if( entity instanceof Helpdesk_ocorrencia){
    	   if( ((Helpdesk_ocorrencia) entity).getCd_classificacao_helpdesk().equals(1) || ((Helpdesk_ocorrencia) entity).getCd_classificacao_helpdesk().equals(2) ){
    		   Dbconf dbconf = null;
    			try {
    				
    				
    				HelpdeskBO hBO = new HelpdeskBO();
    				Integer responsavel = hBO.retornaResponsavel( ((Helpdesk_ocorrencia) entity) );
    				
    				
    				
    				if(responsavel != null){
    					
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
	    	            		
	    	            		String descricaoClassificacao =  RelatorioBO.toText("classificacao_helpdesk", "cd_classificacao_helpdesk", "ds_classificacao_helpdesk", ((Helpdesk_ocorrencia) entity).getCd_classificacao_helpdesk().toString() );
    	            			String descricaoNatureza =  	 RelatorioBO.toText("natureza_ocorrencia", "cd_natureza_ocorrencia", "ds_natureza_ocorrencia", ((Helpdesk_ocorrencia) entity).getCd_natureza_ocorrencia().toString() );
    	            				
            					String descricaoChamado = " - ";  
            					if( ((Helpdesk_ocorrencia) entity).getDs_ocorrencia().length() > 50 ){
            						descricaoChamado += ((Helpdesk_ocorrencia) entity).getDs_ocorrencia().substring(0, 50)+"...";
            					}else{
            						descricaoChamado += ((Helpdesk_ocorrencia) entity).getDs_ocorrencia() + "...";
            					}
            					
            					String nome_usuario = RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", responsavel.toString() );
            					if( nome_usuario.split(" ").length > 1){
            						nome_usuario = nome_usuario.split(" ")[0];
            					}
            					String strChamado = "<b>Ol� <font color='red'>"+ nome_usuario  +"</font></b><br><br>"; 
            					strChamado	+= "Foi aberto um chamado de classifica��o: <b>"+ descricaoClassificacao + "</b> e natureza: <b>"+ descricaoNatureza+"</b><br> pelo solicitante: <b>"+ RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario",  ((Helpdesk_ocorrencia) entity).getCd_responsavel_registro().toString() )+"</b>"
            					+" no dia <b>"+new SimpleDateFormat("dd/MM/yyyy").format(new Date())  
                						+"</b> �s <b>"+new SimpleDateFormat("HH:mm").format(new Date()) + "</b><br><font color='red'>Descri��o da ocorr�ncia:</font> " + ((Helpdesk_ocorrencia) entity).getDs_ocorrencia();
            					strChamado += "<br><br>D�vidas, favor entrar em contato.<br><br>Obrigado.<br><br>";
                				
            					parametros="";
            					parametros= valor + "?codusuario="+responsavel+"&codhelpdesk="+((Helpdesk_ocorrencia) entity).getCd_helpdesk_ocorrencia()+"&analisar";
            					parametroAux = valor + "?codusuario="+responsavel+"&codhelpdesk="+((Helpdesk_ocorrencia) entity).getCd_helpdesk_ocorrencia()+"&adotar";
            					Helpdesk_notificador.enviaEmailParaPessoa( responsavel, "NOT. DE ABERTURA DE CHAMADO" + descricaoChamado, strChamado +"<br><br><a href='"+ parametros +"'>Acesse o sistema para analizar o chamado!<br><br></a><br><br>" +
            							"<a href='"+ parametroAux +"'>Clique aqui </a>para apenas adotar chamado!" );
    	            				
	    	            	}
	    	            }
	    	            in.close();
    	            }
    		    } catch (IOException e) {
    		    	//LogPrimum.mensagem("ERRO LINK EMAIL");
    		    	return false;
    		    } catch (Exception e) {
    		    	//LogPrimum.mensagem("ERRO LINK EMAIL");
    		    	return false;
    			}
    	   }
		}
		
		//SE A REVISAO ESTA COM STATUS 'AGUARDANDO APROVA��O LEGAL'
		//MANDA EMAIL PARA O FISCAL
		if( entity instanceof Revisao_programa_qualidade){
			if(((Revisao_programa_qualidade) entity).getSt_revisao_programa_qualidade().equals(1)){
				RelatorioBO r = new RelatorioBO();
				String strProgramaQualidade = RelatorioBO.toText("programa_qualidade", "cd_programa_qualidade", "nm_programa_qualidade", ((Revisao_programa_qualidade) entity ).getCd_programa_qualidade().toString() );
				Helpdesk_notificador.enviaEmailParaPessoa(((Revisao_programa_qualidade) entity).getCd_fiscal_responsavel(), "NOTIFICA��O DE REVIS�O DE PROGRAMA DE QUALIDADE", "Existe uma nova revis�o para o programa de qualidade "+strProgramaQualidade+".<br><br>Entre no sistema e realize a avalia��o");
			}
			
			if(((Revisao_programa_qualidade) entity).getSt_revisao_programa_qualidade().equals(2)){
				RelatorioBO r = new RelatorioBO();
				String strProgramaQualidade = RelatorioBO.toText("programa_qualidade", "cd_programa_qualidade", "nm_programa_qualidade", ((Revisao_programa_qualidade) entity ).getCd_programa_qualidade().toString() );
				
				Helpdesk_notificador.enviaEmailParaPessoa(((Revisao_programa_qualidade) entity).getCd_usuario_elaborador(), "NOTIFICA��O DE REVIS�O DE PROGRAMA DE QUALIDADE", "Existe uma nova avalia��o para o programa de qualidade "+strProgramaQualidade+".<br><br>Entre no sistema para visualiz�-la.");
				Helpdesk_notificador.enviaEmailParaPessoa(((Revisao_programa_qualidade) entity).getCd_fiscal_responsavel(), "NOTIFICA��O DE REVIS�O DE PROGRAMA DE QUALIDADE", "Avalia��o do programa de qualidade "+strProgramaQualidade+" realizada com sucesso!");
			}
		}
				
		
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
            		valorURL = valor;
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
		
		
	    if ( entity instanceof Plano_acao ) {
	    	Setor s = (Setor) this.getDao().getSession().createCriteria(Setor.class).add(Restrictions.eq("cd_setor", ( (Plano_acao) entity).getCd_setor_responsavel() )).uniqueResult();
	    	if(s!=null){
	    		String nomeSetor = RelatorioBO.toText("setor","cd_setor", "nm_setor", s.getCd_setor().toString());
	    		String nomeRespSetor = RelatorioBO.toText("usuario","cd_usuario", "nm_usuario", s.getCd_responsavel_setor().toString());
	    		Helpdesk_notificador.enviaEmailParaPessoa(s.getCd_responsavel_setor() , "NOTIFICA��O DE PLANO A��O", "Ol� <b>"+ nomeRespSetor+"</b>, <br><br>Foi aberta uma nova solicita��o para o setor <b>"+ nomeSetor+"</b> no qual voc� � respons�vel.<br><br><a href='"+ valorURL +"'>Acesse o sistema para tomar as devidas provid�ncias!</a>");
	    	}
	    	
        }
		
		if( entity instanceof Acao_corretiva_helpdesk){ 
			RelatorioBO r = new RelatorioBO();
			
			String str = "<b>Ol� "+ r.toText("usuario", "cd_usuario", "nm_usuario", ((Acao_corretiva_helpdesk) entity ).getCd_responsavel_acao().toString() ) +"</b><br><br>";
			
			str += "<b>Voc� � respons�vel pela execu��o da seguinte a��o:</b><br>";
			str += "<b>A��o imediata: </b>"+((Acao_corretiva_helpdesk) entity ).getDs_acao_corretiva_helpdesk()+"<br>";
			
			if( ((Acao_corretiva_helpdesk) entity ).getDt_prazo_previsto()!=null ){
				if( ((Acao_corretiva_helpdesk) entity ).getDt_prazo_previsto().toLocaleString().split(" ").length> 1 ){
					str += "<b>Prazo previsto:</b> "+ ((Acao_corretiva_helpdesk) entity ).getDt_prazo_previsto().toLocaleString().split(" ")[0]+" - <b>Hora:</b> "+ ((Acao_corretiva_helpdesk) entity ).getDt_prazo_previsto().toLocaleString().split(" ")[1]+"<br>";
				}
			}

			str += "<br><br>D�vidas, favor entrar em contato.<br><br>Obrigado.<br><br><a href='"+ valorURL +"'>Acesse o sistema para alterar o status da a��o!</a>";
			
			Helpdesk_notificador.enviaEmailParaPessoa(( (Acao_corretiva_helpdesk) entity ).getCd_responsavel_acao(), "NOTIFICA��O DE A��O IMEDIATA", str );
       }
       if( entity instanceof Atividades_acao_imediata){
    	   RelatorioBO r = new RelatorioBO();
    	   String cod_acao = r.toText("acao_corretiva_helpdesk", "cd_acao_corretiva_helpdesk", "cd_helpdesk_ocorrencia", ((Atividades_acao_imediata) entity ).getCd_acao_corretiva_helpdesk().toString() ) +"<br>";
    	   
    	   String str = "Ocorr�ncia: " + r.toText("helpdesk_ocorrencia", "cd_helpdesk_ocorrencia", "ds_ocorrencia", cod_acao ) +"<br>";
    	   str += "Atividade corretiva: "+  r.toText("atividades", "cd_atividades", "nm_atividades", ((Atividades_acao_imediata) entity ).getCd_atividades().toString() )+"<br>";
    	   str += "Descri��o atividade: "+  ((Atividades_acao_imediata) entity ).getDs_atividades()+"<br>";
    	   
    	   if( ((Atividades_acao_imediata) entity ).getDt_prazo_previsto()!=null ){
    		   if( ((Atividades_acao_imediata) entity ).getDt_prazo_previsto().toLocaleString().split(" ").length> 1 ){
	    		   str += "Prazo previsto para execu��o: "+ ((Atividades_acao_imediata) entity ).getDt_prazo_previsto().toLocaleString().split(" ")[0]+ " - Hora: "+ ((Atividades_acao_imediata) entity ).getDt_prazo_previsto().toLocaleString().split(" ")[1]+"<br>";
	    	   }
    	   }
    	   
    	   str += "<br>Acesse o sistema para acompanhar a a��o";
    	   Helpdesk_notificador.enviaEmailParaPessoa(( (Atividades_acao_imediata) entity ).getCd_responsavel(), "NOTIFICA��O DE ATIVIDADE DE A��O IMEDIATA", str );
       }
       
       
       if ( (table != null) && (this.getDao().getSession() != null) ) {
    	
    	    if( entity instanceof Documento ){
    	    	metodoCRUD(table, 1, 1, true, (Documento) entity);
    	    	
    	    	String nome;
    	    	Object valor;
    	    	
				try {
					nome = Reflection.getNomeChavePrimaria(entity);
					valor = this.getDao().getSession().createCriteria(entity.getClass()).setProjection(Projections.projectionList().add(Projections.max(nome))).uniqueResult();	
					
					Documento d = new Documento();
					d.setCd_documento( Integer.parseInt(valor.toString()) + 1 );
					d.setCd_responsavel_criar( ( (Documento) entity).getCd_responsavel_criar() );
					d.setNm_documento( ( (Documento) entity).getNm_documento() );
					
					criaLogDocumento(1, d);
				} catch (Exception e) {
					e.printStackTrace();
				}
	   		}else{
	   			metodoCRUD(table, 1, 1, false, null);
	   		}
       }
       
       return false;
    }

    public void afterTransactionCompletion(Transaction tx) {
        if ( tx.getStatus() == TransactionStatus.COMMITTED ) {
            System.out.printf("Creations: " + creates + ", Updates: " + updates, "Loads: " + loads);
            System.out.printf("tx.getClass: " + tx.getClass() );
        }
        
        //if(HibernateSessionFactory.getSession()!=null)
        	//HibernateSessionFactory.getSession().flush();
        updates=0;
        creates=0;
        loads=0;
        //INFORMA AOS RESPONSÁVEIS QUE REGISTROS FORAM ADICIONADOS.
/*        ChatService cs = new ChatService();
        for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			cs.enviarMensagem( usuariosLogados.getClientId() , "atualiza_notificacoes" );
		}*/
    }

	@Override
	public void setTransactionID(String id) {
		System.out.println("setTransactionID: " + id);
	}

	@Override
	public void setObjetoInterceptado(Object o) {
		this.objetoEntity = o;
	}

	@Override
	public Object getObjetoInterceptado() {

		return this.objetoEntity;
	}
	
	@SuppressWarnings("unchecked")
	public void criaLogDocumento( Integer tp_log_documento, Documento doc ){
		Log_documento log = new Log_documento();
			
		log.setCd_documento( doc.getCd_documento() );
		log.setCd_usuario( doc.getCd_responsavel_criar() );
		log.setTp_log_documento( tp_log_documento );
		log.setNm_documento(doc.getNm_documento() );
		log.setDt_log_documento( new Date() );
		
		CrudGenericBO crud = new CrudGenericBO();
	    try {
			crud.salvar(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void metodoCRUD(Tabela table, Integer cd_operacao_notificacao, Integer tp_operacao, Boolean ehDocumento, Documento doc ){
		
		if ( (table != null) && (this.getDao().getSession() != null) ) {
        	
    		List<Notificacao_automatica> listaNotCRUD = this.getDao().getSession().createCriteria(Notificacao_automatica.class)
    								    	   	.add(Restrictions.isNull("cd_periodicidade"))
    								    	   	.add(Restrictions.eq("cd_tabela", table.getCd_tabela()))
    								    	   	.add(Restrictions.eq("cd_operacao_notificacao", cd_operacao_notificacao))
    								    	   	.list();

    	   if ( (listaNotCRUD != null) && (listaNotCRUD.size() > 0) ) {
    		   NotificacoesAutomaticasService notificacoesAutomaticasService = new NotificacoesAutomaticasService();
    		   for (Notificacao_automatica notificacao : listaNotCRUD ) {
    			   
    			   List<Notificacao_usuario> listaUsuarios = this.getDao().getSession().createCriteria(Notificacao_usuario.class)
    					   .add(Restrictions.eq("cd_notificacao_automatica", notificacao.getCd_notificacao_automatica()))
    					   .add(Restrictions.isNull("cd_notificacao"))
    					   .list();
				   
//        			   if(ehDocumento && doc.getTp_disponibilidade().equals(1)){
//        				   List<Notificacao_usuario> listaUsuAux = new ArrayList<>();
//        				   for(Notificacao_usuario nu :listaUsuarios){
//        					   for(Permissao_acesso_documento pad :doc.getListaPermissoes()){
//                				   if(pad.getCd_colaborador().equals(nu.getCd_usuario())){
//                					   listaUsuAux.add(nu);
//                					   break;
//                				   }
//                			   }
//            			   }
//        				   notificacao.setListaNotificacaoUsuario(listaUsuAux);
//        			   }else{
//	        			   notificacao.setListaNotificacaoUsuario(listaUsuarios);
//        			   }
    			   
    			   notificacao.setListaNotificacaoUsuario(listaUsuarios);
    			   notificacoesAutomaticasService.onSalvarCRUD( notificacao , tp_operacao , doc.getCd_documento());
    		   }
    	   }
    	   
    	   /**
    	    * LISTA DE QUEM DEVE APROVAR UM DOCUMENTO
    	   
    	   if ( (doc != null) && (doc.getListaChecagem_documento() != null) ) {
    		   
    		   Checagem_documento checkAux = null;
			   for (Checagem_documento check : doc.getListaChecagem_documento()) {

	    		   boolean inserirNotificacao = true;
				   for (Notificacao_usuario not_usu : doc.getNotificacao().getLista_notificacao_usuario()) {
					   if ( (not_usu.getCd_usuario().equals(check.getCd_usuario())) &&
						    (not_usu.getSt_notificar().equals(5)) ) {
						   inserirNotificacao = false;
						   break;
					   }
				   }

        		   if ( (inserirNotificacao) && (check.getSt_checagem_documento() == null) && (check.getTp_checagem_documento() == null) ) {
        			   Notificacao_usuario not_usu = new Notificacao_usuario();
        			   not_usu.setCd_usuario(check.getCd_usuario());
        			   not_usu.setCd_codigo_parametro(doc.getCd_documento());
        			   not_usu.setSt_notificar(5);
        			   not_usu.setSt_visualizado(0);

        			   if (doc.getNotificacao().getLista_notificacao_usuario() == null) {
        				   doc.getNotificacao().setLista_notificacao_usuario(new ArrayList<Notificacao_usuario>());
        			   }

        			   doc.getNotificacao().getLista_notificacao_usuario().add(not_usu);
        		   } else if ( (inserirNotificacao) && (check.getSt_checagem_documento() == null) && (check.getTp_checagem_documento() != null) && ( (checkAux != null) && (checkAux.getSt_checagem_documento() != null) && (checkAux.getSt_checagem_documento().equals(1))) ) {
        			   Notificacao_usuario not_usu = new Notificacao_usuario();
        			   not_usu.setCd_usuario(check.getCd_usuario());
        			   not_usu.setCd_codigo_parametro(doc.getCd_documento());
        			   not_usu.setSt_notificar(5);
        			   not_usu.setSt_visualizado(0);

        			   if (doc.getNotificacao().getLista_notificacao_usuario() == null) {
        				   doc.getNotificacao().setLista_notificacao_usuario(new ArrayList<Notificacao_usuario>());
        			   }

        			   doc.getNotificacao().getLista_notificacao_usuario().add(not_usu);
        		   }
    			   checkAux = check;
        	   }
    	   } */
       }
	}


//	public void atualizaDicionario(Tabela t, Campo c, CodigosFixos cf){
//		if( t != null ){
//			List<Tabela> listaTabela = new ArrayList<>();
//			if(DicionarioDados.getInstance().getListaTabelas()!=null){
//				listaTabela.addAll( DicionarioDados.getInstance().getListaTabelas() );	
//			}
//			listaTabela.add( ((Tabela) t) );
//			DicionarioDados.getInstance().setListaTabelas( listaTabela );
//		}else if( c != null ){
//			if(DicionarioDados.getInstance().getListaTabelas()!=null){
//				for(Tabela tab : DicionarioDados.getInstance().getListaTabelas()){
//					if(tab.getCd_tabela().equals(c.getCd_tabela())){
//						List<Campo> listaCampos = new ArrayList<>();
//						listaCampos.addAll(tab.getListaCampos());
//						listaCampos.add(c);
//						tab.setListaCampos(listaCampos);
//					}
//				}
//			}
//		}else if( cf != null ){
//			if(DicionarioDados.getInstance().getListaTabelas()!=null){
//				for(Tabela tab : DicionarioDados.getInstance().getListaTabelas()){
//					if(tab.getCd_tabela().equals(c.getCd_tabela())){
//						for(Campo campo : tab.getListaCampos()){
//							if(campo.getCd_campo().equals( cf.getCd_campo() )){
//								List<CodigosFixos> listaCF = new ArrayList<>();
//								listaCF.addAll(campo.getListaCodigosFixos());
//								listaCF.add(cf);
//								campo.setListaCodigosFixos( listaCF );
//							}
//						}
//					}
//				}
//			}
//		}
//	}
			
	public void enviarNotificacao(Object o) {

		if (o.equals(this.getObjetoInterceptado())) {
		
			Tabela table = (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("ds_caminhoclasse", o.getClass().getName() )).uniqueResult();
	
	    	if(table!=null){
	
	    		try {
					Notificacao notify = (Notificacao) this.getDao().getSession().createCriteria(Notificacao.class)
											.add(Restrictions.eq("cd_tabela", table.getCd_tabela() ))
											.add(Restrictions.eq("cd_codigo_parametro", Reflection.getValorChavePrimaria(o) )).uniqueResult();
	
					Tipo_notificacao tipo = (Tipo_notificacao) this.getDao().getSession().createCriteria(Tipo_notificacao.class).add(Restrictions.eq("cd_tipo_notificacao", notify.getCd_tipo_notificacao() )).uniqueResult();
					if(tipo!=null){
						INotificador obj = (INotificador) Class.forName(tipo.getDs_caminho_classe()).newInstance();
						obj.efetuarNotificacao(o);
					}
	
				} catch (HibernateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	
	    	this.setObjetoInterceptado(null);
		}
	}

}