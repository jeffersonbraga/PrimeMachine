package br.com.lsd.bo.ouvidoria;

import java.util.List;

import org.hibernate.criterion.Restrictions;


import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.configuracao_ouvidoria.Configuracao_ouvidoria;
import br.com.primum.modelo.ocorrencia_ouvidoria.Ocorrencia_ouvidoria;


public class OuvidoriaBO {
	
	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	@SuppressWarnings("unchecked")
	public List<Configuracao_ouvidoria> getListaConfiguracoes(){
		this.setDao(new GenericDAO());
		
		List<Configuracao_ouvidoria> lista = (List<Configuracao_ouvidoria>) this.getDao().getSession().createCriteria(Configuracao_ouvidoria.class).list();
		return lista;
	}
	
	public Boolean deletaListaConfiguracoes(Integer cd_configuracao_ouvidoria){
		
		Boolean resultado = true;
		this.setDao(new GenericDAO());
		
		Configuracao_ouvidoria conf = (Configuracao_ouvidoria) this.getDao().getSession().createCriteria(Configuracao_ouvidoria.class).add(Restrictions.eq("cd_configuracao_ouvidoria", cd_configuracao_ouvidoria)).uniqueResult();
		
			try {
				this.getDao().delete(conf);
			} catch (Exception e) {
				resultado = false;
				e.printStackTrace();
			}
			
		return resultado;
	}
	
	public Boolean gravaListaConfiguracoes(List<Configuracao_ouvidoria> lista){
		
		Boolean resultado = true;
		this.setDao(new GenericDAO());
		
	
		for( Configuracao_ouvidoria conf : lista){
			if(conf!=null){
				try {
						this.getDao().salvar(conf);
					} catch (Exception e) {
						resultado = false;
						e.printStackTrace();
					}
			}
		}
		
		return resultado;
	}
	
	public Boolean mudaStatusOcorrenciaOuvidoria(Integer cd_ocorrencia_ouvidoria, String valor){
		
		Boolean resultado = true;
		this.setDao(new GenericDAO());
		
		Ocorrencia_ouvidoria ocorrencia = (Ocorrencia_ouvidoria) this.getDao().getSession().createCriteria(Ocorrencia_ouvidoria.class).add(Restrictions.eq("cd_ocorrencia_ouvidoria", cd_ocorrencia_ouvidoria)).uniqueResult();
		ocorrencia.setSt_ocorrencia_ouvidoria( valor );
		
		try {
			this.getDao().atualizar(ocorrencia);
		} catch (Exception e) {
			resultado = false;
			e.printStackTrace();
		}
		
		return resultado;
	}

}
