package br.com.primum.modelo.exame_funcao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.exame_medico.Exame_medico;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="exame_funcao")
public class Exame_funcao {

	@Id
	@Column
	private Integer cd_exame_funcao;
	
	@Column
	private Integer cd_exame_medico;
	
	@Column
	private Integer cd_descricao_funcional;

	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.exame_medico.Exame_medico", campoBuscar="cd_exame_medico", campoComparar="cd_exame_medico")
	@Transient
	private Exame_medico exame_medico;
	
	
	
	public Exame_medico getExame_medico() {
		return exame_medico;
	}

	public void setExame_medico(Exame_medico exame_medico) {
		this.exame_medico = exame_medico;
	}

	public Integer getCd_exame_funcao() {
		return cd_exame_funcao;
	}

	public void setCd_exame_funcao(Integer cd_exame_funcao) {
		this.cd_exame_funcao = cd_exame_funcao;
	}

	public Integer getCd_exame_medico() {
		return cd_exame_medico;
	}

	public void setCd_exame_medico(Integer cd_exame_medico) {
		this.cd_exame_medico = cd_exame_medico;
	}

	public Integer getCd_descricao_funcional() {
		return cd_descricao_funcional;
	}

	public void setCd_descricao_funcional(Integer cd_descricao_funcional) {
		this.cd_descricao_funcional = cd_descricao_funcional;
	}	
	
}
