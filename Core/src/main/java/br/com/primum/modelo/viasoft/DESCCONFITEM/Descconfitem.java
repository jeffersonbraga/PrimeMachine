package br.com.primum.modelo.viasoft.DESCCONFITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="DESCCONFITEM")
public class Descconfitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDDESCCONF;

	@Column
	private Integer SEQUENCIA;

	@Column
	private Integer ORDEM;

	@Column
	private Integer PRAZOATE;

	@Column
	private Integer IDDESCITEM;

	@Column
	private Integer IDDESCNOTACONF;

	@Column
	private Integer IDDESCPESSOA;

	@Column
	private Integer DESCONTO;

	@Column
	private Integer DESCPROMO;


	public Integer getIddescconf(){
		return IDDESCCONF;
	}


	public void setIddescconf(Integer IDDESCCONF){
		this.IDDESCCONF = IDDESCCONF;
	}


	public Integer getSequencia(){
		return SEQUENCIA;
	}


	public void setSequencia(Integer SEQUENCIA){
		this.SEQUENCIA = SEQUENCIA;
	}


	public Integer getOrdem(){
		return ORDEM;
	}


	public void setOrdem(Integer ORDEM){
		this.ORDEM = ORDEM;
	}


	public Integer getPrazoate(){
		return PRAZOATE;
	}


	public void setPrazoate(Integer PRAZOATE){
		this.PRAZOATE = PRAZOATE;
	}


	public Integer getIddescitem(){
		return IDDESCITEM;
	}


	public void setIddescitem(Integer IDDESCITEM){
		this.IDDESCITEM = IDDESCITEM;
	}


	public Integer getIddescnotaconf(){
		return IDDESCNOTACONF;
	}


	public void setIddescnotaconf(Integer IDDESCNOTACONF){
		this.IDDESCNOTACONF = IDDESCNOTACONF;
	}


	public Integer getIddescpessoa(){
		return IDDESCPESSOA;
	}


	public void setIddescpessoa(Integer IDDESCPESSOA){
		this.IDDESCPESSOA = IDDESCPESSOA;
	}


	public Integer getDesconto(){
		return DESCONTO;
	}


	public void setDesconto(Integer DESCONTO){
		this.DESCONTO = DESCONTO;
	}


	public Integer getDescpromo(){
		return DESCPROMO;
	}


	public void setDescpromo(Integer DESCPROMO){
		this.DESCPROMO = DESCPROMO;
	}

}