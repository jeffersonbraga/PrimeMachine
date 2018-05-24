package br.com.primum.bo.dicionario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.config_sistema.Config_sistema;
import br.com.primum.modelo.dicionario.DicionarioBean;
import br.com.primum.modelo.dicionario.DicionarioDados;
import br.com.primum.modelo.tabela.Tabela;


public class DicionarioBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	public void montarDicionarioDados(DicionarioDados dicionarioDados) {

		if (this.getDao() == null) {
			this.setDao(new GenericDAO());
		}

		List<?> listaTabelas = null;

		try {

			//listaTabelas = this.getDao().findAll(new Tabela());
			listaTabelas = this.getDao().getSession().createCriteria(Tabela.class)
					.addOrder(Order.asc("cd_tabela"))
					.list();

			for (Tabela tabela : (List<Tabela>) listaTabelas) {

				PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
				pesquisa.buscarObjetoCompleto(tabela);
				
				Collections.sort(tabela.getListaCampos(), new OrdenadorDicionario());  
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (listaTabelas != null) {
			//LogPrimum.mensagem(listaTabelas.size());
			dicionarioDados.setListaTabelas((List<Tabela>) listaTabelas);
		}
	}

	@SuppressWarnings("unchecked")
	public DicionarioDados atualizarDicionario() throws Exception {
		try {
			this.setDao(new GenericDAO());
			this.montarDicionarioDados(DicionarioDados.getInstance());
			List<?> lista = this.getDao().findBySql(this.getSQLDicionario(), new DicionarioBean());
			List<DicionarioBean> listaMenu = new ArrayList<DicionarioBean>();

			for(DicionarioBean item : (List<DicionarioBean>)lista){

				//Caso não tenha referência é um nó do menu
				if(item.getCd_menu_item() == null){

					if(item.getChildren() == null){

						item.setChildren(new ArrayList<DicionarioBean>());					
					}

					item.getChildren().addAll(this.encontrarFilhos(item, (List<DicionarioBean>)lista));
					listaMenu.add(item);							
				}
			}

			DicionarioDados.getInstance().setListaDicionarioBean(listaMenu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DicionarioDados.getInstance();
	}
	
	public DicionarioDados atualizaConfigSistema(Config_sistema conf){
		DicionarioDados.getInstance().setConfiguracaoSistema(conf);
		return DicionarioDados.getInstance();
	}
	
	@SuppressWarnings("unchecked")
	public DicionarioDados carregarDicionario() throws Exception {
		
		if (!DicionarioDados.isInstance()) {

			try {
				this.setDao(new GenericDAO());
				this.montarDicionarioDados(DicionarioDados.getInstance());
				List<?> lista = this.getDao().findBySql(this.getSQLDicionario(), new DicionarioBean());
				List<DicionarioBean> listaMenu = new ArrayList<DicionarioBean>();
	
				for(DicionarioBean item : (List<DicionarioBean>)lista){
	
					//Caso não tenha referência é um nó do menu
					if(item.getCd_menu_item() == null){
	
						if(item.getChildren() == null){
	
							item.setChildren(new ArrayList<DicionarioBean>());					
						}
	
						item.getChildren().addAll(this.encontrarFilhos(item, (List<DicionarioBean>)lista));
						listaMenu.add(item);							
					}
				}

				DicionarioDados.getInstance().setListaDicionarioBean(listaMenu);
				
				List<Config_sistema> listaConfiguracao = (List<Config_sistema>) this.getDao().getSession().createCriteria(Config_sistema.class).list();
				if( listaConfiguracao != null && listaConfiguracao.size() > 0){
					PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
					pesquisa.buscarObjetoCompleto(listaConfiguracao.get(0));
					DicionarioDados.getInstance().setConfiguracaoSistema(listaConfiguracao.get(0));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return DicionarioDados.getInstance();
	}

	/**
	 * Recebe como parâmetro um nó pai do menu e uma lista, procura na lista os filhos do menu
	 * e monta uma nova lista que será adicionada na raiz.
	 */
	private List<DicionarioBean> encontrarFilhos(DicionarioBean itemPai, List<DicionarioBean> lista) {

		List<DicionarioBean> listaFilhos = new ArrayList<DicionarioBean>();
		for(DicionarioBean itemFilho : (List<DicionarioBean>)lista){

			if(itemFilho.getCd_menu_item() != null){

				if(itemFilho.getCd_menu_item().equals(itemPai.getCd_menu())){

					if(itemFilho.getChildren() == null){
						itemFilho.setChildren(new ArrayList<DicionarioBean>());					
					}

					itemFilho.getChildren().addAll(this.encontrarFilhos(itemFilho, lista));
					listaFilhos.add(itemFilho);
				}
			}
		}

		return listaFilhos;
	}

	private String getSQLDicionario() {
		String query = 	"select " +
							"programa.cd_programa as \"cd_programa\", " +
							"programa.ds_programa as \"ds_programa\", " +
							"programa.ds_caminhoprograma as \"ds_caminhoprograma\", " +
							"programa.cd_tabela as \"cd_tabela\", " +
							"programa.tp_programa as \"tp_programa\", " +
							"programa.ds_ajuda_programa as \"ds_ajuda_programa\", " +
							"modulo.cd_modulo as \"cd_modulo\", " +
							"modulo.ds_modulo as \"ds_modulo\", " +
							"menu.cd_menu as \"cd_menu\", " +
							"menu.cd_menu_item as \"cd_menu_item\", " +
							"menu.ds_menu as \"ds_menu\", " +
							"menu.nr_ordem as \"nr_ordem\", " +
							"menu.st_visivel as \"st_visivel\", " +
							"menu.vl_valor_abrir as \"vl_valor_abrir\" " +
						"from " +
							"menu " +
						"left join programa on " +
							"menu.cd_programa = programa.cd_programa " +
						"inner join modulo on " +
							"modulo.cd_modulo = menu.cd_modulo " +
						"order by menu.cd_modulo asc, menu.cd_menu asc, menu.nr_ordem asc";

		//LogPrimum.mensagem("Log: " + query);
		return query;
	}
	/*
	private String getSQLDicionarioCodigosFixos(Integer cd_campo) {
		String query = 	"select " +
							"codigosfixos.cd_campo as cd_campo, " +
							"codigosfixos.vl_valor as vl_valor, " +
							"codigosfixos.ds_descricao as ds_descricao " +
						"from " +
							"codigosfixos " +
						"inner join campo on " +
							"campo.cd_campo = codigosfixos.cd_campo " +
						"inner join tabela on " +
							"tabela.cd_tabela = campo.cd_tabela " +
						"left join tabela tabelaorigem on " +
							"tabelaorigem.cd_tabela = campo.cd_tabelaorigem " +
						"where " +
							"codigosfixos.cd_campo = " + cd_campo;
		
		//LogPrimum.mensagem("getSQLDicionarioCodigosFixos: " + query);
		
		return query;
	}
	
	private String getSQLCamposTabela(Integer cd_tabela) {
		String query = 	"select " +
							"campo.cd_campo as cd_campo, " +
							"campo.nm_campo as nm_campo, " +
							"campo.ds_label as ds_label, " +
							"campo.cd_tipocampo as cd_tipocampo, " +
							"campo.cd_tabela as cd_tabela, " +
							"campo.cd_tabelaorigem as cd_tabelaorigem " +
						"from " +
							"campo " +
						"where " +
							"campo.cd_tabela = " + cd_tabela;
		return query;
	}
	*/
}