package br.com.primum.service.email;

import java.util.List;

import br.com.primum.bo.email.EmailBO;
import br.com.primum.modelo.email.Email;
import br.com.primum.modelo.usuario.Usuario;

public class EmailService {
	
	private EmailBO emailBO;
	
	
	public EmailBO getEmailBO() {
		return emailBO;
	}

	public void setEmailBO(EmailBO emailBO) {
		this.emailBO = emailBO;
	}

	public EmailService(){
		setEmailBO( new EmailBO());
	}
	
	public Boolean enviaEmailSimples(Email email ) throws Exception {
		return this.getEmailBO().enviaEmailSimples(email);
	}
	

	public Boolean enviaEmailPraPessoa( Integer cd_usuario, String assunto, String corpoMSG) throws Exception {
		return this.getEmailBO().enviaEmailPraPessoa(cd_usuario, assunto, corpoMSG);
	}
	
	public Boolean envia(List<Email> listaEmail) {
		return this.getEmailBO().envia(listaEmail);
	}
	
	public Boolean enviaEmailsubstituindoCodPorNomePessoa( Integer cd_usuario, String assunto, String corpoMSG ) throws Exception {
		return this.getEmailBO().enviaEmailsubstituindoCodPorNomePessoa(cd_usuario, assunto, corpoMSG);
	}
}
