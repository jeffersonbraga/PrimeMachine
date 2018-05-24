package br.com.primum.modelo.viasoft.CUSTOESTHISTDET;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CUSTOESTHISTDET")
public class Custoesthistdet  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDHIST;

	@Column
	private Integer IDTIPODESC;

	@Column
	private Integer DESCONTO;


	public Integer getIdhist(){
		return IDHIST;
	}


	public void setIdhist(Integer IDHIST){
		this.IDHIST = IDHIST;
	}


	public Integer getIdtipodesc(){
		return IDTIPODESC;
	}


	public void setIdtipodesc(Integer IDTIPODESC){
		this.IDTIPODESC = IDTIPODESC;
	}


	public Integer getDesconto(){
		return DESCONTO;
	}


	public void setDesconto(Integer DESCONTO){
		this.DESCONTO = DESCONTO;
	}

}