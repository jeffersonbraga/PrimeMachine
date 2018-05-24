package br.com.primum.modelo.permissao_padrao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.programa.Programa;

@Entity 
@org.hibernate.annotations.Entity(mutable=true)
@Table(name="permissao_padrao")
public class Permissao_padrao {

	@Id
	@Column
	private Integer cd_permissao_padrao;
	
	@Column
	private Integer cd_programa;
	
	@Column
	private Integer cd_config_sistema;
	
//	@Transient
//	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.programa.Programa", campoBuscar="cd_programa", campoComparar="cd_programa", notificador=false, persistir=false)
//	private Programa programa;

	public Integer getCd_permissao_padrao() {
		return cd_permissao_padrao;
	}

	public void setCd_permissao_padrao(Integer cd_permissao_padrao) {
		this.cd_permissao_padrao = cd_permissao_padrao;
	}

	public Integer getCd_programa() {
		return cd_programa;
	}

	public void setCd_programa(Integer cd_programa) {
		this.cd_programa = cd_programa;
	}

	public Integer getCd_config_sistema() {
		return cd_config_sistema;
	}

	public void setCd_config_sistema(Integer cd_config_sistema) {
		this.cd_config_sistema = cd_config_sistema;
	}

//	public Programa getPrograma() {
//		return programa;
//	}
//
//	public void setPrograma(Programa programa) {
//		this.programa = programa;
//	}
}
