package br.com.primum.modelo.viasoft.CONFEMAILESTAB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONFEMAILESTAB")
public class Confemailestab  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDCONFEMAIL;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdconfemail(){
		return IDCONFEMAIL;
	}


	public void setIdconfemail(Integer IDCONFEMAIL){
		this.IDCONFEMAIL = IDCONFEMAIL;
	}

}