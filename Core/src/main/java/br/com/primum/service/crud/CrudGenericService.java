package br.com.primum.service.crud;

import java.util.List;

import br.com.primum.bo.crud.CrudGenericBO;

public class CrudGenericService {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	private CrudGenericBO crudBo;

	public CrudGenericBO getCrudBo() {

		return crudBo;
	}

	public void setCrudBo(CrudGenericBO crudBo) {

		this.crudBo = crudBo;
	}

	public CrudGenericService() {

	}

	public Object salvar(Object obj) throws Exception {
		this.setCrudBo(new CrudGenericBO());
		return this.getCrudBo().salvar(obj);
	}

	public void atualizar(Object obj) throws Exception {

		this.setCrudBo(new CrudGenericBO());
		this.getCrudBo().atualizar(obj);
	}

	public void deletar(Object obj) throws Exception {

		this.setCrudBo(new CrudGenericBO());
		this.getCrudBo().deletar(obj);
	}

	public Object buscarGenerico(Object obj, Integer id) throws Exception {

		this.setCrudBo(new CrudGenericBO());
		Object objeto = this.getCrudBo().buscarGenerico(obj, id);
		return objeto;
	}

	public boolean salvarObjetoLista(Object obj, List<?> listaPersistir, List<?> listaExcluir) throws Exception {
		this.setCrudBo(new CrudGenericBO());
		return this.getCrudBo().salvarObjetoLista(obj, listaPersistir, listaExcluir);
	}
	
	public List<?> salvaObjetosDaLista( List<?> listaDeObjetos, Boolean persistirObjetoCompleto ) throws Exception {
		this.setCrudBo(new CrudGenericBO());
		return this.getCrudBo().salvaObjetosLista(listaDeObjetos, persistirObjetoCompleto);
	}
	
	public Boolean excluiObjetosDaLista( List<?> listaDeObjetos, Boolean excluirObjetoCompleto ) throws Exception {
		this.setCrudBo(new CrudGenericBO());
		return this.getCrudBo().excluiObjetosDaLista(listaDeObjetos, excluirObjetoCompleto);
	}
	
	public Boolean deletarTabela( String tabela ) throws Exception {
		this.setCrudBo(new CrudGenericBO());
		return this.getCrudBo().deletarTabela( tabela );
	}
	
	
	/*
	public boolean salvarObjetoLista(Object obj, List<?> listaPersistir) throws Exception {
		this.setCrudBo(new CrudGenericBO());
		return this.getCrudBo().salvarObjetoLista(obj, listaPersistir);
	}*/
	
	public List<?> buscarConjuntoRegistros(Integer cd_programa, List<Object> listaTabelasProcurar, Object valorBuscar) throws Exception {
		this.setCrudBo(new CrudGenericBO());
		return this.getCrudBo().buscarConjuntoRegistros(cd_programa, listaTabelasProcurar, valorBuscar);
	}

	public Object buscarObjetoCompleto(Object objetoBuscar) throws Exception {
		this.setCrudBo(new CrudGenericBO());
		return this.getCrudBo().buscarObjetoCompleto(objetoBuscar);
	}

	public Object salvarObjetoCompleto(Object objetoPersistir) {
		this.setCrudBo(new CrudGenericBO());
		return this.getCrudBo().salvarObjetoCompleto(objetoPersistir);
	}
	
	public boolean excluirObjetoCompleto(Object objetoPersistir) throws Exception {
		this.setCrudBo(new CrudGenericBO());
		return this.getCrudBo().excluirObjetoCompleto(objetoPersistir);
	}
	public Integer getValorChavePrimaria(Object bean, String tabela, String nomeCampo) throws Exception {
		this.setCrudBo(new CrudGenericBO());
		return this.getCrudBo().getValorChavePrimaria( bean , tabela, nomeCampo);
	}
}