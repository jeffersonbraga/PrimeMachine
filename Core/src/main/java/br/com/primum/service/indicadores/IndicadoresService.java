package br.com.primum.service.indicadores;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.indicadores.IndicadoresBO;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.conclusao_auditoria.Conclusao_auditoria;
import br.com.primum.modelo.cronograma_auditoria.Cronograma_auditoria;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.indicador_customizado.Indicador_customizado;
import br.com.primum.modelo.modulo_indicadores.Modulo_indicadores;
import br.com.primum.modelo.programa_qualidade.Programa_qualidade;
import br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario;
import br.com.primum.modelo.unidade.Unidade;

public class IndicadoresService {
	
	private IndicadoresBO indicadoresBO;

	public IndicadoresBO getIndicadoresBO() {
		return indicadoresBO;
	}

	public void setIndicadoresBO(IndicadoresBO indicadoresBO) {
		this.indicadoresBO = indicadoresBO;
	}
	
	// ******************************************
	// ***********  AUDITORIA  ******************
	public List<?> Auditoria_ScorePorAvaliacao(Integer cd_cronograma_auditoria){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().Auditoria_ScorePorAvaliacao( cd_cronograma_auditoria );
	}
	
	public List<?> Auditoria_Pontuacao_e_ScoreConformidade(){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().Auditoria_Pontuacao_e_ScoreConformidade();
	}
	
	//public List<?> Auditoria_EvolucaoPlanoDeAuditoria(Integer cd_auditoria, Integer cd_pessoa_juridica, Integer tp_cronograma_auditoria){
	public List<?> Auditoria_EvolucaoPlanoDeAuditoria( Integer cd_auditoria ){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().Auditoria_EvolucaoPlanoDeAuditoria( cd_auditoria );
	}
	
	public List<?> Auditoria_NCsPorSituacao(Integer cd_cronograma_auditoria){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().Auditoria_NCsPorSituacao(cd_cronograma_auditoria);
	}

	// ******************************************
	// ***********  OUVIDORIA  ******************
	public List<?> Ouvidoria_chamados_por_periodo(Integer ano){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().Ouvidoria_chamados_por_periodo(ano);
	}
	
	public List<?> Ouvidoria_chamadosRegistradosXFinalizados(){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().Ouvidoria_chamadosRegistradosXFinalizados();
	}
	
	public List<?> Ouvidoria_principais_reclamacoes(Date dataInicio, Date dataFim){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().Ouvidoria_principais_reclamacoes( dataInicio, dataFim);
	}
	
	public List<?> Ouvidoria_reclamacoes_por_periodo(Integer cd_classificacao, Date dataInicio, Date dataFim){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().Ouvidoria_reclamacoes_por_periodo(cd_classificacao, dataInicio, dataFim);
	}
	
	// ******************************************
	// ***********  HELPDESK ********************
	public List<List<?>> chamadosPorStatus(){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().chamadosPorStatus();
	}	
	
	public List<List<?>> chamadosPorStatusFiltrado(Date data_Inicial, Date data_Final, Unidade u ){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().chamadosPorStatusFiltrado( data_Inicial, data_Final, u);
	}
	
	public List<List<?>> chamadosPorStatusEPeriodoGeral(){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().chamadosPorStatusEPeriodoGeral();
	}
	
	public List<List<?>> chamadosPorStatusEPeriodo(Date data_Inicial, Date data_Final, Unidade u){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().chamadosPorStatusEPeriodo(data_Inicial, data_Final, u);
	}
	
	public List<List<?>> chamadosPorMotivoAbertura(){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().chamadosPorMotivoAbertura();
	}
	
	public List<List<?>> chamadosPorMotivoAberturaFiltrado(Date data_Inicial, Date data_Final, Unidade u){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().chamadosPorMotivoAberturaFiltrado( data_Inicial, data_Final, u );
	}
	
	public List<List<?>> chamadosPorMotivoAberturaClassificacao(String ds_classificacao){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().chamadosPorMotivoAberturaClassificacao( ds_classificacao );
	}
	
	public List<List<?>> chamadosPorMotivoAberturaClassificacao(String ds_classificacao, Date data_Inicial, Date data_Final, Unidade u){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().chamadosPorMotivoAberturaClassificacao( ds_classificacao, data_Inicial, data_Final, u );
	}
	
	public List<List<?>> chamadosPorUnidadePrazo(Date data_Inicial, Date data_Final){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().chamadosPorUnidadePrazo( data_Inicial, data_Final );
	}
	
	public List<List<?>> chamadosPorSetorDaUnidade(String ds_unidade, Date data_Inicial, Date data_Final){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().chamadosPorSetorDaUnidade(ds_unidade, data_Inicial, data_Final );
	}
	
	public List<List<?>> chamadosAbertosPorAtendente( Date data_Inicial, Date data_Final, Unidade u  ){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().chamadosAbertosPorAtendente( data_Inicial, data_Final, u );
	}
	public List<List<?>> chamadosAbertosPorAtendenteClassificacao(Integer cd_usuario, Date data_Inicial, Date data_Final, Unidade u  ){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().chamadosAbertosPorAtendenteClassificacao(cd_usuario, data_Inicial, data_Final, u );
	}
	// ******************************************
	// ***********  MANUTENÇÃO  ******************
	
