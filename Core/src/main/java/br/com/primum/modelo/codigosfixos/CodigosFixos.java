package br.com.primum.modelo.codigosfixos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="codigosfixos")
public class CodigosFixos {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_codigosfixos;
	
	@Column
	private Integer cd_campo;
	
	@Column
	private Integer vl_valor;
	
	@Column
	private String ds_descricao;
	
	@Column
	private Integer tp_padrao;

	public Integer getCd_codigosfixos() {
		return cd_codigosfixos;
	}

	public void setCd_codigosfixos(Integer cd_codigosfixos) {
		this.cd_codigosfixos = cd_codigosfixos;
	}

	public Integer getCd_campo() {
		return cd_campo;
	}

	public void setCd_campo(Integer cd_campo) {
		this.cd_campo = cd_campo;
	}

	public Integer getVl_valor() {
		return vl_valor;
	}

	public void setVl_valor(Integer vl_valor) {
		this.vl_valor = vl_valor;
	}

	public String getDs_descricao() {
		return ds_descricao;
	}

	public void setDs_descricao(String ds_descricao) {
		this.ds_descricao = ds_descricao;
	}

	public Integer getTp_padrao() {
		return tp_padrao;
	}

	public void setTp_padrao(Integer tp_padrao) {
		this.tp_padrao = tp_padrao;
	}
}