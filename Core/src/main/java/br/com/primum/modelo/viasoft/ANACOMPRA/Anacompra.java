package br.com.primum.modelo.viasoft.ANACOMPRA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ANACOMPRA")
public class Anacompra  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDPESS;

	@Column
	private String IDITEM;

	@Column
	private String USERID;

	@Column
	private Integer QUANTIDADE;

	@Column
	private String TIPO;

	@Column
	private Integer PRECOPARTIDA;

	@Column
	private Integer ICMSPERCBASE;

	@Column
	private Integer ICMSALIQ;

	@Column
	private Integer COFINSPERCBASE;

	@Column
	private Integer COFINSALIQ;

	@Column
	private Integer IPIPERCBASE;

	@Column
	private Integer IPIALIQ;

	@Column
	private Integer PISPERCBASE;

	@Column
	private Integer PISALIQ;

	@Column
	private String IDOPER;

	@Column
	private Date PREVENTREGA;


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


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public Integer getQuantidade(){
		return QUANTIDADE;
	}


	public void setQuantidade(Integer QUANTIDADE){
		this.QUANTIDADE = QUANTIDADE;
	}


	public String getTipo(){
		return TIPO;
	}


	public void setTipo(String TIPO){
		this.TIPO = TIPO;
	}


	public Integer getPrecopartida(){
		return PRECOPARTIDA;
	}


	public void setPrecopartida(Integer PRECOPARTIDA){
		this.PRECOPARTIDA = PRECOPARTIDA;
	}


	public Integer getIcmspercbase(){
		return ICMSPERCBASE;
	}


	public void setIcmspercbase(Integer ICMSPERCBASE){
		this.ICMSPERCBASE = ICMSPERCBASE;
	}


	public Integer getIcmsaliq(){
		return ICMSALIQ;
	}


	public void setIcmsaliq(Integer ICMSALIQ){
		this.ICMSALIQ = ICMSALIQ;
	}


	public Integer getCofinspercbase(){
		return COFINSPERCBASE;
	}


	public void setCofinspercbase(Integer COFINSPERCBASE){
		this.COFINSPERCBASE = COFINSPERCBASE;
	}


	public Integer getCofinsaliq(){
		return COFINSALIQ;
	}


	public void setCofinsaliq(Integer COFINSALIQ){
		this.COFINSALIQ = COFINSALIQ;
	}


	public Integer getIpipercbase(){
		return IPIPERCBASE;
	}


	public void setIpipercbase(Integer IPIPERCBASE){
		this.IPIPERCBASE = IPIPERCBASE;
	}


	public Integer getIpialiq(){
		return IPIALIQ;
	}


	public void setIpialiq(Integer IPIALIQ){
		this.IPIALIQ = IPIALIQ;
	}


	public Integer getPispercbase(){
		return PISPERCBASE;
	}


	public void setPispercbase(Integer PISPERCBASE){
		this.PISPERCBASE = PISPERCBASE;
	}


	public Integer getPisaliq(){
		return PISALIQ;
	}


	public void setPisaliq(Integer PISALIQ){
		this.PISALIQ = PISALIQ;
	}


	public String getIdoper(){
		return IDOPER;
	}


	public void setIdoper(String IDOPER){
		this.IDOPER = IDOPER;
	}


	public Date getPreventrega(){
		return PREVENTREGA;
	}


	public void setPreventrega(Date PREVENTREGA){
		this.PREVENTREGA = PREVENTREGA;
	}

}