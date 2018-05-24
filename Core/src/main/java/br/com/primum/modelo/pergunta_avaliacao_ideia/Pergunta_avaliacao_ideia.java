package br.com.primum.modelo.pergunta_avaliacao_ideia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="pergunta_avaliacao_ideia")
public class Pergunta_avaliacao_ideia {

	@Id
	@Column
	private Integer cd_pergunta_avaliacao_ideia;
	
	@Column
	private Integer cd_avaliacao_ideia;
	
	@Column
	private String ds_pergunta_avaliacao_ideia;
	
	@Column
	private Integer nr_nota_pgt_avl_ideia;

	
	public Integer getCd_pergunta_avaliacao_ideia() {
		return cd_pergunta_avaliacao_ideia;
	}

	public void setCd_pergunta_avaliacao_ideia(Integer cd_pergunta_avaliacao_ideia) {
		this.cd_pergunta_avaliacao_ideia = cd_pergunta_avaliacao_ideia;
	}

	public Integer getCd_avaliacao_ideia() {
		return cd_avaliacao_ideia;
	}

	public void setCd_avaliacao_ideia(Integer cd_avaliacao_ideia) {
		this.cd_avaliacao_ideia = cd_avaliacao_ideia;
	}

	public String getDs_pergunta_avaliacao_ideia() {
		return ds_pergunta_avaliacao_ideia;
	}

	public void setDs_pergunta_avaliacao_ideia(String ds_pergunta_avaliacao_ideia) {
		this.ds_pergunta_avaliacao_ideia = ds_pergunta_avaliacao_ideia;
	}

	public Integer getNr_nota_pgt_avl_ideia() {
		return nr_nota_pgt_avl_ideia;
	}

	public void setNr_nota_pgt_avl_ideia(Integer nr_nota_pgt_avl_ideia) {
		this.nr_nota_pgt_avl_ideia = nr_nota_pgt_avl_ideia;
	}

}
