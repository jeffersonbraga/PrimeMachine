package br.com.primum.modelo.classificacao_helpdesk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="classificacao_helpdesk")
public class Classificacao_helpdesk {

	@Id
	@Column
	private Integer cd_classificacao_helpdesk;

	@Column
	private String ds_classificacao_helpdesk;

	public Integer getCd_classificacao_helpdesk() {
		return cd_classificacao_helpdesk;
	}

	public void setCd_classificacao_helpdesk(Integer cd_classificacao_helpdesk) {
		this.cd_classificacao_helpdesk = cd_classificacao_helpdesk;
	}

	public String getDs_classificacao_helpdesk() {
		return ds_classificacao_helpdesk;
	}

	public void setDs_classificacao_helpdesk(String ds_classificacao_helpdesk) {
		this.ds_classificacao_helpdesk = ds_classificacao_helpdesk;
	}
}
