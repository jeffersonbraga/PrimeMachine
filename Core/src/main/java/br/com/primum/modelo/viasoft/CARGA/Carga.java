package br.com.primum.modelo.viasoft.CARGA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARGA")
public class Carga  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Column
	private String CONFERENTE;

	@Column
	private String DESCCARGA;

	@Column
	private String AJUDANTES;

	@Id
	@Column
	private Integer IDCARGA;

	@Column
	private Integer ESTAB;

	@Column
	private String USERID;

	@Column
	private String PLACA;

	@Column
	private Integer IDMOTORISTA;

	@Column
	private Integer KMINICIAL;

	@Column
	private Integer KMFINAL;

	@Column
	private String SITUACAO;


	public String getConferente(){
		return CONFERENTE;
	}


	public void setConferente(String CONFERENTE){
		this.CONFERENTE = CONFERENTE;
	}


	public String getDesccarga(){
		return DESCCARGA;
	}


	public void setDesccarga(String DESCCARGA){
		this.DESCCARGA = DESCCARGA;
	}


	public String getAjudantes(){
		return AJUDANTES;
	}


	public void setAjudantes(String AJUDANTES){
		this.AJUDANTES = AJUDANTES;
	}


	public Integer getIdcarga(){
		return IDCARGA;
	}


	public void setIdcarga(Integer IDCARGA){
		this.IDCARGA = IDCARGA;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
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


	public Integer getKminicial(){
		return KMINICIAL;
	}


	public void setKminicial(Integer KMINICIAL){
		this.KMINICIAL = KMINICIAL;
	}


	public Integer getKmfinal(){
		return KMFINAL;
	}


	public void setKmfinal(Integer KMFINAL){
		this.KMFINAL = KMFINAL;
	}


	public String getSituacao(){
		return SITUACAO;
	}


	public void setSituacao(String SITUACAO){
		this.SITUACAO = SITUACAO;
	}

}