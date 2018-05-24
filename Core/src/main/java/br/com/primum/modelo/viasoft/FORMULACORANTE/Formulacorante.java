package br.com.primum.modelo.viasoft.FORMULACORANTE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FORMULACORANTE")
public class Formulacorante  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDFORMULA;

	@Column
	private Integer IDCORANTE;

	@Column
	private Integer SEQCORANTE;

	@Column
	private Integer QTDEOZ1;

	@Column
	private Integer QTDEOZ2;

	@Column
	private Integer QTDEML;

	@Column
	private Integer QTDEGR;

	@Column
	private Integer IDCOLECAO;

	@Column
	private Integer QTDEGOTAS;


	public Integer getIdformula(){
		return IDFORMULA;
	}


	public void setIdformula(Integer IDFORMULA){
		this.IDFORMULA = IDFORMULA;
	}


	public Integer getIdcorante(){
		return IDCORANTE;
	}


	public void setIdcorante(Integer IDCORANTE){
		this.IDCORANTE = IDCORANTE;
	}


	public Integer getSeqcorante(){
		return SEQCORANTE;
	}


	public void setSeqcorante(Integer SEQCORANTE){
		this.SEQCORANTE = SEQCORANTE;
	}


	public Integer getQtdeoz1(){
		return QTDEOZ1;
	}


	public void setQtdeoz1(Integer QTDEOZ1){
		this.QTDEOZ1 = QTDEOZ1;
	}


	public Integer getQtdeoz2(){
		return QTDEOZ2;
	}


	public void setQtdeoz2(Integer QTDEOZ2){
		this.QTDEOZ2 = QTDEOZ2;
	}


	public Integer getQtdeml(){
		return QTDEML;
	}


	public void setQtdeml(Integer QTDEML){
		this.QTDEML = QTDEML;
	}


	public Integer getQtdegr(){
		return QTDEGR;
	}


	public void setQtdegr(Integer QTDEGR){
		this.QTDEGR = QTDEGR;
	}


	public Integer getIdcolecao(){
		return IDCOLECAO;
	}


	public void setIdcolecao(Integer IDCOLECAO){
		this.IDCOLECAO = IDCOLECAO;
	}


	public Integer getQtdegotas(){
		return QTDEGOTAS;
	}


	public void setQtdegotas(Integer QTDEGOTAS){
		this.QTDEGOTAS = QTDEGOTAS;
	}

}