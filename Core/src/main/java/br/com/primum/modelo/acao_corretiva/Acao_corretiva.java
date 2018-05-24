package br.com.primum.modelo.acao_corretiva;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="acao_corretiva")
public class Acao_corretiva implements Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//227219489541486140L;

	@Id
	@Column
	private Integer cd_acao_corretiva;
	
	@Column
	private String nm_acao_corretiva;
	
	@Column
	private String ds_acao_corretiva;

	
	public Integer getCd_acao_corretiva() {
		return cd_acao_corretiva;
	}

	public void setCd_acao_corretiva(Integer cd_acao_corretiva) {
		this.cd_acao_corretiva = cd_acao_corretiva;
	}

	public String getNm_acao_corretiva() {
		return nm_acao_corretiva;
	}

	public void setNm_acao_corretiva(String nm_acao_corretiva) {
		this.nm_acao_corretiva = nm_acao_corretiva;
	}

	public String getDs_acao_corretiva() {
		return ds_acao_corretiva;
	}

	public void setDs_acao_corretiva(String ds_acao_corretiva) {
		this.ds_acao_corretiva = ds_acao_corretiva;
	}
}
