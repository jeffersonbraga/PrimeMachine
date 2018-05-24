package br.com.primum.modelo.campo_formulario;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ChaveEstrangeira;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.config_campo_formulario.Config_campo_formulario;
import br.com.primum.modelo.resposta_campo_formulario.Resposta_campo_formulario;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="campo_formulario")
public class Campo_formulario implements Serializable {

	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//4543840598486064049L;

	@Id
	@Column
	private Integer cd_campo_formulario;
	
	@ChaveEstrangeira(nomeObjetoOrigem="cd_grupo_formulario")
	@Column
	private Integer cd_grupo_formulario;

	@Column
	private Integer cd_tipo_campo;

	@Column
	private String ds_campo_formulario;

	@Column
	private String nm_campo_formulario;
	
	@Column
	private String ds_formula;
	
	@Column
	private Integer nr_casas_decimais;
	
	@Column
	private Integer tp_campo_mapa;
	
	@Column
	private Integer st_obrigatorio;
	
	@Column
	private Integer tp_identificador;
	
	@Column
	private Integer tp_descricao;
	
	
	@Transient
	private Integer identificador;

	@Transient	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.config_campo_formulario.Config_campo_formulario", persistir=true, campoBuscar="cd_campo_formulario")
	private List<Config_campo_formulario> listaConfiguracao_campo_formulario;

	@Transient	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.resposta_campo_formulario.Resposta_campo_formulario", campoBuscar="cd_campo_formulario", persistir=true)
	private List<Resposta_campo_formulario> listaResposta_campo_formulario;

	public Integer getCd_campo_formulario() {
		return cd_campo_formulario;
	}

	public void setCd_campo_formulario(Integer cd_campo_formulario) {
		this.cd_campo_formulario = cd_campo_formulario;
	}

	public Integer getCd_tipo_campo() {
		return cd_tipo_campo;
	}

	public void setCd_tipo_campo(Integer cd_tipo_campo) {
		this.cd_tipo_campo = cd_tipo_campo;
	}

	public String getDs_campo_formulario() {
		return ds_campo_formulario;
	}

	public void setDs_campo_formulario(String ds_campo_formulario) {
		this.ds_campo_formulario = ds_campo_formulario;
	}

	public String getNm_campo_formulario() {
		return nm_campo_formulario;
	}

	public void setNm_campo_formulario(String nm_campo_formulario) {
		this.nm_campo_formulario = nm_campo_formulario;
	}

	public Integer getCd_grupo_formulario() {
		return cd_grupo_formulario;
	}

	public void setCd_grupo_formulario(Integer cd_grupo_formulario) {
		this.cd_grupo_formulario = cd_grupo_formulario;
	}

	public List<Config_campo_formulario> getListaConfiguracao_campo_formulario() {
		return listaConfiguracao_campo_formulario;
	}

	public void setListaConfiguracao_campo_formulario(
			List<Config_campo_formulario> listaConfiguracao_campo_formulario) {
		this.listaConfiguracao_campo_formulario = listaConfiguracao_campo_formulario;
	}

	public List<Resposta_campo_formulario> getListaResposta_campo_formulario() {
		return listaResposta_campo_formulario;
	}

	public void setListaResposta_campo_formulario(
			List<Resposta_campo_formulario> listaResposta_campo_formulario) {
		this.listaResposta_campo_formulario = listaResposta_campo_formulario;
	}

	public String getDs_formula() {
		return ds_formula;
	}

	public void setDs_formula(String ds_formula) {
		this.ds_formula = ds_formula;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public Integer getNr_casas_decimais() {
		return nr_casas_decimais;
	}

	public void setNr_casas_decimais(Integer nr_casas_decimais) {
		this.nr_casas_decimais = nr_casas_decimais;
	}

	public Integer getTp_campo_mapa() {
		return tp_campo_mapa;
	}

	public void setTp_campo_mapa(Integer tp_campo_mapa) {
		this.tp_campo_mapa = tp_campo_mapa;
	}

	public Integer getSt_obrigatorio() {
		return st_obrigatorio;
	}

	public void setSt_obrigatorio(Integer st_obrigatorio) {
		this.st_obrigatorio = st_obrigatorio;
	}

	public Integer getTp_identificador() {
		return tp_identificador;
	}

	public void setTp_identificador(Integer tp_identificador) {
		this.tp_identificador = tp_identificador;
	}

	public Integer getTp_descricao() {
		return tp_descricao;
	}

	public void setTp_descricao(Integer tp_descricao) {
		this.tp_descricao = tp_descricao;
	}
}