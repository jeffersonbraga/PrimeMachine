package br.com.primum.modelo.viasoft.IMPITEMETIQ;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="IMPITEMETIQ")
public class Impitemetiq  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDIMPETIQ;

	@Column
	private Integer SEQITEM;

	@Column
	private String IDITEM;

	@Column
	private Integer QTDE;

	@Column
	private Integer ESTAB;

	@Column
	private String USERID;

	@Column
	private String ETIQUETADO;

	@Column
	private String CODBARRAS;

	@Column
	private Integer IDEMBALAGEM;


	public Integer getIdimpetiq(){
		return IDIMPETIQ;
	}


	public void setIdimpetiq(Integer IDIMPETIQ){
		this.IDIMPETIQ = IDIMPETIQ;
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


	public Integer getQtde(){
		return QTDE;
	}


	public void setQtde(Integer QTDE){
		this.QTDE = QTDE;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public String getEtiquetado(){
		return ETIQUETADO;
	}


	public void setEtiquetado(String ETIQUETADO){
		this.ETIQUETADO = ETIQUETADO;
	}


	public String getCodbarras(){
		return CODBARRAS;
	}


	public void setCodbarras(String CODBARRAS){
		this.CODBARRAS = CODBARRAS;
	}


	public Integer getIdembalagem(){
		return IDEMBALAGEM;
	}


	public void setIdembalagem(Integer IDEMBALAGEM){
		this.IDEMBALAGEM = IDEMBALAGEM;
	}

}