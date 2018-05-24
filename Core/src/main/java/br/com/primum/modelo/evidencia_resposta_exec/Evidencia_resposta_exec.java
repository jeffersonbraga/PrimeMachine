package br.com.primum.modelo.evidencia_resposta_exec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="evidencia_resposta_exec")
public class Evidencia_resposta_exec {

	@Id
	@Column
	private Integer cd_evidencia_resposta_exec;

	@Column
	private Integer cd_resposta_exec_formulario;
	
	@Column
	private String  ds_caminho;
	

	public Integer getCd_evidencia_resposta_exec() {
		return cd_evidencia_resposta_exec;
	}

	public void setCd_evidencia_resposta_exec(Integer cd_evidencia_resposta_exec) {
		this.cd_evidencia_resposta_exec = cd_evidencia_resposta_exec;
	}

	public Integer getCd_resposta_exec_formulario() {
		return cd_resposta_exec_formulario;
	}

	public void setCd_resposta_exec_formulario(Integer cd_resposta_exec_formulario) {
		this.cd_resposta_exec_formulario = cd_resposta_exec_formulario;
	}

	public String getDs_caminho() {
		return ds_caminho;
	}

	public void setDs_caminho(String ds_caminho) {
		this.ds_caminho = ds_caminho;
	}
	
}
