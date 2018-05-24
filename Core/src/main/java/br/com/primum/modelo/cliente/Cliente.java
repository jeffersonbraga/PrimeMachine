package br.com.primum.modelo.cliente;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="cliente")
public class Cliente {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_cliente;
	
//	@Column
//	private Integer cd_pessoa;
//	
//	@Column
//	private String nm_fantasia;
//	
//	@Column
//	private String nm_razaosocial;
//	
//	@Column
//	private Integer st_cliente;
	
	@Column
	private String nm_cliente;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_validade;
	
	@Column
	private String ds_email;
	
	@Column
	private String nr_cnpj;
	
	@Column
	private String nm_schema;

	@Column
	private String ds_token;
	
	
	
	public String getDs_token() {
		return ds_token;
	}

	public void setDs_token(String ds_token) {
		this.ds_token = ds_token;
	}

	public String getNm_cliente() {
		return nm_cliente;
	}

	public void setNm_cliente(String nm_cliente) {
		this.nm_cliente = nm_cliente;
	}

	public Date getDt_validade() {
		return dt_validade;
	}

	public void setDt_validade(Date dt_validade) {
		this.dt_validade = dt_validade;
	}

	public String getDs_email() {
		return ds_email;
	}

	public void setDs_email(String ds_email) {
		this.ds_email = ds_email;
	}

	public String getNm_schema() {
		return nm_schema;
	}

	public void setNm_schema(String nm_schema) {
		this.nm_schema = nm_schema;
	}

	public Integer getCd_cliente() {
		return cd_cliente;
	}

	public void setCd_cliente(Integer cd_cliente) {
		this.cd_cliente = cd_cliente;
	}

//	public Integer getCd_pessoa() {
//		return cd_pessoa;
//	}
//
//	public void setCd_pessoa(Integer cd_pessoa) {
//		this.cd_pessoa = cd_pessoa;
//	}
//
//	public String getNm_fantasia() {
//		return nm_fantasia;
//	}
//
//	public void setNm_fantasia(String nm_fantasia) {
//		this.nm_fantasia = nm_fantasia;
//	}
//
//	public String getNm_razaosocial() {
//		return nm_razaosocial;
//	}
//
//	public void setNm_razaosocial(String nm_razaosocial) {
//		this.nm_razaosocial = nm_razaosocial;
//	}
//
//	public Integer getSt_cliente() {
//		return st_cliente;
//	}
//
//	public void setSt_cliente(Integer st_cliente) {
//		this.st_cliente = st_cliente;
//	}

	public String getNr_cnpj() {
		return nr_cnpj;
	}

	public void setNr_cnpj(String nr_cnpj) {
		this.nr_cnpj = nr_cnpj;
	}
}
