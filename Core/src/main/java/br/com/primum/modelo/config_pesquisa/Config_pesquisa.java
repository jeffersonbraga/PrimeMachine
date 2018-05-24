package br.com.primum.modelo.config_pesquisa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.config_campo_pesquisa.Config_campo_pesquisa;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="config_pesquisa")
public class Config_pesquisa {

	@Id
	@Column
	private Integer cd_config_pesquisa;

	@Column
	private Integer cd_usuario;

	@Column
	private Integer cd_programa;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.config_campo_pesquisa.Config_campo_pesquisa", campoBuscar="cd_config_pesquisa", persistir=true)
	private List<Config_campo_pesquisa> listaCamposPesquisa;

	public Integer getCd_config_pesquisa() {
		return cd_config_pesquisa;
	}

	public void setCd_config_pesquisa(Integer cd_config_pesquisa) {
		this.cd_config_pesquisa = cd_config_pesquisa;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Integer getCd_programa() {
		return cd_programa;
	}

	public void setCd_programa(Integer cd_programa) {
		this.cd_programa = cd_programa;
	}

	public List<Config_campo_pesquisa> getListaCamposPesquisa() {
		return listaCamposPesquisa;
	}

	public void setListaCamposPesquisa(
			List<Config_campo_pesquisa> listaCamposPesquisa) {
		this.listaCamposPesquisa = listaCamposPesquisa;
	}
}
