package br.com.primum.modelo.tabelafixa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="tabelafixa")
public class Tabelafixa {

	@Id
	private Integer cd_tabelafixa;
	
	@Column
	private Integer cd_tabelaorigem;
	
	@Column
	private String caminho;

	public Integer getCd_tabelafixa() {
		return cd_tabelafixa;
	}

	public void setCd_tabelafixa(Integer cd_tabelafixa) {
		this.cd_tabelafixa = cd_tabelafixa;
	}

	public Integer getCd_tabelaorigem() {
		return cd_tabelaorigem;
	}

	public void setCd_tabelaorigem(Integer cd_tabelaorigem) {
		this.cd_tabelaorigem = cd_tabelaorigem;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
	

}
