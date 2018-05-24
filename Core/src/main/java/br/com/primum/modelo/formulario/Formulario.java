package br.com.primum.modelo.formulario;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.acao_corretiva_formulario.Acao_corretiva_formulario;
import br.com.primum.modelo.acao_preventiva_formulario.Acao_preventiva_formulario;
import br.com.primum.modelo.grupo_formulario.Grupo_formulario;
import br.com.primum.modelo.mapa_formulario.Mapa_formulario;
import br.com.primum.modelo.programa_qualidade.Programa_qualidade;
import br.com.primum.modelo.sub_formulario.Sub_formulario;

@XmlRootElement(name = "Formulario")
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="formulario")
public class Formulario implements Serializable {

/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//5067800639722236287L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_formulario;

	@Column
	private Integer cd_revisao_programa_qualidade;

	@Column
	private Integer cd_programa_qualidade;

	@Column
	private Integer cd_usuario_criar;

	@Column
	private Integer cd_usuario_aprovar;

	@Column
	private Integer cd_profissao;

	@Column
	private Integer cd_periodicidade;

	@Column
	private Integer cd_tabela;
	
	@Column
	private Integer cd_unidade;

	@Column
	private Integer cd_formulario_planoacao;

	@Column
	private Integer cd_formulario_verificacao;

	@Column
	private String nm_formulario;

	@Column
	private String ds_identificacao;

	@Column
	private Integer st_formulario;

	@Column
	private String ds_oque;

	@Column
	private String ds_como;

	@Column
	private Integer tp_formulario;

	@Column
	private Integer tp_formato;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_criacao;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_aprovacao;

	@Column
	private Integer nr_tempoestimado;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.acao_corretiva_formulario.Acao_corretiva_formulario", persistir=true, campoBuscar="cd_formulario")
	private List<Acao_corretiva_formulario> listaAcao_corretiva_formulario;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.acao_preventiva_formulario.Acao_preventiva_formulario", persistir=true, campoBuscar="cd_formulario")
	private List<Acao_preventiva_formulario> listaAcao_preventiva_formulario;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.grupo_formulario.Grupo_formulario", persistir=true, campoBuscar="cd_formulario")
	private List<Grupo_formulario> listaGrupo_formulario;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.sub_formulario.Sub_formulario", persistir=true, campoBuscar="cd_formulario")
	private List<Sub_formulario> listaSub_formulario;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.mapa_formulario.Mapa_formulario", persistir=true, campoBuscar="cd_formulario")
	private List<Mapa_formulario> listaMapa_formulario;
	
	
	@Transient
	private Integer cd_formulario_origem;
	
	@Transient
	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.programa_qualidade.Programa_qualidade",campoBuscar="cd_programa_qualidade",campoComparar="cd_programa_qualidade",notificador=false, persistir=false)
	private Programa_qualidade programa_qualidade;


	@Transient
	public static Integer ELABORACAO 	= 0;

	@Transient
	public static Integer AVALIACAO 	= 1;

	@Transient
	public static Integer APROVADO		= 2;

	@Transient
	public static Integer REPROVADO 	= 3;

	@Transient
	public static Integer EM_USO 		= 4;

	@Transient
	public static Integer INATIVO		= 5;

	@Transient
	public static Integer EM_TESTE 		= 6;

	public Programa_qualidade getPrograma_qualidade() {
		return programa_qualidade;
	}

	public void setPrograma_qualidade(Programa_qualidade programa_qualidade) {
		this.programa_qualidade = programa_qualidade;
	}

	public Integer getCd_formulario_origem() {
		return cd_formulario_origem;
	}

	public void setCd_formulario_origem(Integer cd_formulario_origem) {
		this.cd_formulario_origem = cd_formulario_origem;
	}

	public Integer getCd_formulario() {
		return cd_formulario;
	}

	public void setCd_formulario(Integer cd_formulario) {
		this.cd_formulario = cd_formulario;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}

	public Integer getCd_revisao_programa_qualidade() {
		return cd_revisao_programa_qualidade;
	}

	public void setCd_revisao_programa_qualidade(
			Integer cd_revisao_programa_qualidade) {
		this.cd_revisao_programa_qualidade = cd_revisao_programa_qualidade;
	}

	public Integer getCd_programa_qualidade() {
		return cd_programa_qualidade;
	}

	public void setCd_programa_qualidade(Integer cd_programa_qualidade) {
		this.cd_programa_qualidade = cd_programa_qualidade;
	}

	public Integer getCd_usuario_criar() {
		return cd_usuario_criar;
	}

	public void setCd_usuario_criar(Integer cd_usuario_criar) {
		this.cd_usuario_criar = cd_usuario_criar;
	}

