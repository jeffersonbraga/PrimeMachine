package br.com.lsd.bo.cliente;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.email.EmailBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.hibernate.HibernateSessionFactory;
import br.com.primum.modelo.cliente.Cliente;
import br.com.primum.modelo.endereco.Endereco;
import br.com.primum.modelo.pessoa.Pessoa;
import br.com.primum.modelo.revisao_programa_qualidade.Revisao_programa_qualidade;
import br.com.primum.notificadores.Helpdesk_notificador;

public class ClienteBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	public void gravarCliente(Pessoa pessoa, Endereco endereco, Cliente cliente) {
		CrudGenericBO crud= new CrudGenericBO();
		try {
			
			crud.abrirTransacao();
			if (pessoa.getCd_pessoa() == null) {
				crud.salvar(pessoa);
			} else {
				crud.atualizar(pessoa);
			}
			
			if (endereco.getCd_endereco() == null) {
				crud.salvar(endereco);
			} else {
				crud.atualizar(endereco);
			}
			
			//cliente.setCd_pessoa(pessoa.getCd_pessoa());
			if (cliente.getCd_cliente() == null) {
				crud.salvar(cliente);
			} else {
				crud.atualizar(cliente);
			}

			crud.fecharTransacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Cliente verificaToken(String ds_token) {
		this.setDao(new GenericDAO());
		Cliente cli = (Cliente) this.getDao().getSession().createCriteria(Cliente.class).add(Restrictions.eq("ds_token", ds_token)).uniqueResult();
/*
		if (cli != null) {
			HttpServletRequest request = FlexContext.getHttpRequest();
	    	FlexSession session = FlexContext.getFlexSession();
	
	    	for (FlexClient fc : session.getFlexClients()) {
	    		if (fc.getId().equals((String) request.getAttribute("FLEXCLIENT"))) {
	
	    			fc.setAttribute("SCHEMAPRIMUM", cli.getNm_schema());
	    		}
	    	}
		}*/
		return cli;
	}

	public Cliente enviaEmailToken(String valor, Integer tipo) {
		this.setDao(new GenericDAO());
		Cliente cli = null;
		
		if(tipo.equals(0)){
			cli = (Cliente) this.getDao().getSession().createCriteria(Cliente.class).add(Restrictions.eq("ds_email", valor)).uniqueResult();	
		}else if(tipo.equals(1)){
			cli = (Cliente) this.getDao().getSession().createCriteria(Cliente.class).add(Restrictions.eq("nr_cnpj", valor)).uniqueResult();
		}
		
		if(cli!=null){
			EmailBO emailBO = new EmailBO();
			try {
				//emailBO.sendEmail( pessoa.getDs_email() , pessoa.getNm_pessoa() , "NOTIFICA��O DE ATIVIDADE A��O IMEDIATA", "Descri��o: "+ativAI.getDs_atividades() );
				//emailBO.sendEmail( cli.getDs_email() , cli.getNm_cliente() , "VALOR TOKEN", "Valor do seu token: " + cli.getDs_token());
				return cli;
			} catch (Exception e) {
				return null;
			}
		}else{
			return null;
		}
		
		
	}
}
