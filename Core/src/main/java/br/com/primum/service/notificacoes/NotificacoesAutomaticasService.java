package br.com.primum.service.notificacoes;

import java.util.List;

import br.com.primum.bo.notificacoes.NotificacoesAutomaticasBO;
import br.com.primum.modelo.notificacao_automatica.Notificacao_automatica;

public class NotificacoesAutomaticasService {

	private NotificacoesAutomaticasBO notificacoesAutomaticasBO;

	public NotificacoesAutomaticasBO getNotificacoesAutomaticasBO() {
		return notificacoesAutomaticasBO;
	}

	public void setNotificacoesAutomaticasBO(
			NotificacoesAutomaticasBO notificacoesAutomaticasBO) {
		this.notificacoesAutomaticasBO = notificacoesAutomaticasBO;
	}
	
	
	public Boolean onSalvarCRUD(Notificacao_automatica not, Integer tp_operacao,Integer cd_codigo_parametro){
		this.setNotificacoesAutomaticasBO(new NotificacoesAutomaticasBO());
		return this.getNotificacoesAutomaticasBO().onSalvarCRUD(not, tp_operacao, cd_codigo_parametro);	
	}
	
	public Boolean onSalvar(Notificacao_automatica not){
		this.setNotificacoesAutomaticasBO(new NotificacoesAutomaticasBO());
		return this.getNotificacoesAutomaticasBO().onSalvar(not);	
	}
	
	public Boolean onEditar(Notificacao_automatica not){
		this.setNotificacoesAutomaticasBO(new NotificacoesAutomaticasBO());
		return this.getNotificacoesAutomaticasBO().onEditar(not);
	}
	
	public Boolean onDelete(Notificacao_automatica not){
		this.setNotificacoesAutomaticasBO(new NotificacoesAutomaticasBO());
		return this.getNotificacoesAutomaticasBO().onDelete(not);
	}
	
	public List<Notificacao_automatica> getListaNotificacoesAuto(Integer cd_usuario){
		this.setNotificacoesAutomaticasBO(new NotificacoesAutomaticasBO());
		return this.getNotificacoesAutomaticasBO().getListaNotificacoesAuto(cd_usuario);
	}
	
	public List<Notificacao_automatica> getListaNotificacoesAutoCRUD(Integer cd_usuario){
		this.setNotificacoesAutomaticasBO(new NotificacoesAutomaticasBO());
		return this.getNotificacoesAutomaticasBO().getListaNotificacoesAutoCRUD(cd_usuario);
	}
	
	public Boolean setNotificacoesVisualizada(Integer cd_notificacao_usuario){
		this.setNotificacoesAutomaticasBO(new NotificacoesAutomaticasBO());
		return this.getNotificacoesAutomaticasBO().setNotificacoesVisualizada(cd_notificacao_usuario);
	}
	
	public Boolean setNotificacoesCRUDVisualizada(Integer cd_notificacao_usuario){
		this.setNotificacoesAutomaticasBO(new NotificacoesAutomaticasBO());
		return this.getNotificacoesAutomaticasBO().setNotificacoesCRUDVisualizada(cd_notificacao_usuario);
	}
	
	public Boolean verificaseExisteServicoRodando(){
		this.setNotificacoesAutomaticasBO(new NotificacoesAutomaticasBO());
		return this.getNotificacoesAutomaticasBO().verificaseExisteServicoRodando();
	}
}
