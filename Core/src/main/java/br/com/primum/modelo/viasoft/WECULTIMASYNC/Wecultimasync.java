package br.com.primum.modelo.viasoft.WECULTIMASYNC;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="WECULTIMASYNC")
public class Wecultimasync  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private String TABELA;


	public String getTabela(){
		return TABELA;
	}


	public void setTabela(String TABELA){
		this.TABELA = TABELA;
	}

}