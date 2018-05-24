package br.com.primum.relatorio.modelo.formulario;

import java.util.Date;
import java.util.List;

import br.com.primum.modelo.formulario.Formulario;

public class resultPesquisaFormulario {
	
	private String nm_campo_formulario;
	
	private String nm_grupo_formulario;
	
	private Integer tp_resposta_exec_formulario;
	
	private String ds_resposta_exec_formulario;
	
	private Integer cd_resposta_campo_formulario;
	
	private Date dt_execucao_formulario;
	
	private String nm_usuario;

	private Formulario formulario;

	private List<resultPesquisaFormulario> listaGrupos;
	
	private List<resultPesquisaFormulario> listaFilhos;
	
	
	public Integer getCd_resposta_campo_formulario() {
		return cd_resposta_campo_formulario;
	}

	public void setCd_resposta_campo_formulario(Integer cd_resposta_campo_formulario) {
		this.cd_resposta_campo_formulario = cd_resposta_campo_formulario;
	}

	public String getNm_campo_formulario() {
		return nm_campo_formulario;
	}

	public void setNm_campo_formulario(String nm_campo_formulario) {
		this.nm_campo_formulario = nm_campo_formulario;
	}

	public String getNm_grupo_formulario() {
		return nm_grupo_formulario;
	}

	public void setNm_grupo_formulario(String nm_grupo_formulario) {
		this.nm_grupo_formulario = nm_grupo_formulario;
	}

	public Integer getTp_resposta_exec_formulario() {
		return tp_resposta_exec_formulario;
	}

	public void setTp_resposta_exec_formulario(Integer tp_resposta_exec_formulario) {
		this.tp_resposta_exec_formulario = tp_resposta_exec_formulario;
	}

	public String getDs_resposta_exec_formulario() {
		return ds_resposta_exec_formulario;
	}

	public void setDs_resposta_exec_formulario(String ds_resposta_exec_formulario) {
		this.ds_resposta_exec_formulario = ds_resposta_exec_formulario;
	}

	public Date getDt_execucao_formulario() {
		return dt_execucao_formulario;
	}

	public void setDt_execucao_formulario(Date dt_execucao_formulario) {
		this.dt_execucao_formulario = dt_execucao_formulario;
	}

	public String getNm_usuario() {
		return nm_usuario;
	}

	public void setNm_usuario(String nm_usuario) {
		this.nm_usuario = nm_usuario;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	public List<resultPesquisaFormulario> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<resultPesquisaFormulario> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public List<resultPesquisaFormulario> getListaFilhos() {
		return listaFilhos;
	}

	public void setListaFilhos(List<resultPesquisaFormulario> listaFilhos) {
		this.listaFilhos = listaFilhos;
	}
	

}
