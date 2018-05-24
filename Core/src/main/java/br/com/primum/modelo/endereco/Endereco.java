package br.com.primum.modelo.endereco;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="endereco")
public class Endereco {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_endereco;
	
	@Column
	private Integer cd_municipio;
	
	@Column
	private String nm_rua;
	
	@Column
	private String nm_bairro;
	
	@Column
	private String ds_complemento;
	
	@Column
	private String nr_endereco;
	
	@Column
	private String nr_cep;
	
	@Column
	private String nr_telefone;
	
	@Column
	private Integer st_endereco;

	public Integer getCd_endereco() {
		return cd_endereco;
	}

	public void setCd_endereco(Integer cd_endereco) {
		this.cd_endereco = cd_endereco;
	}

	public Integer getCd_municipio() {
		return cd_municipio;
	}

	public void setCd_municipio(Integer cd_municipio) {
		this.cd_municipio = cd_municipio;
	}

	public String getNm_rua() {
		return nm_rua;
	}

	public void setNm_rua(String nm_rua) {
		this.nm_rua = nm_rua;
	}

	public String getNm_bairro() {
		return nm_bairro;
	}

	public void setNm_bairro(String nm_bairro) {
		this.nm_bairro = nm_bairro;
	}

	public String getDs_complemento() {
		return ds_complemento;
	}

	public void setDs_complemento(String ds_complemento) {
		this.ds_complemento = ds_complemento;
	}

	public String getNr_endereco() {
		return nr_endereco;
	}

	public void setNr_endereco(String nr_endereco) {
		this.nr_endereco = nr_endereco;
	}

	public String getNr_cep() {
		return nr_cep;
	}

	public void setNr_cep(String nr_cep) {
		this.nr_cep = nr_cep;
	}

	public String getNr_telefone() {
		return nr_telefone;
	}

	public void setNr_telefone(String nr_telefone) {
		this.nr_telefone = nr_telefone;
	}

	public Integer getSt_endereco() {
		return st_endereco;
	}

	public void setSt_endereco(Integer st_endereco) {
		this.st_endereco = st_endereco;
	}
	
}