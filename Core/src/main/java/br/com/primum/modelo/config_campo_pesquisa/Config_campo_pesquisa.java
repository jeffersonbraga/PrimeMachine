package br.com.primum.modelo.config_campo_pesquisa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="config_campo_pesquisa")
public class Config_campo_pesquisa {

	@Id
	@Column
	private Integer cd_config_campo_pesquisa;

	@Column
	private Integer cd_config_pesquisa;
	
	@Column
	private Integer cd_campo;
	
	@Column
	private String nm_campo;
	
	@Column
	private String ds_label;
	
	@Column
	private String ds_pesquisa;

	@Column
	private Integer cd_tabelaorigem;

	public Integer getCd_config_campo_pesquisa() {
		return cd_config_campo_pesquisa;
	}

	public void setCd_config_campo_pesquisa(Integer cd_config_campo_pesquisa) {
		this.cd_config_campo_pesquisa = cd_config_campo_pesquisa;
	}

	public Integer getCd_config_pesquisa() {
		return cd_config_pesquisa;
	}

	public void setCd_config_pesquisa(Integer cd_config_pesquisa) {
		this.cd_config_pesquisa = cd_config_pesquisa;
	}

	public Integer getCd_campo() {
		return cd_campo;
	}

	public void setCd_campo(Integer cd_campo) {
		this.cd_campo = cd_campo;
	}

	public String getNm_campo() {
		return nm_campo;
	}

	public void setNm_campo(String nm_campo) {
		this.nm_campo = nm_campo;
	}

	public String getDs_label() {
		return ds_label;
	}

	public void setDs_label(String ds_label) {
		this.ds_label = ds_label;
	}

	public String getDs_pesquisa() {
		return ds_pesquisa;
	}

	public void setDs_pesquisa(String ds_pesquisa) {
		this.ds_pesquisa = ds_pesquisa;
	}

	public Integer getCd_tabelaorigem() {
		return cd_tabelaorigem;
	}

	public void setCd_tabelaorigem(Integer cd_tabelaorigem) {
		this.cd_tabelaorigem = cd_tabelaorigem;
	}

}
