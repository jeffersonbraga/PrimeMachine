package br.com.primum.modelo.viasoft.MOTIVODESC;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="MOTIVODESC")
public class Motivodesc  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDMOTIVODESC;

	@Column
	private String DESCRICAO;


	public Integer getIdmotivodesc(){
		return IDMOTIVODESC;
	}


	public void setIdmotivodesc(Integer IDMOTIVODESC){
		this.IDMOTIVODESC = IDMOTIVODESC;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}