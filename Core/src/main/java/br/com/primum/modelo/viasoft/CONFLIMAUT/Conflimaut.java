package br.com.primum.modelo.viasoft.CONFLIMAUT;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONFLIMAUT")
public class Conflimaut  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer NROAUT;

	@Column
	private Integer LIMITE;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getNroaut(){
		return NROAUT;
	}


	public void setNroaut(Integer NROAUT){
		this.NROAUT = NROAUT;
	}


	public Integer getLimite(){
		return LIMITE;
	}


	public void setLimite(Integer LIMITE){
		this.LIMITE = LIMITE;
	}

}