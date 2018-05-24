package br.com.primum.modelo.viasoft.U_LINHA_FIXA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="U_LINHA_FIXA")
public class U_linha_fixa  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer U_LINHA_FIXA_ID;

	@Column
	private String DESCRICAO;

	@Column
	private String ORDEM;

	@Column
	private String PERMITE_ADD;

	@Column
	private String PROC_BUSCA;


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


	public String getPermite_add(){
		return PERMITE_ADD;
	}


	public void setPermite_add(String PERMITE_ADD){
		this.PERMITE_ADD = PERMITE_ADD;
	}


	public String getProc_busca(){
		return PROC_BUSCA;
	}


	public void setProc_busca(String PROC_BUSCA){
		this.PROC_BUSCA = PROC_BUSCA;
	}

}