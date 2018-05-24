package br.com.lsd.bo.conclusao_auditoria;

import org.hibernate.criterion.Restrictions;

import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.conclusao_auditoria.Conclusao_auditoria;
import br.com.primum.modelo.cronograma_auditoria.Cronograma_auditoria;

public class Conclusao_auditoriaBO {

	private GenericDAO dao;
	
	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	public void salvarConclusaoAuditoria(Conclusao_auditoria conclusao_auditoria) {

		this.setDao(new GenericDAO());
		this.getDao().salvar(conclusao_auditoria);

		Cronograma_auditoria cronograma = (Cronograma_auditoria) this.getDao().getSession().createCriteria(Cronograma_auditoria.class).add(Restrictions.eq("cd_cronograma_auditoria", conclusao_auditoria.getCd_cronograma_auditoria())).uniqueResult();
		cronograma.setSt_cronograma_auditoria(2);

		this.getDao().salvar(cronograma);
	}
}