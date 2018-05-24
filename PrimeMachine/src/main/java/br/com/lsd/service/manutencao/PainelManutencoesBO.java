package br.com.lsd.service.manutencao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.bo.relatorios.RelatorioBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.atv_exec_manutencao.Atv_exec_manutencao;
import br.com.primum.modelo.cronograma_manutencao.Cronograma_manutencao;
import br.com.primum.modelo.equipamento.Equipamento;
import br.com.primum.modelo.executar_manutencao.Executar_manutencao;
import br.com.primum.modelo.manutencao_preventiva.Manutencao_preventiva;
import br.com.primum.modelo.modulo.Modulo;

public class PainelManutencoesBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	public List<Cronograma_manutencao> buscarTodasManutencoes() {
		
		this.setDao(new GenericDAO());			
		
		List<Cronograma_manutencao> listaManutencoes = (List<Cronograma_manutencao>) this.getDao().getSession().createCriteria(Cronograma_manutencao.class).addOrder(Order.asc("dt_previsao_inicio")).list();

		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		for (Cronograma_manutencao exec : (List<Cronograma_manutencao>) listaManutencoes) {
			
			pesquisa.buscarObjetoCompleto(exec);
			List<Manutencao_preventiva> listaManutencoesPreventivas = (List<Manutencao_preventiva> )this.getDao().getSession().createCriteria(Manutencao_preventiva.class).add(Restrictions.eq("cd_manutencao_preventiva", exec.getCd_manutencao_preventiva())).list();
			//List<Manutencao_preventiva> listaManutencoesPreventivas = (List<Manutencao_preventiva> )this.getDao().getSession().createCriteria(Manutencao_preventiva.class).list();
			//List<Manutencao_preventiva> listaManutencoesPreventivas = (List<Manutencao_preventiva> )this.getDao().getSession().createSQLQuery("SELECT * FROM manutencao_preventiva").addEntity(Manutencao_preventiva.class).list();
			
			if(listaManutencoesPreventivas.size()>0){
				exec.setManutencao_preventiva(listaManutencoesPreventivas.get(0));
			}
		}
		return listaManutencoes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Atv_exec_manutencao> buscarTodasAtividades(Integer cd_executar_manutencao) {
		this.setDao(new GenericDAO());
		List<Atv_exec_manutencao> listaAtividades = this.getDao().getSession().createCriteria(Atv_exec_manutencao.class).add(Restrictions.eq("cd_executar_manutencao", cd_executar_manutencao)).list();
		return listaAtividades;
	}
	
