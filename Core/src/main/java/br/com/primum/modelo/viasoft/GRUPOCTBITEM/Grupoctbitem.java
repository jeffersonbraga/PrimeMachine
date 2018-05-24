package br.com.primum.modelo.viasoft.GRUPOCTBITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="GRUPOCTBITEM")
public class Grupoctbitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDGRUPOCTB;

	@Column
	private Integer SEQUENCIA;

	@Column
	private Integer CTADEBITO;

	@Column
	private Integer CTACREDITO;

	@Column
	private String CFOP;


	public Integer getIdgrupoctb(){
		return IDGRUPOCTB;
	}


	public void setIdgrupoctb(Integer IDGRUPOCTB){
		this.IDGRUPOCTB = IDGRUPOCTB;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public Integer getCtadebito(){
		return CTADEBITO;
	}


	public void setCtadebito(Integer CTADEBITO){
		this.CTADEBITO = CTADEBITO;
	}


	public Integer getCtacredito(){
		return CTACREDITO;
	}


	public void setCtacredito(Integer CTACREDITO){
		this.CTACREDITO = CTACREDITO;
	}


	public String getCfop(){
		return CFOP;
	}


	public void setCfop(String CFOP){
		this.CFOP = CFOP;
	}

}