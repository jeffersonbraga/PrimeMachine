package br.com.primum.service.sistema;

import java.util.List;

import br.com.primum.bo.sistema.SistemaBO;
import br.com.primum.modelo.permissao_padrao.Permissao_padrao;
import br.com.primum.modelo.programa.Programa;
import br.com.primum.modelo.usuario.Usuario;

public class SistemaService {
	
	private SistemaBO sistemaBO;
	
	public SistemaBO getSistemaBO() {
		return sistemaBO;
	}

	public void setSistemaBO(SistemaBO sistemaBO) {
		this.sistemaBO = sistemaBO;
	}

	public Integer setaCampoObrigatorio(Object dataSource, String datafield, Integer valor){
		this.setSistemaBO(new SistemaBO());
		return this.getSistemaBO().setaCampoObrigatorio( dataSource, datafield, valor);
	}
	
	public String enviaEmailAteracaoSenha(Usuario usuario){
		this.setSistemaBO(new SistemaBO());
		return this.getSistemaBO().enviaEmailAteracaoSenha( usuario );
	}
	
	public Boolean executaScriptTodosSchemas(String SQL, Integer tp_SQL){
		this.setSistemaBO(new SistemaBO());
		return this.getSistemaBO().executaScriptTodosSchemas( SQL , tp_SQL);
	}
	
	public List<Programa> getListaPermissoesPadrao(){
		this.setSistemaBO(new SistemaBO());
		return this.getSistemaBO().getListaPermissoesPadrao();
	}
	
	public Boolean deletaPermissoes(){
		this.setSistemaBO(new SistemaBO());
		return this.getSistemaBO().deletaPermissoes();
	}
}
