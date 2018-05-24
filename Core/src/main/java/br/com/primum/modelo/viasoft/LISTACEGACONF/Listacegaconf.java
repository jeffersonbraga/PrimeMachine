package br.com.primum.modelo.viasoft.LISTACEGACONF;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="LISTACEGACONF")
public class Listacegaconf  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDLISTACEGA;

	@Column
	private Integer SEQUENCIA;

	@Column
	private Integer CONTAGEM;

	@Column
	private String IDITEM;

	@Column
	private Integer QUANTIDADE;

	@Column
	private String CONTAGEMOK;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdlistacega(){
		return IDLISTACEGA;
	}


	public void setIdlistacega(Integer IDLISTACEGA){
		this.IDLISTACEGA = IDLISTACEGA;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public Integer getContagem(){
		return CONTAGEM;
	}


	public void setContagem(Integer CONTAGEM){
		this.CONTAGEM = CONTAGEM;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getQuantidade(){
		return QUANTIDADE;
	}


	public void setQuantidade(Integer QUANTIDADE){
		this.QUANTIDADE = QUANTIDADE;
	}


	public String getContagemok(){
		return CONTAGEMOK;
	}


	public void setContagemok(String CONTAGEMOK){
		this.CONTAGEMOK = CONTAGEMOK;
	}

}