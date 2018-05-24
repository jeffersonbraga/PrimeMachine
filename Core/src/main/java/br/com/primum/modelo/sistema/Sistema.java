package br.com.primum.modelo.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;


@Entity 
@org.hibernate.annotations.Entity(mutable=true)
@Table(name = "sistema")
public class Sistema implements Serializable {

	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-2712776360973824240L;

	@Column
	@Id
	private Integer cd_sistema;
	
	@Column
	private String nm_sistema;

	public Integer getCd_sistema() {
		return cd_sistema;
	}

	public void setCd_sistema(Integer cd_sistema) {
		this.cd_sistema = cd_sistema;
	}

	public String getNm_sistema() {
		return nm_sistema;
	}

	public void setNm_sistema(String nm_sistema) {
		this.nm_sistema = nm_sistema;
	}
}
