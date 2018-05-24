package br.com.primum.modelo.config_sistema;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.permissao_padrao.Permissao_padrao;
import br.com.primum.modelo.sistema.Sistema;

@Entity 
@org.hibernate.annotations.Entity(mutable=true)
@Table(name="config_sistema")
public class Config_sistema {

	@Id
	@Column
	private Integer cd_config_sistema;
	
	@Column
	private Integer st_gerar_permissao;
	
	@Column
	private Integer st_gerar_plano_acao;
	
	@Column
	private Integer st_validar_campos;
	
	@Column
	private Integer st_verif_status;
	
	@Column
	private Integer nr_intervalo_verif;
	
	@Column
	private Integer cd_sistema;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.permissao_padrao.Permissao_padrao", campoBuscar="cd_config_sistema", persistir=true)
	private List<Permissao_padrao> listaPermissoesPadrao;
	
	@Transient
	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.sistema.Sistema", campoBuscar="cd_sistema", campoComparar="cd_sistema", notificador=false, persistir=false)
	private Sistema sistema;
	
	
	public Integer getCd_config_sistema() {
		return cd_config_sistema;
	}

	public void setCd_config_sistema(Integer cd_config_sistema) {
		this.cd_config_sistema = cd_config_sistema;
	}

	public Integer getSt_gerar_permissao() {
		return st_gerar_permissao;
	}

	public void setSt_gerar_permissao(Integer st_gerar_permissao) {
		this.st_gerar_permissao = st_gerar_permissao;
	}

	public Integer getSt_gerar_plano_acao() {
		return st_gerar_plano_acao;
	}

	public void setSt_gerar_plano_acao(Integer st_gerar_plano_acao) {
		this.st_gerar_plano_acao = st_gerar_plano_acao;
	}

	public Integer getSt_validar_campos() {
		return st_validar_campos;
	}

	public void setSt_validar_campos(Integer st_validar_campos) {
		this.st_validar_campos = st_validar_campos;
	}

	public List<Permissao_padrao> getListaPermissoesPadrao() {
		return listaPermissoesPadrao;
	}

	public void setListaPermissoesPadrao(
			List<Permissao_padrao> listaPermissoesPadrao) {
		this.listaPermissoesPadrao = listaPermissoesPadrao;
	}

	
	public Integer getCd_sistema() {
		return cd_sistema;
	}

	public void setCd_sistema(Integer cd_sistema) {
		this.cd_sistema = cd_sistema;
	}

	public Integer getSt_verif_status() {
		return st_verif_status;
	}

	public void setSt_verif_status(Integer st_verif_status) {
		this.st_verif_status = st_verif_status;
	}

	public Integer getNr_intervalo_verif() {
		return nr_intervalo_verif;
	}

	public void setNr_intervalo_verif(Integer nr_intervalo_verif) {
		this.nr_intervalo_verif = nr_intervalo_verif;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	
}
