package br.com.primum.bo.relatorios;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.campo.Campo;
import br.com.primum.modelo.campo_formulario.Campo_formulario;
import br.com.primum.modelo.classificacao_auditoria.Classificacao_auditoria;
import br.com.primum.modelo.codigosfixos.CodigosFixos;
import br.com.primum.modelo.conclusao_auditoria.Conclusao_auditoria;
import br.com.primum.modelo.cronograma_auditoria.Cronograma_auditoria;
import br.com.primum.modelo.cronograma_manutencao.Cronograma_manutencao;
import br.com.primum.modelo.descritivo_treinamento.Descritivo_treinamento;
import br.com.primum.modelo.diagrama_helpdesk.Diagrama_helpdesk;
import br.com.primum.modelo.equipe_cronograma_auditoria.Equipe_cronograma_auditoria;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.executar_reuniao.Executar_reuniao;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.grupo_formulario.Grupo_formulario;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.modelo.manutencao_preventiva.Manutencao_preventiva;
import br.com.primum.modelo.modulo.Modulo;
import br.com.primum.modelo.ocorrencia_ouvidoria.Ocorrencia_ouvidoria;
import br.com.primum.modelo.participantes_treinamento.Participantes_treinamento;
import br.com.primum.modelo.plano_acao.Plano_acao;
import br.com.primum.modelo.plano_acao_formulario.Plano_acao_formulario;
import br.com.primum.modelo.plano_acao_ocorrencia.Plano_acao_ocorrencia;
import br.com.primum.modelo.planos_cronograma_auditoria.Planos_cronograma_auditoria;
import br.com.primum.modelo.programa_qualidade.Programa_qualidade;
import br.com.primum.modelo.registro_treinamento.Registro_treinamento;
import br.com.primum.modelo.resolucao_ocorrencia.Resolucao_ocorrencia;
import br.com.primum.modelo.resposta_campo_formulario.Resposta_campo_formulario;
import br.com.primum.modelo.resposta_exec_auditoria.Resposta_exec_auditoria;
import br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario;
import br.com.primum.modelo.revisao_programa_qualidade.Revisao_programa_qualidade;
import br.com.primum.modelo.setor.Setor;
import br.com.primum.modelo.sub_formulario.Sub_formulario;
import br.com.primum.modelo.tabela.Tabela;
import br.com.primum.reflection.Reflection;
import br.com.primum.relatorio.modelo.formulario.resultPesquisaFormulario;

public class RelatorioBO {
	
	private static GenericDAO dao;
	private String[] arrMeses = {"Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"}; 
	
