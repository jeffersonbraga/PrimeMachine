package br.com.primum.modelo.viasoft.ROMANEIOITE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ROMANEIOITE")
public class Romaneioite  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDROMANEIO;

	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;


	public Integer getIdromaneio(){
		return IDROMANEIO;
	}


	public void setIdromaneio(Integer IDROMANEIO){
		this.IDROMANEIO = IDROMANEIO;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
	}

}