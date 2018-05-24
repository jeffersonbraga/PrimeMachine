package br.com.primum.modelo.descricao_funcional;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.exame_funcao.Exame_funcao;
import br.com.primum.modelo.treinamento_funcao.Treinamento_funcao;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="descricao_funcional")
public class Descricao_funcional {
	
	@Id
	@Column
	private Integer cd_descricao_funcional;

	@Column
	private String nm_descricao_funcional;
	
	@Column
	private String ds_descricao_funcional;
	
	@Column
	private Integer cd_profissao;
	
	@Column
	private Integer cd_profissao_superior;
	
	@Column
	private Integer cd_profissao_subordinado;
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.treinamento_funcao.Treinamento_funcao", persistir=true, campoBuscar="cd_descricao_funcional")
	@Transient
	private List<Treinamento_funcao> listaTreinamentos;
	
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.exame_funcao.Exame_funcao", persistir=true, campoBuscar="cd_descricao_funcional")
	@Transient
	private List<Exame_funcao> listaExamesMedicos;
	
	
	public List<Exame_funcao> getListaExamesMedicos() {
		return listaExamesMedicos;
	}

	public void setListaExamesMedicos(List<Exame_funcao> listaExamesMedicos) {
		this.listaExamesMedicos = listaExamesMedicos;
	}

	public List<Treinamento_funcao> getListaTreinamentos() {
		return listaTreinamentos;
	}

	public void setListaTreinamentos(List<Treinamento_funcao> listaTreinamentos) {
		this.listaTreinamentos = listaTreinamentos;
	}

	public Integer getCd_descricao_funcional() {
		return cd_descricao_funcional;
	}

	public void setCd_descricao_funcional(Integer cd_descricao_funcional) {
		this.cd_descricao_funcional = cd_descricao_funcional;
	}

	public String getNm_descricao_funcional() {
		return nm_descricao_funcional;
	}

	public void setNm_descricao_funcional(String nm_descricao_funcional) {
		this.nm_descricao_funcional = nm_descricao_funcional;
	}

	public String getDs_descricao_funcional() {
		return ds_descricao_funcional;
	}

	public void setDs_descricao_funcional(String ds_descricao_funcional) {
		this.ds_descricao_funcional = ds_descricao_funcional;
	}

	public Integer getCd_profissao() {
		return cd_profissao;
	}

	public void setCd_profissao(Integer cd_profissao) {
		this.cd_profissao = cd_profissao;
	}

	public Integer getCd_profissao_superior() {
		return cd_profissao_superior;
	}

	public void setCd_profissao_superior(Integer cd_profissao_superior) {
		this.cd_profissao_superior = cd_profissao_superior;
	}

	public Integer getCd_profissao_subordinado() {
		return cd_profissao_subordinado;
	}

	public void setCd_profissao_subordinado(Integer cd_profissao_subordinado) {
		this.cd_profissao_subordinado = cd_profissao_subordinado;
	}
}
