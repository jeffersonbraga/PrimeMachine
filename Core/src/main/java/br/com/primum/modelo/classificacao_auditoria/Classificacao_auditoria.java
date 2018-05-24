package br.com.primum.modelo.classificacao_auditoria;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.faixa_class_auditoria.Faixa_class_auditoria;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="classificacao_auditoria")
public class Classificacao_auditoria {

	@Id
	@Column
	private Integer cd_classificacao_auditoria;

	@Column
	private String nm_classificacao_auditoria;

	@Column
	private String ds_classificacao_auditoria;

	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.faixa_class_auditoria.Faixa_class_auditoria", persistir=true, campoBuscar="cd_classificacao_auditoria")
	@Transient
	private List<Faixa_class_auditoria> listaFaixa_classificacao;

	public Integer getCd_classificacao_auditoria() {
		return cd_classificacao_auditoria;
	}

	public void setCd_classificacao_auditoria(Integer cd_classificacao_auditoria) {
		this.cd_classificacao_auditoria = cd_classificacao_auditoria;
	}

	public String getNm_classificacao_auditoria() {
		return nm_classificacao_auditoria;
	}

	public void setNm_classificacao_auditoria(String nm_classificacao_auditoria) {
		this.nm_classificacao_auditoria = nm_classificacao_auditoria;
	}

	public String getDs_classificacao_auditoria() {
		return ds_classificacao_auditoria;
	}

	public void setDs_classificacao_auditoria(String ds_classificacao_auditoria) {
		this.ds_classificacao_auditoria = ds_classificacao_auditoria;
	}

	public List<Faixa_class_auditoria> getListaFaixa_classificacao() {
		return listaFaixa_classificacao;
	}

	public void setListaFaixa_classificacao(
			List<Faixa_class_auditoria> listaFaixa_classificacao) {
		this.listaFaixa_classificacao = listaFaixa_classificacao;
	}
}