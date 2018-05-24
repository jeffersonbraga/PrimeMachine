package br.com.lsd.bo.filtro;

import java.util.List;

import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.tabela.Tabela;

public class FiltroBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	public List<?> buscarRegistrosFiltro(Integer cd_tabela) {
		this.setDao(new GenericDAO());
		//Tabela tabela = (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("cd_tabela", cd_tabela)).uniqueResult();
		Tabela tabela = null;
		List<?> listaRetorno = null;
		
		if ( (tabela != null) && (tabela.getDs_caminhoclasse() != null) ) {
			Object obj = null;
			
			try {
				obj = Class.forName(tabela.getDs_caminhoclasse()).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (obj != null) {
				
				try {
					listaRetorno = this.getDao().findAll(obj);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
		return listaRetorno;
	}
}
