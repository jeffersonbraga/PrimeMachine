package br.com.opsocial.ejb.entity.documento;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="documento", schema="opsocial")
public class Documento extends Persistent {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "documento_seq")
	private Integer cd_documento;

	@Column
	private Integer cd_pessoa;

	@Column
	private Integer tp_documento;

	@Column
	private String ds_numero_documento;

	@Column
	private Date dt_validade;

	public Integer getCd_documento() {
		return cd_documento;
	}

	public Integer getCd_pessoa() {
		return cd_pessoa;
	}

	public Integer getTp_documento() {
		return tp_documento;
	}

	public String getDs_numero_documento() {
		return ds_numero_documento;
	}

	public Date getDt_validade() {
		return dt_validade;
	}

	public void setCd_documento(Integer cd_documento) {
		this.cd_documento = cd_documento;
	}

	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}

	public void setTp_documento(Integer tp_documento) {
		this.tp_documento = tp_documento;
	}

	public void setDs_numero_documento(String ds_numero_documento) {
		this.ds_numero_documento = ds_numero_documento;
	}

	public void setDt_validade(Date dt_validade) {
		this.dt_validade = dt_validade;
	}
}