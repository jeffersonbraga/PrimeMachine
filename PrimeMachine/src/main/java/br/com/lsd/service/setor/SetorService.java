package br.com.lsd.service.setor;

import java.util.List;

import br.com.lsd.bo.pesquisa.PesquisaBO;
import br.com.lsd.bo.setor.SetorBO;
import br.com.primum.modelo.setor.Setor;

public class SetorService {

	private SetorBO setorBO;

	public SetorBO getSetorBO() {
		return setorBO;
	}

	public void setSetorBO(SetorBO setorBO) {
		this.setorBO = setorBO;
	}

	public List<Setor> buscaSetores(Integer cd_unidade){
		this.setSetorBO(new SetorBO());
		return getSetorBO().buscarTodosSetores(cd_unidade);
	}

	public List<Setor> buscaDepartamentos(Integer cd_departamento){
		this.setSetorBO(new SetorBO());
		return getSetorBO().buscarTodosDepartamentos(cd_departamento);
	}

	public List<?> pesquisa(String datafield, String valor) throws Exception {
		this.setSetorBO(new SetorBO());
		return this.getSetorBO().pesquisa(datafield, valor);
	}
}