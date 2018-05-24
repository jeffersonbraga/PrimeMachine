package br.com.lsd.bo.verificacao_documental;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.programa_qualidade.Programa_qualidade;

public class Verificacao_documentalBO {

	@SuppressWarnings("unchecked")
	public List<Programa_qualidade> buscarProgramasQualidade() {
		GenericDAO dao = new GenericDAO();
		List<Programa_qualidade> listaProgramas = (List<Programa_qualidade>) dao.getSession().createCriteria(Programa_qualidade.class).list();
		PesquisaPadraoBO pesq = new PesquisaPadraoBO();
		for(Programa_qualidade p : listaProgramas){
			pesq.buscarObjetoCompleto( p );
		}
		
		return listaProgramas;
	}
	@SuppressWarnings("unchecked")
	public List<Execucao_formulario> buscarFormularios(Formulario formulario, Date dataInicial, Date dataFinal) {

		GenericDAO dao = new GenericDAO();

		GregorianCalendar dtInicio = new GregorianCalendar();
		dtInicio.setTime(dataInicial);
		dtInicio.set(dtInicio.HOUR_OF_DAY, 0);
		dtInicio.set(dtInicio.MINUTE, 0);

		GregorianCalendar dtTermino = new GregorianCalendar();
		dtTermino.setTime(dataFinal);
		dtTermino.set(dtTermino.HOUR_OF_DAY, dtTermino.getMaximum(dtTermino.HOUR_OF_DAY));
		dtTermino.set(dtTermino.MINUTE, dtTermino.getMaximum(dtTermino.MINUTE));

		formulario = (Formulario) dao.getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", formulario.getCd_formulario())).uniqueResult();

		List<Execucao_formulario> lista = (List<Execucao_formulario>) dao.getSession()
				.createCriteria(Execucao_formulario.class)
				.add(Restrictions.eq("cd_formulario", formulario.getCd_formulario_verificacao()))
				.add(Restrictions.between("dt_execucao_formulario", dtInicio.getTime(), dtTermino.getTime()))
				.addOrder(Order.asc("dt_execucao_formulario"))
				.list();

		dao.fecharConexao();
		if (lista != null && lista.size() > 0) {

			for (Execucao_formulario execucao_formulario : lista) {

				PesquisaPadraoBO pesq = new PesquisaPadraoBO();
				pesq.buscarObjetoCompleto(execucao_formulario);
			}
		}

		return lista;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Execucao_formulario> buscarTodosFormularios(Formulario formulario, Date dataInicial, Date dataFinal) {

		GenericDAO dao = new GenericDAO();
		List<Execucao_formulario> lista = new ArrayList<>();
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();

		dataFinal.setDate(dataFinal.getDate()+1);

		if(formulario == null ||formulario.getCd_formulario() == null){

			lista = (List<Execucao_formulario>) dao.getSession()
					.createCriteria(Execucao_formulario.class)
					.add(Restrictions.between("dt_execucao_formulario", dataInicial, dataFinal))
					.addOrder(Order.asc("dt_execucao_formulario"))
					.list();

			List<Execucao_formulario> listaAux = new ArrayList<>();

			for(Execucao_formulario exec:lista){

				pesquisa.buscarObjetoCompleto(exec);
				if(exec.getFormulario().getCd_programa_qualidade().equals( formulario.getCd_programa_qualidade() )){
					listaAux.add(exec);
				}
			}

			lista = new ArrayList<>();
			lista.addAll(listaAux);

		}else{
			lista = (List<Execucao_formulario>) dao.getSession()
					.createCriteria(Execucao_formulario.class)
					.add(Restrictions.eq("cd_formulario", formulario.getCd_formulario()))
					.add(Restrictions.between("dt_execucao_formulario", dataInicial, dataFinal))
					.addOrder(Order.asc("dt_execucao_formulario"))
					.list();
		}

		dao.fecharConexao();
		if (formulario.getCd_formulario() != null) {

			for (Execucao_formulario execucao_formulario : lista) {

				PesquisaPadraoBO pesq = new PesquisaPadraoBO();
				pesq.buscarObjetoCompleto(execucao_formulario);
			}
		}

		return lista;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Execucao_formulario> buscarTodosFormulariosChecklist(Formulario formulario, Date dataInicial, Date dataFinal, Integer cd_usuario) {

		GenericDAO dao = new GenericDAO();
		List<Execucao_formulario> lista = new ArrayList<>();
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();

		dataFinal.setDate(dataFinal.getDate()+1);

		if(formulario == null ||formulario.getCd_formulario() == null){

			if( cd_usuario != null ){
				lista = (List<Execucao_formulario>) dao.getSession()
						.createCriteria(Execucao_formulario.class)
						.add(Restrictions.between("dt_execucao_formulario", dataInicial, dataFinal))
						.add(Restrictions.eq("cd_usuario", cd_usuario))
						.addOrder(Order.asc("dt_execucao_formulario"))
						.list();
			}else{
			
				lista = (List<Execucao_formulario>) dao.getSession()
					.createCriteria(Execucao_formulario.class)
					.add(Restrictions.between("dt_execucao_formulario", dataInicial, dataFinal))
					.addOrder(Order.asc("dt_execucao_formulario"))
					.list();
			
			}
			
			List<Execucao_formulario> listaAux = new ArrayList<>();

			for(Execucao_formulario exec:lista){
				pesquisa.buscarObjetoCompleto(exec);
				listaAux.add(exec);
				
			}

			lista = new ArrayList<>();
			lista.addAll(listaAux);

		}else{
			if( cd_usuario != null ){
				lista = (List<Execucao_formulario>) dao.getSession()
						.createCriteria(Execucao_formulario.class)
						.add(Restrictions.eq("cd_formulario", formulario.getCd_formulario()))
						.add(Restrictions.between("dt_execucao_formulario", dataInicial, dataFinal))
						.add(Restrictions.eq("cd_usuario", cd_usuario))
						.addOrder(Order.asc("dt_execucao_formulario"))
						.list();
			}else{
				
			
				lista = (List<Execucao_formulario>) dao.getSession()
						.createCriteria(Execucao_formulario.class)
						.add(Restrictions.eq("cd_formulario", formulario.getCd_formulario()))
						.add(Restrictions.between("dt_execucao_formulario", dataInicial, dataFinal))
						.addOrder(Order.asc("dt_execucao_formulario"))
						.list();
			}
		}

		dao.fecharConexao();
		if (formulario.getCd_formulario() != null) {

			for (Execucao_formulario execucao_formulario : lista) {

				PesquisaPadraoBO pesq = new PesquisaPadraoBO();
				pesq.buscarObjetoCompleto(execucao_formulario);
			}
		}

		return lista;
	}

	public boolean salvarVerificacaoMobile(Execucao_formulario execuca_verificadora) {
		
		GenericDAO dao = new GenericDAO();
		dao.salvar(execuca_verificadora);
		return true;
	}		

	public boolean salvarVerificacao(Execucao_formulario execuca_verificadora, Execucao_formulario execuca_verificada) {
		
		GenericDAO dao = new GenericDAO();
		CrudGenericBO crud = new CrudGenericBO();
		PesquisaPadraoBO p = new PesquisaPadraoBO();
		
		Formulario f1 = ( Formulario ) dao.getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", execuca_verificadora.getCd_formulario())).uniqueResult(); 
		Formulario f2 = ( Formulario ) dao.getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", execuca_verificadora.getCd_formulario_verificacao())).uniqueResult();
		execuca_verificadora.setFormulario( f1 );
		execuca_verificadora.setFormulario_verificacao( f2 );
		
		f1 = ( Formulario ) dao.getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", execuca_verificada.getCd_formulario())).uniqueResult(); 
		f2 = ( Formulario ) dao.getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", execuca_verificada.getCd_formulario_verificacao())).uniqueResult();
		execuca_verificada.setFormulario( f1 );
		execuca_verificada.setFormulario_verificacao( f2 );
		
		crud.salvarObjetoCompleto( execuca_verificadora );
		crud.salvarObjetoCompleto( execuca_verificada );
		
		//GenericDAO dao = new GenericDAO();
		//dao.salvar(execuca_verificadora);
		//dao.salvar(execuca_verificada);
		return true;
	}
	
	public Execucao_formulario buscarVerificacaoExecutada(Integer tipo, Execucao_formulario execucao_verificada) {

		GenericDAO dao = new GenericDAO();
		Execucao_formulario execucao_verificadora = (Execucao_formulario) dao.getSession().createCriteria(Execucao_formulario.class).add(Restrictions.eq("cd_execucao_verificada", execucao_verificada.getCd_execucao_formulario())).add(Restrictions.eq("tp_verificacao", tipo)).uniqueResult();
		
		if(execucao_verificadora != null ){
			PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
			pesquisa.buscarObjetoCompleto( execucao_verificadora );
		}
		
		return execucao_verificadora;
	}
	
	public Formulario buscarFormularioMonitoramento(Integer cd_formulario) {
		GenericDAO dao = new GenericDAO();
		Formulario formulario_verificacao =  (Formulario) dao.getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", cd_formulario)).uniqueResult();
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		pesquisa.buscarObjetoCompleto( formulario_verificacao );
		return formulario_verificacao;
	}
	
}