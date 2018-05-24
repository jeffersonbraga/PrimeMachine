package br.com.primum.modelo.viasoft.U_LINHA_VARIAVEL;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="U_LINHA_VARIAVEL")
public class U_linha_variavel  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer U_LINHA_VARIAVEL_ID;

	@Column
	private Integer U_LINHA_FIXA_ID;

	@Column
	private String DESCRICAO;

	@Column
	private String ORDEM;


	public Integer getU_linha_variavel_id(){
		return U_LINHA_VARIAVEL_ID;
	}


	public void setU_linha_variavel_id(Integer U_LINHA_VARIAVEL_ID){
		this.U_LINHA_VARIAVEL_ID = U_LINHA_VARIAVEL_ID;
	}


	public Integer getU_linha_fixa_id(){
		return U_LINHA_FIXA_ID;
	}


	public void setU_linha_fixa_id(Integer U_LINHA_FIXA_ID){
		this.U_LINHA_FIXA_ID = U_LINHA_FIXA_ID;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public String getOrdem(){
		return ORDEM;
	}


	public void setOrdem(String ORDEM){
		this.ORDEM = ORDEM;
	}

}