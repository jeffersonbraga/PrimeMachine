package br.com.lsd.bo.programa;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.relatorios.RelatorioBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.grupo_usuario_programa.Grupo_usuario_programa;
import br.com.primum.modelo.lista_modulos.Lista_modulos;
import br.com.primum.modelo.menu.Menu;
import br.com.primum.modelo.permissao_acesso.Permissao_acesso;
import br.com.primum.modelo.programa.Programa;

public class ProgramaBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	public List<?> buscarCamposTabela(Integer cd_tabela) {
		List<?> listaCampos = null;
		try {
			this.setDao(new GenericDAO());
			//listaCampos = this.getDao().getSession().createCriteria(Campo.class).add(Restrictions.eq("cd_tabela", cd_tabela)).list();
			listaCampos = null;
			
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaCampos;
	}
	/*
	public List<?> buscarCamposPesquisaPrograma(Integer cd_programa) {
		List<?> listaCampos = null;
		try {
			
			this.setDao(new GenericDAO());
			listaCampos = this.getDao().findBySql(this.getSQLCamposPesquisa(cd_programa), new Campo());			
		} catch (HibernateException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return listaCampos;
	}
	
	private String getSQLCamposPesquisa(Integer cd_programa) {
		String query = 	"select " +
							"campo.cd_campo as 'cd_campo', " +
							"campo.nm_campo as 'nm_campo', " +
							"campo.ds_label as 'ds_label', " +
							"campo.cd_tipocampo as 'cd_tipocampo', " +
							"campo.cd_tabela as 'cd_tabela', " +
							"campo.cd_tabelaorigem as 'cd_tabelaorigem', " +
							"campo.tp_chaveprimaria as 'tp_chaveprimaria', " +
							"campo.tp_chaveestrangeira as 'tp_chaveestrangeira', " +
							"campo.st_aceitavalornull as 'st_aceitavalornull', " +
							"campo.ds_descricao as 'ds_descricao' " +
						"from campospesquisa " +
						"inner join campo on " +
							"campo.cd_campo = campospesquisa.cd_campo " +
						"where " +
							"cd_programa = " + cd_programa;
		return query;
	}*/
	
	
	public List<Grupo_usuario_programa> buscarProgramaPermissao() {

		
		
		List<Lista_modulos> listaModulos = new ArrayList<>();
		List<Menu> listaMenus = new ArrayList<>();
		List<Grupo_usuario_programa> listaProgramas = new ArrayList<>();
		
		try {
			
			this.setDao(new GenericDAO());
			listaModulos   = (List<Lista_modulos>) this.getDao().getSession().createCriteria(Lista_modulos.class).list();
			for(Lista_modulos lista : listaModulos ){
				
				listaMenus     = (List<Menu>)     	   this.getDao().getSession().createCriteria(Menu.class).add(Restrictions.eq("cd_modulo", lista.getCd_modulo())).list();
				for(Menu m : listaMenus ){
					Programa p = (Programa) this.getDao().getSession().createCriteria(Programa.class).add(Restrictions.eq("cd_programa", m.getCd_programa())).uniqueResult();
					if(p!=null){
	
						boolean existeConfiguracao = false;
						
						for (Grupo_usuario_programa permissao : listaProgramas) {
							if (permissao.getCd_programa().equals(p.getCd_programa())) {
								existeConfiguracao = true;
								break;
							}
						}
						
						if (!existeConfiguracao) {
							
							Grupo_usuario_programa gup = new Grupo_usuario_programa();
							gup.setCd_programa(p.getCd_programa());
							gup.setTp_editar(0);
							gup.setTp_excluir(0);
							gup.setTp_salvar(0);
							gup.setTp_visualizar(0);
							gup.setCd_modulo(m.getCd_modulo());
							gup.setNm_programa(p.getDs_programa());
							listaProgramas.add(gup);
						
						}
					}
				}
			}
			return listaProgramas;
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Grupo_usuario_programa> setaModuloProgramas( List<Grupo_usuario_programa> listaPermissoes) {
		this.setDao(new GenericDAO());
		for(Grupo_usuario_programa gup : listaPermissoes ){
			Integer cd_modulo = Integer.parseInt( RelatorioBO.toText("menu", "cd_programa", "cd_modulo", gup.getCd_programa().toString()) );
			gup.setNm_programa( RelatorioBO.toText("programa", "cd_programa", "ds_programa", gup.getCd_programa().toString()) );
			gup.setCd_modulo(cd_modulo);
		}
		return listaPermissoes;
	}
	
	public Boolean setTpPrograma( Programa p ){
		if(p.getTp_programa() == null ){
			CrudGenericBO crud = new CrudGenericBO();
			try {
				p.setTp_programa(0);
				crud.atualizar( p );
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
		
	}
}
