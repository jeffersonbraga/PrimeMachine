package br.com.primum.modelo.categoria_ocorrencia;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.causa_efeito.Causa_efeito;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="categoria_ocorrencia")
public class Categoria_ocorrencia {

	@Id
	@Column
	private Integer cd_categoria_ocorrencia;
	
	@Column
	private Integer cd_categoria_ishikawa;

	@Column
	private Integer cd_diagrama_helpdesk;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.causa_efeito.Causa_efeito", campoBuscar="cd_categoria_ocorrencia", persistir=true)
	private List<Causa_efeito> listaCausas;

	
	public Integer getCd_diagrama_helpdesk() {
		return cd_diagrama_helpdesk;
	}

	public void setCd_diagrama_helpdesk(Integer cd_diagrama_helpdesk) {
		this.cd_diagrama_helpdesk = cd_diagrama_helpdesk;
	}

	public Integer getCd_categoria_ocorrencia() {
		return cd_categoria_ocorrencia;
	}

	public void setCd_categoria_ocorrencia(Integer cd_categoria_ocorrencia) {
		this.cd_categoria_ocorrencia = cd_categoria_ocorrencia;
	}

	public Integer getCd_categoria_ishikawa() {
		return cd_categoria_ishikawa;
	}

	public void setCd_categoria_ishikawa(Integer cd_categoria_ishikawa) {
		this.cd_categoria_ishikawa = cd_categoria_ishikawa;
	}

//	public Integer getCd_helpdesk_ocorrencia() {
//		return cd_helpdesk_ocorrencia;
//	}
//
//	public void setCd_helpdesk_ocorrencia(Integer cd_helpdesk_ocorrencia) {
//		this.cd_helpdesk_ocorrencia = cd_helpdesk_ocorrencia;
//	}

	public List<Causa_efeito> getListaCausas() {
		return listaCausas;
	}

	public void setListaCausas(List<Causa_efeito> listaCausas) {
		this.listaCausas = listaCausas;
	}
}
