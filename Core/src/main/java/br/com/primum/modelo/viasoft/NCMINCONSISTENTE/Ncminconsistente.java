package br.com.primum.modelo.viasoft.NCMINCONSISTENTE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NCMINCONSISTENTE")
public class Ncminconsistente  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer SEQITEM;

	@Column
	private String IDITEM;

	@Column
	private Date DATA;

	@Column
	private String NCMITE;

	@Column
	private String NCMXML;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
	}


	public Integer getSeqitem(){
		return SEQITEM;
	}


	public void setSeqitem(Integer SEQITEM){
		this.SEQITEM = SEQITEM;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
	}


	public String getNcmite(){
		return NCMITE;
	}


	public void setNcmite(String NCMITE){
		this.NCMITE = NCMITE;
	}


	public String getNcmxml(){
		return NCMXML;
	}


	public void setNcmxml(String NCMXML){
		this.NCMXML = NCMXML;
	}

}