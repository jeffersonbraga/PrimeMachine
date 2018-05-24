package br.com.primum.modelo.viasoft.ENDTEMP;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ENDTEMP")
public class Endtemp  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDEND;


	public Integer getIdend(){
		return IDEND;
	}


	public void setIdend(Integer IDEND){
		this.IDEND = IDEND;
	}

}