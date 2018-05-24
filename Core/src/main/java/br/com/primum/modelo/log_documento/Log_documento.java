package br.com.primum.modelo.log_documento;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="log_documento")
public class Log_documento {

	@Id
	@Column
	private Integer cd_log_documento;
	
	@Column
	private Integer cd_usuario;
	
	@Column
	private Integer cd_documento;
	
	@Column
	private String nm_documento;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_log_documento;
	
	@Column
	private Integer tp_log_documento;

	public Integer getCd_log_documento() {
		return cd_log_documento;
	}

	public void setCd_log_documento(Integer cd_log_documento) {
		this.cd_log_documento = cd_log_documento;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Integer getCd_documento() {
		return cd_documento;
	}

	public void setCd_documento(Integer cd_documento) {
		this.cd_documento = cd_documento;
	}

	public Date getDt_log_documento() {
		return dt_log_documento;
	}

	public void setDt_log_documento(Date dt_log_documento) {
		this.dt_log_documento = dt_log_documento;
	}

	public Integer getTp_log_documento() {
		return tp_log_documento;
	}

	public void setTp_log_documento(Integer tp_log_documento) {
		this.tp_log_documento = tp_log_documento;
	}

	public String getNm_documento() {
		return nm_documento;
	}

	public void setNm_documento(String nm_documento) {
		this.nm_documento = nm_documento;
	}
	
	
}
