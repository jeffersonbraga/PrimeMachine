package br.com.primum.modelo.endereco_pessoa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="endereco_pessoa_juridica")
public class Endereco_pessoa {

	@Id
	@Column
	private Integer cd_endereco_pessoa;
	
	@Column
	private Integer cd_pessoa;
	
	@Column
	private Integer cd_endereco;

	public Integer getCd_endereco_pessoa() {
		return cd_endereco_pessoa;
	}

	public void setCd_endereco_pessoa(Integer cd_endereco_pessoa) {
		this.cd_endereco_pessoa = cd_endereco_pessoa;
	}

	public Integer getCd_pessoa() {
		return cd_pessoa;
	}

	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}

	public Integer getCd_endereco() {
		return cd_endereco;
	}

	public void setCd_endereco(Integer cd_endereco) {
		this.cd_endereco = cd_endereco;
	}
}