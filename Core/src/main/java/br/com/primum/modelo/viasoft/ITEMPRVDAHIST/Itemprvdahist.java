package br.com.primum.modelo.viasoft.ITEMPRVDAHIST;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMPRVDAHIST")
public class Itemprvdahist  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDBANDEIRA;

	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private Integer SEQUENCIA;

	@Column
	private Integer PRECO;

	@Column
	private String USERID;

	@Column
	private Integer PRECOOFF;

	@Column
	private Date PRECODTOFF;

	@Column
	private Integer MARGEM;

	@Column
	private String ATPERCENTUAIS;


	public Integer getIdbandeira(){
		return IDBANDEIRA;
	}


	public void setIdbandeira(Integer IDBANDEIRA){
		this.IDBANDEIRA = IDBANDEIRA;
	}


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


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public Integer getPreco(){
		return PRECO;
	}


	public void setPreco(Integer PRECO){
		this.PRECO = PRECO;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public Integer getPrecooff(){
		return PRECOOFF;
	}


	public void setPrecooff(Integer PRECOOFF){
		this.PRECOOFF = PRECOOFF;
	}


	public Date getPrecodtoff(){
		return PRECODTOFF;
	}


	public void setPrecodtoff(Date PRECODTOFF){
		this.PRECODTOFF = PRECODTOFF;
	}


	public Integer getMargem(){
		return MARGEM;
	}


	public void setMargem(Integer MARGEM){
		this.MARGEM = MARGEM;
	}


	public String getAtpercentuais(){
		return ATPERCENTUAIS;
	}


	public void setAtpercentuais(String ATPERCENTUAIS){
		this.ATPERCENTUAIS = ATPERCENTUAIS;
	}

}