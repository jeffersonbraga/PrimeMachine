package br.com.primum.modelo.grupo_formulario;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ChaveEstrangeira;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.campo_formulario.Campo_formulario;

@XmlRootElement(name = "Grupo_formulario")
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="grupo_formulario")
public class Grupo_formulario implements Serializable {

	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//9161157715056521808L;

	@Id
	@Column
	private Integer cd_grupo_formulario;
	
	@ChaveEstrangeira(nomeObjetoOrigem="cd_formulario")
	@Column
	private Integer cd_formulario;

	@Column
	private Integer cd_sub_formulario;

	@ChaveEstrangeira(nomeObjetoOrigem="cd_grupo_formulario")
	@Column
	private Integer cd_grupo_pai;

	@Column
	private String nm_grupo_formulario;

	@Column
	private Integer tp_grupo_formulario;

	
	@Transient	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.campo_formulario.Campo_formulario", persistir=true, campoBuscar="cd_grupo_formulario")
	private List<Campo_formulario> listaCampo_formulario;

	@Transient	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.grupo_formulario.Grupo_formulario", persistir=true, campoBuscar="cd_grupo_pai")
	private List<Grupo_formulario> listaGrupo_formulario;

	
	public Integer getTp_grupo_formulario() {
		return tp_grupo_formulario;
	}

	public void setTp_grupo_formulario(Integer tp_grupo_formulario) {
		this.tp_grupo_formulario = tp_grupo_formulario;
	}

	public Integer getCd_grupo_formulario() {
		return cd_grupo_formulario;
	}

	public void setCd_grupo_formulario(Integer cd_grupo_formulario) {
		this.cd_grupo_formulario = cd_grupo_formulario;
	}

	public Integer getCd_formulario() {
		return cd_formulario;
	}

	public void setCd_formulario(Integer cd_formulario) {
		this.cd_formulario = cd_formulario;
	}

	public Integer getCd_grupo_pai() {
		return cd_grupo_pai;
	}

	public void setCd_grupo_pai(Integer cd_grupo_pai) {
		this.cd_grupo_pai = cd_grupo_pai;
	}

	public String getNm_grupo_formulario() {
		return nm_grupo_formulario;
	}

	public void setNm_grupo_formulario(String nm_grupo_formulario) {
		this.nm_grupo_formulario = nm_grupo_formulario;
	}

	public List<Campo_formulario> getListaCampo_formulario() {
		return listaCampo_formulario;
	}

	public void setListaCampo_formulario(
			List<Campo_formulario> listaCampo_formulario) {
		this.listaCampo_formulario = listaCampo_formulario;
	}

	public List<Grupo_formulario> getListaGrupo_formulario() {
		return listaGrupo_formulario;
	}

	public void setListaGrupo_formulario(
			List<Grupo_formulario> listaGrupo_formulario) {
		this.listaGrupo_formulario = listaGrupo_formulario;
	}

	public Integer getCd_sub_formulario() {
		return cd_sub_formulario;
	}

	public void setCd_sub_formulario(Integer cd_sub_formulario) {
		this.cd_sub_formulario = cd_sub_formulario;
	}
}