package br.com.primum.modelo.sub_formulario;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.grupo_formulario.Grupo_formulario;

@XmlRootElement(name = "Sub_formulario")
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="sub_formulario")
public class Sub_formulario implements Serializable {

	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-5313270721216772880L;

	@Id
	@Column
	private Integer cd_sub_formulario;

	@Column
	private String ds_sub_formulario;

	@Column
	private Integer cd_formulario;

	@Column
	private Integer vl_filtro;

	@Transient	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.grupo_formulario.Grupo_formulario", campoBuscar="cd_sub_formulario", persistir=true)
	private List<Grupo_formulario> listaGrupo_formulario;

	
	@Transient
	private Integer sub_formulario_origem;

	
	
	public Integer getSub_formulario_origem() {
		return sub_formulario_origem;
	}

	public void setSub_formulario_origem(Integer sub_formulario_origem) {
		this.sub_formulario_origem = sub_formulario_origem;
	}

	public Integer getCd_sub_formulario() {
		return cd_sub_formulario;
	}

	public void setCd_sub_formulario(Integer cd_sub_formulario) {
		this.cd_sub_formulario = cd_sub_formulario;
	}

	public Integer getCd_formulario() {
		return cd_formulario;
	}

	public void setCd_formulario(Integer cd_formulario) {
		this.cd_formulario = cd_formulario;
	}

	public Integer getVl_filtro() {
		return vl_filtro;
	}

	public void setVl_filtro(Integer vl_filtro) {
		this.vl_filtro = vl_filtro;
	}

	public List<Grupo_formulario> getListaGrupo_formulario() {
		return listaGrupo_formulario;
	}

	public void setListaGrupo_formulario(
			List<Grupo_formulario> listaGrupo_formulario) {
		this.listaGrupo_formulario = listaGrupo_formulario;
	}

	public String getDs_sub_formulario() {
		return ds_sub_formulario;
	}

	public void setDs_sub_formulario(String ds_sub_formulario) {
		this.ds_sub_formulario = ds_sub_formulario;
	}
}
