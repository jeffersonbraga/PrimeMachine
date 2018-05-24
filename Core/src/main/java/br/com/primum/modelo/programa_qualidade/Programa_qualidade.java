package br.com.primum.modelo.programa_qualidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.revisao_programa_qualidade.Revisao_programa_qualidade;

@XmlRootElement(name = "Programa_qualidade")
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="programa_qualidade")
public class Programa_qualidade  implements Serializable {

	
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-8634399630667445399L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_programa_qualidade;
	
	@Column
	private String nm_programa_qualidade;
	
	@Column
	private String ds_programa_qualidade;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.revisao_programa_qualidade.Revisao_programa_qualidade", campoBuscar="cd_programa_qualidade", persistir=false)
	private List<Revisao_programa_qualidade> listaRevisao_programa_qualidade;

	public Integer getCd_programa_qualidade() {
		return cd_programa_qualidade;
	}

	public void setCd_programa_qualidade(Integer cd_programa_qualidade) {
		this.cd_programa_qualidade = cd_programa_qualidade;
	}

	public String getNm_programa_qualidade() {
		return nm_programa_qualidade;
	}

	public void setNm_programa_qualidade(String nm_programa_qualidade) {
		this.nm_programa_qualidade = nm_programa_qualidade;
	}

	public String getDs_programa_qualidade() {
		return ds_programa_qualidade;
	}

	public void setDs_programa_qualidade(String ds_programa_qualidade) {
		this.ds_programa_qualidade = ds_programa_qualidade;
	}

	public List<Revisao_programa_qualidade> getListaRevisao_programa_qualidade() {
		return listaRevisao_programa_qualidade;
	}

	public void setListaRevisao_programa_qualidade(
			List<Revisao_programa_qualidade> listaRevisao_programa_qualidade) {
		this.listaRevisao_programa_qualidade = listaRevisao_programa_qualidade;
	}

    @Override
    public String toString() {

        return this.getNm_programa_qualidade();
    }

}