package br.com.primum.modelo.viasoft.CONFERENCIADIVLOT;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONFERENCIADIVLOT")
public class Conferenciadivlot  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String IDCONFERENCIA;

	@Column
	private Integer SEQUENCIA;

	@Column
	private String IDITEM;

	@Column
	private String LOTEORIG;

	@Column
	private String LOTEREC;

	@Column
	private Integer VALORORI;

	@Column
	private Integer VALORREC;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getIdconferencia(){
		return IDCONFERENCIA;
	}


	public void setIdconferencia(String IDCONFERENCIA){
		this.IDCONFERENCIA = IDCONFERENCIA;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public String getLoteorig(){
		return LOTEORIG;
	}


	public void setLoteorig(String LOTEORIG){
		this.LOTEORIG = LOTEORIG;
	}


	public String getLoterec(){
		return LOTEREC;
	}


	public void setLoterec(String LOTEREC){
		this.LOTEREC = LOTEREC;
	}


	public Integer getValorori(){
		return VALORORI;
	}


	public void setValorori(Integer VALORORI){
		this.VALORORI = VALORORI;
	}


	public Integer getValorrec(){
		return VALORREC;
	}


	public void setValorrec(Integer VALORREC){
		this.VALORREC = VALORREC;
	}

}