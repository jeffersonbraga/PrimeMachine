package br.com.lsd.service.cliente;

import br.com.lsd.bo.cliente.ClienteBO;
import br.com.primum.modelo.cliente.Cliente;
import br.com.primum.modelo.endereco.Endereco;
import br.com.primum.modelo.pessoa.Pessoa;

public class ClienteService {
	
	private ClienteBO clienteBO;
	
	public ClienteBO getClienteBO() {
		return clienteBO;
	}

	public void setClienteBO(ClienteBO clienteBO) {
		this.clienteBO = clienteBO;
	}

	public void gravarCliente(Pessoa pessoa, Endereco endereco, Cliente cliente) {
		this.setClienteBO(new ClienteBO());
		this.getClienteBO().gravarCliente(pessoa, endereco, cliente);
	}
	
	public Cliente verificaToken(String ds_token) {
		this.setClienteBO(new ClienteBO());
		return this.getClienteBO().verificaToken(ds_token);
	}
	
	public Cliente enviaEmailToken(String valor, Integer tipo) {
		this.setClienteBO(new ClienteBO());
		return this.getClienteBO().enviaEmailToken( valor, tipo);
	}
	
	
}
