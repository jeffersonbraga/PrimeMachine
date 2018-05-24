package br.com.primum.modelo.viasoft.FILIALCONFCONFERE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FILIALCONFCONFERE")
public class Filialconfconfere  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTACONF;

	@Column
	private String DESCREDUZ;

	@Column
	private String TPDOC;


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


	public String getDescreduz(){
		return DESCREDUZ;
	}


	public void setDescreduz(String DESCREDUZ){
		this.DESCREDUZ = DESCREDUZ;
	}


	public String getTpdoc(){
		return TPDOC;
	}


	public void setTpdoc(String TPDOC){
		this.TPDOC = TPDOC;
	}

}