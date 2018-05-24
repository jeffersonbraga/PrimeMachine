package br.com.primum.modelo.viasoft.CARRINHOPAGFIN;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARRINHOPAGFIN")
public class Carrinhopagfin  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCARRINHO;

	@Column
	private Integer SEQUENCIA;

	@Column
	private String FORMAFIN;

	@Column
	private String CONTRATO;

	@Column
	private Integer NROPARCELA;

	@Column
	private Date PVENCTO;

	@Column
	private Integer VALORP;


	public Integer getIdcarrinho(){
		return IDCARRINHO;
	}


	public void setIdcarrinho(Integer IDCARRINHO){
		this.IDCARRINHO = IDCARRINHO;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public String getFormafin(){
		return FORMAFIN;
	}


	public void setFormafin(String FORMAFIN){
		this.FORMAFIN = FORMAFIN;
	}


	public String getContrato(){
		return CONTRATO;
	}


	public void setContrato(String CONTRATO){
		this.CONTRATO = CONTRATO;
	}


	public Integer getNroparcela(){
		return NROPARCELA;
	}


	public void setNroparcela(Integer NROPARCELA){
		this.NROPARCELA = NROPARCELA;
	}


	public Date getPvencto(){
		return PVENCTO;
	}


	public void setPvencto(Date PVENCTO){
		this.PVENCTO = PVENCTO;
	}


	public Integer getValorp(){
		return VALORP;
	}


	public void setValorp(Integer VALORP){
		this.VALORP = VALORP;
	}

}