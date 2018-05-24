package br.com.primum.modelo.acao_corretiva_ouvidoria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.acao_corretiva.Acao_corretiva;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="acao_corretiva_ouvidoria")
public class Acao_corretiva_ouvidoria {

	@Id
	@Column
	private Integer cd_acao_corretiva_ouvidoria;
	
	@Column
	private Integer cd_acao_corretiva;
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.acao_corretiva.Acao_corretiva", campoBuscar="cd_acao_corretiva", campoComparar="cd_acao_corretiva")
	@Transient
	private Acao_corretiva acao_corretiva;

	public Integer getCd_acao_corretiva_ouvidoria() {
		return cd_acao_corretiva_ouvidoria;
	}

	public void setCd_acao_corretiva_ouvidoria(Integer cd_acao_corretiva_ouvidoria) {
		this.cd_acao_corretiva_ouvidoria = cd_acao_corretiva_ouvidoria;
	}

	public Integer getCd_acao_corretiva() {
		return cd_acao_corretiva;
	}

	public void setCd_acao_corretiva(Integer cd_acao_corretiva) {
		this.cd_acao_corretiva = cd_acao_corretiva;
	}

	public Acao_corretiva getAcao_corretiva() {
		return acao_corretiva;
	}

	public void setAcao_corretiva(Acao_corretiva acao_corretiva) {
		this.acao_corretiva = acao_corretiva;
	}
	
}
