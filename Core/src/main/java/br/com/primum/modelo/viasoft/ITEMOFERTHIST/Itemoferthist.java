package br.com.primum.modelo.viasoft.ITEMOFERTHIST;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMOFERTHIST")
public class Itemoferthist  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDBANDEIRA;

	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private Integer SEQUENCIA;

	@Column
	private Date DATA;

	@Column
	private Integer OFERTA;

	@Column
	private Integer OFERTAOFF;

	@Column
	private Date OFERTADTOFF;

	@Column
	private Integer OFERTAQTDMIN;

	@Column
	private Integer OFERTAQTDMAX;

	@Column
	private Date OFERTADTINI;

	@Column
	private Date OFERTADTFIN;

	@Column
	private String USERID;

	@Column
	private Integer MARGEM;


	public Integer getIdbandeira(){
		return IDBANDEIRA;
	}


	public void setIdbandeira(Integer IDBANDEIRA){
		this.IDBANDEIRA = IDBANDEIRA;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
	}


	public Integer getOferta(){
		return OFERTA;
	}


	public void setOferta(Integer OFERTA){
		this.OFERTA = OFERTA;
	}


	public Integer getOfertaoff(){
		return OFERTAOFF;
	}


	public void setOfertaoff(Integer OFERTAOFF){
		this.OFERTAOFF = OFERTAOFF;
	}


	public Date getOfertadtoff(){
		return OFERTADTOFF;
	}


	public void setOfertadtoff(Date OFERTADTOFF){
		this.OFERTADTOFF = OFERTADTOFF;
	}


	public Integer getOfertaqtdmin(){
		return OFERTAQTDMIN;
	}


	public void setOfertaqtdmin(Integer OFERTAQTDMIN){
		this.OFERTAQTDMIN = OFERTAQTDMIN;
	}


	public Integer getOfertaqtdmax(){
		return OFERTAQTDMAX;
	}


	public void setOfertaqtdmax(Integer OFERTAQTDMAX){
		this.OFERTAQTDMAX = OFERTAQTDMAX;
	}


	public Date getOfertadtini(){
		return OFERTADTINI;
	}


	public void setOfertadtini(Date OFERTADTINI){
		this.OFERTADTINI = OFERTADTINI;
	}


	public Date getOfertadtfin(){
		return OFERTADTFIN;
	}


	public void setOfertadtfin(Date OFERTADTFIN){
		this.OFERTADTFIN = OFERTADTFIN;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public Integer getMargem(){
		return MARGEM;
	}


	public void setMargem(Integer MARGEM){
		this.MARGEM = MARGEM;
	}

}