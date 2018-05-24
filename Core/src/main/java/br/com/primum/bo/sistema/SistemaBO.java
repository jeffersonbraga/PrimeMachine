package br.com.primum.bo.sistema;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Table;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.crud.CrudGenericBO;
//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.campo.Campo;
import br.com.primum.modelo.cliente.Cliente;
import br.com.primum.modelo.cronograma_manutencao.Cronograma_manutencao;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.executar_manutencao.Executar_manutencao;
import br.com.primum.modelo.manutencao_preventiva.Manutencao_preventiva;
import br.com.primum.modelo.menu.Menu;
import br.com.primum.modelo.permissao_padrao.Permissao_padrao;
import br.com.primum.modelo.pessoa.Pessoa;
import br.com.primum.modelo.programa.Programa;
import br.com.primum.modelo.tabela.Tabela;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.notificadores.Helpdesk_notificador;

public class SistemaBO {
	
	private GenericDAO dao;
	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	public Integer setaCampoObrigatorio(Object dataSource, String datafield, Integer valor){
		
		this.setDao(new GenericDAO());
		
		//0 - OK
		//1 - NÃO EXISTE TABELA CRIADA
		//2 - NÃO EXISTE CAMPO CRIADO
		//3 - ERRO AO ATUALIZAR CAMPO
		Integer resultado=0;
		
		Boolean existe = dataSource.getClass().isAnnotationPresent(Table.class);
		if(existe){
			
			//LogPrimum.mensagem("> " +   dataSource.getClass().getAnnotation(Table.class).name() );
			Tabela tabela = null;
			
			try{
				tabela = (Tabela) this.getDao().getSession().createCriteria(Tabela.class)
						.add(Restrictions.eq("nm_tabela", dataSource.getClass().getAnnotation(Table.class).name()))
						.uniqueResult();
					
			}catch (Exception e) {
				//LogPrimum.mensagem("2 OU MAIS CAMPOS PARA UMA TABELA. CHECAR CD_TABELA = "+ tabela.getNm_tabela());
			}
			
			if(tabela!=null){
				
				Campo campo=null;
				try{
					campo = (Campo) this.getDao().getSession().createCriteria(Campo.class)
							.add(Restrictions.eq("cd_tabela", tabela.getCd_tabela()))
							.add(Restrictions.eq("nm_campo", datafield))
							.uniqueResult();
				
				}catch (Exception e) {
					//LogPrimum.mensagem("2 OU MAIS CAMPOS PARA UMA TABELA. CHECAR CD_TABELA = "+ tabela.getNm_tabela());
				}
				
				if(campo!=null){
					
					campo.setSt_obrigatorio(valor);
					
					CrudGenericBO crud = new CrudGenericBO();
					try {
						crud.atualizar(campo);
					} catch (Exception e) {
						resultado = 3;
						e.printStackTrace();
					}
					
				}else{
					resultado = 2;
				}
				
			}else{
				resultado=1;
			}
		
		}
		return resultado;
	}
	
