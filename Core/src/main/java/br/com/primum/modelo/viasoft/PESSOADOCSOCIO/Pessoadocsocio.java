package br.com.primum.modelo.viasoft.PESSOADOCSOCIO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOADOCSOCIO")
public class Pessoadocsocio  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDPESS;

	@Column
	private Integer IDPESSOADOCSOCIO;

	@Column
	private String NOME;

	@Column
	private String EMAIL;

	@Column
	private String TELEFONE;

	@Column
	private String CELULAR;

	@Column
	private String COMPLEMENTAR;

	@Column
	private String FUNCAO;

	@Column
	private String CPF;

	@Column
	private Date DTNASCIMENTO;


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getIdpessoadocsocio(){
		return IDPESSOADOCSOCIO;
	}


	public void setIdpessoadocsocio(Integer IDPESSOADOCSOCIO){
		this.IDPESSOADOCSOCIO = IDPESSOADOCSOCIO;
	}


	public String getNome(){
		return NOME;
	}


	public void setNome(String NOME){
		this.NOME = NOME;
	}


	public String getEmail(){
		return EMAIL;
	}


	public void setEmail(String EMAIL){
		this.EMAIL = EMAIL;
	}


	public String getTelefone(){
		return TELEFONE;
	}


	public void setTelefone(String TELEFONE){
		this.TELEFONE = TELEFONE;
	}


	public String getCelular(){
		return CELULAR;
	}


	public void setCelular(String CELULAR){
		this.CELULAR = CELULAR;
	}


	public String getComplementar(){
		return COMPLEMENTAR;
	}


	public void setComplementar(String COMPLEMENTAR){
		this.COMPLEMENTAR = COMPLEMENTAR;
	}


	public String getFuncao(){
		return FUNCAO;
	}


	public void setFuncao(String FUNCAO){
		this.FUNCAO = FUNCAO;
	}


	public String getCpf(){
		return CPF;
	}


	public void setCpf(String CPF){
		this.CPF = CPF;
	}


	public Date getDtnascimento(){
		return DTNASCIMENTO;
	}


	public void setDtnascimento(Date DTNASCIMENTO){
		this.DTNASCIMENTO = DTNASCIMENTO;
	}

}