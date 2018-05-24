package br.com.primum.modelo.viasoft.LOCALRETIRADA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="LOCALRETIRADA")
public class Localretirada  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDLOCALRETIRADA;

	@Column
	private String DESCRICAO;

	@Column
	private String EXPEDICAO;

	@Column
	private String RETIRA;

	@Column
	private String CONFERE;

	@Column
	private String CONFIRMA;

	@Column
	private String SHARED;

	@Column
	private Integer IDESTOQUELOCAL;

	@Column
	private String COR;

	@Column
	private String GERATXENTREGA;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdlocalretirada(){
		return IDLOCALRETIRADA;
	}


	public void setIdlocalretirada(Integer IDLOCALRETIRADA){
		this.IDLOCALRETIRADA = IDLOCALRETIRADA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public String getExpedicao(){
		return EXPEDICAO;
	}


	public void setExpedicao(String EXPEDICAO){
		this.EXPEDICAO = EXPEDICAO;
	}


	public String getRetira(){
		return RETIRA;
	}


	public void setRetira(String RETIRA){
		this.RETIRA = RETIRA;
	}


	public String getConfere(){
		return CONFERE;
	}


	public void setConfere(String CONFERE){
		this.CONFERE = CONFERE;
	}


	public String getConfirma(){
		return CONFIRMA;
	}


	public void setConfirma(String CONFIRMA){
		this.CONFIRMA = CONFIRMA;
	}


	public String getShared(){
		return SHARED;
	}


	public void setShared(String SHARED){
		this.SHARED = SHARED;
	}


	public Integer getIdestoquelocal(){
		return IDESTOQUELOCAL;
	}


	public void setIdestoquelocal(Integer IDESTOQUELOCAL){
		this.IDESTOQUELOCAL = IDESTOQUELOCAL;
	}


	public String getCor(){
		return COR;
	}


	public void setCor(String COR){
		this.COR = COR;
	}


	public String getGeratxentrega(){
		return GERATXENTREGA;
	}


	public void setGeratxentrega(String GERATXENTREGA){
		this.GERATXENTREGA = GERATXENTREGA;
	}

}