package br.com.lsd.bo.menu;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.escalonamento_usuario.Escalonamento_usuario;
import br.com.primum.modelo.menu.Menu;
import br.com.primum.modelo.menu_indicadores.Menu_indicadores;
import br.com.primum.modelo.modulo.Modulo;
import br.com.primum.modelo.modulo_indicadores.Modulo_indicadores;
import br.com.primum.modelo.permissao_menu_indicadores.Permissao_menu_indicadores;

@Service
@Transactional
public class MenuBO {

	private GenericDAO dao;
	
	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	public String getMenu(String modulo) throws Exception {
		
		this.setDao(new GenericDAO());
		this.getDao().findBySql(this.getSQLMenu(), new Menu());
		String xmlMenu = "";
		return xmlMenu;
	}
	
	private String getSQLMenu() {
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public List<Modulo> listaModulos(Integer cd_usuario, Integer cd_unidade){
		
		this.setDao(new GenericDAO());
		List<Modulo> listaDeModulosPermissao = (List<Modulo>) this.getDao().getSession().createSQLQuery("select distinct modulo.ds_modulo from modulo INNER JOIN lista_modulos on lista_modulos.cd_modulo = modulo.cd_modulo INNER JOIN menu on menu.cd_modulo = modulo.cd_modulo INNER JOIN programa on programa.cd_programa = menu.cd_programa INNER JOIN permissao_acesso on  permissao_acesso.cd_programa = programa.cd_programa AND permissao_acesso.cd_usuario = "+cd_usuario+" AND permissao_acesso.cd_unidade = "+cd_unidade+" AND( (permissao_acesso.tp_editar = 1) OR (permissao_acesso.tp_excluir = 1) OR (permissao_acesso.tp_salvar = 1) OR (permissao_acesso.tp_visualizar = 1) ) order by modulo.ds_modulo").setResultTransformer(Transformers.aliasToBean(Modulo.class)).list();
		List<Modulo> listaDeModulosGrupo = (List<Modulo>) this.getDao().getSession().createSQLQuery("select distinct modulo.ds_modulo from modulo INNER JOIN lista_modulos on lista_modulos.cd_modulo = modulo.cd_modulo INNER JOIN menu on menu.cd_modulo = modulo.cd_modulo INNER JOIN programa on programa.cd_programa = menu.cd_programa INNER JOIN grupo_usuario_programa on  grupo_usuario_programa.cd_programa = programa.cd_programa INNER JOIN usuario_grupo_usuario on usuario_grupo_usuario.cd_usuario = "+cd_usuario+" AND usuario_grupo_usuario.cd_unidade = "+cd_unidade+" AND( (grupo_usuario_programa.tp_editar = 1) OR (grupo_usuario_programa.tp_excluir = 1) OR (grupo_usuario_programa.tp_salvar = 1) OR (grupo_usuario_programa.tp_visualizar = 1) ) order by modulo.ds_modulo").setResultTransformer(Transformers.aliasToBean(Modulo.class)).list();
		List<Modulo> listaDeModulos = new ArrayList<Modulo>();
		
		for(Modulo moduloPermissao : listaDeModulosPermissao){
			listaDeModulos.add( moduloPermissao );
		}
		
		for(Modulo modulo : (List<Modulo>) listaDeModulosGrupo){
			Boolean existe=false;
			for(Modulo mod : listaDeModulosPermissao){
				if(mod.getDs_modulo().toUpperCase().equals(modulo.getDs_modulo().toUpperCase())){
					existe=true;
					break;
				}
			}	
			
			if(!existe){
				listaDeModulos.add( modulo );
			}
		}
		
		return listaDeModulos;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Modulo_indicadores> listaModulosIndicadores( Integer cd_usuario, Integer cd_unidade ){
		
		this.setDao(new GenericDAO());
		List<Modulo_indicadores> listaDeModulos = (List<Modulo_indicadores>) this.getDao().getSession().
					createCriteria(Modulo_indicadores.class).addOrder(Order.asc("cd_modulo_indicadores")).list();
		
		List<Permissao_menu_indicadores> listaPermissoesUsuario = (List<Permissao_menu_indicadores>) this.getDao().getSession().createCriteria(Permissao_menu_indicadores.class)
				.add(Restrictions.eq("cd_usuario", cd_usuario))
				.add(Restrictions.eq("cd_unidade", cd_unidade)).list();
		
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		if(listaPermissoesUsuario.size() > 0 ){
			for(Modulo_indicadores mod : listaDeModulos){
				pesquisa.buscarObjetoCompleto( mod );
				mod.setListaMenus( verificaListaPermissao( mod.getListaMenus() , listaPermissoesUsuario) );
			}
		}
		return listaDeModulos;

	}
	
	private List<Menu_indicadores> verificaListaPermissao(List<Menu_indicadores> listaSubMenus, List<Permissao_menu_indicadores> listaPermissoes ){
		
		List<Menu_indicadores> listaAux = new ArrayList<Menu_indicadores>();
		for( Menu_indicadores m : listaSubMenus ){
			
			for( Permissao_menu_indicadores p : listaPermissoes ){
				if(p.getCd_menu_indicadores().equals(m.getCd_menu_indicadores()) && p.getTp_visualizar().equals(1) ){
					listaAux.add( m );
					break;
				}
			}
			
			if(m.getListaSubMenus().size() > 0 ){
				m.setListaSubMenus( verificaListaPermissao( m.getListaSubMenus(), listaPermissoes) );
			}
			
		}
		
		return listaAux;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Menu> listaProgramasMenu(String str){
		this.setDao(new GenericDAO());
		List<Menu> lista =( List<Menu> ) this.getDao().getSession().createCriteria(Menu.class)
				.add(Restrictions.isNotNull("cd_programa"))
				.add(Restrictions.ilike("ds_menu", "%" + str + "%"))
				.addOrder(Order.asc("ds_menu"))
				.list();
		
		return lista;
	}
	
}
