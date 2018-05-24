package br.com.primum.bo.indicadores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;


//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.bo.email.EmailBO;
import br.com.primum.bo.escalonamento_formulario.Escalonamento_formularioBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.bo.relatorios.RelatorioBO;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.campo_agrupador.Campo_agrupador;
import br.com.primum.modelo.campo_formulario.Campo_formulario;
import br.com.primum.modelo.campo_indicador.Campo_indicador;
import br.com.primum.modelo.classificacao_helpdesk.Classificacao_helpdesk;
import br.com.primum.modelo.classificacao_ouvidoria.Classificacao_ouvidoria;
import br.com.primum.modelo.conclusao_auditoria.Conclusao_auditoria;
import br.com.primum.modelo.cronograma_auditoria.Cronograma_auditoria;
import br.com.primum.modelo.escalonamento_formulario.Escalonamento_formulario;
import br.com.primum.modelo.escalonamento_usuario.Escalonamento_usuario;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.grupo_auditoria.Grupo_auditoria;
import br.com.primum.modelo.grupo_formulario.Grupo_formulario;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.modelo.indicador_customizado.Indicador_customizado;
import br.com.primum.modelo.modulo_indicadores.Modulo_indicadores;
import br.com.primum.modelo.natureza_ocorrencia.Natureza_ocorrencia;
import br.com.primum.modelo.ocorrencia_ouvidoria.Ocorrencia_ouvidoria;
import br.com.primum.modelo.pergunta_auditoria.Pergunta_auditoria;
import br.com.primum.modelo.permissao_indicador.Permissao_indicador;
import br.com.primum.modelo.plano_acao.Plano_acao;
import br.com.primum.modelo.plano_acao_formulario.Plano_acao_formulario;
import br.com.primum.modelo.planos_cronograma_auditoria.Planos_cronograma_auditoria;
import br.com.primum.modelo.programa_qualidade.Programa_qualidade;
import br.com.primum.modelo.resposta_exec_auditoria.Resposta_exec_auditoria;
import br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario;
import br.com.primum.modelo.setor.Setor;
import br.com.primum.modelo.tipo_turno.Tipo_turno;
import br.com.primum.modelo.unidade.Unidade;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.notificadores.Helpdesk_notificador;
import br.com.primum.notificadores.Indicadores_notificador;

public class IndicadoresBO {
	private GenericDAO dao;
	
	GregorianCalendar dataAtual = (GregorianCalendar) new GregorianCalendar();
	GregorianCalendar dataFim = (GregorianCalendar) new GregorianCalendar();
	GregorianCalendar dataInicialDia = (GregorianCalendar) new GregorianCalendar();
	GregorianCalendar dataFinalDia = (GregorianCalendar) new GregorianCalendar();
	
	public String susbtituiAcentosHTML(String str){
		
		str = str.replaceAll("Á","&Aacute");
		str = str.replaceAll("á","&aacute");
		str = str.replaceAll("Â","&Acirc");
		str = str.replaceAll("â","&acirc");
		str = str.replaceAll("À","&Agrave");
		str = str.replaceAll("à","&agrave");
		str = str.replaceAll("Ã","&Atilde");
		str = str.replaceAll("ã","&atilde");
		str = str.replaceAll("É","&Eacute");
		str = str.replaceAll("é","&eacute");
		str = str.replaceAll("Ê","&Ecirc");
		str = str.replaceAll("ê","&ecirc");
		str = str.replaceAll("È","&Egrave");
		str = str.replaceAll("è","&egrave");
		str = str.replaceAll("Í","&Iacute");
		str = str.replaceAll("í","&iacute");
		str = str.replaceAll("Î","&Icirc");
		str = str.replaceAll("î","&icirc");
		str = str.replaceAll("Ì","&Igrave");
		str = str.replaceAll("ì","&igrave");
		str = str.replaceAll("Ó","&Oacute");
		str = str.replaceAll("ó","&oacute");
		str = str.replaceAll("Ô","&Ocirc");
		str = str.replaceAll("ô","&ocirc");
		str = str.replaceAll("Ò","&Ograve");
		str = str.replaceAll("ò","&ograve");
		str = str.replaceAll("Õ","&Otilde");
		str = str.replaceAll("õ","&otilde");
		str = str.replaceAll("Ú","&Uacute");
		str = str.replaceAll("ú","&uacute");
		str = str.replaceAll("Ù","&Ugrave");
		str = str.replaceAll("ù","&ugrave");
		str = str.replaceAll("Ü","&Uuml");
		str = str.replaceAll("ü","&uuml");
		str = str.replaceAll("Ç","&Ccedil");
		str = str.replaceAll("ç","&ccedil");
		str = str.replaceAll("Ñ","&Ntilde");
		str = str.replaceAll("ñ","&ntilde");

		return str;
		
	}
	
	@SuppressWarnings("static-access")
	public GregorianCalendar getDataAtual() {
		dataAtual = (GregorianCalendar) new GregorianCalendar().getInstance();
		dataAtual.set(Calendar.HOUR_OF_DAY , 1 );
		dataAtual.set(Calendar.MINUTE      , 0 );
		dataAtual.set(Calendar.SECOND      , 0 );
		dataAtual.set(Calendar.MILLISECOND , 0 );
		return dataAtual;
	}

	public void setDataAtual(GregorianCalendar dataAtual) {
		this.dataAtual = dataAtual;
	}

	@SuppressWarnings("static-access")
	public GregorianCalendar getDataFim() {
		dataFim = (GregorianCalendar) new GregorianCalendar().getInstance();
		dataFim.set(Calendar.HOUR_OF_DAY  , 23 );
		dataFim.set(Calendar.MINUTE, 59);
		dataFim.set(Calendar.SECOND, 59);
		dataFim.set(Calendar.MILLISECOND, 0);
		return dataFim;
	}

	public void setDataFim(GregorianCalendar dataFim) {
		this.dataFim = dataFim;
	}

	@SuppressWarnings("static-access")
	public GregorianCalendar getDataInicialDia() {
		dataInicialDia = (GregorianCalendar) new GregorianCalendar().getInstance();
		dataInicialDia.set(Calendar.HOUR_OF_DAY,0);     
		dataInicialDia.set(Calendar.MINUTE,0);     
		dataInicialDia.set(Calendar.SECOND,0);
		return dataInicialDia;
	}

	public void setDataInicialDia(GregorianCalendar dataInicialDia) {
		this.dataInicialDia = dataInicialDia;
	}

	@SuppressWarnings("static-access")
	public GregorianCalendar getDataFinalDia() {
		dataFinalDia = (GregorianCalendar) new GregorianCalendar().getInstance();
		dataFinalDia.set(Calendar.HOUR_OF_DAY,23);     
		dataFinalDia.set(Calendar.MINUTE,59);     
		dataFinalDia.set(Calendar.SECOND,59);
		return dataFinalDia;
	}

	public void setDataFinalDia(GregorianCalendar dataFinalDia) {
		this.dataFinalDia = dataFinalDia;
	}

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	//CONSTRUTOR
	public IndicadoresBO(){}
	
	// ******************************************
	// ***********  AUDITORIA  ******************
	//******************************************
	@SuppressWarnings("unchecked")
	public List<?> Auditoria_ScorePorAvaliacao(Integer cd_cronograma_auditoria){
		this.setDao(new GenericDAO());
		
		/*PesquisaPadraoBO pesquisa= new PesquisaPadraoBO();

		List<Conclusao_auditoria> listaConclusoes = (List<Conclusao_auditoria>) this.getDao().getSession().createCriteria(Conclusao_auditoria.class).list();
		List<String> listaNomes = new ArrayList<>();
		List<Integer> listaScore = new ArrayList<>();
		List<Integer> listaMedia = new ArrayList<>();
		List<List<?>> listaGeral = new ArrayList<>();
		Integer contTotalPerguntas =0;
		Integer contTotalConformes=0;
		
		for(Conclusao_auditoria conclusao : listaConclusoes){
			pesquisa.buscarObjetoCompleto(conclusao);
			Cronograma_auditoria cronograma= (Cronograma_auditoria) this.getDao().getSession().createCriteria(Cronograma_auditoria.class).add(Restrictions.eq("cd_cronograma_auditoria", conclusao.getCd_cronograma_auditoria())).uniqueResult();
			conclusao.setCronograma_auditoria(cronograma);
			
			for(Planos_cronograma_auditoria planos : conclusao.getCronograma_auditoria().getListaPlanoAuditoria() ){
				for(Grupo_auditoria grupo : planos.getAuditoria().getListaGruposAuditoria()){
					listaNomes.add( grupo.getNm_grupo_auditoria() );
					
					//**************
					Integer qtdePerguntas = grupo.getListaPerguntasAuditoria().size();
					Integer contConformes = 0;
					Integer mediaFinal = 0;
					
					contTotalPerguntas+=qtdePerguntas;
					
					for(Pergunta_auditoria perg: grupo.getListaPerguntasAuditoria()){
						
						for(Resposta_execucao_auditoria resp : planos.getExecucao_auditoria().getListaRespostas()){
							if(resp.getCd_pergunta_auditoria().equals( perg.getCd_pergunta_auditoria() )){
								if(resp.getResposta_auditoria().getTp_resposta_auditoria() == 1){
									contConformes+=1;
								}
								break;
							}
						}
						
					}
					
					contTotalConformes += contConformes;
					
					System.out.println("contConformes: "+ contConformes);
					mediaFinal = (contConformes*100)/qtdePerguntas;
					listaScore.add( mediaFinal );
				}
			}
		}
		listaMedia.add ((contTotalConformes * 100) / contTotalPerguntas);
		
		listaGeral.add(listaNomes);
		listaGeral.add(listaScore);
		listaGeral.add(listaMedia);
		
		return listaGeral;
		*/
		
		PesquisaPadraoBO pesquisa= new PesquisaPadraoBO();
		
		List<String> listaNomes = new ArrayList<>();
		List<Integer> listaScore = new ArrayList<>();
		List<Integer> listaMedia = new ArrayList<>();
		List<List<?>> listaGeral = new ArrayList<>();
		Integer contTotalPerguntas =0;
		Integer contTotalConformes=0;
		
		List<Cronograma_auditoria> listaCronograma = (List<Cronograma_auditoria>) this.getDao().getSession().createCriteria(Cronograma_auditoria.class).add( Restrictions.eq("cd_cronograma_auditoria", cd_cronograma_auditoria) ).list();
		Cronograma_auditoria cronograma = listaCronograma.get(0);
		pesquisa.buscarObjetoCompleto(cronograma);
		
		for(Planos_cronograma_auditoria planos : cronograma.getListaPlanoAuditoria() ){
			for(Grupo_auditoria grupo : planos.getAuditoria().getListaGruposAuditoria()){
				listaNomes.add( grupo.getNm_grupo_auditoria() );
				
				//**************
				Integer qtdePerguntas = grupo.getListaPerguntasAuditoria().size();
				Integer contConformes = 0;
				Integer mediaFinal = 0;
				
				contTotalPerguntas+=qtdePerguntas;
				
				for(Pergunta_auditoria perg: grupo.getListaPerguntasAuditoria()){
					if(planos.getExecucao_auditoria() != null ){
						for(Resposta_exec_auditoria resp : planos.getExecucao_auditoria().getListaRespostas()){
							if(resp.getCd_pergunta_auditoria().equals( perg.getCd_pergunta_auditoria() )){
								if(resp.getResposta_auditoria().getTp_resposta_auditoria() == 1){
									contConformes+=1;
								}
								break;
							}
						}
					}else{
						contConformes+=1;
					}
					
				}
				
				contTotalConformes += contConformes;
				
				System.out.println("contConformes: "+ contConformes);
				mediaFinal = (contConformes*100)/qtdePerguntas;
				listaScore.add( mediaFinal );
			}
		}
		if(contTotalPerguntas>0 ){
			listaMedia.add ((contTotalConformes * 100) / contTotalPerguntas);	
		}else{
			listaMedia.add(0);
		}
		
		
		listaGeral.add(listaNomes);
		listaGeral.add(listaScore);
		listaGeral.add(listaMedia);
		
		return listaGeral;
		
	}
	
	public List<?> Auditoria_Pontuacao_e_ScoreConformidade(){
		this.setDao(new GenericDAO());
		List<?> a = new ArrayList<>();
		return a;
	}
	
	@SuppressWarnings({ "unchecked"})
	//public List<?> Auditoria_EvolucaoPlanoDeAuditoria(Integer cd_auditoria, Integer cd_pessoa_juridica, Integer tp_cronograma_auditoria){
	public List<?> Auditoria_EvolucaoPlanoDeAuditoria(Integer cd_auditoria){
		this.setDao(new GenericDAO());
		
		List<String> listaNomes = new ArrayList<>();
		List<Integer> listaScore = new ArrayList<>();
		List<Date> listaDatas = new ArrayList<>();
		List<List<?>> listaGeral = new ArrayList<>();
		
		PesquisaPadraoBO pesquisa= new PesquisaPadraoBO();
		List<Cronograma_auditoria> listaCronogramas = (List<Cronograma_auditoria>) this.getDao().getSession().createCriteria(Cronograma_auditoria.class).addOrder( Order.asc("dt_cronograma_auditoria_inicio")).list();
		for(Cronograma_auditoria cronograma_auditoria : listaCronogramas){
			//SO PEGA OS PLANOS DOS CRONOGRAMAS FINALIZADOS
			if(cronograma_auditoria.getSt_cronograma_auditoria()==2){
				
				pesquisa.buscarObjetoCompleto(cronograma_auditoria);
				List<Planos_cronograma_auditoria> listaPlanos = (List<Planos_cronograma_auditoria>) this.getDao().getSession().createCriteria(Planos_cronograma_auditoria.class).add(Restrictions.eq("cd_cronograma_auditoria", cronograma_auditoria.getCd_cronograma_auditoria())).list();
				cronograma_auditoria.setListaPlanoAuditoria(listaPlanos);
				
				for(Planos_cronograma_auditoria planos : cronograma_auditoria.getListaPlanoAuditoria()){
					if(planos.getCd_auditoria().equals( cd_auditoria ) ){
						Conclusao_auditoria conclusao =  (Conclusao_auditoria) this.getDao().getSession().createCriteria(Conclusao_auditoria.class).add( Restrictions.eq("cd_cronograma_auditoria", cronograma_auditoria.getCd_cronograma_auditoria()) ).uniqueResult();
						if(conclusao!=null){
							listaNomes.add(planos.getAuditoria().getNm_auditoria());
							listaScore.add(conclusao.getNr_percentual_atendimento());
							listaDatas.add(conclusao.getDt_conclusao_auditoria());
						}
						break;
					}
				}
			}
		}
		
		listaGeral.add(listaNomes);
		listaGeral.add(listaScore);
		listaGeral.add(listaDatas);
		
		return listaGeral;	
	}
	
	@SuppressWarnings("unchecked")
	public List<?> Auditoria_NCsPorSituacao(Integer cd_cronograma_auditoria){
		this.setDao(new GenericDAO());

		PesquisaPadraoBO pesquisa= new PesquisaPadraoBO();

		List<Cronograma_auditoria> listaCronograma = (List<Cronograma_auditoria>) this.getDao().getSession().createCriteria(Cronograma_auditoria.class).add( Restrictions.eq("cd_cronograma_auditoria", cd_cronograma_auditoria) ).list();
		Cronograma_auditoria cronograma_auditoria = listaCronograma.get(0);
		pesquisa.buscarObjetoCompleto(cronograma_auditoria);
		
		List<String> listaNomes = new ArrayList<>();
		List<Integer> listaNCs = new ArrayList<>();
		List<List<?>> listaGeral = new ArrayList<>();
		
		Integer contNCsAbertas=0;
		Integer contNCsExecucao=0;
		Integer contNCsFinalizadas=0;

		for(Planos_cronograma_auditoria planos : cronograma_auditoria.getListaPlanoAuditoria()){
			
			for(Grupo_auditoria grupo : planos.getAuditoria().getListaGruposAuditoria()){
				for(Pergunta_auditoria perg: grupo.getListaPerguntasAuditoria()){
					for(Resposta_exec_auditoria resp : planos.getExecucao_auditoria().getListaRespostas()){
						if( resp.getCd_pergunta_auditoria().equals( perg.getCd_pergunta_auditoria() ) ){
							if(resp.getResposta_auditoria().getTp_resposta_auditoria() == 0){
								if(resp.getPlano_acao_auditoria()!=null){
									if(resp.getPlano_acao_auditoria().getPlano_acao().getSt_plano_acao()==0){
										contNCsAbertas+=1;
									}else if(resp.getPlano_acao_auditoria().getPlano_acao().getSt_plano_acao()==1){
										contNCsExecucao+=1;
									}else{
										contNCsFinalizadas+=1;
									}
								}else{
									contNCsAbertas+=1;
								}
							}
						}
					}
					
				}
			}
		}
		
		
		listaNomes.add("Não conformidades abertas");
		listaNomes.add("Não conformidades em execução");
		listaNomes.add("Não conformidades finalizadas");
		
		listaNCs.add(contNCsAbertas);
		listaNCs.add(contNCsExecucao);
		listaNCs.add(contNCsFinalizadas);
		
		listaGeral.add( listaNomes);
		listaGeral.add( listaNCs);
		
		return listaGeral;	
	}
	
	
	
	// ******************************************
	// ***********  OUVIDORIA  ******************
	// ******************************************
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<?> Ouvidoria_chamados_por_periodo(Integer ano){
		this.setDao(new GenericDAO());

		List<String> listaNomesMeses = new ArrayList<>();
		List<Integer> listaQtde = new ArrayList<>();
		List<List<?>> listaGeral = new ArrayList<>();
		String meses = "Jan_Fev_Mar_Abr_Mai_Jun_Jul_Ago_Set_Out_Nov_Dez";
		for(String a : meses.split("_") ){
			listaNomesMeses.add(a);
			listaQtde.add(0);
		}
		
		List<Ocorrencia_ouvidoria> listaChamados = (List<Ocorrencia_ouvidoria>) this.getDao().getSession().createCriteria(Ocorrencia_ouvidoria.class).list();
		
		for(Ocorrencia_ouvidoria ocorrencia : listaChamados){
			if(ocorrencia.getDt_ocorrencia_ouvidoria()!=null){
				if( (ocorrencia.getDt_ocorrencia_ouvidoria().getYear()+1900) == ano ){
					Integer qtde = listaQtde.get( ocorrencia.getDt_ocorrencia_ouvidoria().getMonth() );
					
					System.out.println("Day: "+ ocorrencia.getDt_ocorrencia_ouvidoria().getDay() );
					System.out.println("Month: "+ ocorrencia.getDt_ocorrencia_ouvidoria().getMonth() );
					
					listaQtde.set(ocorrencia.getDt_ocorrencia_ouvidoria().getMonth(),qtde+1);
				}
			} 	
		}
		
		listaGeral.add( listaNomesMeses );
		listaGeral.add( listaQtde);
		
		return listaGeral;
		
	}
	
	public List<?> Ouvidoria_chamadosRegistradosXFinalizados(){
		this.setDao(new GenericDAO());
		
		List<String> listaNomes = new ArrayList<>();
		List<Integer> listaQtde = new ArrayList<>();
		List<List<?>> listaGeral = new ArrayList<>();
		
		listaNomes.add("Registrados");
		listaNomes.add("Finalizados");
		listaQtde.add( this.getDao().getSession().createCriteria(Ocorrencia_ouvidoria.class).list().size() );
		listaQtde.add( this.getDao().getSession().createCriteria(Ocorrencia_ouvidoria.class).add(Restrictions.eq("st_ocorrencia_ouvidoria", "2")).list().size() );
		
		listaGeral.add( listaNomes);
		listaGeral.add( listaQtde);
		
		return listaGeral;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> Ouvidoria_principais_reclamacoes(Date dataInicio, Date dataFim){
		this.setDao(new GenericDAO());
		
		List<String> listaNomesClassificacoes = new ArrayList<>();
		List<Integer> listaQtde = new ArrayList<>();
		List<List<?>> listaGeral = new ArrayList<>();
		
		List<Classificacao_ouvidoria> listaClassificacoes = (List<Classificacao_ouvidoria>) this.getDao().getSession().createCriteria(Classificacao_ouvidoria.class).list(); 
		
		for(Classificacao_ouvidoria classific : listaClassificacoes){
			
			
			listaNomesClassificacoes.add(classific.getDs_classificacao_ouvidoria());
			listaQtde.add( this.getDao().getSession().createCriteria(Ocorrencia_ouvidoria.class).add(Restrictions.eq("cd_classificacao_ouvidoria", classific.getCd_classificacao_ouvidoria())).add(Restrictions.between("dt_ocorrencia_ouvidoria", dataInicio, dataFim)).list().size() );
			
		}
		
		listaGeral.add( listaNomesClassificacoes);
		listaGeral.add( listaQtde);
		
		return listaGeral;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<?> Ouvidoria_reclamacoes_por_periodo(Integer cd_classificacao, Date dataInicio, Date dataFim){
		this.setDao(new GenericDAO());
		
		List<String> listaNomesClassificacoes = new ArrayList<>();
		List<Integer> listaQtde = new ArrayList<>();
		List<List<?>> listaGeral = new ArrayList<>();
		
		List<Classificacao_ouvidoria> listaClassificacoes = (List<Classificacao_ouvidoria>) this.getDao().getSession().createCriteria(Classificacao_ouvidoria.class).add(Restrictions.eq("cd_classificacao_ouvidoria", cd_classificacao)).list(); 
		
		for(Classificacao_ouvidoria classific : listaClassificacoes){
			
			
			listaNomesClassificacoes.add(classific.getDs_classificacao_ouvidoria());
			listaQtde.add( this.getDao().getSession().createCriteria(Ocorrencia_ouvidoria.class).add(Restrictions.eq("cd_classificacao_ouvidoria", classific.getCd_classificacao_ouvidoria())).add(Restrictions.between("dt_ocorrencia_ouvidoria", dataInicio, dataFim)).list().size() );
			
		}
		
		listaGeral.add( listaNomesClassificacoes);
		listaGeral.add( listaQtde);
		
		return listaGeral;
	}
	
	
	// ******************************************
	// ***********  MANUTENÇÃO  ******************
	// ******************************************
	
	
	// ******************************************
	// ***********  HELPDESK  *******************
	// ******************************************
	@SuppressWarnings("unchecked")
	public List<List<?>> chamadosAbertosPorAtendenteClassificacao(Integer cd_usuario, Date data_Inicial, Date data_Final, Unidade u  ){
		
		this.setDao(new GenericDAO());
		List<Integer> listaHDAbertos = new ArrayList<>();
		List<Helpdesk_ocorrencia> listaHDPorAtendente = new ArrayList<>();
		List<Classificacao_helpdesk> listaClassificacao_helpdesks = (List<Classificacao_helpdesk>) this.getDao().getSession().createCriteria(Classificacao_helpdesk.class)
				.addOrder(Order.asc("cd_classificacao_helpdesk")).list();
		
		List<String> listaNomeClassificacao = new ArrayList<>();
		List<Integer> listaCdClassificacao = new ArrayList<>();
		List<Integer> listaQtdeClassificacao = new ArrayList<>();
		for(Classificacao_helpdesk c : listaClassificacao_helpdesks){
			listaNomeClassificacao.add(c.getDs_classificacao_helpdesk());
			listaCdClassificacao.add(c.getCd_classificacao_helpdesk());
			listaQtdeClassificacao.add(0);
		}
		
		Criteria pesquisa = this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class);
		Criterion restricaoUnidade;
		Criterion restricaoSituacao = Restrictions.le("st_helpdesk_ocorrencia",3);
		Criterion restricaoDatas;

		if(u != null){
			restricaoUnidade = Restrictions.eq("cd_unidade_registro", u.getCd_unidade());
			pesquisa.add(restricaoUnidade);
		}
			
		if(data_Inicial!=null && data_Final !=null){
			restricaoDatas = Restrictions.between("dt_criacao",data_Inicial,data_Final);
			pesquisa.add(restricaoDatas);
		}
		
		if(cd_usuario!=null){
			pesquisa.add(Restrictions.eq("cd_resp_resolucao",cd_usuario));
		}else{
			pesquisa.add(Restrictions.isNull("cd_resp_resolucao"));
		}
		
		pesquisa.add(restricaoSituacao);
		pesquisa.addOrder(Order.asc("cd_resp_resolucao"));
		listaHDAbertos = (List<Integer>) pesquisa.setProjection( Projections.projectionList().add(Projections.groupProperty("cd_resp_resolucao"))).list();
		
		for(Integer i : listaHDAbertos){
			Criteria pesquisaAux = this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class);
			if(u != null){
				restricaoUnidade = Restrictions.eq("cd_unidade_registro", u.getCd_unidade());
				pesquisaAux.add(restricaoUnidade);
			}
				
			if(data_Inicial!=null && data_Final !=null){
				restricaoDatas = Restrictions.between("dt_criacao",data_Inicial,data_Final);
				pesquisaAux.add(restricaoDatas);
			}
			pesquisaAux.add(restricaoSituacao);
			if(i!=null){
				pesquisaAux.add(Restrictions.eq("cd_resp_resolucao", i));
			}else{
				pesquisaAux.add(Restrictions.isNull("cd_resp_resolucao"));
			}
			
			listaHDPorAtendente = new ArrayList<>();
			listaHDPorAtendente =(List<Helpdesk_ocorrencia>) pesquisaAux.list();
			for(Helpdesk_ocorrencia hd : listaHDPorAtendente){
				listaQtdeClassificacao.set(listaCdClassificacao.indexOf(hd.getCd_classificacao_helpdesk()), (listaQtdeClassificacao.get(listaCdClassificacao.indexOf(hd.getCd_classificacao_helpdesk()))+1));
			}
		}
		
		List<List<?>> listaTotal = new ArrayList<>();
		listaTotal.add(listaNomeClassificacao);
		listaTotal.add(listaQtdeClassificacao);
		listaTotal.add(listaCdClassificacao);
		
		return listaTotal;
	}
	@SuppressWarnings("unchecked")
	public List<List<?>> chamadosAbertosPorAtendente( Date data_Inicial, Date data_Final, Unidade u  ){

		this.setDao(new GenericDAO());
		List<Integer> listaHDAbertos = new ArrayList<>();
		List<Helpdesk_ocorrencia> listaHDPorAtendente = new ArrayList<>();
		
		List<String> listaNomes = new ArrayList<String>();
		List<Integer> listaQtde = new ArrayList<Integer>();
		List<Integer> listaCods = new ArrayList<Integer>();
		
		Criteria pesquisa = this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class);
		Criterion restricaoUnidade;
		Criterion restricaoSituacao = Restrictions.le("st_helpdesk_ocorrencia",3);
		Criterion restricaoDatas;
		
		
		
