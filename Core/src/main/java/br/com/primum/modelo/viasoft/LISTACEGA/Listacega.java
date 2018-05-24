package br.com.primum.modelo.viasoft.LISTACEGA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="LISTACEGA")
public class Listacega  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDLISTACEGA;

	@Column
	private Integer IDNOTA;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdlistacega(){
		return IDLISTACEGA;
	}


	public void setIdlistacega(Integer IDLISTACEGA){
		this.IDLISTACEGA = IDLISTACEGA;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
	}

}