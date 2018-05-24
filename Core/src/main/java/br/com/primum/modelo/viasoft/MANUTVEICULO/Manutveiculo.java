package br.com.primum.modelo.viasoft.MANUTVEICULO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="MANUTVEICULO")
public class Manutveiculo  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDMANUT;

	@Column
	private String PLACA;

	@Column
	private Integer IDFTITEMPREV;

	@Column
	private String USERINICIO;

	@Column
	private String USERFIM;

	@Column
	private String SITUACAO;

	@Column
	private String OBS;

	@Column
	private Integer ESTABNOTA;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer HODOMETRO;

	@Column
	private Integer HORIMETRO;


	public Integer getIdmanut(){
		return IDMANUT;
	}


	public void setIdmanut(Integer IDMANUT){
		this.IDMANUT = IDMANUT;
	}


	public String getPlaca(){
		return PLACA;
	}


	public void setPlaca(String PLACA){
		this.PLACA = PLACA;
	}


	public Integer getIdftitemprev(){
		return IDFTITEMPREV;
	}


	public void setIdftitemprev(Integer IDFTITEMPREV){
		this.IDFTITEMPREV = IDFTITEMPREV;
	}


	public String getUserinicio(){
		return USERINICIO;
	}


	public void setUserinicio(String USERINICIO){
		this.USERINICIO = USERINICIO;
	}


	public String getUserfim(){
		return USERFIM;
	}


	public void setUserfim(String USERFIM){
		this.USERFIM = USERFIM;
	}


	public String getSituacao(){
		return SITUACAO;
	}


	public void setSituacao(String SITUACAO){
		this.SITUACAO = SITUACAO;
	}


	public String getObs(){
		return OBS;
	}


	public void setObs(String OBS){
		this.OBS = OBS;
	}


	public Integer getEstabnota(){
		return ESTABNOTA;
	}


	public void setEstabnota(Integer ESTABNOTA){
		this.ESTABNOTA = ESTABNOTA;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
	}


	public Integer getHodometro(){
		return HODOMETRO;
	}


	public void setHodometro(Integer HODOMETRO){
		this.HODOMETRO = HODOMETRO;
	}


	public Integer getHorimetro(){
		return HORIMETRO;
	}


	public void setHorimetro(Integer HORIMETRO){
		this.HORIMETRO = HORIMETRO;
	}

}