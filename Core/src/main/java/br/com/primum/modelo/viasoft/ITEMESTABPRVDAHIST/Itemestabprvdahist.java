package br.com.primum.modelo.viasoft.ITEMESTABPRVDAHIST;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMESTABPRVDAHIST")
public class Itemestabprvdahist  implements Serializable {

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
	private Integer SEQUENCIA;

	@Column
	private String USERALT;

	@Column
	private Integer OFERTA;

	@Column
	private Integer OFERTAQTDMIN;

	@Column
	private Integer OFERTAQTDMAX;

	@Column
	private Integer OFERTAOFF;

	@Column
	private Integer MARGEM;


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


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public String getUseralt(){
		return USERALT;
	}


	public void setUseralt(String USERALT){
		this.USERALT = USERALT;
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


	public Integer getMargem(){
		return MARGEM;
	}


	public void setMargem(Integer MARGEM){
		this.MARGEM = MARGEM;
	}

}