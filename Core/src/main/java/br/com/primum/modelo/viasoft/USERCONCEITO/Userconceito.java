package br.com.primum.modelo.viasoft.USERCONCEITO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="USERCONCEITO")
public class Userconceito  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer GRUPO;

	@Column
	private Integer EMPRESA;

	@Column
	private Integer IDCONCEITO;


	public Integer getGrupo(){
		return GRUPO;
	}


	public void setGrupo(Integer GRUPO){
		this.GRUPO = GRUPO;
	}


	public Integer getEmpresa(){
		return EMPRESA;
	}


	public void setEmpresa(Integer EMPRESA){
		this.EMPRESA = EMPRESA;
	}


	public Integer getIdconceito(){
		return IDCONCEITO;
	}


	public void setIdconceito(Integer IDCONCEITO){
		this.IDCONCEITO = IDCONCEITO;
	}

}