package br.com.primum.modelo.viasoft.MOTIVOCANC;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="MOTIVOCANC")
public class Motivocanc  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDMOTIVOCANC;

	@Column
	private String DESCRICAO;


	public Integer getIdmotivocanc(){
		return IDMOTIVOCANC;
	}


	public void setIdmotivocanc(Integer IDMOTIVOCANC){
		this.IDMOTIVOCANC = IDMOTIVOCANC;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}

}