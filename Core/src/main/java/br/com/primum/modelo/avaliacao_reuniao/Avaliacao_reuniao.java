package br.com.primum.modelo.avaliacao_reuniao;

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
import br.com.primum.modelo.ideia_reuniao_pic.Ideia_reuniao_pic;
import br.com.primum.modelo.participantes_reuniao_pic.Participantes_reuniao_pic;
import br.com.primum.modelo.reuniao_pic.Reuniao_pic;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="avaliacao_reuniao")
public class Avaliacao_reuniao {
	
	@Id
	@Column
	private Integer cd_avaliacao_reuniao;
	
	@Column
	private Integer cd_reuniao_pic;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_inicio;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_fim;
	
	@Column
	private Integer st_avaliacao_reuniao;
	
	@Transient
	@ObjetoBusca(notificador=false,  caminhoClasseJava ="br.com.primum.modelo.reuniao_pic.Reuniao_pic", campoBuscar="cd_reuniao_pic", campoComparar="cd_reuniao_pic", persistir=false)
	private Reuniao_pic reuniao_pic;
	
	@Transient
	@ListaBusca( caminhoClasseJava ="br.com.primum.modelo.participantes_reuniao_pic.Participantes_reuniao_pic", campoBuscar="cd_reuniao_pic", persistir=true)
	private List<Participantes_reuniao_pic> listaParticipantes;
	
	@Transient
	@ListaBusca( caminhoClasseJava ="br.com.primum.modelo.ideia_reuniao_pic.Ideia_reuniao_pic", campoBuscar="cd_reuniao_pic", persistir=false)
	private List<Ideia_reuniao_pic> listaIdeias;

	
	public Reuniao_pic getReuniao_pic() {
		return reuniao_pic;
	}

	public void setReuniao_pic(Reuniao_pic reuniao_pic) {
		this.reuniao_pic = reuniao_pic;
	}

	public Integer getCd_avaliacao_reuniao() {
		return cd_avaliacao_reuniao;
	}

	public void setCd_avaliacao_reuniao(Integer cd_avaliacao_reuniao) {
		this.cd_avaliacao_reuniao = cd_avaliacao_reuniao;
	}

	public Integer getCd_reuniao_pic() {
		return cd_reuniao_pic;
	}

	public void setCd_reuniao_pic(Integer cd_reuniao_pic) {
		this.cd_reuniao_pic = cd_reuniao_pic;
	}

	public Date getDt_inicio() {
		return dt_inicio;
	}

	public void setDt_inicio(Date dt_inicio) {
		this.dt_inicio = dt_inicio;
	}

	public Date getDt_fim() {
		return dt_fim;
	}

	public void setDt_fim(Date dt_fim) {
		this.dt_fim = dt_fim;
	}

	public Integer getSt_avaliacao_reuniao() {
		return st_avaliacao_reuniao;
	}

	public void setSt_avaliacao_reuniao(Integer st_avaliacao_reuniao) {
		this.st_avaliacao_reuniao = st_avaliacao_reuniao;
	}

	public List<Participantes_reuniao_pic> getListaParticipantes() {
		return listaParticipantes;
	}

	public void setListaParticipantes(
			List<Participantes_reuniao_pic> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}

	public List<Ideia_reuniao_pic> getListaIdeias() {
		return listaIdeias;
	}

	public void setListaIdeias(List<Ideia_reuniao_pic> listaIdeias) {
		this.listaIdeias = listaIdeias;
	}
	
}
