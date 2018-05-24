package br.com.primum.modelo.viasoft.CONFPEDIDO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CONFPEDIDO")
public class Confpedido  implements Serializable {

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
	private Integer QTDETRIBCONF;

	@Column
	private Integer QTDEVOLCONF;


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


	public Integer getQtdetribconf(){
		return QTDETRIBCONF;
	}


	public void setQtdetribconf(Integer QTDETRIBCONF){
		this.QTDETRIBCONF = QTDETRIBCONF;
	}


	public Integer getQtdevolconf(){
		return QTDEVOLCONF;
	}


	public void setQtdevolconf(Integer QTDEVOLCONF){
		this.QTDEVOLCONF = QTDEVOLCONF;
	}

}