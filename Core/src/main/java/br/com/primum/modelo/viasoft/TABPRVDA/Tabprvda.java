package br.com.primum.modelo.viasoft.TABPRVDA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="TABPRVDA")
public class Tabprvda  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDTABPRVDA;

	@Column
	private String DESCRICAO;

	@Column
	private Date DTINICIAL;

	@Column
	private Date DTFINAL;

	@Column
	private String TIPO;

	@Column
	private String BASE;

	@Column
	private String TDITENS;

	@Column
	private Integer PERCPADRAO;

	@Column
	private Integer TIPOTAB;

	@Column
	private String CLASSMERC;

	@Column
	private String DEPARTAMENTOS;

	@Column
	private String SECOES;

	@Column
	private String GRUPOSITENS;

	@Column
	private String SUBGRUPOS;

	@Column
	private String MARCAS;


	public Integer getIdtabprvda(){
		return IDTABPRVDA;
	}


	public void setIdtabprvda(Integer IDTABPRVDA){
		this.IDTABPRVDA = IDTABPRVDA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Date getDtinicial(){
		return DTINICIAL;
	}


	public void setDtinicial(Date DTINICIAL){
		this.DTINICIAL = DTINICIAL;
	}


	public Date getDtfinal(){
		return DTFINAL;
	}


	public void setDtfinal(Date DTFINAL){
		this.DTFINAL = DTFINAL;
	}


	public String getTipo(){
		return TIPO;
	}


	public void setTipo(String TIPO){
		this.TIPO = TIPO;
	}


	public String getBase(){
		return BASE;
	}


	public void setBase(String BASE){
		this.BASE = BASE;
	}


	public String getTditens(){
		return TDITENS;
	}


	public void setTditens(String TDITENS){
		this.TDITENS = TDITENS;
	}


	public Integer getPercpadrao(){
		return PERCPADRAO;
	}


	public void setPercpadrao(Integer PERCPADRAO){
		this.PERCPADRAO = PERCPADRAO;
	}


	public Integer getTipotab(){
		return TIPOTAB;
	}


	public void setTipotab(Integer TIPOTAB){
		this.TIPOTAB = TIPOTAB;
	}


	public String getClassmerc(){
		return CLASSMERC;
	}


	public void setClassmerc(String CLASSMERC){
		this.CLASSMERC = CLASSMERC;
	}


	public String getDepartamentos(){
		return DEPARTAMENTOS;
	}


	public void setDepartamentos(String DEPARTAMENTOS){
		this.DEPARTAMENTOS = DEPARTAMENTOS;
	}


	public String getSecoes(){
		return SECOES;
	}


	public void setSecoes(String SECOES){
		this.SECOES = SECOES;
	}


	public String getGrupositens(){
		return GRUPOSITENS;
	}


	public void setGrupositens(String GRUPOSITENS){
		this.GRUPOSITENS = GRUPOSITENS;
	}


	public String getSubgrupos(){
		return SUBGRUPOS;
	}


	public void setSubgrupos(String SUBGRUPOS){
		this.SUBGRUPOS = SUBGRUPOS;
	}


	public String getMarcas(){
		return MARCAS;
	}


	public void setMarcas(String MARCAS){
		this.MARCAS = MARCAS;
	}

}