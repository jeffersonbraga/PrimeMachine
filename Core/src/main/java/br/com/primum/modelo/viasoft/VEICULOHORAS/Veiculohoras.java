package br.com.primum.modelo.viasoft.VEICULOHORAS;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="VEICULOHORAS")
public class Veiculohoras  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDVEICHORA;

	@Column
	private Integer IDMOTORISTA;

	@Column
	private String PLACA;

	@Column
	private Date DATA;

	@Column
	private Integer HORIMETRO;


	public Integer getIdveichora(){
		return IDVEICHORA;
	}


	public void setIdveichora(Integer IDVEICHORA){
		this.IDVEICHORA = IDVEICHORA;
	}


	public Integer getIdmotorista(){
		return IDMOTORISTA;
	}


	public void setIdmotorista(Integer IDMOTORISTA){
		this.IDMOTORISTA = IDMOTORISTA;
	}


	public String getPlaca(){
		return PLACA;
	}


	public void setPlaca(String PLACA){
		this.PLACA = PLACA;
	}


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
	}


	public Integer getHorimetro(){
		return HORIMETRO;
	}


	public void setHorimetro(Integer HORIMETRO){
		this.HORIMETRO = HORIMETRO;
	}

}