package br.com.primum.modelo.configuracao_helpdesk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="configuracao_helpdesk")
public class Configuracao_helpdesk {

	@Id
	@Column
	private Integer cd_configuracao_helpdesk;

	@Column
	private Integer cd_colaborador;

	@Column
	private Integer cd_classificacao_helpdesk;
	
	@Column
	private Integer cd_natureza_ocorrencia;
	
	@Column
	private Integer st_recebe_email;

	
	public Integer getCd_configuracao_helpdesk() {
		return cd_configuracao_helpdesk;
	}

	public void setCd_configuracao_helpdesk(Integer cd_configuracao_helpdesk) {
		this.cd_configuracao_helpdesk = cd_configuracao_helpdesk;
	}

	public Integer getCd_colaborador() {
		return cd_colaborador;
	}

	public void setCd_colaborador(Integer cd_colaborador) {
		this.cd_colaborador = cd_colaborador;
	}

	public Integer getCd_classificacao_helpdesk() {
		return cd_classificacao_helpdesk;
	}

	public void setCd_classificacao_helpdesk(Integer cd_classificacao_helpdesk) {
		this.cd_classificacao_helpdesk = cd_classificacao_helpdesk;
	}

	public Integer getCd_natureza_ocorrencia() {
		return cd_natureza_ocorrencia;
	}

	public void setCd_natureza_ocorrencia(Integer cd_natureza_ocorrencia) {
		this.cd_natureza_ocorrencia = cd_natureza_ocorrencia;
	}

	public Integer getSt_recebe_email() {
		return st_recebe_email;
	}

	public void setSt_recebe_email(Integer st_recebe_email) {
		this.st_recebe_email = st_recebe_email;
	}

}
