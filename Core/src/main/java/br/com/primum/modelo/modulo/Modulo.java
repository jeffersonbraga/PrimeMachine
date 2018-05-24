package br.com.primum.modelo.modulo;

import java.io.Serializable;
import javax.persistence.*;

import br.com.primum.contexto.ContextoAplicacao;

/**
 */

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name = "modulo")
public class Modulo implements Serializable {
	//private static final long serialVersionUID = ContextoAplicao.SERIAL_PADRAO;// 1L;//1L;
	
	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-2589619205517908858L;

	@Id
	@Column(name = "cd_modulo", nullable = false)
	private Integer cd_modulo;

	@Column(name = "ds_modulo", length = 45)
	private String ds_modulo;

	public Integer getCd_modulo() {
		return cd_modulo;
	}

	public void setCd_modulo(Integer cd_modulo) {
		this.cd_modulo = cd_modulo;
	}

	public String getDs_modulo() {
		return ds_modulo;
	}

	public void setDs_modulo(String ds_modulo) {
		this.ds_modulo = ds_modulo;
	}
}
