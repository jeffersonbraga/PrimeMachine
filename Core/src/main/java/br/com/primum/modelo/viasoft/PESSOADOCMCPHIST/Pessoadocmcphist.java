package br.com.primum.modelo.viasoft.PESSOADOCMCPHIST;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOADOCMCPHIST")
public class Pessoadocmcphist  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer EMPRESA;

	@Column
	private Integer IDPESS;

	@Column
	private Integer SEQHIST;

	@Column
	private Integer IDCONCEITO;

	@Column
	private Integer IDCONCEITOANT;

	@Column
	private String USERID;

	@Column
	private Integer ESTAB;


	public Integer getEmpresa(){
		return EMPRESA;
	}


	public void setEmpresa(Integer EMPRESA){
		this.EMPRESA = EMPRESA;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getSeqhist(){
		return SEQHIST;
	}


	public void setSeqhist(Integer SEQHIST){
		this.SEQHIST = SEQHIST;
	}


	public Integer getIdconceito(){
		return IDCONCEITO;
	}


	public void setIdconceito(Integer IDCONCEITO){
		this.IDCONCEITO = IDCONCEITO;
	}


	public Integer getIdconceitoant(){
		return IDCONCEITOANT;
	}


	public void setIdconceitoant(Integer IDCONCEITOANT){
		this.IDCONCEITOANT = IDCONCEITOANT;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}

}