	public RelatorioBO(){
		
	}
	
	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		RelatorioBO.dao = dao;
	}
	
	@SuppressWarnings("unchecked")
	public Integer relatorioTreinamentoPorColaborador(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Integer cod_colaborador){
		this.setDao(new GenericDAO());
		
		//TreinamentoBO treinamento = new TreinamentoBO();
		//List<?> lista = new ArrayList<>();
		//List<Participante_treinamento> listaAux = new ArrayList<>();
		List<Participantes_treinamento> listaFinal = new ArrayList<>();
		
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Registro_treinamento> listaRegistrosTreinamentos = this.getDao().getSession().createCriteria(Registro_treinamento.class).addOrder( Order.desc("dt_inicio") ).list();
		for(Registro_treinamento registro : listaRegistrosTreinamentos){
			pesquisa.buscarObjetoCompleto(registro);
			
			for(Participantes_treinamento participante : registro.getListaParticipantes()){
				if(participante.getCd_pessoa().equals( cod_colaborador )){
					pesquisa.buscarObjetoCompleto(participante);
					Registro_treinamento registroAux = (Registro_treinamento) this.getDao().getSession().createCriteria(Registro_treinamento.class).add(Restrictions.eq("cd_registro_treinamento", participante.getCd_registro_treinamento())).uniqueResult();
					Descritivo_treinamento descritivo = (Descritivo_treinamento) this.getDao().getSession().createCriteria(Descritivo_treinamento.class).add(Restrictions.eq("cd_descritivo_treinamento", registroAux.getCd_descritivo_treinamento())).uniqueResult();
					participante.setRegistro_treinamento(registroAux);
					participante.setDescritivo_treinamento(descritivo);
					
					listaFinal.add(participante);
				}
			}
		}
		
		
//		try {
//			lista = treinamento.getListaTreinamentosPorPessoa(cod_colaborador);
//			listaAux = (List<Participante_treinamento>) lista.get(0);
//			for(Participante_treinamento p : listaAux){
//				listaFinal.add( p );
//			}
//			
//			listaAux = (List<Participante_treinamento>) lista.get(1);
//			for(Participante_treinamento p : listaAux){
//				listaFinal.add( p );
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		//lista.get(0) =  listaDados;
		//lista.get(1) =  listaAgendados;
		Map<String, Object> parametros = new HashMap<>();
		//parametros.put("listaTreinamentosDados", (List<Participante_treinamento>) lista.get(0));
		//parametros.put("listaTreinamentosAgendados", (List<Participante_treinamento>) lista.get(1));
				
		return this.gerarRelatorio( listaFinal, parametros, arqLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public Integer relatorioTreinamentoPorPeriodo(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Date dataInicio, Date dataFim){
		this.setDao(new GenericDAO());
		
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("dataInicio", ( dataInicio.toGMTString().split(" ")[0] + " "+ arrMeses[dataInicio.getMonth()] +" " + dataInicio.toGMTString().split(" ")[2] ) );
		parametros.put("dataFim", ( dataFim.toGMTString().split(" ")[0] + " "+ arrMeses[dataFim.getMonth()] +" " + dataFim.toGMTString().split(" ")[2] ) );
		
		dataFim.setDate( dataFim.getDate()+1);
		dataInicio.setDate( dataInicio.getDate()+1);
		List<Registro_treinamento> listaTreinamentos = this.getDao().getSession().createCriteria(Registro_treinamento.class).add(Restrictions.between("dt_inicio", dataInicio, dataFim)).addOrder( Order.asc("dt_inicio") ).list();
		for( Registro_treinamento registro : listaTreinamentos){
			
			pesquisa.buscarObjetoCompleto(registro);
			
			Descritivo_treinamento descritivo_treinamento = (Descritivo_treinamento) this.getDao().getSession().createCriteria(Descritivo_treinamento.class).add(Restrictions.eq("cd_descritivo_treinamento", registro.getCd_descritivo_treinamento())).uniqueResult();
			List<Participantes_treinamento> listaParticipantes = this.getDao().getSession().createCriteria(Participantes_treinamento.class).add(Restrictions.eq("cd_registro_treinamento", registro.getCd_registro_treinamento())).list();
			
			registro.setDescritivo_treinamento(descritivo_treinamento);
			registro.setListaParticipantes(listaParticipantes);
		}
		
		return this.gerarRelatorio(listaTreinamentos, parametros, arqLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public Integer relatorioOuvidoriaPorPeriodo(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Date dataInicio, Date dataFim){
		this.setDao(new GenericDAO());
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("dataInicio", ( dataInicio.toGMTString().split(" ")[0] + " "+ arrMeses[dataInicio.getMonth()] +" " + dataInicio.toGMTString().split(" ")[2] ) );
		parametros.put("dataFim", ( dataFim.toGMTString().split(" ")[0] + " "+ arrMeses[dataFim.getMonth()] +" " + dataFim.toGMTString().split(" ")[2] ) );
		
		dataFim.setDate( dataFim.getDate()+1);
		List<Ocorrencia_ouvidoria> listaOuvidoria = this.getDao().getSession().createCriteria(Ocorrencia_ouvidoria.class).add(Restrictions.between("dt_ocorrencia_ouvidoria", dataInicio, dataFim)).addOrder( Order.asc("dt_ocorrencia_ouvidoria") ).list();

		return this.gerarRelatorio(listaOuvidoria, parametros, arqLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	
	public Integer relatorioHelpdesk(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Helpdesk_ocorrencia hd){
		this.setDao(new GenericDAO());
		List<Helpdesk_ocorrencia> listaHD= new ArrayList<>();
		listaHD.add(hd);
		return this.gerarRelatorio(listaHD, null, arqLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	
	@SuppressWarnings("unchecked")
	public Integer relatorioRevisaoProgramaQualidade(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Integer cd_revisao_programa_qualidade){
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		Revisao_programa_qualidade revisao = (Revisao_programa_qualidade) this.getDao().getSession().createCriteria(Revisao_programa_qualidade.class)
			.add(Restrictions.eq("cd_revisao_programa_qualidade", cd_revisao_programa_qualidade)).uniqueResult();
		if(revisao!=null){
			pesquisa.buscarObjetoCompleto(revisao);
		}
		
		List<Formulario> listaFormularios =this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_revisao_programa_qualidade", revisao.getCd_revisao_programa_qualidade())).addOrder(Order.asc("nm_formulario")).list();

		for (Formulario formulario : (List<Formulario>) listaFormularios) {
			pesquisa.buscarObjetoCompleto(formulario);
		}
		
		List<Revisao_programa_qualidade> listaRevisao= new ArrayList<>();
		listaRevisao.add(revisao);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("listaFormularios", listaFormularios);
		
		return this.gerarRelatorio(listaRevisao, parametros, arqLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	
	
	@SuppressWarnings("unchecked")
	public Integer relatorioOuvidoria(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Integer cod_chamado){
		this.setDao(new GenericDAO());
		
		List<Ocorrencia_ouvidoria> listaOuvidoria = this.getDao().getSession().createCriteria(Ocorrencia_ouvidoria.class).add(Restrictions.eq("cd_ocorrencia_ouvidoria", cod_chamado)).list();
		List<Resolucao_ocorrencia> listaResolucoes = new ArrayList<>(); 
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		for( Ocorrencia_ouvidoria ocorrencia : listaOuvidoria){
			
			List<Resolucao_ocorrencia> resolucaoAux = (List<Resolucao_ocorrencia>) this.getDao().getSession().createCriteria(Resolucao_ocorrencia.class).add(Restrictions.eq("cd_ocorrencia_ouvidoria",ocorrencia.getCd_ocorrencia_ouvidoria())).list();
			if(resolucaoAux.size()>0){
				if(resolucaoAux.get(0)!=null){
					
					pesquisa.buscarObjetoCompleto(resolucaoAux);
					Ocorrencia_ouvidoria ocorrenciaAux =  (Ocorrencia_ouvidoria) this.getDao().getSession().createCriteria(Ocorrencia_ouvidoria.class).add(Restrictions.eq("cd_ocorrencia_ouvidoria",resolucaoAux.get(0).getCd_ocorrencia_ouvidoria())).uniqueResult();
					List<Plano_acao_ocorrencia> listaPlanos = this.getDao().getSession().createCriteria(Plano_acao_ocorrencia.class).add(Restrictions.eq("cd_resolucao_ocorrencia",resolucaoAux.get(0).getCd_resolucao_ocorrencia())).list();
					
					resolucaoAux.get(0).setOcorrencia_ouvidoria(ocorrenciaAux);
					resolucaoAux.get(0).setListaPlanos(listaPlanos);
					listaResolucoes.add(resolucaoAux.get(0));
				}else{
					resolucaoAux.get(0).setOcorrencia_ouvidoria(ocorrencia);
					listaResolucoes.add(resolucaoAux.get(0));
				}
			}else{
				Resolucao_ocorrencia resolucaoExtra = new Resolucao_ocorrencia();
				resolucaoExtra.setOcorrencia_ouvidoria(ocorrencia);
				listaResolucoes.add(resolucaoExtra);
			}
			
		}
		
		return this.gerarRelatorio(listaResolucoes, null, arqLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	
//	@SuppressWarnings("unchecked")
//	public Boolean relatorioAuditoria(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo){
//		this.setDao(new GenericDAO());
//		
//		List<Cronograma_auditoria> listaCronograma = this.getDao().getSession().createCriteria(Cronograma_auditoria.class).list();
//		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
//		
//		Map<String, Object> parametro = new HashMap<String, Object>();
//		
//		for( Cronograma_auditoria cronograma : listaCronograma){
//			pesquisa.buscarObjetoCompleto(cronograma);
//			
//			Classificacao_auditoria classificacao = (Classificacao_auditoria) this.getDao().getSession().createCriteria(Classificacao_auditoria.class).add(Restrictions.eq("cd_classificacao_auditoria", cronograma.getCd_classificacao_auditoria())).uniqueResult();
//			List<Equipe_cronograma_auditoria> listaEquipe = this.getDao().getSession().createCriteria(Equipe_cronograma_auditoria.class).add(Restrictions.eq("cd_cronograma_auditoria",cronograma.getCd_cronograma_auditoria())).list();
//			List<Planos_cronograma_auditoria> listaPlanos = this.getDao().getSession().createCriteria(Planos_cronograma_auditoria.class).add(Restrictions.eq("cd_cronograma_auditoria",cronograma.getCd_cronograma_auditoria())).list();
//			
//			cronograma.setClassificacao_auditoria(classificacao);
//			cronograma.setListaEquipeAuditoria(listaEquipe);
//			cronograma.setListaPlanoAuditoria(listaPlanos);
//		}
//		
//		List<Conclusao_auditoria> listaConclusao = (List<Conclusao_auditoria>) this.getDao().getSession().createCriteria(Conclusao_auditoria.class).list();
//		List<Faixa_classificacao_auditoria> listaFaixa = (List<Faixa_classificacao_auditoria>) this.getDao().getSession().createCriteria(Conclusao_auditoria.class).list();
//		
//		parametro.put("ListaConclusao_Auditoria", listaConclusao);
//		parametro.put("ListaFaixa_ClassificacaoAuditoria", listaFaixa);
//		
//		return this.gerarRelatorio(listaCronograma,parametro, arqLeitura, caminhoGravacao, nomeArquivo, exportTo);
//	}
	
	
	@SuppressWarnings("unchecked")
	public Integer relatorioAuditoria(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Integer cd_cronograma_auditoria){
		this.setDao(new GenericDAO());
		
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Cronograma_auditoria> listaCronograma = (List<Cronograma_auditoria>) this.getDao().getSession().createCriteria(Cronograma_auditoria.class).add(Restrictions.eq("cd_cronograma_auditoria", cd_cronograma_auditoria)).list();
		List<Conclusao_auditoria> listaConclusaoAux = new ArrayList<>();

		for( Cronograma_auditoria cronograma : listaCronograma){
			
			pesquisa.buscarObjetoCompleto(cronograma);
			Classificacao_auditoria classificacao = (Classificacao_auditoria) this.getDao().getSession().createCriteria(Classificacao_auditoria.class).add(Restrictions.eq("cd_classificacao_auditoria", cronograma.getCd_classificacao_auditoria())).uniqueResult();
			List<Equipe_cronograma_auditoria> listaEquipe = this.getDao().getSession().createCriteria(Equipe_cronograma_auditoria.class).add(Restrictions.eq("cd_cronograma_auditoria",cronograma.getCd_cronograma_auditoria())).list();
			List<Planos_cronograma_auditoria> listaPlanos = this.getDao().getSession().createCriteria(Planos_cronograma_auditoria.class).add(Restrictions.eq("cd_cronograma_auditoria",cronograma.getCd_cronograma_auditoria())).list();
			
			cronograma.setClassificacao_auditoria(classificacao);
			cronograma.setListaEquipeAuditoria(listaEquipe);
			cronograma.setListaPlanoAuditoria(listaPlanos);
			
			
			List<Conclusao_auditoria> listaConclusao = (List<Conclusao_auditoria>) this.getDao().getSession().createCriteria(Conclusao_auditoria.class).add(Restrictions.eq("cd_cronograma_auditoria",cronograma.getCd_cronograma_auditoria())).list();
			if(listaConclusao.size()>0){
				if(listaConclusao.get(0)!=null){
					listaConclusao.get(0).setCronograma_auditoria(cronograma);
					listaConclusaoAux.add(listaConclusao.get(0));
				}else{
					Conclusao_auditoria conclusaoAux = new Conclusao_auditoria();
					conclusaoAux.setCronograma_auditoria(cronograma);
					listaConclusaoAux.add( conclusaoAux );
				}
			}else{
				Conclusao_auditoria conclusaoAux = new Conclusao_auditoria();
				conclusaoAux.setCronograma_auditoria(cronograma);
				listaConclusaoAux.add( conclusaoAux );
			}
			
		}
		
		//***********************
		
		String str ="";
		for( Conclusao_auditoria conclusao : listaConclusaoAux){
			
			for(Planos_cronograma_auditoria planos : conclusao.getCronograma_auditoria().getListaPlanoAuditoria() ){
				
				str += planos.getAuditoria().getNm_auditoria() +"<br>";
				String grupoAtual="";
				if(planos.getExecucao_auditoria()!=null){
					
					//for(Resposta_execucao_auditoria res: planos.getExecucao_auditoria().getListaRespostas() ){
					for(int i =0; i< planos.getExecucao_auditoria().getListaRespostas().size() ;i++ ){
						Resposta_exec_auditoria res = (Resposta_exec_auditoria) planos.getExecucao_auditoria().getListaRespostas().get(i);
						if(grupoAtual!=res.getGrupo_auditoria().getNm_grupo_auditoria()){
							str+="<br>";
							str+="<style forecolor='#FF0000'>"; 
							str+="   "+ res.getGrupo_auditoria().getNm_grupo_auditoria() +"<br>";
							str+="</style>";
							grupoAtual = res.getGrupo_auditoria().getNm_grupo_auditoria();
							str+="<br>";
						}
						str+="<style forecolor='#0066FF'>";
						if(res.getDs_justificativa()==null){
							str+="      "+ res.getPergunta_auditoria().getDs_pergunta_auditoria() +" ..... "+ res.getResposta_auditoria().getDs_resposta_auditoria() +"<br>";
						}else{
							str+="      "+ res.getPergunta_auditoria().getDs_pergunta_auditoria() +" ..... "+ res.getResposta_auditoria().getDs_resposta_auditoria() +"( "+ res.getDs_justificativa()+" )<br>";
						}
						str+="</style>";
						str+="<br>";
					}
				}
				str+="<br><br><br>";
	
			}
			conclusao.getCronograma_auditoria().getListaPlanoAuditoria();
			conclusao.getCronograma_auditoria().getListaEquipeAuditoria();
			
			System.out.println(str);
		}
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("string_planos", str);
		//***********************
		
		return this.gerarRelatorio(listaConclusaoAux,parametros, arqLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	
	public Integer relatorioOrdemServicoManutencao(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Cronograma_manutencao cronograma){
		
		this.setDao(new GenericDAO());
		List<Cronograma_manutencao> listaCronogramasManutencao = new ArrayList<>();
		listaCronogramasManutencao.add(cronograma);
		
		return this.gerarRelatorio(listaCronogramasManutencao,null, arqLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	
	public Integer relatorioManutencaoPreventiva(String arquivoLeitura,String caminhoGravacao, String nomeArquivo, String exportTo, Manutencao_preventiva manutencao){
		
		this.setDao(new GenericDAO());
		List<Manutencao_preventiva> listaManutencao = new ArrayList<>();
		listaManutencao.add(manutencao);
		
		return this.gerarRelatorio(listaManutencao,null, arquivoLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	
	public Integer relatorioManutencaoCorretiva(String arquivoLeitura,String caminhoGravacao, String nomeArquivo, String exportTo, Plano_acao plano){
		
		List<Plano_acao> listaPlano = new ArrayList<>();
		listaPlano.add(plano);
		
		return this.gerarRelatorio(listaPlano,null, arquivoLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	
	
	public Integer relatorioFormularioVerificacao(String arquivoLeitura,String caminhoGravacao, String nomeArquivo, String exportTo, Execucao_formulario exec_formulario){
		
		this.setDao(new GenericDAO());
		List<Execucao_formulario> listaFormulario = new ArrayList<>();
		if(exec_formulario !=null ){
			String strFinal="";
			if(exec_formulario !=null ){
				if(exec_formulario.getFormulario().getTp_formato().equals(2)){
					
					for(Sub_formulario sub : exec_formulario.getFormulario().getListaSub_formulario()){
						Setor setor = (Setor) this.getDao().getSession().createCriteria(Setor.class).add(Restrictions.eq("cd_setor", sub.getVl_filtro())).uniqueResult();
						
						strFinal+= setor.getNm_setor() +"<br>";
						
						for(Grupo_formulario grupo : sub.getListaGrupo_formulario()){
							for(Grupo_formulario grupoFilho: grupo.getListaGrupo_formulario()){
								strFinal+= "<p><b>" + grupoFilho.getNm_grupo_formulario() + "</b><br>";
								for(Campo_formulario campo: grupoFilho.getListaCampo_formulario()){
									
									String campoEResp= campo.getNm_campo_formulario();
									campoEResp += "............";
									for(Resposta_exec_formulario resp_exec_form : exec_formulario.getListaRespostas_execucao_formulario()){
										if(resp_exec_form.getCd_campo_formulario().equals(campo.getCd_campo_formulario())){
											
											if(campo.getCd_tipo_campo().equals(11)){
												if(resp_exec_form.getTp_resposta_exec_formulario() != null){
													if(resp_exec_form.getTp_resposta_exec_formulario().equals(0))
														campoEResp += "NÃO CONFORME";
													else
														campoEResp += "CONFORME";
												}else{
													campoEResp += "NÃO INFORMADO";
												}
											}else{
												campoEResp += resp_exec_form.getDs_resposta_exec_formulario() ;
											}
											
											if( resp_exec_form.getTp_resposta_exec_formulario() != null && resp_exec_form.getTp_resposta_exec_formulario().equals(0)){
												Plano_acao_formulario planoAux = (Plano_acao_formulario) this.getDao().getSession().createCriteria(Plano_acao_formulario.class).add(Restrictions.eq("cd_resposta_exec_formulario", resp_exec_form.getCd_resposta_exec_formulario())).uniqueResult();
												if(planoAux!=null){
													Plano_acao planoFinal = (Plano_acao) this.getDao().getSession().createCriteria(Plano_acao.class).add(Restrictions.eq("cd_plano_acao", planoAux.getCd_plano_acao())).uniqueResult();
													//listaPlanosAcao.add(planoFinal);
													//listaCampoNC.add(""+campo.getNm_campo_formulario());
													
													campoEResp="<font color='#FF0000'>"+ campoEResp + "</font> - Cód. plano ação: "+planoFinal.getCd_plano_acao();
												}
											}
											
										}
									}
									
									strFinal+="<p><p>" + campoEResp +"<br>";
									
								}
								strFinal+="<br>";
							}
						}
					}
					
				}else{
					
					for(Grupo_formulario grupo : exec_formulario.getFormulario().getListaGrupo_formulario()){
						if( grupo.getListaGrupo_formulario().size() > 0  ){
							for(Grupo_formulario grupoFilho: grupo.getListaGrupo_formulario()){
								strFinal+= "<b>"+grupoFilho.getNm_grupo_formulario() +"</b><br>";
								for(Campo_formulario campo: grupoFilho.getListaCampo_formulario()){
		
									String campoEResp= campo.getNm_campo_formulario();
									campoEResp += "............";
									for(Resposta_exec_formulario resp_exec_form : exec_formulario.getListaRespostas_execucao_formulario()){
										if(resp_exec_form.getCd_campo_formulario().equals(campo.getCd_campo_formulario())){

											if(campo.getCd_tipo_campo().equals(11)){
												if(resp_exec_form.getTp_resposta_exec_formulario() != null){
													if(resp_exec_form.getTp_resposta_exec_formulario().equals(0))
														campoEResp += "NÃO CONFORME";
													else
														campoEResp += "CONFORME";
												}else{
													campoEResp += "NÃO INFORMADO";
												}
											}else{
												campoEResp += resp_exec_form.getDs_resposta_exec_formulario() ;
											}
											
											if( resp_exec_form.getTp_resposta_exec_formulario() != null &&  resp_exec_form.getTp_resposta_exec_formulario().equals(0)){
												Plano_acao_formulario planoAux = (Plano_acao_formulario) this.getDao().getSession().createCriteria(Plano_acao_formulario.class).add(Restrictions.eq("cd_resposta_exec_formulario", resp_exec_form.getCd_resposta_exec_formulario())).uniqueResult();
												if(planoAux!=null){
													Plano_acao planoFinal = (Plano_acao) this.getDao().getSession().createCriteria(Plano_acao.class).add(Restrictions.eq("cd_plano_acao", planoAux.getCd_plano_acao())).uniqueResult();
													//listaPlanosAcao.add(planoFinal);
													//listaCampoNC.add(""+campo.getNm_campo_formulario());
													campoEResp="<font color='#FF0000'>"+ campoEResp + "</font> - Cód. plano ação: "+planoFinal.getCd_plano_acao();
												}
											}
											
										}
									}
									
									strFinal+="<p>" + campoEResp +"<br>";
								}
								strFinal+="<br>";
							}
							
							for(Campo_formulario campo: grupo.getListaCampo_formulario()){
								String campoEResp= campo.getNm_campo_formulario();
								campoEResp += "............";
								for(Resposta_exec_formulario resp_exec_form : exec_formulario.getListaRespostas_execucao_formulario()){
									if(resp_exec_form.getCd_campo_formulario().equals(campo.getCd_campo_formulario())){

										if(campo.getCd_tipo_campo().equals(11)){
											if(resp_exec_form.getTp_resposta_exec_formulario() != null){
												if(resp_exec_form.getTp_resposta_exec_formulario().equals(0))
													campoEResp += "NÃO CONFORME";
												else
													campoEResp += "CONFORME";
											}else{
												campoEResp += "NÃO INFORMADO";
											}
										}else{
											campoEResp += resp_exec_form.getDs_resposta_exec_formulario() ;
										}
										
										if(  resp_exec_form.getTp_resposta_exec_formulario() != null && resp_exec_form.getTp_resposta_exec_formulario().equals(0)){
											Plano_acao_formulario planoAux = (Plano_acao_formulario) this.getDao().getSession().createCriteria(Plano_acao_formulario.class).add(Restrictions.eq("cd_resposta_exec_formulario", resp_exec_form.getCd_resposta_exec_formulario())).uniqueResult();
											if(planoAux!=null){
												Plano_acao planoFinal = (Plano_acao) this.getDao().getSession().createCriteria(Plano_acao.class).add(Restrictions.eq("cd_plano_acao", planoAux.getCd_plano_acao())).uniqueResult();
												//listaPlanosAcao.add(planoFinal);
												//listaCampoNC.add(""+campo.getNm_campo_formulario());
												campoEResp="<font color='#FF0000'>"+ campoEResp + "</font> - Cód. plano ação: "+planoFinal.getCd_plano_acao();
											}
										}
									}
								}
								
								strFinal+=campoEResp +"<br>";
							}
							
						}else{
							for(Campo_formulario campo: grupo.getListaCampo_formulario()){
								String campoEResp= campo.getNm_campo_formulario();
								campoEResp += "............";
								for(Resposta_exec_formulario resp_exec_form : exec_formulario.getListaRespostas_execucao_formulario()){
									if(resp_exec_form.getCd_campo_formulario().equals(campo.getCd_campo_formulario())){

										if(campo.getCd_tipo_campo().equals(11)){
											if(resp_exec_form.getTp_resposta_exec_formulario() != null){
												if(resp_exec_form.getTp_resposta_exec_formulario().equals(0))
													campoEResp += "NÃO CONFORME";
												else
													campoEResp += "CONFORME";
											}else{
												campoEResp += "NÃO INFORMADO";
											}
										}else{
											campoEResp += resp_exec_form.getDs_resposta_exec_formulario() ;
										}
										
										if( resp_exec_form.getTp_resposta_exec_formulario() != null && resp_exec_form.getTp_resposta_exec_formulario().equals(0)){
											Plano_acao_formulario planoAux = (Plano_acao_formulario) this.getDao().getSession().createCriteria(Plano_acao_formulario.class).add(Restrictions.eq("cd_resposta_exec_formulario", resp_exec_form.getCd_resposta_exec_formulario())).uniqueResult();
											if(planoAux!=null){
												Plano_acao planoFinal = (Plano_acao) this.getDao().getSession().createCriteria(Plano_acao.class).add(Restrictions.eq("cd_plano_acao", planoAux.getCd_plano_acao())).uniqueResult();
												//listaPlanosAcao.add(planoFinal);
												//listaCampoNC.add(""+campo.getNm_campo_formulario());
												campoEResp="<font color='#FF0000'>"+ campoEResp + "</font> - Cód. plano ação: "+planoFinal.getCd_plano_acao();
											}
										}
									}
								}
								strFinal+= campoEResp +"<br>";
							}
						}	
					}
				}
			}	
			exec_formulario.setDs_descricao(strFinal + "<br><HR>");
			listaFormulario.add(exec_formulario);
		}else{
			return 0;
		}

		return this.gerarRelatorio(listaFormulario,null, arquivoLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	
	@SuppressWarnings("unchecked")
	public Integer relatorioFormularioPeriodo(String arquivoLeitura,String caminhoGravacao, String nomeArquivo, String exportTo, Formulario f, java.util.Date data_inicio, java.util.Date data_fim){
	
		this.setDao(new GenericDAO());
		resultPesquisaFormulario objPai = new resultPesquisaFormulario();
		List<resultPesquisaFormulario> listaPai = new ArrayList<resultPesquisaFormulario>();
		List<resultPesquisaFormulario> listaMae = new ArrayList<resultPesquisaFormulario>();
		List<resultPesquisaFormulario> listaAux = this.getDao().getSession()
							.createSQLQuery("select "+ 
											"campo_formulario.nm_campo_formulario as nm_campo_formulario, "+
											"grupo_formulario.nm_grupo_formulario as nm_grupo_formulario, "+
											"resposta_exec_formulario.tp_resposta_exec_formulario as tp_resposta_exec_formulario, "+
											"resposta_exec_formulario.ds_resposta_exec_formulario as ds_resposta_exec_formulario, "+
											"resposta_exec_formulario.cd_resposta_campo_formulario as cd_resposta_campo_formulario, "+
											"execucao_formulario.dt_execucao_formulario as dt_execucao_formulario, "+
											"usuario.nm_usuario as nm_usuario "+
											"from execucao_formulario "+ 
											"INNER JOIN usuario on usuario.cd_usuario = execucao_formulario.cd_usuario "+
											"INNER JOIN resposta_exec_formulario  on resposta_exec_formulario.cd_execucao_formulario = execucao_formulario.cd_execucao_formulario "+
											"INNER JOIN campo_formulario on campo_formulario.cd_campo_formulario = resposta_exec_formulario.cd_campo_formulario "+
											"INNER JOIN grupo_formulario on grupo_formulario.cd_grupo_formulario = campo_formulario.cd_grupo_formulario "+
											"AND execucao_formulario.cd_formulario = "+ f.getCd_formulario() + " "+
											"AND execucao_formulario.nr_hora_termino IS NOT NULL " +
											"AND execucao_formulario.dt_execucao_formulario between DATE '"+new SimpleDateFormat("yyyy-MM-dd").format(data_inicio)+"' and DATE '"+new SimpleDateFormat("yyyy-MM-dd").format(data_fim)+"' "+		
											"ORDER BY resposta_exec_formulario.cd_campo_formulario, execucao_formulario.cd_execucao_formulario;").setResultTransformer(Transformers.aliasToBean(resultPesquisaFormulario.class)).list();

		
		String strNomeGrupo = "";
		if( listaAux != null  && listaAux.size() > 0){
			for(Integer i = 0 ; i< listaAux.size() ; i++){
				
				//listaAux.get(i).setFormulario( f );
				
				if( !listaAux.get(i).getNm_grupo_formulario().equals( strNomeGrupo ) ){
					
					strNomeGrupo = listaAux.get(i).getNm_grupo_formulario();
					listaMae.add( listaAux.get(i) );
					listaMae.get(listaMae.size() - 1 ).setListaFilhos(new ArrayList<resultPesquisaFormulario>());	
					
					
					for(Integer e = 0 ; e< listaAux.size() ; e++){
						if( listaAux.get(e).getNm_grupo_formulario().equals( strNomeGrupo )){
							listaMae.get(listaMae.size() - 1 ).getListaFilhos().add( listaAux.get(e) );
						}
					}
				}
			}
		}
		
		
//		List<Execucao_formulario> listaExecucao_formulario = (List<Execucao_formulario>) this.getDao().getSession().createCriteria(Execucao_formulario.class)
//				.add(Restrictions.eq("cd_formulario", f.getCd_formulario()))
//				.add(Restrictions.between("dt_execucao_formulario", data_inicio, data_fim))
//				.addOrder(Order.asc("dt_execucao_formulario"))
//				.list();
//		
//		Integer qtdeExecucoes = 0;
//		if( listaExecucao_formulario != null  && listaExecucao_formulario.size() > 0){
//			
//			Execucao_formulario exec_formulario = listaExecucao_formulario.get(0);
//			PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
//			pesquisa.buscarObjetoCompleto(exec_formulario);
//			
//			qtdeExecucoes = listaExecucao_formulario.size();
//			
//			extraiStringGrupoFormulario( exec_formulario.getFormulario().getListaGrupo_formulario() );
//			
//		
//		}
		
		objPai.setListaGrupos(new ArrayList<resultPesquisaFormulario>());
		objPai.setListaGrupos(listaMae);
		objPai.setFormulario( f );
		listaPai.add(objPai);
		
		return this.gerarRelatorio( listaPai, null, arquivoLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	
//	Collections.sort(movieItems, new Comparator<Movie>() {
//	    public int compare(Movie m1, Movie m2) {
//	        return m1.getDate().compare(m2.getDate());
//	    }
//	});
	
	public String[] extraiStringGrupoFormulario( List<Grupo_formulario> listaGrupos){
		
		String vetorString[] = new String[1000];
		Integer indice = 0;
		Integer indiceGrupo = 0;		
		
		for(Grupo_formulario g: listaGrupos){
			
			vetorString[indice] = "[g="+ g.getNm_grupo_formulario()+"]["+indiceGrupo+"]";
			indice += 1;
			
			for(Campo_formulario c : g.getListaCampo_formulario()){
				vetorString[indice] = "["+indiceGrupo+"]"+c.getDs_campo_formulario();
				indice += 1;
			}
			
			if(g.getListaGrupo_formulario().size()>0){
				String vetorStringAux[] = new String[1000];
				vetorStringAux = extraiStringGrupoFormulario( g.getListaGrupo_formulario() );
				for(Integer i = 0 ; i < vetorStringAux.length ; i++){
					vetorString[indice] = vetorStringAux[i];
					indice +=1 ;
				}
					
			}
			
			indiceGrupo+=1;
			
		}
		return vetorString;
	}
	
	public Integer relatorioFormularioChecklist(String arquivoLeitura,String caminhoGravacao, String nomeArquivo, String exportTo, Execucao_formulario exec_formulario){
		return this.relatorioFormulario(arquivoLeitura, caminhoGravacao, nomeArquivo, exportTo, exec_formulario);
	}
	
	public String formataData(String data){
		String[] arr = data.split(" ");
		List<String> arrMeses = new ArrayList<>();
		arrMeses.add("Jan");
		arrMeses.add("Feb");
		arrMeses.add("Mar");
		arrMeses.add("Jun");
		arrMeses.add("Jul");
		arrMeses.add("Aug");
		arrMeses.add("Sep");
		arrMeses.add("Oct");
		arrMeses.add("Nov");
		arrMeses.add("Dez");
		
		if(arr.length>3){
			return arr[2] + "/" + (arrMeses.indexOf( arr[1] ) + 1) + "/" +arr[arr.length-1];	
		}
		return "";
	}
	
	public String formataHora(String data){
		String[] arr = data.split(" ");
		if(arr.length>3){
			return arr[3];
		}
		return "";
	}
	
	public String retornaResultadoresposta(Campo_formulario campo, Resposta_exec_formulario resposta){
		String campoEResp="";
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		if(campo.getCd_tipo_campo().equals(3)){ 
			if(resposta.getDs_resposta_exec_formulario() == ""){
				campoEResp += "NÃO INFORMADO";
			}else{
				campoEResp += formataData(resposta.getDs_resposta_exec_formulario());	
			}
		}else if(campo.getCd_tipo_campo().equals(5)){
			pesquisa.buscarObjetoCompleto(resposta);
			if(resposta.getCd_resposta_campo_formulario() != null){
				if(resposta.getCampo_formulario() != null){
					for(Resposta_campo_formulario r: resposta.getCampo_formulario().getListaResposta_campo_formulario()){
						if(r.getCd_resposta_campo_formulario().equals(resposta.getCd_resposta_campo_formulario())){
							campoEResp += r.getDs_resposta_campo_formulario();
							break;
						}
					}
					
				}	
			}
		}else if(campo.getCd_tipo_campo().equals(8) ){
			if(resposta.getDs_resposta_exec_formulario() == ""){
				campoEResp += "NÃO INFORMADO";
			}else{
				campoEResp += formataHora(resposta.getDs_resposta_exec_formulario());	
			}
		}else if(campo.getCd_tipo_campo().equals(11)){
			if(resposta.getTp_resposta_exec_formulario() != null){
				if(resposta.getTp_resposta_exec_formulario().equals(0)){
					campoEResp += "NÃO CONFORME";
				}else{
					campoEResp += "CONFORME";
				}
			}else{
				campoEResp += "NÃO INFORMADO";
			}
		}else if(campo.getCd_tipo_campo().equals(15)){
			if(resposta.getTp_resposta_exec_formulario() != null){
				if(resposta.getTp_resposta_exec_formulario().equals(0)){
					campoEResp += "NÃO";
				}else{
					campoEResp += "SIM";
				}
			}else{
				campoEResp += "NÃO INFORMADO";
			}
		}else{
			if(resposta.getDs_resposta_exec_formulario() == null){
				campoEResp += "NÃO INFORMADO";	
			}else{
				campoEResp += resposta.getDs_resposta_exec_formulario();
			}
		}
		
		return campoEResp;
	}
	
	public Integer relatorioFormulario(String arquivoLeitura,String caminhoGravacao, String nomeArquivo, String exportTo, Execucao_formulario exec_formulario){
		
		this.setDao(new GenericDAO());
		List<Execucao_formulario> listaFormulario = new ArrayList<>();
		List<Plano_acao> listaPlanosAcao = new ArrayList<>();
		List<String> listaCampoNC = new ArrayList<>();
		String strFinal ="";
		String tabela = "";
		//Execucao_formulario exec_formulario = (Execucao_formulario) this.getDao().getSession().createCriteria(Execucao_formulario.class).add(Restrictions.eq("cd_formulario", formulario.getCd_formulario())).uniqueResult();
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();

		if(exec_formulario !=null ){

			//pesquisa.buscarObjetoCompleto(exec_formulario);

			if((exec_formulario.getFormulario().getTp_formato() != null) && (exec_formulario.getFormulario().getTp_formato().equals(2))){

				for(Sub_formulario sub : exec_formulario.getFormulario().getListaSub_formulario()){
					Setor setor = (Setor) this.getDao().getSession().createCriteria(Setor.class).add(Restrictions.eq("cd_setor", sub.getVl_filtro())).uniqueResult();

					strFinal+= setor.getNm_setor() +"<br>";

					for(Grupo_formulario grupo : sub.getListaGrupo_formulario()){
						for(Grupo_formulario grupoFilho: grupo.getListaGrupo_formulario()){

							strFinal += this.montarHTML_Campo_formulario(grupoFilho, exec_formulario.getListaRespostas_execucao_formulario());
						}

						strFinal += this.montarHTML_Campo_formulario(grupo, exec_formulario.getListaRespostas_execucao_formulario());
					}
				}
			}else{

				for(Grupo_formulario grupo : exec_formulario.getFormulario().getListaGrupo_formulario()){
					if( grupo.getListaGrupo_formulario().size() > 0  ){

						for(Grupo_formulario grupoFilho: grupo.getListaGrupo_formulario()){
							strFinal += this.montarHTML_Campo_formulario(grupoFilho, exec_formulario.getListaRespostas_execucao_formulario());
						}

						strFinal += this.montarHTML_Campo_formulario(grupo, exec_formulario.getListaRespostas_execucao_formulario());
					}else{

						strFinal += this.montarHTML_Campo_formulario(grupo, exec_formulario.getListaRespostas_execucao_formulario());
					}
					
				}
				
			}
			
			exec_formulario.setDs_descricao(strFinal);
			listaFormulario.add(exec_formulario);
		}else{
			return 0;
		}
		
		for(Plano_acao p : listaPlanosAcao){
			pesquisa.buscarObjetoCompleto(p);
		}
		
		System.out.println(">"+strFinal);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("strFinal", strFinal);
		parametros.put("listaPlanosAcao", listaPlanosAcao);
		
		return this.gerarRelatorio(listaFormulario,parametros, arquivoLeitura, caminhoGravacao, nomeArquivo, exportTo);
	}
	
	public Integer historicoDiagramas(String arquivoLeitura,String caminhoGravacao, String nomeArquivo, String exportTo, String strHTML){
		
		Diagrama_helpdesk d = new Diagrama_helpdesk();
		d.setDs_comentario(strHTML);
		List<Diagrama_helpdesk> listaDiagramas = new ArrayList<>();
		listaDiagramas.add( d );
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("strHTML", strHTML);
		
		try {
			JasperReport pathjrxml = JasperCompileManager.compileReport( arquivoLeitura );
			JasperPrint printReport = JasperFillManager.fillReport(pathjrxml, parametros, new JRBeanCollectionDataSource(listaDiagramas));
			JasperExportManager.exportReportToPdfFile(printReport, caminhoGravacao+"//"+ nomeArquivo +".pdf");
			
		} catch (JRException e) {
			e.printStackTrace();
			return 1;
		}
		
		return 0;
	}
	
	public Integer relatorioReuniao(String arquivoLeitura,String caminhoGravacao, String nomeArquivo, String exportTo, Executar_reuniao exec){
		
		List<Executar_reuniao> listaReuniao = new ArrayList<>();
		listaReuniao.add(exec);
		
		return this.gerarRelatorio(listaReuniao,null, arquivoLeitura, caminhoGravacao, nomeArquivo, exportTo);
		
	}
	
	public static String codigoFixo(String nm_tabela, String nm_campo, Integer valor){
		dao = new GenericDAO();
		Tabela tabela = (Tabela) dao.getSession().createCriteria(Tabela.class).add(Restrictions.eq("nm_tabela", nm_tabela)).uniqueResult();
		if(tabela!=null){
			List<Campo> listaCampo = (List<Campo>) dao.getSession().createCriteria(Campo.class).add(Restrictions.eq("cd_tabela", tabela.getCd_tabela())).list();
			if(listaCampo!=null){
				for(Campo c:listaCampo){
					if(c.getNm_campo().equals(nm_campo)){
						List<CodigosFixos> listaCodigosfixos = (List<CodigosFixos>) dao.getSession().createCriteria(CodigosFixos.class)
								.add(Restrictions.eq("cd_campo", c.getCd_campo())).list();
						for(CodigosFixos cf:listaCodigosfixos){
							if(cf.getVl_valor().equals(valor)){
								return cf.getDs_descricao();
							}
						}
						break;
					}
					
				}
			}
		}
		return "";
	}
	
	public static String toText(String tabela, String campoBusca, String campoResultado, String cod){
		System.out.println(tabela+" <> "+campoBusca+" <> "+campoResultado+" <> "+cod);
		
		if(cod!=null && cod!="null"){
		
			dao = new GenericDAO();
			
			String classeAux = tabela;
			String inicial=tabela.substring(0,1);
			tabela = inicial.toUpperCase()+tabela.substring(1, tabela.length());
			classeAux += "."+tabela;
			
			System.out.println("classeAux: "+classeAux+"<<");
			System.out.println("SELECT "+campoResultado+" FROM "+tabela+" where "+campoBusca+" = "+cod);
			
			Object obj = null;
			String resultado="";
			
			try {
				if( tabela != null && tabela != "" &&
					campoResultado != null && campoResultado != "" && 
					campoBusca != null && campoBusca != "" && 
					cod != null && cod != "" ){
					
					obj = dao.findBySqlObject("SELECT "+campoResultado+" FROM "+tabela+" where "+campoBusca+" = "+cod, Class.forName("br.com.primum.modelo."+classeAux).newInstance());
					Field[] fields = obj.getClass().getDeclaredFields();
					for (Field field : fields) {
						System.out.println("field.getName(): "+field.getName());
						if(field.getName().equals(campoResultado)){
							if(Reflection.getValorCampo(obj, field.getName())!=null){
								
								if( Reflection.getValorCampo(obj, field.getName()) instanceof java.util.Date){
									resultado = ((java.util.Date) Reflection.getValorCampo(obj, field.getName())).toLocaleString();
								}else{
									resultado = Reflection.getValorCampo(obj, field.getName()).toString();
								}
								return resultado;
							}else{
								break;
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				return resultado;
			}
			return resultado;
		}else{
			return "Não informado";
		}
	}
	

	/*********************/
	/* GERAR RELATORIO */
	public Integer gerarRelatorio(List<?> listaObjeto,Map<String, Object> parametro, String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo){

		Integer cod_erro = 1;

		if(listaObjeto.size()>0){
			try {

				cod_erro = -1;

				JasperReport pathjrxml = JasperCompileManager.compileReport( arqLeitura );
				JasperPrint printReport = JasperFillManager.fillReport(pathjrxml, parametro, new JRBeanCollectionDataSource(listaObjeto));

				JasperExportManager.exportReportToPdfFile(printReport, caminhoGravacao+"//"+ nomeArquivo +".pdf");
				JasperExportManager.exportReportToHtmlFile(printReport, caminhoGravacao+"//"+ nomeArquivo +".html");

				//JasperExportManager.exportReportToHtmlFile(printReport, caminhoGravacao+"\\"+ nomeArquivo +".html");
				//JasperViewer.viewReport(printReport,false);
				
			} catch (JRException e) {

				cod_erro = 1;
				e.printStackTrace();
			}
		}
		
		//código do erro 
		//0 - arquivo nao contém páginas
		//1 - outro erro
		
//		try {
//			JasperExportManager.exportReportToPdfFile(print, caminhoGravacao+"\\"+ nomeArquivo +".pdf");
//		} catch (JRException e) {
//			resultado=false;
//			e.printStackTrace();
//		}
		
		//case "pdf":
		//JasperExportManager.exportReportToPdfFile(print, caminhoGravacao+"\\"+ nomeArquivo +".pdf");
		//break;
		//
		//case "html":
		//JasperExportManager.exportReportToHtmlFile(print, caminhoGravacao+"\\"+ nomeArquivo +".html");
		//break;
		//
		//case "xml":
		//JasperExportManager.exportReportToXmlFile(print, caminhoGravacao+"\\"+ nomeArquivo +".xml", true);
		//break;
		return cod_erro;
	}
	/* GERAR RELATORIO */
	/*********************/
	
	/*******/
	
	@SuppressWarnings("unchecked")
	public List<?> gerarGraficoFormulariosPorProgramaQualidade(){
		
		this.setDao(new GenericDAO());
		
		List<Programa_qualidade> listaProgramasQualidade = (List<Programa_qualidade>) this.getDao().getSession().createCriteria(Programa_qualidade.class).list();
		
		List<String> listaNomesPrograma = new ArrayList<>();
		List<Integer> listaQtdeFormularios = new ArrayList<>();
		
		List<List<?>> listaGeral = new ArrayList<>();
		
		for(Programa_qualidade prog : listaProgramasQualidade){
			Integer qtde = this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_programa_qualidade", prog.getCd_programa_qualidade())).list().size();
			if( qtde > 0){
				listaNomesPrograma.add( prog.getNm_programa_qualidade() );
				listaQtdeFormularios.add( this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_programa_qualidade", prog.getCd_programa_qualidade())).list().size() );
			}
		}
		
		listaGeral.add(listaNomesPrograma);
		listaGeral.add(listaQtdeFormularios);
		
		return listaGeral;
	}
	/*******/
	
	@SuppressWarnings("unchecked")
	public static Object getStringExecucaoFormularioVerificacao(Integer cd_formulario_verificacao, String campo){
//		dao = new GenericDAO();
//		List<Execucao_formulario> listaExec = ( List<Execucao_formulario> ) dao.getSession().createCriteria(Execucao_formulario.class)
//				.add(Restrictions.eq("cd_formulario", cd_formulario_verificacao))
//				.addOrder(Order.desc("cd_execucao_formulario"))
//				.list();
//		
//		if(listaExec!=null){
//			if(listaExec.size()>0){
//				Field[] fields = listaExec.get(0).getClass().getDeclaredFields();
//				try {
//					for (Field field : fields) {
//						if(field.getName().equals(campo)){
//							if(Reflection.getValorCampo( listaExec.get(0), field.getName() )!=null){
//								return Reflection.getValorCampo( listaExec.get(0), field.getName() );
//							}
//							return "";
//						}
//					}
//				}catch (Exception e) {
//
//				}
//			}
//		}
		return "";
	}

	private String montarHTML_Campo_formulario(Grupo_formulario grupo, List<Resposta_exec_formulario> listaResposta_Execucoes) {

		String tabela = "<table width='530' border='1'><tr><td/>" + grupo.getNm_grupo_formulario() + "</td><td>&nbsp;</td></tr>";
		for(Campo_formulario campo: grupo.getListaCampo_formulario()){
			String campoEResp= "<tr><td>" + campo.getNm_campo_formulario() + ":</td>";
			campoEResp += "<td>";
			for(Resposta_exec_formulario resp_exec_form : listaResposta_Execucoes){
				if(resp_exec_form.getCd_campo_formulario().equals(campo.getCd_campo_formulario())){

					campoEResp += retornaResultadoresposta(campo, resp_exec_form);

					if( resp_exec_form.getTp_resposta_exec_formulario() != null && resp_exec_form.getTp_resposta_exec_formulario().equals(0)){
						Plano_acao_formulario planoAux = (Plano_acao_formulario) this.getDao().getSession().createCriteria(Plano_acao_formulario.class).add(Restrictions.eq("cd_resposta_exec_formulario", resp_exec_form.getCd_resposta_exec_formulario())).uniqueResult();
						if(planoAux!=null){
							Plano_acao planoFinal = (Plano_acao) this.getDao().getSession().createCriteria(Plano_acao.class).add(Restrictions.eq("cd_plano_acao", planoAux.getCd_plano_acao())).uniqueResult();
							//listaPlanosAcao.add(planoFinal);
							//listaCampoNC.add(""+campo.getNm_campo_formulario());
							campoEResp="<style forecolor='#FF0000'>"+ campoEResp + "</style> - Cód. plano ação: "+planoFinal.getCd_plano_acao();
						}
					}
				}
			}

			tabela += campoEResp +"</td></tr>";
		}

		tabela += "</table>";
		return tabela;
	}
}