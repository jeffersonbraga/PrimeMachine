package br.com.primum.modelo.viasoft.DESCPESSOA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="DESCPESSOA")
public class Descpessoa  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDDESCPESSOA;

	@Column
	private String DESCRICAO;


	public Integer getIddescpessoa(){
		return IDDESCPESSOA;
	}


	public void setIddescpessoa(Integer IDDESCPESSOA){
		this.IDDESCPESSOA = IDDESCPESSOA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}