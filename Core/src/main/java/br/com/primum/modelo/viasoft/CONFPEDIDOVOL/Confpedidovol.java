package br.com.primum.modelo.viasoft.CONFPEDIDOVOL;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONFPEDIDOVOL")
public class Confpedidovol  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDPEDIDO;

	@Column
	private String IDITEM;

	@Column
	private String CODBARRVOL;

	@Column
	private Integer QTDECONF;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdpedido(){
		return IDPEDIDO;
	}


	public void setIdpedido(Integer IDPEDIDO){
		this.IDPEDIDO = IDPEDIDO;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public String getCodbarrvol(){
		return CODBARRVOL;
	}


	public void setCodbarrvol(String CODBARRVOL){
		this.CODBARRVOL = CODBARRVOL;
	}


	public Integer getQtdeconf(){
		return QTDECONF;
	}


	public void setQtdeconf(Integer QTDECONF){
		this.QTDECONF = QTDECONF;
	}

}