package br.com.primum.modelo.viasoft.FILIALPESSOA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FILIALPESSOA")
public class Filialpessoa  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer FILIAL;

	@Column
	private Integer IDPESS;


	public Integer getFilial(){
		return FILIAL;
	}


	public void setFilial(Integer FILIAL){
		this.FILIAL = FILIAL;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}

}