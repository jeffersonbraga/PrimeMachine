package br.com.primum.modelo.servico;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="servico")
public class Servico  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	private Integer cd_servico;

	@Column
	private Integer cd_pessoa;

	@Column
	private Integer cd_veiculo;

	@Column
	private Date dt_inicio;

	@Column
	private Date dt_termino;

	@Column
	private String ds_servico;


	public Integer getCd_servico(){
		return cd_servico;
	}


	public void setCd_servico(Integer cd_servico){
		this.cd_servico = cd_servico;
	}


	public Integer getCd_pessoa(){
		return cd_pessoa;
	}


	public void setCd_pessoa(Integer cd_pessoa){
		this.cd_pessoa = cd_pessoa;
	}


	public Integer getCd_veiculo(){
		return cd_veiculo;
	}


	public void setCd_veiculo(Integer cd_veiculo){
		this.cd_veiculo = cd_veiculo;
	}


	public Date getDt_inicio(){
		return dt_inicio;
	}


	public void setDt_inicio(Date dt_inicio){
		this.dt_inicio = dt_inicio;
	}


	public Date getDt_termino(){
		return dt_termino;
	}


	public void setDt_termino(Date dt_termino){
		this.dt_termino = dt_termino;
	}


	public String getDs_servico(){
		return ds_servico;
	}


	public void setDs_servico(String ds_servico){
		this.ds_servico = ds_servico;
	}
}