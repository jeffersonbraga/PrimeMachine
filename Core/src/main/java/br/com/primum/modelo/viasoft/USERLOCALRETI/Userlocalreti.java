package br.com.primum.modelo.viasoft.USERLOCALRETI;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="USERLOCALRETI")
public class Userlocalreti  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer GRUPO;

	@Column
	private Integer ESTAB;

	@Column
	private Integer IDLOCALRETIRADA;

	@Column
	private Integer ESTABLOCAL;


	public Integer getGrupo(){
		return GRUPO;
	}


	public void setGrupo(Integer GRUPO){
		this.GRUPO = GRUPO;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdlocalretirada(){
		return IDLOCALRETIRADA;
	}


	public void setIdlocalretirada(Integer IDLOCALRETIRADA){
		this.IDLOCALRETIRADA = IDLOCALRETIRADA;
	}


	public Integer getEstablocal(){
		return ESTABLOCAL;
	}


	public void setEstablocal(Integer ESTABLOCAL){
		this.ESTABLOCAL = ESTABLOCAL;
	}

}