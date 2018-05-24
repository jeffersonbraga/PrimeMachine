package br.com.primum.modelo.viasoft.INFOVENDA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="INFOVENDA")
public class Infovenda  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDINFOVENDA;

	@Column
	private String DESCRICAO;

	@Column
	private String OBRIGATORIO;


	public Integer getIdinfovenda(){
		return IDINFOVENDA;
	}


	public void setIdinfovenda(Integer IDINFOVENDA){
		this.IDINFOVENDA = IDINFOVENDA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public String getObrigatorio(){
		return OBRIGATORIO;
	}


	public void setObrigatorio(String OBRIGATORIO){
		this.OBRIGATORIO = OBRIGATORIO;
	}

}