package br.com.primum.bo.ideia;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.avaliacao_ideia.Avaliacao_ideia;
import br.com.primum.modelo.ideia_reuniao_pic.Ideia_reuniao_pic;
import br.com.primum.modelo.participantes_reuniao_pic.Participantes_reuniao_pic;
import br.com.primum.modelo.reuniao_pic.Pre_avaliacao_ideia_pic;
import br.com.primum.modelo.reuniao_pic.Reuniao_pic;

public class IdeiaBO {
	
	private GenericDAO dao;
	
	public IdeiaBO() {
	}

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	
	public List<Pre_avaliacao_ideia_pic> buscaPreAvaliacoes(Reuniao_pic reuniao_pic, Integer cd_usuario_logado){
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		List<Pre_avaliacao_ideia_pic> listaAux = new ArrayList<>();
		
		for(Participantes_reuniao_pic p : reuniao_pic.getListaParticipantesReuniao()){
			if(p.getCd_pessoa().equals(cd_usuario_logado)){
				for(Ideia_reuniao_pic i : reuniao_pic.getListaIdeiasReuniao()){
					
					Pre_avaliacao_ideia_pic pre = (Pre_avaliacao_ideia_pic ) this.getDao().getSession().createCriteria(Pre_avaliacao_ideia_pic.class)
																							.add(Restrictions.eq("cd_participantes_reuniao_pic", cd_usuario_logado))
																							.add(Restrictions.eq("cd_ideia_reuniao_pic", i.getCd_ideia_reuniao_pic())).uniqueResult();
					if(pre!=null){
						pesquisa.buscarObjetoCompleto(pre);
						listaAux.add(pre);
					}else{
						Pre_avaliacao_ideia_pic pr = new Pre_avaliacao_ideia_pic();
						pr.setCd_ideia_reuniao_pic(i.getCd_ideia_reuniao_pic());
						pr.setCd_participantes_reuniao_pic(cd_usuario_logado);
						
						CrudGenericBO crud = new CrudGenericBO();
						
						pr = (Pre_avaliacao_ideia_pic) crud.salvarObjetoCompleto(pr);
						
						if(pr.getCd_pre_avaliacao_ideia_pic()!=null){
							pesquisa.buscarObjetoCompleto(pr);
							listaAux.add(pr);
						}
					}
				}
			}
			p.setLista_PreAvaliacoes(listaAux);
		}
		
		return listaAux;
	}
	
	public Avaliacao_ideia buscaPerguntas(Ideia_reuniao_pic ideia){
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		Avaliacao_ideia avaliacao = (Avaliacao_ideia ) this.getDao().getSession().createCriteria(Avaliacao_ideia.class)
				.add(Restrictions.eq("cd_ideia_reuniao_pic", ideia.getCd_ideia_reuniao_pic()))
				.add(Restrictions.eq("cd_reuniao_pic", ideia.getCd_reuniao_pic())).uniqueResult();
		
		if(avaliacao!=null){
			pesquisa.buscarObjetoCompleto(avaliacao);
			return avaliacao;
		}
		
		return null;
	}
}
