package br.com.lsd.service.login;

import br.com.lsd.bo.login.LoginBO;
import br.com.primum.modelo.usuario.Usuario;

public class LoginService {

	private LoginBO loginBO;
	
	public LoginService() {
		
	}
	
	public LoginBO getLoginBO() {
		return loginBO;
	}

	public void setLoginBO(LoginBO loginBO) {
		this.loginBO = loginBO;
	}

	public Object checkIn(Usuario usuario) throws Exception {
		
		this.setLoginBO(new LoginBO());
		Object retorno = this.getLoginBO().checkIn(usuario);		
		return retorno;
	}
}
