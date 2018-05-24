package br.com.primum.modelo.viasoft.CONFERENCIADIV;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONFERENCIADIV")
public class Conferenciadiv  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String IDCONFERENCIA;

	@Column
	private Integer SEQUENCIA;

	@Column
	private String DOCORIGEM;

	@Column
	private String TIPO;

	@Column
	private String IDITEM;

	@Column
	private Integer VALORORI;

	@Column
	private Integer VALORREC;

	@Column
	private String FINALIZADO;

	@Column
	private String ACAO;

	@Column
	private Integer IDPEDIDOITEM;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getIdconferencia(){
		return IDCONFERENCIA;
	}


	public void setIdconferencia(String IDCONFERENCIA){
		this.IDCONFERENCIA = IDCONFERENCIA;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public String getDocorigem(){
		return DOCORIGEM;
	}


	public void setDocorigem(String DOCORIGEM){
		this.DOCORIGEM = DOCORIGEM;
	}


	public String getTipo(){
		return TIPO;
	}


	public void setTipo(String TIPO){
		this.TIPO = TIPO;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getValorori(){
		return VALORORI;
	}


	public void setValorori(Integer VALORORI){
		this.VALORORI = VALORORI;
	}


	public Integer getValorrec(){
		return VALORREC;
	}


	public void setValorrec(Integer VALORREC){
		this.VALORREC = VALORREC;
	}


	public String getFinalizado(){
		return FINALIZADO;
	}


	public void setFinalizado(String FINALIZADO){
		this.FINALIZADO = FINALIZADO;
	}


	public String getAcao(){
		return ACAO;
	}


	public void setAcao(String ACAO){
		this.ACAO = ACAO;
	}


	public Integer getIdpedidoitem(){
		return IDPEDIDOITEM;
	}


	public void setIdpedidoitem(Integer IDPEDIDOITEM){
		this.IDPEDIDOITEM = IDPEDIDOITEM;
	}

}