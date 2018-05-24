package br.com.primum.modelo.executar_reuniao;

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
import br.com.primum.modelo.participantes_reuniao.Participantes_reuniao;
import br.com.primum.modelo.reuniao.Reuniao;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="executar_reuniao")
public class Executar_reuniao {

	@Id
	@Column
	private Integer cd_executar_reuniao;
	
	@Column
	private Integer cd_reuniao;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_inicio_realizacao;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_fim_realizacao;
	
	@Column
	private Integer st_executar_reuniao;
	
	@Column
	private String ds_ata;
	
	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.reuniao.Reuniao", campoBuscar="cd_reuniao", campoComparar="cd_reuniao", persistir = true)
	private Reuniao reuniao;

	
	
	public Integer getCd_executar_reuniao() {
		return cd_executar_reuniao;
	}

	public void setCd_executar_reuniao(Integer cd_executar_reuniao) {
		this.cd_executar_reuniao = cd_executar_reuniao;
	}

	public Integer getCd_reuniao() {
		return cd_reuniao;
	}

	public void setCd_reuniao(Integer cd_reuniao) {
		this.cd_reuniao = cd_reuniao;
	}

	public Date getDt_inicio_realizacao() {
		return dt_inicio_realizacao;
	}

	public void setDt_inicio_realizacao(Date dt_inicio_realizacao) {
		this.dt_inicio_realizacao = dt_inicio_realizacao;
	}

	public Date getDt_fim_realizacao() {
		return dt_fim_realizacao;
	}

	public void setDt_fim_realizacao(Date dt_fim_realizacao) {
		this.dt_fim_realizacao = dt_fim_realizacao;
	}

	public Integer getSt_executar_reuniao() {
		return st_executar_reuniao;
	}

	public void setSt_executar_reuniao(Integer st_executar_reuniao) {
		this.st_executar_reuniao = st_executar_reuniao;
	}

	public String getDs_ata() {
		return ds_ata;
	}

	public void setDs_ata(String ds_ata) {
		this.ds_ata = ds_ata;
	}

	public Reuniao getReuniao() {
		return reuniao;
	}

	public void setReuniao(Reuniao reuniao) {
		this.reuniao = reuniao;
	}

	
}
