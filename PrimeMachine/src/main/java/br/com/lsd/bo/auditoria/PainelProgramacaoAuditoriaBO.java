package br.com.lsd.bo.auditoria;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.cronograma_auditoria.Cronograma_auditoria;

public class PainelProgramacaoAuditoriaBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	public List<Cronograma_auditoria> buscarCronogramasAuditoria() {

		this.setDao(new GenericDAO());
		List<?> listaCronogramas 	= null;
		List<Cronograma_auditoria> listaCronogramasAux = new ArrayList<Cronograma_auditoria>();
		try { 

			//listaCronogramas = this.getDao().findAll(new Cronograma_auditoria());
			listaCronogramas = this.getDao().getSession().createCriteria(Cronograma_auditoria.class).add(Restrictions.ne("st_cronograma_auditoria", 2)).list();
			for(Cronograma_auditoria cronograma : (List<Cronograma_auditoria>) listaCronogramas){

				PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
				pesquisa.buscarObjetoCompleto(cronograma);

				if ( (cronograma.getListaPlanoAuditoria() == null) || (cronograma.getListaPlanoAuditoria().size() == 0) ) {
					listaCronogramasAux.add(cronograma);
				}
			}

			listaCronogramas.removeAll(listaCronogramasAux);
		} catch (Exception e) {

			e.printStackTrace();
		}

		this.getDao().fecharConexao();
		return (List<Cronograma_auditoria>) listaCronogramas;
	}
}