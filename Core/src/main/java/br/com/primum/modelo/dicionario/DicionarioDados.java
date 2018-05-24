package br.com.primum.modelo.dicionario;

import java.util.ArrayList;
import java.util.List;

import br.com.primum.hibernate.HibernateSessionFactory;
import br.com.primum.modelo.campo.Campo;
import br.com.primum.modelo.campospesquisa.CamposPesquisa;
import br.com.primum.modelo.codigosfixos.CodigosFixos;
import br.com.primum.modelo.config_sistema.Config_sistema;
import br.com.primum.modelo.tabela.Tabela;

public class DicionarioDados {

	private static DicionarioDados instance;

	private List<DicionarioBean> listaDicionarioBean;

	private List<Tabela> listaTabelas;

	private List<Campo> listaCampos;

	private List<CamposPesquisa> listaCamposPesquisa;

	private List<CodigosFixos> listaCodigosFixos;
	
	private Config_sistema configuracaoSistema;

    public static List<HibernateSessionFactory> listaHibernateSessionFactory = new ArrayList<>();

	public static boolean isInstance() {

		boolean valor = false;
		if(instance != null) {
			valor = true;
		}

		return valor;
	}

	public static DicionarioDados getInstance() {

		if(instance == null) {
			instance = new DicionarioDados();
		}
		return instance;
	}

	public List<Campo> getListaCampos() {
		return listaCampos;
	}

	public void setListaCampos(List<Campo> listaCampos) {
		this.listaCampos = listaCampos;
	}

	public List<CamposPesquisa> getListaCamposPesquisa() {
		return listaCamposPesquisa;
	}

	public void setListaCamposPesquisa(List<CamposPesquisa> listaCamposPesquisa) {
		this.listaCamposPesquisa = listaCamposPesquisa;
	}

	public List<CodigosFixos> getListaCodigosFixos() {
		return listaCodigosFixos;
	}

	public void setListaCodigosFixos(List<CodigosFixos> listaCodigosFixos) {
		this.listaCodigosFixos = listaCodigosFixos;
	}

	public List<DicionarioBean> getChildren() {
		return listaDicionarioBean;
	}

	public List<DicionarioBean> getListaDicionarioBean() {
		return listaDicionarioBean;
	}

	public void setListaDicionarioBean(List<DicionarioBean> listaDicionarioBean) {
		this.listaDicionarioBean = listaDicionarioBean;
	}

	public List<Tabela> getListaTabelas() {
		return listaTabelas;
	}

	public void setListaTabelas(List<Tabela> listaTabelas) {
		this.listaTabelas = listaTabelas;
	}

	public Config_sistema getConfiguracaoSistema() {
		return configuracaoSistema;
	}

	public void setConfiguracaoSistema(Config_sistema configuracaoSistema) {
		this.configuracaoSistema = configuracaoSistema;
	}

}