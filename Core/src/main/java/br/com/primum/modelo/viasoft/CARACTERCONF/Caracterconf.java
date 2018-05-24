package br.com.primum.modelo.viasoft.CARACTERCONF;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARACTERCONF")
public class Caracterconf  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDCARACTERCONF;

	@Column
	private Integer IDCARACTER;

	@Column
	private Integer IDGRUPOITEM;

	@Column
	private String IDSUBGRUPO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdcaracterconf(){
		return IDCARACTERCONF;
	}


	public void setIdcaracterconf(Integer IDCARACTERCONF){
		this.IDCARACTERCONF = IDCARACTERCONF;
	}


	public Integer getIdcaracter(){
		return IDCARACTER;
	}


	public void setIdcaracter(Integer IDCARACTER){
		this.IDCARACTER = IDCARACTER;
	}


	public Integer getIdgrupoitem(){
		return IDGRUPOITEM;
	}


	public void setIdgrupoitem(Integer IDGRUPOITEM){
		this.IDGRUPOITEM = IDGRUPOITEM;
	}


	public String getIdsubgrupo(){
		return IDSUBGRUPO;
	}


	public void setIdsubgrupo(String IDSUBGRUPO){
		this.IDSUBGRUPO = IDSUBGRUPO;
	}

}