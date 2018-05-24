package br.com.primum.modelo.viasoft.NCMITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NCMITEM")
public class Ncmitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String NCM;

	@Column
	private Integer IDDEPTO;

	@Column
	private Integer IDSECAO;

	@Column
	private Integer IDGRUPOITEM;

	@Column
	private Integer IDSUBGRUPO;

	@Column
	private String TIPO;

	@Column
	private Integer IDTRIBICMS;

	@Column
	private Integer IDTRIBIPI;

	@Column
	private Integer IDTRIBPIS;

	@Column
	private Integer IDTRIBCOFINS;

	@Column
	private Integer IDSETOR;

	@Column
	private Integer IDFAMILIA;

	@Column
	private Integer IDSUBFAMILIA;

	@Column
	private Integer IDTRIBFUNRURAL;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getNcm(){
		return NCM;
	}


	public void setNcm(String NCM){
		this.NCM = NCM;
	}


	public Integer getIddepto(){
		return IDDEPTO;
	}


	public void setIddepto(Integer IDDEPTO){
		this.IDDEPTO = IDDEPTO;
	}


	public Integer getIdsecao(){
		return IDSECAO;
	}


	public void setIdsecao(Integer IDSECAO){
		this.IDSECAO = IDSECAO;
	}


	public Integer getIdgrupoitem(){
		return IDGRUPOITEM;
	}


	public void setIdgrupoitem(Integer IDGRUPOITEM){
		this.IDGRUPOITEM = IDGRUPOITEM;
	}


	public Integer getIdsubgrupo(){
		return IDSUBGRUPO;
	}


	public void setIdsubgrupo(Integer IDSUBGRUPO){
		this.IDSUBGRUPO = IDSUBGRUPO;
	}


	public String getTipo(){
		return TIPO;
	}


	public void setTipo(String TIPO){
		this.TIPO = TIPO;
	}


	public Integer getIdtribicms(){
		return IDTRIBICMS;
	}


	public void setIdtribicms(Integer IDTRIBICMS){
		this.IDTRIBICMS = IDTRIBICMS;
	}


	public Integer getIdtribipi(){
		return IDTRIBIPI;
	}


	public void setIdtribipi(Integer IDTRIBIPI){
		this.IDTRIBIPI = IDTRIBIPI;
	}


	public Integer getIdtribpis(){
		return IDTRIBPIS;
	}


	public void setIdtribpis(Integer IDTRIBPIS){
		this.IDTRIBPIS = IDTRIBPIS;
	}


	public Integer getIdtribcofins(){
		return IDTRIBCOFINS;
	}


	public void setIdtribcofins(Integer IDTRIBCOFINS){
		this.IDTRIBCOFINS = IDTRIBCOFINS;
	}


	public Integer getIdsetor(){
		return IDSETOR;
	}


	public void setIdsetor(Integer IDSETOR){
		this.IDSETOR = IDSETOR;
	}


	public Integer getIdfamilia(){
		return IDFAMILIA;
	}


	public void setIdfamilia(Integer IDFAMILIA){
		this.IDFAMILIA = IDFAMILIA;
	}


	public Integer getIdsubfamilia(){
		return IDSUBFAMILIA;
	}


	public void setIdsubfamilia(Integer IDSUBFAMILIA){
		this.IDSUBFAMILIA = IDSUBFAMILIA;
	}


	public Integer getIdtribfunrural(){
		return IDTRIBFUNRURAL;
	}


	public void setIdtribfunrural(Integer IDTRIBFUNRURAL){
		this.IDTRIBFUNRURAL = IDTRIBFUNRURAL;
	}

}