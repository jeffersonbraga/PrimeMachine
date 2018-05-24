package br.com.primum.modelo.viasoft.DISTNORMAL;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="DISTNORMAL")
public class Distnormal  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDDISTNORMAL;

	@Column
	private Integer EIXO;

	@Column
	private Integer VALOR;


	public Integer getIddistnormal(){
		return IDDISTNORMAL;
	}


	public void setIddistnormal(Integer IDDISTNORMAL){
		this.IDDISTNORMAL = IDDISTNORMAL;
	}


	public Integer getEixo(){
		return EIXO;
	}


	public void setEixo(Integer EIXO){
		this.EIXO = EIXO;
	}


	public Integer getValor(){
		return VALOR;
	}


	public void setValor(Integer VALOR){
		this.VALOR = VALOR;
	}

}