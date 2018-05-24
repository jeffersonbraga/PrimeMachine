package br.com.primum.service.notificacoes;

import br.com.primum.bo.notificacoes.NotificacoesBO;
import br.com.primum.modelo.obj_notificacao.Obj_notificacao;

public class NotificacoesService {

	private NotificacoesBO notificacoesBO;

	public NotificacoesBO getNotificacoesBO() {
		return notificacoesBO;
	}

	public void setNotificacoesBO(NotificacoesBO notificacoesBO) {
		this.notificacoesBO = notificacoesBO;
	}
	
	
	public Obj_notificacao getAllNotificacoes(Integer cd_usuario){
		this.setNotificacoesBO(new NotificacoesBO());
		return this.getNotificacoesBO().getAllNotificacoes(cd_usuario);
	}
	
	public Boolean setNotificacoesDocumentoVisualizadas(Integer cd_usuario){
		this.setNotificacoesBO(new NotificacoesBO());
		return this.getNotificacoesBO().setNotificacoesDocumentoVisualizadas(cd_usuario);
	}
}
