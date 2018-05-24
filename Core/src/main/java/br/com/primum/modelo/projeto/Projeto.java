package br.com.primum.modelo.projeto;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;

/**
 */
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(catalog = "gestor", name = "projeto")
public class Projeto implements Serializable {
	//private static final long serialVersionUID = ContextoAplicao.SERIAL_PADRAO;// 1L;//1L;

	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//7329441776476355624L;

	@Id	
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "cd_projeto")
	private Integer cd_projeto;
	
	@Column(name = "cd_usuario")
	private Integer cd_usuario;
	
	@Column(name = "nm_projeto", length = 45)
	private String nm_projeto;
	
	@Column(name = "ds_projeto", columnDefinition = "TEXT")
	private String ds_projeto;

	////@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name = "dt_inicio")
	//@Temporal(value = TemporalType.DATE)
	private Calendar dt_inicio;

	////@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name = "dt_termino")
	//@Temporal(value = TemporalType.DATE)
	private Calendar dt_termino;
	
	@Column(name = "st_projeto")
	private Integer st_projeto;

	public Integer getCd_projeto() {
		return cd_projeto;
	}
	public void setCd_projeto(Integer cd_projeto) {
		this.cd_projeto = cd_projeto;
	}
	public Integer getCd_usuario() {
		return cd_usuario;
	}
	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}
	public String getNm_projeto() {
		return nm_projeto;
	}
	public void setNm_projeto(String nm_projeto) {
		this.nm_projeto = nm_projeto;
	}
	public String getDs_projeto() {
		return ds_projeto;
	}
	public void setDs_projeto(String ds_projeto) {
		this.ds_projeto = ds_projeto;
	}
	public Calendar getDt_inicio() {
		return dt_inicio;
	}
	public void setDt_inicio(Calendar dt_inicio) {
		this.dt_inicio = dt_inicio;
	}
	public Calendar getDt_termino() {
		return dt_termino;
	}
	public void setDt_termino(Calendar dt_termino) {
		this.dt_termino = dt_termino;
	}
	public Integer getSt_projeto() {
		return st_projeto;
	}
	public void setSt_projeto(Integer st_projeto) {
		this.st_projeto = st_projeto;
	}
}
