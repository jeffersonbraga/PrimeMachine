package br.com.primum.modelo.viasoft.ITEMBASE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMBASE")
public class Itembase  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDBASE;

	@Column
	private Integer IDEMBTINTA;

	@Column
	private String IDITEM;

	@Column
	private String IDITEMNOTA;

	@Column
	private Integer IDCOLECAO;

	@Column
	private Integer IDPRODUTO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdbase(){
		return IDBASE;
	}


	public void setIdbase(Integer IDBASE){
		this.IDBASE = IDBASE;
	}


	public Integer getIdembtinta(){
		return IDEMBTINTA;
	}


	public void setIdembtinta(Integer IDEMBTINTA){
		this.IDEMBTINTA = IDEMBTINTA;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public String getIditemnota(){
		return IDITEMNOTA;
	}


	public void setIditemnota(String IDITEMNOTA){
		this.IDITEMNOTA = IDITEMNOTA;
	}


	public Integer getIdcolecao(){
		return IDCOLECAO;
	}


	public void setIdcolecao(Integer IDCOLECAO){
		this.IDCOLECAO = IDCOLECAO;
	}


	public Integer getIdproduto(){
		return IDPRODUTO;
	}


	public void setIdproduto(Integer IDPRODUTO){
		this.IDPRODUTO = IDPRODUTO;
	}

}