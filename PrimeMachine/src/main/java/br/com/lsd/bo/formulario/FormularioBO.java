package br.com.lsd.bo.formulario;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.interfaces.ChaveEstrangeira;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.escalonamento_formulario.Escalonamento_formulario;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.programa_qualidade.Programa_qualidade;
import br.com.primum.modelo.revisao_programa_qualidade.Revisao_programa_qualidade;
import br.com.primum.modelo.sub_formulario.Sub_formulario;
import br.com.primum.modelo.tabela.Tabela;
import br.com.primum.modelo.tipo_campo.Tipo_campo;
import br.com.primum.reflection.Reflection;

public class FormularioBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
//	public Boolean buscaRevisaoeSetaStNova_Revisao(Integer cd_revisao) {
//		Boolean resultado=true;
//		this.setDao(new GenericDAO());
//		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
//		Revisao_programa_qualidade revisao = (Revisao_programa_qualidade) this.getDao().getSession().createCriteria(Revisao_programa_qualidade.class).add(Restrictions.eq("cd_revisao_programa_qualidade",cd_revisao)).uniqueResult();
//		if(revisao!=null){
//			pesquisa.buscarObjetoCompleto(revisao);
//			try {
//				//SETA REVISAO COMO ARQUIVADA
//				revisao.setSt_nova_revisao(1);
//				CrudGenericBO crudGenericBO = new CrudGenericBO();
//				crudGenericBO.atualizar(revisao);
//				
//			} catch (Exception e) {
//				resultado = false;
//				e.printStackTrace();
//			}
//		}
//		
//		return resultado;
//	}
	
	public Boolean verificaRevisaoAprovada(Integer cd_revisao) {
		this.setDao(new GenericDAO());		
		Revisao_programa_qualidade revisao = (Revisao_programa_qualidade) this.getDao().getSession().createCriteria(Revisao_programa_qualidade.class).add(Restrictions.eq("cd_revisao_programa_qualidade",cd_revisao)).uniqueResult();
		return revisao.getSt_revisao_programa_qualidade().equals(2);
	}
	
	
	public Boolean buscaRevisaoeArquiva(Integer cd_revisao) {
		
		Boolean resultado=true;
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		Revisao_programa_qualidade revisao = (Revisao_programa_qualidade) this.getDao().getSession().createCriteria(Revisao_programa_qualidade.class).add(Restrictions.eq("cd_revisao_programa_qualidade",cd_revisao)).uniqueResult();
		if(revisao!=null){
			pesquisa.buscarObjetoCompleto(revisao);
			try {
				//SETA REVISAO COMO ARQUIVADA
				revisao.setSt_revisao_programa_qualidade(4);
				CrudGenericBO crudGenericBO = new CrudGenericBO();
				crudGenericBO.atualizar(revisao);
				
			} catch (Exception e) {
				resultado = false;
				e.printStackTrace();
			}
		}
		
		return resultado;
	}
	
	public List<?> buscarDadosFiltroSubFormulario(Integer cd_tabela) {

		this.setDao(new GenericDAO());
		List<?> listaBusca = null;
		try {

			Tabela tabela = (Tabela) this.getDao().findById(new Tabela(), cd_tabela);
			if ( (tabela != null) && (tabela.getDs_caminhoclasse() != null) ) {
				listaBusca = this.getDao().getSession().createCriteria( Class.forName(tabela.getDs_caminhoclasse()) ).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaBusca;
	}

	public List<?> buscarObjetosPaleta() {
		this.setDao(new GenericDAO());
		List<?> lista  = this.getDao().getSession().createCriteria(Tipo_campo.class).list();
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<?> buscarFormulariosExecutados() {
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		pesquisa.setDao(new GenericDAO());
		List<?> lista  = pesquisa.getDao().getSession().createCriteria(Execucao_formulario.class).list();

		for (Execucao_formulario execucao : (List<Execucao_formulario>) lista) {
			pesquisa.buscarObjetoCompleto(execucao);
		}
		return lista;
	}

	public List<?> pesquisar(Integer cd_programa){
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Formulario> buscarFormulariosPorRevisao(Integer cd_revisao_programa_qualidade){
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		this.setDao(new GenericDAO());
		
		List<Formulario> lista  = this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_revisao_programa_qualidade", cd_revisao_programa_qualidade)).addOrder(Order.asc("nm_formulario")).list();

		for (Formulario formulario : (List<Formulario>) lista) {
			pesquisa.buscarObjetoCompleto(formulario);
		}
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> buscarProgramasQualidade_Formularios(){
		
		this.setDao(new GenericDAO());
		
		List<Programa_qualidade> listaProgramas  = this.getDao().getSession().createCriteria(Programa_qualidade.class).list();
		List<List<Formulario>> listaListas = new ArrayList<>();
		List<List<?>> listaTotal = new ArrayList<>();
		
		for (Programa_qualidade programa : (List<Programa_qualidade>) listaProgramas) {
			List<Formulario> listaFormularios  = this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_programa_qualidade", programa.getCd_programa_qualidade())).list();
			listaListas.add(listaFormularios);
		}
		
		listaTotal.add(listaProgramas);
		listaTotal.add(listaListas);
		
		
		return listaTotal;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> renovarFormulariosEFilhos( List<Formulario> listaFormularios, Integer novoCd_revisao){
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		List<List<?>> listaItens = new ArrayList<>();
		List<Formulario> listaForms = new ArrayList<>();
		List<List<?>> listaFinalAux = new ArrayList<>();
		List<List<?>> listaFinal = new ArrayList<>();
		
		for(Formulario f:listaFormularios){
			
			f.setCd_formulario_origem(f.getCd_formulario());
			for(Sub_formulario sf: f.getListaSub_formulario()){
				sf.setSub_formulario_origem(sf.getCd_sub_formulario());
			}
			
			
			
			List<Escalonamento_formulario> listaEscalonamentos = (List<Escalonamento_formulario>) this.getDao().getSession().createCriteria(Escalonamento_formulario.class).add(Restrictions.eq("cd_formulario", f.getCd_formulario())).list();
			if(listaEscalonamentos!=null){
				if(listaEscalonamentos.size()>0){
					for(Escalonamento_formulario esc:listaEscalonamentos){
						pesquisa.buscarObjetoCompleto(esc);
					}
				}
			}
			
			
			
			f.setCd_revisao_programa_qualidade(novoCd_revisao);
			getFields(f);
			
			listaItens = new ArrayList<>();
			listaForms = new ArrayList<>();
			listaFinalAux = new ArrayList<>();
			
			listaForms.add(f);
			listaItens.add(listaEscalonamentos);
			listaFinalAux.add(listaForms);
			listaFinalAux.add(listaItens);
			listaFinal.add(listaFinalAux);
		}
		return listaFinal;
	}
	
	public void getFields(Object o){
		Field[] fields = o.getClass().getDeclaredFields();
		
		try {
			String nomeChavePrimaria = Reflection.getNomeChavePrimaria(o);
			for (Field field : fields) {
				
				if(field.getName().equals(nomeChavePrimaria)){
					Reflection.setValorChavePrimaria(o,null);
				}else{
					if ( field.getType().getName().equals("java.util.List") ) {
						if ( (field.isAnnotationPresent(ListaBusca.class)) && (field.getAnnotation(ListaBusca.class).persistir()) ) {
							
							List<?> lista = (List<?>) Reflection.getValorCampo(o, field.getName());
							if ( (lista != null) && (lista.size() > 0) ) {

								for (Object objetoFilho : lista) {
									getFields(objetoFilho);
								}
							}
						}
					}else if (field.isAnnotationPresent(ObjetoBusca.class) && field.getAnnotation(ObjetoBusca.class).persistir()) {
						Object objetoFilho = Reflection.getValorCampo(o, field.getName());
						if(objetoFilho != null) {
							getFields(objetoFilho);
						}
					}else{
						if( field.isAnnotationPresent(ChaveEstrangeira.class) ){
							Object aux = null;
							String nome = field.getName();
							nome = "set" + nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
							Method metodo = o.getClass().getMethod(nome, Integer.class);
							metodo.invoke( o, aux );
						}
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public Formulario salvarFormulario(Formulario f){

		CrudGenericBO crud = new CrudGenericBO();
		//Formulario formularioRetorno = (Formulario) crud.salvarObjetoCompleto( f );
		crud.salvarObjetoCompleto( f );
		return f;
	}

	@SuppressWarnings("unchecked")
	public List<Formulario> buscaListaFormulariosPorProgramaQualidade(Integer cd_programa_qualidade){

		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Formulario> listaFormularios = ( List<Formulario> ) this.getDao().getSession().createCriteria(Formulario.class)
				.add(Restrictions.eq("cd_programa_qualidade", cd_programa_qualidade))
				.add(Restrictions.eq("tp_formulario", 0))
				.list(); 

		for(Formulario f:listaFormularios){
			pesquisa.buscarObjetoCompleto( f );
		}

		return listaFormularios;
	}

	@SuppressWarnings("unchecked")
	public List<?> buscarDadosFormulariosExecutados(Integer cd_formulario) {
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		pesquisa.setDao(new GenericDAO());
		List<?> lista  = pesquisa.getDao().getSession().createCriteria(Execucao_formulario.class).add(Restrictions.eq("cd_formulario", cd_formulario)).list();

		for (Execucao_formulario execucao : (List<Execucao_formulario>) lista) {
			pesquisa.buscarObjetoCompleto(execucao);
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object> buscarFormulariosMenuPrograma(Integer cd_usuario) {
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		pesquisa.setDao(new GenericDAO());
		List<?> lista  = pesquisa.getDao().getSession().createCriteria(Revisao_programa_qualidade.class).add(Restrictions.eq("st_revisao_programa_qualidade", Revisao_programa_qualidade.REVISAO_APROVADA)).list();
		List<Object> listaFormulario  = new ArrayList<Object>();

		for (Revisao_programa_qualidade rev : (List<Revisao_programa_qualidade>) lista) {

			Programa_qualidade prg = (Programa_qualidade) pesquisa.getDao().getSession().createCriteria(Programa_qualidade.class).add(Restrictions.eq("cd_programa_qualidade", rev.getCd_programa_qualidade())).uniqueResult();

			listaFormulario.add(prg);
			List<?> listaAux = pesquisa.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_revisao_programa_qualidade", rev.getCd_revisao_programa_qualidade())).add(Restrictions.eq("st_formulario", Formulario.APROVADO)).addOrder(Order.asc("nm_formulario")).list();

			if ( (listaAux != null) && (listaAux.size() > 0) ) {
				listaFormulario.add(listaAux);
			}
		}

		return listaFormulario;
	}
}