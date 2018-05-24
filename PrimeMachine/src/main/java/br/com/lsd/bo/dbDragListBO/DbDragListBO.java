package br.com.lsd.bo.dbDragListBO;

import java.util.List;

import br.com.primum.hibernate.GenericDAO;

public class DbDragListBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	public List<?> buscarRegistrosListaDe(Object objeto) throws Exception {

		this.setDao(new GenericDAO());
		List<?> lista = this.getDao().findAll(objeto);
		return lista;
	}
}