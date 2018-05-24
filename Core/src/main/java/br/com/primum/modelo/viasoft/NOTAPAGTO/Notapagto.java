package br.com.primum.modelo.viasoft.NOTAPAGTO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NOTAPAGTO")
public class Notapagto  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer SEQUENCIA;

	@Column
	private String NUMDOC;

	@Column
	private String OPERACAO;

	@Column
	private Date DATA;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public String getNumdoc(){
		return NUMDOC;
	}


	public void setNumdoc(String NUMDOC){
		this.NUMDOC = NUMDOC;
	}


	public String getOperacao(){
		return OPERACAO;
	}


	public void setOperacao(String OPERACAO){
		this.OPERACAO = OPERACAO;
	}


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
	}

}