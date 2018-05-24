package br.com.lsd.bo.projeto;

import java.util.List;

import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.atividade.Atividade;

public class ProjetoBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	public List<?> buscarAtividades(Integer cd_projeto) {
		this.setDao(new GenericDAO());
		List<?> lista = null;
		try {
			lista = this.getDao().findBySql(this.getSQLAtividadesProjeto(cd_projeto), new Atividade());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}

	private String getSQLAtividadesProjeto(Integer cd_projeto) {
		String query = "select * from atividade where cd_projeto = " + cd_projeto;
		return query;
	}
}
