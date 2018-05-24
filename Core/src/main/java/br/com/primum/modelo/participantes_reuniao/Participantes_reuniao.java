package br.com.primum.modelo.participantes_reuniao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="participantes_reuniao")
public class Participantes_reuniao {

	@Id
	@Column
	private Integer cd_participantes_reuniao;
	
	@Column
	private Integer cd_reuniao;
	
	@Column
	private Boolean tp_presenca;
	
	@Column
	private Integer cd_pessoa;

	@Column
	private Integer tp_confirmacao_presenca;

	public Integer getCd_participantes_reuniao() {
		return cd_participantes_reuniao;
	}


	public void setCd_participantes_reuniao(Integer cd_participantes_reuniao) {
		this.cd_participantes_reuniao = cd_participantes_reuniao;
	}


	public Integer getCd_reuniao() {
		return cd_reuniao;
	}


	public void setCd_reuniao(Integer cd_reuniao) {
		this.cd_reuniao = cd_reuniao;
	}


	public Boolean getTp_presenca() {
		return tp_presenca;
	}


	public void setTp_presenca(Boolean tp_presenca) {
		this.tp_presenca = tp_presenca;
	}


	public Integer getTp_confirmacao_presenca() {
		return tp_confirmacao_presenca;
	}


	public void setTp_confirmacao_presenca(Integer tp_confirmacao_presenca) {
		this.tp_confirmacao_presenca = tp_confirmacao_presenca;
	}


	public Integer getCd_pessoa() {
		return cd_pessoa;
	}


	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}

}