	public String enviaEmailAteracaoSenha(Usuario usuario){
		try {
			this.setDao(new GenericDAO());
			Pessoa pessoa = (Pessoa) this.getDao().getSession().createCriteria(Pessoa.class).add(Restrictions.eq("cd_pessoa", usuario.getCd_pessoa())).uniqueResult();
			Helpdesk_notificador.enviaEmailParaPessoa( usuario.getCd_usuario(), "Alteração de senha", "Usuário: "+usuario.getNm_usuario()+"<br> Senha atual: "+usuario.getDs_senha());
			
			if(pessoa!=null){
				return pessoa.getDs_email();
			}
			
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<List<Integer>> listaMeses(Integer cd_equipamento, Integer ano){
		
		this.setDao(new GenericDAO());
		List<List<?>> listaTotal = new ArrayList<>();
		List<Cronograma_manutencao> listaCronogramasAux = new ArrayList<Cronograma_manutencao>();
		List<Cronograma_manutencao> listaCronogramas = new ArrayList<Cronograma_manutencao>();
		List<Manutencao_preventiva> listaManutencoes = (List<Manutencao_preventiva>) this.getDao().getSession().createCriteria(Manutencao_preventiva.class)
				.add(Restrictions.eq("cd_equipamento", cd_equipamento)).list();
		
		for(Manutencao_preventiva m : listaManutencoes){
			listaCronogramasAux.addAll( (List<Cronograma_manutencao>) this.getDao().getSession().createCriteria(Cronograma_manutencao.class)
				.add(Restrictions.eq("cd_manutencao_preventiva", m.getCd_manutencao_preventiva())).list() );
		}
					
		for(Cronograma_manutencao c: listaCronogramasAux){
			if(c.getDt_execucao().getYear() == ano){
				listaCronogramas.add( c );
			}
		}
		
		
		for(Cronograma_manutencao c: listaCronogramasAux){
			List<Integer> listAux = new ArrayList<>();
			Executar_manutencao execucao = null;
			
			GregorianCalendar data = new GregorianCalendar();
			data.set(Calendar.YEAR, c.getDt_execucao().getYear());
			listAux.add( data.get(Calendar.YEAR) );
			data.set(Calendar.MONTH, c.getDt_execucao().getMonth());
			listAux.add( data.get(Calendar.MONTH) );
			data.set(Calendar.DATE, c.getDt_execucao().getDate());
			listAux.add( data.get(Calendar.DATE) );
			data.set(Calendar.DATE, c.getHr_execucao().getDate());
			listAux.add( data.get(Calendar.DATE) );
			
			
//			try {
//				execucao = (Executar_manutencao) this.getDao().getSession().createCriteria(Executar_manutencao.class)
//						.add(Restrictions.eq("cd_cronograma_manutencao", c.getCd_cronograma_manutencao()))
//						.add(Restrictions.eq("cd_usuario", cd_usuario))
//						.add(Restrictions.eq("cd_escalonamento_usuario", esc.getCd_escalonamento_usuario()))
//						.add(Restrictions.ge("dt_execucao_formulario", dataInicialDia.getTime()))
//						.add(Restrictions.le("nr_hora_termino", dataFinalDia.getTime())).uniqueResult();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				//LogPrimum.mensagem("2 OU MAIS EXECUÇÕES PARA UMA TAREFA NA MESMA DATA. CHECAR ESCALONAMENTO_BO");
//			}

//			if(execucao!=null && execucao.getCd_execucao_formulario()!=null){
//				st_escalonamento_usuario = this.verificaSituacaoAtividade(execucao, esc, formulario);
//
//				if (execucao.getDt_execucao_formulario() != null) {
//					esc.setHr_execucao_efetiva( execucao.getDt_execucao_formulario().toString().split(" ")[1] );
//				}
//
//				if (execucao.getNr_hora_termino() != null) {
//					esc.setHr_termino_efetivo( execucao.getNr_hora_termino().toString().split(" ")[1] );
//				}
//			}
//			
//			listAux.add( verificaSituacao() );
			
		}
		
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean executaScriptTodosSchemas( String SQL , Integer tp_SQL){
		
		this.setDao(new GenericDAO());
		String[] arrSQL = null;

		List<Cliente> listaClientes = (List<Cliente>) this.getDao().getSession().createCriteria(Cliente.class).list();
		for(Cliente cli : listaClientes){
			
			String execucao = "";
		
			if(tp_SQL.equals(0)){
				//INSERT
				arrSQL = SQL.toUpperCase().split("INTO ");
				execucao += arrSQL[0];
				execucao += "INTO " + cli.getNm_schema()+ "."+ arrSQL[1]; 
				
			}else if(tp_SQL.equals(1)){
				//UPDATE
				arrSQL = SQL.toUpperCase().split(" ");
				execucao += arrSQL[0] + " ";
				execucao += cli.getNm_schema()+ "."+ arrSQL[1];
				
			}else if(tp_SQL.equals(2)){
				//DELETE
				arrSQL = SQL.toUpperCase().split("FROM");
				execucao += arrSQL[0] + " FROM ";
				execucao += cli.getNm_schema()+ "."+ arrSQL[1];
				
			}
			System.out.println(execucao);
		}
		
		this.getDao().getSession().createSQLQuery(SQL).executeUpdate();
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Programa> getListaPermissoesPadrao(){
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		//List<Permissao_padrao> listaPermissoes = ( List<Permissao_padrao> ) this.getDao().getSession().createCriteria(Permissao_padrao.class).list();
		List<Programa> listaProgramas = new ArrayList<>();
		
//		if( listaPermissoes !=null && listaPermissoes.size() > 0 ){
//			for( Permissao_padrao p : listaPermissoes ){ 
//				pesquisa.buscarObjetoCompleto(p);
//			}
//			return listaPermissoes;
//		}else{
			//listaPermissoes = new ArrayList<>();
			List<Menu> listaMenus = (List<Menu>) this.getDao().getSession().createCriteria(Menu.class).add(Restrictions.isNotNull("cd_programa")).
					addOrder(Order.asc("cd_programa"))
					.setProjection(Projections.distinct(Projections.property("cd_programa")))
					.list();
			
			for( Integer i = 0 ; i<listaMenus.size() ; i++ ){
				
				Programa p = (Programa) this.getDao().getSession().createCriteria(Programa.class).add(Restrictions.eq("cd_programa", listaMenus.get(i))).uniqueResult();
				listaProgramas.add(p);
				
//				Permissao_padrao pp = new Permissao_padrao();
//				pp.setCd_programa( menu.getCd_programa() );
//				pesquisa.buscarObjetoCompleto(pp);
//				listaPermissoes.add(pp);
			}
	//	}
	//	return listaPermissoes;
			
			return listaProgramas;
	}
	
	public Boolean deletaPermissoes(){
		this.setDao(new GenericDAO());
		try {
			this.getDao().getSession().createSQLQuery("DELETE FROM permissao_padrao;").executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
}
