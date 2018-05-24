package br.com.primum.modelo.participantes_treinamento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.descritivo_treinamento.Descritivo_treinamento;
import br.com.primum.modelo.pessoa.Pessoa;
import br.com.primum.modelo.registro_treinamento.Registro_treinamento;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="participantes_treinamento")
public class Participantes_treinamento {
	
	@Id
	@Column
	private Integer cd_participantes_treinamento;

	@Column
	private Integer cd_registro_treinamento;

	@Column
	private Integer cd_pessoa;
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.pessoa.Pessoa", campoBuscar="cd_pessoa", campoComparar="cd_pessoa")
	@Transient
	private Pessoa pessoa;

	@Transient
	private Registro_treinamento registro_treinamento;
	
	@Transient
	private Descritivo_treinamento descritivo_treinamento;

	public Integer getCd_participantes_treinamento() {
		return cd_participantes_treinamento;
	}

	public void setCd_participantes_treinamento(Integer cd_participantes_treinamento) {
		this.cd_participantes_treinamento = cd_participantes_treinamento;
	}

	public Integer getCd_registro_treinamento() {
		return cd_registro_treinamento;
	}

	public void setCd_registro_treinamento(Integer cd_registro_treinamento) {
		this.cd_registro_treinamento = cd_registro_treinamento;
	}

	public Integer getCd_pessoa() {
		return cd_pessoa;
	}

	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Registro_treinamento getRegistro_treinamento() {
		return registro_treinamento;
	}

	public void setRegistro_treinamento(Registro_treinamento registro_treinamento) {
		this.registro_treinamento = registro_treinamento;
	}
	
	/*****/
	
	public Descritivo_treinamento getDescritivo_treinamento() {
		return descritivo_treinamento;
	}

	public void setDescritivo_treinamento(Descritivo_treinamento descritivo_treinamento) {
		this.descritivo_treinamento = descritivo_treinamento;
	}
}
