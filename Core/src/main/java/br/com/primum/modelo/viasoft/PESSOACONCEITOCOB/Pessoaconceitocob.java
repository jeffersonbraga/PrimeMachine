package br.com.primum.modelo.viasoft.PESSOACONCEITOCOB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOACONCEITOCOB")
public class Pessoaconceitocob  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer EMPRESA;

	@Column
	private Integer IDCONCEITO;

	@Column
	private Integer ESTABCOB;

	@Column
	private String IDBANCO;

	@Column
	private Integer IDCOBCAB;

	@Column
	private Integer IDCOBDET;


	public Integer getEmpresa(){
		return EMPRESA;
	}


	public void setEmpresa(Integer EMPRESA){
		this.EMPRESA = EMPRESA;
	}


	public Integer getIdconceito(){
		return IDCONCEITO;
	}


	public void setIdconceito(Integer IDCONCEITO){
		this.IDCONCEITO = IDCONCEITO;
	}


	public Integer getEstabcob(){
		return ESTABCOB;
	}


	public void setEstabcob(Integer ESTABCOB){
		this.ESTABCOB = ESTABCOB;
	}


	public String getIdbanco(){
		return IDBANCO;
	}


	public void setIdbanco(String IDBANCO){
		this.IDBANCO = IDBANCO;
	}


	public Integer getIdcobcab(){
		return IDCOBCAB;
	}


	public void setIdcobcab(Integer IDCOBCAB){
		this.IDCOBCAB = IDCOBCAB;
	}


	public Integer getIdcobdet(){
		return IDCOBDET;
	}


	public void setIdcobdet(Integer IDCOBDET){
		this.IDCOBDET = IDCOBDET;
	}

}