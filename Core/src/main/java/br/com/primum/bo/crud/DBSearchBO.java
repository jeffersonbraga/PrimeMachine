package br.com.primum.bo.crud;

import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.ferramentas.ObjetoVirtual;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.campo.Campo;
import br.com.primum.modelo.campo_formulario.Campo_formulario;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.grupo_formulario.Grupo_formulario;
import br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario;
import br.com.primum.modelo.tabela.Tabela;
import br.com.primum.reflection.Reflection;
public class DBSearchBO {

	private GenericDAO dao;

	public Object efetuarBuscaValor(String tabela, String campo, String valor, String classeJava) throws Exception, HibernateException {

		String campoAux = null;
		String valorAux = null;

		if (campo.indexOf(";") > -1) {			
			campoAux 	= campo.split(";")[1]; 
			campo 		= campo.split(";")[0];
			valorAux	= "%" + valor + "%";
		}

		//Object obj = Class.forName(classeJava).newInstance();
		//Class classe = obj.getClass();
		boolean erro = false;
		try {
			Integer.parseInt(valor);			
		} catch (Exception e) {
			erro = true;
			//e.printStackTrace();
		}

		Object o = null;
		List<?> lista = null;
		try {
			this.setDao(new GenericDAO());

			if (!erro) {

				try {
					o = this.getDao().getSession().createCriteria(Class.forName(classeJava)).add(Restrictions.eq(campo, Integer.parseInt(valor))).uniqueResult();
				} catch(HibernateException erroConexao) {
					erroConexao.printStackTrace();
				} catch(ClassCastException erroConexao) {
					o = this.getDao().getSession().createCriteria(Class.forName(classeJava)).add(Restrictions.eq(campo, valor)).uniqueResult();
				} catch(Exception erroConexao) {
					erroConexao.printStackTrace();
				}
			}

			if ( (o == null) && (campoAux != null) ) {

				if (valorAux == null) {
					valorAux = valor;
				}
				lista	= this.getDao().getSession().createCriteria(Class.forName(classeJava)).add(Restrictions.like(campoAux, valorAux).ignoreCase()).list();

				if (lista.size() > 0) {
					o = lista.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	public List<?> buscarRegistrosFiltro(String tabela, String classeJava, String campoFiltro, Object valorFiltro) {

		this.setDao(new GenericDAO());
		List<?> lista = null;
		try {

			/* TODO 
				CRIAR INSTANCIA DE DICIONARIO ÚNICA PARA O SERVER.
			*/
			Tabela tabelaDicionario = (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("nm_tabela", tabela)).add(Restrictions.eq("ds_caminhoclasse", classeJava)).uniqueResult();
			String campoOrdenar = "";
			if (tabelaDicionario != null) {

				CrudGenericBO crud = new CrudGenericBO();
				crud.setDao(this.getDao());
				crud.buscarObjetoCompleto(tabelaDicionario);

				for (Campo campo : ((Tabela) tabelaDicionario).getListaCampos()) {
					if ( (campo.getTp_identificador() != null) && (campo.getTp_identificador().equals(1)) ) {
						campoOrdenar = campo.getNm_campo();
						break;
					}
				}
			}

			this.setDao(new GenericDAO());
			Criteria criteria = this.getDao().getSession().createCriteria(Class.forName(classeJava));

			if ( (campoOrdenar != null && campoOrdenar != "") && criteria != null) {
				criteria.addOrder(Order.asc(campoOrdenar));
			}
			
			if ( ((campoFiltro != null) && (campoFiltro != "")) && ((valorFiltro != null) && (valorFiltro != "")) ) {
				criteria.add(Restrictions.eq(campoFiltro, Integer.parseInt((valorFiltro.toString()))));
			}

			lista = criteria.list();
		} catch (HibernateException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
	
	public List<?> buscarRegistrosListaAuxiliar(String nomePropriedade, Integer valorPropriedade, String nomeLista, String classeJava) {
		
		this.setDao(new GenericDAO());
		
		List<?> lista = null;
		Object obj;
		try {
			obj = this.getDao().getSession().createCriteria(Class.forName(classeJava))
					.add(Restrictions.eq(nomePropriedade, valorPropriedade)).uniqueResult();
		
		
		
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
	
				if(field.getName().equals(nomeLista)){
					if ( field.getType().getName().equals("java.util.List") ) {
		
						if (  field.isAnnotationPresent(ListaBusca.class) ) {
							String caminho = field.getAnnotation(ListaBusca.class).caminhoClasseJava();
							
							Criteria criteria = this.getDao().getSession().createCriteria(Class.forName(caminho))
									.add(Restrictions.eq(nomePropriedade, valorPropriedade));
							lista = criteria.list();
							break;
						}
					}
				}
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}

	public List<?> buscarTodosRegistros(String tabela, String classeJava) {
		this.setDao(new GenericDAO());
		List<?> lista = null;
		try {

			/* TODO 
				CRIAR INSTANCIA DE DICIONARIO ÚNICA PARA O SERVER.
			*/
			Object tabelaDicionario = this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("nm_tabela", tabela.toLowerCase())).add(Restrictions.eq("ds_caminhoclasse", classeJava)).uniqueResult();
			String campoOrdenar = "";
			if (tabelaDicionario != null) {	
				CrudGenericBO crud = new CrudGenericBO();
				crud.setDao(this.getDao());
				crud.buscarObjetoCompleto(tabelaDicionario);

				for (Campo campo : ((Tabela) tabelaDicionario).getListaCampos()) {
					if ( (campo.getTp_identificador() != null) && (campo.getTp_identificador().equals(1)) ) {
						campoOrdenar = campo.getNm_campo();
						break;
					}
				}
			}
			
			
			
			this.setDao(new GenericDAO());
			Criteria criteria = this.getDao().getSession().createCriteria(Class.forName(classeJava));
			if ( (campoOrdenar != null && campoOrdenar != "") && criteria != null) {
				criteria.addOrder(Order.asc(campoOrdenar));
			}
			//lista = this.getDao().getSession().createCriteria(Class.forName(classeJava)).list();
			lista = criteria.list();
		} catch (HibernateException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	public List<?> buscarTodosRegistrosCompleto(String tabela, String classeJava) {
		this.setDao(new GenericDAO());
		List<?> lista = null;
		try {
			

			
			/* TODO 
				CRIAR INSTANCIA DE DICIONARIO ÚNICA PARA O SERVER.
			*/
			Object tabelaDicionario = this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("nm_tabela", tabela)).add(Restrictions.eq("ds_caminhoclasse", classeJava)).uniqueResult();
			String campoOrdenar = "";
			if (tabelaDicionario != null) {	
				CrudGenericBO crud = new CrudGenericBO();
				crud.setDao(this.getDao());
				crud.buscarObjetoCompleto(tabelaDicionario);

				for (Campo campo : ((Tabela) tabelaDicionario).getListaCampos()) {
					if ( (campo.getTp_identificador() != null) && (campo.getTp_identificador().equals(1)) ) {
						campoOrdenar = campo.getNm_campo();
						break;
					}
				}
			}
			
			
			
			
			this.setDao(new GenericDAO());
			Criteria criteria = this.getDao().getSession().createCriteria(Class.forName(classeJava));
			if ( (campoOrdenar != null && campoOrdenar != "") && criteria != null) {
				criteria.addOrder(Order.asc(campoOrdenar));
			}

			lista = criteria.list();
			//lista = this.getDao().getSession().createCriteria(Class.forName(classeJava)).list();
			
			if ( (lista != null) && (lista.size() > 0) ) {
				
				for (Object o : lista) {
					PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
					pesquisa.buscarObjetoCompleto(o);
				}
			}
		} catch (HibernateException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	public Object efetuarBuscaValorCompleto(String tabela, String campo, String valor, String classeJava) throws Exception {

		Object o = this.efetuarBuscaValor(tabela, campo, valor, classeJava);		
		
		if (o != null) {
			PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
			try {
				pesquisa.buscarObjetoCompleto(o);
			} catch (Exception e) {
				pesquisa.getDao().getSession().flush();
				e.printStackTrace();
			}
		}
		//pesquisa.buscarObjetoCompletoChaveEstrangeira(o);
		return o;
	}

	public Object efetuarBuscaValorFiltro(String tabela, String campo, String valor, String classeJava, String campoFiltro, Object valorFiltro) throws Exception {
		Object o = this.efetuarBuscaValor(tabela, campo, valor, classeJava);

		//LogPrimum.mensagem("Campo Filtro: " + campoFiltro);
		//LogPrimum.mensagem("Valor Filtro: " + valorFiltro);

		boolean erro = false;
		try {

			Integer valorAux 		= null;
			if (Reflection.getValorCampo(o, campoFiltro) != null) {
				valorAux 		= Integer.parseInt( Reflection.getValorCampo(o, campoFiltro).toString() );
			}

			Integer valorFiltroAux 	= Integer.parseInt(valorFiltro.toString());

			if ( (valorAux != null) && (valorAux.equals(valorFiltroAux))) {
				//LogPrimum.mensagem("===================Aplicar filtro===================");
				PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
				pesquisa.buscarObjetoCompleto(o);
			} else if ( (valorAux == null) && (valorFiltroAux != null) ) {
				//LogPrimum.mensagem("===================Aplicar filtro===================");
				
				Reflection.setValorObject(o, campoFiltro, valorFiltroAux, Integer.class);
				
				PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();				
				pesquisa.efetuarBuscaRegistro(o);
			} else {
				o = null;
			}

		} catch (Exception e) {
			erro = true;
			//e.printStackTrace();
		}

		//pesquisa.buscarObjetoCompletoChaveEstrangeira(o);
		return o;
	}	

	public Object efetuarBuscaValorAutomatizada(String tabela, String campo, String valor, String classeJava, Object valorFiltro) throws Exception, HibernateException {

		String campoAux = null;
		String valorAux = null;

		if (campo.indexOf(";") > -1) {			
			campoAux 	= campo.split(";")[1]; 
			campo 		= campo.split(";")[0];
			valorAux	= "%" + valor + "%";
		}

		//Object obj = Class.forName(classeJava).newInstance();
		//Class classe = obj.getClass();
		boolean erro = false;
		try {
			Integer.parseInt(valor);			
		} catch (Exception e) {
			erro = true;
			//e.printStackTrace();
		}

		Object o = null;
		List<?> lista = null;
		try {
			this.setDao(new GenericDAO());

			if (!erro) {

				try {
					o = this.getDao().getSession().createCriteria(Class.forName(classeJava)).add(Restrictions.eq(campo, Integer.parseInt(valor))).uniqueResult();
				} catch(HibernateException erroConexao) {
					erroConexao.printStackTrace();
				} catch(ClassCastException erroConexao) {
					o = this.getDao().getSession().createCriteria(Class.forName(classeJava)).add(Restrictions.eq(campo, valor)).add(Restrictions.eq("cd_campo_formulario", valorFiltro)).uniqueResult();
				} catch(Exception erroConexao) {
					erroConexao.printStackTrace();
				}
			}

			if ( (o == null) && (campoAux != null) ) {

				if (valorAux == null) {
					valorAux = valor;
				}
				lista	= this.getDao().getSession().createCriteria(Class.forName(classeJava)).add(Restrictions.like(campoAux, valorAux).ignoreCase()).list();

				if (lista.size() > 0) {
					o = lista.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	public Object efetuarBuscaValorCompletoAutomatizada(String tabela, String campo, String valor, String classeJava) throws Exception {

		Object o = this.efetuarBuscaValor(tabela, campo, valor, classeJava);		
		
		if (o != null) {
			PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
			try {
				pesquisa.buscarObjetoCompleto(o);
			} catch (Exception e) {
				pesquisa.getDao().getSession().flush();
				e.printStackTrace();
			}
		}
		//pesquisa.buscarObjetoCompletoChaveEstrangeira(o);
		return o;
	}

	public Object efetuarBuscaValorFiltroAutomatizada(String tabela, String campo, String valor, String classeJava, String campoFiltro, Object valorFiltro) throws Exception {
		Object o = this.efetuarBuscaValorAutomatizada(tabela, campo, valor, classeJava, valorFiltro);

		//LogPrimum.mensagem("Campo Filtro: " + campoFiltro);
		//LogPrimum.mensagem("Valor Filtro: " + valorFiltro);

		boolean erro = false;
		try {

			Integer valorAux 		= null;
			if (Reflection.getValorCampo(o, campoFiltro) != null) {
				valorAux 		= Integer.parseInt( Reflection.getValorCampo(o, campoFiltro).toString() );
			}

			Integer valorFiltroAux 	= Integer.parseInt(valorFiltro.toString());

			if ( (valorAux != null) && (valorAux.equals(valorFiltroAux))) {
				//LogPrimum.mensagem("===================Aplicar filtro===================");
				PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
				pesquisa.buscarObjetoCompleto(o);
			} else if ( (valorAux == null) && (valorFiltroAux != null) ) {
				//LogPrimum.mensagem("===================Aplicar filtro===================");
				
				Reflection.setValorObject(o, campoFiltro, valorFiltroAux, Integer.class);
				
				PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();				
				pesquisa.efetuarBuscaRegistro(o);
			} else {
				o = null;
			}

		} catch (Exception e) {
			erro = true;
			//e.printStackTrace();
		}

		//pesquisa.buscarObjetoCompletoChaveEstrangeira(o);
		Execucao_formulario exe = null;
		if ( (o != null) && (o instanceof Resposta_exec_formulario)) {

			exe = (Execucao_formulario) this.getDao().getSession().createCriteria(Execucao_formulario.class).add(Restrictions.eq("cd_execucao_formulario", ((Resposta_exec_formulario) o).getCd_execucao_formulario())).uniqueResult();
			if (exe != null) {
				CrudGenericBO crud = new CrudGenericBO();
				crud.buscarObjetoCompleto(exe);
			}
		}

		return exe;
	}

	public List<Resposta_exec_formulario> buscarDadosSpinner(ObjetoVirtual obj) throws Exception {

		this.setDao(new GenericDAO());
		
		String sql = "select distinct(campo_desc.cd_campo_formulario) from resposta_exec_formulario inner join campo_formulario campo_id on campo_id.cd_campo_formulario = resposta_exec_formulario.cd_campo_formulario inner join campo_formulario campo_desc on campo_desc.cd_grupo_formulario = campo_id.cd_grupo_formulario and campo_desc.tp_descricao = 1 where resposta_exec_formulario.cd_campo_formulario = " + obj.getValorFiltro();

		Object valorAux = this.getDao().getSession().createSQLQuery(sql).uniqueResult();

		List<Resposta_exec_formulario> listaDadosDbSearch = this.getDao().getSession().createCriteria(Resposta_exec_formulario.class).add(Restrictions.eq("cd_campo_formulario", valorAux)).list();
		return listaDadosDbSearch;
	}		
}