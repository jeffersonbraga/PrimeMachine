package br.com.primum.modelo.viasoft.CONFCLASS;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONFCLASS")
public class Confclass  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDCONFCLASS;

	@Column
	private String TIPO;

	@Column
	private String CLASSE;

	@Column
	private Integer PERCINI;

	@Column
	private Integer PERCFIN;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdconfclass(){
		return IDCONFCLASS;
	}


	public void setIdconfclass(Integer IDCONFCLASS){
		this.IDCONFCLASS = IDCONFCLASS;
	}


	public String getTipo(){
		return TIPO;
	}


	public void setTipo(String TIPO){
		this.TIPO = TIPO;
	}


	public String getClasse(){
		return CLASSE;
	}


	public void setClasse(String CLASSE){
		this.CLASSE = CLASSE;
	}


	public Integer getPercini(){
		return PERCINI;
	}


	public void setPercini(Integer PERCINI){
		this.PERCINI = PERCINI;
	}


	public Integer getPercfin(){
		return PERCFIN;
	}


	public void setPercfin(Integer PERCFIN){
		this.PERCFIN = PERCFIN;
	}

}