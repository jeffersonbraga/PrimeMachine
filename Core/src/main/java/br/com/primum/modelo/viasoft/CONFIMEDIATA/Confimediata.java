package br.com.primum.modelo.viasoft.CONFIMEDIATA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONFIMEDIATA")
public class Confimediata  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer SEQITEM;

	@Column
	private Integer QTDINFO;

	@Column
	private Integer IDMOTIENT;

	@Column
	private String MOTENTREGA;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
	}


	public Integer getSeqitem(){
		return SEQITEM;
	}


	public void setSeqitem(Integer SEQITEM){
		this.SEQITEM = SEQITEM;
	}


	public Integer getQtdinfo(){
		return QTDINFO;
	}


	public void setQtdinfo(Integer QTDINFO){
		this.QTDINFO = QTDINFO;
	}


	public Integer getIdmotient(){
		return IDMOTIENT;
	}


	public void setIdmotient(Integer IDMOTIENT){
		this.IDMOTIENT = IDMOTIENT;
	}


	public String getMotentrega(){
		return MOTENTREGA;
	}


	public void setMotentrega(String MOTENTREGA){
		this.MOTENTREGA = MOTENTREGA;
	}

}