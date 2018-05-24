package br.com.primum.modelo.viasoft.ITEMEMP;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMEMP")
public class Itememp  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer EMPRESA;

	@Column
	private Integer ESTABITEM;

	@Column
	private String IDITEM;

	@Column
	private String CODREF;

	@Column
	private Integer MULTIPLOVDA;

	@Column
	private Integer MULTIPLOCOMP;

	@Column
	private Integer VALMAXVENDA;

	@Column
	private String IDITEMCONSUMIDO;

	@Column
	private Integer FATORCONSUMO;

	@Column
	private Integer PERCCONSUMIDO;

	@Column
	private String ACREDESCCONSUMIDO;

	@Column
	private Integer IDTPGARANTIA;

	@Column
	private Integer MULTIPLOCONSUMIDO;

	@Column
	private String PODESERCONSUMIDO;


	public Integer getEmpresa(){
		return EMPRESA;
	}


	public void setEmpresa(Integer EMPRESA){
		this.EMPRESA = EMPRESA;
	}


	public Integer getEstabitem(){
		return ESTABITEM;
	}


	public void setEstabitem(Integer ESTABITEM){
		this.ESTABITEM = ESTABITEM;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public String getCodref(){
		return CODREF;
	}


	public void setCodref(String CODREF){
		this.CODREF = CODREF;
	}


	public Integer getMultiplovda(){
		return MULTIPLOVDA;
	}


	public void setMultiplovda(Integer MULTIPLOVDA){
		this.MULTIPLOVDA = MULTIPLOVDA;
	}


	public Integer getMultiplocomp(){
		return MULTIPLOCOMP;
	}


	public void setMultiplocomp(Integer MULTIPLOCOMP){
		this.MULTIPLOCOMP = MULTIPLOCOMP;
	}


	public Integer getValmaxvenda(){
		return VALMAXVENDA;
	}


	public void setValmaxvenda(Integer VALMAXVENDA){
		this.VALMAXVENDA = VALMAXVENDA;
	}


	public String getIditemconsumido(){
		return IDITEMCONSUMIDO;
	}


	public void setIditemconsumido(String IDITEMCONSUMIDO){
		this.IDITEMCONSUMIDO = IDITEMCONSUMIDO;
	}


	public Integer getFatorconsumo(){
		return FATORCONSUMO;
	}


	public void setFatorconsumo(Integer FATORCONSUMO){
		this.FATORCONSUMO = FATORCONSUMO;
	}


	public Integer getPercconsumido(){
		return PERCCONSUMIDO;
	}


	public void setPercconsumido(Integer PERCCONSUMIDO){
		this.PERCCONSUMIDO = PERCCONSUMIDO;
	}


	public String getAcredescconsumido(){
		return ACREDESCCONSUMIDO;
	}


	public void setAcredescconsumido(String ACREDESCCONSUMIDO){
		this.ACREDESCCONSUMIDO = ACREDESCCONSUMIDO;
	}


	public Integer getIdtpgarantia(){
		return IDTPGARANTIA;
	}


	public void setIdtpgarantia(Integer IDTPGARANTIA){
		this.IDTPGARANTIA = IDTPGARANTIA;
	}


	public Integer getMultiploconsumido(){
		return MULTIPLOCONSUMIDO;
	}


	public void setMultiploconsumido(Integer MULTIPLOCONSUMIDO){
		this.MULTIPLOCONSUMIDO = MULTIPLOCONSUMIDO;
	}


	public String getPodeserconsumido(){
		return PODESERCONSUMIDO;
	}


	public void setPodeserconsumido(String PODESERCONSUMIDO){
		this.PODESERCONSUMIDO = PODESERCONSUMIDO;
	}

}