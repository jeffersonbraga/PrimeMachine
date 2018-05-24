package br.com.primum.modelo.pergunta_auditoria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="pergunta_auditoria")
public class Pergunta_auditoria {

	@Id
	@Column
	private Integer cd_pergunta_auditoria;
	
	@Column
	private Integer cd_grupo_auditoria;
	
	@Column
	private String ds_pergunta_auditoria;
	
	@Column
	private Integer nr_ordem_pergunta;
	
	@Column
	private Integer vl_valor_pergunta;

	public Integer getCd_pergunta_auditoria() {
		return cd_pergunta_auditoria;
	}

	public void setCd_pergunta_auditoria(Integer cd_pergunta_auditoria) {
		this.cd_pergunta_auditoria = cd_pergunta_auditoria;
	}

	public Integer getCd_grupo_auditoria() {
		return cd_grupo_auditoria;
	}

	public void setCd_grupo_auditoria(Integer cd_grupo_auditoria) {
		this.cd_grupo_auditoria = cd_grupo_auditoria;
	}

	public String getDs_pergunta_auditoria() {
		return ds_pergunta_auditoria;
	}

	public void setDs_pergunta_auditoria(String ds_pergunta_auditoria) {
		this.ds_pergunta_auditoria = ds_pergunta_auditoria;
	}

	public Integer getNr_ordem_pergunta() {
		return nr_ordem_pergunta;
	}

	public void setNr_ordem_pergunta(Integer nr_ordem_pergunta) {
		this.nr_ordem_pergunta = nr_ordem_pergunta;
	}

	public Integer getVl_valor_pergunta() {
		return vl_valor_pergunta;
	}

	public void setVl_valor_pergunta(Integer vl_valor_pergunta) {
		this.vl_valor_pergunta = vl_valor_pergunta;
	}
}