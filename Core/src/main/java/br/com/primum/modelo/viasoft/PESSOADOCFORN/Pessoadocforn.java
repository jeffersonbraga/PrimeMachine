package br.com.primum.modelo.viasoft.PESSOADOCFORN;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOADOCFORN")
public class Pessoadocforn  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDPESS;

	@Column
	private Integer IDPESSOADOCFORN;

	@Column
	private Integer FREQCONTATO;

	@Column
	private Integer PRAZOENTREGA;

	@Column
	private Integer PEDMIN;

	@Column
	private String FRETEPAD;

	@Column
	private Integer VALFRETECIF;

	@Column
	private Integer DIAVENC;

	@Column
	private String HOMEPAGEFORN;

	@Column
	private Integer PESSMATRIZ;

	@Column
	private String IDBANCO;

	@Column
	private String AGENCIA;

	@Column
	private String CONTA;

	@Column
	private String CONTATIPO;

	@Column
	private Integer DIACONTATO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getIdpessoadocforn(){
		return IDPESSOADOCFORN;
	}


	public void setIdpessoadocforn(Integer IDPESSOADOCFORN){
		this.IDPESSOADOCFORN = IDPESSOADOCFORN;
	}


	public Integer getFreqcontato(){
		return FREQCONTATO;
	}


	public void setFreqcontato(Integer FREQCONTATO){
		this.FREQCONTATO = FREQCONTATO;
	}


	public Integer getPrazoentrega(){
		return PRAZOENTREGA;
	}


	public void setPrazoentrega(Integer PRAZOENTREGA){
		this.PRAZOENTREGA = PRAZOENTREGA;
	}


	public Integer getPedmin(){
		return PEDMIN;
	}


	public void setPedmin(Integer PEDMIN){
		this.PEDMIN = PEDMIN;
	}


	public String getFretepad(){
		return FRETEPAD;
	}


	public void setFretepad(String FRETEPAD){
		this.FRETEPAD = FRETEPAD;
	}


	public Integer getValfretecif(){
		return VALFRETECIF;
	}


	public void setValfretecif(Integer VALFRETECIF){
		this.VALFRETECIF = VALFRETECIF;
	}


	public Integer getDiavenc(){
		return DIAVENC;
	}


	public void setDiavenc(Integer DIAVENC){
		this.DIAVENC = DIAVENC;
	}


	public String getHomepageforn(){
		return HOMEPAGEFORN;
	}


	public void setHomepageforn(String HOMEPAGEFORN){
		this.HOMEPAGEFORN = HOMEPAGEFORN;
	}


	public Integer getPessmatriz(){
		return PESSMATRIZ;
	}


	public void setPessmatriz(Integer PESSMATRIZ){
		this.PESSMATRIZ = PESSMATRIZ;
	}


	public String getIdbanco(){
		return IDBANCO;
	}


	public void setIdbanco(String IDBANCO){
		this.IDBANCO = IDBANCO;
	}


	public String getAgencia(){
		return AGENCIA;
	}


	public void setAgencia(String AGENCIA){
		this.AGENCIA = AGENCIA;
	}


	public String getConta(){
		return CONTA;
	}


	public void setConta(String CONTA){
		this.CONTA = CONTA;
	}


	public String getContatipo(){
		return CONTATIPO;
	}


	public void setContatipo(String CONTATIPO){
		this.CONTATIPO = CONTATIPO;
	}


	public Integer getDiacontato(){
		return DIACONTATO;
	}


	public void setDiacontato(Integer DIACONTATO){
		this.DIACONTATO = DIACONTATO;
	}

}