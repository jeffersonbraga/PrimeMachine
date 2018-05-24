package br.com.primum.modelo.viasoft.CLASSMERC;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CLASSMERC")
public class Classmerc  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCLASS;

	@Column
	private Integer ESTAB;

	@Column
	private Integer IDDEPTO;

	@Column
	private Integer IDSECAO;

	@Column
	private Integer IDGRUPOITEM;

	@Column
	private Integer IDSUBGRUPO;

	@Column
	private Integer IDSETOR;

	@Column
	private Integer IDFAMILIA;

	@Column
	private Integer IDSUBFAMILIA;

	@Column
	private Integer MARGEMOFERTA;

	@Column
	private Integer MARGEMVENDA;

	@Column
	private Integer DESPESASOPER;

	@Column
	private Integer PERCCOFINS;

	@Column
	private Integer PERCICMS;

	@Column
	private Integer PERCPIS;


	public Integer getIdclass(){
		return IDCLASS;
	}


	public void setIdclass(Integer IDCLASS){
		this.IDCLASS = IDCLASS;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
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


	public Integer getMargemoferta(){
		return MARGEMOFERTA;
	}


	public void setMargemoferta(Integer MARGEMOFERTA){
		this.MARGEMOFERTA = MARGEMOFERTA;
	}


	public Integer getMargemvenda(){
		return MARGEMVENDA;
	}


	public void setMargemvenda(Integer MARGEMVENDA){
		this.MARGEMVENDA = MARGEMVENDA;
	}


	public Integer getDespesasoper(){
		return DESPESASOPER;
	}


	public void setDespesasoper(Integer DESPESASOPER){
		this.DESPESASOPER = DESPESASOPER;
	}


	public Integer getPerccofins(){
		return PERCCOFINS;
	}


	public void setPerccofins(Integer PERCCOFINS){
		this.PERCCOFINS = PERCCOFINS;
	}


	public Integer getPercicms(){
		return PERCICMS;
	}


	public void setPercicms(Integer PERCICMS){
		this.PERCICMS = PERCICMS;
	}


	public Integer getPercpis(){
		return PERCPIS;
	}


	public void setPercpis(Integer PERCPIS){
		this.PERCPIS = PERCPIS;
	}

}