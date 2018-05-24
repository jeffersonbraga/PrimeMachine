package br.com.primum.modelo.viasoft.PINLOCAL;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PINLOCAL")
public class Pinlocal  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer PINID;

	@Column
	private String OBSERVACAO;

	@Column
	private String SMTPSERVER;

	@Column
	private String POPSERVER;

	@Column
	private Integer SMTPPORTA;

	@Column
	private Integer POPPORTA;

	@Column
	private String USUARIO;

	@Column
	private String SENHA;

	@Column
	private String AUTENTICAR;

	@Column
	private String USUARIOAUT;

	@Column
	private String SENHAAUT;


	public Integer getPinid(){
		return PINID;
	}


	public void setPinid(Integer PINID){
		this.PINID = PINID;
	}


	public String getObservacao(){
		return OBSERVACAO;
	}


	public void setObservacao(String OBSERVACAO){
		this.OBSERVACAO = OBSERVACAO;
	}


	public String getSmtpserver(){
		return SMTPSERVER;
	}


	public void setSmtpserver(String SMTPSERVER){
		this.SMTPSERVER = SMTPSERVER;
	}


	public String getPopserver(){
		return POPSERVER;
	}


	public void setPopserver(String POPSERVER){
		this.POPSERVER = POPSERVER;
	}


	public Integer getSmtpporta(){
		return SMTPPORTA;
	}


	public void setSmtpporta(Integer SMTPPORTA){
		this.SMTPPORTA = SMTPPORTA;
	}


	public Integer getPopporta(){
		return POPPORTA;
	}


	public void setPopporta(Integer POPPORTA){
		this.POPPORTA = POPPORTA;
	}


	public String getUsuario(){
		return USUARIO;
	}


	public void setUsuario(String USUARIO){
		this.USUARIO = USUARIO;
	}


	public String getSenha(){
		return SENHA;
	}


	public void setSenha(String SENHA){
		this.SENHA = SENHA;
	}


	public String getAutenticar(){
		return AUTENTICAR;
	}


	public void setAutenticar(String AUTENTICAR){
		this.AUTENTICAR = AUTENTICAR;
	}


	public String getUsuarioaut(){
		return USUARIOAUT;
	}


	public void setUsuarioaut(String USUARIOAUT){
		this.USUARIOAUT = USUARIOAUT;
	}


	public String getSenhaaut(){
		return SENHAAUT;
	}


	public void setSenhaaut(String SENHAAUT){
		this.SENHAAUT = SENHAAUT;
	}

}