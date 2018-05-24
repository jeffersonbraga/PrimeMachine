package br.com.primum.modelo.viasoft.SOLICITACOMPITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="SOLICITACOMPITEM")
public class Solicitacompitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDSOLICITACAO;

	@Column
	private String IDITEM;

	@Column
	private Integer IDPESS;

	@Column
	private String OBS;

	@Column
	private Integer IDCLIENTE;

	@Column
	private String ORIGEM;

	@Column
	private Integer QUANTIDADE;

	@Column
	private Integer SEQITEM;

	@Column
	private String STATUS;

	@Column
	private String USERID;

	@Column
	private Integer IDPEDIDO;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdsolicitacao(){
		return IDSOLICITACAO;
	}


	public void setIdsolicitacao(Integer IDSOLICITACAO){
		this.IDSOLICITACAO = IDSOLICITACAO;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public String getObs(){
		return OBS;
	}


	public void setObs(String OBS){
		this.OBS = OBS;
	}


	public Integer getIdcliente(){
		return IDCLIENTE;
	}


	public void setIdcliente(Integer IDCLIENTE){
		this.IDCLIENTE = IDCLIENTE;
	}


	public String getOrigem(){
		return ORIGEM;
	}


	public void setOrigem(String ORIGEM){
		this.ORIGEM = ORIGEM;
	}


	public Integer getQuantidade(){
		return QUANTIDADE;
	}


	public void setQuantidade(Integer QUANTIDADE){
		this.QUANTIDADE = QUANTIDADE;
	}


	public Integer getSeqitem(){
		return SEQITEM;
	}


	public void setSeqitem(Integer SEQITEM){
		this.SEQITEM = SEQITEM;
	}


	public String getStatus(){
		return STATUS;
	}


	public void setStatus(String STATUS){
		this.STATUS = STATUS;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}


	public Integer getIdpedido(){
		return IDPEDIDO;
	}


	public void setIdpedido(Integer IDPEDIDO){
		this.IDPEDIDO = IDPEDIDO;
	}

}