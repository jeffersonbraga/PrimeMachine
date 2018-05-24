package br.com.primum.modelo.viasoft.U_BALANCETE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="U_BALANCETE")
public class U_balancete  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer U_BALANCETE_ID;

	@Column
	private Integer IDANALITICA;

	@Column
	private String DESCRICAO;

	@Column
	private String TIPOBUSCA;

	@Column
	private Integer AGRUPARCOM;


	public Integer getU_balancete_id(){
		return U_BALANCETE_ID;
	}


	public void setU_balancete_id(Integer U_BALANCETE_ID){
		this.U_BALANCETE_ID = U_BALANCETE_ID;
	}


	public Integer getIdanalitica(){
		return IDANALITICA;
	}


	public void setIdanalitica(Integer IDANALITICA){
		this.IDANALITICA = IDANALITICA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public String getTipobusca(){
		return TIPOBUSCA;
	}


	public void setTipobusca(String TIPOBUSCA){
		this.TIPOBUSCA = TIPOBUSCA;
	}


	public Integer getAgruparcom(){
		return AGRUPARCOM;
	}


	public void setAgruparcom(Integer AGRUPARCOM){
		this.AGRUPARCOM = AGRUPARCOM;
	}

}