package br.com.primum.notificadores;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.chat.service.ChatService;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.interfaces.INotificador;
import br.com.primum.modelo.documento.Documento;
import br.com.primum.modelo.notificacao.Notificacao;
import br.com.primum.modelo.notificacao_usuario.Notificacao_usuario;
import br.com.primum.modelo.usuario.Usuario;

public class Documento_notificador implements INotificador{

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	public Documento_notificador() {
		
	//	percorre listas das notificadoe do documento
	//	e se a st_notificar == a do documento envia mensagem pro usuario
	}

	public void efetuarNotificacao(Object documento){
		
		if(documento instanceof Documento){
			if(((Documento) documento).getNotificacao()!=null){
				
				retiraNotificacaoTodosUsuarios();
				
				for( Notificacao_usuario not : ((Notificacao) ((Documento) documento).getNotificacao()).getLista_notificacao_usuario()){
					if(not.getSt_notificar().equals( ((Documento) documento).getSt_documento() )){
						not.setSt_visualizado(0);
						CrudGenericBO crud = new CrudGenericBO();
						//MUDA VISUALIZADO PARA 'NÃO'
						try {
							crud.salvar(not);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						enviaNotificacaoParaUsuario( not.getCd_usuario() );
					}
				}		
			}
		}	
	}

	
	private void enviaNotificacaoParaUsuario(Integer cd_usuario){
		
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
    		if( usuariosLogados.getCd_usuario().equals( cd_usuario ) ){
    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoDocumento");
    			//break;
    		}
    	}
	}
	
	private void retiraNotificacaoTodosUsuarios(){
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
    		cs.enviarMensagem( usuariosLogados.getClientId() , "retiraNotificacaoDocumento");
    	}
	}
}
