package br.com.primum.modelo.config_impressao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="config_impressao")
public class Config_impressao {

	@Id
	private Integer cd_config_impressao;

	@Column
	private Integer cd_documento;

	@Column
	private Integer nr_copias_fisicas;

	@Column
	private Integer tp_config_impressao;

	public Integer getCd_config_impressao() {
		return cd_config_impressao;
	}

	public void setCd_config_impressao(Integer cd_config_impressao) {
		this.cd_config_impressao = cd_config_impressao;
	}

	public Integer getCd_documento() {
		return cd_documento;
	}

	public void setCd_documento(Integer cd_documento) {
		this.cd_documento = cd_documento;
	}

	public Integer getNr_copias_fisicas() {
		return nr_copias_fisicas;
	}

	public void setNr_copias_fisicas(Integer nr_copias_fisicas) {
		this.nr_copias_fisicas = nr_copias_fisicas;
	}

	public Integer getTp_config_impressao() {
		return tp_config_impressao;
	}

	public void setTp_config_impressao(Integer tp_config_impressao) {
		this.tp_config_impressao = tp_config_impressao;
	}
}