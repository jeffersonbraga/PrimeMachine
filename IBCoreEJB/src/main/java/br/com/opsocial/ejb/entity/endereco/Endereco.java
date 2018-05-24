package br.com.opsocial.ejb.entity.endereco;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="endereco", schema="opsocial")
public class Endereco extends Persistent {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "endereco_seq")
	private Integer cd_endereco;

	@Column
	private String ds_cep;

	@Column
	private String ds_rua;

	@Column
	private String ds_numero;

	@Column
	private String ds_complemento;

	@Column
	private String ds_bairro;

	@Column
	private String ds_cidade;

	@Column
	private String ds_estado;

	@Column
	private String ds_telefone;

	@Column
	private String ds_observacoes;

	public Integer getCd_endereco() {
		return cd_endereco;
	}

	public String getDs_cep() {
		return ds_cep;
	}

	public String getDs_rua() {
		return ds_rua;
	}

	public String getDs_numero() {
		return ds_numero;
	}

	public String getDs_complemento() {
		return ds_complemento;
	}

	public String getDs_bairro() {
		return ds_bairro;
	}

	public String getDs_cidade() {
		return ds_cidade;
	}

	public String getDs_estado() {
		return ds_estado;
	}

	public String getDs_telefone() {
		return ds_telefone;
	}

	public String getDs_observacoes() {
		return ds_observacoes;
	}

	public void setCd_endereco(Integer cd_endereco) {
		this.cd_endereco = cd_endereco;
	}

	public void setDs_cep(String ds_cep) {
		this.ds_cep = ds_cep;
	}

	public void setDs_rua(String ds_rua) {
		this.ds_rua = ds_rua;
	}

	public void setDs_numero(String ds_numero) {
		this.ds_numero = ds_numero;
	}

	public void setDs_complemento(String ds_complemento) {
		this.ds_complemento = ds_complemento;
	}

	public void setDs_bairro(String ds_bairro) {
		this.ds_bairro = ds_bairro;
	}

	public void setDs_cidade(String ds_cidade) {
		this.ds_cidade = ds_cidade;
	}

	public void setDs_estado(String ds_estado) {
		this.ds_estado = ds_estado;
	}

	public void setDs_telefone(String ds_telefone) {
		this.ds_telefone = ds_telefone;
	}

	public void setDs_observacoes(String ds_observacoes) {
		this.ds_observacoes = ds_observacoes;
	}
}