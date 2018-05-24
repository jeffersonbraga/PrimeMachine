package br.com.primum.modelo.itempedidomaterial;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="itempedidomaterial")
public class ItemPedidoMaterial {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_itempedidomaterial;
	
	@Column
	private Integer cd_empresa;
	
	@Column
	private Integer cd_filial;
	
	@Column
	private Integer cd_pedidomaterial;
	
	@Column
	private Integer cd_insumo;
	
	@Column
	private Float nr_quantidade;
	
	@Column
	private Float nr_saldo;
	
	@Column
	private Integer st_itempedidomaterial;

	public Integer getCd_itempedidomaterial() {
		return cd_itempedidomaterial;
	}

	public void setCd_itempedidomaterial(Integer cd_itempedidomaterial) {
		this.cd_itempedidomaterial = cd_itempedidomaterial;
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

	public Integer getCd_pedidomaterial() {
		return cd_pedidomaterial;
	}

	public void setCd_pedidomaterial(Integer cd_pedidomaterial) {
		this.cd_pedidomaterial = cd_pedidomaterial;
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

	public Float getNr_saldo() {
		return nr_saldo;
	}

	public void setNr_saldo(Float nr_saldo) {
		this.nr_saldo = nr_saldo;
	}

	public Integer getSt_itempedidomaterial() {
		return st_itempedidomaterial;
	}

	public void setSt_itempedidomaterial(Integer st_itempedidomaterial) {
		this.st_itempedidomaterial = st_itempedidomaterial;
	}
}
