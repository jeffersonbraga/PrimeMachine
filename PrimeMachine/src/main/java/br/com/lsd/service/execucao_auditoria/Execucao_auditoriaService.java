package br.com.lsd.service.execucao_auditoria;

import java.util.List;

import br.com.lsd.bo.execucao_auditoria.Execucao_auditoriaBO;
import br.com.primum.modelo.execucao_auditoria.Execucao_auditoria;
import br.com.primum.modelo.planos_cronograma_auditoria.Planos_cronograma_auditoria;

public class Execucao_auditoriaService {

	private Execucao_auditoriaBO exeBO;

	public Execucao_auditoriaBO getExeBO() {
		return exeBO;
	}

	public void setExeBO(Execucao_auditoriaBO exeBO) {
		this.exeBO = exeBO;
	}

	public List<?> buscarGrupoAuditoria(Integer cd_planos_cronograma_auditoria) {

		this.setExeBO(new Execucao_auditoriaBO());
		return this.getExeBO().buscarGrupoAuditoria(cd_planos_cronograma_auditoria);
	}

	public List<?> buscarPerguntasGrupoAuditoria(Integer cd_grupo_auditoria) {

		this.setExeBO(new Execucao_auditoriaBO());
		return this.getExeBO().buscarPerguntasGrupoAuditoria(cd_grupo_auditoria);
	}

	public List<?> buscarRespostasAuditoria(Integer cd_planos_cronograma_auditoria) throws Exception {
		this.setExeBO(new Execucao_auditoriaBO());
		return this.getExeBO().buscarRespostasAuditoria(cd_planos_cronograma_auditoria);
	}

	public Execucao_auditoria verificarSituacaoCronogramaAuditoria(Planos_cronograma_auditoria planos_cronograma_auditoria) {
		this.setExeBO(new Execucao_auditoriaBO());
		return this.getExeBO().verificarSituacaoCronogramaAuditoria(planos_cronograma_auditoria);
	}
	
	public Execucao_auditoria salvarRespostasGrupoAuditoria (Execucao_auditoria execucao_auditoria) {
		this.setExeBO(new Execucao_auditoriaBO());
		return this.getExeBO().salvarRespostasGrupoAuditoria(execucao_auditoria);
	}
}