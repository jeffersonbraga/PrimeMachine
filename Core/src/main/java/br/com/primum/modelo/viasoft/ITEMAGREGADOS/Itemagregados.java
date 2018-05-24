package br.com.primum.modelo.viasoft.ITEMAGREGADOS;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMAGREGADOS")
public class Itemagregados  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private String IDAGREGADO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public String getIdagregado(){
		return IDAGREGADO;
	}


	public void setIdagregado(String IDAGREGADO){
		this.IDAGREGADO = IDAGREGADO;
	}

}