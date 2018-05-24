package br.com.primum.modelo.evidencia_helpdesk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="evidencia_helpdesk")
public class Evidencia_helpdesk {

	@Id
	private Integer cd_evidencia_helpdesk;

	@Column
	private Integer cd_helpdesk_ocorrencia;

	@Column
	private String ds_caminho_evidencia;

	@Column
	private String nm_evidencia;

	public Integer getCd_evidencia_helpdesk() {
		return cd_evidencia_helpdesk;
	}

	public void setCd_evidencia_helpdesk(Integer cd_evidencia_helpdesk) {
		this.cd_evidencia_helpdesk = cd_evidencia_helpdesk;
	}

	public Integer getCd_helpdesk_ocorrencia() {
		return cd_helpdesk_ocorrencia;
	}

	public void setCd_helpdesk_ocorrencia(Integer cd_helpdesk_ocorrencia) {
		this.cd_helpdesk_ocorrencia = cd_helpdesk_ocorrencia;
	}

	public String getDs_caminho_evidencia() {
		return ds_caminho_evidencia;
	}

	public void setDs_caminho_evidencia(String ds_caminho_evidencia) {
		this.ds_caminho_evidencia = ds_caminho_evidencia;
	}

	public String getNm_evidencia() {
		return nm_evidencia;
	}

	public void setNm_evidencia(String nm_evidencia) {
		this.nm_evidencia = nm_evidencia;
	}
}