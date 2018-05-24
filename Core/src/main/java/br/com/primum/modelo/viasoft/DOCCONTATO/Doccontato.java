package br.com.primum.modelo.viasoft.DOCCONTATO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="DOCCONTATO")
public class Doccontato  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer EMPRESA;

	@Column
	private Integer IDDOCTO;

	@Column
	private Integer SEQCONTATO;

	@Column
	private String ORIGEM;

	@Column
	private String USERID;

	@Column
	private Integer IDSTATUS;


	public Integer getEmpresa(){
		return EMPRESA;
	}


	public void setEmpresa(Integer EMPRESA){
		this.EMPRESA = EMPRESA;
	}


	public Integer getIddocto(){
		return IDDOCTO;
	}


	public void setIddocto(Integer IDDOCTO){
		this.IDDOCTO = IDDOCTO;
	}


	public Integer getSeqcontato(){
		return SEQCONTATO;
	}


	public void setSeqcontato(Integer SEQCONTATO){
		this.SEQCONTATO = SEQCONTATO;
	}


	public String getOrigem(){
		return ORIGEM;
	}


	public void setOrigem(String ORIGEM){
		this.ORIGEM = ORIGEM;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public Integer getIdstatus(){
		return IDSTATUS;
	}


	public void setIdstatus(Integer IDSTATUS){
		this.IDSTATUS = IDSTATUS;
	}

}