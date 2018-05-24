package br.com.primum.modelo.categoria_ishikawa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="categoria_ishikawa")
public class Categoria_ishikawa {

	@Id
	@Column
	private Integer cd_categoria_ishikawa;
	
	@Column
	private String nm_categoria_ishikawa;

	public Integer getCd_categoria_ishikawa() {
		return cd_categoria_ishikawa;
	}

	public void setCd_categoria_ishikawa(Integer cd_categoria_ishikawa) {
		this.cd_categoria_ishikawa = cd_categoria_ishikawa;
	}

	public String getNm_categoria_ishikawa() {
		return nm_categoria_ishikawa;
	}

	public void setNm_categoria_ishikawa(String nm_categoria_ishikawa) {
		this.nm_categoria_ishikawa = nm_categoria_ishikawa;
	}
	
}
