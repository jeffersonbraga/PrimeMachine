package br.com.primum.modelo.tabela;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.campo.Campo;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="tabela")
public class Tabela {

	@Id
	private Integer cd_tabela;
	
	@Column
	private String nm_tabela;
	
	@Column
	private String ds_tabela;
	
	@Column
	private String ds_chaveprimaria;
	
	@Column
	private String ds_caminhoclasse;
	
	@Column
	private String ds_descricao;
	
	@Column
	private Integer tp_visivelprogramas;
	
	@Column
	private Integer tp_conf_notificacao;
	
	@Column
	private Integer tp_sincronizar;
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.campo.Campo", campoBuscar="cd_tabela", persistir=true)
	@Transient
	private List<Campo> listaCampos;
	
	public Integer getCd_tabela() {
		return cd_tabela;
	}
	
	public void setCd_tabela(Integer cd_tabela) {
		this.cd_tabela = cd_tabela;
	}
	
	public String getNm_tabela() {
		return nm_tabela;
	}
	
	public void setNm_tabela(String nm_tabela) {
		this.nm_tabela = nm_tabela;
	}
	
	public String getDs_tabela() {
		return ds_tabela;
	}
	
	public void setDs_tabela(String ds_tabela) {
		this.ds_tabela = ds_tabela;
	}
	
	public String getDs_chaveprimaria() {
		return ds_chaveprimaria;
	}
	
	public void setDs_chaveprimaria(String ds_chaveprimaria) {
		this.ds_chaveprimaria = ds_chaveprimaria;
	}
	
	public List<Campo> getListaCampos() {
		return listaCampos;
	}
	
	public void setListaCampos(List<Campo> listaCampos) {
		
		this.listaCampos = listaCampos;
	}

	public String getDs_caminhoclasse() {
		return ds_caminhoclasse;
	}

	public void setDs_caminhoclasse(String ds_caminhoclasse) {
		this.ds_caminhoclasse = ds_caminhoclasse;
	}

	public String getDs_descricao() {
		return ds_descricao;
	}

	public void setDs_descricao(String ds_descricao) {
		this.ds_descricao = ds_descricao;
	}

	public Integer getTp_visivelprogramas() {
		return tp_visivelprogramas;
	}

	public void setTp_visivelprogramas(Integer tp_visivelprogramas) {
		this.tp_visivelprogramas = tp_visivelprogramas;
	}
	
	public boolean equals(Tabela tabela) {

		boolean retorno = true;		
//		if ( (this.getCd_tabela() != null) && (!tabela.getCd_tabela().equals(this.getCd_tabela())) ) {
//			retorno = false;
//		}

		if ( (this.getNm_tabela() != null) && (!tabela.getNm_tabela().equals(this.getNm_tabela())) ) {
			retorno = false;
		}

//		if ( (this.getDs_tabela() != null) && (!tabela.getDs_tabela().equals(this.getDs_tabela())) ) {
//			retorno = false;
//		}

		if ( (this.getDs_chaveprimaria() != null) && (!tabela.getDs_chaveprimaria().equals(this.getDs_chaveprimaria())) ) {
			retorno = false;
		}

		if ( (this.getDs_caminhoclasse() != null) && (!tabela.getDs_caminhoclasse().equals(this.getDs_caminhoclasse())) ) {
			retorno = false;
		}

//		if ( (this.getDs_descricao() != null) && (!tabela.getDs_descricao().equals(this.getDs_descricao())) ) {
//			retorno = false;
//		}
//
//		if ( (this.getTp_visivelprogramas() != null) && (!tabela.getTp_visivelprogramas().equals(this.getTp_visivelprogramas())) ) {
//			retorno = false;
//		}

		return retorno;
	}

	public Integer getTp_conf_notificacao() {
		return tp_conf_notificacao;
	}

	public void setTp_conf_notificacao(Integer tp_conf_notificacao) {
		this.tp_conf_notificacao = tp_conf_notificacao;
	}

	public Integer getTp_sincronizar() {
		return tp_sincronizar;
	}

	public void setTp_sincronizar(Integer tp_sincronizar) {
		this.tp_sincronizar = tp_sincronizar;
	}
}
