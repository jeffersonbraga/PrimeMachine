package br.com.primum.modelo.execucao_formulario;

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
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.plano_acao.Plano_acao;
import br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario;

@XmlRootElement(name = "Execucao_formulario")
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="execucao_formulario")
public class Execucao_formulario implements Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//5031162208147371093L;

	@Id
	@Column
	private Integer cd_execucao_formulario;

	@Column
	private Integer cd_formulario;

	@Column
	private Integer cd_usuario;

	@Column
	private Integer cd_formulario_verificacao;

	@Column
	private Integer cd_sub_formulario;

	@Column
	//@Temporal(value = TemporalType.TIMESTAMP)
	private Date dt_execucao_formulario;

	@Column
	//@Temporal(value = TemporalType.TIMESTAMP)
	private Date nr_hora_termino;

	@Column
	private Integer st_execucao_formulario;

	@Column
	private Integer cd_execucao_verificada;
 
	@Column
	private Integer st_execucao_verificada;

	@Column
	private Integer st_verificacao_doc;
	
	@Column
	private Integer cd_escalonamento_usuario;
	
	@Column
	private Integer cd_unidade;
	
	@Column
	private Integer cd_plano_acao;
	
	@Column 
	private Integer tp_verificacao;
	
	@Column 
	private Integer st_verificacao_fiscal;

	@Column
	private Integer cd_tipo_turno;

	@Transient
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.formulario.Formulario", campoBuscar="cd_formulario", campoComparar="cd_formulario")
	private Formulario formulario;
	
	@Transient
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.formulario.Formulario", campoBuscar="cd_formulario", campoComparar="cd_formulario_verificacao")
	private Formulario formulario_verificacao;

	@Transient
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.plano_acao.Plano_acao", campoBuscar="cd_plano_acao", campoComparar="cd_plano_acao")
	private Plano_acao plano_acao;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario", persistir=true, campoBuscar="cd_execucao_formulario")
	private List<Resposta_exec_formulario> listaRespostas_execucao_formulario;

	@Transient
	private String ds_descricao;

	@Transient
	private Integer st_sincronizado;

	
	
	public Formulario getFormulario_verificacao() {
		return formulario_verificacao;
	}

	public void setFormulario_verificacao(Formulario formulario_verificacao) {
		this.formulario_verificacao = formulario_verificacao;
	}

	public String getDs_descricao() {
		return ds_descricao;
	}

	public void setDs_descricao(String ds_descricao) {
		this.ds_descricao = ds_descricao;
	}

	public Integer getCd_escalonamento_usuario() {
		return cd_escalonamento_usuario;
	}

	public void setCd_escalonamento_usuario(Integer cd_escalonamento_usuario) {
		this.cd_escalonamento_usuario = cd_escalonamento_usuario;
	}

	public Integer getCd_execucao_formulario() {
		return cd_execucao_formulario;
	}

	public void setCd_execucao_formulario(Integer cd_execucao_formulario) {
		this.cd_execucao_formulario = cd_execucao_formulario;
	}

	public Integer getCd_formulario() {
		return cd_formulario;
	}

	public void setCd_formulario(Integer cd_formulario) {
		this.cd_formulario = cd_formulario;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Date getDt_execucao_formulario() {
		return dt_execucao_formulario;
	}

	public void setDt_execucao_formulario(Date dt_execucao_formulario) {
		this.dt_execucao_formulario = dt_execucao_formulario;
	}

	public Date getNr_hora_termino() {
		return nr_hora_termino;
	}

	public void setNr_hora_termino(Date nr_hora_termino) {
		this.nr_hora_termino = nr_hora_termino;
	}

	public Integer getSt_execucao_formulario() {
		return st_execucao_formulario;
	}

	public void setSt_execucao_formulario(Integer st_execucao_formulario) {
		this.st_execucao_formulario = st_execucao_formulario;
	}

	public List<Resposta_exec_formulario> getListaRespostas_execucao_formulario() {
		return listaRespostas_execucao_formulario;
	}

	public void setListaRespostas_execucao_formulario(
			List<Resposta_exec_formulario> listaRespostas_execucao_formulario) {
		this.listaRespostas_execucao_formulario = listaRespostas_execucao_formulario;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	public Integer getCd_formulario_verificacao() {
		return cd_formulario_verificacao;
	}

	public void setCd_formulario_verificacao(Integer cd_formulario_verificacao) {
		this.cd_formulario_verificacao = cd_formulario_verificacao;
	}

	public Integer getCd_sub_formulario() {
		return cd_sub_formulario;
	}

	public void setCd_sub_formulario(Integer cd_sub_formulario) {
		this.cd_sub_formulario = cd_sub_formulario;
	}

	public Integer getCd_execucao_verificada() {
		return cd_execucao_verificada;
	}

	public void setCd_execucao_verificada(Integer cd_execucao_verificada) {
		this.cd_execucao_verificada = cd_execucao_verificada;
	}

	public Integer getSt_execucao_verificada() {
		return st_execucao_verificada;
	}

	public void setSt_execucao_verificada(Integer st_execucao_verificada) {
		this.st_execucao_verificada = st_execucao_verificada;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}

	public Integer getSt_verificacao_doc() {
		return st_verificacao_doc;
	}

	public void setSt_verificacao_doc(Integer st_verificacao_doc) {
		this.st_verificacao_doc = st_verificacao_doc;
	}

	public Integer getCd_plano_acao() {
		return cd_plano_acao;
	}

	public void setCd_plano_acao(Integer cd_plano_acao) {
		this.cd_plano_acao = cd_plano_acao;
	}

	public Plano_acao getPlano_acao() {
		return plano_acao;
	}

	public void setPlano_acao(Plano_acao plano_acao) {
		this.plano_acao = plano_acao;
	}

	public Integer getTp_verificacao() {
		return tp_verificacao;
	}

	public void setTp_verificacao(Integer tp_verificacao) {
		this.tp_verificacao = tp_verificacao;
	}

	public Integer getSt_verificacao_fiscal() {
		return st_verificacao_fiscal;
	}

	public void setSt_verificacao_fiscal(Integer st_verificacao_fiscal) {
		this.st_verificacao_fiscal = st_verificacao_fiscal;
	}

	public Integer getSt_sincronizado() {
		return st_sincronizado;
	}

	public void setSt_sincronizado(Integer st_sincronizado) {
		this.st_sincronizado = st_sincronizado;
	}

	public Integer getCd_tipo_turno() {
		return cd_tipo_turno;
	}

	public void setCd_tipo_turno(Integer cd_tipo_turno) {
		this.cd_tipo_turno = cd_tipo_turno;
	}
}