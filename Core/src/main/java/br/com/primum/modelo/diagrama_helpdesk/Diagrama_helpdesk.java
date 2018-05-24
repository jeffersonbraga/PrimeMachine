package br.com.primum.modelo.diagrama_helpdesk;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.categoria_ocorrencia.Categoria_ocorrencia;
import br.com.primum.modelo.setor.Setor;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="diagrama_helpdesk")
public class Diagrama_helpdesk {

	@Id
	@Column
	private Integer cd_diagrama_helpdesk;

	@Column
	private Integer cd_diagrama_origem;

	@Column
	private Integer cd_helpdesk_ocorrencia;

	@Column
	private Integer cd_usuario_diagrama;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_criacao_diagrama;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_analise_diagrama;
	
	@Column
	private String ds_comentario;

	
	@Column
	private Integer st_diagrama_helpdesk;

	@Column
	private Integer cd_usuario_analise;

	
	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.diagrama_helpdesk.Diagrama_helpdesk",persistir=false, campoBuscar="cd_diagrama_helpdesk", campoComparar="cd_diagrama_origem")
	private Diagrama_helpdesk diagrama_origem;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.categoria_ocorrencia.Categoria_ocorrencia", campoBuscar="cd_diagrama_helpdesk", persistir=true)
	private List<Categoria_ocorrencia> listaCategoria_ocorrencia;
	
	@Transient
	private String ds_ocorrencia_helpdesk;
	
	
	
	public Integer getCd_usuario_analise() {
		return cd_usuario_analise;
	}

	public void setCd_usuario_analise(Integer cd_usuario_analise) {
		this.cd_usuario_analise = cd_usuario_analise;
	}

	public String getDs_ocorrencia_helpdesk() {
		return ds_ocorrencia_helpdesk;
	}

	public void setDs_ocorrencia_helpdesk(String ds_ocorrencia_helpdesk) {
		this.ds_ocorrencia_helpdesk = ds_ocorrencia_helpdesk;
	}

	public String getDs_comentario() {
		return ds_comentario;
	}

	public void setDs_comentario(String ds_comentario) {
		this.ds_comentario = ds_comentario;
	}

	public List<Categoria_ocorrencia> getListaCategoria_ocorrencia() {
		return listaCategoria_ocorrencia;
	}

	public void setListaCategoria_ocorrencia(
			List<Categoria_ocorrencia> listaCategoria_ocorrencia) {
		this.listaCategoria_ocorrencia = listaCategoria_ocorrencia;
	}

	public Diagrama_helpdesk getDiagrama_origem() {
		return diagrama_origem;
	}

	public void setDiagrama_origem(Diagrama_helpdesk diagrama_origem) {
		this.diagrama_origem = diagrama_origem;
	}

	public Integer getCd_diagrama_helpdesk() {
		return cd_diagrama_helpdesk;
	}

	public void setCd_diagrama_helpdesk(Integer cd_diagrama_helpdesk) {
		this.cd_diagrama_helpdesk = cd_diagrama_helpdesk;
	}

	public Integer getCd_diagrama_origem() {
		return cd_diagrama_origem;
	}

	public void setCd_diagrama_origem(Integer cd_diagrama_origem) {
		this.cd_diagrama_origem = cd_diagrama_origem;
	}

	public Integer getCd_helpdesk_ocorrencia() {
		return cd_helpdesk_ocorrencia;
	}

	public void setCd_helpdesk_ocorrencia(Integer cd_helpdesk_ocorrencia) {
		this.cd_helpdesk_ocorrencia = cd_helpdesk_ocorrencia;
	}

	
	public Integer getCd_usuario_diagrama() {
		return cd_usuario_diagrama;
	}

	public void setCd_usuario_diagrama(Integer cd_usuario_diagrama) {
		this.cd_usuario_diagrama = cd_usuario_diagrama;
	}

	public Date getDt_criacao_diagrama() {
		return dt_criacao_diagrama;
	}

	public void setDt_criacao_diagrama(Date dt_criacao_diagrama) {
		this.dt_criacao_diagrama = dt_criacao_diagrama;
	}

	public Integer getSt_diagrama_helpdesk() {
		return st_diagrama_helpdesk;
	}

	public void setSt_diagrama_helpdesk(Integer st_diagrama_helpdesk) {
		this.st_diagrama_helpdesk = st_diagrama_helpdesk;
	}

	public Date getDt_analise_diagrama() {
		return dt_analise_diagrama;
	}

	public void setDt_analise_diagrama(Date dt_analise_diagrama) {
		this.dt_analise_diagrama = dt_analise_diagrama;
	}
	
	
}
