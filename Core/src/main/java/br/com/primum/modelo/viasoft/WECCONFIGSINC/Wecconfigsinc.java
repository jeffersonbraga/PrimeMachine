package br.com.primum.modelo.viasoft.WECCONFIGSINC;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="WECCONFIGSINC")
public class Wecconfigsinc  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private String TABELA;

	@Column
	private String HORAINICIAL;

	@Column
	private String HORAFINAL;

	@Column
	private Integer SINCRONIZARCADA;

	@Column
	private String SINCAUTOMATICA;

	@Column
	private Integer ESTABEC;


	public String getTabela(){
		return TABELA;
	}


	public void setTabela(String TABELA){
		this.TABELA = TABELA;
	}


	public String getHorainicial(){
		return HORAINICIAL;
	}


	public void setHorainicial(String HORAINICIAL){
		this.HORAINICIAL = HORAINICIAL;
	}


	public String getHorafinal(){
		return HORAFINAL;
	}


	public void setHorafinal(String HORAFINAL){
		this.HORAFINAL = HORAFINAL;
	}


	public Integer getSincronizarcada(){
		return SINCRONIZARCADA;
	}


	public void setSincronizarcada(Integer SINCRONIZARCADA){
		this.SINCRONIZARCADA = SINCRONIZARCADA;
	}


	public String getSincautomatica(){
		return SINCAUTOMATICA;
	}


	public void setSincautomatica(String SINCAUTOMATICA){
		this.SINCAUTOMATICA = SINCAUTOMATICA;
	}


	public Integer getEstabec(){
		return ESTABEC;
	}


	public void setEstabec(Integer ESTABEC){
		this.ESTABEC = ESTABEC;
	}

}