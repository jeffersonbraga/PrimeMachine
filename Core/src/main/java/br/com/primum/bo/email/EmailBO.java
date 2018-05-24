package br.com.primum.bo.email;

import java.security.Security;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.EmailException;
import org.hibernate.criterion.Restrictions;

import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.email.Email;
import br.com.primum.modelo.pessoa.Pessoa;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.singleton.SingletonEmail;

public class EmailBO {
	
	public GenericDAO genericDAO;
	
	
	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
	
	public void sendEmail(String para, String nome, String assunto, String corpoMsg) throws EmailException {
	  		
	}
	
	public Email criaEmail(String para, String assunto, String corpoMsg){
		Email email = new Email();
		email.setUsuario(SingletonEmail.getInstance().getEmail_enviar());
		email.setSenha(SingletonEmail.getInstance().getEmail_senha());
		email.setHost(SingletonEmail.getInstance().getSmtp());
		email.setPort(SingletonEmail.getInstance().getPorta_smtp().toString());
		email.setFrom(SingletonEmail.getInstance().getEmail_enviar());
		email.setAssunto( assunto );	
		email.setCorpo( corpoMsg );
		email.setTo( para );
		return email;
	}
	
	public Boolean enviaEmailSimples(final Email email)  throws Exception {	
		
		//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		 
		
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", email.getHost());
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", email.getPort());
		props.put("mail.smtp.socketFactory.port", email.getPort());
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");
 
		
		try {
			 
			Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() 
			{
				protected PasswordAuthentication getPasswordAuthentication()
				{ 
					return new PasswordAuthentication(email.getUsuario(),email.getSenha());	
				}
			});		
	 
			MimeMessage message = new MimeMessage(session);
			message.setSender(new InternetAddress(email.getUsuario()));
			message.setSubject(email.getAssunto());
			
			
			message.setContent(email.getCorpo(), "text/plain");
			
//			for ( Usuario u : listaPessoas) {
//				
//				Pessoa pessoa = (Pessoa)this.getGenericDAO().getSession().createCriteria(Pessoa.class).add(Restrictions.eq("cd_usuario", u.getCd_pessoa())).uniqueResult();
//				message.setRecipient(Message.RecipientType.TO, new InternetAddress( pessoa.getDs_email() ));
//				//message.setRecipient(Message.RecipientType.TO, new InternetAddress(item.getEmail()));
//			}
			
//			this.setGenericDAO(new GenericDAO());			
//			Usuario usu = (Usuario)this.getGenericDAO().getSession().createCriteria(Usuario.class).add(Restrictions.eq("cd_usuario", cd_usuario)).uniqueResult();
//			if(usu!=null){
//				Pessoa pessoa = (Pessoa)this.getGenericDAO().getSession().createCriteria(Pessoa.class).add(Restrictions.eq("cd_usuario", usu.getCd_pessoa())).uniqueResult();
//				message.setRecipient(Message.RecipientType.TO, new InternetAddress( pessoa.getDs_email() ));
//			}
			
			message.setRecipient(Message.RecipientType.TO, new InternetAddress( email.getTo() ));
			Transport.send(message);
 
		} catch (MessagingException e) {
			System.out.println(e.toString());
			throw new RuntimeException(e);
			
		}
		
		return true;
		
	}
	
	
	public Boolean envia (List<Email> listaEmail) {
		
		//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		
		for (final Email item : listaEmail) {
			final String username = item.getUsuario();
			final String password = item.getSenha();
	 
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", item.getHost());
			props.put("mail.smtp.port", item.getPort());
			
			props.put("mail.smtp.socketFactory.port", item.getPort());
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.quitwait", "false");
			
	 
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });
	 
			try {
	 
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(item.getFrom()));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(item.getTo()));
				message.setSubject(item.getAssunto());
				message.setText(item.getCorpo());
	 
				Transport.send(message);
	 
				System.out.println("Done");
	 
			} catch (MessagingException e) {
				System.out.println(e.toString());
				throw new RuntimeException(e);
			}
		}
	
		return true;
	}
	
	public Pessoa achaPessoa(Integer cd_usuario){
		
		setGenericDAO(new GenericDAO());
		Usuario usu = (Usuario) this.getGenericDAO().getSession().createCriteria(Usuario.class).add(Restrictions.eq("cd_usuario", cd_usuario)).uniqueResult();
		if(usu!=null){
			Pessoa pessoa = (Pessoa) this.getGenericDAO().getSession().createCriteria(Pessoa.class).add(Restrictions.eq("cd_pessoa", usu.getCd_pessoa())).uniqueResult();
			if(pessoa!=null){
				if(pessoa.getDs_email()!=""){
					return pessoa;
				}
			}
		}
		
		return null;
	}
	
	public Boolean enviaEmailPraPessoa(Integer cd_usuario,String assunto, String corpoMSG){
		Pessoa p = achaPessoa(cd_usuario);
		if(p!=null){
			try {
				sendEmail(p.getDs_email(), p.getNm_pessoa() , assunto, corpoMSG);
			} catch (EmailException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	
	public Boolean enviaEmailsubstituindoCodPorNomePessoa(Integer cd_usuario,String assunto, String corpoMSG){
		Pessoa p = achaPessoa(cd_usuario);
		
		String[] arrStrings = corpoMSG.split(" ");
		for(Integer i = 0 ; i< arrStrings.length ; i++){
			Boolean ehNumero=false;
			Integer numero = null;
			try {  
				numero = (Integer.parseInt( arrStrings[i] ));  
				ehNumero = true;  
			}catch (NumberFormatException e) {       
				ehNumero = false;  
			}
			
			if (ehNumero) {  
				if(numero!=null){
					Pessoa pessoa = achaPessoa( numero );
					if(pessoa != null && pessoa.getNm_pessoa() != null ){
						arrStrings[i] = pessoa.getNm_pessoa().toString();
					}
				}
			}
		}
		
		corpoMSG ="";
		for(Integer i = 0 ; i< arrStrings.length ; i++){
			corpoMSG += arrStrings[i] +" ";
		}
		
		
		if(p!=null){
			try {
				sendEmail(p.getDs_email(), p.getNm_pessoa() , assunto, corpoMSG);
			} catch (EmailException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
