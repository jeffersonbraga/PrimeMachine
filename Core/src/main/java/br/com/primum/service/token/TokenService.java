package br.com.primum.service.token;

import java.io.IOException;

import br.com.primum.bo.token.TokenBO;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.modelo.cliente.Cliente;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;

public class TokenService {

    public Object request;

    public FlexSession session;

    private TokenBO tokenBO;

	public TokenService() {
	}

	public TokenBO getTokenBO() {
		return tokenBO;
	}

	public void setTokenBO(TokenBO tokenBO) {
		this.tokenBO = tokenBO;
	}

	public void validarToken(String token) {

//		this.setTokenBO(new TokenBO());
//		this.getTokenBO().session = this.session;
//		this.getTokenBO().validarToken(token);
		/*try {
			Process p = Runtime.getRuntime().exec("cmd.exe C:\\Program Files\\PostgreSQL\\9.2\\bin\\psql.exe -U postgres -d checklist -f "+ ContextoAplicacao.CAMINHO_APLICACAO + "primum//novoSchemaInsert.primum -n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public void validarToken1(String token) {

		this.setTokenBO(new TokenBO());
		this.getTokenBO().session = this.session;
		this.getTokenBO().validarToken(token);
	}

	public void validarToken2(String token) {

		this.setTokenBO(new TokenBO());
		this.getTokenBO().session = this.session;
		this.getTokenBO().validarToken(token);
	}

	public boolean criarSchema(Cliente cliente) {

		this.setTokenBO(new TokenBO());
		this.getTokenBO().session = this.session;
		return this.getTokenBO().criarSchema(cliente);
	}
}