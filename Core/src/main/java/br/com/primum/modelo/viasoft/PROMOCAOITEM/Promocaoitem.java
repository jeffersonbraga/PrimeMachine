package br.com.primum.modelo.viasoft.PROMOCAOITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PROMOCAOITEM")
public class Promocaoitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDPROMO;

	@Column
	private Integer SEQUENCIA;

	@Column
	private Integer SEQ;

	@Column
	private String IDITEM;

	@Column
	private Integer IDMARCA;

	@Column
	private Integer QTD;


	public Integer getIdpromo(){
		return IDPROMO;
	}


	public void setIdpromo(Integer IDPROMO){
		this.IDPROMO = IDPROMO;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public Integer getSeq(){
		return SEQ;
	}


	public void setSeq(Integer SEQ){
		this.SEQ = SEQ;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getIdmarca(){
		return IDMARCA;
	}


	public void setIdmarca(Integer IDMARCA){
		this.IDMARCA = IDMARCA;
	}


	public Integer getQtd(){
		return QTD;
	}


	public void setQtd(Integer QTD){
		this.QTD = QTD;
	}

}