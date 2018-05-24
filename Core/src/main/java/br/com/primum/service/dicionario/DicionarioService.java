package br.com.primum.service.dicionario;

import flex.messaging.FlexContext;
import br.com.primum.bo.dicionario.DicionarioBO;
import br.com.primum.modelo.config_sistema.Config_sistema;
import br.com.primum.modelo.dicionario.DicionarioDados;
import br.com.primum.singleton.SingletonEmail;
import br.com.primum.singleton.SingletonFlexSession;

public class DicionarioService {

	private DicionarioBO dic;

	public DicionarioBO getDic() {
		return dic;
	}

	public void setDic(DicionarioBO dic) {
		this.dic = dic;
	}
	
	public DicionarioDados carregarDicionario() throws Exception {
		
		if (!SingletonFlexSession.isInstance()) {
			SingletonFlexSession.getInstance();
			SingletonFlexSession.setMessageBroker(FlexContext.getMessageBroker());
		}
		
		if (!SingletonEmail.isInstance()) {
			SingletonEmail.getInstance();
		}
		
		this.setDic(new DicionarioBO());
		return this.getDic().carregarDicionario();
	}
	
	public DicionarioDados atualizarDicionario() throws Exception {
		this.setDic(new DicionarioBO());
		return this.getDic().atualizarDicionario();
	}
	
	public DicionarioDados atualizaConfigSistema(Config_sistema conf) throws Exception {
		this.setDic(new DicionarioBO());
		return this.getDic().atualizaConfigSistema(conf);
	}
}
