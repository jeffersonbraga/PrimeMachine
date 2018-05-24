package br.com.primum.modelo.anexo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="anexo")
public class Anexo {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_anexo;
	
	@Column
	private Integer cd_atividade;
	
	@Column
	private String nm_anexo;
	
	@Column
	private String ds_caminhoAnexo;

	public Integer getCd_anexo() {
		return cd_anexo;
	}

	public void setCd_anexo(Integer cd_anexo) {
		this.cd_anexo = cd_anexo;
	}

	public Integer getCd_atividade() {
		return cd_atividade;
	}

	public void setCd_atividade(Integer cd_atividade) {
		this.cd_atividade = cd_atividade;
	}

	public String getNm_anexo() {
		return nm_anexo;
	}

	public void setNm_anexo(String nm_anexo) {
		this.nm_anexo = nm_anexo;
	}

	public String getDs_caminhoAnexo() {
		return ds_caminhoAnexo;
	}

	public void setDs_caminhoAnexo(String ds_caminhoAnexo) {
		this.ds_caminhoAnexo = ds_caminhoAnexo;
	}
}