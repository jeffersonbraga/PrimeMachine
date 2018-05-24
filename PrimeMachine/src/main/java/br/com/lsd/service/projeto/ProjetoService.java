package br.com.lsd.service.projeto;

import java.util.List;

import br.com.lsd.bo.projeto.ProjetoBO;

public class ProjetoService {

	private ProjetoBO projetoBO;
	
	public ProjetoBO getProjetoBO() {
		return projetoBO;
	}
	
	public void setProjetoBO(ProjetoBO projetoBO) {
		this.projetoBO = projetoBO;
	}
	
	public List<?> buscarAtividades(Integer cd_projeto) {
		this.setProjetoBO(new ProjetoBO());
		return this.getProjetoBO().buscarAtividades(cd_projeto);
	}
}
