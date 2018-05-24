package br.com.primum.modelo.viasoft.ITEMCORANTE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMCORANTE")
public class Itemcorante  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDCORANTE;

	@Column
	private String IDITEM;

	@Column
	private Integer FATOR;

	@Column
	private Integer IDCOLECAO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdcorante(){
		return IDCORANTE;
	}


	public void setIdcorante(Integer IDCORANTE){
		this.IDCORANTE = IDCORANTE;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getFator(){
		return FATOR;
	}


	public void setFator(Integer FATOR){
		this.FATOR = FATOR;
	}


	public Integer getIdcolecao(){
		return IDCOLECAO;
	}


	public void setIdcolecao(Integer IDCOLECAO){
		this.IDCOLECAO = IDCOLECAO;
	}

}