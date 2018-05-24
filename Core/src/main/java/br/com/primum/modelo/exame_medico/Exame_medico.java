package br.com.primum.modelo.exame_medico;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="exame_medico")
public class Exame_medico {

	@Id
	@Column
	private Integer cd_exame_medico;
	
	@Column
	private String nm_exame_medico;
	
	@Column
	private String ds_exame_medico;

	
	
	public Integer getCd_exame_medico() {
		return cd_exame_medico;
	}

	public void setCd_exame_medico(Integer cd_exame_medico) {
		this.cd_exame_medico = cd_exame_medico;
	}

	public String getNm_exame_medico() {
		return nm_exame_medico;
	}

	public void setNm_exame_medico(String nm_exame_medico) {
		this.nm_exame_medico = nm_exame_medico;
	}

	public String getDs_exame_medico() {
		return ds_exame_medico;
	}

	public void setDs_exame_medico(String ds_exame_medico) {
		this.ds_exame_medico = ds_exame_medico;
	}
	
}
