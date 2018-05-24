package br.com.primum.modelo.conclusao_auditoria;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.cronograma_auditoria.Cronograma_auditoria;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="conclusao_auditoria")
public class Conclusao_auditoria {

	@Id
	@Column
	private Integer cd_conclusao_auditoria;

	@Column
	private Integer cd_faixa_class_auditoria;

	@Column
	private Integer cd_cronograma_auditoria;

	////@Temporal(value=TemporalType.DATE)
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_conclusao_auditoria;

	@Column
	private Integer nr_percentual_atendimento;

	@Column
	private String ds_comentario;

	@Column
	private String ds_melhoria;
	
	@Column
	private String ds_conclusao;
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.cronograma_auditoria.Cronograma_auditoria", campoBuscar="cd_cronograma_auditoria", campoComparar="cd_cronograma_auditoria")
	@Transient
	private Cronograma_auditoria cronograma_auditoria;

	
	public String getDs_melhoria() {
		return ds_melhoria;
	}

	public void setDs_melhoria(String ds_melhoria) {
		this.ds_melhoria = ds_melhoria;
	}

	public Integer getCd_conclusao_auditoria() {
		return cd_conclusao_auditoria;
	}

	public void setCd_conclusao_auditoria(Integer cd_conclusao_auditoria) {
		this.cd_conclusao_auditoria = cd_conclusao_auditoria;
	}

	public Integer getCd_faixa_class_auditoria() {
		return cd_faixa_class_auditoria;
	}

	public void setCd_faixa_class_auditoria(Integer cd_faixa_class_auditoria) {
		this.cd_faixa_class_auditoria = cd_faixa_class_auditoria;
	}

	public Integer getCd_cronograma_auditoria() {
		return cd_cronograma_auditoria;
	}

	public void setCd_cronograma_auditoria(Integer cd_cronograma_auditoria) {
		this.cd_cronograma_auditoria = cd_cronograma_auditoria;
	}

	public Date getDt_conclusao_auditoria() {
		return dt_conclusao_auditoria;
	}

	public void setDt_conclusao_auditoria(Date dt_conclusao_auditoria) {
		this.dt_conclusao_auditoria = dt_conclusao_auditoria;
	}

	public String getDs_comentario() {
		return ds_comentario;
	}

	public void setDs_comentario(String ds_comentario) {
		this.ds_comentario = ds_comentario;
	}

	public String getDs_conclusao() {
		return ds_conclusao;
	}

	public void setDs_conclusao(String ds_conclusao) {
		this.ds_conclusao = ds_conclusao;
	}

	public Integer getNr_percentual_atendimento() {
		return nr_percentual_atendimento;
	}

	public void setNr_percentual_atendimento(Integer nr_percentual_atendimento) {
		this.nr_percentual_atendimento = nr_percentual_atendimento;
	}

	public Cronograma_auditoria getCronograma_auditoria() {
		return cronograma_auditoria;
	}

	public void setCronograma_auditoria(Cronograma_auditoria cronograma_auditoria) {
		this.cronograma_auditoria = cronograma_auditoria;
	}
}
