package br.com.primum.modelo.classificacao_ouvidoria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="classificacao_ouvidoria")
public class Classificacao_ouvidoria {

	@Id
	@Column
	private Integer cd_classificacao_ouvidoria;
	
	@Column
	private String ds_classificacao_ouvidoria;

	
	
	public Integer getCd_classificacao_ouvidoria() {
		return cd_classificacao_ouvidoria;
	}

	public void setCd_classificacao_ouvidoria(Integer cd_classificacao_ouvidoria) {
		this.cd_classificacao_ouvidoria = cd_classificacao_ouvidoria;
	}

	public String getDs_classificacao_ouvidoria() {
		return ds_classificacao_ouvidoria;
	}

	public void setDs_classificacao_ouvidoria(String ds_classificacao_ouvidoria) {
		this.ds_classificacao_ouvidoria = ds_classificacao_ouvidoria;
	}

}
