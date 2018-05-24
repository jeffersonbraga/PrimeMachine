package br.com.primum.modelo.viasoft.BALANCO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="BALANCO")
public class Balanco  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDBALANCO;

	@Column
	private Integer IDESTOQUELOCAL;

	@Column
	private Date DATA;

	@Column
	private Date DATAFEC;

	@Column
	private String DESCRICAO;

	@Column
	private Integer IDNOTAENT;

	@Column
	private Integer IDNOTASAI;

	@Column
	private Integer IDESTOQUETIPO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdbalanco(){
		return IDBALANCO;
	}


	public void setIdbalanco(Integer IDBALANCO){
		this.IDBALANCO = IDBALANCO;
	}


	public Integer getIdestoquelocal(){
		return IDESTOQUELOCAL;
	}


	public void setIdestoquelocal(Integer IDESTOQUELOCAL){
		this.IDESTOQUELOCAL = IDESTOQUELOCAL;
	}


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
	}


	public Date getDatafec(){
		return DATAFEC;
	}


	public void setDatafec(Date DATAFEC){
		this.DATAFEC = DATAFEC;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getIdnotaent(){
		return IDNOTAENT;
	}


	public void setIdnotaent(Integer IDNOTAENT){
		this.IDNOTAENT = IDNOTAENT;
	}


	public Integer getIdnotasai(){
		return IDNOTASAI;
	}


	public void setIdnotasai(Integer IDNOTASAI){
		this.IDNOTASAI = IDNOTASAI;
	}


	public Integer getIdestoquetipo(){
		return IDESTOQUETIPO;
	}


	public void setIdestoquetipo(Integer IDESTOQUETIPO){
		this.IDESTOQUETIPO = IDESTOQUETIPO;
	}

}