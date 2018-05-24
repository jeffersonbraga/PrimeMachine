package br.com.lsd.bo.campocalculado;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.condicao.Condicao;

public class CampoCalculadoBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	@SuppressWarnings("unchecked")
	public List<Condicao> buscarOperacoes() {
		this.setDao(new GenericDAO());
		List<?> lista = this.getDao().getSession().createCriteria(Condicao.class)
				//.add(Restrictions.eq("tp_operacao", 1))
				.list();
		return (List<Condicao>) lista;
	}
}
