package br.com.opsocial.ejb.entity.questao;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="questao", schema="opsocial")
public class Questao extends Persistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "questao_seq")
	private Integer cd_questao;

	@Column
	private Integer cd_questionario;

	@Column
	private String ds_questao;

	@Column
	private String vl_questao;

	@Column
	private String ds_observacao;

	public Integer getCd_questao() {
		return cd_questao;
	}

	public Integer getCd_questionario() {
		return cd_questionario;
	}

	public String getDs_questao() {
		return ds_questao;
	}

	public String getVl_questao() {
		return vl_questao;
	}

	public String getDs_observacao() {
		return ds_observacao;
	}

	public void setCd_questao(Integer cd_questao) {
		this.cd_questao = cd_questao;
	}

	public void setCd_questionario(Integer cd_questionario) {
		this.cd_questionario = cd_questionario;
	}

	public void setDs_questao(String ds_questao) {
		this.ds_questao = ds_questao;
	}

	public void setVl_questao(String vl_questao) {
		this.vl_questao = vl_questao;
	}

	public void setDs_observacao(String ds_observacao) {
		this.ds_observacao = ds_observacao;
	}
}