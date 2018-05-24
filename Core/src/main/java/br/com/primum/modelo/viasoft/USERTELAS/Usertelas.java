package br.com.primum.modelo.viasoft.USERTELAS;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="USERTELAS")
public class Usertelas  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDTELA;

	@Column
	private Integer GRUPO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdtela(){
		return IDTELA;
	}


	public void setIdtela(Integer IDTELA){
		this.IDTELA = IDTELA;
	}


	public Integer getGrupo(){
		return GRUPO;
	}


	public void setGrupo(Integer GRUPO){
		this.GRUPO = GRUPO;
	}

}