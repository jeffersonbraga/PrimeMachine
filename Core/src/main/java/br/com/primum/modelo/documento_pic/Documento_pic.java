package br.com.primum.modelo.documento_pic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="documento_pic")
public class Documento_pic {

	@Id
	@Column
	private Integer cd_documento_pic;
	
	@Column
	private Integer cd_pic;
	
	@Column
	private String ds_caminho;
	
	@Column
	private String nm_documento;

	
	public Integer getCd_documento_pic() {
		return cd_documento_pic;
	}

	public void setCd_documento_pic(Integer cd_documento_pic) {
		this.cd_documento_pic = cd_documento_pic;
	}

	public Integer getCd_pic() {
		return cd_pic;
	}

	public void setCd_pic(Integer cd_pic) {
		this.cd_pic = cd_pic;
	}

	public String getDs_caminho() {
		return ds_caminho;
	}

	public void setDs_caminho(String ds_caminho) {
		this.ds_caminho = ds_caminho;
	}

	public String getNm_documento() {
		return nm_documento;
	}

	public void setNm_documento(String nm_documento) {
		this.nm_documento = nm_documento;
	}
		
}
