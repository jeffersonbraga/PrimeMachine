package br.com.primum.modelo.viasoft.ITEMESTABPRVDA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMESTABPRVDA")
public class Itemestabprvda  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer ESTABITEM;

	@Column
	private String IDITEM;

	@Column
	private Integer OFERTA;

	@Column
	private Integer OFERTAQTDMIN;

	@Column
	private Integer OFERTAQTDMAX;

	@Column
	private Integer OFERTAOFF;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getEstabitem(){
		return ESTABITEM;
	}


	public void setEstabitem(Integer ESTABITEM){
		this.ESTABITEM = ESTABITEM;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getOferta(){
		return OFERTA;
	}


	public void setOferta(Integer OFERTA){
		this.OFERTA = OFERTA;
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


	public Integer getOfertaoff(){
		return OFERTAOFF;
	}


	public void setOfertaoff(Integer OFERTAOFF){
		this.OFERTAOFF = OFERTAOFF;
	}

}