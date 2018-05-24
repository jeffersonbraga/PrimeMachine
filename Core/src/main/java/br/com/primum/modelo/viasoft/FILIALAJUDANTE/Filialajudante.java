package br.com.primum.modelo.viasoft.FILIALAJUDANTE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FILIALAJUDANTE")
public class Filialajudante  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDFUNCAO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdfuncao(){
		return IDFUNCAO;
	}


	public void setIdfuncao(Integer IDFUNCAO){
		this.IDFUNCAO = IDFUNCAO;
	}

}