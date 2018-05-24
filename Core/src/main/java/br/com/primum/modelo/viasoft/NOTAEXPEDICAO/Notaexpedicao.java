package br.com.primum.modelo.viasoft.NOTAEXPEDICAO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NOTAEXPEDICAO")
public class Notaexpedicao  implements Serializable {

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
	private Integer SEQEXP;

	@Column
	private Integer QTDERETIRA;

	@Column
	private Integer QTDEENTREGA;

	@Column
	private String ENTREGUE;

	@Column
	private String USERID;

	@Column
	private String RECEBEDOR;

	@Column
	private String NROCARTAO;

	@Column
	private Integer IDLOCALRETIRADA;

	@Column
	private Integer ESTABBX;

	@Column
	private Integer IDNOTAREF;

	@Column
	private String COBNAENT;

	@Column
	private String NECESASSINAT;

	@Column
	private String ALOCAR;

	@Column
	private Integer QTDEINFORMADA;

	@Column
	private Integer IDLOCALENTREGA;

	@Column
	private String TRANSFERIDO;

	@Column
	private String RECEBIDO;

	@Column
	private Integer IDTRANSFRECEXP;

	@Column
	private String USERCONFERE;

	@Column
	private Integer GRUPOROTEIRO;

	@Column
	private Integer SEQROTEIRO;

	@Column
	private String IMPIMEDIATA;


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


	public Integer getQtderetira(){
		return QTDERETIRA;
	}


	public void setQtderetira(Integer QTDERETIRA){
		this.QTDERETIRA = QTDERETIRA;
	}


	public Integer getQtdeentrega(){
		return QTDEENTREGA;
	}


	public void setQtdeentrega(Integer QTDEENTREGA){
		this.QTDEENTREGA = QTDEENTREGA;
	}


	public String getEntregue(){
		return ENTREGUE;
	}


	public void setEntregue(String ENTREGUE){
		this.ENTREGUE = ENTREGUE;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public String getRecebedor(){
		return RECEBEDOR;
	}


	public void setRecebedor(String RECEBEDOR){
		this.RECEBEDOR = RECEBEDOR;
	}


	public String getNrocartao(){
		return NROCARTAO;
	}


	public void setNrocartao(String NROCARTAO){
		this.NROCARTAO = NROCARTAO;
	}


	public Integer getIdlocalretirada(){
		return IDLOCALRETIRADA;
	}


	public void setIdlocalretirada(Integer IDLOCALRETIRADA){
		this.IDLOCALRETIRADA = IDLOCALRETIRADA;
	}


	public Integer getEstabbx(){
		return ESTABBX;
	}


	public void setEstabbx(Integer ESTABBX){
		this.ESTABBX = ESTABBX;
	}


	public Integer getIdnotaref(){
		return IDNOTAREF;
	}


	public void setIdnotaref(Integer IDNOTAREF){
		this.IDNOTAREF = IDNOTAREF;
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


	public String getAlocar(){
		return ALOCAR;
	}


	public void setAlocar(String ALOCAR){
		this.ALOCAR = ALOCAR;
	}


	public Integer getQtdeinformada(){
		return QTDEINFORMADA;
	}


	public void setQtdeinformada(Integer QTDEINFORMADA){
		this.QTDEINFORMADA = QTDEINFORMADA;
	}


	public Integer getIdlocalentrega(){
		return IDLOCALENTREGA;
	}


	public void setIdlocalentrega(Integer IDLOCALENTREGA){
		this.IDLOCALENTREGA = IDLOCALENTREGA;
	}


	public String getTransferido(){
		return TRANSFERIDO;
	}


	public void setTransferido(String TRANSFERIDO){
		this.TRANSFERIDO = TRANSFERIDO;
	}


	public String getRecebido(){
		return RECEBIDO;
	}


	public void setRecebido(String RECEBIDO){
		this.RECEBIDO = RECEBIDO;
	}


	public Integer getIdtransfrecexp(){
		return IDTRANSFRECEXP;
	}


	public void setIdtransfrecexp(Integer IDTRANSFRECEXP){
		this.IDTRANSFRECEXP = IDTRANSFRECEXP;
	}


	public String getUserconfere(){
		return USERCONFERE;
	}


	public void setUserconfere(String USERCONFERE){
		this.USERCONFERE = USERCONFERE;
	}


	public Integer getGruporoteiro(){
		return GRUPOROTEIRO;
	}


	public void setGruporoteiro(Integer GRUPOROTEIRO){
		this.GRUPOROTEIRO = GRUPOROTEIRO;
	}


	public Integer getSeqroteiro(){
		return SEQROTEIRO;
	}


	public void setSeqroteiro(Integer SEQROTEIRO){
		this.SEQROTEIRO = SEQROTEIRO;
	}


	public String getImpimediata(){
		return IMPIMEDIATA;
	}


	public void setImpimediata(String IMPIMEDIATA){
		this.IMPIMEDIATA = IMPIMEDIATA;
	}

}