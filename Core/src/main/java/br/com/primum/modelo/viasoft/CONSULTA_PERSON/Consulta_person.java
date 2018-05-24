package br.com.primum.modelo.viasoft.CONSULTA_PERSON;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONSULTA_PERSON")
public class Consulta_person  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ID;


	public Integer getId(){
		return ID;
	}


	public void setId(Integer ID){
		this.ID = ID;
	}

}