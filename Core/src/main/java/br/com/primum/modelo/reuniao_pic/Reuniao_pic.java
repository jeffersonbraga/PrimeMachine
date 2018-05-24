package br.com.primum.modelo.reuniao_pic;

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
import br.com.primum.modelo.ideia_reuniao_pic.Ideia_reuniao_pic;
import br.com.primum.modelo.participantes_reuniao_pic.Participantes_reuniao_pic;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="reuniao_pic")
public class Reuniao_pic {

	@Id
	@Column
	private Integer cd_reuniao_pic;
	
	@Column
	private String nm_reuniao_pic;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_inicio_reuniao_pic;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_fim_reuniao_pic;
	
	@Column
	private Integer st_reuniao_pic;
	
	@Column
	private Integer cd_usuario_coordenador;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.participantes_reuniao_pic.Participantes_reuniao_pic", campoBuscar="cd_reuniao_pic", persistir = true)
	private List<Participantes_reuniao_pic> listaParticipantesReuniao;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.ideia_reuniao_pic.Ideia_reuniao_pic", campoBuscar="cd_reuniao_pic", persistir = true)
	private List<Ideia_reuniao_pic> listaIdeiasReuniao;

	
	public Integer getCd_reuniao_pic() {
		return cd_reuniao_pic;
	}

	public void setCd_reuniao_pic(Integer cd_reuniao_pic) {
		this.cd_reuniao_pic = cd_reuniao_pic;
	}

	public String getNm_reuniao_pic() {
		return nm_reuniao_pic;
	}

	public void setNm_reuniao_pic(String nm_reuniao_pic) {
		this.nm_reuniao_pic = nm_reuniao_pic;
	}

	public Date getDt_inicio_reuniao_pic() {
		return dt_inicio_reuniao_pic;
	}

	public void setDt_inicio_reuniao_pic(Date dt_inicio_reuniao_pic) {
		this.dt_inicio_reuniao_pic = dt_inicio_reuniao_pic;
	}

	public Date getDt_fim_reuniao_pic() {
		return dt_fim_reuniao_pic;
	}

	public void setDt_fim_reuniao_pic(Date dt_fim_reuniao_pic) {
		this.dt_fim_reuniao_pic = dt_fim_reuniao_pic;
	}

	public Integer getSt_reuniao_pic() {
		return st_reuniao_pic;
	}

	public void setSt_reuniao_pic(Integer st_reuniao_pic) {
		this.st_reuniao_pic = st_reuniao_pic;
	}

	public Integer getCd_usuario_coordenador() {
		return cd_usuario_coordenador;
	}

	public void setCd_usuario_coordenador(Integer cd_usuario_coordenador) {
		this.cd_usuario_coordenador = cd_usuario_coordenador;
	}

	public List<Participantes_reuniao_pic> getListaParticipantesReuniao() {
		return listaParticipantesReuniao;
	}

	public void setListaParticipantesReuniao(
			List<Participantes_reuniao_pic> listaParticipantesReuniao) {
		this.listaParticipantesReuniao = listaParticipantesReuniao;
	}

	public List<Ideia_reuniao_pic> getListaIdeiasReuniao() {
		return listaIdeiasReuniao;
	}

	public void setListaIdeiasReuniao(List<Ideia_reuniao_pic> listaIdeiasReuniao) {
		this.listaIdeiasReuniao = listaIdeiasReuniao;
	}

}
