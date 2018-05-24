package br.com.primum.modelo.config_envio_indicadores;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="config_envio_indicadores")
public class Config_envio_indicadores {

	@Id
	@Column
	private Integer cd_config_envio_indicadores;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_notificar;

	@Column
	private Integer cd_config_indicadores;

	@Column
	private Integer vl_mesmo_horario;

	
	public Integer getCd_config_envio_indicadores() {
		return cd_config_envio_indicadores;
	}

	public void setCd_config_envio_indicadores(Integer cd_config_envio_indicadores) {
		this.cd_config_envio_indicadores = cd_config_envio_indicadores;
	}

	public Date getDt_notificar() {
		return dt_notificar;
	}

	public void setDt_notificar(Date dt_notificar) {
		this.dt_notificar = dt_notificar;
	}

	public Integer getCd_config_indicadores() {
		return cd_config_indicadores;
	}

	public void setCd_config_indicadores(Integer cd_config_indicadores) {
		this.cd_config_indicadores = cd_config_indicadores;
	}

	public Integer getVl_mesmo_horario() {
		return vl_mesmo_horario;
	}

	public void setVl_mesmo_horario(Integer vl_mesmo_horario) {
		this.vl_mesmo_horario = vl_mesmo_horario;
	}

	
}
