package br.com.primum.modelo.databasechangeloglock;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;

import java.util.Date;


@Entity
@Table(name="databasechangeloglock")
public class Databasechangeloglock  implements Serializable {

	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//1L;
	@Id
	@Column
	private Integer id;

	@Column
	private Date lockgranted;

	@Column
	private String lockedby;


	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id = id;
	}


	public Date getLockgranted(){
		return lockgranted;
	}


	public void setLockgranted(Date lockgranted){
		this.lockgranted = lockgranted;
	}


	public String getLockedby(){
		return lockedby;
	}


	public void setLockedby(String lockedby){
		this.lockedby = lockedby;
	}

}