	//******************************************
	// ***************  ACTION  ****************
	// ------------   GRUPO 1 ----------------
	public List<Integer> atividadesExecutadas(Integer cd_usuario, Integer cd_unidade, Date dataInicio, Date dataFim){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().atividadesExecutadas(cd_usuario, cd_unidade, dataInicio, dataFim);
	}
	
	public List<List<?>> atividadesExecutadasTodos(Integer cd_unidade, Date dataInicio, Date dataFim){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().atividadesExecutadasTodos( cd_unidade, dataInicio, dataFim);
	}
	
	
	public List<Float> tempoMedioExecucao(Integer cd_usuario, Integer cd_unidade, Date dataInicio, Date dataFim){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().tempoMedioExecucao(cd_usuario, cd_unidade, dataInicio, dataFim);
	}
	
	public List<List<?>> tempoMedioExecucaoTodos(Integer cd_unidade, Date dataInicio, Date dataFim){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().tempoMedioExecucaoTodos( cd_unidade, dataInicio, dataFim);
	}
	
	public List<Integer> tempoPrevistoTempoUtilizado(Integer cd_usuario, Integer cd_unidade, Date dataInicio, Date dataFim){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().tempoPrevistoTempoUtilizado(cd_usuario, cd_unidade,  dataInicio, dataFim);
	}
	
	public List<List<?>> diaPorcentagemExecutadas(Integer cd_usuario, Integer cd_unidade, Date dataInicio, Date dataFim){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().diaPorcentagemExecutadas(cd_usuario, cd_unidade, dataInicio, dataFim);
	}
	
	public List<List<?>> diaQtdeExecucoes( Date dataInicio, Date dataFim ){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().diaQtdeExecucoes( dataInicio, dataFim);
	}
	
	public List<List<?>> qtdeExecucoesPorProgramaQualidade( Date dataInicio, Date dataFim ){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().qtdeExecucoesPorProgramaQualidade( dataInicio, dataFim );
	}
	
	public List<Execucao_formulario> respostasCNCPorFormulario(Integer cd_formulario, Date dataInicio, Date dataFim ){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().respostasCNCPorFormulario( cd_formulario, dataInicio, dataFim );
	}
	
	public List<Indicador_customizado> getIndicadoresPorPermissao(Integer cd_usuario){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().getIndicadoresPorPermissao( cd_usuario );
	}

	
	// ------------   GRUPO 2 ----------------
	
	// ------------   GRUPO 3 ----------------
	
	public List<List<?>> ncsPorSetor( Integer cd_unidade, Date dt_inicio, Date dt_fim ){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().ncsPorSetor( cd_unidade, dt_inicio, dt_fim);
	}
	
	public List< Conclusao_auditoria > desempenhoAuditorias( Cronograma_auditoria c, Date data_inicio, Date data_fim ){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().desempenhoAuditorias(c, data_inicio, data_fim);
	}
	
	public List<List<?>> NcsPorPeriodo( Date dt_inicio, Date dt_fim){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().NcsPorPeriodo( dt_inicio, dt_fim);
	}
	
	public List<BigDecimal> nivelNcs(Integer cd_unidade, Date dt_inicio, Date dt_fim){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().nivelNcs( cd_unidade, dt_inicio, dt_fim);
	}
	
	public List<List<?>> NcsPorProgramaQualidade( Date dt_inicio, Date dt_fim){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().NcsPorProgramaQualidade( dt_inicio, dt_fim);
	}
	
	
	//******************************************
	// ***************  OUTROS  ****************
	public List<Modulo_indicadores> buscarIndicadoresModulos(){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().buscarIndicadoresModulos();
	}
	
	public Boolean agendarEnvio( Integer cd_config_indicadores, String jobName, Date dataNotificar ){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().agendarEnvio( cd_config_indicadores, jobName, dataNotificar );
	}
	
	 
	public List<Resposta_exec_formulario> indicadoresTemperatura( List<List<?>> listaFormularios, Date dt_inicio, Date dt_fim, String st_tipo_turno){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().indicadoresTemperatura( listaFormularios, dt_inicio, dt_fim, 0);
	}
	
	public List<Resposta_exec_formulario> indicadoresTemperatura( List<List<?>> listaFormularios, Date dt_inicio, Date dt_fim, Integer st_tipo_turno){
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().indicadoresTemperatura( listaFormularios, dt_inicio, dt_fim, st_tipo_turno);
	}
	 
	public String indiceNcEMetas( List<Indicador_customizado> listaIndicadores , List<List<List<?>>> listaTotal, Date dataInicial , Date dataFinal , Integer cd_tipo_turno) {
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().indiceNcEMetas( listaIndicadores, listaTotal, dataInicial, dataFinal, cd_tipo_turno);
	}
	
	public List<List<?>> indiceNcEMetasPorIndicador( List<List<?>> listaItens , Indicador_customizado indicador , Date dataInicial , Date dataFinal , Integer cd_tipo_turno) {
		this.setIndicadoresBO(new IndicadoresBO());
		return getIndicadoresBO().indiceNcEMetasPorIndicador( listaItens, indicador, dataInicial, dataFinal, cd_tipo_turno);
	}
	
	
	 
}
