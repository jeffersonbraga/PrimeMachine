package br.com.primum.modelo.viasoft.DESCCONF;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="DESCCONF")
public class Descconf  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDDESCCONF;

	@Column
	private String DESCRICAO;

	@Column
	private Integer DESCONTO;

	@Column
	private String VALSUBTOTAL;

	@Column
	private String DISTRSUBTOT;

	@Column
	private String VALDESCDIG;

	@Column
	private String DESCLUCRAT;


	public Integer getIddescconf(){
		return IDDESCCONF;
	}


	public void setIddescconf(Integer IDDESCCONF){
		this.IDDESCCONF = IDDESCCONF;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getDesconto(){
		return DESCONTO;
	}


	public void setDesconto(Integer DESCONTO){
		this.DESCONTO = DESCONTO;
	}


	public String getValsubtotal(){
		return VALSUBTOTAL;
	}


	public void setValsubtotal(String VALSUBTOTAL){
		this.VALSUBTOTAL = VALSUBTOTAL;
	}


	public String getDistrsubtot(){
		return DISTRSUBTOT;
	}


	public void setDistrsubtot(String DISTRSUBTOT){
		this.DISTRSUBTOT = DISTRSUBTOT;
	}


	public String getValdescdig(){
		return VALDESCDIG;
	}


	public void setValdescdig(String VALDESCDIG){
		this.VALDESCDIG = VALDESCDIG;
	}


	public String getDesclucrat(){
		return DESCLUCRAT;
	}


	public void setDesclucrat(String DESCLUCRAT){
		this.DESCLUCRAT = DESCLUCRAT;
	}

}