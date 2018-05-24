package br.com.primum.modelo.viasoft.NOTAMONTA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NOTAMONTA")
public class Notamonta  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

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

	@Column
	private Integer CODIMP;

	@Column
	private String FINALIZA;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
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


	public Integer getCodimp(){
		return CODIMP;
	}


	public void setCodimp(Integer CODIMP){
		this.CODIMP = CODIMP;
	}


	public String getFinaliza(){
		return FINALIZA;
	}


	public void setFinaliza(String FINALIZA){
		this.FINALIZA = FINALIZA;
	}

}