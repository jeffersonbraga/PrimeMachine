package br.com.primum.bo.pesquisa;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.campo.Campo;
import br.com.primum.modelo.campospesquisa.CamposPesquisa;
import br.com.primum.modelo.codigosfixos.CodigosFixos;
import br.com.primum.modelo.dicionario.DicionarioDados;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.pessoa.Pessoa;
import br.com.primum.modelo.programa.Programa;
import br.com.primum.modelo.tabela.Tabela;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.reflection.Reflection;
import br.com.primum.service.dicionario.DicionarioService;

public class PesquisaPadraoBO {

	private GenericDAO dao;
	
	public PesquisaPadraoBO() {
	}

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	public Programa carregarDadosJanelaPesquisa(Integer cd_programa) throws Exception {
		this.setDao(new GenericDAO());
		//List<?> lista = this.getDao().findBySql(this.getSQLDadosJanelaPesquisa(cd_programa), new PesquisaPadraoBean());
		Programa programa 	= (Programa) this.getDao().findById(new Programa(), cd_programa);
		this.buscarObjetoCompleto(programa);
		
		if(programa.getCd_campo_filtro()!=null){
			Campo campoFiltro = (Campo) this.getDao().getSession().createCriteria(Campo.class).add(Restrictions.eq("cd_campo", programa.getCd_campo_filtro())).uniqueResult();
			Tabela tabelaOrigem = (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("cd_tabela", campoFiltro.getCd_tabelaorigem())).uniqueResult();
			programa.setListaValoresFiltro( this.getDao().findAll( Class.forName(tabelaOrigem.getDs_caminhoclasse()).newInstance() ) );
		}
		
		List<Object> lista = new ArrayList<>();
		for (CamposPesquisa campoPesquisa : programa.getListaCamposPesquisa()) {

			if (campoPesquisa.getCampo().getCd_tabelaorigem() != null) {

				lista.addAll(this.buscarInformacaoCampoEstrangeiro(campoPesquisa));
			}
		}

		programa.setListaDados(lista);
		return programa;
	}

