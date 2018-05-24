package br.com.primum.modelo.resposta_auditoria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="resposta_auditoria")
public class Resposta_auditoria {
	
	@Id
	@Column
	private Integer cd_resposta_auditoria;
	
	@Column
	private Integer cd_auditoria;
	
	@Column
	private String ds_resposta_auditoria;
	
	@Column
	private Integer tp_resposta_auditoria;
	
	@Column
	private Integer vl_resposta;

	public Integer getCd_resposta_auditoria() {
		return cd_resposta_auditoria;
	}

	public void setCd_resposta_auditoria(Integer cd_resposta_auditoria) {
		this.cd_resposta_auditoria = cd_resposta_auditoria;
	}

	public Integer getCd_auditoria() {
		return cd_auditoria;
	}

	public void setCd_auditoria(Integer cd_auditoria) {
		this.cd_auditoria = cd_auditoria;
	}

	public String getDs_resposta_auditoria() {
		return ds_resposta_auditoria;
	}

	public void setDs_resposta_auditoria(String ds_resposta_auditoria) {
		this.ds_resposta_auditoria = ds_resposta_auditoria;
	}

	public Integer getTp_resposta_auditoria() {
		return tp_resposta_auditoria;
	}

	public void setTp_resposta_auditoria(Integer tp_resposta_auditoria) {
		this.tp_resposta_auditoria = tp_resposta_auditoria;
	}

	public Integer getVl_resposta() {
		return vl_resposta;
	}

	public void setVl_resposta(Integer vl_resposta) {
		this.vl_resposta = vl_resposta;
	}
}