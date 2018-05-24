package br.com.opsocial.ejb.entity.questionario;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.questao.Questao;

@Entity
@Table(name="questionario", schema="opsocial")
public class Questionario extends Persistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "questionario_seq")
	private Integer cd_questionario;

	@Column
	private Integer cd_especialidade;

	private List<Questao> questoes;

	public Integer getCd_questionario() {
		return cd_questionario;
	}

	public Integer getCd_especialidade() {
		return cd_especialidade;
	}

	public List<Questao> getQuestoes() {
		return questoes;
	}

	public void setCd_questionario(Integer cd_questionario) {
		this.cd_questionario = cd_questionario;
	}

	public void setCd_especialidade(Integer cd_especialidade) {
		this.cd_especialidade = cd_especialidade;
	}

	public void setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
	}
}