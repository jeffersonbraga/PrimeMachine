package br.com.opsocial.ejb.entity.procedimento;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="procedimento", schema="opsocial")
public class Procedimento extends Persistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "procedimento_seq")
	private Integer cd_procedimento;

	@Column
	private Integer cd_especialidade;
	
	@Column
	private String nm_procedimento;
	
	@Column
	private String ds_procedimento;

	private List<Procedimento> listaprocedimento;

	public Integer getCd_procedimento() {
		return cd_procedimento;
	}

	public Integer getCd_especialidade() {
		return cd_especialidade;
	}

	public String getNm_procedimento() {
		return nm_procedimento;
	}

	public String getDs_procedimento() {
		return ds_procedimento;
	}

	public List<Procedimento> getListaprocedimento() {
		return listaprocedimento;
	}

	public void setCd_procedimento(Integer cd_procedimento) {
		this.cd_procedimento = cd_procedimento;
	}

	public void setCd_especialidade(Integer cd_especialidade) {
		this.cd_especialidade = cd_especialidade;
	}

	public void setNm_procedimento(String nm_procedimento) {
		this.nm_procedimento = nm_procedimento;
	}

	public void setDs_procedimento(String ds_procedimento) {
		this.ds_procedimento = ds_procedimento;
	}

	public void setListaprocedimento(List<Procedimento> listaprocedimento) {
		this.listaprocedimento = listaprocedimento;
	}
}