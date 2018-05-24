package br.com.primum.modelo.condicao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="condicao")
public class Condicao {

	@Id
	@Column
	private Integer cd_condicao;

	@Column
	private String nm_condicao;

	@Column
	private String ds_sinal_condicao;
	
	@Column
	private Integer tp_operacao;

	
	public Integer getTp_operacao() {
		return tp_operacao;
	}

	public void setTp_operacao(Integer tp_operacao) {
		this.tp_operacao = tp_operacao;
	}

	public Integer getCd_condicao() {
		return cd_condicao;
	}

	public void setCd_condicao(Integer cd_condicao) {
		this.cd_condicao = cd_condicao;
	}

	public String getNm_condicao() {
		return nm_condicao;
	}

	public void setNm_condicao(String nm_condicao) {
		this.nm_condicao = nm_condicao;
	}

	public String getDs_sinal_condicao() {
		return ds_sinal_condicao;
	}

	public void setDs_sinal_condicao(String ds_sinal_condicao) {
		this.ds_sinal_condicao = ds_sinal_condicao;
	}
}
