package br.com.primum.modelo.viasoft.COTACAO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="COTACAO")
public class Cotacao  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDPEDIDO;

	@Column
	private Integer IDPESS;

	@Column
	private Integer FRETE;

	@Column
	private Integer DESCONTO;

	@Column
	private String ENVIADO;

	@Column
	private String OBS;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdpedido(){
		return IDPEDIDO;
	}


	public void setIdpedido(Integer IDPEDIDO){
		this.IDPEDIDO = IDPEDIDO;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getFrete(){
		return FRETE;
	}


	public void setFrete(Integer FRETE){
		this.FRETE = FRETE;
	}


	public Integer getDesconto(){
		return DESCONTO;
	}


	public void setDesconto(Integer DESCONTO){
		this.DESCONTO = DESCONTO;
	}


	public String getEnviado(){
		return ENVIADO;
	}


	public void setEnviado(String ENVIADO){
		this.ENVIADO = ENVIADO;
	}


	public String getObs(){
		return OBS;
	}


	public void setObs(String OBS){
		this.OBS = OBS;
	}

}