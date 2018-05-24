package br.com.opsocial.ejb.entity.pessoa;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.documento.Documento;
import br.com.opsocial.ejb.entity.endereco.Endereco;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="pessoa", schema="opsocial")
public class Pessoa extends Persistent {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pessoa_seq")
	private Integer cd_pessoa;

	@Column
	private String nm_pessoa;

	@Column
	private String nr_registro;

	@Column
	private String nr_cpf;

	@Column
	private String ds_email;

	@Column
	private String nr_telefone;

	@JoinColumn(name = "cd_endereco", referencedColumnName = "cd_endereco")
	private Endereco endereco;

	@JoinColumn(name = "cd_pessoa", referencedColumnName = "cd_pessoa")
	private List<Documento> documentos;

	public Integer getCd_pessoa() {
		return cd_pessoa;
	}

	public String getNm_pessoa() {
		return nm_pessoa;
	}

	public String getNr_registro() {
		return nr_registro;
	}

	public String getNr_cpf() {
		return nr_cpf;
	}

	public String getDs_email() {
		return ds_email;
	}

	public String getNr_telefone() {
		return nr_telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}

	public void setNm_pessoa(String nm_pessoa) {
		this.nm_pessoa = nm_pessoa;
	}

	public void setNr_registro(String nr_registro) {
		this.nr_registro = nr_registro;
	}

	public void setNr_cpf(String nr_cpf) {
		this.nr_cpf = nr_cpf;
	}

	public void setDs_email(String ds_email) {
		this.ds_email = ds_email;
	}

	public void setNr_telefone(String nr_telefone) {
		this.nr_telefone = nr_telefone;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

}