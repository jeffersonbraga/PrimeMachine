package br.com.primum.modelo.setor;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ListaBusca;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="setor")
public class Setor implements Serializable {
	
	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-2002787719062913298L;

	@Id
	@Column
	private Integer cd_setor;
	
	@Column
	private Integer cd_departamento;
	
	@Column
	private Integer cd_responsavel_setor;
	
	@Column
	private String nm_setor;
	
	@Column
	private String ds_setor;
	
	@Column
	private Integer cd_unidade;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.setor.Setor", campoBuscar="cd_departamento", persistir=false)
	private List<Setor> listaDepartamentos;

	
	public Integer getCd_setor() {
		return cd_setor;
	}
	public void setCd_setor(Integer cd_setor) {
		this.cd_setor = cd_setor;
	}
	public Integer getCd_departamento() {
		return cd_departamento;
	}
	public void setCd_departamento(Integer cd_departamento) {
		this.cd_departamento = cd_departamento;
	}
	public String getNm_setor() {
		return nm_setor;
	}
	public void setNm_setor(String nm_setor) {
		this.nm_setor = nm_setor;
	}
	public String getDs_setor() {
		return ds_setor;
	}
	public void setDs_setor(String ds_setor) {
		this.ds_setor = ds_setor;
	}
	public Integer getCd_responsavel_setor() {
		return cd_responsavel_setor;
	}
	public void setCd_responsavel_setor(Integer cd_responsavel_setor) {
		this.cd_responsavel_setor = cd_responsavel_setor;
	}
	public List<Setor> getListaDepartamentos() {
		return listaDepartamentos;
	}
	public void setListaDepartamentos(List<Setor> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}
	public Integer getCd_unidade() {
		return cd_unidade;
	}
	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}
}
