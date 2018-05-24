package br.com.primum.modelo.viasoft.CONTROLEEXP;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONTROLEEXP")
public class Controleexp  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCONTROLEEXP;

	@Column
	private Integer SEQCONTROLE;

	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer SEQITEM;

	@Column
	private Integer SEQEXP;

	@Column
	private Integer QTDEENTREGAR;

	@Column
	private String RECEBEDOR;

	@Column
	private String IMPRESSO;

	@Column
	private String DEVOLVIDO;

	@Column
	private String MOTIVODEV;

	@Column
	private String COBNAENT;

	@Column
	private String NECESASSINAT;

	@Column
	private Integer QTDEORI;

	@Column
	private Integer QTDEDEVOL;

	@Column
	private Integer QTDERETIRADA;

	@Column
	private String LOTE;

	@Column
	private String USERID;


	public Integer getIdcontroleexp(){
		return IDCONTROLEEXP;
	}


	public void setIdcontroleexp(Integer IDCONTROLEEXP){
		this.IDCONTROLEEXP = IDCONTROLEEXP;
	}


	public Integer getSeqcontrole(){
		return SEQCONTROLE;
	}


	public void setSeqcontrole(Integer SEQCONTROLE){
		this.SEQCONTROLE = SEQCONTROLE;
	}


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


	public Integer getSeqexp(){
		return SEQEXP;
	}


	public void setSeqexp(Integer SEQEXP){
		this.SEQEXP = SEQEXP;
	}


	public Integer getQtdeentregar(){
		return QTDEENTREGAR;
	}


	public void setQtdeentregar(Integer QTDEENTREGAR){
		this.QTDEENTREGAR = QTDEENTREGAR;
	}


	public String getRecebedor(){
		return RECEBEDOR;
	}


	public void setRecebedor(String RECEBEDOR){
		this.RECEBEDOR = RECEBEDOR;
	}


	public String getImpresso(){
		return IMPRESSO;
	}


	public void setImpresso(String IMPRESSO){
		this.IMPRESSO = IMPRESSO;
	}


	public String getDevolvido(){
		return DEVOLVIDO;
	}


	public void setDevolvido(String DEVOLVIDO){
		this.DEVOLVIDO = DEVOLVIDO;
	}


	public String getMotivodev(){
		return MOTIVODEV;
	}


	public void setMotivodev(String MOTIVODEV){
		this.MOTIVODEV = MOTIVODEV;
	}


	public String getCobnaent(){
		return COBNAENT;
	}


	public void setCobnaent(String COBNAENT){
		this.COBNAENT = COBNAENT;
	}


	public String getNecesassinat(){
		return NECESASSINAT;
	}


	public void setNecesassinat(String NECESASSINAT){
		this.NECESASSINAT = NECESASSINAT;
	}


	public Integer getQtdeori(){
		return QTDEORI;
	}


	public void setQtdeori(Integer QTDEORI){
		this.QTDEORI = QTDEORI;
	}


	public Integer getQtdedevol(){
		return QTDEDEVOL;
	}


	public void setQtdedevol(Integer QTDEDEVOL){
		this.QTDEDEVOL = QTDEDEVOL;
	}


	public Integer getQtderetirada(){
		return QTDERETIRADA;
	}


	public void setQtderetirada(Integer QTDERETIRADA){
		this.QTDERETIRADA = QTDERETIRADA;
	}


	public String getLote(){
		return LOTE;
	}


	public void setLote(String LOTE){
		this.LOTE = LOTE;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}

}