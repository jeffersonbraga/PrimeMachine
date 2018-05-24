package br.com.primum.modelo.mapa_formulario;

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
import br.com.primum.modelo.ponto_mapa.Ponto_mapa;


@XmlRootElement(name = "Mapa_formulario")
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="mapa_formulario")
public class Mapa_formulario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-2143131347081591675L;

	@Id
	@Column
	private Integer cd_mapa_formulario;

	@Column
	private Integer cd_formulario;

	@Column
	private String ds_caminho;

	
	@Column
	private Integer cd_sub_formulario;
	
	@Column
	private Integer nr_width;
	
	@Column
	private Integer nr_height;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.ponto_mapa.Ponto_mapa", persistir=true, campoBuscar="cd_mapa_formulario")
	private List<Ponto_mapa> listaPontos_mapa;

	
	public Integer getCd_formulario(){
		return cd_formulario;
	}


	public void setCd_formulario(Integer cd_formulario){
		this.cd_formulario = cd_formulario;
	}


	public Integer getCd_mapa_formulario(){
		return cd_mapa_formulario;
	}


	public void setCd_mapa_formulario(Integer cd_mapa_formulario){
		this.cd_mapa_formulario = cd_mapa_formulario;
	}


	public String getDs_caminho(){
		return ds_caminho;
	}


	public void setDs_caminho(String ds_caminho){
		this.ds_caminho = ds_caminho;
	}


	public Integer getCd_sub_formulario(){
		return cd_sub_formulario;
	}


	public void setCd_sub_formulario(Integer cd_subformulario){
		this.cd_sub_formulario = cd_subformulario;
	}

	public Integer getNr_width() {
		return nr_width;
	}

	public void setNr_width(Integer nr_width) {
		this.nr_width = nr_width;
	}

	public Integer getNr_height() {
		return nr_height;
	}


	public void setNr_height(Integer nr_height) {
		this.nr_height = nr_height;
	}


	public List<Ponto_mapa> getListaPontos_mapa() {
		return listaPontos_mapa;
	}


	public void setListaPontos_mapa(List<Ponto_mapa> listaPontos_mapa) {
		this.listaPontos_mapa = listaPontos_mapa;
	}

}