package br.com.primum.modelo.viasoft.WMCONFSERVER;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="WMCONFSERVER")
public class Wmconfserver  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer PORTA;

	@Column
	private String HOST;

	@Column
	private String USAECOMMERCE;

	@Column
	private String HOSTLOCAL;

	@Column
	private Integer PORTALOCAL;


	public Integer getPorta(){
		return PORTA;
	}


	public void setPorta(Integer PORTA){
		this.PORTA = PORTA;
	}


	public String getHost(){
		return HOST;
	}


	public void setHost(String HOST){
		this.HOST = HOST;
	}


	public String getUsaecommerce(){
		return USAECOMMERCE;
	}


	public void setUsaecommerce(String USAECOMMERCE){
		this.USAECOMMERCE = USAECOMMERCE;
	}


	public String getHostlocal(){
		return HOSTLOCAL;
	}


	public void setHostlocal(String HOSTLOCAL){
		this.HOSTLOCAL = HOSTLOCAL;
	}


	public Integer getPortalocal(){
		return PORTALOCAL;
	}


	public void setPortalocal(Integer PORTALOCAL){
		this.PORTALOCAL = PORTALOCAL;
	}

}