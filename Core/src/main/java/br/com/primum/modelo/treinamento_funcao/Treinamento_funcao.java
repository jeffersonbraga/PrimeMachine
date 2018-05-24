package br.com.primum.modelo.treinamento_funcao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.descritivo_treinamento.Descritivo_treinamento;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="treinamento_funcao")
public class Treinamento_funcao {
	
	@Id
	@Column
	private Integer cd_treinamento_funcao;

	@Column
	private Integer cd_descricao_funcional;

	@Column
	private Integer cd_descritivo_treinamento;
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.descritivo_treinamento.Descritivo_treinamento", campoBuscar="cd_descritivo_treinamento", campoComparar="cd_descritivo_treinamento")
	@Transient
	private Descritivo_treinamento descritivo_treinamento;

	
	public Integer getCd_treinamento_funcao() {
		return cd_treinamento_funcao;
	}

	public void setCd_treinamento_funcao(Integer cd_treinamento_funcao) {
		this.cd_treinamento_funcao = cd_treinamento_funcao;
	}

	public Integer getCd_descricao_funcional() {
		return cd_descricao_funcional;
	}

	public void setCd_descricao_funcional(Integer cd_descricao_funcional) {
		this.cd_descricao_funcional = cd_descricao_funcional;
	}

	public Integer getCd_descritivo_treinamento() {
		return cd_descritivo_treinamento;
	}

	public void setCd_descritivo_treinamento(Integer cd_descritivo_treinamento) {
		this.cd_descritivo_treinamento = cd_descritivo_treinamento;
	}

	public Descritivo_treinamento getDescritivo_treinamento() {
		return descritivo_treinamento;
	}

	public void setDescritivo_treinamento(Descritivo_treinamento descritivo_treinamento) {
		this.descritivo_treinamento = descritivo_treinamento;
	}
}
