package br.com.primum.modelo.unidade;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.setor.Setor;
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="unidade")
public class Unidade {

	@Id
	private Integer cd_unidade;
	
	@Column
	private String ds_unidade;
	
	@Column
	private Integer st_unidade;
	
	@Column
	private Integer cd_empresa;

	@Column
	private Integer cd_responsavel_unidade;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.setor.Setor", campoBuscar="cd_unidade", persistir = false)
	private List<Setor> listaSetores;
	
	
	
	public List<Setor> getListaSetores() {
		return listaSetores;
	}

	public void setListaSetores(List<Setor> listaSetores) {
		this.listaSetores = listaSetores;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}

	public String getDs_unidade() {
		return ds_unidade;
	}

	public void setDs_unidade(String ds_unidade) {
		this.ds_unidade = ds_unidade;
	}

	public Integer getSt_unidade() {
		return st_unidade;
	}

	public void setSt_unidade(Integer st_unidade) {
		this.st_unidade = st_unidade;
	}

	public Integer getCd_empresa() {
		return cd_empresa;
	}

	public void setCd_empresa(Integer cd_empresa) {
		this.cd_empresa = cd_empresa;
	}

	public Integer getCd_responsavel_unidade() {
		return cd_responsavel_unidade;
	}

	public void setCd_responsavel_unidade(Integer cd_responsavel_unidade) {
		this.cd_responsavel_unidade = cd_responsavel_unidade;
	} 
	
	
}