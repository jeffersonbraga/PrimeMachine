package br.com.primum.modelo.pedidocompra;

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
@Table(name="pedidocompra")
public class PedidoCompra {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_pedidocompra;
	
	@Column
	private Integer cd_usuario;
	
	@Column
	private Integer cd_empresa;
	
	@Column
	private Integer cd_filial;
	
	////@Temporal(value=TemporalType.DATE)
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_pedidocompra;
	
	@Column
	private Integer st_pedidocompra;
	
	@Column
	private Integer nr_pedidocompra;

	public Integer getCd_pedidocompra() {
		return cd_pedidocompra;
	}

	public void setCd_pedidocompra(Integer cd_pedidocompra) {
		this.cd_pedidocompra = cd_pedidocompra;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
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

	public Date getDt_pedidocompra() {
		return dt_pedidocompra;
	}

	public void setDt_pedidocompra(Date dt_pedidocompra) {
		this.dt_pedidocompra = dt_pedidocompra;
	}

	public Integer getSt_pedidocompra() {
		return st_pedidocompra;
	}

	public void setSt_pedidocompra(Integer st_pedidocompra) {
		this.st_pedidocompra = st_pedidocompra;
	}

	public Integer getNr_pedidocompra() {
		return nr_pedidocompra;
	}

	public void setNr_pedidocompra(Integer nr_pedidocompra) {
		this.nr_pedidocompra = nr_pedidocompra;
	}
}