package br.com.primum.modelo.viasoft.ITEMCLASSHIST;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMCLASSHIST")
public class Itemclasshist  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer ESTABITEM;

	@Column
	private String IDITEM;

	@Column
	private Integer SEQUENCIA;

	@Column
	private String CAMPO;

	@Column
	private String CLASSE;

	@Column
	private Date DATA;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getEstabitem(){
		return ESTABITEM;
	}


	public void setEstabitem(Integer ESTABITEM){
		this.ESTABITEM = ESTABITEM;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public String getCampo(){
		return CAMPO;
	}


	public void setCampo(String CAMPO){
		this.CAMPO = CAMPO;
	}


	public String getClasse(){
		return CLASSE;
	}


	public void setClasse(String CLASSE){
		this.CLASSE = CLASSE;
	}


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
	}

}