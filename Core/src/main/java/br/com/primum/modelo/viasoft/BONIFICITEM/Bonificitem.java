package br.com.primum.modelo.viasoft.BONIFICITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="BONIFICITEM")
public class Bonificitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDBONIFICACAO;

	@Column
	private String IDBONIFICITEM;

	@Column
	private String IDITEM;

	@Column
	private Integer QUANTIDADE;

	@Column
	private Integer CUSTOUN;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdbonificacao(){
		return IDBONIFICACAO;
	}


	public void setIdbonificacao(Integer IDBONIFICACAO){
		this.IDBONIFICACAO = IDBONIFICACAO;
	}


	public String getIdbonificitem(){
		return IDBONIFICITEM;
	}


	public void setIdbonificitem(String IDBONIFICITEM){
		this.IDBONIFICITEM = IDBONIFICITEM;
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


	public Integer getCustoun(){
		return CUSTOUN;
	}


	public void setCustoun(Integer CUSTOUN){
		this.CUSTOUN = CUSTOUN;
	}

}