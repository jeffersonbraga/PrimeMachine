package br.com.primum.modelo.viasoft.NOTAREFITE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NOTAREFITE")
public class Notarefite  implements Serializable {

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
	private Integer IDNOTAREF;

	@Column
	private Integer SEQITEMREF;

	@Column
	private Integer IDNOTAREFITE;

	@Column
	private Integer QTDEREF;

	@Column
	private Integer ESTABREF;

	@Column
	private Integer IDCARGA;

	@Column
	private String DESCONSIDERA;


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


	public Integer getIdnotaref(){
		return IDNOTAREF;
	}


	public void setIdnotaref(Integer IDNOTAREF){
		this.IDNOTAREF = IDNOTAREF;
	}


	public Integer getSeqitemref(){
		return SEQITEMREF;
	}


	public void setSeqitemref(Integer SEQITEMREF){
		this.SEQITEMREF = SEQITEMREF;
	}


	public Integer getIdnotarefite(){
		return IDNOTAREFITE;
	}


	public void setIdnotarefite(Integer IDNOTAREFITE){
		this.IDNOTAREFITE = IDNOTAREFITE;
	}


	public Integer getQtderef(){
		return QTDEREF;
	}


	public void setQtderef(Integer QTDEREF){
		this.QTDEREF = QTDEREF;
	}


	public Integer getEstabref(){
		return ESTABREF;
	}


	public void setEstabref(Integer ESTABREF){
		this.ESTABREF = ESTABREF;
	}


	public Integer getIdcarga(){
		return IDCARGA;
	}


	public void setIdcarga(Integer IDCARGA){
		this.IDCARGA = IDCARGA;
	}


	public String getDesconsidera(){
		return DESCONSIDERA;
	}


	public void setDesconsidera(String DESCONSIDERA){
		this.DESCONSIDERA = DESCONSIDERA;
	}

}