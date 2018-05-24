package br.com.primum.modelo.endereco_pessoa_juridica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="endereco_pessoa_juridica")
public class Endereco_pessoa_juridica {

	@Id
	@Column
	private Integer cd_endereco_pessoa_juridica;
	
	@Column
	private Integer cd_pessoa_juridica;
	
	@Column
	private Integer cd_endereco;

	public Integer getCd_endereco_pessoa_juridica() {
		return cd_endereco_pessoa_juridica;
	}

	public void setCd_endereco_pessoa_juridica(Integer cd_endereco_pessoa_juridica) {
		this.cd_endereco_pessoa_juridica = cd_endereco_pessoa_juridica;
	}

	public Integer getCd_pessoa_juridica() {
		return cd_pessoa_juridica;
	}

	public void setCd_pessoa_juridica(Integer cd_pessoa_juridica) {
		this.cd_pessoa_juridica = cd_pessoa_juridica;
	}

	public Integer getCd_endereco() {
		return cd_endereco;
	}

	public void setCd_endereco(Integer cd_endereco) {
		this.cd_endereco = cd_endereco;
	}
}