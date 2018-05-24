package br.com.primum.modelo.viasoft.ROMANEIO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ROMANEIO")
public class Romaneio  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDROMANEIO;

	@Column
	private Integer ESTAB;

	@Column
	private Date DATA;

	@Column
	private String USERID;

	@Column
	private String PLACA;

	@Column
	private Integer IDMOTORISTA;


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


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public String getPlaca(){
		return PLACA;
	}


	public void setPlaca(String PLACA){
		this.PLACA = PLACA;
	}


	public Integer getIdmotorista(){
		return IDMOTORISTA;
	}


	public void setIdmotorista(Integer IDMOTORISTA){
		this.IDMOTORISTA = IDMOTORISTA;
	}

}