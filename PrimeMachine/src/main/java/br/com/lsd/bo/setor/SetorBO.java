package br.com.lsd.bo.setor;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.setor.Setor;

public class SetorBO {
	private GenericDAO dao;
		
	public SetorBO() {
	}

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
//	
//	@SuppressWarnings("unchecked")
//	public List<Setor> buscarAreas(Integer cd_unidade){
//		this.setDao(new GenericDAO());
//		List<Setor> lista = this.getDao().getSession().createCriteria(Setor.class)
//				.add(Restrictions.isNull("cd_departamento"))
//				.add(Restrictions.eq("cd_unidade", cd_unidade))
//				.list();
//		
//		return
//	}
	
	@SuppressWarnings("unchecked")
	public List<Setor> buscarTodosSetores(Integer cd_unidade){
//		this.setDao(new GenericDAO());
//		List<Setor> listaSetores= this.getDao().getSession().createCriteria(Setor.class).list();
//		List<Setor> listaSetoresAux = new ArrayList<>();
//		
//		if(listaSetores.size()>0){
//			for(int i = 0 ; i < listaSetores.size();i++){
//				if( listaSetores.get(i).getCd_departamento() == null){
//					listaSetoresAux.add(listaSetores.get(i));
//				}
//			}
//		}
//		
//		return listaSetoresAux;
		
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		this.setDao(new GenericDAO());
		List<Setor> lista = new ArrayList<>();
		lista = this.getDao().getSession().createCriteria(Setor.class)
				.add(Restrictions.isNull("cd_departamento"))
				.add(Restrictions.eq("cd_unidade", cd_unidade))
				.list();
		
		for (Setor setor : lista) {
			pesquisa.buscarObjetoCompleto(setor);
		}
		
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Setor> buscarTodosDepartamentos(Integer cd_departamento){
		this.setDao(new GenericDAO());
		List<Setor> listaDepartamentos = new ArrayList<>();

		if(cd_departamento==0){
			
			List<Setor> listaDepartamentosAux = (List<Setor>) this.getDao().getSession().createCriteria(Setor.class).list();
			
			for(Setor setor : listaDepartamentosAux){
				if( setor.getCd_departamento() != null ){
					listaDepartamentos.add(setor);
				}
			}
			
		}else{
			listaDepartamentos= this.getDao().getSession().createCriteria(Setor.class).add(Restrictions.eq("cd_departamento", cd_departamento)).list();
		}

		return listaDepartamentos;
	}

	public List<?> pesquisa(String datafield, String valor) throws Exception {
		return null;
	}
}
