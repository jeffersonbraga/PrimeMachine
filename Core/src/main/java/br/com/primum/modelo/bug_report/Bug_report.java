package br.com.primum.modelo.bug_report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="bug_report")
public class Bug_report {
	
	@Id
	@Column
	private Integer cd_bug_report;

	@Column
	private Integer cd_programa;
	
	@Column
	private Integer cd_usuario_reportador;
	
	@Column
	private String ds_descricao_bug;

	public Integer getCd_bug_report() {
		return cd_bug_report;
	}

	public void setCd_bug_report(Integer cd_bug_report) {
		this.cd_bug_report = cd_bug_report;
	}

	public Integer getCd_programa() {
		return cd_programa;
	}

	public void setCd_programa(Integer cd_programa) {
		this.cd_programa = cd_programa;
	}

	public Integer getCd_usuario_reportador() {
		return cd_usuario_reportador;
	}

	public void setCd_usuario_reportador(Integer cd_usuario_reportador) {
		this.cd_usuario_reportador = cd_usuario_reportador;
	}

	public String getDs_descricao_bug() {
		return ds_descricao_bug;
	}

	public void setDs_descricao_bug(String ds_descricao_bug) {
		this.ds_descricao_bug = ds_descricao_bug;
	}
}
