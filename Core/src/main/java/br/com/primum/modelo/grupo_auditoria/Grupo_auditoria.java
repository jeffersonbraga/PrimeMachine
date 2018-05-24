package br.com.primum.modelo.grupo_auditoria;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.pergunta_auditoria.Pergunta_auditoria;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="grupo_auditoria")
public class Grupo_auditoria {

	@Id
	@Column
	private Integer cd_grupo_auditoria;
	
	@Column
	private Integer cd_auditoria;
	
	@Column
	private String nm_grupo_auditoria;
	
	@Column
	private Integer nr_ordem;
	
	@Column
	private Float vl_peso;
	
	@Column
	private String ds_grupo_auditoria;
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.pergunta_auditoria.Pergunta_auditoria", persistir=true, campoBuscar="cd_grupo_auditoria")
	@Transient
	private List<Pergunta_auditoria> listaPerguntasAuditoria;


	public Integer getCd_grupo_auditoria() {
		return cd_grupo_auditoria;
	}

	public void setCd_grupo_auditoria(Integer cd_grupo_auditoria) {
		this.cd_grupo_auditoria = cd_grupo_auditoria;
	}

	public Integer getCd_auditoria() {
		return cd_auditoria;
	}

	public void setCd_auditoria(Integer cd_auditoria) {
		this.cd_auditoria = cd_auditoria;
	}

	public String getNm_grupo_auditoria() {
		return nm_grupo_auditoria;
	}

	public void setNm_grupo_auditoria(String nm_grupo_auditoria) {
		this.nm_grupo_auditoria = nm_grupo_auditoria;
	}

	public Integer getNr_ordem() {
		return nr_ordem;
	}

	public void setNr_ordem(Integer nr_ordem) {
		this.nr_ordem = nr_ordem;
	}

	public Float getVl_peso() {
		return vl_peso;
	}

	public void setVl_peso(Float vl_peso) {
		this.vl_peso = vl_peso;
	}

	public String getDs_grupo_auditoria() {
		return ds_grupo_auditoria;
	}

	public void setDs_grupo_auditoria(String ds_grupo_auditoria) {
		this.ds_grupo_auditoria = ds_grupo_auditoria;
	}

	public List<Pergunta_auditoria> getListaPerguntasAuditoria() {
		return listaPerguntasAuditoria;
	}

	public void setListaPerguntasAuditoria(
			List<Pergunta_auditoria> listaPerguntasAuditoria) {
		this.listaPerguntasAuditoria = listaPerguntasAuditoria;
	}
}