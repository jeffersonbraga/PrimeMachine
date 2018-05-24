package br.com.lsd.service.ouvidoria;

import java.util.List;

import br.com.lsd.bo.ouvidoria.OuvidoriaBO;
import br.com.primum.modelo.configuracao_ouvidoria.Configuracao_ouvidoria;


public class OuvidoriaService {

	private OuvidoriaBO ouvidoriaBO;

	public OuvidoriaBO getOuvidoriaBO() {
		return ouvidoriaBO;
	}

	public void setOuvidoriaBO(OuvidoriaBO ouvidoriaBO) {
		this.ouvidoriaBO = ouvidoriaBO;
	}
	
	
	public List<Configuracao_ouvidoria> ListaConfiguracoesOuvidoria(){
		
		this.setOuvidoriaBO(new OuvidoriaBO());
		return getOuvidoriaBO().getListaConfiguracoes();
	}
	
	public Boolean gravaListaConfiguracoesOuvidoria(List<Configuracao_ouvidoria> lista){
		
		this.setOuvidoriaBO(new OuvidoriaBO());
		return getOuvidoriaBO().gravaListaConfiguracoes(lista);
	}
	
	public Boolean deletaListaConfiguracoesOuvidoria(Integer cd_configuracao_ouvidoria){
		
		this.setOuvidoriaBO(new OuvidoriaBO());
		return getOuvidoriaBO().deletaListaConfiguracoes(cd_configuracao_ouvidoria);
	}
	
	public Boolean mudaStatusChamadoOuvidoria(Integer cd_ocorrencia_ouvidoria, String valor){
		this.setOuvidoriaBO(new OuvidoriaBO());
		return getOuvidoriaBO().mudaStatusOcorrenciaOuvidoria(cd_ocorrencia_ouvidoria, valor);
	}
	

	
}
