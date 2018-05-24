package br.com.lsd.service.auditoria;

import java.util.List;

import br.com.lsd.bo.auditoria.PainelProgramacaoAuditoriaBO;
import br.com.primum.modelo.cronograma_auditoria.Cronograma_auditoria;

public class PainelProgramacaoAuditoria {
	
	private PainelProgramacaoAuditoriaBO painelBO;

	public PainelProgramacaoAuditoriaBO getPainelBO() {
		return painelBO;
	}

	public void setPainelBO(PainelProgramacaoAuditoriaBO painelBO) {
		this.painelBO = painelBO;
	}
	
	public List<Cronograma_auditoria> buscarCronogramasAuditoria() {
		this.setPainelBO(new PainelProgramacaoAuditoriaBO());
		return this.getPainelBO().buscarCronogramasAuditoria();
	}
}