	@SuppressWarnings("unchecked")
	public List<Executar_manutencao> buscarTodasExecucoes(Integer cd_cronograma_manutencao) {
		
		this.setDao(new GenericDAO());			
		List<Executar_manutencao> listaExecucoes = (List<Executar_manutencao>) this.getDao().getSession().createCriteria(Executar_manutencao.class).add(Restrictions.eq("cd_cronograma_manutencao", cd_cronograma_manutencao)).list();
		
		if(listaExecucoes.size() > 0){
			
			//for(Integer i = 0 ; i<listaExecucoes.size();i++ ){
				List<Atv_exec_manutencao> listaAtividades = (List<Atv_exec_manutencao>) this.getDao().getSession().createCriteria(Atv_exec_manutencao.class).add(Restrictions.eq("cd_executar_manutencao", listaExecucoes.get(0).getCd_executar_manutencao())).list();
				listaExecucoes.get(0).setListaAtividadesExtras(listaAtividades);
			//}
		}
		
		return listaExecucoes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Executar_manutencao> buscarTodasExecucoes(){
		
		this.setDao(new GenericDAO());			
		List<Executar_manutencao> listaExecucoes = (List<Executar_manutencao>) this.getDao().getSession().createCriteria(Executar_manutencao.class).list();
		
		return listaExecucoes;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Executar_manutencao> buscarAllManutencoes(){
		
		this.setDao(new GenericDAO());			
		List<Executar_manutencao> listaManutencoes = (List<Executar_manutencao>) this.getDao().getSession().createCriteria(Executar_manutencao.class).add(Restrictions.eq("st_executar_manutencao", 2)).list();
		
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		for (Executar_manutencao exec : (List<Executar_manutencao>) listaManutencoes) {
			
			pesquisa.buscarObjetoCompleto(exec);
			Cronograma_manutencao cronograma_manutencao = (Cronograma_manutencao) this.getDao().getSession().createCriteria(Cronograma_manutencao.class).add(Restrictions.eq("cd_cronograma_manutencao", exec.getCd_cronograma_manutencao())).uniqueResult();
			exec.setCronograma_manutencao(cronograma_manutencao);
			
			Equipamento equipamento = (Equipamento) this.getDao().getSession().createCriteria(Equipamento.class).add(Restrictions.eq("cd_equipamento", exec.getCd_equipamento())).uniqueResult();
			exec.setEquipamento(equipamento);
		}
		
		return listaManutencoes;
	}
	
	
	@SuppressWarnings({ "unchecked", "static-access" })
	public List<Executar_manutencao> buscarExecucoesPorEquipamento(Integer cd_equipamento, Integer ano){
		
		this.setDao(new GenericDAO());	
		List<Cronograma_manutencao> listaCronogramas = new ArrayList<>();
		List<Manutencao_preventiva> listaManutencoes = (List<Manutencao_preventiva>) this.getDao().getSession().createCriteria(Manutencao_preventiva.class).add(Restrictions.eq("cd_equipamento", cd_equipamento)).list();
		for(Manutencao_preventiva m : listaManutencoes ){
			List<Cronograma_manutencao> listaCronogramasAux = ( List<Cronograma_manutencao> ) this.getDao().getSession().createCriteria(Cronograma_manutencao.class).add(Restrictions.eq("cd_manutencao_preventiva", m.getCd_manutencao_preventiva())).list();
			listaCronogramas.addAll( listaCronogramasAux );
		}
		
		List<Executar_manutencao> listaExecucoes = new ArrayList<Executar_manutencao>();
		
		GregorianCalendar gAnoAtual = (GregorianCalendar) new GregorianCalendar().getInstance();
		GregorianCalendar gAnoSeguinte = (GregorianCalendar) new GregorianCalendar().getInstance();
		gAnoAtual.set(Calendar.YEAR, ano );
		gAnoAtual.set(Calendar.DAY_OF_YEAR, 1);
		gAnoAtual.set(Calendar.MONTH, 0);
		gAnoAtual.set(Calendar.HOUR_OF_DAY, 0);
		gAnoAtual.set(Calendar.MINUTE, 0);
		gAnoAtual.set(Calendar.SECOND, 0);
		
		gAnoSeguinte.set(Calendar.YEAR, ano + 1 );
		gAnoAtual.set(Calendar.DAY_OF_YEAR, 1);
		gAnoAtual.set(Calendar.MONTH, 0);
		gAnoAtual.set(Calendar.HOUR_OF_DAY, 0);
		gAnoAtual.set(Calendar.MINUTE, 0);
		gAnoAtual.set(Calendar.SECOND, 0);
		
		PesquisaPadraoBO p = new PesquisaPadraoBO();
		for(Cronograma_manutencao c : listaCronogramas ){
			p.buscarObjetoCompleto( c );
			if( c.getDt_previsao_inicio() != null){
				if( c.getDt_previsao_inicio().getTime() > gAnoAtual.getTime().getTime() && 
						c.getDt_previsao_inicio().getTime() < gAnoSeguinte.getTime().getTime() ){
					
					try {
						Executar_manutencao exe = (Executar_manutencao) this.getDao().getSession().createCriteria(Executar_manutencao.class).add(Restrictions.eq("cd_cronograma_manutencao", c.getCd_cronograma_manutencao())).uniqueResult();
						if( exe != null ){
							if(exe.getCd_executar_manutencao() != null){
								p.buscarObjetoCompleto( exe );
								exe.setNm_resp_execucao(RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", exe.getCronograma_manutencao().getCd_usuario().toString()));
								listaExecucoes.add( exe );
							}else{
								Executar_manutencao exeAux = new Executar_manutencao();
								exeAux.setSt_executar_manutencao(0);
								exeAux.setCronograma_manutencao( c );
								exeAux.setNm_resp_execucao(RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", c.getCd_usuario().toString()));
								listaExecucoes.add( exeAux );
							}
						}else{
							Executar_manutencao exeAux = new Executar_manutencao();
							exeAux.setSt_executar_manutencao(0);
							exeAux.setCronograma_manutencao( c );
							exeAux.setNm_resp_execucao(RelatorioBO.toText("usuario", "cd_usuario", "nm_usuario", c.getCd_usuario().toString()));
							listaExecucoes.add( exeAux );
						}
						
					} catch (Exception e) {
						LogPrimum.mensagem(" 2 OU MAIS EXECUTAR MANUTENCOEES - CLASSE PainelManutencoesBO");
					}
				}
			}
			
		}
		
		return listaExecucoes;
	}
	
//	@SuppressWarnings("unchecked")
//	public List<Cronograma_manutencao> buscarCronogramas(){
//		
//		this.setDao(new GenericDAO());			
//		List<Cronograma_manutencao> listaCronogramas = (List<Cronograma_manutencao>) this.getDao().getSession().createCriteria(Cronograma_manutencao.class).addOrder( Order.asc("dt_previsao_inicio") ).list();
//		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
//		for(Cronograma_manutencao cronograma : listaCronogramas){
//			pesquisa.buscarObjetoCompleto( cronograma );
//			
//			List<Atividades_cronograma_manutencao> listaAtividades = (List<Atividades_cronograma_manutencao>) this.getDao().getSession().createCriteria(Atividades_cronograma_manutencao.class).add( Restrictions.eq("cd_cronograma_manutencao",cronograma.getCd_cronograma_manutencao() ) ).list();
//			Manutencao_preventiva manutencao = (Manutencao_preventiva) this.getDao().getSession().createCriteria(Manutencao_preventiva.class).add( Restrictions.eq("cd_manutencao_preventiva",cronograma.getCd_manutencao_preventiva() ) ).uniqueResult();
//			
//			cronograma.setListaAtividadesExecucao(listaAtividades);
//			cronograma.setManutencao_preventiva(manutencao);
//			
//			System.out.print( cronograma.getListaAtividadesExecucao().size() );
//		}
//		
//		return listaCronogramas; 
//	}
//	
//	public Boolean mudaNr_Executada_Atividade(List<?> listaAtividades){
//		
//		this.setDao(new GenericDAO());
//		Boolean resultado = true;
//		
//		Atividades_manutencao manutencao = null;
//		Atividades_cronograma_manutencao cronograma = null;
//		
//		for(int i = 0 ; i < listaAtividades.size() ; i++){
//			if(listaAtividades.get(i).getClass().getName() == "br.com.primum.modelo.atividades_manutencao.Atividades_manutencao"){
//				manutencao = (Atividades_manutencao)listaAtividades.get(i);
//			}else if(listaAtividades.get(i).getClass().getName() == "br.com.primum.modelo.atividades_cronograma_manutencao.Atividades_cronograma_manutencao"){
//				cronograma = (Atividades_cronograma_manutencao)listaAtividades.get(i);
//			}
//
//			if(manutencao!=null){
//				try {
//					this.getDao().salvar(manutencao);
//				} catch (Exception e) {
//					resultado = false;
//					e.printStackTrace();
//					break;
//				}
//			}else if(cronograma!=null){
//				try {
//					this.getDao().salvar(cronograma);
//				} catch (Exception e) {
//					resultado = false;
//					e.printStackTrace();
//					break;
//				}
//			}
//		}
//	
//		return resultado;
//	}
	
}
