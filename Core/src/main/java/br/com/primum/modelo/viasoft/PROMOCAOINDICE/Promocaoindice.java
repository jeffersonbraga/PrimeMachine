package br.com.primum.modelo.viasoft.PROMOCAOINDICE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PROMOCAOINDICE")
public class Promocaoindice  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDPROMO;

	@Column
	private Integer SEQUENCIA;

	@Column
	private String DESCRICAO;

	@Column
	private Integer TIPOPROMOCAO;

	@Column
	private String IDITEM;

	@Column
	private String IDITEMKIT;

	@Column
	private String BONIFTODOS;

	@Column
	private Integer QTDMIN;

	@Column
	private Integer PERC;

	@Column
	private Integer VALOR;

	@Column
	private Integer VALORMIN;

	@Column
	private Integer IDTIPOPESS;

	@Column
	private String ATIVO;

	@Column
	private Integer QTD;


	public Integer getIdpromo(){
		return IDPROMO;
	}


	public void setIdpromo(Integer IDPROMO){
		this.IDPROMO = IDPROMO;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getTipopromocao(){
		return TIPOPROMOCAO;
	}


	public void setTipopromocao(Integer TIPOPROMOCAO){
		this.TIPOPROMOCAO = TIPOPROMOCAO;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public String getIditemkit(){
		return IDITEMKIT;
	}


	public void setIditemkit(String IDITEMKIT){
		this.IDITEMKIT = IDITEMKIT;
	}


	public String getBoniftodos(){
		return BONIFTODOS;
	}


	public void setBoniftodos(String BONIFTODOS){
		this.BONIFTODOS = BONIFTODOS;
	}


	public Integer getQtdmin(){
		return QTDMIN;
	}


	public void setQtdmin(Integer QTDMIN){
		this.QTDMIN = QTDMIN;
	}


	public Integer getPerc(){
		return PERC;
	}


	public void setPerc(Integer PERC){
		this.PERC = PERC;
	}


	public Integer getValor(){
		return VALOR;
	}


	public void setValor(Integer VALOR){
		this.VALOR = VALOR;
	}


	public Integer getValormin(){
		return VALORMIN;
	}


	public void setValormin(Integer VALORMIN){
		this.VALORMIN = VALORMIN;
	}


	public Integer getIdtipopess(){
		return IDTIPOPESS;
	}


	public void setIdtipopess(Integer IDTIPOPESS){
		this.IDTIPOPESS = IDTIPOPESS;
	}


	public String getAtivo(){
		return ATIVO;
	}


	public void setAtivo(String ATIVO){
		this.ATIVO = ATIVO;
	}


	public Integer getQtd(){
		return QTD;
	}


	public void setQtd(Integer QTD){
		this.QTD = QTD;
	}

}