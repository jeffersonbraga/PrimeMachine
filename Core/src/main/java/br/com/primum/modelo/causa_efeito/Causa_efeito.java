package br.com.primum.modelo.causa_efeito;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.plano_acao_hp.Plano_acao_hp;

/**
 * @author Usuario
 *
 */
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="causa_efeito")
public class Causa_efeito {

	@Id
	@Column
	private Integer cd_causa_efeito;
	
	@Column
	private Integer cd_categoria_ocorrencia;
		
	@Column
	private Integer cd_resp_criar_causa;
		
	@Column
	private String nm_causa_efeito;
	
	@Column
	private String ds_causa_efeito;
	
	@Column
	private Integer st_causa_efeito;
	
	@Column
	private Integer vl_prioridade;

	@Transient
	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.plano_acao_hp.Plano_acao_hp", campoBuscar="cd_causa_efeito", campoComparar="cd_causa_efeito", persistir=true, notificador=false)
	private Plano_acao_hp plano_acao;
	
	
	public Integer getCd_resp_criar_causa() {
		return cd_resp_criar_causa;
	}

	public void setCd_resp_criar_causa(Integer cd_resp_criar_causa) {
		this.cd_resp_criar_causa = cd_resp_criar_causa;
	}

	public Integer getCd_causa_efeito() {
		return cd_causa_efeito;
	}

	public void setCd_causa_efeito(Integer cd_causa_efeito) {
		this.cd_causa_efeito = cd_causa_efeito;
	}

	public Integer getCd_categoria_ocorrencia() {
		return cd_categoria_ocorrencia;
	}

	public void setCd_categoria_ocorrencia(Integer cd_categoria_ocorrencia) {
		this.cd_categoria_ocorrencia = cd_categoria_ocorrencia;
	}

	public String getNm_causa_efeito() {
		return nm_causa_efeito;
	}

	public void setNm_causa_efeito(String nm_causa_efeito) {
		this.nm_causa_efeito = nm_causa_efeito;
	}

	public String getDs_causa_efeito() {
		return ds_causa_efeito;
	}

	public void setDs_causa_efeito(String ds_causa_efeito) {
		this.ds_causa_efeito = ds_causa_efeito;
	}

	public Integer getSt_causa_efeito() {
		return st_causa_efeito;
	}

	public void setSt_causa_efeito(Integer st_causa_efeito) {
		this.st_causa_efeito = st_causa_efeito;
	}

	public Integer getVl_prioridade() {
		return vl_prioridade;
	}

	public void setVl_prioridade(Integer vl_prioridade) {
		this.vl_prioridade = vl_prioridade;
	}

	public Plano_acao_hp getPlano_acao() {
		return plano_acao;
	}

	public void setPlano_acao(Plano_acao_hp plano_acao) {
		this.plano_acao = plano_acao;
	}
	
}
