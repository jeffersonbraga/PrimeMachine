package br.com.primum.modelo.viasoft.CARRINHOLOTE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARRINHOLOTE")
public class Carrinholote  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDCARRINHO;

	@Column
	private Integer SEQITEM;

	@Column
	private String IDITEM;

	@Column
	private String LOTE;

	@Column
	private Integer QUANTIDADE;

	@Column
	private Integer ESTABLOTE;

	@Column
	private Integer PRECOLOTE;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdcarrinho(){
		return IDCARRINHO;
	}


	public void setIdcarrinho(Integer IDCARRINHO){
		this.IDCARRINHO = IDCARRINHO;
	}


	public Integer getSeqitem(){
		return SEQITEM;
	}


	public void setSeqitem(Integer SEQITEM){
		this.SEQITEM = SEQITEM;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
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


	public Integer getEstablote(){
		return ESTABLOTE;
	}


	public void setEstablote(Integer ESTABLOTE){
		this.ESTABLOTE = ESTABLOTE;
	}


	public Integer getPrecolote(){
		return PRECOLOTE;
	}


	public void setPrecolote(Integer PRECOLOTE){
		this.PRECOLOTE = PRECOLOTE;
	}

}