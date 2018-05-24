package br.com.lsd.service.campocalculado;

import java.util.List;

import br.com.lsd.bo.campocalculado.CampoCalculadoBO;
import br.com.primum.modelo.condicao.Condicao;

public class CampoCalculadoService {
	
	private CampoCalculadoBO cpoCalculadoBO;

	public CampoCalculadoBO getCpoCalculadoBO() {
		return cpoCalculadoBO;
	}

	public void setCpoCalculadoBO(CampoCalculadoBO cpoCalculadoBO) {
		this.cpoCalculadoBO = cpoCalculadoBO;
	}

	public List<Condicao> buscarOperacoes() {
		this.setCpoCalculadoBO(new CampoCalculadoBO());
		return this.getCpoCalculadoBO().buscarOperacoes();
	}
}