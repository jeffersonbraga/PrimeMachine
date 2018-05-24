package br.com.lsd.service.auditoria;

import java.util.List;

import br.com.lsd.bo.auditoria.AuditoriaBO;
import br.com.primum.modelo.auditoria.Auditoria;
import br.com.primum.modelo.grupo_auditoria.Grupo_auditoria;
import br.com.primum.modelo.resposta_exec_auditoria.Resposta_exec_auditoria;


public class AuditoriaService {

	private AuditoriaBO auditoriaBO;

	public AuditoriaBO getAuditoriaBO() {
		return auditoriaBO;
	}

	public void setAuditoriaBO(AuditoriaBO auditoriaBO) {
		this.auditoriaBO = auditoriaBO;
	}

	public boolean salvarNovoPlanoAuditoria(Auditoria auditoria) {

		this.setAuditoriaBO(new AuditoriaBO());
		return this.getAuditoriaBO().salvarPlanoAuditoria(auditoria);
	}

	public Auditoria buscarDadosPlanoAuditoria(Auditoria auditoria) {
		this.setAuditoriaBO(new AuditoriaBO());
		return this.getAuditoriaBO().buscarDadosPlanoAuditoria(auditoria);
	}
	
	public List<Resposta_exec_auditoria> buscarRespostasExecucaoAuditoria(List<Grupo_auditoria> lista_grupo_auditoria, Integer cd_grupo_auditoria){
		this.setAuditoriaBO(new AuditoriaBO());
		return this.getAuditoriaBO().buscarRespostasExecucaoAuditoria(lista_grupo_auditoria, cd_grupo_auditoria);
	}
}