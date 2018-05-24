package br.com.primum.modelo.tipo_notificacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="tipo_notificacao")
public class Tipo_notificacao {

	@Id
	@Column
	private Integer cd_tipo_notificacao;

	@Column
	private String ds_tipo_notificacao;

	@Column
	private String ds_caminho_classe;

	public Integer getCd_tipo_notificacao() {
		return cd_tipo_notificacao;
	}

	public void setCd_tipo_notificacao(Integer cd_tipo_notificacao) {
		this.cd_tipo_notificacao = cd_tipo_notificacao;
	}

	public String getDs_tipo_notificacao() {
		return ds_tipo_notificacao;
	}

	public void setDs_tipo_notificacao(String ds_tipo_notificacao) {
		this.ds_tipo_notificacao = ds_tipo_notificacao;
	}

	public String getDs_caminho_classe() {
		return ds_caminho_classe;
	}

	public void setDs_caminho_classe(String ds_caminho_classe) {
		this.ds_caminho_classe = ds_caminho_classe;
	}
}