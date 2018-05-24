package br.com.primum.modelo.viasoft.ITEMEMBALAGEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMEMBALAGEM")
public class Itemembalagem  implements Serializable {

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
	private Integer IDEMBALAGEM;

	@Column
	private String EMBPADCOMP;

	@Column
	private String EMBPADVDA;

	@Column
	private Integer PRECO;

	@Column
	private Integer FATORMULT;


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


	public Integer getIdembalagem(){
		return IDEMBALAGEM;
	}


	public void setIdembalagem(Integer IDEMBALAGEM){
		this.IDEMBALAGEM = IDEMBALAGEM;
	}


	public String getEmbpadcomp(){
		return EMBPADCOMP;
	}


	public void setEmbpadcomp(String EMBPADCOMP){
		this.EMBPADCOMP = EMBPADCOMP;
	}


	public String getEmbpadvda(){
		return EMBPADVDA;
	}


	public void setEmbpadvda(String EMBPADVDA){
		this.EMBPADVDA = EMBPADVDA;
	}


	public Integer getPreco(){
		return PRECO;
	}


	public void setPreco(Integer PRECO){
		this.PRECO = PRECO;
	}


	public Integer getFatormult(){
		return FATORMULT;
	}


	public void setFatormult(Integer FATORMULT){
		this.FATORMULT = FATORMULT;
	}

}