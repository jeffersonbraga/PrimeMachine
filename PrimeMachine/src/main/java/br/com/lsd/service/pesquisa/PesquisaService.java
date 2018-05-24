package br.com.lsd.service.pesquisa;

import java.util.Date;
import java.util.List;

import br.com.lsd.bo.pesquisa.PesquisaBO;
import br.com.primum.modelo.campo_formulario.Campo_formulario;
import br.com.primum.modelo.categoria_ishikawa.Categoria_ishikawa;
import br.com.primum.modelo.documento.Documento;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.modelo.setor.Setor;
import br.com.primum.modelo.periodicidade.Periodicidade;

public class PesquisaService {

	private PesquisaBO pesquisaBO;
	
	public PesquisaService() {
		
	}

	public PesquisaBO getPesquisaBO() {
		return pesquisaBO;
	}

	public void setPesquisaBO(PesquisaBO pesquisaBO) {
		this.pesquisaBO = pesquisaBO;
	}

	public List<?> pesquisa(String datafield, String valor) throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().pesquisar(datafield, valor);
	}

	public List<?> getListaObjetos(String ds_caminhoclasse, Boolean buscarCompleto, String campoOrdenar, Integer tipoOrdenacao) throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaObjetos(ds_caminhoclasse, buscarCompleto, campoOrdenar, tipoOrdenacao);
	}

	public List<?> getListaTabelasNotificacao() throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaTabelasNotificacao();
	}

	public List<?> getObjetoByTabelaEID(Integer cd_tabela, Integer cd_obj) throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getObjetoByTabelaEID(cd_tabela, cd_obj);
	}

	public Boolean criarBeans(String nm_tabela) throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().criarBeans(nm_tabela);
	}

	public Integer verificaNroSequencial() throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().verificaNroSequencial();
	}
	
	public Integer verificaNroSequencialParametros(String chavePrimaria, String tabela) throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().verificaNroSequencialParametros( chavePrimaria, tabela );
	}

	public Integer mudaRevisao(Integer numRevisao, Integer revisaoAtual){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().mudaRevisao(numRevisao, revisaoAtual);
	}

	public Integer getNumDocumento(Integer tp_documento, Integer tp_processo){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getNumDocumento(tp_documento, tp_processo);
	}

	public Integer verificaExistencia(Integer tp_documento, Integer tp_processo, Integer nr_sequencial){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().verificaExistencia(tp_documento, tp_processo, nr_sequencial);
	}

	public List<Documento> getListaDocumentos(Integer cd_usuario){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaDocumentos(cd_usuario);
	}

	public List<List<?>> getListaDocumentosListaMestra(){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaDocumentosListaMestra();
	}

	public List<Helpdesk_ocorrencia> getListaHelpDesks(Integer cd_usuario, Date dt_inicial, Date dt_final, Integer cd_unidade, Integer st_hd, Boolean buscarComFiltro){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaHelpDesks(cd_usuario, dt_inicial, dt_final, cd_unidade, st_hd, buscarComFiltro);
	}

	public List<Helpdesk_ocorrencia> getListaHelpDesksAbertos(Integer cd_usuario){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaHelpDesksAbertos(cd_usuario);
	}

	public List<Helpdesk_ocorrencia> getListaHelpDesksAnalise(Integer cd_usuario){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaHelpDesksAnalise(cd_usuario);
	}

	public List<Categoria_ishikawa> getCategoriasIshikawa(){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getCategoriasIshikawa();
	}

	public List<Setor> pesquisaServiceSetor(Integer cd_unidade) {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().pesquisaServiceSetor(cd_unidade);		
	}

	public List<Periodicidade> getPeriodicidadePorSituacao(){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getPeriodicidadePorSituacao();
	}

	public Campo_formulario buscarCampoIdentificador(Formulario formulario) {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().buscarCampoIdentificador(formulario);
	}
}