package br.com.primum.flexclient;

import java.security.Principal;

import br.com.primum.modelo.cliente.Cliente;
import br.com.primum.modelo.usuario.Usuario;

public class PrimumFlexClient implements Principal {

	private Cliente cliente;

	private Usuario usuario;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String getName() {
		String nome = null;

		if (this.getUsuario() != null) {
			nome = this.getUsuario().getNm_usuario();
		}
		return nome;
	}
}