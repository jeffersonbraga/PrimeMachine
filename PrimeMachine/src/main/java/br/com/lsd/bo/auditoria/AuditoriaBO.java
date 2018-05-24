package br.com.lsd.bo.auditoria;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.auditoria.Auditoria;
import br.com.primum.modelo.grupo_auditoria.Grupo_auditoria;
import br.com.primum.modelo.pergunta_auditoria.Pergunta_auditoria;
import br.com.primum.modelo.plano_acao.Plano_acao;
import br.com.primum.modelo.resposta_auditoria.Resposta_auditoria;
import br.com.primum.modelo.resposta_exec_auditoria.Resposta_exec_auditoria;
import br.com.primum.reflection.Reflection;

public class AuditoriaBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	public boolean salvarPlanoAuditoria(Auditoria auditoria) {

		boolean erro = false;
		try{

			this.setDao(new GenericDAO());			
			this.getDao().abrirTransacao();

			this.getDao().atualizar(auditoria);
			for (Grupo_auditoria itemGrupo : auditoria.getListaGruposAuditoria()) {

				itemGrupo.setCd_auditoria(auditoria.getCd_auditoria());
				Reflection.setValorChaveEstrangeira(auditoria, itemGrupo);
				this.getDao().salvar(itemGrupo);

				if (itemGrupo.getListaPerguntasAuditoria() != null) {
					for (Pergunta_auditoria itemPergunta : itemGrupo.getListaPerguntasAuditoria()) {

						Reflection.setValorChaveEstrangeira(itemGrupo, itemPergunta);
						this.getDao().salvar(itemPergunta);
					}
				}
			}

			for (Resposta_auditoria itemResposta : auditoria.getListaRespostasAuditoria()) {
				Reflection.setValorChaveEstrangeira(auditoria, itemResposta);
				this.getDao().salvar(itemResposta);
			}

			this.getDao().fecharConexao();

		} catch (Exception e){

			e.printStackTrace();
			erro = true;
		}

		return erro;
	}

	public Auditoria buscarDadosPlanoAuditoria(Auditoria auditoria) {

		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		pesquisa.buscarObjetoCompleto(auditoria);
		return auditoria;
	}
	
	@SuppressWarnings("unchecked")
	public List<Resposta_exec_auditoria> buscarRespostasExecucaoAuditoria(List<Grupo_auditoria> lista_grupo_auditoria, Integer cd_grupo_auditoria){
		
		this.setDao(new GenericDAO());
		List<Resposta_exec_auditoria> lista = new ArrayList<>();
		List<Resposta_exec_auditoria> listaAux = new ArrayList<>();
		
		if(lista_grupo_auditoria!=null){
			if(cd_grupo_auditoria==null || cd_grupo_auditoria==0){
				if(lista_grupo_auditoria.size()>0){
					for(int i=0;i< lista_grupo_auditoria.size();i++){
						listaAux = new ArrayList<>();
						listaAux = (List<Resposta_exec_auditoria>) this.getDao().getSession().createCriteria(Resposta_exec_auditoria.class).add(Restrictions.eq("cd_grupo_auditoria", lista_grupo_auditoria.get(i).getCd_grupo_auditoria())).list();
						lista.addAll(listaAux);
					}
				}
			}else{
				lista = (List<Resposta_exec_auditoria>) this.getDao().getSession().createCriteria(Resposta_exec_auditoria.class).add(Restrictions.eq("cd_grupo_auditoria", cd_grupo_auditoria )).list();
			}
		}else{
			lista = (List<Resposta_exec_auditoria>) this.getDao().getSession().createCriteria(Resposta_exec_auditoria.class).list();
		}
		
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		for( Resposta_exec_auditoria resposta : lista){
			pesquisa.buscarObjetoCompleto(resposta);
			Resposta_auditoria resposta_auditoria = (Resposta_auditoria) this.getDao().getSession().createCriteria(Resposta_auditoria.class).add(Restrictions.eq("cd_resposta_auditoria", resposta.getCd_resposta_auditoria())).uniqueResult();
			resposta.setResposta_auditoria(resposta_auditoria);
			Pergunta_auditoria pergunta_auditoria = (Pergunta_auditoria) this.getDao().getSession().createCriteria(Pergunta_auditoria.class).add(Restrictions.eq("cd_pergunta_auditoria", resposta.getCd_pergunta_auditoria())).uniqueResult();
			resposta.setPergunta_auditoria(pergunta_auditoria);
		}
		
		listaAux = new ArrayList<>();
		for(int i=0;i< lista.size();i++){
			if(lista.get(i).getResposta_auditoria().getTp_resposta_auditoria()==0){
				listaAux.add(lista.get(i));
				
				if( lista.get(i).getPlano_acao_auditoria()!=null ){
					pesquisa.buscarObjetoCompleto( lista.get(i).getPlano_acao_auditoria() );
					Plano_acao plano = (Plano_acao) this.getDao().getSession().createCriteria(Plano_acao.class).add(Restrictions.eq("cd_plano_acao", lista.get(i).getPlano_acao_auditoria().getCd_plano_acao() )).uniqueResult();
					lista.get(i).getPlano_acao_auditoria().setPlano_acao(plano);
				}
				
			}
		}
		
		return listaAux;
	}
	
}