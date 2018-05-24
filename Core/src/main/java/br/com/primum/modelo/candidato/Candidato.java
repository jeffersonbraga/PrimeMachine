package br.com.primum.modelo.candidato;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;

import java.util.Date;


@Entity
@Table(name="candidato")
public class Candidato  implements Serializable {

	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//1L;
	@Id
	@Column
	private Integer cd_candidato;

	@Column
	private Integer cd_pessoa;

	@Column
	private String ds_nome_candidato;

	@Column
	private String ds_numero;

	@Column
	private String ds_partido;

	@Column
	private String ds_facebook;

	@Column
	private String ds_instagram;

	@Column
	private String ds_tweeter;


	public Integer getCd_candidato(){
		return cd_candidato;
	}


	public void setCd_candidato(Integer cd_candidato){
		this.cd_candidato = cd_candidato;
	}


	public Integer getCd_pessoa(){
		return cd_pessoa;
	}


	public void setCd_pessoa(Integer cd_pessoa){
		this.cd_pessoa = cd_pessoa;
	}


	public String getDs_nome_candidato(){
		return ds_nome_candidato;
	}


	public void setDs_nome_candidato(String ds_nome_candidato){
		this.ds_nome_candidato = ds_nome_candidato;
	}


	public String getDs_numero(){
		return ds_numero;
	}


	public void setDs_numero(String ds_numero){
		this.ds_numero = ds_numero;
	}


	public String getDs_partido(){
		return ds_partido;
	}


	public void setDs_partido(String ds_partido){
		this.ds_partido = ds_partido;
	}


	public String getDs_facebook(){
		return ds_facebook;
	}


	public void setDs_facebook(String ds_facebook){
		this.ds_facebook = ds_facebook;
	}


	public String getDs_instagram(){
		return ds_instagram;
	}


	public void setDs_instagram(String ds_instagram){
		this.ds_instagram = ds_instagram;
	}


	public String getDs_tweeter(){
		return ds_tweeter;
	}


	public void setDs_tweeter(String ds_tweeter){
		this.ds_tweeter = ds_tweeter;
	}

}