package br.com.primum.modelo.viasoft.DESCITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="DESCITEM")
public class Descitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDDESCITEM;

	@Column
	private String DESCRICAO;


	public Integer getIddescitem(){
		return IDDESCITEM;
	}


	public void setIddescitem(Integer IDDESCITEM){
		this.IDDESCITEM = IDDESCITEM;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}