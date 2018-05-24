package br.com.primum.modelo.viasoft.CONFERENCIACAB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONFERENCIACAB")
public class Conferenciacab  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String IDCONFERENCIA;

	@Column
	private String USERIDINI;

	@Column
	private String USERIDFIN;

	@Column
	private Integer IDPEDIDO;

	@Column
	private Integer IDNOTA;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getIdconferencia(){
		return IDCONFERENCIA;
	}


	public void setIdconferencia(String IDCONFERENCIA){
		this.IDCONFERENCIA = IDCONFERENCIA;
	}


	public String getUseridini(){
		return USERIDINI;
	}


	public void setUseridini(String USERIDINI){
		this.USERIDINI = USERIDINI;
	}


	public String getUseridfin(){
		return USERIDFIN;
	}


	public void setUseridfin(String USERIDFIN){
		this.USERIDFIN = USERIDFIN;
	}


	public Integer getIdpedido(){
		return IDPEDIDO;
	}


	public void setIdpedido(Integer IDPEDIDO){
		this.IDPEDIDO = IDPEDIDO;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
	}

}