	public Integer getCd_usuario_aprovar() {
		return cd_usuario_aprovar;
	}

	public void setCd_usuario_aprovar(Integer cd_usuario_aprovar) {
		this.cd_usuario_aprovar = cd_usuario_aprovar;
	}

	public Integer getCd_profissao() {
		return cd_profissao;
	}

	public void setCd_profissao(Integer cd_profissao) {
		this.cd_profissao = cd_profissao;
	}

	public Integer getCd_periodicidade() {
		return cd_periodicidade;
	}

	public void setCd_periodicidade(Integer cd_periodicidade) {
		this.cd_periodicidade = cd_periodicidade;
	}

	public Integer getCd_tabela() {
		return cd_tabela;
	}

	public void setCd_tabela(Integer cd_tabela) {
		this.cd_tabela = cd_tabela;
	}

	public Integer getCd_formulario_planoacao() {
		return cd_formulario_planoacao;
	}

	public void setCd_formulario_planoacao(Integer cd_formulario_planoacao) {
		this.cd_formulario_planoacao = cd_formulario_planoacao;
	}

	public Integer getCd_formulario_verificacao() {
		return cd_formulario_verificacao;
	}

	public void setCd_formulario_verificacao(Integer cd_formulario_verificacao) {
		this.cd_formulario_verificacao = cd_formulario_verificacao;
	}

	public String getNm_formulario() {
		return nm_formulario;
	}

	public void setNm_formulario(String nm_formulario) {
		this.nm_formulario = nm_formulario;
	}

	public String getDs_identificacao() {
		return ds_identificacao;
	}

	public void setDs_identificacao(String ds_identificacao) {
		this.ds_identificacao = ds_identificacao;
	}

	public Integer getSt_formulario() {
		return st_formulario;
	}

	public void setSt_formulario(Integer st_formulario) {
		this.st_formulario = st_formulario;
	}

	public String getDs_oque() {
		return ds_oque;
	}

	public void setDs_oque(String ds_oque) {
		this.ds_oque = ds_oque;
	}

	public String getDs_como() {
		return ds_como;
	}

	public void setDs_como(String ds_como) {
		this.ds_como = ds_como;
	}

	public Integer getTp_formulario() {
		return tp_formulario;
	}

	public void setTp_formulario(Integer tp_formulario) {
		this.tp_formulario = tp_formulario;
	}

	public Integer getTp_formato() {
		return tp_formato;
	}

	public void setTp_formato(Integer tp_formato) {
		this.tp_formato = tp_formato;
	}

	public Date getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(Date dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public Date getDt_aprovacao() {
		return dt_aprovacao;
	}

	public void setDt_aprovacao(Date dt_aprovacao) {
		this.dt_aprovacao = dt_aprovacao;
	}

	public Integer getNr_tempoestimado() {
		return nr_tempoestimado;
	}

	public void setNr_tempoestimado(Integer nr_tempoestimado) {
		this.nr_tempoestimado = nr_tempoestimado;
	}

	public List<Acao_corretiva_formulario> getListaAcao_corretiva_formulario() {
		return listaAcao_corretiva_formulario;
	}

	public void setListaAcao_corretiva_formulario(
			List<Acao_corretiva_formulario> listaAcao_corretiva_formulario) {
		this.listaAcao_corretiva_formulario = listaAcao_corretiva_formulario;
	}

	public List<Grupo_formulario> getListaGrupo_formulario() {
		return listaGrupo_formulario;
	}

	public void setListaGrupo_formulario(
			List<Grupo_formulario> listaGrupo_formulario) {
		this.listaGrupo_formulario = listaGrupo_formulario;
	}

	public List<Sub_formulario> getListaSub_formulario() {
		return listaSub_formulario;
	}

	public void setListaSub_formulario(List<Sub_formulario> listaSub_formulario) {
		this.listaSub_formulario = listaSub_formulario;
	}

	public List<Acao_preventiva_formulario> getListaAcao_preventiva_formulario() {
		return listaAcao_preventiva_formulario;
	}

	public void setListaAcao_preventiva_formulario(
			List<Acao_preventiva_formulario> listaAcao_preventiva_formulario) {
		this.listaAcao_preventiva_formulario = listaAcao_preventiva_formulario;
	}

	public List<Mapa_formulario> getListaMapa_formulario() {
		return listaMapa_formulario;
	}

	public void setListaMapa_formulario(List<Mapa_formulario> listaMapa_formulario) {
		this.listaMapa_formulario = listaMapa_formulario;
	}

    @Override
    public String toString() {

        return this.getNm_formulario();
    }

}