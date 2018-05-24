package br.com.primum.modelo.viasoft.GRUPOITEMCP;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="GRUPOITEMCP")
public class Grupoitemcp  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDGRUPOITEM;

	@Column
	private Integer IDTPGARANTIA;


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


	public Integer getIdtpgarantia(){
		return IDTPGARANTIA;
	}


	public void setIdtpgarantia(Integer IDTPGARANTIA){
		this.IDTPGARANTIA = IDTPGARANTIA;
	}

}