package br.com.primum.modelo.viasoft.GRUPOROTEIRO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="GRUPOROTEIRO")
public class Gruporoteiro  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDGRUPO;

	@Column
	private String DESCRICAO;

	@Column
	private Integer ESTAB;


	public Integer getIdgrupo(){
		return IDGRUPO;
	}


	public void setIdgrupo(Integer IDGRUPO){
		this.IDGRUPO = IDGRUPO;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}

}