		if(u != null){
			restricaoUnidade = Restrictions.eq("cd_unidade_registro", u.getCd_unidade());
			pesquisa.add(restricaoUnidade);
		}
			
		if(data_Inicial!=null && data_Final !=null){
			restricaoDatas = Restrictions.between("dt_criacao",data_Inicial,data_Final);
			pesquisa.add(restricaoDatas);
		}
		
		pesquisa.add(restricaoSituacao);
		pesquisa.addOrder(Order.asc("cd_resp_resolucao"));
		listaHDAbertos = (List<Integer>) pesquisa.setProjection( Projections.projectionList().add(Projections.groupProperty("cd_resp_resolucao"))).list();
		
		for(Integer i : listaHDAbertos){
			Criteria pesquisaAux = this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class);
			if(u != null){
				restricaoUnidade = Restrictions.eq("cd_unidade_registro", u.getCd_unidade());
				pesquisaAux.add(restricaoUnidade);
			}
				
			if(data_Inicial!=null && data_Final !=null){
				restricaoDatas = Restrictions.between("dt_criacao",data_Inicial,data_Final);
				pesquisaAux.add(restricaoDatas);
			}
			pesquisaAux.add(restricaoSituacao);
			if(i!=null){
				pesquisaAux.add(Restrictions.eq("cd_resp_resolucao", i));
			}else{
				pesquisaAux.add(Restrictions.isNull("cd_resp_resolucao"));
			}
			
			listaHDPorAtendente = new ArrayList<>();
			listaHDPorAtendente =(List<Helpdesk_ocorrencia>) pesquisaAux.list();
			if(i!=null){
				String nomeCaboco = RelatorioBO.toText("usuario", "cd_usuario","nm_usuario", i.toString());
				
				if(nomeCaboco == ""){
					nomeCaboco = "Usuário não existente!";
				}
				
				listaNomes.add(nomeCaboco);
			}else{
				listaNomes.add("Não adotados");
			}
			
