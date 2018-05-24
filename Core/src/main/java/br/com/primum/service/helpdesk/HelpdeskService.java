package br.com.primum.service.helpdesk;

import java.util.List;

import br.com.primum.bo.helpdesk.HelpdeskBO;
import br.com.primum.modelo.acao_corretiva_helpdesk.Acao_corretiva_helpdesk;
import br.com.primum.modelo.acao_plano_acao_hp.Acao_plano_acao_hp;
import br.com.primum.modelo.configuracao_helpdesk.Configuracao_helpdesk;
import br.com.primum.modelo.diagrama_helpdesk.Diagrama_helpdesk;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.modelo.plano_acao_hp.Plano_acao_hp;

public class HelpdeskService {

	private HelpdeskBO helpdeskBO;

	public HelpdeskBO getHelpdeskBO() {
		return helpdeskBO;
	}

	public void setHelpdeskBO(HelpdeskBO helpdeskBO) {
		this.helpdeskBO = helpdeskBO;
	}
	
	
	
	public List<Configuracao_helpdesk> ListaConfiguracoesHelpdesk(){
		this.setHelpdeskBO(new HelpdeskBO());
		return this.getHelpdeskBO().ListaConfiguracoesHelpdesk();
	}
	
	public Boolean gravaListaConfiguracoes(List<Configuracao_helpdesk> lista){
		
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().gravaListaConfiguracoes(lista);
	}
	
	public Boolean deletaListaConfiguracoes(Integer cd_configuracao_helpdesk){
		
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().deletaListaConfiguracoes(cd_configuracao_helpdesk);
	}
	
	public Boolean setResponsavelChamado(Helpdesk_ocorrencia hd, Integer cd_usuario){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().setResponsavelChamado(hd, cd_usuario);
	}
	
	public Helpdesk_ocorrencia buscaHDAtual(Integer cd_helpdesk_ocorrencia){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().buscaHDAtual(cd_helpdesk_ocorrencia);
	}
	
	public Boolean mudaSituacao(Integer cd_helpdesk_ocorrencia){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().mudaSituacao(cd_helpdesk_ocorrencia);
	}
	
	public Boolean mudaParecer(Integer cd_helpdesk_ocorrencia, Integer tp_parecer_reclamante, String ds_observacoes){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().mudaParecer( cd_helpdesk_ocorrencia, tp_parecer_reclamante, ds_observacoes);
	}
	
	public Boolean setaAtividadeConcluida(Integer cd_atividades_acao_imediata){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().setaAtividadeConcluida( cd_atividades_acao_imediata );
	}
	
	public Boolean setaAcaoConcluida(Integer cd_acao_corretiva_helpdesk){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().setaAcaoConcluida( cd_acao_corretiva_helpdesk );
	}
	

	public Boolean setaAtividadePlanoAcaoIshikawaConcluida(Integer cd_atividade_acao_pl_hp){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().setaAtividadePlanoAcaoIshikawaConcluida( cd_atividade_acao_pl_hp );
	}
	
	public Boolean setaAcaoPlanoAcaoIshikawaConcluida(Integer cd_acao_plano_acao_hp){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().setaAcaoPlanoAcaoIshikawaConcluida( cd_acao_plano_acao_hp );
	}
	
	
	public List<Acao_corretiva_helpdesk> listaNotificacoesAcoesAtividades(Integer cd_usuario){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().listaNotificacoesAcoesAtividades(cd_usuario);
	}
	
	public List<Acao_plano_acao_hp> listaNotificacoesAcoesAtividadesPA_ishi(Integer cd_usuario){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().listaNotificacoesAcoesAtividadesPA_ishi(cd_usuario);
	}
	
	public List<Diagrama_helpdesk> getDiagramasAnalise(Integer cd_usuario){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().getDiagramasAnalise( cd_usuario );
	}
	
	public String getHistoricoDiagramas(Integer cd_diagrama_helpdesk){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().getHistoricoDiagramas( cd_diagrama_helpdesk );
	}
	
//	public List<Plano_acao_hp> listaNotificacoesAcoesAtividadesPA_ishi(Integer cd_usuario){
//		this.setHelpdeskBO(new HelpdeskBO());
//		return getHelpdeskBO().listaNotificacoesAcoesAtividadesPA_ishi(cd_usuario);
//	}
	
	public Helpdesk_ocorrencia getHelpDeskCompletoPelaAcao(Integer cd_acao_corretiva){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().getHelpDeskCompletoPelaAcao(cd_acao_corretiva);
	}
	
	public Helpdesk_ocorrencia getCodigoHDPelaCausa(Integer cd_causa_efeito){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().getCodigoHDPelaCausa( cd_causa_efeito );
	}
	
	public Boolean enviaEmailLink(Helpdesk_ocorrencia helpdesk_ocorrencia){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().enviaEmailLink(helpdesk_ocorrencia);
	}
	
	public Boolean enviaMensagemChat(Helpdesk_ocorrencia hd, Integer cd_usuario_de, String msg, Integer vl_valor_abrir){
		this.setHelpdeskBO(new HelpdeskBO());
		return getHelpdeskBO().enviaMensagemChat(hd, cd_usuario_de, msg, vl_valor_abrir);
	}
}
