package br.com.primum.modelo.analise_ocorrencia;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.acao_corretiva_helpdesk.Acao_corretiva_helpdesk;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="registro_ocorrencia")
public class Analise_ocorrencia {

	@Id
	@Column
	private Integer cd_analise_ocorrencia;

	@Column
	private Integer tp_analise;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.acao_corretiva_ocorrencia.Acao_corretiva_ocorrencia", campoBuscar="cd_analise_ocorrencia", persistir=true)
	private List<Acao_corretiva_helpdesk> listaAcoesImediatas;

	public Integer getCd_analise_ocorrencia() {
		return cd_analise_ocorrencia;
	}

	public void setCd_analise_ocorrencia(Integer cd_analise_ocorrencia) {
		this.cd_analise_ocorrencia = cd_analise_ocorrencia;
	}

	public Integer getTp_analise() {
		return tp_analise;
	}

	public void setTp_analise(Integer tp_analise) {
		this.tp_analise = tp_analise;
	}

	public List<Acao_corretiva_helpdesk> getListaAcoesImediatas() {
		return listaAcoesImediatas;
	}

	public void setListaAcoesImediatas(
			List<Acao_corretiva_helpdesk> listaAcoesImediatas) {
		this.listaAcoesImediatas = listaAcoesImediatas;
	}
	
	
}
