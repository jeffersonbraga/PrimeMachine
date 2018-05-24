package br.com.primum.modelo.campo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.codigosfixos.CodigosFixos;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="campo")
public class Campo {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_campo;

	@Column
	private String nm_campo;

	@Column
	private String ds_label;

	@Column
	private Integer cd_tipo_campo;

	@Column
	private Integer cd_tabela;

	@Column
	private Integer	cd_tabelaorigem;
	
	@Column
	private Integer	tp_chaveprimaria;
	
	@Column
	private Integer	tp_chaveestrangeira;
	
	@Column
	private Integer	st_aceitavalornull;
	
	@Column
	private String ds_descricao;
	
	@Column
	private Integer	tp_identificador;
	
	@Column
	private Integer	st_obrigatorio;

	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.codigosfixos.CodigosFixos", campoBuscar="cd_campo", persistir=true)
	@Transient
	private List<CodigosFixos> listaCodigosFixos;
	

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

	public Integer getCd_tipo_campo() {
		return cd_tipo_campo;
	}

	public void setCd_tipo_campo(Integer cd_tipo_campo) {
		this.cd_tipo_campo = cd_tipo_campo;
	}

	public Integer getCd_tabela() {
		return cd_tabela;
	}

	public void setCd_tabela(Integer cd_tabela) {
		this.cd_tabela = cd_tabela;
	}

	public List<CodigosFixos> getListaCodigosFixos() {
		return listaCodigosFixos;
	}
	
	public void setListaCodigosFixos(List<CodigosFixos> listaCodigosFixos) {
		
		this.listaCodigosFixos = listaCodigosFixos;
	}

	public Integer getCd_tabelaorigem() {
		return cd_tabelaorigem;
	}

	public void setCd_tabelaorigem(Integer cd_tabelaorigem) {
		this.cd_tabelaorigem = cd_tabelaorigem;
	}

	public Integer getTp_chaveprimaria() {
		return tp_chaveprimaria;
	}

	public void setTp_chaveprimaria(Integer tp_chaveprimaria) {
		this.tp_chaveprimaria = tp_chaveprimaria;
	}

	public Integer getTp_chaveestrangeira() {
		return tp_chaveestrangeira;
	}

	public void setTp_chaveestrangeira(Integer tp_chaveestrangeira) {
		this.tp_chaveestrangeira = tp_chaveestrangeira;
	}

	public Integer getSt_aceitavalornull() {
		return st_aceitavalornull;
	}

	public void setSt_aceitavalornull(Integer st_aceitavalornull) {
		this.st_aceitavalornull = st_aceitavalornull;
	}

	public String getDs_descricao() {
		return ds_descricao;
	}

	public void setDs_descricao(String ds_descricao) {
		this.ds_descricao = ds_descricao;
	}

	public Integer getTp_identificador() {
		return tp_identificador;
	}

	public void setTp_identificador(Integer tp_identificador) {
		this.tp_identificador = tp_identificador;
	}

	public Integer getSt_obrigatorio() {
		return st_obrigatorio;
	}

	public void setSt_obrigatorio(Integer st_obrigatorio) {
		this.st_obrigatorio = st_obrigatorio;
	}
}
