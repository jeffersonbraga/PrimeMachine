package br.com.lsd.service.dbDragListService;

import java.util.List;

import br.com.lsd.bo.dbDragListBO.DbDragListBO;

public class DbDragListService {

	private DbDragListBO listBO;

	public DbDragListBO getListBO() {
		return listBO;
	}

	public void setListBO(DbDragListBO listBO) {
		this.listBO = listBO;
	}
	
	public List<?> buscarRegistrosListaDe(Object objeto) throws Exception {
		this.setListBO(new DbDragListBO());
		return this.getListBO().buscarRegistrosListaDe(objeto);
	}
}
