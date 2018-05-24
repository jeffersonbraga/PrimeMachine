package br.com.primum.modelo.viasoft.CARRINHOMONTA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARRINHOMONTA")
public class Carrinhomonta  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDCARRINHO;

	@Column
	private Integer SEQITEM;

	@Column
	private Integer IDPESS;

	@Column
	private String MONTA;

	@Column
	private String QUALIDADE;

	@Column
	private String MONTOU;

	@Column
	private String OBS;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdcarrinho(){
		return IDCARRINHO;
	}


	public void setIdcarrinho(Integer IDCARRINHO){
		this.IDCARRINHO = IDCARRINHO;
	}


	public Integer getSeqitem(){
		return SEQITEM;
	}


	public void setSeqitem(Integer SEQITEM){
		this.SEQITEM = SEQITEM;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public String getMonta(){
		return MONTA;
	}


	public void setMonta(String MONTA){
		this.MONTA = MONTA;
	}


	public String getQualidade(){
		return QUALIDADE;
	}


	public void setQualidade(String QUALIDADE){
		this.QUALIDADE = QUALIDADE;
	}


	public String getMontou(){
		return MONTOU;
	}


	public void setMontou(String MONTOU){
		this.MONTOU = MONTOU;
	}


	public String getObs(){
		return OBS;
	}


	public void setObs(String OBS){
		this.OBS = OBS;
	}

}