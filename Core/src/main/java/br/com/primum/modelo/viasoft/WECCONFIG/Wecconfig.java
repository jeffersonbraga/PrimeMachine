package br.com.primum.modelo.viasoft.WECCONFIG;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="WECCONFIG")
public class Wecconfig  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTABEC;

	@Column
	private String LOGIN;

	@Column
	private String SENHA;

	@Column
	private String URLSERVICO;

	@Column
	private String AGREGADOSTO;

	@Column
	private String SIMILARESTO;

	@Column
	private String DOWNLOADPEDIDOS;

	@Column
	private String DIRLOG;

	@Column
	private Integer IDNOTACONF;

	@Column
	private String VERSAO;

	@Column
	private Integer IDOUTROS;

	@Column
	private String AMBIENTE;


	public Integer getEstabec(){
		return ESTABEC;
	}


	public void setEstabec(Integer ESTABEC){
		this.ESTABEC = ESTABEC;
	}


	public String getLogin(){
		return LOGIN;
	}


	public void setLogin(String LOGIN){
		this.LOGIN = LOGIN;
	}


	public String getSenha(){
		return SENHA;
	}


	public void setSenha(String SENHA){
		this.SENHA = SENHA;
	}


	public String getUrlservico(){
		return URLSERVICO;
	}


	public void setUrlservico(String URLSERVICO){
		this.URLSERVICO = URLSERVICO;
	}


	public String getAgregadosto(){
		return AGREGADOSTO;
	}


	public void setAgregadosto(String AGREGADOSTO){
		this.AGREGADOSTO = AGREGADOSTO;
	}


	public String getSimilaresto(){
		return SIMILARESTO;
	}


	public void setSimilaresto(String SIMILARESTO){
		this.SIMILARESTO = SIMILARESTO;
	}


	public String getDownloadpedidos(){
		return DOWNLOADPEDIDOS;
	}


	public void setDownloadpedidos(String DOWNLOADPEDIDOS){
		this.DOWNLOADPEDIDOS = DOWNLOADPEDIDOS;
	}


	public String getDirlog(){
		return DIRLOG;
	}


	public void setDirlog(String DIRLOG){
		this.DIRLOG = DIRLOG;
	}


	public Integer getIdnotaconf(){
		return IDNOTACONF;
	}


	public void setIdnotaconf(Integer IDNOTACONF){
		this.IDNOTACONF = IDNOTACONF;
	}


	public String getVersao(){
		return VERSAO;
	}


	public void setVersao(String VERSAO){
		this.VERSAO = VERSAO;
	}


	public Integer getIdoutros(){
		return IDOUTROS;
	}


	public void setIdoutros(Integer IDOUTROS){
		this.IDOUTROS = IDOUTROS;
	}


	public String getAmbiente(){
		return AMBIENTE;
	}


	public void setAmbiente(String AMBIENTE){
		this.AMBIENTE = AMBIENTE;
	}

}