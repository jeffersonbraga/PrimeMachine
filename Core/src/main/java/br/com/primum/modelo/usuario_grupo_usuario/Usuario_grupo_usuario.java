package br.com.primum.modelo.usuario_grupo_usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="usuario_grupo_usuario")
public class Usuario_grupo_usuario {

	@Id
	@Column	
	private Integer cd_usuario_grupo_usuario; 
	
	@Column
	private Integer cd_usuario; 
	
	@Column
	private Integer cd_grupo_usuario; 
	
	@Column
	private Integer cd_unidade;

	
	@Transient
	private String nm_usuario;
	
	@Transient
	private String nm_grupo_pertencente;
	
	@Transient
	private Boolean marcado = false;
	

	@Transient
	private byte[] sourceImagem;
	
	
	public byte[] getSourceImagem() {
		return sourceImagem;
	}

	public void setSourceImagem(byte[] sourceImagem) {
		this.sourceImagem = sourceImagem;
	}
	
	public Boolean getMarcado() {
		return marcado;
	}

	public void setMarcado(Boolean marcado) {
		this.marcado = marcado;
	}

	public String getNm_usuario() {
		return nm_usuario;
	}

	public void setNm_usuario(String nm_usuario) {
		this.nm_usuario = nm_usuario;
	}

	public String getNm_grupo_pertencente() {
		return nm_grupo_pertencente;
	}

	public void setNm_grupo_pertencente(String nm_grupo_pertencente) {
		this.nm_grupo_pertencente = nm_grupo_pertencente;
	}

	public Integer getCd_usuario_grupo_usuario() {
		return cd_usuario_grupo_usuario;
	}

	public void setCd_usuario_grupo_usuario(Integer cd_usuario_grupo_usuario) {
		this.cd_usuario_grupo_usuario = cd_usuario_grupo_usuario;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Integer getCd_grupo_usuario() {
		return cd_grupo_usuario;
	}

	public void setCd_grupo_usuario(Integer cd_grupo_usuario) {
		this.cd_grupo_usuario = cd_grupo_usuario;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}

}
