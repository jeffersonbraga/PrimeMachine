package br.com.primum.modelo.resolucao_ocorrencia;

//import java.util.Comparator;
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
import br.com.primum.modelo.ocorrencia_ouvidoria.Ocorrencia_ouvidoria;
import br.com.primum.modelo.plano_acao_ocorrencia.Plano_acao_ocorrencia;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="resolucao_ocorrencia")
//public class Resolucao_ocorrencia implements Comparator<Resolucao_ocorrencia>{
public class Resolucao_ocorrencia{
	@Id
	@Column
	private Integer cd_resolucao_ocorrencia;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_resolucao_ocorrencia;
	
	@Column
	private String ds_resposta_reclamante;
	
	@Column
	private Integer cd_ocorrencia_ouvidoria;
	
	@Column
	private Integer st_ocorrencia_ouvidoria;

	@Column
	private Integer st_resolucao_ocorrencia;
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.plano_acao_ocorrencia.Plano_acao_ocorrencia", persistir=true, campoBuscar="cd_resolucao_ocorrencia")
	@Transient
	private List<Plano_acao_ocorrencia> listaPlanos;
	
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.ocorrencia_ouvidoria.Ocorrencia_ouvidoria", campoBuscar="cd_ocorrencia_ouvidoria", campoComparar="cd_ocorrencia_ouvidoria")
	@Transient
	private Ocorrencia_ouvidoria ocorrencia_ouvidoria;
	
	
	@Transient
	private Integer vl_matrizgut;
	


	public Integer getVl_matrizgut() {
		return vl_matrizgut;
	}

	public void setVl_matrizgut(Integer vl_matrizgut) {
		this.vl_matrizgut = vl_matrizgut;
	}

	public Integer getSt_resolucao_ocorrencia() {
		return st_resolucao_ocorrencia;
	}

	public void setSt_resolucao_ocorrencia(Integer st_resolucao_ocorrencia) {
		this.st_resolucao_ocorrencia = st_resolucao_ocorrencia;
	}

	public Ocorrencia_ouvidoria getOcorrencia_ouvidoria() {
		return ocorrencia_ouvidoria;
	}

	public void setOcorrencia_ouvidoria(Ocorrencia_ouvidoria ocorrencia_ouvidoria) {
		this.ocorrencia_ouvidoria = ocorrencia_ouvidoria;
	}

	public List<Plano_acao_ocorrencia> getListaPlanos() {
		return listaPlanos;
	}

	public void setListaPlanos(List<Plano_acao_ocorrencia> listaPlanos) {
		this.listaPlanos = listaPlanos;
	}

	public Integer getCd_resolucao_ocorrencia() {
		return cd_resolucao_ocorrencia;
	}

	public void setCd_resolucao_ocorrencia(Integer cd_resolucao_ocorrencia) {
		this.cd_resolucao_ocorrencia = cd_resolucao_ocorrencia;
	}

	public Date getDt_resolucao_ocorrencia() {
		return dt_resolucao_ocorrencia;
	}

	public void setDt_resolucao_ocorrencia(Date dt_resolucao_ocorrencia) {
		this.dt_resolucao_ocorrencia = dt_resolucao_ocorrencia;
	}

	public String getDs_resposta_reclamante() {
		return ds_resposta_reclamante;
	}

	public void setDs_resposta_reclamante(String ds_resposta_reclamante) {
		this.ds_resposta_reclamante = ds_resposta_reclamante;
	}

	public Integer getCd_ocorrencia_ouvidoria() {
		return cd_ocorrencia_ouvidoria;
	}

	public void setCd_ocorrencia_ouvidoria(Integer cd_ocorrencia_ouvidoria) {
		this.cd_ocorrencia_ouvidoria = cd_ocorrencia_ouvidoria;
	}

	public Integer getSt_ocorrencia_ouvidoria() {
		return st_ocorrencia_ouvidoria;
	}

	public void setSt_ocorrencia_ouvidoria(Integer st_ocorrencia_ouvidoria) {
		this.st_ocorrencia_ouvidoria = st_ocorrencia_ouvidoria;
	}

//	@Override
//	public int compare(Resolucao_ocorrencia arg0, Resolucao_ocorrencia arg1) {
//		return arg0.vl_matrizgut < arg1.vl_matrizgut ? -1 : (arg0.vl_matrizgut > arg1.vl_matrizgut ? +1 : 0);
//	}

	
}

