package br.com.primum.service.crud;

import java.util.List;

import br.com.primum.bo.crud.DBSearchBO;
import br.com.primum.ferramentas.ObjetoVirtual;
import br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario;

public class DBSearchService {

	private DBSearchBO dbSearch;

	public DBSearchBO getDbSearch() {
		return dbSearch;
	}

	public void setDbSearch(DBSearchBO dbSearch) {
		this.dbSearch = dbSearch;
	}

	public Object efetuarBuscaValor(String tabela, String campo, String valor, String classeJava) throws Exception {
		this.setDbSearch(new DBSearchBO());
		return this.getDbSearch().efetuarBuscaValor(tabela, campo, valor, classeJava);
	}

	public List<?> buscarTodosRegistros(String tabela, String classeJava) {
		this.setDbSearch(new DBSearchBO());
		return this.getDbSearch().buscarTodosRegistros(tabela, classeJava);
	}
	
	public List<?> buscarRegistrosListaAuxiliar(String nomePropriedade, Integer valorPropriedade, String nomeLista, String classeJava) {
		this.setDbSearch(new DBSearchBO());
		return this.getDbSearch().buscarRegistrosListaAuxiliar(nomePropriedade, valorPropriedade, nomeLista, classeJava);
	}

	public List<?> buscarTodosRegistrosCompleto(String tabela, String classeJava) {
		this.setDbSearch(new DBSearchBO());
		return this.getDbSearch().buscarTodosRegistrosCompleto(tabela, classeJava);
	}

	public Object efetuarBuscaValorCompleto(String tabela, String campo, String valor, String classeJava) throws Exception {
		this.setDbSearch(new DBSearchBO());
		return this.getDbSearch().efetuarBuscaValorCompleto(tabela, campo, valor, classeJava);
	}

	public Object efetuarBuscaValorFiltro(String tabela, String campo, String valor, String classeJava, String campoFiltro, Object valorFiltro) throws Exception {
		this.setDbSearch(new DBSearchBO());
		return this.getDbSearch().efetuarBuscaValorFiltro(tabela, campo, valor, classeJava, campoFiltro, valorFiltro);
	}

	public List<?> buscarRegistrosFiltro(String tabela, String classeJava, String campoFiltro, Object valorFiltro) {
		this.setDbSearch(new DBSearchBO());
		return this.getDbSearch().buscarRegistrosFiltro(tabela, classeJava, campoFiltro, valorFiltro);
	}

	public Object efetuarBuscaValorCompletoAutomatizada(String tabela, String campo, String valor, String classeJava) throws Exception {
		this.setDbSearch(new DBSearchBO());
		return this.getDbSearch().efetuarBuscaValorCompletoAutomatizada(tabela, campo, valor, classeJava);
	}

	public Object efetuarBuscaValorFiltroAutomatizada(String tabela, String campo, String valor, String classeJava, String campoFiltro, Object valorFiltro) throws Exception {
		this.setDbSearch(new DBSearchBO());
		return this.getDbSearch().efetuarBuscaValorFiltroAutomatizada(tabela, campo, valor, classeJava, campoFiltro, valorFiltro);
	}

	public List<Resposta_exec_formulario> buscarDadosSpinner(ObjetoVirtual obj) throws Exception {
		this.setDbSearch(new DBSearchBO());
		return this.getDbSearch().buscarDadosSpinner(obj);
	}
}