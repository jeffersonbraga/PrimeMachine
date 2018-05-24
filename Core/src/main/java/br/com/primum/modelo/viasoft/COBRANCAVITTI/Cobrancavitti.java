package br.com.primum.modelo.viasoft.COBRANCAVITTI;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="COBRANCAVITTI")
public class Cobrancavitti  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDCOBVITTI;

	@Column
	private Integer ESTABDUP;

	@Column
	private Integer IDDUPREC;

	@Column
	private Integer REMESSA;

	@Column
	private Integer IDPESS;

	@Column
	private Integer IDPORTADORANT;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdcobvitti(){
		return IDCOBVITTI;
	}


	public void setIdcobvitti(Integer IDCOBVITTI){
		this.IDCOBVITTI = IDCOBVITTI;
	}


	public Integer getEstabdup(){
		return ESTABDUP;
	}


	public void setEstabdup(Integer ESTABDUP){
		this.ESTABDUP = ESTABDUP;
	}


	public Integer getIdduprec(){
		return IDDUPREC;
	}


	public void setIdduprec(Integer IDDUPREC){
		this.IDDUPREC = IDDUPREC;
	}


	public Integer getRemessa(){
		return REMESSA;
	}


	public void setRemessa(Integer REMESSA){
		this.REMESSA = REMESSA;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getIdportadorant(){
		return IDPORTADORANT;
	}


	public void setIdportadorant(Integer IDPORTADORANT){
		this.IDPORTADORANT = IDPORTADORANT;
	}

}