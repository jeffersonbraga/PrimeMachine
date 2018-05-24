package br.com.primum.modelo.viasoft.CONFERENCIAITE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONFERENCIAITE")
public class Conferenciaite  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String IDCONFERENCIA;

	@Column
	private String IDITEM;

	@Column
	private Integer SEQUENCIA;

	@Column
	private Integer QUANTIDADE;

	@Column
	private Integer IDAVARIACAO;

	@Column
	private String FINALIZADO;


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


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public Integer getQuantidade(){
		return QUANTIDADE;
	}


	public void setQuantidade(Integer QUANTIDADE){
		this.QUANTIDADE = QUANTIDADE;
	}


	public Integer getIdavariacao(){
		return IDAVARIACAO;
	}


	public void setIdavariacao(Integer IDAVARIACAO){
		this.IDAVARIACAO = IDAVARIACAO;
	}


	public String getFinalizado(){
		return FINALIZADO;
	}


	public void setFinalizado(String FINALIZADO){
		this.FINALIZADO = FINALIZADO;
	}

}