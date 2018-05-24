package br.com.primum.modelo.auditoria;

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

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.grupo_auditoria.Grupo_auditoria;
import br.com.primum.modelo.resposta_auditoria.Resposta_auditoria;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="auditoria")
public class Auditoria implements Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-4097370907099350095L;

	@Id
	@Column
	private Integer cd_auditoria;

	@Column
	private Integer cd_usuario;

	@Column
	private String nm_auditoria;

	////@Temporal(value=TemporalType.DATE)
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_criacao;

	@Column
	private String ds_auditoria;

	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.grupo_auditoria.Grupo_auditoria", campoBuscar="cd_auditoria", persistir=true)
	@Transient	
	private List<Grupo_auditoria> listaGruposAuditoria;
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.resposta_auditoria.Resposta_auditoria", campoBuscar="cd_auditoria", persistir=true)
	@Transient
	private List<Resposta_auditoria> listaRespostasAuditoria;
	
	public Integer getCd_auditoria() {
		return cd_auditoria;
	}

	public void setCd_auditoria(Integer cd_auditoria) {
		this.cd_auditoria = cd_auditoria;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public String getNm_auditoria() {
		return nm_auditoria;
	}

	public void setNm_auditoria(String nm_auditoria) {
		this.nm_auditoria = nm_auditoria;
	}

	public Date getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(Date dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public String getDs_auditoria() {
		return ds_auditoria;
	}

	public void setDs_auditoria(String ds_auditoria) {
		this.ds_auditoria = ds_auditoria;
	}

	public List<Grupo_auditoria> getListaGruposAuditoria() {
		return listaGruposAuditoria;
	}

	public void setListaGruposAuditoria(List<Grupo_auditoria> listaGruposAuditoria) {
		this.listaGruposAuditoria = listaGruposAuditoria;
	}

	public List<Resposta_auditoria> getListaRespostasAuditoria() {
		return listaRespostasAuditoria;
	}

	public void setListaRespostasAuditoria(
			List<Resposta_auditoria> listaRespostasAuditoria) {
		this.listaRespostasAuditoria = listaRespostasAuditoria;
	}
}
