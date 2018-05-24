package br.com.primum.modelo.viasoft.ITEMVINC;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMVINC")
public class Itemvinc  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTABO;

	@Column
	private String IDITEMO;

	@Column
	private Integer ESTABD;

	@Column
	private String IDITEMD;


	public Integer getEstabo(){
		return ESTABO;
	}


	public void setEstabo(Integer ESTABO){
		this.ESTABO = ESTABO;
	}


	public String getIditemo(){
		return IDITEMO;
	}


	public void setIditemo(String IDITEMO){
		this.IDITEMO = IDITEMO;
	}


	public Integer getEstabd(){
		return ESTABD;
	}


	public void setEstabd(Integer ESTABD){
		this.ESTABD = ESTABD;
	}


	public String getIditemd(){
		return IDITEMD;
	}


	public void setIditemd(String IDITEMD){
		this.IDITEMD = IDITEMD;
	}

}