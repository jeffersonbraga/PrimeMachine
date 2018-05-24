package br.com.primum.modelo.viasoft.ITEMCARACTER;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMCARACTER")
public class Itemcaracter  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private Integer IDCARACTER;

	@Column
	private String OPCAO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getIdcaracter(){
		return IDCARACTER;
	}


	public void setIdcaracter(Integer IDCARACTER){
		this.IDCARACTER = IDCARACTER;
	}


	public String getOpcao(){
		return OPCAO;
	}


	public void setOpcao(String OPCAO){
		this.OPCAO = OPCAO;
	}

}