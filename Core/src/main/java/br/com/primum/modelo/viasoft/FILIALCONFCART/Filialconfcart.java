package br.com.primum.modelo.viasoft.FILIALCONFCART;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FILIALCONFCART")
public class Filialconfcart  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTACONF;

	@Column
	private String SITUACAO;

	@Column
	private String DESCREDUZ;

	@Column
	private String PERMCONSFIN;

	@Column
	private String PADCALCIMP;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdnotaconf(){
		return IDNOTACONF;
	}


	public void setIdnotaconf(Integer IDNOTACONF){
		this.IDNOTACONF = IDNOTACONF;
	}


	public String getSituacao(){
		return SITUACAO;
	}


	public void setSituacao(String SITUACAO){
		this.SITUACAO = SITUACAO;
	}


	public String getDescreduz(){
		return DESCREDUZ;
	}


	public void setDescreduz(String DESCREDUZ){
		this.DESCREDUZ = DESCREDUZ;
	}


	public String getPermconsfin(){
		return PERMCONSFIN;
	}


	public void setPermconsfin(String PERMCONSFIN){
		this.PERMCONSFIN = PERMCONSFIN;
	}


	public String getPadcalcimp(){
		return PADCALCIMP;
	}


	public void setPadcalcimp(String PADCALCIMP){
		this.PADCALCIMP = PADCALCIMP;
	}

}