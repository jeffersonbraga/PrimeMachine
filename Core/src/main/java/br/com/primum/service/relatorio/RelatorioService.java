package br.com.primum.service.relatorio;

import java.sql.Date;
import java.util.List;

import br.com.primum.bo.relatorios.RelatorioBO;
import br.com.primum.modelo.cronograma_manutencao.Cronograma_manutencao;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.executar_reuniao.Executar_reuniao;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.modelo.manutencao_preventiva.Manutencao_preventiva;
import br.com.primum.modelo.plano_acao.Plano_acao;
import br.com.primum.modelo.revisao_programa_qualidade.Revisao_programa_qualidade;

public class RelatorioService {

	private RelatorioBO relatorioBO;

	public RelatorioBO getRelatorioBO() {
		return relatorioBO;
	}

	public void setRelatorioBO(RelatorioBO relatorioBO) {
		this.relatorioBO = relatorioBO;
	}
	
	public Integer relatorioTreinamentoPorColaborador(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Integer cod_colaborador){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioTreinamentoPorColaborador(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, cod_colaborador);
	}
	
	public Integer relatorioTreinamentoPorPeriodo(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Date dataInicio, Date dataFim){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioTreinamentoPorPeriodo(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, dataInicio, dataFim);
	}
	
	public Integer relatorioAuditoria(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Integer cd_cronograma_auditoria){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioAuditoria(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, cd_cronograma_auditoria);
	}
	
	public Integer relatorioOuvidoria(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Integer cod_chamado){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioOuvidoria(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, cod_chamado);
	}
	
	public Integer relatorioOuvidoriaPorPeriodo(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Date dataInicio, Date dataFim){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioOuvidoriaPorPeriodo(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, dataInicio, dataFim);
	}
	
	
	public Integer relatorioOrdemServicoManutencao(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Cronograma_manutencao cronograma){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioOrdemServicoManutencao(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, cronograma);
	}

	public Integer relatorioManutencaoPreventiva(String arquivoLeitura,String caminhoGravacao, String nomeArquivo, String exportTo, Manutencao_preventiva manutencao){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioManutencaoPreventiva(arquivoLeitura, caminhoGravacao, nomeArquivo, exportTo, manutencao);
	}

	public Integer relatorioManutencaoCorretiva(String arquivoLeitura,String caminhoGravacao, String nomeArquivo, String exportTo, Plano_acao plano){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioManutencaoCorretiva(arquivoLeitura, caminhoGravacao, nomeArquivo, exportTo, plano);
	}

	public Integer relatorioFormularioPeriodo(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Formulario form, Date data_inicio, Date data_fim){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioFormularioPeriodo(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, form , data_inicio, data_fim);
	}
	
	public Integer relatorioFormulario(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Execucao_formulario exec_formulario){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioFormulario(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, exec_formulario);
	}
	
	public Integer relatorioFormularioChecklist(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Execucao_formulario exec_formulario){
		try {
			this.setRelatorioBO( new RelatorioBO() );
			return this.getRelatorioBO().relatorioFormularioChecklist(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, exec_formulario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 1;
	}
	
	public Integer relatorioFormularioVerificacao(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Execucao_formulario exec_formulario){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioFormularioVerificacao(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, exec_formulario);
	}
	
	
	
	public Integer relatorioReuniao(String arquivoLeitura,String caminhoGravacao, String nomeArquivo, String exportTo, Executar_reuniao exec){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioReuniao( arquivoLeitura, caminhoGravacao, nomeArquivo, exportTo, exec );
	}
	
	public Integer relatorioHelpdesk(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Helpdesk_ocorrencia hd){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioHelpdesk(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, hd);
	}
	
	public Integer historicoDiagramas(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, String strHTML){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().historicoDiagramas(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, strHTML);
	}
	
	public Integer relatorioRevisaoProgramaQualidade(String arqLeitura, String caminhoGravacao, String nomeArquivo, String exportTo, Integer cd_revisao_programa_qualidade){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().relatorioRevisaoProgramaQualidade(arqLeitura, caminhoGravacao, nomeArquivo, exportTo, cd_revisao_programa_qualidade);
	}
	
	public String toText(String tabela, String campoBusca, String campoResultado, String cod){
		return RelatorioBO.toText(tabela, campoBusca, campoResultado, cod);
	}
	
	public List<?> gerarGrafico(){
		this.setRelatorioBO( new RelatorioBO() );
		return this.getRelatorioBO().gerarGraficoFormulariosPorProgramaQualidade();
	}
	
}