			listaCods.add(i);
			listaQtde.add( listaHDPorAtendente.size() );
			
		}
		
		List<List<?>> listaTotal = new ArrayList<>();
		listaTotal.add(listaNomes);
		listaTotal.add(listaQtde);
		listaTotal.add(listaCods);
		
		return listaTotal;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> chamadosTotalAnalisadosNaoAnalisados( Date data_Inicial, Date data_Final, Unidade u  ){
		this.setDao(new GenericDAO());
		
		List<Helpdesk_ocorrencia> listaHDAbertos = new ArrayList<>();
		List<Integer> listaQtdes = new ArrayList<Integer>();
		Integer qtdeAnalisados=0;
		Integer qtdeNaoAnalisados=0;
		
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
		dataInicial.setTime( data_Inicial );
		dataFinal.setTime( data_Final ); 

		dataInicial.set(Calendar.SECOND, 0);
		dataInicial.set(Calendar.MINUTE, 0);
		dataInicial.set(Calendar.HOUR_OF_DAY, 0);
		
		dataFinal.set(Calendar.SECOND, 59);
		dataFinal.set(Calendar.MINUTE, 59);
		dataFinal.set(Calendar.HOUR_OF_DAY, 23);
		
		if( u != null ){
			
			listaHDAbertos = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.between("dt_criacao",dataInicial.getTime(),dataFinal.getTime()))
					.list();
		}else{
			listaHDAbertos = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.eq("cd_unidade_registro",u.getCd_unidade()))
					.add(Restrictions.between("dt_criacao",dataInicial.getTime(),dataFinal.getTime()))
					.list();
		}
		
		for(Helpdesk_ocorrencia h : listaHDAbertos ){
			if(h.getSt_helpdesk_ocorrencia() > 1){
				qtdeAnalisados += 1;
			}else{
				qtdeNaoAnalisados += 1;
			}
		}
		
		if(listaHDAbertos!=null){
			listaQtdes.add( listaHDAbertos.size() );
			listaQtdes.add( qtdeAnalisados );
			listaQtdes.add( qtdeNaoAnalisados );
		}
		
		
		return listaQtdes;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> chamadosPorStatusFiltrado( Date data_Inicial, Date data_Final, Unidade u  ){
		
		this.setDao(new GenericDAO());
		List<Helpdesk_ocorrencia> listaHDAbertos = new ArrayList<>();
		List<Helpdesk_ocorrencia> listaHDFinalizados = new ArrayList<>();
		
		if(u != null){
			listaHDAbertos = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.le("st_helpdesk_ocorrencia",3))
					.add(Restrictions.between("dt_criacao",data_Inicial,data_Final))
					.add(Restrictions.eq("cd_unidade_registro", u.getCd_unidade()))
					.list();
			
			listaHDFinalizados = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.ge("st_helpdesk_ocorrencia",4))
					.add(Restrictions.between("dt_criacao",data_Inicial,data_Final))
					.add(Restrictions.eq("cd_unidade_registro", u.getCd_unidade()))
					.list();
		}else{
			listaHDAbertos = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.le("st_helpdesk_ocorrencia",3))
					.add(Restrictions.between("dt_criacao",data_Inicial,data_Final))
					.list();
			
			listaHDFinalizados = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.ge("st_helpdesk_ocorrencia",4))
					.add(Restrictions.between("dt_criacao",data_Inicial,data_Final))
					.list();
		}
		
		
		List<String> listaStr = new ArrayList<String>();
		listaStr.add("Abertos");
		listaStr.add("Fechados");
		
		List<Integer> listaQtde = new ArrayList<Integer>();
		listaQtde.add(listaHDAbertos.size());
		listaQtde.add(listaHDFinalizados.size());
		
		List<List<?>> listaTotal = new ArrayList<>();
		listaTotal.add(listaStr);
		listaTotal.add(listaQtde);
		
		return listaTotal;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<List<?>> chamadosPorStatus(){
		this.setDao(new GenericDAO());
		List<Helpdesk_ocorrencia> listaHDAbertos = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.le("st_helpdesk_ocorrencia",3)).list();
		
		List<Helpdesk_ocorrencia> listaHDFinalizados = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.ge("st_helpdesk_ocorrencia",4)).list();
		
		
		List<String> listaStr = new ArrayList<String>();
		listaStr.add("Abertos");
		listaStr.add("Fechados");
		
		List<Integer> listaQtde = new ArrayList<Integer>();
		listaQtde.add(listaHDAbertos.size());
		listaQtde.add(listaHDFinalizados.size());
		
		List<List<?>> listaTotal = new ArrayList<>();
		listaTotal.add(listaStr);
		listaTotal.add(listaQtde);
		
		return listaTotal;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<List<?>> chamadosPorStatusEPeriodoGeral(){
		this.setDao(new GenericDAO());
		List<Helpdesk_ocorrencia> listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class).list();
		
		List<Integer> listaStr = new ArrayList<Integer>();
		listaStr.add(0);listaStr.add(1);listaStr.add(2);listaStr.add(3);listaStr.add(4);listaStr.add(5);listaStr.add(6);
		
		List<Integer> listaQtdes = new ArrayList<Integer>();
		listaQtdes.add(0);listaQtdes.add(0);listaQtdes.add(0);listaQtdes.add(0);listaQtdes.add(0);listaQtdes.add(0);listaQtdes.add(0);
		
		if( listaHDs != null ){
			for(Helpdesk_ocorrencia hd:listaHDs){
				
				Integer index = listaStr.indexOf( hd.getSt_helpdesk_ocorrencia() );
				listaQtdes.set(index, listaQtdes.get(index) + 1);
				
			}
		}
		
		List<String> listaStrFinal = new ArrayList<String>();
		listaStrFinal.add("Aberto");
		listaStrFinal.add("Visualizado");
		listaStrFinal.add("Ações Iniciadas");
		listaStrFinal.add("Ações Finalizadas");
		listaStrFinal.add("Finalizado");
		listaStrFinal.add("Op. de Melhoria");
		listaStrFinal.add("N.C");
		
		List<List<?>> listaTotal = new ArrayList<>();
		listaTotal.add(listaStrFinal);
		listaTotal.add(listaQtdes);
		
		return listaTotal;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> chamadosPorStatusEPeriodo(Date data_Inicial, Date data_Final, Unidade u){
		this.setDao(new GenericDAO());
		GregorianCalendar dataInicial = new GregorianCalendar();
		GregorianCalendar dataFinal = new GregorianCalendar();
		dataInicial.setTime(data_Inicial);
		dataFinal.setTime(data_Final);
		
		dataInicial.set(Calendar.HOUR_OF_DAY,0);     
		dataInicial.set(Calendar.MINUTE,0);     
		dataInicial.set(Calendar.SECOND,0);
		
		dataFinal.set(Calendar.HOUR_OF_DAY,0);     
		dataFinal.set(Calendar.MINUTE,0);     
		dataFinal.set(Calendar.SECOND,0);
		
		System.out.println( new SimpleDateFormat("dd/MM/yyy HH:mm").format( dataInicial.getTime() ));
		System.out.println( new SimpleDateFormat("dd/MM/yyy HH:mm").format(   dataFinal.getTime() ));
		
		List<Helpdesk_ocorrencia> listaHDs = new ArrayList<>();
		
		if( u != null){
			listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.eq("cd_unidade", u.getCd_unidade()))
					.add(Restrictions.between("dt_criacao", dataInicial.getTime(), dataFinal.getTime()))
					.list();
		}else{
			listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.between("dt_criacao", dataInicial.getTime(), dataFinal.getTime())).list();
		}
		
		List<Integer> listaStr = new ArrayList<Integer>();
		listaStr.add(0);listaStr.add(1);listaStr.add(2);listaStr.add(3);listaStr.add(4);listaStr.add(5);listaStr.add(6);
		
		List<Integer> listaQtdes = new ArrayList<Integer>();
		listaQtdes.add(0);listaQtdes.add(0);listaQtdes.add(0);listaQtdes.add(0);listaQtdes.add(0);listaQtdes.add(0);listaQtdes.add(0);
		
		if( listaHDs != null ){
			for(Helpdesk_ocorrencia hd:listaHDs){
				
				Integer index = listaStr.indexOf( hd.getSt_helpdesk_ocorrencia() );
				listaQtdes.set(index, listaQtdes.get(index) + 1);
				
			}
		}
		
		List<String> listaStrFinal = new ArrayList<String>();
		listaStrFinal.add("Aberto");
		listaStrFinal.add("Visualizado");
		listaStrFinal.add("Ações Iniciadas");
		listaStrFinal.add("Ações Finalizadas");
		listaStrFinal.add("Finalizado");
		listaStrFinal.add("Op. de Melhoria");
		listaStrFinal.add("N.C");
		
		List<List<?>> listaTotal = new ArrayList<>();
		listaTotal.add(listaStrFinal);
		listaTotal.add(listaQtdes);
		
		return listaTotal;
	
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> chamadosPorMotivoAberturaClassificacao(String ds_classificacao_helpdesk, Date data_Inicial, Date data_Final, Unidade u){
		this.setDao(new GenericDAO());
		Classificacao_helpdesk classificacao = ( Classificacao_helpdesk ) this.getDao().getSession().createCriteria(Classificacao_helpdesk.class)
				.add(Restrictions.eq("ds_classificacao_helpdesk", ds_classificacao_helpdesk)).uniqueResult();
		
		List<Natureza_ocorrencia> listaNatureza = (List<Natureza_ocorrencia>) this.getDao().getSession().createCriteria(Natureza_ocorrencia.class)
				.add(Restrictions.eq("cd_classificacao_helpdesk", classificacao.getCd_classificacao_helpdesk())).list();
		
		
		List<String> listaStrFinal = new ArrayList<String>();
		List<Integer> listaQtdes = new ArrayList<Integer>();
		
		for(Natureza_ocorrencia natureza: listaNatureza){
			List<Helpdesk_ocorrencia> listaHDs = new ArrayList<>();
			
			if( u != null){
			
				listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
						.add(Restrictions.eq("cd_natureza_ocorrencia", natureza.getCd_natureza_ocorrencia()))
						.add(Restrictions.eq("cd_unidade", u.getCd_unidade()))
						.add(Restrictions.between("dt_criacao", data_Inicial , data_Final ))
						.list();
			}else{
				listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
						.add(Restrictions.eq("cd_natureza_ocorrencia", natureza.getCd_natureza_ocorrencia()))
						.add(Restrictions.between("dt_criacao", data_Inicial , data_Final))
						.list();
			}
			
			
			listaStrFinal.add(natureza.getDs_natureza_ocorrencia());
			if(listaHDs != null){
				listaQtdes.add(listaHDs.size());	
			}else{
				listaQtdes.add(0);
			}
		}
		
		List<List<?>> listaTotal = new ArrayList<>();
		listaTotal.add(listaStrFinal);
		listaTotal.add(listaQtdes);
		
		return listaTotal;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> chamadosPorMotivoAberturaClassificacao(String ds_classificacao_helpdesk){
		this.setDao(new GenericDAO());
		Classificacao_helpdesk classificacao = ( Classificacao_helpdesk ) this.getDao().getSession().createCriteria(Classificacao_helpdesk.class)
				.add(Restrictions.eq("ds_classificacao_helpdesk", ds_classificacao_helpdesk)).uniqueResult();
		
		List<Natureza_ocorrencia> listaNatureza = (List<Natureza_ocorrencia>) this.getDao().getSession().createCriteria(Natureza_ocorrencia.class)
				.add(Restrictions.eq("cd_classificacao_helpdesk", classificacao.getCd_classificacao_helpdesk())).list();
		
		
		List<String> listaStrFinal = new ArrayList<String>();
		List<Integer> listaQtdes = new ArrayList<Integer>();
		
		for(Natureza_ocorrencia natureza: listaNatureza){
			List<Helpdesk_ocorrencia> listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.eq("cd_natureza_ocorrencia", natureza.getCd_natureza_ocorrencia())).list();
			
			listaStrFinal.add(natureza.getDs_natureza_ocorrencia());
			if(listaHDs != null){
				listaQtdes.add(listaHDs.size());	
			}else{
				listaQtdes.add(0);
			}
		}
		
		List<List<?>> listaTotal = new ArrayList<>();
		listaTotal.add(listaStrFinal);
		listaTotal.add(listaQtdes);
		
		return listaTotal;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<List<?>> chamadosPorMotivoAberturaFiltrado(Date data_Inicial, Date data_Final, Unidade u){
		
		this.setDao(new GenericDAO());
		
		List<Classificacao_helpdesk> listaClassificacao_helpdesks = (List<Classificacao_helpdesk>) this.getDao().getSession().createCriteria(Classificacao_helpdesk.class)
				.addOrder(Order.asc("cd_classificacao_helpdesk")).list();
		
		List<Helpdesk_ocorrencia> listaHDs = new ArrayList<>();
		
		if(u!=null){
			listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.eq("cd_unidade_registro", u.getCd_unidade()))
					.add(Restrictions.between("dt_criacao", data_Inicial , data_Final ))
					.list();
		}else{
			listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.between("dt_criacao", data_Inicial , data_Final ))
					.list();	
		}
		
		List<Integer> listaStr = new ArrayList<Integer>();
		List<String> listaStrFinal = new ArrayList<String>();
		List<Integer> listaQtdes = new ArrayList<Integer>();
		
		for(Classificacao_helpdesk c: listaClassificacao_helpdesks){
			listaStr.add(c.getCd_classificacao_helpdesk());
			listaQtdes.add(0);
		}
		
		for(Helpdesk_ocorrencia hd:listaHDs){
			Integer index = listaStr.indexOf( hd.getCd_classificacao_helpdesk() );
			listaQtdes.set(index, listaQtdes.get(index) + 1);
		}
		
		for(Classificacao_helpdesk c: listaClassificacao_helpdesks){
			listaStrFinal.add(c.getDs_classificacao_helpdesk());
			
		}
		
		List<List<?>> listaTotal = new ArrayList<>();
		listaTotal.add(listaStrFinal);
		listaTotal.add(listaQtdes);
		
		return listaTotal;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> chamadosPorMotivoAbertura(){
		this.setDao(new GenericDAO());
		List<Classificacao_helpdesk> listaClassificacao_helpdesks = (List<Classificacao_helpdesk>) this.getDao().getSession().createCriteria(Classificacao_helpdesk.class)
				.addOrder(Order.asc("cd_classificacao_helpdesk")).list();
		List<Helpdesk_ocorrencia> listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class).list();
		
		List<Integer> listaStr = new ArrayList<Integer>();
		List<String> listaStrFinal = new ArrayList<String>();
		List<Integer> listaQtdes = new ArrayList<Integer>();
		
		for(Classificacao_helpdesk c: listaClassificacao_helpdesks){
			listaStr.add(c.getCd_classificacao_helpdesk());
			listaQtdes.add(0);
		}
		
		for(Helpdesk_ocorrencia hd:listaHDs){
			Integer index = listaStr.indexOf( hd.getCd_classificacao_helpdesk() );
			listaQtdes.set(index, listaQtdes.get(index) + 1);
		}
		
		for(Classificacao_helpdesk c: listaClassificacao_helpdesks){
			listaStrFinal.add(c.getDs_classificacao_helpdesk());
			
		}
		
		List<List<?>> listaTotal = new ArrayList<>();
		listaTotal.add(listaStrFinal);
		listaTotal.add(listaQtdes);
		
		return listaTotal;
	
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> chamadosPorSetorDaUnidade(String ds_unidade, Date data_Inicial, Date data_Final){
		this.setDao(new GenericDAO());
		
		
		/****/
//		try {
//			CrudGenericBO crud = new CrudGenericBO();
//			List<Helpdesk_ocorrencia> listaHD = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class).list();
//			for(Helpdesk_ocorrencia hd : listaHD){
//				List<Setor> listaSetores = (List<Setor>) this.getDao().getSession().createCriteria(Setor.class)
//						.add(Restrictions.eq("cd_unidade", hd.getCd_unidade_registro())).list();
//				
//				if(listaSetores.size() > 0){
//				
//					Double d = new Double(Math.floor(Math.random()*listaSetores.size() ));
//					hd.setCd_setor( listaSetores.get(d.intValue()).getCd_setor() );
//				
//					crud.atualizar(hd);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		/***/
		
		
		GregorianCalendar dataInicial = new GregorianCalendar();
		GregorianCalendar dataFinal = new GregorianCalendar();
		dataInicial.setTime(data_Inicial);
		dataFinal.setTime(data_Final);
		
		dataInicial.set(Calendar.HOUR_OF_DAY,0);     
		dataInicial.set(Calendar.MINUTE,0);     
		dataInicial.set(Calendar.SECOND,0);
		
		dataFinal.set(Calendar.HOUR_OF_DAY,0);     
		dataFinal.set(Calendar.MINUTE,0);     
		dataFinal.set(Calendar.SECOND,0);
		
		List<String> listaStrFinal = new ArrayList<String>();
		List<Integer> listaQtdes = new ArrayList<Integer>();
		
		Unidade u = (Unidade) this.getDao().getSession().createCriteria(Unidade.class).add(Restrictions.eq("ds_unidade", ds_unidade)).uniqueResult();
		List<Setor> listaSetores = (List<Setor>) this.getDao().getSession().createCriteria(Setor.class)
				.add(Restrictions.eq("cd_unidade", u.getCd_unidade())).list();
		
		for(Setor s : listaSetores){

			List<Helpdesk_ocorrencia> listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.eq("cd_setor", s.getCd_setor()))
					.add(Restrictions.between("dt_criacao", dataInicial.getTime(), dataFinal.getTime()))
					.list();
			
			
			
			listaStrFinal.add(s.getNm_setor());
			if(listaHDs != null){
				listaQtdes.add(listaHDs.size());	
			}else{
				listaQtdes.add(0);
			}
			
		}
		
		List<List<?>> listaTotal = new ArrayList<>();
		listaTotal.add(listaStrFinal);
		listaTotal.add(listaQtdes);
		
		return listaTotal;
	
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> chamadosPorUnidadePrazo(Date data_Inicial, Date data_Final){
		this.setDao(new GenericDAO());
		GregorianCalendar dataInicial = new GregorianCalendar();
		GregorianCalendar dataFinal = new GregorianCalendar();
		dataInicial.setTime(data_Inicial);
		dataFinal.setTime(data_Final);
		
		dataInicial.set(Calendar.HOUR_OF_DAY,0);     
		dataInicial.set(Calendar.MINUTE,0);     
		dataInicial.set(Calendar.SECOND,0);
		
		dataFinal.set(Calendar.HOUR_OF_DAY,0);     
		dataFinal.set(Calendar.MINUTE,0);     
		dataFinal.set(Calendar.SECOND,0);
		
		
		List<String> listaStrFinal = new ArrayList<String>();
		List<Integer> listaQtdes = new ArrayList<Integer>();
		
		
		System.out.println( new SimpleDateFormat("dd/MM/yyy HH:mm").format( dataInicial.getTime() ));
		System.out.println( new SimpleDateFormat("dd/MM/yyy HH:mm").format(   dataFinal.getTime() ));
		
		List<Unidade> listaUnidades = (List<Unidade>) this.getDao().getSession().createCriteria(Unidade.class).list();
		
		
		
		for(Unidade u : listaUnidades){

			List<Helpdesk_ocorrencia> listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
					.add(Restrictions.eq("cd_unidade_registro", u.getCd_unidade()))
					.add(Restrictions.between("dt_criacao", dataInicial.getTime(), dataFinal.getTime()))
					.list();
			
			if( listaHDs != null ){
				listaStrFinal.add(u.getDs_unidade());
				listaQtdes.add( listaHDs.size() );
			}
			
		}
		
		List<List<?>> listaTotal = new ArrayList<>();
		listaTotal.add(listaStrFinal);
		listaTotal.add(listaQtdes);
		
		return listaTotal;
	
		
	}
	// ******************************************
	// ***********  ACTION  *********************
	// ******************************************
	@SuppressWarnings("unchecked")
	public List<Escalonamento_formulario> getEscalonamentosPeriodo(GregorianCalendar dataAtual, Integer cd_unidade){
		List<Escalonamento_formulario> listaEscForm = (List<Escalonamento_formulario>) this.getDao().getSession().createCriteria(Escalonamento_formulario.class)
				.add(Restrictions.eq("cd_unidade",cd_unidade))
				.add(Restrictions.le("dt_inicio_vigencia",dataAtual.getTime()))
				.add(Restrictions.ge("dt_termino_vigencia",dataAtual.getTime()))
				.list();
		return listaEscForm;
	}
	
	@SuppressWarnings("unchecked")
	public List<Escalonamento_usuario> getListaEscalonamentoUsuario(Integer cd_usuario, Escalonamento_formulario esc, Integer diaSemana){
		List<Escalonamento_usuario> listaEscalonamentosUsuario = (List<Escalonamento_usuario>) this.getDao().getSession().createCriteria(Escalonamento_usuario.class)
				.add(Restrictions.eq("cd_usuario", cd_usuario))
				.add(Restrictions.eq("cd_escalonamento_formulario", esc.getCd_escalonamento_formulario()))
				.add(Restrictions.isNotNull("hr_execucao"))
				.add(Restrictions.like("ds_dias_repeticao", "%" + diaSemana.toString() + "%"))
				.list();
		return listaEscalonamentosUsuario;
	}
	
	@SuppressWarnings("unchecked")
	public Execucao_formulario getExecucao(Escalonamento_formulario esc, Integer cd_usuario, Escalonamento_usuario escUsu){
		
		Execucao_formulario exe = (Execucao_formulario) this.getDao().getSession().createCriteria(Execucao_formulario.class)
		.add(Restrictions.eq("cd_formulario", esc.getCd_formulario()))
		.add(Restrictions.eq("cd_usuario", cd_usuario))
		.add(Restrictions.eq("cd_escalonamento_usuario", escUsu.getCd_escalonamento_usuario()))
		.add(Restrictions.ge("dt_execucao_formulario", dataInicialDia.getTime()))
		.add(Restrictions.le("nr_hora_termino", dataFinalDia.getTime())).uniqueResult();
		
		System.out.println("dataInicialDia: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicialDia.getTime()) );
		System.out.println("dataFinalDia  : " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinalDia.getTime()) );
		
		return exe;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<List<?>> atividadesExecutadasTodos( Integer cd_unidade,  Date dt_inicio, Date dt_fim){
		this.setDao(new GenericDAO());
		List<Usuario> listaUsuarios = this.getDao().getSession().createCriteria(Usuario.class).list();
		List<List<?>> listaListas = new ArrayList<>();
		for(Usuario u: listaUsuarios){
			listaListas.add( atividadesExecutadas(u.getCd_usuario(), cd_unidade, dt_inicio, dt_fim) );
		}
		return listaListas;
	}
	
	
	public List<Resposta_exec_formulario> indicadoresTemperatura( List<List<?>> listaFormularios, Date dt_inicio, Date dt_fim, String st_tipo_turno){
		return indicadoresTemperatura(listaFormularios, dt_inicio, dt_fim, 0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Resposta_exec_formulario> indicadoresTemperatura( List<List<?>> listaFormularios, Date dt_inicio, Date dt_fim, Integer st_tipo_turno){
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();

		
		GregorianCalendar gDataInicial = (GregorianCalendar) GregorianCalendar.getInstance();
		gDataInicial.setTime(dt_inicio);
		GregorianCalendar gDataFinal = (GregorianCalendar) GregorianCalendar.getInstance();
		gDataFinal.setTime(dt_fim);
		
		
		gDataInicial.set(Calendar.HOUR_OF_DAY,0);
		gDataInicial.set(Calendar.MINUTE,0);
		gDataInicial.set(Calendar.SECOND,0);
		
		gDataFinal.set(Calendar.HOUR_OF_DAY,0);
		gDataFinal.set(Calendar.MINUTE,0);
		gDataFinal.set(Calendar.SECOND,0);
		
		gDataFinal.set(Calendar.DATE, gDataFinal.get( Calendar.DATE ) + 1 );
		
		
		System.out.println("Data Inicial: " + new SimpleDateFormat("dd/MM/yyy HH:mm").format(gDataInicial.getTime()));
		System.out.println("Data Final: " + new SimpleDateFormat("dd/MM/yyy HH:mm").format(gDataFinal.getTime()));
		
		
		List<Resposta_exec_formulario> listaRespostas = new ArrayList<>();

		if(listaFormularios.size() > 0){

			//List<Campo_agrupador> listaCamposAgrupados = new ArrayList<>(); 

			String strCondicoes = "";
			for(List<?> lista : listaFormularios){

				Formulario formulario  = (Formulario) lista.get(0);
				Campo_formulario campo = (Campo_formulario) lista.get(1);
				
//				for( Campo_agrupador c : ( List<Campo_agrupador> ) lista.get(2) ){
//					listaCamposAgrupados.add( c );
//				}

				strCondicoes += "(formulario.cd_formulario = "+ formulario.getCd_formulario() +" AND resposta_exec_formulario.cd_campo_formulario = "+ campo.getCd_campo_formulario() + ")";
				strCondicoes += " OR ";
			}

			strCondicoes = strCondicoes.substring(0, strCondicoes.length() - 4);
			/**
			 * VERIFICAR SEMPRE OS RESULTADOS COM ATENÇÃO
			 */
			String strSQL = "SELECT resposta_exec_formulario.* FROM resposta_exec_formulario";  
			strSQL += " INNER JOIN execucao_formulario on execucao_formulario.cd_execucao_formulario = resposta_exec_formulario.cd_execucao_formulario AND execucao_formulario.cd_tipo_turno ";  
			strSQL += (st_tipo_turno==null)?"IS NULL":((st_tipo_turno==0)?"IS NULL OR execucao_formulario.cd_tipo_turno IS NOT NULL":" = " + st_tipo_turno);
			strSQL += " INNER JOIN formulario on formulario.cd_formulario = execucao_formulario.cd_formulario ";
			strSQL += " INNER JOIN campo_formulario on campo_formulario.cd_campo_formulario = resposta_exec_formulario.cd_campo_formulario";
			strSQL += " INNER JOIN grupo_formulario on grupo_formulario.cd_grupo_formulario = campo_formulario.cd_grupo_formulario ";
			strSQL += "WHERE (" + strCondicoes + ") AND execucao_formulario.dt_execucao_formulario between DATE '"+ new SimpleDateFormat("yyyy-MM-dd").format( gDataInicial.getTime() ) +"' and DATE '"+ new SimpleDateFormat("yyyy-MM-dd").format( gDataFinal.getTime() ) +"' ORDER BY execucao_formulario.dt_execucao_formulario;"; 

			List<Resposta_exec_formulario> listaRespostasAux = ( List<Resposta_exec_formulario> ) this.getDao().getSession().createSQLQuery(strSQL).setResultTransformer(Transformers.aliasToBean(Resposta_exec_formulario.class)).list();
			for(Resposta_exec_formulario resp: listaRespostasAux){
				pesquisa.buscarObjetoCompleto( resp );
				Execucao_formulario exe = (Execucao_formulario) this.getDao().getSession().createCriteria(Execucao_formulario.class).add(Restrictions.eq("cd_execucao_formulario", resp.getCd_execucao_formulario())).uniqueResult();
				pesquisa.buscarObjetoCompleto( exe );
				resp.setExecucao_formulario(exe);
				resp.setDt_execucao_formulario( exe.getDt_execucao_formulario() );
	
//				for(Campo_agrupador campoAgrup : listaCamposAgrupados){
//					if( resp.getCd_campo_formulario().equals( campoAgrup.getCd_campo_formulario() )){
//						Campo_indicador campo_indicador = (Campo_indicador) this.getDao().getSession().createCriteria(Campo_indicador.class).add(Restrictions.eq("cd_campo_indicador", campoAgrup.getCd_campo_indicador())).uniqueResult();
//						resp.setCd_campo_formulario( campo_indicador.getCd_campo_formulario() );
//						resp.getExecucao_formulario().setCd_formulario( campo_indicador.getCd_formulario() );
//						break;
//					}
//				}
				
			}
			listaRespostas.addAll(listaRespostasAux);
			return listaRespostas;
		}else{
			return null;
			//new ArrayList<Resposta_exec_formulario>();	
		}
	}
	
	
	private void setaMes(GregorianCalendar data, Integer mes){
		if(mes==0){
			data.set(Calendar.MONTH, Calendar.JANUARY);
		}else if(mes==1){
			data.set(Calendar.MONTH, Calendar.FEBRUARY);
		}else if(mes==2){
			data.set(Calendar.MONTH, Calendar.MARCH);
		}else if(mes==3){
			data.set(Calendar.MONTH, Calendar.APRIL);
		}else if(mes==4){
			data.set(Calendar.MONTH, Calendar.MAY);
		}else if(mes==5){
			data.set(Calendar.MONTH, Calendar.JUNE);
		}else if(mes==6){
			data.set(Calendar.MONTH, Calendar.JULY);
		}else if(mes==7){
			data.set(Calendar.MONTH, Calendar.AUGUST);
		}else if(mes==8){
			data.set(Calendar.MONTH, Calendar.SEPTEMBER);
		}else if(mes==9){
			data.set(Calendar.MONTH, Calendar.OCTOBER);
		}else if(mes==10){
			data.set(Calendar.MONTH, Calendar.NOVEMBER);
		}else if(mes==11){
			data.set(Calendar.MONTH, Calendar.DECEMBER);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> atividadesExecutadas(Integer cd_usuario, Integer cd_unidade, Date dt_inicio, Date dt_fim){
		
		Integer qtdeAtividades=0;
		Integer qtdeAtividadesExecutadas=0;

		dataAtual = getDataAtual();
		setaMes(dataAtual, dt_inicio.getMonth());
		dataAtual.set(Calendar.DATE , dt_inicio.getDate()  );
		//dataAtual.set(Calendar.MONTH, dt_inicio.getMonth() );
		dataFim = getDataFim();
		setaMes(dataFim, dt_fim.getMonth());
		dataFim.set(Calendar.DATE, dt_fim.getDate() );
		//dataFim.set(Calendar.MONTH, dt_fim.getMonth() );
		dataInicialDia = getDataInicialDia();
		dataFinalDia   = getDataFinalDia();
		
		
		this.setDao(new GenericDAO());
		
		if(dt_inicio!=null && dt_fim!=null  ){
			
			Boolean terminouPeriodo=false;
			while(!terminouPeriodo){
				
				Integer diaSemana = dataAtual.get(GregorianCalendar.DAY_OF_WEEK);
				System.out.println("DIA ATUAL: "+  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataAtual.getTime()) );
				
				
				List<Escalonamento_formulario> listaEscForm = getEscalonamentosPeriodo(dataAtual, cd_unidade);
				
				if(listaEscForm!=null){
					for(Escalonamento_formulario esc:listaEscForm){
						List<Escalonamento_usuario> listaEscalonamentosUsuario2 = getListaEscalonamentoUsuario(cd_usuario, esc, diaSemana);
						
						if(listaEscalonamentosUsuario2!=null){
							qtdeAtividades = qtdeAtividades + listaEscalonamentosUsuario2.size();
						
							Execucao_formulario execucao = null;
							if(listaEscalonamentosUsuario2.size()>0){
								for(Escalonamento_usuario escUsu:listaEscalonamentosUsuario2){
									try {
										
										dataInicialDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
										dataFinalDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
										dataInicialDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
										dataFinalDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
										
										execucao = getExecucao(esc, cd_usuario, escUsu);
										
									} catch (Exception e) {
										//LogPrimum.mensagem("2 OU MAIS EXECUÇÕES PARA UMA TAREFA NA MESMA DATA. CHECAR ESCALONAMENTO_BO");
									}
	
									if(execucao!=null && execucao.getCd_execucao_formulario()!=null){
										qtdeAtividadesExecutadas+=1;
									}
								}
							}
						}
					}
				}

				if(dataAtual.get(Calendar.DATE) == dataFim.get(Calendar.DATE) &&
						dataAtual.get(Calendar.MONTH) == dataFim.get(Calendar.MONTH ) &&
						dataAtual.get(Calendar.YEAR) == dataFim.get(Calendar.YEAR) ){
					terminouPeriodo=true;
					break;
				}
				dataAtual.set(Calendar.DATE, dataAtual.get(Calendar.DATE) +1 );
			}
		}
		
		System.out.println("Qtde de atividades designadas para esse periodo: "+ qtdeAtividades);
		System.out.println("Qtde de atividades executadas nesse periodo: "+ qtdeAtividadesExecutadas);
	
		List<Integer> listaGeral = new ArrayList<>();
		listaGeral.add(qtdeAtividades);
		listaGeral.add(qtdeAtividadesExecutadas);
		
		return listaGeral;
	}

	
	@SuppressWarnings("unchecked")
	public List<List<?>> tempoMedioExecucaoTodos(Integer cd_unidade, Date dt_inicio, Date dt_fim){
		this.setDao(new GenericDAO());
		List<Usuario> listaUsuarios = this.getDao().getSession().createCriteria(Usuario.class).list();
		List<List<?>> listaListas = new ArrayList<>();
		for(Usuario u: listaUsuarios){
			listaListas.add( tempoMedioExecucao(u.getCd_usuario(), cd_unidade, dt_inicio, dt_fim) );
		}
		return listaListas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Float> tempoMedioExecucao(Integer cd_usuario, Integer cd_unidade, Date dt_inicio, Date dt_fim){
		
		Float tempoMedioAtividades= new Float(0);
		Float tempoMedioAtividadesExecutadas=new Float(0);
		Float qtdeAtividades=new Float(0);
		Float qtdeAtividadesExecutadas=new Float(0);
		
		dataAtual = getDataAtual();
		dataAtual.set(Calendar.MONTH, dt_inicio.getMonth() );
		dataAtual.set(Calendar.DATE , dt_inicio.getDate()  );
		
		dataFim = getDataFim();
		dataFim.set(Calendar.MONTH, dt_fim.getMonth() );
		dataFim.set(Calendar.DATE, dt_fim.getDate() );
		
		dataInicialDia = getDataInicialDia();
		dataFinalDia   = getDataFinalDia();
		
		this.setDao(new GenericDAO());
		if(dt_inicio!=null && dt_fim!=null  ){
			
			Boolean terminouPeriodo=false;
			while(!terminouPeriodo){
				
				Integer diaSemana = dataAtual.get(GregorianCalendar.DAY_OF_WEEK);
				System.out.println("DIA ATUAL: "+  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataAtual.getTime()) );
				
				List<Escalonamento_formulario> listaEscForm = getEscalonamentosPeriodo(dataAtual, cd_unidade);
				
				if(listaEscForm!=null){
					for(Escalonamento_formulario esc:listaEscForm){
						List<Escalonamento_usuario> listaEscalonamentosUsuario2 = getListaEscalonamentoUsuario(cd_usuario, esc, diaSemana);
						if(listaEscalonamentosUsuario2!=null){
							qtdeAtividades = qtdeAtividades + listaEscalonamentosUsuario2.size();
							
							Execucao_formulario execucao = null;
							if(listaEscalonamentosUsuario2.size()>0){
								for(Escalonamento_usuario escUsu:listaEscalonamentosUsuario2){
									
									Formulario form = (Formulario) this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", esc.getCd_formulario())).uniqueResult();
									tempoMedioAtividades = tempoMedioAtividades + form.getNr_tempoestimado(); 
									
									try {

										dataInicialDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
										dataFinalDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
										dataInicialDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
										dataFinalDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
										
										
										System.out.println("DIA COMPARACAO: "+  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicialDia.getTime()) );
										execucao = getExecucao(esc, cd_usuario, escUsu);
									} catch (Exception e) {
										//LogPrimum.mensagem("2 OU MAIS EXECUÇÕES PARA UMA TAREFA NA MESMA DATA. CHECAR ESCALONAMENTO_BO");
									}
	
									if(execucao!=null && execucao.getCd_execucao_formulario()!=null){
										
										qtdeAtividadesExecutadas+=1;
										Long tempoTotaLong;
										Integer tempoTotal;
										tempoTotaLong = execucao.getNr_hora_termino().getTime() - execucao.getDt_execucao_formulario().getTime();
										tempoTotal = tempoTotaLong.intValue();
										if(tempoTotal!=0){
											tempoTotal = tempoTotal/(60*1000);
										}else{
											tempoTotal=0;
										}
										tempoMedioAtividadesExecutadas = tempoMedioAtividadesExecutadas + tempoTotal;
										
									}
								}
							}
						}
					}
				}

				if(dataAtual.get(Calendar.DATE) == dataFim.get(Calendar.DATE) &&
						dataAtual.get(Calendar.MONTH) == dataFim.get(Calendar.MONTH ) &&
						dataAtual.get(Calendar.YEAR) == dataFim.get(Calendar.YEAR) ){
					terminouPeriodo=true;
					break;
				}
				dataAtual.set(Calendar.DATE, dataAtual.get(Calendar.DATE) +1 );
			}
		}
		
		if(tempoMedioAtividades!=0 && qtdeAtividades!=0){
			System.out.println("Qtde de tempo das atividades designadas para esse periodo: "+ tempoMedioAtividades/qtdeAtividades);
			if(tempoMedioAtividadesExecutadas!=0 && qtdeAtividadesExecutadas!=0){
				System.out.println("Qtde de tempo das atividades executadas nesse periodo: "+ tempoMedioAtividadesExecutadas/qtdeAtividadesExecutadas);
			}
		}
		
		
		List<Float> listaGeral = new ArrayList<>();
		if(tempoMedioAtividades!=0 && qtdeAtividades!=0){
			listaGeral.add(tempoMedioAtividades/qtdeAtividades);
		}else{
			listaGeral.add(new Float(0));
		}
		
		
		if( tempoMedioAtividadesExecutadas != 0  && qtdeAtividadesExecutadas!= 0){
			listaGeral.add(tempoMedioAtividadesExecutadas/qtdeAtividadesExecutadas);	
		}else{
			listaGeral.add( new Float(0) );
		}
		
		
		return listaGeral;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> tempoPrevistoTempoUtilizadoPorPessoa(Integer cd_usuario, Integer cd_unidade, Date dt_inicio, Date dt_fim){
		Integer tempoMedioAtividades=0;
		Integer tempoMedioAtividadesExecutadas=0;
		
		dataAtual = getDataAtual();
		dataAtual.set(Calendar.MONTH, dt_inicio.getMonth() );
		dataAtual.set(Calendar.DATE , dt_inicio.getDate()  );
		
		dataFim = getDataFim();
		dataFim.set(Calendar.MONTH, dt_fim.getMonth() );
		dataFim.set(Calendar.DATE, dt_fim.getDate() );
		
		dataInicialDia = getDataInicialDia();
		dataFinalDia   = getDataFinalDia();
		
		this.setDao(new GenericDAO());
		
		if(dt_inicio!=null && dt_fim!=null  ){
			
			Boolean terminouPeriodo=false;
			while(!terminouPeriodo){
				
				Integer diaSemana = dataAtual.get(GregorianCalendar.DAY_OF_WEEK);
				System.out.println("DIA ATUAL: "+  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataAtual.getTime()) );
				
				List<Escalonamento_formulario> listaEscForm = getEscalonamentosPeriodo(dataAtual, cd_unidade);
				
				if(listaEscForm!=null){
					for(Escalonamento_formulario esc:listaEscForm){
						
						List<Escalonamento_usuario> listaEscalonamentosUsuario = getListaEscalonamentoUsuario(cd_usuario, esc, diaSemana);
						if(listaEscalonamentosUsuario!=null){
							
							Execucao_formulario execucao = null;
							if(listaEscalonamentosUsuario.size()>0){
								for(Escalonamento_usuario escUsu:listaEscalonamentosUsuario){
									
									Formulario form = (Formulario) this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", esc.getCd_formulario())).uniqueResult();
									tempoMedioAtividades = tempoMedioAtividades + form.getNr_tempoestimado(); 
									
									
									try {
										dataInicialDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
										dataFinalDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
										dataInicialDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
										dataFinalDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
										
										System.out.println("DIA COMPARACAO: "+  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicialDia.getTime()) );
										execucao = getExecucao(esc, cd_usuario, escUsu);
									} catch (Exception e) {
										//LogPrimum.mensagem("2 OU MAIS EXECUÇÕES PARA UMA TAREFA NA MESMA DATA. CHECAR ESCALONAMENTO_BO");
									}
	
									if(execucao!=null && execucao.getCd_execucao_formulario()!=null){
										
										Long tempoTotaLong;
										Integer tempoTotal;
										tempoTotaLong = execucao.getNr_hora_termino().getTime() - execucao.getDt_execucao_formulario().getTime();
										tempoTotal = tempoTotaLong.intValue();
										if(tempoTotal!=0){
											tempoTotal = tempoTotal/(60*1000);
										}else{
											tempoTotal=0;
										}
										tempoMedioAtividadesExecutadas = tempoMedioAtividadesExecutadas + tempoTotal;
										
									}
								}
							}
						}
					}
				}

				if(dataAtual.get(Calendar.DATE) == dataFim.get(Calendar.DATE) &&
						dataAtual.get(Calendar.MONTH) == dataFim.get(Calendar.MONTH ) &&
						dataAtual.get(Calendar.YEAR) == dataFim.get(Calendar.YEAR) ){
					terminouPeriodo=true;
					break;
				}
				dataAtual.set(Calendar.DATE, dataAtual.get(Calendar.DATE) +1 );
			}
		}
		
		List<Integer> listaGeral = new ArrayList<>();
		
		listaGeral.add(tempoMedioAtividades);
		listaGeral.add(tempoMedioAtividadesExecutadas);		

		return listaGeral;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> tempoPrevistoTempoUtilizado(Integer cd_usuario, Integer cd_unidade, Date dt_inicio, Date dt_fim){
		
		Integer tempoMedioAtividades=0;
		Integer tempoMedioAtividadesExecutadas=0;
		
		Integer tempoMedioAtividadesTodas=0;
		Integer tempoMedioAtividadesExecutadasTodas=0;
		

		dataAtual = getDataAtual();
		dataAtual.set(Calendar.MONTH, dt_inicio.getMonth() );
		dataAtual.set(Calendar.DATE , dt_inicio.getDate()  );
		
		dataFim = getDataFim();
		dataFim.set(Calendar.MONTH, dt_fim.getMonth() );
		dataFim.set(Calendar.DATE, dt_fim.getDate() );
		
		dataInicialDia = getDataInicialDia();
		dataFinalDia   = getDataFinalDia();
		
		this.setDao(new GenericDAO());
		
		if(dt_inicio!=null && dt_fim!=null  ){
			
			Boolean terminouPeriodo=false;
			while(!terminouPeriodo){
				
				Integer diaSemana = dataAtual.get(GregorianCalendar.DAY_OF_WEEK);
				System.out.println("DIA ATUAL: "+  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataAtual.getTime()) );
				
				List<Escalonamento_formulario> listaEscForm = getEscalonamentosPeriodo(dataAtual, cd_unidade);
				
				if(listaEscForm!=null){
					for(Escalonamento_formulario esc:listaEscForm){
						
						/*****/
						List<Usuario> listaUsuarios = (List<Usuario>) this.getDao().getSession().createCriteria(Usuario.class)
								.addOrder(Order.asc("cd_usuario")).list();
							
						for(Usuario u:listaUsuarios){
							List<Escalonamento_usuario> listaEscalonamentosUsuario = getListaEscalonamentoUsuario(u.getCd_usuario(), esc, diaSemana);
							if(listaEscalonamentosUsuario!=null){
								
								Execucao_formulario execucao = null;
								if(listaEscalonamentosUsuario.size()>0){
									for(Escalonamento_usuario escUsu:listaEscalonamentosUsuario){
										
										Formulario form = (Formulario) this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", esc.getCd_formulario())).uniqueResult();
										tempoMedioAtividadesTodas = tempoMedioAtividadesTodas + form.getNr_tempoestimado(); 
										
										try {
											dataInicialDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
											dataFinalDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
											dataInicialDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
											dataFinalDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
											
											execucao = getExecucao(esc, u.getCd_usuario(), escUsu);
										} catch (Exception e) {
											//LogPrimum.mensagem("2 OU MAIS EXECUÇÕES PARA UMA TAREFA NA MESMA DATA. CHECAR ESCALONAMENTO_BO");
										}
		
										if(execucao!=null && execucao.getCd_execucao_formulario()!=null){
											
											Long tempoTotaLong;
											Integer tempoTotal;
											tempoTotaLong = execucao.getNr_hora_termino().getTime() - execucao.getDt_execucao_formulario().getTime();
											tempoTotal = tempoTotaLong.intValue();
											if(tempoTotal!=0){
												tempoTotal = tempoTotal/(60*1000);
											}else{
												tempoTotal=0;
											}
											tempoMedioAtividadesExecutadasTodas = tempoMedioAtividadesExecutadasTodas + tempoTotal;
											
										}
									}
								}
							}
						}
						/*****/
						
						List<Escalonamento_usuario> listaEscalonamentosUsuario = getListaEscalonamentoUsuario(cd_usuario, esc, diaSemana);
						if(listaEscalonamentosUsuario!=null){
							
							Execucao_formulario execucao = null;
							if(listaEscalonamentosUsuario.size()>0){
								for(Escalonamento_usuario escUsu:listaEscalonamentosUsuario){
									
									Formulario form = (Formulario) this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", esc.getCd_formulario())).uniqueResult();
									tempoMedioAtividades = tempoMedioAtividades + form.getNr_tempoestimado(); 
									
									
									try {
										dataInicialDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
										dataFinalDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
										dataInicialDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
										dataFinalDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
										
										System.out.println("DIA COMPARACAO: "+  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicialDia.getTime()) );
										execucao = getExecucao(esc, cd_usuario, escUsu);
									} catch (Exception e) {
										//LogPrimum.mensagem("2 OU MAIS EXECUÇÕES PARA UMA TAREFA NA MESMA DATA. CHECAR ESCALONAMENTO_BO");
									}
	
									if(execucao!=null && execucao.getCd_execucao_formulario()!=null){
										
										Long tempoTotaLong;
										Integer tempoTotal;
										tempoTotaLong = execucao.getNr_hora_termino().getTime() - execucao.getDt_execucao_formulario().getTime();
										tempoTotal = tempoTotaLong.intValue();
										if(tempoTotal!=0){
											tempoTotal = tempoTotal/(60*1000);
										}else{
											tempoTotal=0;
										}
										tempoMedioAtividadesExecutadas = tempoMedioAtividadesExecutadas + tempoTotal;
										
									}
								}
							}
						}
					}
				}

				if(dataAtual.get(Calendar.DATE) == dataFim.get(Calendar.DATE) &&
						dataAtual.get(Calendar.MONTH) == dataFim.get(Calendar.MONTH ) &&
						dataAtual.get(Calendar.YEAR) == dataFim.get(Calendar.YEAR) ){
					terminouPeriodo=true;
					break;
				}
				dataAtual.set(Calendar.DATE, dataAtual.get(Calendar.DATE) +1 );
			}
		}
		
		List<Integer> listaGeral = new ArrayList<>();
		
		listaGeral.add(tempoMedioAtividades);
		listaGeral.add(tempoMedioAtividadesExecutadas);	
		
		listaGeral.add(tempoMedioAtividadesTodas);
		listaGeral.add(tempoMedioAtividadesExecutadasTodas);	

		return listaGeral;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<List<?>> diaPorcentagemExecutadas(Integer cd_usuario, Integer cd_unidade, Date dt_inicio, Date dt_fim){
		
		List<String> listaDias = new ArrayList<>();
		List<Integer> listaPorcentagem = new ArrayList<>();
		
		List<String> listaDiasTotal = new ArrayList<>();
		List<Integer> listaPorcentagemTotal = new ArrayList<>();
		
		Integer totalAtividadesDiaTodos=0;
		Integer totalAtrasadoTodos=0;
		
		Integer totalAtividadesDia=0;
		Integer totalAtrasada=0;
		
		dataAtual = getDataAtual();
		dataAtual.set(Calendar.MONTH, dt_inicio.getMonth() );
		dataAtual.set(Calendar.DATE , dt_inicio.getDate()  );
		
		dataFim = getDataFim();
		dataFim.set(Calendar.MONTH, dt_fim.getMonth() );
		dataFim.set(Calendar.DATE, dt_fim.getDate() );
		
		dataInicialDia = getDataInicialDia();
		dataFinalDia   = getDataFinalDia();
		
		this.setDao(new GenericDAO());
		List<Usuario> listaUsuarios = (List<Usuario>) this.getDao().getSession().createCriteria(Usuario.class)
					.addOrder(Order.asc("cd_usuario")).list();
		if(dt_inicio!=null && dt_fim!=null  ){
			
			Boolean terminouPeriodo=false;
			while(!terminouPeriodo){
				
				Integer diaSemana = dataAtual.get(GregorianCalendar.DAY_OF_WEEK);
				List<Escalonamento_formulario> listaEscForm = (List<Escalonamento_formulario>) this.getDao().getSession().createCriteria(Escalonamento_formulario.class)
						.add(Restrictions.le("dt_inicio_vigencia",dataAtual.getTime()))
						.add(Restrictions.ge("dt_termino_vigencia",dataAtual.getTime()))
						.list();
				if(listaEscForm!=null){
					
					totalAtividadesDia  = 0;
					totalAtrasada		= 0;
					
					totalAtividadesDiaTodos  = 0;
					totalAtrasadoTodos		= 0;
					
					for(Escalonamento_formulario esc:listaEscForm){
						
						/***/
						
						for(Usuario u : listaUsuarios){

							List<Escalonamento_usuario> listaEscalonamentosUsuario = getListaEscalonamentoUsuario(u.getCd_usuario(), esc, diaSemana);
							if(listaEscalonamentosUsuario!=null){
								
								Execucao_formulario execucao = null;
								Execucao_formulario execucaoTotal = null;
								if(listaEscalonamentosUsuario.size()>0){
									
									for(Escalonamento_usuario escUsu:listaEscalonamentosUsuario){
										
	//									Formulario form = (Formulario) this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", esc.getCd_formulario())).uniqueResult();
	//									List<Usuario> listaUsuarios = (List<Usuario>) this.getDao().getSession().createCriteria(Usuario.class)
	//											.addOrder(Order.asc("cd_usuario")).list();
										
										
										
										try {
											dataInicialDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
											dataFinalDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
											dataInicialDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
											dataFinalDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
											
											System.out.println("DIA COMPARACAO: "+  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicialDia.getTime()) );
											
												execucaoTotal = getExecucao(esc, escUsu.getCd_usuario(), escUsu);
												
												if(execucaoTotal!=null && execucaoTotal.getCd_execucao_formulario()!=null){
													
													if(escUsu.getCd_usuario().equals(cd_usuario)){
														Integer horaExecucao = escUsu.getHr_execucao().getHours() * 60 * 60 * 1000;
														horaExecucao += escUsu.getHr_execucao().getMinutes() * 60 * 1000;
														
														totalAtividadesDia += 1;
														
														//SE A ATIVIDADE FOI INICIADA ATÉ 5 MINUTOS DEPOIS DE INICIADA
														System.out.println("DIA ATUAL: "+  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataAtual.getTime()) );
														System.out.println("DIA COMPARAÇÃO: "+  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicialDia.getTime()) );
														System.out.println( ((execucaoTotal.getDt_execucao_formulario().getHours() * 60 * 60 * 1000)+ (execucaoTotal.getDt_execucao_formulario().getMinutes() * 60 * 1000))  +" <> "+ (horaExecucao+300000) );
														
														if( ((execucaoTotal.getDt_execucao_formulario().getHours() * 60 * 60 * 1000)+ (execucaoTotal.getDt_execucao_formulario().getMinutes() * 60 * 1000)) <= horaExecucao+300000){
															
														}else{
															totalAtrasada+=1;
														}
													}
													
													Integer horaExecucao = escUsu.getHr_execucao().getHours() * 60 * 60 * 1000;
													horaExecucao += escUsu.getHr_execucao().getMinutes() * 60 * 1000;
													totalAtividadesDiaTodos += 1;
													if( ((execucaoTotal.getDt_execucao_formulario().getHours() * 60 * 60 * 1000)+ (execucaoTotal.getDt_execucao_formulario().getMinutes() * 60 * 1000)) <= horaExecucao+300000){
													}else{
														totalAtrasadoTodos +=1;
													}
												}
											
										} catch (Exception e) {
											//LogPrimum.mensagem("2 OU MAIS EXECUÇÕES PARA UMA TAREFA NA MESMA DATA. CHECAR ESCALONAMENTO_BO");
										}
									}
								}
							}
						}
					}
				}

				if(!totalAtrasada.equals(0)){
					listaDias.add(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataAtual.getTime()));
					if((totalAtividadesDia-totalAtrasada)!=0){
						listaPorcentagem.add( Math.round( ((totalAtividadesDia-totalAtrasada)*100) / totalAtividadesDia ) );
					}else{
						listaPorcentagem.add( 0 );
					}
				}else{
					if(!totalAtividadesDia.equals(0)){
						listaDias.add(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataAtual.getTime()));
						listaPorcentagem.add( 100 );
					}
				}
				
				if(!totalAtrasadoTodos.equals(0)){
					listaDiasTotal.add(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataAtual.getTime()));
					if((totalAtividadesDiaTodos-totalAtrasadoTodos)!=0){
						listaPorcentagemTotal.add( Math.round( ((totalAtividadesDiaTodos-totalAtrasadoTodos)*100) / totalAtividadesDiaTodos ) );
					}else{
						listaPorcentagemTotal.add( 0 );
					}
				}else{
					if(!totalAtividadesDiaTodos.equals(0)){
						listaDiasTotal.add(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataAtual.getTime()));
						listaPorcentagemTotal.add( 100 );
					}
				}

				if(dataAtual.get(Calendar.DATE) == dataFim.get(Calendar.DATE) &&
						dataAtual.get(Calendar.MONTH) == dataFim.get(Calendar.MONTH ) &&
						dataAtual.get(Calendar.YEAR) == dataFim.get(Calendar.YEAR) ){
					terminouPeriodo=true;
					break;
				}
				dataAtual.set(Calendar.DATE, dataAtual.get(Calendar.DATE) +1 );
			}
		}
		
		List<List<?>> listaGeral = new ArrayList<>();
		
		listaGeral.add(listaDias);
		listaGeral.add(listaPorcentagem);	
		listaGeral.add(listaDiasTotal);
		listaGeral.add(listaPorcentagemTotal);
		
		return listaGeral;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> diaPorcentagemExecutadasPorPessoa(Integer cd_usuario, Integer cd_unidade, Date dt_inicio, Date dt_fim){
		
		List<String> listaDias = new ArrayList<>();
		List<Integer> listaPorcentagem = new ArrayList<>();
		
		Integer totalAtividadesDia=0;
		Integer totalAtrasada=0;
		
		dataAtual = getDataAtual();
		dataAtual.setTime(dt_inicio);
		
		dataFim = getDataFim();
		dataFim.setTime( dt_fim );
		
		dataInicialDia = getDataInicialDia();
		dataFinalDia   = getDataFinalDia();
		
		this.setDao(new GenericDAO());
		
		if(dt_inicio!=null && dt_fim!=null  ){
			
			Boolean terminouPeriodo=false;
			while(!terminouPeriodo){
				
				Integer diaSemana = dataAtual.get(GregorianCalendar.DAY_OF_WEEK);
				List<Escalonamento_formulario> listaEscForm = (List<Escalonamento_formulario>) this.getDao().getSession().createCriteria(Escalonamento_formulario.class)
						.add(Restrictions.le("dt_inicio_vigencia",dataAtual.getTime()))
						.add(Restrictions.ge("dt_termino_vigencia",dataAtual.getTime()))
						.list();
				if(listaEscForm!=null){
					
					totalAtividadesDia  = 0;
					totalAtrasada		= 0;

					
					for(Escalonamento_formulario esc:listaEscForm){
						
						/***/
		
						List<Escalonamento_usuario> listaEscalonamentosUsuario = getListaEscalonamentoUsuario( cd_usuario , esc, diaSemana);
						if(listaEscalonamentosUsuario!=null){
							
							Execucao_formulario execucao = null;
							Execucao_formulario execucaoTotal = null;
							if(listaEscalonamentosUsuario.size()>0){
								
								for(Escalonamento_usuario escUsu:listaEscalonamentosUsuario){
									
									try {
										dataInicialDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
										dataFinalDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
										dataInicialDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
										dataFinalDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
										
										System.out.println("DIA COMPARACAO: "+  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicialDia.getTime()) );
										
										execucaoTotal = getExecucao(esc, escUsu.getCd_usuario(), escUsu);
										
										if(execucaoTotal!=null && execucaoTotal.getCd_execucao_formulario()!=null){
											
											Integer horaExecucao = escUsu.getHr_execucao().getHours() * 60 * 60 * 1000;
											horaExecucao += escUsu.getHr_execucao().getMinutes() * 60 * 1000;
											
											totalAtividadesDia += 1;
											
											//SE FOI EXECUTADA NO PRAZO OU COM 15 MINUTOS DE TOLERANCIA
											if( ((execucaoTotal.getDt_execucao_formulario().getHours() * 60 * 60 * 1000)+ (execucaoTotal.getDt_execucao_formulario().getMinutes() * 60 * 1000)) <= horaExecucao+900000){
												
											}else{
												totalAtrasada+=1;
											}

										}
										
									} catch (Exception e) {
										//LogPrimum.mensagem("2 OU MAIS EXECUÇÕES PARA UMA TAREFA NA MESMA DATA. CHECAR ESCALONAMENTO_BO");
									}
								}
							}
						}
					}
				}

				if(!totalAtrasada.equals(0)){
					listaDias.add(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataAtual.getTime()));
					if((totalAtividadesDia-totalAtrasada)!=0){
						listaPorcentagem.add( Math.round( ((totalAtividadesDia-totalAtrasada)*100) / totalAtividadesDia ) );
					}else{
						listaPorcentagem.add( 0 );
					}
				}else{
					if(!totalAtividadesDia.equals(0)){
						listaDias.add(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataAtual.getTime()));
						listaPorcentagem.add( 100 );
					}
				}

				if(dataAtual.get(Calendar.DATE) == dataFim.get(Calendar.DATE) &&
						dataAtual.get(Calendar.MONTH) == dataFim.get(Calendar.MONTH ) &&
						dataAtual.get(Calendar.YEAR) == dataFim.get(Calendar.YEAR) ){
					terminouPeriodo=true;
					break;
				}
				dataAtual.set(Calendar.DATE, dataAtual.get(Calendar.DATE) +1 );
			}
		}
		
		List<List<?>> listaGeral = new ArrayList<>();
		
		listaGeral.add(listaDias);
		listaGeral.add(listaPorcentagem);
		
		return listaGeral;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> ncsPorSetor(Integer cd_unidade, Date dt_inicio, Date dt_fim ){
		
		if(dt_inicio!=null && dt_fim!=null  ){
			
			dataInicialDia = getDataAtual();
			dataInicialDia.set(Calendar.YEAR, dt_inicio.getYear() + 1900 );
			dataInicialDia.set(Calendar.MONTH, dt_inicio.getMonth() );
			dataInicialDia.set(Calendar.DATE , dt_inicio.getDate()  );
			
			
			dataFim = getDataFim();
			dataFim.set(Calendar.YEAR, dt_fim.getYear() + 1900);
			dataFim.set(Calendar.MONTH, dt_fim.getMonth() );
			dataFim.set(Calendar.DATE, dt_fim.getDate() );
			
			
			this.setDao(new GenericDAO());
			List<Setor> listaSetores = (List<Setor>) this.getDao().getSession().createCriteria(Setor.class)
					.add(Restrictions.eq("cd_unidade", cd_unidade)).list();
			
			List<List<Integer>> listaNcs= new ArrayList<List<Integer>>();
			
			for (Setor s : listaSetores ) {
				
			
				List<Plano_acao> listaPlanos = (List<Plano_acao>) this.getDao().getSession().createCriteria(Plano_acao.class)
						.add(Restrictions.eq("cd_setor_ocorrencia", s.getCd_setor()))
						.add(Restrictions.ge("dt_criacao",dataInicialDia.getTime()))
						.add(Restrictions.le("dt_criacao",dataFim.getTime()))
						.list();
				
				System.out.println( new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataInicialDia.getTime()) );
				System.out.println( new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataFim.getTime()) );
				
				
				Integer abertas=0, emAndamento=0, finalizadas = 0;
				List<Integer> lista = new ArrayList<Integer>();
				for(Plano_acao p :listaPlanos){
					if(p.getSt_plano_acao().equals(0)){
						abertas+=1;
					}else if(p.getSt_plano_acao().equals(1)){
						emAndamento+=1;
					}else{
						finalizadas+=1;
					}
				}
				lista.add(abertas);
				lista.add(emAndamento);
				lista.add(finalizadas);
				listaNcs.add(lista);
			}
			
			List<List<?>> listaGeral = new ArrayList<>();		
			listaGeral.add(listaSetores);
			listaGeral.add(listaNcs);	
	
			return listaGeral;
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List< Conclusao_auditoria > desempenhoAuditorias( Cronograma_auditoria c, Date data_inicio, Date data_fim ){
		
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();	
		this.setDao(new GenericDAO());
		List<Conclusao_auditoria> listaConclusoes= (List<Conclusao_auditoria>) this.getDao().getSession().createCriteria(Conclusao_auditoria.class)
				.add(Restrictions.eq("cd_cronograma_auditoria", c.getCd_cronograma_auditoria()))
				.add(Restrictions.ge("dt_conclusao_auditoria", data_inicio ))
				.add(Restrictions.le("dt_conclusao_auditoria", data_fim ))
				.addOrder(Order.asc("dt_conclusao_auditoria"))
				.list();
		
		for(Conclusao_auditoria ca : listaConclusoes){
			pesquisa.buscarObjetoCompleto( ca );
		}
		
		return listaConclusoes;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> NcsPorPeriodo( Date dt_inicio, Date dt_fim){

		this.setDao(new GenericDAO());
		List<String> listaDias = new ArrayList<>();
		List<Integer> listaPorcentagem = new ArrayList<>();
		
		GregorianCalendar dataAtual = getDataAtual();
		dataAtual.set(Calendar.MONTH, dt_inicio.getMonth() );
		dataAtual.set(Calendar.DATE , dt_inicio.getDate()  );
		dataFim = getDataFim();
		dataFim.set(Calendar.MONTH, dt_fim.getMonth() );
		dataFim.set(Calendar.DATE, dt_fim.getDate() );
		dataInicialDia = getDataInicialDia();
		dataFinalDia   = getDataFinalDia();
		
		Boolean terminouPeriodo=false;
		while(!terminouPeriodo){
			
			dataInicialDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
			dataFinalDia.set(Calendar.MONTH , dataAtual.get( Calendar.MONTH ) );
			dataInicialDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
			dataFinalDia.set(Calendar.DATE , dataAtual.get( Calendar.DATE ) );
			
			
			List<Plano_acao> listaPlanos= (List<Plano_acao>) this.getDao().getSession().createCriteria(Plano_acao.class).
					add(Restrictions.ge("dt_criacao", dataInicialDia.getTime())).
					add(Restrictions.le("dt_criacao", dataFinalDia.getTime())).
					list();
			
			if( listaPlanos.size() > 0 ){
				listaDias.add( new SimpleDateFormat("dd/MM/yyyy HH:mm").format( dataAtual.getTime() ) );
				listaPorcentagem.add( listaPlanos.size() );
			}
			
			if(dataAtual.get(Calendar.DATE) == dataFim.get(Calendar.DATE) &&
					dataAtual.get(Calendar.MONTH) == dataFim.get(Calendar.MONTH ) &&
					dataAtual.get(Calendar.YEAR) == dataFim.get(Calendar.YEAR) ){
				terminouPeriodo=true;
				break;
			}
			dataAtual.set(Calendar.DATE, dataAtual.get(Calendar.DATE) +1 );
		}
		
		List<List<?>> listaGeral = new ArrayList<>();		
		listaGeral.add(listaDias);
		listaGeral.add(listaPorcentagem);	

		return listaGeral;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<List<?>> NcsPorProgramaQualidade( Date dt_inicio, Date dt_fim){

		this.setDao(new GenericDAO());
		List<Integer> listaQtdes = new ArrayList<>();
		List<List<?>> listaGeral = new ArrayList<>();
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		GregorianCalendar dataInicial = new GregorianCalendar();
		GregorianCalendar dataFinal   = new GregorianCalendar();
		dataInicial.setTime(dt_inicio);
		dataFinal.setTime(dt_fim);
		
		dataInicial.set(Calendar.SECOND, 0);
		dataInicial.set(Calendar.MINUTE, 0);
		dataInicial.set(Calendar.HOUR_OF_DAY, 0);

		dataInicial.set(Calendar.SECOND, 59);
		dataInicial.set(Calendar.MINUTE, 59);
		dataInicial.set(Calendar.HOUR_OF_DAY, 23);

		
		List<Programa_qualidade> listaProgramas = (List<Programa_qualidade>) this.getDao().getSession().createCriteria(Programa_qualidade.class).list();
		for(Integer i = 0; i< listaProgramas.size() ; i++){
			listaQtdes.add( 0 );
		}
		
		List<Plano_acao_formulario> listaPlanosForms = (List<Plano_acao_formulario>) this.getDao().getSession().createCriteria(Plano_acao_formulario.class)
				//.add(Restrictions.between("dt_criacao", dataInicial.getTime(),dataFinal.getTime()))
				.list();
		
		for(Plano_acao_formulario p : listaPlanosForms){
			
			pesquisa.buscarObjetoCompleto( p );
			if(p.getPlano_acao().getDt_criacao().after(dataInicial.getTime()) &&
				p.getPlano_acao().getDt_criacao().before(dataFinal.getTime())){
			
				Resposta_exec_formulario resp = ( Resposta_exec_formulario ) this.getDao().getSession().createCriteria(Resposta_exec_formulario.class).add(Restrictions.eq("cd_resposta_exec_formulario", p.getCd_resposta_exec_formulario())).uniqueResult();
				if(resp!=null){
					Campo_formulario campo = ( Campo_formulario ) this.getDao().getSession().createCriteria(Campo_formulario.class).add(Restrictions.eq("cd_campo_formulario", resp.getCd_campo_formulario())).uniqueResult();
					if(campo!=null){
						Grupo_formulario grupo = ( Grupo_formulario ) this.getDao().getSession().createCriteria(Grupo_formulario.class).add(Restrictions.eq("cd_grupo_formulario", campo.getCd_grupo_formulario())).uniqueResult();
						if(grupo!=null){
							
							if(grupo.getCd_formulario()==null){
								grupo = ( Grupo_formulario ) this.getDao().getSession().createCriteria(Grupo_formulario.class).add(Restrictions.eq("cd_grupo_formulario", grupo.getCd_grupo_pai())).uniqueResult();
							}
							
							Formulario f = ( Formulario ) this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", grupo.getCd_formulario())).uniqueResult();
							if(f!=null){
								Programa_qualidade prog_qualidade = ( Programa_qualidade ) this.getDao().getSession().createCriteria(Programa_qualidade.class).add(Restrictions.eq("cd_programa_qualidade", f.getCd_programa_qualidade())).uniqueResult();
								
								for(Integer ii = 0; ii< listaProgramas.size() ; ii++){
									if( listaProgramas.get( ii ).getCd_programa_qualidade().equals(prog_qualidade.getCd_programa_qualidade()) ){
										listaQtdes.set(ii, listaQtdes.get( ii ) + 1);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		
		listaGeral.add( listaProgramas );
		listaGeral.add( listaQtdes );
		
		return listaGeral;
	}

	@SuppressWarnings("unchecked")
	public List<BigDecimal> nivelNcs( Integer cd_unidade, Date dt_inicio, Date dt_fim) {
		
		dataAtual = getDataInicialDia();
		dataAtual.setTime( dt_inicio );
		dataAtual.set(Calendar.SECOND, 0 );
		dataAtual.set(Calendar.MINUTE, 0 );
		dataAtual.set(Calendar.HOUR_OF_DAY, 0 );
		
		dataFim = getDataFinalDia();
		dataFim.setTime( dt_fim );
		dataFim.set(Calendar.SECOND, 59 );
		dataFim.set(Calendar.MINUTE, 59 );
		dataFim.set(Calendar.HOUR_OF_DAY, 23 );
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		Float qtdeExecucoes = new Float(0);
		Float qtdeExecucoesNaoConfomes = new Float(0);
		Float qtdeRespostas = new Float(0);
		Float qtdeRespostasNaoConformes = new Float(0);
		

		this.getDao().getSession().createSQLQuery("update resposta_exec_formulario set tp_resposta_exec_formulario = 0 where cd_resposta_exec_formulario in( SELECT a.cd_resposta_exec_formulario  from resposta_exec_formulario a inner join plano_acao_formulario on a.cd_resposta_exec_formulario = plano_acao_formulario.cd_resposta_exec_formulario where a.tp_resposta_exec_formulario > -1)").executeUpdate();
		List<Execucao_formulario> listaExecucoes = (List<Execucao_formulario>) this.getDao().getSession().createCriteria(Execucao_formulario.class).
				add(Restrictions.eq("cd_unidade", cd_unidade)).
				add(Restrictions.between("dt_execucao_formulario", dataAtual.getTime(), dataFim.getTime())).
				add(Restrictions.isNotNull("nr_hora_termino")).
				list();
		
		Boolean execucaoNC=false;
		
		for( Execucao_formulario exe: listaExecucoes ){
			qtdeExecucoes += 1;
			execucaoNC=false;
			pesquisa.buscarObjetoCompleto( exe );
			for( Resposta_exec_formulario resp: exe.getListaRespostas_execucao_formulario() ){
				qtdeRespostas+=1;
				if(resp.getTp_resposta_exec_formulario()!=null){
					if(resp.getTp_resposta_exec_formulario().equals(0)){
						qtdeRespostasNaoConformes+=1;
						execucaoNC=true;
					}
				}
			}
			if(execucaoNC){
				qtdeExecucoesNaoConfomes+=1;
			}
		}

		List<BigDecimal> listaGeral = new ArrayList<>();
		
		if(qtdeExecucoes!=0){
			if(qtdeExecucoesNaoConfomes!=0){
				listaGeral.add( new BigDecimal((qtdeExecucoesNaoConfomes*100)/qtdeExecucoes)  );
			}else{
				listaGeral.add( new BigDecimal(0) );
			}
			
			if(qtdeRespostas!=0){
				if(qtdeRespostasNaoConformes!=0){
					listaGeral.add( new BigDecimal( (qtdeRespostasNaoConformes*100)/qtdeRespostas ) );
				}else{
					listaGeral.add(new BigDecimal(0) );
				}
			}else{
				listaGeral.add(new BigDecimal(0) );
			}
		}else{
			listaGeral.add( new BigDecimal(0) );
			listaGeral.add( new BigDecimal(0) );
		}

		listaGeral.add(new BigDecimal( qtdeExecucoes ));
		listaGeral.add(new BigDecimal( qtdeExecucoesNaoConfomes ));
		listaGeral.add(new BigDecimal( qtdeRespostas ));
		listaGeral.add(new BigDecimal( qtdeRespostasNaoConformes ));

		return listaGeral;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> diaQtdeExecucoes( Date dtInicio, Date dtFim ){
		this.setDao(new GenericDAO());
		List<Formulario> listaFormulario = new ArrayList<>();
		List<Integer> listaQtdes = new ArrayList<>();
		List<List<?>> listaTotal= new ArrayList<>();
		
		
		GregorianCalendar dataInicio  = new GregorianCalendar();
		GregorianCalendar dataFinal  = new GregorianCalendar();
		dataInicio.setTime(dtInicio);
		dataFinal.setTime(dtFim);
		dataInicio.set(Calendar.SECOND, 0);
		dataInicio.set(Calendar.MINUTE, 0);
		dataInicio.set(Calendar.HOUR_OF_DAY, 0);
		dataFinal.set(Calendar.SECOND, 59);
		dataFinal.set(Calendar.MINUTE, 59);
		dataFinal.set(Calendar.HOUR_OF_DAY, 23);
		
		List<Formulario> listaFormularios = (List<Formulario>) this.getDao().getSession().createCriteria(Formulario.class)
				.addOrder(Order.asc("nm_formulario")).list();
		
		for(Formulario form : listaFormularios){
			List<Execucao_formulario> listaExecucoes = (List<Execucao_formulario>) this.getDao().getSession().createCriteria(Execucao_formulario.class)
					.add(Restrictions.eq("cd_formulario", form.getCd_formulario()))
					.add(Restrictions.between("dt_execucao_formulario", dataInicio.getTime(), dataFinal.getTime()))
					.add(Restrictions.isNotNull("nr_hora_termino"))
					.addOrder(Order.asc("dt_execucao_formulario"))
					.list();
				
			if(listaExecucoes != null && listaExecucoes.size() > 0){
				listaFormulario.add( form );
				listaQtdes.add( listaExecucoes.size() );
			}
		}
		


		listaTotal.add( listaFormulario );
		listaTotal.add( listaQtdes );
		
		return listaTotal;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<List<?>> qtdeExecucoesPorProgramaQualidade( Date dtInicio, Date dtFim ){
		
		List<Programa_qualidade> listaProgramas = new ArrayList<>();
		List<Integer> listaQtdes = new ArrayList<>();
		List<List<?>> listaTotal = new ArrayList<>();
		
		
		GregorianCalendar dataInicio  = new GregorianCalendar();
		GregorianCalendar dataFinal  = new GregorianCalendar();
		dataInicio.setTime(dtInicio);
		dataFinal.setTime(dtFim);
		dataInicio.set(Calendar.SECOND, 0);
		dataInicio.set(Calendar.MINUTE, 0);
		dataInicio.set(Calendar.HOUR_OF_DAY, 0);
		dataFim.set(Calendar.SECOND, 59);
		dataFim.set(Calendar.MINUTE, 59);
		dataFim.set(Calendar.HOUR_OF_DAY, 23);
		
		this.setDao(new GenericDAO());
		
		List<Programa_qualidade> listaProgramasQualidade = (List<Programa_qualidade>) this.getDao().getSession().createCriteria(Programa_qualidade.class).list();
		if(listaProgramasQualidade != null && listaProgramasQualidade.size() > 0){
			
			for(Programa_qualidade prog : listaProgramasQualidade){
				
				List<Formulario> listaForms = (List<Formulario>) this.getDao().getSession().createCriteria(Formulario.class)
						.add(Restrictions.eq("cd_programa_qualidade", prog.getCd_programa_qualidade()))
						.list();
				
		
				if(listaForms != null && listaForms.size() > 0){
					Integer qtde = 0; 
					
					for(Formulario f : listaForms){
						
						List<Execucao_formulario> listaExecucoes = (List<Execucao_formulario>) this.getDao().getSession().createCriteria(Execucao_formulario.class)
								.add(Restrictions.eq("cd_formulario", f.getCd_formulario()))
								.add(Restrictions.between("dt_execucao_formulario", dataInicio.getTime(), dataFinal.getTime()))
								.add(Restrictions.isNotNull("nr_hora_termino"))
								.addOrder(Order.asc("dt_execucao_formulario"))
								.list();
							
						if(listaExecucoes != null ){
							qtde += listaExecucoes.size();
						}
						
					}
					
					listaProgramas.add( prog );
					listaQtdes.add( qtde );
					
				}
			}
		
		}
		
		listaTotal.add(listaProgramas);
		listaTotal.add(listaQtdes);
		return listaTotal;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Indicador_customizado> getIndicadoresPorPermissao(Integer cd_usuario){
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Indicador_customizado> listaIndicadores = new ArrayList<>();
		List<Permissao_indicador> listaPermissoes = (List<Permissao_indicador>) this.getDao().getSession().createCriteria(Permissao_indicador.class)
		.add(Restrictions.eq("cd_usuario", cd_usuario)).list();
		
		for(Permissao_indicador p : listaPermissoes){
			Indicador_customizado ind = ( Indicador_customizado ) this.getDao().getSession().createCriteria(Indicador_customizado.class).add(Restrictions.eq("cd_indicador_customizado", p.getCd_indicador_customizado())).uniqueResult();
			pesquisa.buscarObjetoCompleto( ind );
			
			listaIndicadores.add( ind );
		}
		
		return listaIndicadores;
	}
	
	@SuppressWarnings("unchecked")
	public List<Execucao_formulario> respostasCNCPorFormulario(Integer cd_formulario, Date dataInicio, Date dataFim ){
		
		dataInicialDia.setTime( dataInicio );
		dataFinalDia.setTime( dataFim );
		dataFinalDia.set(Calendar.SECOND, 59);
		dataFinalDia.set(Calendar.MINUTE, 59);
		dataFinalDia.set(Calendar.HOUR_OF_DAY, 23);
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO p = new PesquisaPadraoBO();
		List<Execucao_formulario> listaExecucoes = ( List<Execucao_formulario> ) this.getDao().getSession().createCriteria(Execucao_formulario.class)
				.add( Restrictions.eq("cd_formulario", cd_formulario) )
				.add( Restrictions.between("dt_execucao_formulario", dataInicialDia.getTime(), dataFinalDia.getTime() ) )
				.addOrder( Order.asc("dt_execucao_formulario") )
				.list();
		
		for(Execucao_formulario exe : listaExecucoes ){
			p.buscarObjetoCompleto( exe );
		}
		
		return listaExecucoes;
	}
	
	
	/**********/
	
	@SuppressWarnings("unchecked")
	public List<Modulo_indicadores> buscarIndicadoresModulos(){
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Modulo_indicadores> listaModulos = this.getDao().getSession().createCriteria(Modulo_indicadores.class).list();
		
		for(Integer i = 0 ; i< listaModulos.size() ; i++) {
			pesquisa.buscarObjetoCompleto( listaModulos.get(i) );
		}
		
		return listaModulos;
	}


	/**********/

	public String retornaImagemCodificada(String imagem){
		//<img src="data:image/jpeg;base64,
		
		try {
			
			File file = new File(imagem);
			byte[] bytes = loadFile(file);
			byte[] encoded = Base64.encodeBase64(bytes);
			String encodedString = new String(encoded);
			

			return encodedString;
			
		} catch (Exception e) {
			return null;
		}

	}
	
	private static byte[] loadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);
 
	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }
 
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }
 
	    is.close();
	    return bytes;
	}
	
	 public Boolean agendarEnvio( Integer cd_config_indicadores, String jobName, Date dataNotificar ){
		/*
		 JobDetail job = new JobDetailImpl();
		 
		 job.setName(jobName);
		 job.setJobClass(Indicadores_notificador.class);
		 job.getJobDataMap().put("envio_ind", cd_config_indicadores);
		
		 //configure the scheduler time
		 SimpleTrigger trigger = new SimpleTrigger();
		 trigger.setName("trigger_primum_"+ jobName);
		
		 trigger.setStartTime( dataNotificar );
		 //trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		
		 trigger.setRepeatCount(0);
		 trigger.setRepeatInterval(1);
			
		 
		 
		 //schedule it
		 try {
			 Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			 
			 
			 for (String groupName : scheduler.getJobGroupNames()) {
				 
		    	for (String nomeJob: scheduler.getJobNames(groupName)) {
		 
		    		Trigger[] triggers = scheduler.getTriggersOfJob(nomeJob,groupName);
		    		Date nextFireTime = triggers[0].getNextFireTime();
		    		
		    		if(nomeJob.equals("envio_ind-"	+cd_config_indicadores )){
		    			scheduler.unscheduleJob("trigger_primum_"+ jobName, groupName);
		    		}
		    		System.out.println("[jobName] : " + nomeJob + " [groupName] : " + groupName + " - nextFireTime: "+nextFireTime);
		    		//scheduler.unscheduleJob(triggerName, triggerGroup);
		    	}
		 
		    }
			 
			 scheduler.start();
			 scheduler.scheduleJob(job, trigger);
		 } catch (SchedulerException e) {
			 e.printStackTrace();
			 return false;
		 }
		 */
		 return true;
	 }
	 //agendarTarefa( not.getCd_notificacao_automatica() , strJobName, tempoTotal, not.getDt_inicio() );
	 
	 /**************************************/
	 /*** INDICADORES EMAIL ****************/
	 
//	 public String getURLImagens(){
//		
//		Dbconf dbconf = null;
//		try {
//			String dir = ContextoAplicacao.CAMINHO_APLICACAO + "//classes//dbconf.txt";
//			BufferedReader in = new BufferedReader(new FileReader(dir));
//
//			dbconf = new Dbconf();            
//            while (in.ready()) {
//
//            	String strDbconf = null;
//            	strDbconf = in.readLine();
//            	String campo = strDbconf.split("&")[0];
//            	String valor = strDbconf.split("&")[1];
//            	if(campo.equals("urlservidor")){
//            		return "http://"+ valor.split("/")[2] + "/Quality/imagens/";
//            	}
//            }
//            in.close();
//	    } catch (IOException e) {
//	    	//LogPrimum.mensagem("ERRO LINK EMAIL");
//	    } catch (Exception e) {
//	    	//LogPrimum.mensagem("ERRO LINK EMAIL");
//		}
//		
//		return "";
//	 }
	 
	 public String criaLegenda(){
		 String cabecalhoLegenda = "<hr/><table border='0'><tr><td columnspan='2'>Legenda</td></tr>";
		 return cabecalhoLegenda;
	 }
	 
	 public String addItemLegenda(String img, String descricao){
		 String linhaLegenda = ""+ 
		"<tr>";
			
			if(img == "azul"){
				linhaLegenda+= "<td width='60'><div style='background-color:#0098FF; position: relative; bottom: 0; float: left; width: 30px; height: 20px; margin: 1px; padding-top:3px'>&nbsp;</div></td>" ;
			}else if(img == "verde"){
				linhaLegenda+= "<td width='60'><div style='background-color:#65CC32; position: relative; bottom: 0; float: left; width: 30px; height: 20px; margin: 1px; padding-top:3px'>&nbsp;</div></td>" ;
			}if(img == "laranja"){
				linhaLegenda+= "<td width='60'><div style='background-color:#FF9800; position: relative; bottom: 0; float: left; width: 30px; height: 20px; margin: 1px; padding-top:3px'>&nbsp;</div></td>" ;
			}if(img == "vermelho"){
				linhaLegenda+= "<td width='60'><div style='background-color:#FF0800; position: relative; bottom: 0; float: left; width: 30px; height: 20px; margin: 1px; padding-top:3px'>&nbsp;</div></td>" ; 
			}else if(img == "azulEscuro"){
				linhaLegenda+= "<td width='60'><div style='background-color:#006D92; position: relative; bottom: 0; float: left; width: 30px; height: 20px; margin: 1px; padding-top:3px'>&nbsp;</div></td>"; 
			}else if(img == "verdeEscuro"){
				linhaLegenda+= "<td width='60'><div style='background-color:#097F00; position: relative; bottom: 0; float: left; width: 30px; height: 20px; margin: 1px; padding-top:3px'>&nbsp;</div></td>";
			}
	
			//"<td width='60'><div class='"+img+"' style='position: relative; bottom: 0; float: left; width: 30px; height: 20px; margin: 1px; padding-top:3px'>&nbsp;</div></td>" +	 
			 
			linhaLegenda += "<td width='210'>"+ descricao +"</td>" +
		"</tr>";
		 
		 return linhaLegenda;
	 }
	 
	 public String fechaLegenda(){
		 String rodapeLegenda = "</table><br><br><br>";
		 return rodapeLegenda;
	 }
	 
	 public String chamaIndicador( Integer cd_menu_indicadores , Integer cd_usuario, Integer cd_unidade, Date data_envio, Integer periodo, Boolean retornaString){
		
		String strEmail="";
		GregorianCalendar data = new GregorianCalendar();
		data.setTime(data_envio);
		data.set(Calendar.SECOND, 0);
		data.set(Calendar.MINUTE, 0);
		data.set(Calendar.HOUR_OF_DAY, 0);
		
		if(cd_menu_indicadores.equals(2) ){
			strEmail = EmailPercentualAtividadesExecutadas( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(3) ){
			strEmail = EmailTempoMedioExecucaoAtividades( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(4) ){
			strEmail = EmailTempoPrevistoUtilizado( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(5) ){
			strEmail = EmailPercentualAtividadesExecutadasPrazo( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(6) ){
			strEmail = EmailNCSPorSetor( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(7) ){
			strEmail = EmailDesempenhoEmAuditorias( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(8) ){
			strEmail = EmailNCSPorPeriodo( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(9) ){
			strEmail = EmailNivelDeNCS( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(10) ){
			strEmail = EmailNCSPorProgQualidade( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(13) ){
			strEmail = EmailStatusGeralOcorrencias( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(14) ){
			strEmail = EmailStatusGeralOcorrenciasPorPeriodo( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(15) ){
			strEmail = EmailOcorrenciasPorMotivoAbertura( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(16) ){
			strEmail = EmailOcorrenciasPorUnidade( cd_usuario, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(17) ){
			strEmail = EmailChamadosAbertosPorAtendente( cd_usuario, cd_unidade, data, periodo, retornaString );
		}else if(cd_menu_indicadores.equals(18) ){
			strEmail = EmailTotalChamados( cd_usuario, cd_unidade, data, periodo, retornaString );
		}
		
		return strEmail;
	 }
	

	@SuppressWarnings({ "unchecked", "static-access" })
	public String EmailPercentualAtividadesExecutadas( Integer cd_usuario, Integer cd_unidade,  GregorianCalendar data, Integer periodo, Boolean retornaString ){
			
			this.setDao(new GenericDAO());
			

			List<Usuario> listaUsuarios = ( List<Usuario> ) this.getDao().getSession().createCriteria(Usuario.class).addOrder(Order.asc("nm_usuario")).list();
			
			Float totalAtividades = new Float(0);
			Float totalExecutadas = new Float(0);
			Float totalDentroPrazo = new Float(0);
			Float totalForaPrazo = new Float(0);
			
			Long tempoTotalAtividades = new Long(0);
			Long tempoTotalExecutadas = new Long(0);
			
			Float totalAtividadesUsuario = new Float(0);
			Float totalAtividadesExecutadas = new Float(0);
			Float totalAtividadesExecutadasAtrasadas = new Float(0);
			Float totalAtividadesExecutadasPrazo = new Float(0);
			
			
			Long tempoTotalAtividadesUsuario = new Long(0);
			Long tempoTotalAtividadesExecutadas = new Long(0);
			
			Float porcentagemExecutadas = new Float(0);
			Float porcentagemExecutadasPrazo = new Float(0);
			Float porcentagemExecutadasForaPrazo = new Float(0);
			
			Double porcentagemMinutos = new Double(0);
			
			Escalonamento_formularioBO escBO = new Escalonamento_formularioBO();
			List<List<?>> listaGeral = new ArrayList<>();
			
			GregorianCalendar dataInicial = (GregorianCalendar) new GregorianCalendar().getInstance();
			GregorianCalendar dataFinal = (GregorianCalendar) new GregorianCalendar().getInstance();
			
			
			dataInicial.set(Calendar.SECOND, 0);
			dataInicial.set(Calendar.MINUTE, 0);
			dataInicial.set(Calendar.HOUR_OF_DAY, 0);
			dataFinal.set(Calendar.SECOND, 59);
			dataFinal.set(Calendar.MINUTE, 59);
			dataFinal.set(Calendar.HOUR_OF_DAY, 23);
			
			
			dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
			dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
			
//			Usuario usr = (Usuario) this.getDao().getSession().createCriteria(Usuario.class).add(Restrictions.eq("cd_usuario", cd_usuario)).uniqueResult();
//			Unidade unidade = ( Unidade ) usr.getListaUnidades_acesso().get(0);
//					
			String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
							"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
							"<p>Indicador: <font color='blue'> PERCENTUAL DE ATIVIDADES EXECUTADAS (NO PRAZO E ATRASADAS) / TEMPO PREVISTO E TEMPO UTILIZADO</font></p>"+
							"<hr/>";
			
			 
			//String tabela = "<hr/>";
			
			for(Integer i= -periodo ; i < 0 ; i++){
				
				List<?> lista = escBO.montarPainelProximoDia(i,cd_unidade);
				listaGeral.add( lista );
			}
			
			String imgAzul = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzul.png");
			String imgVerdeEscura = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraVerdeEscura.png");
			String imgAzulEscura = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzulEscura.png");
			String imgVerde = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraVerde.png");
			String imgVermelha = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraVermelha.png");
			
			String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
			
			
			for(Usuario u : listaUsuarios){
				
				totalAtividadesUsuario = new Float(0);
				totalAtividadesExecutadas = new Float(0);
				totalAtividadesExecutadasAtrasadas = new Float(0);
				totalAtividadesExecutadasPrazo = new Float(0);
				
				tempoTotalAtividadesExecutadas = new Long(0);
				tempoTotalAtividadesUsuario = new Long(0);
				
				for (Integer ii = 0 ; ii < listaGeral.size() ; ii++){
					List<?> lista = new ArrayList<>();
					lista = ((List<?> )listaGeral.get(ii));
					for (Integer e= 0; e< lista.size() ; e++){
	
						
						if(u.getCd_usuario().equals( ((Usuario)((List<?>) lista.get(e)).get(0)).getCd_usuario() )){
							totalAtividadesUsuario += ((List<Escalonamento_usuario>)((List<?>) lista.get(e)).get(1)).size();
						
						
							//((List<?>) lista.get(e)).get(0)   ---->  USUÁRIO
							//((List<?>) lista.get(e)).get(1)   ---->  LISTA DE ESCALONAMENTOS DELE DO DIA
						
							System.out.println( "Usuário '"+ ((Usuario)((List<?>) lista.get(e)).get(0)).getNm_usuario() +"' tem " + ((List<Escalonamento_usuario>)((List<?>) lista.get(e)).get(1)).size() + " atividades nesse dia!");
							
							totalAtividades += ((List<Escalonamento_usuario>)((List<?>) lista.get(e)).get(1)).size();
							
							for(Escalonamento_usuario esc : ((List<Escalonamento_usuario>)((List<?>) lista.get(e)).get(1))){
								
								Escalonamento_formulario escForm = (Escalonamento_formulario) this.getDao().getSession().createCriteria(Escalonamento_formulario.class).add(Restrictions.eq("cd_escalonamento_formulario", esc.getCd_escalonamento_formulario())).uniqueResult();
								Formulario form = ( Formulario ) this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", escForm.getCd_formulario())).uniqueResult();
								tempoTotalAtividades += form.getNr_tempoestimado()*60*1000;
								tempoTotalAtividadesUsuario += form.getNr_tempoestimado()*60*1000;
								
								if( !esc.getSt_escalonamento_usuario().equals(1) && !esc.getSt_escalonamento_usuario().equals(3) ){
									
									totalExecutadas+=1;
									totalAtividadesExecutadas+=1;
									
									
									if( esc.getExecucao_formulario() != null ) {
										if (esc.getExecucao_formulario().getDt_execucao_formulario() != null 
												&& esc.getExecucao_formulario().getNr_hora_termino() != null) {
											
											tempoTotalAtividadesExecutadas += (esc.getExecucao_formulario().getNr_hora_termino().getTime() - esc.getExecucao_formulario().getDt_execucao_formulario().getTime());
											tempoTotalExecutadas  += (esc.getExecucao_formulario().getNr_hora_termino().getTime() - esc.getExecucao_formulario().getDt_execucao_formulario().getTime());
										}
									}

									if( esc.getSt_escalonamento_usuario().equals(4) ){
										totalAtividadesExecutadasAtrasadas  += 1;
										totalForaPrazo+=1;
									}else{
										totalAtividadesExecutadasPrazo += 1;
										totalDentroPrazo+=1;
									}
								}	
							}
						}
					}
				}
				
				System.out.println( "totalAtividadesUsuario: "+ totalAtividadesUsuario +" <> totalAtividadesExecutadas: "+totalAtividadesExecutadas); 
				
				if( totalAtividadesUsuario > 0 ){
					porcentagemExecutadas = new Float(0);
					porcentagemExecutadasPrazo = new Float(0);
					porcentagemExecutadasForaPrazo = new Float(0);
					
					if(totalAtividadesExecutadas > 0){
						porcentagemExecutadas = ((totalAtividadesExecutadas * 100)/totalAtividadesUsuario);
						
						if(totalAtividadesExecutadasAtrasadas > 0){
							porcentagemExecutadasForaPrazo = ((totalAtividadesExecutadasAtrasadas * 100)/totalAtividadesExecutadas);
						}
						
						if( totalAtividadesExecutadasPrazo > 0){
							porcentagemExecutadasPrazo = ((totalAtividadesExecutadasPrazo * 100)/totalAtividadesExecutadas);
						}
					}
					
					porcentagemExecutadas = (porcentagemExecutadas>0)?(porcentagemExecutadas/100):0;
					porcentagemExecutadasPrazo = (porcentagemExecutadasPrazo>0)?(porcentagemExecutadasPrazo/100):0;
					porcentagemExecutadasForaPrazo = (porcentagemExecutadasForaPrazo>0)?(porcentagemExecutadasForaPrazo/100):0;
					
					porcentagemMinutos = new Double(0);
					if( tempoTotalAtividadesExecutadas > 0 && tempoTotalAtividadesUsuario > 0){
						
						Double a = Double.valueOf(tempoTotalAtividadesExecutadas); 
						Double b = Double.valueOf(tempoTotalAtividadesUsuario);  
						porcentagemMinutos= Double.valueOf((a / b));  

					}

					tabela += "<table border='0'>" + 
							"<tr>" + 
							"<td width='75' align='center'>Nome</td>" +
							"<td width='155' align='center'>Percentual de atividades executadas</td>" + 
							"<tr>" +
					"</table>"; 
				
					tabela += "<table border='0'>" +
										"<tr>" + 
											"<td width='70' rowspan='3'>"+ u.getNm_usuario() +"</td>" +
											"<td rowspan='3'><div style='position: relative; bottom: 0; float: left; width: 0px; height: 90px; background-color: #000000; margin: 1px;'>&nbsp;</div>" +    
											"<td width='150' height='33'><div class='azul' style='background-color:#0098FF; position: relative; bottom: 0; float: left; width: "+ 150*porcentagemExecutadas +"px; height: 20px; padding-top:3px'>&nbsp;</div></td>" +
											"<td width='170'>"+ new DecimalFormat("0.00").format( porcentagemExecutadas*100 ) +"%</td>" +
											
											"<td width='70' rowspan='3'>"+ u.getNm_usuario() +"</td>"+
											"<td rowspan='3'><div style='position: relative; bottom: 0; float: left; width: 0px; height: 99px; background-color: #000000; margin: 1px;' align='top'>&nbsp;</div></td>"+
											
											"<td width='100'><div class='verdeEscuro' style='background-color:#097F00; position: relative; bottom: 0; float: left; width: 100px; height: 20px; margin: 1px; padding-top:3px'>&nbsp;</div></td>"+
											"<td>"+ String.format("%d min", TimeUnit.MILLISECONDS.toMinutes( tempoTotalAtividadesUsuario )) +"</td>"+	

										"</tr>" +
			
										"<tr>" +
											"<td><div class='verde' style='background-color:#65CC32; position: relative; bottom: 0; float: left; width: "+ 150*porcentagemExecutadasPrazo +"px; height: 20px; padding-top:3px'>&nbsp;</div></td>" +											
											"<td>"+  new DecimalFormat("0.00").format( porcentagemExecutadasPrazo*100 ) +"%</td>" +
											
											"<td width='100'><div class='azulEscuro' style='background-color:#006D92;  position: relative; bottom: 0; float: left; width: "+ 100*porcentagemMinutos +"px; height: 20px; padding-top:3px'>&nbsp;</div></td>"+
											"<td>"+ String.format("%d min, %d sec", 
												    TimeUnit.MILLISECONDS.toMinutes( tempoTotalAtividadesExecutadas ),
												    TimeUnit.MILLISECONDS.toSeconds( tempoTotalAtividadesExecutadas ) - 
												    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes( tempoTotalAtividadesExecutadas ))) +"</td>"+	
										"</tr>" +
										
										"<tr>" +
											"<td><div class='vermelho' style='background-color:#FF0800; position: relative; bottom: 0; float: left; width: "+ 150*porcentagemExecutadasForaPrazo +"px; height: 20px; padding-top:3px'>&nbsp;</div></td>" +
											"<td>"+  new DecimalFormat("0.00").format( porcentagemExecutadasForaPrazo*100 ) +"%</td>" +


										"</tr>" +
									"</table><br>";
					
				}
				
			}
			
			System.out.println( "totalAtividades: "+ totalAtividades +" <> totalExecutadas: "+totalExecutadas);
			
			if( totalAtividades > 0 ){
				porcentagemExecutadas = new Float(0);
				porcentagemExecutadasPrazo = new Float(0);
				porcentagemExecutadasForaPrazo = new Float(0);
				
				if(totalExecutadas > 0){
					porcentagemExecutadas = ((totalExecutadas * 100)/totalAtividades);
					
					if(totalForaPrazo > 0){
						porcentagemExecutadasForaPrazo = ((totalForaPrazo * 100)/totalExecutadas);
					}
					
					if( totalDentroPrazo > 0){
						porcentagemExecutadasPrazo = ((totalDentroPrazo * 100)/totalExecutadas);
					}
				}
				
				porcentagemExecutadas = (porcentagemExecutadas>0)?(porcentagemExecutadas/100):0;
				porcentagemExecutadasPrazo = (porcentagemExecutadasPrazo>0)?(porcentagemExecutadasPrazo/100):0;
				porcentagemExecutadasForaPrazo = (porcentagemExecutadasForaPrazo>0)?(porcentagemExecutadasForaPrazo/100):0;
				
				porcentagemMinutos = new Double(0);
				if( tempoTotalAtividadesExecutadas > 0 && tempoTotalAtividadesUsuario > 0){
					
					Double a = Double.valueOf(tempoTotalAtividadesExecutadas); 
					Double b = Double.valueOf(tempoTotalAtividadesUsuario);  
					porcentagemMinutos= Double.valueOf((a / b));  

				}
			}
			
			
			
			tabela += "<table border='0'>" +
					"<tr>" + 
						"<td width='70' rowspan='3'>Todos os colaboradores</td>" +
						"<td rowspan='3'><div style='position: relative; bottom: 0; float: left; width: 0px; height: 90px; background-color: #000000; margin: 1px; padding-top:3px'>&nbsp;</div></td>" +    
						"<td width='150'><div class='azul' style='background-color:#0098FF; position: relative; bottom: 0; float: left; width: "+ 150*porcentagemExecutadas +"px; height: 20px; margin: 1px; padding-top:3px'>&nbsp;</div></td>" +
						"<td width='170'>"+ new DecimalFormat("0.00").format( porcentagemExecutadas*100 ) +"%</td>" +
						
						"<td width='70' rowspan='3'>Todos os colaboradores</td>"+
						"<td rowspan='3'><div style='position: relative; bottom: 0; float: left; width: 0px; height: 90px; background-color: #000000; margin: 1px; padding-top:3px' align='top'>&nbsp;</div></td>"+
						
						"<td width='100'><div class='verdeEscuro' style='background-color:#097F00; position: relative; bottom: 0; float: left; width: "+ ((tempoTotalAtividadesUsuario>0)?100:0) +"px; height: 70px; margin: 1px; padding-top:3px'>&nbsp;</div></td>"+
						"<td>"+ String.format("%d min", TimeUnit.MILLISECONDS.toMinutes( tempoTotalAtividadesUsuario )) +"</td>"+	

					"</tr>" +

					"<tr>" +
						"<td><div class='verde' style='background-color:#65CC32; position: relative; bottom: 0; float: left; width: "+ 150*porcentagemExecutadasPrazo +"px; height: 20px; margin: 1px; padding-top:3px'>&nbsp;</div></td>" +											
						"<td>"+  new DecimalFormat("0.00").format( porcentagemExecutadasPrazo*100 ) +"%</td>" +
						"<td width='100'><div class='azulEscuro' style='background-color:#006D92;  position: relative; bottom: 0; float: left; width: "+ 100*porcentagemMinutos +"px; height: 20px; margin: 1px; padding-top:3px'>&nbsp;</div></td>"+
						"<td>"+ String.format("%d min, %d sec", 
							    TimeUnit.MILLISECONDS.toMinutes( tempoTotalAtividadesExecutadas ),
							    TimeUnit.MILLISECONDS.toSeconds( tempoTotalAtividadesExecutadas ) - 
							    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes( tempoTotalAtividadesExecutadas ))) +"</td>"+	
					"</tr>" +
					
					"<tr>" +
						"<td><div class='vermelho' style='background-color:#FF0800; position: relative; bottom: 0; float: left; width: "+ 150*porcentagemExecutadasForaPrazo +"px; height: 20px; margin: 1px; padding-top:3px'>&nbsp;</div></td>" +
						"<td>"+  new DecimalFormat("0.00").format( porcentagemExecutadasForaPrazo*100 ) +"%</td>" +


					"</tr>" +
				"</table><br><br><br>";
			
			tabela += criaLegenda();
			tabela += addItemLegenda("azul", "Total de atividades executadas");
			tabela += addItemLegenda("verde", "Executadas no prazo previsto");
			tabela += addItemLegenda("vermelho", "Executadas atrasadas");
			tabela += addItemLegenda("verdeEscuro", "Tempo total previsto de atividades");
			tabela += addItemLegenda("azulEscuro", "Tempo utilizado");
			tabela += fechaLegenda();
			
			if( !retornaString ){
				//tabela = susbtituiAcentosHTML( tabela );
				Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
        				"<head>" +
//        				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//		        		"div.azulEscuro{ background-color:#006D92; }"+
//		        		"div.azul{ background-color:#0098FF; }"+
//		        		"div.verde{ background-color:#65CC32; }"+
//		        		"div.vermelho{ background-color:#FF0800; }"+
//		        		"div.laranja{ background-color:#FF9800; }"+
//		        		"div{ background-color: #aeaeae; }"+
//		        		"</style>"+
        				"</head><body>" + tabela +"</body></html>");
			}
				
			return tabela;
	 }
	
	@SuppressWarnings("unchecked")
	public String EmailTempoMedioExecucaoAtividades( Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString ) {
		
		this.setDao(new GenericDAO());
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
		
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
		
		List<Usuario> listaUsuarios = this.getDao().getSession().createCriteria(Usuario.class).addOrder(Order.asc("nm_usuario")).list();
		List<Float> listaValores = new ArrayList<>();
		
		
		System.out.println("data inicial: "+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime() ));
		System.out.println("data final  : "+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime() ));	
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
						"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
						"<p>Indicador: <font color='blue'> TEMPO MÉDIO PLANEJADO E TEMPO MÉDIO EXECUTADO</font></p>"+
						"<hr/>";
		
		Float somaTotalAtividades = new Float(0);
		Float somaTotalExecucoes = new Float(0);
		Float resultado1 = new Float(0);
		Float resultado2 = new Float(0);
		Integer qtde = 0;
		
		String img1 = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzulEscura2.png");
		String img2 = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzul2.png");
		String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
		
		for(Usuario u: listaUsuarios){
			listaValores = tempoMedioExecucao(u.getCd_usuario(), cd_unidade, dataInicial.getTime(), dataFinal.getTime());
			
			if(  listaValores.get(0) != 0  ){
				
				somaTotalAtividades += listaValores.get(0);
				somaTotalExecucoes += listaValores.get(1);
				qtde+=1;
				
				tabela += "<table border='0'>" + 
						"<tr>" + 
						"<td width='80' align='center'>Tempo médio</td>" +
						"<td width='80' align='center'>Tempo executado</td>" + 
						"<tr>" +
				"</table>"; 
				
				tabela += "<table border='0'>"+
				
				  "<tr>"+
				    "<th  align='center'>"+ new DecimalFormat("0.00").format( listaValores.get(0) ) +"</th>"+
				    "<th  align='center'>"+ new DecimalFormat("0.00").format( listaValores.get(1) )+"</th>"+
				  "</tr>"+
				  
				  "<tr>"+
				    "<td align='center' style='vertical-align:bottom'><div class='azulEscuro' style='background-color:#006D92;  width:20px; height: "+ (( listaValores.get(0) == 0 )?0:100) +"px'>&nbsp;</div></td>"+
				    "<td align='center' style='vertical-align:bottom'><div class='azul' style='background-color:#0098FF; width:20px; height: "+(( listaValores.get(1) == 0 )?0:(( listaValores.get(0) == 0 )?0: ((listaValores.get(1)*100)/listaValores.get(0)))) +"px'>&nbsp;</div></td>"+
				  "</tr>"+
				  		
				  "<tr>"+
				  
				    "<td colspan='2'><div style='position: relative; bottom: 0; float: left; width: 100px; height: 0px; margin: 1px; vertical-align:top'>&nbsp;</div></td>"+
				  "</tr>"+
				  
				  "<tr>"+
				    "<td colspan='2' align='center'><b>"+ u.getNm_usuario() +"</b></td>"+
				  "</tr>"+
				"</table><br><br>";
			}
		}
		
		resultado1 = somaTotalAtividades/qtde;
		resultado2 = somaTotalExecucoes/qtde;
		
		tabela += "<table border='0'>"+
				
				  "<tr>"+
				    "<th align='center'>"+ new DecimalFormat("0.00").format(resultado1) +"</th>"+
				    "<th align='center'>"+ new DecimalFormat("0.00").format(resultado2) +"</th>"+
				  "</tr>"+
	
				  "<tr>"+
				    "<td align='center' style='vertical-align:bottom'><div class='azulEscuro' style='background-color:#006D92;  width:20px; height: "+ (( resultado1.equals(0))?0:100) +"px'>&nbsp;</div></td>"+
				    "<td align='center' style='vertical-align:bottom'><div class='azul' style='background-color:#0098FF; width:20px; height: "+(( resultado2.equals(0))?0:(( resultado1.equals(0))?0: ((resultado2*100)/resultado1))) +"px'>&nbsp;</div></td>"+
				  "</tr>"+
				  		
				  "<tr>"+
				    "<td colspan='2'><div style='position: relative; bottom: 0; float: left; width: 100px; height: 0px; margin: 1px; vertical-align:top'>&nbsp;</div></td>"+
				  "</tr>"+
				  
				  "<tr>"+
				    "<td colspan='2' align='center'><b>Todos</b></td>"+
				  "</tr>"+
				"</table><br><br>";
		
		tabela += criaLegenda();
		tabela += addItemLegenda("azulEscuro", "Tempo médio planejado das tarefas (min)");
		tabela += addItemLegenda("azul", "Tempo médio executado das tarefas (min)");
		tabela += fechaLegenda();
		
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
	}
	
	@SuppressWarnings("unchecked")
	public String EmailTempoPrevistoUtilizado(Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString) {
		
		this.setDao(new GenericDAO());
		
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
		
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
		
		List<Usuario> listaUsuarios = this.getDao().getSession().createCriteria(Usuario.class).addOrder(Order.asc("nm_usuario")).list();
		List<Integer> listaValores = new ArrayList<>();
		
		
		System.out.println("data inicial: "+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime() ));
		System.out.println("data final  : "+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime() ));	
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
						"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
						"<p>Indicador: <font color='blue'> TEMPO PREVISTO E TEMPO UTILIZADO DAS ATIVIDADES</font></p>"+
						"<hr/>";
		
		Float somaTotalAtividades = new Float(0);
		Float somaTotalExecucoes = new Float(0);
//		Float resultado1 = new Float(0);
//		Float resultado2 = new Float(0);
		
		String imgAzulEscura = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzulEscura.png");
		String imgVerdeEscura = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraVerdeEscura.png");
		String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
		
		for(Usuario u: listaUsuarios){
			listaValores = tempoPrevistoTempoUtilizadoPorPessoa(u.getCd_usuario(), cd_unidade, dataInicial.getTime(), dataFinal.getTime());
			
			//listaValores.get(0)  --> tempo total atividades 
			//listaValores.get(1)  --> tempo total executadas
			//listaValores.get(2)  --> tempo total pessoa
			//listaValores.get(3)  --> tempo total executadas pessoa
			if((listaValores.get(0)>0)){
				
				somaTotalAtividades += listaValores.get(0); 
				somaTotalExecucoes += listaValores.get(1);
				
				tabela += "<table border='0'>" + 
						"<tr>" + 
						"<td width='120' align='center'>Tempo previsto</td>" +
						"<td width='180' align='center'>Tempo utilizado</td>" + 
						"<tr>" +
				"</table>"; 
				
				tabela+=
						"<table>" +
							"<tr>" +
								"<th rowspan='3' width='110'>"+ u.getNm_usuario()+"</td>" +
								"<td rowspan='3'><div style='width: 0px; height: 70px'; background-color: #000000' align='center'>&nbsp;</div></td>" +
					
								"<td width='180' height='35'>" +
									"<div class='azulEscuro' style='background-color:#006D92;  position: relative; bottom: 0; float: left; width: "+ ((listaValores.get(0)>0)?150:0) +"px; height: 20px ; margin: 1px'>&nbsp;</div>" +
								"</td>" +
					
								"<td width='30'>"+ listaValores.get(0) +"</td>" +
					
							"</tr>" +
					
							"<tr>" +
								"<td height='35'><div class='verdeEscuro' style='background-color:#097F00; position: relative; bottom: 0; float: left; width: "+ ((listaValores.get(1)>0)? (( listaValores.get(0)>0 ) ? ((listaValores.get(1)*150)/listaValores.get(0)):0) :0) +"px ; height: 20px; margin: 1px'>&nbsp;</div></td>" +
								"<td>"+ listaValores.get(1) +"</td>" +
							"</tr>" +
						"</table><br><br>";
			}
		} 
		
		tabela+= "<table>" +
					"<tr>" +
						"<th rowspan='3' width='110'>Todos colaboradores</td>" +
						"<td rowspan='3'><div style='width: 0px ; height: 70px; background-color: #000000;' align='center'>&nbsp;</div></td>" +
			
						"<td width='180' height='35'>" +
							"<div class='azulEscuro' style='background-color:#006D92; position: relative; bottom: 0; float: left; width: "+ ((somaTotalAtividades>0)?150:0) +"px; height: 20px'>&nbsp;</div>" +
						"</td>" +
			
						"<td width='30'>"+ somaTotalAtividades +"</td>" +
			
					"</tr>" +
			
					"<tr>" +
						"<td height='35'><div class='verdeEscuro' style='background-color:#097F00; position: relative; bottom: 0; float: left; width: "+ ((somaTotalExecucoes>0)? ((somaTotalExecucoes>0)?((somaTotalExecucoes*150)/somaTotalAtividades):0) :0) +"px; height: 20px; margin: 1px'>&nbsp;</div></td>" +
						"<td>"+ somaTotalExecucoes +"</td>" +
					"</tr>" +
				"</table><br><br>";
		
		tabela += criaLegenda();
		tabela += addItemLegenda("azulEscuro", "Tempo previsto (min)");
		tabela += addItemLegenda("verdeEscuro", "Tempo utilizado (min)");
		tabela += fechaLegenda();
		
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
	}
	
	@SuppressWarnings("unchecked")
	public String EmailPercentualAtividadesExecutadasPrazo(Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString ) {
		
		this.setDao(new GenericDAO());
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
			
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
		List<Usuario> listaUsuarios = this.getDao().getSession().createCriteria(Usuario.class).addOrder(Order.asc("nm_usuario")).list();
		List<List<?>> listaTotal = diaPorcentagemExecutadasPorPessoa(cd_usuario, cd_unidade, dataInicial.getTime(), dataFinal.getTime());
		 
		

		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
						"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
						"<p>Indicador: <font color='blue'> PERCENTUAL DE ATIVIDADES EXECUTADAS NO PRAZO</font></p>"+
						"<hr/>";
		
		 //listaTotal.get(0)  --> array com os dias
		 //listaTotal.get(1)  --> array com as porcentagens
		
		String imgAzul2 = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzul2.png");
		String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
		
		
		
		for(Usuario u: listaUsuarios){
		
			listaTotal = diaPorcentagemExecutadasPorPessoa(u.getCd_usuario(), cd_unidade,dataInicial.getTime(), dataFinal.getTime());
			
			if(	((ArrayList<Integer>)listaTotal.get(1)).size() >0 ){
				
				String strColunasDias="";
				String strColunasPerc="";
				String strColunasPorcentagem="";
				
				for(Integer perc: ((ArrayList<Integer>)listaTotal.get(1))){
					strColunasPorcentagem += "<td align='center'>"+ perc +" %</td>";
					strColunasPerc += "<td align='center' style='vertical-align:bottom'><div class='azul' style='background-color:#0098FF; width: 20px; height: "+((perc>0)?((new Float(perc)/100)*180):0) +"px' align='center'>";
					strColunasPerc += (perc>0)?"&nbsp;":"";
					strColunasPerc += "</div></td>";
				}
				
				for(String dias: ((ArrayList<String>)listaTotal.get(0))){
					strColunasDias += "<td align='center' width='50'>"+dias.split("/")[0] +"/"+dias.split("/")[1] +"</td>";
				}
				
				
				tabela+=""+
			
					"<table border='0'>"+
						"<tr>"+
						  "<th rowspan='5' width='110'>"+ u.getNm_usuario() +"</th>"+
						"</tr>"+
				
						"<tr>"+
							strColunasPorcentagem + 
						"</tr>"+

						"<tr>"+
							strColunasPerc + 
						"</tr>"+
						"<tr>"+
						  "<td align='center' colspan='"+((ArrayList<Integer>)listaTotal.get(1)).size()+"'><div style=' width: "+((ArrayList<Integer>)listaTotal.get(1)).size()*50+"px; height: 0px;' align='center'>&nbsp;</div></td>" +
						"</tr>"+
				
						"<tr>"+
							strColunasDias + 
						"</tr>"+
					"</table><br><br><br><br>";
			}
		}
		
		tabela += criaLegenda();
		tabela += addItemLegenda("azul", "Porcentagem executada no prazo");
		tabela += fechaLegenda();
		
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
		
	}
	
	@SuppressWarnings("unchecked")
	public String EmailNCSPorSetor( Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString ) {
		
		this.setDao(new GenericDAO());
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
			
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
		
		
		
		List<List<?>> listaTotal = ncsPorSetor(cd_unidade, dataInicial.getTime(), dataFinal.getTime());

		//listaTotal.get(0) --> listaSetores
		//listaTotal.get(1) --> lista de lista com 3 resultados

		//0 --> abertas
		//1 --> emAndamento
		//2 --> finalizadas
		
		String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
		String imgAzul = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzul.png");
		String imgLaranja = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLaranja.png");
		String imgVerdeEscura = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraVerdeEscura.png");
		
		
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
				"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
				"<p>Indicador: <font color='blue'> NÃO CONFORMIDADES POR SETOR</font></p>"+
				"<hr/>";
		
		Integer maior = 0;
		
		for(Integer i = 0; i < ((List<Setor>) listaTotal.get(0)).size(); i++){
			List<Integer> listaQtdes = ((List<Integer>) ((List<List<Integer>>) listaTotal.get(1)).get(i) );
			if(listaQtdes.get(0) > maior){
				maior = listaQtdes.get(0);
			}
			if(listaQtdes.get(1) > maior){
				maior = listaQtdes.get(1);			
			}
			if(listaQtdes.get(2) > maior){
				maior = listaQtdes.get(2);
			}
		}
		
		
		for(Integer i = 0; i < ((List<Setor>) listaTotal.get(0)).size(); i++){
			
			
			if(i==0){
				tabela += "<table>" +
						"<tr>"+
						    "<th rowspan='4' width='180' height='70'>"+ ((List<Setor>) listaTotal.get(0)).get(i).getNm_setor() +"</th>"+
						    "<th rowspan='"+ (((List<Setor>) listaTotal.get(0)).size()*5 - 1) +"' width='10'><div style='width: 0px; height: "+((List<Setor>) listaTotal.get(0)).size()*80+"px; background-color: #000000' align='top'>&nbsp;</div></th>"+
						  "</tr>";
			}else{
				
				tabela += "<tr>"+
							"<th colspan='4' align='left'><div style='height: 3px; width: 500px;' align='top'>&nbsp;</div></th>"+
						   "</tr>";
				
				tabela += "<tr>"+
						    "<th rowspan='4' width='180' height='70'>"+ ((List<Setor>) listaTotal.get(0)).get(i).getNm_setor() +"</th>"+
						  "</tr>";
			}
			
			
			List<Integer> listaQtdes = ((List<Integer>) ((List<List<Integer>>) listaTotal.get(1)).get(i) );
			
			//listaQtdes.get(0);
			//listaQtdes.get(1);
			//listaQtdes.get(2);
			
			tabela += "<tr>"+
						  	"<td align='left' width='300'><div class='azul' style='background-color:#0098FF; position: relative; bottom: 0; float: left; width: "+ ((listaQtdes.get(0)>0)?((listaQtdes.get(0)*300)/maior):0) +"px; height: 20px; margin: 1px;'>&nbsp;</div></td>"+
						  	"<td align='center'>"+ listaQtdes.get(0) +"</td>"+
						"</tr>"+
					   "<tr>"+
					   		"<td align='left' width='300'><div class='laranja' style='background-color:#FF9800; position: relative; bottom: 0; float: left; width: "+ ((listaQtdes.get(1)>0)?((listaQtdes.get(1)*300)/maior):0) +"px; height: 20px; margin: 1px;'>&nbsp;</div></td>"+
					   		"<td align='center'>"+ listaQtdes.get(1) +"</td>"+
					   "</tr>"+
					   "<tr>"+
					   		"<td align='left' width='300'><div class='verdeEscuro' style='background-color:#097F00; position: relative; bottom: 0; float: left; width: "+ ((listaQtdes.get(2)>0)?((listaQtdes.get(2)*300)/maior):0) +"px; height: 20px; margin: 1px;'>&nbsp;</div></td>"+
					   		"<td align='center'>"+ listaQtdes.get(2) +"</td>"+
					   "</tr>";
			
		}
		
		tabela += "</table><br><br><br>";

		tabela += criaLegenda();
		tabela += addItemLegenda("azul", "Nc's abertas");
		tabela += addItemLegenda("laranja", "Nc's em andamento");
		tabela += addItemLegenda("verdeEscuro", "Nc's finalizadas");
		tabela += fechaLegenda();
		
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
	}
	
	@SuppressWarnings("unchecked")
	public String EmailDesempenhoEmAuditorias( Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString  ) {
		this.setDao(new GenericDAO());
		
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
					
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
				
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
					"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
					"<p>Indicador: <font color='blue'>DESEMPENHO EM AUDITORIAS</font></p>"+
					"<hr/>";
		 
		String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
		String imgAzul2 = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzul2.png");
		
		List<Cronograma_auditoria> listaCronogramas = (List<Cronograma_auditoria>) this.getDao().getSession().createCriteria(Cronograma_auditoria.class).list();
		for(Cronograma_auditoria crono : listaCronogramas){
			List<Conclusao_auditoria> listaConclusoes = desempenhoAuditorias( crono , dataInicial.getTime() , dataFinal.getTime() );
			
			String strColunasDias="";
			String strColunasPerc="";
			String strColunasPorcentagem="";
		
//			for(Conclusao_auditoria conclusao : listaConclusoes){
//				strColunasPorcentagem += "<td align='center'>"+ conclusao.getNr_percentual_atendimento() +" %</td>";
//				strColunasPerc += "<td align='center' style='vertical-align:bottom'><div class='azul' style='position: relative; bottom: 0; float: left; margin:1px; width: 20px; height: "+((conclusao.getNr_percentual_atendimento()>0)?((new Float(conclusao.getNr_percentual_atendimento())/100)*180):0) +"px' align='center'/></td>";
//				strColunasDias += "<td align='center' width='50'>"+ new SimpleDateFormat("dd/MM").format(conclusao.getDt_conclusao_auditoria())  +"</td>";
//			}
			
//			DESCOMENTAR PARA TESTES E COMENTAR O FOR ACIMA			
			for(Integer i=0;i<5;i++){
				GregorianCalendar dataRandom = (GregorianCalendar) new GregorianCalendar().getInstance();
				
				Conclusao_auditoria conclusao = new Conclusao_auditoria();
				conclusao.setNr_percentual_atendimento( ((Double) Math.floor( Math.random()*100 )).intValue() );
				
				dataRandom.set(Calendar.DATE, dataRandom.get(Calendar.DATE) + i );
				conclusao.setDt_conclusao_auditoria( dataRandom.getTime() );
				
				strColunasPorcentagem += "<td align='center'>"+ conclusao.getNr_percentual_atendimento() +" %</td>";
				strColunasPerc += "<td align='center' style='vertical-align:bottom'><div style='position: relative; bottom: 0; float: left; margin:1px; width: 20px; height: "+((conclusao.getNr_percentual_atendimento()>0)?((new Float(conclusao.getNr_percentual_atendimento())/100)*180):0) +"' align='center' class='azul'>&nbsp;</div></td>";
				strColunasDias += "<td align='center' width='50'>"+ new SimpleDateFormat("dd/MM").format(conclusao.getDt_conclusao_auditoria())  +"</td>";
				
				listaConclusoes.add(conclusao);
			}
			
			tabela+=""+
		
				"<table border='0'>"+
					"<tr>"+
					  "<th rowspan='5' width='110'>"+ crono.getNm_cronograma_auditoria() +"</th>"+
					"</tr>"+
			
					"<tr>"+
						strColunasPorcentagem + 
					"</tr>"+

					"<tr>"+
						strColunasPerc + 
					"</tr>"+
					"<tr>"+
					  "<td align='center' colspan='"+listaConclusoes.size()+"'><div style='width: "+listaConclusoes.size()*50+"px; height: 0px; background-color: #000000' align='top'>&nbsp;</div></td>" +
					"</tr>"+
			
					"<tr>"+
						strColunasDias + 
					"</tr>"+
				"</table><br><br><br><br>";
			
			
		}
		
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
		
	}

	public String EmailNCSPorPeriodo( Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString  ) {
		this.setDao(new GenericDAO());
		 
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
					
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
				
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
					"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
					"<p>Indicador: <font color='blue'> NÃO CONFORMIDADES POR PERÍODO</font></p>"+
					"<hr/>" +
					"<table>";
		 
		String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
		String imgVerdeEscura = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraVerdeEscura.png");
		
		Integer maior = 0 ;
		List<List<?>> listaDiasNcs = NcsPorPeriodo( dataInicial.getTime() , dataFinal.getTime() );
		
		for( Integer ind = 0; ind < ((List<Integer>) listaDiasNcs.get(1)).size() ; ind++){
			if( ((List<Integer>) listaDiasNcs.get(1)).get(ind) > maior){
				maior = ((List<Integer>) listaDiasNcs.get(1)).get(ind);
			}
		}
		
		
		for( Integer ind = 0; ind < ((List<String>) listaDiasNcs.get(0)).size() ; ind++){
			
			
			String  dia  = ((List<String>) listaDiasNcs.get(0)).get(ind);
			Integer qtde = ((List<Integer>) listaDiasNcs.get(1)).get(ind);
			
			tabela += "<tr>" +
						 "<td height='35' align='left'>"+ dia +"</td>" +
						 ((ind==0)?"<td rowspan='"+ listaDiasNcs.get(0).size()+"'><div style='height: "+ listaDiasNcs.get(0).size()*35+"px; width: 0px; background-color: #000000' align='top'>&nbsp;</div></td>":"") +
						 "<td align='left'><div class='verdeEscuro' style='background-color:#097F00; position: relative; bottom: 0; float: left; width: "+ ((qtde>0)?((qtde*200)/maior):0) +"px; height: 20px; margin: 1px;'>&nbsp;</div></td>" +
						 "<td align='center'>"+ qtde +"</td>" +
					  "</tr>";	
		}
		
		
		tabela += "</table><br><br><br><br>";
		
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
		
	}

	public String EmailNivelDeNCS( Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString  ) {
		this.setDao(new GenericDAO());
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
					
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
				
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
					"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
					"<p>Indicador: <font color='blue'> NÍVEL DE NÃO CONFORMIDADES POR PROGRAMA DE QUALIDADE</font></p>"+
					"<hr/>";
		 
		List<BigDecimal> listaResultados = nivelNcs( cd_unidade, dataInicial.getTime() , dataFinal.getTime() );
		//listaResultados( 0 ) == qtdeExecucoes 
		//listaResultados( 1 ) == qtdeExecucoesNaoConfomes
		//listaResultados( 2 ) == qtdeRespostas
		//listaResultados( 3 ) == qtdeRespostasNaoConformes
		
//		tabela += "<table>"+
//					   "<tr>" +
//					   		"<td align='center'><div style='background: linear-gradient(to right, #0cfc24 0%,#fff200 50%,#ff0000 100%)'>ASDFSAASFSFAD</div></td>" +
//					   	"</tr>" +
//						"<tr>" +
//						 	"<td align='center'><div style='background: linear-gradient(to right, #ff0000 0%,#0000ff 100%)'>WQERE231243</div></td>" +
//						"</tr>" +
//						"<tr>" +
//						 	"<td align='center'><div style='background: linear-gradient(to right, #00ff00 0%,#ffff00 50%,#00ffff 100%)'>ASDFSD AASD</div></td>" +
//						"</tr>" +
//						"<tr>" +
//						 	"<td align='center'><div style='background: linear-gradient(to right, #c6c6c6 0%,#bbbbbb 50%,#ff00ff 100%)'>9876565879</div></td>" +
//						"</tr>"+	
//				  "</table>";
		
		
		Double qtdeExecucoes  			 = new Double( listaResultados.get( 2 ).doubleValue() ); 
		Double qtdeExecucoesNaoConfomes  = new Double( listaResultados.get( 3 ).doubleValue() ); 
		Double qtdeRespostas 			 = new Double( listaResultados.get( 4 ).doubleValue() );
		Double qtdeRespostasNaoConformes = new Double( listaResultados.get( 5 ).doubleValue() );
		
		
		Double angulo1 = (listaResultados.get( 3 ).doubleValue()>0)?((listaResultados.get( 2 ).doubleValue()>0)?((listaResultados.get( 3 ).doubleValue()*180)/listaResultados.get( 2 ).doubleValue()):0):0;
		Double angulo2 = (listaResultados.get( 5 ).doubleValue()>0)?((listaResultados.get( 4 ).doubleValue()>0)?((listaResultados.get( 5 ).doubleValue()*180)/listaResultados.get( 4 ).doubleValue()):0):0;
		
		angulo1 = new BigDecimal(angulo1).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		angulo2 = new BigDecimal(angulo2).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		
//		tabela += ""+
//		"<div style='position:relative; top:0px; left:0px;'>"+
//			"<img src='data:image/png;base64,"+urlFinal+"angulos/velocimetro.png' border=0 />"+
//				"<div style='position:absolute; top:-200px; left:0px;'>"+
//					"<img src='data:image/png;base64,"+urlFinal+"angulos/angulo%20("+ angulo.toString() +").png' border=0 />" +
//				"</div>" +
//		"</div>";
		
		
		//DESCOMENTAR PARA TESTES
		//listaResultados.set(3, new BigDecimal( 20 ) );
		//listaResultados.set(5, new BigDecimal( 235 ) );
		
		String velocimetro1 = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/velocimetros/velocimetro ("+ String.valueOf( ((int) Math.round(angulo1)) ) +").png");
		String velocimetro2 = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/velocimetros/velocimetro ("+ String.valueOf( ((int) Math.round(angulo2)) ) +").png");
		
		tabela += "<br><br><br>"+
		"<table border='0'>" +

			"<tr>" +
				"<td width='250' align='center'><b>TOTAL DE EXECUÇÕES NC'S</b></td>" +
				"<td align='center'><b>TOTAL DE RESPOSTAS NC'S</b></td>" +
			"</tr>" +
			
			"<tr>" +
				"<td align='center' width='230'><img src='data:image/png;base64,"+velocimetro1+"' border=0></td>" +
				"<td align='center' width='230'><img src='data:image/png;base64,"+velocimetro2+"' border=0></td>" +
			"</tr>" +
	
			"<tr>" +
				"<td align='center'>Total de execuções........ "+listaResultados.get( 2 )+"</td>" +
				"<td align='center'>Total de respostas........ "+listaResultados.get( 4 )+"</td>" +
			"</tr>" +
	
			"<tr>" +
				"<td align='center'>Total de execuções Nc's... <font color='red'>"+listaResultados.get( 3 )+"</font></td>" +
				"<td align='center'>Total de respostas Nc's... <font color='red'>"+listaResultados.get( 5 )+"</font></td>" +
			"</tr>" +

		"</table>";
		
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
	}
	

	public String EmailNCSPorProgQualidade( Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString  ) {
		this.setDao(new GenericDAO());
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
					
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
					"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
					"<p>Indicador: <font color='blue'> NÃO CONFORMIDADES POR PROGRAMA DE QUALIDADE</font></p>"+
					"<hr/><br><br><br>";
		
		String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
		String imgLaranja = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLaranja.png");
		
		Integer maior=0;
		List<List<?>> listaGeral = NcsPorProgramaQualidade(dataInicial.getTime(), dataFinal.getTime() );
		List<Programa_qualidade> listaProgramas =((List<Programa_qualidade>)listaGeral.get(0));
		List<Integer> listaQtdes =((List<Integer>)listaGeral.get(1));
		String strAux ="[";
		for(Integer i=0;i<listaQtdes.size();i++){
			
			//DESCOMENTAR PARA TESTES
			//listaQtdes.set(i, (int) (1 * Math.floor(Math.random()*12)) );
			
			if(listaQtdes.get(i) > maior){
				maior = listaQtdes.get(i);
			}
			strAux+=listaQtdes.get(i).toString();
			strAux+=(i==(listaQtdes.size()-1))?"":",";
		}
		strAux+="]";
		System.out.println( strAux );
		tabela += "<table>";
		
		
		for(Integer e=0;e<listaProgramas.size();e++){
			
			tabela += "<tr height='55'><td width='250'>" + listaProgramas.get(e).getNm_programa_qualidade()+"</td>";
			tabela += ((e==0)?"<td rowspan='"+listaProgramas.size()+"'><div style='width: 0px; height: "+listaProgramas.size()*55+"px; background-color: #000000' align='middle'>&nbsp;</div></td>":"");
			
			if(listaQtdes.get(e)>0 && maior>0){
				tabela += "<td width='250'><div class='laranja' style='background-color:#FF9800; height: 30px; position: relative; bottom: 0; float: left; width: " + ((new Double( listaQtdes.get(e) )*250)/new Double(maior)) +"px; margin: 1px'>&nbsp;</div></td>";
			}else{
				tabela += "<td width='250'></td>";
			}
			
			tabela += "<td width='30'>"+listaQtdes.get(e).toString()+"</td></tr>";
		}
		
		tabela += "</table><br><br><br>";
		
		tabela += criaLegenda();
		tabela += addItemLegenda("laranja", "Quantidade de não conformidades");
		tabela += fechaLegenda();
		
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
	}
	 
	// ******************************************
	// ***********  HELPDESK ********************
	@SuppressWarnings("unchecked")
	public String EmailTotalChamados( Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString  ) {
		this.setDao(new GenericDAO());
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
					
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
				
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
					"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
					"<p>Indicador: <font color='blue'> CHAMADOS CADASTRADOS NO PERÍODO( TODAS AS UNIDADES )</font></p>"+
					"<hr/><br><br><br>";
		
		Unidade u = ( Unidade ) this.getDao().getSession().createCriteria(Unidade.class).add(Restrictions.eq("cd_unidade", cd_unidade)).uniqueResult();
		List<Integer> listaTotal = chamadosTotalAnalisadosNaoAnalisados(dataInicial.getTime(), dataFinal.getTime(), u);
		
		
		
		tabela += "<table border='0'>";
		tabela += 	"<tr>";
		tabela += 		"<td width='220'>Total de chamados cadastrados</td>";
		tabela += 		"<td width='220'>";
		tabela += 		(listaTotal.get(0)>0)?"<div style='background-color:#006D92; position: relative; bottom: 0; float: left; height: 30px; width: 220px; margin: 1px'>&nbsp;</div>":"";
		
		tabela += 		"</td>";
		tabela += 		(listaTotal.get(0)>0)?"<td width='80' align='center'>"+ listaTotal.get(0) +"</td>":"<td width='80' align='center'>0</td>";
		tabela += 	"</tr>";
		
		tabela += 	"<tr>";
		tabela += 		"<td width='220'>Total de chamados analisados</td>";
		tabela += 		"<td width='220'>";
		tabela += 			(listaTotal.get(1)>0)? "<div style='background-color:#65CC32; position: relative; bottom: 0; float: left; height: 30px; width: "+((listaTotal.get(1)!= 0 && listaTotal.get(0)!= 0)?((listaTotal.get(1) * 220)/ listaTotal.get(0)):0)+"px; margin: 1px'>&nbsp;</div>":"";
		tabela += 		"</td>";
		tabela += 		"<td width='80' align='center'>"+listaTotal.get(1)+"</td>";
		tabela += 	"</tr>";

		tabela += 	"<tr>";
		tabela += 		"<td width='220'>Total de chamados não analisados</td>";
		tabela += 		"<td width='220'>";
		tabela += 			(listaTotal.get(2)>0)? "<div style='background-color:#FF9800; position: relative; bottom: 0; float: left; height: 30px; width: "+((listaTotal.get(2)!= 0 && listaTotal.get(0)!= 0)?((listaTotal.get(2) * 220)/ listaTotal.get(0)):0)+"px; margin: 1px'>&nbsp;</div>":"";
		tabela += 		"</td>";
		tabela += 		"<td width='80' align='center'>"+listaTotal.get(2)+"</td>";
		tabela += 	"</tr>";

		tabela += "</table>";
		
		if( !retornaString ){
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head></head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
	}
	
	@SuppressWarnings("unchecked")
	public String EmailChamadosAbertosPorAtendente( Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString  ) {
		this.setDao(new GenericDAO());
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
					
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
				
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
					"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
					"<p>Indicador: <font color='blue'> CHAMADOS ABERTOS ADOTADOS POR ATENDENTE( TODAS AS UNIDADES )</font></p>"+
					"<hr/><br><br><br>";
		
		
		String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
		String imgLaranja = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLaranja.png");
		String imgVerde = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraVerde.png");
		String imgAzul = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzul.png");
		
		Unidade u = ( Unidade ) this.getDao().getSession().createCriteria(Unidade.class).add(Restrictions.eq("cd_unidade", cd_unidade)).uniqueResult();
		
		List<List<?>> listaTotal = chamadosAbertosPorAtendente(dataInicial.getTime(), dataFinal.getTime(), u);
		Integer maior = 0;
		for(Integer e = 0 ; e < ((List<Integer> )listaTotal.get(1)).size() ; e++){
			if(((List<Integer> )listaTotal.get(1)).get(e) > maior){
				maior = ((List<Integer> )listaTotal.get(1)).get(e);
			}
		}
		
		tabela += "<table border='0'>";
		
		for(Integer i = 0 ; i < ((List<String> )listaTotal.get(0)).size() ; i++){
			
			String  nome = ((List<String> )listaTotal.get(0)).get(i);
			Integer qtde = ((List<Integer> )listaTotal.get(1)).get(i);
			Integer cod = ((List<Integer> )listaTotal.get(2)).get(i);
			
			tabela += "<tr height='45'>";
			tabela += "<td width='250'>" + nome + "</td>";
			tabela += 	(i==0)?"<td rowspan='"+ ((List<String> )listaTotal.get(0)).size() +"'><div style='width: 0px; height: "+45*(listaTotal.get(0).size()+1)+"px; background-color: #000000' align='middle'>&nbsp;</div></td>":"";
			if( qtde > 0 && maior > 0){
				if(cod == null){
					tabela += "<td width='250'><div class='verde' style='background-color:#65CC32; position: relative; bottom: 0; float: left; height: 30px; width: " +  ((new Double( qtde )*250)/new Double(maior)) +"px; margin: 1px'>&nbsp;</div></td>";
				}else{
					tabela += "<td width='250'><div class='laranja' style='background-color:#FF9800; position: relative; bottom: 0; float: left; height: 30px; width: " +  ((new Double( qtde )*250)/new Double(maior)) +"px; margin: 1px'>&nbsp;</div></td>";
				}
				
			}else{
				tabela += "<td width='250'></td>";
			}
			
			tabela += "<td width='80' align='center'>" + qtde + "</td>";
			tabela += "</tr>";
			
		}
		
		tabela += "</table>";
		
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
	}
	
	
	public String EmailStatusGeralOcorrencias( Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString  ) {
		this.setDao(new GenericDAO());
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
					
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
				
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
					"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
					"<p>Indicador: <font color='blue'> STATUS GERAL CHAMADOS ( TODAS AS UNIDADES )</font></p>"+
					"<hr/><br><br><br>";
		
		String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
		String imgLaranja = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLaranja.png");
		String imgVerde = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraVerde.png");
		String imgAzul = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzul.png");
		
		Unidade u = ( Unidade ) this.getDao().getSession().createCriteria(Unidade.class).add(Restrictions.eq("cd_unidade", cd_unidade)).uniqueResult();
		
		List<List<?>> listaTotal =  chamadosPorStatusFiltrado(dataInicial.getTime(), dataFinal.getTime(), u);
		Integer qtdeTotal = ((Integer) listaTotal.get(1).get(0)) + ((Integer) listaTotal.get(1).get(1));
		//qtde de abertas = listaTotal.get(1).get(0)
		//qtde de finalizadas = listaTotal.get(1).get(0)
		
		
		
		tabela += "<table border='0'>" +
					"<tr height='45'>" +
						"<td>Abertas</td>" +
						"<td rowspan='4'><div style='width: 0px; height: 135px; background-color: #000000' align='middle'>&nbsp;</div></td>";
		
						if( ((Integer) listaTotal.get(1).get(0)) > 0){
							tabela += "<td><div class='laranja' style='background-color:#FF9800; position: relative; bottom: 0; float: left; height: 30px; width: " + ((new Double( ((Integer) listaTotal.get(1).get(0)) )*250)/new Double(qtdeTotal)) +"px'>&nbsp;</div></td>";
						}else{
							tabela += "<td></td>";
						}
		
		tabela += 		"<td>"+((Integer) listaTotal.get(1).get(0))+"</td>" +
					"</tr>" +
					
					"<tr height='45'>" +
						"<td>Finalizadas</td>";
						if( ((Integer) listaTotal.get(1).get(1)) > 0){
							tabela += "<td><div class='verde' style='background-color:#65CC32; position: relative; bottom: 0; float: left; height: 30px; width: " +  ((new Double( ((Integer) listaTotal.get(1).get(1)) )*250)/new Double(qtdeTotal)) +"px'>&nbsp;</div></td>";
						}else{
							tabela += "<td></td>";
						}
						
		tabela += 		"<td>"+((Integer) listaTotal.get(1).get(1))+"</td>" +
					"</tr>" +
				
					"<tr height='45'>" +
						"<td>Total</td>";
						
						if( qtdeTotal > 0){
							tabela += "<td><div class='azul' style='background-color:#0098FF; position: relative; bottom: 0; float: left; height: 30px; width: 250'>&nbsp;</div></td>";
						}else{
							tabela += "<td width='250'></td>";
						}
		tabela += 		"<td>"+ qtdeTotal.toString() +"</td>" +
					"</tr>" +
					
				"</table><br><br><br>";

		tabela += criaLegenda();
		tabela += addItemLegenda("laranja", "Quantidades de chamados abertos");
		tabela += addItemLegenda("verde", "Quantidades de chamados finalizados");
		tabela += addItemLegenda("azul", "Quantidades total de chamados");
		tabela += fechaLegenda();
		
		
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
		
	}
	
	public String EmailStatusGeralOcorrenciasPorPeriodo( Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString  ) {
		this.setDao(new GenericDAO());
		
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
					
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
				
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
					"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
					"<p>Indicador: <font color='blue'> STATUS DOS CHAMADOS ( TODAS AS UNIDADES )</font></p>"+
					"<hr/><br><br><br>";
		
		Unidade u = ( Unidade ) this.getDao().getSession().createCriteria(Unidade.class).add(Restrictions.eq("cd_unidade", cd_unidade)).uniqueResult();
		
		List<List<?>> listaTotal =  chamadosPorStatusEPeriodo( dataInicial.getTime(), dataFinal.getTime() , u);
		Integer qtdeTotal = 0;
		
		for(Integer i=0 ; i< listaTotal.get(1).size() ; i++){
			qtdeTotal += ((Integer) listaTotal.get(1).get(i));	
		}
		
		
		
		String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
		String imgLaranja = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLaranja.png");
		String imgVerde = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraVerde.png");
		String imgAzul = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzul.png");
		String imgAzulEscura = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzulEscura.png");
		
		
		tabela += "<table border='0'>";
		
		for(Integer e=0 ; e< listaTotal.get(0).size() ; e++){
			
			tabela += "<tr height='45'>";
			tabela += 	"<td>"+listaTotal.get(0).get(e).toString()+"</td>";
			tabela += 	(e==0)?"<td rowspan='8'><div style='width: 0px; height: "+45*(listaTotal.get(0).size()+1)+"px; background-color: #000000' align='middle'>&nbsp;</div></td>":"";
			if( ((Integer) listaTotal.get(1).get(e)) > 0){
				if(e < 4){
					tabela += "<td width='250'><div class='laranja' style='background-color:#FF9800; height: 30px; width: "+ ((new Double( ((Integer) listaTotal.get(1).get(e)) )*250)/new Double(qtdeTotal)) +"px; position: relative; bottom: 0; float: left; margin: 1px;'>&nbsp;</div></td>";
				}else{
					tabela += "<td width='250'><div class='verde' style='background-color:#65CC32; height: 30px; width: "+ ((new Double( ((Integer) listaTotal.get(1).get(e)) )*250)/new Double(qtdeTotal)) +"px; position: relative; bottom: 0; float: left; margin: 1px;'>&nbsp;</div></td>";
				}
			}else{
				tabela += 	"<td width='250'></td>";
			}
			tabela += 	"<td>"+listaTotal.get(1).get(e).toString()+"</td>";
			tabela += "</tr>";
			
		}
		
		tabela += "<tr height='45'>";
		tabela += "<td>Total</td>";
		tabela += (qtdeTotal>0) ? "<td><div class='azulEscuro' style='background-color:#006D92; height: 30px; width: 250px; position: relative; bottom: 0; float: left; margin: 1px;'>&nbsp;</div></td>" : "<td></td>";
		tabela += "<td>"+qtdeTotal.toString()+"</td>";
		tabela += "</tr>";
		
		tabela += "</table><br><br><br>";
			
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
	}
	
	public String EmailOcorrenciasPorMotivoAbertura( Integer cd_usuario, Integer cd_unidade, GregorianCalendar data, Integer periodo, Boolean retornaString  ) {
		this.setDao(new GenericDAO());
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
					
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
				
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
					"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
					"<p>Indicador: <font color='blue'> CHAMADOS POR CLASSIFICAÇÃO E NATUREZA ( TODAS AS UNIDADES )</font></p>"+
					"<hr/><br><br><br>";
		
		String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
		String imgLaranja = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLaranja.png");
		String imgVerde = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraVerde.png");
		String imgAzul = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzul.png");

		Unidade u = ( Unidade ) this.getDao().getSession().createCriteria(Unidade.class).add(Restrictions.eq("cd_unidade", cd_unidade)).uniqueResult();
		
		List<List<?>> listaTotal = chamadosPorMotivoAberturaFiltrado(dataInicial.getTime(), dataFinal.getTime() , u);
		Integer qtdeTotal = 0;
		for(Integer i = 0 ; i< listaTotal.get(0).size() ; i++){
			qtdeTotal += ((Integer)listaTotal.get(1).get(i));
		}
		
		String tabelaNaturezas="";
		String tabelaAux="";
		
		tabela += "<table border='0'>";
		for(Integer i = 0 ; i< listaTotal.get(0).size() ; i++){
			
			tabela += "<tr height='45'>";
			tabela += 	"<td>"+listaTotal.get(0).get(i).toString()+"</td>";
			tabela += 	(i==0)?"<td rowspan='"+listaTotal.get(0).size()+"'><div style='width: 0px; height: "+45*(listaTotal.get(0).size())+"px; background-color: #000000' align='middle'>&nbsp;</div></td>":"";
			if( ((Integer) listaTotal.get(1).get(i)) > 0){
				tabela += 	"<td width='250'><div class='azul' style='background-color:#0098FF; height: 30px; width: "+ ((new Double( ((Integer) listaTotal.get(1).get(i)) )*250)/new Double(qtdeTotal)) +"px; position: relative; bottom: 0; float: left; margin: 1px;'>&nbsp;</div></td>";
			}else{
				tabela += 	"<td width='250'></td>";
			}
			tabela += 	"<td>"+listaTotal.get(1).get(i).toString()+"</td>";
			tabela += "</tr>";
			
			if(((Integer)listaTotal.get(1).get(i)) >0){
				
				List<List<?>> listaChamadosPorClassificacao =  chamadosPorMotivoAberturaClassificacao(((String) listaTotal.get(0).get(i)), dataInicial.getTime(), dataFinal.getTime() , null);
				tabelaAux = "";
				tabelaAux += "<table border ='0'>";
				tabelaAux += 	"<tr>";
				tabelaAux += 		"<td align='center' colspan='4'><b>"+((String) listaTotal.get(0).get(i))+"</b></td>";
				tabelaAux += 	"</tr>";
				
				for(Integer e = 0 ; e < listaChamadosPorClassificacao.get(0).size() ; e++){
					//natureza = listaChamadosPorClassificacao.get(0).get(e)
					//qtde = listaChamadosPorClassificacao.get(1).get(e)
					
					tabelaAux += 	"<tr height='45'>";
					tabelaAux += 		"<td width='210'>"+listaChamadosPorClassificacao.get(0).get(e).toString()+"</td>";
					
					tabelaAux += 	(e==0)?"<td rowspan='"+listaChamadosPorClassificacao.get(0).size()+"'><div style='width: 0px; height: "+45*(listaChamadosPorClassificacao.get(0).size())+"px; background-color: #000000' align='middle'>&nbsp;</div></td>":"";
					
					tabelaAux += 		(((Integer) listaChamadosPorClassificacao.get(1).get(e)) > 0)?"<td width='250'><div class='azul' style='background-color:#0098FF; height: 30px; width: "+ ((new Double( ((Integer) listaChamadosPorClassificacao.get(1).get(e)) )*250)/new Double( ((Integer)listaTotal.get(1).get(i)) )) +"px; position: relative; bottom: 0; float: left; margin: 1px;'>&nbsp;</div></td>":"<td width='250'></td>";
					tabelaAux += 		"<td>"+listaChamadosPorClassificacao.get(1).get(e)+"</td>";
					tabelaAux += 	"</tr>";
					
				}
				
				tabelaAux += "</table><br><br>";
				tabelaNaturezas += tabelaAux;
			}
		}
		
		tabela += "</table><br><br><br>";
		tabela += tabelaNaturezas;
		
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
		
	}
	
	public String EmailOcorrenciasPorUnidade( Integer cd_usuario, GregorianCalendar data, Integer periodo, Boolean retornaString  ) {
		this.setDao(new GenericDAO());
		
		GregorianCalendar dataInicial = getDataInicialDia();
		GregorianCalendar dataFinal = getDataFinalDia();
					
		dataInicial.set(Calendar.DATE, data.get(Calendar.DATE) - periodo);
		dataFinal.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
				
		
		String tabela = "<p>Período Analisado: <font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataInicial.getTime())+"</font> à " +
					"<font color='blue'>"+ new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataFinal.getTime()) +"</font></p>"+
					"<p>Indicador: <font color='blue'>CHAMADOS POR UNIDADE E SETOR</font></p>"+
					"<hr/><br><br><br>";
		
		List<List<?>> listaTotal =  chamadosPorUnidadePrazo(dataInicial.getTime(), dataFinal.getTime());
		Integer qtdeTotal = 0;
		String barraLateral = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLateral.png");
		String imgLaranja = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraLaranja.png");
		String imgVerde = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraVerde.png");
		String imgAzul = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzul.png");
		
		for(Integer i = 0 ; i< listaTotal.get(0).size() ; i++){
			qtdeTotal += ((Integer)listaTotal.get(1).get(i)); 
		}
		
	
		String tabelaSetores="";
		String tabelaAux="";
		
		tabela += "<table border='0'>";
		for(Integer i = 0 ; i< listaTotal.get(0).size() ; i++){
			
			tabela += "<tr height='45'>";
			tabela += 	"<td>"+listaTotal.get(0).get(i).toString()+"</td>";
			tabela += 	(i==0)?"<td rowspan='"+listaTotal.get(0).size()+"'><div style='width: 0px; height: "+45*(listaTotal.get(0).size())+"px; background-color: #000000' align='middle'>&nbsp;</div></td>":"";
			if( ((Integer) listaTotal.get(1).get(i)) > 0){
				tabela += 	"<td width='250'><div class='azul' style='background-color:#0098FF; height: 30px; width: "+ ((new Double( ((Integer) listaTotal.get(1).get(i)) )*250)/new Double(qtdeTotal)) +"px; position: relative; bottom: 0; float: left; margin: 1px;'>&nbsp;</div></td>";
			}else{
				tabela += 	"<td width='250'></td>";
			}
			tabela += 	"<td>"+listaTotal.get(1).get(i).toString()+"</td>";
			tabela += "</tr>";
			
			if(((Integer)listaTotal.get(1).get(i)) >0){
				
				List<List<?>> listaChamadosPorSetor =  chamadosPorSetorDaUnidade( ((String) listaTotal.get(0).get(i)) , dataInicial.getTime(), dataFinal.getTime() );
				
				if(listaChamadosPorSetor.get(0).size()>0 && listaChamadosPorSetor.get(1).size()>0 ){
				
					tabelaAux = "";
					tabelaAux += "<table border ='0'>";
					tabelaAux += 	"<tr>";
					tabelaAux += 		"<td align='center' colspan='5'><b>"+((String) listaTotal.get(0).get(i))+"</b></td>";
					tabelaAux += 	"</tr>";
					
					for(Integer e = 0 ; e < listaChamadosPorSetor.get(0).size() ; e++){
				
						tabelaAux += 	"<tr height='45'>";
						tabelaAux += 		"<td width='210'>"+listaChamadosPorSetor.get(0).get(e).toString()+"</td>";
						
						tabelaAux += 	(e==0)?"<td rowspan='"+listaChamadosPorSetor.get(0).size()+"'><div style='width: 0px; height: "+45*(listaChamadosPorSetor.get(0).size())+"px; background-color: #000000' align='middle'>&nbsp;</div></td>":"";
						
						tabelaAux += 		(((Integer) listaChamadosPorSetor.get(1).get(e)) > 0)?"<td width='250'><div class='azul' style='background-color:#0098FF; height: 30px; width: "+ ((new Double( ((Integer) listaChamadosPorSetor.get(1).get(e)) )*250)/new Double( ((Integer)listaTotal.get(1).get(i)) )) +"px; position: relative; bottom: 0; float: left; margin: 1px;'>&nbsp;</div></td>":"<td width='250'></td>";
						tabelaAux += 		"<td>"+listaChamadosPorSetor.get(1).get(e)+"</td>";
						
						Double valor = new Double(0);
						if( ((Integer)listaTotal.get(1).get(i)) > 0 && ((Integer)listaChamadosPorSetor.get(1).get(e)) > 0){
							valor = new Double( ((Integer)listaChamadosPorSetor.get(1).get(e) * 100)/((Integer)listaTotal.get(1).get(i)));
						}
						
						tabelaAux += 		"<td>"+valor+" %</td>";
						tabelaAux += 	"</tr>";
						
					}
					
					tabelaAux += "</table><br><br>";
					tabelaSetores += tabelaAux;
				}
			}
		}
		
		tabela += "</table><br><br><br>";
		tabela += tabelaSetores;
		
		if( !retornaString ){
			//tabela = susbtituiAcentosHTML( tabela );
			Helpdesk_notificador.enviaEmailParaPessoa(cd_usuario, "INDICADOR " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date() ), "<html>" +
    				"<head>" +
//    				"<style> div.verdeEscuro{ background-color:#097F00; }"+ 
//	        		"div.azulEscuro{ background-color:#006D92; }"+
//	        		"div.azul{ background-color:#0098FF; }"+
//	        		"div.verde{ background-color:#65CC32; }"+
//	        		"div.vermelho{ background-color:#FF0800; }"+
//	        		"div.laranja{ background-color:#FF9800; }"+
//	        		"div{ background-color: #aeaeae; }"+
//	        		"</style>"+
    				"</head><body>" + tabela +"</body></html>");
		}
			
		return tabela;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> indiceNcEMetasPorIndicador( List<List<?>> listaItens , Indicador_customizado indicador , Date dataInicial , Date dataFinal , Integer cd_tipo_turno) {
		
		this.setDao(new GenericDAO());
		Tipo_turno tipo_turno = (Tipo_turno) this.getDao().getSession().createCriteria(Tipo_turno.class).add(Restrictions.eq("cd_tipo_turno", cd_tipo_turno)).uniqueResult();
		GregorianCalendar dataInicialGC = (GregorianCalendar) new GregorianCalendar().getInstance();
		GregorianCalendar dataFinalGC = (GregorianCalendar) new GregorianCalendar().getInstance();
		dataInicialGC.setTime(dataInicial);
		dataFinalGC.setTime(dataFinal);
		
		GregorianCalendar dataInicialAux = (GregorianCalendar) new GregorianCalendar().getInstance();
		GregorianCalendar dataFinalAux = (GregorianCalendar) new GregorianCalendar().getInstance();
		dataInicialAux.setTime( dataInicial );
		dataFinalAux.setTime(dataInicial);
		dataFinalAux.set(Calendar.DATE, dataFinalAux.get(Calendar.DATE)+1);
		
		System.out.println("Data Inicial: " + new SimpleDateFormat("dd/MM/yyy HH:mm").format(dataInicialAux.getTime()));
		System.out.println("Data Final: " + new SimpleDateFormat("dd/MM/yyy HH:mm").format(dataFinalAux.getTime()));
		
		List<List<?>> listaTotal = new ArrayList<>();
		List<Date> listaDatas = new ArrayList<>();
		List<Double> listaPorcentagemNc = new ArrayList<>();
		
		while(dataFinalAux.getTime().getTime() < dataFinalGC.getTime().getTime()){
			
			dataInicialAux.set(Calendar.DATE, dataInicialAux.get(Calendar.DATE)+1);
			
			
			//System.out.println("Data Inicial: " + new SimpleDateFormat("dd/MM/yyy HH:mm").format(dataInicialAux.getTime()));
			//System.out.println("Data Final: " + new SimpleDateFormat("dd/MM/yyy HH:mm").format(dataFinalAux.getTime()));
			//System.out.println(""+ dataFinalAux.getTime().getTime() + " <> " + dataFinalGC.getTime().getTime());
			List<Resposta_exec_formulario> listaRespostas = indicadoresTemperatura( listaItens, dataInicialAux.getTime(), dataFinalAux.getTime(), cd_tipo_turno);
			
			dataFinalAux.set(Calendar.DATE, dataFinalAux.get(Calendar.DATE)+1);
			
			Double totalRespostas = Double.valueOf( listaRespostas.size() );
			Double totalNC = new Double(0);
			
			for(Resposta_exec_formulario resp : listaRespostas){
				if(resp.getTp_resposta_exec_formulario().equals(0) || 
						resp.getTp_resposta_exec_formulario().equals(-1) ){
					totalNC+=1;
				}
			}
			
			listaDatas.add( dataInicialAux.getTime() );
			if(totalNC>0){
				listaPorcentagemNc.add( Double.valueOf( (totalNC*100)/totalRespostas ));
			}else{
				listaPorcentagemNc.add( new Double(0) );
			}
			
		}
		
		listaTotal.add( listaDatas );
		listaTotal.add( listaPorcentagemNc );
		
		return listaTotal;
	}
	
	@SuppressWarnings("unchecked")
	public String indiceNcEMetas(List<Indicador_customizado> listaIndicadores, List<List<List<?>>> listaTotal, Date dataInicial , Date dataFinal , Integer cd_tipo_turno) {
		
		this.setDao(new GenericDAO());
		Tipo_turno tipo_turno = (Tipo_turno) this.getDao().getSession().createCriteria(Tipo_turno.class).add(Restrictions.eq("cd_tipo_turno", cd_tipo_turno)).uniqueResult();
		GregorianCalendar dataInicialGC = (GregorianCalendar) new GregorianCalendar().getInstance();
		GregorianCalendar dataFinalGC = (GregorianCalendar) new GregorianCalendar().getInstance();
		dataInicialGC.setTime(dataInicial);
		dataFinalGC.setTime(dataFinal);
		
		GregorianCalendar dataInicialAux = (GregorianCalendar) new GregorianCalendar().getInstance();
		GregorianCalendar dataFinalAux = (GregorianCalendar) new GregorianCalendar().getInstance();
		dataInicialAux.setTime( dataInicial );
		dataFinalAux.setTime(dataInicial);
		dataFinalAux.set(Calendar.DATE, dataFinalAux.get(Calendar.DATE)+6);
		
		List<Double> listaPorcentagemNc = new ArrayList<>();
		List<Date> listaDatas = new ArrayList<>();
		
		String tabela = "<html><body><table border='1'>"+
							"<tr><td>";
		 
		for(Integer ii =0 ; ii< listaTotal.size() ; ii++){
			
			listaPorcentagemNc = new ArrayList<>();
			listaDatas = new ArrayList<>();
			
			for(Integer ee =0 ; ee< 5 ; ee++){

				List<List<?>> listaItens = listaTotal.get(ii);
				List<Resposta_exec_formulario> listaRespostas = indicadoresTemperatura( listaItens, dataInicialAux.getTime(), dataFinalAux.getTime(), cd_tipo_turno);
				
				Double totalRespostas = Double.valueOf( listaRespostas.size() );
				Double totalNC = new Double(0);
				
				for(Resposta_exec_formulario resp : listaRespostas){
					if(resp.getTp_resposta_exec_formulario().equals(0) || 
							resp.getTp_resposta_exec_formulario().equals(-1) ){
						totalNC+=1;
					}
				}
				
				if(totalNC>0){
					listaPorcentagemNc.add( Double.valueOf( (totalNC*100)/totalRespostas ));
				}else{
					listaPorcentagemNc.add( new Double(0) );
				}
				
				listaDatas.add(dataFinalAux.getTime());
				
				dataInicialAux.set(Calendar.DATE, dataFinalAux.get(Calendar.DATE)+1);
				dataFinalAux.set(Calendar.DATE, dataFinalAux.get(Calendar.DATE)+7);
			}
			
			Integer nr_NC = 100 - listaIndicadores.get(ii).getNr_meta_conformidade();
			
			
			tabela += "<table border='1'>"+
					"<tr><td>" + 
					"<table border='0' width='600'>";
			
			
			tabela+= "<tr>";
			tabela+= "<td colspan='12' align='center'><b>"+listaIndicadores.get(ii).getNm_indicador_customizado()+"</b></td>";
			tabela+= "</tr>";
			
			String dataini= new SimpleDateFormat("dd/MM/yyy").format( dataInicialGC.getTime() );
			String datafin= new SimpleDateFormat("dd/MM/yyy").format( dataFinalGC.getTime() );
			
			tabela+= "<tr>";
			tabela+= "<td colspan='12' align='center'><b>"+dataini +" a "+ datafin+" "+ tipo_turno.getDs_tipo_turno() +"</b></td>";
			tabela+= "</tr>";

			tabela+= "<tr height='100'>";
			tabela+= "<td align='center' style='vertical-align:bottom'>";
			tabela+= "<div style='height:20; width:20'>"+nr_NC+"%</div>";
			tabela+= "<div style='background-color:0B0063; height:"+nr_NC+"; width:20'></div></td>";

			tabela+= "<td align='center' style='vertical-align:bottom'>";
			tabela+= "<div style='height:20; width:20'>"+new DecimalFormat("0.00").format( listaPorcentagemNc.get(0) )+"%</div>";
			tabela+= "<div style='background-color:FF6600; height:"+ (listaPorcentagemNc.get(0))+"; width:20'></div></td>";

			tabela+= "<td align='center' style='vertical-align:bottom'>";
			tabela+= "<div style='height:20; width:20'>"+nr_NC+"%</div>";
			tabela+= "<div style='background-color:0B0063; height:"+nr_NC+"; width:20'></div></td>";

			tabela+= "<td align='center' style='vertical-align:bottom'>";
			tabela+= "<div style='height:20; width:20'>"+new DecimalFormat("0.00").format( listaPorcentagemNc.get(1) )+"%</div>";
			tabela+= "<div style='background-color:FF6600; height:"+(listaPorcentagemNc.get(1))+"; width:20'></div></td>";
			
			tabela+= "<td align='center' style='vertical-align:bottom'>";
			tabela+= "<div style='height:20; width:20'>"+nr_NC+"%</div>";
			tabela+= "<div style='background-color:0B0063; height:"+nr_NC+"; width:20'></div></td>";
			
			tabela+= "<td align='center' style='vertical-align:bottom'>";
			tabela+= "<div style='height:20; width:20'>"+new DecimalFormat("0.00").format(listaPorcentagemNc.get(2))+"%</div>";
			tabela+= "<div style='background-color:FF6600; height:"+(listaPorcentagemNc.get(2))+"; width:20'></div></td>";

			tabela+= "<td align='center' style='vertical-align:bottom'>";
			tabela+= "<div style='height:20; width:20'>"+nr_NC+"%</div>";
			tabela+= "<div style='background-color:0B0063; height:"+nr_NC+"; width:20'></div></td>";
			
			tabela+= "<td align='center' style='vertical-align:bottom'>";
			tabela+= "<div style='height:20; width:20'>"+new DecimalFormat("0.00").format(listaPorcentagemNc.get(3))+"%</div>";
			tabela+= "<div style='background-color:FF6600; height:"+(listaPorcentagemNc.get(3))+"; width:20'></div></td>";

			tabela+= "<td align='center' style='vertical-align:bottom'>";
			tabela+= "<div style='height:20; width:20'>"+nr_NC+"%</div>";
			tabela+= "<div style='background-color:0B0063; height:"+nr_NC+"; width:20'></div></td>";
			
			tabela+= "<td align='center' style='vertical-align:bottom'>";
			tabela+= "<div style='height:20; width:20'>"+new DecimalFormat("0.00").format(listaPorcentagemNc.get(4))+"%</div>";
			tabela+= "<div style='background-color:FF6600; height:"+(listaPorcentagemNc.get(4))+"; width:20'></div></td>";

			tabela+= "<td align='center' style='vertical-align:bottom'>";
			tabela+= "<div style='height:20; width:20'>"+nr_NC+"%</div>";
			tabela+= "<div style='background-color:0B0063; height:"+nr_NC+"; width:20'></div></td>";
			
			tabela+= "<td align='center' style='vertical-align:bottom'>";
			
			if( listaPorcentagemNc.get(0) + listaPorcentagemNc.get(1) + listaPorcentagemNc.get(2) + listaPorcentagemNc.get(3) + listaPorcentagemNc.get(4) > 0){
				tabela+= "<div style='height:20; width:20'>"+new DecimalFormat("0.00").format( ((listaPorcentagemNc.get(0)
						+ listaPorcentagemNc.get(1)
						+ listaPorcentagemNc.get(2)
						+ listaPorcentagemNc.get(3)
						+ listaPorcentagemNc.get(4))/5))+"%</div>";
			}else{
				tabela+= "<div style='height:20; width:20'>0.0%</div>";
			}
			
			
			if( listaPorcentagemNc.get(0) + listaPorcentagemNc.get(1) + listaPorcentagemNc.get(2) + listaPorcentagemNc.get(3) + listaPorcentagemNc.get(4) > 0){

				tabela+= "<div style='background-color:FF6600; height:"+((listaPorcentagemNc.get(0)
							+ listaPorcentagemNc.get(1)
							+ listaPorcentagemNc.get(2)
							+ listaPorcentagemNc.get(3)
							+ listaPorcentagemNc.get(4))/5)+"; width:20'></div></td>";
				
			}else{
					tabela+= "<div style='background-color:FF6600; height:0; width:20'></div></td>";
			}
			
			tabela+= "</tr>";
	
			tabela+= "<tr>";
			tabela+= "<td align='center'>Meta</td>";
			tabela+= "<td align='center'>NC</td>";
			tabela+= "<td align='center'>Meta</td>";
			tabela+= "<td align='center'>NC</td>";
			tabela+= "<td align='center'>Meta</td>";
			tabela+= "<td align='center'>NC</td>";
			tabela+= "<td align='center'>Meta</td>";
			tabela+= "<td align='center'>NC</td>";
			tabela+= "<td align='center'>Meta</td>";
			tabela+= "<td align='center'>NC</td>";
			tabela+= "<td align='center'>Meta</td>";
			tabela+= "<td align='center'>Real</td>";
			tabela+= "</tr>";

			tabela+= "<tr>";
			tabela+= "<td colspan='2' align='center'>"+ new SimpleDateFormat("dd/MM/yyyy ").format(listaDatas.get(0)) +"</td>";
			tabela+= "<td colspan='2' align='center'>"+ new SimpleDateFormat("dd/MM/yyyy ").format(listaDatas.get(1)) +"</td>";
			tabela+= "<td colspan='2'align='center'>"+ new SimpleDateFormat("dd/MM/yyyy ").format(listaDatas.get(2)) +"</td>";
			tabela+= "<td colspan='2'align='center'>"+ new SimpleDateFormat("dd/MM/yyyy ").format(listaDatas.get(3)) +"</td>";
			tabela+= "<td colspan='2'align='center'>"+ new SimpleDateFormat("dd/MM/yyyy ").format(listaDatas.get(4)) +"</td>";
			tabela+= "<td colspan='2'align='center'>PERÍODO</td>";
			
			tabela+= "</tr>";

			tabela += "</table></td></tr>"+
						"</table><br><br>";
			
			dataInicialAux.setTime( dataInicial );
			dataFinalAux.setTime(dataInicial);
			dataFinalAux.set(Calendar.DATE, dataFinalAux.get(Calendar.DATE)+6);
		}
		
		tabela += "</body></html>";
		System.out.println(tabela);
		
		GregorianCalendar g =  (GregorianCalendar) GregorianCalendar.getInstance();
		String nomeDocumento= "indicador_"+ g.get(Calendar.DATE) +"_"+ g.get(Calendar.MONTH) +"_"+ g.get(Calendar.YEAR) +"_"+  g.get(Calendar.HOUR_OF_DAY) +"_"+ g.get(Calendar.MINUTE) +"_"+ g.get(Calendar.SECOND) +".html";
		File fileHTML = new File(ContextoAplicacao.CAMINHO_SAIDA + "/" + nomeDocumento);
		try {
			if (fileHTML.createNewFile()){
					
				FileWriter fw = new FileWriter(fileHTML.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(tabela);
				bw.close();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return nomeDocumento;
	}
	
	
//	public void cocobongo(){
//		try {
//        	
//			//"<body><img width='200' height='40' src='data:image/png;base64,"+imgAzul2+"'/>"+
//			
//			EmailBO emailBO = new EmailBO();
//        	
//        	String imgAzul2 = retornaImagemCodificada(ContextoAplicacao.CAMINHO_SAIDA+"/imagens/barraAzul2.png");
//        	String corpoMSG = "" +
//        	"<html>" +
//    		"<head>" +
//    		"<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>" +
//    		"</head>" +
//    		
//			"<body><div style='background-color:#FF9800; height: 50px; width: 120px; position: relative; bottom: 0; float: left; margin: 1px;'>&nbsp;</div>"+
//    		"</body></html>";
//    		
//        	emailBO.sendEmail( "vine.fm@gmail.com", "vine" ,"teste imagem", corpoMSG);
//    		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
}

