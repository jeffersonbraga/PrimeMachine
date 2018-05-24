package br.com.lsd.bo.execucao_auditoria;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.cronograma_auditoria.Cronograma_auditoria;
import br.com.primum.modelo.execucao_auditoria.Execucao_auditoria;
import br.com.primum.modelo.planos_cronograma_auditoria.Planos_cronograma_auditoria;

public class Execucao_auditoriaBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	public List<?> buscarGrupoAuditoria(Integer cd_planos_cronograma_auditoria) {

		this.setDao(new GenericDAO());
		//Planos_cronograma_auditoria planos_cronograma_auditoria = (Planos_cronograma_auditoria) this.getDao().getSession().createCriteria(Planos_cronograma_auditoria.class).add(Restrictions.eq("cd_planos_cronograma_auditoria", cd_planos_cronograma_auditoria)).uniqueResult();
		Planos_cronograma_auditoria planos_cronograma_auditoria = null;

		//List<?> lista = this.getDao().getSession().createCriteria(Grupo_auditoria.class).add(Restrictions.eq("cd_auditoria", planos_cronograma_auditoria.getCd_auditoria())).list();
		List<?> lista = null;

		return lista;
	}

	public List<?> buscarPerguntasGrupoAuditoria(Integer cd_grupo_auditoria) {

		this.setDao(new GenericDAO());
		//List<?> listaPerguntas = this.getDao().getSession().createCriteria(Pergunta_auditoria.class).add(Restrictions.eq("cd_grupo_auditoria", cd_grupo_auditoria)).list();
		List<?> listaPerguntas = null;
		//LogPrimum.mensagem(listaPerguntas.size());
		return listaPerguntas;
	}

	public List<?> buscarRespostasAuditoria(Integer cd_planos_cronograma_auditoria) throws Exception {

		this.setDao(new GenericDAO());

		Planos_cronograma_auditoria plano = (Planos_cronograma_auditoria) this.getDao().findById(new Planos_cronograma_auditoria(), cd_planos_cronograma_auditoria);
		List<?> lista = null;
		if (plano != null) {
			//lista =  this.getDao().getSession().createCriteria(Resposta_auditoria.class).add(Restrictions.eq("cd_auditoria", plano.getCd_auditoria())).list();
			lista =  null;
		}

		return lista;
	}

	public Execucao_auditoria verificarSituacaoCronogramaAuditoria(Planos_cronograma_auditoria planos_cronograma_auditoria) {

		Execucao_auditoria execucao_auditoria = null;
		try {
			this.setDao(new GenericDAO());
			execucao_auditoria = (Execucao_auditoria) this.getDao().getSession().createCriteria(Execucao_auditoria.class).add(Restrictions.eq("cd_planos_cronograma_auditoria", planos_cronograma_auditoria.getCd_planos_cronograma_auditoria())).uniqueResult();

			if ( execucao_auditoria != null ) {
				PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
				pesquisa.buscarObjetoCompleto(execucao_auditoria);
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return execucao_auditoria;
	}

	public Execucao_auditoria salvarRespostasGrupoAuditoria (Execucao_auditoria execucao_auditoria) {

		CrudGenericBO crud = new CrudGenericBO();
		try {
			crud.salvarObjetoCompleto(execucao_auditoria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.atualizarSituacaoCronogramaAuditoria(execucao_auditoria);

		return execucao_auditoria;
	}

	public void atualizarSituacaoCronogramaAuditoria(Execucao_auditoria execucao_auditoria) {

		try {
			this.setDao(new GenericDAO());
			Planos_cronograma_auditoria planos_cronograma_auditoria = (Planos_cronograma_auditoria) this.getDao().getSession().createCriteria(Planos_cronograma_auditoria.class).add(Restrictions.eq("cd_planos_cronograma_auditoria", execucao_auditoria.getCd_planos_cronograma_auditoria())).uniqueResult();
			Cronograma_auditoria cronograma_auditoria = (Cronograma_auditoria) this.getDao().getSession().createCriteria(Cronograma_auditoria.class).add(Restrictions.eq("cd_cronograma_auditoria", planos_cronograma_auditoria.getCd_cronograma_auditoria())).uniqueResult();
			cronograma_auditoria.setSt_cronograma_auditoria(1);
			this.getDao().salvar(cronograma_auditoria);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}