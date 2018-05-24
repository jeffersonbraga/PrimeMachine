package br.com.primum.modelo.pessoa_juridica;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.endereco_pessoa_juridica.Endereco_pessoa_juridica;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="pessoa_juridica")
public class Pessoa_juridica {

	@Id
	@Column
	private Integer cd_pessoa_juridica;
	
	@Column
	private String nm_pessoa_juridica;
	
	@Column
	private String nr_cnpj;
	
	@Column
	private String nm_empresarial;
	
	////@Temporal(value=TemporalType.DATE)
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_abertura;
	
	@Column
	private String nm_fantasia;
	
	@Column
	private Integer st_empresarial;
	
	@Column
	private String nr_telefone;
	
	@Column
	private String ds_email;
	
	@Column
	private String nm_contato;
	
	@Column
	private String ds_site;
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.endereco_pessoa_juridica.Endereco_pessoa_juridica", persistir=true, campoBuscar="cd_pessoa_juridica")
	@Transient
	private List<Endereco_pessoa_juridica> listaEnderecos;

	public Integer getCd_pessoa_juridica() {
		return cd_pessoa_juridica;
	}

	public void setCd_pessoa_juridica(Integer cd_pessoa_juridica) {
		this.cd_pessoa_juridica = cd_pessoa_juridica;
	}

	public String getNm_pessoa_juridica() {
		return nm_pessoa_juridica;
	}

	public void setNm_pessoa_juridica(String nm_pessoa_juridica) {
		this.nm_pessoa_juridica = nm_pessoa_juridica;
	}

	public String getNr_cnpj() {
		return nr_cnpj;
	}

	public void setNr_cnpj(String nr_cnpj) {
		this.nr_cnpj = nr_cnpj;
	}

	public String getNm_empresarial() {
		return nm_empresarial;
	}

	public void setNm_empresarial(String nm_empresarial) {
		this.nm_empresarial = nm_empresarial;
	}

	public Date getDt_abertura() {
		return dt_abertura;
	}

	public void setDt_abertura(Date dt_abertura) {
		this.dt_abertura = dt_abertura;
	}

	public String getNm_fantasia() {
		return nm_fantasia;
	}

	public void setNm_fantasia(String nm_fantasia) {
		this.nm_fantasia = nm_fantasia;
	}

	public Integer getSt_empresarial() {
		return st_empresarial;
	}

	public void setSt_empresarial(Integer st_empresarial) {
		this.st_empresarial = st_empresarial;
	}

	public String getNr_telefone() {
		return nr_telefone;
	}

	public void setNr_telefone(String nr_telefone) {
		this.nr_telefone = nr_telefone;
	}

	public String getDs_email() {
		return ds_email;
	}

	public void setDs_email(String ds_email) {
		this.ds_email = ds_email;
	}

	public String getNm_contato() {
		return nm_contato;
	}

	public void setNm_contato(String nm_contato) {
		this.nm_contato = nm_contato;
	}

	public String getDs_site() {
		return ds_site;
	}

	public void setDs_site(String ds_site) {
		this.ds_site = ds_site;
	}

	public List<Endereco_pessoa_juridica> getListaEnderecos() {
		return listaEnderecos;
	}

	public void setListaEnderecos(List<Endereco_pessoa_juridica> listaEnderecos) {
		this.listaEnderecos = listaEnderecos;
	}
}