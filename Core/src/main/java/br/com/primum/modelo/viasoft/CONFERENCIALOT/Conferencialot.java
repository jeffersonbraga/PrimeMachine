package br.com.primum.modelo.viasoft.CONFERENCIALOT;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONFERENCIALOT")
public class Conferencialot  implements Serializable {

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
	private String LOTE;

	@Column
	private Integer QUANTIDADE;


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


	public String getLote(){
		return LOTE;
	}


	public void setLote(String LOTE){
		this.LOTE = LOTE;
	}


	public Integer getQuantidade(){
		return QUANTIDADE;
	}


	public void setQuantidade(Integer QUANTIDADE){
		this.QUANTIDADE = QUANTIDADE;
	}

}