package br.com.primum.modelo.escala_tempo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="escala_tempo")
public class Escala_tempo {

	@Id
	private Integer cd_escala_tempo;

	@Column
	private String ds_escala_tempo;

	@Column
	private Integer vl_equivalencia_minuto;

	public Integer getCd_escala_tempo() {
		return cd_escala_tempo;
	}

	public void setCd_escala_tempo(Integer cd_escala_tempo) {
		this.cd_escala_tempo = cd_escala_tempo;
	}

	public String getDs_escala_tempo() {
		return ds_escala_tempo;
	}

	public void setDs_escala_tempo(String ds_escala_tempo) {
		this.ds_escala_tempo = ds_escala_tempo;
	}

	public Integer getVl_equivalencia_minuto() {
		return vl_equivalencia_minuto;
	}

	public void setVl_equivalencia_minuto(Integer vl_equivalencia_minuto) {
		this.vl_equivalencia_minuto = vl_equivalencia_minuto;
	}
}