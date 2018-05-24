package br.com.lsd.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.candidato.Candidato;
import br.com.primum.modelo.container_principal.Container_principal;
import br.com.primum.modelo.evento_campanha.Evento_campanha;

public class PoliticAppDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;
	
	private List<Container_principal> l 	= null;
	private List<Candidato> lCandidato 		= null;
	private List<Evento_campanha> lEvento 	= null;
	private List<Object> lEnvio 			= null;

	public List<Container_principal> getL() {
		return l;
	}

	public void setL(List<Container_principal> l) {
		this.l = l;
	}

	public List<Candidato> getlCandidato() {
		return lCandidato;
	}

	public void setlCandidato(List<Candidato> lCandidato) {
		this.lCandidato = lCandidato;
	}

	public List<Evento_campanha> getlEvento() {
		return lEvento;
	}

	public void setlEvento(List<Evento_campanha> lEvento) {
		this.lEvento = lEvento;
	}

	public PoliticAppDTO() {

		GenericDAO c = new  GenericDAO();
		l 			= (List<Container_principal>) c.getSession().createCriteria(Container_principal.class).list();
		lCandidato 	= (List<Candidato>) c.getSession().createCriteria(Candidato.class).list();
		lEvento 	= (List<Evento_campanha>) c.getSession().createCriteria(Evento_campanha.class).list();

		List<Object> lEnvio = new ArrayList<>();
		lEnvio.add(l);
		lEnvio.add(lCandidato);
		lEnvio.add(lEvento);
	}
}