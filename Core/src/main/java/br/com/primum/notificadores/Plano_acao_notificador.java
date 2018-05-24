package br.com.primum.notificadores;

//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.chat.service.ChatService;
import br.com.primum.modelo.atividade_plano_acao.Atividade_plano_acao;
import br.com.primum.modelo.notificacao_plano_acao.Notificacao_plano_acao;
import br.com.primum.modelo.plano_acao.Plano_acao;
import br.com.primum.modelo.usuario.Usuario;

public class Plano_acao_notificador {
	
//	private GenericDAO dao;
//	public GenericDAO getDao() {
//		return dao;
//	}
//	public void setDao(GenericDAO dao) {
//		this.dao = dao;
//	}
	
	public static void verificaResponsavelSetor(Plano_acao plano_acao){
		//this.setDao(new GenericDAO());
		
		ChatService cs = new ChatService();
    	//Setor setor = (Setor) this.getDao().getSession().createCriteria(Setor.class).add( Restrictions.eq("cd_setor", plano_acao.getCd_setor_ocorrencia()) ).uniqueResult();
		//if(setor!=null){
			for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
	    		
				//LogPrimum.mensagem(  usuariosLogados.getClientId() );
	    		
				if( usuariosLogados.getCd_usuario().equals( plano_acao.getSetor_responsavel().getCd_responsavel_setor()) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebida" );
	    			break;
	    		}
	    	}
		//}
	}
	
	
	public static Boolean verificaSituacaoPlano(Plano_acao plano_acao){
		
		ChatService cs = new ChatService();

		//LogPrimum.mensagem(plano_acao.toString());
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){

			if(plano_acao.getCd_usuario_validacao()==null){

				if( usuariosLogados.getCd_usuario().equals( plano_acao.getCd_usuario_abrir() ) && plano_acao.getSt_plano_acao().equals(2) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoEnviada" );
	    		}

				if( usuariosLogados.getCd_usuario().equals( plano_acao.getSetor_responsavel().getCd_responsavel_setor()) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebidaFinalizada");
	    		}

			}else{
				if( usuariosLogados.getCd_usuario().equals( plano_acao.getCd_usuario_abrir() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoEnviadaFinalizada" );
	    		}
			}
		}
		return true;
	}

	public static void retiraNotificacaoRecebida(Plano_acao plano_acao){
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( usuariosLogados.getCd_usuario().equals( plano_acao.getCd_usuario_abrir() ) ){
    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoEnviadaFinalizada"  );
    		}
		}
	}

	public static void retiraNotificacaoEnviada(Plano_acao plano_acao){
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( usuariosLogados.getCd_usuario().equals(  plano_acao.getSetor_ocorrencia().getCd_responsavel_setor() ) ){
    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebidaFinalizada"  );
    		}
		}
	}

	/*************************************************/
	public static void adicionaNotificacaoResponsavelSetorCorrecao(Plano_acao plano){
		ChatService cs = new ChatService();
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( usuariosLogados.getCd_usuario().equals(  plano.getSetor_responsavel().getCd_responsavel_setor() ) ){
    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebida");
    		}
		}	
	}

	public static void adicionaNotificacaoResponsavelAtividade(Atividade_plano_acao atividade){
		ChatService cs = new ChatService();
		if(!atividade.getSt_atividade_plano_acao().equals(2)){
			for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
				if( usuariosLogados.getCd_usuario().equals(  atividade.getCd_responsavel_execucao() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebida");
	    		}
			}	
		}
	}
	
	/*************************************/
	
	public static void retiraNotificacaoResponsavelAtividade(Atividade_plano_acao atividade){
		ChatService cs = new ChatService();
		if(atividade.getSt_atividade_plano_acao().equals(2)){
			for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
				if( usuariosLogados.getCd_usuario().equals(  atividade.getCd_responsavel_execucao() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebidaFinalizada" );
	    		}
			}	
		}
	}
	/*************************************/
	
	public static void retiraNotificacaoResponsavelSetorCorrecao(Plano_acao plano){
		ChatService cs = new ChatService();
		if(plano.getSt_plano_acao().equals(2)){
			for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
				if( usuariosLogados.getCd_usuario().equals(  plano.getSetor_responsavel().getCd_responsavel_setor() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoRecebidaFinalizada" );
	    		}
				
				if( usuariosLogados.getCd_usuario().equals(  plano.getCd_usuario_abrir() ) ){
	    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoEnviada" );
	    		}
			}	
		}
	}
	
	/**************************************/
	
	public static void retiraNotificacaoResponsavelAbrir(Plano_acao plano){
		ChatService cs = new ChatService();
		
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( usuariosLogados.getCd_usuario().equals(  plano.getCd_usuario_abrir() ) ){
    			cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoEnviadaFinalizada" );
    		}
		}	
		
	}
	
	/*******************************************/
	public static void adicionaNotificacaoFeed(){
		ChatService cs = new ChatService();
		
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( usuariosLogados.getCd_profissao().equals(  1  ) ){
				cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoFeedRecebida" );
			}
		}
		
	}
	
	
	public static void atualizaNotificacaoFeed(Notificacao_plano_acao not){
		ChatService cs = new ChatService();
		
		for(Usuario usuariosLogados : cs.buscarContatosAtivos()){
			if( (usuariosLogados.getCd_profissao() != null) && (usuariosLogados.getCd_profissao().equals(  1  )) ){
				if(not.getSt_exibir().equals(0)){
					cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoFeedRecebida");
				}else{
					cs.enviarMensagem( usuariosLogados.getClientId() , "notificacaoFeedFinalizada" );
				}
			}
		}
		
	}
	
}
