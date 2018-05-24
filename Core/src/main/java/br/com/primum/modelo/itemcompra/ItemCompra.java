package br.com.primum.modelo.itemcompra;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="itemcompra")
public class ItemCompra {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_itempedidocompra;
	
	@Column
	private Integer cd_pedidocompra;
	
	@Column
	private Integer cd_empresa;
	
	@Column
	private Integer cd_filial;
	
	@Column
	private Integer cd_insumo;
	
	@Column
	private Float nr_quantidade;

	public Integer getCd_itempedidocompra() {
		return cd_itempedidocompra;
	}

	public void setCd_itempedidocompra(Integer cd_itempedidocompra) {
		this.cd_itempedidocompra = cd_itempedidocompra;
	}

	public Integer getCd_pedidocompra() {
		return cd_pedidocompra;
	}

	public void setCd_pedidocompra(Integer cd_pedidocompra) {
		this.cd_pedidocompra = cd_pedidocompra;
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

	public Integer getCd_insumo() {
		return cd_insumo;
	}

	public void setCd_insumo(Integer cd_insumo) {
		this.cd_insumo = cd_insumo;
	}

	public Float getNr_quantidade() {
		return nr_quantidade;
	}

	public void setNr_quantidade(Float nr_quantidade) {
		this.nr_quantidade = nr_quantidade;
	}
}
