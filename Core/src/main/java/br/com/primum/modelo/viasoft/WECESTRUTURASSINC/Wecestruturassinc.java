package br.com.primum.modelo.viasoft.WECESTRUTURASSINC;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="WECESTRUTURASSINC")
public class Wecestruturassinc  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDGRUPO;

	@Column
	private Integer IDSUBGRUPO;

	@Column
	private Integer IDSUBGRUPOECOMMERCE;


	public Integer getIdgrupo(){
		return IDGRUPO;
	}


	public void setIdgrupo(Integer IDGRUPO){
		this.IDGRUPO = IDGRUPO;
	}


	public Integer getIdsubgrupo(){
		return IDSUBGRUPO;
	}


	public void setIdsubgrupo(Integer IDSUBGRUPO){
		this.IDSUBGRUPO = IDSUBGRUPO;
	}


	public Integer getIdsubgrupoecommerce(){
		return IDSUBGRUPOECOMMERCE;
	}


	public void setIdsubgrupoecommerce(Integer IDSUBGRUPOECOMMERCE){
		this.IDSUBGRUPOECOMMERCE = IDSUBGRUPOECOMMERCE;
	}

}