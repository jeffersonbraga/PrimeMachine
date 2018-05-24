package br.com.lsd.service.filtro;

import java.util.List;

import br.com.lsd.bo.filtro.FiltroBO;

public class FiltroService {

	private FiltroBO filtroBO;

	public FiltroBO getFiltroBO() {
		return filtroBO;
	}

	public void setFiltroBO(FiltroBO filtroBO) {
		this.filtroBO = filtroBO;
	}
	
	public List<?> buscarRegistrosFiltro(Integer cd_tabela) {
		this.setFiltroBO(new FiltroBO());
		return this.getFiltroBO().buscarRegistrosFiltro(cd_tabela);
	}
}
