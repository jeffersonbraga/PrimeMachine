package br.com.primum.modelo.pedidomaterial;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="pedidomaterial")
public class PedidoMaterial {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_pedidomaterial;
	
	@Column
	private Integer cd_empresa;
	
	@Column
	private Integer cd_filial;
	
	@Column
	private Integer cd_servico;
	
	@Column
	private Integer st_pedidomaterial;
	
	@Column
	private Integer cd_usuario;
	
	@Column
	private String nr_numero;
	
	////@Temporal(value=TemporalType.DATE)
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_pedido;

	public Integer getCd_pedidomaterial() {
		return cd_pedidomaterial;
	}

	public void setCd_pedidomaterial(Integer cd_pedidomaterial) {
		this.cd_pedidomaterial = cd_pedidomaterial;
	}

	public Integer getCd_empresa() {
		return cd_empresa;
	}

	public void setCd_empresa(Integer cd_empresa) {
		this.cd_empresa = cd_empresa;
	}

	public Integer getCd_filial() {
		return cd_filial;
	}

	public void setCd_filial(Integer cd_filial) {
		this.cd_filial = cd_filial;
	}

	public Integer getCd_servico() {
		return cd_servico;
	}

	public void setCd_servico(Integer cd_servico) {
		this.cd_servico = cd_servico;
	}

	public Integer getSt_pedidomaterial() {
		return st_pedidomaterial;
	}

	public void setSt_pedidomaterial(Integer st_pedidomaterial) {
		this.st_pedidomaterial = st_pedidomaterial;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public String getNr_numero() {
		return nr_numero;
	}

	public void setNr_numero(String nr_numero) {
		this.nr_numero = nr_numero;
	}

	public Date getDt_pedido() {
		return dt_pedido;
	}

	public void setDt_pedido(Date dt_pedido) {
		this.dt_pedido = dt_pedido;
	}
}