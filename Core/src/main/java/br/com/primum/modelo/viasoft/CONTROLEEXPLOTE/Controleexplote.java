package br.com.primum.modelo.viasoft.CONTROLEEXPLOTE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONTROLEEXPLOTE")
public class Controleexplote  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDCONTROLEEXP;

	@Column
	private Integer SEQCONTROLE;

	@Column
	private String LOTE;

	@Column
	private Integer QTDERETIRA;

	@Column
	private Integer QTDEENTREGA;

	@Column
	private Integer QTDEDEVOL;

	@Column
	private Integer QTDEORI;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdcontroleexp(){
		return IDCONTROLEEXP;
	}


	public void setIdcontroleexp(Integer IDCONTROLEEXP){
		this.IDCONTROLEEXP = IDCONTROLEEXP;
	}


	public Integer getSeqcontrole(){
		return SEQCONTROLE;
	}


	public void setSeqcontrole(Integer SEQCONTROLE){
		this.SEQCONTROLE = SEQCONTROLE;
	}


	public String getLote(){
		return LOTE;
	}


	public void setLote(String LOTE){
		this.LOTE = LOTE;
	}


	public Integer getQtderetira(){
		return QTDERETIRA;
	}


	public void setQtderetira(Integer QTDERETIRA){
		this.QTDERETIRA = QTDERETIRA;
	}


	public Integer getQtdeentrega(){
		return QTDEENTREGA;
	}


	public void setQtdeentrega(Integer QTDEENTREGA){
		this.QTDEENTREGA = QTDEENTREGA;
	}


	public Integer getQtdedevol(){
		return QTDEDEVOL;
	}


	public void setQtdedevol(Integer QTDEDEVOL){
		this.QTDEDEVOL = QTDEDEVOL;
	}


	public Integer getQtdeori(){
		return QTDEORI;
	}


	public void setQtdeori(Integer QTDEORI){
		this.QTDEORI = QTDEORI;
	}

}