package br.com.lsd.service.conclusao_auditoria;

import br.com.lsd.bo.conclusao_auditoria.Conclusao_auditoriaBO;
import br.com.primum.modelo.conclusao_auditoria.Conclusao_auditoria;

public class Conclusao_auditoriaService {
	
	private Conclusao_auditoriaBO conclusao_auditoriaBO; 

	public Conclusao_auditoriaBO getConclusao_auditoriaBO() {
		return conclusao_auditoriaBO;
	}

	public void setConclusao_auditoriaBO(Conclusao_auditoriaBO conclusao_auditoriaBO) {
		this.conclusao_auditoriaBO = conclusao_auditoriaBO;
	}

	public void salvarConclusaoAuditoria(Conclusao_auditoria conclusao_auditoria) {
		
		this.setConclusao_auditoriaBO(new Conclusao_auditoriaBO());
		this.getConclusao_auditoriaBO().salvarConclusaoAuditoria(conclusao_auditoria);
	}
}