package br.com.primum.modelo.viasoft.PEDIDOITEMCP;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PEDIDOITEMCP")
public class Pedidoitemcp  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDPEDIDO;

	@Column
	private Integer IDPEDIDOITEM;

	@Column
	private String IDITEMKIT;

	@Column
	private String COMPL;


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


	public Integer getIdpedidoitem(){
		return IDPEDIDOITEM;
	}


	public void setIdpedidoitem(Integer IDPEDIDOITEM){
		this.IDPEDIDOITEM = IDPEDIDOITEM;
	}


	public String getIditemkit(){
		return IDITEMKIT;
	}


	public void setIditemkit(String IDITEMKIT){
		this.IDITEMKIT = IDITEMKIT;
	}


	public String getCompl(){
		return COMPL;
	}


	public void setCompl(String COMPL){
		this.COMPL = COMPL;
	}

}