package br.com.primum.modelo.ocorrencia_periodicidade;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="ocorrencia_periodicidade")
public class Ocorrencia_periodicidade {
	
	
	@Id
	@Column
	private Integer cd_ocorrencia_periodicidade;
	
	@Column
	//@Temporal(value = TemporalType.TIMESTAMP)
	private Date dt_ocorrencia_periodicidade;
	
	@Column
	//@Temporal(value = TemporalType.TIMESTAMP)
	private Date dt_expirar;
	
	@Column
	private Integer cd_periodicidade;

	
	public Integer getCd_ocorrencia_periodicidade() {
		return cd_ocorrencia_periodicidade;
	}

	public void setCd_ocorrencia_periodicidade(Integer cd_ocorrencia_periodicidade) {
		this.cd_ocorrencia_periodicidade = cd_ocorrencia_periodicidade;
	}

	public Date getDt_ocorrencia_periodicidade() {
		return dt_ocorrencia_periodicidade;
	}

	public void setDt_ocorrencia_periodicidade(Date dt_ocorrencia_periodicidade) {
		this.dt_ocorrencia_periodicidade = dt_ocorrencia_periodicidade;
	}

	public Date getDt_expirar() {
		return dt_expirar;
	}

	public void setDt_expirar(Date dt_expirar) {
		this.dt_expirar = dt_expirar;
	}

	public Integer getCd_periodicidade() {
		return cd_periodicidade;
	}

	public void setCd_periodicidade(Integer cd_periodicidade) {
		this.cd_periodicidade = cd_periodicidade;
	}

}
