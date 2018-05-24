package br.com.primum.modelo.viasoft.ITEMIMAGEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMIMAGEM")
public class Itemimagem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private Integer IDITEMIMAGEM;

	@Column
	private String WEIMAGEFILE;

	@Column
	private String WETIPO;


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


	public Integer getIditemimagem(){
		return IDITEMIMAGEM;
	}


	public void setIditemimagem(Integer IDITEMIMAGEM){
		this.IDITEMIMAGEM = IDITEMIMAGEM;
	}


	public String getWeimagefile(){
		return WEIMAGEFILE;
	}


	public void setWeimagefile(String WEIMAGEFILE){
		this.WEIMAGEFILE = WEIMAGEFILE;
	}


	public String getWetipo(){
		return WETIPO;
	}


	public void setWetipo(String WETIPO){
		this.WETIPO = WETIPO;
	}

}