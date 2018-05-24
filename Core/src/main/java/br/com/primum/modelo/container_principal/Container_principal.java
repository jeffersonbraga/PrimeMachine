package br.com.primum.modelo.container_principal;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;

import java.util.Date;


@Entity
@Table(name="container_principal")
public class Container_principal  implements Serializable {

	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//1L;
	@Id
	@Column
	private Integer cd_container;

	@Column
	private String nm_imagem;

	@Column
	private String ds_caminho_imagem;

	@Column
	private Integer nr_sequencia;

	@Column
	private String ds_faixa;


	public Integer getCd_container(){
		return cd_container;
	}


	public void setCd_container(Integer cd_container){
		this.cd_container = cd_container;
	}


	public String getNm_imagem(){
		return nm_imagem;
	}


	public void setNm_imagem(String nm_imagem){
		this.nm_imagem = nm_imagem;
	}


	public String getDs_caminho_imagem(){
		return ds_caminho_imagem;
	}


	public void setDs_caminho_imagem(String ds_caminho_imagem){
		this.ds_caminho_imagem = ds_caminho_imagem;
	}


	public Integer getNr_sequencia(){
		return nr_sequencia;
	}


	public void setNr_sequencia(Integer nr_sequencia){
		this.nr_sequencia = nr_sequencia;
	}


	public String getDs_faixa(){
		return ds_faixa;
	}


	public void setDs_faixa(String ds_faixa){
		this.ds_faixa = ds_faixa;
	}

}