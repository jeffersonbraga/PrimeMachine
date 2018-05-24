package br.com.primum.modelo.profissao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="profissao")
public class Profissao {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_profissao;
	
	@Column
	private String ds_profissao;

	@Column
	public Integer getCd_profissao() {
		return cd_profissao;
	}

	public void setCd_profissao(Integer cd_profissao) {
		this.cd_profissao = cd_profissao;
	}

	public String getDs_profissao() {
		return ds_profissao;
	}

	public void setDs_profissao(String ds_profissao) {
		this.ds_profissao = ds_profissao;
	}
	
}
