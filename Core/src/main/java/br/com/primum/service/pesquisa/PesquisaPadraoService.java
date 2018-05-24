 package br.com.primum.service.pesquisa;

import java.util.List;

import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.modelo.campospesquisa.CamposPesquisa;
import br.com.primum.modelo.codigosfixos.CodigosFixos;
import br.com.primum.modelo.programa.Programa;

public class PesquisaPadraoService {

	private PesquisaPadraoBO pesquisaPadraoBO;
	
	public PesquisaPadraoService() {
		
	}

	public PesquisaPadraoBO getPesquisaPadraoBO() {
		return pesquisaPadraoBO;
	}

	public void setPesquisaPadraoBO(PesquisaPadraoBO pesquisaPadraoBO) {
		this.pesquisaPadraoBO = pesquisaPadraoBO;
	}

	public Programa carregarDadosJanelaPesquisa(Integer cd_programa) throws Exception {
		this.setPesquisaPadraoBO(new PesquisaPadraoBO());
		return this.getPesquisaPadraoBO().carregarDadosJanelaPesquisa(cd_programa);
	}

	public List<?> pesquisarValores(Integer cd_programa, Integer vl_filtro) throws Exception {
		this.setPesquisaPadraoBO(new PesquisaPadraoBO());
		return this.getPesquisaPadraoBO().pesquisarValores(cd_programa, vl_filtro);
	}

	public List<?> buscarConjuntoRegistrosProgramas(Integer cd_programa, List<Object> listaTabelasProcurar, Object valorBuscar) throws Exception {
		this.setPesquisaPadraoBO(new PesquisaPadraoBO());
		return this.getPesquisaPadraoBO().buscarConjuntoRegistrosProgramas(cd_programa, listaTabelasProcurar, valorBuscar);
	}

	public List<?> efetuarBuscaRegistro(Object objeto) throws Exception {
		this.setPesquisaPadraoBO(new PesquisaPadraoBO());
		return this.getPesquisaPadraoBO().efetuarBuscaRegistro(objeto);
	}

	public List<?> efetuarBuscaRegistroCompleto(Object buscar, Object filtro) throws Exception {
		this.setPesquisaPadraoBO(new PesquisaPadraoBO());
		return this.getPesquisaPadraoBO().efetuarBuscaRegistroCompleto(buscar, filtro);
	}

	public List<?> buscarLabelFunctionPadraoValorExterno(Integer cd_tabela) throws Exception {
		this.setPesquisaPadraoBO(new PesquisaPadraoBO());
		return this.getPesquisaPadraoBO().buscarLabelFunctionPadraoValorExterno(cd_tabela);
	}
	
	public List<?> buscarInformacaoCampoEstrangeiro(CamposPesquisa campoPesquisa) throws Exception {
		this.setPesquisaPadraoBO(new PesquisaPadraoBO());
		return this.getPesquisaPadraoBO().buscarInformacaoCampoEstrangeiro(campoPesquisa);
	}
	
	public List<?> buscarInformacaoFiltroCampoEstrangeiro(Integer cd_tabela_origem) throws Exception {
		this.setPesquisaPadraoBO(new PesquisaPadraoBO());
		return this.getPesquisaPadraoBO().buscarInformacaoFiltroCampoEstrangeiro( cd_tabela_origem );
	}
	
	public List<CodigosFixos> buscarInformacaoCodigosFixos( Integer cd_tabela, String nm_campo ) {
		this.setPesquisaPadraoBO(new PesquisaPadraoBO());
		return this.getPesquisaPadraoBO().buscarInformacaoCodigosFixos( cd_tabela, nm_campo);
	}
	
	public Object buscarObjetoCompleto( Object obj ) {
		this.setPesquisaPadraoBO(new PesquisaPadraoBO());
		return this.getPesquisaPadraoBO().buscarObjetoCompleto( obj );
	}
}