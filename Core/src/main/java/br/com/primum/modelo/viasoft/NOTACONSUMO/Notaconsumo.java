package br.com.primum.modelo.viasoft.NOTACONSUMO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NOTACONSUMO")
public class Notaconsumo  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer SEQITEM;

	@Column
	private Integer IDCORANTE;

	@Column
	private String IDITEM;

	@Column
	private Integer IDFORMULA;

	@Column
	private Integer QTDE;

	@Column
	private Integer VALORUNIT;

	@Column
	private Integer IDESTOQUELOCAL;

	@Column
	private Integer CUSTOUNIT;

	@Column
	private Integer QTDENOTA;

	@Column
	private Integer FATOR;

	@Column
	private Integer IDEMBTINTA;

	@Column
	private Integer IDCOLECAO;

	@Column
	private Integer CUSTOMEDIO;

	@Column
	private Integer VALORML;

	@Column
	private Integer ESTABBX;


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


	public Integer getSeqitem(){
		return SEQITEM;
	}


	public void setSeqitem(Integer SEQITEM){
		this.SEQITEM = SEQITEM;
	}


	public Integer getIdcorante(){
		return IDCORANTE;
	}


	public void setIdcorante(Integer IDCORANTE){
		this.IDCORANTE = IDCORANTE;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getIdformula(){
		return IDFORMULA;
	}


	public void setIdformula(Integer IDFORMULA){
		this.IDFORMULA = IDFORMULA;
	}


	public Integer getQtde(){
		return QTDE;
	}


	public void setQtde(Integer QTDE){
		this.QTDE = QTDE;
	}


	public Integer getValorunit(){
		return VALORUNIT;
	}


	public void setValorunit(Integer VALORUNIT){
		this.VALORUNIT = VALORUNIT;
	}


	public Integer getIdestoquelocal(){
		return IDESTOQUELOCAL;
	}


	public void setIdestoquelocal(Integer IDESTOQUELOCAL){
		this.IDESTOQUELOCAL = IDESTOQUELOCAL;
	}


	public Integer getCustounit(){
		return CUSTOUNIT;
	}


	public void setCustounit(Integer CUSTOUNIT){
		this.CUSTOUNIT = CUSTOUNIT;
	}


	public Integer getQtdenota(){
		return QTDENOTA;
	}


	public void setQtdenota(Integer QTDENOTA){
		this.QTDENOTA = QTDENOTA;
	}


	public Integer getFator(){
		return FATOR;
	}


	public void setFator(Integer FATOR){
		this.FATOR = FATOR;
	}


	public Integer getIdembtinta(){
		return IDEMBTINTA;
	}


	public void setIdembtinta(Integer IDEMBTINTA){
		this.IDEMBTINTA = IDEMBTINTA;
	}


	public Integer getIdcolecao(){
		return IDCOLECAO;
	}


	public void setIdcolecao(Integer IDCOLECAO){
		this.IDCOLECAO = IDCOLECAO;
	}


	public Integer getCustomedio(){
		return CUSTOMEDIO;
	}


	public void setCustomedio(Integer CUSTOMEDIO){
		this.CUSTOMEDIO = CUSTOMEDIO;
	}


	public Integer getValorml(){
		return VALORML;
	}


	public void setValorml(Integer VALORML){
		this.VALORML = VALORML;
	}


	public Integer getEstabbx(){
		return ESTABBX;
	}


	public void setEstabbx(Integer ESTABBX){
		this.ESTABBX = ESTABBX;
	}

}