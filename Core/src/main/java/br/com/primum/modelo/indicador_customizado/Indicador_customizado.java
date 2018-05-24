package br.com.primum.modelo.indicador_customizado;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.formulario_indicador.Formulario_indicador;
import br.com.primum.modelo.permissao_indicador.Permissao_indicador;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="indicador_customizado")
public class Indicador_customizado{

	@Id
	@Column
	private Integer cd_indicador_customizado;

	@Column
	private String nm_indicador_customizado;

	@Column
	private Integer nr_meta_conformidade;

	@Column
	private Integer nr_aceite_naoconformidade;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.formulario_indicador.Formulario_indicador", campoBuscar="cd_indicador_customizado",persistir=true)
	private List<Formulario_indicador> listaFormularios_indicador;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.permissao_indicador.Permissao_indicador", campoBuscar="cd_indicador_customizado", persistir=true)
	private List<Permissao_indicador> listaPermissoes;
	
	public Integer getCd_indicador_customizado(){
		return cd_indicador_customizado;
	}


	public void setCd_indicador_customizado(Integer cd_indicador_customizado){
		this.cd_indicador_customizado = cd_indicador_customizado;
	}


	public String getNm_indicador_customizado(){
		return nm_indicador_customizado;
	}


	public void setNm_indicador_customizado(String nm_indicador_customizado){
		this.nm_indicador_customizado = nm_indicador_customizado;
	}


	public Integer getNr_meta_conformidade(){
		return nr_meta_conformidade;
	}


	public void setNr_meta_conformidade(Integer nr_meta_conformidade){
		this.nr_meta_conformidade = nr_meta_conformidade;
	}


	public Integer getNr_aceite_naoconformidade(){
		return nr_aceite_naoconformidade;
	}


	public void setNr_aceite_naoconformidade(Integer nr_aceite_naoconformidade){
		this.nr_aceite_naoconformidade = nr_aceite_naoconformidade;
	}


	public List<Permissao_indicador> getListaPermissoes() {
		return listaPermissoes;
	}


	public void setListaPermissoes(List<Permissao_indicador> listaPermissoes) {
		this.listaPermissoes = listaPermissoes;
	}


	public List<Formulario_indicador> getListaFormularios_indicador() {
		return listaFormularios_indicador;
	}


	public void setListaFormularios_indicador(
			List<Formulario_indicador> listaFormularios_indicador) {
		this.listaFormularios_indicador = listaFormularios_indicador;
	}

	
}