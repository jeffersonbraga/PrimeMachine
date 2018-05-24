package br.com.primum.modelo.reuniao;

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
import br.com.primum.modelo.participantes_reuniao.Participantes_reuniao;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="reuniao")
public class Reuniao {
	
	@Id
	@Column
	private Integer cd_reuniao;
	
	@Column
	private String nm_reuniao;
	
	@Column
	private String ds_pauta;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_inicio;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_fim;
	
	@Column
	private Integer st_reuniao;
	
	@Column
	private Integer cd_usuario_coordenador;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.participantes_reuniao.Participantes_reuniao", campoBuscar="cd_reuniao", persistir = true)
	private List<Participantes_reuniao> listaParticipantes;

	
	
	public Integer getCd_reuniao() {
		return cd_reuniao;
	}

	public void setCd_reuniao(Integer cd_reuniao) {
		this.cd_reuniao = cd_reuniao;
	}

	public String getNm_reuniao() {
		return nm_reuniao;
	}

	public void setNm_reuniao(String nm_reuniao) {
		this.nm_reuniao = nm_reuniao;
	}

	public String getDs_pauta() {
		return ds_pauta;
	}

	public void setDs_pauta(String ds_pauta) {
		this.ds_pauta = ds_pauta;
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

	public Integer getSt_reuniao() {
		return st_reuniao;
	}

	public void setSt_reuniao(Integer st_reuniao) {
		this.st_reuniao = st_reuniao;
	}

	public Integer getCd_usuario_coordenador() {
		return cd_usuario_coordenador;
	}

	public void setCd_usuario_coordenador(Integer cd_usuario_coordenador) {
		this.cd_usuario_coordenador = cd_usuario_coordenador;
	}

	public List<Participantes_reuniao> getListaParticipantes() {
		return listaParticipantes;
	}

	public void setListaParticipantes(List<Participantes_reuniao> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}
	
}
