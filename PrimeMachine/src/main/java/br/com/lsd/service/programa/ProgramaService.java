package br.com.lsd.service.programa;

import java.util.List;

import br.com.lsd.bo.programa.ProgramaBO;
import br.com.primum.modelo.grupo_usuario_programa.Grupo_usuario_programa;
import br.com.primum.modelo.programa.Programa;

public class ProgramaService {

	private ProgramaBO programaBO;
	
	public ProgramaBO getProgramaBO() {
		return programaBO;
	}
	
	public void setProgramaBO(ProgramaBO programaBO) {
		this.programaBO = programaBO;
	}
	
	public List<?> buscarCamposTabela(Integer cd_tabela) {
		this.setProgramaBO(new ProgramaBO());
		return this.getProgramaBO().buscarCamposTabela(cd_tabela);
	}
	/*
	public List<?> buscarCamposPesquisaPrograma(Integer cd_programa) {
		this.setProgramaBO(new ProgramaBO());
		return this.getProgramaBO().buscarCamposPesquisaPrograma(cd_programa);
	}*/
	
	public List<Grupo_usuario_programa> buscarProgramaPermissao() {
		this.setProgramaBO(new ProgramaBO());
		return this.getProgramaBO().buscarProgramaPermissao();
	}
	
	public List<Grupo_usuario_programa> setaModuloProgramas( List<Grupo_usuario_programa> listaPermissoes) {
		this.setProgramaBO(new ProgramaBO());
		return this.getProgramaBO().setaModuloProgramas( listaPermissoes );
	}
	
	public Boolean setTpPrograma( Programa programa) {
		this.setProgramaBO(new ProgramaBO());
		return this.getProgramaBO().setTpPrograma( programa );
	}
}