	public List<?> buscarInformacaoFiltroCampoEstrangeiro(Integer cd_tabela_origem) {

		if(this.getDao()==null){
			this.setDao(new GenericDAO());
		}
		
		List<List<?>> listaResultado = new ArrayList<>();
		List<Campo> listaCampo = new ArrayList<Campo>();
		Campo campo = (Campo) this.getDao().getSession().createCriteria(Campo.class)
				.add(Restrictions.eq("cd_tabela", cd_tabela_origem))
				.add(Restrictions.eq("tp_identificador", 1)).uniqueResult();
		
		listaCampo.add(  campo );
		listaResultado.add(  listaCampo );
		
		Tabela tabela = (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("cd_tabela", cd_tabela_origem)).uniqueResult();
		List<?> lista = null;
		try {
			lista = this.getDao().getSession().createCriteria(Class.forName(tabela.getDs_caminhoclasse())).list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		listaResultado.add(  lista  );
		
		return listaResultado;
	}
	
	public List<?> buscarInformacaoCampoEstrangeiro(CamposPesquisa campoPesquisa) {
		
		if(this.getDao()==null){
			this.setDao(new GenericDAO());
		}
		
		Tabela tabela = (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("cd_tabela", campoPesquisa.getCampo().getCd_tabelaorigem())).uniqueResult();
		List<?> lista = null;
		try {
			lista = this.getDao().getSession().createCriteria(Class.forName(tabela.getDs_caminhoclasse())).list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<CodigosFixos> buscarInformacaoCodigosFixos( Integer cd_tabela, String nm_campo ) {
		
		this.setDao(new GenericDAO());
		
		try {
			
			Campo campo = (Campo) this.getDao().getSession().createCriteria(Campo.class).add(Restrictions.eq("nm_campo", nm_campo )).
				add(Restrictions.eq("cd_tabela", cd_tabela )).uniqueResult();
		
			List<CodigosFixos> listaCodigosfixos = (List<CodigosFixos>) this.getDao().getSession().createCriteria(CodigosFixos.class)
				.add(Restrictions.eq("cd_campo", campo.getCd_campo() )).list();
		
			return listaCodigosfixos;
		} catch (Exception e) {
			//LogPrimum.mensagem("TALVEZ DOIS CAMPOS IGUAIS DA MESMA TABELA ... VERIFICAR ISSO");
			return null;
		}
	}
	/*
	private String getSQLDadosJanelaPesquisa(Integer cd_programa) {
		String query = "select " +
				"campo.cd_campo as \"cd_campo\", " +
				"campo.nm_campo as \"nm_campo\", " +
				"campo.ds_label as \"ds_label\", " +
				"campo.cd_tipocampo as \"cd_tipocampo\", " +
				"campo.cd_tabela as \"cd_tabela\", " +
				"campospesquisa.cd_campospesquisa as \"cd_campospesquisa\", " +
				"campospesquisa.cd_programa as \"cd_programa\", " +
				"campospesquisa.st_listapesquisa as \"st_listapesquisa\", " +
				"campospesquisa.st_gridpesquisa as \"st_gridpesquisa\" " +
				"from " +
				"campospesquisa " +
				"inner join campo on " +
				"campospesquisa.cd_campo = campo.cd_campo " +
				"where " +
				"campospesquisa.cd_programa = " + cd_programa;
		//LogPrimum.mensagem("getSQLDadosJanelaPesquisa: " + query);
		return query;
	}
	*/
	
	public List<?> pesquisarValores(Integer cd_programa, Integer vl_filtro) throws Exception {

		this.setDao(new GenericDAO());
		Programa programa 	= (Programa) this.getDao().getSession().createCriteria(Programa.class).add(Restrictions.eq("cd_programa", cd_programa)).uniqueResult();
		
		if(programa!=null){
			
			Tabela tabela 		= (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("cd_tabela", programa.getCd_tabela())).uniqueResult();
			if(tabela!=null){
				
				Object obj = null;
				String classe = tabela.getDs_caminhoclasse();
		
				if ( (tabela.getDs_caminhoclasse() != null) && (tabela.getDs_caminhoclasse() != "") ) {
					try{
						obj = Class.forName(classe).newInstance();
					}catch(Exception e){
						//LogPrimum.mensagem(classe);
						e.printStackTrace();
					}
				}
				
				List<?> lista;
				
				if(vl_filtro!=null){
					Criterion campoFiltro = null;
					if(programa.getCd_campo_filtro() != null ){
						Campo campo = (Campo) this.getDao().getSession().createCriteria(Campo.class).add(Restrictions.eq("cd_campo", programa.getCd_campo_filtro())).uniqueResult();
						campoFiltro = Restrictions.eq(campo.getNm_campo(), vl_filtro);
					}
					lista = (campoFiltro!=null)?this.getDao().findAllFiltro(obj, campoFiltro):this.getDao().findAll(obj);
				}else{
					lista = this.getDao().findAll(obj);
				}
				
				
				//SE NAO FOR USUARIO BUSCA OBJETO COMPLETO - SENÃO DEMORA MUTIO PQ CARREGA AS PERMISSOES
				if(obj instanceof Usuario || obj instanceof Pessoa || obj instanceof Formulario)  {
				}else{
					if (lista != null) {
						for (Object o : lista) {
							this.buscarObjetoCompleto(o);
						}
					}
				}
				return lista;
			}
			
			return null;
	//		if (lista != null) {
	//			for (Object o : lista) {
	//				this.buscarObjetoCompleto(o);
	//			}
	//		}
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param cd_programa
	 * @param listaTabelasProcurar é uma lista de objetos que devem ser procurados.
	 * @param valorBuscar
	 * @return
	 * @throws Exception
	 * Procura qual a tabela referência para o programa e a lista de tabelas com registros complementares do programa. Ex.:
	 * A tabela TABELA contém registros complementares na tabela CAMPO.
	 */
	public List<Object> buscarConjuntoRegistrosProgramas(Integer cd_programa, List<Object> listaTabelasProcurar, Object valorBuscar) throws Exception {

		this.setDao(new GenericDAO());
		Programa programa 	= (Programa) this.getDao().getSession().createCriteria(Programa.class).add(Restrictions.eq("cd_programa", cd_programa)).uniqueResult();
		Tabela tabela 		= (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("cd_tabela", programa.getCd_tabela())).uniqueResult();
		Object valor = Reflection.getValorCampo(valorBuscar, tabela.getDs_chaveprimaria());

		List<Object> listaConjuntosRegistros = new ArrayList<>();

		for (Object nometabela : listaTabelasProcurar) {

			//Tabela tabela 		= (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("nm_tabela", nometabela)).uniqueResult();
			List<?> listaRegistros = this.getDao().getSession().createCriteria(nometabela.getClass()).add(Restrictions.eq(tabela.getDs_chaveprimaria(), valor)).list();
			/*
			Object obj = null;

			try{
				String classe;
				classe = tabela.getNm_tabela().substring(0, 1);
				classe = classe.toUpperCase();
				classe += tabela.getNm_tabela().substring(1);
				classe = "br.com.primum.modelo."+ tabela.getNm_tabela().toLowerCase() + "." + classe;
				obj = Class.forName(classe).newInstance();
			}catch(Exception e){
				e.printStackTrace();
			}

			List<?> lista = this.getDao().findAll(obj);
			*/
			listaConjuntosRegistros.addAll(listaRegistros);
		}
		
		/*
		 * boolean resultado = true;
		this.setDao(new GenericDAO());
		try{

			if(Reflection.getExisteChavePrimaria(obj)){
				
				this.getDao().salvar(obj);

				for (Object item : lista) {

					Reflection.setValorChaveEstrangeira(obj, item);
					this.getDao().salvar(item);
				}

			}else{
				this.atualizar(obj);

				for (Object item : lista) {

					this.atualizar(item);
				}
			}

		}catch(Exception e){
			resultado = false;
			e.printStackTrace();
		}
		
		return resultado;
		 * 
		 */
		
		
		//this.getDao().fecharConexao();
		return listaConjuntosRegistros;
	}

	public Object buscarObjetoCompleto(Object objetoBusca) {

		Integer codigoBusca = 0;
		try {

			if (Reflection.getValorChavePrimaria(objetoBusca) != null) {
				codigoBusca = (Integer) Reflection.getValorChavePrimaria(objetoBusca);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (this.getDao() == null) {
			this.setDao(new GenericDAO());
			//this.getDao().getSession().flush();
			//this.getDao().getSession().clear();
		}
		/*
		if (codigoBusca != 0) {
			try {
				objetoBusca = this.getDao().findById(objetoBusca, codigoBusca);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		Field[] fields = objetoBusca.getClass().getDeclaredFields();
		for (Field field : fields) {

			if ( field.getType().getName().equals("java.util.List") ) {

				if (field.isAnnotationPresent(ListaBusca.class)) {

					String caminho = field.getAnnotation(ListaBusca.class).caminhoClasseJava();

					try {

						String nomeChaveOrdenar = "";
						
						try {
							nomeChaveOrdenar = Reflection.getNomeChavePrimaria(Class.forName(caminho).newInstance());
						} catch (Exception e) {

							//LogPrimum.mensagem("nomeChaveOrdenar = Reflection.getNomeChavePrimaria(Class.forName(caminho).newInstance());");
							e.printStackTrace();
						}
						//List<?> lista	= new ArrayList<>(); 
						//List<?> lista	= this.getDao().getSession().createCriteria(Class.forName(caminho)).add(Restrictions.eq(field.getAnnotation(ListaBusca.class).campoBuscar(), codigoBusca)).list();
						List<?> lista	= null;
						
						if ( (nomeChaveOrdenar != null) && (nomeChaveOrdenar != "") ) {
							lista	= this.getDao().getSession().createCriteria(Class.forName(caminho))
									.add(Restrictions.eq(field.getAnnotation(ListaBusca.class).campoBuscar(), codigoBusca))
									.addOrder(Order.asc(nomeChaveOrdenar))
									.list();
						} else {
							lista	= this.getDao().getSession().createCriteria(Class.forName(caminho)).add(Restrictions.eq(field.getAnnotation(ListaBusca.class).campoBuscar(), codigoBusca)).list();
						}
						
						
						//if( campoBuscar.equalsIgnoreCase("cd_tabelaorigem")){
							//lista	= this.getDao().getSession().createCriteria(Class.forName(caminho)).add(Restrictions.eq(field.getAnnotation(ListaBusca.class).campoBuscar(), objetoTabela.getCd_tabela() )).list();
						//}else{
							//lista	= this.getDao().getSession().createCriteria(Class.forName(caminho)).add(Restrictions.eq(field.getAnnotation(ListaBusca.class).campoBuscar(), codigoBusca)).list();
						//}
						
						if ( (Reflection.verificarExisteLista(Class.forName(caminho))) || (Reflection.verificarExisteObjetoBusca(Class.forName(caminho))) ) {
							for (Object objetoFilho : lista) {
								buscarObjetoCompleto(objetoFilho);
							}	
						}

						Reflection.setListaFilhos(objetoBusca, field.getName(), lista);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			} else if (field.isAnnotationPresent(ObjetoBusca.class)) {
				
				try {
					
					String caminho = field.getAnnotation(ObjetoBusca.class).caminhoClasseJava();
					Object valorBuscar = Reflection.getValorCampo(objetoBusca, field.getAnnotation(ObjetoBusca.class).campoComparar());
					
					//LogPrimum.mensagem("caminho: " + caminho);
					//LogPrimum.mensagem("field.getAnnotation(ObjetoBusca.class).campoBuscar(): " + field.getAnnotation(ObjetoBusca.class).campoBuscar());
					if (valorBuscar != null) {
						//LogPrimum.mensagem("valorBuscar.toString(): " + valorBuscar.toString());
					}
					Object o = this.getDao().getSession().createCriteria(Class.forName(caminho)).add(Restrictions.eq(field.getAnnotation(ObjetoBusca.class).campoBuscar(), valorBuscar)).uniqueResult();
					
					Reflection.setValorObject(objetoBusca, field.getName(), o, Class.forName(caminho));
					
					if (o != null) {
						if ( (Reflection.verificarExisteLista(Class.forName(caminho))) || (Reflection.verificarExisteObjetoBusca(Class.forName(caminho))) ) {
	
							buscarObjetoCompleto(o);
						}
					}
				} catch (HibernateException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return objetoBusca;
	}
	
	public void buscarObjetoCompletoChaveEstrangeira(String tabela, String campo, String valor, String classeJava) {

		try {
			DicionarioDados dicionarioDados = new DicionarioService().carregarDicionario();

			for (Tabela tabelaChecar : dicionarioDados.getListaTabelas()) {
				if (tabelaChecar.getNm_tabela().toLowerCase().equals(tabela.toLowerCase())) {
					for (Campo campoItem : tabelaChecar.getListaCampos()) {
						if (campoItem.getNm_campo().toLowerCase().equals(campo.toLowerCase())) {
							
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<?> efetuarBuscaRegistro(Object objeto) throws Exception {

		this.setDao(new GenericDAO());
		Criteria montarBusca = this.getDao().getSession().createCriteria(objeto.getClass());
		Field[] fields = objeto.getClass().getDeclaredFields();
		for (Field field : fields) {

			if ( (! field.getType().getName().equals("java.util.List")) && ( ! field.isAnnotationPresent(Transient.class)) ) {
				Object valorChecar = Reflection.getValorCampo(objeto, field.getName());
				if ( valorChecar != null ) {

					if (field.getType().getName().equals("java.lang.String")) {
						valorChecar = "%" + valorChecar + "%";
						montarBusca.add(Restrictions.like(field.getName(), valorChecar));
					} else if ( field.getType().getName().equals("java.lang.Float") ) {
						if (!Reflection.getValorCampo(objeto, field.getName()).equals(0)) {
							montarBusca.add(Restrictions.like(field.getName(), valorChecar));
						}
					} else {
						montarBusca.add(Restrictions.eq(field.getName(), valorChecar));
					}
				}
			}
		}

		//LogPrimum.mensagem(montarBusca.getAlias());

		List<?> lista = montarBusca.list();

		return lista;
	}

	public List<?> efetuarBuscaRegistroCompleto(Object objetoBuscar, Object objetoFiltro) throws Exception {

		this.setDao(new GenericDAO());
		Criteria montarBusca = this.getDao().getSession().createCriteria(objetoFiltro.getClass());
		Field[] fields = objetoFiltro.getClass().getDeclaredFields();
		for (Field field : fields) {

			if ( (! field.getType().getName().equals("java.util.List")) && ( ! field.isAnnotationPresent(Transient.class)) ) {
				Object valorChecar = Reflection.getValorCampo(objetoFiltro, field.getName());
				if ( valorChecar != null ) {

					if (field.getType().getName().equals("java.lang.String")) {
						valorChecar = "%" + valorChecar + "%";
						montarBusca.add(Restrictions.like(field.getName(), valorChecar));
					} else {
						montarBusca.add(Restrictions.eq(field.getName(), valorChecar));
					}
				}
			}
		}

		List<?> lista = montarBusca.list();

		Integer[] valores = new Integer[lista.size()];

		int i = 0;
		for (Object objetoFilho : lista) {

			try {
				valores[i] = (Integer) Reflection.getValorChavePrimaria(objetoFilho);
			} catch (Exception e) {

				e.printStackTrace();
			}
			i++;
		}

		List<?> listaBuscar = this.getDao().getSession().createCriteria(objetoBuscar.getClass()).add(Restrictions.in(Reflection.getNomeChavePrimaria(objetoFiltro), valores)).list();

		return listaBuscar;
	}

	public List<?> buscarLabelFunctionPadraoValorExterno(Integer cd_tabela) throws Exception {
		
		this.setDao(new GenericDAO());
		Tabela tabela = (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("cd_tabela", cd_tabela)).uniqueResult();
		List<?> lista = null;
		try {
			lista = this.getDao().getSession().createCriteria(Class.forName(tabela.getDs_caminhoclasse())).list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lista;
	}

}