package br.com.primum.modelo.viasoft.GRUPOITEMINFOVDA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="GRUPOITEMINFOVDA")
public class Grupoiteminfovda  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDGRUPOITEM;

	@Column
	private Integer IDINFOVENDA;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdgrupoitem(){
		return IDGRUPOITEM;
	}


	public void setIdgrupoitem(Integer IDGRUPOITEM){
		this.IDGRUPOITEM = IDGRUPOITEM;
	}


	public Integer getIdinfovenda(){
		return IDINFOVENDA;
	}


	public void setIdinfovenda(Integer IDINFOVENDA){
		this.IDINFOVENDA = IDINFOVENDA;
	}

}