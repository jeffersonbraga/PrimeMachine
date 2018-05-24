package br.com.primum.modelo.equipe_cronograma_auditoria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="equipe_cronograma_auditoria")
public class Equipe_cronograma_auditoria {

	@Id
	@Column
	private Integer cd_equipe_cronograma_auditoria;
	
	@Column
	private Integer cd_cronograma_auditoria;
	
	@Column
	private Integer cd_usuario;
	
	@Column
	private String ds_responsabilidade;

	public Integer getCd_equipe_cronograma_auditoria() {
		return cd_equipe_cronograma_auditoria;
	}

	public void setCd_equipe_cronograma_auditoria(
			Integer cd_equipe_cronograma_auditoria) {
		this.cd_equipe_cronograma_auditoria = cd_equipe_cronograma_auditoria;
	}

	public Integer getCd_cronograma_auditoria() {
		return cd_cronograma_auditoria;
	}

	public void setCd_cronograma_auditoria(Integer cd_cronograma_auditoria) {
		this.cd_cronograma_auditoria = cd_cronograma_auditoria;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public String getDs_responsabilidade() {
		return ds_responsabilidade;
	}

	public void setDs_responsabilidade(String ds_responsabilidade) {
		this.ds_responsabilidade = ds_responsabilidade;
	}
}
