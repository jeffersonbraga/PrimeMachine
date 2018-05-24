package br.com.primum.modelo.viasoft.PESSOADOCMCP;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOADOCMCP")
public class Pessoadocmcp  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer EMPRESA;

	@Column
	private Integer IDPESS;

	@Column
	private Integer ESTABCAD;

	@Column
	private Date DTINATIVO;

	@Column
	private Date DTCADASTRO;

	@Column
	private String COBRAR;

	@Column
	private String COBRADOR;

	@Column
	private String COMISSIONADO;

	@Column
	private Integer IDCOMISPESS;

	@Column
	private Integer IDNOTACONF;

	@Column
	private String EHFORNECEDOR;

	@Column
	private String EHCLIENTE;

	@Column
	private Integer IDCONCEITO;

	@Column
	private String PALAVRACHAVE;

	@Column
	private String SEXO;

	@Column
	private String PRESTADOR;

	@Column
	private String EHFUNCIONARIO;


	public Integer getEmpresa(){
		return EMPRESA;
	}


	public void setEmpresa(Integer EMPRESA){
		this.EMPRESA = EMPRESA;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getEstabcad(){
		return ESTABCAD;
	}


	public void setEstabcad(Integer ESTABCAD){
		this.ESTABCAD = ESTABCAD;
	}


	public Date getDtinativo(){
		return DTINATIVO;
	}


	public void setDtinativo(Date DTINATIVO){
		this.DTINATIVO = DTINATIVO;
	}


	public Date getDtcadastro(){
		return DTCADASTRO;
	}


	public void setDtcadastro(Date DTCADASTRO){
		this.DTCADASTRO = DTCADASTRO;
	}


	public String getCobrar(){
		return COBRAR;
	}


	public void setCobrar(String COBRAR){
		this.COBRAR = COBRAR;
	}


	public String getCobrador(){
		return COBRADOR;
	}


	public void setCobrador(String COBRADOR){
		this.COBRADOR = COBRADOR;
	}


	public String getComissionado(){
		return COMISSIONADO;
	}


	public void setComissionado(String COMISSIONADO){
		this.COMISSIONADO = COMISSIONADO;
	}


	public Integer getIdcomispess(){
		return IDCOMISPESS;
	}


	public void setIdcomispess(Integer IDCOMISPESS){
		this.IDCOMISPESS = IDCOMISPESS;
	}


	public Integer getIdnotaconf(){
		return IDNOTACONF;
	}


	public void setIdnotaconf(Integer IDNOTACONF){
		this.IDNOTACONF = IDNOTACONF;
	}


	public String getEhfornecedor(){
		return EHFORNECEDOR;
	}


	public void setEhfornecedor(String EHFORNECEDOR){
		this.EHFORNECEDOR = EHFORNECEDOR;
	}


	public String getEhcliente(){
		return EHCLIENTE;
	}


	public void setEhcliente(String EHCLIENTE){
		this.EHCLIENTE = EHCLIENTE;
	}


	public Integer getIdconceito(){
		return IDCONCEITO;
	}


	public void setIdconceito(Integer IDCONCEITO){
		this.IDCONCEITO = IDCONCEITO;
	}


	public String getPalavrachave(){
		return PALAVRACHAVE;
	}


	public void setPalavrachave(String PALAVRACHAVE){
		this.PALAVRACHAVE = PALAVRACHAVE;
	}


	public String getSexo(){
		return SEXO;
	}


	public void setSexo(String SEXO){
		this.SEXO = SEXO;
	}


	public String getPrestador(){
		return PRESTADOR;
	}


	public void setPrestador(String PRESTADOR){
		this.PRESTADOR = PRESTADOR;
	}


	public String getEhfuncionario(){
		return EHFUNCIONARIO;
	}


	public void setEhfuncionario(String EHFUNCIONARIO){
		this.EHFUNCIONARIO = EHFUNCIONARIO;
	}

}